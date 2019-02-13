package net.univr.pushi.jxatmosphere.feature;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeQuery;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;

import net.univr.pushi.jxatmosphere.R;
import net.univr.pushi.jxatmosphere.adapter.WeathMainAdapter;
import net.univr.pushi.jxatmosphere.adapter.WeathMainBdybAdapter;
import net.univr.pushi.jxatmosphere.base.BaseActivity;
import net.univr.pushi.jxatmosphere.beens.BdskBeen;
import net.univr.pushi.jxatmosphere.beens.GdybBeen;
import net.univr.pushi.jxatmosphere.beens.YujinInfo;
import net.univr.pushi.jxatmosphere.remote.RetrofitHelper;
import net.univr.pushi.jxatmosphere.utils.GetResourceInt;
import net.univr.pushi.jxatmosphere.utils.ShipeiUtils;
import net.univr.pushi.jxatmosphere.utils.YujinWeiZhi;
import net.univr.pushi.jxatmosphere.widget.FullyLinearLayoutManager;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class WeathMainActivity extends BaseActivity implements View.OnClickListener, GeocodeSearch.OnGeocodeSearchListener {


    @BindView(R.id.work_schedule_leave)
    ImageView workScheduleLeave;
    @BindView(R.id.reload)
    ImageView reload;
    @BindView(R.id.date)
    TextView date;
    @BindView(R.id.jsl_data)
    TextView jslData;
    @BindView(R.id.xdsd_data)
    TextView xdsdData;
    @BindView(R.id.fs)
    TextView fsData;
    @BindView(R.id.temper)
    TextView temper;
    @BindView(R.id.titleBar_title)
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
    @BindView(R.id.yujin_info)
    LinearLayout yujin_info;
    private List<YujinInfo.DataBean> data;
    private int mDay;
    private String lat;
    private String lon;
    GeocodeSearch geocoderSearch;
    String city;
    private ProgressDialog progressDialog;
    //声明mlocationClient对象
    public AMapLocationClient mlocationClient;
    //声明mLocationOption对象
    public AMapLocationClientOption mLocationOption;
    @BindView(R.id.city_contain)
    RelativeLayout city_contain;
    @BindView(R.id.point_contain)
    LinearLayout point_contain;

    @BindView(R.id.change_city)
    ImageView change_city;
    String cityTown;
    int cityIndex = 0;//当前city点选中的下标
//    Map<String, Object> citySelectFlag = new HashMap<>();

    @Override
    public int getLayoutId() {
        return R.layout.activity_weath_main;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        super.initViews(savedInstanceState);
        reload.setOnClickListener(this);
        yujin_info.setOnClickListener(this);
        workScheduleLeave.setOnClickListener(this);
        geocoderSearch = new GeocodeSearch(this);
        geocoderSearch.setOnGeocodeSearchListener(this);
        loc.setOnClickListener(this);
        change_city.setOnClickListener(this);
        initDate();
        initLocation();
        initCity();
    }

    private void initCity() {
        String local_lat = SPUtils.getInstance().getString("local_lat", "");
        String local_lon = SPUtils.getInstance().getString("local_lon", "");
        String local_city = SPUtils.getInstance().getString("local_city", "");
        String local_weizhi = SPUtils.getInstance().getString("local_weizhi", "");
        if (null != local_weizhi && !local_weizhi.equals("")) {
            String[] split = local_weizhi.split(",");
            for (int i = -1; i < split.length; i++) {//-1代表本地信息
                ImageView imageView = new ImageView(context);
                if (i == -1) imageView.setImageResource(R.drawable.point);
                else
                    imageView.setImageResource(R.drawable.point_unselect);
                imageView.setId(i);
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String[] lat_split = local_lat.split(",");
                        String[] lon_split = local_lon.split(",");
                        String[] city_split = local_city.split(",");
                        String[] weizhi_split = local_weizhi.split(",");
                        if (v.getId() == -1) {
                            initLocation();
                        } else {
                            lat = lat_split[imageView.getId()];
                            lon = lon_split[imageView.getId()];
                            city = city_split[imageView.getId()];
                            loc.setText(weizhi_split[imageView.getId()]);
                            getTestData();
                            getTestData1();
                            getTestData2();
                            getTestData3();

                        }
                        initCityIcon((v.getId()) + 1);
                        cityIndex = (imageView.getId()) + 1;//设置选中位置
                    }
                });
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(60, 60);
                layoutParams.leftMargin = 30;
                point_contain.addView(imageView, layoutParams);
            }
        }
    }

    void initCityIcon(int index) {
        int childCount = point_contain.getChildCount();
        for (int i = 0; i < childCount; i++) {
            ImageView childAt = (ImageView) point_contain.getChildAt(i);
            if (index == i) {
                childAt.setImageResource(R.drawable.point);
            } else {
                childAt.setImageResource(R.drawable.point_unselect);
            }
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

    private void initLocation() {
        if (ShipeiUtils.isLocationEnabled(context)) {
            mlocationClient = new AMapLocationClient(this);
//初始化定位参数
            mLocationOption = new AMapLocationClientOption();
            //设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
            mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
//设置定位间隔,单位毫秒,默认为2000ms
//        mLocationOption.setInterval(2000);
            mLocationOption.setOnceLocation(true);
            mlocationClient.setLocationOption(mLocationOption);
            mlocationClient.startLocation();
//设置定位监听
            mlocationClient.setLocationListener(new AMapLocationListener() {
                @Override
                public void onLocationChanged(AMapLocation aMapLocation) {
                    if (aMapLocation != null) {
                        if (aMapLocation.getErrorCode() == 0) {
                            lat = String.valueOf(aMapLocation.getLatitude());//获取纬度
                            lon = String.valueOf(aMapLocation.getLongitude());//获取经度
                            getAddress(new LatLonPoint(Double.valueOf(lat), Double.valueOf(lon)));
                            getTestData();
                            getTestData1();
                            getTestData2();
                            getAddress(new LatLonPoint(Double.valueOf(lat), Double.valueOf(lon)));
                        } else {
                            Log.e("AmapError", "location Error, ErrCode:"
                                    + aMapLocation.getErrorCode() + ", errInfo:"
                                    + aMapLocation.getErrorInfo());
                        }
                    }
                }
            });
        } else {
            AlertDialog alertDialog2 = new AlertDialog.Builder(this)
                    .setMessage("是否开启位置信息")
                    .setPositiveButton("是", new DialogInterface.OnClickListener() {//添加"Yes"按钮
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                            startActivity(intent);
                        }
                    })

                    .setNegativeButton("否", new DialogInterface.OnClickListener() {//添加取消
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                        }
                    })
                    .create();
            alertDialog2.show();
        }

    }

    private void initDate() {
        String weekdayStr = "";
        Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH) + 1;
        mDay = c.get(Calendar.DAY_OF_MONTH);
        int weekday = c.get(Calendar.DAY_OF_WEEK);
        if (weekday == 1) weekdayStr = "星期天";
        if (weekday == 2) weekdayStr = "星期一";
        if (weekday == 3) weekdayStr = "星期二";
        if (weekday == 4) weekdayStr = "星期三";
        if (weekday == 5) weekdayStr = "星期四";
        if (weekday == 6) weekdayStr = "星期五";
        if (weekday == 7) weekdayStr = "星期六";
        String dateStr = mYear + "-" + mMonth + "-" + mDay + " " + weekdayStr;
        date.setText(dateStr);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.reload:
                jslData.setText("0.0mm");
                xdsdData.setText("0.0%");
                fsData.setText("");
