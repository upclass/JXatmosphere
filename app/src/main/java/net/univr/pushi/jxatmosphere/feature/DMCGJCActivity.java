package net.univr.pushi.jxatmosphere.feature;

import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import net.univr.pushi.jxatmosphere.R;
import net.univr.pushi.jxatmosphere.adapter.ComPagerAdapter;
import net.univr.pushi.jxatmosphere.base.BaseActivity;
import net.univr.pushi.jxatmosphere.fragments.DMCGJCFragment;
import net.univr.pushi.jxatmosphere.interfaces.BrightnessActivity;
import net.univr.pushi.jxatmosphere.interfaces.CallBackUtil;
import net.univr.pushi.jxatmosphere.widget.CustomViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class DMCGJCActivity extends BaseActivity implements View.OnClickListener {


    @BindView(R.id.viewpager)
    CustomViewPager viewPager;

    @BindView(R.id.back)
    ImageView leave;


    private List<Fragment> list;
    DMCGJCFragment fragment1;
    DMCGJCFragment fragment2;
    DMCGJCFragment fragment3;
    DMCGJCFragment fragment4;
    DMCGJCFragment fragment5;
    DMCGJCFragment fragment6;


    @Override
    public int getLayoutId() {
        return R.layout.activity_dmcgjc;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        super.initViews(savedInstanceState);
        initView();

    }


    private void initView() {
        viewPager.setScanScroll(false);
        list = new ArrayList<>();
        leave.setOnClickListener(this);

        String type1 = "rain";
        String ctype1 = "swzd";
        fragment1 = DMCGJCFragment.newInstance(type1, ctype1, viewPager, list,0);
        String type2 = "temp";
        String ctype2 = "temp";
        fragment2 = DMCGJCFragment.newInstance(type2, ctype2, viewPager, list,1);
        String type3 = "wind";
        String ctype3 = "wind_2minute_avg";
        fragment3 = DMCGJCFragment.newInstance(type3, ctype3, viewPager, list,2);
        String type4 = "humidity";
        String ctype4 = "humidity";
        fragment4 = DMCGJCFragment.newInstance(type4, ctype4, viewPager, list,3);
        String type5 = "pressure";
        String ctype5 = "pressure";
        fragment5 = DMCGJCFragment.newInstance(type5, ctype5, viewPager, list,4);
        String type6 = "vis";
        String ctype6 = "vis_min";
        fragment6 = DMCGJCFragment.newInstance(type6, ctype6, viewPager, list,5);


        list.add(fragment1);
        list.add(fragment2);
        list.add(fragment3);
        list.add(fragment4);
        list.add(fragment5);
        list.add(fragment6);


        ComPagerAdapter adapter = new ComPagerAdapter(
                getSupportFragmentManager(), list);
        viewPager.setAdapter(adapter);

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
                break;
        }

    }


}




