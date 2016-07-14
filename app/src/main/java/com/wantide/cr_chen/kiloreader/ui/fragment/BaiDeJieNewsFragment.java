package com.wantide.cr_chen.kiloreader.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.wantide.cr_chen.kiloreader.R;
import com.wantide.cr_chen.kiloreader.model.bean.BaiDeJieContentBean;
import com.wantide.cr_chen.kiloreader.presenter.BaiDeJieNewsPresenter;
import com.wantide.cr_chen.kiloreader.presenter.impl.BaiDeJieNewView;
import com.wantide.cr_chen.kiloreader.ui.activity.BaiDeJieCheckImageActivity;
import com.wantide.cr_chen.kiloreader.ui.adapter.BaiDeJieNewsAdapter;
import com.wantide.cr_chen.kiloreader.ui.view.CustomMaterialRefreshLayout;
import com.wantide.cr_chen.kiloreader.utils.Constants;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by CR_Chen on 2016/6/21.
 */
public class BaiDeJieNewsFragment extends Fragment implements BaiDeJieNewView
        , BaiDeJieNewsAdapter.PullRefreshListener, BaiDeJieNewsAdapter.ItemClickListener{

    @Bind(R.id.baidejie_news_recycler)
    RecyclerView baidejieNewsRecycler;
    @Bind(R.id.baidejie_refresh)
    CustomMaterialRefreshLayout baidejieRefresh;

    private BaiDeJieNewsPresenter newsPresenter;
    private BaiDeJieNewsAdapter newsAdapter;
    private List<BaiDeJieContentBean> mNewDatas = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_baidejie_news, container, false);
        ButterKnife.bind(this, view);

        initRefresh();
        initRecycleView();

        newsPresenter = new BaiDeJieNewsPresenter(this);
        baidejieRefresh.autoRefresh();//自动刷新

        return view;
    }

    private void initRefresh() {
        baidejieRefresh.setMaterialRefreshListener(new MaterialRefreshListener() {
            @Override
            public void onRefresh(MaterialRefreshLayout materialRefreshLayout) {
                if (newsPresenter != null)
                    newsPresenter.requestNews("1");
            }
        });
    }

    /**
     * 初始化recycleview
     */
    private void initRecycleView() {
        baidejieNewsRecycler.setLayoutManager(new LinearLayoutManager(baidejieNewsRecycler.getContext()));
        newsAdapter = new BaiDeJieNewsAdapter(mNewDatas);
        baidejieNewsRecycler.setAdapter(newsAdapter);
        newsAdapter.setPullRefreshListener(this);
        newsAdapter.setItemClickListener(this);

        baidejieNewsRecycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {

                switch (newState) {
                    case RecyclerView.SCROLL_STATE_IDLE:
                        Glide.with(getActivity()).resumeRequests();
                        break;
                    case RecyclerView.SCROLL_STATE_DRAGGING:
                        Glide.with(getActivity()).pauseRequests();
                        break;
                }
            }
        });
    }

    @Override
    public void onNewsSuccessful(List<BaiDeJieContentBean> contentBeans) {
        baidejieRefresh.finishRefresh();
        this.mNewDatas.clear();
        this.mNewDatas.addAll(contentBeans);
        newsAdapter.notifyDataSetChanged();
    }

    @Override
    public void onNewsFailure() {
        Snackbar.make(baidejieRefresh, getString(R.string.common_loading_error), Snackbar.LENGTH_INDEFINITE)
                .setAction(getString(R.string.comon_retry), new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        newsPresenter.requestNews("1");
                    }
                }).show();
    }

    @Override
    public void onNewsMoreSuccessful(List<BaiDeJieContentBean> contentBeans) {
        baidejieRefresh.finishRefresh();
        this.mNewDatas.addAll(contentBeans);
        newsAdapter.notifyDataSetChanged();
    }

    @Override
    public void onNewsMoreFailure() {
        Snackbar.make(baidejieRefresh, getString(R.string.common_loading_error), Snackbar.LENGTH_INDEFINITE)
                .setAction(getString(R.string.comon_retry), new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        newsPresenter.requestMoreNews();
                    }
                }).show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);

        if (newsPresenter != null)
            newsPresenter.unsubcrible();

        if (baidejieRefresh != null)
            baidejieRefresh.removeAllViews();


    }

    @Override
    public void PullRefresh() {//上拉加载更多
        newsPresenter.requestMoreNews();
    }

    @Override
    public void itemClick(int position) {
        Intent in = new Intent(getActivity(), BaiDeJieCheckImageActivity.class);
        in.putExtra(Constants.BAIDEJIE_IMAGE_INTENT, mNewDatas.get(position).getImage0());
        startActivity(in);
    }
}
