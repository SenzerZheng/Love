package com.senzer.mylove.util;

import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.util.Base64;

import com.senzer.mylove.logger.SpiderLogger;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * ProjectName: StringUtil
 * Description: 字符串处理类
 *
 * review by chenpan, wangkang, wangdong 2017/5/2
 * edit by JeyZheng 2017/5/2
 * author: JeyZheng
 * version: 4.0
 * created at: 2017/5/2 18:16
 */
@SuppressWarnings("ALL")
public class StringUtil {

    public static final String TAG = "StringUtil";

    public static final String MONEY_MARK = "¥";
    public static final String MONEY_RMB = "元";
    public static final String BOOK_MARK = "本";
    public static final String PERCENT_MARK = "%";
    public static final String ELLIPSIS_MARK = "...";

    public static final String ZERO = "0";
    public static final String PRICE_ZERO = "0.00";
    public static final String TIME_ZERO = "00:00";

    public static DecimalFormat decimalformat = new DecimalFormat(PRICE_ZERO);

    /**
     * 获取价格字符串
     *
     * @param price
     * @return
     */
    public static String getFormatPrice(double price) {
        String formatPrice = decimalformat.format(price);
        return formatPrice;
    }

    public static String getFormatPrice(String priceString) {
        float price = getFloat(priceString);

        return getFormatPrice(price);
    }

    /**
     * 添加钱的标识（¥）
     *
     * @param money
     * @return
     */
    public static String appendMoneyMark(String money) {
        if (checkEmpty(money)) {
            return "¥0.00";
        }
        return MONEY_MARK + money;
    }

    /**
     * 获取RMB（例如：3333.33元）
     *
     * @param money
     * @return
     */
    public static String getStrMoney(double money) {
        StringBuffer sb = new StringBuffer();
        sb.append(String.format("%.2f", money));
        sb.append(MONEY_RMB);
        return sb.toString();
    }

    /**
     * 获取RMB（例如：3333.33元）
     *
     * @param money
     * @return
     */
    public static String getStrMoney(String money) {
        StringBuffer sb = new StringBuffer();
        sb.append(String.format("%.2f", money));
        sb.append(MONEY_RMB);
        return sb.toString();
    }

    /**
     * 转换金额格式（例如：3,033.00）
     */
    public static String getStrFormatMoney(String moneyNum) {
        DecimalFormat df = new DecimalFormat(PRICE_ZERO);
        moneyNum = df.format(Double.valueOf(moneyNum));
        String[] str = moneyNum.split("\\.");
        NumberFormat usFormat = NumberFormat.getIntegerInstance(Locale.US);
        moneyNum = usFormat.format(Double.valueOf(str[0]));
        return moneyNum + "." + str[1];
    }

    /**
     * 转换金额格式（例如：3,033.00）
     *
     * @param moneyNum
     * @return
     */
    public static String getStrFormatMoney(double moneyNum) {
        String number = String.valueOf(moneyNum);
        DecimalFormat df = new DecimalFormat(PRICE_ZERO);
        number = df.format(Double.valueOf(number));
        String[] str = number.split("\\.");
        NumberFormat usFormat = NumberFormat.getIntegerInstance(Locale.US);
        number = usFormat.format(Double.valueOf(str[0]));
        return number + "." + str[1];
    }

    /**
     * 获取书的本数
     *
     * @param books
     * @return
     */
    public static String getStrBooks(int books) {
        StringBuffer sb = new StringBuffer();
        sb.append(books);
        sb.append(BOOK_MARK);
        return sb.toString();
    }

    /**
     * 获取浮点数
     *
     * @param string
     * @return
     */
    public static float getFloat(String string) {
        float result = 0;
        try {
            result = Float.valueOf(string);
        } catch (Exception e) {
            SpiderLogger.getLogger().e("getFloat", e.getMessage());
        }
        return result;
    }

    /**
     * 获取String
     *
     * @param d
     * @return
     */
    public static String getString(double d) {
        String result = ZERO;
        try {
            result = String.valueOf(d);
        } catch (Exception e) {
            SpiderLogger.getLogger().e("getString", e.getMessage());
        }
        return result;
    }


