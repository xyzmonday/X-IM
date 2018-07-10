package com.yff.xim.server.sesssion;

import com.yff.xim.model.MessageVo;
import com.yff.xim.model.Session;
import io.netty.channel.ChannelHandlerContext;

import java.util.List;

/**
 * 客户端链接session管理接口
 */
public interface ISessionService {

    /**
     * 添加指定的session
     * @param session
     */
    void saveSession(Session session);

    /**
     * 通过sessionId获取指定的session
     * @param sessionId
     * @return
     */
    Session selectSession(String sessionId);

    /**
     * 创建一个新的链接
     * @param ctx
     * @param messageVo
     */
    Session createSession(ChannelHandlerContext ctx, MessageVo messageVo);

    /**
     * 关闭session
     * @param sessionId:客户端id
     * @param channelId:服务端id
     */
    void closeSession(String sessionId,String channelId);

    /**
     * 获取当前已经链接上来的所有session
     * @return
     */
    List<Session> selectAllSession();

    /**
     * 更新session
     * @param session
     */
    void updateSession(Session session);

    /**
     * 判断该session是否已经建立了链接
     * @param session
     * @return
     */
    boolean isConnected(Session session);

    /**
     * 获取所有sharableSession
     * @param sessionId
     * @return
     */
    List<Session> selectSharableSessionList(String sessionId);

    /**
     * 判断该用户是否在线
     * @param sessionId:用户id
     * @return
     */
    boolean exist(Long sessionId);
}
