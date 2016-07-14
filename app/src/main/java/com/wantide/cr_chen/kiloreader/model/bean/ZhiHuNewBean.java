package com.wantide.cr_chen.kiloreader.model.bean;

import java.util.List;

/**
 * Created by CR_Chen on 2016/6/15.
 */
public class ZhiHuNewBean {
    private String date;//日期
    private List<ZhiHuStoryBean> stories;//当日新闻

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<ZhiHuStoryBean> getStories() {
        return stories;
    }

    public void setStories(List<ZhiHuStoryBean> stories) {
        this.stories = stories;
    }

}
