package com.example.demo.entity;

import java.math.BigDecimal;

public class CardEntity {
    private String studentId;
    private String name;
    private String password;
    private BigDecimal money;
    private String status;

    public CardEntity() {
    }

    public CardEntity(String studentId, String name, String password, BigDecimal money, String status) {
        this.studentId = studentId;
        this.name = name;
        this.password = password;
        this.money = money;
        this.status = status;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
