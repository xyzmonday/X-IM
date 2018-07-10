package com.yff.xim.server;

import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.ChannelGroupFuture;
import io.netty.channel.group.ChannelMatcher;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;
import lombok.extern.log4j.Log4j2;

/**
 * 管理channelGroup，负责给channelGroup的成员发送通知
 */
@Log4j2
public class ImChannelGroupManager {
    private static final ChannelGroup CHANNEL_GROUP = new DefaultChannelGroup("channelGroup",GlobalEventExecutor.INSTANCE);

    public static void addChannel(Channel channel) {
        CHANNEL_GROUP.add(channel);
    }

    public static void remove(Channel channel) {
        CHANNEL_GROUP.remove(channel);
    }

    /**
     * 广播
     * @param msg
     * @return
     */
    public static ChannelGroupFuture broadcast(Object msg) {
        return CHANNEL_GROUP.writeAndFlush(msg);
    }
    /**
     * 广播
     * @param msg
     * @param matcher
     * @return
     */
    public static ChannelGroupFuture broadcast(Object msg, ChannelMatcher matcher) {
        return CHANNEL_GROUP.writeAndFlush(msg, matcher);
    }

    public static ChannelGroup flush() {
        return CHANNEL_GROUP.flush();
    }
    /**
     * 丢弃无用连接
     * @param channel
     * @return
     */
    public static boolean discard(Channel channel) {
        return CHANNEL_GROUP.remove(channel);
    }
    /**
     * 断开所有连接
     * @return
     */
    public static ChannelGroupFuture disconnect() {
        return CHANNEL_GROUP.disconnect();
    }
    /**
     * 断开指定连接
     * @param matcher
     * @return
     */
    public static ChannelGroupFuture disconnect(ChannelMatcher matcher) {
        return CHANNEL_GROUP.disconnect(matcher);
    }
    /**
     *
     * @param channel
     * @return
     */
    public static boolean isExist(Channel channel) {
        return CHANNEL_GROUP.contains(channel);
    }

    /**
     * channel数量
     * @return
     */
    public static int size() {
        return CHANNEL_GROUP.size();
    }
}
