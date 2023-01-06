<div align="center">
  <img id="java-command" width="96" alt="java-command" src="repository_icon/icon.svg">
  <p>『 java-command - 指令大法！』</p>
</div>

[📚 简介](#-简介)

[📦 使用方式](#-使用方式)

[⏳ 进度](#-进度)

[📌 注意事项](#-注意事项)

[🧑‍💻 贡献者](#-贡献者)

[🔦 声明](#-声明)

---

# 📚 简介

一个由 Java 编写的命令行程序，目前含有【矩阵计算】【学生管理】【快速配置 JAVA_HOME 环境变量】三个模块

# 📦 使用方式

- 安装并配置好 Java 环境，下载并安装[微软终端](https://github.com/microsoft/terminal)
- 将下载的 jar 文件放在一个文件夹内如 `C:\Users\28642\Desktop` ，所在路径最好不要包含中文
- 若在文件夹右键有 `在终端中打开` 字样，则点击该选项

  <center><img alt="" src="/image/1.png"></center>

- 若不没有上述字样，要求打开微软终端后使用 `cd` 命令如 `cd C:\Users\28642\Desktop`
- 最终保证终端显示 `PS C:\Users\28642\Desktop>` （这里是我的例子，实际应为 `jar` 文件所在文件夹）

  <center><img alt="" src="/image/2.png"></center>

- 输入 `java -jar Java_Command.jar` 的命令，如成功执行程序，应显示如下画面

  <center><img alt="" src="/image/3.png"></center>

  具体指令语法和 `Minecraft` 命令保持一致，列表如下

|    语法     |        含义        |
|:---------:|:----------------:|
|    纯文本    |    纯文本，直接键入。     |
|   <参数名>   | 需要使用一合适的值来替换该函数。 |
|  [可选输入项]  |    该输入项是可选的。     |
| (输入项｜输入项) | 必须地在显示的值中选择一个填写。 |
| [输入项｜输入项] | 可选地在显示的值中选择一个填写。 |

# ⏳ 进度

已完成，按需修改

# 📌 注意事项

- 【快速配置 JAVA_HOME 环境变量】模块需要管理员权限，请如下图所示勾选管理员选项后重新运行程序，且完成配置后程序自动退出（因为程序占用了 JAVA_HOME 下的 javaw.exe 什么的，反正不是很懂），在终端界面（而不是本程序界面）下输入 java -version 的命令确认是否更改成功

  <center><img alt="" src="/image/4.png"></center>

# 🧑‍💻 贡献者

<a href="https://github.com/Cierra-Runis/java-command/graphs/contributors">
  <img src="https://contrib.rocks/image?repo=Cierra-Runis/java-command" />
</a>

# 🔦 声明

- 若出现问题，将 Java_Command.jar 同一路径下的 Java_Command_log 文件夹内（还未实装该功能）对应 txt 文件发送至 2864283875@qq.com
