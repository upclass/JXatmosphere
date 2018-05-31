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
import android.view.View;
import android.widget.ImageView;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;

import net.univr.pushi.jxatmosphere.MyApplication;
import net.univr.pushi.jxatmosphere.R;
import net.univr.pushi.jxatmosphere.adapter.GkdmgcAdapter1;
import net.univr.pushi.jxatmosphere.adapter.GkdmgcAdapter3;
import net.univr.pushi.jxatmosphere.adapter.MyPagerAdapter;
import net.univr.pushi.jxatmosphere.base.RxLazyFragment;
import net.univr.pushi.jxatmosphere.beens.GkdmClickBeen;
import net.univr.pushi.jxatmosphere.beens.GkdmmenuBeen;
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
public class GKDMGCFragment extends RxLazyFragment {

    @BindView(R.id.title_recycle)
    RecyclerView mRecyclerView1;
    @BindView(R.id.viewpager)
    CustomViewPager mViewPager;
    @BindView(R.id.time_recycle)
    RecyclerView mRecyclerView3;

    @BindView(R.id.pic_ready)
    ImageView isStartPic;
    String item;

    List<GkdmClickBeen> mData3 = new ArrayList<>();
    //播放的下一位置
    int recycle_skipto_position ;
    //是否播放
    Boolean isStart = false;
    //现在位置
    int now_postion;
    ProgressDialog progressDialog;


    List<Fragment> fragmentList = new ArrayList<>();
    List<String> urls = new ArrayList<>();
    MyPagerAdapter viewPagerAdapter;


    public void setStart(Boolean start) {
        isStart = start;
        mViewPager.setScanScroll(true);
    }

    public void setImage() {
        isStartPic.setImageResource(R.drawable.app_start);
    }

    private Context mcontext;
    private GkdmgcAdapter1 mAdapter1;
    private GkdmgcAdapter3 mAdapter3;

    public static GKDMGCFragment newInstance(String item) {
        GKDMGCFragment gkdmgcFragment = new GKDMGCFragment();
        Bundle bundle = new Bundle();
        bundle.putString("item", item);
        gkdmgcFragment.setArguments(bundle);
        return gkdmgcFragment;
    }


    @Override
    public int getLayoutResId() {
        return R.layout.fragment_gkdmgc;
    }

    @Override
    public void finishCreateView(Bundle state) {

        mcontext = getActivity();
        if (getArguments() != null) {
            item = getArguments().getString("item");
        }
        if (item.equals("000")) {
            mRecyclerView1.setVisibility(View.GONE);
        }


        getTestdata();
        isStartPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mData3 == null || mData3.size() == 0) return;
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


    private GkdmgcAdapter1 getAdapter1() {
        if (mAdapter1 == null) {
            LinearLayoutManager layoutManager = new LinearLayoutManager(mcontext, LinearLayoutManager.HORIZONTAL, false);
            List<GkdmmenuBeen> mData1 = new ArrayList<>();
            GkdmmenuBeen gkdmmenuBeen1 = new GkdmmenuBeen();
            gkdmmenuBeen1.setSelect(true);
            gkdmmenuBeen1.setText("500百帕");
            GkdmmenuBeen gkdmmenuBeen2 = new GkdmmenuBeen();
            gkdmmenuBeen2.setSelect(false);
            gkdmmenuBeen2.setText("700百帕");
            GkdmmenuBeen gkdmmenuBeen3 = new GkdmmenuBeen();
            gkdmmenuBeen3.setSelect(false);
            gkdmmenuBeen3.setText("850百帕");
            GkdmmenuBeen gkdmmenuBeen4 = new GkdmmenuBeen();
            gkdmmenuBeen4.setSelect(false);
            gkdmmenuBeen4.setText("925百帕");
            mData1.add(gkdmmenuBeen1);
            mData1.add(gkdmmenuBeen2);
            mData1.add(gkdmmenuBeen3);
            mData1.add(gkdmmenuBeen4);

            mAdapter1 = new GkdmgcAdapter1(mData1);
            mRecyclerView1.setLayoutManager(layoutManager);
            mRecyclerView1.setAdapter(mAdapter1);
            mAdapter1.setOnItemChildClickListener((adapter, view, position) -> {

                isStart = false;
                mViewPager.setScanScroll(true);
                isStartPic.setImageResource(R.drawable.app_start);
                List data = adapter.getData();
                int lastClick = ((GkdmgcAdapter1) adapter).getLastClick();
                GkdmmenuBeen been1 = (GkdmmenuBeen) data.get(lastClick);
                GkdmmenuBeen been2 = (GkdmmenuBeen) data.get(position);
                been1.setSelect(false);
                been2.setSelect(true);
                adapter.notifyItemChanged(lastClick);
                adapter.notifyItemChanged(position);
                ((GkdmgcAdapter1) adapter).setLastClick(position);
                if (position == 0) item = "500";
                if (position == 1) item = "700";
                if (position == 2) item = "850";
                if (position == 3) item = "925";
                getTestdata();
                mAdapter3.setLastClickPosition(position);

            });
        }
        return mAdapter1;
    }


