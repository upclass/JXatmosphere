package net.univr.pushi.jxatmosphere.adapter;

import android.support.annotation.Nullable;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * author : Administrator wl
 * e-mail : 389456264@qq.com
 * time   : 2018/05/15
 * desc   :
 * version: 1.0
 */


public class WtfNewInfoAdapter  extends BaseQuickAdapter<String, BaseViewHolder> {
    public WtfNewInfoAdapter(@Nullable List<String> data) {
        super(android.R.layout.simple_list_item_1, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        TextView textView = helper.getView(android.R.id.text1);
//        helper.addOnClickListener(R.id)
        textView.setText(item);
    }
}
