package com.yff.xim.model;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

/**
 * 用户信息表
 * 
 * @author qiqiim
 * @email 1044053532@qq.com
 * @date 2017-11-27 09:38:52
 */
public class UserInfoEntity extends BaseModel{
	private static final long serialVersionUID = 1L;
	//用户id
	private Long uid;
	//部门
	private Long deptid;
	//姓名
	private String name;
	//昵称
	private String nickname;
	//性别（0女 1男）
	private Integer sex;
	//生日
	private String birthday;
	//身份证
	private String cardid;
	//签名
	private String signature;
	//毕业院校
	private String school;
	//学历
	private Integer education;
	//现居住地址
	private String address;
	//联系电话
	private String phone;
	//邮箱
	private String email;
	//备注
	private String remark;
	//个人头像
	private String profilephoto;
	/**
	 * 设置：用户id
	 */
	public void setUid(Long uid) {
		this.uid = uid;
	}
	/**
	 * 获取：用户id
	 */
	public Long getUid() {
		return uid;
	}
	/**
	 * 设置：部门
	 */
	public void setDeptid(Long deptid) {
		this.deptid = deptid;
	}
	/**
	 * 获取：部门
	 */
	public Long getDeptid() {
		return deptid;
	}
	/**
	 * 设置：姓名
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 获取：姓名
	 */
	public String getName() {
		return name;
	}
	/**
	 * 设置：昵称
	 */
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	/**
	 * 获取：昵称
	 */
	public String getNickname() {
		return nickname;
	}
	/**
	 * 设置：性别（0女 1男）
	 */
	public void setSex(Integer sex) {
		this.sex = sex;
	}
	/**
	 * 获取：性别（0女 1男）
	 */
	public Integer getSex() {
		return sex;
	}
	/**
	 * 设置：生日
	 */
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	/**
	 * 获取：生日
	 */
	public String getBirthday() {
		return birthday;
	}
	/**
	 * 设置：身份证
	 */
	public void setCardid(String cardid) {
		this.cardid = cardid;
	}
	/**
	 * 获取：身份证
	 */
	public String getCardid() {
		return cardid;
	}
	/**
	 * 设置：签名
	 */
	public void setSignature(String signature) {
		this.signature = signature;
	}
	/**
	 * 获取：签名
	 */
	public String getSignature() {
		return signature;
	}
	/**
	 * 设置：毕业院校
	 */
	public void setSchool(String school) {
		this.school = school;
	}
	/**
	 * 获取：毕业院校
	 */
	public String getSchool() {
		return school;
	}
	/**
	 * 设置：学历
	 */
	public void setEducation(Integer education) {
		this.education = education;
	}
	/**
	 * 获取：学历
	 */
	public Integer getEducation() {
		return education;
	}
	/**
	 * 设置：现居住地址
	 */
	public void setAddress(String address) {
		this.address = address;
	}
	/**
	 * 获取：现居住地址
	 */
	public String getAddress() {
		return address;
	}
	/**
	 * 设置：联系电话
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}
	/**
	 * 获取：联系电话
	 */
	public String getPhone() {
		return phone;
	}
	/**
	 * 设置：邮箱
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	/**
	 * 获取：邮箱
	 */
	public String getEmail() {
		return email;
	}
	/**
	 * 设置：备注
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}
	/**
	 * 获取：备注
	 */
	public String getRemark() {
		return remark;
	}
	/**
	 * 设置：个人头像
	 */
	public void setProfilephoto(String profilephoto) {
		this.profilephoto = profilephoto;
	}
	/**
	 * 获取：个人头像
	 */
	public String getProfilephoto() {
		return   StringUtils.isNotEmpty(profilephoto)?profilephoto:"../static/layui/images/0.jpg";//profilephoto;
	}
	 
	@Override
	public String toString() {
		return  ReflectionToStringBuilder.toString(this);
	}
}
