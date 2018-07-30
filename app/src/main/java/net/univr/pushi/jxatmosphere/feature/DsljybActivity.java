package net.univr.pushi.jxatmosphere.feature;

import android.app.ProgressDialog;
import android.content.Context;
import android.location.LocationManager;
import android.os.Bundle;
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
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.esri.arcgisruntime.ArcGISRuntimeEnvironment;
import com.esri.arcgisruntime.geometry.Point;
import com.esri.arcgisruntime.layers.ArcGISMapImageLayer;
import com.esri.arcgisruntime.layers.WebTiledLayer;
import com.esri.arcgisruntime.loadable.LoadStatus;
import com.esri.arcgisruntime.mapping.ArcGISMap;
import com.esri.arcgisruntime.mapping.Basemap;
import com.esri.arcgisruntime.mapping.view.LocationDisplay;
import com.esri.arcgisruntime.mapping.view.MapView;
import com.github.abel533.echarts.axis.CategoryAxis;
import com.github.abel533.echarts.axis.ValueAxis;
import com.github.abel533.echarts.code.Trigger;
import com.github.abel533.echarts.json.GsonOption;
import com.github.abel533.echarts.series.Bar;

import net.univr.pushi.jxatmosphere.R;
import net.univr.pushi.jxatmosphere.base.BaseActivity;
import net.univr.pushi.jxatmosphere.interfaces.MapCall;
import net.univr.pushi.jxatmosphere.remote.RetrofitHelper;
import net.univr.pushi.jxatmosphere.widget.MapScreenClickListen;
import net.univr.pushi.jxatmosphere.widget.TianDiTuMethodsClass;

import java.util.List;

import butterknife.BindView;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class DsljybActivity extends BaseActivity implements MapCall {

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
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.mapview)
    MapView mapView;
    Basemap slbasemap;
    ArcGISMapImageLayer gridLayer;

    Double x;
    Double y;

    private Object barData[];

    public AMapLocationClient mlocationClient;
    public AMapLocationClientOption mLocationOption;
    //    String type="0";
    String select = "now";


    @Override
    public int getLayoutId() {
        return R.layout.activity_dsljyb;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        super.initViews(savedInstanceState);
        initArcgis();
        initLocation();
        initView();
    }

    private void initArcgis() {
        initBaseMap();//初始化底图
        initGps();//初始化gps
        initArcgisUtils();
        startMapLinstenter();
    }

    private void startMapLinstenter() {
        MapScreenClickListen mapScreenClickListen = new MapScreenClickListen(context, mapView, this);
        mapView.setOnTouchListener(mapScreenClickListen);
    }

    private void initBaseMap() {
        try {
            if (slbasemap == null) {
                slbasemap = new Basemap();
                WebTiledLayer webTiledLayer = TianDiTuMethodsClass.CreateTianDiTuTiledLayer
                        (TianDiTuMethodsClass.LayerType.TIANDITU_VECTOR_2000);
                slbasemap.getBaseLayers().add(webTiledLayer);
                slbasemap.getBaseLayers().add(getGridLayer());
                if (mapView.getMap() == null) {
                    ArcGISMap arcGISMap = new ArcGISMap();
                    mapView.setMap(arcGISMap);
                }
                if (mapView.getMap().getBasemap() != slbasemap) {
                    mapView.getMap().setBasemap(slbasemap);
                }
            }
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }


    private void initGps() {
        LocationManager locationManager = (LocationManager) (context).getSystemService(Context
                .LOCATION_SERVICE);
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) &&
                locationManager.isProviderEnabled(locationManager.NETWORK_PROVIDER)) {
            initArcgisLocation();
            return;
        }
    }

    private void initArcgisUtils() {
        ArcGISRuntimeEnvironment.setLicense("runtimelite,1000,rud8065403504,none," +
                "RP5X0H4AH7CLJ9HSX018");
        mapView.setAttributionTextVisible(false);
    }

    private void initArcgisLocation() {
        final LocationDisplay locationDisplay = mapView.getLocationDisplay();
        locationDisplay.setAutoPanMode(LocationDisplay.AutoPanMode.NAVIGATION);
//        设置定位点在屏幕百分比
        locationDisplay.setNavigationPointHeightFactor(0.5f);
        //启动定位
        locationDisplay.startAsync();
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
                    forecast_time.setText("预报时间:"+dsljybBeen.getForecast_time());
                    mWebView.loadUrl("file:///android_asset/jsWeb/ZhuZhuangechart.html");
                }, throwable -> {
                    progressDialog.dismiss();
                    LogUtils.e(throwable);
                    ToastUtils.showShort(getString(R.string.getInfo_error_toast));
                });
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
                }, throwable -> {
                    progressDialog.dismiss();
                    LogUtils.e(throwable);
                    ToastUtils.showShort(getString(R.string.getInfo_error_toast));
                });
    }


    private ArcGISMapImageLayer getGridLayer() {
        if (gridLayer == null) {
            gridLayer = new ArcGISMapImageLayer("http://59.55.128.156:6080/arcgis/rest/services/JX_disaster/jx/MapServer");
            gridLayer.addDoneLoadingListener(() -> {
                if (gridLayer.getLoadStatus() == LoadStatus.LOADED) {
                    mapView.setViewpointGeometryAsync(gridLayer.getFullExtent(), 10);
                } else {
                    ToastUtils.showShort("图层加载失败");
                }
            });
        }
        return gridLayer;
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
                        didian.setText(aMapLocation.getPoiName());
                        x = aMapLocation.getLatitude();//获取纬度
                        y = aMapLocation.getLongitude();//获取经度
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



    }

    @Override
    public void getNewData(Point point) {
        x = point.getX();
        y = point.getY();
        if (select.equals("now")) getDsljyb();
        else getDsljybSum();
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
            option.tooltip().trigger(Trigger.item).formatter("{a} <br/>{b} : {c}");
            option.xAxis(new CategoryAxis().data("6", "12", "18", "24", "30", "36", "42", "48", "54", "60", "66", "72", "78", "84", "90", "96", "102", "108", "114", "120"));
            option.yAxis(new ValueAxis());
            Bar bar = new Bar();
            bar.data(barData);
            option.series(bar);
            return option;
        }
    }

}
