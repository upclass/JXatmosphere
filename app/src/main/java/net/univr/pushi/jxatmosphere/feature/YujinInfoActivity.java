package net.univr.pushi.jxatmosphere.feature;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import net.univr.pushi.jxatmosphere.R;
import net.univr.pushi.jxatmosphere.base.BaseActivity;
import net.univr.pushi.jxatmosphere.beens.YuJinXinhaoBeen;

import butterknife.BindView;

public class YujinInfoActivity extends BaseActivity implements View.OnClickListener {


    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.yujin_yby)
    TextView yujinYby;
    @BindView(R.id.yujin_fbdw)
    TextView yujinFbdw;
    @BindView(R.id.yujin_fbsj)
    TextView yujinFbsj;
    @BindView(R.id.yujin_lb)
    TextView yujinLb;
    @BindView(R.id.yujin_dq)
    TextView yujinDq;
    @BindView(R.id.content)
    TextView content;

    @Override
    public int getLayoutId() {
        return R.layout.activity_yujin_info;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        super.initViews(savedInstanceState);
        initOnclick();
        initDatas();
    }

    private void initDatas() {
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        YuJinXinhaoBeen.DataBean data = (YuJinXinhaoBeen.DataBean) extras.get("data");
        yujinYby.setText(data.getYubaoyuan());
        yujinFbdw.setText(data.getDanwei());
        yujinFbsj.setText(data.getFabu());
        yujinLb.setText(data.getZtname());
        yujinDq.setText(data.getCitySelect());
        content.setText(data.getJielun());
    }

    private void initOnclick() {
        back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
        }

    }

}
