package com.ada.common.util;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.commons.lang3.time.DurationFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 对日期的加减操作使用@link org.apache.commons.lang.time.DateUtils类来处理，
 * 当前类只处理一些特殊的日期获取及日期格式的操作
 *
 * @author lancey
 *
 */
public class DateUtil {
	private static Logger logger = LoggerFactory.getLogger(DateUtil.class);
	/**
	 * 星期一
	 */
	public static final String WEEK_MONDAY = "星期一";
	/**
	 * 星期二
	 */
	public static final String WEEK_TUESDAY = "星期二";
	/**
	 * 星期三
	 */
	public static final String WEEK_WEDNESDAY = "星期三";
	/**
	 * 星期四
	 */
	public static final String WEEK_THURSDAY = "星期四";
	/**
	 * 星期五
	 */
	public static final String WEEK_FRIDAY = "星期五";
	/**
	 * 星期六
	 */
	public static final String WEEK_SATURDAY = "星期六";
	/**
	 * 星期日
	 */
	public static final String WEEK_SUNDAY = "星期日";
	/**
	 * yyyy-MM-dd
	 */
	public static final String SIMPLE_DATE_FORMAT = "yyyy-MM-dd";


	/**
	 * 部分日期规则
	 */
	public enum DATE_PATTERN{
		/**
		 * yyyy-MM-dd
		 */
		SIMPLE(SIMPLE_DATE_FORMAT),
		/**
		 * yyyy.MM.dd
		 */
		SIMPLE_DOT("yyyy.MM.dd"),
		/**
		 * yyyy/MM/dd
		 */
		SIMPLE_SLASH("yyyy/MM/dd"),
		/**
		 * yyyy-MM-dd HH:mm:ss
		 */
		DETAIL("yyyy-MM-dd HH:mm:ss"),
		/**
		 * yyyy.MM.dd HH:mm:ss
		 */
		DETAIL_DOT("yyyy.MM.dd HH:mm:ss"),
		/**
		 * yyyy/MM/dd HH:mm:ss
		 */
		DETAIL_SLASH("yyyy/MM/dd HH:mm:ss"),
		/**
		 * yyyy-MM-dd HH:mm
		 */
		YYYY_MM_DDHH_MM("yyyy-MM-dd HH:mm"),
		/**
		 * yyyy.MM.dd HH:mm
		 */
		YYYY_MM_DDHH_MM_DOT("yyyy.MM.dd HH:mm"),
		/**
		 * yyyy/MM/dd HH:mm
		 */
		YYYY_MM_DDHH_MM_SLASH("yyyy/MM/dd HH:mm"),

		/**
		 * yyyyMMddHHmmss
		 */
		yyyyMMddHHmmss("yyyyMMddHHmmss"), //NOSONAR

		/**
		 * HHmmss
		 */
		HHmmss("HHmmss"), //NOSONAR

		/**
		 * yyyy
		 */
		yyyy("yyyy"),//NOSONAR

		/**
		 * yyyyMMdd
		 */
		yyyyMMdd("yyyyMMdd"),//NOSONAR

		/**
		 * yyyyw
		 */
		yyyyw("yyyyw"),//NOSONAR

		/**
		 * yyyyMM
		 */
		yyyyMM("yyyyMM"),//NOSONAR

		/**
		 * yyyy.MM
		 */
		yyyyMM_DOT("yyyy.MM"), //NOSONAR

		/**
		 * yyyy/MM
		 */
		yyyyMM_SLASH("yyyy/MM"),	//NOSONAR

		/**
		 * yyMMdd
		 */

		yyMMdd("yyMMdd"),//NOSONAR

		/**
		 * yy.MM.dd
		 */
		yyMMdd_DOT("yy.MM.dd"),//NOSONAR

		/**
		 * yy/MM/dd
		 */

		yyMMdd_SLASH("yy/MM/dd");//NOSONAR

		private String pattern;

		DATE_PATTERN(String pattern){
			this.pattern = pattern;
		}

		public Date parse(String date){
			return DateUtil.parse(date,this.pattern);
		}
		public String format(Date date){
			return DateUtil.format(date,this.pattern);
		}
	}


