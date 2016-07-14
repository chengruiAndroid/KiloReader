package com.wantide.cr_chen.kiloreader.utils.imagecache;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.LruCache;

import java.lang.ref.SoftReference;
import java.util.LinkedHashMap;

/**
 * Created by CR_Chen on 2016/6/29.
 */
public class MemoryCache {

    private final static int SOFT_CACHE_SIZE = 15; // 软引用缓存容量
    private static LruCache<String, Bitmap> mLruCache; // 硬引用缓存
    private static LinkedHashMap<String, SoftReference<Bitmap>> mSoftCache; // 软引用缓存

    @SuppressLint("NewApi")
    public MemoryCache(Context context) {
        int memClass = ((ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE)).getMemoryClass();
        int cacheSize = 1024 * 1024 * memClass / 4; // 获取系统的1/4的空间 作为缓存大小
        mLruCache = new LruCache<String, Bitmap>(cacheSize) {

        @Override
        protected int sizeOf(String key, Bitmap value) {
            if (value != null) {
                return value.getRowBytes() * value.getHeight();
            }
            return 0;
        }

        @Override
        protected void entryRemoved(boolean evicted, String key,
                Bitmap oldValue, Bitmap newValue) {
            if (oldValue != null) {
                // 硬引用缓存满的时候，会根据lru算法把最近没有被使用的图片抓入软引用
                mSoftCache.put(key, new SoftReference<Bitmap>(oldValue));
            }
        }
        };
        mSoftCache = new LinkedHashMap<String, SoftReference<Bitmap>>(
                SOFT_CACHE_SIZE, 0.75f, true) {

            private static final long serialVersionUID = 1L;

            @Override
            protected boolean removeEldestEntry(
                    java.util.Map.Entry<String, SoftReference<Bitmap>> eldest) {
                if (size() > SOFT_CACHE_SIZE) {
                    return true;
                }
                return false;
            }
        };
    }

    /**
     * 存储图片到缓存
     *
     * @param url
     *            ：key
     * @param bitmap
     *            ： 图片
     */
    @SuppressLint("NewApi")
    public void saveBitmap(String url, Bitmap bitmap) {
        if (bitmap != null) {
            synchronized (bitmap) {
                mLruCache.put(url, bitmap);
            }
        }
    }

    /**
     * 获取缓存图片
     *
     * @param url
     *            ：url
     * @return
     */
    @SuppressLint("NewApi")
    public Bitmap getBitmap(String url) {
        Bitmap bitmap = null;
        // 从硬引用找
        synchronized (mLruCache) {
            // 从硬引用中获取
            bitmap = mLruCache.get(url);
            if (bitmap != null) {
                // 如果找到了，将元素移动到linkendHashMap的最前面，从而保证lrd算法中的是最后删除
                mLruCache.remove(url);
                mLruCache.put(url, bitmap);
                return bitmap;
            }
        }
        // 硬引用没找到，从软引用找
        synchronized (mSoftCache) {
            SoftReference<Bitmap> softReference = mSoftCache.get(url);
            if (softReference != null) {
                bitmap = softReference.get();
                // 如果找到了，重新添加到硬缓存中
                mLruCache.put(url, bitmap);
                mSoftCache.remove(url);
                return bitmap;
            } else {
                mSoftCache.remove(url);
            }
        }
        return null;
    }

    /**
     * 清除缓存
     */
    public void clearCache() {
        mSoftCache.clear();
    }
}
