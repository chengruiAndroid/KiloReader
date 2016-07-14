package com.wantide.cr_chen.kiloreader.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.wantide.cr_chen.kiloreader.R;
import com.wantide.cr_chen.kiloreader.model.bean.MeiZiDetailBean;
import com.wantide.cr_chen.kiloreader.presenter.MeiZiPresenter;
import com.wantide.cr_chen.kiloreader.presenter.impl.MeiZiNewView;
import com.wantide.cr_chen.kiloreader.ui.activity.MeiZiDetailActivity;
import com.wantide.cr_chen.kiloreader.ui.adapter.MeiZiNewsAdapter;
import com.wantide.cr_chen.kiloreader.ui.view.CustomMaterialRefreshLayout;
import com.wantide.cr_chen.kiloreader.utils.Constants;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by CR_Chen on 2016/7/11.
 */
public class MeiZiNewsFragment extends Fragment implements MeiZiNewView, MeiZiNewsAdapter.PullRefreshListener
        ,MeiZiNewsAdapter.ItemClickListener{

    @Bind(R.id.meizi_news_recycler)
    RecyclerView meiziNewsRecycler;
    @Bind(R.id.meizi_refresh)
    CustomMaterialRefreshLayout meiziRefresh;

    private MeiZiPresenter meiZiPresenter;
    private List<MeiZiDetailBean> mMeiZiDetails = new ArrayList<>();
    private MeiZiNewsAdapter newsAdapter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_meizi_news, container, false);
        ButterKnife.bind(this, view);

        meiziRefresh();
        initRecyclerView();

        meiZiPresenter = new MeiZiPresenter(this);
        meiziRefresh.autoRefresh();//自动刷新
        return view;
    }

    private void meiziRefresh(){
        meiziRefresh.setMaterialRefreshListener(new MaterialRefreshListener() {
            @Override
            public void onRefresh(MaterialRefreshLayout materialRefreshLayout) {
                if (meiZiPresenter != null)
                    meiZiPresenter.getMeiZiDetailDatas();
            }
        });
    }


    private void initRecyclerView() {
        meiziNewsRecycler.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        newsAdapter = new MeiZiNewsAdapter(mMeiZiDetails);
        meiziNewsRecycler.setAdapter(newsAdapter);
        newsAdapter.setPullRefreshListener(this);
        newsAdapter.setItemClickListener(this);

        meiziNewsRecycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
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
    public void onSuccessFulNews(List<MeiZiDetailBean> detailBeans) {
        meiziRefresh.finishRefresh();
        newsAdapter.setNewData(detailBeans);
    }

    @Override
    public void onFailureNews() {
        Snackbar.make(meiziRefresh, getString(R.string.common_loading_error), Snackbar.LENGTH_INDEFINITE)
                .setAction(getString(R.string.comon_retry), new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        meiZiPresenter.getMeiZiDetailDatas();
                    }
                }).show();
    }

    @Override
    public void onSuccessFulMore(List<MeiZiDetailBean> detailBeans) {
        meiziRefresh.finishRefresh();
        newsAdapter.setMoreData(detailBeans);
    }

    @Override
    public void onFailureMore() {
        Snackbar.make(meiziRefresh, getString(R.string.common_loading_error), Snackbar.LENGTH_INDEFINITE)
                .setAction(getString(R.string.comon_retry), new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        meiZiPresenter.getMeiZiMoreDatas();
                    }
                }).show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
        if (meiZiPresenter != null)
            meiZiPresenter.unsubcrible();
    }

    @Override
    public void PullRefresh() {
        meiZiPresenter.getMeiZiMoreDatas();
    }

    @Override
    public void itemClick(String webUrl, String title) {
        Intent intent = new Intent(getActivity(), MeiZiDetailActivity.class);
        intent.putExtra(Constants.MEIZI_INTENT_SIGN, webUrl);
        intent.putExtra(Constants.MEIZI_INTENT_TITLE, title);
        startActivity(intent);
    }
}
