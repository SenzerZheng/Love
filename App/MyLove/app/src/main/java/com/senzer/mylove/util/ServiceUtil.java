package com.senzer.mylove.util;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.Context;

import java.util.List;

/**
 * ProjectName: ServiceUtil
 * Description: 服务工具（判断服务是否正在运行，判断）
 * <p>
 * review by chenpan, wangkang, wangdong 2017/7/11
 * edit by JeyZheng 2017/7/11
 * author: JeyZheng
 * version: 1.0.0
 * created at: 2017/7/11 15:36
 */
public class ServiceUtil {

    public static boolean isServiceWork(Context context, String serviceName) {
        boolean isWork = false;

        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<RunningServiceInfo> services = am.getRunningServices(100);
        if (null == services || services.isEmpty()) {
            return false;
        }

        for (RunningServiceInfo info : services) {
            String sName = info.service.getClassName().toString();
            if (serviceName.equals(sName)) {
                isWork = true;
                break;
            }
        }

        return isWork;
    }

    public static boolean isProcessRunning(Context context, String processName) {
        boolean isRunning = false;

        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<RunningAppProcessInfo> processes = am.getRunningAppProcesses();
        if (null == processes || processes.isEmpty()) {
            return false;
        }

        for (RunningAppProcessInfo info : processes) {
            String pName = info.processName;
            if (processes.equals(pName)) {
                isRunning = true;
                break;
            }
        }

        return isRunning;
    }
}
