package net.univr.pushi.jxatmosphere.feature;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.util.SPUtils;

import net.univr.pushi.jxatmosphere.MyApplication;
import net.univr.pushi.jxatmosphere.R;
import net.univr.pushi.jxatmosphere.base.BaseActivity;
import net.univr.pushi.jxatmosphere.utils.CacheUtils;

import java.io.File;

import butterknife.BindView;

public class SettingActivity extends BaseActivity implements View.OnClickListener {


    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.huancun_tv_1)
    TextView tvCacheSize;
    @BindView(R.id.huancun_tv_2)
    TextView time;
    @BindView(R.id.huancun_tv_3)
    TextView weizhiQuanxian;
    @BindView(R.id.get_location)
    RelativeLayout get_location;
    @BindView(R.id.clean_cache)
    RelativeLayout clean_cache;
    @BindView(R.id.time_parent)
    RelativeLayout time_parent;
    @BindView(R.id.login_out)
    Button loginout;
    @BindView(R.id.setting_main)
    LinearLayout setting_main;
    @BindView(R.id.auto_play_time)
    RelativeLayout auto_play_time;

    PopupWindow popupWindow;


    @Override
    public int getLayoutId() {
        return R.layout.activity_setting;
    }


    @Override
    public void initViews(Bundle savedInstanceState) {
        super.initViews(savedInstanceState);
        initCacheSize(context);
        initLocation();
        back.setOnClickListener(this);
        clean_cache.setOnClickListener(this);
        loginout.setOnClickListener(this);
        get_location.setOnClickListener(this);
        auto_play_time.setOnClickListener(this);
        int auto_time = SPUtils.getInstance().getInt("auto_time", 1000);
        time.setText(auto_time/1000+"s");
    }

    private void initLocation() {
        //判断是否已经获取相应权限
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            //相应操作
            weizhiQuanxian.setText("已开启");
        } else weizhiQuanxian.setText("未开启");
    }


    private void initCacheSize(Context context) {
        tvCacheSize.setText(CacheUtils.getTotalCacheSize(context));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.clean_cache:
                CacheUtils.clearAllCache(context);
                CacheUtils.deleteDir(new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/images" ));
                tvCacheSize.setText(CacheUtils.getTotalCacheSize(context));
                break;
            case R.id.get_location:
                if (weizhiQuanxian.getText().equals("未开启"))
                    ActivityCompat.requestPermissions(SettingActivity.this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
                break;
            case R.id.login_out:
                SPUtils.getInstance().remove("isFirstLogin");
                SPUtils.getInstance().remove("userId");
                removeALLActivity();
                break;
            case R.id.auto_play_time:
                show(context);
//                setting_main.setBackgroundColor(getResources().getColor(R.color.gray_holo_light));
                backgroundAlpha(0.5f);
                break;
            case R.id.second1:
                MyApplication.getInstance().auto_time=1000;
                SPUtils.getInstance().put("auto_time",1000);
                Toast.makeText(context, "设置成功", Toast.LENGTH_SHORT).show();
                popupWindow.dismiss();
                time.setText("1s");
                break;
            case R.id.second2:
                MyApplication.getInstance().auto_time=2000;
                SPUtils.getInstance().put("auto_time",2000);
                time.setText("2s");
                Toast.makeText(context, "设置成功", Toast.LENGTH_SHORT).show();
                popupWindow.dismiss();
                break;
            case R.id.second3:
                MyApplication.getInstance().auto_time=3000;
                SPUtils.getInstance().put("auto_time",3000);
                time.setText("3s");
                Toast.makeText(context, "设置成功", Toast.LENGTH_SHORT).show();
                popupWindow.dismiss();
                break;
            case R.id.second4:
                MyApplication.getInstance().auto_time=4000;
                SPUtils.getInstance().put("auto_time",4000);
                time.setText("4s");
                Toast.makeText(context, "设置成功", Toast.LENGTH_SHORT).show();
                popupWindow.dismiss();
                break;
            case R.id.second5:
                MyApplication.getInstance().auto_time=5000;
                SPUtils.getInstance().put("auto_time",5000);
                time.setText("5s");
                Toast.makeText(context, "设置成功", Toast.LENGTH_SHORT).show();
                popupWindow.dismiss();
                break;
            case R.id.cancle:
                popupWindow.dismiss();
                break;

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0) { //安全写法，如果小于0，肯定会出错了
            for (int i = 0; i < grantResults.length; i++) {

                int grantResult = grantResults[i];
                if (grantResult == PackageManager.PERMISSION_DENIED) { //这个是权限拒绝
                    String s = permissions[i];
                    Toast.makeText(this, s + "权限被拒绝了", Toast.LENGTH_SHORT).show();
                } else { //授权成功了
                    //do Something
                    if (i == 0) weizhiQuanxian.setText("已开启");
                }
            }
        }
    }


    private void show(Context context) {
        View popLayout = LayoutInflater.from(this).inflate(R.layout.popup_setting_layout, null);
        popupWindow = new PopupWindow(popLayout, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
//                setting_main.setBackgroundColor(getResources().getColor(R.color.light_white));
                backgroundAlpha(1f);

            }
        });
        popupWindow.setTouchable(true);
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setAnimationStyle(R.style.pop_anim);
        popupWindow.showAtLocation(time_parent, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
        Button button1 = popLayout.findViewById(R.id.second1);
        Button button2 = popLayout.findViewById(R.id.second2);
        Button button3 = popLayout.findViewById(R.id.second3);
        Button button4 = popLayout.findViewById(R.id.second4);
        Button button5 = popLayout.findViewById(R.id.second5);
        Button cancle = popLayout.findViewById(R.id.cancle);
        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
        button4.setOnClickListener(this);
        button5.setOnClickListener(this);
        cancle.setOnClickListener(this);
    }

    /**
     * 设置添加屏幕的背景透明度
     * @param bgAlpha
     */
    public void backgroundAlpha (float bgAlpha)
    {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        getWindow().setAttributes(lp);
    }

}
