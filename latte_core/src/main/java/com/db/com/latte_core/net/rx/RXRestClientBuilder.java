package com.db.com.latte_core.net.rx;

import android.content.Context;

import com.db.com.latte_core.net.RestClient;
import com.db.com.latte_core.net.RestCreator;
import com.db.com.latte_core.net.callback.IError;
import com.db.com.latte_core.net.callback.IFailure;
import com.db.com.latte_core.net.callback.IRequest;
import com.db.com.latte_core.net.callback.ISuccess;
import com.db.com.latte_core.ui.LoaderStyle;

import java.io.File;
import java.util.Map;
import java.util.WeakHashMap;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class RXRestClientBuilder {
    private String mUrl;
    private static final Map<String, Object> PARAMS = RestCreator.getParams();

    private RequestBody mBody;
    private Context mContext;
    private LoaderStyle mLoaderStyle;
    private File mFile;

    RXRestClientBuilder() {
    }

    public final RXRestClientBuilder url(String url) {
        this.mUrl = url;
        return this;
    }

    public final RXRestClientBuilder params(WeakHashMap<String, Object> params) {
        PARAMS.putAll(params);
        return this;
    }

    public final RXRestClientBuilder file(File file) {

        mFile = file;
        return this;
    }

    public final RXRestClientBuilder file(String file) {

        mFile = new File(file);
        return this;
    }

    public final RXRestClientBuilder params(String key, Object value) {

        PARAMS.put(key, value);
        return this;
    }

    public final RXRestClientBuilder raw(String raw) {
        this.mBody = RequestBody.create(MediaType.parse("application/json;charset=UTF-8"), raw);
        return this;
    }


    public final RXRestClientBuilder loader(Context context, LoaderStyle loaderStyle) {
        this.mContext = context;
        this.mLoaderStyle = loaderStyle;
        return this;
    }

    public final RXRestClientBuilder loader(Context context) {
        this.mContext = context;
        this.mLoaderStyle = LoaderStyle.BallClipRotatePulseIndicator;
        return this;
    }


    public final RxRestClient build() {
        return new RxRestClient(mUrl, PARAMS
                , mBody, mFile, mContext, mLoaderStyle);
    }
}
