package com.example.exceptiontt.jpa.mapperJpa;

import com.example.exceptiontt.jpa.entityJpa.UserJpa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import java.util.List;
import java.util.Optional;

//实体类型和主键类型

public interface UserMapperJpa extends JpaRepository<UserJpa,Integer>{
    /*
    1、Jpa基本增删查改方法
     */
    // 保存用户
    UserJpa save(UserJpa user);

    // 根据主键获取用户
    Optional<UserJpa> findById(Long id);

    // 获取所有用户
    List<UserJpa> findAll();

    // 根据主键删除用户
    void deleteById(Long id);

    /*
    2、自定义查询方法，根据方法名查询，findBy--、deleteBy--，后面字段要严格等于实体类字段（注意大小写）
     */
    UserJpa findByUserName(String username);

    // 根据年龄查询用户列表
    List<UserJpa> findByAge(Integer age);

    // 根据用户名和密码查询用户
    UserJpa findByUserNameAndPassword(String userName, String password);

    // 根据主键和用户名删除用户
    void deleteByIdAndUserName(Long id, String userName);

    /*
    3、查询参数设置
     */
//
//    @Query("SELECT u FROM UserJpa u WHERE u.userName = :userName AND u.age = :age")
//    UserJpa findByUserNameAndAge(@Param("username") String userName, @Param("age")int age);

    /*
    4、使用@Query注解的value导入sql语句查询
     */
    @Query(value = "SELECT * FROM users WHERE username = ?1", nativeQuery = true)
    UserJpa findByIdAndAge(int id,int age);
}
