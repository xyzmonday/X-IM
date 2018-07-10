package com.yff.xim.model;

import com.yff.xim.util.SQLFilter;
import lombok.extern.java.Log;
import lombok.extern.log4j.Log4j2;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 查询参数
 */
@Log4j2
public class Query extends LinkedHashMap<String, Object> {
    private static final long serialVersionUID = 1L;
    //当前页码
    private int page;
    //每页条数
    private int limit;

    public Query(Map<String, Object> params) {
        this.putAll(params);

        try {
            //分页参数
            this.page = Integer.parseInt(params.get("page").toString());
            this.limit = Integer.parseInt(params.get("limit").toString());
            this.put("offset", (page - 1) * limit);
            this.put("page", page);
            this.put("limit", limit);
        } catch (Exception e) {
            log.info(e);
        }

        //防止SQL注入（因为sidx、order是通过拼接SQL实现排序的，会有SQL注入风险）
        try {
            String sidx = params.get("sidx").toString();
            String order = params.get("order").toString();
            this.put("sidx", SQLFilter.sqlInject(sidx));
            this.put("order", SQLFilter.sqlInject(order));
        } catch (Exception e) {

        }

    }


    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }
}