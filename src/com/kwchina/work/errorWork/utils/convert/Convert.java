package com.kwchina.work.errorWork.utils.convert;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Convert {
	  public static Date stringToDate(String source, String pattern) {
	      SimpleDateFormat  simpleDateFormat = new SimpleDateFormat(pattern);
	        Date date = null;
	        try {
	            date = simpleDateFormat.parse(source);
	        } catch (Exception e) {
	        	date=new Date();
	        }
	        return date;
	    }

}
