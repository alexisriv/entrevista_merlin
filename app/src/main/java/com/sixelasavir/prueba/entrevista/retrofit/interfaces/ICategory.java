package com.sixelasavir.prueba.entrevista.retrofit.interfaces;

import com.sixelasavir.prueba.entrevista.retrofit.model.category.Response;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by arivas on 11/10/2017.
 */

public interface ICategory {

    @GET("/reddits.json")
    Call<Response> listCategories();
}
