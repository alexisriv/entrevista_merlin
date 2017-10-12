package com.sixelasavir.prueba.entrevista;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.gson.Gson;
import com.sixelasavir.prueba.entrevista.retrofit.interfaces.ICategory;
import com.sixelasavir.prueba.entrevista.retrofit.model.category.Response;
import com.sixelasavir.prueba.entrevista.retrofit.util.BaseService;

import retrofit2.Call;
import retrofit2.Callback;

public class SplashActivity extends AppCompatActivity {

    private static final String TAG = "SplashActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);


        BaseService.getInstance().getService(ICategory.class).listCategories().enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                try {
                    if(response.isSuccessful()){
                        String bodyString = new Gson().toJson(response.body());

                        nextCategoryActivity(bodyString);

                        Log.d("response", bodyString);

                    } else {
                        throw new Exception(Integer.toString(response.code()));
                    }
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<Response> call, Throwable t) {
                Toast.makeText(getApplicationContext(), R.string.msg_warning_off_line, Toast.LENGTH_LONG).show();
                Log.e(TAG, t.getMessage());
                nextCategoryActivity();
            }
        });
    }

    private void nextCategoryActivity(){
        this.nextCategoryActivity(null);
    }

    private void nextCategoryActivity(final String jsonString){

        new Thread() {
            @Override
            public void run() {
                Intent intent = intent = new Intent(SplashActivity.this,CategoryActivity.class);

                try{
                    if(jsonString!=null && !jsonString.isEmpty())
                        intent.putExtra(AppString.JSON_CATEGORY_STRING, jsonString);
                        // Todo: Toca buscar en cache si esta el json
                    else
                        Log.d(TAG, NOTIFICATION_SERVICE);
                    sleep(3000);
                }catch (Exception e) {
                    Log.e(TAG, e.getMessage());
                } finally {
                    startActivity(intent);
                    finish();
                }
            }
        }.start();


    }
}
