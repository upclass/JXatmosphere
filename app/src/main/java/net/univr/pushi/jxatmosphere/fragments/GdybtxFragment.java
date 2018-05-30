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
import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.squareup.picasso.Picasso;

import net.univr.pushi.jxatmosphere.MyApplication;
import net.univr.pushi.jxatmosphere.R;
import net.univr.pushi.jxatmosphere.adapter.DmcgjcAdapter3;
import net.univr.pushi.jxatmosphere.adapter.DmcgjcMenuAdapter;
import net.univr.pushi.jxatmosphere.adapter.ViewpageAdapter;
import net.univr.pushi.jxatmosphere.base.RxLazyFragment;
import net.univr.pushi.jxatmosphere.beens.DmcgjcmenuBeen;
import net.univr.pushi.jxatmosphere.beens.GkdmClickBeen;
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
public class GdybtxFragment extends RxLazyFragment {

    @BindView(R.id.viepager)
    CustomViewPager mViewPager;
    @BindView(R.id.recycler3)
    RecyclerView mRecyclerView3;
    @BindView(R.id.recycler1)
    RecyclerView mRecyclerView1;

    @BindView(R.id.pic_ready)
    ImageView isStartPic;

    private Context mcontext;


    ViewpageAdapter viewPagerAdapter;

    private DmcgjcMenuAdapter mAdapter1;
    private DmcgjcAdapter3 mAdapter3;
    List<GkdmClickBeen> mData3 = new ArrayList<>();
    String type;


    //是否播放
    Boolean isStart = false;

    //现在的位置
    int now_postion;


    //播放的下一位置
    int recycle_skipto_position = 1;
    List<ImageView> list;

    ProgressDialog progressDialog;


    public static GdybtxFragment newInstance(String type) {
        GdybtxFragment gdybtxFragment = new GdybtxFragment();
        Bundle bundle = new Bundle();
        bundle.putString("type", type);
        gdybtxFragment.setArguments(bundle);
        return gdybtxFragment;
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
        return R.layout.fragment_gdybtx;
    }

    @Override
    public void finishCreateView(Bundle state) {
        mcontext = getActivity();

        if (getArguments() != null) {
            //取出保存的值
            type = getArguments().getString("type");
        }

        getTwoMenuInfo();


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

    private void getTwoMenuInfo() {
        RetrofitHelper.getForecastWarn()
                .getGdybtTwoMenu(type)
                .compose(bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(gdybtxTwoMenu -> {
                    List<String> menu = gdybtxTwoMenu.getData().getMenu();
                    List<DmcgjcmenuBeen.DataBean> dataBeans = new ArrayList<>();
                    for (int i = 0; i < menu.size(); i++) {
                        DmcgjcmenuBeen.DataBean temp=new DmcgjcmenuBeen.DataBean();
                        String menuname = menu.get(i);
                        temp.setZnName(menuname);
                        if(i==0) temp.setSelect(true);
                        else
                        temp.setSelect(false);
                        dataBeans.add(temp);
                    }

                    if (menu!=null&&menu.size()>0){
                       getAdapter1();
                       mAdapter1.setNewData(dataBeans);

                    }
                    else mRecyclerView1.setVisibility(View.INVISIBLE);
                }, throwable -> {
                    LogUtils.e(throwable);
                    ToastUtils.showShort(getString(R.string.getInfo_error_toast));
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
                if (type.equals("wp")&&menu.equals("逐小时")) {
                    type = "wp";
                }

                if (type.equals("wp")&&menu.equals("逐3小时")) {
                    type = "wp3";
                }

                if (type.equals("wp")&&menu.equals("逐6小时")) {
                    type = "wp6";
                }
                if (type.equals("wp")&&menu.equals("逐12小时")) {
                    type = "wp12";
                }

                if (type.equals("rain")&&menu.equals("逐小时")) {
                    type = "rain";
                }
                if (type.equals("rain")&&menu.equals("逐3小时")) {
                    type = "rain3";
                }
                if (type.equals("rain")&&menu.equals("逐6小时")) {
                    type = "rain6";
                }
                if (type.equals("rain")&&menu.equals("逐12小时")) {
                    type = "rain12";
                }
//                if (type.equals("10uv")&&menu.equals("逐小时（001-024小时预报）")) {
//                    type = "10uv";
//                }
//                if (type.equals("10uv")&&menu.equals("逐3小时(027-072小时预报）")) {
//                    type = "10uv3";
//                }
//                if (type.equals("10uv")&&menu.equals("逐12小时（084-240小时预报）")) {
//                    type = "10uv12";
//                }

                progressDialog = ProgressDialog.show(getContext(), "请稍等...", "获取数据中...", true);
                progressDialog.setCancelable(true);
                getTestdata();
            });
        }
        return mAdapter1;
    }


    private DmcgjcAdapter3 getAdapter3() {
        if (mAdapter3 == null) {
            LinearLayoutManager layoutManager = new LinearLayoutManager(mcontext, LinearLayoutManager.HORIZONTAL, false);
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


    private void getTestdata() {
        progressDialog = ProgressDialog.show(getContext(), "请稍等...", "获取数据中...", true);
        progressDialog.setCancelable(true);
        getAdapter3();
        RetrofitHelper.getForecastWarn()
                .getGdybt(type)
                .compose(bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(gdybtx -> {
                    progressDialog.dismiss();
                    recycle_skipto_position = 1;
                    now_postion = 0;


                    list = new ArrayList<>();
                    List<String> picList = gdybtx.getData().getPicList();
                    for (int i = 0; i < picList.size(); i++) {
                        ImageView imageView = new ImageView(getContext());
                        Picasso.with(getContext()).load(picList.get(i)).placeholder(R.drawable.app_imageplacehold).into(imageView);
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


                    List<String> time = gdybtx.getData().getTimeList();
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