	public static Date parse(String date,String pattern){
		SimpleDateFormat sf = new SimpleDateFormat(pattern);
		try {
			return sf.parse(date);
		} catch (ParseException e) {
			logger.info(e.getMessage());
			return null;
		}
	}

    /**
     * 日期格式化
     * @brief   日期格式化
     * @details 日期格式化
     * @param date 日期
     * @param pattern 格式
     * @return
     * @exception
     * @see
     * @author wangxiangyang
     * @date 2016年11月24日 下午1:25:36
     * @note wangxiangyang@ 2016年11月24日添加了此方法
    */
     public static String format(Date date,String pattern){
         return format(date,pattern,null);
     }

    /**
      * 日期格式化
      * @brief   日期格式化
      * @details 日期格式化
      * @param date 日期
      * @param pattern 格式
      * @param def 默认值
      * @return
      * @exception
      * @see
      * @author wangxiangyang
      * @date 2016年11月24日 下午1:25:36
      * @note wangxiangyang@ 2016年11月24日添加了此方法
     */
    public static String format(Date date,String pattern,String def) {
        if (date == null) {
            return def;
        }
        if(StringUtils.isEmpty(pattern)){
            return null;
        }
        try{
            SimpleDateFormat format = new SimpleDateFormat(pattern);
            String result =  format.format(date);
            if(StringUtils.isEmpty(result)){
            	return def;
			}
			return result;
        }catch(Exception e){
			logger.info(e.getMessage());
            return def;
        }
    }

	/**
	 * 月份不包含开始 给定时间月份加一天
	 */
	private static int INCLUDE_START = 0;//NOSONAR
	/**
	 * 月份不包含结尾 给定时间月份减一天
	 */
	private static int INCLUDE_END = 1;//NOSONAR
	/**
	 * 月份不包含结尾 给定时间月份减一天
	 */
	private static int INCLUDE_START_END = 3;//NOSONAR
	/**
	 * 获取指定时间段内的年和月
	 * 比如  给定时间 2015-1-23  到 2015-10-30 返回  2015-1 2015-2 2015-3 .....2015-10。
	 * @Title: getYearAndMonthFromDate
	 * @Description:
 	 * @param @param startDate
	 * @param @param endDate
	 * @param classType 返回字符串类型的 2015-1  还是时间类型的 2015-1
	 * @param dateCon   0 返回的字符串  月份包含开始且包含结尾
 	 *                  1 返回的字符串  月份包含开始不包含结尾
	 *                  2 返回的字符串  月份不包含开始包含结尾
	 *                  3 返回的字符串  月份不包含开始也不包含结尾
	 * @param @return
	 * @return List<Date>
	 * @throws
	 * @Date 2015年1月23日 下午2:17:15
     *
     *
     * @note 蒋文@2016.3.14修改 改为倒序，个位数的月份填充0，包含结尾
 	 */
	public static List getYearAndMonthFromDate (Date startDate,Date endDate,Integer dateCon,Class classType){
		List<Date> dates = new ArrayList<>();

		Calendar c = Calendar.getInstance();
		c.setTime(startDate);

        Calendar c1 = Calendar.getInstance();
        c1.setTime(endDate);

		//月份不包含开始 给定时间月份加一天
		if(dateCon.equals(INCLUDE_START)){
			c.add(Calendar.MONTH, -1);
		}

        //月份不包含结尾 给定时间月份减一天
        if(dateCon.equals(INCLUDE_END)){
            c.add(Calendar.MONTH, -1);
            c1.add(Calendar.MONTH, -1);
        }

        //月份不包含结尾 给定时间月份减一天
        if(dateCon.equals(INCLUDE_START_END)){
            c1.add(Calendar.MONTH, -1);
        }

		try {
			while ((c.get(Calendar.YEAR) < c1.get(Calendar.YEAR))||(c.get(Calendar.YEAR) == c1.get(Calendar.YEAR) &&c.get(Calendar.MONTH) != c1.get(Calendar.MONTH) + 1)) {
				 Date date = new Date();
				 date.setTime(c.getTimeInMillis());
				 dates.add(date);
				 c.add(Calendar.MONTH, 1);
			}

			if(classType.equals(String.class)){
				List<String> dateStr = new ArrayList<>();
                for (int i = dates.size() - 1; i > 0; i--){
					Calendar cno = Calendar.getInstance();
					cno.setTime(dates.get(i));
                    int month = cno.get(Calendar.MONTH) + 1;
					dateStr.add(cno.get(Calendar.YEAR) + "-" + ((month < 10) ? "0" + month : month));
				}
				return dateStr;

			}else if(classType.equals(Date.class)){
				return dates;
			}
		} catch (Exception e) {
			logger.info(e.getMessage());
		}

		return null;
	}



