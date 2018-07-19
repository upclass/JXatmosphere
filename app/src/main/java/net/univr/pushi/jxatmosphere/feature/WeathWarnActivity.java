package net.univr.pushi.jxatmosphere.feature;


import android.animation.ValueAnimator;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import net.univr.pushi.jxatmosphere.R;
import net.univr.pushi.jxatmosphere.adapter.ComPagerAdapter;
import net.univr.pushi.jxatmosphere.base.BaseActivity;
import net.univr.pushi.jxatmosphere.fragments.WeatherWarnFragment;
import net.univr.pushi.jxatmosphere.interfaces.BrightnessActivity;
import net.univr.pushi.jxatmosphere.interfaces.CallBackUtil;
import net.univr.pushi.jxatmosphere.utils.PicUtils;
import net.univr.pushi.jxatmosphere.widget.CustomViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class WeathWarnActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.relative1)
    RelativeLayout relative1;
    @BindView(R.id.relative2)
    RelativeLayout relative2;
    @BindView(R.id.relative3)
    RelativeLayout relative3;
    @BindView(R.id.tabline1)
    View tabline1;
    @BindView(R.id.tabline2)
    View tabline2;
    @BindView(R.id.tabline3)
    View tabline3;
    @BindView(R.id.title1)
    TextView title1;
    @BindView(R.id.title2)
    TextView title2;
    @BindView(R.id.title3)
    TextView title3;
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.reload)
    ImageView reload;

    //    LayoutInflater inflater;
    ComPagerAdapter adapter;
    @BindView(R.id.viewpager)
    CustomViewPager viewpager;
    List<Fragment> list;
    ImageView image;


    @Override
    public int getLayoutId() {
        return R.layout.activity_weath_warn;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        super.initViews(savedInstanceState);
        relative1.setOnClickListener(this);
        relative2.setOnClickListener(this);
        relative3.setOnClickListener(this);
        reload.setOnClickListener(this);
        back.setOnClickListener(this);
        list = new ArrayList<>();
        adapter = new ComPagerAdapter(getSupportFragmentManager(), list);
        viewpager.setAdapter(adapter);
        viewpager.setScanScroll(false);
        viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    ((WeatherWarnFragment) list.get(1)).setImage();
                    ((WeatherWarnFragment) list.get(1)).setStart(false);
                    ((WeatherWarnFragment) list.get(2)).setImage();
                    ((WeatherWarnFragment) list.get(2)).setStart(false);
                    title1.setTextSize(17);
                    tabline1.setVisibility(View.VISIBLE);
                    title2.setTextSize(15);
                    tabline2.setVisibility(View.INVISIBLE);
                    title3.setTextSize(15);
                    tabline3.setVisibility(View.INVISIBLE);
                }
                if (position == 1) {
                    ((WeatherWarnFragment) list.get(0)).setImage();
                    ((WeatherWarnFragment) list.get(0)).setStart(false);
                    ((WeatherWarnFragment) list.get(2)).setImage();
                    ((WeatherWarnFragment) list.get(2)).setStart(false);
                    title2.setTextSize(17);
                    tabline2.setVisibility(View.VISIBLE);
                    title1.setTextSize(15);
                    tabline1.setVisibility(View.INVISIBLE);
                    title3.setTextSize(15);
                    tabline3.setVisibility(View.INVISIBLE);
                }
                if (position == 2) {
                    ((WeatherWarnFragment) list.get(0)).setImage();
                    ((WeatherWarnFragment) list.get(0)).setStart(false);
                    ((WeatherWarnFragment) list.get(1)).setImage();
                    ((WeatherWarnFragment) list.get(1)).setStart(false);
                    title3.setTextSize(17);
                    tabline3.setVisibility(View.VISIBLE);
                    title2.setTextSize(15);
                    tabline2.setVisibility(View.INVISIBLE);
                    title1.setTextSize(15);
                    tabline1.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
//        inflater = LayoutInflater.from(this);
//        getTestData("dz");
//        getTestData("sh");
//        getTestData("hl");
        list.add(WeatherWarnFragment.newInstance("dz"));
        list.add(WeatherWarnFragment.newInstance("sh"));
        list.add(WeatherWarnFragment.newInstance("hl"));
        adapter.notifyDataSetChanged();

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
            case R.id.relative1:
                viewpager.setCurrentItem(0);
                title1.setTextSize(17);
                tabline1.setVisibility(View.VISIBLE);
                title2.setTextSize(15);
                tabline2.setVisibility(View.INVISIBLE);
                title3.setTextSize(15);
                tabline3.setVisibility(View.INVISIBLE);
                break;
            case R.id.relative2:
                viewpager.setCurrentItem(1);
                title2.setTextSize(17);
                tabline2.setVisibility(View.VISIBLE);
                title1.setTextSize(15);
                tabline1.setVisibility(View.INVISIBLE);
                title3.setTextSize(15);
                tabline3.setVisibility(View.INVISIBLE);
                break;
            case R.id.relative3:
                viewpager.setCurrentItem(2);
                title3.setTextSize(17);
                tabline3.setVisibility(View.VISIBLE);
                title2.setTextSize(15);
                tabline2.setVisibility(View.INVISIBLE);
                title1.setTextSize(15);
                tabline1.setVisibility(View.INVISIBLE);
                break;
            case R.id.reload:
                ProgressDialog progressDialog = ProgressDialog.show(context, "请稍等...", "获取数据中...", true);
                progressDialog.setCancelable(true);
                Runnable runnable = new Runnable() {
                    @Override
                    public void run() {
                        progressDialog.dismiss();
                    }
                };
                handler.postDelayed(runnable, 1000);
                int currentItem = viewpager.getCurrentItem();
                WeatherWarnFragment fragment = (WeatherWarnFragment) list.get(currentItem);
                PicUtils.deleteDir("weathWarn/" + fragment.type);
                fragment.getPicList();
                break;
            case R.id.back:
                finish();
                break;
        }
    }

    Handler handler = new Handler();

}
