package com.lp.apiclient.utils;

import com.lp.apiclient.entity.HttpResult;
import com.lp.apiclient.exception.ApiException;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * @author LiPin
 * @date 2017/10/23 11:15
 * 描述：
 */

public class RxUtil {
    /**
     * 普通线程切换: IO -> Main
     *
     * @param <T>
     * @return
     */
    public static <T> ObservableTransformer<T, T> normalSchedulers() {
        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(@NonNull Observable<T> source) {
                return source.subscribeOn(Schedulers.io())
                        .unsubscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    /**
     * 对HttpResult返回结果做预处理，对逻辑错误抛出异常
     *
     * @param <T>
     * @return
     */
    public static <T> Function<HttpResult<T>, T> handleHttpResult() {
        return new Function<HttpResult<T>, T>() {
            @Override
            public T apply(@NonNull HttpResult<T> httpResult) throws Exception {
                if (httpResult.getResultCode() != HttpResult.SUCCESS) {
                    throw new ApiException(httpResult.getResultCode(), httpResult.getResultMessage());
                }
                return httpResult.getData();
            }
        };
    }

}
