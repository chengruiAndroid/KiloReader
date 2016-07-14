package com.wantide.cr_chen.kiloreader.presenter;

import android.content.Context;

import com.wantide.cr_chen.kiloreader.model.ZhiHuNewsApi;
import com.wantide.cr_chen.kiloreader.model.bean.ZhiHuNewBean;
import com.wantide.cr_chen.kiloreader.model.bean.ZhiHuStoryBean;
import com.wantide.cr_chen.kiloreader.model.impl.Listener;
import com.wantide.cr_chen.kiloreader.presenter.impl.BasePresenterApi;
import com.wantide.cr_chen.kiloreader.presenter.impl.ZhiHuNewView;
import com.wantide.cr_chen.kiloreader.utils.Constants;
import com.wantide.cr_chen.kiloreader.utils.DateUtil;
import com.wantide.cr_chen.kiloreader.utils.SPUtils;
import com.wantide.cr_chen.kiloreader.utils.ToolLog;
import com.wantide.cr_chen.kiloreader.utils.TransitionTime;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import rx.Subscription;

/**
 * Created by CR_Chen on 2016/6/15.
 */
public class ZhiHuNewsPresenter extends BasePresenterApi {

    private ZhiHuNewsApi newsApi;
    private ZhiHuNewView newView;
    private List<ZhiHuStoryBean> mStorys;
    private int mPreDay = 1;
    private boolean isRefreshing = false;

    public ZhiHuNewsPresenter(ZhiHuNewView newView){
        this.newView = newView;
        newsApi = new ZhiHuNewsApi();
        mStorys = new ArrayList<>();
    }

    /**
     * 获取到知乎最新数据进行处理
     */
    public void requestNews(){

        Subscription newsSub = newsApi.getZhiHuNewsData(new Listener<ZhiHuNewBean>() {
            @Override
            public void onSuccess(ZhiHuNewBean zhiHuNewBean) {
                mPreDay = 1;//重置上拉加载时间
                mStorys.clear();//先清空队列

                mStorys.add(new ZhiHuStoryBean("今日热闻", -1));
                mStorys.addAll(zhiHuNewBean.getStories());
                newView.onSuccess(mStorys);
            }

            @Override
            public void onFailure(Throwable e) {
                ToolLog.e("ZhiHuNewsPresenter --------- 请求失败" + e);
                newView.onFailure();
            }
        });

        addSubscription(newsSub);
    }

    /**
     * 获取到知乎以前数据进行处理
     */
    public void requestBeforeNews(){
        if (!isRefreshing) {
            isRefreshing = true;
            final String beforeTime = DateUtil.getPreDataStr(mPreDay);

            Subscription beforeNewSub = newsApi.getZhiHuBeforeNewsData(new Listener<ZhiHuNewBean>() {
                @Override
                public void onSuccess(ZhiHuNewBean zhiHuNewBean) {
                    isRefreshing = false;
                    mPreDay++;
                    String mMonthWeek = TransitionTime.DateToMonthWeek(beforeTime);
                    mStorys.add(new ZhiHuStoryBean(mMonthWeek, -1));
                    mStorys.addAll(zhiHuNewBean.getStories());
                    newView.onBeforeSuccess(mStorys);
                }

                @Override
                public void onFailure(Throwable e) {
                    isRefreshing = false;
                    ToolLog.e("ZhiHuNewsPresenter ---------" + beforeTime + " 请求失败" + e);
                    newView.onBeforeFailure();
                }
            }, beforeTime);

            addSubscription(beforeNewSub);
        }
    }







}
