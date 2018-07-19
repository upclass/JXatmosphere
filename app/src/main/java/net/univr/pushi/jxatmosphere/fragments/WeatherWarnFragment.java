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
import net.univr.pushi.jxatmosphere.widget.CustomViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * A simple {@link Fragment} subclass.
 */
public class WeatherWarnFragment extends RxLazyFragment {

    @BindView(R.id.viewpager)
    CustomViewPager viewPager;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    ProgressDialog progressDialog;

    MultiGdybTxAdapter adapter;
    MyPagerAdapter pagerAdapter;
    List<Fragment> list = new ArrayList<>();
    public String type;

    ImageView isStartPic;

    //是否播放
    Boolean isStart = false;
    //现在的位置
    int now_postion = 1;

    //播放的下一位置
    int recycle_skipto_position = 2;
    private List<MultiItemGdybTx> multiItemGdybTxList = new ArrayList<>();

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_weather_warn;
    }

    public static WeatherWarnFragment newInstance(String type) {
        WeatherWarnFragment weatherWarnFragment = new WeatherWarnFragment();
        Bundle bundle = new Bundle();
        bundle.putString("type", type);
        weatherWarnFragment.setArguments(bundle);
        return weatherWarnFragment;
    }


    @Override
    public void finishCreateView(Bundle state) {
        if (getArguments() != null) {
            Bundle arguments = getArguments();
            this.type = arguments.get("type").toString();
        }
        getPicList();
    }


    public void setStart(Boolean start) {
        isStart = start;
        if (viewPager != null) {
            viewPager.setScanScroll(true);
        }
    }

    public void setImage() {
        if (isStartPic != null)
            isStartPic.setImageResource(R.drawable.app_start);
    }

    public void getPicList() {
        progressDialog = ProgressDialog.show(getContext(), "请稍等...", "获取数据中...", true);
        progressDialog.setCancelable(true);
        getAdapter();
        RetrofitHelper.getForecastWarn()
                .getQxfx(type)
                .compose(bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(QxfxBeen -> {
                    progressDialog.dismiss();
                    multiItemGdybTxList.clear();
                    isStart=false;
                    uiHandler.removeCallbacksAndMessages(null);
                    int length = QxfxBeen.getData().getTime().size();
                    MultiItemGdybTx multiItemGdybTx = new MultiItemGdybTx(MultiItemGdybTx.IMG, R.drawable.app_start);
                    multiItemGdybTxList.add(multiItemGdybTx);
                    for (int i = 0; i < length; i++) {
                        MultiItemGdybTx multiItemGdybTx1 = new MultiItemGdybTx(MultiItemGdybTx.TIME_TEXT, null);
                        String temp = QxfxBeen.getData().getTime().get(i);
                        GkdmClickBeen clickBeen = new GkdmClickBeen();
                        String time = temp;
                        now_postion=1;
//                        String time = temp.substring(length - 4, length);
//                        StringBuilder timeBuilder = new StringBuilder(time);
//                        timeBuilder.insert(2, ":");
//                        time = timeBuilder.toString();
                        clickBeen.setText(time);
                        if (i == 0) clickBeen.setOnclick(true);
                        else clickBeen.setOnclick(false);
                        multiItemGdybTx1.setContent(clickBeen);
                        multiItemGdybTxList.add(multiItemGdybTx1);
                    }
                    adapter.setNewData(multiItemGdybTxList);

                    List<String> url = QxfxBeen.getData().getUrl();
                    List<Fragment> fragmentHuancun = new ArrayList<>();
                    for (int i = 0; i < list.size(); i++) {
                        fragmentHuancun.add(list.get(i));
                    }
                    list.clear();
                    for (int i = 0; i < url.size(); i++) {
                        PicLoadFragment picLoadFragment = PicLoadFragment.newInstance(url.get(i), url, "weathWarn/" + type);
                        list.add(picLoadFragment);
                    }
//                    for (int i = 0; i < url.size(); i++) {
//                        int finalI = i;
//                        new Thread(new Runnable() {
//                            @Override
//                            public void run() {
//                                PicUtils.decodeUriAsBitmapFromNet(url.get(finalI), "weathWarn/" + type);
//                            }
//                        }).start();
//                    }

                    pagerAdapter = new MyPagerAdapter(getChildFragmentManager(), list, fragmentHuancun);
                    viewPager.setAdapter(pagerAdapter);
                    viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                        @Override
                        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                        }

                        @Override
                        public void onPageSelected(int position) {
                            if (CallBackUtil.picdispath != null) {
                                CallBackUtil.doDispathPic(position);
                            }
                            MultiItemGdybTx multiItemGdybTxStop = multiItemGdybTxList.get(now_postion);
                            GkdmClickBeen clickBeenStop = multiItemGdybTxStop.getContent();
                            clickBeenStop.setOnclick(false);
                            multiItemGdybTxStop.setContent(clickBeenStop);
                            MultiItemGdybTx multiItemGdybTxNow = multiItemGdybTxList.get(position + 1);
                            GkdmClickBeen clickBeenNow = multiItemGdybTxNow.getContent();
                            clickBeenNow.setOnclick(true);
                            multiItemGdybTxNow.setContent(clickBeenNow);
                            multiItemGdybTxList.set(now_postion, multiItemGdybTxStop);
                            multiItemGdybTxList.set(position + 1, multiItemGdybTxNow);
                            adapter.notifyItemChanged(now_postion);
                            adapter.notifyItemChanged(position + 1);
                            now_postion = position + 1;
                            recyclerView.smoothScrollToPosition(position + 1);
                            recycle_skipto_position = position + 2;
                            if (recycle_skipto_position > multiItemGdybTxList.size() - 1)
                                recycle_skipto_position = 1;
                        }

                        @Override
                        public void onPageScrollStateChanged(int state) {

                        }
                    });


                }, throwable -> {
                    progressDialog.dismiss();
                    LogUtils.e(throwable);
                    ToastUtils.showShort(R.string.getInfo_error_toast);
                });
    }

    MultiGdybTxAdapter getAdapter() {
        if (adapter == null) {
            multiItemGdybTxList = new ArrayList<>();
            adapter = new MultiGdybTxAdapter(multiItemGdybTxList);
            StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(6, StaggeredGridLayoutManager.VERTICAL) {
                @Override
                public boolean canScrollVertically() {
                    return false;
                }
            };
            recyclerView.setLayoutManager(manager);
            recyclerView.setAdapter(adapter);
            adapter.setOnItemChildClickListener((adapter, view, position) -> {
                switch (view.getId()) {
                    case R.id.time:
                        if (isStart == false) {
                            viewPager.setCurrentItem(position - 1);
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
                            viewPager.setScanScroll(false);
                        } else {
                            uiHandler.removeCallbacksAndMessages(null);
                            isStartPic.setImageResource(R.drawable.app_start);
                            isStart = false;
                            viewPager.setScanScroll(true);
                        }
                }
            });

        }
        return adapter;
    }

    private Handler uiHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (recyclerView != null) {
                switch (msg.what) {
                    case 1:
                        viewPager.setCurrentItem(recycle_skipto_position - 1);
                        if (recycle_skipto_position > multiItemGdybTxList.size() - 1) {
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
