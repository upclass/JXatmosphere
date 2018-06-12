package net.univr.pushi.jxatmosphere;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.Utils;
import com.mob.MobSDK;
import com.pgyersdk.crash.PgyCrashManager;

import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * author : Administrator wl
 * e-mail : 389456264@qq.com
 * time   : 2018/03/31
 * desc   :
 * version: 1.0
 */
/*如果您没有在AndroidManifest中设置appliaction的类名，
        MobSDK会将这个设置为com.mob.MobApplication，
        但如果您设置了，请在您自己的Application类中调用：MobSDK.init(this);*/

public class MyApplication extends Application {
    private static MyApplication instance;
    List<Activity> activityList = new ArrayList<Activity>();
    public static Integer auto_time;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        MobSDK.init(this);
        //工具类初始化
        Utils.init(this);
        auto_time= SPUtils.getInstance().getInt("auto_time",1000);
        //初始化XUtils
        x.Ext.init(this);
        //设置debug模式
        x.Ext.setDebug(true);
        PgyCrashManager.register(this);
    }

    public static MyApplication getInstance() {
        return instance;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }


    /**
     * 添加Activity
     */
    public void addActivity_(Activity activity) {
        // 判断当前集合中不存在该Activity
        if (!activityList.contains(activity)) {
            activityList.add(activity);//把当前Activity添加到集合中
        }
    }


    /**
     * 销毁单个Activity
     */
    public void removeActivity_(Activity activity) {
        //判断当前集合中存在该Activity
        if (activityList.contains(activity)) {
            activityList.remove(activity);//从集合中移除
            activity.finish();//销毁当前Activity
        }
    }

    /**
     * 销毁所有的Activity
     */
    public void removeALLActivity_() {
        for (int i = activityList.size()-1; i>=0 ; i--) {
            activityList.get(i).finish();
        }
    }


}
