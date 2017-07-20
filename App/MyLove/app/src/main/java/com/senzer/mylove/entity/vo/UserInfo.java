package com.senzer.mylove.entity.vo;


import java.io.Serializable;

import lombok.Data;

/**
 * ProjectName: UserInfo
 * Description: 用户信息
 * <p>
 * author: JeyZheng
 * version: 4.0
 * created at: 2016/8/16 20:08
 */
@Data
public class UserInfo implements Serializable {
    private static final long serialVersionUID = 3066452218033074132L;
    private String mobile;

    public UserInfo(String mobile) {
        this.mobile = mobile;
    }
}
