package com.wantide.cr_chen.kiloreader.api.meizi;

import com.wantide.cr_chen.kiloreader.model.bean.MeiZiContentBean;
import com.wantide.cr_chen.kiloreader.model.bean.WellcomBean;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by CR_Chen on 2016/7/11.
 */
public interface MeiZiApi {
    //获取豆瓣妹子api地址
    @GET("/category/All/page/{num}")
    Observable<MeiZiContentBean> getMeiZiDetail(@Path("num") String num);
}
