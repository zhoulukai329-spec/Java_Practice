package com.example.demo.controller;

import com.example.demo.dto.CreateCardRequest;
import com.example.demo.dto.MoneyRequest;
import com.example.demo.dto.PasswordRequest;
import com.example.demo.service.CardService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Map;

@RestController
@RequestMapping("/cards")
public class CardController {
    private final CardService cardService;

    public CardController(CardService cardService) {
        this.cardService = cardService;
    }

    @GetMapping("/{studentId}/balance")
    public Map<String, BigDecimal> checkMoneyByStudentId(@PathVariable String studentId) {
        return Map.of("balance", cardService.checkMoneyByStudentId(studentId));
    } // 键值对

    @PostMapping("/{studentId}/top-ups")
    public void topUp(@PathVariable String studentId, @Valid @RequestBody MoneyRequest request) {
        cardService.topUp(request.getAmount(), studentId);
    }

    @PutMapping("/{studentId}/lost")
    public void lostCard(@PathVariable String studentId) {
        cardService.lostCard(studentId);
    }

    @PutMapping("/{studentId}/password")
    public void resetPassword(@PathVariable String studentId, @Valid @RequestBody PasswordRequest request) {
        cardService.resetPassword(request.getPassword(), studentId);
    }

    @PostMapping("/{studentId}/transactions")
    public void transaction(@PathVariable String studentId, @Valid @RequestBody MoneyRequest request) {
        cardService.transaction(request.getAmount(), studentId);
    }

    @DeleteMapping("/{studentId}")
    public void deleteById(@PathVariable String studentId) {
        cardService.deleteById(studentId);
    }

    @PostMapping
    public void insert(@Valid @RequestBody CreateCardRequest request) {
        cardService.insert(request);
    }
}
