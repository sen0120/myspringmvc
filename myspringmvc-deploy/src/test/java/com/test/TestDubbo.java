package com.test;

import com.tongbanjie.commons.lang.Result;
import com.tongbanjie.score.common.dataobject.ScoreExchangeStream;
import com.tongbanjie.score.common.vo.ScoreSpendVO;
import com.tongbanjie.score.consts.ScoreConsts;
import com.tongbanjie.score.enums.ScoreRelatEnum;
import com.tongbanjie.score.facade.ScoreManageFacade;
import com.tongbanjie.score.model.request.SendScoreRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringRunner.class)
@WebAppConfiguration()
//@ContextConfiguration(value = {"classpath*:springbeans-mvc.xml", "classpath*:myspringmvc-servlet.xml"})
@ContextConfiguration(value = {"classpath*:dubbo-test.xml"})
//@ContextConfiguration(value = {"classpath*:springbeans-mvc.xml"})
//@ContextConfiguration(value = { "classpath*:springbeans-mvc-test.xml" })
//@ContextConfiguration(value = {"classpath*:springbeans-mvc-test.xml"})
//@ContextConfiguration(classes = { TestConfiguration.class})
public class TestDubbo {

    @Autowired
    private WebApplicationContext wac;

    @Autowired
    private ScoreManageFacade scoreManageFacade;

    @Test
    public void test1() throws Exception {
        SendScoreRequest sendReq = new SendScoreRequest();
        sendReq.setUserId("100086544");
        sendReq.setScore(Integer.parseInt("10000000"));
        sendReq.setSendType(ScoreRelatEnum.CRM_GENERAL_MANUAL.getSendType());
        sendReq.setRemark("sdf");
        sendReq.setOperator("admin");
        sendReq.setOperateType(ScoreConsts.ScoreStreamOperateType.TONGBANJIE_GIFT);
        Result<Void> voidResult = scoreManageFacade.executeSendScore(sendReq);
        System.out.println(voidResult);

        /*ScoreSpendVO scoreSpendVo = new ScoreSpendVO();
        ScoreExchangeStream scoreExchangeStream = new ScoreExchangeStream();
        scoreExchangeStream.setUserId("100086544");
        scoreExchangeStream.setProductType(ScoreConsts.ScoreProductType.TO_BALANCE);
        scoreExchangeStream.setOperator("SYSTEM");
        scoreExchangeStream.setExchangeStatus(ScoreConsts.ScoreExchangeStatus.EXCHANGE_SUCCESS);
        scoreSpendVo.setCostType(1);
        scoreSpendVo.setScoreExchangeStream(scoreExchangeStream);
        Result<ScoreSpendVO> spendRes = scoreManageFacade.spend(scoreSpendVo);
        System.out.println(spendRes);*/
    }

}
