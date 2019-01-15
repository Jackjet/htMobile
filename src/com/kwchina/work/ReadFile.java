package com.kwchina.work;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.File;

public class ReadFile {
        public ReadFile() {
        }
        /**
         * 读取某个文件夹下的所有文件
         */
        public static boolean readfile(String filepath,int pid) throws FileNotFoundException, IOException {
                try {

                        File file = new File(filepath);
                        if (!file.isDirectory()) {
//                                System.out.println("文件");
//                                System.out.println("path=" + file.getPath());
//                                System.out.println("absolutepath=" + file.getAbsolutePath());
//                                System.out.println("name=" + file.getName());

                        } else if (file.isDirectory()) {
//                                System.out.println("文件夹：" + file.getPath().substring(file.getPath().lastIndexOf("\\")+1));
//                        	System.out.println("文件夹：" + file.getPath());
                                String[] filelist = file.list();
                                for (int i = 0; i < filelist.length; i++) {
                                        File readfile = new File(filepath + "\\" + filelist[i]);
                                        if (!readfile.isDirectory()) {
//                                                System.out.println("path=" + readfile.getPath());
//                                                System.out.println("absolutepath="
//                                                                + readfile.getAbsolutePath());
                                                System.out.println("name=" + readfile.getName());

                                        } else if (readfile.isDirectory()) {
                                                readfile(filepath + "\\" + filelist[i],i+1);
                                        }
                                }

                        }

                } catch (FileNotFoundException e) {
                        System.out.println("readfile()   Exception:" + e.getMessage());
                }
                return true;
        }

        /**
         * 删除某个文件夹下的所有文件夹和文件
         */
        
        
        /*public static boolean deletefile(String delpath)
                        throws FileNotFoundException, IOException {
                try {

                        File file = new File(delpath);
                        if (!file.isDirectory()) {
                                System.out.println("1");
                                file.delete();
                        } else if (file.isDirectory()) {
                                System.out.println("2");
                                String[] filelist = file.list();
                                for (int i = 0; i < filelist.length; i++) {
                                        File delfile = new File(delpath + "\\" + filelist[i]);
                                        if (!delfile.isDirectory()) {
                                                System.out.println("path=" + delfile.getPath());
                                                System.out.println("absolutepath="
                                                                + delfile.getAbsolutePath());
                                                System.out.println("name=" + delfile.getName());
                                                delfile.delete();
                                                System.out.println("删除文件成功");
                                        } else if (delfile.isDirectory()) {
                                                deletefile(delpath + "\\" + filelist[i]);
                                        }
                                }
                                file.delete();

                        }

                } catch (FileNotFoundException e) {
                        System.out.println("deletefile()   Exception:" + e.getMessage());
                }
                return true;
        }*/
        
        public static void main(String[] args) {
                try {
                        readfile("D:/Data/国客中心安全生产标准化体系2016/国客安全标准化要素2016",0);
                        
                        String a = "D:\\Data\\国客中心安全生产标准化体系2016\\国客安全标准化要素2016\\第10章危险源辨识与风险控制\\a\\b";
                        String b = "D:\\Data\\国客中心安全生产标准化体系2016\\国客安全标准化要素2016";
                        int index = a.indexOf(b);
                        System.out.println(a.substring(b.length()).split("/").length);
                        
                        // deletefile("D:/file");
                } catch (FileNotFoundException ex) {
                } catch (IOException ex) {
                }
                System.out.println("ok");
        }

}