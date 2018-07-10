package com.yff.xim.server.enums;

import com.yff.xim.model.proto.MessageProto;

/**
 * 消息协议枚举
 */
public enum MessageProtocol {
    CONNECT, CLOSE, HEART_BEAT, SEND, GROUP, NOTIFY, REPLY, ON_LINE, OFF_LINE;
}
