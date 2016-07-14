package com.wantide.cr_chen.kiloreader.presenter.impl;

import com.wantide.cr_chen.kiloreader.model.bean.BaiDeJieContentBean;

import java.util.List;

/**
 * Created by CR_Chen on 2016/6/21.
 */
public interface BaiDeJieNewView {

    void onNewsSuccessful(List<BaiDeJieContentBean> contentBeans);
    void onNewsFailure();
    void onNewsMoreSuccessful(List<BaiDeJieContentBean> contentBeans);
    void onNewsMoreFailure();
}
