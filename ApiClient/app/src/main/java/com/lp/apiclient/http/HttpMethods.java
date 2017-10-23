package com.lp.apiclient.http;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.franmontiel.persistentcookiejar.PersistentCookieJar;
import com.franmontiel.persistentcookiejar.cache.SetCookieCache;
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor;
import com.lp.apiclient.App;
import com.lp.apiclient.interceptor.HttpCacheInterceptor;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author LiPin
 * @date 2017/10/19 11:17
 * 描述：
 */

public class HttpMethods {

    private static final int DEFAULT_TIMEOUT = 5;
    private static final String BASE_URL = "https://api.douban.com/";
    private static final long DEFAULT_CACHE_SIZE = 1024 * 1024;

    private Retrofit retrofit;
    private volatile static HttpMethods instance;

    /**
     * 构造方法私有
     */
    private HttpMethods() {
        //// 创建OkHttpClient
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder()
                //超时设置
                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                //错误重连
                .retryOnConnectionFailure(true)
                //cookie管理
                .cookieJar(new PersistentCookieJar(new SetCookieCache(),
                        new SharedPrefsCookiePersistor(App.getInstance())));

        // 添加各种插入器
        addInterceptor(httpClientBuilder);

        retrofit = new Retrofit.Builder()
                .client(httpClientBuilder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(BASE_URL)
                .build();

    }

    private void addInterceptor(OkHttpClient.Builder builder) {
        // 添加Header
        //builder.addInterceptor(new HttpHeaderInterceptor());

        // 添加缓存控制策略
        File cacheDir = App.getInstance().getExternalCacheDir();
        Cache cache = new Cache(cacheDir, DEFAULT_CACHE_SIZE);
        builder.cache(cache).addInterceptor(new HttpCacheInterceptor());

        // 添加http log
        HttpLoggingInterceptor logger = new HttpLoggingInterceptor();
        logger.setLevel(HttpLoggingInterceptor.Level.BODY);
        builder.addInterceptor(logger);

        // 添加调试工具
        builder.networkInterceptors().add(new StethoInterceptor());
    }


    /**
     * 获取单例
     *
     * @return
     */
    public static HttpMethods getInstance() {
        //检查实例是否存在，不存在才进入同步块
        if (instance == null) {
            //同步块，保证线程安全
            synchronized (HttpMethods.class) {
                //再次检查实例是否存在，不存在才创建实例
                if (instance == null) {
                    instance = new HttpMethods();
                }
            }
        }
        return instance;
    }

    /**
     * 获取apiservice
     */
    public <T> T getService(Class<T> t) {
        return retrofit.create(t);
    }

}
