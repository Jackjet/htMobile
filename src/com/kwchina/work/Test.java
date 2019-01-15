package com.kwchina.work;

import java.io.File;

public class Test {
	public static void main(String[] args)throws Exception {
//		String a = "2017-11-12 12:39:00";
//		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		System.out.println(sf.format(sf.parse(a)));
//		
//		String beginDate = "2017-11-02";
//		String endDate = "2017-11-10";
//		java.util.Date bDate = DateHelper.getDate(beginDate);
//		java.util.Date eDate = DateHelper.getDate(endDate);
////		System.out.println(DateHelper.daysBetween(bDate, eDate));
//		//for(java.util.Date tmpDate = bDate;(tmpDate.before(eDate) || tmpDate.equals(eDate));DateHelper.addDay(tmpDate, 1)){
//		//	System.out.println(tmpDate);
//		//}
//		for(int i=0;i<DateHelper.daysBetween(bDate, eDate)+1;i++){
////			System.out.println(DateHelper.getString(DateHelper.addDay(bDate, i)));
//			Calendar cal = Calendar.getInstance();
//	        cal.setTime(DateHelper.addDay(bDate, i));
//	        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
//	        System.out.println(w);
//		}
		
			  String path="D:/Data/国客中心安全生产标准化体系2016/国客安全标准化要素2016";
			  File file=new File(path);
			  File[] tempList = file.listFiles();
			  System.out.println("该目录下对象个数："+tempList.length);
			  for (int i = 0; i < tempList.length; i++) {
			   if (tempList[i].isFile()) {
			    System.out.println("文     件："+tempList[i]);
			   }
			   if (tempList[i].isDirectory()) {
			    System.out.println("文件夹："+tempList[i]);
			   }
			  }
		
			  
			  String a="D:/Data/ejy.apk";
			  File filea = new File(a);
			  getFileSize1(filea);
	}
	
	/**
     * 获取文件长度
     * @param file
     */
    public static void getFileSize1(File file)throws Exception {
        if (file.exists() && file.isFile()) {
            String fileName = file.getName();
            System.out.println("文件"+fileName+"的大小是："+file.length() / 1024/1024+" MB");
        }
    }
}
