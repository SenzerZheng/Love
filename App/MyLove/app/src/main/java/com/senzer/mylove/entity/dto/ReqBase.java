package com.senzer.mylove.entity.dto;

import java.io.Serializable;

import lombok.Data;

/**
 * ProjectName: ReqBase
 * Description: 请求参数 - 基类
 * <p>
 * author: JeyZheng
 * version: 2.0
 * created at: 2016/9/28 10:15
 */
@Data
public class ReqBase implements Serializable {
    private String userId;                      // 用户编号（未登录时，为空）
    private String width;                       // 当前设备的分辨率宽度

    public ReqBase(String userId, String width) {
        this.userId = userId;
        this.width = width;
    }
}
