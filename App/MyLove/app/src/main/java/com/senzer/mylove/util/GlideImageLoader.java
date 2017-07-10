package com.senzer.mylove.util;

import android.content.Context;
import android.widget.ImageView;

import com.senzer.mylove.R;
import com.senzer.mylove.entity.vo.Banners;
import com.youth.banner.loader.ImageLoader;


/**
 * ProjectName: GlideImageLoader
 * Description: 使用Glide加载图片
 * <p>
 * review by chenpan, wangkang, wangdong 2017/5/10
 * edit by JeyZheng 2017/5/10
 * author: JeyZheng
 * version: 4.0
 * created at: 2017/5/10 18:07
 */
public class GlideImageLoader extends ImageLoader {

    @Override
    public void displayImage(Context context, Object path, ImageView iv) {
        Banners.BannerEntity data = (Banners.BannerEntity) path;
        GlideUtils.loadImg(context, data.getCover(), iv, R.mipmap.place_holder);
    }
}