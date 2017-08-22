package com.test;

import com.tongbanjie.score.consts.ScoreConsts;
import com.tongbanjie.score.enums.ScoreRelatEnum;
import com.tongbanjie.score.facade.ScoreManageFacade;
import com.tongbanjie.score.model.request.SendScoreRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringRunner.class)
@WebAppConfiguration()
//@ContextConfiguration(value = {"classpath*:springbeans-mvc.xml", "classpath*:myspringmvc-servlet.xml"})
@ContextConfiguration(value = {"classpath*:myspringmvc-servlet.xml"})
//@ContextConfiguration(value = {"classpath*:springbeans-mvc.xml"})
//@ContextConfiguration(value = { "classpath*:springbeans-mvc-test.xml" })
//@ContextConfiguration(value = {"classpath*:springbeans-mvc-test.xml"})
//@ContextConfiguration(classes = { TestConfiguration.class})
public class TestService {

    @Autowired
    private WebApplicationContext wac;



    @Test
    public void test1() throws Exception {
    }

}
