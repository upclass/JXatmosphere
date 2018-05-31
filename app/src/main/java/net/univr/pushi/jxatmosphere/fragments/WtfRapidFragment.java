package net.univr.pushi.jxatmosphere.fragments;


import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;

import net.univr.pushi.jxatmosphere.MyApplication;
import net.univr.pushi.jxatmosphere.R;
import net.univr.pushi.jxatmosphere.adapter.DmcgjcAdapter3;
import net.univr.pushi.jxatmosphere.adapter.DmcgjcMenuAdapter;
import net.univr.pushi.jxatmosphere.adapter.MyPagerAdapter;
import net.univr.pushi.jxatmosphere.base.RxLazyFragment;
import net.univr.pushi.jxatmosphere.beens.DmcgjcmenuBeen;
import net.univr.pushi.jxatmosphere.beens.EcOneMenu;
import net.univr.pushi.jxatmosphere.beens.GkdmClickBeen;
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
public class WtfRapidFragment extends RxLazyFragment {


    @BindView(R.id.recycler1)
    RecyclerView mRecyclerView1;
    @BindView(R.id.viepager)
    CustomViewPager mViewPager;
    @BindView(R.id.recycler3)
    RecyclerView mRecyclerView3;

    @BindView(R.id.pic_ready)
    ImageView isStartPic;


    private Context mcontext;

    private DmcgjcMenuAdapter mAdapter1;

    MyPagerAdapter viewPagerAdapter;
    List<Fragment> fragmentList = new ArrayList<>();
    List<String> urls;

    private DmcgjcAdapter3 mAdapter3;
    List<GkdmClickBeen> mData3 = new ArrayList<>();
    String type;
    String ctype;
    //播放的下一位置
    int recycle_skipto_position = 1;
    //是否播放
    Boolean isStart = false;

    //当前的位置
    int now_postion;
    ProgressDialog progressDialog;


    public static WtfRapidFragment newInstance(String type, String ctype) {
        WtfRapidFragment wtfRapidFragment = new WtfRapidFragment();
        Bundle bundle = new Bundle();
        bundle.putString("type", type);
        bundle.putString("ctype", ctype);
        wtfRapidFragment.setArguments(bundle);
        return wtfRapidFragment;
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
        return R.layout.fragment_wtf_rapid;
    }

