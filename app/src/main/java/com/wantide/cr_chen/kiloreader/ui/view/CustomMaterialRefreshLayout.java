package com.wantide.cr_chen.kiloreader.ui.view;

import android.content.Context;
import android.util.AttributeSet;

import com.cjj.MaterialRefreshLayout;

/**
 * Created by CR_Chen on 2016/6/16.
 */
public class CustomMaterialRefreshLayout extends MaterialRefreshLayout {

    public CustomMaterialRefreshLayout(Context context) {
        this(context, null);
    }

    public CustomMaterialRefreshLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomMaterialRefreshLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initConfigs();
    }

    /**
     * 下拉刷新的初始化配置
     */
    public void initConfigs(){
        setIsOverLay(false);
        setWaveShow(true);
    }


}
