package com.hus.controller;

import com.hus.biz.valid.ValidSupportInterface;
import com.hus.biz.vo.ValidationBeanVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * validation 测试类
 */
@Controller
public class TestValidateController {
    private static final Logger logger = LoggerFactory.getLogger(TestValidateController.class);

    /**
     * @param vo     绑定validate注解的bean
     * @param result validate验证结果类
     * @return
     */
    @RequestMapping("/test/testValidated")
    @ResponseBody
    public Map webPost(@Validated({ValidSupportInterface.ParameterClass1.class}) ValidationBeanVO vo,
                       BindingResult result,
                       HttpServletRequest request, HttpServletResponse response) {
        Map map = new HashMap();
        if (result.hasErrors()) {//validate结果使用例子
            List<ObjectError> allErrors = result.getAllErrors();
            ObjectError objectError = allErrors.get(0);
            String defaultMessage = objectError.getDefaultMessage();//错误信息
            map.put("errorMsg", defaultMessage);
        }
        return map;

    }


}
