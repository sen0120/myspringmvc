package com.hus.test;

import com.hus.domain.StudentDo;
import com.hus.mapper.StudentMapper;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:myspringmvc-servlet.xml" })

public class TestService {
    @Autowired
    StudentMapper studentMapper;

    @Test
    public void test1() {
        StudentDo record = new StudentDo();
        record.setName("Jim");
        record.setAge(1);
        int num = studentMapper.insert(record);
        System.out.println(num);
        Assert.assertTrue(num > 0);
    }
}
