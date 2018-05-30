package net.univr.pushi.jxatmosphere.adapter;

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
 * time   : 2018/05/23
 * desc   :
 * version: 1.0
 */


public class GdybtxMenuAdapter extends BaseQuickAdapter<GkdmmenuBeen, BaseViewHolder> {


    int lastposition = 0;

    public void setLastposition(int lastposition) {
        this.lastposition = lastposition;
    }

    public int getLastposition() {
        return lastposition;
    }

    public GdybtxMenuAdapter(@Nullable List<GkdmmenuBeen> data) {
        super(R.layout.item_recycle_horizontal_econe_menu_layout, data);
    }



    @Override
    protected void convert(BaseViewHolder helper, GkdmmenuBeen item) {
        TextView title = helper.getView(R.id.title);
        View view = helper.getView(R.id.tabline);
        title.setText(item.getText());
        if (item.isSelect()) {
            view.setVisibility(View.VISIBLE);
            title.setTextSize(17);
        } else {
            view.setVisibility(View.INVISIBLE);
            title.setTextSize(15);
        }
        helper.addOnClickListener(R.id.title);
    }

}
