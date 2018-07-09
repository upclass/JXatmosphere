package net.univr.pushi.jxatmosphere.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

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
    public static void saveBitMap(Bitmap bitmap,String pack, String name) {
        int i = name.lastIndexOf("/");
        name = name.substring(i + 1, name.length());
        File PHOTO_DIR = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/images"+"/"+pack);//设置保存路径
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
    public static Bitmap readLocalImage(String name,String pack) {
        int i = name.lastIndexOf("/");
        name = name.substring(i + 1, name.length());
        File PHOTO_DIR = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+ "/images" +"/"+pack);//设置保存路径
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


    public static Bitmap decodeUriAsBitmapFromNet(String url,String pack) {
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
            PicUtils.saveBitMap(bitmap, pack,url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

}
