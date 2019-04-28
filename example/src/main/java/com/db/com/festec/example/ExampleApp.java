package com.db.com.festec.example;

import android.app.Application;

import com.db.com.latte_core.app.Latte;
import com.db.com.latte_core.net.interceptors.DebugInterceptor;
import com.joanzapata.iconify.fonts.FontAwesomeModule;

public class ExampleApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Latte.init(this)
                .withIcon(new FontAwesomeModule())
                .withApiHost("http://127.0.0.1/")
                .withInterceptor(new DebugInterceptor("index",R.raw.test))
                .configure();
    }
}
