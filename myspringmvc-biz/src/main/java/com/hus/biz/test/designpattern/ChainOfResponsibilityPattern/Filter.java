package com.hus.biz.test.designpattern.ChainOfResponsibilityPattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by fanyun on 17/6/7.
 */
public interface Filter {
    void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain);
}
