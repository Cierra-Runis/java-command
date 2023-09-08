package pers.cierra_runis.Command;

import java.util.Objects;
import java.util.Random;
import java.util.Scanner;

public class CommandForMatrix {

    Matrix[] matrices = new Matrix[100];
    int matrixIndex = 0;

    //下面为指令库，要求不重复
    public final String[] Command = {"/help",                                       //帮助
            "/create matrix",                                                       //创建
            "/add matrix",                                                          //和运算
            "/show det",                                                            //行列式
            "/show minor",                                                          //“余矩阵”
            "/set matrix",                                                          //设定元素
            "/show matrix",                                                         //显示
            "/turn matrix",                                                         //转置
            "/adjoint matrix",                                                      //伴随矩阵
            "/multiply matrix",                                                     //矩阵乘法
            "/inverse matrix",                                                      //逆矩阵
    };

    //下面为帮助库，与指令库不同，帮助库仅作为提示消息
    public final String[] Help = {"/help",                                          //帮助
            "/create matrix <matrix_name> <matrix_row> <matrix_col>",               //创建
            "/add matrix <matrix_A> <matrix_B> [to <matrix_C>]",                    //和运算
            "/show det <matrix_name>",                                              //行列式
            "/show minor <matrix_name> <matrix_row> <matrix_col>",                  //“余矩阵”
            "/set matrix <matrix_name> <matrix_row> <matrix_col>",                  //设定元素
            "/show matrix <matrix_name>",                                           //显示
            "/turn matrix <matrix_name>",                                           //转置
            "/adjoint matrix <matrix_name>",                                        //伴随矩阵
            "/multiply matrix (<num>|<matrix_A>) <matrix_B> [to] [<matrix_C>]",     //矩阵乘法
            "/inverse matrix <matrix_name>"                                         //逆矩阵
    };

    //启动用方法
    public void start() {
        Scanner scanner = new Scanner(System.in);
        Random seed = new Random();

        System.out.print("Enter command just like /help\n> ");      //提示
        String input = scanner.nextLine();                          //第一条命令

        while (!input.equals("/exit")) {                            //进入循环，只要输入不是 /exit 就不结束
            match(input);                                           //利用 匹配指令 方法处理命令
            //提示及随机 tip
            System.out.print("Enter command just like " + Help[seed.nextInt(Help.length)] + "\n> ");
            input = scanner.nextLine();                             //再输入
        }
    }

    //输入矩阵名称查找矩阵库中是否存在该矩阵，存在返回索引，反之为-1
    public int searchIndexOf(String name) {
        for (int i = 0; i < matrixIndex; i++) {
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
                String[] splitCommand = input.split(" ");           //以空格切分输入的命令
                toCommand(i, splitCommand);
                return;
            }
        }

