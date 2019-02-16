package net.univr.pushi.jxatmosphere.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;

import net.univr.pushi.jxatmosphere.R;
import net.univr.pushi.jxatmosphere.adapter.GeneforeAdapter;
import net.univr.pushi.jxatmosphere.base.RxLazyFragment;
import net.univr.pushi.jxatmosphere.remote.RetrofitHelper;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * A simple {@link Fragment} subclass.
 */
public class SWZDYLFragment extends RxLazyFragment {

//    @BindView(R.id.content)
//    TextView contentTv;
//    @BindView(R.id.image)
//ImageView image;
//    ProgressDialog progressDialog;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    GeneforeAdapter geneforeAdapter;
    List<String> mData;
    View header;

//    @BindView(R.id.tv_1)
//    TextView tv1;
//    @BindView(R.id.tv_2)
//    TextView tv2;
//    @BindView(R.id.tv_3)
//    TextView tv3;
//    @BindView(R.id.tv_4)
//    TextView tv4;
//    @BindView(R.id.tv_5)
//    TextView tv5;
//    @BindView(R.id.tv_6)
//    TextView tv6;
//    @BindView(R.id.tv_7)
//    TextView tv7;
//
//    @BindView(R.id.tabline1)
//    View tabline1;
//    @BindView(R.id.tabline2)
//    View tabline2;
//    @BindView(R.id.tabline3)
//    View tabline3;
//    @BindView(R.id.tabline4)
//    View tabline4;
//    @BindView(R.id.tabline5)
//    View tabline5;
//    @BindView(R.id.tabline6)
//    View tabline6;
//    @BindView(R.id.tabline7)
//    View tabline7;
//    ViewPager viewPager;
//    List<Fragment> list;

    public SWZDYLFragment() {
    }


    @Override
    public int getLayoutResId() {
        return R.layout.fragment_swzdyl;
    }

    @Override
    public void finishCreateView(Bundle state) {

        getTestdata();
//        tv1.setOnClickListener(this);
//        tv2.setOnClickListener(this);
//        tv3.setOnClickListener(this);
//        tv4.setOnClickListener(this);
//        tv5.setOnClickListener(this);
//        tv6.setOnClickListener(this);
//        tv7.setOnClickListener(this);

    }

//    public static SWZDYLFragment newInstance(ViewPager viewPager, List<Fragment> list) {
//        SWZDYLFragment swzdylFragment = new SWZDYLFragment();
//        swzdylFragment.viewPager = viewPager;
//        swzdylFragment.list = list;
//        return swzdylFragment;
//    }

    private GeneforeAdapter getAdapter() {
        if (geneforeAdapter == null) {
            LayoutInflater inflater = getLayoutInflater();
            header = inflater.inflate(R.layout.item_recycle_genefore_header_layout, null, false);
            LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
            mData = new ArrayList<>();
            geneforeAdapter = new GeneforeAdapter(mData,getContext());
            geneforeAdapter.addHeaderView(header);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(geneforeAdapter);
        }
        return geneforeAdapter;
    }



