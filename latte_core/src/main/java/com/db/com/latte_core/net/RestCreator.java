package com.db.com.latte_core.net;

import com.db.com.latte_core.app.ConfigType;
import com.db.com.latte_core.app.Latte;
import com.db.com.latte_core.net.rx.RxRestService;

import java.util.ArrayList;
import java.util.WeakHashMap;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class RestCreator {

    private static final class ParamsHolder {
        public static final WeakHashMap<String, Object> PAARAMS = new WeakHashMap<>();

    }

    public static WeakHashMap<String, Object> getParams() {
        return ParamsHolder.PAARAMS;
    }


    public static RestService getRestService() {
        return RestServiecHolder.REST_SERVICE;
    }


    private static final class RetrofitHolder {
        private static final String BASE_URL = (String) Latte.getConfigurations().get(ConfigType.API_HOST.name());
        private static final Retrofit RETROFIT_CLIENT = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(OKHttpHolder.OK_HTTP_CLIENT)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

    }

    private static final class OKHttpHolder {

        private static final int TIME_OUT = 60;
        private static final ArrayList<Interceptor> INTERCEPTORS = Latte.getConfiguration(ConfigType.INTERCEPTOR);
        private static final OkHttpClient.Builder BUILDER = new OkHttpClient.Builder();

        private static OkHttpClient.Builder addInterceptor() {
            if (INTERCEPTORS != null && !INTERCEPTORS.isEmpty()) {
                for (Interceptor interceprot : INTERCEPTORS) {
                    BUILDER.addInterceptor(interceprot);
                }
            }
            return BUILDER;
        }


        private static final OkHttpClient OK_HTTP_CLIENT = addInterceptor()
                .connectTimeout(TIME_OUT, TimeUnit.SECONDS)

                .build();
    }

    private static final class RestServiecHolder {
        private static final RestService REST_SERVICE =
                RetrofitHolder.RETROFIT_CLIENT.create(RestService.class);

    }

    private static final class RxRestServiecHolder {
        private static final RxRestService REST_SERVICE =
                RetrofitHolder.RETROFIT_CLIENT.create(RxRestService.class);

    }
    public static RxRestService getRxRestService() {
        return RxRestServiecHolder.REST_SERVICE;
    }

}
