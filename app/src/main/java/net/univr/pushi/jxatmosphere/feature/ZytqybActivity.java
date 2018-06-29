package net.univr.pushi.jxatmosphere.feature;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.widget.OrientationHelper;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.applikeysolutions.cosmocalendar.utils.SelectionType;
import com.applikeysolutions.cosmocalendar.view.CalendarView;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.esri.arcgisruntime.ArcGISRuntimeEnvironment;
import com.esri.arcgisruntime.geometry.Point;
import com.esri.arcgisruntime.layers.ArcGISMapImageLayer;
import com.esri.arcgisruntime.layers.WebTiledLayer;
import com.esri.arcgisruntime.loadable.LoadStatus;
import com.esri.arcgisruntime.mapping.ArcGISMap;
import com.esri.arcgisruntime.mapping.Basemap;
import com.esri.arcgisruntime.mapping.GeoElement;
import com.esri.arcgisruntime.mapping.view.Callout;
import com.esri.arcgisruntime.mapping.view.Graphic;
import com.esri.arcgisruntime.mapping.view.GraphicsOverlay;
import com.esri.arcgisruntime.mapping.view.MapView;
import com.esri.arcgisruntime.symbology.PictureMarkerSymbol;

import net.univr.pushi.jxatmosphere.R;
import net.univr.pushi.jxatmosphere.base.BaseActivity;
import net.univr.pushi.jxatmosphere.beens.ZytqybBeen;
import net.univr.pushi.jxatmosphere.interfaces.MapI;
import net.univr.pushi.jxatmosphere.remote.RetrofitHelper;
import net.univr.pushi.jxatmosphere.widget.MapScreenLinstenter;
import net.univr.pushi.jxatmosphere.widget.TianDiTuMethodsClass;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ZytqybActivity extends BaseActivity implements MapI {
    @BindView(R.id.mapview)
    MapView mapView;
    Basemap slbasemap;
    ArcGISMapImageLayer gridLayer;
    GraphicsOverlay mMissionGraphicLayer;
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.calendar_view)
    CalendarView calendarView;
    @BindView(R.id.confirm)
    TextView confirm;
    @BindView(R.id.cancle_sj)
    TextView cancle_sj;
    @BindView(R.id.date_qi)
    TextView data_qi_bottom;
    @BindView(R.id.date_zhi)
    TextView data_zhi_bottom;
    @BindView(R.id.bottom_lay)
    LinearLayout bottom_lay;
    @BindView(R.id.rili_lay)
    LinearLayout rili_lay;


    @Override
    public int getLayoutId() {
        return R.layout.activity_zytqyb;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        super.initViews(savedInstanceState);
        initArcgis();
        initBottomTime();
        initCalendar();
        startMapLinstenter();
        getZytqyb(data_qi_bottom.getText().toString(), data_zhi_bottom.getText().toString());
    }

    private void getZytqyb(String startTime, String endTime) {
        getMissionGraphicLayer().getGraphics().clear();
        RetrofitHelper.getForecastWarn()
                .getZytqyb(startTime, endTime)
                .compose(bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(zytqybBeen -> {
                    List<ZytqybBeen.DataBean> data = zytqybBeen.getData();
                    for (int i = 0; i < data.size(); i++) {
                        String lat = data.get(i).getLat();
                        Double aDouble = Double.valueOf(lat);
                        String lon = data.get(i).getLon();
                        Double aDouble1 = Double.valueOf(lon);
                        String wep_now = data.get(i).getWEP_Now();
                        String win_d_inst_max = data.get(i).getWIN_D_INST_Max();
                        String win_s_inst_max = data.get(i).getWIN_S_Inst_Max();
                        String trod_type = data.get(i).getTrod_Type();
                        String trod_bear = data.get(i).getTrod_Bear();
                        String snow_depth = data.get(i).getSnow_Depth();
                        String eiced = data.get(i).getEICED();
                        String hail_diam_max = data.get(i).getHAIL_Diam_Max();
                        String pre_1h = data.get(i).getPRE_1h();
                        String pre_3h = data.get(i).getPRE_3h();
                        Map<String, Object> stringObjectMap = new HashMap<>();
                        stringObjectMap.put("wep_now", wep_now);
                        stringObjectMap.put("win_d_inst_max", win_d_inst_max);
                        stringObjectMap.put("win_s_inst_max", win_s_inst_max);
                        stringObjectMap.put("trod_type", trod_type);
                        stringObjectMap.put("trod_bear", trod_bear);
                        stringObjectMap.put("snow_depth", snow_depth);
                        stringObjectMap.put("eiced", eiced);
                        stringObjectMap.put("hail_diam_max", hail_diam_max);
                        stringObjectMap.put("pre_1h", pre_1h);
                        stringObjectMap.put("pre_3h", pre_3h);

                        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.location);
                        PictureMarkerSymbol pictureMarkerSymbol = new PictureMarkerSymbol(new BitmapDrawable(bitmap));
                        pictureMarkerSymbol.setWidth(30);
                        pictureMarkerSymbol.setHeight(30);
                        try {
                            Graphic graphic = new Graphic(new Point(aDouble1,
                                    aDouble,
                                    mapView.getSpatialReference()), stringObjectMap, pictureMarkerSymbol);
                            getMissionGraphicLayer().getGraphics().add(graphic);
                        } catch (NumberFormatException e) {
                            e.printStackTrace();
                        }
//                        mapView.setViewpointGeometryAsync(getMissionGraphicLayer().getExtent(), 100);
                    }
                }, throwable -> {
                    LogUtils.e(throwable);
                    ToastUtils.showShort(getString(R.string.getInfo_error_toast));
                });
    }

    private void initBottomTime() {
        Date now = new Date();
        long nowLong = now.getTime();
        long oneDayBeforeLong = nowLong - 3600 * 1000;
        Date oneDayBefore = new Date(oneDayBeforeLong);
        String oneDayBeforeStr = getTime(oneDayBefore);
        String nowTimeStr = getTime(now);

        data_qi_bottom.setText(oneDayBeforeStr + " 00:00:00");
        data_zhi_bottom.setText(nowTimeStr + " 23:59:59");
        bottom_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mapView.getCallout().dismiss();
                rili_lay.setVisibility(View.VISIBLE);
            }
        });
    }

    private void startMapLinstenter() {
        MapScreenLinstenter mapScreenLinstenter = new MapScreenLinstenter(context, mapView, this);
        mapView.setOnTouchListener(mapScreenLinstenter);
    }

    public String getTime(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");// 可以方便地修改日期格式
        String nowStr = dateFormat.format(date);
        return nowStr;
    }


    private void initCalendar() {
        calendarView.setCalendarOrientation(OrientationHelper.HORIZONTAL);
        calendarView.setSelectionType(SelectionType.RANGE);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Calendar> selectedDates = calendarView.getSelectedDates();
                rili_lay.setVisibility(View.INVISIBLE);
                String qi = selectedDates.get(0).get(Calendar.YEAR) + "-" + (selectedDates.get(0).get(Calendar.MONTH) + 1) + "-" + selectedDates.get(0).get(Calendar.DAY_OF_MONTH) + " 00:00:00";
                String zhi = selectedDates.get(selectedDates.size() - 1).get(Calendar.YEAR) + "-" + (selectedDates.get(selectedDates.size() - 1).get(Calendar.MONTH) + 1) + "-" + selectedDates.get(selectedDates.size() - 1).get(Calendar.DAY_OF_MONTH) + " 23:59:59";
                data_qi_bottom.setText(qi);
                data_zhi_bottom.setText(zhi);
                getZytqyb(data_qi_bottom.getText().toString(),data_zhi_bottom.getText().toString());
            }
        });
        cancle_sj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Calendar> selectedDates = calendarView.getSelectedDates();
                rili_lay.setVisibility(View.INVISIBLE);
                selectedDates.clear();
            }
        });

    }


    private void initArcgis() {
        initArcgisUtils();
        initBaseMap();//初始化底图
    }


    private void initArcgisUtils() {
        ArcGISRuntimeEnvironment.setLicense("runtimelite,1000,rud8065403504,none," +
                "RP5X0H4AH7CLJ9HSX018");
        mapView.setAttributionTextVisible(false);
    }

    private void initBaseMap() {
        if (slbasemap == null) {
            slbasemap = new Basemap();
            WebTiledLayer webTiledLayer = TianDiTuMethodsClass.CreateTianDiTuTiledLayer
                    (TianDiTuMethodsClass.LayerType.TIANDITU_VECTOR_2000);
            slbasemap.getBaseLayers().add(webTiledLayer);
            slbasemap.getBaseLayers().add(getGridLayer());
            if (mapView.getMap() == null) {
                mapView.setMap(new ArcGISMap());
            }
            if (mapView.getMap().getBasemap() != slbasemap) {
                mapView.getMap().setBasemap(slbasemap);
            }
        }
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

    public GraphicsOverlay getMissionGraphicLayer() {
        if (!mapView.getGraphicsOverlays().contains(mMissionGraphicLayer)) {
            if (mMissionGraphicLayer == null) {
                mMissionGraphicLayer = new GraphicsOverlay();
            }
            mapView.getGraphicsOverlays().add(mMissionGraphicLayer);
        }
        return mMissionGraphicLayer;
    }


    @Override
    public void updateBottomState(Boolean b) {

    }

    @Override
    public void showIdentQueryPoint(GeoElement geoElement) {
        Graphic graphic = new Graphic(geoElement.getGeometry(), geoElement.getAttributes());
        showIdentQueryPoint(graphic);
    }

    @Override
    public void showIdentQueryPoint(Graphic graphic) {
//        mapView.setViewpointGeometryAsync(graphic.getGeometry(),100);
        ShowCallout(graphic);
    }

    /**
     * 显示和初始化弹出框
     *
     * @param graphic
     */
    public void ShowCallout(Graphic graphic) {
        String wep_now = (String) graphic.getAttributes().get("wep_now");
        String win_d_inst_max = (String) graphic.getAttributes().get("win_d_inst_max");
        String win_s_inst_max = (String) graphic.getAttributes().get("win_s_inst_max");
        String trod_type = (String) graphic.getAttributes().get("trod_type");
        String trod_bear = (String) graphic.getAttributes().get("trod_bear");
        String snow_depth = (String) graphic.getAttributes().get("snow_depth");
        String eiced = (String) graphic.getAttributes().get("eiced");
        String hail_diam_max = (String) graphic.getAttributes().get("hail_diam_max");
        String pre_1h = (String) graphic.getAttributes().get("pre_1h");
        String pre_3h = (String) graphic.getAttributes().get("pre_3h");

        mapView.getCallout().setStyle(new Callout.Style(context, R.xml.my_callout_identify));
        View view = getLayoutInflater().inflate(R.layout.zytqyb_xq_layout, null);



        TextView xztq = view.findViewById(R.id.xztq);
        TextView jdfsd = view.findViewById(R.id.jdfsd);
        TextView jdfsm = view.findViewById(R.id.jdfsm);
        TextView ljflx = view.findViewById(R.id.ljflx);
        TextView ljffw = view.findViewById(R.id.ljffw);
        TextView jxsd = view.findViewById(R.id.jxsd);
        TextView djbzj = view.findViewById(R.id.djbzj);
        TextView zdbbzj = view.findViewById(R.id.zdbbzj);
        TextView gqyxsjyl = view.findViewById(R.id.gqyxsjyl);
        TextView gqsxsjyl = view.findViewById(R.id.gqsxsjyl);
        ImageView imageView= view.findViewById(R.id.cancle_xq);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mapView.getCallout().dismiss();
            }
        });
        xztq.setText(wep_now);
        jdfsd.setText(win_d_inst_max);
        jdfsm.setText(win_s_inst_max);
        ljflx.setText(trod_type);
        ljffw.setText(trod_bear);
        jxsd.setText(snow_depth);
        djbzj.setText(eiced);
        zdbbzj.setText(hail_diam_max);
        gqyxsjyl.setText(pre_1h);
        gqsxsjyl.setText(pre_3h);
        mapView.getCallout().show(view, graphic.getGeometry().getExtent().getCenter());
    }

}
