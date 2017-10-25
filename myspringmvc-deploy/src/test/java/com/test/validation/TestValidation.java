package com.test.validation;

import com.hus.biz.test.TestBean;
import com.hus.mapper.StudentMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.context.WebApplicationContext;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.constraints.NotNull;
import java.util.Iterator;
import java.util.Set;

@RunWith(SpringRunner.class)
@WebAppConfiguration()
//@ContextConfiguration(value = {"classpath*:springbeans-mvc.xml", "classpath*:myspringmvc-servlet.xml"})
//@ContextConfiguration(value = {"classpath*:springbeans-mvc.xml"})
//@ContextConfiguration(value = { "classpath*:springbeans-mvc-test.xml" })
@ContextConfiguration(value = {"classpath*:springbeans-mvc-test.xml"})
//@ContextConfiguration(classes = { TestConfiguration.class})
public class TestValidation {

    @Autowired
    TestBean testBean;

    @Autowired
    private StudentMapper studentMapper;

    @Autowired
    private WebApplicationContext wac;

    static Validator validator;

    static {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
//        factory.getValidator().
    }

    @Test
    public void test1() throws Exception {
        Car car = new Car();
        car.setPerson(new Person());
        Set<ConstraintViolation<Car>> constraintViolations = validator.validate(car);
        Iterator<ConstraintViolation<Car>> iterator = constraintViolations.iterator();
        while (iterator.hasNext()) {
            ConstraintViolation<Car> next = iterator.next();
            System.err.println(next.getMessage());
        }
        testInteger(null);
        System.out.println(1);

    }

    private void testInteger(@NotNull Integer integer) {

    }

}
