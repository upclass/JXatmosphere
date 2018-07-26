package net.univr.pushi.jxatmosphere.utils;

import android.content.Context;
import android.graphics.Bitmap;

import com.facebook.stetho.common.LogUtil;
import com.squareup.picasso.Transformation;

/**
 * author : Administrator wl
 * e-mail : 389456264@qq.com
 * time   : 2018/07/25
 * desc   :
 * version: 1.0
 */


public class PicassoTransformation implements Transformation {
    Context context;

    public PicassoTransformation(Context context) {
        this.context = context;
    }

    @Override
    public Bitmap transform(Bitmap source) {
        int targetWidth = ShipeiUtils.getWidth(context);
        LogUtil.i("source.getHeight()=" + source.getHeight() + ",source.getWidth()=" + source.getWidth() + ",targetWidth=" + targetWidth);
        //如果图片小于屏幕宽度
        if (source.getWidth() < targetWidth) {
            //则按照设置的宽度比例来缩放
            double aspectRatio = (double) source.getHeight() / (double) source.getWidth();
            int targetHeight = (int) (targetWidth * aspectRatio);
            if (targetHeight != 0 && targetWidth != 0) {
                Bitmap result = Bitmap.createScaledBitmap(source, targetWidth, targetHeight, false);
                if (result != source) {
                    // Same bitmap is returned if sizes are the same
                    source.recycle();
                }
                return result;
            }
        }
        return source;
    }



    @Override
    public String key() {
        return "transformation" + " desiredWidth";
    }
}
