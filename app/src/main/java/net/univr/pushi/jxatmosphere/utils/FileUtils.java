package net.univr.pushi.jxatmosphere.utils;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.provider.MediaStore;

import com.blankj.utilcode.util.ToastUtils;

import net.univr.pushi.jxatmosphere.R;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * author : Administrator wl
 * e-mail : 389456264@qq.com
 * time   : 2018/09/12
 * desc   :
 * version: 1.0
 */


public class FileUtils {

    public static void saveImageToGallery(Context context, File file, String url) {
        String suffix = url.substring(url.lastIndexOf(".") + 1);
        String fileName = System.currentTimeMillis() + "." + suffix;
        // 其次把文件插入到系统图库
        try {
            MediaStore.Images.Media.insertImage(context.getContentResolver(),
                    file.getAbsolutePath(), fileName, null);
            showShortToast(context, "保存成功");
        } catch (FileNotFoundException e) {
            showShortToast(context, "保存失败");
            e.printStackTrace();
        }
        // 最后通知图库更新
        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,
                Uri.fromFile(new File(file.getPath()))));
    }

    private static void showShortToast(Context context, String message) {
        ToastUtils.showShort(message);
        ToastUtils.setBgColor(Color.parseColor("#f6f6f6"));
        ToastUtils.setMsgColor(context.getResources().getColor(R.color.yujin_black));
    }
}
