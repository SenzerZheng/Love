package com.senzer.mylove.util;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

/**
 * ProjectName: ToastHelper
 * Description:
 * <p>
 * Toast.LENGTH_LONG = 3.5s
 * <br>
 * Toast.LENGTH_SHORT = 2s
 * <p>
 * review by chenpan, wangkang, wangdong 2017/6/14
 * edit by JeyZheng 2017/6/14
 * author: JeyZheng
 * version: 4.0
 * created at: 2017/6/14 18:10
 */
public class ToastHelper {

    private static Toast mToast;

    public static void toast(Context context, String msg) {
        toast(context, msg, Toast.LENGTH_LONG);
    }

    public static void toast(Context context, int msgResId) {
        toast(context, context.getString(msgResId), Toast.LENGTH_LONG);
    }

    public static void toast(Context context, int msgResId, int time) {
        toast(context, context.getString(msgResId), time);
    }

    public static void toast(Context context, String msg, int time) {
        if (null == mToast) {
            mToast = Toast.makeText(context, msg, time);
            mToast.setGravity(Gravity.CENTER, 0, 0);
        } else {
            mToast.setText(msg);
            mToast.setDuration(time);
        }

        mToast.show();
    }

    public static void hide() {
        if (null != mToast) {
            mToast.cancel();
        }
    }
}