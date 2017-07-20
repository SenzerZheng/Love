package com.senzer.mylove.api;

import android.util.Base64;

import com.senzer.mylove.app.AppContext;
import com.senzer.mylove.entity.dto.ReqBase;
import com.senzer.mylove.entity.vo.UserInfo;
import com.senzer.mylove.logger.SpiderLogger;
import com.senzer.mylove.util.AppInfoHelper;
import com.senzer.mylove.util.ClassInfoUtils;
import com.senzer.mylove.util.Md5Util;
import com.senzer.mylove.util.StringUtil;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.RequestBody;

import java.io.File;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * ProjectName: ParamsHelper
 * Description: 参数 帮助类
 * <p>
 * author: JeyZheng
 * version: 4.0
 * created at: 2016/8/16 19:12
 */
@SuppressWarnings("ALL")
public class ParamsHelper {
    public static final String TAG = "ParamsHelper";

    // 公共参数值（VALUE）
    public static final String FILE_TYPE_JSON = "json";
    public static final String KEY_READER = "spiderReader";                         // 蜘蛛精公钥，商户号
    public static final String PLATFORM = "android_reader";
    public static final String PSOURCE_VALUE = "readeapp";                          // 来源（蜘蛛精默认为：readeapp）；充值，获取订单号
    public static final String SOURCE = "reader";                                   // 来源；默认
    public static final String VERSION = "4.0.2";                                   // 服务器版本

    public static final String PRIVATEKEY = "a25$54%589@65d#Ad#fh$aS%dfe";          // 蜘蛛精私钥

    // 电影票APP的私钥与公钥（m站），实现支付功能
    public static final String SF_PAY_KEY = "spiderfilm";                           // 电影票公钥
    public static final String SF_PAY_PRIVATEKEY = "hdif36gh46346fgjl#kmb@yuyer76"; // 电影票私钥

    // 字段名（KEY），如有疑问见《蜘蛛精接口文档4.0》
    public static final String VERSION_APP = "versionApp";
    public static final String SIGN = "sign";
    public static final String FILE_TYPE = "fileType";
    public static final String KEY = "key";
    public static final String PLATFORM_ID = "platformId";
    public static final String PSOURCE_KEY = "psource";
    public static final String NET_PAY_AMOUNT = "netpayamount";
    public static final String RANDOM_CODE = "randomCode";              // 使用uuid生成
    public static final String IMUSER_FLAG = "imuserFlag";              // 是否需要用户环信信息
    public static final String IMUSER_FLAG_YES = "1";                   // 需要返回环信信息
    public static final String IMUSER_FLAG_NO = "0";                    // 不需要返回环信信息
    public static final String TIME_STAMP = "timeStamp";


    // 是否已经加载过图片验证码（true：已加载，false：未加载），为了解决加载图片验证码的RandomCode与登录一样
    private static boolean isLoadedImgVC;
    private static String sUUID = "";
    private static String sTimeStamp = "";

    // ----------------------- 通用方法 START ------------------------

    /**
     * 初始化共同Map
     *
     * @param common
     */
    private static void initCommonMap(Map<String, String> common) {
        String tempChanner = AppInfoHelper.getChannelId(AppContext.getInstance());

        common.put("key", KEY_READER);                                          // 商户号

//        common.put("fileType", FILE_TYPE);                                    // 返回数据类型
        common.put("platform", PLATFORM);                                       // 终端，iPhone_film/android_reader/web/wap

        common.put("source", SOURCE);                                           // 来源，film，reader，subscriber
        common.put("channel", tempChanner);                                     // 渠道，用户表存入sourcedesc字段中
        common.put("version", VERSION);                                         // 版本，服务器当前版本 例如：v3.2.0

        sTimeStamp = String.valueOf(System.currentTimeMillis());
        common.put(TIME_STAMP, sTimeStamp);                                     // 时间戳

        sUUID = getUUID();
        common.put(RANDOM_CODE, sUUID);                                          // sUUID
    }

