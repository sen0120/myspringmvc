package com.test.poi;

import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by muye on 18/3/16.
 * <p>
 * T享投交易成功时MQ推来的消息对象
 */
public class TPlanMqModel implements Serializable {

    //用户id
    private String userId;

    //投资用户类型 1-非标  2-铜子 3e直投
    private Integer type;

    //订单号
    private String orderNo;

    //消息来源 1-非标  2-铜子  3-续期 4e直接投
    private Integer source;

    //序列号
    private String serialNo;

    //产品期限
    private Integer prodPeriods;

    //投资金额
    private BigDecimal investAmount;

    //预约单号
    private String matchPreOrderNum;


    /**
     * 检查参数是否合法 必填参数校验及必须是铜子交易
     *
     * @param model
     * @return
     */
    public static boolean checkParam(TPlanMqModel model) {
        if (model.getType() == null || model.getType() != 2 || model.getSource() == null ||
                StringUtils.isEmpty(model.getUserId()) ||
                StringUtils.isEmpty(model.getOrderNo()) ||
                StringUtils.isEmpty(model.getSerialNo())) {
            return false;
        }
        return true;
    }


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public Integer getSource() {
        return source;
    }

    public void setSource(Integer source) {
        this.source = source;
    }

    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

    public Integer getProdPeriods() {
        return prodPeriods;
    }

    public void setProdPeriods(Integer prodPeriods) {
        this.prodPeriods = prodPeriods;
    }

    public BigDecimal getInvestAmount() {
        return investAmount;
    }

    public void setInvestAmount(BigDecimal investAmount) {
        this.investAmount = investAmount;
    }

    public String getMatchPreOrderNum() {
        return matchPreOrderNum;
    }

    public void setMatchPreOrderNum(String matchPreOrderNum) {
        this.matchPreOrderNum = matchPreOrderNum;
    }
}
