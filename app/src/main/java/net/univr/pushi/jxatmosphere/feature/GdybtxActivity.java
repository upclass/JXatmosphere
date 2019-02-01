package net.univr.pushi.jxatmosphere.feature;

import android.animation.ValueAnimator;
import android.app.ProgressDialog;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListPopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;

import net.univr.pushi.jxatmosphere.MyApplication;
import net.univr.pushi.jxatmosphere.R;
import net.univr.pushi.jxatmosphere.adapter.DmcgjcMenuAdapter;
import net.univr.pushi.jxatmosphere.adapter.MultiGdybTxAdapterForGdybtx;
import net.univr.pushi.jxatmosphere.adapter.MyPagerAdapter;
import net.univr.pushi.jxatmosphere.base.BaseActivity;
import net.univr.pushi.jxatmosphere.beens.DmcgjcmenuBeen;
import net.univr.pushi.jxatmosphere.beens.GdybtxBeen;
import net.univr.pushi.jxatmosphere.beens.GdybtxMenuBeen;
import net.univr.pushi.jxatmosphere.beens.GkdmClickBeen;
import net.univr.pushi.jxatmosphere.beens.MultiItemGdybTx;
import net.univr.pushi.jxatmosphere.fragments.PicLoadFragment;
import net.univr.pushi.jxatmosphere.interfaces.BrightnessActivity;
import net.univr.pushi.jxatmosphere.interfaces.CallBackUtil;
import net.univr.pushi.jxatmosphere.remote.RetrofitHelper;
import net.univr.pushi.jxatmosphere.utils.ExStaggeredGridLayoutManager;
import net.univr.pushi.jxatmosphere.utils.PicUtils;
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
    @BindView(R.id.reload)
    ImageView reload;

//    @BindView(R.id.spDwon)
//    Spinner spDown;

    @BindView(R.id.recycler1)
    RecyclerView mRecyclerView1;
    @BindView(R.id.recycler3)
    RecyclerView mRecyclerView3;
    @BindView(R.id.menu)
    RecyclerView menuRecycleview;
    @BindView(R.id.time)
    TextView timeTag;

//    private ArrayAdapter<String> spinAdapter;
//    private List<String> oneMenu=new ArrayList<>();

    private DmcgjcMenuAdapter mAdapter1;
    private DmcgjcMenuAdapter menuAdapter;
    private MultiGdybTxAdapterForGdybtx mAdapter3;
    List<MultiItemGdybTx> multitemList = new ArrayList<>();

