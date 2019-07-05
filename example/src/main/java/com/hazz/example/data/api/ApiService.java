package com.hazz.example.data.api;

import com.hazz.example.data.entity.BaseEntity;
import com.hazz.example.data.entity.BaseResult;
import com.hazz.example.data.entity.TestNews;
import com.hazz.baselibs.net.BaseHttpResult;
import com.hazz.example.data.entity.ZkyEntity;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;

/**
 * @author xuhao
 * @date 2018/6/11 23:04
 * @desc
 */
public interface ApiService {

    @GET("api/data/Android/10/1")
    Observable<BaseHttpResult<List<TestNews>>> getGankData();


    /**
     * 检测接口
     * @return
     */
    @Headers("urlName:zky")
    @POST("/api/seetaas/icebox")
    @Multipart
    Observable<ZkyEntity> detection(@PartMap() Map<String, RequestBody> partMap, @Part MultipartBody.Part file);


    /**
     * 中科院结果上传接口
     * @return
     */
    @Headers("urlName:test")
    @POST("/mxg/trade/zky/generator/order")
    @FormUrlEncoded
    Observable<BaseResult> generateOrder(@Field("machine_sn") String machine_sn,
                                         @Field("trade_id") String trade_id,
                                         @Field("sku_code") String sku_code,
                                         @Field("camera_index") String camera_index,
                                         @Field("security") String security);


    /** 上报错误信息
     * @param
     * @return
     */
    @Headers("urlName:zong")
    @FormUrlEncoded
    @POST("/coffeeapi/coffee/api")
    Observable<BaseEntity> reportError(@Field("cmd") String cmd, @Field("vmc_no") String vmc_no,
                                       @Field("date_time") String date_time, @Field("fault_id") String fault_id,
                                       @Field(value = "fault_desc", encoded = true) String fault_desc, @Field("is_set") boolean is_set,
                                       @Field("is_ok") boolean is_ok, @Field("fault_type") String fault_type);


}
