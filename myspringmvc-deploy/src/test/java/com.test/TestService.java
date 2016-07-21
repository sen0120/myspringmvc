package com.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.biz.test.TestBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringRunner.class)
@WebAppConfiguration()
//@ContextConfiguration(value = {"classpath*:springbeans-mvc.xml", "classpath*:myspringmvc-servlet.xml"})
//@ContextConfiguration(value = {"classpath*:springbeans-mvc.xml"})
@ContextConfiguration(value = { "classpath*:springbeans-mvc-test.xml" })
public class TestService {

    @Autowired
    TestBean testBean;

    @Autowired
    private WebApplicationContext wac;

    /* @BeforeClass
    public static void beforeAction() {
        ApplicationContext context = new ClassPathXmlApplicationContext(new String[] { "springbeans-mvc-test.xml" });
        testBean = context.getBean("testBean", TestBean.class);
    }*/

    @Test
    public void test1() {
        testBean.sout();
        System.out.println(wac.getBean("testBean")!=null);
    }

}
