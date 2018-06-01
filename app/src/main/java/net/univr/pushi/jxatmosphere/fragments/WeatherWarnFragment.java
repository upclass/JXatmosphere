package net.univr.pushi.jxatmosphere.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;

import net.univr.pushi.jxatmosphere.R;
import net.univr.pushi.jxatmosphere.adapter.MyPagerAdapter;
import net.univr.pushi.jxatmosphere.base.RxLazyFragment;
import net.univr.pushi.jxatmosphere.remote.RetrofitHelper;

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
    ViewPager viewPager;
    MyPagerAdapter pagerAdapter;
    List<Fragment> list = new ArrayList<>();
    String type;

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

    private void getPicList() {
        RetrofitHelper.getForecastWarn()
                .getQxfx(type)
                .compose(bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(QxfxBeen -> {
                    List<String> url = QxfxBeen.getData().get(0).getUrl();
                    List<Fragment>fragmentHuancun=new ArrayList<>();
                    for (int i = 0; i < list.size(); i++) {
                        fragmentHuancun.add(list.get(i));
                    }
                    list.clear();
                    for (int i = 0; i < url.size(); i++) {
                        PicLoadFragment picLoadFragment = PicLoadFragment.newInstance(url.get(i));
                        list.add(picLoadFragment);
                    }
                    pagerAdapter = new MyPagerAdapter(getChildFragmentManager(), list, fragmentHuancun);
                    viewPager.setAdapter(pagerAdapter);
                }, throwable -> {
                    LogUtils.e(throwable);
                    ToastUtils.showShort(R.string.getInfo_error_toast);
                });

    }
}
