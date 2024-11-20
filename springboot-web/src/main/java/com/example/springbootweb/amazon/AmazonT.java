package com.example.springbootweb.amazon;

import java.util.Scanner;

public class AmazonT {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        switch (Integer.valueOf(scanner.next())){
            case 1 :
            case 2 :
                System.out.println("2");
            default:
                System.out.println("其他的");
        }
    }
}
