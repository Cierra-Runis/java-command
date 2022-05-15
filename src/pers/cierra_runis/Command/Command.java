package pers.cierra_runis.Command;

import java.util.Objects;
import java.util.Scanner;

public class Command {
    public void start() {
        while (true) {

            Scanner input = new Scanner(System.in);
            System.out.print("Enter command just like /module (Matrix|Manage)\n> ");
            String str = input.nextLine();

            if (Objects.equals(str, "/module Matrix")) {
                System.out.print("\33[32;1mWelcome to Matrix module.\33[0m\n");
                new CommandForMatrix("").start();
            } else if (Objects.equals(str, "/module Manage")) {
                System.out.print("\33[32;1mWelcome to Manage module.\33[0m\n");
                new CommandForManage().start();
            } else if (Objects.equals(str, "/exit")) {
                System.out.print("\33[32;1mSee you next time.\33[0m\n");
                return;
            } else {
                System.out.print("\33[31;1mThis command is illegal!\33[0m\n");
                System.out.print("Check by this: \33[31;1m" + "/module (Matrix|Student)" + "\33[0m\n\n");
            }

        }
    }
}
