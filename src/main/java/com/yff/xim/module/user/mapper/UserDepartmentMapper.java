package com.yff.xim.module.user.mapper;

import com.yff.xim.model.ImFriendUserData;

import java.util.List;

/**
 * 部门
 * 
 * @author qiqiim
 * @email 1044053532@qq.com
 * @date 2017-11-27 09:38:52
 */
public interface UserDepartmentMapper  {
	
	public List<ImFriendUserData> queryGroupAndUser();
}
