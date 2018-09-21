package net.univr.pushi.jxatmosphere.adapter;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.squareup.picasso.Picasso;

import net.univr.pushi.jxatmosphere.R;
import net.univr.pushi.jxatmosphere.beens.CollectionListBeen;

import java.util.List;

/**
 * author : Administrator wl
 * e-mail : 389456264@qq.com
 * time   : 2018/09/15
 * desc   :
 * version: 1.0
 */


public class CollectionAdapter extends BaseQuickAdapter<CollectionListBeen.DataBean, BaseViewHolder> {
    private Context mContext;

    public CollectionAdapter(List<CollectionListBeen.DataBean> data, Context context) {
        super(R.layout.collection_list_layout, data);
        mContext = context;
    }


    @Override
    protected void convert(BaseViewHolder helper, CollectionListBeen.DataBean item) {
        ImageView imageView = helper.getView(R.id.picUrl);
        Picasso.with(mContext).load(item.getUrl()).placeholder(R.drawable.app_imageplacehold).into(imageView);
        TextView time = helper.getView(R.id.collection_time);
        time.setText(item.getTime());
        TextView type = helper.getView(R.id.type);
        type.setText(item.getType());
        TextView ctype = helper.getView(R.id.ctype);
        ctype.setText(item.getCtype());
        helper.addOnClickListener(R.id.item);
    }
}
