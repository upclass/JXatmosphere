package net.univr.pushi.jxatmosphere.feature;

import android.animation.ValueAnimator;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;

import net.univr.pushi.jxatmosphere.R;
import net.univr.pushi.jxatmosphere.adapter.DmcgjcCustomMenuAdapter;
import net.univr.pushi.jxatmosphere.adapter.DmcgjcMenuAdapterForSwzd;
import net.univr.pushi.jxatmosphere.adapter.MyPagerAdapter;
import net.univr.pushi.jxatmosphere.base.BaseActivity;
import net.univr.pushi.jxatmosphere.beens.DmcgjcCustomMenu;
import net.univr.pushi.jxatmosphere.beens.DmcgjcmenuBeen;
import net.univr.pushi.jxatmosphere.fragments.DMCGJCFragment;
import net.univr.pushi.jxatmosphere.fragments.SWZDYLFragment;
import net.univr.pushi.jxatmosphere.interfaces.BrightnessActivity;
import net.univr.pushi.jxatmosphere.interfaces.CallBackUtil;
import net.univr.pushi.jxatmosphere.remote.RetrofitHelper;
import net.univr.pushi.jxatmosphere.utils.PicUtils;
import net.univr.pushi.jxatmosphere.widget.CustomViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class DMCGJCActivity extends BaseActivity implements View.OnClickListener {


    @BindView(R.id.back)
    ImageView leave;
    @BindView(R.id.reload)
    ImageView reload;

    @BindView(R.id.viepager)
    CustomViewPager mViewPager;

    //一级菜单
    @BindView(R.id.menu_fragment)
    RecyclerView menu_recycle;

    //二级菜单
    @BindView(R.id.recycler1)
    RecyclerView mRecyclerView1;


    private DmcgjcCustomMenuAdapter menuAdapter;
    private DmcgjcMenuAdapterForSwzd mAdapter1;


    ProgressDialog progressDialog;

    List<Fragment> fragmentlist = new ArrayList<>();


    List<DmcgjcmenuBeen.DataBean> oneMenuList = new ArrayList<>();
    List<DmcgjcmenuBeen.DataBean> twoMenuList = new ArrayList<>();

    MyPagerAdapter viewPagerAdapter;

    List<DmcgjcCustomMenu.DataBean> menuData;


    @Override
    public int getLayoutId() {
        return R.layout.activity_dmcgjc;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        super.initViews(savedInstanceState);
        initView();

    }

    private void getCustomMenuData() {
        RetrofitHelper.getWeatherMonitorAPI()
                .getDmcgjcCustomMenu()
                .compose(bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(DmcgjcCustomMenu -> {
                            menuData = DmcgjcCustomMenu.getData();

                            DmcgjcmenuBeen.DataBean twoMenuItemSW = new DmcgjcmenuBeen.DataBean();
                            twoMenuItemSW.setSelect(false);
                            twoMenuItemSW.setType("rain");
                            twoMenuItemSW.setPaname("降水");
                            twoMenuItemSW.setName("SW");
                            twoMenuItemSW.setZnName("气象水文");
                            twoMenuList.add(twoMenuItemSW);
                            //一级菜单
                            for (int i = 0; i < menuData.size(); i++) {
                                List<DmcgjcCustomMenu.DataBean.TwoMenuBean> twoMenu = menuData.get(i).getTwoMenu();
                                DmcgjcmenuBeen.DataBean dataBean = new DmcgjcmenuBeen.DataBean();
                                if (i == 0)
                                    dataBean.setSelect(true);
                                else dataBean.setSelect(false);
                                dataBean.setZnName(twoMenu.get(0).getZnName());
                                dataBean.setId(twoMenu.get(0).getId());
                                dataBean.setName(twoMenu.get(0).getName());
                                dataBean.setPaname(twoMenu.get(0).getPaname());
                                dataBean.setType(twoMenu.get(0).getType());
                                oneMenuList.add(dataBean);
                            }
                            //二级菜单
                            List<DmcgjcCustomMenu.DataBean.TwoMenuBean> twoMenu = menuData.get(0).getTwoMenu();
                            for (int i = 0; i < twoMenu.size(); i++) {
                                DmcgjcmenuBeen.DataBean dataBean = new DmcgjcmenuBeen.DataBean();
                                if (twoMenu.get(i).getZnName().equals("24h"))
                                    dataBean.setSelect(true);
                                dataBean.setZnName(twoMenu.get(i).getZnName());
                                dataBean.setId(twoMenu.get(i).getId());
                                dataBean.setName(twoMenu.get(i).getName());
                                dataBean.setPaname(twoMenu.get(i).getPaname());
                                dataBean.setType(twoMenu.get(i).getType());
                                twoMenuList.add(dataBean);
                            }
                            getMenuAdapter().setNewData(oneMenuList);
                            getAdapter1().setNewData(twoMenuList);

                            for (int i = 0; i < twoMenuList.size(); i++) {
                                if (i == 6) {
                                        fragmentlist.add(DMCGJCFragment.newInstance(twoMenuList.get(i).getType(), twoMenuList.get(i).getName(), true));
                                } else {
                                    if (twoMenuList.get(i).getZnName().equals("气象水文")) {
                                        fragmentlist.add(new SWZDYLFragment());
                                    } else {
                                        fragmentlist.add(DMCGJCFragment.newInstance(twoMenuList.get(i).getType(), twoMenuList.get(i).getName(), false));
                                    }
                                }

                            }
                            viewPagerAdapter = new MyPagerAdapter(getSupportFragmentManager(), fragmentlist, null);
                            // 绑定适配器
                            mViewPager.setOffscreenPageLimit(8);
                            mViewPager.setAdapter(viewPagerAdapter);
                            mViewPager.setCurrentItem(6);

                        }
                        , throwable -> {
                            LogUtils.e(throwable);
                            ToastUtils.showShort("请求菜单数据异常");
                        });
    }


    private DmcgjcCustomMenuAdapter getMenuAdapter() {
        if (menuAdapter == null) {
            LinearLayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
            List<DmcgjcmenuBeen.DataBean> mData1 = new ArrayList<>();
            menuAdapter = new DmcgjcCustomMenuAdapter(mData1);
            menu_recycle.setLayoutManager(layoutManager);
            menu_recycle.setAdapter(menuAdapter);
            menuAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
                @Override
                public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                    getAdapter1().setLastposition(0);
                    DmcgjcCustomMenuAdapter adapter1 = (DmcgjcCustomMenuAdapter) adapter;
                    int lastposition = adapter1.getLastposition();
                    DmcgjcmenuBeen.DataBean dataBean = adapter1.getData().get(position);
                    DmcgjcmenuBeen.DataBean dataBeanLast = adapter1.getData().get(lastposition);
                    dataBeanLast.setSelect(false);
                    dataBean.setSelect(true);
                    adapter.notifyItemChanged(lastposition);
                    adapter.notifyItemChanged(position);
                    adapter1.setLastposition(position);
                    if (position == 0) {
                        twoMenuList.clear();
                        getAdapter1().setLastposition(6);
                        //二级菜单
                        List<DmcgjcCustomMenu.DataBean.TwoMenuBean> twoMenu = menuData.get(0).getTwoMenu();

                        DmcgjcmenuBeen.DataBean twoMenuItemSW = new DmcgjcmenuBeen.DataBean();
                        twoMenuItemSW.setSelect(false);
                        twoMenuItemSW.setType("rain");
                        twoMenuItemSW.setPaname("降水");
                        twoMenuItemSW.setName("SW");
                        twoMenuItemSW.setZnName("气象水文");
                        twoMenuList.add(twoMenuItemSW);

                        for (int i = 0; i < twoMenu.size(); i++) {
                            DmcgjcmenuBeen.DataBean dataBean1 = new DmcgjcmenuBeen.DataBean();
                            if (twoMenu.get(i).getZnName().equals("24h"))
                                dataBean1.setSelect(true);
                            dataBean1.setZnName(twoMenu.get(i).getZnName());
                            dataBean1.setId(twoMenu.get(i).getId());
                            dataBean1.setName(twoMenu.get(i).getName());
                            dataBean1.setPaname(twoMenu.get(i).getPaname());
                            dataBean1.setType(twoMenu.get(i).getType());
                            twoMenuList.add(dataBean1);
                        }
                        getAdapter1().setNewData(twoMenuList);
                        List<Fragment> fragmentHuancun = new ArrayList<>();
                        for (int i = 0; i < fragmentlist.size(); i++) {
                            fragmentHuancun.add(fragmentlist.get(i));
                        }
                        fragmentlist.clear();

                        for (int i = 0; i < twoMenuList.size(); i++) {
                            if (i == 6) {
                                    fragmentlist.add(DMCGJCFragment.newInstance(twoMenuList.get(i).getType(), twoMenuList.get(i).getName(), true));
                            } else {
                                if (twoMenuList.get(i).getZnName().equals("气象水文")) {
                                    fragmentlist.add(new SWZDYLFragment());
                                } else {
                                    fragmentlist.add(DMCGJCFragment.newInstance(twoMenuList.get(i).getType(), twoMenuList.get(i).getName(), false));
                                }
                            }

                        }

                        viewPagerAdapter = new MyPagerAdapter(getSupportFragmentManager(), fragmentlist, fragmentHuancun);
                        // 绑定适配器
                        mViewPager.setAdapter(viewPagerAdapter);
                        for (Fragment fragment : fragmentlist) {
                            fragmentHuancun.add(fragment);
                        }
                        mViewPager.setCurrentItem(6);
                    } else {
                        twoMenuList.clear();
                        List<DmcgjcCustomMenu.DataBean.TwoMenuBean> twoMenu = menuData.get(position).getTwoMenu();
                        for (int i = 0; i < twoMenu.size(); i++) {
                            DmcgjcmenuBeen.DataBean dataBean1 = new DmcgjcmenuBeen.DataBean();
                            if (i == 0) dataBean1.setSelect(true);
                            else dataBean1.setSelect(false);
                            dataBean1.setZnName(twoMenu.get(i).getZnName());
                            dataBean1.setId(twoMenu.get(i).getId());
                            dataBean1.setName(twoMenu.get(i).getName());
                            dataBean1.setPaname(twoMenu.get(i).getPaname());
                            dataBean1.setType(twoMenu.get(i).getType());
                            twoMenuList.add(dataBean1);
                        }
                        getAdapter1().setNewData(twoMenuList);

                        List<Fragment> fragmentHuancun = new ArrayList<>();
                        for (int i = 0; i < fragmentlist.size(); i++) {
                            fragmentHuancun.add(fragmentlist.get(i));
                        }
                        fragmentlist.clear();
                        for (int i = 0; i < twoMenu.size(); i++) {
                            if (i == 0)
                                fragmentlist.add(DMCGJCFragment.newInstance(twoMenu.get(i).getType(), twoMenu.get(i).getName(), true));
                            else
                                fragmentlist.add(DMCGJCFragment.newInstance(twoMenu.get(i).getType(), twoMenu.get(i).getName(), false));
                        }
                        viewPagerAdapter = new MyPagerAdapter(getSupportFragmentManager(), fragmentlist, fragmentHuancun);
                        // 绑定适配器
                        mViewPager.setAdapter(viewPagerAdapter);
                        for (Fragment fragment : fragmentlist) {
                            fragmentHuancun.add(fragment);
                        }
                    }
                    layoutManager.scrollToPositionWithOffset(position, 0);
                    layoutManager.setStackFromEnd(false);
                    LinearLayoutManager layoutManager1 = (LinearLayoutManager) mRecyclerView1.getLayoutManager();
                    layoutManager1.scrollToPositionWithOffset(0, 0);
                    layoutManager1.setStackFromEnd(false);
                }
            });
        }
        return menuAdapter;
    }

    private DmcgjcMenuAdapterForSwzd getAdapter1() {
        if (mAdapter1 == null) {
            LinearLayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
            List<DmcgjcmenuBeen.DataBean> mData1 = new ArrayList<>();

            mAdapter1 = new DmcgjcMenuAdapterForSwzd(mData1);
            mRecyclerView1.setLayoutManager(layoutManager);
            mRecyclerView1.setAdapter(mAdapter1);
            mAdapter1.setLastposition(6);//选中24h
            mAdapter1.setOnItemChildClickListener((adapter, view, position) -> {
                layoutManager.scrollToPositionWithOffset(position, 0);
                layoutManager.setStackFromEnd(false);
                List<DmcgjcmenuBeen.DataBean> data = adapter.getData();
                int lastclick = ((DmcgjcMenuAdapterForSwzd) adapter).getLastposition();
                DmcgjcmenuBeen.DataBean dataBeanlasted = data.get(lastclick);
                DmcgjcmenuBeen.DataBean dataBean = data.get(position);
                dataBeanlasted.setSelect(false);
                dataBean.setSelect(true);
                adapter.notifyItemChanged(lastclick);
                adapter.notifyItemChanged(position);
                ((DmcgjcMenuAdapterForSwzd) adapter).setLastposition(position);
                if (twoMenuList.get(0).getZnName().equals("气象水文")) {
                    if (lastclick == 0) ;
                    else {
                        DMCGJCFragment dmcgjcFragment = ((DMCGJCFragment) fragmentlist.get(lastclick));
                        dmcgjcFragment.setStart(false);
                        dmcgjcFragment.setImage();
                    }

                } else {
                    DMCGJCFragment dmcgjcFragment = ((DMCGJCFragment) fragmentlist.get(lastclick));
                    dmcgjcFragment.setStart(false);
                    dmcgjcFragment.setImage();
                }
                mViewPager.setCurrentItem(position);
            });
        }
        return mAdapter1;
    }


    private void initView() {
        mViewPager.setScanScroll(false);
        getCustomMenuData();
        leave.setOnClickListener(this);
        reload.setOnClickListener(this);
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

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
            case R.id.reload:
                int currentItem = mViewPager.getCurrentItem();
                if (null == twoMenuList || twoMenuList.size() == 0) {
                    getCustomMenuData();
                } else {
                    if (twoMenuList.get(0).getZnName().equals("气象水文")) {
                        if (currentItem == 0) ((SWZDYLFragment) fragmentlist.get(0)).getTestdata();
                        else {
                            DMCGJCFragment fragment = (DMCGJCFragment) fragmentlist.get(currentItem);
                            PicUtils.deleteDir("dmcgjc/" + fragment.type + "/" + fragment.ctype);
                            fragment.getTestdata();
                        }

                    } else {
                        DMCGJCFragment fragment = (DMCGJCFragment) fragmentlist.get(currentItem);
                        PicUtils.deleteDir("dmcgjc/" + fragment.type + "/" + fragment.ctype);
                        fragment.getTestdata();
                    }
                }
                break;
        }

    }

}




