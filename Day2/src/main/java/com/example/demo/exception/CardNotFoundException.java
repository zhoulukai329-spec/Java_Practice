package com.example.demo.exception;

public class CardNotFoundException extends RuntimeException {
    public CardNotFoundException(String studentId) {
        super("student card not found: " + studentId);
    }
}
