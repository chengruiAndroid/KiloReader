package com.wantide.cr_chen.kiloreader.model;

import com.wantide.cr_chen.kiloreader.api.zhihu.ZhihuRequest;
import com.wantide.cr_chen.kiloreader.model.bean.ZhiHuNewBean;
import com.wantide.cr_chen.kiloreader.model.impl.Listener;
import com.wantide.cr_chen.kiloreader.utils.ToolLog;

import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by CR_Chen on 2016/6/15.
 */
public class ZhiHuNewsApi {
    private long mRequestTime;
    private long mDiffTime = 0;

    /**
     * 获取到最新新闻的数据
     * @return
     */
    public Subscription getZhiHuNewsData(final Listener<ZhiHuNewBean> listener){
        mRequestTime = System.currentTimeMillis();

        return ZhihuRequest.getZhihuApi().getHotNews()
                .subscribeOn(Schedulers.io())
                .filter(new Func1<ZhiHuNewBean, Boolean>() {
                    @Override
                    public Boolean call(ZhiHuNewBean zhiHuNewBean) {
                        if (System.currentTimeMillis() - mRequestTime < 1500) {
                            mDiffTime = 1500 - (System.currentTimeMillis() - mRequestTime);
                        } else {
                            mDiffTime = 0;
                        }
                        return zhiHuNewBean != null;
                    }
                })
                .delay(mDiffTime, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ZhiHuNewBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        listener.onFailure(e);
                    }

                    @Override
                    public void onNext(ZhiHuNewBean zhiHuNewBean) {
                        listener.onSuccess(zhiHuNewBean);
                    }
                });
    }


    /**
     * 获取过往新闻数据,上拉加载不需要延时
     * @param listener 数据更新监听
     * @param beforeTime 获取某天数据的日期
     * @return
     */
    public Subscription getZhiHuBeforeNewsData(final Listener<ZhiHuNewBean> listener, String beforeTime){
        return ZhihuRequest.getZhihuApi().getBeforeNews(beforeTime)
                .subscribeOn(Schedulers.io())
                .filter(new Func1<ZhiHuNewBean, Boolean>() {
                    @Override
                    public Boolean call(ZhiHuNewBean zhiHuNewBean) {
                        return zhiHuNewBean != null;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ZhiHuNewBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        listener.onFailure(e);
                    }

                    @Override
                    public void onNext(ZhiHuNewBean zhiHuNewBean) {
                        listener.onSuccess(zhiHuNewBean);
                    }
                });
    }

}
