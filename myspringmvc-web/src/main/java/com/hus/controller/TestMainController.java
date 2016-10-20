package com.hus.controller;

import com.hus.domain.StudentDo;
import com.hus.domain.StudentExample;
import com.hus.mapper.Student2Mapper;
import com.hus.mapper.Student3Mapper;
import com.hus.mapper.StudentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by fanyun on 16/10/20.
 */
@Controller
@RequestMapping("test")
public class TestMainController {
    @Autowired
    private StudentMapper studentMapper;
    @Autowired
    private Student2Mapper student2Mapper;
    @Autowired
    private Student3Mapper student3Mapper;

    @RequestMapping("test1")
    @ResponseBody
    public StudentDo test1() {
        try {
            studentMapper.selectByExample(new StudentExample());
            student2Mapper.selectByExample(new StudentExample());
            student3Mapper.selectByExample(new StudentExample());
        } catch (Exception e) {
            e.printStackTrace();
        }
        StudentDo studentDo = new StudentDo();
        studentDo.setName("sdfsdf");
        return studentDo;
    }

    @RequestMapping("test2")
    public String test2() {
        return "test";
    }
}
