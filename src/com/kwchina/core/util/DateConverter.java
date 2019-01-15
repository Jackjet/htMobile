package com.kwchina.core.util;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.apache.commons.beanutils.Converter;

/**
 * 
 * 日期转换对象，使用该转换器，在BaseForm当中做一下注册，
 * 系统自动地帮助字符的日期表示转换为java.util.Date对象.
 *
 */
public class DateConverter implements Converter {
	/**
	 * 日期格式化对象.
	 */
	private static SimpleDateFormat df = new SimpleDateFormat();

	/**
	 * 模式集合.
	 */
	private static Set<String> patterns = new HashSet<String>();
	// 注册一下日期的转换格式
	static {
		DateConverter.patterns.add("yyyy-MM-dd");
		DateConverter.patterns.add("yyyy-MM-dd HH:mm");
		DateConverter.patterns.add("yyyy-MM-dd HH:mm:ss");
		DateConverter.patterns.add("yyyy/MM/dd HH:mm:ss");
	}

	/**
	 * 日期转换器.
	 * 
	 * @param type
	 *            Class
	 * @param value
	 *            Object return Date Object.
	 */
	public Object convert(Class type, Object value) {
		if (value == null) {
			return null;
		} else if (value instanceof String) {
			Object dateObj = null;
			Iterator it = patterns.iterator();
			while (it.hasNext()) {
				try {
					String pattern = (String) it.next();
					df.applyPattern(pattern);
					dateObj = df.parse((String) value);
					java.util.Date date = (java.util.Date)dateObj;
					dateObj = new java.sql.Date(date.getTime());
					break;
				} catch (ParseException ex) {
					// do iterator continue
				}
			}
			return dateObj;
		} else {
			return null;
		}
	}
	
	/**
     * 得到当前的年和月
     *
     * @return int[2] index.0 is year index.1 is month
     */

    public static int getDefaultCheckYear(String dataYear) {
        int returnYear = 0;

        if ((dataYear != null) && !(dataYear.equals(""))) {
            returnYear = Integer.parseInt(dataYear);
        } else {
            java.util.Date dateNow = new java.util.Date(System.currentTimeMillis());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String nowStr = sdf.format(dateNow);
            String yearStr = nowStr.substring(0, 4);
            String monthStr = nowStr.substring(5, 7);
            String dayStr = nowStr.substring(8, 10);

            int theDay = Integer.parseInt(dayStr);
            int theMonth = Integer.parseInt(monthStr);
            int theYear = Integer.parseInt(yearStr);

            //25日开始考核
            if (theDay < 25) {
                if (theMonth == 1) {
                    returnYear = theYear - 1;
                }
            } else {
                returnYear = theYear;
            }
        }

        return returnYear;
    }


    public static int getDefaultCheckMonth(String dataMonth) {
        int returnMonth = 0;

        if ((dataMonth != null) && !(dataMonth.equals(""))) {
            returnMonth = Integer.parseInt(dataMonth);
        } else {
            java.util.Date dateNow = new java.util.Date(System.currentTimeMillis());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String nowStr = sdf.format(dateNow);
            String monthStr = nowStr.substring(5, 7);
            String dayStr = nowStr.substring(8, 10);

            int theDay = Integer.parseInt(dayStr);
            int theMonth = Integer.parseInt(monthStr);
            //25日开始考核
            if (theDay < 25) {
                if (theMonth == 1) {
                    returnMonth = 12;
                } else {
                    returnMonth = theMonth - 1;
                }
            } else {
                returnMonth = theMonth;
            }
        }
        return returnMonth;
    }


    public static int[] getCheckPreYearMonth() {
        int[] date = new int[2];
        int yearResult = 0;
        int monthResult = 0;
        int dayResult = 0;

        java.util.Date dateNow = new java.util.Date(System.currentTimeMillis());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String nowStr = sdf.format(dateNow);
        String yearStr = nowStr.substring(0, 4);
        String monthStr = nowStr.substring(5, 7);
        String dayStr = nowStr.substring(8, 10);

        int theYear = Integer.parseInt(yearStr);
        int theDay = Integer.parseInt(dayStr);
        int theMonth = Integer.parseInt(monthStr);

        //25日开始考核
        if (theDay < 25) {
            if (theMonth == 1) {
                yearResult = theYear - 1;
                monthResult = 12;
            } else {
                yearResult = theYear;
                monthResult = theMonth - 1;
            }
        } else {
            yearResult = theYear;
            monthResult = theMonth;
        }

        date[0] = yearResult;
        date[1] = monthResult;

        return date;
    }


