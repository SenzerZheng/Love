package com.senzer.mylove.api;

import com.alibaba.fastjson.annotation.JSONField;
import com.google.gson.Gson;

import java.io.Serializable;

/**
 * ProjectName: DataResponse
 * Description: 返回数据
 * <p>
 * author: JeyZheng
 * version: 4.0
 * created at: 2016/8/16 17:33
 */
public class DataResponse<T> implements Serializable {

    private String result;              // 0：表示成功，非0：失败
    private String message;             // 接口返回错误码（F1001，F1002。。。），详见《针对用户系统接口返回错误码解释》
    private String description;         // 结果描述（缺少必要参数，验签失败。。。），详见《针对用户系统接口返回错误码解释》

    private T data;

    @JSONField(name = "result")
    public String getCode() {
        return result;
    }

    @JSONField(name = "result")
    public void setResult(String result) {
        this.result = result;
    }

    @JSONField(name = "message")
    public String getMessage() {
        return message;
    }

    @JSONField(name = "message")
    public void setMessage(String message) {
        this.message = message;
    }

    @JSONField(name = "description")
    public String getDescription() {
        return description;
    }

    @JSONField(name = "description")
    public void setDescription(String message) {
        this.description = message;
    }

    @JSONField(name = "data")
    public T getData() {
        return data;
    }

    @JSONField(name = "data")
    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        StringBuffer response = new StringBuffer();
        response.append("{");
        response.append("result:");
        response.append("\"");
        response.append(result);
        response.append("\"");
        response.append(",");
        response.append("message:");
        response.append("\"");
        response.append(message);
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