	/**
	 * 获取本月的第一天或者最后一天
	 *
	 * @param b true 为第一天 false为最后一天
	 * @return
	 * @throws Exception
	 */
	public static Date getFirstOrEndDateByMonth(boolean b)  {
		return getFirstOrEndDateByMonth(new Date(),b);
	}
	/**
	 * 获取本月的第一天或者最后一天
	 *
	 * @param b true 为第一天 false为最后一天
	 * @return
	 * @throws Exception
	 */
	public static Date getFirstOrEndDateByMonth(Date date, boolean b)  {

		// 获取当前日期
		Calendar cal = Calendar.getInstance();

		cal.setTime(date);
		if (b) {
			// 获取当前月第一天：
			cal.add(Calendar.MONTH, 0);
			cal.set(Calendar.DAY_OF_MONTH, 1);
			// 设置为1号,当前日期既为本月第一天
		} else {
			// 获取当前月最后一天
			cal.set(Calendar.DAY_OF_MONTH,
					cal.getActualMaximum(Calendar.DAY_OF_MONTH));
		}
		return cal.getTime();
	}

	/**
	 * 获取当前时间的周一和周日
	 *
	 * @param b true 为周一 false为周日
	 * @return
	 * @throws Exception
	 */
	public static Date getFirstOrEndWeekDate(boolean b)  {
		return getFirstOrEndWeekDate(new Date(),b);
	}

    public static Date getFirstOrEndWeekDate(Date date, boolean b)  {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
        if (b) {
            // 获取本周一的日期
            cal.setFirstDayOfWeek(Calendar.MONDAY);
            cal.set(Calendar.HOUR_OF_DAY, 0);
            cal.set(Calendar.MINUTE, 0);
            cal.set(Calendar.SECOND, 0);
            cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        } else {
            // 获取本周日的日期
            cal.setFirstDayOfWeek(Calendar.MONDAY);
            cal.set(Calendar.HOUR_OF_DAY, 23);
            cal.set(Calendar.MINUTE, 59);
            cal.set(Calendar.SECOND, 59);
            cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        }
        return cal.getTime();
    }

	/**
	 * 获取两个时间的天数差
	 *
	 * @param startDate 开始日期
	 * @param endDate 截止日期
	 * @return int
	 */
	public static int getDays(Date startDate, Date endDate) {
		int dayMs = 60 * 60 * 24 * 1000;
		long days = (endDate.getTime() - startDate.getTime()) / dayMs;
		return (int) days;
	}

	/**
	 * @brief 获取开始日期和结束日期之间差的月数
	 * @details 获取开始日期和结束日期之间差的月数
	 * @param start
	 * @param end
	 * @return int
	 * @see
	 * @author 田东兴
	 * @date 2016年4月1日 上午11:08:23
	 * @note 田东兴 2016年4月1日上午11:08:23变更
	 */
	public static int getDifferMonth(Date start, Date end){
		if (start.after(end)) {
			Date t = start;
			start = end;
			end = t;
		}
		Calendar startCalendar = Calendar.getInstance();
		startCalendar.setTime(start);
		Calendar endCalendar = Calendar.getInstance();
		endCalendar.setTime(end);
		Calendar temp = Calendar.getInstance();
		temp.setTime(end);
		temp.add(Calendar.DATE, 1);

		int year = endCalendar.get(Calendar.YEAR)
				- startCalendar.get(Calendar.YEAR);
		int month = endCalendar.get(Calendar.MONTH)
				- startCalendar.get(Calendar.MONTH);

		if ((startCalendar.get(Calendar.DATE) == 1)
				&& (temp.get(Calendar.DATE) == 1)) {
			return year * 12 + month + 1;
		} else if ((startCalendar.get(Calendar.DATE) != 1)
				&& (temp.get(Calendar.DATE) == 1)) {
			return year * 12 + month;
		} else if ((startCalendar.get(Calendar.DATE) == 1)
				&& (temp.get(Calendar.DATE) != 1)) {
			return year * 12 + month;
		} else {
			return (year * 12 + month - 1) < 0 ? 0 : (year * 12 + month);
		}
	}

