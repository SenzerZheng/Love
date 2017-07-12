package com.senzer.mylove.api;

import com.senzer.mylove.logger.SpiderLogger;

/**
 * ProjectName: HttpCode
 * Description: 网络请求错误码 (前缀：0 为请求成功  非0 为失败)
 * <p>
 * author: JeyZheng
 * version: 4.0
 * created at: 2016/8/17 11:31
 */
public class HttpCode {
    // ---------------- 服务器请求失败的错误码（互联网统一） ----------------
    // 服务器错误码：400、401、402、403、404、405、406、407、412、414、500、501、502
    // Http服务器返回的Code码（200：成功）
    public static final int HTTP_CODE_OK = 200;
    /**
     * 网络连接失败类型：客户端网络失败（无网络，未知主机，信号不好等）
     */
    public static final int NET_ERR_SRC_CLIENT = 0x10010;
    /**
     * 网络连接失败类型：服务器网络失败
     * <br>
     * 例如：详细的请看 http://www.cnblogs.com/DeasonGuan/articles/Hanami.html
     * <br>
     * （1）500：错误：服务器内部错误）
     * <br>
     * （2）501：（尚未实施） 服务器不具备完成请求的功能。
     * <br>
     * （3）404：Not Found 请求未找到。
     * <br>
     * （4）405：无法找到该网页。
     * <br>
     * （5）HTTP Status 403 （禁止） ----> 服务器拒绝请求 （这个在建立爬虫的时候，爬取网页就会遇到了，这样就得通过User-Agent欺骗爬取内容）
     * <br>
     * （6）HTTP Status 400 （错误请求）-----> 服务器不理解请求的语法。
     */
    public static final int NET_ERR_SRC_SERVER = 0x10020;

    // ---------------- 蜘蛛网后台返回的错误码（此时HttpCode = 200） ----------------
//    F1001 : 缺少必要参数
//    F1002 ：验签失败
//    F1003 : 账户存在问题,存在多个

    public static final String ACCOUNT_EXISTED = "F1009";               // F1009 : 用户名已存在
    public static final String TOKEN_INVALID = "F2015";                 // F2015 : TOKEN失效
    public static final String ISSUE_OFF_SELL = "F2032";                // F2015 : 该刊期已下架

    public static final String SUCCESS = "1001";
//    private static final String FAILURE = "500";
//    public static final String TIPS_UNKNOWN_MISTAKE = "服务器开小差了，请稍后再试";

    public static boolean verifyCode(String code) {
        if (code.equalsIgnoreCase(SUCCESS)) {
            return true;
        } else {
            SpiderLogger.getLogger().e("HttpCode", "unknown mistake");
            return false;
        }
    }
}
