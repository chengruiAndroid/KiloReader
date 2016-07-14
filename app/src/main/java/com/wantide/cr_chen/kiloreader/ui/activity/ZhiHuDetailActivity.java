package com.wantide.cr_chen.kiloreader.ui.activity;

import android.app.Activity;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.Toolbar;
import android.transition.Explode;
import android.view.MenuItem;
import android.view.Window;
import android.webkit.WebView;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.wantide.cr_chen.kiloreader.R;
import com.wantide.cr_chen.kiloreader.presenter.ZhiHuDetailPresenter;
import com.wantide.cr_chen.kiloreader.presenter.impl.ZhiHuDetailView;
import com.wantide.cr_chen.kiloreader.utils.Constants;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by CR_Chen on 2016/6/17.
 * zhi
 */
public class ZhiHuDetailActivity extends BaseActivity implements ZhiHuDetailView{

    @Bind(R.id.zhihu_detail_image)
    ImageView zhihuDetailImage;
    @Bind(R.id.zhihu_detail_toolbar)
    Toolbar zhihuDetailToolbar;
    @Bind(R.id.zhihu_detail_collapsing_toollayout)
    CollapsingToolbarLayout zhihuDetailCollapsingToollayout;
    @Bind(R.id.zhihu_detail_web)
    WebView zhihuDetailWeb;

    private ZhiHuDetailPresenter detailPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zhihu_detail);
        ButterKnife.bind(this);

        detailPresenter = new ZhiHuDetailPresenter(this);
        initConfigs();


    }

    /**
     * 初始化标题,webview等
     */
    private void initConfigs(){
        //设置标题
        setSupportActionBar(zhihuDetailToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //配置webview
        zhihuDetailWeb.getSettings().setJavaScriptEnabled(true);
        zhihuDetailWeb.getSettings().setDefaultTextEncodingName("UTF-8");

        String pageId = getIntent().getStringExtra(Constants.ZHIHU_PAGE_ID);
        if (pageId != null)
            detailPresenter.getDetailData(pageId);

        String image_path = getIntent().getStringExtra(Constants.ZHIHU_IMAGE_PATH);
        if (image_path != null)
            Glide.with(ZhiHuDetailActivity.this).load(image_path).centerCrop().into(zhihuDetailImage);

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDetailSuccessful(String title, String imageUrl, String webData) {
        zhihuDetailCollapsingToollayout.setTitle(title);

        zhihuDetailWeb.loadData(webData, "text/html; charset=UTF-8", null);
    }

    @Override
    public void onDetailFailure() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        detailPresenter.unsubcrible();

        if (zhihuDetailWeb != null){
            zhihuDetailWeb.destroy();
            zhihuDetailWeb = null;
        }
    }
}
