package com.jm.greedysnake.util;

import android.content.Context;

import org.litepal.LitePal;
import org.litepal.LitePalApplication;

/**
 * Created by Johnny on 1/21/2018.
 */

public class ApplicationUtil extends LitePalApplication {
    public static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        LitePal.initialize(context);
        LitePal.getDatabase();
    }

    public static Context getContext() {
        return context;
    }
}