//    ImageView isStartPic;

    //是否播放
    Boolean isStart = false;
    String type;
    String testType;
    ProgressDialog progressDialog;

    //现在的位置
    int now_postion = 1;

    //播放的下一位置
    int recycle_skipto_position = 2;
    //    List<ImageView> list;
    List<Fragment> list = new ArrayList<>();
    MyPagerAdapter  viewPagerAdapter;

    ListPopupWindow popupWindow;
    private ArrayAdapter timeAdapter;

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
//        initSpinner();
        getOneMenu();
        initOnclick();
        type = "rain";
        testType = "rain12";
        getTwoMenu();
        getTestdata();
        getTag(testType);
        CallBackUtil.setBrightness(new BrightnessActivity() {
            @Override
            public void onDispatchDarken() {
                final Window window = getWindow();
                ValueAnimator valueAnimator = ValueAnimator.ofFloat(1.0f, 0.5f);
                valueAnimator.setDuration(500);
                valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        WindowManager.LayoutParams params = window.getAttributes();
                        params.alpha = (Float) animation.getAnimatedValue();
                        window.setAttributes(params);
                    }
                });

                valueAnimator.start();
            }

            @Override
            public void onDispatchLight() {
                final Window window = getWindow();
                ValueAnimator valueAnimator = ValueAnimator.ofFloat(0.5f, 1.0f);
                valueAnimator.setDuration(500);
                valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        WindowManager.LayoutParams params = window.getAttributes();
                        params.alpha = (Float) animation.getAnimatedValue();
                        window.setAttributes(params);
                    }
                });

                valueAnimator.start();
            }
        });
        timeTag.setOnClickListener(this);

    }

    private void initOnclick() {
        leave.setOnClickListener(this);
        reload.setOnClickListener(this);
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
            case R.id.reload:
                PicUtils.deleteDir("gdybtx/" + testType);
                if (timeTag.getText().equals("请选择时段")) {
                    getTestdata();
                }else{
                    getTagData(testType,timeTag.getText().toString());
                }
                break;
            case R.id.time:
                if(popupWindow!=null){
                    if (popupWindow.isShowing()) {
                        popupWindow.dismiss();
                    }else {
                        popupWindow.show();
                    }
                }else {
                    Toast.makeText(context, "没查询到时段", Toast.LENGTH_SHORT).show();
                }
                break;

        }

    }

    //获得早上、中午、下午、晚上标签
    public void getTag(String type){
        RetrofitHelper.getForecastWarn()
                .getGdyutxTag(type)
                .compose(bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(gdybtxTag -> {
                    List<String> data = gdybtxTag.getData();
                    initSpinner(data);
                    //选择第一个tag为初始值
                    timeTag.setText(data.get(0));
                },throwable -> {
                    throwable.printStackTrace();
                    ToastUtils.showShort(getString(R.string.getInfo_error_toast));
                });
    }

    //根据tag和type去获取数据
    public void getTagData(String type,String tag){
        progressDialog = ProgressDialog.show(context, "请稍等...", "获取数据中...", true);
        progressDialog.setCancelable(true);
        RetrofitHelper.getForecastWarn()
                .getGdyutxTagData(type,tag)
                .compose(bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(gdybtxBeen -> {
                    progressDialog.dismiss();
                    DisplayGdyutxData(gdybtxBeen,tag);
                },throwable -> {
                    progressDialog.dismiss();
                    throwable.printStackTrace();
                    ToastUtils.showShort(getString(R.string.getInfo_error_toast));
                });
    }

    private void initSpinner(List<String> data) {
        popupWindow=new ListPopupWindow(context);
        timeAdapter = new ArrayAdapter(context, android.R.layout.simple_list_item_1, data);
        popupWindow.setAdapter(timeAdapter);
        popupWindow.setAnchorView(timeTag);
        popupWindow.setModal(true);
        popupWindow.setWidth(220);   //WRAP_CONTENT会出错
        popupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            popupWindow.setDropDownGravity(Gravity.END);
        }
        popupWindow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ArrayAdapter adapter = (ArrayAdapter) parent.getAdapter();
                String item = (String) adapter.getItem(position);
                getTagData(testType,item);
                timeTag.setText(item);
                popupWindow.dismiss();
            }
        });
    }

    public void getOneMenu() {
        getMenuAdapter();
        RetrofitHelper.getForecastWarn()
                .getGdybtOneMenu()
                .compose(bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(gdybtxOneMenu -> {
                    List<GdybtxMenuBeen.DataBean.MenuBean> menu = gdybtxOneMenu.getData().getMenu();
                    List<DmcgjcmenuBeen.DataBean> destmenu = new ArrayList<>();
                    for (int i = 0; i < menu.size(); i++) {
                        DmcgjcmenuBeen.DataBean dataBean = new DmcgjcmenuBeen.DataBean();
                        if (i == 0) {
                            dataBean.setSelect(true);
                        }
                        dataBean.setZnName(menu.get(i).getName());
                        destmenu.add(dataBean);
                    }
                    menuAdapter.setNewData(destmenu);

                }, throwable -> {
                    throwable.printStackTrace();
                    ToastUtils.showShort(getString(R.string.getInfo_error_toast));
                });
    }

    private void getTwoMenu() {
        RetrofitHelper.getForecastWarn()
                .getGdybtTwoMenu(type)
                .compose(bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(gdybtxTwoMenu -> {
                    getAdapter1().setLastposition(0);
                    isStart = false;
//                    if (isStartPic != null) {
//                        isStartPic.setImageResource(R.drawable.app_start);
//                        mViewPager.setScanScroll(true);
//                    }
                    mViewPager.setScanScroll(true);
                    List<GdybtxMenuBeen.DataBean.MenuBean> menu = gdybtxTwoMenu.getData().getMenu();
                    List<DmcgjcmenuBeen.DataBean> dataBeans = new ArrayList<>();

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
                        getAdapter1().setLastposition(3);
                        if(type.equals("rain")||type.equals("wp")||type.equals("vis")||type.equals("10uv")||type.equals("tcc")){
                            DmcgjcmenuBeen.DataBean dataBean = dataBeans.get(0);
                            dataBean.setSelect(false);
                            DmcgjcmenuBeen.DataBean dataBean1 = dataBeans.get(3);
                            dataBean1.setSelect(true);
                        }

                        mAdapter1.setNewData(dataBeans);
                    }
                }, throwable -> {
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
//                if (isStartPic != null) {
//                    isStartPic.setImageResource(R.drawable.app_start);
//                    mViewPager.setScanScroll(true);
//                }
                mViewPager.setScanScroll(true);

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
                if (type.equals("rain") && menu.equals("逐24小时")) {
                    testType = "rain24";
                }
                if (type.equals("tmax24") && menu.equals("24小时最高温")) {
                    testType = "tmax24";
                }
                if (type.equals("tmin24") && menu.equals("24小时最低温")) {
                    testType = "tmin24";
                }
                if (type.equals("t2m") && menu.equals("逐小时")) {
                    testType = "t2m";
                }

                if (type.equals("vis") && menu.equals("逐小时")) {
                    testType = "vis";
                }
                if (type.equals("vis") && menu.equals("逐3小时")) {
                    testType = "vis3";
                }
                if (type.equals("vis") && menu.equals("逐6小时")) {
                    testType = "vis6";
                }
                if (type.equals("vis") && menu.equals("逐12小时")) {
                    testType = "vis12";
                }
                if (type.equals("vis") && menu.equals("逐24小时")) {
                    testType = "vis24";
                }

                if (type.equals("10uv") && menu.equals("逐小时")) {
                    testType = "10uv";
                }
                if (type.equals("10uv") && menu.equals("逐3小时")) {
                    testType = "10uv3";
                }
                if (type.equals("10uv") && menu.equals("逐6小时")) {
                    testType = "10uv6";
                }
                if (type.equals("10uv") && menu.equals("逐12小时")) {
                    testType = "10uv12";
                }
                if (type.equals("10uv") && menu.equals("逐24小时")) {
                    testType = "10uv24";
                }
                if (type.equals("rh2m") && menu.equals("逐小时")) {
                    testType = "rh2m";
                }
                if (type.equals("tcc") && menu.equals("逐小时")) {
                    testType = "tcc";
                }
                if (type.equals("tcc") && menu.equals("逐3小时")) {
                    testType = "tcc3";
                }
                if (type.equals("tcc") && menu.equals("逐6小时")) {
                    testType = "tcc6";
                }
                if (type.equals("tcc") && menu.equals("逐12小时")) {
                    testType = "tcc12";
                }
                if (type.equals("tcc") && menu.equals("逐24小时")) {
                    testType = "tcc24";
                }
                if(timeTag.equals("请选择时段"))
                getTestdata();
                else getTagData(testType,timeTag.getText().toString());
                layoutManager.scrollToPositionWithOffset(position,0);
                layoutManager.setStackFromEnd(false);
            });
        }
        return mAdapter1;
    }

    private DmcgjcMenuAdapter getMenuAdapter() {
        if (menuAdapter == null) {
            LinearLayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
            List<DmcgjcmenuBeen.DataBean> mData1 = new ArrayList<>();

            menuAdapter = new DmcgjcMenuAdapter(mData1);
            menuRecycleview.setLayoutManager(layoutManager);
            menuRecycleview.setAdapter(menuAdapter);
            menuAdapter.setOnItemChildClickListener((adapter, view, position) -> {
                getTag(testType);
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
                if (menu.equals("降水量")) {
                    type = "rain";
                    testType = "rain12";
                }
                if (menu.equals("最高温")) {
                    type = "tmax24";
                    testType = "tmax24";
                }
                if (menu.equals("最低温")) {
                    type = "tmin24";
                    testType = "tmin24";
                }
                if (menu.equals("天气现象")) {
                    type = "wp";
                    testType = "wp12";
                }
                if (menu.equals("2米温度")) {
                    type = "t2m";
                    testType = "t2m";
                }
                if (menu.equals("雾")) {
                    type = "vis";
                    testType = "vis12";
                }
                if (menu.equals("10米风")) {
                    type = "10uv";
                    testType = "10uv12";
                }
                if (menu.equals("2米湿度")) {
                    type = "rh2m";
                    testType = "rh2m";
                }
                if (menu.equals("总云量")) {
                    type = "tcc";
                    testType = "tcc12";
                }
                getTwoMenu();
                if(timeTag.equals("请选择时段"))
                    getTestdata();
                else getTagData(testType,timeTag.getText().toString());

                layoutManager.scrollToPositionWithOffset(position,0);
                layoutManager.setStackFromEnd(false);
                LinearLayoutManager layoutManager1 = (LinearLayoutManager) mRecyclerView1.getLayoutManager();
                layoutManager1.scrollToPositionWithOffset(0,0);
                layoutManager1.setStackFromEnd(false);
            });
        }
        return menuAdapter;
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
                    DisplayGdyutxData(gdybtx,null);
                }, throwable -> {
                    progressDialog.dismiss();
                    LogUtils.e(throwable);
                    ToastUtils.showShort(getString(R.string.getInfo_error_toast));
                });
    }

    private void DisplayGdyutxData(GdybtxBeen gdybtx,String tag) {
        GdybtxBeen.DataBean data = gdybtx.getData();
        List<String> timeList = new ArrayList<>();
        List<String> picListtemp = new ArrayList<>();

        for (int i = 0; i < gdybtx.getData().getTimeList().size(); i++) {
            timeList.add(gdybtx.getData().getTimeList().get(i));
            picListtemp.add(gdybtx.getData().getPicList().get(i));
        }
        data.setPicList(picListtemp);
        data.setTimeList(timeList);
        gdybtx.setData(data);
        progressDialog.dismiss();
        recycle_skipto_position = 2;
        now_postion = 1;
        isStart = false;
        uiHandler.removeCallbacksAndMessages(null);
//                    if (isStartPic != null) {
//                        isStartPic.setImageResource(R.drawable.app_start);
//                        mViewPager.setScanScroll(true);
//                    }
        mViewPager.setScanScroll(true);

//                    list = new ArrayList<>();
        List<String> picList = gdybtx.getData().getPicList();
        List<Fragment> fragmentHuancun = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            fragmentHuancun.add(list.get(i));
        }
        list.clear();
        for (int i = 0; i < picList.size(); i++) {
            PicLoadFragment picLoadFragment;
            if(tag!=null)
                picLoadFragment= PicLoadFragment.newInstance(picList.get(i), picList, "gdybtx/"+ tag+"/"+ testType);
            else picLoadFragment= PicLoadFragment.newInstance(picList.get(i), picList, "gdybtx/" + testType);;
            list.add(picLoadFragment);
        }
