package net.univr.pushi.jxatmosphere.fragments;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.ImageView;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;

import net.univr.pushi.jxatmosphere.MyApplication;
import net.univr.pushi.jxatmosphere.R;
import net.univr.pushi.jxatmosphere.adapter.MultiGdybTxAdapterForDmcgjc;
import net.univr.pushi.jxatmosphere.adapter.MyPagerAdapter;
import net.univr.pushi.jxatmosphere.base.RxLazyFragment;
import net.univr.pushi.jxatmosphere.beens.GkdmClickBeen;
import net.univr.pushi.jxatmosphere.beens.MultiItemGdybTx;
import net.univr.pushi.jxatmosphere.interfaces.CallBackUtil;
import net.univr.pushi.jxatmosphere.remote.RetrofitHelper;
import net.univr.pushi.jxatmosphere.utils.ExStaggeredGridLayoutManager;
import net.univr.pushi.jxatmosphere.widget.CustomViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * A simple {@link Fragment} subclass.
 */
public class DMCGJCFragment extends RxLazyFragment implements View.OnClickListener {

    @BindView(R.id.viepager)
    CustomViewPager mViewPager;
    @BindView(R.id.recycler3)
    RecyclerView mRecyclerView3;


    MyPagerAdapter viewPagerAdapter;
    List<Fragment> fragmentList = new ArrayList<>();
    List<String> urls;


    public String type;
    public String ctype;

    //当前的位置
    int now_postion;
    //播放的下一位置
    int recycle_skipto_position;

    //是否播放
    Boolean isStart = false;


    ProgressDialog progressDialog;
    private MultiGdybTxAdapterForDmcgjc mAdapter3;
    List<MultiItemGdybTx> multitemList = new ArrayList<>();
    ImageView isStartPic;



    List<Fragment> list;
    public String interval = "5";

    public static DMCGJCFragment newInstance(String type, String ctype) {
        DMCGJCFragment dmcgjcFragment = new DMCGJCFragment();
        Bundle bundle = new Bundle();
        bundle.putString("type", type);
        bundle.putString("ctype", ctype);
        dmcgjcFragment.setArguments(bundle);
        return dmcgjcFragment;
    }


    public void setStart(Boolean start) {
        isStart = start;
        if (mViewPager != null) {
            mViewPager.setScanScroll(true);
        }

    }

    public void setImage() {
        if (isStartPic != null)
            isStartPic.setImageResource(R.drawable.app_start);
    }


    @Override
    public int getLayoutResId() {
        return R.layout.fragment_dmcgjc;
    }


    @Override
    public void finishCreateView(Bundle state) {


        if (getArguments() != null) {
            //取出保存的值
            type = getArguments().getString("type");
            ctype = getArguments().getString("ctype");
        }
        getTestdata();
    }


    private MultiGdybTxAdapterForDmcgjc getAdapter3() {
        if (mAdapter3 == null) {
            ExStaggeredGridLayoutManager layoutManager = new ExStaggeredGridLayoutManager(8, StaggeredGridLayoutManager.VERTICAL) {
                @Override
                public boolean canScrollVertically() {
                    return false;
                }
            };
            mAdapter3 = new MultiGdybTxAdapterForDmcgjc(multitemList);
            mRecyclerView3.setLayoutManager(layoutManager);
            mRecyclerView3.setAdapter(mAdapter3);
            mAdapter3.setOnItemChildClickListener((adapter, view, position) -> {
                switch (view.getId()) {
                    case R.id.time:
                        if (isStart == false) {
                            mViewPager.setCurrentItem(position - 1);
                        }
                        break;
                    case R.id.pic_ready:
                        if (isStart == false) {
                            isStartPic = ((ImageView) view);
                            isStartPic.setImageResource(R.drawable.app_end);
                            Message message = uiHandler.obtainMessage();
                            message.what = 1;
                            uiHandler.sendMessageDelayed(message, MyApplication.getInstance().auto_time);
                            isStart = true;
                            mViewPager.setScanScroll(false);
                        } else {
                            uiHandler.removeCallbacksAndMessages(null);
                            isStartPic.setImageResource(R.drawable.app_start);
                            isStart = false;
                            mViewPager.setScanScroll(true);
                        }

                        break;

                }
            });
        }
        return mAdapter3;
    }


