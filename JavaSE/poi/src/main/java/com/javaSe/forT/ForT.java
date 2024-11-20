package com.javaSe.forT;

public class ForT {
    public static void main(String[] args) {
        int x=5;
        for (int i= 0; i < 3 ; i++){
            for (int j=i+1; j< 10 ;j++){

                if (j==5){
                    i=2;
                }
                System.out.println(i+ "  "+j);
            }
        }
    }
}
