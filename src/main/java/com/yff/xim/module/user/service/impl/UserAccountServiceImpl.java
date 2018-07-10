package com.yff.xim.module.user.service.impl;

import com.yff.xim.exception.BizException;
import com.yff.xim.model.*;
import com.yff.xim.module.user.mapper.UserAccountMapper;
import com.yff.xim.module.user.mapper.UserDepartmentMapper;
import com.yff.xim.module.user.mapper.UserInfoMapper;
import com.yff.xim.module.user.service.IUserAccountService;
import com.yff.xim.util.GlobalConstant;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserAccountServiceImpl implements IUserAccountService {

    @Autowired
    UserAccountMapper accountMapper;

    @Autowired
    UserInfoMapper userInfoMapper;

    @Autowired
    UserDepartmentMapper userDepartmentMapper;

    @Override
    public UserAccountEntity login(String username, String password) {
        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
            throw new BizException(GlobalConstant.ResponseCode.ILLGAL, "用户名或者登录密码为空!!!");
        }
        UserAccountEntity userAccount = accountMapper.findUserAccount(username, password);
        if (userAccount == null) {
            throw new BizException(GlobalConstant.ResponseCode.UNREGISTER, "未查询到该用户!!!");
        }
        return userAccount;
    }

    @Transactional
    @Override
    public UserAccountEntity register(UserAccountEntity userAccount) {
        if (StringUtils.isEmpty(userAccount.getAccount())) {
            throw new BizException(GlobalConstant.ResponseCode.ILLGAL, "用户名为空!!!");
        }
        //检查改用户是否已经注册过
        UserAccountEntity account = accountMapper.findByAccount(userAccount.getAccount());
        if (account != null) {
            throw new BizException(GlobalConstant.ResponseCode.REGISGTERFAIL, userAccount.getAccount() + "已经注册过!!!");
        }
        //保存账户信息
        accountMapper.insert(userAccount);
        //保存用户的基本信息
        UserInfoEntity userInfo = userAccount.getUserInfo();
        userInfo.setUid(userAccount.getId());
        userInfoMapper.insert(userInfo);
        return userAccount;
    }


}
