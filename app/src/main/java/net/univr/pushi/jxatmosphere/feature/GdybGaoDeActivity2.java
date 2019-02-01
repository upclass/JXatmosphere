package net.univr.pushi.jxatmosphere.feature;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.Projection;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.CameraPosition;
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
import net.univr.pushi.jxatmosphere.adapter.CeilingAdapter;
import net.univr.pushi.jxatmosphere.adapter.SimpleAdapter;
import net.univr.pushi.jxatmosphere.base.BaseActivity;
import net.univr.pushi.jxatmosphere.beens.GdybBeen1;
import net.univr.pushi.jxatmosphere.beens.GdybKeyBeen;
import net.univr.pushi.jxatmosphere.beens.GdybSearchBeen;
import net.univr.pushi.jxatmosphere.remote.RetrofitHelper;
import net.univr.pushi.jxatmosphere.utils.ScreenUtil;
import net.univr.pushi.jxatmosphere.utils.ShipeiUtils;
import net.univr.pushi.jxatmosphere.utils.ThreadUtil;
import net.univr.pushi.jxatmosphere.utils.ViewUtil;
import net.univr.pushi.jxatmosphere.widget.PowerGroupListener;
import net.univr.pushi.jxatmosphere.widget.SectionDecoration;

import java.math.BigDecimal;
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

public class GdybGaoDeActivity2 extends BaseActivity implements View.OnClickListener, DistrictSearch.OnDistrictSearchListener, AMap.OnMapClickListener, GeocodeSearch.OnGeocodeSearchListener {


    ProgressDialog dialog = null;
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
    @BindView(R.id.search)
    LinearLayout search;
    public AMapLocationClient mlocationClient;
    public AMapLocationClientOption mLocationOption;
    private GeocodeSearch geocoderSearch;
    private MyLocationStyle myLocationStyle;

    String interval = "1";
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


    @BindView(R.id.weizhi_relative)
    RelativeLayout weizhi_relative;
    @BindView(R.id.content)
    LinearLayout content;
    @BindView(R.id.now_position)
    ImageView nowPosition;

    private int mMarkerX, mMarderY;
    @BindView(R.id.tq_recycleView)
    RecyclerView tq_recyclerView;
    //    private Map<Integer,String> keys=new HashMap<>();//存放所有标题的位置和内容
    private List<GdybKeyBeen> keyList = new ArrayList<>();
    private List<GdybBeen1.DataBean.ContentBean> data = new ArrayList<>();//天气数据源
    private List<String> groupList = new ArrayList<>();
    String tempRiqi = "";
    CeilingAdapter mAdapter;


    @Override
    public int getLayoutId() {
        return R.layout.activity_gdyb_gao_de2;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        super.initViews(savedInstanceState);
        initView(savedInstanceState);
        getJiangxiFanwei();
        initLocation();
        back.setOnClickListener(this);
        delete.setOnClickListener(this);
        search_info.setOnClickListener(this);
        nowPosition.setOnClickListener(this);

    }


    //手动加定位图标
//                            MarkerOptions markerOption = new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.drawable.location))
//                                    .position(latLng)
//                                    .draggable(false);
//                            mAMap.addMarker(markerOption);

