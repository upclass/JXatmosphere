package net.univr.pushi.jxatmosphere.interfaces;

import android.graphics.drawable.Drawable;

/**
 * author : Administrator wl
 * e-mail : 389456264@qq.com
 * time   : 2018/06/13
 * desc   :
 * version: 1.0
 */


public class CallBackUtil {
    public static DispatchDrawable dispatchDrawable;

    public static void setDispatchDrawable(DispatchDrawable dispatchDrawable1) {
        dispatchDrawable = dispatchDrawable1;
    }

    public static void doDispatch(Drawable drawable) {
        dispatchDrawable.onDispatchBitmap(drawable);
    }
}
