package com.example.demo.service;

import java.util.List;

import com.example.demo.entity.User;
import com.example.demo.mapper.UserMapper;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserMapper userMapper;

    public UserService(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public User getById(Long id) {
        return userMapper.findById(id);
    }

    public User getByName(String nickname) {
        return userMapper.findByName(nickname);
    }

    public int insert(User user) {
        return userMapper.insert(user);
    }

    public int deleteById(Long id) {
        return userMapper.deleteById(id);
    }

    public int deleteByName(String nickname) {
        return userMapper.deleteByName(nickname);
    }

    public int update(User user) {
        return userMapper.updateInfo(user);
    }

    public List<User> getAll() {
        return userMapper.getAll();
    }

    public User userLogin(String nickname, String password) {
        User user = userMapper.findByName(nickname);
        if (nickname == null) {
            System.out.println("Error, no account named " + nickname);
        }
        if (user.getPassword() != password) {
            System.out.println("Password wrong!");
        }
        return user;

    }
}