    public static int[] getCurrYearMonth() {
        int[] date = new int[3];
        java.util.Date dateNow = new java.util.Date(System.currentTimeMillis());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String nowStr = sdf.format(dateNow);
        String yearStr = nowStr.substring(0, 4);
        String monthStr = nowStr.substring(5, 7);
        String dayStr = nowStr.substring(8, 10);
        int theYear = Integer.parseInt(yearStr);
        int theMonth = Integer.parseInt(monthStr);
        int theDay = Integer.parseInt(dayStr);
        date[0] = theYear;
        date[1] = theMonth;
        date[2] = theDay;
        return date;
    }

    public static int[] getCurrYearMonth(String dateStr) {
        try {
            int[] date = new int[3];
            String yearStr = dateStr.substring(0, 4);
            String monthStr = dateStr.substring(5, 7);
            String dayStr = dateStr.substring(8, 10);
            int theYear = Integer.parseInt(yearStr);
            int theMonth = Integer.parseInt(monthStr);
            int theDay = Integer.parseInt(dayStr);
            date[0] = theYear;
            date[1] = theMonth;
            date[2] = theDay;
            return date;
        } catch (Exception e) {
            return getCurrYearMonth();
        }

    }


    public static int[] getYearMonthDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        int[] dates = new int[3];
        dates[0] = calendar.get(Calendar.YEAR);
        dates[1] = calendar.get(Calendar.MONTH) + 1;
        dates[2] = calendar.get(Calendar.DATE);
        return dates;

    }

    public static java.sql.Date getCurrDate(String dateStr){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        java.util.Date dt;
		try {
			dt = sdf.parse(dateStr);
			java.sql.Date sqlDate = new java.sql.Date(dt.getTime());
			return sqlDate;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
    }

    public static String getDateString(java.util.Date date) {
        int[] ymd = getYearMonthDay(new Date(date.getTime()));
        return getDateString(ymd[0], ymd[1], ymd[2]);
    }

    public static String getDateString(java.sql.Date date) {
        int[] ymd = getYearMonthDay(date);
        return getDateString(ymd[0], ymd[1], ymd[2]);
    }

    public static String getDateString(int year, int month, int day) {
        StringBuffer dateBuffer = new StringBuffer();
        dateBuffer.append(year + "-");
        if (month < 10) {
            dateBuffer.append("0" + month + "-");
        } else {
            dateBuffer.append(month + "-");
        }
        if (day < 10) {
            dateBuffer.append("0" + day);
        } else {
            dateBuffer.append(day);
        }
        return dateBuffer.toString();
    }

    public static class DateHelper {
        private int year;
        private int month;
        private int day;

        public int getDay() {
            return day;
        }

        public void setDay(int day) {
            this.day = day;
        }

        public int getMonth() {
            return month;
        }

        public void setMonth(int month) {
            this.month = month;
        }

        public int getYear() {
            return year;
        }

        public void setYear(int year) {
            this.year = year;
        }

    }

    /**
     * month是从0开始的,所以实际的month减去1得到正确结果
     *
     * @param year
     * @param month
     * @return
     */
    public static int getMonthHaveDays(int year, int month) {
        Calendar calendar = GregorianCalendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month - 1);
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }


    public static int[] getUpperYearMonth(int year, int month) {
        int[] upperYearMonth = new int[2];
        int day = 1;
        Calendar calendar = GregorianCalendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month - 1);
        calendar.set(Calendar.DATE, day - 1);

        upperYearMonth[0] = calendar.get(Calendar.YEAR);
        upperYearMonth[1] = calendar.get(Calendar.MONTH) + 1;
        return upperYearMonth;
    }

    //返回一个日期加上月份数的日期
    public static Date getDateAddMonth(Date date, int month) throws Exception {
        int[] ymd = getYearMonthDay(date);
        Calendar calendar = GregorianCalendar.getInstance();
        calendar.set(Calendar.YEAR, ymd[0]);
        calendar.set(Calendar.MONTH, ymd[1] + month - 1);
        calendar.set(Calendar.DATE, ymd[2]);
        int tyear = calendar.get(Calendar.YEAR);
        int tmonth = calendar.get(Calendar.MONTH) + 1;
        int tday = calendar.get(Calendar.DATE);
        return getCurrDate(getDateString(tyear, tmonth, tday));
    }
    //  返回一个日期加上月份数的日期
    public static Date getDateAddDay(Date date, int dayNum) throws Exception {
        int[] ymd = getYearMonthDay(date);
        Calendar calendar = GregorianCalendar.getInstance();
        calendar.set(Calendar.YEAR, ymd[0]);
        calendar.set(Calendar.MONTH, ymd[1] - 1);
        calendar.set(Calendar.DATE, ymd[2]+dayNum);
        int tyear = calendar.get(Calendar.YEAR);
        int tmonth = calendar.get(Calendar.MONTH) + 1;
        int tday = calendar.get(Calendar.DATE);
        return getCurrDate(getDateString(tyear, tmonth, tday));
    }

    public static void main(String[] args) {
        try {
            Date date = getCurrDate("2006-02-04");
            Date tdate = getDateAddMonth(date, 3);
            System.out.println(tdate);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //取得系统目前的年份
    public int getCurrentYear() {
        java.util.Date dateNow = new java.util.Date(System.currentTimeMillis());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String nowStr = sdf.format(dateNow);
        String yearStr = nowStr.substring(0, 4);
        int theYear = Integer.parseInt(yearStr);

        return theYear;
    }


	public static String getNextDate(Date endTime) {
		int[] ymd = getYearMonthDay(endTime);
		
		switch(ymd[1]){
			case 1:{
				if(ymd[2] <31){
					ymd[2]++;
				}else {
					ymd[2] = 1;
					ymd[1] = 12;
					ymd[0] ++;
				}
				break;
			}
			case 2:{
				if(ymd[0]%4 == 0){
					if(ymd[2] < 29){
						ymd[2]++;
					}else {
						ymd[2] = 1;
						ymd[1] ++;
					}
				}
				break;
			}
			case 3:{
				if(ymd[2] <31){
					ymd[2]++;
				}else {
					ymd[2] = 1;
					ymd[1] ++;
				}
				break;
			}
			case 4:{
				if(ymd[2] <30){
					ymd[2]++;
				}else {
					ymd[2] = 1;
					ymd[1] ++;
				}
				break;
			}
			case 5:{
				if(ymd[2] <31){
					ymd[2]++;
				}else {
					ymd[2] = 1;
					ymd[1] ++;
				}
				break;
			}
			case 6:{
				if(ymd[2] <30){
					ymd[2]++;
				}else {
					ymd[2] = 1;
					ymd[1] ++;
				}
				break;
			}
			case 7:{
				if(ymd[2] <31){
					ymd[2]++;
				}else {
					ymd[2] = 1;
					ymd[1] ++;
				}
				break;
			}
			case 8:{
				if(ymd[2] <31){
					ymd[2]++;
				}else {
					ymd[2] = 1;
					ymd[1] ++;
				}
				break;
			}
			case 9:{
				if(ymd[2] <30){
					ymd[2]++;
				}else {
					ymd[2] = 1;
					ymd[1] ++;
				}
				break;
			}
			case 10:{
				if(ymd[2] <31){
					ymd[2]++;
				}else {
					ymd[2] = 1;
					ymd[1] ++;
				}
				break;
			}
			case 11:{
				if(ymd[2] <30){
					ymd[2]++;
				}else {
					ymd[2] = 1;
					ymd[1] ++;
				}
				break;
			}
			case 12:{
				if(ymd[2] <31){
					ymd[2]++;
				}else {
					ymd[2] = 1;
					ymd[1] = 1;
					ymd[0] ++;
				}
				break;
			}
		}
		
		return "" + ymd[0] + "-" + ymd[1] + "-" + ymd[2];
	}


	public static int[] getNextYearMonth() {
		int[] date = getCurrYearMonth();
		if(date[1] == 12){
			date[0] ++;
			date[1] = 1;
		}else{
			date[1] ++;
		}
		return date;
	}


	public static Date getYesterday(Date temp) {
		Calendar ll = Calendar.getInstance();
		ll.setTime(temp);
		int td = ll.get(Calendar.DAY_OF_MONTH);
		ll.set(Calendar.DAY_OF_MONTH,td-1);
		java.util.Date yesterday = ll.getTime();
		return new java.sql.Date(yesterday.getTime());
	}


	public static int[] getLastMonth() {
		int[] date = getCurrYearMonth();
		date[1] --;
		if(date[1] == 0){
			date[0] --;
			date[1] = 12;
		}
		return date;
	}
}
