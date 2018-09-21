package net.univr.pushi.jxatmosphere.feature;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.Picture;
import android.os.Build;
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

public class HelpActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.webview)
    WebView webView;
    @BindView(R.id.back)
    ImageView back;
    //    @BindView(R.id.share_to)
//    ImageView share_to;
    ProgressDialog progressDialog = null;
//    @BindView(R.id.loadImg)
//    ImageView loadImg;


    @Override
    public int getLayoutId() {
        return R.layout.activity_help;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        super.initViews(savedInstanceState);
        initView();
    }

    private void initView() {
        back.setOnClickListener(this);
//        share_to.setOnClickListener(this);
        webView.getSettings().setJavaScriptEnabled(true);
        WebSettings settings = webView.getSettings();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.TEXT_AUTOSIZING);
        }
        //      自适应屏幕
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);

//              设置可以支持缩放
//        settings.setSupportZoom(true);
        settings.setDefaultFontSize(40);
//        webView.setInitialScale(200);
//      设置出现缩放工具
//        settings.setBuiltInZoomControls(true);

        Map<String, String> map = new HashMap<String, String>();
        map.put("User-Agent", "Android");
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return false;
            }

            @Override
            public void onReceivedError(WebView view, int errorCode,
                                        String description, String failingUrl) {
                //网页加载失败的处理，一般是出错图片，跳转到出错处理页面
                progressDialog.dismiss();
                super.onReceivedError(view, errorCode, description, failingUrl);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                progressDialog.dismiss();
                webView.setVisibility(View.VISIBLE);
                super.onPageFinished(view, url);
                //网页加载结束的处理，可以停止动画

            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {

                super.onPageFinished(view, url);
                progressDialog = ProgressDialog.show(context, "请稍等...", "获取数据中...", true);
                progressDialog.setCancelable(true);
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

        //加载assets文件夹下的html
//        webView.loadUrl("file:///android_asset/html/index.html");
        //加载网络请求的html
//        webView.loadUrl(API_BASE_URL + "page/help.html", map);
        webView.loadUrl("http://192.168.0.140:8088/upload/37_农气气象/问题讨论列表.html", map);
//        webView.loadUrl("http://111.75.199.107:8080/upload/37_农气气象/问题讨论列表.html", map);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
//            case R.id.share_to:
//                OnekeyShare oks = new OnekeyShare();
//                //关闭sso授权
//                oks.disableSSOWhenAuthorize();
//
//                // title标题，微信、QQ和QQ空间等平台使用
//                oks.setTitle(getString(R.string.sharetest));
//                // titleUrl QQ和QQ空间跳转链接
//                oks.setTitleUrl("http://sharesdk.cn");
//                // text是分享文本，所有平台都需要这个字段
//                oks.setText("我是分享文本");
//                // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
//                oks.setImagePath("/sdcard/popup_feedback_layout.jpg");//确保SDcard下面存在此张图片
//                // url在微信、微博，Facebook等平台中使用
//                oks.setUrl("http://sharesdk.cn");
//                // comment是我对这条分享的评论，仅在人人网使用
//                oks.setComment("我是测试评论文本");
//                // 启动分享GUI
//                oks.show(this);
//                break;
            case R.id.back:
                finish();
                break;

        }
    }


}