    @Override
    public void finishCreateView(Bundle state) {

        mcontext = getActivity();

        if (getArguments() != null) {
            //取出保存的值
            type = getArguments().getString("type");
            ctype = getArguments().getString("ctype");
        }
        progressDialog = ProgressDialog.show(getContext(), "请稍等...", "获取数据中...", true);
        progressDialog.setCancelable(true);
        getAdapter1();
        RetrofitHelper.getDataForecastAPI()
                .getOneMenu(type)
                .compose(bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(wtfRapidBeen -> {
                    List<DmcgjcmenuBeen.DataBean> data = new ArrayList<>();
                    List<EcOneMenu.DataBean.MenuBean> menu = wtfRapidBeen.getData().getMenu();
                    for (int i = 0; i < menu.size(); i++) {
                        DmcgjcmenuBeen.DataBean temp = new DmcgjcmenuBeen.DataBean();
                        if (i == 0) {
                            temp.setSelect(true);
                        } else {
                            temp.setSelect(false);
                        }
                        temp.setZnName(menu.get(i).getZnName());
                        temp.setType(menu.get(i).getType());
                        temp.setPaname(menu.get(i).getPaname());
                        temp.setName(menu.get(i).getName());
                        temp.setId(menu.get(i).getId());
                        data.add(temp);
                    }
                    getAdapter1().setNewData(data);
                }, throwable -> {
                    LogUtils.e(throwable);
                    ToastUtils.showShort(getString(R.string.getInfo_error_toast));
                });

        getTestdata();

        isStartPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isStart == false) {
                    isStartPic.setImageResource(R.drawable.app_end);
                    Message message = uiHandler.obtainMessage();
                    message.what = 1;
                    uiHandler.sendMessageDelayed(message,MyApplication.getInstance().auto_time);
                    isStart = true;
                    mViewPager.setScanScroll(false);
                } else {
                    uiHandler.removeCallbacksAndMessages(null);
                    isStartPic.setImageResource(R.drawable.app_start);
                    isStart = false;
                    mViewPager.setScanScroll(true);
                }

            }
        });
    }

    private DmcgjcMenuAdapter getAdapter1() {
        if (mAdapter1 == null) {
            LinearLayoutManager layoutManager = new LinearLayoutManager(mcontext, LinearLayoutManager.HORIZONTAL, false);
            List<DmcgjcmenuBeen.DataBean> mData1 = new ArrayList<>();

            mAdapter1 = new DmcgjcMenuAdapter(mData1);
            mRecyclerView1.setLayoutManager(layoutManager);
            mRecyclerView1.setAdapter(mAdapter1);
            mAdapter1.setOnItemChildClickListener((adapter, view, position) -> {
                isStart = false;
                mViewPager.setScanScroll(true);
                isStartPic.setImageResource(R.drawable.app_start);

                List<DmcgjcmenuBeen.DataBean> data = adapter.getData();
                int lastclick = ((DmcgjcMenuAdapter) adapter).getLastposition();
                DmcgjcmenuBeen.DataBean dataBeanlasted = data.get(lastclick);
                DmcgjcmenuBeen.DataBean dataBean = data.get(position);
                dataBeanlasted.setSelect(false);
                dataBean.setSelect(true);
                adapter.notifyItemChanged(lastclick);
                adapter.notifyItemChanged(position);
                ((DmcgjcMenuAdapter) adapter).setLastposition(position);

                TextView title = ((TextView) view);
                String menu = title.getText().toString();
                if (menu.equals("1小时降水预报")) {
                    ctype = "Precipitation_1h";
                }
                if (menu.equals("3小时降水预报")) {
                    ctype = "Precipitation_3h";
                }


                if (menu.equals("6小时降水预报")) {
                    ctype = "Precipitation_6h";
                }
                if (menu.equals("12小时降水预报")) {
                    ctype = "Precipitation_12h";
                }
                if (menu.equals("24小时降水预报")) {
                    ctype = "Precipitation_24h";
                }
                if (menu.equals("对流有效位能")) {
                    ctype = "Cape";
                }
                if (menu.equals("对流抑制")) {
                    ctype = "Cin";
                }
                if (menu.equals("1小时冰雹直径")) {
                    ctype = "Hail_1h";
                }
                if (menu.equals("最大雷达回波反射率")) {
                    ctype = "MaxDBZ";
                }
                if (menu.equals("整层水汽含量")) {
                    ctype = "PW";
                }
                if (menu.equals("地面2米温度")) {
                    ctype = "SurfaceTemperature";
                }

                if (menu.equals("地面风")) {
                    ctype = "SurfaceWind";
                }
                if (menu.equals("850百帕温度")) {
                    ctype = "Temperature_850hPa";
                }
                if (menu.equals("925百帕温度")) {
                    ctype = "Temperature_925hPa";
                }
                if (menu.equals("850百帕风场")) {
                    ctype = "Wind_850hPa";
                }
                if (menu.equals("925百帕风场")) {
                    ctype = "Wind_925hPa";
                }

                progressDialog = ProgressDialog.show(getContext(), "请稍等...", "获取数据中...", true);
                progressDialog.setCancelable(true);
                getTestdata();
            });
        }
        return mAdapter1;
    }


    private DmcgjcAdapter3 getAdapter3() {
        if (mAdapter3 == null) {
            ExStaggeredGridLayoutManager layoutManager = new ExStaggeredGridLayoutManager(6, StaggeredGridLayoutManager.VERTICAL){
                @Override
                public boolean canScrollVertically() {
                    return  false;
                }
            };
            mAdapter3 = new DmcgjcAdapter3(mData3);
            mRecyclerView3.setLayoutManager(layoutManager);
            mRecyclerView3.setAdapter(mAdapter3);
            mAdapter3.setOnItemChildClickListener((adapter, view, position) -> {
                switch (view.getId()) {
                    case R.id.time:
                        if (isStart == false) {
                            mViewPager.setCurrentItem(position);
                            //事件处理
                            List data = adapter.getData();
                            GkdmClickBeen clickBeenBefore = (GkdmClickBeen) (data.get(now_postion));
                            clickBeenBefore.setOnclick(false);
                            recycle_skipto_position = position + 1;
                            if (recycle_skipto_position > mData3.size() - 1)
                                recycle_skipto_position = 0;
                            GkdmClickBeen clickBeenNow = (GkdmClickBeen) (data.get(position));
                            clickBeenNow.setOnclick(true);
                            mData3.set(now_postion, clickBeenBefore);
                            mData3.set(position, clickBeenNow);
                            adapter.notifyItemChanged(now_postion);
                            adapter.notifyItemChanged(position);
                        }
                        break;
                }
            });
        }
        return mAdapter3;
    }


    public void getTestdata() {
        getAdapter3();
        RetrofitHelper.getDataForecastAPI()
                .getEcContent1(type, ctype)
                .compose(bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(ecBeen -> {
                    progressDialog.dismiss();
                    recycle_skipto_position = 1;
                    now_postion = 0;

                    List<Fragment> HuancunfragmentList = new ArrayList<>();
                    for (int i = 0; i < fragmentList.size(); i++) {
                        Fragment fragment = fragmentList.get(i);
                        HuancunfragmentList.add(fragment);
                    }
                    fragmentList.clear();
                    urls = ecBeen.getData().getUrl();
                    for (int i = 0; i < urls.size(); i++) {
                        WtfPicFragment fragment = WtfPicFragment.newInstance(urls.get(i),type,this);
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
                            GkdmClickBeen clickBeenStop = mData3.get(now_postion);
                            clickBeenStop.setOnclick(false);
                            GkdmClickBeen clickBeenNow = mData3.get(position);
                            clickBeenNow.setOnclick(true);
                            mData3.set(now_postion, clickBeenStop);
                            mData3.set(position, clickBeenNow);
                            mAdapter3.notifyItemChanged(now_postion);
                            mAdapter3.notifyItemChanged(position);
                            now_postion = position;
                            mRecyclerView3.smoothScrollToPosition(position);
                            recycle_skipto_position = position + 1;
                            if (recycle_skipto_position > mData3.size() - 1)
                                recycle_skipto_position = 0;
                        }

                        @Override
                        public void onPageScrollStateChanged(int state) {

                        }
                    });


                    List<String> time = ecBeen.getData().getTime();
                    mData3.clear();
                    for (int i = 0; i < time.size(); i++) {
                        GkdmClickBeen clickBeen = new GkdmClickBeen();
                        if (i == 0)
                            clickBeen.setOnclick(true);
                        else clickBeen.setOnclick(false);
                        clickBeen.setText(time.get(i));
                        mData3.add(clickBeen);
                    }
                    getAdapter3().setNewData(mData3);
                    //播放轮播

                }, throwable -> {
                    progressDialog.dismiss();
                    LogUtils.e(throwable);
                    ToastUtils.showShort(getString(R.string.getInfo_error_toast));
                });
    }


    public void getTestDataBytime(String timePs) {
        getAdapter3();
        RetrofitHelper.getDataForecastAPI()
                .getEcContent2(type, ctype,timePs)
                .compose(bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(ecBeen -> {
                    recycle_skipto_position = 1;
                    now_postion = 0;

                    List<Fragment> HuancunfragmentList = new ArrayList<>();
                    for (int i = 0; i < fragmentList.size(); i++) {
                        Fragment fragment = fragmentList.get(i);
                        HuancunfragmentList.add(fragment);
                    }
                    fragmentList.clear();
                    urls = ecBeen.getData().getUrl();
                    for (int i = 0; i < urls.size(); i++) {
                        WtfPicFragment fragment = WtfPicFragment.newInstance(urls.get(i),type,this);
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
                            GkdmClickBeen clickBeenStop = mData3.get(now_postion);
                            clickBeenStop.setOnclick(false);
                            GkdmClickBeen clickBeenNow = mData3.get(position);
                            clickBeenNow.setOnclick(true);
                            mData3.set(now_postion, clickBeenStop);
                            mData3.set(position, clickBeenNow);
                            mAdapter3.notifyItemChanged(now_postion);
                            mAdapter3.notifyItemChanged(position);
                            now_postion = position;
                            mRecyclerView3.smoothScrollToPosition(position);
                            recycle_skipto_position = position + 1;
                            if (recycle_skipto_position > mData3.size() - 1)
                                recycle_skipto_position = 0;
                        }

                        @Override
                        public void onPageScrollStateChanged(int state) {

                        }
                    });


                    List<String> time = ecBeen.getData().getTime();
                    mData3.clear();
                    for (int i = 0; i < time.size(); i++) {
                        GkdmClickBeen clickBeen = new GkdmClickBeen();
                        if (i == 0)
                            clickBeen.setOnclick(true);
                        else clickBeen.setOnclick(false);
                        clickBeen.setText(time.get(i));
                        mData3.add(clickBeen);
                    }
                    getAdapter3().setNewData(mData3);
                    //播放轮播

                }, throwable -> {
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
                        mViewPager.setCurrentItem(recycle_skipto_position);
                        if (recycle_skipto_position > mData3.size() - 1) {
                            recycle_skipto_position = 0;
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



}



