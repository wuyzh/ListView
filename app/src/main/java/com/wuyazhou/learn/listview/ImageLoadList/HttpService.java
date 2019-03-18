package com.wuyazhou.learn.listview.ImageLoadList;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * @author wuyzh
 * */
public interface HttpService {
    /**
     * 获取网络列表
     * @param num
     * @param type
     * @return Observable<ItemInfo> 被观察者
     * */
    @GET("teacher")
    Observable<ItemInfos> getItems(@Query("type")String type,@Query("num")String num);
}
