package com.senzer.mylove.entity.dto;

import java.io.Serializable;

import lombok.Data;

/**
 * ProjectName: ConfigResp
 * Description: 配置实体类
 * <p>
 * author: JeyZheng
 * version: 1.0.0
 * created at: 2017/7/18 14:12
 */
@Data
public class ConfigResp implements Serializable {
    public static final String IS_UPLOAD_IMG_NO = "0";
    public static final String IS_UPLOAD_IMG_YES = "1";

    private String isUploadImage;
}
