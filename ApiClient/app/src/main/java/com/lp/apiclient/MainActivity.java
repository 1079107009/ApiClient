package com.lp.apiclient;

import android.os.Bundle;
import android.util.Log;

import com.lp.apiclient.api.IDoubanApi;
import com.lp.apiclient.entity.HttpResult;
import com.lp.apiclient.entity.Subjects;
import com.lp.apiclient.http.HttpMethods;
import com.lp.apiclient.http.RxCallback;
import com.lp.apiclient.utils.RxUtil;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

/**
 * @author Administrator
 */
public class MainActivity extends RxAppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onStart() {
        super.onStart();
        HttpMethods.getInstance().getService(IDoubanApi.class)
                .inTheaters()
                .compose(this.<HttpResult<Subjects>>bindToLifecycle())
                .map(RxUtil.<Subjects>handleHttpResult())
                .compose(RxUtil.<Subjects>normalSchedulers())
                .subscribe(new RxCallback<Subjects>() {
                    @Override
                    public void onSuccess(Subjects subjects) {
                        Log.e("MainActivity", "------------onSuccess");
                    }

                    @Override
                    public void onFinished() {
                        Log.e("MainActivity", "------------onFinished");
                    }
                });
    }

}
