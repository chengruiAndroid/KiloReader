package com.wantide.cr_chen.kiloreader.model;

import com.wantide.cr_chen.kiloreader.api.meizi.MeiZiRequest;
import com.wantide.cr_chen.kiloreader.model.bean.MeiZiContentBean;
import com.wantide.cr_chen.kiloreader.model.impl.Listener;
import com.wantide.cr_chen.kiloreader.utils.ToolLog;

import java.util.concurrent.TimeUnit;

import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by CR_Chen on 2016/7/11.
 */
public class MeiZiNewsApi {
    private long mRequestTime;
    private long mDiffTime = 0;
    /**
     * 获取妹子json数据
     * @param listener
     * @param pageId
     * @return
     */
    public Subscription getMeiZiNewData(final Listener<MeiZiContentBean> listener, String pageId){
        mRequestTime = System.currentTimeMillis();

        return MeiZiRequest.getMeiZiApi().getMeiZiDetail(pageId)
                .subscribeOn(Schedulers.io())
                .filter(new Func1<MeiZiContentBean, Boolean>() {
                    @Override
                    public Boolean call(MeiZiContentBean meiZiContentBean) {
                        if (System.currentTimeMillis() - mRequestTime < 1500) {
                            mDiffTime = 1500 - (System.currentTimeMillis() - mRequestTime);
                        } else {
                            mDiffTime = 0;
                        }
                        return meiZiContentBean != null;
                    }
                })
                .delay(mDiffTime, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<MeiZiContentBean>() {
                    @Override
                    public void onCompleted() {
                        ToolLog.e("获取妹子json数据成功");
                    }

                    @Override
                    public void onError(Throwable e) {
                        listener.onFailure(e);
                    }

                    @Override
                    public void onNext(MeiZiContentBean meiZiContentBean) {
                        listener.onSuccess(meiZiContentBean);
                    }
                });
    }


    /**
     * 获取妹子json数据
     * @param listener
     * @param pageId
     * @return
     */
    public Subscription getMeiZiMoreData(final Listener<MeiZiContentBean> listener, String pageId){
        return MeiZiRequest.getMeiZiApi().getMeiZiDetail(pageId)
                .subscribeOn(Schedulers.io())
                .filter(new Func1<MeiZiContentBean, Boolean>() {
                    @Override
                    public Boolean call(MeiZiContentBean meiZiContentBean) {
                        return meiZiContentBean != null;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<MeiZiContentBean>() {
                    @Override
                    public void onCompleted() {
                        ToolLog.e("获取妹子json数据成功");
                    }

                    @Override
                    public void onError(Throwable e) {
                        listener.onFailure(e);
                    }

                    @Override
                    public void onNext(MeiZiContentBean meiZiContentBean) {
                        listener.onSuccess(meiZiContentBean);
                    }
                });
    }



}
