package com.senzer.mylove.entity.vo;

import java.io.Serializable;
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
public class Banners implements Serializable {
    private List<BannerEntity> banners;         // Banners链接

    @Data
    public class BannerEntity implements Serializable {
        private String id;                      // Banner的唯一标识
        private String title;                   // Banner的标题
        private String cover;                   // Banner的封面图
        private String detailsUrl;              // Banner的详情H5页面
    }
}
