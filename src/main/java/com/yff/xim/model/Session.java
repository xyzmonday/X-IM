package com.yff.xim.model;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 用户连接会话。该对象用来描述一个客户端链接。
 * 客户端和服务端通信实际上是通过Channel来描述，所以session可能出现的情况是：
 * 1. 一个客户端同时打开过个页面，此时共用一个sessionId，但是多个session，多个channel;
 * 2. 一个客户端在一个页面反复请求创建连接，此时一个session，一个channel
 */
@Data
public class Session implements Serializable {
    /**
     * 客户端id
     */
    private String sessionId;
    /**
     * 客户端和服务端通信的通道
     */
    private transient Channel channel;
    /**
     * 本机服务id
     */
    private String channelId;

    /**
     * 创建时间
     */
    private Date createdDate;
    /**
     * 更新时间
     */
    private Date updateDate;
    /**
     * 终端类型
     */
    private String platform;
    /**
     * 终端版本号
     */
    private String platformVersion;
    /**
     * 客户端key
     */
    private String appKey;
    /**
     * 消息来源 用于区分是websocket,socket,dwr
     */
    private int source;

    /**
     * 客户端设备id，一般用来唯一标识一个设备
     */
    private String deviceId;
    /**
     * 设备签名
     */
    private String sign;

    /**
     * 序列化
     */
    public Session() {

    }

    public Session(Channel ch) {
        this.channel = ch;
        this.channelId = ch.id().asShortText();
    }


}
