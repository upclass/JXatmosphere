package net.univr.pushi.jxatmosphere.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.webkit.WebView;

/**
 * author : Administrator wl
 * e-mail : 389456264@qq.com
 * time   : 2018/06/19
 * desc   :
 * version: 1.0
 */


public class NoScrollWebView extends WebView {
    public NoScrollWebView(Context context) {
        super(context);
    }

    public NoScrollWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NoScrollWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public NoScrollWebView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    //禁止滑动
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return false;
    }
}
