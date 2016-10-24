package com.biz.vo;


import com.biz.valid.ValidSupportInterface;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 含有validatator注解的bean,通过注解标记可在Controller层验证参数,
 * 例子结合TestValidateController.webPost()
 * <br> org.hibernate.validator.constraints.NotEmpty 针对string, collection, map or array is not {@code null} or empty.
 * <br> javax.validation.constraints.NotNull 针对Object,不能为空,但是可能是空集合,所以能用NotEmpty的优先用NotEmpty.
 * <br> javax.validation.constraints.Range 针对Number或者String.
 */
public class ValidationBeanVO implements Serializable {
    private static final long serialVersionUID = -1L;

    /**
     * 版块id
     */
    @NotNull(groups = {ValidSupportInterface.ParameterClass1.class, ValidSupportInterface.ParameterClass2.class},
            message = "版块{org.hibernate.validator.constraints.NotBlank.message}")
    private Long moduleId;
    /**
     * 标题(最多10个字控制)
     */
    @NotEmpty(groups = {ValidSupportInterface.ParameterClass1.class, ValidSupportInterface.ParameterClass2.class},
            message = "标题{org.hibernate.validator.constraints.NotBlank.message}")
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
    @NotEmpty(groups = {ValidSupportInterface.ParameterClass1.class, ValidSupportInterface.ParameterClass2.class},
            message = "内容{org.hibernate.validator.constraints.NotBlank.message}")
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
    @NotNull(groups = {ValidSupportInterface.ParameterClass1.class},
            message = "发帖状态{org.hibernate.validator.constraints.NotBlank.message}")
    @Range(groups = {ValidSupportInterface.ParameterClass1.class},
            min = 0, max = 1, message = "发帖状态:0草稿,1发布")
    private Integer status;
    /**
     * 发布时间
     */
    private Date publishTime;
    /**
     * 置顶帖优先级-非置顶帖为null
     */
    private Integer priority;
    /**
     * 帖子回复数
     */
    private Long replyCount;
    /**
     * 最后回复时间
     */
    private Date lastReplyTime;
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
     * 最后修改人
     */
    private String modifyUser;

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
     * replyCount getter & setter
     */
    public Long getReplyCount() {
        return replyCount;
    }

    public void setReplyCount(Long replyCount) {
        this.replyCount = replyCount;
    }

    /**
     * lastReplyTime getter & setter
     */
    public Date getLastReplyTime() {
        return lastReplyTime;
    }

    public void setLastReplyTime(Date lastReplyTime) {
        this.lastReplyTime = lastReplyTime;
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

}