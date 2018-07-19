package net.univr.pushi.jxatmosphere.adapter;

import android.graphics.Color;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import net.univr.pushi.jxatmosphere.R;
import net.univr.pushi.jxatmosphere.beens.MultiItemGdybTx;

import java.util.List;

/**
 * author : Administrator wl
 * e-mail : 389456264@qq.com
 * time   : 2018/06/04
 * desc   :
 * version: 1.0
 */


public class MultiGdybTxAdapter extends BaseMultiItemQuickAdapter<MultiItemGdybTx, BaseViewHolder> {
    public MultiGdybTxAdapter(@Nullable List<MultiItemGdybTx> data) {
        super(data);
        addItemType(MultiItemGdybTx.IMG, R.layout.gdybtxheader_layout);
        addItemType(MultiItemGdybTx.TIME_TEXT, R.layout.item_recycle_horizontal_dmcgjc3_layout);
    }

    @Override
    protected void convert(BaseViewHolder helper, MultiItemGdybTx item) {
        switch (helper.getItemViewType()) {
            case MultiItemGdybTx.IMG:
                helper.setImageResource(R.id.pic_ready, item.getResId());
                helper.addOnClickListener(R.id.pic_ready);
                break;
            case MultiItemGdybTx.TIME_TEXT:
                TextView TextView = helper.getView(R.id.time);
                TextView.setText(item.getContent().getText());
                if (item.getContent().getOnclick() == true) {
                    TextView.setBackgroundColor(Color.parseColor("#0081e7"));
                    TextView.setTextColor(Color.parseColor("#FFFFFF"));
                } else {
                    TextView.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    TextView.setTextColor(Color.parseColor("#2c2a2a"));
                }
                helper.addOnClickListener(R.id.time);
                break;
        }
    }
}
