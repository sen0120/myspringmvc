package com.hus.test;

import com.alibaba.dubbo.common.json.JSONObject;
import com.hus.domain.StudentDo;
import com.hus.mapper.StudentMapper;
import com.toowell.crm.facade.base.BatchResult;
import com.toowell.crm.facade.base.RemoteProjectEnum;
import com.toowell.crm.facade.base.Result;
import com.toowell.crm.facade.product.service.ProductFacade;
import com.toowell.crm.facade.store.entity.BankRo;
import com.toowell.crm.facade.store.entity.CodeDescRo;
import com.toowell.crm.facade.store.entity.StoreRo;
import com.toowell.crm.facade.store.entity.StoreSafetyRo;
import com.toowell.crm.facade.store.service.StoreFacade;
import org.apache.commons.lang.StringUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:myspringmvc-servlet.xml" })

public class TestService {
    @Autowired
    StudentMapper studentMapper;

    @Test
    public void test1() {

    }
}
