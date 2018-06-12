package net.univr.pushi.jxatmosphere.feature;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.blankj.utilcode.util.SPUtils;

import net.univr.pushi.jxatmosphere.R;
import net.univr.pushi.jxatmosphere.base.BaseActivity;

import butterknife.BindView;

public class PersonalInfoActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.login_out)
    Button loginOut;


    @Override
    public int getLayoutId() {
        return R.layout.activity_personal_info;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.login_out:
                SPUtils.getInstance().remove("isFirstLogin");
                removeALLActivity();
                break;

        }

    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        loginOut.setOnClickListener(this);
        back.setOnClickListener(this);
    }
}