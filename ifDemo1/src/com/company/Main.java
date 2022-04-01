package com.company;

public class Main {

    public static void main(String[] args) {
        System.out.print(" ");
        for(int i = 0; i < 20; i ++){
            System.out.print(",");
        }
        System.out.println();
        System.out.print(" ");
        for(int i = 0; i < 20; i ++){
            System.out.print("|");
        }
        System.out.println();
        for(int i = 0; i < 5; i ++){
            for(int j = 0; j <= 21; j ++){
                System.out.print("@");
            }
            System.out.println();
        }
    }
}