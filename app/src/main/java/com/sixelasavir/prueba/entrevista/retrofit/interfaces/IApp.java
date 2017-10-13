package com.sixelasavir.prueba.entrevista.retrofit.interfaces;

import com.sixelasavir.prueba.entrevista.retrofit.model.app.Response;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by alexis on 12/10/17.
 */

public interface IApp {

    @GET("{path}.json")
    Call<Response> listApps(@Path("path") String path);
}
