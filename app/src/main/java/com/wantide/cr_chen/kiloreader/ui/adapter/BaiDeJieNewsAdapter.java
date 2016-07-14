package com.wantide.cr_chen.kiloreader.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.wantide.cr_chen.kiloreader.R;
import com.wantide.cr_chen.kiloreader.model.bean.BaiDeJieContentBean;
import com.wantide.cr_chen.kiloreader.ui.view.CustomStudioView;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by CR_Chen on 2016/6/21.
 */
public class BaiDeJieNewsAdapter extends RecyclerView.Adapter<BaiDeJieNewsAdapter.ViewHolder> {
    private List<BaiDeJieContentBean> mNewDatas;
    private PullRefreshListener mPullRefresh = null;
    private ItemClickListener mItemClick = null;

    public BaiDeJieNewsAdapter(List<BaiDeJieContentBean> mNewDatas){
        this.mNewDatas = mNewDatas;
    }

    public interface ItemClickListener{
        void itemClick(int position);
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
    public BaiDeJieNewsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_baidejie_news, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final BaiDeJieNewsAdapter.ViewHolder holder, final int position) {

        BaiDeJieContentBean deJieContent = mNewDatas.get(position);

        //用户图片
        Glide.with(holder.userImageView.getContext()).load(deJieContent.getProfile_image())
                .fitCenter().into(holder.userImageView);
        //用户名
        holder.userNameText.setText(deJieContent.getName());

        //文章发布时间
        holder.userReleaseTimeText.setText(deJieContent.getCreate_time());

        //正文
        String textStr = deJieContent.getText().replaceAll("\n", "").replaceAll(" ", "");
        holder.contentText.setText(textStr);

        //图片以及gif************************************************************************Glide就是内存开销不是很乐观呀
//        String imageStrUrl = deJieContent.getImage0();
//        String typeStr = imageStrUrl.substring(imageStrUrl.length() - 3, imageStrUrl.length());

//        if (typeStr.equals(Constants.IMAGE_TYPE_GIF)){
//            Glide.with(holder.contentImage.getContext()).load(deJieContent.getImage0())
//                    .asGif()
//                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
//                    .into(holder.contentImage);
//        }else {
//            Glide.with(holder.contentImage.getContext()).load(deJieContent.getImage0())
//                    .asBitmap()
//                    .transform(new CustomTransformation(holder.contentImage.getContext()))
//                    .into(holder.contentImage);
//        }
        Glide.clear(holder.contentImage);
        holder.contentImage.setResourceUrl(deJieContent.getImage0());

        holder.contentImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mItemClick != null){
                    mItemClick.itemClick(position);
                }
            }
        });


        //监听列表滑动到底部
        if (position == getItemCount() - 1 && mPullRefresh != null){
            mPullRefresh.PullRefresh();
        }
    }

    @Override
    public int getItemCount() {
        if (mNewDatas != null && mNewDatas.size() > 0){
            return mNewDatas.size();
        }
        return 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private CircleImageView userImageView;
        private TextView userNameText;
        private TextView userReleaseTimeText;
        private TextView contentText;
        private CustomStudioView contentImage;
        private LinearLayout mainView;


        public ViewHolder(View itemView) {
            super(itemView);

            mainView = (LinearLayout)itemView.findViewById(R.id.baidejie_content_view);
            userImageView = (CircleImageView)itemView.findViewById(R.id.baidejie_user_image);
            userNameText = (TextView)itemView.findViewById(R.id.baidejie_user_name);
            userReleaseTimeText = (TextView)itemView.findViewById(R.id.baidejie_release_time);
            contentText = (TextView)itemView.findViewById(R.id.baidejie_text);
            contentImage = (CustomStudioView)itemView.findViewById(R.id.baidejie_image);
        }
    }

}
