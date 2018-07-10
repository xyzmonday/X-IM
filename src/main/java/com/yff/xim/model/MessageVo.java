package com.yff.xim.model;

import com.yff.xim.server.enums.MessageProtocol;
import lombok.Data;

import java.io.Serializable;

/**
 * 封装前后端消息通信的实体类
 */
@Data
public class MessageVo implements Serializable {
    /**
     * 发送者
     */
    private String sessionId;
    /**
     * 接受者
     */
    private String recSessionId;
    /**
     * 消息协议
     */
    private MessageProtocol messageProtocol;
    /**
     * 消息来源 用于区分是websocket，socket，dwr
     */
    private int source;

    /**
     * 消息类型
     */
    private int msgType;
    /**
     * 消息内容
     */
    private Object body;
    /**
     * pc浏览器
     */
    private String platform;

    private String groupId;
    private String platformVersion;


    public boolean isGroup() {
        return MessageProtocol.GROUP.equals(this.messageProtocol);
    }

    public boolean isConnect() {
        return MessageProtocol.CONNECT.equals(this.messageProtocol);
    }

    public boolean isClose() {
        return MessageProtocol.CLOSE.equals(this.messageProtocol);
    }

    public boolean isHeartbeat() {
        return MessageProtocol.HEART_BEAT.equals(this.messageProtocol);
    }

    public boolean isSend() {
        return MessageProtocol.SEND.equals(this.messageProtocol);
    }

    public boolean isNotify() {
        return MessageProtocol.NOTIFY.equals(this.messageProtocol);
    }

    public boolean isReply() {
        return MessageProtocol.REPLY.equals(this.messageProtocol);
    }

    public boolean isOnline() {
        return MessageProtocol.ON_LINE.equals(this.messageProtocol);
    }

    public boolean isOffline() {
        return MessageProtocol.OFF_LINE.equals(this.messageProtocol);
    }

}
