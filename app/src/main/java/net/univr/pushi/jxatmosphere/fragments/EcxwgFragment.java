package net.univr.pushi.jxatmosphere.fragments;


import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.ListPopupWindow;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;

import net.univr.pushi.jxatmosphere.MyApplication;
import net.univr.pushi.jxatmosphere.R;
import net.univr.pushi.jxatmosphere.adapter.DmcgjcMenuAdapter;
import net.univr.pushi.jxatmosphere.adapter.EcxwgOneMenuAdapter;
import net.univr.pushi.jxatmosphere.adapter.MultiGdybTxAdapter;
import net.univr.pushi.jxatmosphere.adapter.MyPagerAdapter;
import net.univr.pushi.jxatmosphere.base.RxLazyFragment;
import net.univr.pushi.jxatmosphere.beens.DmcgjcmenuBeen;
import net.univr.pushi.jxatmosphere.beens.EcOneMenu;
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
public class EcxwgFragment extends RxLazyFragment {

    @BindView(R.id.recycler1)
    RecyclerView mRecyclerView1;
    @BindView(R.id.viepager)
    CustomViewPager mViewPager;
    @BindView(R.id.recycler3)
    RecyclerView mRecyclerView3;
    @BindView(R.id.menu1)
    LinearLayout menu1Linear;
    @BindView(R.id.recyclerView)
    RecyclerView recycleView;
    EcxwgOneMenuAdapter madapter;
    CustomViewPager oneMenuViewpager;
    List<Fragment> oneMenuFragmentList;
    int whichFragment;


    private Context mcontext;

    private DmcgjcMenuAdapter mAdapter1;


    private MultiGdybTxAdapter mAdapter3;
    List<MultiItemGdybTx> multitemList = new ArrayList<>();

    ImageView isStartPic;

    MyPagerAdapter viewPagerAdapter;
    List<Fragment> fragmentList = new ArrayList<>();
    List<String> urls;
    String type = "ecmwf_thin";
    String ctype1;
    String ctype2;
    //播放的下一位置
    int recycle_skipto_position = 2;
    //是否播放
    Boolean isStart = false;

    //现在的位置
    int now_postion = 1;
    ProgressDialog progressDialog;


    @BindView(R.id.time)
    TextView time;
    List<String> menuTime;
    private ListPopupWindow popupWindow;
    private ArrayAdapter timeAdapter;
    Boolean oneMenu = true;


    public static EcxwgFragment newInstance(String ctype1, String ctype2, CustomViewPager oneMenuViewpager, List<Fragment> oneMenuFragmentList,int whichFragment) {
        EcxwgFragment ecxwgFragment = new EcxwgFragment();
        Bundle bundle = new Bundle();
        bundle.putString("ctype1", ctype1);
        bundle.putString("ctype2", ctype2);
        ecxwgFragment.setArguments(bundle);
        ecxwgFragment.oneMenuViewpager = oneMenuViewpager;
        ecxwgFragment.oneMenuFragmentList = oneMenuFragmentList;
        ecxwgFragment.whichFragment=whichFragment;

        return ecxwgFragment;
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
        return R.layout.fragment_ecxwg;
    }


