package net.univr.pushi.jxatmosphere.feature;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import net.univr.pushi.jxatmosphere.R;
import net.univr.pushi.jxatmosphere.base.BaseActivity;
import net.univr.pushi.jxatmosphere.beens.YujinInfo;
import net.univr.pushi.jxatmosphere.utils.YujinWeiZhi;

import java.util.ArrayList;

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
        ArrayList dataArrayList = (ArrayList) extras.get("data");
        Object o = dataArrayList.get(0);
        YujinInfo.DataBean data = (YujinInfo.DataBean) o;
        StringBuilder diqu = initDiqu(data.getAcodes());
        yujinYby.setText(data.getYuBaoYuan());
        yujinFbdw.setText(data.getDanwei());
        yujinFbsj.setText(data.getFabu());
        yujinLb.setText(data.getYujinInfo());
        yujinDq.setText(diqu.toString());
        content.setText(data.getContent());
    }

    private StringBuilder initDiqu(String acode) {
        StringBuilder ret=new StringBuilder();
        String[] split = acode.split(",");

        for (int i = 0; i < split.length; i++) {
            ret.append(YujinWeiZhi.getTitle(split[i]));
            ret.append(",");
        }
        ret.deleteCharAt(ret.length()-1);
        return ret;
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
