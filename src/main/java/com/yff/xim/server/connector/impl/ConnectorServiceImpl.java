package com.yff.xim.server.connector.impl;

import com.yff.xim.model.MessageVo;
import com.yff.xim.model.Session;
import com.yff.xim.model.proto.MessageProto;
import com.yff.xim.module.user.service.IUserMessageService;
import com.yff.xim.server.ImChannelGroupManager;
import com.yff.xim.server.connector.IConnectorService;
import com.yff.xim.server.message.IMessageProcessor;
import com.yff.xim.server.sesssion.ISessionService;
import com.yff.xim.util.ChannelUtils;
import com.yff.xim.util.GlobalConstant;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Log4j2
public class ConnectorServiceImpl implements IConnectorService {

    @Autowired
    ISessionService sessionService;

    @Autowired
    IMessageProcessor messageProcessor;

    @Autowired
    IUserMessageService userMessageService;

    /**
     * 关闭客户端和服务端之间的链接
     *
     * @param ctx
     */
    @Override
    public void close(ChannelHandlerContext ctx) {
        log.info("ConnectorManager 关闭链接");
        String sessionId = ChannelUtils.getChannelSessionId(ctx);
        String channelId = ChannelUtils.getChannelId(ctx);
        sessionService.closeSession(sessionId, channelId);
    }

    /**
     * 根据消息协议对消息进行处理。客户端的cmdType类型可能是1绑定  2心跳   3上线   4下线 5消息 6重连
     *
     * @param ctx
     * @param message
     */
    @Override
    public void processMessage(ChannelHandlerContext ctx, MessageProto.Model message, int source) {
        MessageVo messageVo = messageProcessor.messageConvert(message, source);
        String sessionId = messageVo.getSessionId();
        if (messageVo.isConnect()) {
            //请求链接
            responseHeatBeat(ctx);
            String sessionId0 = ChannelUtils.getChannelSessionId(ctx);
            synchronized (this) {
                //如果sessionId0存在或者与当前的sessionId相等，那么视为同一个用户重新链接
                if (StringUtils.isNotEmpty(sessionId0) || (StringUtils.isNotEmpty(sessionId) && sessionId.equals(sessionId0))) {
                    log.info("connector reconnect sessionId -> " + sessionId + ", ctx -> " + ctx.toString());
                    //发送重连消息给客户端
                    MessageProto.Model reconnectMsg = messageProcessor.createReconnectMsg(sessionId, ChannelUtils.getChannelId(ctx));
                    sendMessage(sessionId, reconnectMsg);
                } else {
                    //创建一个新的session,并保存该连接
                    log.info("connector connect sessionId -> " + sessionId + ", sessionId0 -> " + sessionId0 + ", ctx -> " + ctx.toString());
                    Session session = sessionService.createSession(ctx, messageVo);
                    ChannelUtils.setChannelSessionId(ctx, session.getSessionId());
                }
            }
        } else if (messageVo.isHeartbeat()) {
            //心跳
            log.info("connector receives heartbeat pong from client with sessionId :{}", sessionId);
            responseHeatBeat(ctx);
        } else if (messageVo.isReply()) {
            //回复消息
            if (!GlobalConstant.ImWebsocketServerConfig.REBOT_SESSIONID.equals(sessionId)) {
                //如果不是机器人回复
                Session session = sessionService.selectSession(sessionId);
                if (session == null) {
                    throw new RuntimeException(String.format("session %s is not exist.", sessionId));
                }
                //获取接收人，给接收人写消息
                Session recSession = sessionService.selectSession(messageVo.getRecSessionId());
                 if (recSession != null && sessionService.isConnected(recSession)) {
                    //将消息发送给指定的接受人
                    boolean result = sendMessage(recSession.getSessionId(), (MessageProto.Model) messageVo.getBody());
                    //TODO 不管成功或者失败，保存该消息
                    if(result) {
                        messageProcessor.saveOnlineMessageToDB(messageVo);
                    } else {
                        messageProcessor.saveOfflineMessageToDB(messageVo);
                    }
                } else {
                    //TODO 说明已经下线，保存离线消息
                    messageProcessor.saveOfflineMessageToDB(messageVo);
                }
            }
        } else if (messageVo.isGroup()) {
            //群聊
            ImChannelGroupManager.broadcast(message);
            //TODO 保存群发的消息
            messageProcessor.saveOnlineMessageToDB(messageVo);
        }
    }


    /**
     * 服务端发送消息
     *
     * @param sessionId:接受人的id
     * @param msg:消息
     */
    @Override
    public boolean sendMessage(String sessionId, MessageProto.Model msg) {
        if (StringUtils.isEmpty(sessionId)) {
            return false;
        }
        Session session = sessionService.selectSession(sessionId);
        //向sharableSession发送消息
        List<Session> list = sessionService.selectSharableSessionList(sessionId);
        for (Session s : list) {
            if (s.getChannel() != null) {
                s.getChannel().writeAndFlush(msg);
            }
        }
        return session.getChannel().writeAndFlush(msg).awaitUninterruptibly(5000);
    }


    /**
     * 处理客户端发送过来的心跳响应
     */
    private void responseHeatBeat(ChannelHandlerContext ctx) {
        //如果客户端发送过来的心跳pong,设置心跳响应时间
        ctx.channel().attr(GlobalConstant.SessionConfig.SERVER_SESSION_HEARBEAT).set(System.currentTimeMillis());
    }
}
