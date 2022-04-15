package com.testtese.java;

import java.util.Objects;
import java.util.Scanner;

public class CommandForMatrix {

    String input;
    Matrix[] matrices = new Matrix[100];
    int matrixindex = 0;

    //下面为指令库，要求不重复
    public final String[] Command = {"/help",                                       //帮助
            "/create matrix",                                                       //创建
            "/add matrix",                                                          //和运算
            "/show det",                                                            //行列式
            "/show minor",                                                          //“余矩阵”
            "/set matrix",                                                          //设定元素
            "/show matrix",                                                         //显示
            "/turn matrix",                                                         //转置
            "/adjoin matrix",                                                       //伴随矩阵
            "/multiply matrix",                                                     //矩阵乘法
            "/inverse matrix",                                                      //逆矩阵
    };

    //下面为帮助库，与指令库不同，帮助库仅作为提示消息
    public final String[] Help = {"/help",                                          //帮助
            "/create matrix <matrix_name> <matrix_row> <matrix_col>",               //创建
            "/add matrix <matrix_A> <matrix_B> [to] [<matrix_C>]",                  //和运算
            "/show det <matrix_name>",                                              //行列式
            "/show minor <matrix_name> <matrix_row> <matrix_col>",                  //“余矩阵”
            "/set matrix <matrix_name> <matrix_row> <matrix_col>",                  //设定元素
            "/show matrix <matrix_name>",                                           //显示
            "/turn matrix <matrix_name>",                                           //转置
            "/adjoin matrix <matrix_name>",                                         //伴随矩阵
            "/multiply matrix (<num>|<matrix_A>) <matrix_B> [to] [<matrix_C>]",     //矩阵乘法
            "/inverse matrix <matrix_name>"                                         //逆矩阵
    };

    //使用含参数的构造方法时提供输入的指令
    CommandForMatrix(String input) {
        this.input = input;
    }

    //输入矩阵名称查找矩阵库中是否存在该矩阵，存在返回索引，反之为-1
    public int searchIndexOf(String name) {
        for (int i = 0; i < matrixindex; i++) {
            if (Objects.equals(name, matrices[i].name)) {
                return i;
            }
        }
        return -1;
    }

    //匹配指令
    public void match(String input) {

        int i;                                              //指令索引
        for (i = 0; i < Command.length; i++) {              //搜索
            //若输入的指令左边部分与指令库中指令相符
            if (input.regionMatches(0, Command[i], 0, Command[i].length())) {
                //则将左边部分切掉，把指令索引和右边切片传入 toCommand 函数
                input = input.substring(Command[i].length());
                String[] splitofcommand = input.split(" ");           //以空格切分输入的命令
                toCommand(i, splitofcommand);
                return;
            }
        }

        //否则提示无匹配指令
        if (i == Command.length) {
            System.out.print("\33[31;1mNo matched command!\33[0m\n\n");
        }
    }

