package com.example.demo.entity;

public class User {
    private Long id;
    private String nickname;
    private Integer age;
    private String password;

    public User() {
    }

    public User(Long id, String nickname, Integer age, String password) {
        this.id = id;
        this.nickname = nickname;
        this.age = age;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