    public void getTestdata() {
//        progressDialog = ProgressDialog.show(getContext(), "请稍等...", "获取数据中...", true);
//        progressDialog.setCancelable(true);
        RetrofitHelper.getWeatherMonitorAPI()
                .getSWZD()
                .compose(bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(SWZDBeen -> {
//                    progressDialog.dismiss();
//                    String context = SWZDBeen.getData().getTextStr();
//                    String url = SWZDBeen.getData().getUrl();
//                    contentTv.setText(context);
//                    Picasso.with(getContext())
//                            .load(url).placeholder(R.drawable.ic_placeholder).transform(new PicassoTransformation(getContext()))
//                            .into(image);
//                    image.setOnClickListener(v -> {
//                                Intent intent = new Intent(getActivity(), PicDealActivity.class);
//                                intent.putExtra("url", url);
//                                startActivity(intent);
//                            }
//                    );
                    List<String> list=new ArrayList();
                    list.add(SWZDBeen.getData().getUrl());
                    getAdapter().setNewData(list);
                    TextView content = header.findViewById(R.id.content);
                    content.setText(SWZDBeen.getData().getTextStr());
                }, throwable -> {
//                    progressDialog.dismiss();
                    LogUtils.e(throwable);
                    ToastUtils.showShort(getString(R.string.getInfo_error_toast));
                });
    }


//    @Override
//    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.tv_1:
//                viewPager.setCurrentItem(0);
////                ((DMCGJCActivity) getContext()).setIsPalyInit(0);
////                tv1.setTextSize(17);
////                tv1.setTextColor(getResources().getColor(R.color.toolbar_color));
////                tv2.setTextSize(15);
////                tv2.setTextColor(getResources().getColor(R.color.black));
////                tv3.setTextSize(15);
////                tv3.setTextColor(getResources().getColor(R.color.black));
////                tv4.setTextSize(15);
////                tv4.setTextColor(getResources().getColor(R.color.black));
////                tv5.setTextSize(15);
////                tv5.setTextColor(getResources().getColor(R.color.black));
////                tv6.setTextSize(15);
////                tv6.setTextColor(getResources().getColor(R.color.black));
////                tabline1.setVisibility(View.VISIBLE);
////                tabline2.setVisibility(View.INVISIBLE);
////                tabline3.setVisibility(View.INVISIBLE);
////                tabline4.setVisibility(View.INVISIBLE);
////                tabline5.setVisibility(View.INVISIBLE);
////                tabline6.setVisibility(View.INVISIBLE);
//                break;
//            case R.id.tv_2:
//                viewPager.setCurrentItem(1);
////                ((DMCGJCActivity) getContext()).setIsPalyInit(1);
////                tv2.setTextSize(17);
////                tv2.setTextColor(getResources().getColor(R.color.toolbar_color));
////                tv1.setTextSize(15);
////                tv1.setTextColor(getResources().getColor(R.color.black));
////                tv3.setTextSize(15);
////                tv3.setTextColor(getResources().getColor(R.color.black));
////                tv4.setTextSize(15);
////                tv4.setTextColor(getResources().getColor(R.color.black));
////                tv5.setTextSize(15);
////                tv5.setTextColor(getResources().getColor(R.color.black));
////                tv6.setTextSize(15);
////                tv6.setTextColor(getResources().getColor(R.color.black));
////                tabline1.setVisibility(View.INVISIBLE);
////                tabline2.setVisibility(View.VISIBLE);
////                tabline3.setVisibility(View.INVISIBLE);
////                tabline4.setVisibility(View.INVISIBLE);
////                tabline5.setVisibility(View.INVISIBLE);
////                tabline6.setVisibility(View.INVISIBLE);
//                break;
//
//            case R.id.tv_3:
//                viewPager.setCurrentItem(2);
////                ((DMCGJCActivity) getContext()).setIsPalyInit(2);
////                tv3.setTextSize(17);
////                tv3.setTextColor(getResources().getColor(R.color.toolbar_color));
////                tv1.setTextSize(15);
////                tv1.setTextColor(getResources().getColor(R.color.black));
////                tv2.setTextSize(15);
////                tv2.setTextColor(getResources().getColor(R.color.black));
////                tv4.setTextSize(15);
////                tv4.setTextColor(getResources().getColor(R.color.black));
////                tv5.setTextSize(15);
////                tv5.setTextColor(getResources().getColor(R.color.black));
////                tv6.setTextSize(15);
////                tv6.setTextColor(getResources().getColor(R.color.black));
////                tabline1.setVisibility(View.INVISIBLE);
////                tabline2.setVisibility(View.INVISIBLE);
////                tabline3.setVisibility(View.VISIBLE);
////                tabline4.setVisibility(View.INVISIBLE);
////                tabline5.setVisibility(View.INVISIBLE);
////                tabline6.setVisibility(View.INVISIBLE);
//                break;
//            case R.id.tv_4:
//                viewPager.setCurrentItem(3);
////                ((DMCGJCActivity) getContext()).setIsPalyInit(3);
////                tv4.setTextSize(17);
////                tv4.setTextColor(getResources().getColor(R.color.toolbar_color));
////                tv2.setTextSize(15);
////                tv2.setTextColor(getResources().getColor(R.color.black));
////                tv3.setTextSize(15);
////                tv3.setTextColor(getResources().getColor(R.color.black));
////                tv1.setTextSize(15);
////                tv1.setTextColor(getResources().getColor(R.color.black));
////                tv5.setTextSize(15);
////                tv5.setTextColor(getResources().getColor(R.color.black));
////                tv6.setTextSize(15);
////                tv6.setTextColor(getResources().getColor(R.color.black));
////                tabline1.setVisibility(View.INVISIBLE);
////                tabline2.setVisibility(View.INVISIBLE);
////                tabline3.setVisibility(View.INVISIBLE);
////                tabline4.setVisibility(View.VISIBLE);
////                tabline5.setVisibility(View.INVISIBLE);
////                tabline6.setVisibility(View.INVISIBLE);
//                break;
//            case R.id.tv_5:
//                viewPager.setCurrentItem(4);
////                ((DMCGJCActivity) getContext()).setIsPalyInit(4);
////                tv5.setTextSize(17);
////                tv5.setTextColor(getResources().getColor(R.color.toolbar_color));
////                tv1.setTextSize(15);
////                tv1.setTextColor(getResources().getColor(R.color.black));
////                tv2.setTextSize(15);
////                tv2.setTextColor(getResources().getColor(R.color.black));
////                tv3.setTextSize(15);
////                tv3.setTextColor(getResources().getColor(R.color.black));
////                tv4.setTextSize(15);
////                tv4.setTextColor(getResources().getColor(R.color.black));
////                tv6.setTextSize(15);
////                tv5.setTextColor(getResources().getColor(R.color.black));
////                tabline1.setVisibility(View.INVISIBLE);
////                tabline2.setVisibility(View.INVISIBLE);
////                tabline3.setVisibility(View.INVISIBLE);
////                tabline4.setVisibility(View.INVISIBLE);
////                tabline5.setVisibility(View.VISIBLE);
////                tabline6.setVisibility(View.INVISIBLE);
//                break;
//            case R.id.tv_6:
//                viewPager.setCurrentItem(5);
//            case R.id.tv_7:
//                viewPager.setCurrentItem(6);
////                ((DMCGJCActivity) getContext()).setIsPalyInit(5);
////                tv6.setTextSize(17);
////                tv6.setTextColor(getResources().getColor(R.color.toolbar_color));
////                tv1.setTextSize(15);
////                tv1.setTextColor(getResources().getColor(R.color.black));
////                tv2.setTextSize(15);
////                tv2.setTextColor(getResources().getColor(R.color.black));
////                tv3.setTextSize(15);
////                tv3.setTextColor(getResources().getColor(R.color.black));
////                tv4.setTextSize(15);
////                tv4.setTextColor(getResources().getColor(R.color.black));
////                tv5.setTextSize(15);
////                tv5.setTextColor(getResources().getColor(R.color.black));
////
////                tabline1.setVisibility(View.INVISIBLE);
////                tabline2.setVisibility(View.INVISIBLE);
////                tabline3.setVisibility(View.INVISIBLE);
////                tabline4.setVisibility(View.INVISIBLE);
////                tabline5.setVisibility(View.INVISIBLE);
////                tabline6.setVisibility(View.VISIBLE);
//                break;
//        }
//
//    }
//
//    public void setIsPalyInit() {
//        for (int i = 1; i < list.size(); i++) {
//            ((DMCGJCFragment) list.get(i)).setStart(false);
//            ((DMCGJCFragment) list.get(i)).setImage();
//        }
//    }
}
