package com.hus.controller;

import com.hus.domain.StudentDo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by fanyun on 16/10/20.
 */
@Controller
@RequestMapping("test")
public class TestMainController {
   /* @Autowired
    private StudentMapper studentMapper;*/

    @RequestMapping("test1")
    @ResponseBody
    public StudentDo test1() {
//        studentMapper.selectByExample(new StudentExample());
        StudentDo studentDo = new StudentDo();
        studentDo.setName("sdfsdf");
        return studentDo;
    }

    @RequestMapping("test2")
    public String test2() {
        return "test";
    }
}
