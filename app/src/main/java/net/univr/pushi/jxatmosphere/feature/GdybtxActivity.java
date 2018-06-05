package net.univr.pushi.jxatmosphere.feature;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.squareup.picasso.Picasso;

import net.univr.pushi.jxatmosphere.MyApplication;
import net.univr.pushi.jxatmosphere.R;
import net.univr.pushi.jxatmosphere.adapter.DmcgjcMenuAdapter;
import net.univr.pushi.jxatmosphere.adapter.MultiGdybTxAdapter;
import net.univr.pushi.jxatmosphere.adapter.ViewpageAdapter;
import net.univr.pushi.jxatmosphere.base.BaseActivity;
import net.univr.pushi.jxatmosphere.beens.DmcgjcmenuBeen;
import net.univr.pushi.jxatmosphere.beens.GdybtxBeen;
import net.univr.pushi.jxatmosphere.beens.GdybtxMenuBeen;
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

public class GdybtxActivity extends BaseActivity implements View.OnClickListener {


    @BindView(R.id.viepager)
    CustomViewPager mViewPager;

    @BindView(R.id.back)
    ImageView leave;

    @BindView(R.id.spDwon)
    Spinner spDown;

    @BindView(R.id.recycler1)
    RecyclerView mRecyclerView1;
    @BindView(R.id.recycler3)
    RecyclerView mRecyclerView3;

    private ArrayAdapter<String> spinAdapter;
    private List<String> oneMenu;

    private DmcgjcMenuAdapter mAdapter1;
    private MultiGdybTxAdapter mAdapter3;
    List<MultiItemGdybTx> multitemList = new ArrayList<>();

    ImageView isStartPic;

    //是否播放
    Boolean isStart = false;
    String type;
    String testType;
    ProgressDialog progressDialog;

    //现在的位置
    int now_postion = 1;

