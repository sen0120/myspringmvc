package com.test.validation.myconsistent;

import javax.validation.Payload;

public @interface ConsistentDateParameters {
    String message() default "{org.hibernate.validator.referenceguide.chapter06." +
            "crossparameter.ConsistentDateParameters.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}