    @Override
    public void finishCreateView(Bundle state) {

        mcontext = getActivity();

        if (getArguments() != null) {
            //取出保存的值
            ctype1 = getArguments().getString("ctype1");
            ctype2 = getArguments().getString("ctype2");
        }

        progressDialog = ProgressDialog.show(getContext(), "请稍等...", "获取数据中...", true);
        progressDialog.setCancelable(true);
        getRecycleInfo();
        getAdapter1();
        initSpinner();
        getOneTime();
        RetrofitHelper.getDataForecastAPI()
                .getTwoMenu("ecmwf_thin", ctype1)
                .compose(bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(ectwomenu -> {
                    List<DmcgjcmenuBeen.DataBean> menu = ectwomenu.getData();
                    if (menu == null || menu.size() == 0)
//                        menu1Linear.setVisibility(View.GONE);
                        mRecyclerView1.setVisibility(View.GONE);

                    else {
                        for (int i = 0; i < menu.size(); i++) {
                            if (i == 0) {
                                DmcgjcmenuBeen.DataBean temp = menu.get(i);
                                temp.setSelect(true);
                                menu.set(i, temp);
                            } else {
                                DmcgjcmenuBeen.DataBean temp = menu.get(i);
                                temp.setSelect(false);
                                menu.set(i, temp);
                            }
                        }
                    }


                    getAdapter1().setNewData(menu);
                }, throwable -> {
                    LogUtils.e(throwable);
                    ToastUtils.showShort(getString(R.string.getInfo_error_toast));
                });

        getTestdata();
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
                if (isStartPic != null)
                    isStartPic.setImageResource(R.drawable.app_start);

                List<DmcgjcmenuBeen.DataBean> data = adapter.getData();
                int lastclick = ((DmcgjcMenuAdapter) adapter).getLastposition();
                DmcgjcmenuBeen.DataBean dataBeanlasted = data.get(lastclick);
                dataBeanlasted.setSelect(false);
                DmcgjcmenuBeen.DataBean dataBean = data.get(position);
                dataBean.setSelect(true);
                adapter.notifyItemChanged(lastclick);
                adapter.notifyItemChanged(position);
                ((DmcgjcMenuAdapter) adapter).setLastposition(position);

                TextView title = ((TextView) view);
                String menu = title.getText().toString();
                if (menu.equals("3小时隔降水")) {
                    ctype2 = "rain03";
                }
                if (menu.equals("6小时隔降水")) {
                    ctype2 = "rain06";
                }
                if (menu.equals("12小时隔降水")) {
                    ctype2 = "rain12";
                }

                if (menu.equals("24小时隔降水")) {
                    ctype2 = "rain24";
                }

                if (menu.equals("3小时间隔降水相态")) {
                    ctype2 = "xt03";
                }
                if (menu.equals("6小时间隔降水相态")) {
                    ctype2 = "xt06";
                }
                if (menu.equals("12小时间隔降水相态")) {
                    ctype2 = "xt12";
                }
                if (menu.equals("24小时间隔降水相态")) {
                    ctype2 = "xt24";
                }


//                if (menu.equals("10米风场预报")) {
//                    ctype = null;
//                }
//                if (menu.equals("对流有效位能和850hPa风场")) {
//                    ctype = null;
//                }


                if (menu.equals("低云量预报")) {
                    ctype2 = "lcc";
                }
                if (menu.equals("总云量预报")) {
                    ctype2 = "tcc";
                }


                //                if (menu.equals("海平面24小时变压")) {
//                    ctype = null;
//                }

                if (menu.equals("2米24小时变温")) {
                    ctype2 = "2m";
                }
                if (menu.equals("850hPa24小时变温）")) {
                    ctype2 = "h850";
                }


                if (menu.equals("2米48小时变温")) {
                    ctype2 = "2m";
                }
                if (menu.equals("850hPa48小时变温")) {
                    ctype2 = "h850";
                }

                //                if (menu.equals("地表24小时间隔最低气温")) {
//                    ctype = null;
//                }


                if (menu.equals("200hPa位势高度和200hPa风场")) {
                    ctype2 = "h200w200";
                }
                if (menu.equals("500hPa位势高度和500hPa风场")) {
                    ctype2 = "h500w500";
                }
                if (menu.equals("500hPa位势高度和700hPa风场")) {
                    ctype2 = "h500w700";
                }

                if (menu.equals("500hPa位势高度和850hPa风场")) {
                    ctype2 = "h500w850";
                }
                if (menu.equals("500hPa位势高度和925hPa风场")) {
                    ctype2 = "h500w925";
                }
                //                if (menu.equals("500hPa位势高度和海平面气压场")) {
//                    ctype = null;
//                }

                //                if (menu.equals("整层水汽含量")) {
//                    ctype = null;
//                }


                if (menu.equals("700hPa比湿场")) {
                    ctype2 = "h700";
                }
                if (menu.equals("850hPa比湿场")) {
                    ctype2 = "h850";
                }
                if (menu.equals("925hPa比湿场")) {
                    ctype2 = "h925";
                }


                if (menu.equals("700hPa水汽通量")) {
                    ctype2 = "h700";
                }
                if (menu.equals("850hPa水汽通量")) {
                    ctype2 = "h850";
                }
                if (menu.equals("925hPa水汽通量")) {
                    ctype2 = "h925";
                }


                if (menu.equals("2米相对湿度场")) {
                    ctype2 = "2m";
                }
                if (menu.equals("500hPa米相对湿度场")) {
                    ctype2 = "h500";
                }
                if (menu.equals("700hPa米相对湿度场")) {
                    ctype2 = "h700";
                }
                if (menu.equals("850hPa米相对湿度场")) {
                    ctype2 = "h850";
                }
                if (menu.equals("925hPa米相对湿度场")) {
                    ctype2 = "h925";
                }


                //                if (menu.equals("海平面气压场")) {
//                    ctype = null;
//                }


                if (menu.equals("500hPa流场和风速")) {
                    ctype2 = "h500";
                }
                if (menu.equals("700hPa流场和风速")) {
                    ctype2 = "h700";
                }
                if (menu.equals("850hPa流场和风速")) {
                    ctype2 = "h850";
                }
                if (menu.equals("925hPa流场和风速")) {
                    ctype2 = "h925";
                }


                if (menu.equals("500hPa假相当位温差和850hPa风场")) {
                    ctype2 = "h500";
                }
                if (menu.equals("500hPa假相当位温差和700hPa风场")) {
                    ctype2 = "h700";
                }
                if (menu.equals("850hPa假相当位温差和风场")) {
                    ctype2 = "h850";
                }


                if (menu.equals("2米温度预报")) {
                    ctype2 = "2m";
                }
                if (menu.equals("700hPa温度预报")) {
                    ctype2 = "h700";
                }
                if (menu.equals("850hPa温度预报")) {
                    ctype2 = "h850";
                }
                if (menu.equals("925hPa温度预报")) {
                    ctype2 = "h925";
                }
                if (menu.equals("地表温度预报")) {
                    ctype2 = "surface";
                }

                //                if (menu.equals("风寒指数预报")) {
//                    ctype = null;
//                }

                progressDialog = ProgressDialog.show(getContext(), "请稍等...", "获取数据中...", true);
                progressDialog.setCancelable(true);
                getTestdata();
            });
        }
        return mAdapter1;
    }


    private void initSpinner() {
        menuTime = new ArrayList<>();
        popupWindow = new ListPopupWindow(getContext());
        timeAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1, menuTime);
        popupWindow.setAdapter(timeAdapter);
        popupWindow.setAnchorView(time);
        popupWindow.setWidth(370);   //WRAP_CONTENT会出错
        popupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setDropDownGravity(Gravity.END);
        popupWindow.setModal(true);
        popupWindow.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                if (oneMenu) {
                    getTwoTime(menuTime.get(position));
                    oneMenu = false;
                } else {
                    getTestDataBytime(menuTime.get(position));
                    getOneTime();
                    oneMenu = true;
                }


                popupWindow.dismiss();

            }
        });
        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.show();

            }
        });
    }


    public void getOneTime() {
        RetrofitHelper.getDataForecastAPI()
                .getDataForecastContentBytime(type)
                .compose(bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(wtfOneMenu -> {
                    menuTime.clear();
                    List<String> menu = wtfOneMenu.getData();
                    for (int i = 0; i < menu.size(); i++) {
                        menuTime.add(menu.get(i));
                    }
                    timeAdapter.notifyDataSetChanged();
                    time.setText(menuTime.get(0));

                }, throwable -> {
                    throwable.printStackTrace();
                    ToastUtils.showShort(getString(R.string.getInfo_error_toast));
                });
    }

    private void getTwoTime(String param) {
        RetrofitHelper.getDataForecastAPI()
                .getDataForecastContentBytime1(type, param)
                .compose(bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(timeTwoMenu -> {
                    menuTime.clear();
                    List<String> data = timeTwoMenu.getData();
                    for (int i = 0; i < data.size(); i++) {
                        menuTime.add(data.get(i));
                    }
                    timeAdapter.notifyDataSetChanged();
                    time.setText(menuTime.get(0));
                }, throwable -> {
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

    public void getRecycleInfo() {
        RetrofitHelper.getDataForecastAPI()
                .getOneMenu("ecmwf_thin")
                .compose(bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(ecOneMenu -> {
                    List<EcOneMenu.DataBean.MenuBean> menu = ecOneMenu.getData().getMenu();
                    for (int i = 0; i < menu.size(); i++) {
                        EcOneMenu.DataBean.MenuBean menuBean = menu.get(i);
                        if (i == whichFragment) {
                            menuBean.setSelect(true);
                            menu.set(i, menuBean);
                        } else {
                            menuBean.setSelect(false);
                            menu.set(i, menuBean);
                        }

                    }

                    if (madapter == null) {
                        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
                        madapter = new EcxwgOneMenuAdapter(menu);
                        recycleView.setLayoutManager(layoutManager);
                        recycleView.setAdapter(madapter);
                        recycleView.smoothScrollToPosition(whichFragment);
                        madapter.setOnItemChildClickListener((adapter, view, position) -> {
//                            List<EcOneMenu.DataBean.MenuBean> data = adapter.getData();
//                            int lastclick = ((EcxwgOneMenuAdapter) adapter).getLastposition();
//                            EcOneMenu.DataBean.MenuBean dataBeanlasted = data.get(lastclick);
//                            dataBeanlasted.setSelect(false);
//                            EcOneMenu.DataBean.MenuBean dataBean = data.get(position);
//                            dataBean.setSelect(true);
//                            adapter.notifyItemChanged(lastclick);
//                            adapter.notifyItemChanged(position);
//                            ((EcxwgOneMenuAdapter) adapter).setLastposition(position);

                            oneMenuViewpager.setCurrentItem(position);
                            for (int i = 0; i < oneMenuFragmentList.size(); i++) {
                                if (position == i) {
                                } else {
                                    EcxwgFragment fragment = (EcxwgFragment) oneMenuFragmentList.get(i);
                                    fragment.setStart(false);
                                    fragment.setImage();
                                }
                            }
                        });
                    }

                }, throwable -> {
                    LogUtils.e(throwable);
                    ToastUtils.showShort(getString(R.string.getInfo_error_toast));
                });
    }


    private void getTestdata() {
        getAdapter3();
        RetrofitHelper.getDataForecastAPI()
                .getEcContent("ecmwf_thin", ctype1, ctype2)
                .compose(bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(ecBeen -> {
                    progressDialog.dismiss();
                    recycle_skipto_position = 2;
                    now_postion = 1;
                    isStart = false;
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
                    urls = ecBeen.getData().getUrl();
                    for (int i = 0; i < urls.size(); i++) {
                        WtfPicFragment fragment = WtfPicFragment.newInstance(urls.get(i), "ecmwf_thin", this,urls);
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
                            if (CallBackUtil.picdispath!=null) {
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


                    List<String> time = ecBeen.getData().getTime();
                    multitemList.clear();
                    MultiItemGdybTx multiItemGdybTx = new MultiItemGdybTx(MultiItemGdybTx.IMG, R.drawable.app_start);
                    multitemList.add(multiItemGdybTx);
                    for (int i = 0; i < time.size(); i++) {

                        GkdmClickBeen clickBeen = new GkdmClickBeen();
                        if (i == 0)
                            clickBeen.setOnclick(true);
                        else clickBeen.setOnclick(false);
                        clickBeen.setText(time.get(i));
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

    public void getTestDataBytime(String timePs) {
        getAdapter3();
        RetrofitHelper.getDataForecastAPI()
                .getEcContent3(type, ctype1, ctype2, timePs)
                .compose(bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(ecBeen -> {
                    progressDialog.dismiss();
                    recycle_skipto_position = 2;
                    now_postion = 1;
                    isStart = false;
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
                    urls = ecBeen.getData().getUrl();
                    for (int i = 0; i < urls.size(); i++) {
                        WtfPicFragment fragment = WtfPicFragment.newInstance(urls.get(i), "ecmwf_thin", this,urls);
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
                            if (CallBackUtil.picdispath!=null) {
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


                    List<String> time = ecBeen.getData().getTime();
                    multitemList.clear();
                    MultiItemGdybTx multiItemGdybTx = new MultiItemGdybTx(MultiItemGdybTx.IMG, R.drawable.app_start);
                    multitemList.add(multiItemGdybTx);
                    for (int i = 0; i < time.size(); i++) {

                        GkdmClickBeen clickBeen = new GkdmClickBeen();
                        if (i == 0)
                            clickBeen.setOnclick(true);
                        else clickBeen.setOnclick(false);
                        clickBeen.setText(time.get(i));
                        multiItemGdybTx = new MultiItemGdybTx(MultiItemGdybTx.TIME_TEXT, clickBeen);
                        multitemList.add(multiItemGdybTx);
                    }
                    getAdapter3().setNewData(multitemList);
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