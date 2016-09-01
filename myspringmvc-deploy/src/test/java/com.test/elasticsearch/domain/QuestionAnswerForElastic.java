package com.test.elasticsearch.domain;

import org.springframework.format.annotation.DateTimeFormat;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author Zhang.Jinwei
 *
 * @date 2014-11-24
 */
public class QuestionAnswerForElastic {
    private Long id;//

    private Long catalogId;//文章类别

    private String question;//问题

    private String answer;//答案

    private Integer sort;//排序
    
    private String chartUrl;//图表地址

    private Integer isHot;//1：热门；0：非热门

    private Integer isPublish;//1.发布 0： 不发布

    private String modifyUser;//

    private String publishUser;//

    @DateTimeFormat( pattern = "yyyy-MM-dd HH:mm:ss" )
    private Date publishTime;//

    private Date createTime;//创建时间

    private Date modifyTime;//更新时间
    
    
    private List keyWordTagsList;//temp
    private String catalogName;//类别名字

    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Long getCatalogId() {
        return catalogId;
    }

    public void setCatalogId(Long catalogId) {
        this.catalogId = catalogId;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question == null ? null : question.trim();
    }

    public Integer getSort() {
        return sort;
    }
    
    public void setSort(Integer sort) {
        this.sort = sort;
    }
    
    public Integer getIsPublish() {
        return isPublish;
    }

    public void setIsPublish(Integer isPublish) {
        this.isPublish = isPublish;
    }
    
    public String getModifyUser() {
        return modifyUser;
    }

    public void setModifyUser(String modifyUser) {
        this.modifyUser = modifyUser == null ? null : modifyUser.trim();
    }
    
    public String getPublishUser() {
        return publishUser;
    }

    public void setPublishUser(String publishUser) {
        this.publishUser = publishUser == null ? null : publishUser.trim();
    }

    public String getPublishTime() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return simpleDateFormat.format(publishTime!=null?publishTime:new Date());
    }

    public void setPublishTime(Date publishTime) {
        this.publishTime = publishTime;
    }

    public String getCreateTime() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return simpleDateFormat.format(createTime!=null?createTime:new Date());
    }

    
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getModifyTime() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return simpleDateFormat.format(modifyTime!=null?modifyTime:new Date());
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }
    
    public String getAnswer() {
        return answer;
    }
    
    public void setAnswer(String answer) {
        this.answer = answer == null ? null : answer.trim();
    }

	public Integer getIsHot() {
		return isHot;
	}

	public void setIsHot(Integer isHot) {
		this.isHot = isHot;
	}

	public String getChartUrl() {
		return chartUrl;
	}

	public void setChartUrl(String chartUrl) {
		this.chartUrl = chartUrl;
	}

	public List getKeyWordTagsList() {
		return keyWordTagsList;
	}

	public void setKeyWordTagsList(List keyWordTagsList) {
		this.keyWordTagsList = keyWordTagsList;
	}

	public String getCatalogName() {
		return catalogName;
	}

	public void setCatalogName(String catalogName) {
		this.catalogName = catalogName;
	}
	
}