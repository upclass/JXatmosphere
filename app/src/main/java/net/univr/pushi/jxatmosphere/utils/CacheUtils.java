package net.univr.pushi.jxatmosphere.utils;

import android.content.Context;
import android.os.Environment;

import java.io.File;
import java.math.BigDecimal;

/**
 * author : Administrator wl
 * e-mail : 389456264@qq.com
 * time   : 2018/05/26
 * desc   :
 * version: 1.0
 */


public class CacheUtils {
    public static String getTotalCacheSize(Context context){
        //context.getCacheDir()获取的是内存缓存的目录/data/data/wj.com.universalimageloader/cache
        long cacheSize = getFolderSize(context.getCacheDir());
        //context.getExternalCacheDir()获取的是sdCard的缓存目录
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            cacheSize += getFolderSize(context.getExternalCacheDir());
        }
        return getFormatSize(cacheSize);
    }

    //获取对应文件夹 的总大小
    public static long getFolderSize(File file){
        long size=0;

        try{
            File[] files=file.listFiles();
            for(int i=0;i<files.length;i++){
                //如果下面有文件
                if(files[i].isDirectory()){
                    size=size+getFolderSize(files[i]);
                }else{
                    size=size+files[i].length();
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return size;
    }

    /**
     * 格式化单位
     *
     * @param size
     * @return
     */
    public static String getFormatSize(double size) {
        double kiloByte = size / 1024;
        if (kiloByte < 1) {
            return size + "B";
//            return "0K";
        }

        double megaByte = kiloByte / 1024;
        if (megaByte < 1) {
            BigDecimal result1 = new BigDecimal(Double.toString(kiloByte));
            return result1.setScale(2, BigDecimal.ROUND_HALF_UP)
                    .toPlainString() + "KB";
        }

        double gigaByte = megaByte / 1024;
        if (gigaByte < 1) {
            BigDecimal result2 = new BigDecimal(Double.toString(megaByte));
            return result2.setScale(2, BigDecimal.ROUND_HALF_UP)
                    .toPlainString() + "MB";
        }

        double teraBytes = gigaByte / 1024;
        if (teraBytes < 1) {
            BigDecimal result3 = new BigDecimal(Double.toString(gigaByte));
            return result3.setScale(2, BigDecimal.ROUND_HALF_UP)
                    .toPlainString() + "GB";
        }
        BigDecimal result4 = new BigDecimal(teraBytes);
        return result4.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString()
                + "TB";
    }

    //清空所有缓存文件夹文件
    public static void clearAllCache(Context context) {
        deleteDir(context.getCacheDir());
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            deleteDir(context.getExternalCacheDir());
        }
    }

    //删除对应文件夹文件
    private static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        return dir.delete();
    }


}
