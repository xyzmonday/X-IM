package com.yff.xim.module.user.mapper;


import com.yff.xim.model.UserAccountEntity;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * 用户帐号
 */
public interface UserAccountMapper {

    public UserAccountEntity findUserAccount(@Param("username") String username,@Param("password") String password);

    /**
     * 查找账户为username的用户
     * @param username
     * @return
     */
    public UserAccountEntity findByAccount(@Param("username") String username);

    /**
     * 插入一条账户记录
     * @param userAccount
     */
    void insert(UserAccountEntity userAccount);
}
