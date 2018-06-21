package net.univr.pushi.jxatmosphere.interfaces;

/**
 * author : Administrator wl
 * e-mail : 389456264@qq.com
 * time   : 2018/06/13
 * desc   :
 * version: 1.0
 */


public class CallBackUtil {
    public static BrightnessActivity brightness;
    public static Picdispath picdispath;

    public static void setBrightness(BrightnessActivity brightness1) {
        brightness = brightness1;
    }
    public static void setPicdispath(Picdispath picdispath1) {
        picdispath = picdispath1;
    }

    public static void doDispatchDarken() {
        brightness.onDispatchDarken();
    }
    public static void doDispatchLight() {
        brightness.onDispatchLight();
    }


    public static void doDispathPic(int  position) {
        picdispath.onDispatchPic(position);
    }
}
