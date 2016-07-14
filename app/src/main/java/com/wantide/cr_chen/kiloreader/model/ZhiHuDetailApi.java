package com.wantide.cr_chen.kiloreader.model;

import com.wantide.cr_chen.kiloreader.api.zhihu.ZhihuRequest;
import com.wantide.cr_chen.kiloreader.model.bean.ZhiHuDetailBean;
import com.wantide.cr_chen.kiloreader.model.impl.Listener;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by CR_Chen on 2016/6/17.
 */
public class ZhiHuDetailApi {

    /**
     * 获取某一篇知乎页面的网络数据
     */
    public Subscription getZhihuDetailNewData(final Listener<ZhiHuDetailBean> listener, String pageId){

        return ZhihuRequest.getZhihuApi().getDetailNew(pageId)
                .subscribeOn(Schedulers.io())
                .filter(new Func1<ZhiHuDetailBean, Boolean>() {
                    @Override
                    public Boolean call(ZhiHuDetailBean zhiHuDetailBean) {
                        return zhiHuDetailBean != null;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ZhiHuDetailBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        listener.onFailure(e);
                    }

                    @Override
                    public void onNext(ZhiHuDetailBean zhiHuDetailBean) {
                        listener.onSuccess(zhiHuDetailBean);
                    }
                });
    }

}