    /**
     * 初始化Map - 初始化共同参数与通过反射添加具体参数
     *
     * @param map
     */
    private static void initCommonMapByReflect(Map<String, String> map, Object obj) {
        // init base map
        initCommonMap(map);

        /** class */
        // put the fieldName and the fieldValue with reflecting
        Class<?> clazz = obj.getClass();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            String fieldName = field.getName();
            // remove the constant
            if (Character.isUpperCase(fieldName.charAt(0))) {
                continue;
            }

            // remove the super class filed: serialVersionUID and $change
            if ("serialVersionUID".equals(fieldName)
                    || "$change".equals(fieldName)) {
                continue;
            }

            String fieldValue = String.valueOf(ClassInfoUtils.getFieldValueByName(fieldName, obj));

//            if (!StringUtil.checkEmpty(fieldValue)) {
//                map.put(fieldName, fieldValue);
//            }
            map.put(fieldName, fieldValue);
        }

        // avoid getting the Object class
//        if (clazz.getCanonicalName().equals(ReqBase.class.getCanonicalName())) {
        if (clazz == ReqBase.class) {
            return;
        }

        /** superClass */
        Class<?> superClass = clazz.getSuperclass();
        Field[] superFields = superClass.getDeclaredFields();
        for (Field field : superFields) {
            String fieldName = field.getName();
            // remove the constant
            if (Character.isUpperCase(fieldName.charAt(0))) {
                continue;
            }

            // remove the super class filed: serialVersionUID and $change
            if ("serialVersionUID".equals(fieldName)
                    || "$change".equals(fieldName)) {
                continue;
            }

            String fieldValue = String.valueOf(ClassInfoUtils.getFieldValueByName(fieldName, obj));

//            if (!StringUtil.checkEmpty(fieldValue)) {
//                map.put(fieldName, fieldValue);
//            }
            map.put(fieldName, fieldValue);
        }
    }

    /**
     * 初始化共同Map - 为了解决加载图片验证码的RandomCode与登录一样
     *
     * @param common
     */
    private static void initCommonMap4Login(Map<String, String> common) {
        String tempChanner = AppInfoHelper.getChannelId(AppContext.getInstance());

        common.put("key", KEY_READER);                                          // 商户号

//        common.put("fileType", FILE_TYPE);                                    // 返回数据类型
        common.put("platform", PLATFORM);                                       // 终端，iPhone_film/android_reader/web/wap

        common.put("source", SOURCE);                                           // 来源，film，reader，subscriber
        common.put("channel", tempChanner);                                     // 渠道，用户表存入sourcedesc字段中
        common.put("version", VERSION);                                         // 版本，服务器当前版本 例如：v3.2.0

        sTimeStamp = String.valueOf(System.currentTimeMillis());
        common.put(TIME_STAMP, sTimeStamp);                                     // 时间戳

        if (!isLoadedImgVC) {

            sUUID = getUUID();
            isLoadedImgVC = true;
        }
        common.put(RANDOM_CODE, sUUID);                                          // sUUID
    }

    /**
     * 初始化共同Map - 上传文件
     * <p>
     * .
     *
     * @param common
     */
    @Deprecated
    private static void initCommonMapReqBody(Map<String, RequestBody> common) {
        String tempChanner = AppInfoHelper.getChannelId(AppContext.getInstance());

        common.put("key", RequestBody.create(MediaType.parse("text/plain"), KEY_READER));           // 商户号

//        common.put("fileType", RequestBody.create(MediaType.parse("text/plain"), FILE_TYPE));     // 返回数据类型
        common.put("platform", RequestBody.create(MediaType.parse("text/plain"), PLATFORM));        // 终端，iPhone_film/android_reader/web/wap

        common.put("source", RequestBody.create(MediaType.parse("text/plain"), SOURCE));            // 来源，film，reader，subscriber
        common.put("channel", RequestBody.create(MediaType.parse("text/plain"), tempChanner));      // 渠道，用户表存入sourcedesc字段中
        common.put("version", RequestBody.create(MediaType.parse("text/plain"), VERSION));          // 版本，当前版本 例如：v3.2.0

        common.put(TIME_STAMP, RequestBody.create(MediaType.parse("text/plain"), sTimeStamp));      // 时间戳
        common.put(RANDOM_CODE, RequestBody.create(MediaType.parse("text/plain"), sUUID));          // sUUID
    }

    /**
     * 获取Base64格式字符串
     *
     * @param src
     * @return
     */
    private static String getBase64Str(String src) {
        String strBase64 = Base64.encodeToString(src.getBytes(), Base64.DEFAULT);

        if (StringUtil.checkEmpty(strBase64)) {
            return src;
        }

        return strBase64.trim();
    }

    /**
     * 获取32位UUID
     *
     * @return
     */
    private static String getUUID() {
        final String UUID_SEPARATOR = "-";
        String getid = UUID.randomUUID().toString().replace(UUID_SEPARATOR, "");
        return getid;
    }

    /**
     * 生成签名，先按字段排序，然后MD5加密
     *
     * @param params 参数
     * @return
     */
    public static String getInterfSign(Map<String, String> params) {
        if (params.size() < 1) {
            return Md5Util.getMd5(PRIVATEKEY).toUpperCase();
        }

        Object[] keys = params.keySet().toArray();
        Arrays.sort(keys);
        StringBuilder rawSign = new StringBuilder();

        for (Object key : keys) {
            String value = params.get(key);
            if (!key.toString().equals(SIGN)                    // "sign" do not add
                    && !StringUtil.checkEmpty(value)) {
                rawSign.append(key).append(value);
            }
        }
        rawSign.append(PRIVATEKEY);
        SpiderLogger.getLogger().i("ParamsHelper - sign", rawSign.toString());

        return Md5Util.getMd5(rawSign.toString()).toUpperCase();
    }
    // ----------------------- 通用方法 END ------------------------

    // ---------------------- 接口文档 - 请求参数 ------------------
    /**
     * 获取定位信息
     *
     * @param locationInfo
     * @return
     */
    public static Map<String, String> registerMap(UserInfo uInfo) {
        Map<String, String> map = new HashMap<>();
        map.put("mobile", uInfo.getMobile());

        return map;
    }

    /**
     * 获取定位信息
     *
     * @param locationInfo
     * @return
     */
    public static Map<String, String> updateLocationMap(String locationInfo) {
        Map<String, String> map = new HashMap<>();
        map.put("locationInfo", locationInfo);

        return map;
    }

    /**
     * 文件上传具体实现方法（单文件上传）
     *
     * @param file
     * @return
     */
    public static Map<String, RequestBody> uploadMap(File file) {
        Map<String, RequestBody> map = new HashMap<>();

        String fileKey = "image\"; filename=\"" + file.getName() + "\"";
        RequestBody reqBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        map.put(fileKey, reqBody);
        return map;
    }

    /**
     * 批量上传图片
     *
     * @param files
     * @return
     */
    public static Map<String, RequestBody> uploadBatchMap(List<File> files) {
        Map<String, RequestBody> map = new HashMap<>();

        /** way one, apotic name and floating filename */
        for (File file : files) {
            String fileKey = "images\"; filename=\"" + file.getName() + "\"";
            RequestBody reqBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
//            RequestBody reqBody = RequestBody.create(MediaType.parse("image/png"), file);
            map.put(fileKey, reqBody);
        }

        /** way two, floating name and floating filename */
//        int index = 0;
//        for (File file : files) {
//            String fileKey = "image" + index + "\"; filename=\"" + file.getName() + "\"";
//            RequestBody reqBody = RequestBody.create(MediaType.parse("image/png"), file);
//            map.put(fileKey, reqBody);
//        }
        return map;
    }
}