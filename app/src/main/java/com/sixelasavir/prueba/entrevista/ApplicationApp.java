package com.sixelasavir.prueba.entrevista;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by alexis on 15/10/17.
 */

public class ApplicationApp extends Application{

    private Context context;
    private SharedPreferences sharedPreferences;
    private static final String KEY_SHARED_PREFERENCES = "k.s.p";
    public ApplicationApp() {
        }

    @Override
    public void onCreate() {
        super.onCreate();
        this.context = getApplicationContext();
        this.sharedPreferences = getSharedPreferences(KEY_SHARED_PREFERENCES, Context.MODE_PRIVATE);

    }

    public Context getContext() {
        return context;
    }

    public SharedPreferences getSharedPreferences() {
        return sharedPreferences;
    }

}
