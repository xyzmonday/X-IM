package com.yff.xim.module.user.controller;

import com.yff.xim.model.*;
import com.yff.xim.module.user.service.IUserAccountService;
import com.yff.xim.module.user.service.IUserDepartmentService;
import com.yff.xim.util.GlobalConstant;
import com.yff.xim.util.ImUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.jws.Oneway;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/userAccount")
public class UserAccountController {

    @Autowired
    IUserAccountService userAccountService;

    @Autowired
    IUserDepartmentService userDepartmentService;

    @RequestMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/toLogin")
    public String login(UserAccountEntity accountEntity, Model model,HttpServletRequest request) {
        UserAccountEntity userAccount = userAccountService.login(accountEntity.getAccount(), accountEntity.getPassword());
        if (userAccount != null) {
            //将用户信息保存到session中
            ImUtils.saveLoginUser(request,userAccount);
            model.addAttribute("user", userAccount);
            String template = ImUtils.check(request);
            if (template.equals(GlobalConstant.ViewTemplateConfig.mobiletemplate)) {
                return "layimmobile";
            }
            return "layim";
        }
        return "redirect:/userAccount/login";
    }

    /**
     * @ModelAttribute:注解的使用说明
     * 1、 如果注解在方法上，那么在执行这个Controller的其他方法时会执行被这个注解注解上的方法，把数据放到model中；
     * 2、 如果注解在方法的返回上，那么就是将返回值放到model中；
     * 3、 如果注解到入参上，那么就是从model取出对应的传入参数
     * @param userAccount
     * @return
     */
    @PostMapping("/register")
    @ResponseBody
    public Result<UserAccountEntity> register(@ModelAttribute UserAccountEntity userAccount) {
        Result<UserAccountEntity> result = new Result<>();
        userAccount.setDisablestate(0);
        userAccount.setIsdel(0);
        userAccount = userAccountService.register(userAccount);
        result.setCode(GlobalConstant.ResponseCode.NORMAL);
        result.setMessage("请求成功");
        result.setData(userAccount);
        return result;
    }

    /**
     * 取得所有聊天用户
     *
     * @return
     * @throws Exception
     */
    @RequestMapping("/getusers")
    @ResponseBody
    public ImUserData getAllUser(HttpServletRequest request) throws Exception {
        // 数据格式请参考文档  http://www.layui.com/doc/modules/layim.html
        UserAccountEntity loginUser = ImUtils.getLoginUser(request);
        if (loginUser != null) {
            UserInfoEntity user = loginUser.getUserInfo();
            ImFriendUserInfoData my = new ImFriendUserInfoData();
            my.setId(user.getUid());
            my.setAvatar(user.getProfilephoto());
            my.setSign(user.getSignature());
            my.setUsername(user.getName());
            my.setStatus("online");

            //模拟群信息
            ImGroupUserData group = new ImGroupUserData();
            group.setAvatar("http://tva2.sinaimg.cn/crop.0.0.199.199.180/005Zseqhjw1eplix1brxxj305k05kjrf.jpg");
            group.setId(1L);
            group.setGroupname("公司群");
            List<ImGroupUserData> groups = new ArrayList<ImGroupUserData>();
            groups.add(group);

            Map map = new HashMap();
            ImUserData us = new ImUserData();
            us.setCode("0");
            us.setMsg("");
            map.put("mine", my);
            map.put("group", groups);
            //获取用户分组 及用户
            List<ImFriendUserData> friends = userDepartmentService.queryGroupAndUser();
            map.put("friend", friends);
            us.setData(map);
            return us;
        } else {
            return null;
        }
    }


}
