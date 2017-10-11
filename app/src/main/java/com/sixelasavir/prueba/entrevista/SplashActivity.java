package com.sixelasavir.prueba.entrevista;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sixelasavir.prueba.entrevista.retrofit.interfaces.ICategory;
import com.sixelasavir.prueba.entrevista.retrofit.model.category.Response;
import com.sixelasavir.prueba.entrevista.retrofit.util.BaseService;

import retrofit2.Call;
import retrofit2.Callback;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        BaseService.getInstance().getRetrofit().create(ICategory.class).listCategories().enqueue(new Callback<Response>() {
        //BaseService.getInstance().getService(ICategory.class).listCategories().enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                try {
                    if(response.isSuccessful()){
                        Log.d("response", new Gson().toJson(response.body()));
                    } else {
                        throw new Exception(Integer.toString(response.code()));
                    }
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG);
                }

            }

            @Override
            public void onFailure(Call<Response> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG);
            }
        });
    }
}
