
package com.yff.xim.server.sesssion.impl;

import com.yff.xim.model.MessageVo;
import com.yff.xim.model.Session;
import com.yff.xim.model.proto.MessageProto;
import com.yff.xim.server.ImChannelGroupManager;
import com.yff.xim.server.message.IMessageProcessor;
import com.yff.xim.server.sesssion.ISessionService;
import com.yff.xim.server.sesssion.ISessionRepository;
import com.yff.xim.util.ChannelUtils;
import com.yff.xim.util.GlobalConstant;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
@Log4j2
public class SessionServiceImpl implements ISessionService {

    @Autowired
    ISessionRepository sessionRepository;

    @Autowired
    IMessageProcessor messageProcessor;

    @Override
    public void saveSession(Session session) {
        if (session != null) {
            //TODO 暂时先将所有session添加到ChannelGroup中,这样同一个用户多个页面会接到其他用户消息
            ImChannelGroupManager.addChannel(session.getChannel());
            sessionRepository.saveSession(session);
        }
    }


    @Override
    public Session selectSession(String sessionId) {
        return sessionRepository.selectSession(sessionId);
    }

    @Override
    public Session createSession(ChannelHandlerContext ctx, MessageVo messageVo) {
        String sessionId = messageVo.getSessionId();
        //当在同一个浏览器打开多个chat.html页面，那么此时的sessionId相同，那么将拿到最新的session对象
        Session session = sessionRepository.selectSession(sessionId);
        if (session != null) {
            switch (session.getSource()) {
                case GlobalConstant.ImWebsocketServerConfig.WEBSOCKET:
                    //此时是同一个用户打开了多个websocket页面，这里的处理方式是多个session同时在线
                    Session sharableSession = createSession0(ctx, messageVo);
                    //因为每一个session都对应了一个channel，只有它们的sessionId是相同的
                    sessionRepository.saveSharableSession(sessionId, sharableSession);
                    //更新session的日期
                    session.setUpdateDate(new Date());
                    updateSession(session);
                    ImChannelGroupManager.addChannel(sharableSession.getChannel());
                    return session;
            }
        }
        //如果session为空，说明该客户端是第一次请求链接
        session = createSession0(ctx, messageVo);
        saveSession(session);
        //将新上线的客户端通知到其他已经连级的用户
        MessageProto.Model onLineMsg = messageProcessor.createOnLineMsg(sessionId, messageVo.getPlatform(), messageVo.getPlatformVersion());
        ImChannelGroupManager.broadcast(onLineMsg);
        return session;
    }

    /**
     * 因为一个用户可以打开多个页面，也就是说sessionId相同存在多个channelId
     *
     * @param sessionId:客户端id
     * @param channelId:服务端id
     */
    @Override
    public void closeSession(String sessionId, String channelId) {
        log.info("正在关闭session:{}", sessionId);
        Session session = selectSession(sessionId);
        if (session != null) {
            if (StringUtils.isNotEmpty(channelId) && session.getChannel() != null) {
                String channelId0 = session.getChannel().id().asShortText();
                if (channelId.equals(channelId0)) {
                    //第一次链接的channel
                    session.getChannel().close();
                    sessionRepository.removeSession(sessionId);
                } else {
                    //表示关闭sessionId的sharableSession
                    session = sessionRepository.selectSharableSession(sessionId, channelId);
                    if (session != null && session.getChannel() != null) {
                        session.getChannel().close();
                        sessionRepository.removeSharableSession(sessionId, channelId);
                    }
                }
            }
        }
        //关闭该通道的消息推送
        ImChannelGroupManager.remove(session.getChannel());
        List<Session> list = sessionRepository.selectSharableSessionList(sessionId);
        if (list.size() <= 0) {
            //说明该用户已经下线
            MessageProto.Model offLineMsg = messageProcessor.createOffLineMsg(sessionId, channelId);
            ImChannelGroupManager.broadcast(offLineMsg);
        }
    }


    @Override
    public List<Session> selectAllSession() {
        List<Session> sessions = sessionRepository.selectAllSession();
        return sessions;
    }

    @Override
    public void updateSession(Session session) {
        sessionRepository.updateSession(session);
    }

    /**
     * 判断该session是否已经链接成功
     *
     * @param session
     * @return
     */
    @Override
    public boolean isConnected(Session session) {
        if (session == null) {
            return false;
        }
        //如果该session存在，获取它的sharableSession
        List<Session> list = sessionRepository.selectSharableSessionList(session.getSessionId());
        for (Session s : list) {
            if (s.getChannel() != null) {
                return s.getChannel().isActive();
            }
        }

        //如果没有sharableSession
        if (session.getChannel() != null) {
            return session.getChannel().isActive();
        }

        return false;
    }

    @Override
    public List<Session> selectSharableSessionList(String sessionId) {
        return sessionRepository.selectSharableSessionList(sessionId);
    }

    @Override
    public boolean exist(Long sessionId) {
        if (sessionId == null) {
            return false;
        }
        Session session = sessionRepository.selectSession(String.valueOf(sessionId));
        return session == null ? false : true;
    }

    /**
     * 创建会话
     *
     * @param ctx
     * @param messageVo
     * @return
     */
    private Session createSession0(ChannelHandlerContext ctx, MessageVo messageVo) {
        MessageProto.Model model = (MessageProto.Model) messageVo.getBody();
        //注意构造方法已经赋值了channel,channelId
        Session session = new Session(ctx.channel());
        session.setSessionId(messageVo.getSessionId());
        session.setSource(messageVo.getSource());
        session.setAppKey(model.getAppKey());
        session.setDeviceId(model.getDeviceId());
        session.setPlatform(model.getPlatform());
        session.setPlatformVersion(model.getPlatformVersion());
        session.setSign(model.getSign());
        return session;
    }
}
