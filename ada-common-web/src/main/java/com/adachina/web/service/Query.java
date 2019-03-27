package com.adachina.web.service;


import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 查询参数
 *
 * @author Jack
 */
public class Query extends LinkedHashMap<String, Object> {
    private static final long serialVersionUID = 1L;

    private int page = 1;

    private int limit = 10;

    public Query(Map<String, Object> params) {
        this.putAll(params);
        //分页参数
        if (params.get("page") != null) {
            this.page = Integer.parseInt(params.get("page").toString());
        }
        if (params.get("limit") != null) {
            this.limit = Integer.parseInt(params.get("limit").toString());
        }
        this.remove("page");
        this.remove("limit");
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
