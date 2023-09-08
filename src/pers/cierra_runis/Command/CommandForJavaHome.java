package pers.cierra_runis.Command;

import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.util.Random;
import java.util.Scanner;

public class CommandForJavaHome {

    final String[] AllJava;

    //下面是指令库，要求不重复
    public final String[] Command = {
            "/help",                                                                    //帮助
            "/show Java",                                                               //显示所有 Java
            "/set JAVA_HOME",                                                           //设置 JAVA_HOME 系统变量
    };

    //下面为帮助库，与指令库不同，帮助库仅作为提示消息
    public final String[] Help = {
            "/help",                                                                    //帮助
            "/show Java",                                                               //显示所有 Java
            "/set JAVA_HOME <Java_Path>",                                               //设置 JAVA_HOME 系统变量
    };

    //无参构造方法时获取各盘下 Program Files 和 Program Files (x86) 下 Java 文件夹（不一定存在）内的 jre 或 jdk 地址
    CommandForJavaHome() {
        String[] temp = getAllJava();
        int num = 0;
        for (String s : temp) {
            if (s != null) {
                num++;
            }
        }
        AllJava = new String[num];
        System.arraycopy(temp, 0, AllJava, 0, num);
    }

    //启动用方法
    public void start() {
        Scanner scanner = new Scanner(System.in);
        scanner.close();
        Random seed = new Random();

        System.out.print("Enter command just like /help\n> ");      //提示
        String input = scanner.nextLine();                          //第一条命

        while (!input.equals("/exit")) {                            //进入循环，只要输入不是 /exit 就不结束
            match(input);                                           //利用 匹配指令 方法处理命令
            //提示及随机 tip
            System.out.print("Enter command just like " + Help[seed.nextInt(Help.length)] + "\n> ");
            input = scanner.nextLine();                             //再输入
        }
    }

    //匹配指令
    public void match(String input) {

        int i;                                              //指令索引
        for (i = 0; i < Command.length; i++) {              //搜索
            //若输入的指令左边部分与指令库中指令相符
            if (input.regionMatches(0, Command[i], 0, Command[i].length())) {
                //则将左边部分切掉，把指令索引和右边全部传入 toCommand 函数
                input = input.substring(Command[i].length());
                toCommand(i, input);
                return;
            }
        }

        //否则提示无匹配指令
        if (i == Command.length) {
            System.out.print("\33[31;1mNo matched command!\33[0m\n\n");
        }
    }

