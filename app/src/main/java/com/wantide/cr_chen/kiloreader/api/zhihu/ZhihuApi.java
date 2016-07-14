package com.wantide.cr_chen.kiloreader.api.zhihu;

import com.wantide.cr_chen.kiloreader.model.bean.WellcomBean;
import com.wantide.cr_chen.kiloreader.model.bean.ZhiHuDetailBean;
import com.wantide.cr_chen.kiloreader.model.bean.ZhiHuNewBean;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by 蔡小木 on 2016/3/6 0006.
 */
public interface ZhihuApi {
    //获取欢迎界面
    @GET("/api/4/start-image/{size}")
    Observable<WellcomBean> getStartImage(@Path("size") String size);

    //获取最新消息
    @GET("/api/4/news/latest")
    Observable<ZhiHuNewBean> getHotNews();

    //获取过往消息
    @GET("/api/4/news/before/{data}")
    Observable<ZhiHuNewBean> getBeforeNews(@Path("data") String data);

    //获取消息内容
    @GET("/api/4/news/{id}")
    Observable<ZhiHuDetailBean> getDetailNew(@Path("id") String id);



}
