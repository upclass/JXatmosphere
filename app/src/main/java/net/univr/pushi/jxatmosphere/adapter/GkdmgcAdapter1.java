package net.univr.pushi.jxatmosphere.adapter;

import android.graphics.Color;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import net.univr.pushi.jxatmosphere.R;
import net.univr.pushi.jxatmosphere.beens.GkdmmenuBeen;

import java.util.List;

/**
 * author : Administrator wl
 * e-mail : 389456264@qq.com
 * time   : 2018/05/03
 * desc   :
 * version: 1.0
 */


public class GkdmgcAdapter1 extends BaseQuickAdapter<GkdmmenuBeen, BaseViewHolder> {


    int lastClick;

    public void setLastClick(int lastClick) {
        this.lastClick = lastClick;
    }

    public int getLastClick() {
        return lastClick;
    }

    public GkdmgcAdapter1(@Nullable List<GkdmmenuBeen> data) {
        super(R.layout.item_recycle_horizontal_gkdmgc1_layout, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, GkdmmenuBeen item) {
        TextView view = helper.getView(R.id.title);

        if (item.isSelect()) {
            view.setTextSize(15);
            view.setTextColor(Color.parseColor("#0081e7"));
            helper.getView(R.id.tabline).setVisibility(View.VISIBLE);
        } else {
            view.setTextSize(13);
            Color.parseColor("#0081e7");
            helper.getView(R.id.tabline).setVisibility(View.INVISIBLE);
        }
        view.setText(item.getText());
        helper.addOnClickListener(R.id.title);
    }
};
