package com.senzer.mylove.api;

import com.google.gson.Gson;

import java.io.Serializable;

import lombok.Data;

/**
 * ProjectName: DataResponse
 * Description: 返回数据
 * <p>
 * author: JeyZheng
 * version: 4.0
 * created at: 2016/8/16 17:33
 */
@Data
public class DataResponse<T> implements Serializable {

    // inject to field instead of getter and setter
    private String code;                // 0：表示成功，非0：失败
    private String msg;                 // 接口返回错误码（F1001，F1002。。。），详见《针对用户系统接口返回错误码解释》
    private String description;         // 结果描述（缺少必要参数，验签失败。。。），详见《针对用户系统接口返回错误码解释》

    private T data;

    @Override
    public String toString() {
        StringBuffer response = new StringBuffer();
        response.append("{");
        response.append("result:");
        response.append("\"");
        response.append(code);
        response.append("\"");
        response.append(",");
        response.append("message:");
        response.append("\"");
        response.append(msg);
        response.append("\"");

        if (null != data) {
            String srcData = new Gson().toJson(data);
            response.append(",");
            response.append("data:");
            response.append(srcData);
        }
        response.append("}");

        return response.toString();
    }
}
