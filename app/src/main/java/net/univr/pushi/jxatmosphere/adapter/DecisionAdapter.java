package net.univr.pushi.jxatmosphere.adapter;

import android.support.annotation.Nullable;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import net.univr.pushi.jxatmosphere.R;
import net.univr.pushi.jxatmosphere.beens.DecisionBeen1;

import java.util.List;

/**
 * author : Administrator wl
 * e-mail : 389456264@qq.com
 * time   : 2019/02/01
 * desc   :
 * version: 1.0
 */


public class DecisionAdapter extends BaseQuickAdapter<DecisionBeen1.DataBean, BaseViewHolder> {
    public DecisionAdapter(@Nullable List<DecisionBeen1.DataBean> data) {
        super(R.layout.decision_item, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, DecisionBeen1.DataBean item) {
        ((TextView) helper.getView(R.id.title)).setText(item.getTitle());
        ((TextView) helper.getView(R.id.issue)).setText(item.getIssue());
        ((TextView) helper.getView(R.id.date)).setText(item.getDate());
        helper.addOnClickListener(R.id.content);
    }
}
