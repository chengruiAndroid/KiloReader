package com.wantide.cr_chen.kiloreader.ui.activity;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.ImageView;

import com.wantide.cr_chen.kiloreader.R;
import com.wantide.cr_chen.kiloreader.ui.view.largeImage.LargeImageView;
import com.wantide.cr_chen.kiloreader.utils.Constants;
import com.wantide.cr_chen.kiloreader.utils.FileUtils;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by CR_Chen on 2016/6/28.
 */
public class BaiDeJieCheckImageActivity extends Activity{

    @Bind(R.id.baidejie_large_image)
    LargeImageView baidejieLargeImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baidejie_check_image);
        ButterKnife.bind(this);

        String imageUrl = getIntent().getStringExtra(Constants.BAIDEJIE_IMAGE_INTENT);

        String[] imageStrs = imageUrl.split("\\.");
        String type = imageStrs[imageStrs.length - 1];
        if (type.equals(Constants.IMAGE_TYPE_GIF)){
            baidejieLargeImage.setGifPath(imageUrl, 1);
        }else {
            baidejieLargeImage.setImagePath(FileUtils.getImagePath(imageUrl), 0);
        }

        overridePendingTransition(R.anim.slide_scale_in, 0);//切换动画
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (baidejieLargeImage != null){
            Drawable d = ((ImageView)baidejieLargeImage).getDrawable();
            if(d != null && d instanceof BitmapDrawable)
            {
                Bitmap bmp = ((BitmapDrawable)d).getBitmap();
                bmp.recycle();
                bmp = null;
            }
        }

        finish();
    }
}