        //否则提示无匹配指令
        if (i == Command.length) {
            System.out.print("\33[31;1mNo matched command!\33[0m\n\n");
        }
    }

    //根据传入的索引将索引和输入的指令分配到对应的处理部分
    public void toCommand(int commandNum, String[] splitCommand) {
        switch (commandNum) {
            case 0: {
                ShowHelp();
                break;
            }
            case 1: {
                CreateMatrix(commandNum, splitCommand);
                break;
            }
            case 2: {
                AddMatrix(commandNum, splitCommand);
                break;
            }
            case 3: {
                ShowDet(commandNum, splitCommand);
                break;
            }
            case 4: {
                ShowMinor(commandNum, splitCommand);
                break;
            }
            case 5: {
                SetMatrix(commandNum, splitCommand);
                break;
            }
            case 6: {
                ShowMatrix(commandNum, splitCommand);
                break;
            }
            case 7: {
                TurnMatrix(commandNum, splitCommand);
                break;
            }
            case 8: {
                AdjointMatrix(commandNum, splitCommand);
                break;
            }
            case 9: {
                MultiplyMatrix(commandNum, splitCommand);
                break;
            }
            case 10: {
                InverseMatrix(commandNum, splitCommand);
                break;
            }
        }
    }

    //第一个指令，显示帮助库
    public void ShowHelp() {

        int linePerPage = 9;                                                 //指定一页显示的最大行数
        if (Help.length == 0) {                                             //当帮助库里不写东西时，显示无帮助
            System.out.print("\33[31;1mNo command!\33[0m\n\n");
        } else {
            //反之
            int totalPage = 1 + Help.length / (linePerPage + 1);             //计算总页数
            for (int currentPage = 1; currentPage <= totalPage; currentPage++) {        //从第一页至最后一页

                System.out.print("--------Help Page--------\n");            //显示帮助头
                //若当前页为最后一页
                if (currentPage != totalPage) {
                    //余数的限制下显示帮助
                    for (int line = 1; line <= linePerPage; line++) {
                        int num = linePerPage * (currentPage - 1) + line;
                        System.out.printf("%d. %s\n", num, Help[num - 1]);
                    }
                } else {
                    //否则在最大行数的限制下显示帮助
                    if (Help.length % linePerPage == 0) {
                        //当最后一页也是 9 行时，按 9 行输出
                        for (int line = 1; line <= linePerPage; line++) {
                            int num = linePerPage * (currentPage - 1) + line;
                            System.out.printf("%d. %s\n", num, Help[num - 1]);
                        }
                    } else {
                        //最后一页非 9 行时，按余数输出
                        for (int line = 1; line <= Help.length % linePerPage; line++) {
                            int num = linePerPage * (currentPage - 1) + line;
                            System.out.printf("%d. %s\n", num, Help[num - 1]);
                        }
                    }
                }
                System.out.printf("--------Page %d--------\n\n", currentPage);  //显示帮助尾
            }
        }
    }

    //第二个指令，创建矩阵
    public void CreateMatrix(int commandNum, String[] splitCommand) {

        //按照 /create matrix <matrix_name> <matrix_row> <matrix_col> 的描述
        //现在输入的部分应当满足 <matrix_name> <matrix_row> <matrix_col> 的格式
        //先判断切分后的个数是否为 4 ，再判断后面两个是不是数字
        if ((splitCommand.length != 4) || !Showing.isNumeric(splitCommand[2]) || !Showing.isNumeric(splitCommand[3])) {
            //不满足格式直接 illegal 处理，再把对应的帮助怼到用户脸上
            System.out.print("\33[31;1mThis command is illegal!\33[0m\n");
            System.out.print("Check by this: \33[31;1m" + Help[commandNum] + "\33[0m\n\n");
            return;
        }

        //这里防止名称为数字
        if (Showing.isNumeric(splitCommand[1])) {
            System.out.print("\33[31;1mName of matrix should not be a number!\33[0m\n");
            System.out.print("Check by this: \33[31;1m" + Help[commandNum] + "\33[0m\n\n");
            return;
        }

        //满足格式则分配下去
        String name = splitCommand[1];
        int row = Integer.parseInt(splitCommand[2]);
        int col = Integer.parseInt(splitCommand[3]);
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
            System.out.print("\33[31;1mThis value is illegal!\33[0m\n");
            System.out.print("Check by this: \33[31;1m" + Help[commandNum] + "\33[0m\n\n");
            return;
        }

        //至此完全合理，创建矩阵并设定初始值
        matrices[matrixIndex] = new Matrix(name, row, col);
        matrices[matrixIndex].setMatrix();
        //创建好了，那么矩阵库的矩阵数就加一
        matrixIndex++;
    }

    //第三个指令，矩阵相加
    public void AddMatrix(int commandNum, String[] splitCommand) {

        //按照 /add matrix <matrix_A> <matrix_B> [to] [<matrix_C>] 的描述
        //因为该命令含有可选部分，先判断切分后的个数为 3 还是 5
        if (splitCommand.length == 3) {

            //切分个数为 3 时，输入的部分应当满足 <matrix_A> <matrix_B> 的格式
            //判断相加的两者是否存在
            if (searchIndexOf(splitCommand[1]) == -1 || searchIndexOf(splitCommand[2]) == -1) {
                //不存在则提示
                System.out.print("\33[31;1mThis name of matrix doesn't exist, please check the name of matrix you want to add!\33[0m\n\n");
                return;
            }

            //至此两者都存在，但还需判断是否同型
            String matrix_A = splitCommand[1];
            int indexA = searchIndexOf(matrix_A);
            String matrix_B = splitCommand[2];
            int indexB = searchIndexOf(matrix_B);

            Matrix result = matrices[indexA].add(matrices[indexB]);
            //两者同型则 result 不为 null
            if (result != null) {
                result.showMatrix();
            } else {
                //不同型则把对应的帮助怼到用户脸上
                System.out.print("Check by this: \33[31;1m" + Help[commandNum] + "\33[0m\n\n");
            }
        } else if (splitCommand.length == 5) {

            //切分个数为 5 时，输入的部分应当满足 <matrix_A> <matrix_B> to <matrix_C> 的格式
            //判断第四个切片是否为 to
            if (!Objects.equals(splitCommand[3], "to")) {
                //不满足格式直接 illegal 处理，再把对应的帮助怼到用户脸上
                System.out.print("\33[31;1mThis command is illegal!\33[0m\n");
                System.out.print("Check by this: \33[31;1m" + Help[commandNum] + "\33[0m\n\n");
                return;
            }
            //判断相加的两者是否存在
            if (searchIndexOf(splitCommand[1]) == -1 || searchIndexOf(splitCommand[2]) == -1) {
                //不存在则提示
                System.out.print("\33[31;1mThis name of matrix doesn't exist, please check the name of matrix you want to add!\33[0m\n\n");
                return;
            }

            //至此前两者都存在，最后的矩阵不一定存在
            //但先把他们的名称和索引留下
            String matrix_A = splitCommand[1];
            int indexA = searchIndexOf(matrix_A);
            String matrix_B = splitCommand[2];
            int indexB = searchIndexOf(matrix_B);
            String matrix_C = splitCommand[4];
            int indexC = searchIndexOf(matrix_C);

            //判断结果接收者是否存在
            if (indexC != -1) {
                //存在时判断是否同型
                if (matrices[indexC].matrix.length != matrices[indexA].matrix.length || matrices[indexC].matrix[0].length != matrices[indexA].matrix[0].length || matrices[indexC].matrix.length != matrices[indexB].matrix.length || matrices[indexC].matrix[0].length != matrices[indexB].matrix[0].length) {
                    //不同型则提示
                    System.out.printf("\33[31;1mMatrix %s isn't same kind of matrix with %s or %s!\33[0m\n\n", matrix_C, matrix_A, matrix_B);
                    return;
                }

                //此处保证了三者同型
                matrices[indexC].matrix = matrices[indexA].add(matrices[indexB]).matrix;
                matrices[indexC].showMatrix();
            } else {
                //不存在则要创造一个与前两者同型的矩阵
                Matrix result = matrices[indexA].add(matrices[indexB]);
                //前两者同型则 result 不为 null
                if (result != null) {
                    //把结果给至矩阵库
                    matrices[matrixIndex] = new Matrix(matrix_C, matrices[indexA].matrix.length, matrices[indexA].matrix[0].length);
                    matrices[matrixIndex].matrix = result.matrix;
                    matrices[matrixIndex].showMatrix();
                    //创建好了，那么矩阵库的矩阵数就加一
                    matrixIndex++;
                } else {
                    //否则把对应的帮助怼到用户脸上
                    System.out.print("Check by this: \33[31;1m" + Help[commandNum] + "\33[0m\n\n");
                }
            }
        } else {
            //切分个数不为 3 或 5 时直接 illegal 处理，再把对应的帮助怼到用户脸上
            System.out.print("\33[31;1mThis command is illegal!\33[0m\n");
            System.out.print("Check by this: \33[31;1m" + Help[commandNum] + "\33[0m\n\n");
        }
    }

    //第四个命令，显示矩阵的行列式
    public void ShowDet(int commandNum, String[] splitCommand) {

        //按照 /show matrix <matrix_name> 的描述
        //切分后的切片有且只有两个
        if (splitCommand.length != 2) {
            //不满足格式直接 illegal 处理，再把对应的帮助怼到用户脸上
            System.out.print("\33[31;1mThis command is illegal!\33[0m\n");
            System.out.print("Check by this: \33[31;1m" + Help[commandNum] + "\33[0m\n\n");
            return;
        }

        //满足格式则分配下去
        String name = splitCommand[1];
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
    public void ShowMinor(int commandNum, String[] splitCommand) {

        //按照 /show minor <matrix_name> <matrix_row> <matrix_col> 的描述
        //现在输入的部分应当满足 <matrix_name> <matrix_row> <matrix_col> 的格式
        //先判断切分后的个数是否为 4 ，再判断后面两个是不是数字
        if ((splitCommand.length != 4) || !Showing.isNumeric(splitCommand[2]) || !Showing.isNumeric(splitCommand[3])) {
            System.out.print("\33[31;1mThis command is illegal!\33[0m\n");
            System.out.print("Check by this: \33[31;1m" + Help[commandNum] + "\33[0m\n\n");
            return;
        }

        //满足格式则分配下去
        String name = splitCommand[1];
        int row = Integer.parseInt(splitCommand[2]);
        int col = Integer.parseInt(splitCommand[3]);
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
            System.out.print("\33[31;1mThis value is illegal!\33[0m\n");
            System.out.print("Check by this: \33[31;1m" + Help[commandNum] + "\33[0m\n\n");
            return;
        }

        //至此显示“余矩阵”即可
        if (matrices[index].minorOfMatrix(row - 1, col - 1) != null) {
            matrices[index].minorOfMatrix(row - 1, col - 1).showMatrix();
        } else {
            //不合理直接 illegal 处理，再把对应的帮助怼到用户脸上
            System.out.printf("\33[31;1mMinor of %s[%d][%d] doesn't exist!\33[0m\n", name, row, col);
            System.out.print("Check by this: \33[31;1m" + Help[commandNum] + "\33[0m\n\n");
        }
    }

    //第六个命令，设定矩阵特定元素的值
    public void SetMatrix(int commandNum, String[] splitCommand) {

        //按照 /set matrix <matrix_name> <matrix_row> <matrix_col> 的描述
        //现在输入的部分应当满足 <matrix_name> <matrix_row> <matrix_col> 的格式
        //先判断切分后的个数是否为 4 ，再判断后面两个是不是数字
        if ((splitCommand.length != 4) || !Showing.isNumeric(splitCommand[2]) || !Showing.isNumeric(splitCommand[3])) {
            //不满足格式直接 illegal 处理，再把对应的帮助怼到用户脸上
            System.out.print("\33[31;1mThis command is illegal!\33[0m\n");
            System.out.print("Check by this: \33[31;1m" + Help[commandNum] + "\33[0m\n\n");
            return;
        }

        //满足格式则分配下去
        String name = splitCommand[1];
        int row = Integer.parseInt(splitCommand[2]);
        int col = Integer.parseInt(splitCommand[3]);
        int index = searchIndexOf(name);

        //判断该名为 name 的矩阵是否存在
        if (index == -1) {
            //不存在则提示
            System.out.printf("\33[31;1mMatrix %s doesn't exist, please check the name of matrix you want to set!\33[0m\n\n", name);
            return;
        }

        //还要检查 row 和 col 是否合理
        if (row < 1 || row > matrices[index].matrix.length || col < 1 || col > matrices[index].matrix[0].length) {
            System.out.print("\33[31;1mThis value is illegal!\33[0m\n");
            System.out.print("Check by this: \33[31;1m" + Help[commandNum] + "\33[0m\n\n");
            return;
        }

        //至此完全合理，开始设定步骤
        Scanner scanner = new Scanner(System.in);
        System.out.printf("Enter %s[%d][%d] = ", name, row, col);
        matrices[index].matrix[row - 1][col - 1] = scanner.nextDouble();
        System.out.printf("%s[%d][%d] had set as %s\n\n", name, row, col, matrices[index].matrix[row - 1][col - 1]);
    }

    //第七个命令，显示矩阵
    public void ShowMatrix(int commandNum, String[] splitCommand) {

        //按照 /show matrix <matrix_name> 的描述
        //切分后的切片有且只有两个
        if (splitCommand.length != 2) {
            //不满足格式直接 illegal 处理，再把对应的帮助怼到用户脸上
            System.out.print("\33[31;1mThis command is illegal!\33[0m\n");
            System.out.print("Check by this: \33[31;1m" + Help[commandNum] + "\33[0m\n\n");
            return;
        }

        //满足格式则分配下去
        String name = splitCommand[1];
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
    public void TurnMatrix(int commandNum, String[] splitCommand) {

        //按照 /turn matrix <matrix_name> 的描述
        //切分后的切片有且只有两个
        if (splitCommand.length != 2) {
            //不满足格式直接 illegal 处理，再把对应的帮助怼到用户脸上
            System.out.print("\33[31;1mThis command is illegal!\33[0m\n");
            System.out.print("Check by this: \33[31;1m" + Help[commandNum] + "\33[0m\n\n");
            return;
        }

        //满足格式则分配下去
        String name = splitCommand[1];
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
    public void AdjointMatrix(int commandNum, String[] splitCommand) {

        //按照 /adjoint matrix <matrix_name> 的描述
        //切分后的切片有且只有两个
        if (splitCommand.length != 2) {
            //不满足格式直接 illegal 处理，再把对应的帮助怼到用户脸上
            System.out.print("\33[31;1mThis command is illegal!\33[0m\n");
            System.out.print("Check by this: \33[31;1m" + Help[commandNum] + "\33[0m\n\n");
            return;
        }

        //满足格式则分配下去
        String name = splitCommand[1];
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
    public void MultiplyMatrix(int commandNum, String[] splitCommand) {

        //按照 /multiply matrix (<num>|<matrix_A>) <matrix_B> [to] [<matrix_C>] 的描述
        //因为该命令含有可选部分，先判断切分后的个数为 3 还是 5
        if (splitCommand.length == 3) {

            //切分个数为 3 时，输入的部分应当满足 (<num>|<matrix_A>) <matrix_B> 的格式
            //先判断前者是否为数字
            if (!Showing.isNumeric(splitCommand[1])) {
                //不是数字则要判断前者是否存在
                if (searchIndexOf(splitCommand[1]) == -1) {
                    //既不是数字也不是存在的矩阵名称就提示
                    System.out.print("\33[31;1mThis name of matrix doesn't exist, please check the name of matrix you want to multiply!\33[0m\n\n");
                    return;
                }
            }

            //至此前者一定为数字或存在的矩阵，还需判断后者
            if (Showing.isNumeric(splitCommand[2])) {
                //若后者为数字，直接 illegal 处理，再把对应的帮助怼到用户脸上
                System.out.print("\33[31;1mThis command is illegal!\33[0m\n");
                System.out.print("Check by this: \33[31;1m" + Help[commandNum] + "\33[0m\n\n");
                return;
            }
            if (searchIndexOf(splitCommand[2]) == -1) {
                //不存在则提示
                System.out.print("\33[31;1mThis name of matrix doesn't exist, please check the name of matrix you want to multiply!\33[0m\n\n");
                return;
            }

            //至此后者存在且不为数字
            String matrix_B = splitCommand[2];
            int indexB = searchIndexOf(matrix_B);

            //准备 result 矩阵
            Matrix result;

            //按前者是数字还是矩阵名分类赋值 result 矩阵
            if (Showing.isNumeric(splitCommand[1])) {
                double num = Double.parseDouble(splitCommand[1]);
                result = matrices[indexB].multiply(num);
            } else {
                String matrix_A = splitCommand[1];
                int indexA = searchIndexOf(matrix_A);
                result = matrices[indexA].multiply(matrices[indexB]);
            }

            //两者可乘则 result 不为 null
            if (result != null) {
                result.showMatrix();
            } else {
                //不可乘则把对应的帮助怼到用户脸上
                System.out.print("Check by this: \33[31;1m" + Help[commandNum] + "\33[0m\n\n");
            }
        } else if (splitCommand.length == 5) {

            //切分个数为 5 时，输入的部分应当满足(<num>|<matrix_A>) <matrix_B> to <matrix_C> 的格式
            //判断第四个切片是否为 to
            if (!Objects.equals(splitCommand[3], "to")) {
                //不满足格式直接 illegal 处理，再把对应的帮助怼到用户脸上
                System.out.print("\33[31;1mThis command is illegal!\33[0m\n");
                System.out.print("Check by this: \33[31;1m" + Help[commandNum] + "\33[0m\n\n");
                return;
            }
            //先判断前者是否为数字
            if (!Showing.isNumeric(splitCommand[1])) {
                //不是数字则要判断前者是否存在
                if (searchIndexOf(splitCommand[1]) == -1) {
                    //既不是数字也不是存在的矩阵名称就提示
                    System.out.print("\33[31;1mThis name of matrix doesn't exist, please check the name of matrix you want to multiply!\33[0m\n\n");
                    return;
                }
            }

            //至此前者一定为数字或存在的矩阵，还需判断后者
            if (Showing.isNumeric(splitCommand[2])) {
                //若后者为数字，直接 illegal 处理，再把对应的帮助怼到用户脸上
                System.out.print("\33[31;1mThis command is illegal!\33[0m\n");
                System.out.print("Check by this: \33[31;1m" + Help[commandNum] + "\33[0m\n\n");
                return;
            }
            if (searchIndexOf(splitCommand[2]) == -1) {
                //不存在则提示
                System.out.print("\33[31;1mThis name of matrix doesn't exist, please check the name of matrix you want to multiply!\33[0m\n\n");
                return;
            }

            //至此后者存在且不为数字
            String matrix_B = splitCommand[2];
            int indexB = searchIndexOf(matrix_B);

            //准备 result 矩阵
            Matrix result;

            //按前者是数字还是矩阵名分类赋值 result 矩阵
            if (Showing.isNumeric(splitCommand[1])) {
                double num = Double.parseDouble(splitCommand[1]);
                result = matrices[indexB].multiply(num);
            } else {
                String matrix_A = splitCommand[1];
                int indexA = searchIndexOf(matrix_A);
                result = matrices[indexA].multiply(matrices[indexB]);
            }

            //两者可乘则 result 不为 null
            if (result != null) {
                //可乘则要来看结果给的那个矩阵存不存在，能不能给
                String matrix_C = splitCommand[4];
                int indexC = searchIndexOf(matrix_C);
                if (indexC != -1) {
                    //存在则要看能不能给
                    if (matrices[indexC].matrix.length != result.matrix.length || matrices[indexC].matrix[0].length != result.matrix[0].length) {
                        //不能给则提示
                        System.out.printf("\33[31;1mMatrix %s isn't same kind of matrix with output!\33[0m\n\n", matrix_C);
                        return;
                    }
                    //存在且能给
                    matrices[indexC].matrix = result.matrix;
                    matrices[indexC].showMatrix();
                } else {
                    //不存在则要创造一个与结果同型的矩阵
                    matrices[matrixIndex] = new Matrix(matrix_C, result.matrix.length, result.matrix[0].length);
                    matrices[matrixIndex].matrix = result.matrix;
                    matrices[matrixIndex].showMatrix();
                    //创建好了，那么矩阵库的矩阵数就加一
                    matrixIndex++;
                }
            } else {
                //不可乘则把对应的帮助怼到用户脸上
                System.out.print("Check by this: \33[31;1m" + Help[commandNum] + "\33[0m\n\n");
            }
        }
    }

    //第十一个指令，逆矩阵
    public void InverseMatrix(int commandNum, String[] splitCommand) {

        //按照 /inverse matrix <matrix_name> 的描述
        //切分后的切片有且只有两个
        if (splitCommand.length != 2) {
            //不满足格式直接 illegal 处理，再把对应的帮助怼到用户脸上
            System.out.print("\33[31;1mThis command is illegal!\33[0m\n");
            System.out.print("Check by this: \33[31;1m" + Help[commandNum] + "\33[0m\n\n");
            return;
        }

        //满足格式则分配下去
        String name = splitCommand[1];
        int index = searchIndexOf(name);

        //判断该名为 name 的矩阵是否存在
        if (index == -1) {
            //不存在则提示
            System.out.printf("\33[31;1mMatrix %s doesn't exist, please check the name of matrix you want to inverse!\33[0m\n\n", name);
            return;
        }

        //存在则还需处理 null 情况
        if (matrices[index].inverseMatrix() == null) {
            //是方阵时提示
            if (matrices[index].matrix.length == matrices[index].matrix[0].length) {
                //提示行列式为零
                System.out.printf("\33[31;1mDet of %s is 0, so it has not inverse matrix!\33[0m\n\n", name);
            }
            //非方阵提示已在上一个 if 语句中输出
            return;
        }
        //最后显示逆矩阵
        matrices[index].inverseMatrix().showMatrix();
    }
}
