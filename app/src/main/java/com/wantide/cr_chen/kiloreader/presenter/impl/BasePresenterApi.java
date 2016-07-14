package com.wantide.cr_chen.kiloreader.presenter.impl;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by CR_Chen on 2016/5/25.
 */
public class BasePresenterApi implements BasePresenter {

    private CompositeSubscription mCompositeSubscription;

    protected void addSubscription(Subscription s) {
        if (this.mCompositeSubscription == null) {
            this.mCompositeSubscription = new CompositeSubscription();
        }
        this.mCompositeSubscription.add(s);
    }

    @Override
    public void unsubcrible() {
        if (this.mCompositeSubscription != null) {
            this.mCompositeSubscription.unsubscribe();
        }
    }
}
