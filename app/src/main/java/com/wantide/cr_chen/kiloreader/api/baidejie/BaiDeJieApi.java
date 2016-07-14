package com.wantide.cr_chen.kiloreader.api.baidejie;

import com.wantide.cr_chen.kiloreader.model.bean.BaiDeJieShowBean;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Streaming;
import retrofit2.http.Url;
import rx.Observable;

/**
 * Created by CR_Chen on 2016/6/20.
 */
public interface BaiDeJieApi {

    @FormUrlEncoded
    @POST("/255-1")
    Observable<BaiDeJieShowBean> getBaiDeJieShow(@Field("showapi_appid") String appid, @Field("showapi_timestamp") String mCurTime
            , @Field("type") String type, @Field("showapi_sign") String sign, @Field("page") String page);
    //type=10 图片 type=29 段子  type=31 声音  type=41 视频

    @GET
    Call<ResponseBody> downloadImageFile(@Url String url);

}
