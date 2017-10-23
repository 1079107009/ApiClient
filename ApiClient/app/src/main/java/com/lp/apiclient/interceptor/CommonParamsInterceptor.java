package com.lp.apiclient.interceptor;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author LiPin
 * @date 2017/10/23 13:41
 * 描述：网络请求公共参数插入器
 */

public class CommonParamsInterceptor implements Interceptor {

    private final String GET = "GET";
    private final String POST = "POST";

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();

        if (GET.equals(request.method())) {
            HttpUrl httpUrl = request.url().newBuilder()
                    .addQueryParameter("version", "xxx")
                    .addQueryParameter("device", "Android")
                    .addQueryParameter("timestamp", String.valueOf(System.currentTimeMillis()))
                    .build();
            request = request.newBuilder().url(httpUrl).build();
        } else if (POST.equals(request.method()) && request.body() instanceof FormBody) {
            FormBody.Builder bodyBuilder = new FormBody.Builder();
            FormBody formBody = (FormBody) request.body();

            for (int i = 0; i < formBody.size(); i++) {
                bodyBuilder.addEncoded(formBody.encodedName(i), formBody.encodedValue(i));
            }

            formBody = bodyBuilder
                    .addEncoded("version", "xxx")
                    .addEncoded("device", "Android")
                    .addEncoded("timestamp", String.valueOf(System.currentTimeMillis()))
                    .build();

            request = request.newBuilder().post(formBody).build();
        }

        return chain.proceed(request);

    }
}
