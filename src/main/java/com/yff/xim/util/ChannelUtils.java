package com.yff.xim.util;

import io.netty.channel.ChannelHandlerContext;
import org.apache.commons.lang3.StringUtils;

public class ChannelUtils {

    /**
     * 获取channel保存的客户端sessionId
     *
     * @param ctx
     * @return
     */
    public static String getChannelSessionId(ChannelHandlerContext ctx) {
        return ctx.channel().attr(GlobalConstant.SessionConfig.SERVER_SESSION_ID).get();
    }

    /**
     * 将客户端的sessionId保存到对应的channel中
     *
     * @param ctx
     * @param sessionId
     */
    public static void setChannelSessionId(ChannelHandlerContext ctx, String sessionId) {
        if (StringUtils.isNotEmpty(sessionId)) {
            ctx.channel().attr(GlobalConstant.SessionConfig.SERVER_SESSION_ID).set(sessionId);
        }
    }

    public static String getChannelId(ChannelHandlerContext ctx) {
        return ctx.channel().id().asShortText();
    }
}
