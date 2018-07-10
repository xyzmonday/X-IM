package com.yff.xim.module.user.mapper;


import com.yff.xim.model.UserMessageEntity;

import java.util.List;
import java.util.Map;

/**
 * @author qiqiim
 * @email 1044053532@qq.com
 * @date 2017-11-23 10:47:47
 */
public interface UserMessageMapper {
    /**
     * 获取历史记录
     *
     * @param map
     * @return
     */
    List<UserMessageEntity> getHistoryMessageList(Map<String, Object> map);

    /**
     * 获取历史记录总条数
     *
     * @param map
     * @return
     */
    int getHistoryMessageCount(Map<String, Object> map);

    /**
     * 获取离线消息
     *
     * @param map
     * @return
     */
    List<UserMessageEntity> getOfflineMessageList(Map<String, Object> map);

    /**
     * 修改消息为已读状态
     *
     * @param map
     * @return
     */
    int updatemsgstate(Map<String, Object> map);


    void save(UserMessageEntity messageEntity);

}
