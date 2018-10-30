package net.univr.pushi.jxatmosphere.feature;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
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
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.LatLngBounds;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
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
import com.github.abel533.echarts.axis.AxisLabel;
import com.github.abel533.echarts.axis.CategoryAxis;
import com.github.abel533.echarts.axis.SplitLine;
import com.github.abel533.echarts.axis.ValueAxis;
import com.github.abel533.echarts.code.Trigger;
import com.github.abel533.echarts.json.GsonOption;
import com.github.abel533.echarts.series.Line;

import net.univr.pushi.jxatmosphere.R;
import net.univr.pushi.jxatmosphere.adapter.PiaoGeAdapter;
import net.univr.pushi.jxatmosphere.adapter.SimpleAdapter;
import net.univr.pushi.jxatmosphere.base.BaseActivity;
import net.univr.pushi.jxatmosphere.beens.GdybBeen;
import net.univr.pushi.jxatmosphere.beens.GdybSearchBeen;
import net.univr.pushi.jxatmosphere.remote.RetrofitHelper;
import net.univr.pushi.jxatmosphere.utils.ThreadUtil;
import net.univr.pushi.jxatmosphere.utils.ViewUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class GdybGaoDeActivity extends BaseActivity implements View.OnClickListener, DistrictSearch.OnDistrictSearchListener, AMap.OnMapClickListener, GeocodeSearch.OnGeocodeSearchListener {

    @BindView(R.id.webView)
    WebView mWebView;
    @BindView(R.id.tuxin_tv)
    TextView tuxin_tv;
    @BindView(R.id.biaoge_tv)
    TextView biaoge_tv;
    @BindView(R.id.scroll)
    HorizontalScrollView scrollView;
    @BindView(R.id.recycle)
    RecyclerView recyclerView;
    PiaoGeAdapter adapter;
    ProgressDialog dialog = null;
    @BindView(R.id.start_forecast_time)
    TextView start_forecast_time;
    @BindView(R.id.one_hour)
    TextView one_hour;
    @BindView(R.id.three_hour)
    TextView three_hour;
    @BindView(R.id.six_hour)
    TextView six_hour;
    @BindView(R.id.twelve_hour)
    TextView twelve_hour;
    @BindView(R.id.gd_dizhi)
    EditText sousuo_tv;
    @BindView(R.id.search_info)
    TextView search_info;
    @BindView(R.id.twentyfour_hour)
    TextView twentyfour_hour;
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
    @BindView(R.id.weizhi_relative)
    RelativeLayout weizhiRelative;
    @BindView(R.id.content)
    LinearLayout content;
    @BindView(R.id.delete)
    ImageView delete;
    public AMapLocationClient mlocationClient;
    public AMapLocationClientOption mLocationOption;
    private GeocodeSearch geocoderSearch;

    String interval = "1";
    private Object times[];
    private Object temps[];
    private Object rains[];
    private Object winds[];
    private Object humiditys[];
    private List<GdybBeen.DataBean> data = new ArrayList<>();
    Double x;
    Double y;
    int i = 0;

    @BindView(R.id.recyclerView_search)
    RecyclerView recyclerView_search;
    SimpleAdapter simpleAdapter;

    AMap mAMap;
    Marker marker;
    Boolean selectItem=false;

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
        tuxin_tv.setOnClickListener(this);
        biaoge_tv.setOnClickListener(this);
        one_hour.setOnClickListener(this);
        three_hour.setOnClickListener(this);
        six_hour.setOnClickListener(this);
        twelve_hour.setOnClickListener(this);
        twentyfour_hour.setOnClickListener(this);
        back.setOnClickListener(this);
        delete.setOnClickListener(this);
        search_info.setOnClickListener(this);

    }


    private void initView(Bundle savedInstanceState) {
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        mapView.onCreate(savedInstanceState);
        mAMap = mapView.getMap();
        mAMap.getUiSettings().setRotateGesturesEnabled(false);
        mAMap.getUiSettings().setZoomControlsEnabled(false);
        mAMap.setOnMapClickListener(this);
        geocoderSearch = new GeocodeSearch(this);
        geocoderSearch.setOnGeocodeSearchListener(this);
//        mWebView.getSettings().setBuiltInZoomControls(false);
//        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
//        webSettings.setSupportZoom(false);
//        webSettings.setDisplayZoomControls(false);
//        webSettings.setBlockNetworkLoads(false);
//        webSettings.setAllowFileAccess(true);

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int mDensity = metrics.densityDpi;
        if (mDensity == 120) {
            webSettings.setDefaultZoom(WebSettings.ZoomDensity.CLOSE);
        } else if (mDensity == 160) {
            webSettings.setDefaultZoom(WebSettings.ZoomDensity.MEDIUM);
        } else if (mDensity == 240) {
            webSettings.setDefaultZoom(WebSettings.ZoomDensity.FAR);
        }
        //自适应屏幕
//        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
//        webSettings.setUseWideViewPort(true);
//        webSettings.setLoadWithOverviewMode(true);
//        mWebView.setInitialScale(200);
        mWebView.setVerticalScrollBarEnabled(false);//允许垂直滚动
        mWebView.addJavascriptInterface(new GdybGaoDeActivity.WebAppInterface(this), "Android");
//        mWebView.setHorizontalScrollBarEnabled(false);//禁止水平滚动

        sousuo_tv.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!selectItem){
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
        weizhiRelative.setVisibility(View.VISIBLE);
        content.setVisibility(View.VISIBLE);
        dialog = ProgressDialog.show(context, "请稍等...", "获取数据中...", true);
        dialog.setCancelable(true);
        start_forecast_time.setText(initForecastTime());
        RetrofitHelper.getWeatherMonitorAPI()
//                .getGdyb("114.25781250000001", "27.800209937418252", interval, "bd09ll", initForecastTime())
                .getGdyb(String.valueOf(x), String.valueOf(y), interval, "bd09ll", initForecastTime())
                .compose(bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(gdybBeen -> {
                    data = gdybBeen.getData();
                    province.setText(gdybBeen.getLocation().getProvince());
                    city.setText(gdybBeen.getLocation().getCity());
                    town.setText(gdybBeen.getLocation().getDistrict());
//                    if(isClickToLocation){
//                        sousuo_tv.setText(gdybBeen.getLocation().getDistrict());
//                        sousuo_tv.setSelection(sousuo_tv.getText().toString().length());
//                    }

//                    isClickToLocation=true;
                    getadapter().setNewData(data);
                    times = new Object[data.size()];
                    temps = new Object[data.size()];
                    rains = new Object[data.size()];
                    winds = new Object[data.size()];
                    humiditys = new Object[data.size()];
                    for (int i = 0; i < data.size(); i++) {
                        GdybBeen.DataBean dataBean = data.get(i);
                        String forecastTime = dataBean.getForecastTime();
                        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                        try {
                            Date date = format.parse(forecastTime);
                            String shijian = date.getDate() + "日" + date.getHours() + "时";
                            times[i] = shijian;
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        temps[i] = dataBean.getTemper();
                        rains[i] = dataBean.getRain();
                        winds[i] = dataBean.getWindSpeed();
                        humiditys[i] = dataBean.getHumidity();
                    }
                    if (scrollView.getVisibility() == View.VISIBLE) {
                        int width = 250 * (data.size());
                        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(width, LinearLayout.LayoutParams.MATCH_PARENT);
                        mWebView.setLayoutParams(lp);
                    }
                    mWebView.loadUrl("file:///android_asset/jsWeb/echart.html");
                    dialog.dismiss();
                }, throwable -> {
                    dialog.dismiss();
                    LogUtils.e(throwable);
                    ToastUtils.showShort(getString(R.string.getInfo_error_toast));
                });

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
            case R.id.tuxin_tv:
                scrollView.setVisibility(View.VISIBLE);
                tuxin_tv.setBackground(getResources().getDrawable(R.drawable.gd_text_bg1_select));
                tuxin_tv.setTextColor(getResources().getColor(R.color.white));
                biaoge_tv.setBackground(getResources().getDrawable(R.drawable.gd_text_bg3));
                biaoge_tv.setTextColor(getResources().getColor(R.color.toolbar_color));
                break;
            case R.id.biaoge_tv:
                scrollView.setVisibility(View.GONE);
                tuxin_tv.setBackground(getResources().getDrawable(R.drawable.gd_text_bg1));
                tuxin_tv.setTextColor(getResources().getColor(R.color.toolbar_color));
                biaoge_tv.setBackground(getResources().getDrawable(R.drawable.gd_text_bg3_select));
                biaoge_tv.setTextColor(getResources().getColor(R.color.white));
                break;
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
                weizhiRelative.setVisibility(View.GONE);
                content.setVisibility(View.GONE);
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
                    selectItem=true;
                    GdybSearchBeen gdybSearchBeen = (GdybSearchBeen) adapter.getData().get(position);
                    sousuo_tv.setText(gdybSearchBeen.getTitle());
                    recyclerView_search.setVisibility(View.GONE);
                    ViewUtil.hide_keyboard_from(context, sousuo_tv);
                    sousuo_tv.clearFocus();
                    Double lat = gdybSearchBeen.getLat();
                    Double lon = gdybSearchBeen.getLon();
                    x = lon;
                    y = lat;
                    if(marker!=null)marker.destroy();
                    addMarkersToMap(new LatLng(y,x));
                    getAddress(new LatLonPoint(y,x));
                    getTestdata();
                    selectItem=false;
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
                city.setText(result.getRegeocodeAddress().getCity());
                town.setText(result.getRegeocodeAddress().getDistrict());
                province.setText(result.getRegeocodeAddress().getTownship());
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


    /**
     * 注入到JS里的对象接口
     */
    class WebAppInterface {
        Context mContext;

        public WebAppInterface(Context c) {
            mContext = c;
        }

        /**
         * 获取
         *
         * @return
         */
        @JavascriptInterface
        public String getLineChartOptions() {
            GsonOption option = markLineChartOptions();
            return option.toString();
        }


        @JavascriptInterface
        public GsonOption markLineChartOptions() {
            GsonOption option = new GsonOption();
            option.calculable(false);
            option.tooltip().trigger(Trigger.axis).formatter("气温:{c}℃ <br/> 降雨量:{c1}cm <br/>风速:{c2}m/s<br/>相对湿度:{c3}%");
            //设置位置左上右下边距
            option.grid().x(34);
            option.grid().y(20);
            ValueAxis valueAxis = new ValueAxis();
            valueAxis.setSplitNumber(4);
            option.yAxis(valueAxis);


            CategoryAxis categoryAxis = new CategoryAxis();
            categoryAxis.axisLine().onZero(false);
            categoryAxis.boundaryGap(false);

            //每个数据点都绘制
            AxisLabel axisLabel = categoryAxis.axisLabel();
            axisLabel.setInterval(0);
            categoryAxis.data(times);
            SplitLine splitLine1 = categoryAxis.splitLine();
            //分个坐标轴的线是否有
            splitLine1.show(false);
            option.xAxis(categoryAxis);
            Line line1 = new Line();
            line1.smooth(true).data(temps).itemStyle().normal().lineStyle().shadowColor("rgba(6,7,2,9.4)");
            Line line2 = new Line();
            line2.smooth(true).data(rains).itemStyle().normal().lineStyle().shadowColor("rgba(3,2,2,2.7)");
            Line line3 = new Line();
            line3.smooth(true).data(winds).itemStyle().normal().lineStyle().shadowColor("rgba(4,3,8,2.7)");
            Line line4 = new Line();
            line4.smooth(true).data(humiditys).itemStyle().normal().lineStyle().shadowColor("rgba(5,1,2,4.7)");
            option.series(line1, line2, line3, line4);
            return option;
        }
    }

    PiaoGeAdapter getadapter() {
        if (adapter == null) {
            LinearLayoutManager manager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
            recyclerView.setLayoutManager(manager);
            adapter = new PiaoGeAdapter(data);
            LayoutInflater inflater = getLayoutInflater();
            View inflate = inflater.inflate(R.layout.gdyb_recycle_header, (ViewGroup) recyclerView.getParent(), false);
            adapter.addHeaderView(inflate, 0, LinearLayout.HORIZONTAL);
            recyclerView.setAdapter(adapter);
        }
        return adapter;
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
                        city.setText(aMapLocation.getCity());
                        town.setText(aMapLocation.getDistrict());
                        province.setText(aMapLocation.getProvince());
                        x =aMapLocation.getLongitude() ;//获取经度
                        y = aMapLocation.getLatitude();//获取纬度
                        LatLng latLng = new LatLng(y, x);
                        addMarkersToMap(latLng);
                        getTestdata();

                    } else {
                        //显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
                        Log.e("AmapError", "location Error, ErrCode:"
                                + aMapLocation.getErrorCode() + ", errInfo:"
                                + aMapLocation.getErrorInfo());
                    }
                }
            }
        });

    }

    private void addMarkersToMap(LatLng point) {
        MarkerOptions markerOption = new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.drawable.location))
                .position(point)
                .draggable(false);
        marker = mAMap.addMarker(markerOption);
    }

    @Override
    public void onMapClick(LatLng point) {
        LatLonPoint latLonPoint = new LatLonPoint(point.latitude, point.longitude);
        getAddress(latLonPoint);
        if (marker != null)
            marker.destroy();
        addMarkersToMap(point);
        x = point.longitude;
        y = point.latitude;
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
    private void centerJiangxi(){
        LatLngBounds bounds = new LatLngBounds.Builder()
                .include(new LatLng(24.43704147338867, 118.68101043701172))
                .include(new LatLng(30.1299808502, 113.52340240478516)).build();
        mAMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 40));
    }

}
