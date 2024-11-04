package com.example.exceptiontt.design.proxy.staticP;

public class StaticProxyT {
    public static void main(String[] args) {
        Landlord landlord = new Landlord();
        Agency agency = new Agency(landlord);
        agency.rent();
    }
}
