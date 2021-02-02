import java.io.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args){
        //生成操作界面
        System.out.println("-----------MENU------------");
        System.out.println("1.分析目录中的源程序文件");
        System.out.println("2.查看分析结果");
        System.out.println("0.退出程序");
        System.out.println("---------------------------");
        System.out.print("请选择：");

        Scanner input=new Scanner(System.in);
        int i=input.nextInt();
        Main demo=new Main();
        demo.Scan(i);
        while(i!=0){
            System.out.print("请您继续选择要进行的操作：");
            i=input.nextInt();
            Scan(i);
        }
        System.out.println("程序退出，感谢您的使用！");
    }

    public static void Scan(int a){
        DirectoryAnalyze demo2=new DirectoryAnalyze();
        switch (a){
            case 1:
                try {
                    demo2.DirectoryAnalyze();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case 2:
                File result=new File("src/result");
                if(!result.exists()){//如果文件不存在，则先创建一个同名文件
                    System.out.println("还没有分析结果！");
                }
                else {
                    File items[] = result.listFiles();
                        System.out.println("可以查看的结果文件有：");
                        System.out.println("--------------------");
                        //显示文件列表篇
                        for (int i = 0; i < items.length; i++) {
                            System.out.println((i + 1) + "." + items[i].getName());
                        }
                        System.out.println("--------------------");
                        System.out.print("请选择您要打开的文件(0表示放弃)：");
                        Scanner input = new Scanner(System.in);
                        int i = input.nextInt();
                        if (i < 0 || i > items.length) {
                            System.out.println("您输入的数字不合法，现在返回主菜单");
                        } else if (i != 0) {//显示菜单里的文件
                            InputStreamReader isr = null;
                            try {
                                isr = new InputStreamReader(new FileInputStream("src/result/" + items[i - 1].getName()), "utf-8");
                                BufferedReader br = new BufferedReader(isr);
                                String lineTxt = null;
                                while ((lineTxt = br.readLine()) != null) {
                                    System.out.println(lineTxt);
                                }
                                br.close();
                            } catch (UnsupportedEncodingException e) {
                                e.printStackTrace();
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                break;
        }
    }
}
