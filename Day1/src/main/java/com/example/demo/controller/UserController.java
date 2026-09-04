package com.example.demo.controller;

import java.util.List;

import com.example.demo.entity.User;
import com.example.demo.service.UserService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/framework_dynconfig-1.1.0")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable Long id) {
        return userService.getById(id);
    }

    @GetMapping("/nickname/{nickname}")
    public User getUserByName(@PathVariable String nickname) {
        return userService.getByName(nickname);
    }

    @PostMapping
    public int insertUser(@RequestBody User user) {
        return userService.insert(user);
    }

    @DeleteMapping("/{id}")
    public int deleteById(@PathVariable Long id) {
        return userService.deleteById(id);
    }

    @DeleteMapping("/nickname/{nickname}")
    public int deleteByName(@PathVariable String nickname) {
        return userService.deleteByName(nickname);
    }

    @PutMapping("/{id}")
    public int update(@PathVariable Long id, @RequestBody User user) {
        user.setId(id);
        return userService.update(user);
    }

    @GetMapping
    public List<User> getAll() {
        return userService.getAll();
    }

    @PostMapping("/login")
    public User userLogin(@RequestBody String nickname, @RequestBody String password) {
        return userService.userLogin(nickname, password);
    }
}
