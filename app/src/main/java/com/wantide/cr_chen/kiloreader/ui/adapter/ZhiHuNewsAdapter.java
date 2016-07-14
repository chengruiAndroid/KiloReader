package com.wantide.cr_chen.kiloreader.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.wantide.cr_chen.kiloreader.R;
import com.wantide.cr_chen.kiloreader.model.bean.ZhiHuStoryBean;
import com.wantide.cr_chen.kiloreader.presenter.ZhiHuNewsPresenter;
import com.wantide.cr_chen.kiloreader.utils.ToolLog;

import java.util.List;

/**
 * Created by CR_Chen on 2016/6/15.
 */
public class ZhiHuNewsAdapter extends RecyclerView.Adapter<ZhiHuNewsAdapter.ViewHolder> {

    private List<ZhiHuStoryBean> mStorys;
    private ItemClickListener mItemClick = null;
    private PullRefreshListener mPullRefresh = null;//上拉加载更多监听
    private static final int TYPE_TITLE = 0;//表示为列表中标题
    private static final int TYPE_LIST = 1;//表示为列表


    public ZhiHuNewsAdapter(List<ZhiHuStoryBean> mStorys){
        this.mStorys = mStorys;
    }

    public interface ItemClickListener{
        void itemClick(View view, int position);
    }

    public void setItemClickListener(ItemClickListener mItemClick){
        this.mItemClick = mItemClick;
    }


    public interface PullRefreshListener{
        void PullRefresh();
    }

    public void setPullRefreshListener(PullRefreshListener mPullRefresh){
        this.mPullRefresh = mPullRefresh;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_TITLE){
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.adapter_zhihu_news_title, parent, false);

            return new ViewHolder(view, viewType);
        }else {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.adapter_zhihu_news, parent, false);

            return new ViewHolder(view, viewType);
        }

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        int viewType = getItemViewType(position);

        if (viewType == TYPE_TITLE){
            holder.mNewsTitleView.setText(mStorys.get(position).getTitle());
        } else {
            holder.mTitleView.setText(mStorys.get(position).getTitle());

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mItemClick != null){
                        mItemClick.itemClick(holder.mImageView, position);
                    }
                }
            });

            List<String> mImageUrls = mStorys.get(position).getImages();
            if (mImageUrls != null && mImageUrls.size() > 0){
                Glide.with(holder.mImageView.getContext())
                        .load(mImageUrls.get(0))
                        .centerCrop()
                        .into(holder.mImageView);
            }
        }

        //监听列表滑动到底部
        if (position == getItemCount() - 1 && mPullRefresh != null){
            mPullRefresh.PullRefresh();
        }

    }


    @Override
    public int getItemViewType(int position) {
        //异常处理
        if (null == mStorys || position < 0 || position >mStorys.size()){
            return TYPE_LIST;
        }

        if (mStorys.get(position).getType() == -1){
            return TYPE_TITLE;
        }

        return TYPE_LIST;
    }

    @Override
    public int getItemCount() {
        if (mStorys != null && mStorys.size() > 0){
            return mStorys.size();
        }

        return 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private View itemView;
        private TextView mTitleView;
        private ImageView mImageView;
        private TextView mNewsTitleView;

        public ViewHolder(View itemView, int viewType) {
            super(itemView);

            if (viewType == TYPE_TITLE){
                mNewsTitleView = (TextView)itemView.findViewById(R.id.zhihu_news_title);
            } else {
                this.itemView = itemView;
                mTitleView = (TextView)itemView.findViewById(R.id.news_title);
                mImageView = (ImageView)itemView.findViewById(R.id.news_image);
            }
        }
    }


}
