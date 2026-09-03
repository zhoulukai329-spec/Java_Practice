package main.resources.java.Mapper;

import main.resources.java.Entity.*;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public class UserMapper {
    User findById(Long id);

    User findByName(String nickname);

    int insert(User user);

    int deleteByID(Long id);

    int deleteByName(String nickname);

    int updateInfo(User user);

    List<User> getAll();

}
