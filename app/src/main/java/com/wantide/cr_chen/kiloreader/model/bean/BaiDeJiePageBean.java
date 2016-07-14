package com.wantide.cr_chen.kiloreader.model.bean;

import java.util.List;

/**
 * Created by CR_Chen on 2016/6/20.
 */
public class BaiDeJiePageBean {

    private int allPages;
    private List<BaiDeJieContentBean> contentlist;
    private int currentPage;
    private int allNum;
    private int maxResult;

    public int getAllPages() {
        return allPages;
    }

    public void setAllPages(int allPages) {
        this.allPages = allPages;
    }

    public List<BaiDeJieContentBean> getContentlist() {
        return contentlist;
    }

    public void setContentlist(List<BaiDeJieContentBean> contentlist) {
        this.contentlist = contentlist;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getAllNum() {
        return allNum;
    }

    public void setAllNum(int allNum) {
        this.allNum = allNum;
    }

    public int getMaxResult() {
        return maxResult;
    }

    public void setMaxResult(int maxResult) {
        this.maxResult = maxResult;
    }
}
