package com.db.com.latte_core.net;

import android.content.Context;

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

public class RestClientBuilder {
    private String mUrl;
    private static final Map<String, Object> PARAMS=RestCreator.getParams();
    private IRequest mIRequest;
    private ISuccess mISuccess;
    private IFailure mIFailure;
    private IError mIError;
    private RequestBody mBody;
    private Context mContext;
    private LoaderStyle mLoaderStyle;
    private File mFile;
    private String mDownloadDir = null;
    private String mExtension = null;
    private String mName = null;
    RestClientBuilder() {
    }

    public final RestClientBuilder url(String url) {
        this.mUrl = url;
        return this;
    }

    public final RestClientBuilder params(WeakHashMap<String, Object> params) {
        PARAMS.putAll(params);
        return this;
    }

    public final RestClientBuilder file(File file) {

        mFile=file;
        return this;
    }
    public final RestClientBuilder file(String file) {

        mFile=new File(file);
        return this;
    }

    public final RestClientBuilder params(String key, Object value) {

        PARAMS.put(key, value);
        return this;
    }

    public final RestClientBuilder raw(String raw) {
        this.mBody = RequestBody.create(MediaType.parse("application/json;charset=UTF-8"), raw);
        return this;
    }

    public final RestClientBuilder onRequest(IRequest iRequest) {
        this.mIRequest=iRequest;
        return this;
    }

    public final RestClientBuilder success(ISuccess iSuccess) {
        this.mISuccess=iSuccess;
        return this;
    }
    public final RestClientBuilder failure(IFailure iFailure) {
        this.mIFailure=iFailure;
        return this;
    }
    public final RestClientBuilder error(IError iError) {
        this.mIError=iError;
        return this;
    }

    public final RestClientBuilder loader(Context context,LoaderStyle loaderStyle) {
        this.mContext=context;
        this.mLoaderStyle=loaderStyle;
        return this;
    }

    public final RestClientBuilder loader(Context context) {
        this.mContext=context;
        this.mLoaderStyle=LoaderStyle.BallClipRotatePulseIndicator;
        return this;
    }


    public final RestClientBuilder name(String name) {
        this.mName = name;
        return this;
    }

    public final RestClientBuilder dir(String dir) {
        this.mDownloadDir = dir;
        return this;
    }

    public final RestClientBuilder extension(String extension) {
        this.mExtension = extension;
        return this;
    }
    public final RestClient build(){
        return new RestClient(mUrl,PARAMS,mIRequest,mISuccess,mIFailure,mIError
                ,mBody,mContext,mLoaderStyle,mFile,mDownloadDir,mExtension,mName);
    }
}
