package net.univr.pushi.jxatmosphere.adapter;

import android.support.annotation.Nullable;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import net.univr.pushi.jxatmosphere.R;

import java.util.List;

/**
 * author : Administrator wl
 * e-mail : 389456264@qq.com
 * time   : 2018/07/14
 * desc   :
 * version: 1.0
 */


public class ZytqybAdapter extends BaseQuickAdapter <String,BaseViewHolder> {
    public ZytqybAdapter( @Nullable List<String> data) {
        super(R.layout.zytqyb_recycle_item, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        if (helper.getAdapterPosition()==0){
            ((TextView) helper.getView(R.id.item_1)).setText("现在天气");
        }
        if (helper.getAdapterPosition()==1){
            ((TextView) helper.getView(R.id.item_1)).setText("极大风速的风向(度)");
        }
        if (helper.getAdapterPosition()==2){
            ((TextView) helper.getView(R.id.item_1)).setText("极大的风速的风向(m/s)");
        }
        if (helper.getAdapterPosition()==3){
            ((TextView) helper.getView(R.id.item_1)).setText("龙卷风类型");
        }
        if (helper.getAdapterPosition()==4){
            ((TextView) helper.getView(R.id.item_1)).setText("龙卷风方位");
        }
        if (helper.getAdapterPosition()==5){
            ((TextView) helper.getView(R.id.item_1)).setText("积雪深度(cm)");
        }
        if (helper.getAdapterPosition()==6){
            ((TextView) helper.getView(R.id.item_1)).setText("电线积冰直径(mm)");
        }
        if (helper.getAdapterPosition()==7){
            ((TextView) helper.getView(R.id.item_1)).setText("最大冰雹直径(mm)");
        }
        if (helper.getAdapterPosition()==8){
            ((TextView) helper.getView(R.id.item_1)).setText("过去一小时降雨量(mm)");
        }

        if (helper.getAdapterPosition()==9){
            ((TextView) helper.getView(R.id.item_1)).setText("过去三小时降雨量(mm)");
        }
        ((TextView) helper.getView(R.id.item_2)).setText(item);
    }
}
