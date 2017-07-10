package com.senzer.mylove.entity.vo;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

import lombok.Data;

/**
 * ProjectName: Banners
 * Description: 首页的封面图
 *
 * review by chenpan, wangkang, wangdong 2017/7/10
 * edit by JeyZheng 2017/7/10
 * author: JeyZheng
 * version: 4.0
 * created at: 2017/7/10 18:07
 */
@Data
public class Banners implements Parcelable {
    private List<BannerEntity> banners;         // Banners链接

    protected Banners(Parcel in) {
        banners = in.createTypedArrayList(BannerEntity.CREATOR);
    }

    public static final Creator<Banners> CREATOR = new Creator<Banners>() {
        @Override
        public Banners createFromParcel(Parcel in) {
            return new Banners(in);
        }

        @Override
        public Banners[] newArray(int size) {
            return new Banners[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(banners);
    }
}
