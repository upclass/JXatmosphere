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
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;

import net.univr.pushi.jxatmosphere.MyApplication;
import net.univr.pushi.jxatmosphere.R;
import net.univr.pushi.jxatmosphere.adapter.DmcgjcMenuAdapter;
import net.univr.pushi.jxatmosphere.adapter.MultiGdybTxAdapter;
import net.univr.pushi.jxatmosphere.adapter.MyPagerAdapter;
import net.univr.pushi.jxatmosphere.base.RxLazyFragment;
import net.univr.pushi.jxatmosphere.beens.DmcgjcmenuBeen;
import net.univr.pushi.jxatmosphere.beens.GkdmClickBeen;
import net.univr.pushi.jxatmosphere.beens.MultiItemGdybTx;
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
    @BindView(R.id.recycler1)
    RecyclerView mRecyclerView1;
    @BindView(R.id.viepager)
    CustomViewPager mViewPager;
    @BindView(R.id.recycler3)
    RecyclerView mRecyclerView3;

//    @BindView(R.id.pic_ready)
//    ImageView isStartPic;

    private Context mcontext;

    private DmcgjcMenuAdapter mAdapter1;

    MyPagerAdapter viewPagerAdapter;
    List<Fragment> fragmentList = new ArrayList<>();
    List<String> urls;

    //    List<GkdmClickBeen> mData3 = new ArrayList<>();
    String type;
    String ctype;

    //当前的位置
    int now_postion ;
    //播放的下一位置
    int recycle_skipto_position ;

    //是否播放
    Boolean isStart = false;


    ProgressDialog progressDialog;

    private MultiGdybTxAdapter mAdapter3;
    List<MultiItemGdybTx> multitemList = new ArrayList<>();

    ImageView isStartPic;

    @BindView(R.id.tv_1)
    TextView tv1;
    @BindView(R.id.tv_2)
    TextView tv2;
    @BindView(R.id.tv_3)
    TextView tv3;
    @BindView(R.id.tv_4)
    TextView tv4;
    @BindView(R.id.tv_5)
    TextView tv5;
    @BindView(R.id.tv_6)
    TextView tv6;

    @BindView(R.id.tabline1)
    View tabline1;
    @BindView(R.id.tabline2)
    View tabline2;
    @BindView(R.id.tabline3)
    View tabline3;
    @BindView(R.id.tabline4)
    View tabline4;
    @BindView(R.id.tabline5)
    View tabline5;
    @BindView(R.id.tabline6)
    View tabline6;
    @BindView(R.id.scrollview)
    HorizontalScrollView scrollview;

    ViewPager viewPager;
    List<Fragment> list;
    Handler handler = new Handler();

    public static DMCGJCFragment newInstance(String type, String ctype, ViewPager viewPager, List<Fragment> list) {
        DMCGJCFragment dmcgjcFragment = new DMCGJCFragment();
        Bundle bundle = new Bundle();
        bundle.putString("type", type);
        bundle.putString("ctype", ctype);
        dmcgjcFragment.setArguments(bundle);
        dmcgjcFragment.viewPager = viewPager;
        dmcgjcFragment.list = list;
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

        mcontext = getActivity();

        if (getArguments() != null) {
            //取出保存的值
            type = getArguments().getString("type");
            ctype = getArguments().getString("ctype");
        }
        progressDialog = ProgressDialog.show(getContext(), "请稍等...", "获取数据中...", true);
        progressDialog.setCancelable(true);
        getAdapter1();
        RetrofitHelper.getWeatherMonitorAPI()
                .getDmcgjcMenu(type)
                .compose(bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(dmcgjcBeen -> {
                    List<DmcgjcmenuBeen.DataBean> data = dmcgjcBeen.getData();
                    for (int i = 0; i < data.size(); i++) {
                        if (i == 0) {
                            DmcgjcmenuBeen.DataBean temp = data.get(i);
                            temp.setSelect(true);
                            data.set(i, temp);
                        } else {
                            DmcgjcmenuBeen.DataBean temp = data.get(i);
                            temp.setSelect(false);
                            data.set(i, temp);
                        }
                    }
                    getAdapter1().setNewData(data);
                }, throwable -> {
                    LogUtils.e(throwable);
                    ToastUtils.showShort(getString(R.string.getInfo_error_toast));
                });

        getTestdata();

//        isStartPic.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (isStart == false) {
//                    isStartPic.setImageResource(R.drawable.app_end);
//                    Message message = uiHandler.obtainMessage();
//                    message.what = 1;
//                    uiHandler.sendMessageDelayed(message, MyApplication.getInstance().auto_time);
//                    isStart = true;
//                    mViewPager.setScanScroll(false);
//                } else {
//                    uiHandler.removeCallbacksAndMessages(null);
//                    isStartPic.setImageResource(R.drawable.app_start);
//                    isStart = false;
//                    mViewPager.setScanScroll(true);
//                }
//
//            }
//        });

        tv1.setOnClickListener(this);
        tv2.setOnClickListener(this);
        tv3.setOnClickListener(this);
        tv4.setOnClickListener(this);
        tv5.setOnClickListener(this);
        tv6.setOnClickListener(this);

        initOneMenu();

    }

    private void initOneMenu() {
        if (type.equals("rain")) {
            tv2.setTextColor(getResources().getColor(R.color.toolbar_color));
            tv2.setTextSize(17);
            tabline2.setVisibility(View.VISIBLE);
        }
        if (type.equals("temp")) {
            tv3.setTextColor(getResources().getColor(R.color.toolbar_color));
            tv3.setTextSize(17);
            tabline3.setVisibility(View.VISIBLE);
        }
        if (type.equals("wind")) {
            tv4.setTextColor(getResources().getColor(R.color.toolbar_color));
            tv4.setTextSize(17);
            tabline4.setVisibility(View.VISIBLE);
        }
        if (type.equals("humidity")) {
            tv5.setTextColor(getResources().getColor(R.color.toolbar_color));
            tv5.setTextSize(17);
            tabline5.setVisibility(View.VISIBLE);
        }
        if (type.equals("pressure")) {
            tv6.setTextColor(getResources().getColor(R.color.toolbar_color));
            tv6.setTextSize(17);
            tabline6.setVisibility(View.VISIBLE);
        }
        initScrollView();
    }


    public void initScrollView() {
        if (type.equals("rain")) {
            handler.postAtTime(new Runnable() {
                @Override
                public void run() {
                    scrollview.scrollTo(50, 0);
                }
            }, 2000);
        }
        if (type.equals("temp")) {
            handler.postAtTime(new Runnable() {
                @Override
                public void run() {
                    scrollview.scrollTo(250, 0);
                }
            }, 2000);
        }
        if (type.equals("wind")) {
            handler.postAtTime(new Runnable() {
                @Override
                public void run() {
                    scrollview.scrollTo(550, 0);
                }
            }, 2000);
        }
        if (type.equals("humidity")) {
            handler.postAtTime(new Runnable() {
                @Override
                public void run() {
                    scrollview.scrollTo(580, 0);
                }
            }, 2000);
        }
        if (type.equals("pressure")) {
            handler.postAtTime(new Runnable() {
                @Override
                public void run() {
                    scrollview.scrollBy(580, 0);
                }
            }, 2000);
        }
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
                if(isStartPic!=null)
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
                if (menu.equals("6分钟累计降水")) {
                    ctype = "rain_sum_6";
                }

                if (menu.equals("1小时累计降水")) {
                    ctype = "rain_sum1";
                }
                if (menu.equals("3小时累计降水")) {
                    ctype = "rain_sum3";
                }
                if (menu.equals("6小时累计降水")) {
                    ctype = "rain_sum6";
                }
                if (menu.equals("12小时累计降水")) {
                    ctype = "rain_sum12";
                }
                if (menu.equals("24小时累计降水")) {
                    ctype = "rain_sum";
                }

                if (menu.equals("气温")) {
                    ctype = "temp";
                }
                if (menu.equals("平均气温（20时-20时）")) {
                    ctype = "temp_avg_20";   //日期型
                }
                if (menu.equals("最高气温")) {
                    ctype = "temp_max";
                }
                if (menu.equals("最高气温（20时-20时）")) {
                    ctype = "temp_max_20";//日期型
                }
                if (menu.equals("最低气温")) {
                    ctype = "temp_min";
                }
                if (menu.equals("最低气温（20时-20时）")) {
                    ctype = "temp_min_20";//日期型
                }
                if (menu.equals("24小时变温")) {
                    ctype = "temp_deta24";
                }
                if (menu.equals("1小时变温")) {
                    ctype = "temp_deta1";
                }
                if (menu.equals("体感温度")) {
                    ctype = "body_feeling_temp";
                }
                if (menu.equals("最小水平能见度")) {
                    ctype = "vis_min";
                }

                if (menu.equals("二分钟平均风速")) {
                    ctype = "wind_2minute_avg";
                }
                if (menu.equals("十分钟平均风速")) {
                    ctype = "wind_10minute_avg";
                }
                if (menu.equals("一小时内极大风速")) {
                    ctype = "wind_great";
                }
                if (menu.equals("极大风速（20时-20时）")) {
                    ctype = "wind_great_20";//日期型
                }
                if (menu.equals("一小时内最大风速")) {
                    ctype = "wind_max";
                }
                if (menu.equals("瞬时风速")) {
                    ctype = "wind_inst";
                }
                if (menu.equals("极大风速")) {
                    ctype = "wind_inst_max";
                }
                if (menu.equals("最大风速")) {
                    ctype = "wind_max_5";
                }
                if (menu.equals("一分钟平均风速")) {
                    ctype = "wind_1minute_avg";
                }


                if (menu.equals("相对湿度")) {
                    ctype = "humidity";
                }
                if (menu.equals("最小相对湿度")) {
                    ctype = "humidity_min";
                }

                if (menu.equals("本站气压")) {
                    ctype = "pressure";
                }
                if (menu.equals("3小时变压")) {
                    ctype = "pressure_deta3";
                }
                if (menu.equals("24小时变压")) {
                    ctype = "pressure_deta24";
                }
                if (menu.equals("1小时内最高气压")) {
                    ctype = "pressure_max";
                }
                if (menu.equals("1小时内最低气压")) {
                    ctype = "pressure_min";
                }
                if (menu.equals("海平面气压")) {
                    ctype = "sea_level_pressure";
                }
                progressDialog = ProgressDialog.show(getContext(), "请稍等...", "获取数据中...", true);
                progressDialog.setCancelable(true);
                getTestdata();
            });
        }
        return mAdapter1;
    }


    private MultiGdybTxAdapter getAdapter3() {
        if (mAdapter3 == null) {
            ExStaggeredGridLayoutManager layoutManager = new ExStaggeredGridLayoutManager(6, StaggeredGridLayoutManager.VERTICAL) {
                @Override
                public boolean canScrollVertically() {
                    return false;
                }
            };
            mAdapter3 = new MultiGdybTxAdapter(multitemList);
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


    private void getTestdata() {
        getAdapter3();
        RetrofitHelper.getWeatherMonitorAPI()
                .getDmcgjc(type, ctype)
                .compose(bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(DmcgjcDeen -> {
                    recycle_skipto_position = 1;
                    now_postion = DmcgjcDeen.getData().get(0).getUrl().size()- 1;
                    isStart = false;
                    if(isStartPic!=null){
                        isStartPic.setImageResource(R.drawable.app_start);
                        mViewPager.setScanScroll(true);
                    }
                    progressDialog.dismiss();
                    List<Fragment> HuancunfragmentList = new ArrayList<>();
                    for (int i = 0; i < fragmentList.size(); i++) {
                        Fragment fragment = fragmentList.get(i);
                        HuancunfragmentList.add(fragment);
                    }
                    fragmentList.clear();
                    urls = DmcgjcDeen.getData().get(0).getUrl();
                    for (int i = 0; i < urls.size(); i++) {
                        PicLoadFragment fragment = PicLoadFragment.newInstance(urls.get(i));
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






                    List<String> time = DmcgjcDeen.getData().get(0).getTime();
                    multitemList.clear();
                    MultiItemGdybTx multiItemGdybTx = new MultiItemGdybTx(MultiItemGdybTx.IMG, R.drawable.app_start);
                    multitemList.add(multiItemGdybTx);
                    for (int i = 0; i < time.size(); i++) {

                        GkdmClickBeen clickBeen = new GkdmClickBeen();
                        if (i == time.size()-1)
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
            case R.id.tv_1:
                viewPager.setCurrentItem(0);
                SWZDYLFragment swzdylFragment = (SWZDYLFragment) list.get(0);
                swzdylFragment.setIsPalyInit();
                break;
            case R.id.tv_2:
                viewPager.setCurrentItem(1);
                DMCGJCFragment dmcgjcFragment1 = (DMCGJCFragment) list.get(1);
                dmcgjcFragment1.setIsPalyInit(1);
                break;

            case R.id.tv_3:
                viewPager.setCurrentItem(2);
                DMCGJCFragment dmcgjcFragment2 = (DMCGJCFragment) list.get(2);
                dmcgjcFragment2.setIsPalyInit(2);
                break;
            case R.id.tv_4:
                viewPager.setCurrentItem(3);
                DMCGJCFragment dmcgjcFragment3 = (DMCGJCFragment) list.get(3);
                dmcgjcFragment3.setIsPalyInit(3);
                break;
            case R.id.tv_5:
                viewPager.setCurrentItem(4);
                DMCGJCFragment dmcgjcFragment4 = (DMCGJCFragment) list.get(4);
                dmcgjcFragment4.setIsPalyInit(4);

                break;
            case R.id.tv_6:
                viewPager.setCurrentItem(5);
                DMCGJCFragment dmcgjcFragment5 = (DMCGJCFragment) list.get(5);
                dmcgjcFragment5.setIsPalyInit(5);
                break;
        }


    }


    public void setIsPalyInit(int current) {
        for (int i = 1; i < list.size(); i++) {
            if (current == i) ;
            else {
                ((DMCGJCFragment) list.get(i)).setStart(false);
                ((DMCGJCFragment) list.get(i)).setImage();
            }

        }

    }

}
