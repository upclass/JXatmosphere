package net.univr.pushi.jxatmosphere.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.squareup.picasso.Picasso;

import net.univr.pushi.jxatmosphere.R;

import java.util.List;

/**
 * author : Administrator wl
 * e-mail : 389456264@qq.com
 * time   : 2018/05/03
 * desc   :
 * version: 1.0
 */


public class GkdmgcAdapter2 extends BaseQuickAdapter<String,BaseViewHolder> {
    private  Context context;
    public GkdmgcAdapter2(@Nullable List<String> data, Context context) {
        super(R.layout.item_recycle_horizontal_gkdmgc2_layout, data);
        this.context=context;
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        ImageView imageView = helper.getView(R.id.url);
        Picasso.with(context)
                .load(item)
                .placeholder(R.drawable.app_imageplacehold)
                .into(imageView);
    }
}