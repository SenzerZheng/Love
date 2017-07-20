package com.senzer.mylove.api;


import android.support.annotation.NonNull;

import com.senzer.mylove.BuildConfig;

/**
 * ProjectName: HttpUrls
 * Description: 网路请求的URL
 * <p>
 * author: JeyZheng
 * version: 4.0
 * created at: 2016/5/19 17:55
 */
@SuppressWarnings("ALL")
public class HttpUrls {
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

    // ------------------ 基类接口地址 -----------------
    /**
     * 常规请求，服务器基本地址
     */
    public static final String LOCAL_HOST = getLocalHost();

    @NonNull
    private static final String getLocalHost() {
        if (BT_RELEASE.equalsIgnoreCase(BuildConfig.BUILD_TYPE)) {              // release(生产), forbid the log and request
            return "http://23.106.149.67:8080/";
        } else if (BT_DEBUG_REL.equalsIgnoreCase(BuildConfig.BUILD_TYPE)) {     // debugRelease(for viewBug)
            return "http://23.106.149.67:8080/";
        } else if (BT_ALPHA.equalsIgnoreCase(BuildConfig.BUILD_TYPE)) {         // alpha(preRelease)
            return "http://23.106.149.67:8080/";
        }

        return "http://192.168.1.181:8080/";                                    // debug(developer)
    }

    // ACCOUNT_MGR_HOST
    public static final String APP_VER_ACCOUNT = "users/";                              // 服务器地址 - 账户控制器（账户管理系统）
    public static final String APP_VER_LOCATIONS = "locations/";                        // 服务器地址 - 定位控制器
    public static final String APP_VER_FILES = "files/";                                // 服务器地址 - 文件控制器
    public static final String APP_VER_CONFIG = "config/";                              // 服务器地址 - 配置控制器

    // ------------------ 接口名 -----------------
    // ----- 登录注册 -----
    /**
     * 常规用户注册
     */
    public static final String USER_REGISTER = APP_VER_ACCOUNT + "register";

    /**
     * 常规用户登录（未使用）
     */
    public static final String USER_LOGIN = APP_VER_ACCOUNT + "userLogin.action";

    /**
     * 更新位置信息，FORM方式请求
     */
    public static final String UPDATE_LOCATION = APP_VER_LOCATIONS + "add";

    /**
     * 更新位置信息，JSON方式请求
     */
    public static final String UPDATE_LOCATION_BY_JSON = APP_VER_LOCATIONS + "addByJson";

    /**
     * 多文件上传
     */
    public static final String UPLOAD = APP_VER_FILES + "upload";

    /**
     * 多文件上传
     */
    public static final String UPLOAD_BATCH = APP_VER_FILES + "upload/batch";

    /**
     * 获取图片列表
     */
    public static final String GET_IMAGES = APP_VER_FILES + "images";

    /**
     * 获取配置信息
     */
    public static final String GET_CONFIGS = APP_VER_CONFIG + "isUploadImage";
}