//                    ArrayList<String> picTemp = new ArrayList<>();
//                    for (int i = 0; i < picList.size(); i++) {
//                        picTemp.add(picList.get(i));
//                        ImageView imageView = new ImageView(context);
//                        Picasso.with(context).load(picList.get(i)).placeholder(R.drawable.app_imageplacehold).into(imageView);
//                        int finalI = i;
//                        imageView.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                Intent intent = new Intent(context, PicDealActivity.class);
//                                intent.putExtra("url", picList.get(finalI));
//                                intent.putStringArrayListExtra("urls", picTemp);
//                                startActivity(intent);
//                            }
//                        });
//                        list.add(imageView);
//                    }
        viewPagerAdapter = new MyPagerAdapter(getSupportFragmentManager(), list, fragmentHuancun);
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
    }

    private MultiGdybTxAdapterForGdybtx getAdapter3() {
        if (mAdapter3 == null) {
//            ExStaggeredGridLayoutManager layoutManager = new ExStaggeredGridLayoutManager(6, StaggeredGridLayoutManager.VERTICAL) {
//                @Override
//                public boolean canScrollVertically() {
//                    return false;
//                }
//            };
            ExStaggeredGridLayoutManager layoutManager = new ExStaggeredGridLayoutManager(8, StaggeredGridLayoutManager.VERTICAL);
            mAdapter3 = new MultiGdybTxAdapterForGdybtx(multitemList);
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
//                            isStartPic = ((ImageView) view);
//                            isStartPic.setImageResource(R.drawable.app_end);
                            MultiItemGdybTx multiItemGdybTx = new MultiItemGdybTx(MultiItemGdybTx.IMG, R.drawable.app_end);
                            multitemList.set(0, multiItemGdybTx);
                            getAdapter3().notifyItemChanged(0);
                            Message message = uiHandler.obtainMessage();
                            message.what = 1;
                            uiHandler.sendMessageDelayed(message, MyApplication.getInstance().auto_time);
                            isStart = true;
                            mViewPager.setScanScroll(false);
                        } else {
                            uiHandler.removeCallbacksAndMessages(null);
//                            isStartPic.setImageResource(R.drawable.app_start);
                            MultiItemGdybTx multiItemGdybTx = new MultiItemGdybTx(MultiItemGdybTx.IMG, R.drawable.app_start);
                            multitemList.set(0, multiItemGdybTx);
                            getAdapter3().notifyItemChanged(0);
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



