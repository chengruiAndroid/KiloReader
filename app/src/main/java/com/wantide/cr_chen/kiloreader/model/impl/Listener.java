package com.wantide.cr_chen.kiloreader.model.impl;

/**
 * Created by CR_Chen on 2016/5/20.
 */
public interface Listener<T> {

    void onSuccess(T t);
    void onFailure(Throwable e);

}
