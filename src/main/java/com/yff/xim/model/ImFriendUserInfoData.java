package com.yff.xim.model;

import org.apache.commons.lang3.StringUtils;

public class ImFriendUserInfoData {
    public Long id;//好友ID
    public String username;//好友昵称
    public String avatar;//好友头像
    public String sign;//签名
    public String status = "offline"; //若值为offline代表离线，online或者不填为在线

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAvatar() {

        return StringUtils.isNotEmpty(avatar) ? avatar : "../static/layui/images/0.jpg";//avatar;
    }

    public void setAvatar(String avatar) {

        this.avatar = avatar;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


}
