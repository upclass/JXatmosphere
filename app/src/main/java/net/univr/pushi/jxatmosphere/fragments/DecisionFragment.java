package net.univr.pushi.jxatmosphere.fragments;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.Picture;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;

import net.univr.pushi.jxatmosphere.R;
import net.univr.pushi.jxatmosphere.base.RxLazyFragment;
import net.univr.pushi.jxatmosphere.remote.RetrofitHelper;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class DecisionFragment extends RxLazyFragment {

    @BindView(R.id.webview)
    WebView webView;
    String type;
    ProgressDialog progressDialog = null;

    public DecisionFragment() {
    }


    public static DecisionFragment newInstance(String type) {
        DecisionFragment decisionFragment = new DecisionFragment();
        Bundle bundle = new Bundle();
        bundle.putString("type", type);
        decisionFragment.setArguments(bundle);
        return decisionFragment;
    }


    private void initWebView() {

        WebSettings settings = webView.getSettings();
        //      自适应屏幕
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        settings.setJavaScriptEnabled(true);

//              设置可以支持缩放
//        settings.setSupportZoom(true);
        settings.setDefaultFontSize(35);
//      设置出现缩放工具
//        settings.setBuiltInZoomControls(true);


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
                super.onPageFinished(view, url);
                //网页加载结束的处理，可以停止动画

            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {

                super.onPageFinished(view, url);
                webView.setVisibility(View.INVISIBLE);
                //网页加载开始的处理，开始动画
            }
        });

        webView.setPictureListener(new WebView.PictureListener() {
            @Override
            public void onNewPicture(WebView view, Picture picture) {
                //移除动画或者删除背景图片
            }
        });

        //加载网络请求的html
    }


    @Override
    public int getLayoutResId() {
        return R.layout.fragment_decision;
    }

    @Override
    public void finishCreateView(Bundle state) {
        initWebView();
        if (getArguments() != null) {
            //取出保存的值
            type = getArguments().getString("type");
        }
        getTestdata();

    }


    private void getTestdata() {
        if (type.equals("qkfy")) {
            progressDialog = ProgressDialog.show(getActivity(), "请稍等...", "获取数据中...", true);
            progressDialog.setCancelable(true);
        }
        RetrofitHelper.getFeedbackAPI()
                .getDecision(type)
                .compose(bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(decisionBeen -> {
                    if (progressDialog != null) {
                        progressDialog.dismiss();
                    }
                    String url = decisionBeen.getData().getUrl();
                    Map<String, String> map = new HashMap<String, String>();
                    map.put("User-Agent", "Android");
                    webView.loadUrl(url, map);
                }, throwable -> {
                    if (progressDialog != null)
                        progressDialog.dismiss();
                    LogUtils.e(throwable);
                    ToastUtils.showShort(getString(R.string.getInfo_error_toast));
                });
    }


}
