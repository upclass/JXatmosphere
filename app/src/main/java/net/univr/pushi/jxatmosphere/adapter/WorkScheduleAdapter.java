package net.univr.pushi.jxatmosphere.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import net.univr.pushi.jxatmosphere.R;
import net.univr.pushi.jxatmosphere.beens.LocalDutyBeen;

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


public class WorkScheduleAdapter extends BaseQuickAdapter<LocalDutyBeen.DataBean.DutysBean, WorkScheduleAdapter.MyViewHolder> {


    public WorkScheduleAdapter(Context context,@Nullable List<LocalDutyBeen.DataBean.DutysBean> data) {
        super(R.layout.item_work_sche, data);
    }


    @Override
    protected void convert(MyViewHolder helper, LocalDutyBeen.DataBean.DutysBean item) {
        helper.tv1.setText(item.getDate().trim());
        helper.tv2.setText(item.getName().trim());
        helper.tv3.setText(item.getDuty().trim());
        helper.tv4.setText(item.getProperty().trim());

        if (item.getDiver()){
            helper.diver.setBackgroundColor(mContext.getResources().getColor(R.color.red));
        }else{
            helper.diver.setBackgroundColor(mContext.getResources().getColor(R.color.tine_black1));
        }
        helper.addOnClickListener(R.id.phone);
        helper.addOnClickListener(R.id.telePhone);
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
        @BindView(R.id.diver)
        View diver;
        @BindView(R.id.phone)
        ImageView phone;
        @BindView(R.id.telePhone)
        ImageView telePhone;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
