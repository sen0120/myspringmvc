package com.biz.valid;

import javax.validation.constraints.NotNull;

/**
 * Created by fanyun on 16/10/24.
 * 用与给hibernate validate验证分组的的group做参数用
 * @see NotNull#groups()
 */
public interface ValidSupportInterface {
    /**
     * web端发帖
     */
    interface ParameterClass1 {
    }

    interface ParameterClass2 {
    }

}
