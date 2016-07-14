package com.wantide.cr_chen.kiloreader.model.bean;

import java.util.List;

/**
 * Created by CR_Chen on 2016/6/15.
 */
public class ZhiHuStoryBean {
    private String title;//新闻标题
    private List<String> images;//图像地址（官方 API 使用数组形式。目前暂未有使用多张图片的情形出现，曾见无 images 属性的情况，请在使用中注意 ）
    private String ga_prefix;//供 Google Analytics 使用
    private long id; //url 与 share_url 中最后的数字（应为内容的 id）
    private int type;

    public ZhiHuStoryBean(String title, int type) {
        this.title = title;
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public String getGa_prefix() {
        return ga_prefix;
    }

    public void setGa_prefix(String ga_prefix) {
        this.ga_prefix = ga_prefix;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
