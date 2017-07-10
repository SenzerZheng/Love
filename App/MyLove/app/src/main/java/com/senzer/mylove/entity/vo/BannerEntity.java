package com.senzer.mylove.entity.vo;

import android.os.Parcel;
import android.os.Parcelable;

import lombok.Data;

/**
 * ProjectName: MyLove
 * Description:
 * <p>
 * author: JeyZheng
 * version: 2.0
 * created at: 2017/7/10 18:51
 */
@Data
public class BannerEntity implements Parcelable {

    private int id;                         // Banner的唯一标识
    private String title;                   // Banner的标题
    private String cover;                   // Banner的封面图
    private String detailsUrl;              // Banner的详情H5页面
    private boolean isBig;                  // Banner是否为大图

    protected BannerEntity(Parcel in) {
        id = in.readInt();
        title = in.readString();
        cover = in.readString();
        detailsUrl = in.readString();
        isBig = in.readInt() == 1;
    }

    public static final Creator<BannerEntity> CREATOR = new Creator<BannerEntity>() {
        @Override
        public BannerEntity createFromParcel(Parcel in) {
            return new BannerEntity(in);
        }

        @Override
        public BannerEntity[] newArray(int size) {
            return new BannerEntity[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(title);
        dest.writeString(cover);
        dest.writeString(detailsUrl);
        dest.writeInt(isBig ? 1 : 0);
    }
}
