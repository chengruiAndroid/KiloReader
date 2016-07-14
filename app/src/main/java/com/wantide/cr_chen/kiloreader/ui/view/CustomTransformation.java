package com.wantide.cr_chen.kiloreader.ui.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.wantide.cr_chen.kiloreader.utils.FileUtils;

/**
 * Created by CR_Chen on 2016/6/21.
 */
public class CustomTransformation extends BitmapTransformation {

    private String mImageUrl;
    public CustomTransformation(Context context, String mImageUrl){
        super(context);
        this.mImageUrl = mImageUrl;
    }



    @Override
    protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
        Bitmap originalBitmap = toTransform.copy(Bitmap.Config.ARGB_8888, true);
        FileUtils.saveImage(originalBitmap, mImageUrl);

        int imageHeight = originalBitmap.getHeight();
        if (imageHeight > 600){
            originalBitmap = Bitmap.createBitmap(originalBitmap, 0, 0, originalBitmap.getWidth(), 600);
        }

        float mScale = (float)outWidth / (float)originalBitmap.getWidth();
        originalBitmap = big(originalBitmap, mScale);

        return originalBitmap;
    }

    @Override
    public String getId() {
        return "Custom";
    }

    private static Bitmap big(Bitmap bitmap, float scale) {
        Matrix matrix = new Matrix();
        matrix.postScale(scale,scale); //长和宽放大缩小的比例
        return Bitmap.createBitmap(bitmap, 0, 0 ,bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }









}
