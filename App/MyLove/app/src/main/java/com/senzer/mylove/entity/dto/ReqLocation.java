package com.senzer.mylove.entity.dto;

import lombok.Data;

/**
 * ProjectName: ReqLocation
 * Description: 定位信息JSON请求的Body
 * <p>
 * review by chenpan, wangkang, wangdong 2017/7/12
 * edit by JeyZheng 2017/7/12
 * author: JeyZheng
 * version: 4.0
 * created at: 2017/7/12 18:37
 */
@Data
public class ReqLocation {
    private String locationInfo;

    public ReqLocation(String locationInfo) {
        this.locationInfo = locationInfo;
    }
}
