package com.yff.xim.module.user.mapper;

import com.yff.xim.model.UserInfoEntity;

public interface UserInfoMapper {
    /**
     * 通过uid查询该用户的信息
     * @param uid:user的唯一标识
     * @return
     */
    public UserInfoEntity queryByUid(Long uid);

    /**
     * 插入一条用户信息数据
     * @param userInfo
     */
    void insert(UserInfoEntity userInfo);
}
