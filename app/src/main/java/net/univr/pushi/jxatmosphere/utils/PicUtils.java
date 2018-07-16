package net.univr.pushi.jxatmosphere.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowManager;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * author : Administrator wl
 * e-mail : 389456264@qq.com
 * time   : 2018/07/06
 * desc   :
 * version: 1.0
 */


public class PicUtils {
    public static void saveBitMap(Bitmap bitmap, String pack, String name) {
        int i = name.lastIndexOf("/");
        name = name.substring(i + 1, name.length());
        File PHOTO_DIR = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/images" + "/" + pack);//设置保存路径
        if (!PHOTO_DIR.exists())
            PHOTO_DIR.mkdirs();
        File avaterFile = new File(PHOTO_DIR, name);//设置文件名称
        try {
            FileOutputStream fos = new FileOutputStream(avaterFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //读取本地图片
    public static Bitmap readLocalImage(String name, String pack, Context context) {
        int i = name.lastIndexOf("/");
        name = name.substring(i + 1, name.length());
        File PHOTO_DIR = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/images" + "/" + pack);//设置保存路径
        Bitmap bitmap = null;
        try {
            File avaterFile = new File(PHOTO_DIR, name);
            if (avaterFile.exists()) {
                BitmapFactory.Options opts = new BitmapFactory.Options();
                opts.inJustDecodeBounds = true;
                bitmap = BitmapFactory.decodeFile(PHOTO_DIR + "/" + name);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (bitmap != null) {
            bitmap = changeBitmapSize(bitmap, context);
        }
        return bitmap;
    }

    public static Bitmap readLocalImageWithouChange(String name, String pack) {
        int i = name.lastIndexOf("/");
        name = name.substring(i + 1, name.length());
        File PHOTO_DIR = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/images" + "/" + pack);//设置保存路径
        Bitmap bitmap = null;
        try {
            File avaterFile = new File(PHOTO_DIR, name);
            if (avaterFile.exists()) {
                BitmapFactory.Options opts = new BitmapFactory.Options();
                opts.inJustDecodeBounds = true;
                bitmap = BitmapFactory.decodeFile(PHOTO_DIR + "/" + name);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }


    public static Bitmap decodeUriAsBitmapFromNet(String url, String pack) {
        URL fileUrl = null;
        Bitmap bitmap = null;

        try {
            fileUrl = new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        try {
            HttpURLConnection conn = (HttpURLConnection) fileUrl
                    .openConnection();
            conn.setDoInput(true);
            conn.connect();
            InputStream is = conn.getInputStream();
            bitmap = BitmapFactory.decodeStream(is);
            is.close();
            PicUtils.saveBitMap(bitmap, pack, url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }


    private static Bitmap changeBitmapSize(Bitmap bitmap, Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        int wd = dm.widthPixels;         // 屏幕宽度（像素）
        int hg = dm.heightPixels;         // 屏幕高度（像素）

        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        float wdf=Float.valueOf(width);
        float hgf=Float.valueOf(height);
        float scale=wdf/hgf;
        float newHeight = height;
        float newWidth = width;
        //设置想要的大小
        if(width>wd&&height>hg){
            newWidth=wd;
            newHeight=wd/scale;
        }else{
            if(width>wd){
                newWidth=wd;
                newHeight=wd/scale;
            }
            if(height>hg){
                newWidth=hg*scale;
                newHeight=hg;
            }
        }

        //计算压缩的比率
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;

        //获取想要缩放的matrix
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);

        //获取新的bitmap
        bitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
        bitmap.getWidth();
        bitmap.getHeight();
        Log.e("newWidth", "newWidth" + bitmap.getWidth());
        Log.e("newHeight", "newHeight" + bitmap.getHeight());
        return bitmap;
    }


}
