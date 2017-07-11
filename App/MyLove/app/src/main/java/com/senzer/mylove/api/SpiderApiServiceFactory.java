package com.senzer.mylove.api;


import retrofit.Retrofit;

/**
 * ProjectName: SpiderApiServiceFactory
 * Description: 接口工厂类
 * <p>
 * author: JeyZheng
 * version: 4.0
 * created at: 2016/8/30 14:50
 */
public class SpiderApiServiceFactory {

    /**
     * 创建接口对象
     *
     * @return
     */
    public static SpiderApiService getSpiderApiService(String url) {

        SpiderApiService spiderApiService = null;

        Retrofit retrofit = RetrofitManager.get(url);
        spiderApiService = retrofit.create(SpiderApiService.class);

        return spiderApiService;
    }

    /**
     * 创建接口对象
     * @return
     *//*
    public static SpiderApiServiceTwo getSpiderApiServiceTwo() {

        SpiderApiServiceTwo spiderApiServiceTwo = null;

        if (BuildConfig.mock) {
            spiderApiServiceTwo = new MockApiService();
        } else if (BuildConfig.test){
            Retrofit retrofit = RetrofitManager.get(SpiderApiServiceTwo.LOCAL_HOST);
            spiderApiServiceTwo = retrofit.create(SpiderApiServiceTwo.class);
        } else if (BuildConfig.mtest) {
            Retrofit retrofit = RetrofitManager.get(SpiderApiServiceTwo.MTEST_URL);
            spiderApiServiceTwo = retrofit.create(SpiderApiServiceTwo.class);
        }

        return spiderApiServiceTwo;
    }*/

    /**
     * 创建接口对象
     * @return
     *//*
    public static SpiderApiServiceThree getSpiderApiServiceThree() {

        SpiderApiServiceThree spiderApiServiceThree = null;

        if (BuildConfig.mock) {
            spiderApiServiceThree = new MockApiService();
        } else if (BuildConfig.test){
            Retrofit retrofit = RetrofitManager.get(SpiderApiServiceThree.LOCAL_HOST);
            spiderApiServiceThree = retrofit.create(SpiderApiServiceThree.class);
        } else if (BuildConfig.mtest) {
            Retrofit retrofit = RetrofitManager.get(SpiderApiServiceThree.MTEST_URL);
            spiderApiServiceThree = retrofit.create(SpiderApiServiceThree.class);
        }


        return spiderApiServiceThree;
    }*/

} 