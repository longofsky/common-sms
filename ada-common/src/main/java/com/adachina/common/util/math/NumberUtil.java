package com.adachina.common.util.math;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Description
 * @Author litianlong
 * @Date 2019-03-22 11:40
 */
public class NumberUtil {
	protected static final Logger logger = LoggerFactory.getLogger(NumberUtil.class);


	private NumberUtil(){
		throw new IllegalStateException("NumberUtil Utility class");
	}

	private static Pattern pattern = Pattern.compile("\\d+");
	private static int lastBusinessNumSize = 14;

	/**
	 * 生成一个随机的x位数，纯数字。
	 * @param x
	 * @return
	 */
	public  static String creatRandomNumber(Integer x){
		Random random = new Random();
		StringBuilder sb = new StringBuilder(x);
		for (int i = 0; i < x; i++) {
			Integer xxx= random.nextInt(10);
			sb.append(xxx);
		}
		return sb.toString();
	}

	/**
	 * 生成一个随机的X位的字符串，包含数据字母。
	 * @param x
	 * @return
	 */
	public static String creatRandomString(Integer x){

		String base = "abcdefghijklmnopqrstuvwxyz0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";

		Random random = new Random();
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < x; i++) {
			int number = random.nextInt(base.length());
			sb.append(base.charAt(number));
		}
		return sb.toString();
	}

	/**
	 * 根据时间生成时间戳字符串
	 * @Title: createStrNumByTime
	 * @param date
	 * @return
	 * @author shixinxin
	 * @return String    时间戳字符串
	 * @date 2015年6月22日 下午2:50:17
	 */
	public static String createStrNumByTime(Date date){
		//当前时间，精度为秒。
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		String dateStr = sdf.format(date);
		return dateStr;
	}

	/**
	 * 根据上一个业务编号生成当前业务编号
	 * @Title: createBusinessNumByLastNum
	 * @param lastBusinessNum
	 * @return
	 * @throws Exception
	 * @author shixinxin
	 * @return String    生成的业务编号
	 * @date 2015年6月22日 下午3:19:24
	 */
	public static String createBusinessNumByLastNum(String lastBusinessNum,Date now){
		StringBuilder nowSbfNum = new StringBuilder();
		String frontNum = "";
		String randomStr = "";
		String nowLastFourNumStr = "";
		if (lastBusinessNum != null && !"".equals(lastBusinessNum))
		{
			StringBuilder sbfNum = new StringBuilder(lastBusinessNum);
			String lastFourNum = sbfNum.substring(sbfNum.length()-4, sbfNum.length());
			if(sbfNum.length() > lastBusinessNumSize){
				frontNum = sbfNum.substring(0, lastBusinessNumSize);
			}else{
				frontNum = sbfNum.substring(0, sbfNum.length()-8);
			}
			//在此基础上加1
			Integer nowLastFourNum = Integer.parseInt(lastFourNum) + 1;
			nowLastFourNumStr = nowLastFourNum.toString();
		}else
		{
			frontNum = createStrNumByTime(now);
			nowLastFourNumStr = "0001";
		}
		randomStr = creatRandomNumber(4);
		nowSbfNum.append(frontNum).append(randomStr).append(nowLastFourNumStr);
		return nowSbfNum.toString();
	}

	/**
	 * 将数字转化为大写
	 * @param num 数字
	 * @return 数字大写
	 * @author liuyan
	 * @date 2017年12月18日
	 */
	public static String numToUpper(int num) {
		String[] u = { "〇", "一", "二", "三", "四", "五", "六", "七", "八", "九" };
		char[] str = String.valueOf(num).toCharArray();
		String rstr = "";
		for (int i = 0; i < str.length; i++) {
			rstr = rstr + u[Integer.parseInt(str[i] + "")];
		}
		return rstr;
	}

	/**
	 * 左边补零
	 * @author 雷传盛
	 * @param s
	 * @return
	 */
	public static String leftPadZero(String s) {
		int standarLen = 10;
		if(null == s) {
			s = "0000000000";
		}
		StringBuilder stringBuilder = new StringBuilder(s);
		if(s.length() < standarLen) {
			int len = standarLen-s.length();
			for(int i=0;i<len;i++) {
				stringBuilder.append("0").append(s);
			}
		}
		return stringBuilder.toString();
	}
	/**
	 * @Description: double转BigDecimal
	 * @param @param s  doubleg型数值
	 * @param @param precision 精度
	 * @param @return
	 * @return BigDecimal
	 * @throws
	 * @author zhouyun
	 * @date 2015年12月25日下午7:50:55
	 */
	public static BigDecimal doubleToBigDecimal(Double s,int precision) {
		double precisionValue=Math.pow(10, precision);
		BigDecimal amount = BigDecimal.valueOf(Math.round(s*precisionValue)).divide(BigDecimal.valueOf(precisionValue));
		return amount;
	}



	/**
	 * @brief 这是一个生成编号给用户展示方法
	 * @details 创建一个编号显示给用户看  时间(14)+主流水号（10）+业务类型（8）+校验位（2）
	 * @param createTime 创建时间
	 * @param doneCode   业务流水号
	 * @param bizType    业务类型
	 * @return String
	 * @retval 流水号
	 * @author 冯银鹏
	 * @date 2016年3月11日下午2:37:10
	 * @note 变更：创建月日时分秒（12位，去掉2位年）+流水编号（10位前补零）+校验位（2位，前面各位之和对78取余数）
	 */
	public static String createShowNum(Date createTime,Integer doneCode,String bizType){
		logger.info("createShowNum "+createTime + " "+doneCode+" "+bizType);
		StringBuilder reStr = new StringBuilder();

		SimpleDateFormat simpleDateFormat  = new SimpleDateFormat("yyMMddHHmmss");
		String dateStr = simpleDateFormat.format(createTime);
		reStr.append(dateStr);

		String doneCodeStr = String.format("%010d", doneCode);
		reStr.append(doneCodeStr);

		Integer sum = 0;
		for (int i = 0; i < reStr.length(); i++) {
			sum += Integer.parseInt(reStr.charAt(i)+"");
		}

		String endStr = sum%78+"";

		reStr.append(endStr);

		return reStr.toString();
	}

	private static final char[] BASE52_DICT = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz".toCharArray();
	private static final int[] BASE52_REVDICT = getReverseDict(BASE52_DICT);
	private static final char[] BASE36_DICT = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
	private static final int[] BASE36_REVDICT = getReverseDict(BASE36_DICT);

	public static String toBase52(long n) {
		return toBaseM(n, BASE52_DICT);
	}

	public static String toBase36(long n) {
		return toBaseM(n, BASE36_DICT);
	}

	public static long fromBase52(String s) {
		return fromBaseM(s, BASE52_DICT, BASE52_REVDICT);
	}

	public static long fromBase36(String s) {
		return fromBaseM(s, BASE36_DICT, BASE36_REVDICT);
	}

	private static int[] getReverseDict(char[] dict){
		int[] rdict = new int[128];
		Arrays.fill(rdict, -1);
		for(int i=0; i < dict.length; i++){
			char c = dict[i];
			if(c > 128){
				throw new IllegalArgumentException("字典表字符必须为ascii字符。dict[" + i + "]=" + Character.toString(c));
			}
			rdict[c] = i;
		}
		return rdict;
	}

	private static String toBaseM(long n, char[] dict) {
		StringBuilder s = new StringBuilder();
		if(n < 0){
			throw new IllegalArgumentException("待转换数字必须大于等于0. n=" + n);
		}
		if(n == 0){
			return Character.toString(dict[0]);
		}
		while (n > 0) {
			long m = n % dict.length;
			if (0 <= m && m < dict.length) {
				s.insert(0, dict[(int) m]);
			}else {
				s.insert(0, '?');
			}
			n = (n - m) / dict.length;
		}
		return s.toString();
	}

	private static long fromBaseM(String s, char[] dict, int[] reverseDict) {
		if (s == null) {
			return 0;
		}
		long n = 0;
		int base = dict.length;
		for (int i = s.length() - 1, j = 1; i >= 0; i--, j *= base) {
			char c = s.charAt(i);
			if(c > 128 || reverseDict[c] == -1){
				throw new IllegalArgumentException("待解析字符非法。s[" + i + "]=" + Character.toString(c));
			}
			n += reverseDict[c] * j;
		}
		return n;
	}

	public static String getNumbers(String content) {
		if(StringUtils.isBlank(content)){
			return "";
		}
		Matcher matcher = pattern.matcher(content);
		if (matcher.find()) {
			return matcher.group(0);
		}
		return "";
	}

}
