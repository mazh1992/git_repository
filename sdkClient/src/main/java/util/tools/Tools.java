package util.tools;

import com.netfinworks.common.lang.StringUtil;
import com.netfinworks.common.util.money.Money;
import com.netfinworks.mag.constant.ReqConstant;
import com.netfinworks.mag.domain.PayMethod;
import com.netfinworks.mag.enums.PayChannelKind;
import com.netfinworks.mag.exception.CommonDefinedException;
import com.netfinworks.mag.exception.ErrorCodeException.CommonException;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * <p>
 * 通用工具类
 * </p>
 *
 * @author leelun
 * @version $Id: Tools.java, v 0.1 2013-12-4 下午7:33:59 lilun Exp $
 */
public class Tools {

	public static final String SEP = "\\^";
	public static final String COMMA = ",";
	public static final String VERTICAL = "\\|";
	public static final String COLON = ":";
    public static final String MINUS   = "-";

    private static String      separ_d  = "|";
	private static String      separ_e  = "^";
	/**
	 * list转String，用","分隔
	 *
	 * @param stringList
	 * @return
	 */
	public static String list2String(List<String> stringList) {
		if (stringList == null) {
			return null;
		}
		StringBuilder result = new StringBuilder();
		boolean flag = false;
		for (String string : stringList) {

			if (flag) {
				result.append(COMMA);
			} else {
				flag = true;
			}
			result.append(string);
		}
		return result.toString();
	}

	/**
	 * list转String，用"^"分隔
	 *
	 * @param stringList
	 * @return
	 */
	public static String list2StringBySep(List<String> stringList) {
		if (stringList == null) {
			return null;
		}
		StringBuilder result = new StringBuilder();
		boolean flag = false;
		for (String string : stringList) {

			if (flag) {
				result.append(SEP);
			} else {
				flag = true;
			}
			result.append(string);
		}
		return result.toString();
	}

	public static List<String> str2List(String listText) {
		if (listText == null || listText.equals("")) {
			return null;
		}
		List<String> list = new ArrayList<String>();
		String[] text = listText.split(SEP);
		for (String str : text) {
			list.add(str);
		}
		return list;
	}

	public static String[] str2Array(String listText) {
		if (listText == null || listText.equals("")) {
			return null;
		}
		String[] text = listText.split(COMMA);

		return text;
	}

	/**
	 * 返回当天结束时间，用于默认结束时间为空的地方
	 *
	 * @return
	 */
	public static Date endDayTime() {
		Calendar todayEnd = Calendar.getInstance();
		todayEnd.set(Calendar.HOUR_OF_DAY, 23);
		todayEnd.set(Calendar.MINUTE, 59);
		todayEnd.set(Calendar.SECOND, 59);
		todayEnd.set(Calendar.MILLISECOND, 999);
		return todayEnd.getTime();
	}

	/**
	 * 返回当天开始时间，作为开始时间为空时的默认值
	 *
	 * @return 当天的开始时间
	 */
	public static Date startDayTime() {
		Calendar todayStart = Calendar.getInstance();
		todayStart.set(Calendar.HOUR_OF_DAY,0);
		todayStart.set(Calendar.MINUTE,0);
		todayStart.set(Calendar.SECOND,0);
		todayStart.set(Calendar.MILLISECOND,0);
		return todayStart.getTime();
	}

	/**
	 * 返回昨天开始时间
	 *
	 * @return 昨天的开始时间
	 */
	public static Date yesterdayStartTime() {
		Calendar yesterdayStart = Calendar.getInstance();
		yesterdayStart.add(Calendar.DATE, -1);
		yesterdayStart.set(Calendar.HOUR_OF_DAY,0);
		yesterdayStart.set(Calendar.MINUTE,0);
		yesterdayStart.set(Calendar.SECOND,0);
		yesterdayStart.set(Calendar.MILLISECOND,0);
		return yesterdayStart.getTime();
	}

	/**
	 * 返回昨天结束时间
	 *
	 * @return 昨天的结束时间
	 */
	public static Date yesterdayEndTime() {
        Calendar yesterdayEnd = Calendar.getInstance();
        yesterdayEnd.add(Calendar.DATE, -1);
        yesterdayEnd.set(Calendar.HOUR_OF_DAY, 23);
        yesterdayEnd.set(Calendar.MINUTE, 59);
        yesterdayEnd.set(Calendar.SECOND, 59);
        yesterdayEnd.set(Calendar.MILLISECOND, 999);
		return yesterdayEnd.getTime();
	}

