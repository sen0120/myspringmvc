package com.hus.biz.elasticsearch;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by fanyun on 17/1/18.
 */
public class SearchSelectResult {
    /**
     * 查询结果匹配总条数
     */
    private long total;
    /**
     * 页码
     */
    private int pageNo;
    /**
     * 每页大小
     */
    private int pageSize;
    /**
     * 查询结果,map的key为字段名,value为字段值,遍历map
     */
    private List<Map<String, Object>> list = new ArrayList();

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public List<Map<String, Object>> getList() {
        return list;
    }

    public void setList(List<Map<String, Object>> list) {
        this.list = list;
    }
}
