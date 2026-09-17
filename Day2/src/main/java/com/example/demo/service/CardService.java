package com.example.demo.service;

import com.example.demo.dto.CreateCardRequest;
import com.example.demo.entity.CardEntity;
import com.example.demo.exception.BusinessException;
import com.example.demo.exception.CardNotFoundException;
import com.example.demo.mapper.CardMapper;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class CardService {
    private final CardMapper cardMapper;

    public CardService(CardMapper cardMapper) {
        this.cardMapper = cardMapper;
    }

    public BigDecimal checkMoneyByStudentId(String studentId) {
        BigDecimal balance = cardMapper.checkMoneyByStudentId(studentId);
        if (balance == null) {
            throw new CardNotFoundException(studentId);
        }
        return balance;
    }

    public void topUp(BigDecimal amount, String studentId) {
        int affectedRows = cardMapper.topUp(amount, studentId);
        if (affectedRows == 0) {
            CardEntity card = requireCard(studentId);
            if ("LOST".equals(card.getStatus())) {
                throw new BusinessException("lost card cannot be topped up");
            }
            throw new BusinessException("top-up failed");
        }
    }

    public void lostCard(String studentId) {
        int affectedRows = cardMapper.markLost(studentId);
        if (affectedRows == 0) {
            CardEntity card = requireCard(studentId);
            if ("LOST".equals(card.getStatus())) {
                throw new BusinessException("card is already marked as lost");
            }
            throw new BusinessException("failed to mark card as lost");
        }
    }

    public void resetPassword(String password, String studentId) {
        requireCard(studentId);
        cardMapper.resetPassword(password, studentId);
    }

    @Transactional
    public void transaction(BigDecimal amount, String studentId) {
        int affectedRows = cardMapper.deduct(amount, studentId);
        if (affectedRows == 1) {
            return;
        }

        CardEntity card = requireCard(studentId);
        if ("LOST".equals(card.getStatus())) {
            throw new BusinessException("lost card cannot be used for payment");
        }
        if (card.getMoney().compareTo(amount) < 0) {
            throw new BusinessException("insufficient balance");
        }
        throw new BusinessException("transaction failed");
    }

    public void deleteById(String studentId) {
        if (cardMapper.deleteById(studentId) == 0) {
            throw new CardNotFoundException(studentId);
        }
    }

    public void insert(CreateCardRequest request) {
        CardEntity card = new CardEntity(
                request.getStudentId(),
                request.getName(),
                request.getPassword(),
                request.getInitialMoney(),
                "ACTIVE"
        );

        try {
            cardMapper.insert(card);
        } catch (DuplicateKeyException e) {
            throw new BusinessException("student card already exists: " + request.getStudentId());
        }
    }

    private CardEntity requireCard(String studentId) {
        CardEntity card = cardMapper.findByStudentId(studentId);
        if (card == null) {
            throw new CardNotFoundException(studentId);
        }
        return card;
    }
}