//                loc.setText("");
                temper.setText("");

                if (mAdapter != null) {
                    mAdapter.getData().clear();
                    mAdapter.notifyDataSetChanged();
                }


                if (mbdybAdapter != null) {
                    mbdybAdapter.getData().clear();
                    mbdybAdapter.notifyDataSetChanged();
                }

                tqms.setText("");
                fbdw.setText("");
                bd_tqtp.setImageBitmap(null);
                getTestData();
                getTestData1();
                getTestData2();
//                initLocation();
//                Handler handler = new Handler();
//                handler.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        progressDialog.dismiss();
//                    }
//                }, 1000);
                break;
            case R.id.work_schedule_leave:
                finish();
                break;
            case R.id.yujin_info:
                Intent intent = new Intent(context, YujinInfoActivity.class);
                Bundle bundle = new Bundle();
                ArrayList arrayList = new ArrayList();
                arrayList.addAll(data);
                bundle.putParcelableArrayList("data", arrayList);
                intent.putExtras(bundle);
                startActivity(intent);
                break;
//            case R.id.titleBar_title:
//                Intent intent1 = new Intent(context, LocationChangeActivity.class);
//                startActivityForResult(intent1, 11);
//                break;
            case R.id.change_city:
                Intent intent2 = new Intent(context, CitySelectActivity.class);
                intent2.putExtra("cityTown", cityTown);
                startActivityForResult(intent2, 11);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 11) {
            Bundle extras = data.getExtras();
            if (extras == null) ;
            else {
                Boolean addOrRemove = extras.getBoolean("addOrRemove", false);
                Boolean relocation = extras.getBoolean("relocation", false);
                if(relocation){
                    initLocation();
                    initCityIcon(0);
                }
                if (addOrRemove) {
                    point_contain.removeAllViews();
                    String local_lat = SPUtils.getInstance().getString("local_lat", "");
                    String local_lon = SPUtils.getInstance().getString("local_lon", "");
                    String local_city = SPUtils.getInstance().getString("local_city", "");
                    String local_weizhi = SPUtils.getInstance().getString("local_weizhi", "");
                    if (local_weizhi.equals("")) {
                    } else {
                        initCity();
                        String[] lat_split = local_lat.split(",");
                        String[] lon_split = local_lon.split(",");
                        String[] city_split = local_city.split(",");
                        String[] weizhi_split = local_weizhi.split(",");
                        int length = weizhi_split.length-1;
                        if(cityIndex==0);
                        else{
                            if (cityIndex-1 <= length-1){
                                initCityIcon(cityIndex);
                                RequestData(lat_split[cityIndex - 1], lon_split[cityIndex - 1], city_split[cityIndex - 1], weizhi_split[cityIndex - 1]);
                            }
                            else{
                                initCityIcon(length+1);
                                RequestData(lat_split[length], lon_split[length], city_split[length], weizhi_split[length]);
                            }
                        }
                    }
                } else {
                    if (resultCode == 1) {//点击了选项
                        Integer position = extras.getInt("position", -100);
                        if (position != -100) {
                            initCityIcon(position);
                        }
                        RequestData(extras.getString("lat"), extras.getString("lon"), extras.getString("city"), extras.getString("weizhi"));
                    }
                }
            }
        }
    }

    private void RequestData(String lat_p, String lon_p, String city_p, String weizhi_p) {
        //之前数据置空
        jslData.setText("0.0mm");
        xdsdData.setText("0.0%");
        fsData.setText("");
        loc.setText("");
        temper.setText("");
        if (mAdapter != null) {
            mAdapter.getData().clear();
            mAdapter.notifyDataSetChanged();
        }
        if (mbdybAdapter != null) {
            mbdybAdapter.getData().clear();
            mbdybAdapter.notifyDataSetChanged();
        }
        tqms.setText("");
        fbdw.setText("");
        bd_tqtp.setImageBitmap(null);
        lat = lat_p;
        lon = lon_p;
        city = city_p;
        loc.setText(weizhi_p);
        //重新请求数据
        getTestData();
        getTestData1();
        getTestData2();
        getTestData3();
    }

    public void getTestData() {

        RetrofitHelper.getForecastWarn()
                .getbdsk(String.valueOf(lat), String.valueOf(lon))
                .compose(bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(bdskBeen -> {
                    BdskBeen.DataBean data = bdskBeen.getData().get(0);
                    if (data.getPRE().equals("0") || data.getPRE().equals("0.0"))
                        jslData.setText("0.0mm");
                    else
                        jslData.setText(data.getPRE() + "mm");

                    temper.setText(double2zhen(Double.valueOf(data.getTEM())) + "℃");

                    if (data.getRHU().equals("0") || data.getRHU().equals("0.0"))
                        xdsdData.setText("0.0%");
                    else
                        xdsdData.setText(data.getRHU() + "%");

                    String win_s_inst = data.getWIN_S_INST();
                    Double win_s_inst_D = Double.valueOf(win_s_inst);
                    String windDesc = "无风";
                    if (win_s_inst_D >= 0 && win_s_inst_D < 0.3) {
                        windDesc = "无风";
                    } else if (win_s_inst_D >= 0.3 && win_s_inst_D < 3.4) {
                        windDesc = "微风";
                    } else if (win_s_inst_D >= 3.4 && win_s_inst_D < 5.5) {
                        windDesc = "软风";
                    } else if (win_s_inst_D >= 5.5 && win_s_inst_D < 8.0) {
                        windDesc = "和风";
                    } else if (win_s_inst_D >= 8.0 && win_s_inst_D < 10.8) {
                        windDesc = "青劲风";
                    } else if (win_s_inst_D >= 10.8 && win_s_inst_D < 13.9) {
                        windDesc = "强风";
                    } else if (win_s_inst_D >= 13.9 && win_s_inst_D < 17.2) {
                        windDesc = "疾风";
                    } else if (win_s_inst_D >= 17.2 && win_s_inst_D < 20.8) {
                        windDesc = "大风";
                    } else if (win_s_inst_D >= 20.8 && win_s_inst_D < 24.5) {
                        windDesc = "烈风";
                    } else if (win_s_inst_D >= 24.5 && win_s_inst_D < 28.5) {
                        windDesc = "狂风";
                    } else {
                        windDesc = "飓风";
                    }
                    fsData.setText(windDesc);

                }, throwable -> {
                    LogUtils.e(throwable);
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
                });

    }


    public void getTestData2() {
        progressDialog = ProgressDialog.show(context, "请稍等...", "获取数据中...", true);
        progressDialog.setCancelable(true);
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
                    progressDialog.dismiss();

                }, throwable -> {
                    progressDialog.dismiss();
                    LogUtils.e(throwable);
                    ToastUtils.showShort("没查询到数据");
                });
    }

    public void getTestData3() {
        RetrofitHelper.getForecastWarn()
                .getYujinInfoToBdsk(city)
                .compose(bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(yuJinXinhaoBeen -> {
                    data = yuJinXinhaoBeen.getData();
                    if (null != data && data.size() != 0) {
                        String danwei = data.get(0).getDanwei();
                        String subclass = data.get(0).getYujinInfo();
                        yjxh1.setVisibility(View.VISIBLE);
                        tqms.setText(subclass);
                        fbdw.setText(danwei);
                        String substring = subclass.substring(0, subclass.length() - 2);
                        String picName = YujinWeiZhi.getPicName(substring);
                        int resource = GetResourceInt.getResource(picName, context);
                        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), resource);
                        bd_tqtp.setImageBitmap(bitmap);
                    } else {
                        yjxh.setVisibility(View.VISIBLE);
                    }

//                    if (data.size() > 0) yjxh1.setVisibility(View.VISIBLE);
//                    else yjxh.setVisibility(View.VISIBLE);
//                    for (int i = 0; i < data.size(); i++) {
//                        String danwei = data.get(i).getDanwei();
//                        String subclass = data.get(i).getYujinInfo();
//                        String citySelect = data.get(i).getAcodes();
//                        String[] split = citySelect.split(",");
//                        for (int j = 0; j < split.length; j++) {
//                            String title = YujinWeiZhi.getTitle(split[i]);
//                            if (title.equals(city+"市")) {
//                                yjxh1.setVisibility(View.VISIBLE);
//                                tqms.setText(subclass);
//                                fbdw.setText(danwei);
//                                String substring = subclass.substring(0, subclass.length() - 4);
//                                String picName = YujinWeiZhi.getPicName(substring);
//                                int resource = GetResourceInt.getResource(picName, context);
//                                Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), resource);
//                                bd_tqtp.setImageBitmap(bitmap);
//                                break;
//                            }else {
//                                yjxh.setVisibility(View.VISIBLE);
//                            }
//                        }
//                    }

                }, throwable -> {
                    LogUtils.e(throwable);
                });
    }

    @Override
    public void onRegeocodeSearched(RegeocodeResult regeocodeResult, int i) {
        loc.setText(regeocodeResult.getRegeocodeAddress().getAois().get(0).getAoiName());
        city = regeocodeResult.getRegeocodeAddress().getCity();
        city = city.replace("市", "");
        cityTown = regeocodeResult.getRegeocodeAddress().getTownship() + "·" + regeocodeResult.getRegeocodeAddress().getCity();
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

    public int double2zhen(double num_d) {
        BigDecimal bg = new BigDecimal(num_d).setScale(0, BigDecimal.ROUND_HALF_UP);
        return bg.intValue();
    }

//    @Override
//    protected void onResume() {
//        super.onResume();
//        Object position = citySelectFlag.get("position");
//        if(position!=null){
//            Integer position1 = (Integer) position;
//            initCityIcon(position1);
//        }
//    }
}