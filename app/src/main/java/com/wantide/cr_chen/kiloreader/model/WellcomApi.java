package com.wantide.cr_chen.kiloreader.model;

import com.wantide.cr_chen.kiloreader.api.zhihu.ZhihuRequest;
import com.wantide.cr_chen.kiloreader.model.bean.WellcomBean;
import com.wantide.cr_chen.kiloreader.model.impl.Listener;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by CR_Chen on 2016/5/25.
 */
public class WellcomApi{

    /**
     * 获取欢迎页面的网络数据
     */
    public Subscription getWellcomData(final Listener<WellcomBean> listener){
        return ZhihuRequest.getZhihuApi().getStartImage("720*1184")
                .subscribeOn(Schedulers.io())
                .filter(new Func1<WellcomBean, Boolean>() {
                    @Override
                    public Boolean call(WellcomBean wellcomBean) {
                        return wellcomBean != null;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<WellcomBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        listener.onFailure(e);
                    }

                    @Override
                    public void onNext(WellcomBean wellcomBean) {
                        listener.onSuccess(wellcomBean);
                    }
                });
    }
}
