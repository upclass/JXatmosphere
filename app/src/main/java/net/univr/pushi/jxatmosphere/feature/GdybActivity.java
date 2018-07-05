package net.univr.pushi.jxatmosphere.feature;

import android.app.ProgressDialog;
import android.content.Context;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

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
import com.github.abel533.echarts.axis.AxisLabel;
import com.github.abel533.echarts.axis.CategoryAxis;
import com.github.abel533.echarts.axis.SplitLine;
import com.github.abel533.echarts.axis.ValueAxis;
import com.github.abel533.echarts.code.Trigger;
import com.github.abel533.echarts.json.GsonOption;
import com.github.abel533.echarts.series.Line;

import net.univr.pushi.jxatmosphere.R;
import net.univr.pushi.jxatmosphere.adapter.PiaoGeAdapter;
import net.univr.pushi.jxatmosphere.base.BaseActivity;
import net.univr.pushi.jxatmosphere.beens.GdybBeen;
import net.univr.pushi.jxatmosphere.interfaces.MapCall;
import net.univr.pushi.jxatmosphere.remote.RetrofitHelper;
import net.univr.pushi.jxatmosphere.widget.MapScreenClickListen;
import net.univr.pushi.jxatmosphere.widget.TianDiTuMethodsClass;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class GdybActivity extends BaseActivity implements View.OnClickListener,MapCall {
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
    TextView sousuo_tv;
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
    Basemap slbasemap;
    ArcGISMapImageLayer gridLayer;
    String interval = "1";
    private Object times[];
    private Object temps[];
    private Object rains[];
    private Object winds[];
    private Object humiditys[];
    private List<GdybBeen.DataBean> data = new ArrayList<>();
    Double x;
    Double y;
    int i=0;


    @Override
    public int getLayoutId() {
        return R.layout.activity_gdyb;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        super.initViews(savedInstanceState);
        initView();
        tuxin_tv.setOnClickListener(this);
        biaoge_tv.setOnClickListener(this);
        one_hour.setOnClickListener(this);
        three_hour.setOnClickListener(this);
        six_hour.setOnClickListener(this);
        twelve_hour.setOnClickListener(this);
        twentyfour_hour.setOnClickListener(this);
        back.setOnClickListener(this);
        delete.setOnClickListener(this);
        initArcgis();
    }


    private void initArcgis() {

        initBaseMap();//初始化底图
        initGps();//初始化gps
        initArcgisUtils();
//        initArcgisLocation();
        startMapLinstenter();
    }

    private void startMapLinstenter() {
        MapScreenClickListen mapScreenClickListen = new MapScreenClickListen(context, mapView,this);
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

    private ArcGISMapImageLayer getGridLayer() {
        if (gridLayer == null) {
            gridLayer = new ArcGISMapImageLayer("http://59.55.128.156:6080/arcgis/rest/services/JX_disaster/jx/MapServer");
//            mapView.getMap().getOperationalLayers().add(gridLayer);
            gridLayer.addDoneLoadingListener(() -> {
                if (gridLayer.getLoadStatus() == LoadStatus.LOADED) {
//                    mapView.setViewpointGeometryAsync(gridLayer.getFullExtent());
                    mapView.setViewpointGeometryAsync(gridLayer.getFullExtent(), 10);
                } else {
                    ToastUtils.showShort("图层加载失败");
                }
            });
        }
        return gridLayer;
    }

    /**
     * 初始化gps
     */
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
        //获取arcgis定位管理器
        final LocationDisplay locationDisplay = mapView.getLocationDisplay();
        //设置arcgis定位显示方式
        locationDisplay.setAutoPanMode(LocationDisplay.AutoPanMode.NAVIGATION);
//        设置定位点在屏幕百分比
        locationDisplay.setNavigationPointHeightFactor(0.5f);
        //启动定位
        locationDisplay.startAsync();
        locationDisplay.addLocationChangedListener(new LocationDisplay.LocationChangedListener() {
            @Override
            public void onLocationChanged(LocationDisplay.LocationChangedEvent locationChangedEvent) {
                x=locationChangedEvent.getLocation().getPosition().getX();
                y=locationChangedEvent.getLocation().getPosition().getY();
                if(i==0)
                getTestdata();
                i++;
            }
        });
    }

    private void initView() {
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
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
        mWebView.addJavascriptInterface(new WebAppInterface(this), "Android");
//        mWebView.setHorizontalScrollBarEnabled(false);//禁止水平滚动


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
                    sousuo_tv.setText(gdybBeen.getLocation().getDistrict());
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
        }
    }

    @Override
    public void getNewData(Point point) {
         x = point.getX();
         y = point.getY();
        getTestdata();
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
}

