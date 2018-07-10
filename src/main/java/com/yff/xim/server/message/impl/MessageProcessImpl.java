package com.yff.xim.server.message.impl;

import com.yff.xim.model.MessageVo;
import com.yff.xim.model.UserMessageEntity;
import com.yff.xim.model.proto.MessageBodyProto;
import com.yff.xim.model.proto.MessageProto;
import com.yff.xim.module.user.service.IUserMessageService;
import com.yff.xim.server.enums.MessageProtocol;
import com.yff.xim.exception.MessageConvertException;
import com.yff.xim.server.message.IMessageProcessor;
import com.yff.xim.util.GlobalConstant;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Log4j2
public class MessageProcessImpl implements IMessageProcessor {

    @Autowired
    IUserMessageService userMessageService;

    /**
     * 根据请求消息的cmdType做出相应的处理
     *
     * @param message
     * @return
     */
    @Override
    public MessageVo messageConvert(MessageProto.Model message, int source) throws MessageConvertException {
        if (message == null) {
            return null;
        }
        MessageVo messageVo = null;
        switch (message.getCmd()) {
            case GlobalConstant.CmdType.BIND:
                //绑定类型的命令
                messageVo = new MessageVo();
                messageVo.setSessionId(message.getSender());
                messageVo.setMessageProtocol(MessageProtocol.CONNECT);
                messageVo.setRecSessionId(null);
                messageVo.setMsgType(message.getMsgtype());
                messageVo.setPlatform(message.getPlatform());
                messageVo.setPlatformVersion(message.getPlatformVersion());
                messageVo.setSource(source);
                messageVo.setBody(message);
                return messageVo;
            case GlobalConstant.CmdType.HEARTBEAT:
                //心跳
                messageVo = new MessageVo();
                messageVo.setSessionId(message.getSender());
                messageVo.setMessageProtocol(MessageProtocol.HEART_BEAT);
                messageVo.setRecSessionId(null);
                messageVo.setPlatform(message.getPlatform());
                messageVo.setMsgType(message.getMsgtype());
                messageVo.setSource(source);
                messageVo.setBody(message);
                return messageVo;
            case GlobalConstant.CmdType.OFFLINE:
                messageVo = new MessageVo();
                messageVo.setSessionId(message.getSender());
                messageVo.setPlatform(message.getPlatform());
                messageVo.setPlatformVersion(message.getPlatformVersion());
                messageVo.setMessageProtocol(MessageProtocol.CLOSE);
                return messageVo;
            case GlobalConstant.CmdType.MESSAGE:
                //将消息发给其他人（一对一，一对多，机器人回复）
                if (StringUtils.isNotEmpty(message.getReceiver())) {
                    //判断是否发消息给机器人
                    if (message.getReceiver().equals(GlobalConstant.ImWebsocketServerConfig.REBOT_SESSIONID)) {

                    } else {
                        messageVo = new MessageVo();
                        messageVo.setSource(source);
                        messageVo.setMsgType(message.getMsgtype());
                        messageVo.setSessionId(message.getSender());
                        messageVo.setRecSessionId(message.getReceiver());
                        messageVo.setPlatform(message.getPlatform());
                        messageVo.setPlatformVersion(message.getPlatformVersion());
                        messageVo.setMessageProtocol(MessageProtocol.REPLY);
                        messageVo.setBody(message);
                    }
                } else if (StringUtils.isNotEmpty(message.getGroupId())) {
                    //如果消息没有接收人，那么可能是群聊
                    messageVo = new MessageVo();
                    messageVo.setSource(source);
                    messageVo.setMsgType(message.getMsgtype());
                    messageVo.setSessionId(message.getSender());
                    messageVo.setRecSessionId(null);
                    messageVo.setGroupId(message.getGroupId());
                    messageVo.setMessageProtocol(MessageProtocol.GROUP);
                    messageVo.setPlatform(message.getPlatform());
                    messageVo.setPlatformVersion(message.getPlatformVersion());
                    messageVo.setBody(message);
                }
                return messageVo;
        }
        if (messageVo == null) {
            throw new MessageConvertException("消息cmpType传入有误，未能够匹配到该类型");
        }
        return messageVo;
    }

    @Override
    public void saveOnlineMessageToDB(MessageVo message) {
        try {
            UserMessageEntity userMessage = convertMessageWrapperToBean(message);
            if (userMessage != null) {
                userMessage.setIsread(1);
                userMessageService.saveMessageToDB(userMessage);
            }
        } catch (Exception e) {
            log.error("MessageProxyImpl  user " + message.getSessionId() + " send msg to " + message.getRecSessionId() + " error");
            throw new RuntimeException(e.getCause());
        }
    }


    @Override
    public void saveOfflineMessageToDB(MessageVo message) {
        try {
            UserMessageEntity userMessage = convertMessageWrapperToBean(message);
            if (userMessage != null) {
                userMessage.setIsread(0);
                userMessageService.saveMessageToDB(userMessage);
            }
        } catch (Exception e) {
            log.error("MessageProxyImpl  user " + message.getSessionId() + " send msg to " + message.getRecSessionId() + " error");
            throw new RuntimeException(e.getCause());
        }
    }

    private UserMessageEntity convertMessageWrapperToBean(MessageVo message) {
        try {
            //保存非机器人消息
            if (!message.getSessionId().equals(GlobalConstant.ImWebsocketServerConfig.REBOT_SESSIONID)) {
                MessageProto.Model msg = (MessageProto.Model) message.getBody();
                MessageBodyProto.MessageBody msgContent = MessageBodyProto.MessageBody.parseFrom(msg.getContent());
                UserMessageEntity userMessage = new UserMessageEntity();
                userMessage.setSenduser(message.getSessionId());
                userMessage.setReceiveuser(message.getRecSessionId());
                userMessage.setContent(msgContent.getContent());
                userMessage.setGroupid(msg.getGroupId());
                userMessage.setCreatedate(msg.getTimeStamp());
                userMessage.setIsread(1);
                return userMessage;
            }
        } catch (Exception e) {
            throw new RuntimeException(e.getCause());
        }
        return null;
    }

    /**
     * 创建重连消息
     *
     * @param sessionId:已经上线的客户端
     * @param channelId:发送人
     * @return
     */
    @Override
    public MessageProto.Model createReconnectMsg(String sessionId, String channelId) {
        MessageProto.Model.Builder msg = MessageProto.Model.newBuilder();
        msg.setTimeStamp(DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
        msg.setSender(channelId);
        msg.setReceiver(sessionId);
        //重连消息
        msg.setCmd(GlobalConstant.CmdType.RECON);
        return msg.build();
    }


    /**
     * 创建新客户端上线消息
     *
     * @param sessionId:新上线的客户端id
     * @return
     */
    @Override
    public MessageProto.Model createOnLineMsg(String sessionId, String platform, String platformVersion) {
        MessageProto.Model.Builder msg = MessageProto.Model.newBuilder();
        msg.setTimeStamp(DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
        msg.setReceiver(sessionId);
        msg.setPlatform(platform);
        msg.setPlatformVersion(platformVersion);
        msg.setCmd(GlobalConstant.CmdType.ONLINE);
        return msg.build();
    }

    @Override
    public MessageProto.Model createOffLineMsg(String sessionId, String channelId) {
        MessageProto.Model.Builder result = MessageProto.Model.newBuilder();
        result.setTimeStamp(DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
        result.setSender(channelId);
        result.setReceiver(sessionId);
        result.setCmd(GlobalConstant.CmdType.OFFLINE);
        return result.build();
    }

}
