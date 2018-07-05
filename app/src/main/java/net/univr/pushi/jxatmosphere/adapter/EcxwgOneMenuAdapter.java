package net.univr.pushi.jxatmosphere.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import net.univr.pushi.jxatmosphere.R;
import net.univr.pushi.jxatmosphere.beens.EcOneMenu;

import java.util.List;

/**
 * author : Administrator wl
 * e-mail : 389456264@qq.com
 * time   : 2018/05/09
 * desc   :
 * version: 1.0
 */


public class EcxwgOneMenuAdapter extends BaseQuickAdapter<EcOneMenu.DataBean.MenuBean, BaseViewHolder> {

    Context mContext;
    int lastposition = 0;

    public void setLastposition(int lastposition) {
        this.lastposition = lastposition;
    }

    public int getLastposition() {
        return lastposition;
    }

    public EcxwgOneMenuAdapter(@Nullable List<EcOneMenu.DataBean.MenuBean> data, Context context) {
        super(R.layout.item_recycle_horizontal_econe_menu_layout, data);
        mContext=context;
    }

    @Override
    protected void convert(BaseViewHolder helper, EcOneMenu.DataBean.MenuBean item) {
        TextView title = helper.getView(R.id.title);
//        View view = helper.getView(R.id.tabline);
        title.setText(item.getZnName());
        if(item.getSelect()){
//            view.setVisibility(View.VISIBLE);
            title.setTextColor(mContext.getResources().getColor(R.color.toolbar_color));
//            title.setTextSize(17);
        }

        else{
//            view.setVisibility(View.INVISIBLE);
            title.setTextColor(mContext.getResources().getColor(R.color.black));
//            title.setTextSize(15);
        }

        helper.addOnClickListener(R.id.title);
    }

}
