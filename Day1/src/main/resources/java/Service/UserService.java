package main.resources.java.Service;

import main.resources.java.Entity.*;
import org.apache.ibatis.annotations.userMapper;

/**
 * 本项目是一个用户管理系统，包含以下功能：
 * 查询用户
 * 查询单个用户
 * 新增用户
 * 修改用户信息（密码、昵称等）
 * 删除用户
 */
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
        userMapper.insert(user);
    }

    public int deleteByID(Long id) {
        userMapper.deleteByID(id);
    }

    public int deleteByName(String nickname) {
        userMapper.deleteByName(nickname);
    }

    public int update(User user) {
        userMapper.updateInfo(user);
    }

    public List<User> getAll() {
        return userMapper.getAll();
    }

}
