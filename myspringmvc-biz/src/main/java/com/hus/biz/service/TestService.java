package com.hus.biz.service;

import com.hus.biz.annotation.TestAnnotation;
import org.springframework.stereotype.Component;

/**
 * Created by husen@qccr.com on 2015/12/3.
 */
@Component
@TestAnnotation(value = "qwe")
public class TestService extends TestParents implements TestInterface {

    public Integer toInteger(String str) {
        System.out.println("toInteger");
        return Integer.parseInt(str);
    }

    /*public Integer toIntegerWrap(String str) {
        return toInteger(str);
    }*/
}
