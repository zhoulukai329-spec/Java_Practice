package com.example.demo.entity_2;

public class cardEntity {

    private String studentId; // 学号

    private String name; // 学生姓名

    private String password; // 饭卡密码

    private float money; // 余额

    public cardEntity() {
    }

    public cardEntity(String studentId, String name, String password, float money) {
        this.studentId = studentId;
        this.name = name;
        this.password = password;
        this.money = money;
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

    public float getMoney() {
        return money;
    }

    public void setMoney(float money) {
        this.money = money;
    }
}
