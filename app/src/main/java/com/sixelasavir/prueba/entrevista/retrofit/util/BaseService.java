package com.sixelasavir.prueba.entrevista.retrofit.util;

import retrofit2.Retrofit;

/**
 * Created by arivas on 11/10/2017.
 */
public class BaseService {

    private static final  String URL = "https://www.reddit.com";
    private static BaseService ourInstance = new BaseService();

    private Retrofit retrofit;

    public static BaseService getInstance() {
        return ourInstance;
    }

    private BaseService() {
        retrofit = new Retrofit.Builder().baseUrl(URL).build();
    }

    public Retrofit getRetrofit() {
        return retrofit;
    }

    public <T> T getService(Class<T> tClass){
        return retrofit.create(tClass);
    }
}
