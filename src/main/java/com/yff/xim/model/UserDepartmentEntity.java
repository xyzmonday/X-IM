package com.yff.xim.model;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;


/**
 * 部门
 * 
 * @author qiqiim
 * @email 1044053532@qq.com
 * @date 2017-11-27 09:38:52
 */
public class UserDepartmentEntity extends BaseModel{
	private static final long serialVersionUID = 1L;


	//部门名称
	private String name;
	//部门人数
	private Integer count;
	//等级
	private Integer level;
	//上级部门ID
	private Long parentid;
	//备注
	private String remark;
	//是否删除（0否1是）
	private Integer isdel;

	/**
	 * 设置：部门名称
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 获取：部门名称
	 */
	public String getName() {
		return name;
	}
	/**
	 * 设置：部门人数
	 */
	public void setCount(Integer count) {
		this.count = count;
	}
	/**
	 * 获取：部门人数
	 */
	public Integer getCount() {
		return count;
	}
	/**
	 * 设置：等级
	 */
	public void setLevel(Integer level) {
		this.level = level;
	}
	/**
	 * 获取：等级
	 */
	public Integer getLevel() {
		return level;
	}
	/**
	 * 设置：上级部门ID
	 */
	public void setParentid(Long parentid) {
		this.parentid = parentid;
	}
	/**
	 * 获取：上级部门ID
	 */
	public Long getParentid() {
		return parentid;
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
	 * 设置：是否删除（0否1是）
	 */
	public void setIsdel(Integer isdel) {
		this.isdel = isdel;
	}
	/**
	 * 获取：是否删除（0否1是）
	 */
	public Integer getIsdel() {
		return isdel;
	}

	 
	@Override
	public String toString() {
		return  ReflectionToStringBuilder.toString(this);
	}
}
