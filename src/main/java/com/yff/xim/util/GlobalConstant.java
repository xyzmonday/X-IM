package com.yff.xim.util;

import io.netty.util.AttributeKey;

/**
 * 项目常量
 */
public interface GlobalConstant {


    public static interface ResponseCode {
        int ILLGAL = 0;
        int UNLOGIN = -1;
        int SYSERROR = -2;
        int UNREGISTER = -3;
        int REGISGTERFAIL=-4;
        int NORMAL = 200;
    }

    public static interface ViewTemplateConfig{
        public static String template = "pctemplate/";
        public static String mobiletemplate = "mobiletemplate/";
    }

    interface ImWebsocketServerConfig {
        //连接空闲时间
        public static final int READ_IDLE_TIME = 60;//秒
        //发送心跳包循环时间
        public static final int WRITE_IDLE_TIME = 40;//秒
        //心跳响应 超时时间
        public static final int PING_TIME_OUT = 70; //秒   需大于空闲时间

        // 最大协议包长度
        public static final int MAX_FRAME_LENGTH = 1024 * 10; // 10k
        //
        public static final int MAX_AGGREGATED_CONTENT_LENGTH = 65536;

        public static final String REBOT_SESSIONID = "0";//机器人SessionID

        public static final int WEBSOCKET = 1;//websocket标识

        public static final int SOCKET = 0;//socket标识
    }


    interface SessionConfig {
        public static final String SESSION_KEY = "account";
        public static final AttributeKey<String> SERVER_SESSION_ID = AttributeKey.valueOf(SESSION_KEY);
        public static final AttributeKey<Long> SERVER_SESSION_HEARBEAT = AttributeKey.valueOf("heartbeat");
    }


    interface MsgType {
        byte SEND = 1; //请求
        byte RECEIVE = 2; //接收
        byte NOTIFY = 3; //通知
        byte REPLY = 4; //回复
    }

    interface CmdType {
        byte BIND = 1; //绑定
        byte HEARTBEAT = 2; //心跳
        byte ONLINE = 3; //上线
        byte OFFLINE = 4; //下线
        byte MESSAGE = 5; //消息
        byte RECON = 6; //重连
    }

}
