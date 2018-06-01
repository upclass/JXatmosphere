package net.univr.pushi.jxatmosphere.utils;

import android.app.Activity;
import android.content.Context;

/**
 * author : Administrator wl
 * e-mail : 389456264@qq.com
 * time   : 2018/05/28
 * desc   :
 * version: 1.0
 */


public class GetResourceInt {

    public static int getResource(String imageName,Context context) {
        Context ctx = ((Activity) context).getBaseContext();
        String packageName = ctx.getPackageName();
        int resId = context.getResources().getIdentifier(imageName, "drawable", packageName);
        //如果没有在"mipmap"下找到imageName,将会返回0
        return resId;
    }

    public static int getResource(String imageName,Context context,String type) {
        Context ctx = ((Activity) context).getBaseContext();
        int resId = context.getResources().getIdentifier(imageName, type, ctx.getPackageName());
        //如果没有在"mipmap"下找到imageName,将会返回0
        return resId;
    }
}
