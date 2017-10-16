package com.sixelasavir.prueba.entrevista;

import android.app.ActivityOptions;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.Slide;
import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;

import com.google.gson.Gson;
import com.sixelasavir.prueba.entrevista.retrofit.interfaces.IApp;
import com.sixelasavir.prueba.entrevista.retrofit.model.category.Children;
import com.sixelasavir.prueba.entrevista.retrofit.model.category.Response;
import com.sixelasavir.prueba.entrevista.retrofit.util.BaseService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class CategoryActivity extends AppCompatActivity implements CategoryAdapter.GoListener {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private static final String TAG = "CategoryActivity";

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        progressDialog = new ProgressDialog(this);
        progressDialog.setIcon(R.mipmap.ic_reddit);
        progressDialog.setMessage(getResources().getString(R.string.msg_info_wait));
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        Bundle bundle = getIntent().getExtras();
        List<Children> categories = new ArrayList<>();
        if (bundle != null) {
            Response response = new Gson().fromJson(bundle.getString(BundleString.JSON_CATEGORY_STRING), Response.class);
            categories = response.getDataListing().getChildren();
        } else {
            Toast.makeText(this, R.string.msg_warning_no_data, Toast.LENGTH_LONG).show();
        }

        recyclerView = (RecyclerView) findViewById(R.id.categories);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new CategoryAdapter(this, categories);
        recyclerView.setAdapter(adapter);

        setupTransition();
    }

    @Override
    public void executeListener(String path) {
        progressDialog.show();
        BaseService.getInstance().getService(IApp.class).listApps(path).enqueue(new Callback<com.sixelasavir.prueba.entrevista.retrofit.model.app.Response>() {
            @Override
            public void onResponse(Call<com.sixelasavir.prueba.entrevista.retrofit.model.app.Response> call, retrofit2.Response<com.sixelasavir.prueba.entrevista.retrofit.model.app.Response> response) {
                try {
                    if (response.isSuccessful()) {
                        String bodyString = new Gson().toJson(response.body());
                        nextAppActivity(bodyString);
                        Log.d("response", bodyString);
                    } else {
                        throw new Exception(Integer.toString(response.code()));
                    }
                } catch (Exception e) {
                    if (progressDialog.isShowing())
                        progressDialog.dismiss();

                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<com.sixelasavir.prueba.entrevista.retrofit.model.app.Response> call, Throwable t) {
                Toast.makeText(getApplicationContext(), R.string.msg_warning_off_line, Toast.LENGTH_LONG).show();
                Log.e(TAG, t.getMessage());
                nextAppActivity();
            }
        });
    }

    private void nextAppActivity() {
        this.nextAppActivity(null);
    }

    private void nextAppActivity(String jsonString) {

        ActivityOptions options = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
             options = ActivityOptions.makeSceneTransitionAnimation(this);
        }
        Intent intent = new Intent(this, AppActivity.class);

        try {
            if (jsonString != null && !jsonString.isEmpty())
                intent.putExtra(BundleString.JSON_APP_STRING, jsonString);
                // Todo: Toca buscar en cache si esta el json
            else
                Log.d(TAG, NOTIFICATION_SERVICE);

        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        } finally {
            if (progressDialog.isShowing())
                progressDialog.dismiss();

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP)
                startActivity(intent, options.toBundle());
            else
                startActivity(intent);
        }
    }


    private void setupTransition() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            Slide slideEnter = new Slide();
            slideEnter.setSlideEdge(Gravity.RIGHT);
            slideEnter.setDuration(1000);
            getWindow().setEnterTransition(slideEnter);

            Slide slideExit = new Slide();
            slideExit.setSlideEdge(Gravity.LEFT);
            slideExit.setDuration(1000);
            getWindow().setExitTransition(slideExit);
        }
    }

}
