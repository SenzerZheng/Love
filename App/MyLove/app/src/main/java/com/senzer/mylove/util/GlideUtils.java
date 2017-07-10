package com.senzer.mylove.util;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.senzer.mylove.R;
import com.senzer.mylove.app.AppContext;

/**
 * ProjectName: SpiderReaderAndroid
 * Description: 对Glide增加一层封装
 * <p>
 * DiskCacheStrategy.NONE 什么都不缓存，就像刚讨论的那样
 * DiskCacheStrategy.SOURCE 仅仅只缓存原来的全分辨率的图像。在我们上面的例子中，将会只有一个 1000x1000 像素的图片
 * DiskCacheStrategy.RESULT 仅仅缓存最终的图像（根据控件大小格式化后的图像），即，降低分辨率后的（或者是转换后的）
 * DiskCacheStrategy.ALL 缓存所有版本的图像（默认行为）
 * <p>
 * author: JeyZheng
 * version: 2.0
 * created at: 2016/9/22 9:42
 */
public class GlideUtils {
    /**
     * 加载图片（自定义placeHolder, skip, strategy）
     *
     * @param context
     * @param imgUrl
     * @param imageView
     * @param placeHolder
     * @param skip        True to allow the resource to skip the memory cache.
     * @param strategy    The strategy to use.
     */
    public static void loadImg(Context context, String imgUrl, ImageView imageView,
                               int placeHolder, boolean skip, DiskCacheStrategy strategy) {
        Glide.with(context).load(imgUrl)
                .skipMemoryCache(skip)                              // 内存缓存（默认，可不加）
                .diskCacheStrategy(strategy)                        // 硬盘缓存（默认，可不加）
                .crossFade()
//                .centerCrop()
                .placeholder(placeHolder)
//                .placeholder(R.mipmap.loading_animimation)
                .into(imageView);
    }

    /**
     * 缓存加载图片（自定义placeHolder）
     * NOTE: 此处使用asBitmap规范，避免首次加载图片会有压缩现象
     *
     * @param context
     * @param imgUrl
     * @param imageView
     * @param placeHolder
     */
    public static void loadImg(Context context, String imgUrl, ImageView imageView, int placeHolder) {
        Glide.with(context).load(imgUrl)
                .asBitmap()
                .skipMemoryCache(false)                             // 内存缓存（默认，可不加）
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)        // 硬盘缓存（缓存源数据，防止图片显示变绿）
//                .centerCrop()
                .placeholder(placeHolder)
//                .placeholder(R.mipmap.loading_animimation)
                .into(imageView);
    }

    /**
     * 缓存加载图片
     *
     * @param context
     * @param imgUrl
     * @param imageView
     */
    public static void loadImg(Context context, String imgUrl, ImageView imageView) {
        Glide.with(context).load(imgUrl)
                .skipMemoryCache(false)                             // 内存缓存（默认，可不加）
                .diskCacheStrategy(DiskCacheStrategy.ALL)           // 硬盘缓存（默认，可不加）
                .crossFade()
//                .centerCrop()
                .placeholder(R.mipmap.place_holder)
//                .placeholder(R.mipmap.loading_animimation)
                .into(imageView);
    }

    /**
     * 无缓存加载图片
     *
     * @param context
     * @param imgUrl
     * @param imageView
     */
    public static void loadImgNoCache(Context context, String imgUrl, ImageView imageView) {
        Glide.with(context).load(imgUrl)
                .skipMemoryCache(true)                                  // 跳过内存缓存
                .diskCacheStrategy(DiskCacheStrategy.NONE)              // 跳过硬盘缓存
                .crossFade()
                .placeholder(R.mipmap.place_holder)
                .into(imageView);
    }

    /**
     * 缓存加载图片，无placeHolder
     * NOTE: 此处使用asBitmap规范，避免首次加载图片会有压缩现象
     *
     * @param context
     * @param imgUrl
     * @param imageView
     */
    public static void loadImgNoHolder(Context context, String imgUrl, ImageView imageView) {
        Glide.with(context).load(imgUrl)
                .asBitmap()
                .skipMemoryCache(false)                                     // 内存缓存（默认，可不加）
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)                // 硬盘缓存（缓存源数据，防止图片显示变绿）
                .into(imageView);
    }

    /**
     * 缓存加载图片，无placeHolder
     *
     * @param context
     * @param imgUrl
     * @param imageView
     */
    public static void loadImgNoHolderCache(Context context, String imgUrl, ImageView imageView) {
        Glide.with(context).load(imgUrl)
                .skipMemoryCache(true)                                      // 内存缓存（无）
                .diskCacheStrategy(DiskCacheStrategy.NONE)                  // 硬盘缓存（无）
                .crossFade()
                .into(imageView);
    }

    public static void loadGif(Context context, String imgUrl, ImageView imageView) {
        Glide.with(context).load(imgUrl)
                .asGif()
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(imageView);
    }

    public static void loadGif(Context context, int resId, ImageView imageView) {
        Glide.with(context).load(resId)
                .asGif()
//                .skipMemoryCache(false)                             // 内存缓存（默认，可不加）
//                .diskCacheStrategy(DiskCacheStrategy.ALL)           // 硬盘缓存（默认，可不加）
                .into(imageView);
    }

    public static void loadGifNoCache(Context context, String imgUrl, ImageView imageView) {
        Glide.with(context).load(imgUrl)
                .asGif()
                .skipMemoryCache(true)                                  // 跳过内存缓存
                .diskCacheStrategy(DiskCacheStrategy.NONE)              // 跳过硬盘缓存
                .into(imageView);
    }

    /**
     * 清除图片内存缓存
     */
    public static void clearMemory() {
        Glide.get(AppContext.getInstance()).clearMemory();
    }

    /**
     * 清除图片磁盘缓存
     */
    public static void clearDiskCache() {
        Glide.get(AppContext.getInstance()).clearDiskCache();
    }

    /**
     * 清除图片所有缓存
     */
    public static void clearImageAllCache() {
        Glide.get(AppContext.getInstance()).clearDiskCache();
    }
}
