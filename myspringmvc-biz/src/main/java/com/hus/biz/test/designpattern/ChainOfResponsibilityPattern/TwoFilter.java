package com.hus.biz.test.designpattern.ChainOfResponsibilityPattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by fanyun on 17/6/7.
 */
public class TwoFilter implements Filter {
    public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) {
        System.out.println("TwoFilter");
        filterChain.doFilter(request, response);
    }
}
