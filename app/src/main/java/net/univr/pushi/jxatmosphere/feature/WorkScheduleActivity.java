package net.univr.pushi.jxatmosphere.feature;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import net.univr.pushi.jxatmosphere.R;
import net.univr.pushi.jxatmosphere.base.BaseActivity;
import net.univr.pushi.jxatmosphere.fragments.WorkScheduleFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class WorkScheduleActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.viewpager)
    ViewPager viewPager;
    @BindView(R.id.tv1)
    TextView tv1;
    @BindView(R.id.tv2)
    TextView tv2;
    @BindView(R.id.tv3)
    TextView tv3;
    @BindView(R.id.tabline)
    ImageView tabline;
//    @BindView(R.id.share_to)
//    ImageView share_to;
    @BindView(R.id.work_schedule_leave)
    ImageView leave;

    private List<Fragment> list;

    private int tabLineLength;// 1/3屏幕宽
    private int currentPage = 0;// 初始化当前页为0（第一页）

    int marginleft;


    @Override
    public int getLayoutId() {
        return R.layout.activity_work_schedule;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        super.initViews(savedInstanceState);
        // 初始化滑动条1/3
        initTabLine();
        // 初始化界面
        initView();
    }

    private void initTabLine() {
        // 获取显示屏信息
        Display display = getWindow().getWindowManager().getDefaultDisplay();
        // 得到显示屏宽度
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);
        // 1/3屏幕宽度
        tabLineLength = metrics.widthPixels / 3;
        // 1/24屏幕宽度
        marginleft = tabLineLength / 8;
        // 控件参数
        LinearLayout.LayoutParams ps= (LinearLayout.LayoutParams) tabline.getLayoutParams();
        ps.width=tabLineLength*3/4;
        tabline.setLayoutParams(ps);
    }

    private void initView() {


        list = new ArrayList<Fragment>();
        tv1.setOnClickListener(this);
        tv2.setOnClickListener(this);
        tv3.setOnClickListener(this);
//        share_to.setOnClickListener(this);
        leave.setOnClickListener(this);

        // 设置参数
        String a = "before";
        WorkScheduleFragment fragment = WorkScheduleFragment.newInstance(a);

        String b = "week";
        WorkScheduleFragment fragment1 = WorkScheduleFragment.newInstance(b);

        String c = "next";
        WorkScheduleFragment fragment2 = WorkScheduleFragment.newInstance(c);


        list.add(fragment);
        list.add(fragment1);
        list.add(fragment2);

        // 设置适配器
        FragmentPagerAdapter adapter = new FragmentPagerAdapter(
                getSupportFragmentManager()) {

            @Override
            public int getCount() {
                return list.size();
            }

            @Override
            public Fragment getItem(int arg0) {
                return list.get(arg0);
            }
        };

        // 绑定适配器
        viewPager.setAdapter(adapter);

        // 设置滑动监听
        viewPager.setOnPageChangeListener(new OnPageChangeListener() {

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
                Log.i("tuzi", arg0 + "," + arg1 + "," + arg2);
             /*   arg0 :当前页面，及你点击滑动的页面
                arg1:当前页面偏移的百分比
                arg2:当前页面偏移的像素位置*/
                // 取得该控件的实例
                LinearLayout.LayoutParams ll = (android.widget.LinearLayout.LayoutParams) tabline
                        .getLayoutParams();

                if (currentPage == 0 && arg0 == 0) { // 0->1移动(第一页到第二页)
                    ll.leftMargin = (int) (currentPage * tabLineLength + arg1
                            * tabLineLength+marginleft);
                } else if (currentPage == 1 && arg0 == 1) { // 1->2移动（第二页到第三页）
                    ll.leftMargin = (int) (currentPage * tabLineLength + arg1
                            * tabLineLength+marginleft);
                } else if (currentPage == 1 && arg0 == 0) { // 1->0移动（第二页到第一页）
                    ll.leftMargin = (int) (currentPage * tabLineLength - ((1 - arg1) * tabLineLength)+marginleft);
                } else if (currentPage == 2 && arg0 == 1) { // 2->1移动（第三页到第二页）
                    ll.leftMargin = (int) (currentPage * tabLineLength - (1 - arg1)
                            * tabLineLength+marginleft);
                }
                tabline.setLayoutParams(ll);
            }


        });

    }

    @Override
    public void onClick(View v) {
        LinearLayout.LayoutParams ll = (android.widget.LinearLayout.LayoutParams) tabline
                .getLayoutParams();
        switch (v.getId()) {
            case R.id.tv1:
                currentPage = 0;
                changeSize(0);
                viewPager.setCurrentItem(0);
                ll.leftMargin = marginleft;
                tabline.setLayoutParams(ll);
                break;
            case R.id.tv2:
                currentPage = 1;
                changeSize(1);
                viewPager.setCurrentItem(1);
                ll.leftMargin =  tabLineLength + marginleft;
                tabline.setLayoutParams(ll);
                break;
            case R.id.tv3:
                currentPage = 2;
                changeSize(2);
                viewPager.setCurrentItem(2);
                ll.leftMargin =  tabLineLength*2 + marginleft;
                tabline.setLayoutParams(ll);
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
            case R.id.work_schedule_leave:
                finish();
                break;
        }

    }

    public void changeSize(int flag) {
        if (flag == 0) {
            tv1.setTextSize(17);
            tv2.setTextSize(15);
            tv3.setTextSize(15);
        }
        if (flag == 1) {
            tv1.setTextSize(15);
            tv2.setTextSize(17);
            tv3.setTextSize(15);
        }
        if (flag == 2) {
            tv1.setTextSize(15);
            tv2.setTextSize(15);
            tv3.setTextSize(17);
        }
    }
}