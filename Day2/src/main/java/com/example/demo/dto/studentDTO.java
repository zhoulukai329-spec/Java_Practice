package com.example.demo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class studentDTO {

    @NotBlank(message = "name should not be null") // 姓名不能为空，否则报错
    @Size(min = 2, max = 20, message = "input valid name")
    private String name;

    @NotBlank(message = "major should not be null")
    private String major;

    @NotBlank(message = "student ID should not be null")
    @Size(min = 10, max = 10, message = "input valid student ID")
    private String studentId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }
}
