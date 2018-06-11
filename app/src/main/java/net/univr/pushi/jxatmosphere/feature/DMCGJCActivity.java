package net.univr.pushi.jxatmosphere.feature;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ImageView;

import net.univr.pushi.jxatmosphere.R;
import net.univr.pushi.jxatmosphere.adapter.ComPagerAdapter;
import net.univr.pushi.jxatmosphere.base.BaseActivity;
import net.univr.pushi.jxatmosphere.fragments.DMCGJCFragment;
import net.univr.pushi.jxatmosphere.fragments.SWZDYLFragment;
import net.univr.pushi.jxatmosphere.widget.CustomViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class DMCGJCActivity extends BaseActivity implements View.OnClickListener {


    @BindView(R.id.viewpager)
    CustomViewPager viewPager;

//    @BindView(R.id.share_to)
//    ImageView share_to;
    @BindView(R.id.back)
    ImageView leave;
//    @BindView(R.id.scrollview)
//    HorizontalScrollView scrollView;

    private List<Fragment> list;
    DMCGJCFragment fragment1;
    DMCGJCFragment fragment2;
    DMCGJCFragment fragment3;
    DMCGJCFragment fragment4;
    DMCGJCFragment fragment5;


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

//        share_to.setOnClickListener(this);
        leave.setOnClickListener(this);


        SWZDYLFragment fragment = SWZDYLFragment.newInstance(viewPager,list);

        String type1 = "rain";
        String ctype1="rain_sum";
        fragment1 = DMCGJCFragment.newInstance(type1,ctype1,viewPager,list);
        String type2 = "temp";
        String ctype2="temp";
        fragment2 = DMCGJCFragment.newInstance(type2,ctype2,viewPager,list);
        String type3 = "wind";
        String ctype3="wind_2minute_avg";
        fragment3 = DMCGJCFragment.newInstance(type3,ctype3,viewPager,list);
        String type4 = "humidity";
        String ctype4="humidity";
        fragment4 = DMCGJCFragment.newInstance(type4,ctype4,viewPager,list);
        String type5 = "pressure";
        String ctype5="pressure";
        fragment5 = DMCGJCFragment.newInstance(type5,ctype5,viewPager,list);


        list.add(fragment);
        list.add(fragment1);
        list.add(fragment2);
        list.add(fragment3);
        list.add(fragment4);
        list.add(fragment5);


        // 设置适配器
        ComPagerAdapter adapter = new ComPagerAdapter(
                getSupportFragmentManager(), list);
        // 绑定适配器
        viewPager.setAdapter(adapter);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
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



}




