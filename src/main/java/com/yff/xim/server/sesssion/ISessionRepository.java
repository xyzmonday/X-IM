package com.yff.xim.server.sesssion;

import com.yff.xim.model.Session;

import java.util.List;

/**
 * 该接口定义了session持久化dao层，目的是如果需要高可用持久化session可以
 * 使用Redis,Mysql,Memcache等分布式集群来存储。
 */
public interface ISessionRepository {
    /**
     * 保存一个Session对象
     * @param session
     */
    void saveSession(Session session);

    /**
     * 移除一个session
     * @param sessionId
     */
    void removeSession(String sessionId);

    /**
     * 更新session
     * @param session
     */
    void updateSession(Session session);

    /**
     * 获取一个session对象
     * @param sessionId
     */
    Session selectSession(String sessionId);

    /**
     * 查询sessionId相同的session
     * @return
     */
    List<Session> selectSharableSessionList(String shrableSessionId);

    /**
     * 获取某一个sessionId的sharableSession集合中与sessionId相同的session
     * @param shableSessionId:sharableSession集合的id
     * @param sessionId:目标session的id
     * @return
     */
    Session selectSharableSession(String shableSessionId,String sessionId);

    /**
     * 保存sessionId相同的session
     * @param shrableSessionId
     * @param sharableSession
     */
    void saveSharableSession(String shrableSessionId,Session sharableSession);

    /**
     * 获取所有的session,注意不包含sharableSession
     * @return
     */
    List<Session> selectAllSession();

    /**
     * 关闭某一个sharableSession
     * @param sessionId
     * @param channelId
     */
    void removeSharableSession(String sessionId, String channelId);
}
