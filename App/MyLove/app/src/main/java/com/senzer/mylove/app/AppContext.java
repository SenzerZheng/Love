package com.senzer.mylove.app;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.bumptech.glide.Glide;
import com.senzer.mylove.BuildConfig;
import com.senzer.mylove.R;
import com.senzer.mylove.api.HttpUrls;
import com.senzer.mylove.api.SpiderApiService;
import com.senzer.mylove.api.SpiderApiServiceFactory;
import com.senzer.mylove.entity.vo.UserInfo;
import com.senzer.mylove.logger.SpiderLogger;

/**
 * ProjectName: AppContext
 * Description: 应用程序上下文
 *
 * review by chenpan, wangkang, wangdong 2017/7/10
 * edit by JeyZheng 2017/7/10
 * author: JeyZheng
 * version: 4.0
 * created at: 2017/7/10 17:03
 */
@SuppressWarnings("ALL")
public class AppContext extends Application {

    public static final String TAG = "AppContext";

    public static String sDeviceId = "";
    public static String sIpAddr = "";

    private volatile static AppContext appContext;

    // retrofit
    private SpiderApiService spiderApiService;

    public static boolean showUpdateDlg;

    public static boolean isTestVerify = false;
    public static String testVerifyCode = "852862";
    public static String verifyCode;
    public static String cartCount = "0";                       // 购物车商品个数
    public static String SPIDER_STORE_ID = "1";                 // 蜘蛛网自营店铺id
    public static String currentUserNick = "";                  // 当前用户nickname,为了苹果推送不是userid而是昵称
    private static String FIRST_OPEN = "firstopen";             // 是否为首次打开app
    private static String EXIST_SYAYTEM_NEWS = "system";        // 存在未读的系统消息

    public static String URL = "";      //默认为空

    // ---------- userInfo ------------
    private UserInfo mUserInfo;

    /**
     * 单例 - 静态内部类，lazy loading
     * <p>
     * 这种方式是Singleton类被装载了，INSTANCE不一定被初始化。
     * 因为SingletonHolder类没有被主动使用，只有显示通过调用getInstance方法时，
     * 才会显示装载SingletonHolder类，从而实例化instance。
     *
     * @param context
     */
//    private static class SingletonHolder {
//        private static AppContext INSTANCE = new AppContext();
//    }
//
//    private AppContext() {
//    }
//
//    public static final AppContext getInstance() {
//        return SingletonHolder.INSTANCE;
//    }

    /**
     * 单例 - 双重校验锁
     *
     * @param context
     */
    public static AppContext getInstance() {
        if (appContext == null) {
            synchronized (AppContext.class) {
                if (appContext == null) {
                    appContext = new AppContext();
                }
            }
        }

        return appContext;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);

        appContext = this;
        MultiDex.install(this);

//        SpiderTrackManager.getInstance().initTracks(getApplicationContext());

        // memory leak checking
//        LeakCanary.install(this);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        Glide.get(this).clearMemory();
    }

    @Override
    public void onCreate() {
        super.onCreate();

        // retrofit start
        // common api service
        spiderApiService = SpiderApiServiceFactory.getSpiderApiService(HttpUrls.LOCAL_HOST);
        // TODO：...
        // testReadAndDown = SpiderApiServiceFactory.getSpiderApiService(HttpUrls.FILM_URL);
        // retrofit end

        // control the logger
        SpiderLogger.getLogger().init(this, BuildConfig.BUILD_TYPE);
    }

    /**
     * 跳转动画
     *
     * @param context
     */
    public static void overridePendingTransition(Context context) {
        if (context != null && context instanceof Activity) {
            ((Activity) context).overridePendingTransition(R.anim.slid_in_right, R.anim.slid_out_left);
        }
    }

    /**
     * 获取基本接口类
     *
     * @return
     */
    public SpiderApiService getSpiderApiService() {
        return spiderApiService;
    }

    /**
     * 退出登录/注销用户
     */
    public void logout() {
        // reset parameters
        mUserInfo = null;
    }
}