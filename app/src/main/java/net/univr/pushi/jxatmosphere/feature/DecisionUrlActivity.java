package net.univr.pushi.jxatmosphere.feature;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Picture;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import net.univr.pushi.jxatmosphere.R;
import net.univr.pushi.jxatmosphere.base.BaseActivity;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;

public class DecisionUrlActivity extends BaseActivity {
    @BindView(R.id.webview)
    WebView webView;
    String url;
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.reload)
    ImageView reload;
    Map<String, String> map = new HashMap<String, String>();


    @Override
    public int getLayoutId() {
        return R.layout.activity_decision_url;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        super.initViews(savedInstanceState);
        map.put("User-Agent", "Android");
        reload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                webView.loadUrl(url, map);
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        Intent intent = getIntent();
        url = intent.getStringExtra("url");
    }

    @Override
    protected void onResume() {
        super.onResume();
        initWebView();
    }

    private void initWebView() {
        WebSettings settings = webView.getSettings();
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        settings.setJavaScriptEnabled(true);
        settings.setDefaultFontSize(35);


        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return false;
            }

            @Override
            public void onReceivedError(WebView view, int errorCode,
                                        String description, String failingUrl) {
                //网页加载失败的处理，一般是出错图片，跳转到出错处理页面
                super.onReceivedError(view, errorCode, description, failingUrl);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                webView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageFinished(view, url);
                webView.setVisibility(View.INVISIBLE);
            }
        });

        webView.setPictureListener(new WebView.PictureListener() {
            @Override
            public void onNewPicture(WebView view, Picture picture) {
            }
        });
        //加载网络请求的html

        webView.loadUrl(url, map);
    }


}
