package com.test;

import com.biz.controller.TestController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = { "classpath*:springbeans-mvc.xml" })

public class TestService {
    @Resource
    TestController testController;

    @Test
    public void test1() {
        System.out.println("test1:" + testController.toInteger("5"));
    }

}
