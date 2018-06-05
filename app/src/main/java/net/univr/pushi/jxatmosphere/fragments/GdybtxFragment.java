package net.univr.pushi.jxatmosphere.fragments;


import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;

import net.univr.pushi.jxatmosphere.MyApplication;
import net.univr.pushi.jxatmosphere.R;
import net.univr.pushi.jxatmosphere.adapter.DmcgjcAdapter3;
import net.univr.pushi.jxatmosphere.adapter.ViewpageAdapter;
import net.univr.pushi.jxatmosphere.base.RxLazyFragment;
import net.univr.pushi.jxatmosphere.beens.GdybtxMenuBeen;
import net.univr.pushi.jxatmosphere.beens.GkdmClickBeen;
import net.univr.pushi.jxatmosphere.utils.ExStaggeredGridLayoutManager;
import net.univr.pushi.jxatmosphere.widget.CustomViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

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

//    @BindView(R.id.pic_ready)
//    ImageView isStartPic;

    //    @BindView(R.id.tvShow)
//    TextView tvShow;
    @BindView(R.id.spDwon)
    Spinner spDown;

    private List<String> oneMenu = new ArrayList<>();
    private ArrayAdapter<String> spinAdapter;
    List<GdybtxMenuBeen.DataBean.MenuBean> menulist;
    CustomViewPager actviewpager;

    private Context mcontext;


    ViewpageAdapter viewPagerAdapter;


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
    private ImageView parent;


    public static GdybtxFragment newInstance(String type, List<GdybtxMenuBeen.DataBean.MenuBean> list, CustomViewPager viewpager) {

        GdybtxFragment gdybtxFragment = new GdybtxFragment();
        Bundle bundle = new Bundle();
        bundle.putString("type", type);
        gdybtxFragment.menulist = list;
        gdybtxFragment.actviewpager = viewpager;
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
        if (parent != null)
            parent.setImageResource(R.drawable.app_start);
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
        initOneMenu();
//        getTwoMenuInfo();


//        getTestdata();

        parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isStart == false) {
                    parent.setImageResource(R.drawable.app_end);
                    Message message = uiHandler.obtainMessage();
                    message.what = 1;
                    uiHandler.sendMessageDelayed(message, MyApplication.getInstance().auto_time);
                    isStart = true;
                    mViewPager.setScanScroll(false);
                } else {
                    uiHandler.removeCallbacksAndMessages(null);
                    parent.setImageResource(R.drawable.app_start);
                    isStart = false;
                    mViewPager.setScanScroll(true);
                }

            }
        });

    }

    private void initOneMenu() {
//        if(type.equals("rain"))
//        tvShow.setText(menulist.get(0).getName());
//        if(type.equals("temp"))
//            tvShow.setText(menulist.get(1).getName());
//        if(type.equals("wp"))
//            tvShow.setText(menulist.get(2).getName());
        for (int i = 0; i < menulist.size(); i++) {
            oneMenu.add(menulist.get(i).getName());
        }
      /*新建适配器*/
        spinAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, oneMenu);

        /*adapter设置一个下拉列表样式，参数为系统子布局*/
        spinAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        /*spDown加载适配器*/
        spDown.setAdapter(spinAdapter);

        /*soDown的监听器*/
        if (type.equals("rain"))
            spDown.setSelection(0);
        if (type.equals("temp"))
            spDown.setSelection(1);
        if (type.equals("wp"))
            spDown.setSelection(2);
        spDown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                actviewpager.setCurrentItem(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }






    private DmcgjcAdapter3 getAdapter3() {
        if (mAdapter3 == null) {
            ExStaggeredGridLayoutManager layoutManager = new ExStaggeredGridLayoutManager(6, StaggeredGridLayoutManager.VERTICAL) {
                @Override
                public boolean canScrollVertically() {
                    return false;
                }
            };
            mAdapter3 = new DmcgjcAdapter3(mData3);
            mRecyclerView3.setLayoutManager(layoutManager);
            LayoutInflater inflater = LayoutInflater.from(getContext());
            LinearLayout linear = (LinearLayout) inflater.inflate(R.layout.gdybtxheader_layout, null);
            parent = linear.findViewById(R.id.pic_ready);
            linear.removeView(parent);
            mAdapter3.addHeaderView(parent);
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