    //根据传入的索引将索引和输入的指令分配到对应的处理部分
    public void toCommand(int commandnum, String[] command) {
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
                break;
            }
            case 5: {
                SetMatrix(commandnum, command);
                break;
            }
            case 6: {
                ShowMatrix(commandnum, command);
                break;
            }
            case 7: {
                TurnMatrix(commandnum, command);
                break;
            }
            case 8: {
                AdjointMatrix(commandnum, command);
                break;
            }
            case 9: {
                MultiplyMatrix(commandnum, command);
            }
            case 10: {
                InverseMatrix(commandnum, command);
            }
        }
    }

    //第一个指令，显示帮助库
    public void ShowHelp() {

        int lineinpage = 9;                                                 //指定一页显示的最大行数
        if (Help.length == 0) {                                             //当帮助库里不写东西时，显示无帮助
            System.out.print("\33[31;1mNo command!\33[0m\n\n");
        } else {
            //反之
            int totalpage = 1 + Help.length / (lineinpage + 1);             //计算总页数
            for (int nowpage = 1; nowpage <= totalpage; nowpage++) {        //从第一页至最后一页

                System.out.print("--------Help Page--------\n");            //显示帮助头
                //若当前页为最后一页
                if (nowpage != totalpage) {
                    //余数的限制下显示帮助
                    for (int line = 1; line <= lineinpage; line++) {
                        int num = lineinpage * (nowpage - 1) + line;
                        System.out.printf("%d. %s\n", num, Help[num - 1]);
                    }
                } else {
                    //否则在最大行数的限制下显示帮助
                    if (Help.length % lineinpage == 0) {
                        //当最后一页也是 9 行时，按 9 行输出
                        for (int line = 1; line <= lineinpage; line++) {
                            int num = lineinpage * (nowpage - 1) + line;
                            System.out.printf("%d. %s\n", num, Help[num - 1]);
                        }
                    } else {
                        //最后一页非 9 行时，按余数输出
                        for (int line = 1; line <= Help.length % lineinpage; line++) {
                            int num = lineinpage * (nowpage - 1) + line;
                            System.out.printf("%d. %s\n", num, Help[num - 1]);
                        }
                    }
                }
                System.out.printf("--------Page %d--------\n\n", nowpage);  //显示帮助尾
            }
        }
    }

    //第二个指令，创建矩阵
    public void CreateMatrix(int commandnum, String[] splitofcommand) {

        //按照 /create matrix <matrix_name> <matrix_row> <matrix_col> 的描述
        //现在输入的部分应当满足 <matrix_name> <matrix_row> <matrix_col> 的格式
        //先判断切分后的个数是否为 4 ，再判断后面两个是不是数字
        if ((splitofcommand.length != 4) || !Showing.isNumeric(splitofcommand[2]) || !Showing.isNumeric(splitofcommand[3])) {
            //不满足格式直接 illegal 处理，再把对应的帮助怼到用户脸上
            System.out.print("\33[31;1mThis command is illegal!\33[0m\n");
            System.out.print("Check by this: \33[31;1m" + Help[commandnum] + "\33[0m\n\n");
            return;
        }

        //这里防止名称为数字
        if (Showing.isNumeric(splitofcommand[1])) {
            System.out.print("\33[31;1mName of matrix should not be a number!\33[0m\n");
            System.out.print("Check by this: \33[31;1m" + Help[commandnum] + "\33[0m\n\n");
            return;
        }

        //满足格式则分配下去
        String name = splitofcommand[1];
        int row = Integer.parseInt(splitofcommand[2]);
        int col = Integer.parseInt(splitofcommand[3]);
        int index = searchIndexOf(name);

        //因为是创建矩阵，所以先判断该名为 name 的矩阵是否存在
        if (index != -1) {
            //存在则提示
            System.out.printf("\33[31;1mMatrix %s already exists, please change the name of matrix you want to create!\33[0m\n\n", name);
            return;
        }

        //还要检查 row 和 col 是否合理
        if (row < 1 || col < 1) {
            //不合理直接 illegal 处理，再把对应的帮助怼到用户脸上
            System.out.print("\33[31;1mThis vauel is illegal!\33[0m\n");
            System.out.print("Check by this: \33[31;1m" + Help[commandnum] + "\33[0m\n\n");
            return;
        }

        //至此完全合理，创建矩阵并设定初始值
        matrices[matrixindex] = new Matrix(name, row, col);
        matrices[matrixindex].setMatrix();
        //创建好了，那么矩阵库的矩阵数就加一
        matrixindex++;
    }

    //第三个指令，矩阵相加
    public void AddMatrix(int commandnum, String[] splitofcommand) {

        //按照 /add matrix <matrix_A> <matrix_B> [to] [<matrix_C>] 的描述
        //因为该命令含有可选部分，先判断切分后的个数为 3 还是 5
        if (splitofcommand.length == 3) {

            //切分个数为 3 时，输入的部分应当满足 <matrix_A> <matrix_B> 的格式
            //判断相加的两者是否存在
            if (searchIndexOf(splitofcommand[1]) == -1 || searchIndexOf(splitofcommand[2]) == -1) {
                //不存在则提示
                System.out.print("\33[31;1mThis name of matrix doesn't exist, please check the name of matrix you want to add!\33[0m\n\n");
                return;
            }

            //至此两者都存在，但还需判断是否同型
            String matrix_A = splitofcommand[1];
            int indexofA = searchIndexOf(matrix_A);
            String matrix_B = splitofcommand[2];
            int indexofB = searchIndexOf(matrix_B);

            Matrix result = matrices[indexofA].addMatrix(matrices[indexofB]);
            //两者同型则 result 不为 null
            if (result != null) {
                result.showMatrix();
            } else {
                //不同型则把对应的帮助怼到用户脸上
                System.out.print("Check by this: \33[31;1m" + Help[commandnum] + "\33[0m\n\n");
            }
        } else if (splitofcommand.length == 5) {

            //切分个数为 5 时，输入的部分应当满足 <matrix_A> <matrix_B> to <matrix_C> 的格式
            //判断第四个切片是否为 to
            if (!Objects.equals(splitofcommand[3], "to")) {
                //不满足格式直接 illegal 处理，再把对应的帮助怼到用户脸上
                System.out.print("\33[31;1mThis command is illegal!\33[0m\n");
                System.out.print("Check by this: \33[31;1m" + Help[commandnum] + "\33[0m\n\n");
                return;
            }
            //判断相加的两者是否存在
            if (searchIndexOf(splitofcommand[1]) == -1 || searchIndexOf(splitofcommand[2]) == -1) {
                //不存在则提示
                System.out.print("\33[31;1mThis name of matrix doesn't exist, please check the name of matrix you want to add!\33[0m\n\n");
                return;
            }

            //至此前两者都存在，最后的矩阵不一定存在
            //但先把他们的名称和索引留下
            String matrix_A = splitofcommand[1];
            int indexofA = searchIndexOf(matrix_A);
            String matrix_B = splitofcommand[2];
            int indexofB = searchIndexOf(matrix_B);
            String matrix_C = splitofcommand[4];
            int indexofC = searchIndexOf(matrix_C);

            //判断结果接收者是否存在
            if (indexofC != -1) {
                //存在时判断是否同型
                if (matrices[indexofC].matrix.length != matrices[indexofA].matrix.length || matrices[indexofC].matrix[0].length != matrices[indexofA].matrix[0].length || matrices[indexofC].matrix.length != matrices[indexofB].matrix.length || matrices[indexofC].matrix[0].length != matrices[indexofB].matrix[0].length) {
                    //不同型则提示
                    System.out.printf("\33[31;1mMatrix %s isn't same kind of matrix with %s or %s!\33[0m\n\n", matrix_C, matrix_A, matrix_B);
                    return;
                }

                //此处保证了三者同型
                matrices[indexofC].matrix = matrices[indexofA].addMatrix(matrices[indexofB]).matrix;
                matrices[indexofC].showMatrix();
            } else {
                //不存在则要创造一个与前两者同型的矩阵
                Matrix result = matrices[indexofA].addMatrix(matrices[indexofB]);
                //前两者同型则 result 不为 null
                if (result != null) {
                    //把结果给至矩阵库
                    matrices[matrixindex] = new Matrix(matrix_C, matrices[indexofA].matrix.length, matrices[indexofA].matrix[0].length);
                    matrices[matrixindex].matrix = result.matrix;
                    matrices[matrixindex].showMatrix();
                    //创建好了，那么矩阵库的矩阵数就加一
                    matrixindex++;
                } else {
                    //否则把对应的帮助怼到用户脸上
                    System.out.print("Check by this: \33[31;1m" + Help[commandnum] + "\33[0m\n\n");
                }
            }
        } else {
            //切分个数不为 3 或 5 时直接 illegal 处理，再把对应的帮助怼到用户脸上
            System.out.print("\33[31;1mThis command is illegal!\33[0m\n");
            System.out.print("Check by this: \33[31;1m" + Help[commandnum] + "\33[0m\n\n");
        }
    }

    //第四个命令，显示矩阵的行列式
    public void ShowDet(int commandnum, String[] splitofcommand) {

        //按照 /show matrix <matrix_name> 的描述
        //切分后的切片有且只有两个
        if (splitofcommand.length != 2) {
            //不满足格式直接 illegal 处理，再把对应的帮助怼到用户脸上
            System.out.print("\33[31;1mThis command is illegal!\33[0m\n");
            System.out.print("Check by this: \33[31;1m" + Help[commandnum] + "\33[0m\n\n");
            return;
        }

        //满足格式则分配下去
        String name = splitofcommand[1];
        int index = searchIndexOf(name);

        //判断该名为 name 的矩阵是否存在
        if (index == -1) {
            //不存在则提示
            System.out.printf("\33[31;1mMatrix %s doesn't exist, please check the name of matrix you want to show!\33[0m\n\n", name);
            return;
        }
        //存在则显示
        matrices[index].showDet();
    }

    //第五个命令，显示矩阵的“余矩阵”
    public void ShowMinor(int commandnum, String[] splitofcommand) {

        //按照 /show minor <matrix_name> <matrix_row> <matrix_col> 的描述
        //现在输入的部分应当满足 <matrix_name> <matrix_row> <matrix_col> 的格式
        //先判断切分后的个数是否为 4 ，再判断后面两个是不是数字
        if ((splitofcommand.length != 4) || !Showing.isNumeric(splitofcommand[2]) || !Showing.isNumeric(splitofcommand[3])) {
            System.out.print("\33[31;1mThis command is illegal!\33[0m\n");
            System.out.print("Check by this: \33[31;1m" + Help[commandnum] + "\33[0m\n\n");
            return;
        }

        //满足格式则分配下去
        String name = splitofcommand[1];
        int row = Integer.parseInt(splitofcommand[2]);
        int col = Integer.parseInt(splitofcommand[3]);
        int index = searchIndexOf(name);

        //判断该名为 name 的矩阵是否存在
        if (index == -1) {
            //不存在则提示
            System.out.printf("\33[31;1mMatrix %s doesn't exist, please check the name of matrix you want to show it's minor!\33[0m\n\n", name);
            return;
        }

        //还要检查 row 和 col 是否合理
        if (row < 1 || row > matrices[index].matrix.length || col < 1 || col > matrices[index].matrix[0].length) {
            //不合理直接 illegal 处理，再把对应的帮助怼到用户脸上
            System.out.print("\33[31;1mThis vauel is illegal!\33[0m\n");
            System.out.print("Check by this: \33[31;1m" + Help[commandnum] + "\33[0m\n\n");
            return;
        }

        //至此显示“余矩阵”即可
        if (matrices[index].minorOfMatrix(row - 1, col - 1) != null) {
            matrices[index].minorOfMatrix(row - 1, col - 1).showMatrix();
        } else {
            //不合理直接 illegal 处理，再把对应的帮助怼到用户脸上
            System.out.printf("\33[31;1mMinor of %s[%d][%d] doesn't exist!\33[0m\n", name, row, col);
            System.out.print("Check by this: \33[31;1m" + Help[commandnum] + "\33[0m\n\n");
        }
    }

    //第六个命令，设定矩阵特定元素的值
    public void SetMatrix(int commandnum, String[] splitofcommand) {

        //按照 /set matrix <matrix_name> <matrix_row> <matrix_col> 的描述
        //现在输入的部分应当满足 <matrix_name> <matrix_row> <matrix_col> 的格式
        //先判断切分后的个数是否为 4 ，再判断后面两个是不是数字
        if ((splitofcommand.length != 4) || !Showing.isNumeric(splitofcommand[2]) || !Showing.isNumeric(splitofcommand[3])) {
            //不满足格式直接 illegal 处理，再把对应的帮助怼到用户脸上
            System.out.print("\33[31;1mThis command is illegal!\33[0m\n");
            System.out.print("Check by this: \33[31;1m" + Help[commandnum] + "\33[0m\n\n");
            return;
        }

        //满足格式则分配下去
        String name = splitofcommand[1];
        int row = Integer.parseInt(splitofcommand[2]);
        int col = Integer.parseInt(splitofcommand[3]);
        int index = searchIndexOf(name);

        //判断该名为 name 的矩阵是否存在
        if (index == -1) {
            //不存在则提示
            System.out.printf("\33[31;1mMatrix %s doesn't exist, please check the name of matrix you want to set!\33[0m\n\n", name);
            return;
        }

        //还要检查 row 和 col 是否合理
        if (row < 1 || row > matrices[index].matrix.length || col < 1 || col > matrices[index].matrix[0].length) {
            System.out.print("\33[31;1mThis vauel is illegal!\33[0m\n");
            System.out.print("Check by this: \33[31;1m" + Help[commandnum] + "\33[0m\n\n");
            return;
        }

        //至此完全合理，开始设定步骤
        Scanner setvaule = new Scanner(System.in);
        System.out.printf("Enter %s[%d][%d] = ", name, row, col);
        matrices[index].matrix[row - 1][col - 1] = setvaule.nextDouble();
        System.out.printf("%s[%d][%d] had set as %s\n\n", name, row, col, matrices[index].matrix[row - 1][col - 1]);
    }

    //第七个命令，显示矩阵
    public void ShowMatrix(int commandnum, String[] splitofcommand) {

        //按照 /show matrix <matrix_name> 的描述
        //切分后的切片有且只有两个
        if (splitofcommand.length != 2) {
            //不满足格式直接 illegal 处理，再把对应的帮助怼到用户脸上
            System.out.print("\33[31;1mThis command is illegal!\33[0m\n");
            System.out.print("Check by this: \33[31;1m" + Help[commandnum] + "\33[0m\n\n");
            return;
        }

        //满足格式则分配下去
        String name = splitofcommand[1];
        int index = searchIndexOf(name);

        //判断该名为 name 的矩阵是否存在
        if (index == -1) {
            //不存在则提示
            System.out.printf("\33[31;1mMatrix %s doesn't exist, please check the name of matrix you want to show!\33[0m\n\n", name);
            return;
        }
        //存在则显示
        matrices[index].showMatrix();
    }

    //第八个指令，转置矩阵
    public void TurnMatrix(int commandnum, String[] splitofcommand) {

        //按照 /turn matrix <matrix_name> 的描述
        //切分后的切片有且只有两个
        if (splitofcommand.length != 2) {
            //不满足格式直接 illegal 处理，再把对应的帮助怼到用户脸上
            System.out.print("\33[31;1mThis command is illegal!\33[0m\n");
            System.out.print("Check by this: \33[31;1m" + Help[commandnum] + "\33[0m\n\n");
            return;
        }

        //满足格式则分配下去
        String name = splitofcommand[1];
        int index = searchIndexOf(name);

        //判断该名为 name 的矩阵是否存在
        if (index == -1) {
            //不存在则提示
            System.out.printf("\33[31;1mMatrix %s doesn't exist, please check the name of matrix you want to turn!\33[0m\n\n", name);
            return;
        }
        //存在则显示
        matrices[index].turnOfMatrix().showMatrix();
    }

    //第九个指令，伴随矩阵
    public void AdjointMatrix(int commandnum, String[] splitofcommand) {

        //按照 /adjoint matrix <matrix_name> 的描述
        //切分后的切片有且只有两个
        if (splitofcommand.length != 2) {
            //不满足格式直接 illegal 处理，再把对应的帮助怼到用户脸上
            System.out.print("\33[31;1mThis command is illegal!\33[0m\n");
            System.out.print("Check by this: \33[31;1m" + Help[commandnum] + "\33[0m\n\n");
            return;
        }

        //满足格式则分配下去
        String name = splitofcommand[1];
        int index = searchIndexOf(name);

        //判断该名为 name 的矩阵是否存在
        if (index == -1) {
            //不存在则提示
            System.out.printf("\33[31;1mMatrix %s doesn't exist, please check the name of matrix you want to adjoint!\33[0m\n\n", name);
            return;
        }

        //存在则显示
        if (matrices[index].adjointOfMatrix() != null) {
            matrices[index].adjointOfMatrix().showMatrix();
        } else {
            //非方阵则报错
            System.out.print("\33[31;1mError, it's not a n*n matrix.\33[0m\n\n");
        }

    }

    //第十个指令，矩阵乘法
    public void MultiplyMatrix(int commandnum, String[] splitofcommand) {

        //按照 /multiply matrix (<num>|<matrix_A>) <matrix_B> [to] [<matrix_C>] 的描述
        //因为该命令含有可选部分，先判断切分后的个数为 3 还是 5
        if (splitofcommand.length == 3) {

            //切分个数为 3 时，输入的部分应当满足 (<num>|<matrix_A>) <matrix_B> 的格式
            //先判断前者是否为数字
            if (!Showing.isNumeric(splitofcommand[1])) {
                //不是数字则要判断前者是否存在
                if (searchIndexOf(splitofcommand[1]) == -1) {
                    //既不是数字也不是存在的矩阵名称就提示
                    System.out.print("\33[31;1mThis name of matrix doesn't exist, please check the name of matrix you want to multiply!\33[0m\n\n");
                    return;
                }
            }

            //至此前者一定为数字或存在的矩阵，还需判断后者
            if (searchIndexOf(splitofcommand[2]) == -1) {
                //不存在则提示
                System.out.print("\33[31;1mThis name of matrix doesn't exist, please check the name of matrix you want to multiply!\33[0m\n\n");
                return;
            }

            //至此后者存在
            String matrix_B = splitofcommand[2];
            int indexofB = searchIndexOf(matrix_B);

            //准备 result 矩阵
            Matrix result;

            //按前者是数字还是矩阵名分类赋值 result 矩阵
            if (Showing.isNumeric(splitofcommand[1])) {
                double num = Double.parseDouble(splitofcommand[1]);
                result = matrices[indexofB].multiplyMatrix(num);
            } else {
                String matrix_A = splitofcommand[1];
                int indexofA = searchIndexOf(matrix_A);
                result = matrices[indexofA].multiplyMatrix(matrices[indexofB]);
            }

            //两者可乘则 result 不为 null
            if (result != null) {
                result.showMatrix();
            } else {
                //不可乘则把对应的帮助怼到用户脸上
                System.out.print("Check by this: \33[31;1m" + Help[commandnum] + "\33[0m\n\n");
            }
        } else if (splitofcommand.length == 5) {

            //切分个数为 5 时，输入的部分应当满足(<num>|<matrix_A>) <matrix_B> to <matrix_C> 的格式
            //判断第四个切片是否为 to
            if (!Objects.equals(splitofcommand[3], "to")) {
                //不满足格式直接 illegal 处理，再把对应的帮助怼到用户脸上
                System.out.print("\33[31;1mThis command is illegal!\33[0m\n");
                System.out.print("Check by this: \33[31;1m" + Help[commandnum] + "\33[0m\n\n");
                return;
            }
            //先判断前者是否为数字
            if (!Showing.isNumeric(splitofcommand[1])) {
                //不是数字则要判断前者是否存在
                if (searchIndexOf(splitofcommand[1]) == -1) {
                    //既不是数字也不是存在的矩阵名称就提示
                    System.out.print("\33[31;1mThis name of matrix doesn't exist, please check the name of matrix you want to multiply!\33[0m\n\n");
                    return;
                }
            }

            //至此前者一定为数字或存在的矩阵，还需判断后者
            if (searchIndexOf(splitofcommand[2]) == -1) {
                //不存在则提示
                System.out.print("\33[31;1mThis name of matrix doesn't exist, please check the name of matrix you want to multiply!\33[0m\n\n");
                return;
            }

            //至此后者存在
            String matrix_B = splitofcommand[2];
            int indexofB = searchIndexOf(matrix_B);

            //准备 result 矩阵
            Matrix result;

            //按前者是数字还是矩阵名分类赋值 result 矩阵
            if (Showing.isNumeric(splitofcommand[1])) {
                double num = Double.parseDouble(splitofcommand[1]);
                result = matrices[indexofB].multiplyMatrix(num);
            } else {
                String matrix_A = splitofcommand[1];
                int indexofA = searchIndexOf(matrix_A);
                result = matrices[indexofA].multiplyMatrix(matrices[indexofB]);
            }

            //两者可乘则 result 不为 null
            if (result != null) {
                //可乘则要来看结果给的那个矩阵存不存在，能不能给
                String matrix_C = splitofcommand[4];
                int indexofC = searchIndexOf(matrix_C);
                if (indexofC != -1) {
                    //存在则要看能不能给
                    if (matrices[indexofC].matrix.length != result.matrix.length || matrices[indexofC].matrix[0].length != result.matrix[0].length) {
                        //不能给则提示
                        System.out.printf("\33[31;1mMatrix %s isn't same kind of matrix with output!\33[0m\n\n", matrix_C);
                        return;
                    }
                    //存在且能给
                    matrices[indexofC].matrix = result.matrix;
                    matrices[indexofC].showMatrix();
                } else {
                    //不存在则要创造一个与结果同型的矩阵
                    matrices[matrixindex] = new Matrix(matrix_C, result.matrix.length, result.matrix[0].length);
                    matrices[matrixindex].matrix = result.matrix;
                    matrices[matrixindex].showMatrix();
                    //创建好了，那么矩阵库的矩阵数就加一
                    matrixindex++;
                }
            } else {
                //不可乘则把对应的帮助怼到用户脸上
                System.out.print("Check by this: \33[31;1m" + Help[commandnum] + "\33[0m\n\n");
            }
        }
    }

    //第十一个指令，逆矩阵
    public void InverseMatrix(int commandnum, String[] splitofcommand) {

        //按照 /inverse matrix <matrix_name> 的描述
        //切分后的切片有且只有两个
        if (splitofcommand.length != 2) {
            //不满足格式直接 illegal 处理，再把对应的帮助怼到用户脸上
            System.out.print("\33[31;1mThis command is illegal!\33[0m\n");
            System.out.print("Check by this: \33[31;1m" + Help[commandnum] + "\33[0m\n\n");
            return;
        }

        //满足格式则分配下去
        String name = splitofcommand[1];
        int index = searchIndexOf(name);

        //判断该名为 name 的矩阵是否存在
        if (index == -1) {
            //不存在则提示
            System.out.printf("\33[31;1mMatrix %s doesn't exist, please check the name of matrix you want to show!\33[0m\n\n", name);
            return;
        }

        //存在则还需处理 null 情况
        if (matrices[index].inverseMatrix() == null) {
            if (matrices[index].matrix.length != matrices[index].matrix[0].length) {
                //非方阵提示已在上一个 if 语句中输出
                return;
            } else {
                //提示行列式为零
                System.out.printf("\33[31;1mDet of %s is 0, so it has not inverse matrix!\33[0m\n\n", name);
                return;
            }
        }
        //最后显示
        matrices[index].inverseMatrix().showMatrix();
    }
}
