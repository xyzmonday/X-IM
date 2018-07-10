package com.yff.xim.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 分页，可以用于手机端，通用分页类<br>
 * 传入的数据可以覆盖定义的数据<br>
 * int pageSize = 10:每页几条数据<br>
 * int pageNo = 1:第几页<br>
 * int totalPages = 10:总的页数<br>
 * int totalSize = 0:总的记录数<br>
 * int step = 5:步长<br>
 * String url="/":跳转页面url<br>
 * String showPage:页面嵌套的底部分页<br>
 * Map<String,Object> params:分页所带的参数<br>
 * List<?> resultList:分页返回结果集<br>
 * */
public class Pager {
	private int pageSize = 10;		// 每页多少条
	private int pageNo = 1; 		// 第几页
	private int totalPages = 10;	// 总的页数
	private int totalSize = 0;	// 总的记录数
	private int step = 5;		//步长
	private String url="/"; 			// 跳转页面url
	private String showPage;		//页面嵌套的底部分页	
	private Map<String,Object> params; //ajax 分页所带的参数
	private List<?> resultList; //分页返回结果集
	
	public Pager() {
	}
	
	public Pager(int pageNo, int pageSize) {
		super();
		this.pageSize = pageSize;
		this.pageNo = pageNo;
	}
	
	public Pager(int pageNo, int pageSize,int totalSize) {
		super();
		this.pageSize = pageSize;
		this.pageNo = pageNo;
		this.totalSize = totalSize;
	}
	

	public int getBegin() {
		return (this.getPageNo() - 1) * this.getPageSize() + 1;
	}

	public int getEnd() {
		return (this.getPageNo() - 1) * this.getPageSize() + this.getPageSize();
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		if (pageSize < 1) {
			this.pageSize = 10;
		} else {
			this.pageSize = pageSize;
		}
	}

	public int getPageNo() {
		if (this.pageNo > this.getTotalPages() && this.getTotalSize() > 0) {
			this.pageNo = this.getTotalPages(); // 如果当前页大于最大页数，则等于最大页数
		}
		return pageNo;
	}

 
	public void setPageNo(int pageNo) {
		if (pageNo < 1) {
			this.pageNo = 1;
		} else {
			this.pageNo = pageNo;
		}
	}

