package com.wantide.cr_chen.kiloreader.presenter.impl;

import com.wantide.cr_chen.kiloreader.model.bean.ZhiHuDetailBean;

/**
 * Created by CR_Chen on 2016/6/17.
 */
public interface ZhiHuDetailView {

    void onDetailSuccessful(String title, String imageUrl, String webData);
    void onDetailFailure();
}
