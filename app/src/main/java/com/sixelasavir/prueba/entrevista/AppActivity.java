package com.sixelasavir.prueba.entrevista;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.Explode;
import android.transition.Fade;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.sixelasavir.prueba.entrevista.retrofit.model.app.Children;
import com.sixelasavir.prueba.entrevista.retrofit.model.app.Response;

import java.util.ArrayList;
import java.util.List;

public class AppActivity extends AppCompatActivity implements AppAdapter.DetailListener {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private static final String TAG = "AppActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle bundle = getIntent().getExtras();
        List<Children> apps = new ArrayList<>();
        if (bundle != null) {
            Response response = new Gson().fromJson(bundle.getString(BundleString.JSON_APP_STRING), Response.class);
            apps = response.getDataListing().getChildren();
        } else {
            Toast.makeText(this, R.string.msg_warning_no_data, Toast.LENGTH_LONG).show();
        }

        recyclerView = (RecyclerView) findViewById(R.id.recycler_apps);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new AppAdapter(this, apps);
        recyclerView.setAdapter(adapter);

        setupTransition();
    }

    @Override
    public void executeListener(String path) {
        nextDetailActivity(path);
    }

    private void nextDetailActivity() {
        this.nextDetailActivity(null);
    }

    private void nextDetailActivity(String url) {

        ActivityOptions options = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            options = ActivityOptions.makeSceneTransitionAnimation(this);
        }
        Intent intent = new Intent(this, DetailActivity.class);

        try {
            if (url != null && !url.isEmpty())
                intent.putExtra(BundleString.URL_DETAIL_STRING, url);
            else
                Log.d(TAG, NOTIFICATION_SERVICE);

        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        } finally {

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP)
                startActivity(intent, options.toBundle());
            else
                startActivity(intent);
        }
    }

    private void setupTransition() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            Fade fadeEnter = new Fade();
            fadeEnter.setDuration(1000);
            getWindow().setEnterTransition(fadeEnter);

            Explode explodeExit = new Explode();
            explodeExit.setDuration(1000);
            getWindow().setExitTransition(explodeExit);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            finishAfterTransition();
        } else
            finish();

        return true;
    }
}