	public int getTotalPages() {
		totalPages = totalPages < 1 ? 1 : totalPages;
		totalPages = getTotalSize() % getPageSize() == 0 ? (getTotalSize() / getPageSize()) : (getTotalSize() / getPageSize() + 1);
		return totalPages;
	}

	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}

	public int getTotalSize() {
		try{
			if(resultList!=null&&resultList.size()>0){
				BaseModel  model =	 (BaseModel)resultList.get(0);
				totalSize = model.getTotalResults();
			}
		}catch(Exception e){
			 try{
					if(resultList!=null&&resultList.size()>0){
						BaseModel  model =	 (BaseModel)resultList.get(0);
						totalSize = model.getTotalResults();
					}
			  }catch(Exception ee){
				  
			  }
		}
		return totalSize;
	}

	public void setTotalSize(int totalSize) {
		this.totalSize = totalSize;
	}

	public String getUrl() {
		return url;
	}

	private String getUrl(int i) {

		String url = "";
		if (this.getUrl().indexOf("?") > 0) {
			url = this.getUrl() + "&pageNo=" + i;
		} else {
			url = this.getUrl() + "?pageNo=" + i;
		}
		url = url + getParamsGet();
		return url;
	}
	/**
	 * 静态URL拼接
	 * @param i
	 * @return
	 */
	private String getStaticUrl(int i) { 
		
		String url = ""; 
		if(getParams() == null || getParams().isEmpty()) {
			return "";
		}
	    url = getUrl(); 
		for (Map.Entry<String, Object> entry : getParams().entrySet()) {
			if(entry.getKey().equals("{p}")){
				url = url.replace(entry.getKey(), String.valueOf(i)); 
			}else{
				url = url.replace(entry.getKey(), (String)entry.getValue()); 
			} 
		}
		return url;  
	}

	public void setUrl(String url) {
		this.url = url;
	}

	
	/**
	 * post提交分页
	 * @return
	 * <div class="dataTables_info" id="dataTable1_info">当前显示 1 	到 10 条，共 14 条记录</div>
										<div class="dataTables_paginate paging_full_numbers" >
											<a   	class="first paginate_button paginate_button_disabled"  >首页</a><a   class="previous paginate_button paginate_button_disabled"  >上一页</a><span><a   	class="paginate_active">1</a><a  	class="paginate_button">2</a></span><a  class="next paginate_button"  >下一页</a><a   class="last paginate_button" 	 >末页</a>
			  	</div>
	 * 
	 */
	public String getShowPage() {
		showPage = "";
		if (this.getTotalPages() > 1) { // 如果总页数大于一页，才显示分页栏
			StringBuffer sb = new StringBuffer();
			sb.append("<div class='dataTables_info' id='dataTable1_info'>当前显示{0}到 {1} 条，共  {2} 条记录</div>");
			sb.append("<div class='dataTables_paginate paging_full_numbers' >");
			if (this.getPageNo() > 1) { // 如果当前页不是第一页，则显示上一页
				sb.append("<a   	class='first paginate_button'  href='javascript:Pager.gotoPage( 1)'>首页</a><a   class='previous paginate_button'  href='javascript:Pager.gotoPage( " + (this.getPageNo() - 1) + ")'>上一页</a>");
			}else{
				sb.append("<a   	class='first paginate_button paginate_button_disabled'  >首页</a><a   class='previous paginate_button paginate_button_disabled' >上一页</a>");
			}
			
			if(totalPages>1){
				 int beginNum =pageNo - 2;
				 if (beginNum >totalPages - 4) beginNum = totalPages - 4;
	             if (beginNum < 1) beginNum = 1;
	             int endNum = beginNum + 4;
	             if (endNum >totalPages) endNum =totalPages;
	             for (int i = beginNum; i <= endNum; i++) {
	                    if (i ==totalPages) {
	                        sb.append("<a   	class='"+((pageNo==i)?"paginate_active":"paginate_button")+"'  href='javascript:Pager.gotoPage(" +i +")' >" + i+ "</a>");
	                    }
	                    else {
	                        sb.append("<a   	class='"+((pageNo==i)?"paginate_active":"paginate_button")+"' href='javascript:Pager.gotoPage(" + i +")' >" + i+ "</a>");
	                    }
	              }
			}else{
				//<span></span>
				
			}
			
			if (this.getPageNo() < totalPages) { // 如果当前页不是最后一页，则显示下一页
				sb.append("	<a  class='next paginate_button'   href='javascript:Pager.gotoPage(" + (this.getPageNo() + 1) +")' >下一页</a><a   class='last paginate_button'   href='javascript:Pager.gotoPage(" +getTotalPages() +")' 	 >末页</a><input  id='pgsize'   value=''  style='margin-left:5px;width:20px' /><a  class='next paginate_button'   href='javascript:var tosize="+totalPages+";var size=$(\"#pgsize\").val();if(size>tosize){size=tosize};Pager.gotoPage(size);' >跳到</a> ");
			}else{
				sb.append("<a   	class='first paginate_button paginate_button_disabled'  >下一页</a><a   class='previous paginate_button paginate_button_disabled' >末页</a><input  id='pgsize'   value=''  style='margin-left:5px;width:20px' /><a  class='next paginate_button'   href='javascript:var tosize="+totalPages+";var size=$(\"#pgsize\").val();if(size>tosize){size=tosize};Pager.gotoPage(size);' >跳到</a> ");
			}
			sb.append("<form  id='pagerForm' name=\"pagerForm\" action=\""+ this.url + "\" method=\"post\">");
			sb.append("<input type='hidden' name='skipToPage'  id='skipToPage'  value='"+pageNo+"'>");
			sb.append(this.getParamsPost());
			sb.append("</form>");
			sb.append("</div>\n");
	 
			showPage = sb.toString();
			showPage = showPage.replace("{0}",String.valueOf(getBegin()) ).replace("{1}", String.valueOf(getEnd())).replace("{2}", String.valueOf(getTotalSize()));
			return showPage;
		}
		return "";
	}
	
	/**
	 * get提交分页
	 * @return
	 */
	public String getShowUrlPage() {
		if (this.getTotalPages() > 1) { // 如果总页数大于一页，才显示分页栏
			StringBuffer sb = new StringBuffer();
			sb.append("<div class='fanye'>\n");

			if (this.getPageNo() > 1) { // 如果当前页不是第一页，则显示上一页
				sb.append("<a href='" + this.getUrl(this.getPageNo()-1) + "'>上一页</a>\n");
			}
		
			if (this.getPageNo() < this.getTotalPages()) { // 如果当前页不是最后一页，则显示下一页
				sb.append("<a href='" + this.getUrl(this.getPageNo()+1) + "'>下一页</a>\n");
			}
			sb.append("</div>\n");
			return sb.toString();
		}
		return "";
	}
	
	/**
	 * get伪静态分页
	 * @return
	 */
	public String getShowStaticUrlPage() {
		if (this.getTotalPages() > 1) { // 如果总页数大于一页，才显示分页栏
			StringBuffer sb = new StringBuffer();
			sb.append("<div class='fanye'>\n");

			if (this.getPageNo() > 1) { // 如果当前页不是第一页，则显示上一页
				sb.append("<a href='" + this.getStaticUrl(this.getPageNo()-1) + "'>上一页</a>\n");
			}
		
			if (this.getPageNo() < this.getTotalPages()) { // 如果当前页不是最后一页，则显示下一页
				sb.append("<a href='" + this.getStaticUrl(this.getPageNo()+1) + "'>下一页</a>\n");
			}
			sb.append("</div>\n");
			return sb.toString();
		}
		return "";
	}


	public Map<String, Object> getParams() {
		if(null == params) {
			params = new HashMap<String, Object>();
		}
		return params;
	}

	public void setParams(Map<String, Object> params) {
		this.params = params;
	}

	public int getStep() {
		return step;
	}

	public void setStep(int step) {
		this.step = step;
	}
 
	
	public List<?> getResultList() {
		return resultList;
	}

	public void setResultList(List<?> resultList) {
		this.resultList = resultList;
	}

	private String getParamsGet() {
		if(getParams() == null || getParams().isEmpty()) {
			return "";
		}
		StringBuffer sb = new StringBuffer();
		for (Map.Entry<String, Object> entry : getParams().entrySet()) {
			sb.append("&");
			sb.append(entry.getKey());
			sb.append("=");
			sb.append(entry.getValue());
		}
		return sb.toString();
	}
	
	private String getParamsPost() {
		if(getParams() == null || getParams().isEmpty()) {
			return "";
		}
		StringBuffer sb = new StringBuffer();
		for (Map.Entry<String, Object> entry : getParams().entrySet()) {
			if( entry.getValue() !=null)
			sb.append("<input type=\"hidden\" name=\"" + entry.getKey() + "\" value=\"" + entry.getValue() + "\" />");
		}
	
		return sb.toString();
	}

	

}
