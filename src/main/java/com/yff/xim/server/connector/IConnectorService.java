package com.yff.xim.server.connector;

import com.yff.xim.model.MessageVo;
import com.yff.xim.model.proto.MessageProto;
import io.netty.channel.ChannelHandlerContext;

/**
 * 链接管理接口，该接口负责管理客户端和服务的Channel，Session等链接信息
 */
public interface IConnectorService {

    /**
     * 关闭客户端和服务端的链接。
     * @param ctx
     */
    void close(ChannelHandlerContext ctx);

    /**
     * 发送消息给客户端
     * @param ctx
     * @param message:需要处理的消息
     * @param source:消息源（websocekt或者socket）
     */
    void processMessage(ChannelHandlerContext ctx, MessageProto.Model message,int source);

    boolean sendMessage(String sessionId, MessageProto.Model msg);
}
