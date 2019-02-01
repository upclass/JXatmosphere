package net.univr.pushi.jxatmosphere.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import net.univr.pushi.jxatmosphere.R;
import net.univr.pushi.jxatmosphere.beens.GkdmClickBeen;

import java.util.List;

/**
 * author : Administrator wl
 * e-mail : 389456264@qq.com
 * time   : 2019/01/21
 * desc   :
 * version: 1.0
 */


public class TimeForecastAdapert extends BaseQuickAdapter<GkdmClickBeen, BaseViewHolder> {
    int lastClick=0;


    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    Context context;

    public TimeForecastAdapert(@Nullable List<GkdmClickBeen> data, Context context) {
        super(R.layout.simple_adapter_item, data);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, GkdmClickBeen item) {
        TextView textView = helper.getView(R.id.simlple_item);
        textView.setText(item.getText());
        if (item.getOnclick()) {
            textView.setTextColor(context.getResources().getColor(R.color.red));
        } else {
            textView.setTextColor(context.getResources().getColor(R.color.black));
        }
        helper.addOnClickListener(R.id.simlple_item);
    }

    public int getLastClick() {
        return lastClick;
    }

    public void setLastClick(int lastClick) {
        this.lastClick = lastClick;
    }
}
