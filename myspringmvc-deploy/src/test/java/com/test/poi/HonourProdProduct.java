package com.test.poi;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * honour_prod_product 实体类
 * Wed Dec 13 20:04:04 CST 2017 AutoGenerate
 */
public class HonourProdProduct implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 数据库字段长度:20
     * 字段备注:主键
     * Wed Dec 13 20:04:04 CST 2017 AutoGenerate
     */
    @JSONField(ordinal = 1)
    private String id;

    /**
     * 数据库字段长度:50
     * 字段备注:产品名称
     * Wed Dec 13 20:04:04 CST 2017 AutoGenerate
     */
    @JSONField(ordinal = 1)
    private String name;

    /**
     * 产品展现优先级，数字越高越靠前
     */
    @JSONField(ordinal = 1)
    private String priority;

    /**
     * 数据库字段长度:10
     * 字段备注:产品年化收益率
     * Wed Dec 13 20:04:04 CST 2017 AutoGenerate
     */
    @JSONField(ordinal = 2)
    private BigDecimal profit;

    /**
     * 数据库字段长度:10
     * 字段备注:产品期限：D,日；M,月;Y，年
     * Wed Dec 13 20:04:04 CST 2017 AutoGenerate
     */
    @JSONField(ordinal = 3)
    private String period;

    /**
     * 数据库字段长度:11
     * 字段备注:交易人数
     * Wed Dec 13 20:04:04 CST 2017 AutoGenerate
     */
    private Long tradeNum;

    /**
     * 数据库字段长度:18
     * 字段备注:实际交易份额
     * Wed Dec 13 20:04:04 CST 2017 AutoGenerate
     */
    private BigDecimal tradeRealAmount;

    /**
     * 数据库字段长度:18
     * 字段备注:交易份额
     * Wed Dec 13 20:04:04 CST 2017 AutoGenerate
     */
    private BigDecimal tradeAmount;

    /**
     * 数据库字段长度:11
     * 字段备注:产品状态：0，待售；1，起售；2.停售；3.售罄
     * Wed Dec 13 20:04:04 CST 2017 AutoGenerate
     */
    @JSONField(ordinal = 100)
    private Integer status;

    /**
     * 数据库字段长度:10
     * 字段备注:产品起息日
     * Wed Dec 13 20:04:04 CST 2017 AutoGenerate
     */
    @JSONField(ordinal = 4)
    private Date startBenefit;

    /**
     * 数据库字段长度:10
     * 字段备注:产品止息日
     * Wed Dec 13 20:04:04 CST 2017 AutoGenerate
     */
    @JSONField(ordinal = 5)
    private Date endBenefit;

    /**
     * 数据库字段长度:18
     * 字段备注:剩余份额
     * Wed Dec 13 20:04:04 CST 2017 AutoGenerate
     */
    @JSONField(ordinal = 6)
    private BigDecimal remainAmount;

    /**
     * 数据库字段长度:18
     * 字段备注:起购金额
     * Wed Dec 13 20:04:04 CST 2017 AutoGenerate
     */
    @JSONField(ordinal = 7)
    private BigDecimal leastAmount;

    /**
     * 数据库字段长度:18
     * 字段备注:最高金额
     * Wed Dec 13 20:04:04 CST 2017 AutoGenerate
     */
    private BigDecimal limitAmount;

    /**
     * 数据库字段长度:11
     * 字段备注:产品购买累进金额
     * Wed Dec 13 20:04:04 CST 2017 AutoGenerate
     */
    @JSONField(ordinal = 8)
    private BigDecimal steppingAmount;

    /**
     * 数据库字段长度:18
     * 字段备注:募集金额
     * Wed Dec 13 20:04:04 CST 2017 AutoGenerate
     */
    @JSONField(ordinal = 6)
    private BigDecimal raiseAmount;

    /**
     * 数据库字段长度:20
     * 字段备注:商户ID
     * Wed Dec 13 20:04:04 CST 2017 AutoGenerate
     */
    private String merchantId;

    /**
     * 数据库字段长度:2
     * 字段备注:回款类型：0（到期一次还本付息）
     * Wed Dec 13 20:04:04 CST 2017 AutoGenerate
     */
    private Integer redeemType;

    /**
     * 数据库字段长度:64
     * 字段备注:回款方式描述
     * Wed Dec 13 20:04:04 CST 2017 AutoGenerate
     */
    @JSONField(ordinal = 9)
    private String redeemTypeDesc;

    /**
     * 数据库字段长度:19
     * 字段备注:售罄时间
     * Wed Dec 13 20:04:04 CST 2017 AutoGenerate
     */
    private Date soldOutTime;

    /**
     * 数据库字段长度:11
     * 字段备注:期限天数,对应period
     * Wed Dec 13 20:04:04 CST 2017 AutoGenerate
     */
    @JSONField(ordinal = 3)
    private Integer periodDaysInt;

    /**
     * 数据库字段长度:21845
     * 字段备注:产品简介
     * Wed Dec 13 20:04:04 CST 2017 AutoGenerate
     */
    @JSONField(ordinal = 10)
    private String note;

    /**
     * 数据库字段长度:19
     * 字段备注:创建时间
     * Wed Dec 13 20:04:04 CST 2017 AutoGenerate
     */
    private Date createTime;

    /**
     * 数据库字段长度:19
     * 字段备注:产品更新时间
     * Wed Dec 13 20:04:04 CST 2017 AutoGenerate
     */
    private Date modifyTime;


    public HonourProdProduct() {
    }

    public HonourProdProduct setId(String id) {
        this.id = id;
        return this;
    }

    public String getId() {
        return id;
    }

    public HonourProdProduct setName(String name) {
        this.name = name;
        return this;
    }

    public String getName() {
        return name;
    }

    public HonourProdProduct setProfit(BigDecimal profit) {
        this.profit = profit;
        return this;
    }

    public BigDecimal getProfit() {
        return profit;
    }

    public HonourProdProduct setPeriod(String period) {
        this.period = period;
        return this;
    }

    public String getPeriod() {
        return period;
    }

    public HonourProdProduct setTradeNum(Long tradeNum) {
        this.tradeNum = tradeNum;
        return this;
    }

    public Long getTradeNum() {
        return tradeNum;
    }

    public HonourProdProduct setTradeRealAmount(BigDecimal tradeRealAmount) {
        this.tradeRealAmount = tradeRealAmount;
        return this;
    }

    public BigDecimal getTradeRealAmount() {
        return tradeRealAmount;
    }

    public HonourProdProduct setTradeAmount(BigDecimal tradeAmount) {
        this.tradeAmount = tradeAmount;
        return this;
    }

    public BigDecimal getTradeAmount() {
        return tradeAmount;
    }

    public HonourProdProduct setStatus(Integer status) {
        this.status = status;
        return this;
    }

    public Integer getStatus() {
        return status;
    }

    public HonourProdProduct setStartBenefit(Date startBenefit) {
        this.startBenefit = startBenefit;
        return this;
    }

    public Date getStartBenefit() {
        return startBenefit;
    }

    public HonourProdProduct setEndBenefit(Date endBenefit) {
        this.endBenefit = endBenefit;
        return this;
    }

    public Date getEndBenefit() {
        return endBenefit;
    }

    public HonourProdProduct setRemainAmount(BigDecimal remainAmount) {
        this.remainAmount = remainAmount;
        return this;
    }

    public BigDecimal getRemainAmount() {
        return remainAmount;
    }

    public HonourProdProduct setLeastAmount(BigDecimal leastAmount) {
        this.leastAmount = leastAmount;
        return this;
    }

    public BigDecimal getLeastAmount() {
        return leastAmount;
    }

    public HonourProdProduct setLimitAmount(BigDecimal limitAmount) {
        this.limitAmount = limitAmount;
        return this;
    }

    public BigDecimal getLimitAmount() {
        return limitAmount;
    }

    public HonourProdProduct setSteppingAmount(BigDecimal steppingAmount) {
        this.steppingAmount = steppingAmount;
        return this;
    }

    public BigDecimal getSteppingAmount() {
        return steppingAmount;
    }

    public HonourProdProduct setRaiseAmount(BigDecimal raiseAmount) {
        this.raiseAmount = raiseAmount;
        return this;
    }

    public BigDecimal getRaiseAmount() {
        return raiseAmount;
    }

    public HonourProdProduct setMerchantId(String merchantId) {
        this.merchantId = merchantId;
        return this;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public HonourProdProduct setRedeemType(Integer redeemType) {
        this.redeemType = redeemType;
        return this;
    }

    public Integer getRedeemType() {
        return redeemType;
    }

    public HonourProdProduct setRedeemTypeDesc(String redeemTypeDesc) {
        this.redeemTypeDesc = redeemTypeDesc;
        return this;
    }

    public String getRedeemTypeDesc() {
        return redeemTypeDesc;
    }

    public HonourProdProduct setSoldOutTime(Date soldOutTime) {
        this.soldOutTime = soldOutTime;
        return this;
    }

    public Date getSoldOutTime() {
        return soldOutTime;
    }

    public HonourProdProduct setPeriodDaysInt(Integer periodDaysInt) {
        this.periodDaysInt = periodDaysInt;
        return this;
    }

    public Integer getPeriodDaysInt() {
        return periodDaysInt;
    }

    public HonourProdProduct setNote(String note) {
        this.note = note;
        return this;
    }

    public String getNote() {
        return note;
    }

    public HonourProdProduct setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public HonourProdProduct setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
        return this;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public String getPriority() {
        return priority;
    }

    public HonourProdProduct setPriority(String priority) {
        this.priority = priority;
        return this;
    }
}

