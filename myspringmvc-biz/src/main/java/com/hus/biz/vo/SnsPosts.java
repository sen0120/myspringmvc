package com.hus.biz.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class SnsPosts implements Serializable {

    private static final long serialVersionUID = 2901238417489196935L;
    private Long id;
    /**
     * 版块id
     */
    private Long moduleId;
    /**
     * 标题(最多10个字控制)
     */
    private String title;
    /**
     * 缩略图片
     */
    private String imgUrl;
    /**
     * 内容简介
     */
    private String contentSubject;
    /**
     * 内容(最少30字限制-最多1000字限制)
     */
    private String content;
    /**
     * 帖子类型:0用户帖-1官方贴
     */
    private Integer postType;
    /**
     * 发帖人id
     */
    private String postUser;
    /**
     * 状态:-2自己删除--1官方删除-0草稿-1发布-
     */
    private Integer status;
    /**
     * 多个 status
     */
    private List<Integer> statusList;
    /**
     * 发布时间
     */
    private Date publishTime;
    /**
     * 置顶帖优先级-非置顶帖为null
     */
    private Integer priority;

    /**
     * replyCount数是否+1
     */
    private boolean incrementReplyCount = false;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 创建人
     */
    private String createUser;
    /**
     * 最后修改时间
     */
    private Date modifyTime;

    /**
     * 自定义生成,作为 网易易盾 需要传递的的唯一标示
     */
    private String antispamTakeId;

    /**
     * 反垃圾 状态
     */
    private Integer antispamStatus;
    /**
     * 发送到网易 检查的次数
     */
    private Integer antispamCheckTime;

    /**
     * 最后修改人
     */
    private String modifyUser;
    private boolean orderByCreateTimeDesc;
    private boolean orderByCreateTimeAsc;
    private boolean orderByModifyTimeDesc;
    private boolean orderByPublishTimeDesc;
    private Integer offset = 0;
    private Integer rows = 10;

    public boolean isOrderByCreateTimeDesc() {
        return orderByCreateTimeDesc;
    }

    public void setOrderByCreateTimeDesc(boolean orderByCreateTimeDesc) {
        this.orderByCreateTimeDesc = orderByCreateTimeDesc;
    }

    public boolean isOrderByModifyTimeDesc() {
        return orderByModifyTimeDesc;
    }

    public void setOrderByModifyTimeDesc(boolean orderByModifyTimeDesc) {
        this.orderByModifyTimeDesc = orderByModifyTimeDesc;
    }

    public boolean isOrderByCreateTimeAsc() {
        return orderByCreateTimeAsc;
    }

    public void setOrderByCreateTimeAsc(boolean orderByCreateTimeAsc) {
        this.orderByCreateTimeAsc = orderByCreateTimeAsc;
    }

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public Integer getRows() {
        return rows;
    }

    public void setRows(Integer rows) {
        this.rows = rows;
    }

    /**
     * moduleId getter & setter
     */
    public Long getModuleId() {
        return moduleId;
    }

    public void setModuleId(Long moduleId) {
        this.moduleId = moduleId;
    }

    /**
     * title getter & setter
     */
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * imgUrl getter & setter
     */
    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    /**
     * contentSubject getter & setter
     */
    public String getContentSubject() {
        return contentSubject;
    }

    public void setContentSubject(String contentSubject) {
        this.contentSubject = contentSubject;
    }

    /**
     * content getter & setter
     */
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    /**
     * postType getter & setter
     */
    public Integer getPostType() {
        return postType;
    }

    public void setPostType(Integer postType) {
        this.postType = postType;
    }

    /**
     * postUser getter & setter
     */
    public String getPostUser() {
        return postUser;
    }

    public void setPostUser(String postUser) {
        this.postUser = postUser;
    }

    /**
     * status getter & setter
     */
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * publishTime getter & setter
     */
    public Date getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(Date publishTime) {
        this.publishTime = publishTime;
    }

    /**
     * priority getter & setter
     */
    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }


    /**
     * createTime getter & setter
     */
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * createUser getter & setter
     */
    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    /**
     * modifyTime getter & setter
     */
    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    /**
     * modifyUser getter & setter
     */
    public String getModifyUser() {
        return modifyUser;
    }

    public void setModifyUser(String modifyUser) {
        this.modifyUser = modifyUser;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isIncrementReplyCount() {
        return incrementReplyCount;
    }

    public void setIncrementReplyCount(boolean incrementReplyCount) {
        this.incrementReplyCount = incrementReplyCount;
    }

    public List<Integer> getStatusList() {
        return statusList;
    }

    public void setStatusList(List<Integer> statusList) {
        this.statusList = statusList;
    }

    public boolean isOrderByPublishTimeDesc() {
        return orderByPublishTimeDesc;
    }

    public void setOrderByPublishTimeDesc(boolean orderByPublishTimeDesc) {
        this.orderByPublishTimeDesc = orderByPublishTimeDesc;
    }

    public String getAntispamTakeId() {
        return antispamTakeId;
    }

    public void setAntispamTakeId(String antispamTakeId) {
        this.antispamTakeId = antispamTakeId;
    }

    public Integer getAntispamStatus() {
        return antispamStatus;
    }

    public void setAntispamStatus(Integer antispamStatus) {
        this.antispamStatus = antispamStatus;
    }

    public Integer getAntispamCheckTime() {
        return antispamCheckTime;
    }

    public void setAntispamCheckTime(Integer antispamCheckTime) {
        this.antispamCheckTime = antispamCheckTime;
    }
}