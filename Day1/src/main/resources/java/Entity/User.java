package main.resources.java.Entity;

/**
 * 用户实体类
 * 用户信息应该包含如下内容：id、昵称、年龄、密码。
 */

public class User {
    private Long id;
    private String nickname;
    private int age;
    private String password;

    public Long getID() {
        return id;
    };

    public String getName() {
        return nickname;
    }

    public int getAge() {
        return age;
    }

    public void resetName(String nickname) {
        this.nickname = nickname;
    }

    public void resetPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }
}
