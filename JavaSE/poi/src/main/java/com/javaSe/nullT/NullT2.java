package com.javaSe.nullT;

import com.javaSe.User;

public class NullT2 {
    //调用get方法时为空,总结：null不能引用别的方法
    public static void main(String[] args) {
        User user = new User();

        //getnull
        String s = user.getName();
        System.out.println(s);

        //set设为null  ,总结set可以为null
        String b = null;
        user.setPhone(b);
        System.out.println(user.getPhone());

        //null调用tostring,空指针异常
        System.out.println(s.toString());
    }
}
