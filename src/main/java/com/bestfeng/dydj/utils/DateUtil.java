package com.bestfeng.dydj.utils;

import com.google.common.collect.Lists;
import org.springframework.util.Assert;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public final class DateUtil {

	/**
	 * dateDiff()方法的unit参数
	 */
	public final static byte DIFF_YEAR = 0;// 以年为单位
	public final static byte DIFF_MONTH = 1;// 以月为单位
	public final static byte DIFF_DAY = 2;// 以日为单位
	public final static byte DIFF_HOUR = 3;// 以小时为单位
	public final static byte DIFF_MINUTE = 4;// 以分钟为单位
	public final static byte DIFF_SECONDE = 5;// 以秒为单位
	public final static byte DIFF_MILLISECOND = 6;// 以毫秒为单位

	public final static String DATA_FORMAT = "yyyy-MM-dd HH:mm:ss";
	public final static String DATA_FORMAT_SIMPLE = "yyyy-MM-dd HH:mm";
	public final static String DATA_FORMAT_DATE = "yyyy-MM-dd";
	public final static String DATA_FORMAT_MM_DD = "MM-dd";
	public final static String DATA_FORMAT_MM_SS = "HH:mm";
    public final static String DATA_FORMAT_DD = "MM/dd/yyyy";
    public final static String DATA_FORMAT_PRE = "yyyyMMdd";

	/** 线程安全处理 存放不同的日期模板格式的Map */
	private static final ThreadLocal<Map<String, SimpleDateFormat>> messageFormat = new ThreadLocal<Map<String, SimpleDateFormat>>() {
		protected Map<String, SimpleDateFormat> initialValue() {
			return new HashMap<String, SimpleDateFormat>();
		}
	};

	/**
	 * 返回一个SimpleDateFormat,每个线程只会new一次dateFormat对应的sdf
	 * 
	 * @param dateFormat 日期格式
	 * @return 格式划对象
	 */
	private static SimpleDateFormat getDateFormat(final String dateFormat) {
		Map<String, SimpleDateFormat> formatters = messageFormat.get();
		SimpleDateFormat sdf = formatters.get(dateFormat);
		if (sdf == null) {
			sdf = new SimpleDateFormat(dateFormat);
			formatters.put(dateFormat, sdf);
		}
		return sdf;
	}

	public static List<String> Day31 = new ArrayList<String>(31);

	static {
		if (Day31.size() == 0) {
			for (int i = 1; i < 32; i++) {
				Day31.add(String.valueOf(i));
			}
		}
	}

	/**
	 * 获取时间日历对象
	 */
	public static Calendar getCalendar() {
		return Calendar.getInstance();
	}

	/**
	 * 获取年份(yyyy)
	 */
	public static String getYear() {
		Date date = null;
		return getYear(date, 0);
	}

	/**
	 * 获取年份(yyyy)
	 * 
	 * @param dateStr 日期字符串，要求yyyy-MM-dd
	 */
	public static String getYear(String dateStr) {
		Date date = parseDate(dateStr);
		return getYear(date, 0);
	}

	/**
	 * 获取年份(yyyy)
	 * 
	 * @param date 日期
	 */
	public static String getYear(Date date) {
		return getYear(date, 0);
	}

	/**
	 * 获取年份(yyyy)
	 * 
	 * @param dateStr 日期字符串，要求yyyy-MM-dd
	 * @param deff 与日期中年份的差值
	 */
	public static String getYear(String dateStr, int deff) {
		Date date = parseDate(dateStr);
		return getYear(date, deff);
	}

	/**
	 * 获取年份(yyyy)
	 * 
	 * @param date 日期
	 * @param deff 与日期中年份的差值
	 */
	public static String getYear(Date date, int deff) {
		Calendar c = getCalendar();
		if (date != null) {
			c.setTime(date);
		}
		int year = c.get(Calendar.YEAR) + deff;
		return "" + year;
	}

	/**
	 * 获取月份(MM)
	 */
	public static String getMonthOfYear() {
		Date date = null;
		return getMonthOfYear(date);
	}

	/**
	 * 获取月份(MM)
	 * 
	 * @param dateStr 日期字符串，要求yyyy-MM-dd
	 */
	public static String getMonthOfYear(String dateStr) {
		Date date = parseDate(dateStr);
		return getMonthOfYear(date);
	}

	/**
	 * 获取月份(MM)
	 * 
	 * @param date 日期
	 */
	public static String getMonthOfYear(Date date) {
		Calendar c = getCalendar();
		if (date != null) {
			c.setTime(date);
		}
		String monthStr = "";
		int month = c.get(Calendar.MONTH) + 1;
		if (month < 10) {
			monthStr += "0" + month;
		} else {
			monthStr += "" + month;
		}
		return monthStr;
	}

	/**
	 * 获取日期(DD)
	 */
	public static int getDayofMonth() {
		Calendar c = getCalendar();
		return c.get(Calendar.DAY_OF_MONTH);
	}

	/**
	 * 获取月份(MM)
	 * 
	 * @param dateStr 日期字符串，要求yyyy-MM-dd
	 * @param deff 与日期中月份的差值
	 */
	public static String getMonthOfYear(String dateStr, int deff) {
		Date date = parseDate(dateStr);
		return getMonthOfYear(date);
	}

	/**
	 * 获取月份(MM)
	 * 
	 * @param date 日期
	 * @param deff 与日期中月份的差值
	 */
	public static String getMonthOfYear(Date date, int deff) {
		Calendar c = getCalendar();
		if (date != null) {
			c.setTime(date);
		}
		String monthStr = "";
		int month = c.get(Calendar.MONTH) + 1 + deff;
		if (month <= 0 || month == 12) {
			month = c.get(Calendar.MONTH) + 1;
		} else if (month > 12) {
			month = month % 12;
		}
		if (month < 10) {
			monthStr += "0" + month;
		} else {
			monthStr += "" + month;
		}
		return monthStr;
	}

	/**
	 * 取得当前月 yyyy-MM
	 * 
	 * @return
	 */
	public static String getMonth(String dateStr) {
		return getMonth(dateStr, 0);
	}

	/**
	 * 取得当前日期后（前）deff个月的月 yyyy-MM
	 * 
	 * @return
	 */
	public static String getMonth(String dateStr, int deff) {
		Calendar c = getCalendar();
		Date date = parseDate(dateStr);
		c.setTime(date);
		int month = c.get(Calendar.MONTH);
		month += deff;
		if (month < 0) {
			month += 12;
			c.set(Calendar.YEAR, c.get(Calendar.YEAR) - 1);
		} else if (month > 11) {
			month -= 12;
			c.set(Calendar.YEAR, c.get(Calendar.YEAR) + 1);
		}
		c.set(c.get(Calendar.YEAR), month, 1);
		return getDate(c.getTime(), "yyyy-MM");
	}

	/**
	 * 得到当前日期所在月的开始日期 yyyy-MM-dd
	 * 
	 * @param dateStr
	 * @return
	 */
	public static String getMonthBegin() {
		return getMonthBegin(null);
	}

	/**
	 * 得到当前日期所在月的结束日期 yyyy-MM-dd
	 * 
	 * @param dateStr
	 * @return
	 */
	public static String getMonthEnd() {
		return getMonthEnd(null);
	}

	/**
	 * 得到由dateStr指定的日期所在月的开始日期 yyyy-MM-dd
	 * 
	 * @param dateStr
	 * @return
	 */
	public static String getMonthBegin(String dateStr) {
		Calendar c = getCalendar();
		if (dateStr != null && !"".equals(dateStr.trim())) {
			Date date = parseDate(dateStr, DATA_FORMAT_DATE);
			c.setTime(date);
		}
		c.set(c.get(Calendar.YEAR), c.get(Calendar.MONTH), 1, 0, 0, 0);
		return getDate(c.getTime(), DateUtil.DATA_FORMAT);
	}

	/**
	 * 得到由dateStr指定的日期所在月的结束日期 yyyy-MM-dd
	 * 
	 * @param dateStr
	 * @return
	 */
	public static String getMonthEnd(String dateStr) {
		Calendar c = getCalendar();
		if (dateStr != null && !"".equals(dateStr.trim())) {
			Date date = parseDate(dateStr, DATA_FORMAT_DATE);
			c.setTime(date);
		}
		c.set(c.get(Calendar.YEAR), c.get(Calendar.MONTH) + 1, 1, 23, 59, 59);
		c.add(Calendar.DATE, -1);
		return getDate(c.getTime(), DateUtil.DATA_FORMAT);
	}

	/**
	 * 根据dateStr得到该月的开始时间和结束时间
	 * 
	 * @param dateStr yyyy-MM
	 * @return Integer[0] 月初开始时间  Integer[1]月末结束时间
	 */
	public static Integer[] getMonthBeginAndEnd(String dateStr) {

		Calendar begin = getCalendar();
		if (dateStr != null && !"".equals(dateStr.trim())) {
			Date date = parseDate(dateStr, "yyyy-MM");
			begin.setTime(date);
		}
		begin.set(begin.get(Calendar.YEAR), begin.get(Calendar.MONTH), 1, 0, 0, 0);

		Calendar end = getCalendar();
		if (dateStr != null && !"".equals(dateStr.trim())) {
			Date date = parseDate(dateStr, "yyyy-MM");
			end.setTime(date);
		}
		end.set(end.get(Calendar.YEAR), end.get(Calendar.MONTH) + 1, 1, 23, 59, 59);
		end.add(Calendar.DATE, -1);

		Integer[] relust = new Integer[2];
		relust[0] = (int) (begin.getTimeInMillis() / 1000);
		relust[1] = (int) (end.getTimeInMillis() / 1000);

		return relust;
	}

	/**
	 * 得到指定月的当前日期 yyyy-MM-dd
	 * 
	 * @param deff 
	 * @return
	 */
	public static String getDateByMonth(int deff) {
		Calendar c = getCalendar();
		c.add(Calendar.MONTH, deff);
		c.set(c.get(Calendar.YEAR), c.get(Calendar.MONTH), 1, 0, 0, 0);
		return getDate(c.getTime(), DATA_FORMAT_DATE);
	}

	/**
	 * 得到指定月的开始日期 yyyy-MM-dd
	 * 
	 * @param deff 
	 * @return
	 */
	public static String getDateBeginByMonth(int deff) {
		Calendar c = getCalendar();
		c.add(Calendar.MONTH, deff);
		return getDate(c.getTime(), DATA_FORMAT_DATE);
	}

	/**
	 * 得到由c指定的日期所在星期的开始日期 yyyy-MM-dd
	 * 
	 * @param c
	 * @return
	 */
	public static String getWeekBegin(String dateStr) {
		return getWeekBegin(dateStr, 0);
	}

	/**
	 * 得到由c指定的日期所在星期的结束日期 yyyy-MM-dd
	 * 
	 * @param c
	 * @return
	 */
	public static String getWeekEnd(String dateStr) {
		return getWeekEnd(dateStr, 0);
	}

	/**
	 * 得到指定的日期所在星期的后（前）deff周的开始日期 yyyy-MM-dd
	 * 
	 * @param c
	 * @return
	 */
	public static String getWeekBegin(String dateStr, int deff) {
		Calendar c = getCalendar();
		Date date = parseDate(dateStr);
		c.setTime(date);
		c.setFirstDayOfWeek(Calendar.MONDAY);
		int week = c.get(Calendar.WEEK_OF_YEAR);
		if (c.get(Calendar.MONTH) == Calendar.JANUARY && week > 51) {
			c.set(Calendar.YEAR, c.get(Calendar.YEAR) - 1);
		} else if (c.get(Calendar.MONTH) == Calendar.DECEMBER && week == 1) {
			c.set(Calendar.YEAR, c.get(Calendar.YEAR) + 1);
		}
		week += deff;
		c.set(Calendar.WEEK_OF_YEAR, week);
		c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		return getDate(c.getTime(), DATA_FORMAT_DATE);
	}

	/**
	 * 得到由c指定的日期所在星期的后（前）deff周的结束日期 yyyy-MM-dd
	 * 
	 * @param c
	 * @return
	 */
	public static String getWeekEnd(String dateStr, int deff) {
		Calendar c = getCalendar();
		Date date = parseDate(dateStr);
		c.setTime(date);
		c.setFirstDayOfWeek(Calendar.MONDAY);
		int week = c.get(Calendar.WEEK_OF_YEAR);
		if (c.get(Calendar.MONTH) == Calendar.JANUARY && week > 51) {
			c.set(Calendar.YEAR, c.get(Calendar.YEAR) - 1);
		} else if (c.get(Calendar.MONTH) == Calendar.DECEMBER && week == 1) {
			c.set(Calendar.YEAR, c.get(Calendar.YEAR) + 1);
		}
		week += deff;
		c.set(Calendar.WEEK_OF_YEAR, week);
		c.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
		return getDate(c.getTime(), DATA_FORMAT_DATE);
	}

	/**
	 * 取得当前日期对象 yyyy-MM-dd HH:mm:ss
	 * 
	 * @return 返回java.util.Date日期对象
	 */
	public static Date getCurDate() {
		return getCalendar().getTime();
	}

	// 根据毫秒获取时间
	public static Date getDateByLong(long millis) {
		Calendar c = getCalendar();
		c.setTimeInMillis(millis);
		return c.getTime();
	}

	public static Date getCurrentDate(int house, int minute, int second) {
		return getDate(new Date(), house, minute, second);
	}
	public static String getCurrentDate(int house, int minute, int second,String format){
		Date date=getCurrentDate(house,minute,second);
		return formatRandomDate(date,format);
	}

	public static Date getDate(Date date, int house, int minute, int second) {
		Assert.notNull(date, "date is not null!");
		Calendar startDate = Calendar.getInstance();
		startDate.setTime(date);
		startDate.set(Calendar.HOUR_OF_DAY, house);
		startDate.set(Calendar.MINUTE, minute);
		startDate.set(Calendar.SECOND, second);
		return startDate.getTime();
	}

	/**
	 * 获取时区+8 后的秒数
	 */
	public static long getDateTimeZone8Seconds(Date date) {
		return date.getTime() / 1000L;
	}

	public static int getCurrentSeconds() {
		return getDateSeconds(new Date());
	}

	public static int getDateSeconds(Date date) {
		return (int) (date.getTime() / 1000L);
	}

	/**
	 * 取得当前日
	 * 
	 * @return
	 */
	public static String getDayOfMonth() {
		Calendar c = getCalendar();
		return "" + (c.get(Calendar.DAY_OF_MONTH));
	}

	public static int dayForMonth(String pTime) throws Exception {
		Calendar c = Calendar.getInstance();
		c.setTime(getDateFormat(DATA_FORMAT_DATE).parse(pTime));
		int m = c.get(Calendar.DAY_OF_MONTH);
		return m;
	}

	/**
	 * 取得当前星期数 1,2,3,4,5,6,7 代表 星期一.....
	 * 
	 * @return
	 */
	public static String getDayOfWeek() {
		Calendar c = getCalendar();
		int week = c.get(Calendar.DAY_OF_WEEK);
		if (week > 1) {
			week--;
		} else {
			week = 7;
		}
		return "" + week;
	}

	/**
	 * 取得当前时间,格式为HH:MM:SS
	 * 
	 * @return 返回当前时间
	 */
	public static String getCurTime() {
		return getDate(getCurDate(), "HH:mm:ss");
	}

	/**
	 * 取得当前日期的字符串表示,格式为 yyyy-MM-dd
	 * 
	 * @return 返回日期的字符串表示
	 */
	public static String getDate() {
		return getDate(getCurDate(), DATA_FORMAT_DATE);
	}

	/**
	 * 取得前N个月当前日期的字符串表示,格式为 yyyy-MM-dd
	 * 
	 * @return 返回日期的字符串表示
	 */
	public static String getNMonthCurrentDate(int n) {
		Calendar c = getCalendar();
		c.set(Calendar.MONTH, Calendar.MONTH + 1);
		return getDate(c.getTime(), DATA_FORMAT_DATE);
	}

	/**
	 * 获取当前时间
	 * 
	 * @Title: getDate
	 * @param date
	 * @return
	 */
	public static String getDate(Date date) {
		return getDate(date, DATA_FORMAT_DATE);
	}

	/**
	 * 获取当前日期时间字符串,格式为 yyyy-MM-dd hh:mm:ss
	 * 
	 * @return 返回当前字符串
	 */
	public static String getDatetime() {
		return getDate(getCurDate(), DATA_FORMAT);
	}

	/**
	 * 获取当前日期时间字符串,格式为 自定义
	 *
	 * @return 返回当前字符串
	 */
	public static String getDatetime(String format) {
		return getDate(getCurDate(), format);
	}

	/**
	 * 将指定Date类型转换成指定格式的字符串,格式串参见类注释
	 * 
	 * @param date 日期
	 * @param format 指定的格式,当format为NULL或空串时 默认为 yyyy-MM-dd 格式
	 * @return 当date为NULL时,返回空串
	 */
	public static String getDate(Date date, String format) {

		String dtstr = "";
		if (date == null) {
			return dtstr;
		}

		if (format == null || "".equals(format.trim())) {
			format = DATA_FORMAT_DATE;
		}

		dtstr = getDateFormat(format).format(date);
		return (dtstr == null ? "" : dtstr);

	}

	/**
	 * 将指定字串转换按指定格式转换成java.util.Date类型
	 * 
	 * @param dateStr 日期字串
	 * @param format 指定的格式,当format为NULL或空串时 默认为 yyyy-MM-dd 格式
	 * @return 当dateStr 为null或者转换出错时,返回NULL值
	 * @throws RuntimeException 日期格式与格式串不一致时，抛出RuntimeException
	 */
	public static Date parseDate(String dateStr, String format) {
		Date date = null;
		if (format == null || "".equals(format.trim())) {
			format = DATA_FORMAT_DATE;
		}
		try {
			date = getDateFormat(format).parse(dateStr);
		} catch (ParseException ex) {
			throw new RuntimeException("日期格式与格式串不一致", ex);
		}
		return date;
	}

	/**
	 * 将指定字串转换按指定格式转换成java.util.Date类型
	 * 
	 * @param dateStr 日期字串 yyyy-MM-dd 格式
	 * @return 当dateStr 为null或者转换出错时,返回NULL值
	 * @throws RuntimeException 日期格式与格式串不一致时，抛出RuntimeException
	 */
	public static Date parseDate(String dateStr) {
		return parseDate(dateStr, DATA_FORMAT_DATE);
	}

	/**
	 * 将指定字串转换按指定格式转换成java.util.Date类型
	 * 
	 * @param dateStr 日期字串 yyyy-MM-dd HH:mm:ss 格式
	 * @return 当dateStr 为null或者转换出错时,返回NULL值
	 * @throws RuntimeException 日期格式与格式串不一致时，抛出RuntimeException
	 */
	public static Date paseDateTime(String dateStr) {
		return parseDate(dateStr, DATA_FORMAT);
	}

	/**
	 * 将java.util.Date转换成 java.sql.Date类型
	 * 
	 * @param date java.util.Date对象
	 * @return java.sql.Date对象,如果date为NULL,则返回NULL值
	 */
	public static java.sql.Date parseSQLDate(Date date) {
		if (date == null) {
			return null;
		}
		return new java.sql.Date(date.getTime());
	}

	/**
	 * 将java.util.Date转换成 java.sql.Timestamp类型
	 * 
	 * @param date java.util.Date对象
	 * @return ava.sql.Timestamp对象,如果date为NULL,则返回NULL值
	 */
	public static Timestamp parseTimestamp(Date date) {

		if (date == null) {
			return null;
		}
		return new Timestamp(date.getTime());

	}

	/**
	 * 将指定时间串转换成日期时间对象
	 * 
	 * @param dateStr 时间串,可以是yyyy-MM-dd格式 或者 yyyy-MM-dd HH:mm:ss 格式
	 * @return 返回指定时间的Calendar对象
	 * @throws NullPointerException 当日期时间格式不正确时
	 */
	public static Calendar parseCalendar(String dateStr) {
		return parseCalendar(dateStr, DATA_FORMAT);
	}

	/**
	 * 将指定时间串转换成日期时间对象
	 *
	 * @param dateStr 时间串,可以是yyyy-MM-dd格式 或者 yyyy-MM-dd HH:mm:ss 格式
	 * @return 返回指定时间的Calendar对象
	 * @throws NullPointerException 当日期时间格式不正确时
	 */
	public static Calendar parseCalendar(String dateStr, String format) {
		Date dt = null;
		dt = parseDate(dateStr, format);
		if (dt == null) {
			dt = parseDate(dateStr);
		}
		Calendar c = getCalendar();
		c.setTime(dt);
		return (c);
	}

	/**
	 * @see #dateDiff(Calendar, Calendar, byte)
	 * @return 相差时差
	 */
	public static long dateDiff(String dateFrom, String dateTo, byte unit) {
		Calendar from = parseCalendar(dateFrom);
		Calendar to = parseCalendar(dateTo);
		return dateDiff(from, to, unit);
	}

	/**
	 * @see #dateDiff(Calendar, Calendar, byte)
	 * @return 相差时差
	 */
	public static long dateDiff(String dateFrom, String dateTo, String format, byte unit) {
		Calendar from = parseCalendar(dateFrom, format);
		Calendar to = parseCalendar(dateTo, format);
		return dateDiff(from, to, unit);
	}

	/**
	 * @see #dateDiff(Calendar, Calendar, byte)
	 * @param dateFrom 开始时间
	 * @param dateTo 结束时间
	 * @param unit 单位
	 * @return
	 */
	public static long dateDiff(Date dateFrom, Date dateTo, byte unit) {
		Calendar from = Calendar.getInstance();
		from.setTime(dateFrom);
		Calendar to = Calendar.getInstance();
		to.setTime(dateTo);
		return dateDiff(from, to, unit);
	}

	/**
	 * 返回2个日期之间的差距 unit是时间计算的单位,为本类常量DIFF_中的任一类型
	 * 
	 * @param dateFrom 开始时间的时间串,可以是yyyy-MM-dd格式 或者 yyyy-MM-dd HH:mm:ss 格式
	 * @param dateTo 结束时间的时间串,可以是yyyy-MM-dd格式 或者 yyyy-MM-dd HH:mm:ss 格式
	 * @param unit 是时间计算的单位
	 * @return 相差时差
	 */
	public static long dateDiff(Calendar dateFrom, Calendar dateTo, byte unit) {
		long diff = dateTo.getTimeInMillis() - dateFrom.getTimeInMillis();
		long interval = 0;
		switch (unit) {
		case 0: {
			Calendar from = dateFrom;
			Calendar to = dateTo;
			interval = to.get(Calendar.YEAR) - from.get(Calendar.YEAR);
			from.set(Calendar.YEAR, to.get(Calendar.YEAR));
			if (from.after(to)) {
				if (interval < 0) {
					interval++;
				} else {
					interval--;
				}
			}
			break;
		}
		case 1: {
			int year = dateTo.get(Calendar.YEAR) - dateFrom.get(Calendar.YEAR);
			int month = dateTo.get(Calendar.MONTH) - dateFrom.get(Calendar.MONTH);
			Calendar from = dateFrom;
			Calendar to = dateTo;
			from.set(Calendar.YEAR, dateTo.get(Calendar.YEAR));
			from.set(Calendar.MONTH, dateTo.get(Calendar.MONTH));
			interval = year * 12 + month;
			if (from.after(to)) {
				if (interval < 0) {
					interval++;
				} else {
					interval--;
				}
			}
			break;
		}
		case 2:
			interval = (int) (diff / (1000 * 60 * 60 * 24));
			break;
		case 3:
			interval = (int) (diff / (1000 * 60 * 60));
			break;
		case 4:
			interval = (int) (diff / (1000 * 60));
			break;
		case 5:
			interval = (int) (diff / 1000);
			break;
		default:
			interval = diff;
		}
		return interval;
	}

	/**
	 * 自由格式化日期串
	 * @param date 日期对象
	 * @param strFormat
	 * @return 如果date 或者 strFormat 为null,则返回空串，否则返回指定格式串
	 */
	public static String formatRandomDate(Date date, String strFormat) {
		if (date == null || strFormat == null) {
			return "";
		}

		String key = strFormat;
		key = key.replaceAll("(?<!\\\\)yyyy", getDate(date, "yyyy"));
		key = key.replaceAll("(?<!\\\\)yy", getDate(date, "yy"));
		key = key.replaceAll("\\\\y", "y");

		key = key.replaceAll("(?<!\\\\)MM", getDate(date, "MM"));
		key = key.replaceAll("(?<!\\\\)M", getDate(date, "M"));
		key = key.replaceAll("(?<!\\\\)mm", getDate(date, "mm"));
		key = key.replaceAll("(?<!\\\\)m", getDate(date, "m"));
		key = key.replaceAll("\\\\(M|m)", "$1");

		key = key.replaceAll("(?<!\\\\)dd", getDate(date, "dd"));
		key = key.replaceAll("(?<!\\\\)d", getDate(date, "d"));
		key = key.replaceAll("\\\\d", "d");

		key = key.replaceAll("(?<!\\\\)hh", getDate(date, "hh"));
		key = key.replaceAll("(?<!\\\\)h", getDate(date, "h"));
		key = key.replaceAll("(?<!\\\\)HH", getDate(date, "HH"));
		key = key.replaceAll("(?<!\\\\)H", getDate(date, "H"));
		key = key.replaceAll("\\\\(H|h)", "$1");

		key = key.replaceAll("(?<!\\\\)ss", getDate(date, "ss"));
		key = key.replaceAll("(?<!\\\\)s", getDate(date, "s"));
		key = key.replaceAll("(?<!\\\\)SSS", getDate(date, "SSS"));
		key = key.replaceAll("(?<!\\\\)SS", getDate(date, "SS"));
		key = key.replaceAll("(?<!\\\\)s", getDate(date, "S"));
		key = key.replaceAll("\\\\(S|s)", "$1");

		key = key.replaceAll("(?<!\\\\)F", getDate(date, "F"));
		key = key.replaceAll("\\\\F", "F");
		key = key.replaceAll("(?<!\\\\)E", getDate(date, "E"));
		key = key.replaceAll("\\\\E", "E");
		key = key.replaceAll("(?<!\\\\)a", getDate(date, "a"));
		key = key.replaceAll("\\\\a", "a");

		return key;
	}

	public static Date getBeforeDate(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		int day = c.get(Calendar.DATE);
		c.set(Calendar.DATE, day - 1);
		return c.getTime();
	}

	public static Date getNextDate(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		int day = c.get(Calendar.DATE);
		c.set(Calendar.DATE, day + 1);
		return c.getTime();
	}

	public static Date getBeforeDate(String date) {
		return getDate(date, -1);
	}

	public static Date getNextDate(String date) {
		return getDate(date, 1);
	}

	/**
	 * 获取指定时间的后一天数据
	 * @Title: getNextDateByDate
	 * @param date
	 * @return
	 */
	public static String getNextDateByDate(String date) {
		return getNDateByDate(date, 1);
	}

	/**
	 *  获取指定时间的前一天数据
	 * @Title: getBeforDateByDate
	 * @param date
	 * @return
	 */
	public static String getBeforDateByDate(String date) {
		return getNDateByDate(date, -1);
	}

	public static String getNDateByDate(String dateStr, int deff) {
		Calendar c = getCalendar();
		c.setTime(parseDate(dateStr));
		int day = c.get(Calendar.DATE);
		c.set(Calendar.DATE, day + deff);
		return getDate(c.getTime());
	}

	public static String getBeforeDateStr(String dateStr, int beforeDays) {
		Calendar c = getCalendar();
		c.setTime(parseDate(dateStr));
		int day = c.get(Calendar.DATE);
		c.set(Calendar.DATE, day - beforeDays);
		return getDate(c.getTime());
	}

	public static Date getDate(String dateStr, int deff) {
		Calendar c = getCalendar();
		c.setTime(parseDate(dateStr));
		int day = c.get(Calendar.DATE);
		c.set(Calendar.DATE, day + deff);
		return c.getTime();
	}

	public static String getDateTime(String dateStr, int deff) {
		Calendar c = getCalendar();
		c.setTime(parseDate(dateStr));
		int day = c.get(Calendar.DATE);
		c.set(Calendar.DATE, day + deff);
		return getDate(c.getTime(), DATA_FORMAT);
	}

	public static String getCurBefDateTime() {
		Calendar c = getCalendar();
		int day = c.get(Calendar.DATE);
		c.set(Calendar.DATE, day - 1);
		return getDate(c.getTime(), DATA_FORMAT);
	}

	public static int getHourOfDay(Date date) {
		Calendar c = getCalendar();
		c.setTime(date);
		return c.get(Calendar.HOUR_OF_DAY);
	}

	public static int getMonth(Long time) {
		Date date = new Date(time);
		Calendar c = getCalendar();
		c.setTime(date);
		int month = c.get(Calendar.MONTH) + 1;
		return month;

	}

	public static long formatDateToLong(String strDate, String formatstr) {
		if (strDate == null || "".equals(strDate.trim()) || "0".equals(strDate)) {
			return 0;
		}
		try {
			Date date = getDateFormat(formatstr).parse(strDate);
			return date.getTime() / 1000;
		} catch (Exception e) {
			return 0;
		}
	}

	public static int dayForWeek(String pTime) throws Exception {
		Calendar c = Calendar.getInstance();
		c.setTime(getDateFormat(DATA_FORMAT_DATE).parse(pTime));
		int dayForWeek = 0;
		if (c.get(Calendar.DAY_OF_WEEK) == 1) {
			dayForWeek = 7;
		} else {
			dayForWeek = c.get(Calendar.DAY_OF_WEEK) - 1;
		}
		return dayForWeek;
	}

	public static int compare_date(String DATE1, String DATE2) {
		try {
			Date dt1 = getDateFormat(DATA_FORMAT_DATE).parse(DATE1);
			Date dt2 = getDateFormat(DATA_FORMAT_DATE).parse(DATE2);
			if (dt1.getTime() > dt2.getTime()) {
				return 1;
			} else if (dt1.getTime() < dt2.getTime()) {
				return -1;
			} else {
				return 0;
			}
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return 0;
	}

	public static String getMonthBeginByMonth(String month) {
		return month + "-01";
	}

	/**
	 * 取得指定月份的最后一天
	 * 
	 * @param month 月份
	 * @return String
	 */
	public static String getMonthEndByMonth(String month) {
		Date date = parseDate(getMonthBeginByMonth(month));
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MONTH, 1);
		calendar.add(Calendar.DAY_OF_YEAR, -1);
		return getDate(calendar.getTime());
	}

	public static int getHour() {
		Calendar c = getCalendar();
		return c.get(Calendar.HOUR_OF_DAY);
	}

	/**
	 * 计算两个日期间的天数
	 * 
	 * @param fromDate 起始日期
	 * @param toDate 结束日期
	 * @return
	 * @throws ParseException
	 */
	public static int dateDiff(String fromDate, String toDate) throws ParseException {
		int days = 0;

		Date from = getDateFormat(DATA_FORMAT_DATE).parse(fromDate);
		Date to = getDateFormat(DATA_FORMAT_DATE).parse(toDate);
		days = (int) Math.abs((to.getTime() - from.getTime()) / (24 * 60 * 60 * 1000)) + 1;

		return days;
	}

	/**
	 * 取得1天前的日期
	 */
	public static String get1DayBeforDate() {
		Calendar now = Calendar.getInstance();
		now.set(Calendar.DAY_OF_MONTH, now.get(Calendar.DAY_OF_MONTH) - 1);
		return getDate(now.getTime(), DATA_FORMAT_DATE);
	}

	/**
	 * 取得1天前的日期
	 */
	public static String get1DayBeforDate(long millis) {
		Calendar now = Calendar.getInstance();
		now.setTimeInMillis(millis);
		now.set(Calendar.DAY_OF_MONTH, now.get(Calendar.DAY_OF_MONTH) - 1);
		return getDate(now.getTime(), DATA_FORMAT_DATE);
	}

	/**
	 * 取得N天前的日期
	 */
	public static String getNDayBeforDate(int n) {
		Calendar now = Calendar.getInstance();
		now.set(Calendar.DAY_OF_MONTH, now.get(Calendar.DAY_OF_MONTH) - n);
		return getDate(now.getTime(), DATA_FORMAT_DATE);
	}

	/**
	 * 取得n天后的日期
	 */
	public static Date getDayBefor(int num) {
		Calendar now = Calendar.getInstance();
		now.set(Calendar.DAY_OF_MONTH, now.get(Calendar.DAY_OF_MONTH) + num);
		return now.getTime();
	}

	/**
	 * 取得n天前或后的日期
	 */
	public static String getDayBeforOrAfterDate(int num) {
		Calendar now = Calendar.getInstance();
		now.set(Calendar.DAY_OF_MONTH, now.get(Calendar.DAY_OF_MONTH) + num);
		return getDate(now.getTime(), DATA_FORMAT_DATE);
	}

	/**
	 * 取得当前准点时间并格式化为hh：mm；ss格式
	 */
	public static String gettimeBeforOrAfter1time() {
		Calendar c = getCalendar();
		String Hour = Integer.toString(c.get(Calendar.HOUR_OF_DAY) - 1);
		if (Hour.length() == 1) {
			Hour = "0" + Hour;
		}
		Hour = Hour + ":00:00";
		return Hour;
	}

	/**
	 * 获取两个日期和其间隔的日期字符串
	 * 
	 * @param start yyyy-MM-dd
	 * @param end yyyy-MM-dd
	 * @return
	 */
	public static List<String> getDateInterval(String start, String end) {
		List<String> dateList = new ArrayList<String>();
		try {
			Date startDate = getDateFormat(DATA_FORMAT_DATE).parse(start);
			Date endDate = getDateFormat(DATA_FORMAT_DATE).parse(end);
			Calendar startCalendar = Calendar.getInstance();
			Calendar endCalendar = Calendar.getInstance();
			startCalendar.setTime(startDate);
			endCalendar.setTime(endDate);
			if (startCalendar.after(endCalendar)) {
				return dateList;
			} else {
				dateList.add(start);
				boolean flag = true;
				while (flag) {
					startCalendar.add(Calendar.DAY_OF_MONTH, 1);
					if (startCalendar.after(endCalendar)) {
						flag = false;
					} else {
						dateList.add(getDateFormat(DATA_FORMAT_DATE).format(startCalendar.getTime()));
					}
				}
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return dateList;
	}

	/**
	 * 获取某年某月的天数
	 * 
	 * @param year
	 * @param month
	 * @return
	 */
	public static int getDaysByYearMonth(int year, int month) {

		Calendar a = Calendar.getInstance();
		a.set(Calendar.YEAR, year);
		a.set(Calendar.MONTH, month - 1);
		a.set(Calendar.DATE, 1);
		a.roll(Calendar.DATE, -1);
		int maxDate = a.get(Calendar.DATE);
		return maxDate;
	}

	/**
	 * 获取多少个月以后的时间
	 * @param num
	 * @return
	 * @return: String
	 */
	public static String getTimeAfterTime(int num) {
		Calendar now = Calendar.getInstance();
		now.add(Calendar.MONTH, num);
		return getDate(now.getTime(), DATA_FORMAT);
	}

	// 获取当天的开始时间
	public static Date getDayBegin() {
		Calendar cal = new GregorianCalendar();
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}

	// 获取当天的结束时间
	public static Date getDayEnd() {
		Calendar cal = new GregorianCalendar();
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		return cal.getTime();
	}

	/**
	 * 获取精确到秒的时间戳 
	 * @param date
	 * @return
	 */
	public static int getSecondTimestampTwo(Long l) {
		if (null == l) {
			return 0;
		}
		String timestamp = String.valueOf(l / 1000);
		return Integer.valueOf(timestamp);
	}

	public static String timeSpecialHandle(Long l) {
		String str = "";
		Date fromd = new Date(l);
		Date tod = new Date();
		long diff = dateDiff(fromd, tod, DIFF_SECONDE);
		if (diff < 60 * 5) {
			str = "刚刚";
		} else if (diff < 60 * 60 && diff >= 60 * 5) {
			int hour = (int) Math.floor(diff / 60);
			str = hour + "分钟前";
		} else if (diff < 60 * 60 * 24 && diff >= 60 * 60) {
			int hour = (int) Math.floor(diff / (60 * 60));
			str = hour + "小时前";
		} else if (diff < 60 * 60 * 24 * 365) {
			str = getDate(fromd, "MM-dd HH:mm");
		} else {
			str = getDate(fromd, null);
		}
		return str;
	}

	public static String timeSpecial2(long mss) {
		String DateTimes = null;
		long days = mss / (60 * 60 * 24);
		long hours = (mss % (60 * 60 * 24)) / (60 * 60);
		long minutes = (mss % (60 * 60)) / 60;
		long seconds = mss % 60;
		if (days > 0) {
			DateTimes = days + "天" + hours + "小时" + minutes + "分钟" + seconds + "秒";
		} else if (hours > 0) {
			DateTimes = hours + "小时" + minutes + "分钟" + seconds + "秒";
		} else if (minutes > 0) {
			DateTimes = minutes + "分钟" + seconds + "秒";
		} else {
			DateTimes = seconds + "秒";
		}
		return DateTimes;
	}

	/**
	 * 根据秒获取date
	 */
	public static Date getDateSeconds(int secondes) {
		long mss = secondes * 1000L;
		return getDateByLong(mss);
	}

	public static String getHoursSeconds(long mss) throws Exception {
		Date date = new Date(mss);
		return getDateFormat(DATA_FORMAT_MM_SS).format(date);
	}

	public static String getMonthDay(int num) {
		Calendar now = Calendar.getInstance();
		now.set(Calendar.DAY_OF_MONTH, now.get(Calendar.DAY_OF_MONTH) + num);
		return getDate(now.getTime(), DATA_FORMAT_MM_DD);
	}

	public static Date getDate(String date, String format) throws Exception {
		return getDateFormat(format).parse(date);
	}

	public static String getDateStr(int secondes, String sdf)  {
		Date date = getDateSeconds(secondes);
		return getDate(date, sdf);
	}

	public static String getDateMilliSecondStr(long millisecond, String sdf) {
		Calendar tem = Calendar.getInstance();
		tem.setTimeInMillis(millisecond);
		return formatRandomDate(tem.getTime(), sdf);
	}

	/**
	 * 处理时间
	 *
	 * @param time   时间基数，如果为null，则默认当前时间
	 * @param field  同{@link Calendar}的field
	 * @param amount 大于0则加，小于0则减
	 */
	public static Date add(Date time, int field, int amount) {
		if (amount == 0)
			return time;

		Calendar cal = Calendar.getInstance();
		if (time != null) {
			cal.setTime(time);
		}
		cal.add(field, amount);
		return cal.getTime();
	}

	public static Date[] getWeekDay() {
		Calendar calendar = Calendar.getInstance();
		while (calendar.get(Calendar.DAY_OF_WEEK) != Calendar.MONDAY) {
			calendar.add(Calendar.DAY_OF_WEEK, -1);
		}
		Date[] dates = new Date[7];
		for (int i = 0; i < 7; i++) {
			dates[i] = calendar.getTime();
			calendar.add(Calendar.DAY_OF_WEEK, 1);
		}
		return dates;
	}

	public static List<String> getCurrentWeekDays() {
		Date date = DateUtil.getCurDate();
		return getWeekDays(date);
	}

	public static List<String> getWeekDays(Date date) {
		String weekBegin = getWeekBegin(DateUtil.formatRandomDate(date, DateUtil.DATA_FORMAT_DATE));
		String weekEnd = getWeekEnd(DateUtil.formatRandomDate(date, DateUtil.DATA_FORMAT_DATE));
		return getBetweenkDays(weekBegin, weekEnd);
	}

	public static List<String> getBetweenkDays(String startDate, String endDate) {
		Date begin = DateUtil.parseDate(startDate, DateUtil.DATA_FORMAT_DATE);
		Date end = DateUtil.parseDate(endDate, DateUtil.DATA_FORMAT_DATE);
		return getBetweenkDays(begin, end);
	}

	public static List<String> getBetweenkDays(Date startDate, Date endDate) {
		String startBegin = DateUtil.formatRandomDate(startDate, DateUtil.DATA_FORMAT_DATE);
		String end = DateUtil.formatRandomDate(endDate, DateUtil.DATA_FORMAT_DATE);
		List<String> dateStrs = Lists.newArrayList();
		dateStrs.add(startBegin);
		String nextDate = startBegin;
		do {
			nextDate = getNextDateByDate(nextDate);
			dateStrs.add(nextDate);
		} while (!StringUtils.equalsIgnoreCase(nextDate, end));
		return dateStrs;
	}

	/**
	 * 获取到当天结束还有多少毫秒
	 * @return
	 */
	public static Long getEndTime() {
		Calendar todayEnd = Calendar.getInstance();
		todayEnd.set(Calendar.HOUR_OF_DAY, 23); // Calendar.HOUR 12小时制
		// HOUR_OF_DAY 24小时制
		todayEnd.set(Calendar.MINUTE, 59);
		todayEnd.set(Calendar.SECOND, 59);
		todayEnd.set(Calendar.MILLISECOND, 999);
		return todayEnd.getTimeInMillis();
	}

	/**
	 * 获取时间差异日期
	 * @param date
	 * @param type
	 * @param deef
	 * @return
	 */
	public static String getCurDateBefore(Date date,int type,int deef){
		Calendar c = Calendar.getInstance();
		c.setTime(DateUtil.getCurDate());
		c.add(type, deef);
		return getDate(c.getTime(),DATA_FORMAT);
	}
    /**
     * 转换 MM/dd/yyyy 格式的字符串为标准时间格式
     *
     * @param date
     * @return
     * @throws Exception
     */
    public static String dateFormat(String date) throws Exception {
        SimpleDateFormat formatDate = new SimpleDateFormat(DateUtil.DATA_FORMAT_DD);
        SimpleDateFormat formatResult = new SimpleDateFormat(DateUtil.DATA_FORMAT);
        Date date1 = formatDate.parse(date);
        return formatResult.format(date1);
    }

    public static Date getDayBeginDiff(int deff){
		Calendar cal = new GregorianCalendar();
		int day = cal.get(Calendar.DATE);
		cal.set(Calendar.DATE, day + deff);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}

	public static Date getDayEndDiff(int deff){
		Calendar cal = new GregorianCalendar();
		int day = cal.get(Calendar.DATE);
		cal.set(Calendar.DATE, day + deff);
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		return cal.getTime();
	}

	public static Date getDateforSecond(int second){
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(second * 1000l);//转换为毫秒
		return calendar.getTime();
	}
}