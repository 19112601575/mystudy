package com.example.exceptiontt.jpa;

import com.example.exceptiontt.jpa.entityJpa.UserJpa;

public class JpaDoReturn {
    public UserJpa getUserJpa(){
        UserJpa userJpa = new UserJpa();
        return userJpa;
    }
}
