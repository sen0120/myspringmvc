package com.test.elasticsearch.domain;

public class QueryResultVO {
    /**
     * ID
     */
    private Long Id;
    /**
     * 搜索相关度得分
     */
    private float score;
    /**
     * 排序
     */
    private int sort;
    /**
     * 文章标题
     */
    private String title;
    /**
     * 是否发布
     */
    private String isPublish;
    /**
     * 副标题
     */
    private String title1;
    /**
     * 文章内容
     */
    private String content;
    /**
     * 结果类别//0:常见问题;1:文章;
     *
     * @return
     */
    private int type;
    /**
     * 图片路径
     */
    private String imgPath;

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTitle1() {
        return title1;
    }

    public void setTitle1(String title1) {
        this.title1 = title1;
    }

    public String getIsPublish() {
        return isPublish;
    }

    public void setIsPublish(String isPublish) {
        this.isPublish = isPublish;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }
}
