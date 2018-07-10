package com.yff.xim.model;

import java.io.Serializable;

public abstract class BaseModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2539516018935036745L;


	public abstract String toString();
	
	/**
	 * 总记录数
	 * */
	protected int totalResults;
	
	/**
     * 标识是否分页，等于1 则为分页
     */
    protected int pagerFlag;
    /**
     * 起始行数
     */
    protected int rows;
    /**
     * 显示的记录数
     */
    protected int rowsCount;
    
    /**
     * 起始条数数
     */
    protected Integer startsize;
    
    /**
     * 结束条数
     */
    protected int endsize;
    
    protected String keyWord;
    
    protected Long id;
    
    protected String createdate="";

    protected Long createuser;
    
    protected String createuserName;

    protected String updatedate="";

    protected Long updateuser;
    
    protected String updateuserName;
  

	public int getTotalResults() {
		return totalResults;
	}

	public void setTotalResults(int totalResults) {
		this.totalResults = totalResults;
	}

	public int getPagerFlag() {
		return pagerFlag;
	}

	public void setPagerFlag(int pagerFlag) {
		this.pagerFlag = pagerFlag;
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public int getRowsCount() {
		return rowsCount;
	}

	public void setRowsCount(int rowsCount) {
		this.rowsCount = rowsCount;
	}

	public Integer getStartsize() {
		return startsize;
	}

	public void setStartsize(Integer startsize) {
		this.startsize = startsize;
	}

	public int getEndsize() {
		return endsize;
	}

	public void setEndsize(int endsize) {
		this.endsize = endsize;
	}

	public String getKeyWord() {
		return keyWord;
	}

	public void setKeyWord(String keyWord) {
		this.keyWord = keyWord;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCreatedate() {
		return createdate;
	}

	public void setCreatedate(String createdate) {
		this.createdate = createdate;
	}

	public Long getCreateuser() {
		return createuser;
	}

	public void setCreateuser(Long createuser) {
		this.createuser = createuser;
	}

	public String getCreateuserName() {
		return createuserName;
	}

	public void setCreateuserName(String createuserName) {
		this.createuserName = createuserName;
	}

	public String getUpdatedate() {
		return updatedate;
	}

	public void setUpdatedate(String updatedate) {
		this.updatedate = updatedate;
	}

	public Long getUpdateuser() {
		return updateuser;
	}

	public void setUpdateuser(Long updateuser) {
		this.updateuser = updateuser;
	}

	public String getUpdateuserName() {
		return updateuserName;
	}

	public void setUpdateuserName(String updateuserName) {
		this.updateuserName = updateuserName;
	}

	 
}