    private void initView(Bundle savedInstanceState) {
        search.getBackground().setAlpha(250);
        weizhi_relative.getBackground().setAlpha(250);
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


    public CeilingAdapter getGdybAdapter() {
        if (mAdapter == null) {
            mAdapter = new CeilingAdapter(context, data);
            //设置布局管理器
            LinearLayoutManager layoutManager = new LinearLayoutManager(this);
            layoutManager.setOrientation(1);
            tq_recyclerView.setLayoutManager(layoutManager);
            tq_recyclerView.setHasFixedSize(true);
            tq_recyclerView.setAdapter(mAdapter);
            initDecoration();
        }
        return mAdapter;
    }


    private void initDecoration() {
        SectionDecoration decoration = SectionDecoration.Builder
                .init(new PowerGroupListener() {
                    @Override
                    public String getGroupName(int position) {
                        //获取组名，用于判断是否是同一组
                        if (keyList.size() > position) {
                            return keyList.get(position).getDate();
                        }
                        return null;
                    }

                    @Override
                    public View getGroupView(int position) {
                        //获取自定定义的组View
                        if (keyList.size() > position) {
                            View view = getLayoutInflater().inflate(R.layout.item_group, null, false);
                            ((TextView) view.findViewById(R.id.weekDay)).setText(keyList.get(position).getWeekDay());
                            ((TextView) view.findViewById(R.id.date)).setText(keyList.get(position).getDate());

                            String tqxx = keyList.get(position).getWeathDesc();
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
                            ((ImageView) view.findViewById(R.id.weathDesc_pic)).setImageResource(getResource(imageName));
                            Double aDouble = Double.valueOf(keyList.get(position).getTemper());
                            BigDecimal bd = new BigDecimal(aDouble).setScale(0, BigDecimal.ROUND_HALF_UP);
                            String tempInt = String.valueOf(Integer.parseInt(bd.toString())) ;
                            ((TextView) view.findViewById(R.id.weathDesc)).setText(keyList.get(position).getWeathDesc() +  tempInt+ "度");
                            ((TextView) view.findViewById(R.id.rain)).setText(keyList.get(position).getRainfall());
                            ((TextView) view.findViewById(R.id.xdsd)).setText(keyList.get(position).getXdsd());
                            ((TextView) view.findViewById(R.id.wind)).setText(keyList.get(position).getWind());
                            return view;
                        } else {
                            return null;
                        }
                    }
                })
                //设置高度
                .setGroupHeight(ScreenUtil.dip2px(this, 60))
                .build();
        tq_recyclerView.addItemDecoration(decoration);
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
                .getGdyb1(String.valueOf(x), String.valueOf(y), "bd09ll", initForecastTime())
                .compose(bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(gdybBeen1 -> {
                    dialog.dismiss();
                    data.clear();
                    keyList.clear();
                    province.setText(gdybBeen1.getLocation().getProvince());
                    city.setText(gdybBeen1.getLocation().getCity());
                    town.setText(gdybBeen1.getLocation().getDistrict());
                    GdybBeen1.DataBean retData = gdybBeen1.getData();
                    List<GdybBeen1.DataBean.ContentBean> oneH = retData.getOneH();
                    List<GdybBeen1.DataBean.ContentBean> threeH = retData.getThreeH();
                    List<GdybBeen1.DataBean.ContentBean> twelveH = retData.getTwelveH();
                    List<GdybBeen1.DataBean.ContentBean> twenty_fourH = retData.getTwenty_fourH();
                    initCellData(oneH, threeH, twelveH, twenty_fourH);
                    setPullAction();
                    getGdybAdapter().notifyDataSetChanged();

                    //点击地图位置设置在搜索框，切不触发文字监听
                    if (clickMap) {
                        LatLonPoint latLonPoint = new LatLonPoint(y, x);
                        getAddress(latLonPoint);
                    }
                    clickMap = false;
                }, throwable -> {
                    dialog.dismiss();
                    LogUtils.e(throwable);
                    ToastUtils.showShort(getString(R.string.getInfo_error_toast));
                });
    }

    //设置keylist数据源
    private void setPullAction() {
        String initStr = "";
        GdybKeyBeen gdybKeyBeen = null;
        if (data != null && data.size() > 0) {
            for (int i = 0; i < data.size(); i++) {
                String forecastTime = data.get(i).getForecastTime();
                SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                Calendar calendar = Calendar.getInstance();
                try {
                    Date parse = format.parse(forecastTime);
                    calendar.setTime(parse);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                int month = calendar.get(Calendar.MONTH) + 1;
                String monthDayStr;
                if (month < 10)
                    monthDayStr = "0" + month + "-" + calendar.get(Calendar.DAY_OF_MONTH);
                else monthDayStr = month + "-" + calendar.get(Calendar.DAY_OF_MONTH);
                if (initStr.equals("")) {
                    gdybKeyBeen = new GdybKeyBeen();
                    gdybKeyBeen.setDate(monthDayStr);
                    gdybKeyBeen.setRainfall(data.get(i).getRain());
                    gdybKeyBeen.setTemper(data.get(i).getTemper());
                    gdybKeyBeen.setWeathDesc(data.get(i).getWeatherDesc());
                    gdybKeyBeen.setWeekDay(getNowWeek(forecastTime));
                    gdybKeyBeen.setWind(data.get(i).getWindDir());
                    gdybKeyBeen.setXdsd(data.get(i).getHumidity());
                    keyList.add(gdybKeyBeen);
                    initStr = monthDayStr;
                } else {
                    if (initStr.equals(monthDayStr)) {
                        keyList.add(gdybKeyBeen);
                    } else {
                        gdybKeyBeen = new GdybKeyBeen();
                        gdybKeyBeen.setDate(monthDayStr);
                        gdybKeyBeen.setRainfall(data.get(i).getRain());
                        gdybKeyBeen.setTemper(data.get(i).getTemper());
                        gdybKeyBeen.setWeathDesc(data.get(i).getWeatherDesc());
                        gdybKeyBeen.setWeekDay(getNowWeek(forecastTime));
                        gdybKeyBeen.setWind(data.get(i).getWindDir());
                        gdybKeyBeen.setXdsd(data.get(i).getHumidity());
                        keyList.add(gdybKeyBeen);
                        initStr = monthDayStr;
                    }
                }
            }
        }
    }

    private void initCellData(List<GdybBeen1.DataBean.ContentBean> oneH, List<GdybBeen1.DataBean.ContentBean> threeH, List<GdybBeen1.DataBean.ContentBean> twelveH, List<GdybBeen1.DataBean.ContentBean> twenty_fourH) {
        for (int j = 0; j < oneH.size(); j++) {
            data.add(oneH.get(j));
        }
        for (int j = 0; j < threeH.size(); j++) {
            data.add(threeH.get(j));
        }
        for (int j = 0; j < twelveH.size(); j++) {
            data.add(twelveH.get(j));
        }
        for (int j = 0; j < twenty_fourH.size(); j++) {
            data.add(twenty_fourH.get(j));
        }
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
                ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(GdybGaoDeActivity2.this.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
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
            myLocationStyle.myLocationIcon(BitmapDescriptorFactory.fromResource(R.drawable.gps_point));
            mAMap.setMyLocationStyle(myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_SHOW));
            //启用定位蓝点
            mAMap.setMyLocationEnabled(true);
            mlocationClient = new AMapLocationClient(this);
            mLocationOption = new AMapLocationClientOption();
            //设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
            mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
            //设置定位间隔,单位毫秒,默认为2000ms
//            mLocationOption.setInterval(1000);
            mLocationOption.setOnceLocation(true);
            mlocationClient.setLocationOption(mLocationOption);
            mlocationClient.startLocation();

            //设置定位监听
            mlocationClient.setLocationListener(new AMapLocationListener() {
                @Override
                public void onLocationChanged(AMapLocation aMapLocation) {

                    if (aMapLocation != null) {
                        if (aMapLocation.getErrorCode() == 0) {
                            x = aMapLocation.getLongitude();//获取经度
                            y = aMapLocation.getLatitude();//获取纬度
                            LatLng latLng = new LatLng(y, x);
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
//            mAMap.setMyLocationEnabled(false);
            //移除之前的数据
            province.setText("");
            city.setText("");
            town.setText("");
            selectItem = true;
            sousuo_tv.setText("");
            selectItem = false;

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
//        myLocationStyle.myLocationIcon(BitmapDescriptorFactory.fromResource(R.drawable.gps_point));
//        mAMap.setMyLocationStyle(myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATE));
        if (marker != null)
            marker.destroy();
        addMarkersToMap(point);
        x = point.longitude;
        y = point.latitude;
        clickMap = true;


        mAMap.moveCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition(point, 7, 0, 0)));
        Projection projection = mAMap.getProjection();
        Point center = projection.toScreenLocation(point);
        //当前(屏幕上的点 )
        mMarkerX = center.x;
        mMarderY = center.y;
        int height = ShipeiUtils.getHeight(context) / 5;
        mMarderY = mMarderY + height;
        Point pointa = new Point(mMarkerX, mMarderY);
        LatLng latLng = projection.fromScreenLocation(pointa);
        mAMap.moveCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition(latLng, 7, 0, 0)));

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

    public String getNowWeek(String forecastTime) {
        String ret = "";
        if (isCurrentDay(forecastTime)) return "当天";
        else {
            SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Calendar calendar = Calendar.getInstance();
            try {
                Date parse = format.parse(forecastTime);
                calendar.setTime(parse);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            int i = calendar.get(Calendar.DAY_OF_WEEK);

            switch (i) {
                case 1:
                    ret = "星期天";
                    break;
                case 2:
                    ret = "星期一";
                    break;
                case 3:
                    ret = "星期二";
                    break;
                case 4:
                    ret = "星期三";
                    break;
                case 5:
                    ret = "星期四";
                    break;
                case 6:
                    ret = "星期五";
                    break;
                case 7:
                    ret = "星期六";
                    break;
            }
            return ret;
        }
    }

    private Boolean isCurrentDay(String forecastTime) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Calendar calendar = Calendar.getInstance();
        try {
            Date parse = format.parse(forecastTime);
            calendar.setTime(parse);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        calendar.setTime(new Date());
        int year1 = calendar.get(Calendar.YEAR);
        int month1 = calendar.get(Calendar.MONTH) + 1;
        int day1 = calendar.get(Calendar.DAY_OF_MONTH);
        if (year == year1 && month == month1 && day == day1) return true;
        else return false;
    }
}
