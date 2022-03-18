package com.testtese.java;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter command just like /help\n");
        String input = scanner.nextLine();
        Meilei command = new Meilei(input);

        while(!input.equals("/exit")){
            command.match(input);
            System.out.print("Enter command just like /help\n");
            input = scanner.nextLine();
        }
    }
}
