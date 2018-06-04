package net.univr.pushi.jxatmosphere.feature;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ImageView;

import com.blankj.utilcode.util.ToastUtils;

import net.univr.pushi.jxatmosphere.R;
import net.univr.pushi.jxatmosphere.adapter.ComPagerAdapter;
import net.univr.pushi.jxatmosphere.base.BaseActivity;
import net.univr.pushi.jxatmosphere.beens.GdybtxMenuBeen;
import net.univr.pushi.jxatmosphere.fragments.GdybtxFragment;
import net.univr.pushi.jxatmosphere.remote.RetrofitHelper;
import net.univr.pushi.jxatmosphere.widget.CustomViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class GdybtxActivity extends BaseActivity implements View.OnClickListener {


    @BindView(R.id.viewpager)
    CustomViewPager viewPager;


//    @BindView(R.id.share_to)
//    ImageView share_to;
    @BindView(R.id.back)
    ImageView leave;

//    @BindView(R.id.recyclerView)
//    RecyclerView recycleView;

//    GdybtxMenuAdapter madapter;

    private List<Fragment> list;

    public void setFristFragment(Boolean fristFragment) {
        this.fristFragment = fristFragment;
    }

    public Boolean getFristFragment() {
        return fristFragment;
    }

    Boolean fristFragment;


    @Override
    public int getLayoutId() {
        return R.layout.activity_gdybtx;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        super.initViews(savedInstanceState);
        initView();

    }


    private void initView() {
        fristFragment=true;
        viewPager.setScanScroll(false);
//        share_to.setOnClickListener(this);
        leave.setOnClickListener(this);
        list = new ArrayList<>();
        getTestData();
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
        RetrofitHelper.getForecastWarn()
                .getGdybtOneMenu()
                .compose(bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(gdybtxOneMenu -> {
                    List<GdybtxMenuBeen.DataBean.MenuBean> menu = gdybtxOneMenu.getData().getMenu();
//                    List<GkdmmenuBeen> destMenu = new ArrayList<>();

                    for (int i = 0; i < menu.size(); i++) {
//                        GkdmmenuBeen menuBean = new GkdmmenuBeen();
//                        menuBean.setText(menu.get(i).getName());
//                        if (i == 0)
//                            menuBean.setSelect(true);
//                        else
//                            menuBean.setSelect(false);
//                        destMenu.add(menuBean);
                        GdybtxFragment gdybtxFragment = new GdybtxFragment().newInstance(menu.get(i).getType(), menu, viewPager);
                        list.add(gdybtxFragment);
                    }
                    ComPagerAdapter comPagerAdapter=new ComPagerAdapter(getSupportFragmentManager(),list);
                    viewPager.setAdapter(comPagerAdapter);
                    viewPager.setOffscreenPageLimit(2);



//                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
//                    madapter = new GdybtxMenuAdapter(destMenu);
//                    recycleView.setLayoutManager(linearLayoutManager);
//                    recycleView.setAdapter(madapter);
//                    madapter.setOnItemChildClickListener((adapter, view, position) -> {
//                        List<GkdmmenuBeen> data = adapter.getData();
//                        int lastclick = ((GdybtxMenuAdapter) adapter).getLastposition();
//                        GkdmmenuBeen dataBeanlasted = data.get(lastclick);
//                        dataBeanlasted.setSelect(false);
//                        GkdmmenuBeen dataBean = data.get(position);
//                        dataBean.setSelect(true);
//                        adapter.notifyItemChanged(lastclick);
//                        adapter.notifyItemChanged(position);
//                        viewPager.setCurrentItem(position);
//                        ((GdybtxMenuAdapter) adapter).setLastposition(position);
//                    });

                }, throwable -> {
                   throwable.printStackTrace();
                    ToastUtils.showShort(getString(R.string.getInfo_error_toast));
                });
    }


}



