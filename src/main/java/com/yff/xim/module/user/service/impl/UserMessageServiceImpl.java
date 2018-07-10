package com.yff.xim.module.user.service.impl;

import com.yff.xim.model.UserMessageEntity;
import com.yff.xim.module.user.mapper.UserMessageMapper;
import com.yff.xim.module.user.service.IUserMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
public class UserMessageServiceImpl implements IUserMessageService {

    @Autowired
    UserMessageMapper userMessageMapper;

    @Override
    public List<UserMessageEntity> getHistoryMessageList(Map<String, Object> map) {
        return userMessageMapper.getHistoryMessageList(map);
    }

    @Override
    public List<UserMessageEntity> getOfflineMessageList(Map<String, Object> map) {
        List<UserMessageEntity> result = userMessageMapper.getOfflineMessageList(map);
        //更新状态为已读状态
        userMessageMapper.updatemsgstate(map);
        return result;
    }

    @Override
    public int getHistoryMessageCount(Map<String, Object> map) {
        return userMessageMapper.getHistoryMessageCount(map);
    }

    @Transactional
    @Override
    public void saveMessageToDB(UserMessageEntity record) {
        userMessageMapper.save(record);
    }
}
