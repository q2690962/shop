package com.db.com.latte_core.app;

import com.joanzapata.iconify.Icon;
import com.joanzapata.iconify.IconFontDescriptor;
import com.joanzapata.iconify.Iconify;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.WeakHashMap;

import okhttp3.Interceptor;

public class Configurator {
    private static final HashMap<Object,Object> LATTE_CONFIGS=new HashMap<>();
    private static final ArrayList<IconFontDescriptor>ICONS=new ArrayList<>();

    private static final ArrayList<Interceptor>INTERCEPTORS=new ArrayList<>();

    private Configurator(){
        LATTE_CONFIGS.put(ConfigType.CONFIG_READY.name(),false);

    }

    public static Configurator getInstance(){
        return Holder.INSTANCE;
    }
    public final HashMap<Object,Object>getLatteConfigs(){
        return LATTE_CONFIGS;
    }
    private static class Holder{
        private static final Configurator INSTANCE=new Configurator();

    }
    public final void configure(){
        initIcons();
        LATTE_CONFIGS.put(ConfigType.CONFIG_READY.name(),true);

    }

    public final Configurator withApiHost(String host){
        LATTE_CONFIGS.put(ConfigType.API_HOST.name(),host);
        return this;
    }

    public void initIcons(){
        if(ICONS.size()>0){
            final Iconify.IconifyInitializer initializer=Iconify.with(ICONS.get(0));
            for (int i=1;i<ICONS.size();i++){
                initializer.with(ICONS.get(i));
            }
        }
    }
    public final Configurator withIcon(IconFontDescriptor descriptor){
        ICONS.add(descriptor);
        return this;
    }

    private void checkConfiguration(){
        final boolean isReady=(boolean)LATTE_CONFIGS.get(ConfigType.CONFIG_READY.name());
        if(!isReady){
            throw new RuntimeException("is not ready");
        }

    }
    @SuppressWarnings("unchecked")
    public final  <T>T getConfiguration(Object key){
        checkConfiguration();
        final Object value = LATTE_CONFIGS.get(key);
        if (value == null) {
            throw new NullPointerException(key.toString() + " IS NULL");
        }
        return (T) LATTE_CONFIGS.get(key);
    }


    public final Configurator withInterceptor(Interceptor interceptor){
        INTERCEPTORS.add(interceptor);
        LATTE_CONFIGS.put(ConfigType.INTERCEPTOR,INTERCEPTORS);
        return this;

    }
    public final Configurator withInterceptors(ArrayList<Interceptor>  interceptors){
        INTERCEPTORS.addAll(interceptors);
        LATTE_CONFIGS.put(ConfigType.INTERCEPTOR,INTERCEPTORS);
        return this;

    }

}
