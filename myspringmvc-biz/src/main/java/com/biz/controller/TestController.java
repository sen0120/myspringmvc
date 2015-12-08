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
public class TestController extends TestParents implements TestInterface {

    public Integer toInteger(String str) {
        System.out.println("toInteger");
        return Integer.parseInt(str);
    }

    /*public Integer toIntegerWrap(String str) {
        return toInteger(str);
    }*/
}
