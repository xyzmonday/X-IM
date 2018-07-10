package com.yff.xim.module.user.service;

import com.yff.xim.exception.BizException;
import com.yff.xim.model.Query;
import com.yff.xim.model.UserAccountEntity;

public interface IUserAccountService {

    /**
     * 根据查询条件查找一个UserAccount
     * @param username:用户名
     * @param password:密码
     * @return
     */
    UserAccountEntity login(String username,String password);

    UserAccountEntity register(UserAccountEntity userAccount);
}
