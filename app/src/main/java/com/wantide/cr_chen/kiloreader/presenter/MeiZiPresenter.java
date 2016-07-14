package com.wantide.cr_chen.kiloreader.presenter;

import com.wantide.cr_chen.kiloreader.model.MeiZiNewsApi;
import com.wantide.cr_chen.kiloreader.model.bean.MeiZiContentBean;
import com.wantide.cr_chen.kiloreader.model.bean.MeiZiDetailBean;
import com.wantide.cr_chen.kiloreader.model.impl.Listener;
import com.wantide.cr_chen.kiloreader.presenter.impl.BasePresenterApi;
import com.wantide.cr_chen.kiloreader.presenter.impl.MeiZiNewView;
import com.wantide.cr_chen.kiloreader.utils.ToolLog;

import java.util.List;

import rx.Subscription;

/**
 * Created by CR_Chen on 2016/7/11.
 */
public class MeiZiPresenter extends BasePresenterApi {
    private MeiZiNewView newView;
    private MeiZiNewsApi newsApi;
    private int pageIdNum = 1;
    private boolean isLoad = false;

    public MeiZiPresenter(MeiZiNewView newView) {
        this.newView = newView;
        newsApi = new MeiZiNewsApi();
    }

    /**
     * 处理需要的数据并返回结果
     */
    public void getMeiZiDetailDatas(){
        pageIdNum = 1;
        Subscription meiZiSub = newsApi.getMeiZiNewData(new Listener<MeiZiContentBean>() {
            @Override
            public void onSuccess(MeiZiContentBean meiZiContentBean) {
                List<MeiZiDetailBean> meiZiDetails = meiZiContentBean.getResults();
                newView.onSuccessFulNews(meiZiDetails);
            }

            @Override
            public void onFailure(Throwable e) {
                ToolLog.e("------MeiZiPresenter------获取数据失败........" + e);
                newView.onFailureNews();
            }
        }, pageIdNum + "");
        addSubscription(meiZiSub);
    }


    public void getMeiZiMoreDatas(){
        if (!isLoad){
            isLoad = true;
            pageIdNum ++;
            Subscription meiZiMoreSub = newsApi.getMeiZiMoreData(new Listener<MeiZiContentBean>() {
                @Override
                public void onSuccess(MeiZiContentBean meiZiContentBean) {
                    isLoad = false;
                    List<MeiZiDetailBean> meiZiDetails = meiZiContentBean.getResults();
                    newView.onSuccessFulMore(meiZiDetails);
                }

                @Override
                public void onFailure(Throwable e) {
                    isLoad = false;
                    pageIdNum --;
                    ToolLog.e("------MeiZiPresenter------获取数据失败........" + e);
                    newView.onFailureMore();
                }
            }, pageIdNum + "");
            addSubscription(meiZiMoreSub);
        }

    }

}
