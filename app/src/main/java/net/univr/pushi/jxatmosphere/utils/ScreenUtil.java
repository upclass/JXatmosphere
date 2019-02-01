package net.univr.pushi.jxatmosphere.utils;

import android.content.Context;

/**
 * @author：byd666 on 2017/12/2 15:39
 */

public class ScreenUtil {

    public static int dip2px(Context context, float dipValue){
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int)(dipValue * scale + 0.5f);
    }
}