	/**
     * 获取两个时间的秒差
     * @param strDate
     * @param endDate
     * @return
     */
    public static long getSeconds(Date strDate,Date endDate){
        long minutes = (endDate.getTime() - strDate.getTime())/ 1000;
        return minutes;
    }

	/**
	 * 获取两个时间的分钟差
	 * @param strDate
	 * @param endDate
	 * @return
	 */
	public static long getMinutes(Date strDate,Date endDate){
		long minutes = (endDate.getTime() - strDate.getTime())/ (1000*60);
		return minutes;
	}
	/**
	 *
	 * @brief 获取两个日期之间的小时数
	 * @details 两个时间之差大于0向上取整、小于0向下取整 (不满一小时按一个计算)
	 * @param strDate 开始日期
	 * @param endDate 结束日期
	 * @return int类型
	 * @author 郑良杰
	 * @date 2016年3月17日下午1:15:40
	 * @note
	 * @note
	 */
	public static int getHours(Date strDate,Date endDate){
		double  minutes = (endDate.getTime() - strDate.getTime())/ (1000*60*60.00);
		int  hours = 0;
		if(minutes>=0){
			hours = (int) Math.ceil (minutes);
		}else{
			hours = (int) Math.floor (minutes);
		}
		return hours;
	}

	private static final int JANUARY = 1;
	private static final int MARCH = 3;
	private static final int APRIL = 4;
	private static final int JUNE = 6;
	private static final int JULY = 7;
	private static final int SEPTEMBER = 9;
	private static final int OCTOBER = 10;
	private static final int DECEMBER = 12;

    /**
     * @brief 获取本季度的第一天或最后一天
     * @details 从一月开始计算
     * @param isFirst 是否获取开始日期 false会获取结束日期
     * @return date
     * @author 蒋文
     * @date 2016/3/11 17:36
     * @note 蒋文新建 2016/3/11 17:36
     */
    public static Date getFirstOrLastDayOfSeason(boolean isFirst) {
        if (isFirst) {
			return getFirstDayOfSeason();
		}
		return getLastDayOfSeason();
    }

	private static Date getFirstDayOfSeason() {
		Calendar c = Calendar.getInstance();
		int currentMonth = c.get(Calendar.MONTH) + 1;
		if (currentMonth >= JANUARY && currentMonth <= MARCH) {
			c.set(Calendar.MONTH, 0);
		} else if (currentMonth >= APRIL && currentMonth <= JUNE) {
			c.set(Calendar.MONTH, 3);
		} else if (currentMonth >= JULY && currentMonth <= SEPTEMBER) {
			c.set(Calendar.MONTH, 6);
		} else if (currentMonth >= OCTOBER && currentMonth <= DECEMBER) {
			c.set(Calendar.MONTH, 9);
		}
		c.set(Calendar.DATE, 1);
		return c.getTime();
	}

