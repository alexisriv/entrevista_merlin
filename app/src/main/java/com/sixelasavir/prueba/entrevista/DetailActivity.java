package com.sixelasavir.prueba.entrevista;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class DetailActivity extends AppCompatActivity {

    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Bundle bundle = getIntent().getExtras();

        if(bundle != null) {
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
        });
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(url);
    }
}
