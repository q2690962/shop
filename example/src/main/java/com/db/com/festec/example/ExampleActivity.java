package com.db.com.festec.example;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.db.com.latte_core.activities.ProxyActivity;
import com.db.com.latte_core.delegates.LatteDelegate;

public class ExampleActivity extends ProxyActivity {



    @Override
    public LatteDelegate setRootDelegate() {
        return new ExampleDelegate();
    }
}
