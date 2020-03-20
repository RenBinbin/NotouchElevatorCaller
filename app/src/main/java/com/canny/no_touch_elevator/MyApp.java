package com.canny.no_touch_elevator;

import android.app.Application;
import com.canny.no_touch_elevator.util.SharedPrefOP;
import com.lzy.okgo.OkGo;

/**
 * Created by 1006177 on 2020/2/17.
 */

public class MyApp extends Application {
    public static MyApp application;
    public static MyApp getApplication() {
        return application;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        application=this;
        OkGo.getInstance().init(this);

        //init our shared preference file OP
        SharedPrefOP.init(getApplicationContext());

        CrashHandler crashHandler = new CrashHandler(this);
        crashHandler.init(getApplicationContext());
    }
}
