package com.example.newsappusingtab.screen;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.newsappusingtab.R;
import com.example.newsappusingtab.databinding.ActivityWebViewBinding;


public class WebViewActivity extends AppCompatActivity {

    WebView webView;
    String loadUrl;
    ActivityWebViewBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_web_view);


        try {
            webView = new WebView(this);
        } catch (Exception e) {
            webView = new WebView(getApplicationContext());
        }
        setupWebView();
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        params.addRule(RelativeLayout.BELOW, R.id.toolbar);
        binding.root.addView(webView, params);
        loadUrl = getIntent().getStringExtra("loadUrl");
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(loadUrl);

    }

    private void setupWebView() {
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setSupportZoom(false);
        webView.setWebChromeClient(new WebChromeClient());
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                binding.progress.setVisibility(View.VISIBLE);
            }

            @Override
            public void onLoadResource(WebView view, String url) {
                super.onLoadResource(view, url);
                binding.progress.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                binding.progress.setVisibility(View.GONE);
                invokeJs();
            }

        });

    }
    private void invokeJs() {
        try {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
                webView.evaluateJavascript("prepareForWebView();", null);
            } else {
                webView.loadUrl("javascript:prepareForWebView();");
            }
        } catch (Exception e) {
            Log.e("EXP", e.getMessage(), e);
        }
        webView.postDelayed(new Runnable() {
            @Override
            public void run() {
                webView.setVisibility(View.VISIBLE);
            }
        }, 50);
    }

    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();
        }
    }
}