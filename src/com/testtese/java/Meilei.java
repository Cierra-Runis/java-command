package com.testtese.java;

import java.util.Objects;
import java.util.Scanner;

public class Meilei {

    String input;
    Matrix[] matrices = new Matrix[100];
    int matrixindex = 0;

    public String[] Command = {"/help",
            "/create matrix ",
            "/add matrix ",
            "/show det ",
            "/show minor ",
            "/set matrix ",
            "/show matrix "};

    public String[] Help = {"/help",
            "/create matrix <matrix_name> <matrix_row> <matrix_col>",
            "/add matrix <matrix_A> <matrix_B>",
            "/show det <matrix_name>",
            "/show minor <matrix_name> <matrix_row> <matrix_col>",
            "/set matrix <matrix_name> <matrix_row> <matrix_col>",
            "/show matrix <matrix_name>"};

    public int searchIndexOf(String name) {
        for (int i = 0; i < matrixindex; i++) {
            if (Objects.equals(name, matrices[i].name)) {
                return i;
            }
        }
        return -1;
    }

    public void toCommand(int commandnum, String command) {
        switch (commandnum) {
            case 0: {
                ShowHelp();
                break;
            }
            case 1: {
                CreateMatrix(commandnum, command);
                break;
            }
            case 2: {
                AddMatrix(commandnum, command);
                break;
            }
            case 3: {
                ShowDet(commandnum, command);
                break;
            }
            case 4: {
                ShowMinor(commandnum, command);
            }
            case 5: {
                SetMatrix(commandnum, command);
            }
            case 6: {
                ShowMatrix(commandnum, command);
            }
        }
    }

    Meilei(String input) {
        this.input = input;
    }

    public void match(String input) {
        int i;
        for (i = 0; i < Command.length; i++) {
            if (input.regionMatches(0, Command[i], 0, Command[i].length())) {
                input = input.substring(Command[i].length());
                toCommand(i, input);
                return;
            }
        }
        if (i == Command.length) {
            System.out.print("\33[31;1mNo matched command!\33[0m\n\n");
        }
    }

    public void ShowHelp() {
        int lineinpage = 9;
        if (Help.length == 0) {
            System.out.print("\33[31;1mNo command!\33[0m\n\n");
        } else {
            int totalpage = 1 + Help.length / (lineinpage + 1);
            for (int nowpage = 1; nowpage <= totalpage; nowpage++) {
                System.out.print("--------Help Page--------\n");
                if (nowpage == totalpage) {
                    for (int line = 1; line <= Help.length % lineinpage; line++) {
                        int num = lineinpage * (nowpage - 1) + line;
                        System.out.printf("%d. %s\n", num, Help[num - 1]);
                    }
                    System.out.printf("--------Page %d--------\n\n", nowpage);
                } else {
                    for (int line = 1; line <= lineinpage; line++) {
                        int num = lineinpage * (nowpage - 1) + line;
                        System.out.printf("%d. %s\n", num, Help[num - 1]);
                    }
                    System.out.printf("--------Page %d--------\n\n", nowpage);
                }
            }
        }
    }

    public void CreateMatrix(int commandnum, String command) {

        String[] splitofcommand = command.split(" ");

        if ((splitofcommand.length != 3) || !Showing.isNumeric(splitofcommand[1]) || !Showing.isNumeric(splitofcommand[2])) {
            System.out.print("\33[31;1mThis command is illegal!\33[0m\n");
            System.out.print("Check by this: \33[31;1m" + Help[commandnum] + "\33[0m\n\n");
            return;
        }
        String name = splitofcommand[0];
        int row = Integer.parseInt(splitofcommand[1]);
        int col = Integer.parseInt(splitofcommand[2]);

        if (searchIndexOf(name) != -1) {
            System.out.printf("\33[31;1mMatrix %s already exists, please change the name of matrix you want to create!\33[0m\n", name);
            return;
        }

        if (row < 1 || col < 1) {
            System.out.print("\33[31;1mThis vauel is illegal!\33[0m\n");
            System.out.print("Check by this: \33[31;1m" + Help[commandnum] + "\33[0m\n\n");
            return;
        }
        matrices[matrixindex] = new Matrix(name, row, col);
        matrices[matrixindex].setMatrix();
        matrixindex++;
    }

    public void AddMatrix(int commandnum, String command) {

        String[] splitofcommand = command.split(" ");

        if (splitofcommand.length != 2) {
            System.out.print("\33[31;1mThis command is illegal!\33[0m\n");
            System.out.print("Check by this: \33[31;1m" + Help[commandnum] + "\33[0m\n\n");
            return;
        }
        if (searchIndexOf(splitofcommand[0]) == -1 || searchIndexOf(splitofcommand[1]) == -1) {
            System.out.print("\33[31;1mThis name of matrix doesn't exists, please check the name of matrix you want to add!\33[0m\n");
            return;
        }
        String matrix_A = splitofcommand[0];
        int indexofA = searchIndexOf(matrix_A);
        String matrix_B = splitofcommand[1];
        int indexofB = searchIndexOf(matrix_B);

        matrices[indexofA].addMatrix(matrices[indexofB]);
    }

    public void ShowDet(int commandnum, String command) {

    }

    public void ShowMinor(int commandnum, String command) {

    }

    public void SetMatrix(int commandnum, String command) {

        String[] splitofcommand = command.split(" ");

        if ((splitofcommand.length != 3) || !Showing.isNumeric(splitofcommand[1]) || !Showing.isNumeric(splitofcommand[2])) {
            System.out.print("\33[31;1mThis command is illegal!\33[0m\n");
            System.out.print("Check by this: \33[31;1m" + Help[commandnum] + "\33[0m\n\n");
            return;
        }

        String name = splitofcommand[0];
        int row = Integer.parseInt(splitofcommand[1]);
        int col = Integer.parseInt(splitofcommand[2]);
        int index = searchIndexOf(name);

        if (index == -1) {
            System.out.printf("\33[31;1mMatrix %s doesn't exists, please check the name of matrix you want to set!\33[0m\n\n", name);
            return;
        }

        if (row < 1 || row > matrices[index].matrix.length || col < 1 || col > matrices[index].matrix[0].length) {
            System.out.print("\33[31;1mThis vauel is illegal!\33[0m\n");
            System.out.print("Check by this: \33[31;1m" + Help[commandnum] + "\33[0m\n\n");
            return;
        }

        Scanner setvaule = new Scanner(System.in);
        System.out.printf("Enter %s[%d][%d] = ", name, row, col);
        matrices[index - 1].matrix[row - 1][col - 1] = setvaule.nextDouble();
        System.out.printf("%s[%d][%d] had set as %s\n\n", name, row, col, matrices[index - 1].matrix[row - 1][col - 1]);
    }

    public void ShowMatrix(int commandnum, String command) {
        String[] splitofcommand = command.split(" ");
        if (splitofcommand.length != 1) {
            System.out.print("\33[31;1mThis command is illegal!\33[0m\n");
            System.out.print("Check by this: \33[31;1m" + Help[commandnum] + "\33[0m\n\n");
            return;
        }

        String name = splitofcommand[0];
        int index = searchIndexOf(name);
        if (index == -1) {
            System.out.printf("\33[31;1mMatrix %s doesn't exists, please check the name of matrix you want to show!\33[0m\n\n", name);
            return;
        }
        matrices[index].showMatrix();
    }

}
