package com.db.com.latte_core.app;

import android.content.Context;

import java.util.HashMap;
import java.util.WeakHashMap;

public final class Latte {
    public static Configurator init(Context context){
        getConfigurations().put(ConfigType.APPLICATION_CONTEXT,context.getApplicationContext());
        return Configurator.getInstance();
    }

    public static HashMap<Object,Object> getConfigurations(){
        return Configurator.getInstance().getLatteConfigs();
    }
    public static Configurator getConfigurator() {
        return Configurator.getInstance();
    }
    public static <T> T getConfiguration(Object key) {
        return getConfigurator().getConfiguration(key);
    }


    public static Context getApplication(){
        return (Context) getConfigurations().get(ConfigType.APPLICATION_CONTEXT);
    }
}
