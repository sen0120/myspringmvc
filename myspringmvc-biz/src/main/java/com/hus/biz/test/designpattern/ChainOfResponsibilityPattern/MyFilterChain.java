package com.hus.biz.test.designpattern.ChainOfResponsibilityPattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Iterator;
import java.util.List;

/**
 * Created by fanyun on 17/6/7.
 */
public class MyFilterChain implements FilterChain {
    private HttpServletRequest request;
    private List<Filter> filterList;
    private Iterator<Filter> iterator;
    private int currentPosition = 0;

    public void doFilter(HttpServletRequest request, HttpServletResponse response) {
        if (this.request != null) {
            throw new IllegalStateException("这个责任链已经调用过了");
        }
        this.request = request;

        iterator = filterList.iterator();
        int i = 0;
        while (iterator.hasNext() && i == 0) {
            Filter next = iterator.next();
            next.doFilter(request, response, this);
        }


    }

    public List<Filter> getFilterList() {
        return filterList;
    }

    public void setFilterList(List<Filter> filterList) {
        this.filterList = filterList;
    }
}
