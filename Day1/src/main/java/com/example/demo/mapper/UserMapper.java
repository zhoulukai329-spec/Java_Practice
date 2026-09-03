package com.example.demo.mapper;

import java.util.List;

import com.example.demo.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {

    User findById(Long id);

    User findByName(String nickname);

    int insert(User user);

    int deleteById(Long id);

    int deleteByName(String nickname);

    int updateInfo(User user);

    List<User> getAll();
}
