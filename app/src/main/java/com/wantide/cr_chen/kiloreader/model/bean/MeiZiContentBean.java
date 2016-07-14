package com.wantide.cr_chen.kiloreader.model.bean;

import java.util.List;

/**
 * Created by CR_Chen on 2016/7/11.
 */
public class MeiZiContentBean {
    private String category;
    private int page;
    private List<MeiZiDetailBean> results;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public List<MeiZiDetailBean> getResults() {
        return results;
    }

    public void setResults(List<MeiZiDetailBean> results) {
        this.results = results;
    }
}
