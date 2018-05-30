package net.univr.pushi.jxatmosphere.adapter;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import net.univr.pushi.jxatmosphere.R;
import net.univr.pushi.jxatmosphere.beens.DutyBeen;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * author : Administrator wl
 * e-mail : 389456264@qq.com
 * time   : 2018/04/03
 * desc   :
 * version: 1.0
 */


public class WorkScheduleAdapter extends BaseQuickAdapter<DutyBeen.DataBean.DutysBean, WorkScheduleAdapter.MyViewHolder> {

    public WorkScheduleAdapter(@Nullable List<DutyBeen.DataBean.DutysBean> data) {
        super(R.layout.item_work_sche, data);
    }


    @Override
    protected void convert(MyViewHolder helper, DutyBeen.DataBean.DutysBean item) {
        helper.tv1.setText(item.getDate().trim());
        helper.tv2.setText(item.getName().trim());
        helper.tv3.setText(item.getDuty().trim());
        helper.tv4.setText(item.getProperty().trim());
    }


    class MyViewHolder extends BaseViewHolder {
        @BindView(R.id.date_time)
        TextView tv1;
        @BindView(R.id.watchkeeper)
        TextView tv2;
        @BindView(R.id.post)
        TextView tv3;
        @BindView(R.id.on_duty_time)
        TextView tv4;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