    /**
     * 获取最短的价格字符串
     *
     * @param priceString
     * @return
     */
    public static String getShortestPriceString(String priceString) {
        StringBuilder decimalStringBuilder = new StringBuilder(priceString);
        int pos = decimalStringBuilder.indexOf(".");
        if (pos != -1) {
            int stringLength = decimalStringBuilder.length();
            while ('0' == decimalStringBuilder.charAt(stringLength - 1)
                    && stringLength >= pos + 1) {
                decimalStringBuilder.deleteCharAt(stringLength - 1);
                stringLength = decimalStringBuilder.length();
            }
            if (pos == decimalStringBuilder.length() - 1) {
                decimalStringBuilder.deleteCharAt(pos);
            }
        }
        return decimalStringBuilder.toString();
    }


    /**
     * 验证手机号
     *
     * @param mobiles
     * @return
     */
    public static boolean isMobile(String mobiles) {
        // NOTE:
        // ^: 匹配输入字符串开始的位置。如果设置了 RegExp 对象的 Multiline 属性，^ 还会与 \n 或 \r 之后的位置匹配。
        // $: 匹配输入字符串结尾的位置。如果设置了 RegExp 对象的 Multiline 属性，$ 还会与 \n 或 \r 之前的位置匹配。
        boolean valid = false;
        if (!checkEmpty(mobiles)) {
            Pattern p = Pattern
                    .compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");

            Matcher m = p.matcher(mobiles);
            valid = m.matches();
        }

        return valid;

    }


    /**
     * 验证手机号
     *
     * @param str
     * @return
     */
    public static boolean isNumber(String str) {
        Pattern p = null;
        Matcher m = null;
        boolean b = false;
        p = Pattern.compile("^[1][3,4,5,7,8][0-9]{9}$");
//        p = Pattern.compile("^[1][0-9]{10}$");
        m = p.matcher(str);
        b = m.matches();
        return b;
    }

    /**
     * 验证邮箱
     *
     * @param email
     * @return
     */
    public static boolean isEmail(String email) {
        Pattern p = null;
        Matcher m = null;
        boolean b = false;
        p = Pattern.compile("^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$");
        m = p.matcher(email);
        b = m.matches();
        return b;
    }


    /**
     * 验证邮政编码
     *
     * @param post
     * @return
     */
    public static boolean checkPost(String post) {
        if (!checkEmpty(post) && post.matches("[1-9]\\d{5}(?!\\d)")) {
            return true;
        } else {
            return false;
        }
    }


    /**
     * 将字符串转换为整形
     *
     * @param string
     * @param defaultValue 当出错时，返回的默认值
     * @return
     */
    public static int intValueOf(String string, int defaultValue) {

        int value = defaultValue;

        try {
            value = Integer.valueOf(string);
        } catch (Exception e) {
            SpiderLogger.getLogger().d(TAG, e.getMessage() + "");
        }

        return value;
    }


    /**
     * 获取整数
     *
     * @param intString
     * @return
     */
    public static int getInt(String intString) {
        int result = 0;
        try {
            result = Integer.valueOf(intString);
        } catch (Exception e) {
            SpiderLogger.getLogger().e("getInt", e.getMessage());
        }
        return result;
    }


