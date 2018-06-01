package net.univr.pushi.jxatmosphere.feature;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;

import net.univr.pushi.jxatmosphere.R;
import net.univr.pushi.jxatmosphere.adapter.YunjinAdapter;
import net.univr.pushi.jxatmosphere.base.BaseActivity;
import net.univr.pushi.jxatmosphere.beens.YuJinXinhaoBeen;
import net.univr.pushi.jxatmosphere.remote.RetrofitHelper;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class YujingActivity extends BaseActivity {
    @BindView(R.id.yujinRecycle)
    RecyclerView mRecyclerView;
    YunjinAdapter mAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_yujing;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        super.initViews(savedInstanceState);
        initRecycleView();
        getYujinInfo();
    }

    private void initRecycleView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        List<YuJinXinhaoBeen.DataBean> dataBeans = new ArrayList<>();
        mAdapter = new YunjinAdapter(dataBeans, context);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }

    private void getYujinInfo() {
        RetrofitHelper.getForecastWarn()
                .getYujinInfo()
                .compose(bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(yunjinInfo -> {
                    List<YuJinXinhaoBeen.DataBean> data = yunjinInfo.getData();
                    mAdapter.setNewData(data);
                }, throwable -> {
                    LogUtils.e(throwable);
                    ToastUtils.showShort(getString(R.string.getInfo_error_toast));
                });
    }


}
