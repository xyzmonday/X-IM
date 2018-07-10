package com.yff.xim.server.handler;

import com.yff.xim.server.connector.IConnectorService;
import com.yff.xim.model.proto.MessageProto;
import com.yff.xim.util.GlobalConstant;
import com.yff.xim.util.ImUtils;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Message格式的消息处理器
 */
@Component
@Log4j2
@Sharable
public class ImWebSocketServerHandler extends SimpleChannelInboundHandler<MessageProto.Model> {

    @Autowired
    private IConnectorService connectorService;

    /**
     * 当客户端第一次链接上来的时候会触发改方法
     *
     * @param ctx
     * @param evt
     * @throws Exception
     */
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            //获取链接上来的客户端sessionId
            String sessionId = ctx.channel().attr(GlobalConstant.SessionConfig.SERVER_SESSION_ID).get();
            //如果是客户端发送过来的消息
            IdleStateEvent event = (IdleStateEvent) evt;
            if (IdleState.READER_IDLE == event.state()) {
                //如果在一定时间内客户端没有发送心跳，那么认为客户端已经下线
                log.info(IdleState.READER_IDLE + "... from " + sessionId + " channelId:" + ctx.channel().id().asShortText());
                //获取上一次客户端发送心跳的时间
                Long lastHeartBeatTime = ctx.channel().attr(GlobalConstant.SessionConfig.SERVER_SESSION_HEARBEAT).get();
                Long currentTime = System.currentTimeMillis();
                if (lastHeartBeatTime == null || (currentTime - lastHeartBeatTime) / 1000 >=
                        GlobalConstant.ImWebsocketServerConfig.PING_TIME_OUT) {
                    //如果超时，那么说明认为该客户端已经下线，此时应该清除关闭该客户端的Channel和Session
                    connectorService.close(ctx);
                }
            }
            //服务端向客户端发送ping心跳消息
            if (IdleState.WRITER_IDLE == event.state()) {
                MessageProto.Model.Builder builder = MessageProto.Model.newBuilder();
                builder.setCmd(GlobalConstant.CmdType.HEARTBEAT);
                builder.setMsgtype(GlobalConstant.MsgType.REPLY);
                ctx.channel().writeAndFlush(builder);
                log.info(IdleState.WRITER_IDLE + "... from " + sessionId + "-->" + ctx.channel().remoteAddress() +
                        " channelId:" + ctx.channel().id().asShortText());
            }
        }
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageProto.Model message) throws Exception {
        connectorService.processMessage(ctx, message, GlobalConstant.ImWebsocketServerConfig.WEBSOCKET);
    }


    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        log.info("ImWebSocketServerHandler join from " + ImUtils.getRemoteAddress(ctx) + " channelId:" + ctx.channel().id().asShortText());
    }

    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        log.debug("ImWebSocketServerHandler Disconnected from {" + ctx.channel().remoteAddress() + "--->" + ctx.channel().localAddress() + "}");
    }

    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        log.debug("ImWebSocketServerHandler channelActive from (" + ImUtils.getRemoteAddress(ctx) + ")");
    }

    /**
     * 处理用户下线
     *
     * @param ctx
     * @throws Exception
     */
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
        connectorService.close(ctx);
    }

    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.warn("ImWebSocketServerHandler (" + ImUtils.getRemoteAddress(ctx) + ") -> Unexpected exception from downstream." + cause);
    }


}
