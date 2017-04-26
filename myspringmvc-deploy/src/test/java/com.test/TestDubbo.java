package com.test;

import com.tongbanjie.commons.lang.Result;
import com.tongbanjie.resconf.facade.ResCatalogManagerFacade;
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
@ContextConfiguration(value = {"classpath*:dubbo-test.xml"})
//@ContextConfiguration(value = {"classpath*:springbeans-mvc.xml"})
//@ContextConfiguration(value = { "classpath*:springbeans-mvc-test.xml" })
//@ContextConfiguration(value = {"classpath*:springbeans-mvc-test.xml"})
//@ContextConfiguration(classes = { TestConfiguration.class})
public class TestDubbo {

    @Autowired
    private WebApplicationContext wac;

    @Autowired
    private ResCatalogManagerFacade resCatalogManagerFacade;

    @Test
    public void test1() throws Exception {
        Result<Void> sdf = resCatalogManagerFacade.deleteCatalog(1L, "sdf");
        System.out.println(sdf);
    }

}
