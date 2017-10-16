package com.sixelasavir.prueba.entrevista;

import android.app.ActivityOptions;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.transition.Explode;
import android.transition.Fade;
import android.transition.Slide;
import android.util.Log;
import android.view.Gravity;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.gson.Gson;
import com.sixelasavir.prueba.entrevista.retrofit.interfaces.ICategory;
import com.sixelasavir.prueba.entrevista.retrofit.model.category.Response;
import com.sixelasavir.prueba.entrevista.retrofit.util.BaseService;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

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
                    if (response.isSuccessful()) {
                        /*for(Children ctg: response.body().getDataListing().getChildren()) {
                            ctg.getDataChildren().setBannerBitmap(new ImageTask(ctg.getDataChildren().getBannerImg()).execute().get());
                            ctg.getDataChildren().setIconBitmap(new ImageTask(ctg.getDataChildren().getIconImg()).execute().get());
                            ctg.getDataChildren().setHeaderBitmap(new ImageTask(ctg.getDataChildren().getHeaderImg()).execute().get());
                            Log.d(TAG.concat("For"), ctg.getDataChildren().getTitle());
                        }*/
                        String bodyString = new Gson().toJson(response.body());

                        SharedPreferences.Editor editor = ((ApplicationApp) getApplicationContext()).getSharedPreferences().edit();
                        editor.putString(BundleString.SP_JSON_CATEGORY_STRING, bodyString);
                        editor.commit();
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
                String bodyString = ((ApplicationApp) getApplicationContext()).getSharedPreferences().getString(BundleString.SP_JSON_CATEGORY_STRING,"");

                if(bodyString.isEmpty())
                    nextCategoryActivity();
                else
                    nextCategoryActivity(bodyString);
            }
        });

    }

    private void nextCategoryActivity() {
        this.nextCategoryActivity(null);
    }

    private void nextCategoryActivity(final String jsonString) {

        ActivityOptions options = null;


        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            options = ActivityOptions.makeSceneTransitionAnimation(SplashActivity.this);
        }
        final Intent intent = new Intent(SplashActivity.this, CategoryActivity.class);

        try {
            if (jsonString != null && !jsonString.isEmpty())
                intent.putExtra(BundleString.JSON_CATEGORY_STRING, jsonString);
                // Todo: Toca buscar en cache si esta el json
            else
                Log.d(TAG, NOTIFICATION_SERVICE);

        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        } finally {
            final ActivityOptions finalOptions = options;
            new Thread() {
                @Override
                public void run() {
                    try {
                        sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                        startActivity(intent, finalOptions.toBundle());
                    } else {
                        startActivity(intent);
                    }
                    finish();

                }}.start();


        }
    }


    public class ImageTask extends AsyncTask<Void, Void, Bitmap> {

        private String url;

        public ImageTask(String url) {
            this.url = url;
        }

        @Override
        protected Bitmap doInBackground(Void... params) {
            try {
                Bitmap bitmap;
                URL urlConnection = new URL(url);
                HttpURLConnection connection = (HttpURLConnection) urlConnection.openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream inputStream = connection.getInputStream();
                bitmap = BitmapFactory.decodeStream(inputStream);
                return bitmap;
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }
    }

}
