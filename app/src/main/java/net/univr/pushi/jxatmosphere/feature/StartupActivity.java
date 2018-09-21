package net.univr.pushi.jxatmosphere.feature;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import com.blankj.utilcode.util.SPUtils;

import net.univr.pushi.jxatmosphere.LoginActivity;
import net.univr.pushi.jxatmosphere.R;
import net.univr.pushi.jxatmosphere.base.BaseActivity;

import java.util.Timer;
import java.util.TimerTask;

public class StartupActivity extends BaseActivity {

    private boolean isFristLogin;


    @Override
    public void initViews(Bundle savedInstanceState) {
        super.initViews(savedInstanceState);
        if (Build.VERSION.SDK_INT >= 21){
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        isFristLogin = SPUtils.getInstance().getBoolean("isFirstLogin", true);
    }


    @Override
    protected void onResume() {
        super.onResume();
        setUpSplash();
    }

    private void setUpSplash() {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                finishTask();
            }
        }, 1500);
    }

    private void finishTask() {
//        startActivity(new Intent(StartupActivity.this, LoginActivity.class));


        if (isFristLogin) {
            startActivity(new Intent(StartupActivity.this, LoginActivity.class));
            finish();
        } else {
            startActivity(new Intent(StartupActivity.this, MainActivity.class));
            finish();
        }

//        startActivity(new Intent(StartupActivity.this, MainActivity.class));
//        finish();
    }


    @Override
    public int getLayoutId() {
        return R.layout.activity_startup;
    }
}
