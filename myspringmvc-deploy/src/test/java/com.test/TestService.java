package com.test;

import com.toowell.crm.facade.base.RemoteProjectEnum;
import com.toowell.crm.facade.base.Result;
import com.toowell.crm.facade.store.entity.StoreRo;
import com.toowell.crm.facade.store.service.StoreFacade;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = {"classpath*:springbeans-mvc.xml", "classpath*:myspringmvc-servlet.xml"}) //
// myspringmvc-servlet dubbo-consumer

public class TestService {
    @Resource(name = "storeFacade")
    StoreFacade storeFacade;

    @Test
    public void test1() {
        //Result<List<StoreRo>> result = storeFacade.getAllStoreDetails();
        Result<StoreRo> result = storeFacade.getStoreByStoreId("110", RemoteProjectEnum.CARMAN);
        System.out.println(result);

    }

}
