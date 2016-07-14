package com.wantide.cr_chen.kiloreader.ui.fragment;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.util.Pair;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.ChangeImageTransform;
import android.transition.Fade;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.wantide.cr_chen.kiloreader.R;
import com.wantide.cr_chen.kiloreader.model.bean.ZhiHuStoryBean;
import com.wantide.cr_chen.kiloreader.presenter.ZhiHuNewsPresenter;
import com.wantide.cr_chen.kiloreader.presenter.impl.ZhiHuNewView;
import com.wantide.cr_chen.kiloreader.ui.activity.ZhiHuDetailActivity;
import com.wantide.cr_chen.kiloreader.ui.adapter.ZhiHuNewsAdapter;
import com.wantide.cr_chen.kiloreader.ui.view.CustomMaterialRefreshLayout;
import com.wantide.cr_chen.kiloreader.utils.Constants;
import com.wantide.cr_chen.kiloreader.utils.ToolLog;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by CR_Chen on 2016/6/15.
 */
public class ZhiHuNewsFragment extends Fragment implements ZhiHuNewView
        , ZhiHuNewsAdapter.ItemClickListener, ZhiHuNewsAdapter.PullRefreshListener {

    @Bind(R.id.zhihu_news_recycler)
    RecyclerView zhihuNewsRecycler;
    @Bind(R.id.zhihu_refresh)
    CustomMaterialRefreshLayout zhihuRefresh;

    private ZhiHuNewsPresenter newsPresenter;
    private List<ZhiHuStoryBean> mStorys = new ArrayList<>();
    private ZhiHuNewsAdapter mNewsAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_zhihu_news, container, false);
        ButterKnife.bind(this, view);

        initRefresh();
        initRecycleView();

        newsPresenter = new ZhiHuNewsPresenter(this);
        zhihuRefresh.autoRefresh();//自动刷新

        return view;
    }

    /**
     * 初始化下拉刷新
     */
    private void initRefresh() {
        zhihuRefresh.setMaterialRefreshListener(new MaterialRefreshListener() {
            @Override
            public void onRefresh(MaterialRefreshLayout materialRefreshLayout) {
                if (newsPresenter != null)
                    newsPresenter.requestNews();
            }
        });
    }

    /**
     * 上拉加载更多监听
     */
    @Override
    public void PullRefresh() {
        if (newsPresenter != null)
            newsPresenter.requestBeforeNews();
    }


    /**
     * 初始化recycleview
     */
    private void initRecycleView() {
        zhihuNewsRecycler.setLayoutManager(new LinearLayoutManager(zhihuNewsRecycler.getContext()));
        mNewsAdapter = new ZhiHuNewsAdapter(mStorys);
        zhihuNewsRecycler.setAdapter(mNewsAdapter);
        mNewsAdapter.setItemClickListener(this);
        mNewsAdapter.setPullRefreshListener(this);
    }

    /**
     * 请求网络数据成功
     *
     * @param storys
     */
    @Override
    public void onSuccess(List<ZhiHuStoryBean> storys) {
        zhihuRefresh.finishRefresh();
        this.mStorys.clear();
        this.mStorys.addAll(storys);
        mNewsAdapter.notifyDataSetChanged();
    }

    @Override
    public void onBeforeSuccess(List<ZhiHuStoryBean> storys) {
        this.mStorys.clear();
        this.mStorys.addAll(storys);
        mNewsAdapter.notifyDataSetChanged();
    }

    @Override
    public void onFailure() {
        Snackbar.make(zhihuRefresh, getString(R.string.common_loading_error), Snackbar.LENGTH_INDEFINITE)
                .setAction(getString(R.string.comon_retry), new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        newsPresenter.requestNews();
                    }
                }).show();
    }

    @Override
    public void onBeforeFailure() {
        Snackbar.make(zhihuRefresh, getString(R.string.common_loading_error), Snackbar.LENGTH_INDEFINITE)
                .setAction(getString(R.string.comon_retry), new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        newsPresenter.requestBeforeNews();
                    }
                }).show();
    }



    @Override
    public void itemClick(View view, int position) {
        Intent intent = new Intent(getActivity(), ZhiHuDetailActivity.class);
        intent.putExtra(Constants.ZHIHU_PAGE_ID, mStorys.get(position).getId() + "");
        intent.putExtra(Constants.ZHIHU_IMAGE_PATH, mStorys.get(position).getImages().get(0));
        if (android.os.Build.VERSION.SDK_INT > 20){
            TransitionManager.beginDelayedTransition(zhihuRefresh, new ChangeImageTransform());
            startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(getActivity(), view, "news_image").toBundle());
        }else {
            startActivity(intent);
        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
        if (newsPresenter != null)
            newsPresenter.unsubcrible();
    }
}
