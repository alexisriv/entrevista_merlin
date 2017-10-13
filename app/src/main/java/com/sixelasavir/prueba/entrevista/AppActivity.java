package com.sixelasavir.prueba.entrevista;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
        Toast.makeText(this, getResources().getString(R.string.msg_info_in_construction).concat(" ").concat(path),Toast.LENGTH_SHORT).show();
    }
}
