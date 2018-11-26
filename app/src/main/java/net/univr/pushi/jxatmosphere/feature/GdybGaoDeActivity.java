package net.univr.pushi.jxatmosphere.feature;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.LatLngBounds;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.maps.model.PolylineOptions;
import com.amap.api.services.core.AMapException;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.district.DistrictItem;
import com.amap.api.services.district.DistrictResult;
import com.amap.api.services.district.DistrictSearch;
import com.amap.api.services.district.DistrictSearchQuery;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeQuery;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import net.univr.pushi.jxatmosphere.R;
import net.univr.pushi.jxatmosphere.adapter.SimpleAdapter;
import net.univr.pushi.jxatmosphere.base.BaseActivity;
import net.univr.pushi.jxatmosphere.beens.GdybBeen;
import net.univr.pushi.jxatmosphere.beens.GdybSearchBeen;
import net.univr.pushi.jxatmosphere.remote.RetrofitHelper;
import net.univr.pushi.jxatmosphere.utils.ShipeiUtils;
import net.univr.pushi.jxatmosphere.utils.ThreadUtil;
import net.univr.pushi.jxatmosphere.utils.ViewUtil;
import net.univr.pushi.jxatmosphere.widget.FutureDaysChart;
import net.univr.pushi.jxatmosphere.widget.ScrollFutureDaysWeatherView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static net.univr.pushi.jxatmosphere.adapter.WeathMainAdapter.initWeathTuPianJson;

public class GdybGaoDeActivity extends BaseActivity implements View.OnClickListener, DistrictSearch.OnDistrictSearchListener, AMap.OnMapClickListener, GeocodeSearch.OnGeocodeSearchListener {


    ProgressDialog dialog = null;
    @BindView(R.id.forecast_time)
    TextView forecast_time;
    @BindView(R.id.one_hour)
    TextView one_hour;
    @BindView(R.id.three_hour)
    TextView three_hour;
    @BindView(R.id.six_hour)
    TextView six_hour;
    @BindView(R.id.twelve_hour)
    TextView twelve_hour;
    @BindView(R.id.twentyfour_hour)
    TextView twentyfour_hour;
    @BindView(R.id.gd_dizhi)
    EditText sousuo_tv;
    @BindView(R.id.search_info)
    TextView search_info;

