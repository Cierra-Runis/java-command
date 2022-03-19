package com.testtese.java;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter command just like /help\n> ");      //提示
        String input = scanner.nextLine();                          //第一条命令
        CommandForMatrix command = new CommandForMatrix(input);     //创建一个 CommandForMatrix 的对象

        while (!input.equals("/exit")) {                            //进入循环，只要输入不是 /exit 就不结束
            command.match(input);                                   //利用 匹配指令 方法处理命令
            System.out.print("Enter command just like /help\n> ");  //提示
            input = scanner.nextLine();                             //再输入
        }
    }
}
