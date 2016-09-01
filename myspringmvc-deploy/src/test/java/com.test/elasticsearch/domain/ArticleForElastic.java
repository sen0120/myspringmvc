package com.test.elasticsearch.domain;

import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Zhang.Jinwei
 *
 * @date 2014-11-24
 */
public class ArticleForElastic implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 3664619265340220021L;

	private Long id;

    private String title;//标题

    private String title2;//副标题

    private String source;//来源
    
    private String sourceUrl;//来源链接

    private Integer sort;//排序

    private Integer isPublish;//状态 1：发布， 0： 不发布

    private String modifyUser;//

    private String publishUser;//

    private Date createTime;//创建时间

    private Date modifyTime;//更新时间

    @DateTimeFormat( pattern = "yyyy-MM-dd HH:mm:ss" )
    private Date publishTime;//发布时间

    private String content;//内容
    
    
    private String imageUrl;//内容图片 默认第一张
    
    
    private Long clickCount;//点击数 缓存获取
    private String startDateStr;//查询起始时间
    private String endDateStr;//查询结束时间

    private Integer type;//类型 0媒体公告；1官方公告

    private Integer showOn;//显示位置：1在首页显示

    public Integer getShowOn() {
        return showOn;
    }

    public void setShowOn(Integer showOn) {
        this.showOn = showOn;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }
    
    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source == null ? null : source.trim();
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

    public String getPublishTime() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return simpleDateFormat.format(publishTime!=null?publishTime:new Date());
    }

    public void setPublishTime(Date publishTime) {
        this.publishTime = publishTime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

	public Long getClickCount() {
		return clickCount;
	}

	public void setClickCount(Long clickCount) {
		this.clickCount = clickCount;
	}

	public String getStartDateStr() {
		return startDateStr;
	}

	public void setStartDateStr(String startDateStr) {
		this.startDateStr = startDateStr;
	}

	public String getEndDateStr() {
		return endDateStr;
	}

	public void setEndDateStr(String endDateStr) {
		this.endDateStr = endDateStr;
	}

	public String getSourceUrl() {
		return sourceUrl;
	}

	public void setSourceUrl(String sourceUrl) {
		this.sourceUrl = sourceUrl;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getTitle2() {
		return title2;
	}

	public void setTitle2(String title2) {
		this.title2 = title2;
	}

}