package com.hus.biz.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * Created by husen@qccr.com on 2015/12/2.
 */
@Component
@Aspect
public class TestAspect {

    @Pointcut("execution(* com.hus.biz.service.TestService.toInteger(..))"//方法
            + "|| execution(* com.hus.biz.service.TestParents.*(..))")//类
    private void IDUMechod() {
    }

    @Before("IDUMechod()")
    public void args(JoinPoint joinPoint) {
        System.out.println("Before");
    }

    /* @Before("execution(* *.*(..)) && @annotation(testAnnotation) && @annotation(testAnnotation2)")
    public void myBeforeLogger(JoinPoint joinPoint, TestAnnotation testAnnotation,
                               TestAnnotation2 testAnnotation2) {
        System.out.println(testAnnotation.value());
        System.out.println(testAnnotation2.value());
    }*/

    /* @Before(value = "execution(* com.biz.service.TestController.toInteger() && args(para))", argNames = "para")
    public void before(String para) {
    //System.out.println("Before");
    System.out.println("para:" + para);
    }*/

    /*//声明例外通知
    @Around("facadePointCut()")
    public Object aroundMethod(ProceedingJoinPoint pjd) {
        try {
            Object proceed = pjd.proceed();
            return proceed;
        } catch (Throwable throwable) {
            logger.error("aroundMethod捕获异常", throwable);
            return Result.buildFail(ModelStatusCodeConstants.BUSINESS_ERROR + "", "服务器错误,请联系客服", throwable.getMessage());
        }
    }*/

}
