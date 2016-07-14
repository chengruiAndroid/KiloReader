package com.wantide.cr_chen.kiloreader.presenter.impl;

import com.wantide.cr_chen.kiloreader.model.bean.ZhiHuStoryBean;

import java.util.List;

/**
 * Created by CR_Chen on 2016/6/15.
 */
public interface ZhiHuNewView {
    void onSuccess(List<ZhiHuStoryBean> storys);
    void onFailure();
    void onBeforeSuccess(List<ZhiHuStoryBean> storys);//上拉加载更多成功
    void onBeforeFailure();//上拉加载更多失败
}
