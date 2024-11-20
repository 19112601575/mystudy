package com.example.exceptiontt.regular;

public class RegularExpression {
    public static void main(String[] args) {
        String i ="2";
        if (i.matches("^[1,3]$")){
            System.out.println("[1,3]");
        }else System.out.println("cuowu");
        if (i.matches("^[1|3]$")){
            System.out.println("[1|3]");
        }if (i.matches("^(1,3)$")){
            System.out.println("(1,3)");
        }if (i.matches("^[1-3]$")){
            System.out.println("[1-3]");
        }if (i.matches("^(1|3)$")){
            System.out.println("(1|3)");
        }
        if (i.matches("^[123]$")){
            System.out.println("[123]");
        }
        if (i.matches("^(1|3|2)$")){
            System.out.println("(1|3|2)");
        }
        String blank = "";
        String empty = null;
        if (blank.isBlank()){
            System.out.println("blank1");
        }
        if (blank.isEmpty()){
            System.out.println("blank2");
        }

//        if (empty.isBlank()){
//            System.out.println("empty1");
//        }
//        if (empty.isEmpty()){
//            System.out.println("empty2");
//        }
    }
}
