package com.test;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.context.WebApplicationContext;

import com.biz.test.TestBean;

import java.io.File;
import java.io.FileInputStream;

@RunWith(SpringRunner.class)
@WebAppConfiguration()
//@ContextConfiguration(value = {"classpath*:springbeans-mvc.xml", "classpath*:myspringmvc-servlet.xml"})
//@ContextConfiguration(value = {"classpath*:springbeans-mvc.xml"})
//@ContextConfiguration(value = { "classpath*:springbeans-mvc-test.xml" })
@ContextConfiguration(value = {"classpath*:springbeans-mvc-test.xml"})
//@ContextConfiguration(classes = { TestConfiguration.class})
public class TestService {

    @Autowired
    TestBean testBean;

    @Autowired
    private WebApplicationContext wac;

    @BeforeClass
    public static void beforeAction() {
        System.out.println("beforClass");
    }

    @Test
    public void test1() throws Exception {
        BeanFactory xmlBeanFactory = new XmlBeanFactory(new FileSystemResource("~/bean.xml"));

        BeanFactory xmlBeanFactory1 = new XmlBeanFactory(new ClassPathResource("bean.xml"));

        XmlBeanFactory xmlBeanFactory2 = new XmlBeanFactory(new InputStreamResource(new FileInputStream(new File(""))));

        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("bean.xml");

        FileSystemXmlApplicationContext applicationContext1 = new FileSystemXmlApplicationContext("~/bean.xml");

        DefaultListableBeanFactory defaultListableBeanFactory = new DefaultListableBeanFactory();
        XmlBeanDefinitionReader xmlBeanDefinitionReader = new XmlBeanDefinitionReader(defaultListableBeanFactory);
        xmlBeanDefinitionReader.loadBeanDefinitions(new FileSystemResource("~/bean.xml"));

//        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader("sdf.xml");
    }

}
