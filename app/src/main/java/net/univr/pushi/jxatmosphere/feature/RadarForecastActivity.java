package net.univr.pushi.jxatmosphere.feature;

import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import net.univr.pushi.jxatmosphere.R;
import net.univr.pushi.jxatmosphere.adapter.ComPagerAdapter;
import net.univr.pushi.jxatmosphere.base.BaseActivity;
import net.univr.pushi.jxatmosphere.fragments.RadarForecastFragment;
import net.univr.pushi.jxatmosphere.interfaces.BrightnessActivity;
import net.univr.pushi.jxatmosphere.interfaces.CallBackUtil;
import net.univr.pushi.jxatmosphere.widget.CustomViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class RadarForecastActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.viewpager)
    CustomViewPager viewPager;
    @BindView(R.id.main_tv)
    TextView main_tv;
    @BindView(R.id.vice_tv)
    TextView vice_tv;

    @BindView(R.id.tabline)
    ImageView tabline;
    //    @BindView(R.id.share_to)
//    ImageView share_to;
    @BindView(R.id.back)
    ImageView leave;

    private List<Fragment> list;

    private int tabLineLength;// 1/2屏幕宽
    private int currentPage = 0;// 初始化当前页为0（第一页）
    int marginleft;
    RadarForecastFragment fragment;
    RadarForecastFragment fragment1;


    @Override
    public int getLayoutId() {
        return R.layout.activity_radar_forecast;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        super.initViews(savedInstanceState);
        // 初始化滑动条1/2

        initTabLine();
        // 初始化界面
        initView();

    }

    private void initTabLine() {
        Display display = getWindow().getWindowManager().getDefaultDisplay(); // 获取显示屏信息
        DisplayMetrics metrics = new DisplayMetrics(); // 得到显示屏宽度
        display.getMetrics(metrics);
        tabLineLength = metrics.widthPixels / 2; // 1/2屏幕宽度
        marginleft = tabLineLength / 8; // 1/16屏幕宽度
        LinearLayout.LayoutParams ps = (LinearLayout.LayoutParams) tabline.getLayoutParams();
        ps.width = tabLineLength * 3 / 4;
        tabline.setLayoutParams(ps);
    }

    private void initView() {
        list = new ArrayList<>();
        main_tv.setOnClickListener(this);
        vice_tv.setOnClickListener(this);
//        share_to.setOnClickListener(this);
        leave.setOnClickListener(this);

        String flag = "rain";
        fragment = RadarForecastFragment.newInstance(flag);
        String flag1 = "ref";
        fragment1 =  RadarForecastFragment.newInstance(flag1);



        list.add(fragment);
        list.add(fragment1);

        // 设置适配器
        ComPagerAdapter adapter = new ComPagerAdapter(
                getSupportFragmentManager(), list);
        // 绑定适配器
        viewPager.setScanScroll(false);
        viewPager.setAdapter(adapter);


        // 设置滑动监听
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                currentPage = position;
                changeSize(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
             /*   arg0 :当前页面，及你点击滑动的页面
                arg1:当前页面偏移的百分比
                arg2:当前页面偏移的像素位置*/
                // 取得该控件的实例
                LinearLayout.LayoutParams ll = (android.widget.LinearLayout.LayoutParams) tabline
                        .getLayoutParams();

                if (currentPage == 0 && arg0 == 0) { // 0->1移动(第一页到第二页)
                    ll.leftMargin = (int) (currentPage * tabLineLength + arg1
                            * tabLineLength + marginleft);


                } else if (currentPage == 1 && arg0 == 0) { // 1->0移动（第二页到第一页）
                    ll.leftMargin = (int) (currentPage * tabLineLength + marginleft - ((1 - arg1) * tabLineLength));

                }
                tabline.setLayoutParams(ll);
            }


        });

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
        LinearLayout.LayoutParams ll = (android.widget.LinearLayout.LayoutParams) tabline
                .getLayoutParams();
        switch (v.getId()) {
            case R.id.main_tv:
                changeSize(0);
                currentPage = 0;
                viewPager.setCurrentItem(0);
                ll.leftMargin = marginleft;
                tabline.setLayoutParams(ll);
                fragment1.setStart(false);
                fragment1.setImage();
                break;
            case R.id.vice_tv:
                changeSize(1);
                currentPage = 1;
                viewPager.setCurrentItem(1);
                ll.leftMargin = tabLineLength + marginleft;
                tabline.setLayoutParams(ll);
                fragment.setStart(false);
                fragment.setImage();

                break;
//            case R.id.share_to:
//
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

    public void changeSize(int flag) {
        if (flag == 0) {
            vice_tv.setTextSize(15);
            main_tv.setTextSize(17);
        }
        if (flag == 1) {
            main_tv.setTextSize(15);
            vice_tv.setTextSize(17);

        }
    }


}
