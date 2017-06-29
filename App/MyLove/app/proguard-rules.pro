# To enable ProGuard in your project, edit project.properties
# to define the proguard.config property as described in that file.
#
# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in ${sdk.dir}/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the ProGuard
# include property in project.properties.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
# -keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
# }
# 指定代码的压缩级别
-optimizationpasses 5
# 是否使用大小写混合
-dontusemixedcaseclassnames
# 是否混淆第三方jar
-dontskipnonpubliclibraryclasses
# 混淆时是否做预校验
-dontpreverify
# 混淆时是否记录日志
-verbose
# 混淆时所采用的算法
-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*
-ignorewarnings

# 保持哪些类不被混淆
# 同样如果你觉得麻烦，就直接将BaseAdpater换成Adapter
-keep public class * extends android.widget.BaseAdapter
-keep public class * extends android.widget.CusorAdapter
-keep public class * extends android.app.Fragment
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference
-keep public class com.android.vending.licensing.ILicensingService
-keep public class * extends com.spider.film.BaseActivity

-keepclasseswithmembernames class * {
    native <methods>;
}

-keepclasseswithmembernames class * {
    public <init>(android.content.Context, android.util.AttributeSet);
}

-keepclasseswithmembernames class * {
    public <init>(android.content.Context, android.util.AttributeSet, int);
}

-keepclassmembers class * extends android.app.Activity {
   public void *(android.view.View);  
}

-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

-keep class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator *;
}

-keep public class com.spider.film.R$*{
    public static final int *;
}

-dontshrink
-dontoptimize
-dontwarn com.google.android.maps.**
-dontwarn android.webkit.WebView
-dontwarn com.umeng.**
-dontwarn com.tencent.weibo.sdk.**
-dontwarn com.facebook.**

-keep enum com.facebook.**
-keepattributes Exceptions,InnerClasses,Signature
-keepattributes *Annotation*
-keepattributes SourceFile,LineNumberTable

-keep public interface com.facebook.**
-keep public interface com.tencent.**
-keep public interface com.umeng.socialize.**
-keep public interface com.umeng.socialize.sensor.**
-keep public interface com.umeng.scrshot.**

-keep public class com.umeng.socialize.* {*;}
-keep public class javax.**
-keep public class android.webkit.**

-keep class com.facebook.**
-keep class com.umeng.scrshot.**
-keep public class com.tencent.** {*;}
-keep class com.umeng.socialize.sensor.**

-keep class com.tencent.mm.sdk.modelmsg.WXMediaMessage {*;}

-keep class com.tencent.mm.sdk.modelmsg.** implements com.tencent.mm.sdk.modelmsg.WXMediaMessage$IMediaObject {*;}

-keep class im.yixin.sdk.api.YXMessage {*;}
-keep class im.yixin.sdk.api.** implements im.yixin.sdk.api.YXMessage$YXMessageData{*;}
-keep public class [your_pkg].R$*{
    public static final int *;
}

-dontwarn android.support.**
-dontwarn com.google.android.maps.**
-dontwarn com.slidingmenu.lib.app.SlidingMapActivity

-keep class android.support.** { *; }  
-keep class com.slidingmenu.** { *; }  
-keep interface com.slidingmenu.** { *; }  
  