	public static List<PayMethod> str2PayMethodList(String payMethodStr)
			throws CommonException {
		List<PayMethod> result = null;
		if (StringUtil.isEmpty(payMethodStr)) {
			return result;
		}else{
		    result=new ArrayList<PayMethod>();
		}
		str2PayMethodListRecursion(AuniXSS.decode(payMethodStr),result);
		return result;
	}
	private static void str2PayMethodListRecursion(String payMethodListStr,List<PayMethod> payMethodInfoList) throws CommonException{
        str2PayMethodRecursion(payMethodListStr,new PayMethod(),0,payMethodInfoList);
    }
    
    private static void str2PayMethodRecursion(String payMethodListStr,PayMethod payMethod, int dataSeq,List<PayMethod> payMethodInfoList) throws CommonException{
        int dataSplit = -1;       
        //数据长度
        Integer dataLenth=0;
        //支付数据属性
        String payMethodE="";
        //属性不为空
        if(!(payMethodListStr.startsWith(separ_e)||payMethodListStr.startsWith(separ_d)||StringUtil.isEmpty(payMethodListStr))){ 
            try{
                dataSplit=payMethodListStr.indexOf(Tools.COLON);
                if(dataSplit==-1){
                    CommonException exp = CommonDefinedException.ILLEGAL_ARGUMENT;
                    exp.setMemo(payMethodListStr + "解析出错");
                    throw exp;
                }
                dataLenth= Integer.valueOf(payMethodListStr.substring(0, dataSplit));
                payMethodE=payMethodListStr.substring(dataSplit + 1, dataSplit + 1 + dataLenth);   
            }catch(NumberFormatException e){
                CommonException exp = CommonDefinedException.ILLEGAL_ARGUMENT;
                exp.setMemo(payMethodListStr + "解析出错");
                throw exp;
            }catch(StringIndexOutOfBoundsException e){
                CommonException exp = CommonDefinedException.ILLEGAL_ARGUMENT;
                exp.setMemo(payMethodListStr + "解析出错");
                throw exp;
            }   
        }
        fillPayMethod(payMethod, dataSeq, payMethodE);
        
        //对后面字符串进行处理
        if(payMethodListStr.length()>dataSplit + 1 + dataLenth){
            String nextStr=payMethodListStr.substring(dataSplit + 1 + dataLenth);
           //支付的下一个属性
           if(nextStr.startsWith(separ_e)){
               str2PayMethodRecursion(nextStr.substring(1),payMethod,++dataSeq,payMethodInfoList);  
           }
           //另外一笔支付开始
           else if(nextStr.startsWith(separ_d)){
               payMethodInfoList.add(payMethod);
               str2PayMethodListRecursion(nextStr.substring(1),payMethodInfoList);
           }else{
               CommonException exp = CommonDefinedException.ILLEGAL_ARGUMENT;
               exp.setMemo(nextStr + "解析出错");
               throw exp;
           }
        }else{
            payMethodInfoList.add(payMethod); 
        }
    }
    private static void fillPayMethod(PayMethod pay, int i, String payE) throws CommonException {
        payE=AuniXSS.encode(payE);
        if (i == 0) {
        	PayChannelKind payChannel = PayChannelKind.getByMsg(payE);
        	if (payChannel == null) {
        		throw CommonDefinedException.PAY_METHOD_ERROR;
        	}
        	pay.setPayChannel(payChannel);
        }
        if (i == 1) {
        	pay.setAmount(payE);
        }
        if (i == 2) {
        	pay.setMemo(payE);
        }
    }

	public static Money convertMoney(String moneyStr) throws CommonException {
		Money result = new Money();
		if (StringUtil.isEmpty(moneyStr)) {
			return result;
		} else {
			try {
				result = new Money(moneyStr);
			} catch (NumberFormatException e) {
				// 金额格式错误，有字母等
				throw CommonDefinedException.ILLEGAL_AMOUNT_FORMAT;
			}
			return result;
		}
	}

