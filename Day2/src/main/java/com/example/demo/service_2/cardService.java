package main.java.com.example.demo.service_2;

import com.example.demo.entity_2.cardEntity;
import com.example.demo.mapper_2.cardMapper;
import org.springframework.stereotype.Service;

@Service
public class cardService {

    private final cardMapper cardMapper;

    public cardService(cardMapper cardMapper) {
        this.cardMapper = cardMapper;
    }

    public Float checkMoneyByStdId(String studentId) {
        return cardMapper.checkMoneyByStdId(studentId);
    }

    public void topUp(float money, String studentId) {
        cardMapper.topUp(money, studentId);
    }

    public void lostCard(String studentId, String name) {
        cardMapper.lostCard(studentId, name);
    }

    public void resetPassword(String password, String studentId) {
        cardMapper.resetPassword(password, studentId);
    }

    public void transaction(float money, String studentId) {
        cardMapper.transaction(money, studentId);
    }

    public void deleteById(String studentId) {
        cardMapper.deleteById(studentId);
    }

    public void insert(cardEntity card) {
        cardMapper.insert(card);
    }
}
