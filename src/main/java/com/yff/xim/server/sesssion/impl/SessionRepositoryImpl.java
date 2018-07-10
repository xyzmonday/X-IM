package com.yff.xim.server.sesssion.impl;

import com.yff.xim.model.Session;
import com.yff.xim.server.sesssion.ISessionRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class SessionRepositoryImpl implements ISessionRepository {

    //这里为了快速开发使用了内存Map
    private static final Map<String, Session> SESSION_REPOSITORY = new ConcurrentHashMap<>();
    private static final Map<String,Map<String,Session>> SHARABLE_SESSION_REPOSITORY = new ConcurrentHashMap<>();

    @Override
    public void saveSession(Session session) {
        if (session != null) {
            String sessionId = session.getSessionId();
            SESSION_REPOSITORY.put(sessionId, session);
        }
    }

    @Override
    public void removeSession(String sessionId) {
        if (StringUtils.isNotEmpty(sessionId)) {
            SESSION_REPOSITORY.remove(sessionId);
        }
    }

    @Override
    public void updateSession(Session session) {
        if (session != null) {
            SESSION_REPOSITORY.put(session.getSessionId(), session);
        }
    }

    @Override
    public Session selectSession(String sessionId) {
        if (StringUtils.isEmpty(sessionId)) {
            return null;
        }
        return SESSION_REPOSITORY.get(sessionId);
    }

    @Override
    public List<Session> selectSharableSessionList(String sharableSessionId) {
        if(StringUtils.isEmpty(sharableSessionId)) {
            return null;
        }
        List<Session> list = new ArrayList<>();
        Map<String, Session> sessionMap = SHARABLE_SESSION_REPOSITORY.get(sharableSessionId);
        if(sessionMap != null && sessionMap.values() != null) {
            list.addAll(sessionMap.values());
        }
        return list;
    }

    @Override
    public Session selectSharableSession(String sharableSessionId, String sessionId) {
        Map<String, Session> sessionMap = SHARABLE_SESSION_REPOSITORY.get(sharableSessionId);
        Session session = sessionMap.get(sessionId);
        return session;
    }

    @Override
    public void saveSharableSession(String sharableSessionId, Session sharableSession) {
        if(sharableSession == null || StringUtils.isEmpty(sharableSessionId))
            return;
        String sessionId = sharableSession.getSessionId();
        Map<String,Session> sharableSessionMap = SHARABLE_SESSION_REPOSITORY.get(sharableSessionId);
        if(sharableSessionMap == null) {
            sharableSessionMap = new HashMap<>();
        }
        sharableSessionMap.put(sessionId,sharableSession);
        SHARABLE_SESSION_REPOSITORY.put(sharableSessionId,sharableSessionMap);
    }

    @Override
    public List<Session> selectAllSession() {
        List<Session> sessions = new ArrayList<>();
        sessions.addAll(SESSION_REPOSITORY.values());
        return sessions;
    }

    @Override
    public void removeSharableSession(String sharableSessionId, String sessionId) {
        if(StringUtils.isNotEmpty(sharableSessionId)) {
            Map<String, Session> sharableSessionMap = SHARABLE_SESSION_REPOSITORY.get(sharableSessionId);
            if(StringUtils.isNotEmpty(sessionId)) {
                sharableSessionMap.remove(sessionId);
            }
        }
    }
}
