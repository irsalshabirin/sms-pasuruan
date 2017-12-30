package co.dust.smspasuruan.tools;

import android.app.Application;

import net.danlew.android.joda.JodaTimeAndroid;

/**
 * Created by irsal on 7/18/17.
 */

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        JodaTimeAndroid.init(this);
    }
}
