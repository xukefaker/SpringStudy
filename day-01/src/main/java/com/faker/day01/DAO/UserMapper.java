package com.faker.day01.DAO;

import com.faker.day01.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

//@Mapper //告诉springboot这是一个mybatis的mapper
@Mapper
@Repository
public interface UserMapper {

    public List<User> getUsers();
    public List<String> getUsernames();
    public User getUserByUserName(String username);
    public User getUserByID(Integer id);
    public void addUser(User user);
    public void quickChangeInfo(@Param("oriUsername") String originalUsername, @Param("username") String username, @Param("password") String password);
    public void changeInfo(@Param("oriUsername") String originalUsername, @Param("username") String username, @Param("password") String password, @Param("email") String email, @Param("birth") String birth, @Param("phone") String phone);
}