    public void getTestdata() {
        progressDialog = ProgressDialog.show(getContext(), "请稍等...", "获取数据中...", true);
        progressDialog.setCancelable(true);
        getAdapter3();
        RetrofitHelper.getWeatherMonitorAPI()
                .getDmcgjc(type, ctype, interval)
                .compose(bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(DmcgjcDeen -> {
                    progressDialog.dismiss();
                    int vis = 0;
                    if (ctype.equals("rain_sum") || ctype.equals("rain_sum1")
                            || ctype.equals("rain_sum3") || ctype.equals("rain_sum6")
                            || ctype.equals("rain_sum12") || ctype.equals("temp")
                            || ctype.equals("body_feeling_temp")
                            || ctype.equals("vis_min")
                            || ctype.equals("wind_2minute_avg")
                            || ctype.equals("wind_10minute_avg")
                            || ctype.equals("wind_inst")
                            || ctype.equals("wind_inst_max")
                            || ctype.equals("wind_max_5")
                            || ctype.equals("wind_1minute_avg")
                            || ctype.equals("humidity") || ctype.equals("pressure")
                            || ctype.equals("surface_temp")) {
                        vis = 1;
                    }
                    recycle_skipto_position = 1;
                    now_postion = DmcgjcDeen.getData().getUrls().size();
                    isStart = false;
                    uiHandler.removeCallbacksAndMessages(null);
                    if (isStartPic != null) {
                        isStartPic.setImageResource(R.drawable.app_start);
                        mViewPager.setScanScroll(true);
                    }

                    List<Fragment> HuancunfragmentList = new ArrayList<>();
                    for (int i = 0; i < fragmentList.size(); i++) {
                        Fragment fragment = fragmentList.get(i);
                        HuancunfragmentList.add(fragment);
                    }
                    fragmentList.clear();
                    urls = DmcgjcDeen.getData().getUrls();
                    for (int i = 0; i < urls.size(); i++) {
                        DmcgjcPicFragment fragment = DmcgjcPicFragment.newInstance(urls.get(i), urls, this, vis, interval, "dmcgjc/" + type + "/" + ctype);
                        fragmentList.add(fragment);
                    }
                    viewPagerAdapter = new MyPagerAdapter(
                            getChildFragmentManager(), fragmentList, HuancunfragmentList);
                    // 绑定适配器
                    mViewPager.setAdapter(viewPagerAdapter);
                    mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                        @Override
                        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                        }

                        @Override
                        public void onPageSelected(int position) {
                            if (CallBackUtil.picdispath != null) {
                                CallBackUtil.doDispathPic(position);
                            }
                            MultiItemGdybTx multiItemGdybTxStop = multitemList.get(now_postion);
                            GkdmClickBeen clickBeenStop = multiItemGdybTxStop.getContent();
                            clickBeenStop.setOnclick(false);
                            multiItemGdybTxStop.setContent(clickBeenStop);
                            MultiItemGdybTx multiItemGdybTxNow = multitemList.get(position + 1);
                            GkdmClickBeen clickBeenNow = multiItemGdybTxNow.getContent();
                            clickBeenNow.setOnclick(true);
                            multiItemGdybTxNow.setContent(clickBeenNow);
                            multitemList.set(now_postion, multiItemGdybTxStop);
                            multitemList.set(position + 1, multiItemGdybTxNow);
                            mAdapter3.notifyItemChanged(now_postion);
                            mAdapter3.notifyItemChanged(position + 1);
                            now_postion = position + 1;
                            mRecyclerView3.smoothScrollToPosition(position + 1);
                            recycle_skipto_position = position + 2;
                            if (recycle_skipto_position > multitemList.size() - 1)
                                recycle_skipto_position = 1;
                        }

                        @Override
                        public void onPageScrollStateChanged(int state) {

                        }
                    });


                    List<String> time = DmcgjcDeen.getData().getTimes();
                    multitemList.clear();
                    MultiItemGdybTx multiItemGdybTx = new MultiItemGdybTx(MultiItemGdybTx.IMG, R.drawable.app_start);
                    multitemList.add(multiItemGdybTx);
                    for (int i = 0; i < time.size(); i++) {
                        GkdmClickBeen clickBeen = new GkdmClickBeen();
                        if (i == time.size() - 1)
                            clickBeen.setOnclick(true);
                        else clickBeen.setOnclick(false);
                        clickBeen.setText(time.get(i));

                        multiItemGdybTx = new MultiItemGdybTx(MultiItemGdybTx.TIME_TEXT, clickBeen);
                        multitemList.add(multiItemGdybTx);
                    }
                    getAdapter3().setNewData(multitemList);
                    mViewPager.setCurrentItem(time.size() - 1);

                    //播放轮播

                }, throwable -> {
                    progressDialog.dismiss();
                    LogUtils.e(throwable);
                    ToastUtils.showShort(getString(R.string.getInfo_error_toast));
                });
    }


    private Handler uiHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (mRecyclerView3 != null) {
                switch (msg.what) {
                    case 1:
                        mViewPager.setCurrentItem(recycle_skipto_position - 1);
                        if (recycle_skipto_position > multitemList.size() - 1) {
                            recycle_skipto_position = 1;
                            Message message = uiHandler.obtainMessage();
                            message.what = 1;
                            if (isStart == false) {
                            } else {
                                uiHandler.sendMessageDelayed(message, MyApplication.getInstance().auto_time);
                            }
                        } else {
                            Message message = uiHandler.obtainMessage();
                            message.what = 1;
                            if (isStart == false) {

                            } else {
                                uiHandler.sendMessageDelayed(message, MyApplication.getInstance().auto_time);
                            }
                        }
                        break;
                    default:
                        break;
                }
            } else {

            }

        }

    };

    @Override
    public void onDestroy() {
        super.onDestroy();
        uiHandler.removeCallbacksAndMessages(null);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        }

    }




    public void setIsPalyInit(int current) {
        for (int i = 0; i < list.size(); i++) {
            if (current == i) ;
            else {
                ((DMCGJCFragment) list.get(i)).setStart(false);
                ((DMCGJCFragment) list.get(i)).setImage();
            }
        }
    }
}
