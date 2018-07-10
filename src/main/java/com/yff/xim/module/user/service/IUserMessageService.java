package com.yff.xim.module.user.service;

import com.yff.xim.model.UserMessageEntity;

import java.util.List;
import java.util.Map;

public interface IUserMessageService {
    /**
     * 获取历史记录
     * @param map
     * @return
     */
    List<UserMessageEntity> getHistoryMessageList(Map<String, Object> map);
    /**
     * 获取离线消息
     * @param map
     * @return
     */
    List<UserMessageEntity> getOfflineMessageList(Map<String, Object> map);
    /**
     * 获取历史记录总条数
     * @param map
     * @return
     */
    int getHistoryMessageCount(Map<String, Object> map);

    /**
     * 保存一条消息
     * @param record
     */
    void saveMessageToDB(UserMessageEntity record);
}
