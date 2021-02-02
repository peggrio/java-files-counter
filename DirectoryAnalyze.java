import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class DirectoryAnalyze {
    //数据域
    private long totalBytes;//文件总字节
    private long totalLines;//文件总行数
    private long totalBlankLines;//文件空白行数
    private int totalNumber;//文件总个数
    private long lines;//单个文件总行数
    private long blankLines;//单个文件空行数
    private File directory;
    private File outputFile;
    private Writer writer;

    //构造函数
    public DirectoryAnalyze(){
    }

    public void DirectoryAnalyze() throws IOException {
        System.out.print("请输入一个目录名称：");
        Scanner input=new Scanner(System.in);
        String directoryName=input.nextLine();
        directory =new File(directoryName);
        String fileName=directory.getName();


        if(!directory.isDirectory()){
            System.out.println("错误：["+directoryName+"]不是目录名或不存在！");
        }else{
            //创建文件
            String path="src/result";
            File f=new File(path);
            if(!f.exists()){
                f.mkdirs(); //创建目录
            }
            outputFile=new File(path,fileName+".txt");
            if(!outputFile.exists()){//如果文件不存在，则先创建一个同名文件
                try {
                    outputFile.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            //----------------------------------------------
            try {
                writer=new FileWriter(outputFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
            writer.write("Files Detail:\n");
            //数据域归零
            totalBytes=0;//文件总字节
            totalLines=0;//文件总行数
            totalBlankLines=0;//文件空白行数
            totalNumber=0;//文件总个数
            lines=0;//单个文件总行数
            blankLines=0;//单个文件空行数

            File items[]=directory.listFiles();//把该目录下的文件装进数组
            writer.write("+"+directory.getName()+"\n");//先输出第一行
//细节部分
                Scan_2(items,"\t");

//总体部分

            writer.write("Total:\n");
            writer.write(String.format("%8d",totalNumber)+" Java Files\n");
            writer.write(String.format("%8d",totalLines)+" lines in files\n");
            writer.write(String.format("%8d",totalBlankLines)+" blank lines\n");
            writer.write(String.format("%8d",totalBytes)+" bytes\n");
            writer.close();
        }
    }

    public void Scan_2(File[] directories,String blank) throws IOException {
        ArrayList<File> fileList=new ArrayList<>();
        for(File directory:directories) {
            if (directory.isDirectory()) {
                ArrayList<File> fileList_2=new ArrayList<>();
                String blank_2 = blank + "\t";
                File items_2[] = directory.listFiles();
                writer.write(blank + "+" + directory.getName() + "\n");
                Scan_2(items_2, blank_2);
            } else if (directory.isFile()) {
                fileList.add(directory);
                totalNumber++;
                totalBytes += directory.length();
            }
        }
        for(int i=0;i<fileList.size();i++) {
            writer.write(blank + "-" + String.format("%-20s", fileList.get(i).getName()) + "\t");

            LineReader(fileList.get(i));
            writer.write("Total:" + String.format("%3d",lines) + ",\tBlank:" + String.format("%3d",blankLines) + ",\t" + String.format("%3d",fileList.get(i).length()) + " Bytes\n");
            //部分数据域归零
            blankLines = 0;
            lines = 0;
        }
        }
    public void LineReader(File file) {
        try {
            BufferedReader lineReader=new BufferedReader(new FileReader(file));
            String string;
            while((string=lineReader.readLine())!=null){//如果不是结尾
                if(string.equals("")){//如果是空行
                    blankLines++;
                }
                lines++;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        totalBlankLines+=blankLines;
        totalLines+=lines;
    }
    }

