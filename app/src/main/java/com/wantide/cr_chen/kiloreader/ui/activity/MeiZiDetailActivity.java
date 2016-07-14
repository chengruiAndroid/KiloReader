package com.wantide.cr_chen.kiloreader.ui.activity;

import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.webkit.WebView;

import com.wantide.cr_chen.kiloreader.R;
import com.wantide.cr_chen.kiloreader.utils.Constants;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by CR_Chen on 2016/7/12.
 */
public class MeiZiDetailActivity extends BaseActivity {

    @Bind(R.id.meizi_detail_toolbar)
    Toolbar meiziDetailToolbar;
    @Bind(R.id.meizi_web)
    WebView meiziWeb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meizi_detail);
        ButterKnife.bind(this);

        initConfigs();
    }

    /**
     * 初始化标题,webview等
     */
    private void initConfigs() {
        String mTitle = getIntent().getStringExtra(Constants.MEIZI_INTENT_TITLE);
        meiziDetailToolbar.setTitle(mTitle);
        //设置标题
        setSupportActionBar(meiziDetailToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //配置webview
        meiziWeb.getSettings().setJavaScriptEnabled(true);
        meiziWeb.getSettings().setDefaultTextEncodingName("UTF-8");
        String webUrl = getIntent().getStringExtra(Constants.MEIZI_INTENT_SIGN);
        meiziWeb.loadUrl(webUrl);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (meiziWeb != null) {
            meiziWeb.resumeTimers();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (meiziWeb != null) {
            meiziWeb.pauseTimers();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
        if (meiziWeb != null) {
            meiziWeb.destroy();
        }
    }
}
