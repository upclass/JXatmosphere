package net.univr.pushi.jxatmosphere.adapter;

import android.support.annotation.Nullable;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import net.univr.pushi.jxatmosphere.R;
import net.univr.pushi.jxatmosphere.beens.Content_ForecastScore_Been;

import java.util.List;

/**
 * author : Administrator wl
 * e-mail : 389456264@qq.com
 * time   : 2019/01/21
 * desc   :
 * version: 1.0
 */


public class ContentForecasterAdapter extends BaseQuickAdapter<Content_ForecastScore_Been, BaseViewHolder> {

    public ContentForecasterAdapter(@Nullable List<Content_ForecastScore_Been> data) {
        super(R.layout.web_forecast_score_item, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Content_ForecastScore_Been item) {
        TextView diqu = helper.getView(R.id.dq);
        diqu.setText(item.getArea());
        TextView gjcp = helper.getView(R.id.gjcp);
        gjcp.setText(item.getScmoc());
        TextView stjt = helper.getView(R.id.stjt);
        stjt.setText(item.getYbmode3());
        TextView stcp = helper.getView(R.id.stcp);
        stcp.setText(item.getGuidance());
        TextView dsrh = helper.getView(R.id.dsrh);
        dsrh.setText(item.getYbmode7());
    }

}
