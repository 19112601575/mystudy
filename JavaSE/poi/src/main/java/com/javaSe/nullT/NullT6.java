package com.javaSe.nullT;

import com.javaSe.User;

public class NullT6 {
    public static void main(String[] args) {
        User user = new User();

        if (user.getAge() == 0 ){
            System.out.println(user.getAge());
        }
    }
}
