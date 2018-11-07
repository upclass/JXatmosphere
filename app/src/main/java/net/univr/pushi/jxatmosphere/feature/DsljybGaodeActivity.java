package net.univr.pushi.jxatmosphere.feature;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
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
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.GroundOverlay;
import com.amap.api.maps.model.GroundOverlayOptions;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.LatLngBounds;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.PolylineOptions;
import com.amap.api.services.core.AMapException;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.district.DistrictItem;
import com.amap.api.services.district.DistrictResult;
import com.amap.api.services.district.DistrictSearch;
import com.amap.api.services.district.DistrictSearch.OnDistrictSearchListener;
import com.amap.api.services.district.DistrictSearchQuery;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeQuery;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.github.abel533.echarts.axis.CategoryAxis;
import com.github.abel533.echarts.axis.ValueAxis;
import com.github.abel533.echarts.code.Trigger;
import com.github.abel533.echarts.json.GsonOption;
import com.github.abel533.echarts.series.Bar;

import net.univr.pushi.jxatmosphere.R;
import net.univr.pushi.jxatmosphere.adapter.LdptWxAdapter;
import net.univr.pushi.jxatmosphere.base.BaseActivity;
import net.univr.pushi.jxatmosphere.beens.GkdmClickBeen;
import net.univr.pushi.jxatmosphere.remote.RetrofitHelper;
import net.univr.pushi.jxatmosphere.utils.PicUtils;
import net.univr.pushi.jxatmosphere.utils.ThreadUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class DsljybGaodeActivity extends BaseActivity implements
        OnDistrictSearchListener, AMap.OnMapClickListener,
        GeocodeSearch.OnGeocodeSearchListener {

    @BindView(R.id.webView)
    WebView mWebView;
    @BindView(R.id.scroll)
    HorizontalScrollView scrollView;
    @BindView(R.id.type)
    LinearLayout type;
    @BindView(R.id.now_time)
    TextView now_time;
    @BindView(R.id.sum_time)
    TextView sum_time;
    @BindView(R.id.city)
    TextView city;
    @BindView(R.id.town)
    TextView town;
    @BindView(R.id.didian)
    TextView didian;
    @BindView(R.id.forecast_time)
    TextView forecast_time;
    @BindView(R.id. forecast_time1)
    TextView forecast_time1;

    @BindView(R.id.back)
    ImageView back;

    @BindView(R.id.mapview)
    MapView mMapView;
    AMap mAMap;

    @BindView(R.id.pic_recycle)
    RecyclerView picRecycleview;

    Double x;
    Double y;

    private Object barData[];

    public AMapLocationClient mlocationClient;
    public AMapLocationClientOption mLocationOption;
    Marker marker;
    String select = "now";
    List<String> overLayerUrls;
    GroundOverlay groundOverlay;
    private GeocodeSearch geocoderSearch;

    @Override
    public int getLayoutId() {
        return R.layout.activity_dsljyb_gaode;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        super.initViews(savedInstanceState);
        mMapView.onCreate(savedInstanceState);
        mAMap = mMapView.getMap();
        getProvinceFanWei();
        initLocation();
        initView();
    }


    /**
     * 方法必须重写
     */
    @Override
    protected void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mMapView.onSaveInstanceState(outState);
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mMapView != null)
            mMapView.onDestroy();
    }


    private void getProvinceFanWei() {
        DistrictSearch search = new DistrictSearch(getApplicationContext());
        DistrictSearchQuery query = new DistrictSearchQuery();
        query.setKeywords("江西省");
        query.setShowBoundary(true);
        search.setQuery(query);
        search.setOnDistrictSearchListener(this);

        search.searchDistrictAsyn();
    }

    private void getDsljyb() {
        ProgressDialog progressDialog = ProgressDialog.show(context, "请稍等...", "获取数据中...", true);
        progressDialog.setCancelable(true);
        RetrofitHelper.getForecastWarn()
                .getRainGird(String.valueOf(x), String.valueOf(y))
                .compose(bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(dsljybBeen -> {
                    progressDialog.dismiss();
                    scrollView.setVisibility(View.VISIBLE);
                    List<String> data = dsljybBeen.getData();
                    barData = new Object[data.size()];
                    for (int i = 0; i < data.size(); i++) {
                        barData[i] = data.get(i);
                    }
                    String timeAfter8Hour = getTimeAddHour(dsljybBeen.getForecast_time(), 8);
                    String timeAfter10Hour = getTimeAddHour(dsljybBeen.getForecast_time(), 10);
                    forecast_time.setText("起报时间:" +timeAfter8Hour );
                    forecast_time1.setText("预报时段:" + getHour(timeAfter8Hour)+"-"+getHour(timeAfter10Hour));
                    mWebView.loadUrl("file:///android_asset/jsWeb/ZhuZhuangechart.html");

                    List<String> picList = dsljybBeen.getPicList();
                    overLayerUrls = picList;
                    if (picList == null) {
                        if (groundOverlay != null) groundOverlay.remove();
                    } else {
                        String pic = picList.get(0);
                        changeOverLayer(pic);
                    }
                }, throwable -> {
                    progressDialog.dismiss();
                    LogUtils.e(throwable);
                    ToastUtils.showShort(getString(R.string.getInfo_error_toast));
                });
    }

    private String getTimeAddHour(String forecast_time,int hour) {
        String ret = null;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        try {
            Date parse = format.parse(forecast_time);
            long l = parse.getTime() + 3600 * 1000 * hour;
            Date date = new Date(l);
            ret = format.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return ret;
    }

    private String getHour(String forecast_time) {
        String ret = null;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        try {
            Date parse = format.parse(forecast_time);
            String hours = String.valueOf(parse.getHours());
            String minutes = String.valueOf(parse.getMinutes());
            if(Integer.valueOf(hours)>=0&&Integer.valueOf(hours)<10)hours="0"+hours;
            if(Integer.valueOf(minutes)>=0&&Integer.valueOf(minutes)<10)hours="0"+minutes;
            ret = hours+":"+minutes;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return ret;
    }
    private void getDsljybSum() {
        ProgressDialog progressDialog = ProgressDialog.show(context, "请稍等...", "获取数据中...", true);
        progressDialog.setCancelable(true);
        RetrofitHelper.getForecastWarn()
                .getRainSumGird(String.valueOf(x), String.valueOf(y))
                .compose(bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(dsljybBeen -> {
                    progressDialog.dismiss();
                    scrollView.setVisibility(View.VISIBLE);
                    type.setVisibility(View.VISIBLE);
                    List<String> data = dsljybBeen.getData();
                    barData = new Object[data.size()];
                    for (int i = 0; i < data.size(); i++) {
                        barData[i] = data.get(i);
                    }
                    mWebView.loadUrl("file:///android_asset/jsWeb/ZhuZhuangechart.html");

                    List<String> picList = dsljybBeen.getPicList();
                    overLayerUrls = picList;
                    if (picList == null) {
                        if (groundOverlay != null) groundOverlay.remove();
                    } else {
                        String pic = picList.get(0);
                        changeOverLayer(pic);
                    }

                }, throwable -> {
                    progressDialog.dismiss();
                    LogUtils.e(throwable);
                    ToastUtils.showShort(getString(R.string.getInfo_error_toast));
                });
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

//        MyLocationStyle myLocationStyle;
//        myLocationStyle = new MyLocationStyle();//初始化定位蓝点样式类myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE);//连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动。（1秒1次定位）如果不设置myLocationType，默认也会执行此种模式。
//        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_SHOW);
//        mAMap.setMyLocationStyle(myLocationStyle);//设置定位蓝点的Style
//        mAMap.setMyLocationEnabled(true);// 设置为true表示启动显示定位蓝点，false表示隐藏定位蓝点并不进行定位，默认是false。

        //设置定位监听
        mlocationClient.setLocationListener(new AMapLocationListener() {
            @Override
            public void onLocationChanged(AMapLocation aMapLocation) {
                if (aMapLocation != null) {
                    if (aMapLocation.getErrorCode() == 0) {
                        city.setText(aMapLocation.getCity());
                        town.setText(aMapLocation.getDistrict());
                        didian.setText(aMapLocation.getAoiName());
                        x = aMapLocation.getLatitude();//获取纬度
                        y = aMapLocation.getLongitude();//获取经度
                        LatLng latLng = new LatLng(x, y);
                        addMarkersToMap(latLng);
                        getDsljyb();

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

    private void initView() {
        geocoderSearch = new GeocodeSearch(this);
        geocoderSearch.setOnGeocodeSearchListener(this);
        mAMap.setOnMapClickListener(this);
        UiSettings uiSettings = mAMap.getUiSettings();
        uiSettings.setZoomControlsEnabled(false);
        uiSettings.setRotateGesturesEnabled(false);
        overLayerUrls = new ArrayList<>();
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        now_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                now_time.setBackground(getResources().getDrawable(R.drawable.dsljyb_bg_select));
                now_time.setTextColor(getResources().getColor(R.color.black));
                sum_time.setBackground(getResources().getDrawable(R.drawable.dsljyb_bg1));
                sum_time.setTextColor(getResources().getColor(R.color.white));
                initRecycleView();
                getDsljyb();
                select = "now";
            }
        });
        sum_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                now_time.setBackground(getResources().getDrawable(R.drawable.dsljyb_bg));
                now_time.setTextColor(getResources().getColor(R.color.white));
                sum_time.setBackground(getResources().getDrawable(R.drawable.dsljyb_bg1_select));
                sum_time.setTextColor(getResources().getColor(R.color.black));
                getDsljybSum();
                initRecycleView();
                select = "sum";
            }
        });
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setSupportZoom(false);
        webSettings.setDisplayZoomControls(false);
        webSettings.setBlockNetworkLoads(false);
        webSettings.setAllowFileAccess(true);


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

        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        //设置webview背景色
        mWebView.setBackgroundColor(0);
        mWebView.addJavascriptInterface(new WebAppInterface(this), "Android");

        initRecycleView();

    }

    private void initRecycleView() {
        LinearLayoutManager manager = new LinearLayoutManager(context, RecyclerView.HORIZONTAL, false);
        List<GkdmClickBeen> data = new ArrayList<>();
        for (int i = 1; i <= 20; i++) {
            GkdmClickBeen clickBeen = new GkdmClickBeen();
            clickBeen.setText(i * 6 + "");
            if (i == 1) clickBeen.setOnclick(true);
            else clickBeen.setOnclick(false);
            data.add(clickBeen);
        }
        LdptWxAdapter adapter = new LdptWxAdapter(data);
        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if (overLayerUrls != null) {
                    int size = overLayerUrls.size();
                    if (position <= size) {
                        LdptWxAdapter adapter1 = (LdptWxAdapter) adapter;
                        List<GkdmClickBeen> list = adapter1.getData();
                        GkdmClickBeen clickBeenLast = list.get(adapter1.lastClickPosition);
                        clickBeenLast.setOnclick(false);
                        GkdmClickBeen clickBeen = list.get(position);
                        clickBeen.setOnclick(true);
                        adapter.notifyItemChanged(position);
                        adapter.notifyItemChanged(adapter1.lastClickPosition);
                        adapter1.lastClickPosition = position;
                        String pic = overLayerUrls.get(position);
                        changeOverLayer(pic);
                    } else {
                        ToastUtils.showShort("这个图片还没生成");
                    }
                } else {
                    ToastUtils.showShort("这个图片还没生成");
                }


            }
        });
        picRecycleview.setLayoutManager(manager);
        picRecycleview.setAdapter(adapter);

    }

    private void changeOverLayer(String pic) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                PicUtils.decodeUriAsBitmapFromNet(pic, "dsljyb");
                Bundle bundle = new Bundle();
                bundle.putString("pic", pic);
                Message message = uiHandler.obtainMessage();
                message.setData(bundle);
                message.what = 1;
                uiHandler.sendMessage(message);
            }
        }).start();
    }


    @Override
    public void onMapClick(LatLng point) {
        LatLonPoint latLonPoint = new LatLonPoint(point.latitude, point.longitude);
        getAddress(latLonPoint);
        if (marker != null)
            marker.destroy();
        addMarkersToMap(point);
        x = point.latitude;
        y = point.longitude;
        if (select.equals("now")) getDsljyb();
        else if (select.equals("sum")) getDsljybSum();
    }

    @Override
    public void onRegeocodeSearched(RegeocodeResult result, int rCode) {
        if (rCode == AMapException.CODE_AMAP_SUCCESS) {
            if (result != null && result.getRegeocodeAddress() != null
                    && result.getRegeocodeAddress().getFormatAddress() != null) {
                city.setText(result.getRegeocodeAddress().getCity());
                town.setText(result.getRegeocodeAddress().getDistrict());
                didian.setText(result.getRegeocodeAddress().getTownship());
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
        public String getZhuZhuangChartOptions() {
            GsonOption option = markZhuZhuangChartOptions();
            return option.toString();
        }


        @JavascriptInterface
        public GsonOption markZhuZhuangChartOptions() {
            GsonOption option = new GsonOption();
            //设置位置左上右下边距
            option.grid().x(46);
            option.grid().y(30);
//            option.legend().x(X.left);
            option.calculable(false);
//            option.tooltip().trigger(Trigger.item).formatter("{a} <br/>{b} : {c}");
            option.tooltip().trigger(Trigger.item).formatter("{b}<br/>  降水量(mm): {c}");
            option.xAxis(new CategoryAxis().data("6", "12", "18", "24", "30", "36", "42", "48", "54", "60", "66", "72", "78", "84", "90", "96", "102", "108", "114", "120"));
            option.yAxis(new ValueAxis());
            Bar bar = new Bar();
            bar.data(barData);
            option.series(bar);
            return option;
        }
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
//                mAMap.moveCamera(
//                        CameraUpdateFactory.newLatLngZoom(new LatLng(centerLatLng.getLatitude(), centerLatLng.getLongitude()), 6));
                LatLngBounds bounds = new LatLngBounds.Builder()
                        .include(new LatLng(24.43704147338867, 118.68101043701172))
                        .include(new LatLng(30.1299808502, 113.52340240478516)).build();
                mAMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 10));
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


    /**
     * 在地图上添加marker
     */
    private void addMarkersToMap(LatLng point) {
        MarkerOptions markerOption = new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.drawable.location))
                .position(point)
                .draggable(false);
        marker = mAMap.addMarker(markerOption);
    }


    private Handler uiHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {
                if (groundOverlay != null) groundOverlay.remove();
                //高德在加入覆盖物范围
                LatLngBounds bounds = new LatLngBounds.Builder()
                        .include(new LatLng(24.2270355, 118.661049))
                        .include(new LatLng(30.1999836, 113.323349)).build();
                Bundle data = msg.getData();
                String pic = data.getString("pic");
                Bitmap bitmap = PicUtils.readLocalImage(pic, "dsljyb", context);

                groundOverlay = mAMap.addGroundOverlay(new GroundOverlayOptions()
                        .anchor(0.5f, 0.5f)
                        .transparency(0.3f)
//				.zIndex(GlobalConstants.ZindexLine - 1)
                        .image(BitmapDescriptorFactory
                                .fromBitmap(bitmap))
                        .positionFromBounds(bounds));
            }
        }
    };


    /**
     * 响应逆地理编码
     */
    public void getAddress(final LatLonPoint latLonPoint) {
        RegeocodeQuery query = new RegeocodeQuery(latLonPoint, 200,
                GeocodeSearch.AMAP);// 第一个参数表示一个Latlng，第二参数表示范围多少米，第三个参数表示是火系坐标系还是GPS原生坐标系
        geocoderSearch.getFromLocationAsyn(query);// 设置异步逆地理编码请求
    }


}

