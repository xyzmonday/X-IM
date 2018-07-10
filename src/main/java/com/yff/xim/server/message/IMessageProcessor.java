package com.yff.xim.server.message;

import com.yff.xim.model.MessageVo;
import com.yff.xim.model.proto.MessageProto;
import com.yff.xim.exception.MessageConvertException;

/**
 * 定义消息过滤，目的是后续自动回复消息。所有从前端接受到
 * 的消息需要经过该消息过滤器，根据不同的消息类型给出不同的消息处理
 */
public interface IMessageProcessor {

    /**
     *
     * @param message:客户端传递过来的message
     */
    MessageVo messageConvert(MessageProto.Model message,int source) throws MessageConvertException;

    /**
     * 创建重连消息给客户端
     * @param sessionId:已经上线的客户端
     * @param channelId:发送人
     * @return
     */
    MessageProto.Model createReconnectMsg(String sessionId,String channelId);

    /**
     * 创建客户端上线消息
     * @param sessionId:新上线的客户端
     * @return
     */
    MessageProto.Model createOnLineMsg(String sessionId,String platform,String platformVersion);

    /**
     * 创建客户端下线消息
     * @param sessionId:客户端
     * @param channelId:服务端
     * @return
     */
    MessageProto.Model createOffLineMsg(String sessionId, String channelId);

    void saveOfflineMessageToDB(MessageVo message);


    void saveOnlineMessageToDB(MessageVo message);

}
