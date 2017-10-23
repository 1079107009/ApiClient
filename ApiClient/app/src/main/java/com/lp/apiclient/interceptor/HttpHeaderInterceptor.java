package com.lp.apiclient.interceptor;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author LiPin
 * @date 2017/10/23 10:57
 * 描述：Http Header可以通过这个拦截器为Request添加全局统一的Header
 */

public class HttpHeaderInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request original = chain.request();
        Request request = original.newBuilder()
                .header("User-Agent", "Android, xxx")
                .header("Accept", "application/json")
                .header("Content-type", "application/json")
                .method(original.method(), original.body())
                .build();
        return chain.proceed(request);
    }
}
