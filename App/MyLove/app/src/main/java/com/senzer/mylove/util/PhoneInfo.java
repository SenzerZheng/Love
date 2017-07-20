package com.senzer.mylove.util;

import android.content.Context;
import android.telephony.TelephonyManager;

/**
 * ProjectName: PhoneInfo
 * Description: 获取手机信息（获取手机号相关性信息）
 * <p>
 * author: JeyZheng
 * version: 1.0.0
 * created at: 2017/7/19 9:48
 */
public class PhoneInfo {

    private static class PhoneInfoHolder {
        private final static PhoneInfo INSTANCE = new PhoneInfo();
    }

    private PhoneInfo() {
    }

    public static PhoneInfo getInstance() {
        return PhoneInfoHolder.INSTANCE;
    }

    private TelephonyManager telephonyManager;

    public void init(Context context) {
        telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
    }

    /**
     * 获取电话号码
     */
    public String getNativePhoneNumber() {
        String nativePhoneNumber = telephonyManager.getLine1Number();
        return nativePhoneNumber;
    }

    /**
     * 获取手机服务商信息
     */
    public String getProvidersName() {
        String providersName = "N/A";
        // 国际移动用户识别码
        String IMSI = "";
        try {
            // IMSI号前面3位460是国家，紧接着后面2位00 02是中国移动，01是中国联通，03是中国电信。
            IMSI = telephonyManager.getSubscriberId();
            if (IMSI.startsWith("46000") || IMSI.startsWith("46002")) {
                providersName = "中国移动";
            } else if (IMSI.startsWith("46001")) {
                providersName = "中国联通";
            } else if (IMSI.startsWith("46003")) {
                providersName = "中国电信";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return providersName;
    }

    /**
     * 获取手机信息
     *
     * @return
     */
    public String getPhoneInfo() {
        StringBuilder sb = new StringBuilder();

        sb.append("\nDeviceId(IMEI) = " + telephonyManager.getDeviceId());
        sb.append("\nDeviceSoftwareVersion = " + telephonyManager.getDeviceSoftwareVersion());
        sb.append("\nLine1Number = " + telephonyManager.getLine1Number());
        sb.append("\nNetworkCountryIso = " + telephonyManager.getNetworkCountryIso());
        sb.append("\nNetworkOperator = " + telephonyManager.getNetworkOperator());
        sb.append("\nNetworkOperatorName = " + telephonyManager.getNetworkOperatorName());
        sb.append("\nNetworkType = " + telephonyManager.getNetworkType());
        sb.append("\nPhoneType = " + telephonyManager.getPhoneType());
        sb.append("\nSimCountryIso = " + telephonyManager.getSimCountryIso());
        sb.append("\nSimOperator = " + telephonyManager.getSimOperator());
        sb.append("\nSimOperatorName = " + telephonyManager.getSimOperatorName());
        sb.append("\nSimSerialNumber = " + telephonyManager.getSimSerialNumber());
        sb.append("\nSimState = " + telephonyManager.getSimState());
        sb.append("\nSubscriberId(IMSI) = " + telephonyManager.getSubscriberId());
        sb.append("\nVoiceMailNumber = " + telephonyManager.getVoiceMailNumber());
        return sb.toString();
    }
}