	private static Date getLastDayOfSeason() {
		Calendar c = Calendar.getInstance();
		int currentMonth = c.get(Calendar.MONTH) + 1;
		if (currentMonth >= JANUARY && currentMonth <= MARCH) {
			c.set(Calendar.MONTH, 2);
			c.set(Calendar.DATE, 31);
		} else if (currentMonth >= APRIL && currentMonth <= JUNE) {
			c.set(Calendar.MONTH, 5);
			c.set(Calendar.DATE, 30);
		} else if (currentMonth >= JULY && currentMonth <= SEPTEMBER) {
			c.set(Calendar.MONTH, 8);
			c.set(Calendar.DATE, 30);
		} else if (currentMonth >= OCTOBER && currentMonth <= DECEMBER) {
			c.set(Calendar.MONTH, 11);
			c.set(Calendar.DATE, 31);
		}
		return c.getTime();
	}


	/**
	 *
	 * @brief 返回指定日期区间内的每一天
	 * @details 返回指定日期区间内的每一天
	 * @param startDate
	 * @param endDate
	 * @return List<Date>
	 * @author lvchongxin
	 * @date 2016年3月12日上午11:45:29
	 * @note
	 */
	public static List<Date> getDateList(Date startDate, Date endDate){
		List<Date> dateList=new ArrayList<Date>();
		Calendar cal = Calendar.getInstance();
		cal.setTime(startDate);
		while(cal.getTime().before(endDate)){
			dateList.add(cal.getTime());
			cal.add(Calendar.DAY_OF_MONTH, 1);
		}
		return dateList;
	}

	/**
	 * @brief 添加
	 * @param date
	 * @return 一个月有多少天
	 * @author hui.wang
	 * @date 2016年7月5日
	 * @note
	 */
	public static int getNumOfMonth(Date date){
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int days = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
 		return days;
	}


	/**
	  * @brief 根据日期获取星期几.
	  * @details
	  * @param
	  * @retval
	  * @retval
	  * @exception
	  * @see
	  * @author 张浩
	  * @date 2017/2/21 17:35
	  * @note 张浩 2017/2/21添加了此方法
	*/
	public static String getWeekOfDate(Date dt) {
		String[] weekDays = {WEEK_SUNDAY, WEEK_MONDAY, WEEK_TUESDAY, WEEK_WEDNESDAY, WEEK_THURSDAY, WEEK_FRIDAY, WEEK_SATURDAY};
		Calendar cal = Calendar.getInstance();
		cal.setTime(dt);
		int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
		if (w < 0) {
			w = 0;
		}
		return weekDays[w];
	}

	public static Date getWeekDay(Date currentDate, int day){
		if (day < Calendar.SUNDAY || day > Calendar.SATURDAY) {
			throw new RuntimeException("指定的星期几不正确");
		}
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(currentDate);
		if (day == Calendar.SUNDAY && calendar.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY) {
			calendar.add(Calendar.DATE, 7);
		}
		if (day != Calendar.SUNDAY && calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
			calendar.add(Calendar.DATE, -7);
		}
		calendar.set(Calendar.DAY_OF_WEEK, day);
		if (day == Calendar.SUNDAY) {
			calendar.set(Calendar.HOUR_OF_DAY, 23);
			calendar.set(Calendar.MINUTE, 59);
			calendar.set(Calendar.SECOND, 59);
		} else {
			calendar.set(Calendar.HOUR_OF_DAY, 0);
			calendar.set(Calendar.MINUTE, 0);
			calendar.set(Calendar.SECOND, 0);
		}
		return calendar.getTime();
	}


