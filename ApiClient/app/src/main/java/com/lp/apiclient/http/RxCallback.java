package com.lp.apiclient.http;

import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.lp.apiclient.App;
import com.lp.apiclient.exception.ApiException;

import java.io.IOException;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import retrofit2.HttpException;

/**
 * @author LiPin
 * @date 2017/10/23 13:30
 * 描述：
 */

public abstract class RxCallback<T> implements Observer<T> {

    /**
     * 成功返回结果时被调用
     *
     * @param t
     */
    public abstract void onSuccess(T t);

    /**
     * 成功或失败到最后都会调用
     */
    public abstract void onFinished();

    @Override
    public void onSubscribe(@NonNull Disposable d) {
        Log.e("RxCallback", "---------------------onSubscribe" + Thread.currentThread().getName());
    }

    @Override
    public void onNext(T t) {
        Log.e("RxCallback", "---------------------onNext"+ Thread.currentThread().getName());
        onSuccess(t);
    }

    @Override
    public void onError(Throwable e) {
        Log.e("RxCallback", "---------------------onError"+ Thread.currentThread().getName());
        String errorMsg;
        if (e instanceof IOException) {
            /** 没有网络 */
            errorMsg = "Please check your network status";
        } else if (e instanceof HttpException) {
            /** 网络异常，http 请求失败，即 http 状态码不在 [200, 300) 之间, such as: "server internal error". */
            errorMsg = ((HttpException) e).response().message();
        } else if (e instanceof ApiException) {
            /** 网络正常，http 请求成功，服务器返回逻辑错误 */
            errorMsg = e.getMessage();
        } else {
            /** 其他未知错误 */
            errorMsg = !TextUtils.isEmpty(e.getMessage()) ? e.getMessage() : "unknown error";
        }

        Toast.makeText(App.getInstance(), errorMsg, Toast.LENGTH_SHORT).show();

        onFinished();
    }

    @Override
    public void onComplete() {
        Log.e("RxCallback", "---------------------onComplete");
        onFinished();
    }
}
