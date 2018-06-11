package net.univr.pushi.jxatmosphere.feature;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import net.univr.pushi.jxatmosphere.R;
import net.univr.pushi.jxatmosphere.base.BaseActivity;

import butterknife.BindView;

public class AboutOursActivity extends BaseActivity {


    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.banbenhao)
    TextView banbenhao;

    @Override
    public int getLayoutId() {
        return R.layout.activity_about_ours;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        try {
            banbenhao.setText("当前版本:  "+getVersionName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private String getVersionName() throws Exception {
        // 获取packagemanager的实例
        PackageManager packageManager = getPackageManager();
        // getPackageName()是你当前类的包名，0代表是获取版本信息
        PackageInfo packInfo = packageManager.getPackageInfo(getPackageName(), 0);
        String version = packInfo.versionName;
        return version;
    }

}