	public static Date convertDate(String dateStr) {
		Date result = new Date();
		if (StringUtil.isNotEmpty(dateStr)) {
			try {
				DateFormat dateFormat = new SimpleDateFormat(
						ReqConstant.DEFAULT_DATE_FORMATE);
				result = dateFormat.parse(dateStr);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}

		return result;
	}

	/**
	 * 将指定的时间间隔，转为具体时间 m-分钟，h-小时，d-天
	 *
	 * @param expiredDateStr
	 * @return
	 */
	public static Date convertExpiredDate(String expiredDateStr)
			throws CommonException {
		Date result = new Date();
		if (StringUtil.isNotEmpty(expiredDateStr)) {
		    if(expiredDateStr.length() == 1){
		        CommonException exp = CommonDefinedException.ILLEGAL_DATE_FORMAT;
	            exp.setMemo("过期时间格式错误");
	            throw exp;
		    }
            String data=expiredDateStr.substring(0,expiredDateStr.length() - 1);
			String unit = expiredDateStr.substring(expiredDateStr.length() - 1);
			if(!data.matches("\\d+")){
			    CommonException exp = CommonDefinedException.ILLEGAL_DATE_FORMAT;
                exp.setMemo("过期时间格式错误");
                throw exp; 
			}
			int length = Integer.valueOf(data);
			UnitTimeKind timeEnum = UnitTimeKind.getByCode(unit);
			if(timeEnum==null){
			    CommonException exp = CommonDefinedException.ILLEGAL_DATE_FORMAT;
                exp.setMemo("过期时间格式错误");
                throw exp;
			}
			Calendar cal = Calendar.getInstance();
			switch (timeEnum) {
			case MINUTE:
				cal.add(Calendar.MINUTE, length);
				break;
			case HOUR:
				cal.add(Calendar.HOUR, length);
				break;
			case DAY:
				cal.add(Calendar.DAY_OF_YEAR, length);
				break;
			default:
			    CommonException exp = CommonDefinedException.ILLEGAL_DATE_FORMAT;
                exp.setMemo("过期时间格式错误");
                throw exp;
			}
			result = cal.getTime();
		} else {
			result = null;
		}

		return result;
	}

	public static boolean isDateTime(String s) {
		try {
			DateFormat dateFormat = new SimpleDateFormat(
					ReqConstant.DEFAULT_DATE_FORMATE);
			dateFormat.parse(s);
		} catch (Exception ex) {
			return false;
		}
		return true;
	}

	// 支持0-2位小数数字,大于0
	public static boolean isNumberNoZero(String str) {
		String regex = "^(?!0(\\d|\\.0+$|$))\\d+(\\.\\d{1,2})?$"; // 大于0
		Pattern pattern = Pattern.compile(regex);
		Matcher match = pattern.matcher(str);
		// match.find()
		return match.matches();
	}

	// 支持0-2位小数数字
	public static boolean isNumber(String str) {
		String regex = "^([0-9]+(.[0-9]{1,2})?)|([0-9]+(.[0-9]{1,2})?)$"; // 包括0，0.00
		Pattern pattern = Pattern.compile(regex);
		Matcher match = pattern.matcher(str);
		// match.find()
		return match.matches();
	}

	// 正整数，不包括0
	public static boolean isInt(String str) {
		String regex = "^[0-9]*[1-9][0-9]*$"; //
		Pattern pattern = Pattern.compile(regex);
		Matcher match = pattern.matcher(str);
		// match.find()
		return match.matches();
	}

	// 将长度:内容的字符串解析出内容
	public static String getContent(String str, String split)
			throws CommonException {
		String result = null;
		if (StringUtil.isEmpty(str)) {
			return result;
		}
		try {
			int spt = str.indexOf(split);
			Integer lenth = Integer.valueOf(str.substring(0, spt));
			result = str.substring(spt + 1, spt + 1 + lenth);
		} catch (StringIndexOutOfBoundsException e) {
			// 金额格式错误，有字母等
			CommonException exp = CommonDefinedException.ILLEGAL_ARGUMENT;
			exp.setMemo(str + "解析出错");
			throw exp;
		}
		return result;
	}

	/**
	 * 判断空字符串
	 * @param str
	 * @return
	 */
	public static boolean isBlank(String str) {
		int strLen;
		if (str == null || (strLen = str.length()) == 0) {
			return true;
		}
		for (int i = 0; i < strLen; i++) {
			if ((Character.isWhitespace(str.charAt(i)) == false)) {
				return false;
			}
		}
		return true;
	}
}
