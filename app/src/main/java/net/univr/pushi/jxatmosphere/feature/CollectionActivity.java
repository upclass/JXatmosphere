package net.univr.pushi.jxatmosphere.feature;


import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;

import net.univr.pushi.jxatmosphere.R;
import net.univr.pushi.jxatmosphere.adapter.RecycleSlipAdapter;
import net.univr.pushi.jxatmosphere.base.BaseActivity;
import net.univr.pushi.jxatmosphere.beens.CollectionListBeen;
import net.univr.pushi.jxatmosphere.remote.RetrofitHelper;
import net.univr.pushi.jxatmosphere.utils.PicUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class CollectionActivity extends BaseActivity implements RecycleSlipAdapter.IonSlidingViewClickListener {

    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.collection_recycle)
    RecyclerView collectionRecycle;
    RecycleSlipAdapter collectionAdapter;
    ProgressDialog progressDialog;
    @BindView(R.id.reload)
    ImageView reload;

    List<CollectionListBeen.DataBean> mDatas;


    @Override
    public int getLayoutId() {
        return R.layout.activity_collection;
    }


    @Override
    public void initViews(Bundle savedInstanceState) {
        super.initViews(savedInstanceState);
        getAdapter();
        getCollectionList();
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        reload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getCollectionList();
            }
        });
    }

    RecycleSlipAdapter getAdapter() {
        if (collectionAdapter == null) {
            mDatas = new ArrayList<>();
            collectionAdapter = new RecycleSlipAdapter(context, mDatas);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
            collectionRecycle.setAdapter(collectionAdapter);
            collectionRecycle.setLayoutManager(linearLayoutManager);
            collectionRecycle.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        }
        return collectionAdapter;
    }

    public void getCollectionList() {
        progressDialog = ProgressDialog.show(context, "请稍等...", "获取数据中...", true);
        progressDialog.setCancelable(true);
        RetrofitHelper.getUserAPI()
                .collectionList(SPUtils.getInstance().getString("userId"))
                .compose(bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(collectionListBeen -> {
                    mDatas.clear();
                    for (int i = 0; i < collectionListBeen.getData().size(); i++) {
                        mDatas.add(collectionListBeen.getData().get(i));
                    }
                    getAdapter().notifyDataSetChanged();
                    progressDialog.dismiss();
                    for (int i = 0; i < mDatas.size(); i++) {
                        String url = mDatas.get(i).getUrl();
                        String type = mDatas.get(i).getType();
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                PicUtils.decodeUriAsBitmapFromNet(url, "collection/" + getPackName(type));
                            }
                        }).start();
                    }

                }, throwable -> {
                    progressDialog.dismiss();
                });
    }

    String getPackName(String name) {
        String ret = null;
        if (name.equals("雷达拼图")) ret = "ldpt/0";
        else if (name.equals("卫星云图")) ret = "ldpt/1";
        else if (name.equals("地面填图")) ret = "gkdm/000";
        else if (name.equals("500百帕高空观测")) ret = "gkdm/500";
        else if (name.equals("700帕高空观测")) ret = "gkdm/700";
        else if (name.equals("850帕高空观测")) ret = "gkdm/850";
        else if (name.equals("925帕高空观测")) ret = "gkdm/925";
        else if (name.equals("回波反射率预报")) ret = "radarForecast/ref";
        else if (name.equals("地质灾害")) ret = "weathWarn/dz";
        else if (name.equals("山洪灾害")) ret = "weathWarn/sh";
        else if (name.equals("中小河流洪水")) ret = "weathWarn/hl";
        else ret = name;
        return ret;

    }


    @Override
    public void onItemClick(View view, int position) {
        CollectionListBeen.DataBean dataBean = mDatas.get(position);
        String url = dataBean.getUrl();
        String pack = "collection/" + getPackName(dataBean.getType());
        int collectId = dataBean.getCollectId();
        Bitmap bitmap = PicUtils.readLocalImageWithouChange(url, pack);
        if (bitmap == null) {
            ToastUtils.showShort("图片正在下载,无法查看大图");
        } else {
            Intent intent = new Intent(CollectionActivity.this, CollectionDetailActivity.class);
            intent.putExtra("url", url);
            intent.putExtra("pack", pack);
            intent.putExtra("collectId", collectId);
            startActivityForResult(intent, 0);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 1) {
            if (requestCode == 0) {
                Boolean refresh = data.getBooleanExtra("refresh", false);
                if (refresh) getCollectionList();
            }
        }
    }


    @Override
    public void onDeleteBtnCilck(View view, int position) {
        CollectionListBeen.DataBean dataBean = mDatas.get(position);
        int collectId = dataBean.getCollectId();
        RetrofitHelper.getUserAPI()
                .deleteCollection(collectId)
                .compose(bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(collectionRet -> {
                    String errcode = collectionRet.getErrcode();
                    if (errcode.equals("0")) {
                        showShortToast("删除成功");
                        collectionAdapter.removeData(position);
                    } else
                        showShortToast("服务异常");
                }, throwable -> {
                });

    }

    private void showShortToast(String message) {
        ToastUtils.showShort(message);
        ToastUtils.setBgColor(Color.parseColor("#f6f6f6"));
        ToastUtils.setMsgColor(getResources().getColor(R.color.yujin_black));
    }
}
