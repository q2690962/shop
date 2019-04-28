package com.db.com.festec.example;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Toast;

import com.db.com.latte_core.delegates.LatteDelegate;
import com.db.com.latte_core.net.RestClient;
import com.db.com.latte_core.net.RestCreator;
import com.db.com.latte_core.net.callback.IError;
import com.db.com.latte_core.net.callback.IFailure;
import com.db.com.latte_core.net.callback.ISuccess;
import com.db.com.latte_core.net.rx.RxRestClient;

import java.util.HashMap;
import java.util.WeakHashMap;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ExampleDelegate extends LatteDelegate {

    @Override
    public Object setLayout() {
        return R.layout.delegate_example;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootview) {
        testRestClient();
    }


    private void testRestClient() {
        RestClient.builder()
                .url("http://127.0.0.1/index")
                .loader(getContext())
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        Toast.makeText(getContext(),response,Toast.LENGTH_SHORT).show();
                    }
                })
                .params("", "")
                .failure(new IFailure() {
                    @Override
                    public void onFailure() {

                    }
                })
                .error(new IError() {
                    @Override
                    public void onError(int code, String msg) {

                    }
                })
                .build()
                .get();
        //
        String url="http://127.0.0.1/index";
        WeakHashMap<String,Object> params=new WeakHashMap<>();
        Observable<String> observable=RestCreator.getRxRestService().get(url,params);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(String s) {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
        //
        RxRestClient.builder()
                .url(url)
                .build()
                .get()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(String s) {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });


    }
    //


   // RestCreator.g
//           observable.sub(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe(new Observer<String>() {
//                @Override
//                public void onSubscribe(Disposable d) {
//
//                }
//
//                @Override
//                public void onNext(String s) {
//
//                }
//
//                @Override
//                public void onError(Throwable e) {
//
//                }
//
//                @Override
//                public void onComplete() {
//
//                }
//            })

}
