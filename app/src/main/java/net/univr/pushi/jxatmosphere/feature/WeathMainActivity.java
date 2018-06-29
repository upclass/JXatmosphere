package net.univr.pushi.jxatmosphere.feature;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeQuery;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;

import net.univr.pushi.jxatmosphere.R;
import net.univr.pushi.jxatmosphere.adapter.WeathMainAdapter;
import net.univr.pushi.jxatmosphere.adapter.WeathMainBdybAdapter;
import net.univr.pushi.jxatmosphere.base.BaseActivity;
import net.univr.pushi.jxatmosphere.beens.BdskBeen;
import net.univr.pushi.jxatmosphere.beens.GdybBeen;
import net.univr.pushi.jxatmosphere.beens.YuJinXinhaoBeen;
import net.univr.pushi.jxatmosphere.remote.RetrofitHelper;
import net.univr.pushi.jxatmosphere.utils.GetResourceInt;
import net.univr.pushi.jxatmosphere.utils.YujinWeiZhi;
import net.univr.pushi.jxatmosphere.widget.FullyLinearLayoutManager;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class WeathMainActivity extends BaseActivity implements View.OnClickListener, GeocodeSearch.OnGeocodeSearchListener {


    @BindView(R.id.work_schedule_leave)
    ImageView workScheduleLeave;
    //    @BindView(R.id.share_to)
//    ImageView shareTo;
    @BindView(R.id.date)
    TextView date;
    @BindView(R.id.jsl_data)
    TextView jslData;
    @BindView(R.id.xdsd_data)
    TextView xdsdData;
    @BindView(R.id.fsfx_data)
    TextView fsfxData;
    @BindView(R.id.fsfx_data1)
    TextView fsfxData1;
    @BindView(R.id.temper)
    TextView temper;
    @BindView(R.id.loc)
    TextView loc;
    @BindView(R.id.recycler)
    RecyclerView recyclerView;
    @BindView(R.id.bdybRecycle)
    RecyclerView bdybRecycle;
    WeathMainAdapter mAdapter;
    WeathMainBdybAdapter mbdybAdapter;
    @BindView(R.id.tqms)
    TextView tqms;
    @BindView(R.id.fbdw)
    TextView fbdw;
    @BindView(R.id.bd_tqtp)
    ImageView bd_tqtp;
    @BindView(R.id.yjxh)
    LinearLayout yjxh;
    @BindView(R.id.yjxh1)
    LinearLayout yjxh1;


    private int mDay;
    private String lat;
    private String lon;
    String adress;
    GeocodeSearch geocoderSearch;
    String city;

    @Override
    public int getLayoutId() {
        return R.layout.activity_weath_main;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        super.initViews(savedInstanceState);
//        shareTo.setOnClickListener(this);
        workScheduleLeave.setOnClickListener(this);
        geocoderSearch = new GeocodeSearch(this);
        geocoderSearch.setOnGeocodeSearchListener(this);
        Intent intent = getIntent();
        lat = intent.getStringExtra("lat");
        lon = intent.getStringExtra("lon");
        adress = intent.getStringExtra("address");
        initDate();
        if (lat == null)
            Toast.makeText(context, "没有定位权限", Toast.LENGTH_SHORT).show();
        else {
            getAddress(new LatLonPoint(Double.valueOf(lat), Double.valueOf(lon)));
            getTestData();
            getTestData1();
            getTestData2();
        }

    }

    //    private  WeathMainAdapter getAdapter{
//        if(mAdapter==null){
//            LinearLayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
//            List<BdybBeen.DataBean.YbListBean>  mData1 = new ArrayList<>();
//            mAdapter = new WeathMainAdapter(context,fbTime,mData1);
//            recyclerView.setLayoutManager(layoutManager);
//            recyclerView.setAdapter(mAdapter);
//        }
//      return mAdapter;
//    }
    private void initDate() {
        String weekdayStr = "";
        Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH) + 1;
        mDay = c.get(Calendar.DAY_OF_MONTH);
        int weekday = c.get(Calendar.DAY_OF_WEEK);
        if (weekday == 1) weekdayStr = "周日";
        if (weekday == 2) weekdayStr = "周一";
        if (weekday == 3) weekdayStr = "周二";
        if (weekday == 4) weekdayStr = "周三";
        if (weekday == 5) weekdayStr = "周四";
        if (weekday == 6) weekdayStr = "周五";
        if (weekday == 7) weekdayStr = "周六";
        String dateStr = mYear + "-" + mMonth + "-" + mDay + " " + weekdayStr;
        date.setText(dateStr);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
//            case R.id.share_to:
//                OnekeyShare oks = new OnekeyShare();
//                //关闭sso授权
//                oks.disableSSOWhenAuthorize();
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

    public void getTestData() {

        RetrofitHelper.getForecastWarn()
                .getbdsk(String.valueOf(lat), String.valueOf(lon))
                .compose(bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(bdskBeen -> {
                    BdskBeen.DataBean data = bdskBeen.getData().get(0);
                    jslData.setText(data.getPRE());
                    loc.setText(adress);
                    temper.setText(data.getTEM() + "℃");
                    xdsdData.setText(data.getRHU());
                    fsfxData.setText(data.getWIN_D_INST());
                    fsfxData1.setText(data.getWIN_S_INST());

                }, throwable -> {
                    LogUtils.e(throwable);
                    ToastUtils.showShort(getString(R.string.getInfo_error_toast));
                });
    }

//    public void getTestData1() {
//        RetrofitHelper.getForecastWarn()
//                .getbdyb("28.67435465", "115.98897081", "江西省南昌市青山湖区艾溪湖管理处艾溪村东南方向")
//                .compose(bindToLifecycle())
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(bdybBeen -> {
//                    BdybBeen.DataBean data = bdybBeen.getData();
//                    //现在只有一条数据
//                    // 得到当前预报时间
//                    String fbTime = data.getFbTime();
//                    long foretimeStamp = 0;
//                    int foretime = data.getYbList().get(0).getForetime();
//                    try {
//                        foretimeStamp = dateToStamp(fbTime);
//                    } catch (ParseException e) {
//                        e.printStackTrace();
//                    }
//                    long forecastTime = foretime * 3600 * 1000 + foretimeStamp;
//                    String forecastTimeStamp = String.valueOf(forecastTime);
//                    String forecastTimeDate = stampToDate(forecastTimeStamp);
//                    Date dateTime = ToDate(forecastTimeDate);
//                    int date = dateTime.getDate();
//                    String tempDate = null;
//                    if (date == mDay) tempDate = "今天" + dateTime.getHours() + "时";
//                    if (date > mDay) {
//                        if (date - mDay == 1)
//                            tempDate = "明天" + dateTime.getHours() + "时";
//                    }
//                    if (date > mDay) {
//                        if (date - mDay == 2)
//                            tempDate = "后天" + dateTime.getHours() + "时";
//                    }
//
//                    forecast_time.setText(tempDate);
//
//                    //得到预报图片
//                    String tqxx = data.getYbList().get(0).getTqxx();
//                    String picBiaoJi = "";
//                    JsonArray jsonElements = initWeathTuPianJson();
//                    for (int i = 0; i < jsonElements.size(); i++) {
//                        JsonObject jsonObject = jsonElements.get(i).getAsJsonObject();
//                        JsonElement picInfo = jsonObject.get("name");
//                        if (picInfo.getAsString().equals(tqxx)) {
//                            picBiaoJi = jsonObject.get("image").getAsString();
//                            break;
//                        } else continue;
//                    }
//                    String imageName = "weath_" + picBiaoJi;
//                    forecast_image.setImageResource(getResource(imageName));
//                    forecast_weath.setText(tqxx);
//                }, throwable -> {
//                    LogUtils.e(throwable);
//                    ToastUtils.showShort(getString(R.string.getInfo_error_toast));
//                });
//    }

    public void getTestData1() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = df.format(System.currentTimeMillis());
        RetrofitHelper.getWeatherMonitorAPI()
                .getGdyb(String.valueOf(lon), String.valueOf(lat), "1", "bd09ll", time)
                .compose(bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(gdybBeen -> {
                    List<GdybBeen.DataBean> data = gdybBeen.getData();
                    LinearLayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
                    mAdapter = new WeathMainAdapter(context, data);

                    recyclerView.setLayoutManager(layoutManager);
                    recyclerView.setAdapter(mAdapter);


                }, throwable -> {
                    LogUtils.e(throwable);
                    ToastUtils.showShort(getString(R.string.getInfo_error_toast));
                });


//        FullyLinearLayoutManager layoutManagerVertical = new FullyLinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
//
//        List<BdybBeen.DataBean.YbListBean>  mData2 = new ArrayList<>();
//
//        for (int i = 0; i < mData1.size(); i++) {
//            int foretime = mData1.get(i).getForetime();
//            if(foretime%24==0){
//                mData2.add(mData1.get(i));
//            }
//        }
//
//        bdybRecycle.setLayoutManager(layoutManagerVertical);
//  mbdybAdapter = new WeathMainBdybAdapter(context,,mData2);
//        bdybRecycle.setAdapter(mbdybAdapter);
//        bdybRecycle.setHasFixedSize(true);
//        bdybRecycle.setNestedScrollingEnabled(false);
    }


    public void getTestData2() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = df.format(System.currentTimeMillis());
        RetrofitHelper.getWeatherMonitorAPI()
                .getGdyb(String.valueOf(lon), String.valueOf(lat), "24", "bd09ll", time)
                .compose(bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(gdybBeen -> {
                    FullyLinearLayoutManager layoutManagerVertical = new FullyLinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);

                    List<GdybBeen.DataBean> data = gdybBeen.getData();
                    bdybRecycle.setLayoutManager(layoutManagerVertical);
                    mbdybAdapter = new WeathMainBdybAdapter(context, data);
                    bdybRecycle.setAdapter(mbdybAdapter);
                    bdybRecycle.setHasFixedSize(true);
                    bdybRecycle.setNestedScrollingEnabled(false);

                }, throwable -> {
                    LogUtils.e(throwable);
                    ToastUtils.showShort(getString(R.string.getInfo_error_toast));
                });
    }

    public void getTestData3() {
        RetrofitHelper.getForecastWarn()
                .getYujinInfoToBdsk(city)
                .compose(bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(yuJinXinhaoBeen -> {
                    List<YuJinXinhaoBeen.DataBean> data = yuJinXinhaoBeen.getData();
                    if (data.size() > 0) yjxh1.setVisibility(View.VISIBLE);
                    else yjxh.setVisibility(View.VISIBLE);
                    for (int i = 0; i < data.size(); i++) {
                        String danwei = data.get(i).getDanwei();
                        String subclass = data.get(i).getSubclass();
                        String citySelect = data.get(i).getCitySelect();
                        if (citySelect.contains(city)) {
                            tqms.setText(subclass);
                            fbdw.setText(danwei);
                            String substring = subclass.substring(0, subclass.length() - 4);
                            String picName = YujinWeiZhi.getPicName(substring);
                            int resource = GetResourceInt.getResource(picName, context);
                            Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), resource);
                            bd_tqtp.setImageBitmap(bitmap);
                        }
                    }
                }, throwable -> {
                    LogUtils.e(throwable);
                    ToastUtils.showShort(getString(R.string.getInfo_error_toast));
                });

    }

    @Override
    public void onRegeocodeSearched(RegeocodeResult regeocodeResult, int i) {
        city = regeocodeResult.getRegeocodeAddress().getTownship();
        getTestData3();
    }

    @Override
    public void onGeocodeSearched(GeocodeResult geocodeResult, int i) {

    }


    public void getAddress(final LatLonPoint latLonPoint) {
        RegeocodeQuery query = new RegeocodeQuery(latLonPoint, 500,
                GeocodeSearch.AMAP);
        geocoderSearch.getFromLocationAsyn(query);
    }
}