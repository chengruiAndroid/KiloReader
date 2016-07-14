package com.wantide.cr_chen.kiloreader.presenter.impl;

import com.wantide.cr_chen.kiloreader.model.bean.MeiZiDetailBean;

import java.util.List;

/**
 * Created by CR_Chen on 2016/7/11.
 */
public interface MeiZiNewView {

    void onSuccessFulNews(List<MeiZiDetailBean> detailBeans);
    void onFailureNews();
    void onSuccessFulMore(List<MeiZiDetailBean> detailBeans);
    void onFailureMore();

}
