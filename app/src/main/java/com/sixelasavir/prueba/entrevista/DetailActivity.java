package com.sixelasavir.prueba.entrevista;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class DetailActivity extends AppCompatActivity {

    private WebView webView;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Bundle bundle = getIntent().getExtras();

        if(bundle != null) {
            progressDialog = new ProgressDialog(this);
            progressDialog.setIcon(R.mipmap.ic_reddit);
            progressDialog.setMessage(getResources().getString(R.string.msg_info_wait));
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);

            progressDialog.show();
            webView = (WebView) findViewById(R.id.webview_detail);
            loadWebView(bundle.getString(BundleString.URL_DETAIL_STRING));
        }else
            Toast.makeText(this, R.string.msg_warning_no_data, Toast.LENGTH_SHORT).show();
    }

    public void loadWebView(String url){
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return false;
            }


            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                if (progressDialog.isShowing())
                    progressDialog.dismiss();
            }
        });
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(url);
    }
}
