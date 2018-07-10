package com.yff.xim.server.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;

import java.util.List;

/**
 * 将WebSocketFrame解码为ByteBuf
 */
public class WebSocketDecoder extends MessageToMessageDecoder<WebSocketFrame> {
    @Override
    protected void decode(ChannelHandlerContext ctx, WebSocketFrame msg, List<Object> out) throws Exception {
        if(msg instanceof BinaryWebSocketFrame) {
            //如果是二进制的websocketFrame流
            BinaryWebSocketFrame frame = (BinaryWebSocketFrame) msg;
            ByteBuf byteBuf = frame.content();
            out.add(byteBuf);
            byteBuf.retain();
        }
    }
}
