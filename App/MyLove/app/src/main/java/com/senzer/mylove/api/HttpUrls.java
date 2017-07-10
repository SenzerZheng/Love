package com.senzer.mylove.api;


import android.support.annotation.NonNull;

import com.spider.reader.BuildConfig;

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
            return "http://mreadinter.spider.com.cn/";
        } else if (BT_DEBUG_REL.equalsIgnoreCase(BuildConfig.BUILD_TYPE)) {     // debugRelease(for viewBug)
            return "http://mreadinter.spider.com.cn/";
        } else if (BT_ALPHA.equalsIgnoreCase(BuildConfig.BUILD_TYPE)) {         // alpha(preRelease)
            return "http://mreadintertest.spider.com.cn/";
        }

        return "http://192.168.1.234:8088/";                                    // debug(developer)
//        return "http://mreadinter.spider.com.cn/";                              // 张威
//        return "http://192.168.1.126:8080/";                                    // 向明亨
    }

    /**
     * 账户管理系统，服务器地址
     */
    public static final String ACCOUNT_MGR_HOST = getAccountMgrHost();

    @NonNull
    private static String getAccountMgrHost() {
        if (BT_RELEASE.equalsIgnoreCase(BuildConfig.BUILD_TYPE)) {              // release(生产)
            return "https://passport.spider.com.cn/";
        } else if (BT_DEBUG_REL.equalsIgnoreCase(BuildConfig.BUILD_TYPE)) {     // debugRelease
            return "https://passport.spider.com.cn/";
//            return "http://passport.spider.com.cn/";
        } else if (BT_ALPHA.equalsIgnoreCase(BuildConfig.BUILD_TYPE)) {         // alpha(preRelease)
            return "http://passporttest.spider.com.cn/";
        }

//        return "http://192.168.1.234:8146/passport/";                           // debug(developer)
        return "http://192.168.1.52:8146/";                           // debug(developer)
//        return "https://passport.spider.com.cn/";                               // 张威
//        return "http://192.168.1.126:8082/passport/";                           // 向明亨
//        return "http://passporttest.spider.com.cn/";                            // 主要为了测试充值功能所加，之后去掉，使用234
    }

    // 支付宝或银联充值使用的接口地址（参考电影票），TODO:后期可能需要后台，优化到支付系统中
    public static final String RECHARGE_HOST = getRechargeHost();

    @NonNull
    private static String getRechargeHost() {
        if (BT_RELEASE.equalsIgnoreCase(BuildConfig.BUILD_TYPE)) {              // release(生产)
            return "http://m.spider.com.cn/";
        } else if (BT_DEBUG_REL.equalsIgnoreCase(BuildConfig.BUILD_TYPE)) {     // debugRelease
            return "http://m.spider.com.cn/";
        }

        return "http://mtest.spider.com.cn/";                                   // debug(developer)
    }

    /**
     * 服务器对应的版本号
     */
    // LOCAL_HOST
    public static final String APP_VER_USER = "accountControl/400/";                    // 服务器地址 - 用户控制器（蜘蛛精）
    public static final String APP_VER_PRODUCT = "productControl/400/";                 // 服务器地址 - 产品控制器（蜘蛛精）
    public static final String APP_VER_AD = "adControl/400/";                           // 服务器地址 - 广告控制器（蜘蛛精）
    public static final String APP_VER_DRAFTS = "draftsControl/400/";                   // 服务器地址 - 刊期草稿箱控制器（蜘蛛精）
    public static final String APP_VER_PERIOD = "periodControl/400/";                   // 服务器地址 - 刊期控制器（蜘蛛精）

    // ACCOUNT_MGR_HOST
    public static final String APP_VER_ACCOUNT = "user/";                               // 服务器地址 - 账户控制器（账户管理系统）
    public static final String APP_VER_ACCOUNT_TPL = "user2/";                          // 服务器地址 - 账户控制器 - 第三方登录（账户管理系统）
    public static final String APP_PER_CENTER = "spiderPerCenter20/";                   // 服务器地址 - 个人中心（账户管理系统）

    /**
     * 使用H5时，第三方登录Wap站的拼接链接
     *
     * @Unused
     */
    public static final String WAP_LOGIN_URL = getWapLoginUrl();

    @NonNull
    private static String getWapLoginUrl() {
        StringBuffer sbUrl = new StringBuffer();
        // release: http://m.spider.com.cn/clientLogin.jsp?
        // debug: http://test.spider.com.cn:8086/spiderwap/clientLogin.jsp?
        sbUrl.append(LOCAL_HOST);
        sbUrl.append("clientLogin.jsp?");

        // release: goUrl=http://m.spider.com.cn/movie20/zydetail.html
        // debug: goUrl=http://test.spider.com.cn:8086/spiderwap/movie20/zydetail.html
        sbUrl.append("goUrl=");
        sbUrl.append(LOCAL_HOST);
        sbUrl.append("movie20/zydetail.html&hideSource=appsbt");
        return sbUrl.toString();
    }

    /**
     * TalkingData开关、版本检测（电影票接口）
     *
     * @Unused
     */
    public static final String FILM_URL = getFilmUrl();

    @NonNull
    private static String getFilmUrl() {
        if (BT_RELEASE.equalsIgnoreCase(BuildConfig.BUILD_TYPE)) {          // release(生产)
            return "http://film.spider.com.cn/huayins/";
        } else if (BT_DEBUG_REL.equalsIgnoreCase(BuildConfig.BUILD_TYPE)) { // debugRelease
            return "http://film.spider.com.cn/huayins/";
        }

        return "http://filmtest.spider.com.cn/huayins/";                    // debug(developer)
    }

    // ------------------ 接口名 -----------------
    // ----- 登录注册 -----
    /**
     * 常规用户登录
     */
    public static final String USER_LOGIN = APP_VER_ACCOUNT + "userLogin.action";

    /**
     * 第三方用户登录（TOKEN方式），非TOKEN方式：openMemberLogin.action
     */
    public static final String USER_THIRD_LOGIN = APP_VER_ACCOUNT_TPL + "userLogin.do";

    /**
     * 用户注册
     */
    public static final String USER_REGISTER = APP_VER_ACCOUNT + "userRegister.action";

    /**
     * 修改登录密码（记得旧密码）
     */
    public static final String CHANGE_LOGIN_PWD = APP_VER_ACCOUNT + "modifyPassword.action";

    /**
     * 找回密码（忘记旧密码）
     */
    public static final String RETRIEVE_PWD = APP_VER_ACCOUNT + "retrievePassword.action";

    /**
     * 验证短信验证码是否正确
     */
    public static final String JUDGE_VERIFY_CODE = APP_VER_ACCOUNT + "judgeVerifyCode.action";

    /**
     * 验证图片验证码是否正确（未使用）
     */
    public static final String JUDGE_VERIFY_IMG_CODE = APP_VER_ACCOUNT + "judgeVerifyIMGCode";

    /**
     * 无账号快捷登录
     */
    public static final String FAST_LOGIN = APP_PER_CENTER + "userLoginByRandom.do";

    /**
     * 获取图形验证码
     */
    public static final String GET_IMG_VERIFY_CODE = APP_VER_ACCOUNT + "imageVerifyCode.action";

    /**
     * 获取手机短信验证码
     */
    public static final String GET_SMS_VERIFY_CODE = APP_VER_ACCOUNT + "sendPhoneVerifyCode.action";

    /**
     * 绑定手机号
     */
    public static final String BIND_NEW_MOBILE = APP_VER_ACCOUNT + "bindPhone.action";

    /**
     * 修改手机号，需要两次验证新旧（手机号与验证码）
     */
    public static final String MODIFY_MOBILE = APP_VER_ACCOUNT + "modifyPhone.action";

    // ----- 个人中心 -----
    /**
     * 上传头像
     */
    public static final String UPLOAD_HEADER_IMG = APP_PER_CENTER + "uploadUserImage.do";

    /**
     * 交易/蛛元/现金
     */
    public static final String GET_CONSUME_DETAIL = APP_PER_CENTER + "getConsumeDetail.do";

    /**
     * 蛛元充值 - 已弃用
     */
    public static final String RECHARGE = APP_VER_ACCOUNT + "recharge.action";

    /**
     * 获取蛛元充值报文 - 支付宝（来源电影票APP）
     */
    public static final String APP_ALIPAY_JPAY_UTIL = "appalipayJPayUtil.action";

    /**
     * 获取蛛元充值报文 - 银联（来源电影票APP）
     */
    public static final String APP_YL_PAY_UTIL = "appylPayUtil.action";

    /**
     * 蛛元充值回调
     */
    public static final String RECHARGE_CALL_BACK = APP_VER_ACCOUNT + "rechargeCallBack.action";

    /**
     * 查询我的账单信息
     */
    public static final String GET_MY_BILLS = APP_VER_USER + "getMyBills.do";

    /**
     * 获取畅读/阅历图书列表
     */
    public static final String GET_READED_PAPERS = APP_VER_USER + "getReadedPapers.do";

    /**
     * 获取已购图书列表
     */
    public static final String GET_PURCHASE_PAPERS = APP_VER_USER + "getPurchasePapers.do";

    /**
     * APP升级
     */
    public static final String APP_UPDATE = APP_VER_USER + "appUpdate.do";

    /**
     * 用户登录状态验证
     */
    public static final String LOGIN_STATUS_VERIFY = APP_VER_ACCOUNT + "loginStatusVerify.action";

    /**
     * 用户退出登录
     */
    public static final String USER_LOGOUT = APP_VER_ACCOUNT + "userLogout.action";

    /**
     * 修改用户信息
     */
    public static final String SAVE_USER_INFO = APP_VER_ACCOUNT + "saveUserInfo.action";

    /**
     * 获取用户详细信息
     */
    public static final String GET_USER_INFO = APP_VER_ACCOUNT + "getUserInfo.action";

    /**
     * 获取他人用户信息
     */
    public static final String GET_OTHER_UINFO = APP_VER_USER + "getOtherUserInfo.do";

    /**
     * 获取蜘蛛精用户信息（账户管理系统与蜘蛛精系统是分开的，所以分为两个接口获取用户信息）
     */
    public static final String GET_READER_USER_INFO = APP_VER_USER + "getGoblinUserInfo.do";

    /**
     * 获取可支付方式列表
     */
    public static final String GET_VALID_PAY_TYPE_LIST = APP_VER_USER + "getValidPaytypeList.do";

    // ----- 阅读流程（共31个接口，开发21接口）- 请求URLs -----
    /**
     * 获取首页封面图
     */
    public static final String GET_MAIN_COVER = APP_VER_AD + "getMainCover.do";

    /**
     * 获取头部Banner列表
     */
    public static final String GET_BANNERS = APP_VER_AD + "getBanners.do";


    /**
     * 获取发现列表 - PRD简化后，暂时无需开发，后续版本迭代
     */
    public static final String GET_DISCOVERY_LIST = APP_VER_USER + "getDiscoveryList.do";

    /**
     * 热读畅销列表 - PRD简化后，暂时无需开发，后续版本迭代
     */
    public static final String GET_HOT_READS_AND_SELLS = APP_VER_USER + "getHotReadsAndSells.do";

    /**
     * 特价专区列表 - PRD简化后，暂时无需开发，后续版本迭代
     */
    public static final String GET_SPECIAL_OFFER_LIST = APP_VER_USER + "getSpecialOfferList.do";

    /**
     * 独家免费列表 - PRD简化后，暂时无需开发，后续版本迭代
     */
    public static final String GET_EXCLUSIVE_FREE_LIST = APP_VER_USER + "getExclusiveFreeList.do";

    /**
     * 获取精品杂志列表 - PRD简化后，暂时无需开发，后续版本迭代
     */
    public static final String GET_ELITE_MAGS = APP_VER_USER + "getEliteMags.do";

    /**
     * 获取更多列表
     */
    public static final String GET_MORE_MAGS = APP_VER_PRODUCT + "getMoreMags.do";

    /**
     * 搜索
     */
    public static final String SEARCH = APP_VER_PRODUCT + "search.do";

    /**
     * 同步用户阅历信息
     */
    public static final String SYNC_USER_SEE_INFOS = APP_VER_USER + "syncUserSeeInfos.do";

    /**
     * 获取商城列表
     */
    public static final String GET_MALL_LIST = APP_VER_PRODUCT + "getMallList.do";

    /**
     * 获取热门专题更多列表 - PRD简化后，暂时无需开发，后续版本迭代
     */
    public static final String GET_MORE_HOT_SUBS = APP_VER_USER + "getMoreHotSubs.do";

    /**
     * 获取热门专题详情 - PRD简化后，暂时无需开发，后续版本迭代
     */
    public static final String GET_HOT_SUB_DETAILS = APP_VER_USER + "getHotSubDetails.do";

    /**
     * 获取杂志刊期详情
     */
    public static final String GET_ISSUE_DETAILS = APP_VER_PRODUCT + "getIssueDetails.do";

    /**
     * 获取刊期目录与书签列表
     */
    public static final String GET_ISSUE_COLUMNS = APP_VER_PRODUCT + "getIssueColumns.do";

    /**
     * 获取刊期文章列表
     */
    public static final String GET_ISSUE_ARTICLE_LIST = APP_VER_PRODUCT + "getIssueArticleList.do";

    /**
     * 获取刊期某篇文章
     */
    public static final String GET_ISSUE_ARTICLE = APP_VER_PRODUCT + "getIssueArticle.do";

    /**
     * 添加/删除书签
     */
    public static final String OPER_LABEL = APP_VER_USER + "operLabel.do";

    /**
     * 添加/删除喜欢
     */
    public static final String OPER_FAV = APP_VER_USER + "operFav.do";

    /**
     * 换一批同类推荐刊物列表
     */
    public static final String CHANGE_SIMILAR_RCMDS = APP_VER_PRODUCT + "changeSimilarRcmds.do";

    /**
     * 添加到书架
     */
    public static final String ADD_TO_BOOKSHELF = APP_VER_USER + "add2Bookshelf.do";

    /**
     * 批量移出书架
     */
    public static final String REMOVE_FORM_BOOKSHELF = APP_VER_USER + "removeFormBookshelf.do";

    /**
     * 评论杂志刊期 - PRD简化后，暂时无需开发，后续版本迭代
     */
    public static final String COMMENT_ISSUE = APP_VER_USER + "commentIssue.do";

    /**
     * 获取评论列表 - PRD简化后，暂时无需开发，后续版本迭代
     */
    public static final String GET_COMMENT_LIST = APP_VER_USER + "getCommentList.do";

    /**
     * 设置支付密码
     */
    public static final String SET_PAY_PASSWORD = APP_PER_CENTER + "setPayPassword.do";

    /**
     * 获取支付订单号（现金，蛛元，第三方支付（微信，支付宝，银联） / 打赏）
     */
    public static final String GET_ORDER_PAY_ID = APP_VER_USER + "getOrderPayId.do";

    /**
     * 获取订单状态
     */
    public static final String GET_ORDER_PAY_STATUS = APP_VER_USER + "getOrderPayStatus.do";

    /**
     * 蛛元/现金确认支付
     */
    public static final String CONFIRM_PAYMENT = APP_VER_USER + "confirmPayment.do";

    /**
     * 获取订单号
     */
    public static final String GET_ORDER_ID = APP_VER_USER + "getOrderId.do";

    /**
     * 获取购买的畅读卡类型列表
     */
    public static final String GET_VIVA_CARD_TYPE_LIST = APP_VER_USER + "getVivaCardTypeList.do";

    /**
     * 绑定畅读卡
     */
    public static final String BIND_VIVA_CARD = APP_VER_USER + "bindVivaCard.do";

    /**
     * 判断是否具有按月支付的能力
     */
    public static final String IS_AVAILABLE_BY_CREDIT = APP_VER_USER + "isAvailableByCredit.do";

    /**
     * 查询书架图书列表
     */
    public static final String GET_SHELF_BOOKS = APP_VER_USER + "getShelfBooks.do";

    /**
     * 添加刊期到畅读或阅历列表
     */
    public static final String ADD_TO_VIVAS_OR_SEES = APP_VER_USER + "addToVivasOrSees.do";

    // ----- 工作室&创造模块（共17个接口）- 请求URLs -----

    /**
     * 获取我的文章&我的杂志数量
     */
    public static final String GET_JOURNAL_AND_ARTICLE_COUNT = APP_VER_USER + "getJournalAndArticleCount.do";

    /**
     * 获取我的文章列表
     */
    public static final String GET_USER_ARTICLE_LIST = APP_VER_DRAFTS + "getUserArticleList.do";

    /**
     * 获取某篇文章详情
     */
    public static final String GET_ARTICLE_DETAILS = APP_VER_DRAFTS + "getArticleDetails.do";

    /**
     * 投入稿件基地&从稿件基地移出
     */
    public static final String OPER_ARTICLE_STATU = APP_VER_DRAFTS + "operArticleStatus.do";

    /**
     * 删除文章
     */
    public static final String DELETE_ARTICLE = APP_VER_DRAFTS + "deleteArticle.do";

    /**
     * 获取我的刊期列表
     */
    public static final String GET_MY_ISSUE_LIST = APP_VER_PERIOD + "getMyIssueList.do";

    /**
     * 获取稿件基地中稿件的列表
     */
    public static final String GET_MANUSCRIPT_LIST = APP_VER_DRAFTS + "getManuscriptList.do";

    /**
     * 保存或申请发布刊期
     */
    public static final String OPER_ISSUE_INFO = APP_VER_PERIOD + "operIssueInfo.do";

    /**
     * 获取保存或发布的刊期详情
     */
    public static final String GET_ISSUE_INFO = APP_VER_PERIOD + "getSaveOrPubIssue.do";

    /**
     * 取消刊期待审核状态
     */
    public static final String CANCEL_ISSUE_STATE = APP_VER_PERIOD + "cancelIssueWaitauditStatus.do";

    /**
     * 获取我的刊物列表
     */
    public static final String GET_MY_JOURNAL_LIST = APP_VER_PRODUCT + "getMyJournalList.do";

    /**
     * 获取添加文章列表
     */
    public static final String GET_ADD_ARTICLE_LIST = APP_VER_DRAFTS + "getAddArticleList.do";

    /**
     * 创作刊物
     */
    public static final String CREATE_MY_JOURNAL = APP_VER_PRODUCT + "createMyJournal.do";

    /**
     * 判断是否具有提现的能力
     */
    public static final String GET_ABILITY_OF_WCASH = APP_VER_USER + "getAbilityOfWCash.do";

    /**
     * 获取绑定的银行卡列表
     */
    public static final String GET_BANK_CARDS = APP_VER_USER + "getBankCards.do";

    /**
     * 获取银行列表
     */
    public static final String GET_BANK_LIST = APP_VER_USER + "getBankList.do";

    /**
     * 绑定银行卡
     */
    public static final String BIND_BANK_CARD = APP_VER_USER + "bindBankCard.do";

    /**
     * 获取提现订单号
     */
    public static final String GET_WCASH_ORDER_ID = APP_VER_USER + "getWCashOrderId.do";

    /**
     * 获取系统消息列表
     */
    public static final String GET_SYS_MSGS = APP_VER_USER + "getSysMsgs.do";

    /**
     * 提交投诉内容
     */
    public static final String SUBMIT_COMPLAINTS = APP_VER_USER + "submitComplaints.do";
}
