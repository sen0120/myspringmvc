package com.biz.controller;

import com.biz.annotation.TestAnnotation;
import com.biz.annotation.TestAnnotation2;
import org.junit.runner.RunWith;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by husen@qccr.com on 2015/12/3.
 */
@Component
@TestAnnotation(value = "qwe")
public class TestController extends TestParents implements TestInterface{
    @TestAnnotation(value = "Roger1")
    //@TestAnnotation2(value = "Roger2")
    public Integer toInteger(String str) {
        System.out.println("controller 1");
        return Integer.parseInt(str);
    }
}
