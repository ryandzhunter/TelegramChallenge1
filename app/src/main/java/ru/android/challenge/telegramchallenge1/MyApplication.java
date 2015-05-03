package ru.android.challenge.telegramchallenge1;

import android.app.Application;
import android.content.Context;

/**
 * Created by Nick.
 */
public class MyApplication extends Application {
    public static Context instance;


    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }
}
