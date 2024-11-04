package com.example.exceptiontt.design.proxy.staticP;

public class Agency implements Rent{
    private Landlord landlord;

    public Agency(Landlord landlord1) {
        this.landlord = landlord1;
    }

    @Override
    public void rent() {
        search();
        landlord.rent();
        fetch();
    }

    private void search(){
        System.out.println("找租客");
    }

    private void fetch(){
        System.out.println("收中介费");
    }
}
