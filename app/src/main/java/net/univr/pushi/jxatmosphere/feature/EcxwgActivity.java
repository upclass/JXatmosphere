package net.univr.pushi.jxatmosphere.feature;

import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;

import net.univr.pushi.jxatmosphere.R;
import net.univr.pushi.jxatmosphere.adapter.ComPagerAdapter;
import net.univr.pushi.jxatmosphere.adapter.EcxwgOneMenuAdapter;
import net.univr.pushi.jxatmosphere.base.BaseActivity;
import net.univr.pushi.jxatmosphere.beens.EcOneMenu;
import net.univr.pushi.jxatmosphere.fragments.EcxwgFragment;
import net.univr.pushi.jxatmosphere.interfaces.BrightnessActivity;
import net.univr.pushi.jxatmosphere.interfaces.CallBackUtil;
import net.univr.pushi.jxatmosphere.remote.RetrofitHelper;
import net.univr.pushi.jxatmosphere.widget.CustomViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class EcxwgActivity extends BaseActivity implements View.OnClickListener {


    @BindView(R.id.viewpager)
    CustomViewPager viewPager;


//    @BindView(R.id.share_to)
//    ImageView share_to;
    @BindView(R.id.back)
    ImageView leave;
//    @BindView(R.id.recyclerView)
//    RecyclerView recycleView;


    EcxwgOneMenuAdapter madapter;

    private List<Fragment> list;


    @Override
    public int getLayoutId() {
        return R.layout.activity_ecxwg;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        super.initViews(savedInstanceState);
        initView();

    }


    private void initView() {
        viewPager.setScanScroll(false);
//        share_to.setOnClickListener(this);
        leave.setOnClickListener(this);
        list = new ArrayList<>();
        getTestData();
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
//            case R.id.share_to:
//                OnekeyShare oks = new OnekeyShare();
//                //关闭sso授权
//                oks.disableSSOWhenAuthorize();
//
//                // title标题，微信、QQ和QQ空间等平台使用
//                oks.setTitle(getString(R.string.sharetest));
//                // titleUrl QQ和QQ空间跳转链接
//                oks.setTitleUrl("http://sharesdk.cn");
//                // text是分享文本，所有平台都需要这个字段
//                oks.setText("我是分享文本");
//                // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
//                oks.setImagePath("/sdcard/popup_feedback_layout.jpg");//确保SDcard下面存在此张图片
//                // url在微信、微博，Facebook等平台中使用
//                oks.setUrl("http://sharesdk.cn");
//                // comment是我对这条分享的评论，仅在人人网使用
//                oks.setComment("我是测试评论文本");
//                // 启动分享GUI
//                oks.show(this);
//                break;
            case R.id.back:
                finish();
                break;
        }

    }


    public void getTestData() {
        RetrofitHelper.getDataForecastAPI()
                .getOneMenu("ecmwf_thin")
                .compose(bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(ecOneMenu -> {

                    String ctype2="";
                    List<EcOneMenu.DataBean.MenuBean> menu = ecOneMenu.getData().getMenu();
                    for (int i = 0; i < menu.size(); i++) {
                        EcOneMenu.DataBean.MenuBean menuBean = menu.get(i);
//                        if (i == 0) {
//                            menuBean.setSelect(true);
//                            menu.set(i, menuBean);
//                        } else {
//                            menuBean.setSelect(false);
//                            menu.set(i, menuBean);
//                        }
                        if (menuBean.getName().equals("rain")) {
                            ctype2="rain03";
                        }
                        if (menuBean.getName().equals("10mwind")) {
                            ctype2="";
                        }
                        if (menuBean.getName().equals("cape")) {
                            ctype2="";
                        }
                        if (menuBean.getName().equals("cloud")) {
                            ctype2="lcc";
                        }
                        if (menuBean.getName().equals("dslp")) {
                            ctype2="";
                        }
                        if (menuBean.getName().equals("dt")) {
                            ctype2="2m";
                        }
                        if (menuBean.getName().equals("dt48")) {
                            ctype2="2m";
                        }
                        if (menuBean.getName().equals("frost")) {
                            ctype2="";
                        }
                        if (menuBean.getName().equals("h500w")) {
                            ctype2="h200w200";
                        }
                        if (menuBean.getName().equals("hl")) {
                            ctype2="";
                        }
                        if (menuBean.getName().equals("kindex")) {
                            ctype2="";
                        }
                        if (menuBean.getName().equals("pwat")) {
                            ctype2="";
                        }
                        if (menuBean.getName().equals("q")) {
                            ctype2="h700";
                        }
                        if (menuBean.getName().equals("qflux")) {
                            ctype2="h700";
                        }
                        if (menuBean.getName().equals("rh")) {
                            ctype2="2m";
                        }
                        if (menuBean.getName().equals("slp")) {
                            ctype2="";
                        }
                        if (menuBean.getName().equals("swind")) {
                            ctype2="h500";
                        }
                        if (menuBean.getName().equals("thse")) {
                            ctype2="h500";
                        }
                        if (menuBean.getName().equals("tmp")) {
                            ctype2="2m";
                        }
                        if (menuBean.getName().equals("windchill")) {
                            ctype2="";
                        }
                        EcxwgFragment fragment = EcxwgFragment.newInstance(menuBean.getName(),ctype2,viewPager,list,i);
                        list.add(fragment);
                    }

                    ComPagerAdapter viewpagerAdapter = new ComPagerAdapter(
                            getSupportFragmentManager(), list);
                    // 绑定适配器
                    viewPager.setAdapter(viewpagerAdapter);


//                    if (madapter == null) {
//                        LinearLayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
//                        madapter = new EcxwgOneMenuAdapter(menu);
//                        recycleView.setLayoutManager(layoutManager);
//                        recycleView.setAdapter(madapter);
//                        madapter.setOnItemChildClickListener((adapter, view, position) -> {
//                            List<EcOneMenu.DataBean.MenuBean> data = adapter.getData();
//                            int lastclick = ((EcxwgOneMenuAdapter) adapter).getLastposition();
//                            EcOneMenu.DataBean.MenuBean dataBeanlasted = data.get(lastclick);
//                            dataBeanlasted.setSelect(false);
//                            EcOneMenu.DataBean.MenuBean dataBean = data.get(position);
//                            dataBean.setSelect(true);
//                            adapter.notifyItemChanged(lastclick);
//                            adapter.notifyItemChanged(position);
//                            ((EcxwgOneMenuAdapter) adapter).setLastposition(position);
//
//                            viewPager.setCurrentItem(position);
//                            for (int i = 0; i < list.size(); i++) {
//                                if (position == i) {
//                                } else {
//                                    EcxwgFragment fragment = (EcxwgFragment) list.get(i);
//                                    fragment.setStart(false);
//                                    fragment.setImage();
//                                }
//                            }
//                        });
//                    }

                }, throwable -> {
                    LogUtils.e(throwable);
                    ToastUtils.showShort(getString(R.string.getInfo_error_toast));
                });
    }


}



