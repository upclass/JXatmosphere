package net.univr.pushi.jxatmosphere.adapter;

import android.support.annotation.Nullable;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import net.univr.pushi.jxatmosphere.R;
import net.univr.pushi.jxatmosphere.beens.Locationbeen;

import java.util.List;

/**
 * author : Administrator wl
 * e-mail : 389456264@qq.com
 * time   : 2019/01/16
 * desc   :
 * version: 1.0
 */


public class LocationAdapter extends BaseQuickAdapter<Locationbeen, BaseViewHolder> {
    public LocationAdapter(@Nullable List<Locationbeen> data) {
        super(R.layout.location_adapter, data);
    }


    @Override
    protected void convert(BaseViewHolder helper, Locationbeen item) {
        TextView content = helper.getView(R.id.content);
        TextView weizhi = helper.getView(R.id.weizhi);
        content.setText(item.getContent());
        weizhi.setText(item.getWeizhi());
        helper.addOnClickListener(R.id.contain);
    }
}
