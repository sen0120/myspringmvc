package com.test;

import com.tongbanjie.resconf.facade.ResSensitiveFacade;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.context.WebApplicationContext;

import java.util.Set;

@RunWith(SpringRunner.class)
@WebAppConfiguration()
//@ContextConfiguration(value = {"classpath*:springbeans-mvc.xml", "classpath*:myspringmvc-servlet.xml"})
@ContextConfiguration(value = {"classpath*:dubbo-test.xml"})
//@ContextConfiguration(value = {"classpath*:springbeans-mvc.xml"})
//@ContextConfiguration(value = { "classpath*:springbeans-mvc-test.xml" })
//@ContextConfiguration(value = {"classpath*:springbeans-mvc-test.xml"})
//@ContextConfiguration(classes = { TestConfiguration.class})
public class TestDubbo {

    @Autowired
    private WebApplicationContext wac;

    @Autowired
    private ResSensitiveFacade resSensitiveFacade;

    @Test
    public void test1() throws Exception {
        String word = resSensitiveFacade.replaceSensitiveWord("fuck man afuckw", "*");
        Set<String> sensitiveWord = resSensitiveFacade.getSensitiveWord("afuck迷昏药w");
        boolean bool = resSensitiveFacade.isContaintSensitiveWord("afuck迷昏药w");
        System.out.println(11);
    }

}
