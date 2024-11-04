package com.example.exceptiontt.mybatisplus.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.exceptiontt.mybatisplus.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {}
