package com.biz.test;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "com.biz.test2")
public class TestConfiguration {

    @Bean(name = "testBean4")
    public TestBean asd() {
        return new TestBean();
    }

    @Bean(name = { "testBean5" })
    public TestBean gdsfds() {
        return new TestBean();
    }
}
