package com.wantide.cr_chen.kiloreader.ui.adapter;

import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.wantide.cr_chen.kiloreader.R;
import com.wantide.cr_chen.kiloreader.model.bean.MeiZiDetailBean;
import com.wantide.cr_chen.kiloreader.utils.Colors;

import java.util.List;

/**
 * Created by CR_Chen on 2016/7/11.
 */
public class MeiZiNewsAdapter extends RecyclerView.Adapter<MeiZiNewsAdapter.ViewHolder> {
    private List<MeiZiDetailBean> mMeiZiDetails;
    private PullRefreshListener mPullRefresh = null;
    private ItemClickListener mItemClick = null;
    private int[] colorThemes = new int[]{
            Colors.MEIZI_LAN, Colors.MEIZI_ZI,
            Colors.MEIZI_HOT, Colors.MEIZI_ORANGE,
            Colors.MEIZI_YELLOW
    };

    public interface ItemClickListener{
        void itemClick(String url, String title);
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

    public MeiZiNewsAdapter(List<MeiZiDetailBean> mMeiZiDetails) {
        this.mMeiZiDetails = mMeiZiDetails;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_meizi_news, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        MeiZiDetailBean meiZiDetail = mMeiZiDetails.get(position);

        Glide.with(holder.mMeiZiIv.getContext())
                .load(meiZiDetail.getImage_url())
                .centerCrop()
                .into(holder.mMeiZiIv);

        holder.mMeiZiCard.setCardBackgroundColor(colorThemes[(int) (Math.random() * colorThemes.length)]);
        holder.mMeiZiTv.setText(meiZiDetail.getTitle());
        holder.mMeiZiCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mItemClick.itemClick(mMeiZiDetails.get(position).getGroup_url(), mMeiZiDetails.get(position).getTitle());
            }
        });

        //监听列表滑动到底部
        if (position == getItemCount() - 1 && mPullRefresh != null){
            mPullRefresh.PullRefresh();
        }
    }

    @Override
    public int getItemCount() {
        return mMeiZiDetails.size();
    }

    /**
     * 获取数据更新列表
     * @param detailBeans
     */
    public void setNewData(List<MeiZiDetailBean> detailBeans){
        mMeiZiDetails.clear();
        mMeiZiDetails.addAll(detailBeans);
        this.notifyDataSetChanged();
    }

    /**
     * 获取数据添加列表
     * @param detailBeans
     */
    public void setMoreData(List<MeiZiDetailBean> detailBeans){
        mMeiZiDetails.addAll(detailBeans);
        this.notifyDataSetChanged();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView mMeiZiIv;
        private TextView mMeiZiTv;
        private CardView mMeiZiCard;


        public ViewHolder(View itemView) {
            super(itemView);
            mMeiZiIv = (ImageView) itemView.findViewById(R.id.meizi_image);
            mMeiZiTv = (TextView) itemView.findViewById(R.id.meizi_text);
            mMeiZiCard = (CardView) itemView.findViewById(R.id.meizi_card);
        }
    }

}