-keep public class * implements java.io.Serializable {  
    public *;  
}  
-keepclassmembers class com.spider.film.BankPayActivity$PayJava {  
 public *;  
}
-keepclassmembers class com.spider.film.BankPayActivity$CloseWap {  
 public *;  
}
-keepclassmembers class com.spider.film.BankPayActivity$PaySuccess {  
 public *;  
}
-keepclassmembers class com.spider.film.BankPayActivity$GetMessage {
 public *;  
}
-keepclassmembers class com.spider.film.BankPayActivity$JavaScriptObject {
 public *;
}
-keepclassmembers class com.spider.film.AdvertWebViewActivity$GetMessage {
 public *;  
}
-keepclassmembers class com.spider.film.AdvertWebViewActivity$WapLogin {
 public *;
}
-keepclassmembers class com.spider.film.AdvertWebViewActivity$JavaScriptObject {
 public *;  
}
-keepclassmembers class com.spider.film.AdvertWebViewActivity$JavaScriptCityInfo {
 public *;  
}
-keepclassmembers class com.spider.film.view.NetPayWebView$PayJava {  
 public *;  
}
-keepclassmembers class com.spider.film.view.NetPayWebView$CloseWap {  
 public *;  
}
-keepclassmembers class com.spider.film.view.NetPayWebView$PaySuccess {  
 public *;  
}
-keepclassmembers class com.spider.film.view.NetPayWebView$GetMessage {
 public *;  
}
-keepclassmembers class com.spider.film.view.NetPayWebView$JavaScriptObject {
 public *;  
}
-keepclassmembers class com.spider.film.BounsWebActivity$GetMessage {
 public *;
}
-keepclassmembers class com.spider.film.BounsWebActivity$WapLogin {
 public *;
}
-keepclassmembers class com.spider.film.BounsWebActivity$JavaScriptObject {
 public *;
}
-keepclassmembers class com.spider.film.BounsWebActivity$JavaScriptCityInfo {
 public *;
}
-keepclassmembers class com.spider.film.BounsWebActivity$WapController {
 public *;
}
-keepattributes *Annotation*
-keepattributes *JavascriptInterface*  
-keepclassmembers class * implements java.io.Serializable {  
    static final long serialVersionUID;  
    private static final java.io.ObjectStreamField[] serialPersistentFields;  
    private void writeObject(java.io.ObjectOutputStream);  
    private void readObject(java.io.ObjectInputStream);  
    java.lang.Object writeReplace();  
    java.lang.Object readResolve();  
}  
-dontwarn com.alibaba.fastjson.**
# -libraryjars libs/fastjson-1.1.43.android.jar
-keep class com.alibaba.fastjson.** { *; } 
-dontwarn net.soureceforge.pinyin4j.**
-dontwarn demo.**
# -libraryjars libs/pinyin4j-2.5.0.jar
-keep class net.sourceforge.pinyin4j.** { *;}
-keep class demo.** { *;}
-keep class com.squareup.okhttp.**{*;}
-dontwarn com.squareup.okhttp.**
-keep class com.mato.** { *; } 
-dontwarn com.mato.**
-keepattributes Exceptions, Signature, InnerClasses, EnclosingMethod

# -libraryjars libs/jpush-android-2.0.5.jar
-dontwarn cn.jpush.**
-keep class cn.jpush.** { *; }

# -dontwarn com.baidu.**
-dontwarn vi.com.gdi.bgl.android.java.**
-keep class vi.com.gdi.bgl.android.java.** {*;}

-keep class com.baidu.** { *; } 
-keep class vi.com.gdi.bgl.android.**{*;}
-keep class vi.com.gdi.bgl.**{*;}
# -libraryjars libs/baidumapapi_map_v3_6_1.jar
# -libraryjars libs/armeabi/libBaiduMapSDK_map_v3_6_1.so

# -libraryjars libs/baidumapapi_base_v3_6_1.jar
# -libraryjars libs/armeabi/libBaiduMapSDK_base_v3_6_1.so

# -libraryjars libs/locSDK_6.13.jar
# -libraryjars libs/armeabi/liblocSDK6a.so

-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep class com.tencent.android.tpush.**  {* ;}
-keep class com.tencent.mid.**  {* ;}

-dontwarn com.google.gson.**

# -libraryjars libs/UPPayAssistEx.jar
# -libraryjars libs/UPPayPluginExPro.jar
# -dontwarn com.unionpay.**
-keep class com.unionpay.**{*;}

-keep  public class java.util.HashMap {
	public <methods>;
}
-keep  public class java.lang.String {
	public <methods>;
}
-keep  public class java.util.List {
	public <methods>;
}

-keepclasseswithmembernames class * {
    native <methods>;
}

-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet);
}

-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet, int);
}

-keepclassmembers class * extends android.app.Activity {
   public void *(android.view.View);
}

-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

-keep class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator *;
}

# ProGuard configurations for NetworkBench Lens
-keep class com.networkbench.** { *; }
-dontwarn com.networkbench.**
-keepattributes Exceptions, Signature, InnerClasses
# End NetworkBench Len

-keep public class com.tendcloud.** { public protected *;}

# 环信IM混淆
-keep class com.easemob.** {*;}
-keep class org.jivesoftware.** {*;}
-keep class org.apache.** {*;}
-dontwarn  com.easemob.**

# 支付宝混淆
# -libraryjars libs/alipaySDK-20150818.jar
-keep class com.alipay.android.app.IAlixPay{*;}
-keep class com.alipay.android.app.IAlixPay$Stub{*;}
-keep class com.alipay.android.app.IRemoteServiceCallback{*;}
-keep class com.alipay.android.app.IRemoteServiceCallback$Stub{*;}
-keep class com.alipay.sdk.app.PayTask{ public *;}
-keep class com.alipay.sdk.app.AuthTask{ public *;}
# -dontwarn com.alipay.**

