package com.example.demo.mapper;

import com.example.demo.entity.CardEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;

@Mapper
public interface CardMapper {
    CardEntity findByStudentId(@Param("studentId") String studentId);

    BigDecimal checkMoneyByStudentId(@Param("studentId") String studentId);

    int topUp(@Param("amount") BigDecimal amount, @Param("studentId") String studentId);

    int markLost(@Param("studentId") String studentId);

    int resetPassword(@Param("password") String password, @Param("studentId") String studentId);

    int deduct(@Param("amount") BigDecimal amount, @Param("studentId") String studentId);

    int deleteById(@Param("studentId") String studentId);

    int insert(CardEntity card);
}
