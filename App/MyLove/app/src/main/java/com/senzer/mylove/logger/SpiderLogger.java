/*
 * SpiderLogger.java
 * classes : com.spider.subscriber.util.SpiderLogger
 * @author liujun
 * V 1.0.0
 * Create at 2014-12-29 下午6:23:01
 * Copyright (c) 2014年  Spider. All Rights Reserved.
 */
package com.senzer.mylove.logger;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

/**
 * ProjectName: SpiderLogger
 * Description: 日志类
 *
 * review by chenpan, wangkang, wangdong 2017/7/10
 * edit by JeyZheng 2017/7/10
 * author: JeyZheng
 * version: 4.0
 * created at: 2017/7/10 16:56
 */
public class SpiderLogger implements ISpiderLogger {
    /** BUILD_TYPE */
    /**
     * debug for testing
     */
    public static final String BT_DEBUG = "debug";
    /**
     * debug for releasing
     */
    public static final String BT_DEBUG_REL = "debugRel";
    /**
     * ver: alpha
     */
    public static final String BT_ALPHA = "alpha";
    /**
     * ver: release & online
     */
    public static final String BT_RELEASE = "release";

    private static final String TAG = "SpiderLoger";
    private Context context;

    // 默认打日志,打正式包时，修改为 LEVEL_NOLOG（千万不要忘记）LEVEL_DEBUG//测试
    private static int level = LEVEL_DEBUG;                     // order n
    private static SpiderLogger logger = new SpiderLogger();    // order n + 1
    private List<ISpiderLogger> loggers = new ArrayList<>();

    private SpiderLogger() {
    }

    /**
     * 初始化日志
     *
     * @param context context
     */
    public void init(Context context) {
        this.context = context;
        loggers.add(new SpiderConsoleLogger(level));
        loggers.add(new SpiderFileLogger(context, level));
    }

    /**
     * 初始化日志
     *
     * @param context   context
     * @param buildType 编译类别
     */
    public void init(Context context, String buildType) {
        this.context = context;

        // BuildConfig.BUILD_TYPE: because the default current model is release, Using the app's model.
        if (BT_RELEASE.equalsIgnoreCase(buildType)) {
            level = LEVEL_NOLOG;
        }
        loggers.add(new SpiderConsoleLogger(level));
        loggers.add(new SpiderFileLogger(context, level));
    }

    /**
     * 获取日志类实例
     */
    public static SpiderLogger getLogger() {
        return logger;
    }

    @Override
    public void d(String tag, String msg) {
        if (level <= LEVEL_DEBUG) {
            for (ISpiderLogger loger : loggers) {
                loger.d(tag, msg);
            }
        }
    }

    @Override
    public void i(String tag, String msg) {
        if (level <= LEVEL_INFO) {
            for (ISpiderLogger loger : loggers) {
                loger.i(tag, msg);
            }
        }
    }

    @Override
    public void w(String tag, String msg) {
        if (level <= LEVEL_WARN) {
            for (ISpiderLogger loger : loggers) {
                loger.w(tag, msg);
            }
        }
    }

    @Override
    public void e(String tag, String msg) {
        if (level <= LEVEL_ERROR) {
            for (ISpiderLogger logger : loggers) {
                logger.e(tag, msg);
            }
        }
    }

    @Override
    public void e(String tag, Exception e) {
        if (level <= LEVEL_ERROR) {
            for (ISpiderLogger logger : loggers) {
                logger.e(tag, e);
            }
        }
    }
}
