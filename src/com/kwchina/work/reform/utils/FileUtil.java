package com.kwchina.work.reform.utils;

/**
 * Created by lijj.
 * com.kwchina.work.reform.utils
 * 2018/9/14 23:23
 *
 * @desc
 */
public class FileUtil {
    public static String[] cutOffFile(String attachName){
        String[] split = attachName.split(";");
        String[] attachs=new String[split.length];
        for (int i=0;i<split.length;i++){
            String s = split[i].split("\\|")[0];
            attachs[i]=s;
        }
        return attachs;
    }
}
