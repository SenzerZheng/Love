package com.senzer.mylove.app;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.icu.text.SimpleDateFormat;
import android.support.multidex.MultiDex;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationClientOption.AMapLocationMode;
import com.amap.api.location.AMapLocationListener;
import com.bumptech.glide.Glide;
import com.senzer.mylove.BuildConfig;
import com.senzer.mylove.R;
import com.senzer.mylove.api.DataResponse;
import com.senzer.mylove.api.HttpCode;
import com.senzer.mylove.api.HttpUrls;
import com.senzer.mylove.api.SpiderApiService;
import com.senzer.mylove.api.SpiderApiServiceFactory;
import com.senzer.mylove.entity.dto.ReqLocation;
import com.senzer.mylove.entity.vo.UserInfo;
import com.senzer.mylove.logger.SpiderLogger;

import java.util.Date;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * ProjectName: AppContext
 * Description: 应用程序上下文
 * <p>
 * review by chenpan, wangkang, wangdong 2017/7/10
 * edit by JeyZheng 2017/7/10
 * author: JeyZheng
 * version: 4.0
 * created at: 2017/7/10 17:03
 */
@SuppressWarnings("ALL")
public class AppContext extends Application {
    public static final String TAG = AppContext.class.getSimpleName();

    private volatile static AppContext appContext;

    // retrofit
    private SpiderApiService spiderApiService;

    // ---------- userInfo ------------
    private UserInfo mUserInfo;

    // ------------- location ----------------
    private double mLatitude;
    private double mLongitude;

    // 声明AMapLocationClient类对象
    public AMapLocationClient mLocationClient;
    // 声明AMapLocationClientOption对象
    public AMapLocationClientOption mLocationOption;
    // 声明定位回调监听器
    public AMapLocationListener mLocationListener = new AMapLocationListener() {
        @Override
        public void onLocationChanged(AMapLocation aMapLocation) {
            if (null == aMapLocation) {
                return;
            }

            if (aMapLocation.getErrorCode() == 0) {         // success

                StringBuffer sb = new StringBuffer();
                sb.append("定位结果来源：").append(aMapLocation.getLocationDetail()).append(", ");
                sb.append("纬度：").append(aMapLocation.getLatitude()).append(", ");
                sb.append("经度：").append(aMapLocation.getLongitude()).append(", ");
                sb.append("精度信息：").append(aMapLocation.getAccuracy()).append(", ");
                sb.append("地址：").append(aMapLocation.getAddress()).append(", ");
//                sb.append("国家信息：").append(aMapLocation.getCountry()).append(", ");
//                sb.append("省信息：").append(aMapLocation.getProvince()).append(", ");
//                sb.append("城市信息：").append(aMapLocation.getCity()).append(", ");
//                sb.append("城区信息：").append(aMapLocation.getDistrict()).append(", ");
//                sb.append("街道信息：").append(aMapLocation.getStreet()).append(", ");
//                sb.append("街道门牌号信息：").append(aMapLocation.getStreetNum()).append(", ");
                sb.append("城市编码：").append(aMapLocation.getCityCode()).append(", ");
                sb.append("地区编码：").append(aMapLocation.getAdCode()).append(", ");
                sb.append("当前定位点的AOI信息：").append(aMapLocation.getAoiName()).append(", ");
//                sb.append("当前室内定位的建筑物Id：").append(aMapLocation.getBuildingId()).append(", ");
//                sb.append("当前室内定位的楼层：").append(aMapLocation.getFloor()).append(", ");
                sb.append("GPS的当前状态：").append(aMapLocation.getGpsAccuracyStatus()).append(", ");
//                sb.append("定位信息描述：").append(aMapLocation.getLocationDetail()).append(", ");

                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date = new Date(aMapLocation.getTime());
                String time = df.format(date);
                sb.append("定位时间：").append(time);

                SpiderLogger.getLogger().d(TAG, "[AMapInfo - Location Info] " + sb.toString());
                double latitude = aMapLocation.getLatitude() * 10000;
                double longitude = aMapLocation.getLongitude() * 10000;
                if (Math.abs(mLatitude - latitude) < 1
                        || Math.abs(mLongitude - longitude) < 1) {

                    return;
                }

                mLatitude = latitude;
                mLongitude = longitude;
                updateLocation(sb.toString());
                /**
                 *
                 *
                 * TODO: 1. 图片后台获取
                 * TODO: 2. 后台配置功能开关
                 * TODO: 3. 获取本机号码
                 *
                 *
                 *
                 */
            } else {                                        // error

                // 定位失败时，可通过ErrCode（错误码）信息来确定失败的原因，errInfo是错误信息，详见错误码表。
                SpiderLogger.getLogger().e(TAG, "[AMapError - Location Error] "
                        + "errorCode: " + aMapLocation.getErrorCode() + ", "
                        + "errorInfo: " + aMapLocation.getErrorInfo());
            }
        }
    };

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
    public void onTrimMemory(int level) {
        // 销毁定位客户端，同时销毁本地定位服务。
//        mLocationClient.onDestroy();
    }

