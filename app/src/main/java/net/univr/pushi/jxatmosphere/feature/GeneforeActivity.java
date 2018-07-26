package net.univr.pushi.jxatmosphere.feature;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;

import net.univr.pushi.jxatmosphere.R;
import net.univr.pushi.jxatmosphere.adapter.MyPagerAdapter;
import net.univr.pushi.jxatmosphere.base.BaseActivity;
import net.univr.pushi.jxatmosphere.beens.GeneforeMenuBeen;
import net.univr.pushi.jxatmosphere.fragments.GeneforeFragment;
import net.univr.pushi.jxatmosphere.remote.RetrofitHelper;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import cn.qqtheme.framework.picker.DatePicker;
import cn.qqtheme.framework.util.ConvertUtils;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class GeneforeActivity extends BaseActivity implements View.OnClickListener {


    @BindView(R.id.time)
    TextView time;
    String timeStr;
    DatePicker picker;
    int mYear;
    int mMonth;
    int mDay;
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.reload)
    ImageView reload;

    @BindView(R.id.relative2)
    RelativeLayout relative2;
    @BindView(R.id.relative3)
    RelativeLayout relative3;
    @BindView(R.id.relative4)
    RelativeLayout relative4;
    @BindView(R.id.relative5)
    RelativeLayout relative5;

    @BindView(R.id.viewpager)
    ViewPager viewPager;

    @BindView(R.id.title2)
    TextView title2;
    @BindView(R.id.tabline2)
    View tabline2;
    @BindView(R.id.title3)
    TextView title3;
    @BindView(R.id.tabline3)
    View tabline3;
    @BindView(R.id.title4)
    TextView title4;
    @BindView(R.id.tabline4)
    View tabline4;
    @BindView(R.id.title5)
    TextView title5;
    @BindView(R.id.tabline5)
    View tabline5;
    List<Fragment> list;
    List<GeneforeMenuBeen> menuList;
    private String tag;

    @Override
    public int getLayoutId() {
        return R.layout.activity_genefore;
    }


    @Override
    public void initViews(Bundle savedInstanceState) {
        getNowTime();
        time.setText(timeStr);
        time.setOnClickListener(this);
        reload.setOnClickListener(this);
        back.setOnClickListener(this);
        relative2.setOnClickListener(this);
        relative3.setOnClickListener(this);
        relative4.setOnClickListener(this);
        relative5.setOnClickListener(this);
        initMenu();
        getTestdata();

    }

    private void getNowTime() {
        Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH) + 1;
        mDay = c.get(Calendar.DAY_OF_MONTH);
        if (mMonth < 10)
            timeStr = mYear + "-0" + mMonth + "-";
        else
            timeStr = mYear + "-" + mMonth + "-";
        if (mDay < 10)
            timeStr = timeStr + "0" + mDay;
        else
            timeStr = timeStr + mDay;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.confirm:
                timeStr = time.getText().toString();
                initFragmentByParam(timeStr);
                picker.dismiss();
                break;
            case R.id.cancel:
                time.setText(timeStr);
                picker.dismiss();
                break;
            case R.id.reload:
                if (list != null && list.size() > 0) {
                    int currentItem = viewPager.getCurrentItem();
                    GeneforeFragment fragment = (GeneforeFragment) list.get(currentItem);
                    ProgressDialog progressDialog = ProgressDialog.show(context, "请稍等...", "获取数据中...", true);
                    progressDialog.setCancelable(true);
                    Handler handler=new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            progressDialog.dismiss();
                        }
                    },1000);
                    fragment.time=time.getText().toString().substring(2, time.getText().toString().length());
                    fragment.getTestdata();
                }

                break;
            case R.id.back:
                finish();
                break;
            case R.id.relative2:
                for (int i = 0; i < menuList.size(); i++) {
                    if (i == 1) menuList.get(0).setSelect(true);
                    else menuList.get(i).setSelect(false);
                }
                changeMenuStyle();
                viewPager.setCurrentItem(0);
                break;
            case R.id.relative3:
                for (int i = 0; i < menuList.size(); i++) {
                    if (i == 1) menuList.get(1).setSelect(true);
                    else menuList.get(i).setSelect(false);
                }
                changeMenuStyle();
                viewPager.setCurrentItem(1);
                break;
            case R.id.relative4:
                for (int i = 0; i < menuList.size(); i++) {
                    if (i == 1) menuList.get(2).setSelect(true);
                    else menuList.get(i).setSelect(false);
                }
                changeMenuStyle();
                viewPager.setCurrentItem(2);
                break;
            case R.id.relative5:
                for (int i = 0; i < menuList.size(); i++) {
                    if (i == 1) menuList.get(3).setSelect(true);
                    else menuList.get(i).setSelect(false);
                }
                changeMenuStyle();
                viewPager.setCurrentItem(3);
                break;
            case R.id.time:
                picker = new DatePicker(this);
                picker.setOffset(2);
                LayoutInflater inflater = LayoutInflater.from(context);
                View layout = inflater.inflate(R.layout.top_time_select_layout, null);
                Button confirm = layout.findViewById(R.id.confirm);
                Button cancel = layout.findViewById(R.id.cancel);
                confirm.setOnClickListener(this);
                cancel.setOnClickListener(this);
                picker.setHeaderView(layout);
                picker.setUseWeight(true);
                picker.setTopPadding(ConvertUtils.toPx(this, 10));
                picker.setGravity(Gravity.BOTTOM);
                picker.setRangeStart(2016, 1, 1);
                picker.setRangeEnd(mYear, mMonth, mDay);
                picker.setSelectedItem(mYear, mMonth, mDay);
                picker.setCanceledOnTouchOutside(false);
                picker.setResetWhileWheel(false);
                picker.setOnWheelListener(new DatePicker.OnWheelListener() {
                    @Override
                    public void onYearWheeled(int index, String year) {
                        time.setText(year + "-" + picker.getSelectedMonth() + "-" + picker.getSelectedDay());
                    }

                    @Override
                    public void onMonthWheeled(int index, String month) {
                        time.setText(picker.getSelectedYear() + "-" + month + "-" + picker.getSelectedDay());
                    }

                    @Override
                    public void onDayWheeled(int index, String day) {
                        time.setText(picker.getSelectedYear() + "-" + picker.getSelectedMonth() + "-" + day);
                    }
                });
                picker.show();
                break;
        }
    }


    void getTestdata() {
        RetrofitHelper.getForecastWarn()
                .getGeneforedefault()
                .compose(bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(geneforeBeen -> {
                    list = new ArrayList<>();
                    tag = geneforeBeen.getData().getClassX();
                    String timeParams = timeStr.substring(2, timeStr.length());
                    GeneforeFragment geneforeFragment1 = GeneforeFragment.newInstance(timeParams, "早晨");
                    GeneforeFragment geneforeFragment2 = GeneforeFragment.newInstance(timeParams, "中午");
                    GeneforeFragment geneforeFragment3 = GeneforeFragment.newInstance(timeParams, "下午");
                    GeneforeFragment geneforeFragment4 = GeneforeFragment.newInstance(timeParams, "三天");
                    list.add(geneforeFragment1);
                    list.add(geneforeFragment2);
                    list.add(geneforeFragment3);
                    list.add(geneforeFragment4);
                    MyPagerAdapter adapter = new MyPagerAdapter(
                            getSupportFragmentManager(), list, null);
                    // 绑定适配器
                    viewPager.setOffscreenPageLimit(4);
                    viewPager.setAdapter(adapter);

                    if (tag.equals("早晨")) {
                        viewPager.setCurrentItem(0);
                        menuList.get(0).setSelect(true);
                        changeMenuStyle();
                    }

                    if (tag.equals("中午")) {
                        viewPager.setCurrentItem(1);
                        menuList.get(1).setSelect(true);
                        changeMenuStyle();
                    }
                    if (tag.equals("下午")) {
                        viewPager.setCurrentItem(2);
                        menuList.get(2).setSelect(true);
                        changeMenuStyle();
                    }

                    if (tag.equals("三天")) {
                        viewPager.setCurrentItem(3);
                        menuList.get(3).setSelect(true);
                        changeMenuStyle();
                    }
                    viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                        @Override
                        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                        }

                        @Override
                        public void onPageSelected(int position) {
                            for (int i = 0; i < menuList.size(); i++) {
                                if (i == position) {
                                    menuList.get(i).setSelect(true);
                                } else {
                                    menuList.get(i).setSelect(false);
                                }
                            }
                            changeMenuStyle();
                        }

                        @Override
                        public void onPageScrollStateChanged(int state) {

                        }
                    });
                }, throwable ->
                {
                    LogUtils.e(throwable);
                    ToastUtils.showShort(getString(R.string.getInfo_error_toast));
                });

    }

    void initFragmentByParam(String time) {
        initMenu();
        String time1 = timeStr.substring(2, time.length());
        List<Fragment> huancunFragment = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            Fragment fragment = list.get(i);
            huancunFragment.add(fragment);
        }
        list.clear();
        Fragment geneforeFragment1 = GeneforeFragment.newInstance(time1, "早晨");
        Fragment geneforeFragment2 = GeneforeFragment.newInstance(time1, "中午");
        Fragment geneforeFragment3 = GeneforeFragment.newInstance(time1, "下午");
        Fragment geneforeFragment4 = GeneforeFragment.newInstance(time1, "三天");
        list.add(geneforeFragment1);
        list.add(geneforeFragment2);
        list.add(geneforeFragment3);
        list.add(geneforeFragment4);
        MyPagerAdapter adapter = new MyPagerAdapter(
                getSupportFragmentManager(), list, huancunFragment);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(0);
        menuList.get(0).setSelect(true);
        changeMenuStyle();
    }


    void initMenu() {
        menuList = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            GeneforeMenuBeen menu = new GeneforeMenuBeen();
            if (i == 0) {
                menu.setTag("早晨");
                menu.setSelect(false);
            }
            if (i == 1) {
                menu.setTag("中午");
                menu.setSelect(false);
            }
            if (i == 2) {
                menu.setTag("下午");
                menu.setSelect(false);
            }
            if (i == 3) {
                menu.setTag("三天");
                menu.setSelect(false);
            }
            menuList.add(menu);
        }
    }

    void changeMenuStyle() {
        for (int i = 0; i < menuList.size(); i++) {
            GeneforeMenuBeen been = menuList.get(i);
            if (been.getSelect()) {
                if (i == 0) {
                    title2.setTextSize(17);
                    title3.setTextSize(15);
                    title4.setTextSize(15);
                    title5.setTextSize(15);
                    tabline2.setVisibility(View.VISIBLE);
                    tabline3.setVisibility(View.INVISIBLE);
                    tabline4.setVisibility(View.INVISIBLE);
                    tabline5.setVisibility(View.INVISIBLE);
                }
                if (i == 1) {
                    title2.setTextSize(15);
                    title3.setTextSize(17);
                    title4.setTextSize(15);
                    title5.setTextSize(15);
                    tabline2.setVisibility(View.INVISIBLE);
                    tabline3.setVisibility(View.VISIBLE);
                    tabline4.setVisibility(View.INVISIBLE);
                    tabline5.setVisibility(View.INVISIBLE);
                }
                if (i == 2) {
                    title2.setTextSize(15);
                    title3.setTextSize(15);
                    title4.setTextSize(17);
                    title5.setTextSize(15);
                    tabline2.setVisibility(View.INVISIBLE);
                    tabline3.setVisibility(View.INVISIBLE);
                    tabline4.setVisibility(View.VISIBLE);
                    tabline5.setVisibility(View.INVISIBLE);
                }
                if (i == 3) {
                    title2.setTextSize(15);
                    title3.setTextSize(15);
                    title4.setTextSize(15);
                    title5.setTextSize(17);
                    tabline2.setVisibility(View.INVISIBLE);
                    tabline3.setVisibility(View.INVISIBLE);
                    tabline4.setVisibility(View.INVISIBLE);
                    tabline5.setVisibility(View.VISIBLE);
                }
            }
        }
    }


    public void setTime(String shijian) {
        time.setText(shijian);
    }
}