package com.senzer.mylove.api;

import com.senzer.mylove.BuildConfig;
import com.senzer.mylove.logger.SpiderLogger;
import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.logging.HttpLoggingInterceptor;

import java.io.IOException;
import java.util.WeakHashMap;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;

/**
 * ProjectName: RetrofitManager
 * Description: Retrofit管理类
 * <p>
 * author: JeyZheng
 * version: 2.1
 * created at: 2016/8/12 11:17
 */
public class RetrofitManager {
    private static String TAG = "RetrofitManager";
    private static RetrofitManager manager;

    private WeakHashMap<String, Retrofit> retrofitMap = new WeakHashMap<>();
    private OkHttpClient okHttpClient;

    public static RetrofitManager getManager() {
        if (manager == null) {
            synchronized (RetrofitManager.class) {
                if (manager == null) {
                    manager = new RetrofitManager();
                }
            }
        }
        return manager;
    }

    private RetrofitManager() {
        okHttpClient = new OkHttpClient();

        // 添加拦截器，打印网络请求信息(正式打包去掉，否会敏感数据会泄漏)
        if (!HttpUrls.BT_RELEASE.equalsIgnoreCase(BuildConfig.BUILD_TYPE)) {
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            okHttpClient.interceptors().add(interceptor);
        }

        okHttpClient.networkInterceptors().add(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                long t1 = System.nanoTime();
                Request request = chain.request();
                SpiderLogger.getLogger().d(TAG, String.format("Sending request %s on %s%n%s",
                        request.url(), chain.connection(), request.headers()));

                Response response = chain.proceed(request);

                long t2 = System.nanoTime();
                SpiderLogger.getLogger().d(TAG, String.format("Received response for %s in %.1fms%n%s",
                        request.url(), (t2 - t1) / 1e6d, response.headers()));
                return response;
            }
        });
    }

    private Retrofit getRetrofit(String baseUrl) {
        // new retrofit
        Retrofit retrofit = retrofitMap.get(baseUrl);
        SpiderLogger.getLogger().i(TAG, baseUrl);
        if (retrofit == null) {
            OkHttpClient tempOkHttpClient = okHttpClient;

            // add the https, while it is requesting the online aacountMgrHost
            // online ev:
            // 1. http://passporttest.spider.com.cn/
            // 2. http://passport.spider.com.cn/
//            if ((HttpUrls.BT_RELEASE.equals(BuildConfig.BUILD_TYPE)
//                    || HttpUrls.BT_DEBUG_REL.equals(BuildConfig.BUILD_TYPE))
//                    && HttpUrls.ACCOUNT_MGR_HOST.equalsIgnoreCase(baseUrl)) {
//                tempOkHttpClient = okHttpClient.clone();
//
//                SSLSocketFactory ssf = SSLContextFactory.getSSLSocketFactory();
//                tempOkHttpClient.setSslSocketFactory(ssf);
//            }

            retrofit = new Retrofit.Builder()
                    .client(tempOkHttpClient)
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(baseUrl)
                    .build();

            retrofitMap.put(baseUrl, retrofit);
        }
        return retrofit;
    }


    /**
     * 创建retrofit实例
     *
     * @param baseUrl 服务器地址
     * @return
     */
    public static Retrofit get(String baseUrl) {
        return getManager().getRetrofit(baseUrl);
    }
}