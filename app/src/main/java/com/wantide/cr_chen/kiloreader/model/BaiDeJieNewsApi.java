package com.wantide.cr_chen.kiloreader.model;

import com.wantide.cr_chen.kiloreader.api.baidejie.BaiDeJieRequest;
import com.wantide.cr_chen.kiloreader.model.bean.BaiDeJieShowBean;
import com.wantide.cr_chen.kiloreader.model.bean.ZhiHuDetailBean;
import com.wantide.cr_chen.kiloreader.model.impl.Listener;
import com.wantide.cr_chen.kiloreader.utils.Constants;
import com.wantide.cr_chen.kiloreader.utils.DateUtil;

import java.util.Date;

import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by CR_Chen on 2016/6/21.
 */
public class BaiDeJieNewsApi {
    private static final String BAIDEJIE_IMAGE_TYPE = "10";

    /**
     * 获取百得姐图片接口数据
     */
    public Subscription getBaiDeJieNewData(final Listener<BaiDeJieShowBean> listener, String pageId){
        String mCurTime = DateUtil.getBaiDeJieTime(new Date().getTime());

        return BaiDeJieRequest.getBaiDeJieApi().getBaiDeJieShow("20794", mCurTime, BAIDEJIE_IMAGE_TYPE, Constants.BAIDEJIE_SIGN, pageId)
                .subscribeOn(Schedulers.io())
                .filter(new Func1<BaiDeJieShowBean, Boolean>() {
                    @Override
                    public Boolean call(BaiDeJieShowBean baiDeJieShowBean) {
                        return baiDeJieShowBean != null;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaiDeJieShowBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        listener.onFailure(e);
                    }

                    @Override
                    public void onNext(BaiDeJieShowBean baiDeJieShowBean) {
                        listener.onSuccess(baiDeJieShowBean);
                    }
                });

    }





}