    private GkdmgcAdapter3 getAdapter3() {
        if (mAdapter3 == null) {
            ExStaggeredGridLayoutManager layoutManager = new ExStaggeredGridLayoutManager(6, LinearLayoutManager.VERTICAL){
                @Override
                public boolean canScrollVertically() {
                    return false;
                }
            };
            mAdapter3 = new GkdmgcAdapter3(mData3);
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


    private void getTestdata() {
        progressDialog=ProgressDialog.show(getContext(),"请稍等...", "获取数据中...",true);
        progressDialog.setCancelable(true);
        if (item.equals("000")) {
            getAdapter3();
            RetrofitHelper.getWeatherMonitorAPI()
                    .getGKDMGC(item)
                    .compose(bindToLifecycle())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(GKDMGCBeen -> {
                        progressDialog.dismiss();
                        recycle_skipto_position = 1;
                        now_postion=0;
                        List<Fragment> huancunFragments = new ArrayList<>();
                        for (int i = 0; i < fragmentList.size(); i++) {
                            huancunFragments.add(fragmentList.get(i));
                        }
                        fragmentList.clear();

                        urls = GKDMGCBeen.getData().getUrl();
                        for (int i = 0; i < urls.size(); i++) {
                            PicLoadFragment fragment = PicLoadFragment.newInstance(urls.get(i));
                            fragmentList.add(fragment);
                        }

                        viewPagerAdapter = new MyPagerAdapter(getChildFragmentManager(), fragmentList, huancunFragments);
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


                        List<String> time = GKDMGCBeen.getData().getTime();
                        for (int i = 0; i < time.size(); i++) {
                            GkdmClickBeen clickBeen = new GkdmClickBeen();
                            if (i == 0) clickBeen.setOnclick(true);
                            else clickBeen.setOnclick(false);
                            clickBeen.setText(time.get(i));
                            mData3.add(clickBeen);
                        }

                        getAdapter3().setNewData(mData3);
                    }, throwable -> {
                        progressDialog.dismiss();
                        LogUtils.e(throwable);
                        ToastUtils.showShort(getString(R.string.getInfo_error_toast));
                    });
        } else {
            getAdapter1();
            getAdapter3();
            RetrofitHelper.getWeatherMonitorAPI()
                    .getGKDMGC(item)
                    .compose(bindToLifecycle())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(GKDMGCBeen -> {
                        progressDialog.dismiss();
                        recycle_skipto_position = 1;
                        now_postion=0;
                        List<Fragment> huancunFragment = new ArrayList<>();
                        for (int i = 0; i < fragmentList.size(); i++) {
                            huancunFragment.add(fragmentList.get(i));
                        }
                        fragmentList.clear();
                        urls = GKDMGCBeen.getData().getUrl();
                        for (int i = 0; i < urls.size(); i++) {
                            PicLoadFragment fragment = PicLoadFragment.newInstance(urls.get(i));
                            fragmentList.add(fragment);
                        }
                        viewPagerAdapter = new MyPagerAdapter(
                                getChildFragmentManager(),fragmentList, huancunFragment);

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

                        List<String> time = GKDMGCBeen.getData().getTime();
                        mData3.clear();
                        for (int i = 0; i < time.size(); i++) {
                            GkdmClickBeen clickBeen = new GkdmClickBeen();
                            if (i == time.size()-1)
                                clickBeen.setOnclick(true);
                            else clickBeen.setOnclick(false);
                            clickBeen.setText(time.get(i));
                            mData3.add(clickBeen);
                        }
                        getAdapter3().setNewData(mData3);
                        mViewPager.setCurrentItem(time.size()-1);
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
