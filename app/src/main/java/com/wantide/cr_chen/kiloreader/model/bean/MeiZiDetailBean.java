package com.wantide.cr_chen.kiloreader.model.bean;

/**
 * Created by CR_Chen on 2016/7/11.
 */
public class MeiZiDetailBean {
    private String category; //图片类型(长腿、黑丝)
    private String group_url; //网页地址
    private String image_url; //图片为大图
    private String objectId;  //
    private String thumb_url;  //图片为小图
    private String title;  //标题

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getGroup_url() {
        return group_url;
    }

    public void setGroup_url(String group_url) {
        this.group_url = group_url;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getThumb_url() {
        return thumb_url;
    }

    public void setThumb_url(String thumb_url) {
        this.thumb_url = thumb_url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