    @BindView(R.id.province)
    TextView province;
    @BindView(R.id.city)
    TextView city;
    @BindView(R.id.town)
    TextView town;
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.mapview)
    MapView mapView;
    @BindView(R.id.delete)
    ImageView delete;
    public AMapLocationClient mlocationClient;
    public AMapLocationClientOption mLocationOption;
    private GeocodeSearch geocoderSearch;
    private MyLocationStyle myLocationStyle;

    String interval = "1";
    private List<GdybBeen.DataBean> data = new ArrayList<>();
    Double x;
    Double y;
    int i = 0;

    @BindView(R.id.recyclerView_search)
    RecyclerView recyclerView_search;
    SimpleAdapter simpleAdapter;

    AMap mAMap;
    Marker marker;
    //poi搜索的条目是否点击了，点击文字覆盖在搜索框，不调用文字变化
    Boolean selectItem = false;
    //是否点击了地图
    Boolean clickMap = false;


    ScrollFutureDaysWeatherView scrollFutureDaysWeatherView;
    private FutureDaysChart futureDaysChart;
    @BindView(R.id.content)
    LinearLayout content;
    @BindView(R.id.myScrollView)
    HorizontalScrollView horizontalScrollView;
    @BindView(R.id.now_position)
    ImageView nowPosition;




    @Override
    public int getLayoutId() {
        return R.layout.activity_gdyb_gao_de;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        super.initViews(savedInstanceState);
        initView(savedInstanceState);
        getJiangxiFanwei();
        initLocation();
        one_hour.setOnClickListener(this);
        three_hour.setOnClickListener(this);
        six_hour.setOnClickListener(this);
        twelve_hour.setOnClickListener(this);
        twentyfour_hour.setOnClickListener(this);
        back.setOnClickListener(this);
        delete.setOnClickListener(this);
        search_info.setOnClickListener(this);
        nowPosition.setOnClickListener(this);

    }


    private void initView(Bundle savedInstanceState) {
        mapView.onCreate(savedInstanceState);
        mAMap = mapView.getMap();
        myLocationStyle = new MyLocationStyle();
        mAMap.getUiSettings().setRotateGesturesEnabled(false);
        mAMap.getUiSettings().setZoomControlsEnabled(false);
        mAMap.setOnMapClickListener(this);
        geocoderSearch = new GeocodeSearch(this);
        geocoderSearch.setOnGeocodeSearchListener(this);
        sousuo_tv.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!selectItem) {
                    String s1 = s.toString();
                    PoiSearch.Query query = new PoiSearch.Query(s1, "", "360000");
                    query.setPageSize(20);
                    PoiSearch poiSearch = new PoiSearch(context, query);
                    poiSearch.setOnPoiSearchListener(new PoiSearch.OnPoiSearchListener() {
                        @Override
                        public void onPoiSearched(PoiResult poiResult, int i) {
                            List<GdybSearchBeen> data = new ArrayList<>();
                            ArrayList<PoiItem> pois = poiResult.getPois();
                            for (int j = 0; j < pois.size(); j++) {
                                PoiItem poiItem = pois.get(j);
                                LatLonPoint latLonPoint = poiItem.getLatLonPoint();
                                double latitude = latLonPoint.getLatitude();
                                double longitude = latLonPoint.getLongitude();
                                String s = poiItem.toString();
                                GdybSearchBeen gdybSearchBeen = new GdybSearchBeen();
                                gdybSearchBeen.setTitle(s);
                                gdybSearchBeen.setLat(latitude);
                                gdybSearchBeen.setLon(longitude);
                                data.add(gdybSearchBeen);
                            }
                            getRecycleAdapter();
                            simpleAdapter.setNewData(data);
                            recyclerView_search.setVisibility(View.VISIBLE);
                        }

                        @Override
                        public void onPoiItemSearched(PoiItem poiItem, int i) {

                        }
                    });
                    poiSearch.searchPOIAsyn();
                }

            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

    }

    private void getTestdata() {
        dialog = ProgressDialog.show(context, "请稍等...", "获取数据中...", true);
        dialog.setCancelable(true);
        content.setVisibility(View.VISIBLE);
        if (ShipeiUtils.isLocationEnabled(context)) {
            ;
        } else {
            //取消定位蓝点
            mAMap.setMyLocationEnabled(false);
        }
        RetrofitHelper.getWeatherMonitorAPI()
                .getGdyb(String.valueOf(x), String.valueOf(y), interval, "bd09ll", initForecastTime())
                .compose(bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(gdybBeen -> {
                    data = gdybBeen.getData();
                    forecast_time.setText(initForecastTime());
                    province.setText(gdybBeen.getLocation().getProvince());
                    city.setText(gdybBeen.getLocation().getCity());
                    town.setText(gdybBeen.getLocation().getDistrict());
                    //移除之前的天气控件
                    horizontalScrollView.removeAllViews();

                    //点击地图位置设置在搜索框，切不触发文字监听
                    if (clickMap) {
                        LatLonPoint latLonPoint = new LatLonPoint(y, x);
                        getAddress(latLonPoint);
                    }
                    clickMap = false;

                    if (data.size() == 0) {
                        dialog.dismiss();
                        return;
                    }
                    scrollFutureDaysWeatherView = new ScrollFutureDaysWeatherView(context, null, 0, data.size());
                    futureDaysChart = scrollFutureDaysWeatherView.getSevenDaysChart();
                    LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) horizontalScrollView.getLayoutParams();
                    params.width = HorizontalScrollView.LayoutParams.MATCH_PARENT;
                    params.height = HorizontalScrollView.LayoutParams.MATCH_PARENT;
                    horizontalScrollView.addView(scrollFutureDaysWeatherView, params);

                    futureDaysChart.setDatas(data);
                    List<View> viewList = scrollFutureDaysWeatherView.getAllViews();
                    for (int i = 0; i < viewList.size(); i++) {
                        View view = viewList.get(i);
                        TextView day = view.findViewById(R.id.day);
                        TextView hour = view.findViewById(R.id.hour);
                        TextView weather_desc = view.findViewById(R.id.weather_desc);
                        ImageView weather_img = view.findViewById(R.id.weather_img);
                        TextView rainfall = view.findViewById(R.id.rainfall);
                        TextView wind = view.findViewById(R.id.wind);
                        TextView xdsd = view.findViewById(R.id.xdsd);
                        //设置日期
                        String forecastTime = data.get(i).getForecastTime();

                        if (forecastTime.length() <= 18) {
                            StringBuilder builder = new StringBuilder(forecastTime);
                            builder.insert(11, 0);
                            forecastTime = builder.toString();
                        }
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yy/MM/dd HH:mm:ss");
                        Calendar calendar = Calendar.getInstance();
                        try {
                            Date forecastDate = simpleDateFormat.parse(forecastTime);
                            calendar.setTime(forecastDate);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        day.setText(calendar.get(Calendar.MONTH) + 1 + "/" + calendar.get(Calendar.DAY_OF_MONTH));
                        //设置小时
                        if (interval.equals("24")) {
                            hour.setVisibility(View.INVISIBLE);
                        } else if (interval.equals("12")) {
                            if (hour.getVisibility() == View.INVISIBLE)
                                hour.setVisibility(View.VISIBLE);
                            if (calendar.get(Calendar.HOUR_OF_DAY) <= 12)
                                hour.setText("上午");
                            else hour.setText("下午");
                        } else {
                            if (hour.getVisibility() == View.INVISIBLE)
                                hour.setVisibility(View.VISIBLE);
                            hour.setText(calendar.get(Calendar.HOUR_OF_DAY) + "时");
                        }
                        weather_desc.setText(data.get(i).getWeatherDesc());
                        //得到天气图片
                        String tqxx = data.get(i).getWeatherDesc();
                        String picBiaoJi = "";
                        JsonArray jsonElements = initWeathTuPianJson();
                        for (int j = 0; j < jsonElements.size(); j++) {
                            JsonObject jsonObject = jsonElements.get(j).getAsJsonObject();
                            JsonElement picInfo = jsonObject.get("name");
                            if (picInfo.getAsString().equals(tqxx)) {
                                picBiaoJi = jsonObject.get("image").getAsString();
                                break;
                            } else continue;
                        }
                        String imageName = picBiaoJi;
                        weather_img.setImageResource(getResource(imageName));
                        //设置降雨量
                        rainfall.setText(data.get(i).getRain());
                        //设置风速
                        Double windSpeed = Double.valueOf(data.get(i).getWindSpeed());
                        String windSpeedStr = "";
                        if (windSpeed >= 0 && windSpeed < 0.3) {
                            windSpeedStr = "无风";
                        } else if (windSpeed >= 0.3 && windSpeed < 3.4) {
                            windSpeedStr = "微风";
                        } else if (windSpeed >= 3.4 && windSpeed < 5.5) {
                            windSpeedStr = "软风";
                        } else if (windSpeed >= 5.5 && windSpeed < 8.0) {
                            windSpeedStr = "和风";
                        } else if (windSpeed >= 8.0 && windSpeed < 10.8) {
                            windSpeedStr = "青劲风";
                        } else if (windSpeed >= 10.8 && windSpeed < 13.9) {
                            windSpeedStr = "强风";
                        } else if (windSpeed >= 13.9 && windSpeed < 17.2) {
                            windSpeedStr = "疾风";
                        } else if (windSpeed >= 17.2 && windSpeed < 20.8) {
                            windSpeedStr = "大风";
                        } else if (windSpeed >= 20.8 && windSpeed < 24.5) {
                            windSpeedStr = "烈风";
                        } else if (windSpeed >= 24.5 && windSpeed < 28.5) {
                            windSpeedStr = "狂风";
                        } else {
                            windSpeedStr = "飓风";
                        }
                        wind.setText(windSpeedStr);
                        //设置相对湿度
                        xdsd.setText(data.get(i).getHumidity());
                    }

                    dialog.dismiss();
                }, throwable -> {
                    dialog.dismiss();
                    LogUtils.e(throwable);
                    ToastUtils.showShort(getString(R.string.getInfo_error_toast));
                });

    }

    public int getResource(String imageName) {
        Context ctx = ((Activity) context).getBaseContext();
        int resId = context.getResources().getIdentifier(imageName, "drawable", ctx.getPackageName());
        //如果没有在"mipmap"下找到imageName,将会返回0
        return resId;
    }


    private String initForecastTime() {
        String destTimeString;
        Date date = new Date(System.currentTimeMillis());
        String datetime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
        int hour = date.getHours();
        if (hour < 20)
            hour = 8;
        else
            hour = 20;
        if (hour == 8)
            destTimeString = datetime.substring(0, 10) + "0" + hour + ":00:00";
        else
            destTimeString = datetime.substring(0, 10) + hour + ":00:00";

        String riqiString = destTimeString.substring(0, 10);

        String shijianString = destTimeString.substring(destTimeString.length() - 8,
                destTimeString.length());
        return riqiString + " " + shijianString;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.one_hour:
                interval = "1";
                one_hour.setBackground(getResources().getDrawable(R.drawable.gd_text_bg1_select));
                one_hour.setTextColor(getResources().getColor(R.color.white));
                three_hour.setBackground(getResources().getDrawable(R.drawable.gd_text_bg2));
                three_hour.setTextColor(getResources().getColor(R.color.toolbar_color));
                six_hour.setBackground(getResources().getDrawable(R.drawable.gd_text_bg2));
                six_hour.setTextColor(getResources().getColor(R.color.toolbar_color));
                twelve_hour.setBackground(getResources().getDrawable(R.drawable.gd_text_bg2));
                twelve_hour.setTextColor(getResources().getColor(R.color.toolbar_color));
                twentyfour_hour.setBackground(getResources().getDrawable(R.drawable.gd_text_bg3));
                twentyfour_hour.setTextColor(getResources().getColor(R.color.toolbar_color));
                getTestdata();
                break;
            case R.id.three_hour:
                interval = "3";
                one_hour.setBackground(getResources().getDrawable(R.drawable.gd_text_bg1));
                one_hour.setTextColor(getResources().getColor(R.color.toolbar_color));
                three_hour.setBackground(getResources().getDrawable(R.drawable.gd_text_bg2_select));
                three_hour.setTextColor(getResources().getColor(R.color.white));
                six_hour.setBackground(getResources().getDrawable(R.drawable.gd_text_bg2));
                six_hour.setTextColor(getResources().getColor(R.color.toolbar_color));
                twelve_hour.setBackground(getResources().getDrawable(R.drawable.gd_text_bg2));
                twelve_hour.setTextColor(getResources().getColor(R.color.toolbar_color));
                twentyfour_hour.setBackground(getResources().getDrawable(R.drawable.gd_text_bg3));
                twentyfour_hour.setTextColor(getResources().getColor(R.color.toolbar_color));
                getTestdata();
                break;
            case R.id.six_hour:
                interval = "6";
                one_hour.setBackground(getResources().getDrawable(R.drawable.gd_text_bg1));
                one_hour.setTextColor(getResources().getColor(R.color.toolbar_color));
                three_hour.setBackground(getResources().getDrawable(R.drawable.gd_text_bg2));
                three_hour.setTextColor(getResources().getColor(R.color.toolbar_color));
                six_hour.setBackground(getResources().getDrawable(R.drawable.gd_text_bg2_select));
                six_hour.setTextColor(getResources().getColor(R.color.white));
                twelve_hour.setBackground(getResources().getDrawable(R.drawable.gd_text_bg2));
                twelve_hour.setTextColor(getResources().getColor(R.color.toolbar_color));
                twentyfour_hour.setBackground(getResources().getDrawable(R.drawable.gd_text_bg3));
                twentyfour_hour.setTextColor(getResources().getColor(R.color.toolbar_color));
                getTestdata();
                break;
            case R.id.twelve_hour:
                interval = "12";
                one_hour.setBackground(getResources().getDrawable(R.drawable.gd_text_bg1));
                one_hour.setTextColor(getResources().getColor(R.color.toolbar_color));
                three_hour.setBackground(getResources().getDrawable(R.drawable.gd_text_bg2));
                three_hour.setTextColor(getResources().getColor(R.color.toolbar_color));
                six_hour.setBackground(getResources().getDrawable(R.drawable.gd_text_bg2));
                six_hour.setTextColor(getResources().getColor(R.color.toolbar_color));
                twelve_hour.setBackground(getResources().getDrawable(R.drawable.gd_text_bg2_select));
                twelve_hour.setTextColor(getResources().getColor(R.color.white));
                twentyfour_hour.setBackground(getResources().getDrawable(R.drawable.gd_text_bg3));
                twentyfour_hour.setTextColor(getResources().getColor(R.color.toolbar_color));
                getTestdata();
                break;
            case R.id.twentyfour_hour:
                interval = "24";
                one_hour.setBackground(getResources().getDrawable(R.drawable.gd_text_bg1));
                one_hour.setTextColor(getResources().getColor(R.color.toolbar_color));
                three_hour.setBackground(getResources().getDrawable(R.drawable.gd_text_bg2));
                three_hour.setTextColor(getResources().getColor(R.color.toolbar_color));
                six_hour.setBackground(getResources().getDrawable(R.drawable.gd_text_bg2));
                six_hour.setTextColor(getResources().getColor(R.color.toolbar_color));
                twelve_hour.setBackground(getResources().getDrawable(R.drawable.gd_text_bg2));
                twelve_hour.setTextColor(getResources().getColor(R.color.toolbar_color));
                twentyfour_hour.setBackground(getResources().getDrawable(R.drawable.gd_text_bg3_select));
                twentyfour_hour.setTextColor(getResources().getColor(R.color.white));
                getTestdata();
                break;
            case R.id.back:
                finish();
                break;
            case R.id.delete:
                content.setVisibility(View.GONE);
                break;
            case R.id.now_position:
                if (marker != null)
                    marker.destroy();

                initLocation();
                break;
            case R.id.search_info:
                ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(GdybGaoDeActivity.this.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                String key = sousuo_tv.getText().toString();
                PoiSearch.Query query = new PoiSearch.Query(key, "", "360000");
                query.setPageSize(20);
                PoiSearch poiSearch = new PoiSearch(context, query);
                poiSearch.setOnPoiSearchListener(new PoiSearch.OnPoiSearchListener() {
                    @Override
                    public void onPoiSearched(PoiResult poiResult, int i) {
                        List<GdybSearchBeen> data = new ArrayList<>();
                        ArrayList<PoiItem> pois = poiResult.getPois();
                        for (int j = 0; j < pois.size(); j++) {
                            PoiItem poiItem = pois.get(j);
                            LatLonPoint latLonPoint = poiItem.getLatLonPoint();
                            double latitude = latLonPoint.getLatitude();
                            double longitude = latLonPoint.getLongitude();
                            String s = poiItem.toString();
                            GdybSearchBeen gdybSearchBeen = new GdybSearchBeen();
                            gdybSearchBeen.setTitle(s);
                            gdybSearchBeen.setLat(latitude);
                            gdybSearchBeen.setLon(longitude);
                            data.add(gdybSearchBeen);
                        }
                        getRecycleAdapter();
                        simpleAdapter.setNewData(data);
                        recyclerView_search.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onPoiItemSearched(PoiItem poiItem, int i) {

                    }
                });
                poiSearch.searchPOIAsyn();

                break;
        }
    }

    SimpleAdapter getRecycleAdapter() {
        if (simpleAdapter == null) {
            List<GdybSearchBeen> data = new ArrayList<>();
            simpleAdapter = new SimpleAdapter(data);
            simpleAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
                @Override
                public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                    selectItem = true;
                    GdybSearchBeen gdybSearchBeen = (GdybSearchBeen) adapter.getData().get(position);
                    sousuo_tv.setText(gdybSearchBeen.getTitle());
                    recyclerView_search.setVisibility(View.GONE);
                    ViewUtil.hide_keyboard_from(context, sousuo_tv);
                    sousuo_tv.clearFocus();
                    Double lat = gdybSearchBeen.getLat();
                    Double lon = gdybSearchBeen.getLon();
                    x = lon;
                    y = lat;
                    if (marker != null) marker.destroy();
                    addMarkersToMap(new LatLng(y, x));
//                    getAddress(new LatLonPoint(y, x));
                    getTestdata();
                    selectItem = false;
                    myLocationStyle.myLocationIcon(BitmapDescriptorFactory.fromResource(R.drawable.gps_point));
                    mAMap.setMyLocationStyle(myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATE));
                }
            });
            LinearLayoutManager manager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
            recyclerView_search.setLayoutManager(manager);
            recyclerView_search.setAdapter(simpleAdapter);
        }
        return simpleAdapter;
    }


    @Override
    public void onDistrictSearched(DistrictResult districtResult) {
        if (districtResult == null || districtResult.getDistrict() == null) {
            return;
        }
        //通过ErrorCode判断是否成功
        if (districtResult.getAMapException() != null && districtResult.getAMapException().getErrorCode() == AMapException.CODE_AMAP_SUCCESS) {
            final DistrictItem item = districtResult.getDistrict().get(0);

            if (item == null) {
                return;
            }
            LatLonPoint centerLatLng = item.getCenter();
            if (centerLatLng != null) {
                centerJiangxi();
            }


            ThreadUtil.getInstance().execute(new Runnable() {
                @Override
                public void run() {

                    String[] polyStr = item.districtBoundary();
                    if (polyStr == null || polyStr.length == 0) {
                        return;
                    }
                    for (String str : polyStr) {
                        String[] lat = str.split(";");
                        PolylineOptions polylineOption = new PolylineOptions();
                        boolean isFirst = true;
                        LatLng firstLatLng = null;
                        for (String latstr : lat) {
                            String[] lats = latstr.split(",");
                            if (isFirst) {
                                isFirst = false;
                                firstLatLng = new LatLng(Double
                                        .parseDouble(lats[1]), Double
                                        .parseDouble(lats[0]));
                            }
                            polylineOption.add(new LatLng(Double
                                    .parseDouble(lats[1]), Double
                                    .parseDouble(lats[0])));
                        }
                        if (firstLatLng != null) {
                            polylineOption.add(firstLatLng);
                        }

                        polylineOption.width(10).color(Color.BLUE);
                        mAMap.addPolyline(polylineOption);
                    }
                }
            });
        } else {
            if (districtResult.getAMapException() != null) {
                ToastUtils.showShort(districtResult.getAMapException().getErrorCode());
            }
        }
    }

    @Override
    public void onRegeocodeSearched(RegeocodeResult result, int rCode) {
        if (rCode == AMapException.CODE_AMAP_SUCCESS) {
            if (result != null && result.getRegeocodeAddress() != null
                    && result.getRegeocodeAddress().getFormatAddress() != null) {
//                city.setText(result.getRegeocodeAddress().getCity());
//                town.setText(result.getRegeocodeAddress().getDistrict());
//                province.setText(result.getRegeocodeAddress().getTownship());
                selectItem = true;
                sousuo_tv.setText(result.getRegeocodeAddress().getTownship());
                selectItem = false;
            } else {
                ToastUtils.showShort("没查询到位置");
            }

        } else {
            ToastUtils.showShort(rCode);
        }
    }

    @Override
    public void onGeocodeSearched(GeocodeResult geocodeResult, int i) {

    }


    //得到江西省范围
    private void getJiangxiFanwei() {
        DistrictSearch search = new DistrictSearch(getApplicationContext());
        DistrictSearchQuery query = new DistrictSearchQuery();
        query.setKeywords("江西省");
        query.setShowBoundary(true);
        search.setQuery(query);
        search.setOnDistrictSearchListener(this);
        search.searchDistrictAsyn();
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();

    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mapView != null)
            mapView.onDestroy();
    }

    private void initLocation() {
        if (ShipeiUtils.isLocationEnabled(context)) {
            myLocationStyle.myLocationIcon(BitmapDescriptorFactory.fromResource(R.drawable.gps_location));
            mAMap.setMyLocationStyle(myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE));
            mlocationClient = new AMapLocationClient(this);
            mLocationOption = new AMapLocationClientOption();
            //设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
            mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
            //设置定位间隔,单位毫秒,默认为2000ms
            mLocationOption.setInterval(1000);
            mLocationOption.setOnceLocation(true);
            mlocationClient.setLocationOption(mLocationOption);
            mlocationClient.startLocation();

            //设置定位监听
            mlocationClient.setLocationListener(new AMapLocationListener() {
                @Override
                public void onLocationChanged(AMapLocation aMapLocation) {
                    if (aMapLocation != null) {
                        if (aMapLocation.getErrorCode() == 0) {
                            //启用定位蓝点
                            mAMap.setMyLocationEnabled(true);
//                        city.setText(aMapLocation.getCity());
//                        town.setText(aMapLocation.getDistrict());
//                        province.setText(aMapLocation.getProvince());
                            x = aMapLocation.getLongitude();//获取经度
                            y = aMapLocation.getLatitude();//获取纬度
//                        LatLng latLng = new LatLng(y, x);
//                        addMarkersToMap(latLng);
                            getTestdata();
                            selectItem = true;
                            sousuo_tv.setText(aMapLocation.getAoiName());
//                        Log.i("location",aMapLocation.toString());
                            selectItem = false;
                        } else {

                            //显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
                            Log.e("AmapError", "location Error, ErrCode:"
                                    + aMapLocation.getErrorCode() + ", errInfo:"
                                    + aMapLocation.getErrorInfo());
                        }
                    }
                }
            });
        } else {
            //取消定位蓝点
            mAMap.setMyLocationEnabled(false);
            //移除之前的数据
            forecast_time.setText("                                   ");
            province.setText("");
            city.setText("");
            town.setText("");
            selectItem = true;
            sousuo_tv.setText("");
            selectItem = false;
            horizontalScrollView.removeAllViews();

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

    private void addMarkersToMap(LatLng point) {
        MarkerOptions markerOption = new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.drawable.location))
                .position(point)
                .draggable(false);
        marker = mAMap.addMarker(markerOption);
    }

    @Override
    public void onMapClick(LatLng point) {
//        LatLonPoint latLonPoint = new LatLonPoint(point.latitude, point.longitude);
//        getAddress(latLonPoint);
        myLocationStyle.myLocationIcon(BitmapDescriptorFactory.fromResource(R.drawable.gps_point));
        mAMap.setMyLocationStyle(myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATE));
        if (marker != null)
            marker.destroy();
        addMarkersToMap(point);
        x = point.longitude;
        y = point.latitude;
        clickMap = true;
        getTestdata();
    }


    /**
     * 响应逆地理编码
     */
    public void getAddress(final LatLonPoint latLonPoint) {
        RegeocodeQuery query = new RegeocodeQuery(latLonPoint, 200,
                GeocodeSearch.AMAP);// 第一个参数表示一个Latlng，第二参数表示范围多少米，第三个参数表示是火系坐标系还是GPS原生坐标系
        geocoderSearch.getFromLocationAsyn(query);// 设置异步逆地理编码请求
    }

    //江西省居中显示
    private void centerJiangxi() {
        LatLngBounds bounds = new LatLngBounds.Builder()
                .include(new LatLng(24.43704147338867, 118.68101043701172))
                .include(new LatLng(30.1299808502, 113.52340240478516)).build();
        mAMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 40));
    }

}
