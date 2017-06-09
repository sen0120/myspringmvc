package com.hus.biz.test.designpattern.ChainOfResponsibilityPattern;

import org.springframework.mock.web.MockHttpServletRequest;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fanyun on 17/6/7.
 */
public class FilterChainDemo {
    public static void main(String[] args) {
        MyFilterChain chain = new MyFilterChain();
        List<Filter> filterList = new ArrayList<Filter>();
        chain.setFilterList(filterList);

        filterList.add(new OneFilter());
        filterList.add(new TwoFilter());
        filterList.add(new ThreeFilter());

        chain.doFilter(new MockHttpServletRequest(), null);

    }
}