    //根据传入的索引将索引和输入的指令分配到对应的处理部分
    public void toCommand(int commandnum, String input) {
        switch (commandnum) {
            case 0: {
                ShowHelp();
                break;
            }
            case 1: {
                ShowAllJava();
                break;
            }
            case 2: {
                SetJavaHome(commandnum, input);
                break;
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

    //第二个指令，显示所有 java
    public void ShowAllJava() {
        int lineinpage = 9;                                                 //指定一页显示的最大行数
        if (AllJava.length == 0) {                                          //当 Java 库里无内容时，显示无帮助
            System.out.print("\33[31;1mNo Java!\33[0m\n\n");
        } else {
            //反之
            int totalpage = 1 + AllJava.length / (lineinpage + 1);             //计算总页数
            for (int nowpage = 1; nowpage <= totalpage; nowpage++) {        //从第一页至最后一页

                System.out.print("--------Java Page--------\n");            //显示帮助头
                //若当前页为最后一页
                if (nowpage != totalpage) {
                    //余数的限制下显示帮助
                    for (int line = 1; line <= lineinpage; line++) {
                        int num = lineinpage * (nowpage - 1) + line;
                        System.out.printf("%d. %s\n", num, AllJava[num - 1]);
                    }
                } else {
                    //否则在最大行数的限制下显示帮助
                    if (AllJava.length % lineinpage == 0) {
                        //当最后一页也是 9 行时，按 9 行输出
                        for (int line = 1; line <= lineinpage; line++) {
                            int num = lineinpage * (nowpage - 1) + line;
                            System.out.printf("%d. %s\n", num, AllJava[num - 1]);
                        }
                    } else {
                        //最后一页非 9 行时，按余数输出
                        for (int line = 1; line <= AllJava.length % lineinpage; line++) {
                            int num = lineinpage * (nowpage - 1) + line;
                            System.out.printf("%d. %s\n", num, AllJava[num - 1]);
                        }
                    }
                }
                System.out.printf("--------Page %d--------\n\n", nowpage);  //显示帮助尾
            }
        }
    }

    //第三个指令，设定 JAVA_HOME 系统变量
    public void SetJavaHome(int commandnum, String input) {

        //按照 /set JAVA_HOME <Java_Path> 的描述
        //现在 input 应该是  <Java_Path> （注意左侧有一个空格）
        if (input.length() == 0 || input.charAt(0) != ' ' || input.substring(1).length() == 0 || !isInAllJava(input.substring(1))) {
            //不满足格式直接 illegal 处理，再把对应的帮助怼到用户脸上
            System.out.print("\33[31;1mThis command is illegal!\33[0m\n");
            System.out.print("Check by this: \33[31;1m" + Help[commandnum] + "\33[0m\n\n");
            return;
        }

        //满足则分配下去
        String path = input.substring(1);
        try {
            Runtime mt = Runtime.getRuntime();
            Process pro = mt.exec("setx JAVA_HOME \"" + path + "\" /m");
            System.out.print("\33[32;1mDone.\33[0m\n\n");
            pro.waitFor();
        } catch (IOException | InterruptedException ioe) {
            ioe.printStackTrace();
        }
        System.exit(0);
    }

    private boolean isInAllJava(String input) {
        for (String s : AllJava) {
            if (input.equals(s)) {
                return true;
            }
        }
        return false;
    }

    public String[] getAllJava() {

        String[] alldisk = getAllDisk();
        String[] alljava = new String[100];
        int javaindex = 0;

        System.out.println("\n正在搜索所有可用的 Java ……");

        for (String diskname : alldisk) {
            //进各盘
            File[] filesInDisk = new File(diskname).listFiles();

            //找 x64 和 x86 的俩文件夹
            boolean foundPForPF86 = false;
            for (File fileInDisk : Objects.requireNonNull(filesInDisk)) {

                if (fileInDisk.getName().equals("Program Files")) {
                    foundPForPF86 = true;

                    //找到后，进该文件夹找 Java 文件夹
                    boolean foundJava = false;
                    File[] filesInProgramFiles = new File(diskname + "/Program Files").listFiles();
                    for (File fileInProgramFiles : Objects.requireNonNull(filesInProgramFiles)) {
                        //找名为 Java 的文件夹

                        if (fileInProgramFiles.getName().equals("Java")) {
                            foundJava = true;

                            //找到后，进文件夹
                            File[] filesInJavaHouse = new File(diskname + "/Program Files/Java").listFiles();
                            for (File fileInJavaHouse : Objects.requireNonNull(filesInJavaHouse)) {
                                alljava[javaindex++] = fileInJavaHouse.getPath();
                            }
                        }
                    }
                    if (!foundJava) {
                        System.out.printf("\33[31;1mThere is not Java file in \"%s\"!\33[0m\n", diskname + "Program Files");
                    } else {
                        System.out.printf("\33[32;1mJava file found in \"%s\"!\33[0m\n", diskname + "Program Files");
                    }
                } else if (fileInDisk.getName().equals("Program Files (x86)")) {
                    foundPForPF86 = true;

                    //找到后，进该文件夹找 Java 文件夹
                    boolean foundJava = false;
                    File[] filesInProgramFiles86 = new File(diskname + "/Program Files (x86)").listFiles();
                    for (File fileInProgramFiles86 : Objects.requireNonNull(filesInProgramFiles86)) {
                        //找名为 Java 的文件夹

                        if (fileInProgramFiles86.getName().equals("Java")) {
                            foundJava = true;

                            //找到后，进文件夹
                            File[] filesInJavaHouse = new File(diskname + "/Program Files (x86)/Java").listFiles();
                            for (File fileInJavaHouse : Objects.requireNonNull(filesInJavaHouse)) {
                                alljava[javaindex++] = fileInJavaHouse.getPath();
                            }
                        }
                    }
                    if (!foundJava) {
                        System.out.printf("\33[31;1mThere is not Java file in \"%s\"!\33[0m\n", diskname + "Program Files (x86)");
                    } else {
                        System.out.printf("\33[32;1mJava file found in \"%s\"!\33[0m\n", diskname + "Program Files (x86)");
                    }
                }

            }
            if (!foundPForPF86) {
                System.out.printf("\33[31;1mThere is not Program Files or Program Files (x86) file in \"%s\"!\33[0m\n", diskname);
            }

        }

        System.out.print("搜索完毕\n\n");
        return alljava;

    }

    public String[] getAllDisk() {

        FileSystemView fsv = FileSystemView.getFileSystemView();
        File[] fs = File.listRoots();
        String[] result = new String[fs.length];

        for (int i = 0; i < fs.length; i++) {
            String name = fsv.getSystemDisplayName(fs[i]);
            name = name.substring(name.lastIndexOf("(") + 1, name.lastIndexOf(")"));
            result[i] = name + "/";
        }

        return result;

    }

}
