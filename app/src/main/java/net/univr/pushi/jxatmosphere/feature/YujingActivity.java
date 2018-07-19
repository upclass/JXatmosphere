package net.univr.pushi.jxatmosphere.feature;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
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
import com.esri.arcgisruntime.mapping.GeoElement;
import com.esri.arcgisruntime.mapping.view.Graphic;
import com.esri.arcgisruntime.mapping.view.GraphicsOverlay;
import com.esri.arcgisruntime.mapping.view.MapView;
import com.esri.arcgisruntime.symbology.PictureMarkerSymbol;

import net.univr.pushi.jxatmosphere.R;
import net.univr.pushi.jxatmosphere.base.BaseActivity;
import net.univr.pushi.jxatmosphere.beens.YuJinXinhaoBeen;
import net.univr.pushi.jxatmosphere.interfaces.MapI;
import net.univr.pushi.jxatmosphere.remote.RetrofitHelper;
import net.univr.pushi.jxatmosphere.utils.GetResourceInt;
import net.univr.pushi.jxatmosphere.utils.YujinWeiZhi;
import net.univr.pushi.jxatmosphere.widget.MapScreenLinstenter;
import net.univr.pushi.jxatmosphere.widget.TianDiTuMethodsClass;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class YujingActivity extends BaseActivity implements MapI, View.OnClickListener {


    @BindView(R.id.mapview)
    MapView mapView;
    Basemap slbasemap;
    ArcGISMapImageLayer gridLayer;
    GraphicsOverlay mMissionGraphicLayer;
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.shengtai)
    TextView shengtai;
    @BindView(R.id.shixian)
    TextView shixian;
    private List<YuJinXinhaoBeen.DataBean> data;
    String tag;
    PopupWindow popupWindow;
    PopupWindow popupWindowXq;
    private View popLayoutXq;
    private View popLayout;


    @Override
    public int getLayoutId() {
        return R.layout.activity_yujing;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        super.initViews(savedInstanceState);
        initArcgis();
        initOnclick();
        initPopWindow();
        initPopWindowXq();


    }

    private void initPopWindowXq() {
        popLayoutXq = LayoutInflater.from(this).inflate(R.layout.yujin_popup_xq_layout, null);
        popupWindowXq = new PopupWindow(popLayoutXq, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindowXq.setTouchable(true);
        popupWindowXq.setFocusable(false);
        popupWindowXq.setOutsideTouchable(false);
//        Drawable drawable = getResources().getDrawable(R.drawable.popbackground);
//        popupWindowXq.setBackgroundDrawable(drawable);
//        popupWindowXq.setAnimationStyle(R.style.pop_anim);
//        popupWindowXq.showAtLocation(mapView, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
    }

    private void initPopWindow() {
        popLayout = LayoutInflater.from(this).inflate(R.layout.yujin_popup_layout, null);
        popupWindow = new PopupWindow(popLayout, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setTouchable(true);
        popupWindow.setFocusable(false);
        popupWindow.setOutsideTouchable(false);
//        popupWindow.setAnimationStyle(R.style.pop_anim);
//        popupWindow.showAtLocation(mapView, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
    }

    private void initOnclick() {
//        gb.setOnClickListener(this);
//        gbQx.setOnClickListener(this);
//        seeXiangqing.setOnClickListener(this);
//        ditu.setOnClickListener(this);
        shengtai.setOnClickListener(this);
        shixian.setOnClickListener(this);
        back.setOnClickListener(this);
    }

    private void initArcgis() {
        initArcgisUtils();
        initBaseMap();//初始化底图
        tag = "0";
        getYujinInfo();
        startMapLinstenter();
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


    private void getYujinInfo() {
        RetrofitHelper.getForecastWarn()
                .getYujinInfo(tag)
                .compose(bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(yunjinInfo -> {
                    data = yunjinInfo.getData();
                    showPollutionGrahpics(data);
                }, throwable -> {
                    LogUtils.e(throwable);
                    ToastUtils.showShort(getString(R.string.getInfo_error_toast));
                });
    }


    private void initArcgisUtils() {
        ArcGISRuntimeEnvironment.setLicense("runtimelite,1000,rud8065403504,none," +
                "RP5X0H4AH7CLJ9HSX018");
        mapView.setAttributionTextVisible(false);
    }

    private ArcGISMapImageLayer getGridLayer() {
        if (gridLayer == null) {
            gridLayer = new ArcGISMapImageLayer("http://59.55.128.156:6080/arcgis/rest/services/JX_disaster/jx/MapServer");
//            mapView.getMap().getOperationalLayers().add(gridLayer);
            gridLayer.addDoneLoadingListener(() -> {
                if (gridLayer.getLoadStatus() == LoadStatus.LOADED) {
                    if(mapView!=null)
                    mapView.setViewpointGeometryAsync(gridLayer.getFullExtent(), 10);
                } else {
                    ToastUtils.showShort("图层加载失败");
                }
            });
        }
        return gridLayer;
    }

    private void showPollutionGrahpics(List<YuJinXinhaoBeen.DataBean> dataBeans) {
        for (int i = 0; i < dataBeans.size(); i++) {
            Double[] info;
            String danwei = dataBeans.get(i).getDanwei();
            if (tag.equals("1")) {
                String substring = danwei.substring(0, danwei.length() - 3);
                info = YujinWeiZhi.getInfo(substring);
            } else {
                String citySelect = dataBeans.get(i).getCitySelect();
                if (citySelect.equals("全市")) continue;
                info = YujinWeiZhi.getInfo(citySelect);
            }

            Double lat = info[1];
            Double lon = info[0];
            if (lat == null || lon == null) continue;

            String subclass = dataBeans.get(i).getSubclass();

            String fabu = dataBeans.get(i).getFabu();
            String yubaoyuan = dataBeans.get(i).getYubaoyuan();
            String qianfaren = dataBeans.get(i).getQianfaren();
            String jielun = dataBeans.get(i).getJielun();
            String substring = subclass.substring(0, subclass.length() - 4);
            String picName = YujinWeiZhi.getPicName(substring);
            String citySelect = dataBeans.get(i).getCitySelect();
            int resource = GetResourceInt.getResource(picName, context);

            Map<String, Object> stringObjectMap = new HashMap<>();
            stringObjectMap.put("subclass", subclass);
            stringObjectMap.put("danwei", danwei);
            stringObjectMap.put("fabu", fabu);
            stringObjectMap.put("yby", yubaoyuan);
            stringObjectMap.put("qfr", qianfaren);
            stringObjectMap.put("fanwei", citySelect);
            stringObjectMap.put("info", jielun);
            stringObjectMap.put("picId", resource);

            Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), resource);
            PictureMarkerSymbol pictureMarkerSymbol = new PictureMarkerSymbol(new BitmapDrawable(bitmap));
            pictureMarkerSymbol.setWidth(30);
            pictureMarkerSymbol.setHeight(30);
            try {
                Graphic graphic = new Graphic(new Point(lon,
                        lat,
                        mapView.getSpatialReference()), stringObjectMap, pictureMarkerSymbol);
                getMissionGraphicLayer().getGraphics().add(graphic);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
//        mapView.setViewpointGeometryAsync(getMissionGraphicLayer().getExtent(), 100);
        }

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


    private void startMapLinstenter() {
        MapScreenLinstenter mapScreenLinstenter = new MapScreenLinstenter(context, mapView, this);
        mapView.setOnTouchListener(mapScreenLinstenter);
    }

    //false显示底部状态栏，true隐藏状态栏
    @Override
    public void updateBottomState(Boolean b) {
        if (b) {
            if (popupWindow != null && popupWindowXq != null) {
                popupWindow.dismiss();
                popupWindowXq.dismiss();
            }
        } else {
//            layoutBottom.setVisibility(View.VISIBLE);
//            show();
        }
    }

    @Override
    public void showIdentQueryPoint(GeoElement geoElement) {
        updateBottomState(false);
        Graphic graphic = new Graphic(geoElement.getGeometry(), geoElement.getAttributes());
        showIdentQueryPoint(graphic);

    }

    @Override
    public void showIdentQueryPoint(Graphic graphic) {
        updateBottomState(false);
//        mapView.setViewpointGeometryAsync(graphic.getGeometry(), 100);
        loadBottomLayout(graphic);
        Point point = graphic.getGeometry().getExtent().getCenter();
        mapView.setViewpointCenterAsync(point);
    }

    /**
     * 加载下方布局信息
     *
     * @param graphic
     */
    private void loadBottomLayout(Graphic graphic) {
        Map<String, Object> selectMap = graphic.getAttributes();
        if (popupWindowXq.isShowing())
            showXq(context, selectMap);
        else {
            show(context, selectMap);
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.shengtai:
                if (popupWindowXq != null && popupWindow != null) {
                    popupWindow.dismiss();
                    popupWindowXq.dismiss();
                }

                shengtai.setBackground(getResources().getDrawable(R.drawable.gd_text_bg1_select));
                shengtai.setTextColor(getResources().getColor(R.color.white));
                shixian.setBackground(getResources().getDrawable(R.drawable.gd_text_bg3));
                shixian.setTextColor(getResources().getColor(R.color.toolbar_color));
                getMissionGraphicLayer().getGraphics().clear();
                tag = "0";
                getYujinInfo();
                break;
            case R.id.shixian:
                if (popupWindowXq != null && popupWindow != null) {
                    popupWindow.dismiss();
                    popupWindowXq.dismiss();
                }
                shengtai.setBackground(getResources().getDrawable(R.drawable.gd_text_bg1));
                shengtai.setTextColor(getResources().getColor(R.color.toolbar_color));
                shixian.setBackground(getResources().getDrawable(R.drawable.gd_text_bg3_select));
                shixian.setTextColor(getResources().getColor(R.color.white));
                getMissionGraphicLayer().getGraphics().clear();
                tag = "1";
                getYujinInfo();
                break;
            case R.id.back:
                finish();
                break;
        }
    }


    private void show(Context context, Map<String, Object> selectMap) {
        popupWindow.showAtLocation(mapView, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
        ImageView gaikuanPic = popLayout.findViewById(R.id.gaikuan_pic);
        TextView gaikuanSubclass = popLayout.findViewById(R.id.gaikuan_subclass);
        TextView gaikuanQxj = popLayout.findViewById(R.id.gaikuan_qxj);
        TextView gaikuanSj = popLayout.findViewById(R.id.gaikuan_sj);
        LinearLayout see_xiangqing = popLayout.findViewById(R.id.see_xiangqing);
        TextView gb = popLayout.findViewById(R.id.gb);
        String subclass = (String) selectMap.get("subclass");
        String danwei = (String) selectMap.get("danwei");
        String fabu = (String) selectMap.get("fabu");
        Integer resource = (Integer) selectMap.get("picId");
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), resource);
        gaikuanPic.setImageBitmap(bitmap);
        gaikuanSubclass.setText(subclass);
        gaikuanQxj.setText(danwei);
        gaikuanSj.setText(fabu);
        see_xiangqing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                showXq(context, selectMap);
            }
        });
        gb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                mapView.setViewpointGeometryAsync(getGridLayer().getFullExtent(), 10);
            }
        });

    }

    private void showXq(Context context, Map<String, Object> selectMap) {
        popupWindowXq.showAtLocation(mapView, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
        ImageView xiangqingPic = popLayoutXq.findViewById(R.id.xiangqing_pic);
        TextView xiangqingSubclass = popLayoutXq.findViewById(R.id.xiangqing_subclass);
        TextView qxjXq = popLayoutXq.findViewById(R.id.qxj_xq);
        TextView sjXq = popLayoutXq.findViewById(R.id.sj_xq);
        TextView yby = popLayoutXq.findViewById(R.id.yby);
        TextView qfr = popLayoutXq.findViewById(R.id.qfr);
        TextView ybfw = popLayoutXq.findViewById(R.id.ybfw);
        TextView ybxx = popLayoutXq.findViewById(R.id.ybxx);
        LinearLayout ditu = popLayoutXq.findViewById(R.id.ditu);
        TextView gb_xq = popLayoutXq.findViewById(R.id.gb_xq);

        String subclass = (String) selectMap.get("subclass");
        String danwei = (String) selectMap.get("danwei");
        String fabu = (String) selectMap.get("fabu");
        Integer resource = (Integer) selectMap.get("picId");
        String ybyStr = (String) selectMap.get("yby");
        String qfrStr = (String) selectMap.get("qfr");
        String fanweiStr = (String) selectMap.get("fanwei");
        String info = "预报信息:" + (String) selectMap.get("info");
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), resource);

        xiangqingPic.setImageBitmap(bitmap);
        xiangqingSubclass.setText(subclass);
        qxjXq.setText(danwei);
        sjXq.setText(fabu);
        yby.setText(ybyStr);
        qfr.setText(qfrStr);
        ybfw.setText(fanweiStr);
        ybxx.setText(info);
        ditu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindowXq.dismiss();
                show(context, selectMap);
            }
        });
        gb_xq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindowXq.dismiss();
                mapView.setViewpointGeometryAsync(getGridLayer().getFullExtent(), 10);
            }
        });
    }

}


