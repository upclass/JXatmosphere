package net.univr.pushi.jxatmosphere.feature;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
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
import com.amap.api.services.district.DistrictItem;
import com.amap.api.services.district.DistrictResult;
import com.amap.api.services.district.DistrictSearch;
import com.amap.api.services.district.DistrictSearchQuery;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;

import net.univr.pushi.jxatmosphere.R;
import net.univr.pushi.jxatmosphere.base.BaseActivity;
import net.univr.pushi.jxatmosphere.beens.YujinInfo;
import net.univr.pushi.jxatmosphere.remote.RetrofitHelper;
import net.univr.pushi.jxatmosphere.utils.GetResourceInt;
import net.univr.pushi.jxatmosphere.utils.PicUtils;
import net.univr.pushi.jxatmosphere.utils.ShipeiUtils;
import net.univr.pushi.jxatmosphere.utils.ThreadUtil;
import net.univr.pushi.jxatmosphere.utils.YujinWeiZhi;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class YuJingGaoDeActivity extends BaseActivity implements DistrictSearch.OnDistrictSearchListener, View.OnClickListener, AMap.OnMarkerClickListener {

    @BindView(R.id.mapview)
    MapView mapView;

    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.reload)
    ImageView reload;
    @BindView(R.id.shengtai)
    TextView shengtai;
    @BindView(R.id.shixian)
    TextView shixian;
    private List<YujinInfo.DataBean> data;
    String tag;
    PopupWindow popupWindow;
    PopupWindow popupWindowXq;
    private View popLayoutXq;
    private View popLayout;
    private AMap mAMap;
    List<Marker> markList;
    private MyLocationStyle myLocationStyle;
    public AMapLocationClient mlocationClient;
    public AMapLocationClientOption mLocationOption;
    ProgressDialog progressDialog;

    @Override
    public int getLayoutId() {
        return R.layout.activity_yu_jing_gao_de;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        super.initViews(savedInstanceState);
        myInit(savedInstanceState);
        initOnclick();
        initPopWindow();
        initPopWindowXq();
    }

    private void myInit(Bundle savedInstanceState) {
        mapView.onCreate(savedInstanceState);
        mAMap = mapView.getMap();
        myLocationStyle = new MyLocationStyle();
        mAMap.getUiSettings().setZoomControlsEnabled(false);
        mAMap.getUiSettings().setLogoBottomMargin(-50);
        mAMap.getUiSettings().setCompassEnabled(true);
        mAMap.getUiSettings().setRotateGesturesEnabled(false);
        initLocation();
        markList = new ArrayList<>();
        tag = "0";
        getYujinInfo();
        getJiangXiFanWei();

    }

    private void initLocation() {
        if (ShipeiUtils.isLocationEnabled(context)) {
            myLocationStyle.myLocationIcon(BitmapDescriptorFactory.fromResource(R.drawable.gps_point));
            mAMap.setMyLocationStyle(myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_SHOW));
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


    private void getJiangXiFanWei() {
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


    private void initPopWindowXq() {
        popLayoutXq = LayoutInflater.from(this).inflate(R.layout.yujin_popup_xq_layout, null);
        popupWindowXq = new PopupWindow(popLayoutXq, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindowXq.setTouchable(true);
        popupWindowXq.setFocusable(false);
        popupWindowXq.setOutsideTouchable(false);
    }

    private void initPopWindow() {
        popLayout = LayoutInflater.from(this).inflate(R.layout.yujin_popup_layout, null);
        popupWindow = new PopupWindow(popLayout, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setTouchable(true);
        popupWindow.setFocusable(false);
        popupWindow.setOutsideTouchable(false);
    }

    private void initOnclick() {
        shengtai.setOnClickListener(this);
        shixian.setOnClickListener(this);
        back.setOnClickListener(this);
        reload.setOnClickListener(this);
        mAMap.setOnMarkerClickListener(this);// 设置点击marker事件监听器
    }


    private void getYujinInfo() {
        progressDialog = ProgressDialog.show(context, "请稍等...", "获取数据中...", true);
        progressDialog.setCancelable(true);
        RetrofitHelper.getForecastWarn()
                .getYujinInfo(tag)
                .compose(bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(yunjinInfo -> {
                    progressDialog.dismiss();
                    data = yunjinInfo.getData();
                    if (null != data && data.size() > 0)
                        showPollutionGrahpics(data, tag);
                }, throwable -> {
                    progressDialog.dismiss();
                    LogUtils.e(throwable);
                    ToastUtils.showShort(getString(R.string.getInfo_error_toast));
                });
    }


    private void showPollutionGrahpics(List<YujinInfo.DataBean> dataBeans, String tag) {
        if (tag.equals("1")) {
            for (int i = 0; i < dataBeans.size(); i++) {
                Double[] info = new Double[2];
                String danwei = dataBeans.get(i).getDanwei();
                String substring = danwei.substring(0, danwei.length() - 3);
                info = YujinWeiZhi.getInfo(substring);
                Double lat = info[1];
                Double lon = info[0];
                if (lat == null || lon == null) continue;
                String subclass = dataBeans.get(i).getYujinInfo();
                String fabu = dataBeans.get(i).getFabu();
                String yubaoyuan = dataBeans.get(i).getYuBaoYuan();
                String jielun = dataBeans.get(i).getContent();
                String fanwei = dataBeans.get(i).getAcodes();
                substring = subclass.substring(0, subclass.length() - 4);
                String picName = YujinWeiZhi.getPicName(substring);
                int resource = GetResourceInt.getResource(picName, context);
                Map<String, Object> stringObjectMap = new HashMap<>();
                stringObjectMap.put("subclass", subclass);
                stringObjectMap.put("danwei", danwei);
                stringObjectMap.put("fabu", fabu);
                stringObjectMap.put("yby", yubaoyuan);
                stringObjectMap.put("info", jielun);
                stringObjectMap.put("picId", resource);
                stringObjectMap.put("fanwei", "预报范围:"+fanwei);

                View parentView = LayoutInflater.from(context).inflate(R.layout.gaode_marker_image, null, false);
                ImageView markerView = parentView.findViewById(R.id.mark_view);
                Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), resource);
                bitmap = PicUtils.setImgSize(bitmap, 80, 80);
                markerView.setImageBitmap(bitmap);
                JSONObject makerJson = JSONObject.parseObject(JSON.toJSONString(stringObjectMap));
                Marker marker = mAMap.addMarker(new MarkerOptions().anchor(0.5f, 0.5f)
                        .position(new LatLng(lat,//设置纬度
                                lon))//设置经度
                        .title(substring)//设置标题
                        .snippet(makerJson.toJSONString())//设置内容
                        .draggable(false) //设置Marker可拖动
                        .icon(BitmapDescriptorFactory.fromView(parentView)));
                marker.hideInfoWindow();
                markList.add(marker);
            }
        } else {
//                else {
////                String citySelect = dataBeans.get(i).getCitySelect();
////                if (citySelect.equals("全市")) continue;
////                info = YujinWeiZhi.getInfo(citySelect);
//                info[1] = 28.69;
//                info[0] = 115.99;
            Double[] info = new Double[2];
            YujinInfo.DataBean dataBean = dataBeans.get(0);
            String acodes = dataBean.getAcodes();
            String[] split = acodes.split(",");
            String title="";
            for (int i = 0; i < split.length; i++) {
                String danwei =dataBean.getDanwei();
                info = YujinWeiZhi.getInfoBycode(split[i]);
                Double lat = info[1];
                Double lon = info[0];
                title=YujinWeiZhi.getTitle(split[i]);
                if (lat == null || lon == null) continue;
                String subclass = dataBean.getYujinInfo();
                String fabu = dataBean.getFabu();
                String yubaoyuan = dataBean.getYuBaoYuan();
                String jielun = dataBean.getContent();
                String substring = subclass.substring(0, subclass.length() - 2);
                String picName = YujinWeiZhi.getPicName(substring);
                String fanwei = dataBean.getAcodes();
                int resource = GetResourceInt.getResource(picName, context);
                Map<String, Object> stringObjectMap = new HashMap<>();
                stringObjectMap.put("subclass", subclass);
                stringObjectMap.put("danwei", danwei);
                stringObjectMap.put("fabu", fabu);
                stringObjectMap.put("yby", yubaoyuan);
                stringObjectMap.put("info", jielun);
                stringObjectMap.put("picId", resource);
                stringObjectMap.put("fanwei", "预报范围:"+initDiqu(fanwei).toString());

                View parentView = LayoutInflater.from(context).inflate(R.layout.gaode_marker_image, null, false);
                ImageView markerView = parentView.findViewById(R.id.mark_view);
                Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), resource);
                bitmap = PicUtils.setImgSize(bitmap, 80, 80);
                markerView.setImageBitmap(bitmap);
                JSONObject makerJson = JSONObject.parseObject(JSON.toJSONString(stringObjectMap));
                Marker marker = mAMap.addMarker(new MarkerOptions().anchor(0.2f, 0.5f)
                        .position(new LatLng(lat,//设置纬度
                                lon))//设置经度
                        .title(title)//设置标题
                        .snippet(makerJson.toJSONString())//设置内容
                        .draggable(false) //设置Marker可拖动
                        .icon(BitmapDescriptorFactory.fromView(parentView)));
                marker.hideInfoWindow();
                markList.add(marker);
            }
        }
    }

    private StringBuilder initDiqu(String acode) {
        StringBuilder ret=new StringBuilder();
        String[] split = acode.split(",");

        for (int i = 0; i < split.length; i++) {
            ret.append(YujinWeiZhi.getTitle(split[i]));
            ret.append(",");
        }
        ret.deleteCharAt(ret.length()-1);
        return ret;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.shengtai:
                if (popupWindowXq != null && popupWindow != null) {
                    popupWindow.dismiss();
                    popupWindowXq.dismiss();
                }

                shengtai.setBackground(getResources().getDrawable(R.drawable.gd_yj_1));
                shengtai.setTextColor(getResources().getColor(R.color.white));
                shixian.setBackground(getResources().getDrawable(R.drawable.gd_yj_2));
                shixian.setTextColor(getResources().getColor(R.color.toolbar_color));
                clearAllMarker();
                tag = "0";
                getYujinInfo();
                break;
            case R.id.shixian:
                if (popupWindowXq != null && popupWindow != null) {
                    popupWindow.dismiss();
                    popupWindowXq.dismiss();
                }
                shengtai.setBackground(getResources().getDrawable(R.drawable.gd_yj_3));
                shengtai.setTextColor(getResources().getColor(R.color.toolbar_color));
                shixian.setBackground(getResources().getDrawable(R.drawable.gd_yj_text));
                shixian.setTextColor(getResources().getColor(R.color.white));
                clearAllMarker();
                tag = "1";
                getYujinInfo();
                break;
            case R.id.back:
                finish();
                break;
            case R.id.reload:
                getJiangXiFanWei();
                initLocation();
                if (popupWindowXq != null)
                    popupWindowXq.dismiss();
                if (popupWindow != null)
                    popupWindow.dismiss();
                getYujinInfo();
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
                centerJiangxi();
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
//        TextView qfr = popLayoutXq.findViewById(R.id.qfr);
        TextView ybfw = popLayoutXq.findViewById(R.id.ybfw);
        TextView ybxx = popLayoutXq.findViewById(R.id.ybxx);
        LinearLayout ditu = popLayoutXq.findViewById(R.id.ditu);
        TextView gb_xq = popLayoutXq.findViewById(R.id.gb_xq);

        String subclass = (String) selectMap.get("subclass");
        String danwei = (String) selectMap.get("danwei");
        String fabu = (String) selectMap.get("fabu");
        Integer resource = (Integer) selectMap.get("picId");
        String ybyStr = (String) selectMap.get("yby");
//        String qfrStr = (String) selectMap.get("qfr");
        String fanweiStr = (String) selectMap.get("fanwei");
        String info = "预报信息:" + (String) selectMap.get("info");
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), resource);

        xiangqingPic.setImageBitmap(bitmap);
        xiangqingSubclass.setText(subclass);
        qxjXq.setText(danwei);
        sjXq.setText(fabu);
        yby.setText(ybyStr);
//        qfr.setText(qfrStr);
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
                centerJiangxi();
            }
        });
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


    private void clearAllMarker() {
        if (markList != null) {
            for (int i = 0; i < markList.size(); i++) {
                markList.get(i).destroy();
            }
        }
    }


    //江西省居中显示
    private void centerJiangxi() {
        LatLngBounds bounds = new LatLngBounds.Builder()
                .include(new LatLng(24.43704147338867, 118.68101043701172))
                .include(new LatLng(30.1299808502, 113.52340240478516)).build();
        mAMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 40));
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        if (popupWindowXq != null)
            popupWindowXq.dismiss();
        if (popupWindow != null)
            popupWindow.dismiss();
        LatLng position = marker.getPosition();
        mAMap.moveCamera(CameraUpdateFactory.changeLatLng(position));
        String snippet = marker.getSnippet();
        JSONObject jsonObject = JSONObject.parseObject(snippet);
        Map<String, Object> itemMap = JSONObject.toJavaObject(jsonObject, Map.class);
        show(context, itemMap);
        return true;
    }


}



