package com.wantide.cr_chen.kiloreader.presenter;

import com.wantide.cr_chen.kiloreader.model.ZhiHuDetailApi;
import com.wantide.cr_chen.kiloreader.model.bean.ZhiHuDetailBean;
import com.wantide.cr_chen.kiloreader.model.impl.Listener;
import com.wantide.cr_chen.kiloreader.presenter.impl.BasePresenterApi;
import com.wantide.cr_chen.kiloreader.presenter.impl.ZhiHuDetailView;
import com.wantide.cr_chen.kiloreader.utils.StringUtils;
import com.wantide.cr_chen.kiloreader.utils.ToolLog;

import rx.Subscription;

/**
 * Created by CR_Chen on 2016/6/17.
 */
public class ZhiHuDetailPresenter extends BasePresenterApi {
    private ZhiHuDetailView detailView;
    private ZhiHuDetailApi detailApi;

    public ZhiHuDetailPresenter(ZhiHuDetailView detailView){
        this.detailView = detailView;
        detailApi = new ZhiHuDetailApi();
    }

    /**
     * 获取数据并处理逻辑
     */
    public void getDetailData(String pageId){
        Subscription detailSub = detailApi.getZhihuDetailNewData(new Listener<ZhiHuDetailBean>() {
            @Override
            public void onSuccess(ZhiHuDetailBean detailBean) {

                String webData = StringUtils.installHtml(detailBean.getBody(), detailBean.getCss());
                detailView.onDetailSuccessful(detailBean.getTitle(),detailBean.getImage(), webData);
            }

            @Override
            public void onFailure(Throwable e) {
                ToolLog.e("ZhiHuDetailPresenter --------- 请求失败" + e);
                detailView.onDetailFailure();
            }
        }, pageId);


        addSubscription(detailSub);
    }
}