    /**
     * 获取格式化的价格
     *
     * @param price
     * @return
     */
    public static SpannableStringBuilder getFormatPriceSpannable(double price) {
        String priceStr = MONEY_MARK + " " + getFormatPrice(price);
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(priceStr);
        spannableStringBuilder.setSpan(new AbsoluteSizeSpan(12, true), 0, 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        int decimalIndex = priceStr.indexOf(".");
        spannableStringBuilder.setSpan(new AbsoluteSizeSpan(18, true), 2, decimalIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableStringBuilder.setSpan(new AbsoluteSizeSpan(12, true), decimalIndex, priceStr.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spannableStringBuilder;
    }

    /**
     * 获取格式化的价格
     *
     * @param price
     * @return
     */
    public static SpannableStringBuilder getFormatPriceSpannable(String price) {
        String priceStr = MONEY_MARK + " " + getFormatPrice(price);
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(priceStr);
        spannableStringBuilder.setSpan(new AbsoluteSizeSpan(12, true), 0, 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        int decimalIndex = priceStr.indexOf(".");
        spannableStringBuilder.setSpan(new AbsoluteSizeSpan(18, true), 2, decimalIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableStringBuilder.setSpan(new AbsoluteSizeSpan(12, true), decimalIndex, priceStr.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spannableStringBuilder;
    }

    /**
     * 判断是否是url
     *
     * @param url
     * @return
     */
    public static boolean isUrl(String url) {
        return !checkEmpty(url) && (url.startsWith("http://") || url.startsWith("https://") || url.startsWith("www"));
    }

    /**
     * @param url
     * @return
     */
    public static String getHttpUrl(String url) {
        if (isUrl(url) || checkEmpty(url)) {
            return url;
        } else {
            return "http://" + url;
        }
    }

    /**
     * 检查String是否为空
     *
     * @param s
     * @return
     */
    public static boolean checkEmpty(String s) {
        boolean isEmpty = true;

        if (null != s && !"".equals(s) && !"null".equals(s)) {
            isEmpty = false;
        }
        return isEmpty;
    }

    /**
     * 检查String是否为空，如果为""或null，则返回""；如果不为""或null，则返回s
     *
     * @param string
     * @return
     */
    public static String formatString(String string) {
        if (checkEmpty(string)) {
            return "";
        } else {
            return string;
        }
    }

    /**
     * 检查String是否为空，如果为""或null，则返回"00:00"；如果不为""或null，则返回s
     *
     * @param string
     * @return
     */
    public static String formatTimeStr(String string) {
        if (checkEmpty(string)) {
            return TIME_ZERO;
        } else {
            return string;
        }
    }

    /**
     * 检查String是否为空，如果为""或null，则返回""；如果不为""或null，则返回s
     *
     * @param s
     * @return
     */
    public static String getEmpty(String s) {
        if (checkEmpty(s)) {
            s = "";
        }

        return s;
    }

    /**
     * 检查String是否为空，如果为""或null，则返回"0"；如果不为""或null，则返回s
     *
     * @param s
     * @return
     */
    public static String getIntEmpty(String s) {
        if (checkEmpty(s)) {
            s = ZERO;
        }

        return s;
    }

    /**
     * 检查String是否为空，如果为""或null，则返回"0.00"；如果不为""或null，则返回s
     *
     * @param s
     * @return
     */
    public static String getIntPriceEmpty(String s) {
        if (checkEmpty(s)) {
            s = PRICE_ZERO;
        }

        return s;
    }

    /**
     * 判断某个数组格式的字符串，是否包含某个字符
     *
     * @param strs
     * @param part
     * @param regex 分隔符
     * @return
     */
    public static boolean contains(String strs, String part, String regex) {
        boolean isContains = false;

        String[] arrs = strs.split(regex);
        for (String str : arrs) {
            if (str.equals(part)) {
                isContains = true;

                break;
            }
        }

        return isContains;
    }

    /**
     * 从数组格式的字符串中，添加某个字符串
     *
     * @param strs
     * @param part
     * @param regex        正则
     * @param rawSplitChar 分隔符
     * @return
     */
    public static String addStr(
            String strs, String part, String regex, String rawSplitChar) {
        if (checkEmpty(strs)) {          // empty
            return part;
        }

        // exist, there is no such situation.
//        String[] arrs = strs.split(regex);
//        int len = arrs.length;
//        for (int i = 0; i < len; i++) {
//            String str = arrs[i];
//            if (str.equals(part)) {
//
//                return strs;
//            }
//        }

        // do no exist
        StringBuffer sb = new StringBuffer();
        sb.append(strs).append(rawSplitChar).append(part);
        return sb.toString();
    }

    /**
     * 从数组格式的字符串中，移除某个字符串
     *
     * @param strs
     * @param part
     * @param regex        正则
     * @param rawSplitChar 分隔符
     * @return
     */
    public static String removeStr(
            String strs, String part, String regex, String rawSplitChar) {

        StringBuffer sb = new StringBuffer();
        String[] arrs = strs.split(regex);
        int len = arrs.length;

        for (int i = 0; i < len; i++) {
            String str = arrs[i];
            if (!str.equals(part)) {        // not equal
                if (!checkEmpty(sb.toString())) {
                    sb.append(rawSplitChar);
                }

                sb.append(str);
            }
        }

        return sb.toString();
    }

    /**
     * 检查是否是有效的密码
     *
     * @param pswd 密码 6-16不含空格
     * @return
     */
    public static boolean checkPswdChar(String pswd) {
        boolean valid = true;
        if (checkEmpty(pswd))
            valid = false;
        else if (pswd.contains(" ")) {
            valid = false;
        } else if (pswd.length() < 6 || pswd.length() > 16) {
            valid = false;
        }
        return valid;
    }

    public static boolean checkPswd(String pwd) {
        if (checkEmpty(pwd)) {
            return false;
        }

        String regex = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,16}$";
        boolean flag = pwd.matches(regex);
        if (flag) {

            return true;
        } else {

//            ToastHelper.toast(context, "你的密码安全级别不够高，建议修改密码", 3000);
            return false;
        }
    }

    /**
     * 检查字符串是否是有效的手机号码
     *
     * @param string
     * @return
     */
    public static boolean checkIsMoblie(String string) {
        boolean result = false;
        if (!checkEmpty(string)) {
            if (string.length() == 11) {
                result = true;
            }
        }
        return result;
    }

    /**
     * 获取加密的手机
     *
     * @param mobile
     * @return
     */
    public static String getEncryMobile(String mobile) {

        String result;

        if (checkIsMoblie(mobile)) {
            StringBuilder stringBuilder = new StringBuilder(mobile.substring(0, 3));
            stringBuilder.append("****");
            stringBuilder.append(mobile.substring(7));
            result = stringBuilder.toString();
        } else {
            result = "";
        }

        return result;
    }


    /**
     * 检查字符串是否有效
     *
     * @param string
     * @param min        最小长度
     * @param max        最大长度
     * @param charString 不包含字符
     * @return 是否有效
     */
    public static boolean checkString(String string, int min, int max,
                                      String charString) {
        boolean valid = false;
        if (!checkEmpty(string)) {
            if (string.length() >= min && string.length() <= max
                    && !string.contains(charString)) {
                valid = true;
            }
        }
        return valid;
    }

    /**
     * 获取Base64格式字符串
     *
     * @param src
     * @return
     */
    public static String getBase64Str(String src) {
        String strBase64 = Base64.encodeToString(src.getBytes(), Base64.DEFAULT);

        if (StringUtil.checkEmpty(strBase64)) {
            return src;
        }

        return strBase64.trim();
    }

    /**
     * 获取解码后的Base64格式字符串
     *
     * @param base64Str
     * @return
     */
    public static String decodeBase64Str(String base64Str) {
        byte[] bytes = Base64.decode(base64Str, Base64.CRLF);

        if (null == bytes) {
            return base64Str;
        }

        return new String(bytes);
    }

    /**
     * 保留一位小数
     *
     * @param m
     * @return
     */
    public static String keepADecimal(double m) {

        DecimalFormat df = new DecimalFormat("0.0");
        return df.format(m);
    }


    /**
     * String类型转数组
     *
     * @param inputString 要转的String
     * @param splitChar   分割符
     * @return
     */
    public static String[] stringToArray(String inputString, String splitChar) {
        if (checkEmpty(splitChar) || checkEmpty(inputString)) {
            return null;
        } else {
            return inputString.split(splitChar);
        }
    }

    /**
     * 格式化限制字符串(13/30)
     *
     * @param len
     * @return
     */
    public static String formatLimitChars(int curLen, int maxLen) {
        StringBuffer sb = new StringBuffer();
        sb.append(curLen);
        sb.append("/");
        sb.append(maxLen);

        return sb.toString();
    }

    /**
     * 格式化百分比（88.99%）
     *
     * @param percent
     * @return
     */
    public static String formatPercentChars(float percent) {
        StringBuffer sb = new StringBuffer();
        sb.append(String.format("%.2f", percent));
        sb.append(PERCENT_MARK);

        return sb.toString();
    }

    /**
     * 获取百分比字符串（88%）
     *
     * @param percent
     * @return
     */
    public static String getFormatPercent(int percent) {
        StringBuffer sb = new StringBuffer();
        sb.append(percent);
        sb.append(PERCENT_MARK);

        return sb.toString();
    }

    /**
     * 获取百分比字符串（88%）
     *
     * @param percent
     * @return
     */
    public static String getFormatPercent(String percent) {
        StringBuffer sb = new StringBuffer();
        sb.append(percent);
        sb.append(PERCENT_MARK);
        return sb.toString();
    }

    /**
     * 获取规范的手机号码（137 2126 2550）
     *
     * @param mobile
     * @return
     */
    public static String getFormatMobile(String mobile) {
        String result;

        if (checkIsMoblie(mobile)) {
            StringBuilder sBuilder = new StringBuilder(mobile.substring(0, 3));
            sBuilder.append(" ");
            sBuilder.append(mobile.substring(3, 7));
            sBuilder.append(" ");
            sBuilder.append(mobile.substring(7, mobile.length()));
            result = sBuilder.toString();
        } else {
            result = "";
        }

        return result;
    }

    /**
     * 是否是蜘蛛网自营
     *
     * @param shop
     * @return
     */
    public static boolean isSpider(String shop) {

        boolean is = false;

        if (checkEmpty(shop)) {
            is = true;
        }

        if ("1".equals(shop)) {
            is = true;
        }

        return is;
    }


    /**
     * 保留一位小数
     *
     * @param dou
     * @return
     */
    public static double changeDouble(Double dou) {
        NumberFormat nf = new DecimalFormat("0.0 ");
        dou = Double.parseDouble(nf.format(dou));
        return dou;
    }

    /**
     * 判断是否是订阅商品
     *
     * @param type 订阅商品类型
     * @return
     */
    public static boolean isSubscrib(String type) {

        boolean is = true;

        if ("ts".equals(type))
            is = false;

        return is;
    }


    /**
     * 配送方式
     *
     * @param dType k-快递，j-就近配送，z-自提
     * @return
     */
    public static boolean isExpress(String dType) {

        boolean is = true;

        if (!"k".equals(dType))
            is = false;

        return is;
    }

    /**
     * 是否是就近配送方式
     *
     * @param dType
     * @return
     */
    public static boolean isNearest(String dType) {
        boolean is = true;

        if (!"j".equals(dType))
            is = false;

        return is;
    }

    /**
     * 是否是蜘蛛网快递
     *
     * @param delivery
     * @return
     */
    public static boolean isSpiderExpress(String delivery) {
        boolean is = true;

        if (!delivery.contains("快递") && !delivery.contains("挂号"))
            is = false;


        return is;
    }


    /**
     * 配送方式
     *
     * @param dType k-快递，j-就近配送，z-自提
     * @return
     */
    public static boolean isArayacak(String dType) {
        boolean is = false;

        if ("z".equals(dType))
            is = true;

        return is;
    }


    /**
     * 配送方式
     *
     * @param dType 1-自提，2-就近配送，3-快递
     * @return
     */
    public static boolean isExpressTwo(String dType) {

        boolean is = true;

        if (!"3".equals(dType))
            is = false;

        return is;
    }


    /**
     * 去除null字符串
     *
     * @param string
     * @return
     */
    public static String removeNullStr(String string) {
        String result = string;
        if (!checkEmpty(string)) {
            result = result.replace("null", "");
        }
        return result;
    }

    /**
     * 返回非空字符串
     *
     * @param string
     * @return
     */
    public static String getNotNullString(String string) {
        if (string == null) {
            string = "";
        }
        return string;
    }

    /**
     * 去除html标签
     *
     * @param content
     * @return
     */
    public static String removeHtml(String content) {

        if (!checkEmpty(content)) {

            // 过滤文章内容中的html
            content = content.replaceAll("</?[^<]+>", "");
            // 去除字符串中的空格 回车 换行符 制表符 等
            content = content.replaceAll("\\s*|\t|\r|\n", "");
            // 去除空格
            content = content.replaceAll("&nbsp;", "");
            // 去掉其他一些字符
            content = content.replaceAll("\\\\", "");

            //去除特殊字符串
            content = content.replaceAll("&apos;", "\'");

        }
        return content;
    }

    /**
     * 是否只有一家供应商（包括蜘蛛网）
     *
     * @param supplier
     * @return
     */
    public static boolean isOneSupplier(String supplier) {
        boolean is = true;

        if (!"1".equals(supplier))
            is = false;

        return is;
    }

    /**
     * 验证验证码的有效性
     *
     * @param verifyCode
     * @return
     */
    public static boolean isValidVerifyCode(String verifyCode) {

        boolean valid = false;

        if (checkEmpty(verifyCode))
            return valid;

        if (verifyCode.length() == 6) {
            valid = true;
            return valid;
        }
        return valid;
    }

    public static String getDataFormat(Double money) {
        DecimalFormat df = new DecimalFormat(PRICE_ZERO);
        return df.format(money).toString();
    }

    public static String subEffectString(String rawStr, int effectLen) {
        if (checkEmpty(rawStr)) {
            return "";
        }

        int rawLen = rawStr.length();
        if (rawLen <= effectLen) {
            return rawStr;
        }

        StringBuffer sb = new StringBuffer();
        sb.append(rawStr.substring(0, effectLen));
        sb.append(ELLIPSIS_MARK);
        return sb.toString();
    }
}
