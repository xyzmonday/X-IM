package com.yff.xim.server;

import com.yff.xim.model.proto.MessageProto;
import com.yff.xim.server.handler.ImWebSocketServerHandler;
import com.yff.xim.server.handler.WebSocketEncoder;
import com.yff.xim.server.handler.WebSocketDecoder;
import com.yff.xim.util.GlobalConstant;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.codec.http.websocketx.extensions.compression.WebSocketServerCompressionHandler;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.channel.ChannelHandler.Sharable;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * 以websocket为通信协议的后端服务
 */
@Component
@Log4j2
public class ImWebsocketServer {

    @Value("${im.websocket.port}")
    private int port;

    private NioEventLoopGroup boss = new NioEventLoopGroup();
    private NioEventLoopGroup worker = new NioEventLoopGroup();
    private Channel channel;
    /**
     * Message对象的解码器
     */
    private ProtobufDecoder messageProtoBufDecoder = new ProtobufDecoder(MessageProto.Model.getDefaultInstance());

    @Autowired
    ImWebSocketServerHandler webSocketServerHandler;

    @PostConstruct
    public void start() throws InterruptedException{
        log.info("正在启动 ImWebsocketServer...");
        // Server 服务启动
        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(boss, worker);
        bootstrap.channel(NioServerSocketChannel.class);
        bootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
            @Override
            public void initChannel(SocketChannel ch) throws Exception {
                ChannelPipeline pipeline = ch.pipeline();
                // HTTP请求的解码和编码
                pipeline.addLast(new HttpServerCodec());
                // 把多个消息转换为一个单一的FullHttpRequest或是FullHttpResponse，
                // 原因是HTTP解码器会在每个HTTP消息中生成多个消息对象HttpRequest/HttpResponse,HttpContent,LastHttpContent
                pipeline.addLast(new HttpObjectAggregator(GlobalConstant.ImWebsocketServerConfig.MAX_AGGREGATED_CONTENT_LENGTH));
                // 主要用于处理大数据流，比如一个1G大小的文件如果你直接传输肯定会撑暴jvm内存的; 增加之后就不用考虑这个问题了
                pipeline.addLast(new ChunkedWriteHandler());
                // WebSocket数据压缩
                pipeline.addLast(new WebSocketServerCompressionHandler());
                // 协议包长度限制
                pipeline.addLast(new WebSocketServerProtocolHandler("/ws", null, true,
                        GlobalConstant.ImWebsocketServerConfig.MAX_FRAME_LENGTH));
                // 协议包解码
                pipeline.addLast(new WebSocketDecoder());
                // 协议包编码
                pipeline.addLast(new WebSocketEncoder());
                // 协议包解码时指定ProtoBuf字节数实例化为CommonProtocol类型
                pipeline.addLast(messageProtoBufDecoder);
                //心跳处理器
                pipeline.addLast(new IdleStateHandler(GlobalConstant.ImWebsocketServerConfig.READ_IDLE_TIME,
                        GlobalConstant.ImWebsocketServerConfig.WRITE_IDLE_TIME,0));
                // 业务处理器
                pipeline.addLast(webSocketServerHandler);
            }
        });


        // 可选参数
        bootstrap.childOption(ChannelOption.TCP_NODELAY, true);
        // 绑定接口，同步等待成功
        log.info("start ImWebsocketserver at port[" + port + "].");
        ChannelFuture future = bootstrap.bind(port).sync();
        channel = future.channel();
        future.addListener(new ChannelFutureListener() {
            public void operationComplete(ChannelFuture future) throws Exception {
                if (future.isSuccess()) {
                    log.info("websocketserver have success bind to " + port);
                } else {
                    log.error("websocketserver fail bind to " + port);
                }
            }
        });
    }

    @PreDestroy
    public void destroy() {
        log.info("destroy ImWebsocketServer ...");
        // 释放线程池资源
        if (channel != null) {
            channel.close();
        }
        boss.shutdownGracefully();
        worker.shutdownGracefully();
        log.info("destroy ImWebsocketServer complete.");
    }
}
