package com.sixelasavir.prueba.entrevista;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getResources().getString(R.string.msg_info_wait));
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);

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
    }

    @Override
    public void executeListener(String path) {
        //Toast.makeText(this, getResources().getString(R.string.msg_info_in_construction).concat(" ").concat(path), Toast.LENGTH_SHORT).show();
        nextDetailActivity(path);
    }

    private void nextDetailActivity() {
        this.nextDetailActivity(null);
    }

    private void nextDetailActivity(String url) {

        Intent intent = new Intent(this, DetailActivity.class);

        try {
            if (url != null && !url.isEmpty())
                intent.putExtra(BundleString.URL_DETAIL_STRING, url);
            else
                Log.d(TAG, NOTIFICATION_SERVICE);

        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        } finally {
            if (progressDialog.isShowing())
                progressDialog.dismiss();

            startActivity(intent);
        }
    }
}
