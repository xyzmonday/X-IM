package com.yff.xim.model;

import java.util.List;

public class ImFriendUserData {
    public Long id;//分组ID
    public String groupname;//好友分组Name
    public List<ImFriendUserInfoData> list;//分组好友列表

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGroupname() {
        return groupname;
    }

    public void setGroupname(String groupname) {
        this.groupname = groupname;
    }

    public List<ImFriendUserInfoData> getList() {
        return list;
    }

    public void setList(List<ImFriendUserInfoData> list) {
        this.list = list;
    }


}
