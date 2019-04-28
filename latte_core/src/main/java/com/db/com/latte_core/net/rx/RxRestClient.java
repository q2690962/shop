package com.db.com.latte_core.net.rx;

import android.content.Context;

import com.db.com.latte_core.net.HttpMethod;
import com.db.com.latte_core.net.RestClientBuilder;
import com.db.com.latte_core.net.RestCreator;
import com.db.com.latte_core.net.RestService;
import com.db.com.latte_core.net.callback.IError;
import com.db.com.latte_core.net.callback.IFailure;
import com.db.com.latte_core.net.callback.IRequest;
import com.db.com.latte_core.net.callback.ISuccess;
import com.db.com.latte_core.net.callback.RequestCallbacks;
import com.db.com.latte_core.net.download.DownloadHandler;
import com.db.com.latte_core.ui.LatteLoader;
import com.db.com.latte_core.ui.LoaderStyle;

import java.io.File;
import java.util.Map;
import java.util.WeakHashMap;

import io.reactivex.Observable;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;

public class RxRestClient {
    private final String URL;
    private static final WeakHashMap<String, Object> PARAMS = RestCreator.getParams();

    private final RequestBody BODY;
    private final LoaderStyle LOADER_STYLE;
    private final Context Context;
    private final File FILE;
    //


    public RxRestClient(String url, Map<String, Object> params, RequestBody body
            ,File file, Context context, LoaderStyle loaderStyle
            ) {
        URL = url;
        PARAMS.putAll(params);

        BODY = body;
        LOADER_STYLE = loaderStyle;
        Context = context;
        FILE=file;

    }

    public static RXRestClientBuilder builder() {
        return new RXRestClientBuilder();

    }
    //

    private Observable<String> request(HttpMethod method) {
        final RxRestService service = RestCreator.getRxRestService();
        Observable<String> observable=null;


        if (LOADER_STYLE != null) {
            LatteLoader.showLoading(Context, LOADER_STYLE);
        }

        switch (method) {
            case GET:
                observable = service.get(URL, PARAMS);
                break;
            case POST:
                observable = service.post(URL, PARAMS);
                break;
            case POST_RAW:
                observable = service.postRaw(URL, BODY);
                break;
            case PUT:
                observable = service.put(URL, PARAMS);
                break;
            case PUT_RAW:
                observable = service.putRaw(URL, BODY);
                break;
            case DELETE:
                observable = service.delete(URL, PARAMS);
                break;
            case UPLOAD:
                final RequestBody requestBody = RequestBody
                        .create(MediaType.parse(MultipartBody.FORM.toString()), FILE);
                final MultipartBody.Part body = MultipartBody.Part.createFormData("file", FILE.getName(), requestBody);
                observable = service.upload(URL, body);

                break;
            default:
                break;

        }
        return observable;

    }



    public final Observable<String> get() {

       return request((HttpMethod.GET));
    }

    public final Observable<String> post() {

        if (BODY == null) {
            return  request((HttpMethod.POST));
        } else {
            if (!PARAMS.isEmpty()) {
                throw new RuntimeException("params must be null");
            }
            return   request(HttpMethod.POST_RAW);
        }

    }

    public final Observable<String> put() {
        if (BODY == null) {
            return  request((HttpMethod.PUT));
        } else {
            if (!PARAMS.isEmpty()) {
                throw new RuntimeException("params must be null");
            }
            return  request(HttpMethod.PUT_RAW);
        }
    }

    public final Observable<String> delete() {

        return  request((HttpMethod.DELETE));
    }

    public final Observable<String> upload() {

        return request(HttpMethod.UPLOAD);
    }

    public final Observable<ResponseBody> download() {

        Observable<ResponseBody>responseBodyObservable=RestCreator.getRxRestService()
                .download(URL,PARAMS);
        return responseBodyObservable;
    }

}
