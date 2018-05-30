package net.univr.pushi.jxatmosphere.feature;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.squareup.picasso.Picasso;

import net.univr.pushi.jxatmosphere.R;
import net.univr.pushi.jxatmosphere.adapter.ViewpageAdapter;
import net.univr.pushi.jxatmosphere.base.BaseActivity;
import net.univr.pushi.jxatmosphere.remote.RetrofitHelper;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

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
//    @BindView(R.id.share_to)
//    ImageView shareTo;

    LayoutInflater inflater;
    ViewpageAdapter adapter;
    @BindView(R.id.viewpager)
    ViewPager viewpager;
    List<ImageView> list;
    ImageView image;
    ProgressDialog progressDialog;


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
//        shareTo.setOnClickListener(this);
        back.setOnClickListener(this);
        list = new ArrayList<>();
        adapter= new ViewpageAdapter(list);
        viewpager.setAdapter(adapter);
        viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    title1.setTextSize(17);
                    tabline1.setVisibility(View.VISIBLE);
                    title2.setTextSize(15);
                    tabline2.setVisibility(View.INVISIBLE);
                    title3.setTextSize(15);
                    tabline3.setVisibility(View.INVISIBLE);
                }
                if (position == 1) {
                    title2.setTextSize(17);
                    tabline2.setVisibility(View.VISIBLE);
                    title1.setTextSize(15);
                    tabline1.setVisibility(View.INVISIBLE);
                    title3.setTextSize(15);
                    tabline3.setVisibility(View.INVISIBLE);
                }
                if (position == 2) {
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
        inflater = LayoutInflater.from(this);


        getTestData("dz");
        getTestData("sh");
        getTestData("hl");
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

    private void getTestData(String type) {
        if(type.equals("dz"))
        progressDialog = ProgressDialog.show(context, "请稍等...", "获取数据中...", true);
        progressDialog.setCancelable(true);
        RetrofitHelper.getForecastWarn()
                .getXqfx(type)
                .compose(bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(qxfxBeen -> {
                    if(progressDialog!=null)
                    progressDialog.dismiss();
                    image=new ImageView(context);
                    list.add(image);
                    adapter.notifyDataSetChanged();
                    String url = qxfxBeen.getData().get(0).getUrl();
                    Picasso.with(context).load(url).placeholder(R.drawable.app_imageplacehold).into(image);

                }, throwable -> {
                    if(progressDialog!=null)
                    progressDialog.dismiss();
                    LogUtils.e(throwable);
                    ToastUtils.showShort(getString(R.string.getInfo_error_toast));
                });

    }


}
