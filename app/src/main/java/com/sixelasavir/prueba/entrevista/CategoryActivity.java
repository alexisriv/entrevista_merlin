package com.sixelasavir.prueba.entrevista;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.sixelasavir.prueba.entrevista.retrofit.model.category.Children;
import com.sixelasavir.prueba.entrevista.retrofit.model.category.Response;

import java.util.ArrayList;
import java.util.List;

public class  CategoryActivity extends AppCompatActivity implements CategoryAdapter.GoListener {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        Bundle bundle = getIntent().getExtras();
        List<Children> categories = new ArrayList<>();
        if(bundle != null) {
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
    }

    @Override
    public void executeListener() {

    }
}
