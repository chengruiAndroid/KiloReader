package com.wantide.cr_chen.kiloreader.api.baidejie;

import com.wantide.cr_chen.kiloreader.KiloApplication;
import com.wantide.cr_chen.kiloreader.api.zhihu.ZhihuApi;
import com.wantide.cr_chen.kiloreader.utils.NetWorkUtil;

import java.io.File;
import java.io.IOException;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by CR_Chen on 2016/6/20.
 */
public class BaiDeJieRequest {

    public BaiDeJieRequest(){}

    private static final Interceptor REWRITE_CACHE_CONTROL_INTERCEPTOR = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Response originalResponse = chain.proceed(chain.request());
            if (NetWorkUtil.isNetWorkAvailable(KiloApplication.getContext())) {
                int maxAge = 60; // 在线缓存在1分钟内可读取
                return originalResponse.newBuilder()
                        .removeHeader("Pragma")
                        .removeHeader("Cache-Control")
                        .header("Cache-Control", "public, max-age=" + maxAge)
                        .build();
            } else {
                int maxStale = 60 * 60 * 24 * 28; // 离线时缓存保存4周
                return originalResponse.newBuilder()
                        .removeHeader("Pragma")
                        .removeHeader("Cache-Control")
                        .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                        .build();
            }
        }
    };
    static File httpCacheDirectory = new File(KiloApplication.getContext().getCacheDir(), "baidejieCache");

    static int cacheSize = 10 * 1024 * 1024; // 10 MiB
    static Cache cache = new Cache(httpCacheDirectory, cacheSize);
    static OkHttpClient client = new OkHttpClient.Builder()
            .addNetworkInterceptor(REWRITE_CACHE_CONTROL_INTERCEPTOR)
            .addInterceptor(REWRITE_CACHE_CONTROL_INTERCEPTOR)
            .cache(cache)
            .build();

    private static BaiDeJieApi deJieApi = null;
    protected static final Object monitor = new Object();
    public static BaiDeJieApi getBaiDeJieApi() {
        synchronized (monitor){
            if (deJieApi == null) {
                deJieApi = new Retrofit.Builder()
                        .baseUrl("https://route.showapi.com")
                        .client(client)
                        .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                        .addConverterFactory(GsonConverterFactory.create())
                        .build().create(BaiDeJieApi.class);
            }
            return deJieApi;
        }
    }

}
