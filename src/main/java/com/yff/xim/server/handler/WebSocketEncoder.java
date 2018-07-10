package com.yff.xim.server.handler;

import com.google.protobuf.MessageLite;
import com.google.protobuf.MessageLiteOrBuilder;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;

import java.util.List;

/**
 * 将MessageLiteOrBuilder对象编码成BinaryWebsocketFrame对象
 */
public class WebSocketEncoder extends MessageToMessageEncoder<MessageLiteOrBuilder> {


    @Override
    protected void encode(ChannelHandlerContext ctx, MessageLiteOrBuilder msg, List<Object> out) throws Exception {
        ByteBuf byteBuf = null;
        if (msg instanceof MessageLite) {
            MessageLite messageLite = (MessageLite) msg;
            byteBuf = Unpooled.wrappedBuffer(messageLite.toByteArray());
        } else if (msg instanceof MessageLite.Builder) {
            MessageLite.Builder builder = (MessageLite.Builder) msg;
            byteBuf = Unpooled.wrappedBuffer(builder.build().toByteArray());
        }
        if(byteBuf != null) {
            //将byteBuf转化为WebsocketFrame
            WebSocketFrame frame = new BinaryWebSocketFrame(byteBuf);
            out.add(frame);
        }
    }
}
