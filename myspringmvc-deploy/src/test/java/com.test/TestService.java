package com.test;

import com.biz.test3.TestElastic;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.context.WebApplicationContext;

import com.biz.test.TestBean;

import java.io.IOException;

@RunWith(SpringRunner.class)
@WebAppConfiguration()
//@ContextConfiguration(value = {"classpath*:springbeans-mvc.xml", "classpath*:myspringmvc-servlet.xml"})
//@ContextConfiguration(value = {"classpath*:springbeans-mvc.xml"})
//@ContextConfiguration(value = { "classpath*:springbeans-mvc-test.xml" })
@ContextConfiguration(value = { "classpath*:springbeans-mvc-test.xml" })
//@ContextConfiguration(classes = { TestConfiguration.class})
public class TestService {

    @Autowired
    TestBean                      testBean;

    @Autowired
    private WebApplicationContext wac;

    @BeforeClass
    public static void beforeAction() {
        System.out.println("beforClass");
    }

    @Test
    public void test1() throws Exception {
        /*System.out.println(wac.getBean("testBean") != null);
        System.out.println(wac.getBean("testBean2") != null);
        System.out.println(wac.getBean("testBeanCreateBean") != null);
        System.out.println(wac.getBean("testBean3") != null);
        System.out.println(wac.getBean("testBean4") != null);
        System.out.println(wac.getBean("testBean5") != null);

        System.out.println(wac.getBean("test2Class") != null);*/

    }

}
