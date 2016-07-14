package com.wantide.cr_chen.kiloreader.ui.view.largeImage;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapRegionDecoder;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Movie;
import android.graphics.Rect;
import android.media.Image;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.wantide.cr_chen.kiloreader.utils.FileUtils;
import com.wantide.cr_chen.kiloreader.utils.ToolLog;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by CR_Chen on 2016/6/29.
 */
public class LargeImageView extends ImageView {
    private BitmapRegionDecoder mDecoder;//区域显示图片
    private int mImageWidth, mImageHeight;//图片的高度和宽度
    private volatile Rect mRect = new Rect();//手机屏幕显示图片的区域
    private Matrix mScaleMatrix = new Matrix();//放大缩小矩阵
    private MoveGestureDetector mDetector;//手势识别
    private static final BitmapFactory.Options options = new BitmapFactory.Options();
    private boolean mOnce = false;
    private Context context;
    private int type;// 0:图片  1:gif

    static {
        options.inPreferredConfig = Bitmap.Config.RGB_565;
    }

    public LargeImageView(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public LargeImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    public LargeImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init();
    }

    public void setImagePath(String imagePath, int type)
    {
        this.type = type;
        try
        {
            mDecoder = BitmapRegionDecoder.newInstance(imagePath, false);
            BitmapFactory.Options tmpOptions = new BitmapFactory.Options();
            // Grab the bounds for the scene dimensions
            tmpOptions.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(imagePath, tmpOptions);
            mImageWidth = tmpOptions.outWidth;
            mImageHeight = tmpOptions.outHeight;

            requestLayout();
            invalidate();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public void setGifPath(String gifPath, int type){
        this.type = type;
        Glide.with(context).load(gifPath)
                .asGif()
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(this);
    }


    public void init() {
        mDetector = new MoveGestureDetector(getContext(), new MoveGestureDetector.SimpleMoveGestureDetector() {
            @Override
            public boolean onMove(MoveGestureDetector detector) {
                int moveX = (int) detector.getMoveX();
                int moveY = (int) detector.getMoveY();

                if (mImageWidth > getWidth()) {
                    mRect.offset(-moveX, 0);
                    checkWidth();
                    invalidate();
                }

                if (mImageHeight > getHeight()) {
                    mRect.offset(0, -moveY);
                    checkHeight();
                    invalidate();
                }

                return true;
            }

        });
    }

    private void checkWidth() {
        Rect rect = mRect;
        int imageWidth = mImageWidth;
        int imageHeight = mImageHeight;

        if (rect.right > imageWidth)
        {
            rect.right = imageWidth;
            rect.left = imageWidth - getWidth();
        }

        if (rect.left < 0)
        {
            rect.left = 0;
            rect.right = getWidth();
        }
    }

    private void checkHeight() {

        Rect rect = mRect;
        int imageWidth = mImageWidth;
        int imageHeight = mImageHeight;


        if (rect.bottom > imageHeight)
        {
            rect.bottom = imageHeight;
            rect.top = imageHeight - getHeight();
        }

        if (rect.top < 0)
        {
            rect.top = 0;
            rect.bottom = getHeight();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        mDetector.onToucEvent(event);
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        if (type == 1){
            super.onDraw(canvas);
            return;
        }

        if (mDecoder != null){
            Bitmap bm = mDecoder.decodeRegion(mRect, options);

            float scale = 1.0f;
            if (!mOnce){
                mOnce = true;
                scale = getWidth() * 1.0f / mImageWidth;
                //计算图片放大后的长度
                float mScreenScale = (getHeight() * 1.0f) / (getWidth() * 1.0f);
                mImageHeight = mImageHeight + (int)(mImageWidth * (scale - 1) * mScreenScale);
                if (mImageHeight < getHeight()){
                    mScaleMatrix.postTranslate(0, getHeight() / 2 - mImageHeight / 2);
                }
            }
            mScaleMatrix.postScale(scale, scale);

            canvas.drawBitmap(bm, mScaleMatrix, null);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int width = getMeasuredWidth();
        int height = getMeasuredHeight();

        mRect.left = 0;
        mRect.top = 0;
        mRect.right = width;
        mRect.bottom = height;

    }

}
