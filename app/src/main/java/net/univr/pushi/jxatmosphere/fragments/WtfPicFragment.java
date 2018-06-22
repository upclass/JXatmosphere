package net.univr.pushi.jxatmosphere.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.squareup.picasso.Picasso;

import net.univr.pushi.jxatmosphere.R;
import net.univr.pushi.jxatmosphere.adapter.WtfNewInfoAdapter;
import net.univr.pushi.jxatmosphere.base.RxLazyFragment;
import net.univr.pushi.jxatmosphere.feature.PicDealActivity;
import net.univr.pushi.jxatmosphere.remote.RetrofitHelper;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * A simple {@link Fragment} subclass.
 */
public class WtfPicFragment extends RxLazyFragment {

    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.pic)
    ImageView pic;
    WtfNewInfoAdapter mAdapter;
    WtfNewInfoAdapter mAdapter1;
    String type;
    String date;
    WtfRapidFragment wtfRapidFragment;
    EcxwgFragment ecxwgFragment;
    //判断当前是否点击就取消
    boolean isCancle;
    private static ArrayList<String> temp;


    public static WtfPicFragment newInstance(String url, String type, WtfRapidFragment wtfRapidFragment, List<String> urls) {
        WtfPicFragment wtfPicFragment = new WtfPicFragment();
        Bundle bundle = new Bundle();
        bundle.putString("url", url);
        bundle.putString("type", type);
        temp = new ArrayList<>();
        for (int i = 0; i < urls.size(); i++) {
            temp.add(urls.get(i));
        }
        bundle.putStringArrayList("urls", temp);
        wtfPicFragment.wtfRapidFragment = wtfRapidFragment;
        wtfPicFragment.setArguments(bundle);
        return wtfPicFragment;
    }


    public static WtfPicFragment newInstance(String url, String type, EcxwgFragment ecxwgFragment, List<String> urls) {
        WtfPicFragment wtfPicFragment = new WtfPicFragment();
        Bundle bundle = new Bundle();
        bundle.putString("url", url);
        bundle.putString("type", type);
        temp = new ArrayList<>();
        for (int i = 0; i < urls.size(); i++) {
            temp.add(urls.get(i));
        }
        bundle.putStringArrayList("urls", temp);
        wtfPicFragment.ecxwgFragment = ecxwgFragment;
        wtfPicFragment.setArguments(bundle);
        return wtfPicFragment;
    }


    @Override
    public int getLayoutResId() {
        return R.layout.fragment_wtf_pic;
    }

    @Override
    public void finishCreateView(Bundle state) {

        String url = "";
        if (getArguments() != null) {
            //取出保存的值
            url = getArguments().getString("url");
            temp = getArguments().getStringArrayList("urls");
            type = getArguments().getString("type");
        }
        Picasso.with(getActivity()).load(url).placeholder(R.drawable.app_imageplacehold).into(pic);
        String finalUrl = url;
        pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), PicDealActivity.class);
                intent.putExtra("url", finalUrl);
                intent.putStringArrayListExtra("urls", temp);
                startActivity(intent);
            }
        });
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isCancle == true) {
                    recyclerView.setVisibility(View.GONE);
                    fab.setImageResource(R.drawable.map_time);
                    isCancle = false;
                } else
                    getOneMenu();
            }
        });
    }

    private void getOneMenu() {
        getAdapter();
        RetrofitHelper.getDataForecastAPI()
                .getDataForecastContentBytime(type)
                .compose(bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(timeOneMenu -> {
                    isCancle = true;
                    fab.setImageResource(R.drawable.map_error);
                    List<String> data = timeOneMenu.getData();
                    mAdapter.setNewData(data);
                    if (mAdapter.getData() != null && mAdapter.getData().size() > 0)
                        recyclerView.setVisibility(View.VISIBLE);
                }, throwable -> {
                    LogUtils.e(throwable);
                    ToastUtils.showShort(getString(R.string.getInfo_error_toast));
                });
    }

    private WtfNewInfoAdapter getAdapter() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        List<String> mData = new ArrayList<>();
        mAdapter = new WtfNewInfoAdapter(mData);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mAdapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            date = ((WtfNewInfoAdapter) adapter).getData().get(position);
            getTwoMenu();
        });
//        mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
//            @Override
//            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
//                if(view==getAdapter().getViewByPosition(recyclerView,)){
//
//                }
//                date = ((WtfNewInfoAdapter) adapter).getData().get(position);
//                getTwoMenu();
//            }
//        });

        return mAdapter;

    }

    private WtfNewInfoAdapter getAdapter1() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        List<String> mData = new ArrayList<>();
        mAdapter1 = new WtfNewInfoAdapter(mData);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mAdapter1);
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
//        mAdapter1.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
//                String time = ((WtfNewInfoAdapter) adapter).getData().get(position);
//                if(wtfRapidFragment!=null){
//                    wtfRapidFragment.getTestDataBytime(time);
//                }else{
//                    ecxwgFragment.getTestdataByTime(time);
//                }
//
//                recyclerView.setVisibility(View.GONE);
//                isCancle=false;
//            }
//        });


        return mAdapter1;

    }

    private void getTwoMenu() {
        getAdapter1();
        RetrofitHelper.getDataForecastAPI()
                .getDataForecastContentBytime1(type, date)
                .compose(bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(timeTwoMenu -> {
                    List<String> data = timeTwoMenu.getData();
                    mAdapter1.setNewData(data);
                    if (mAdapter1.getData() != null && mAdapter1.getData().size() > 0) ;
                    else recyclerView.setVisibility(View.GONE);
                }, throwable -> {
                    LogUtils.e(throwable);
                    ToastUtils.showShort(getString(R.string.getInfo_error_toast));
                });
    }

}
