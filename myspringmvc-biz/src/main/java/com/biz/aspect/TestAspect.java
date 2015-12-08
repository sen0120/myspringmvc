package com.biz.aspect;

import com.biz.annotation.TestAnnotation;
import com.biz.annotation.TestAnnotation2;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

/**
 * Created by husen@qccr.com on 2015/12/2.
 */
@Component
@Aspect
public class TestAspect {

    @Pointcut("execution(* com.biz.controller.TestController.toInteger(..))")
    private void IDUMechod() {
    }

    @Before("IDUMechod()")
    public void args(JoinPoint joinPoint) {
        System.out.println("Before");
    }

    /*@Before("target(com.biz.controller.TestController)")
    public void target(JoinPoint joinPoint) {
        System.out.println("target");
    }*/

    /*@Before("this(com.biz.controller.TestParents)")
    public void this1(JoinPoint joinPoint) {
        System.out.println("this");
    }*/

    /*@Before("@within(com.biz.annotation.TestAnnotation)")
    public void within(JoinPoint joinPoint) {
        System.out.println("within");
    }*/

    /* @Before("args (java.lang.String)")
    public void args(JoinPoint joinPoint) {
        System.out.println("args");
    }*/

    /*@Pointcut
    private void IDUMechod2(Object params) {
    }*/

    /* @Before("execution(* *.*(..)) && @annotation(testAnnotation) && @annotation(testAnnotation2)")
    public void myBeforeLogger(JoinPoint joinPoint, TestAnnotation testAnnotation,
                               TestAnnotation2 testAnnotation2) {
        System.out.println(testAnnotation.value());
        System.out.println(testAnnotation2.value());
    }*/

    /* @Before(value = "execution(* com.biz.controller.TestController.toInteger() && args(para))", argNames = "para")
    public void before(String para) {
    //System.out.println("Before");
    System.out.println("para:" + para);
    }*/

    /* @Around(value = "IDUMechod()")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
    System.out.println("around befor");
    Object proceed = pjp.proceed();
    System.out.println("around after");
    return proceed;
    }
    
    @AfterReturning(value = "IDUMechod()")
    public void afterReturning() {
    System.out.println("AfterReturning");
    }
    
    @AfterThrowing(value = "IDUMechod()")
    public void afterThrowing(JoinPoint joinPoint) {
    System.out.println("afterThrowing");
    }
    
    @After(value = "IDUMechod()")
    public void after(JoinPoint joinPoint) {
    System.out.println("After");
    }*/

}
