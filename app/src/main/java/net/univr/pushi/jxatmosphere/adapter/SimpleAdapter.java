package net.univr.pushi.jxatmosphere.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import net.univr.pushi.jxatmosphere.R;
import net.univr.pushi.jxatmosphere.beens.GdybSearchBeen;

import java.util.List;

/**
 * author : Administrator wl
 * e-mail : 389456264@qq.com
 * time   : 2018/08/01
 * desc   :
 * version: 1.0
 */


public class SimpleAdapter extends BaseQuickAdapter<GdybSearchBeen, BaseViewHolder> {
    public SimpleAdapter(List<GdybSearchBeen> data) {
        super(R.layout.simple_adapter_layout, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, GdybSearchBeen item) {
            helper.setText(R.id.simlple_item,item.getTitle());
            helper.addOnClickListener(R.id.simlple_item);
    }
}
