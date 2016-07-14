package com.wantide.cr_chen.kiloreader.model.bean;

import android.support.v4.app.Fragment;

/**
 * Created by CR_Chen on 2016/6/15.
 */
public class FragmentBean {
    private Fragment fragment;
    private String title;

    public FragmentBean(Fragment fragment, String title) {
        this.fragment = fragment;
        this.title = title;
    }

    public Fragment getFragment() {
        return fragment;
    }

    public void setFragment(Fragment fragment) {
        this.fragment = fragment;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


}
