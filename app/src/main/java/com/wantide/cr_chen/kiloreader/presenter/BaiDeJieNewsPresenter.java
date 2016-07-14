package com.wantide.cr_chen.kiloreader.presenter;

import com.wantide.cr_chen.kiloreader.model.BaiDeJieNewsApi;
import com.wantide.cr_chen.kiloreader.model.bean.BaiDeJieContentBean;
import com.wantide.cr_chen.kiloreader.model.bean.BaiDeJieShowBean;
import com.wantide.cr_chen.kiloreader.model.impl.Listener;
import com.wantide.cr_chen.kiloreader.presenter.impl.BaiDeJieNewView;
import com.wantide.cr_chen.kiloreader.presenter.impl.BasePresenterApi;
import com.wantide.cr_chen.kiloreader.utils.ToolLog;

import java.util.List;

import rx.Subscription;

/**
 * Created by CR_Chen on 2016/6/21.
 */
public class BaiDeJieNewsPresenter extends BasePresenterApi {
    private BaiDeJieNewView newView;
    private BaiDeJieNewsApi newsApi;
    private int mPageId = 2;

    public BaiDeJieNewsPresenter(BaiDeJieNewView newView){
        this.newView = newView;
        newsApi = new BaiDeJieNewsApi();
    }

    /**
     * 请求接口数据并进行处理
     */
    public void requestNews(String pageId){

        Subscription deJieSub = newsApi.getBaiDeJieNewData(new Listener<BaiDeJieShowBean>() {
            @Override
            public void onSuccess(BaiDeJieShowBean baiDeJieShowBean) {
                if (baiDeJieShowBean.getShowapi_res_code() == 0){ //表示请求数据成功
                    List<BaiDeJieContentBean> jieContents = baiDeJieShowBean.getShowapi_res_body().getPagebean().getContentlist();
                    if (jieContents != null){
                        mPageId = 2;
                        newView.onNewsSuccessful(jieContents);
                    }
                } else {//表示请求方式有误
                    ToolLog.e("BaiDeJieNewsPresenter ---------  请求失败:" + baiDeJieShowBean.getShowapi_res_error());
                    newView.onNewsFailure();
                }
            }

            @Override
            public void onFailure(Throwable e) {
                ToolLog.e("BaiDeJieNewsPresenter ---------  请求失败:" + e);
                newView.onNewsFailure();

            }
        }, pageId);

        addSubscription(deJieSub);
    }

    /**
     * 上拉加载更多数据并进行处理
     */
    public void requestMoreNews(){

        Subscription deJieSub = newsApi.getBaiDeJieNewData(new Listener<BaiDeJieShowBean>() {
            @Override
            public void onSuccess(BaiDeJieShowBean baiDeJieShowBean) {
                if (baiDeJieShowBean.getShowapi_res_code() == 0){ //表示请求数据成功
                    List<BaiDeJieContentBean> jieContents = baiDeJieShowBean.getShowapi_res_body().getPagebean().getContentlist();
                    if (jieContents != null){
                        mPageId = mPageId + 1;
                        newView.onNewsMoreSuccessful(jieContents);
                    }
                } else {//表示请求方式有误
                    ToolLog.e("BaiDeJieNewsPresenter   requestMoreNews---------  请求失败:" + baiDeJieShowBean.getShowapi_res_error());
                    newView.onNewsMoreFailure();
                }
            }

            @Override
            public void onFailure(Throwable e) {
                ToolLog.e("BaiDeJieNewsPresenter    requestMoreNews---------  请求失败:" + e);
                newView.onNewsMoreFailure();

            }
        },  mPageId + "");

        addSubscription(deJieSub);
    }





}
