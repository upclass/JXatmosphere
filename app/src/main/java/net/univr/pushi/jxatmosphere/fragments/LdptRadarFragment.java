package net.univr.pushi.jxatmosphere.fragments;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.widget.ImageView;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;

import net.univr.pushi.jxatmosphere.MyApplication;
import net.univr.pushi.jxatmosphere.R;
import net.univr.pushi.jxatmosphere.adapter.MultiGdybTxAdapter;
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
public class LdptRadarFragment extends RxLazyFragment {


    @BindView(R.id.viewpager)
    CustomViewPager mViewPager;

    @BindView(R.id.time_recycle)
    RecyclerView mRecyclerView3;

//    @BindView(R.id.pic_ready)
//    ImageView isStartPic;


    List<MultiItemGdybTx> mData3 = new ArrayList<>();
    //播放的下一位置
    int recycle_skipto_position;
    //是否播放
    Boolean isStart = false;
    //现在的位置
    int now_postion;
    ProgressDialog progressDialog = null;


    List<Fragment> fragmentList = new ArrayList<>();
    List<String> urls = new ArrayList<>();
    List<MultiItemGdybTx> multitemList = new ArrayList<>();
    MyPagerAdapter viewPagerAdapter;
    String flag;
    private MultiGdybTxAdapter mAdapter;
    ImageView isStartPic;


    public void setStart(Boolean start) {
        isStart = start;
        mViewPager.setScanScroll(true);
    }


    public void setImage() {
        if (isStartPic != null)
            isStartPic.setImageResource(R.drawable.app_start);
    }

    public static LdptRadarFragment newInstance(String flag) {
        LdptRadarFragment ldptRadarFragment = new LdptRadarFragment();
        Bundle bundle = new Bundle();
        bundle.putString("flag", flag);
        ldptRadarFragment.setArguments(bundle);
        return ldptRadarFragment;
    }


    @Override
    public int getLayoutResId() {
        return R.layout.fragment_ldpt_radar;
    }

    @Override
    public void finishCreateView(Bundle state) {
        if (getArguments() != null) {
            flag = getArguments().getString("flag");
        }
        getTestdata();
    }


    private MultiGdybTxAdapter getAdapter3() {
        if (mAdapter == null) {
            ExStaggeredGridLayoutManager layoutManager = new ExStaggeredGridLayoutManager(6, StaggeredGridLayoutManager.VERTICAL) {
                @Override
                public boolean canScrollVertically() {
                    return false;
                }
            };
            mAdapter = new MultiGdybTxAdapter(mData3);
            mRecyclerView3.setLayoutManager(layoutManager);
            mRecyclerView3.setAdapter(mAdapter);
            mAdapter.setOnItemChildClickListener((adapter, view, position) -> {
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
        return mAdapter;
    }


    private void getTestdata() {
        progressDialog = ProgressDialog.show(getContext(), "请稍等...", "获取数据中...", true);
        progressDialog.setCancelable(true);
        if (flag.equals("1")) {
            getAdapter3();
            RetrofitHelper.getWeatherMonitorAPI()
                    .getLdpt()
                    .compose(bindToLifecycle())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(LdptBeen -> {
                        progressDialog.dismiss();
                        now_postion = LdptBeen.getData().getBtnName().size() - 1;
                        recycle_skipto_position = 1;

                        List<Fragment> huancunFragments = new ArrayList<>();
                        for (int i = 0; i < fragmentList.size(); i++) {
                            huancunFragments.add(fragmentList.get(i));
                        }
                        fragmentList.clear();
                        urls = LdptBeen.getData().getImageUrl();
                        for (int i = 0; i < urls.size(); i++) {
                            PicLoadFragment fragment = PicLoadFragment.newInstance(urls.get(i),urls,"ldptRadar/"+flag);
                            fragmentList.add(fragment);
                        }

//                        new Thread(new Runnable() {
//                            @Override
//                            public void run() {
//                                for (int i = 0; i < urls.size(); i++) {
//                                    PicUtils.decodeUriAsBitmapFromNet(urls.get(i),"ldptRadar/"+flag);
//                                }
//                            }
//                        }).start();
//                        for (int i = 0; i < urls.size(); i++) {
//                            int finalI = i;
//                            new Thread(new Runnable() {
//                                @Override
//                                public void run() {
//                                    PicUtils.decodeUriAsBitmapFromNet(urls.get(finalI),"ldptRadar/"+flag);
//                                }
//                            }).start();
//                        }

                        viewPagerAdapter = new MyPagerAdapter(getChildFragmentManager(), fragmentList, huancunFragments);
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
                                mAdapter.notifyItemChanged(now_postion);
                                mAdapter.notifyItemChanged(position + 1);
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


                        List<String> time = LdptBeen.getData().getBtnName();
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
        } else {
            getAdapter3();
            RetrofitHelper.getWeatherMonitorAPI()
                    .getWxyt()
                    .compose(bindToLifecycle())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(wxytBeen -> {
                        progressDialog.dismiss();
                        recycle_skipto_position = 1;
                        now_postion = wxytBeen.getData().getBtnNameList().size() - 1;
                        List<Fragment> huancunFragments = new ArrayList<>();
                        for (int i = 0; i < fragmentList.size(); i++) {
                            huancunFragments.add(fragmentList.get(i));
                        }
                        fragmentList.clear();

                        urls = wxytBeen.getData().getImageUrl();
                        for (int i = 0; i < urls.size(); i++) {
                            PicLoadFragment fragment = PicLoadFragment.newInstance(urls.get(i),urls,"ldptRadar/"+flag);
                            fragmentList.add(fragment);
                        }

//                        new Thread(new Runnable() {
//                            @Override
//                            public void run() {
//                                for (int i = 0; i < urls.size(); i++) {
//                                    PicUtils.decodeUriAsBitmapFromNet(urls.get(i),"ldptRadar/"+flag);
//                                }
//                            }
//                        }).start();

//                        for (int i = 0; i < urls.size(); i++) {
//                            int finalI = i;
//                            new Thread(new Runnable() {
//                                @Override
//                                public void run() {
//                                    PicUtils.decodeUriAsBitmapFromNet(urls.get(finalI), "ldptRadar/"+flag);
//                                }
//                            }).start();
//                        }

                        viewPagerAdapter = new MyPagerAdapter(getChildFragmentManager(), fragmentList, huancunFragments);
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
                                mAdapter.notifyItemChanged(now_postion);
                                mAdapter.notifyItemChanged(position + 1);
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

                        List<String> time = wxytBeen.getData().getBtnNameList();
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