# 翼支付
# -libraryjars libs/BestpaySDK-V2.0.1.jar
 -keep class com.bestpay.app.PaymentTask{*;}
 -keep class com.bestpay.app.H5PayActivity{*;}
 -keep class com.bestpay.app.MyWebViewClient{*;}
 -keep class com.bestpay.app.MyWebViewClientError{*;}
 -keep class com.bestpay.app.PassGuardManager{*;}
 -keep class com.bestpay.plugin.Plugin{*;}
 -keep class com.bestpay.app.**{*;}
 -keep class com.bestpay.ui.ProgressWebView{*;}
 -keep public class com.bestpay.db.ResultInfo {
	public <methods>;
 }
 -keep public class com.bestpay.db.AccountInfo {
	public <methods>;
 }
 -keep public class com.bestpay.db.BestPayDao {
	public <methods>;
 }
-dontwarn com.bestpay.**
-keep class com.bestpay.**{*;}

-keep class butterknife.** { *; }
-dontwarn butterknife.internal.**
-keep class **$$ViewBinder { *; }

-keepclasseswithmembernames class * {
    @butterknife.* <fields>;
}

-keepclasseswithmembernames class * {
    @butterknife.* <methods>;
}

# 招行SDK
-keepclasseswithmembers class cmb.pb.util.CMBKeyboardFunc {
    public <init>(android.app.Activity);
    public boolean HandleUrlCall(android.webkit.WebView,java.lang.String);
    public void callKeyBoardActivity();
}

# 友盟混淆
-dontshrink
-dontoptimize
-dontwarn com.google.android.maps.**
-dontwarn android.webkit.WebView
-dontwarn com.umeng.**
-dontwarn com.tencent.weibo.sdk.**
-dontwarn com.facebook.**
-keep public class javax.**
-keep public class android.webkit.**
-dontwarn android.support.v4.**
-keep enum com.facebook.**
-keepattributes Exceptions,InnerClasses,Signature
-keepattributes *Annotation*
-keepattributes SourceFile,LineNumberTable

-keep public interface com.facebook.**
-keep public interface com.tencent.**
-keep public interface com.umeng.socialize.**
-keep public interface com.umeng.socialize.sensor.**
-keep public interface com.umeng.scrshot.**

-keep public class com.umeng.socialize.* {*;}


-keep class com.facebook.**
-keep class com.facebook.** { *; }
-keep class com.umeng.scrshot.**
-keep public class com.tencent.** {*;}
-keep class com.umeng.socialize.sensor.**
-keep class com.umeng.socialize.handler.**
-keep class com.umeng.socialize.handler.*
-keep class com.tencent.mm.sdk.modelmsg.WXMediaMessage {*;}
-keep class com.tencent.mm.sdk.modelmsg.** implements com.tencent.mm.sdk.modelmsg.WXMediaMessage$IMediaObject {*;}

-keep class im.yixin.sdk.api.YXMessage {*;}
-keep class im.yixin.sdk.api.** implements im.yixin.sdk.api.YXMessage$YXMessageData{*;}

-dontwarn twitter4j.**
-keep class twitter4j.** { *; }

-keep class com.tencent.** {*;}
-dontwarn com.tencent.**
-keep public class com.umeng.soexample.R$*{
     public static final int *;
 }
-keep public class com.umeng.soexample.R$*{
     public static final int *;
 }
-keep class com.tencent.open.TDialog$*
-keep class com.tencent.open.TDialog$* {*;}
-keep class com.tencent.open.PKDialog
-keep class com.tencent.open.PKDialog {*;}
-keep class com.tencent.open.PKDialog$*
-keep class com.tencent.open.PKDialog$* {*;}

-keep class com.sina.** {*;}
-dontwarn com.sina.**
-keep class  com.alipay.share.sdk.** {
    *;
 }
-keepnames class * implements android.os.Parcelable {
     public static final ** CREATOR;
 }

-keep class com.linkedin.** { *; }
-keepattributes Signature

# 高德地图混淆
# 3D 地图 V5.0.0之前：
-keep   class com.amap.api.maps.**{*;}
-keep   class com.autonavi.amap.mapcore.*{*;}
-keep   class com.amap.api.trace.**{*;}

# 3D 地图 V5.0.0之后：
-keep   class com.amap.api.maps.**{*;}
-keep   class com.autonavi.**{*;}
-keep   class com.amap.api.trace.**{*;}

# 定位
-keep class com.amap.api.location.**{*;}
-keep class com.amap.api.fence.**{*;}
-keep class com.autonavi.aps.amapapi.model.**{*;}

# 搜索
-keep   class com.amap.api.services.**{*;}

# 2D地图
-keep class com.amap.api.maps2d.**{*;}
-keep class com.amap.api.mapcore2d.**{*;}

# 导航
-keep class com.amap.api.navi.**{*;}
-keep class com.autonavi.**{*;}