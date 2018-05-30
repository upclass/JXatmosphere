package net.univr.pushi.jxatmosphere.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import net.univr.pushi.jxatmosphere.MyApplication;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * author : Administrator wl
 * e-mail : 389456264@qq.com
 * time   : 2018/03/29
 * desc   :
 * version: 1.0
 */


public abstract class BaseActivity extends RxAppCompatActivity {
    private Unbinder bind;
    public Context context;
    private MyApplication application;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        setContentView(getLayoutId());
        bind = ButterKnife.bind(this);
        if (application == null) {
            // 得到Application对象
            application = (MyApplication) getApplication();
        }
        addActivity();
        initViews(savedInstanceState);
    }

    public abstract int getLayoutId();

    public  void initViews(Bundle savedInstanceState){};

    @Override
    protected void onDestroy() {
        super.onDestroy();
        removeActivity();
        bind.unbind();
    }

    // 添加Activity方法
    public void addActivity() {
        application.addActivity_((Activity) context);// 调用myApplication的添加Activity方法
    }

    //销毁当个Activity方法
    public void removeActivity() {
        application.removeActivity_((Activity) context);// 调用myApplication的销毁单个Activity方法
    }


    //销毁所有Activity方法
    public void removeALLActivity() {
        application.removeALLActivity_();// 调用myApplication的销毁所有Activity方法
    }



//    private long time = 0;
//    //退出方法
//    private void exit() {
////如果在两秒大于2秒
//        if (System.currentTimeMillis() - time > 2000) {
////获得当前的时间
//            time = System.currentTimeMillis();
//            showToast("再点击一次退出应用程序");
//        } else {
////点击在两秒以内
//            removeALLActivity();//执行移除所以Activity方法
//        }
//    }


    @Override
    public void finish() {
        super.finish();
        removeActivity();
    }
}
