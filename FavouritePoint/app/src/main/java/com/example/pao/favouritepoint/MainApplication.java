package com.example.pao.favouritepoint;

import com.activeandroid.ActiveAndroid;


/**
 * Created by Pao on 8/12/2015.
 */
public class MainApplication extends com.activeandroid.app.Application {

    @Override
    public void onCreate() {
        super.onCreate();
        ActiveAndroid.initialize(this);
    }
}
