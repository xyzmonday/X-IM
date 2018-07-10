package com.yff.xim.module.user.service.impl;

import com.yff.xim.model.ImFriendUserData;
import com.yff.xim.model.ImFriendUserInfoData;
import com.yff.xim.module.user.mapper.UserDepartmentMapper;
import com.yff.xim.module.user.service.IUserDepartmentService;
import com.yff.xim.server.sesssion.ISessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserDepartmentServiceImpl implements IUserDepartmentService {

    @Autowired
    UserDepartmentMapper userDepartmentMapper;

    @Autowired
    ISessionService sessionService;

    @Override
    public List<ImFriendUserData> queryGroupAndUser() {
        List<ImFriendUserData> friendgroup = userDepartmentMapper.queryGroupAndUser();
        for (ImFriendUserData fg : friendgroup) {
            List<ImFriendUserInfoData> friends = fg.getList();
            if (friends != null && friends.size() > 0) {
                for (ImFriendUserInfoData fr : friends) {
                    boolean exist = sessionService.exist(fr.getId());
                    if (exist)
                        fr.setStatus("online");
                }
            }
        }
        return friendgroup;
    }
}
