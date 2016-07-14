package com.wantide.cr_chen.kiloreader.presenter;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.ViewPropertyAnimation;
import com.wantide.cr_chen.kiloreader.model.WellcomApi;
import com.wantide.cr_chen.kiloreader.model.bean.WellcomBean;
import com.wantide.cr_chen.kiloreader.presenter.impl.BasePresenterApi;
import com.wantide.cr_chen.kiloreader.model.impl.Listener;
import com.wantide.cr_chen.kiloreader.presenter.impl.WellcomView;
import com.wantide.cr_chen.kiloreader.utils.Constants;
import com.wantide.cr_chen.kiloreader.utils.SPUtils;
import com.wantide.cr_chen.kiloreader.utils.ToolLog;

import rx.Subscription;

/**
 * Created by CR_Chen on 2016/5/25.
 */
public class WellcomPresenter extends BasePresenterApi{

    private WellcomApi wellcomApi;
    private WellcomView mView;
    private ImageView imageView;
    private Context context;

    public WellcomPresenter(WellcomView view, ImageView imageView, Context context){
        this.mView = view;
        this.imageView = imageView;
        this.context = context;
        wellcomApi = new WellcomApi();

    }

    public void requestWellcom(){
        Subscription wellcom = wellcomApi.getWellcomData(new Listener<WellcomBean>() {
            @Override
            public void onSuccess(WellcomBean wellcomBean) {
                String imageUrl = wellcomBean.getImg();
                String VersionStr = wellcomBean.getText();
                loadImage(imageUrl);//加载图片
                mView.onSuccessVersionStr(VersionStr);

                ToolLog.e("WellcomPresenter --------- 请求成功");
            }

            @Override
            public void onFailure(Throwable e) {
                ToolLog.e("WellcomPresenter --------- 请求失败" + e);
                String image_url = (String)SPUtils.get(context, Constants.WELLCOM_IMAGE_URL, "");
                if (!"".equals(image_url)){
                    loadImage(image_url);
                }
            }
        });

        addSubscription(wellcom);
    }

    /**
     * 加载欢迎图片到imageview中
     * @param imageUrl 图片网络地址
     */
    private void loadImage(String imageUrl){
        SPUtils.put(context, Constants.WELLCOM_IMAGE_URL, imageUrl);

        ViewPropertyAnimation.Animator animationObject = new ViewPropertyAnimation.Animator() {
            @Override
            public void animate(View view) {
                ObjectAnimator scaleXAnim = ObjectAnimator.ofFloat(view, "scaleX", 1.0f, 1.1f).setDuration(3000);
                ObjectAnimator scaleYAnim = ObjectAnimator.ofFloat(view, "scaleY", 1.0f, 1.1f).setDuration(3000);
                ObjectAnimator fadeAnim = ObjectAnimator.ofFloat(view, "alpha", 1f, 0f).setDuration(1500);

                AnimatorSet animSet = new AnimatorSet();
                animSet.play(scaleXAnim).with(scaleYAnim);
                animSet.play(fadeAnim).after(scaleYAnim);
                animSet.start();

                animSet.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        mView.onRquestFinish();
                    }
                });

            }
        };

        Glide.with(context).load(imageUrl).centerCrop().animate(animationObject).into(imageView);

    }





}
