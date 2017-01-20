package com.hus.biz.elasticsearch;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 是否搜索,bean中只有加了次注释的字段才会提供搜索服务
 */
@Target(FIELD)
@Retention(RUNTIME)
public @interface SearchFieldProperties {
    String type() default "";

    String store() default "";

    String index() default "";

    String analyzer() default "";
}
