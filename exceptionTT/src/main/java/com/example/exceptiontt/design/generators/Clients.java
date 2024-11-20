package com.example.exceptiontt.design.generators;

public class Clients {
    public static void main(String[] args) {
        Directors directors = new Directors();
        Cars baomao = directors.getBaomao();
        Cars benchi = directors.getBenchi();
        System.out.println(benchi);
        System.out.println(baomao);
    }
}
