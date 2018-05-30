package net.univr.pushi.jxatmosphere.adapter;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import net.univr.pushi.jxatmosphere.R;
import net.univr.pushi.jxatmosphere.beens.ForecasterScore;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * author : Administrator wl
 * e-mail : 389456264@qq.com
 * time   : 2018/04/24
 * desc   :
 * version: 1.0
 */


public class ForecasterAdapter  extends BaseQuickAdapter<ForecasterScore.DataBean.DutygradeBean, ForecasterAdapter.MyViewHolder> {
    public ForecasterAdapter(@Nullable List<ForecasterScore.DataBean.DutygradeBean> data) {
        super(R.layout.item_forecaster_score_layout, data);
    }

    @Override
    protected void convert(MyViewHolder helper, ForecasterScore.DataBean.DutygradeBean item) {
        helper.tv1.setText(item.getName().trim());
        helper.tv2.setText(item.getTimes().trim());
        helper.tv3.setText(item.getScore().trim());
        helper.tv4.setText(item.getYearMon().trim());
    }




    class MyViewHolder extends BaseViewHolder {
        @BindView(R.id.tv1)
        TextView tv1;
        @BindView(R.id.tv2)
        TextView tv2;
        @BindView(R.id.tv3)
        TextView tv3;
        @BindView(R.id.tv4)
        TextView tv4;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