    @Override
    public void onLowMemory() {
        Glide.get(this).clearMemory();
    }

    @Override
    public void onCreate() {
        super.onCreate();

        // retrofit start
        // common api service
        spiderApiService = SpiderApiServiceFactory.getSpiderApiService(HttpUrls.LOCAL_HOST);
        // retrofit end

        // control the logger
        SpiderLogger.getLogger().init(this, BuildConfig.BUILD_TYPE);

        initLocation();
    }

    private void initLocation() {
        // REF: http://lbs.amap.com/api/android-location-sdk/guide/android-location/getlocation
        // 初始化定位
        mLocationClient = new AMapLocationClient(getApplicationContext());
        // 设置定位回调监听
        mLocationClient.setLocationListener(mLocationListener);

        // 初始化AMapLocationClientOption对象
        mLocationOption = new AMapLocationClientOption();
        // ----------- 选择定位模式 ---------
        // 设置定位模式为AMapLocationMode.Hight_Accuracy，高精度定位模式。
        mLocationOption.setLocationMode(AMapLocationMode.Hight_Accuracy);
        // 设置定位模式为AMapLocationMode.Battery_Saving，低功耗定位模式。
//        mLocationOption.setLocationMode(AMapLocationMode.Battery_Saving);
        // 设置定位模式为AMapLocationMode.Device_Sensors，仅用设备定位模式。
//        mLocationOption.setLocationMode(AMapLocationMode.Device_Sensors);

        // ------------ 设置单次定位 ---------
        // 获取一次定位结果：
        // 该方法默认为false。
//        mLocationOption.setOnceLocation(true);
        // 获取最近3s内精度最高的一次定位结果：
        // 设置setOnceLocationLatest(boolean b)接口为true，启动定位时SDK会返回最近3s内精度最高的一次定位结果。
        // 如果设置其为true，setOnceLocation(boolean b)接口也会被设置为true，反之不会，默认为false。
//        mLocationOption.setOnceLocationLatest(true);

        // ------------ 自定义连续定位 ---------
        // 设置定位间隔,单位毫秒,默认为2000ms，最低1000ms。
        mLocationOption.setInterval(30 * 1000);

        // ------------ 其他参数 ---------
        // 设置是否返回地址信息（默认返回地址信息）
//        mLocationOption.setNeedAddress(true);
        // 设置是否强制刷新WIFI，默认为true，强制刷新。
//        mLocationOption.setWifiActiveScan(false);
        // 设置是否允许模拟位置,默认为true，允许模拟位置
//        mLocationOption.setMockEnable(true);
        // 单位是毫秒，默认30000毫秒，建议超时时间不要低于8000毫秒。
//        mLocationOption.setHttpTimeOut(20000);
        // 关闭缓存机制
        mLocationOption.setLocationCacheEnable(false);

        // 给定位客户端对象设置定位参数
        mLocationClient.setLocationOption(mLocationOption);
        // 启动定位
        mLocationClient.startLocation();
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

    private void updateLocation(String location) {
        spiderApiService.updateLocation(new ReqLocation(location))
//                spiderApiService.updateLocation(ParamsHelper.updateLocationMap(sb.toString()))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<DataResponse>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(DataResponse dataResponse) {
                        if (HttpCode.verifyCode(dataResponse.getCode())) {

                            SpiderLogger.getLogger().d(TAG, "[AMapInfo - Location Result] SUCCESS!");
                        } else {

                            SpiderLogger.getLogger().e(TAG, "[AMapInfo - Location Result] FAULT!");
                        }
                    }
                });
    }

    /**
     * 退出登录/注销用户
     */
    public void logout() {
        // reset parameters
        mUserInfo = null;
    }
}