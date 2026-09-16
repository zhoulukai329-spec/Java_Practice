package com.example.demo.mapper_2;

import com.example.demo.entity_2.cardEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface cardMapper {

    Float checkMoneyByStdId(@Param("studentId") String studentId);

    void topUp(@Param("money") float money, @Param("studentId") String studentId);

    void lostCard(@Param("studentId") String studentId, @Param("name") String name);

    void resetPassword(@Param("password") String password, @Param("studentId") String studentId);

    void transaction(@Param("money") float money, @Param("studentId") String studentId);

    void deleteById(@Param("studentId") String studentId);

    void insert(cardEntity card);
}
