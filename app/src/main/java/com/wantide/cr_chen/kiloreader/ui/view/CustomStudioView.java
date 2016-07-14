package com.wantide.cr_chen.kiloreader.ui.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.bumptech.glide.request.target.Target;
import com.wantide.cr_chen.kiloreader.R;
import com.wantide.cr_chen.kiloreader.utils.ToolLog;

import java.io.InputStream;
import okhttp3.OkHttpClient;

/**
 * Created by CR_Chen on 2016/6/22.
 */
public class CustomStudioView extends FrameLayout {
    private ImageView iv;
    private ProgressWheel progressView;
    private Context context;
    private OkHttpClient mOkHttpClient;

    public CustomStudioView(Context context) {
        super(context);
        this.context = context;
        initView();
    }

    public CustomStudioView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initView();
    }

    public CustomStudioView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initView();
    }

    /**
     * 初始化
     */
    private void initView(){
        View view = LayoutInflater.from(context).inflate(R.layout.studio_view_item, this, true);
        iv = (ImageView)view.findViewById(R.id.studio_image);
        progressView = (ProgressWheel)view.findViewById(R.id.studio_progress);

        initOkHttp();


    }

    private void initOkHttp(){
        mOkHttpClient = new OkHttpClient();
        Glide.get(context).register(GlideUrl.class, InputStream.class, new OkHttpUrlLoader.Factory(mOkHttpClient));
    }

    /**
     * 加载图片
     * @param url
     */
    public void setResourceUrl(String url){
        progressView.setVisibility(View.VISIBLE);

        Glide.with(context)
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .transform(new CustomTransformation(context, url))
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        progressView.setVisibility(View.GONE);
                        return false;
                    }
                })
                .into(iv);
    }

}