    //播放的下一位置
    int recycle_skipto_position = 2;
    List<ImageView> list;
    ViewpageAdapter viewPagerAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_gdybtx;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        super.initViews(savedInstanceState);
        initView();
    }


    private void initView() {
        initSpinner();
        getOneMenu();
        initOnclick();
    }

    private void initOnclick() {
        leave.setOnClickListener(this);
    }

    private void initSpinner() {
        oneMenu = new ArrayList<>();
        spinAdapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, oneMenu);
        /*adapter设置一个下拉列表样式，参数为系统子布局*/
        spinAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        /*spDown加载适配器*/
        spDown.setAdapter(spinAdapter);
        spDown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if ((spDown.getSelectedItem()).equals("降水量")) {
                    type = "rain";
                    testType = "rain";
                }
                if ((spDown.getSelectedItem()).equals("气温")) {
                    type = "temp";
                    testType = "tmax24";
                }
                if ((spDown.getSelectedItem()).equals("天气现象")) {
                    type = "wp";
                    testType = "wp";
                }
                getTwoMenu();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


    public void setStart(Boolean start) {
        isStart = start;
        if (mViewPager != null) {
            mViewPager.setScanScroll(true);
        }
    }

    public void setImage() {
//        if (isStartPic != null)
//            isStartPic.setImageResource(R.drawable.app_start);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
        }

    }

    public void getOneMenu() {
        RetrofitHelper.getForecastWarn()
                .getGdybtOneMenu()
                .compose(bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(gdybtxOneMenu -> {
                    oneMenu.clear();
                    List<GdybtxMenuBeen.DataBean.MenuBean> menu = gdybtxOneMenu.getData().getMenu();
                    type = menu.get(0).getType();
                    for (int i = 0; i < menu.size(); i++) {
                        oneMenu.add(menu.get(i).getName());
                    }
                    spinAdapter.notifyDataSetChanged();

                }, throwable -> {
                    throwable.printStackTrace();
                    ToastUtils.showShort(getString(R.string.getInfo_error_toast));
                });
    }

    private void getTwoMenu() {
        progressDialog = ProgressDialog.show(context, "请稍等...", "获取数据中...", true);
        progressDialog.setCancelable(true);
        RetrofitHelper.getForecastWarn()
                .getGdybtTwoMenu(type)
                .compose(bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(gdybtxTwoMenu -> {
                    getAdapter1().setLastposition(0);
                    isStart = false;
                    if(isStartPic!=null){
                        isStartPic.setImageResource(R.drawable.app_start);
                        mViewPager.setScanScroll(true);
                    }
                    progressDialog.dismiss();
                    List<GdybtxMenuBeen.DataBean.MenuBean> menu = gdybtxTwoMenu.getData().getMenu();
                    List<DmcgjcmenuBeen.DataBean> dataBeans = new ArrayList<>();
                    getTestdata();
                    for (int i = 0; i < menu.size(); i++) {
                        DmcgjcmenuBeen.DataBean temp = new DmcgjcmenuBeen.DataBean();
                        String menuname = menu.get(i).getName();
                        temp.setZnName(menuname);
                        if (i == 0) temp.setSelect(true);
                        else
                            temp.setSelect(false);
                        dataBeans.add(temp);
                    }

                    if (menu != null && menu.size() > 0) {
                        getAdapter1();
                        mAdapter1.setNewData(dataBeans);
                    }
                }, throwable -> {
                    progressDialog.dismiss();
                    LogUtils.e(throwable);
                    ToastUtils.showShort(getString(R.string.getInfo_error_toast));
                });
    }

    private DmcgjcMenuAdapter getAdapter1() {
        if (mAdapter1 == null) {
            LinearLayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
            List<DmcgjcmenuBeen.DataBean> mData1 = new ArrayList<>();

            mAdapter1 = new DmcgjcMenuAdapter(mData1);
            mRecyclerView1.setLayoutManager(layoutManager);
            mRecyclerView1.setAdapter(mAdapter1);
            mAdapter1.setOnItemChildClickListener((adapter, view, position) -> {
                isStart = false;
                if(isStartPic!=null){
                    isStartPic.setImageResource(R.drawable.app_start);
                    mViewPager.setScanScroll(true);
                }

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
                if (type.equals("wp") && menu.equals("逐小时")) {
                    testType = "wp";
                }

                if (type.equals("wp") && menu.equals("逐3小时")) {
                    testType = "wp3";
                }

                if (type.equals("wp") && menu.equals("逐6小时")) {
                    testType = "wp6";
                }
                if (type.equals("wp") && menu.equals("逐12小时")) {
                    testType = "wp12";
                }

                if (type.equals("rain") && menu.equals("逐小时")) {
                    testType = "rain";
                }
                if (type.equals("rain") && menu.equals("逐3小时")) {
                    testType = "rain3";
                }
                if (type.equals("rain") && menu.equals("逐6小时")) {
                    testType = "rain6";
                }
                if (type.equals("rain") && menu.equals("逐12小时")) {
                    testType = "rain12";
                }
                if (type.equals("rain") && menu.equals("逐12小时")) {
                    testType = "rain12";
                }
                if (type.equals("temp") && menu.equals("24小时最高温")) {
                    testType = "tmax24";
                }
                if (type.equals("temp") && menu.equals("24小时最低温")) {
                    testType = "tmin24";
                }
                getTestdata();
            });
        }
        return mAdapter1;
    }

    private void getTestdata() {
        progressDialog = ProgressDialog.show(context, "请稍等...", "获取数据中...", true);
        progressDialog.setCancelable(true);
        getAdapter3();
        RetrofitHelper.getForecastWarn()
                .getGdybt(testType)
                .compose(bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(gdybtx -> {
                    GdybtxBeen.DataBean data = gdybtx.getData();
                     List<String> timeList=new ArrayList<>();
                     List<String> picListtemp=new ArrayList<>();

                    for (int i = 0; i < gdybtx.getData().getTimeList().size(); i++) {
                        String s = gdybtx.getData().getTimeList().get(i);
                        if(Integer.valueOf(s)>72)
                                break;
                            else{
                                timeList.add(gdybtx.getData().getTimeList().get(i));
                                picListtemp.add(gdybtx.getData().getPicList().get(i));
                            }
                    }
                    data.setPicList(picListtemp);
                    data.setTimeList(timeList);
                    gdybtx.setData(data);
                    progressDialog.dismiss();
                    recycle_skipto_position = 2;
                    now_postion = 1;
                    isStart = false;
                    if(isStartPic!=null){
                        isStartPic.setImageResource(R.drawable.app_start);
                        mViewPager.setScanScroll(true);
                    }

                    list = new ArrayList<>();
                    List<String> picList = gdybtx.getData().getPicList();
                    for (int i = 0; i < picList.size(); i++) {
                        ImageView imageView = new ImageView(context);
                        Picasso.with(context).load(picList.get(i)).placeholder(R.drawable.app_imageplacehold).into(imageView);
                        list.add(imageView);
                    }

                    viewPagerAdapter = new ViewpageAdapter(list);
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


                    List<String> time = gdybtx.getData().getTimeList();
                    multitemList.clear();
                    MultiItemGdybTx multiItemGdybTx = new MultiItemGdybTx(MultiItemGdybTx.IMG, R.drawable.app_start);
                    multitemList.add(multiItemGdybTx);
                    for (int i = 0; i < time.size(); i++) {

                        GkdmClickBeen clickBeen = new GkdmClickBeen();
                        if (i == 0)
                            clickBeen.setOnclick(true);
                        else clickBeen.setOnclick(false);
                        clickBeen.setText(time.get(i));
//                        mData3.add(clickBeen);

                        multiItemGdybTx = new MultiItemGdybTx(MultiItemGdybTx.TIME_TEXT, clickBeen);
                        multitemList.add(multiItemGdybTx);
                    }
                    getAdapter3().setNewData(multitemList);
                    //播放轮播

                }, throwable -> {
                    progressDialog.dismiss();
                    LogUtils.e(throwable);
                    ToastUtils.showShort(getString(R.string.getInfo_error_toast));
                });
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

}