	/**
	 *  判断当前日期是否是今天或者昨天
	 * @param time
	 * @return
	 */
	public static String checkTodayOrTomorrow(String time) {
		SimpleDateFormat format = new SimpleDateFormat(SIMPLE_DATE_FORMAT);
		if(time==null ||"".equals(time)){
			return "";
		}
		Date date = null;
		try {
			date = format.parse(time);
		} catch (ParseException e) {
			e.printStackTrace();
			throw new RuntimeException("转换日期异常："+time);
		}
		Calendar current = Calendar.getInstance();
		//今天
		Calendar today = Calendar.getInstance();
		today.set(Calendar.YEAR, current.get(Calendar.YEAR));
		today.set(Calendar.MONTH, current.get(Calendar.MONTH));
		today.set(Calendar.DAY_OF_MONTH,current.get(Calendar.DAY_OF_MONTH));
		today.set(Calendar.MILLISECOND, 0);
		//Calendar.HOUR——12小时制的小时数 Calendar.HOUR_OF_DAY——24小时制的小时数
		today.set( Calendar.HOUR_OF_DAY, 0);
		today.set( Calendar.MINUTE, 0);
		today.set(Calendar.SECOND, 0);
		//昨天
		Calendar yesterday = Calendar.getInstance();
		yesterday.set(Calendar.YEAR, current.get(Calendar.YEAR));
		yesterday.set(Calendar.MONTH, current.get(Calendar.MONTH));
		yesterday.set(Calendar.DAY_OF_MONTH,current.get(Calendar.DAY_OF_MONTH)-1);
		yesterday.set( Calendar.HOUR_OF_DAY, 0);
		yesterday.set( Calendar.MINUTE, 0);
		yesterday.set(Calendar.SECOND, 0);
		yesterday.set(Calendar.MILLISECOND, 0);
		current.setTime(date);
		String titleDate = time.substring((time.indexOf('-') + 1), (time.length())).replace('-', '.');
		if(current.equals(today)){
			return "今天 ";
		}else if(current.equals(yesterday)){
			return "昨天 ";
		}else {
			return titleDate;
		}
	}


	/**
	 * 获取时间区间的字符串数组
	 * @param start
	 * @param end
	 * @param isDayOrMonth
	 * @return
	 */
	public  static List<String> getLstDateInterval(Date start,Date end,boolean isDayOrMonth){
		List<String> lstDateIntervals=null;
		//日期区间
		if(isDayOrMonth){
			//计算日期  "y-M-d"
			String strSize= DurationFormatUtils.formatPeriod(start.getTime(), end.getTime(), "d");
			int size=Integer.parseInt(strSize);
			lstDateIntervals=new ArrayList<String>(size);
			for(int i=size-1;i>=0;i--){
				Date currentDate= DateUtils.addDays(start,i);
				String formatDate= DateUtil.format(currentDate,SIMPLE_DATE_FORMAT);
				lstDateIntervals.add(formatDate);
			}
		}else {
			//计算月份
			String strSize= DurationFormatUtils.formatPeriod(start.getTime(), end.getTime(), "M");
			int size=Integer.parseInt(strSize);
			lstDateIntervals=new ArrayList<String>(size);
			for(int i=size-1;i>=0;i--){
				Date currentDate= DateUtils.addMonths(start,i);
				String formatDate= DateUtil.format(currentDate,"yyyy-MM");
				lstDateIntervals.add(formatDate);
			}
		}
		return lstDateIntervals;
	}


	/**
	 * 判断是否是一个月的第一天
	 * @return
	 */
	public static boolean checkFirstDay(){
		Calendar c = Calendar.getInstance();
		int today = c.get(Calendar.DAY_OF_MONTH);
		return 1 == today;
	}

	/**
	 * 判断传递进来的日期是否是今天
	 * @param time
	 * @return
	 */
	public static boolean checkIfToday(String time) {
		return DateUtils.isSameDay(DATE_PATTERN.SIMPLE.parse(time),new Date());
	}

	public static Date add(Date date, int calendarField, int amount) {
		if (date == null) {
			throw new IllegalArgumentException("The date must not be null");
		} else {
			Calendar c = Calendar.getInstance();
			c.setTime(date);
			c.add(calendarField, amount);
			return c.getTime();
		}
	}

	public static Date addYears(Date date, int amount) {
		return add(date, 1, amount);
	}

	public static Date addMonths(Date date, int amount) {
		return add(date, 2, amount);
	}

	public static Date addWeeks(Date date, int amount) {
		return add(date, 3, amount);
	}

	public static Date addDays(Date date, int amount) {
		return add(date, 5, amount);
	}

	public static Date addHours(Date date, int amount) {
		return add(date, 11, amount);
	}

	public static Date addMinutes(Date date, int amount) {
		return add(date, 12, amount);
	}

	public static Date addSeconds(Date date, int amount) {
		return add(date, 13, amount);
	}

	public static Date addMilliseconds(Date date, int amount) {
		return add(date, 14, amount);
	}

}
