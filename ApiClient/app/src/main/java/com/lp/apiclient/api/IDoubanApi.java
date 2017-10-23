package com.lp.apiclient.api;

import com.lp.apiclient.entity.HttpResult;
import com.lp.apiclient.entity.Subjects;

import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * @author LiPin
 * @date 2017/10/23 13:12
 * 描述：
 */

public interface IDoubanApi {

    /**
     * 获取热映电影
     *
     * @return
     */
    @GET("/v2/movie/in_theaters")
    Observable<HttpResult<Subjects>> inTheaters();

}
