package net.univr.pushi.jxatmosphere.feature;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
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
import com.amap.api.services.district.DistrictItem;
import com.amap.api.services.district.DistrictResult;
import com.amap.api.services.district.DistrictSearch;
import com.amap.api.services.district.DistrictSearchQuery;
import com.applikeysolutions.cosmocalendar.utils.SelectionType;
import com.applikeysolutions.cosmocalendar.view.CalendarView;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;

import net.univr.pushi.jxatmosphere.R;
import net.univr.pushi.jxatmosphere.adapter.ZytqybAdapter;
import net.univr.pushi.jxatmosphere.base.BaseActivity;
import net.univr.pushi.jxatmosphere.beens.ZytqybBeen;
import net.univr.pushi.jxatmosphere.remote.RetrofitHelper;
import net.univr.pushi.jxatmosphere.utils.PicUtils;
import net.univr.pushi.jxatmosphere.utils.ShipeiUtils;
import net.univr.pushi.jxatmosphere.utils.ThreadUtil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ZytqybGaoDeActivity extends BaseActivity implements DistrictSearch.OnDistrictSearchListener,AMap.InfoWindowAdapter {
    @BindView(R.id.mapview)
    MapView mMapView;
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
    @BindView(R.id.reload)
    ImageView reload;

    AMap mAMap;
    List<Marker> markerList;

    @Override
    public int getLayoutId() {
        return R.layout.activity_zytqyb_gao_de;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        super.initViews(savedInstanceState);
        initBottomTime();
        initCalendar();
        markerList = new ArrayList<>();
        mMapView.onCreate(savedInstanceState);
        mAMap = mMapView.getMap();
        mAMap.getUiSettings().setRotateGesturesEnabled(false);
        getJiangxiFanwei();
        getZytqyb(data_qi_bottom.getText().toString(), data_zhi_bottom.getText().toString());
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        reload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 mAMap.clear();
                 getJiangxiFanwei();
                getZytqyb(data_qi_bottom.getText().toString(), data_zhi_bottom.getText().toString());
            }
        });
        mAMap.setInfoWindowAdapter(this);
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


    private void getZytqyb(String startTime, String endTime) {
        centerJiangxi();
        clearAllMarker();
        RetrofitHelper.getForecastWarn()
                .getZytqyb(startTime, endTime)
                .compose(bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(zytqybBeen -> {
                    List<ZytqybBeen.DataBean> data = zytqybBeen.getData();
                    if (data == null) {
                        ToastUtils.showShort("没查询到数据");
                    } else {
                        addAllMarker(data);
                    }
                }, throwable -> {
                    LogUtils.e(throwable);
                    ToastUtils.showShort(getString(R.string.getInfo_error_toast));
                });
    }

    private void clearAllMarker() {
        if (markerList != null) {
            for (int i = 0; i < markerList.size(); i++) {
                markerList.get(i).destroy();
            }
        }
    }

    private void addAllMarker(List<ZytqybBeen.DataBean> data) {
        for (int i = 0; i < data.size(); i++) {
            String lat = data.get(i).getLat();
            Double aDouble = Double.valueOf(lat);
            String lon = data.get(i).getLon();
            Double aDouble1 = Double.valueOf(lon);
            String cnty = data.get(i).getCnty();
            String city = data.get(i).getCity();
            String weatherDes = data.get(i).getWeatherDes();
            List<ZytqybBeen.DataBean.InfoArrayBean> infoArray = data.get(i).getInfoArray();
            String shi = city + cnty;
            String forecast_time = data.get(i).getDatetime();
            Map<String, Object> stringObjectMap = new HashMap<>();
            stringObjectMap.put("shi", shi);
            stringObjectMap.put("forecast_time", forecast_time);
            if (infoArray.size() == 1) {
                stringObjectMap.put("infoArray_key", infoArray.get(0).getInfo_key());
                stringObjectMap.put("infoArray_values", infoArray.get(0).getInfo_values());
            }
            if (infoArray.size() == 2) {
                stringObjectMap.put("infoArray_key", infoArray.get(0).getInfo_key());
                stringObjectMap.put("infoArray_values", infoArray.get(0).getInfo_values());
                stringObjectMap.put("infoArray_key1", infoArray.get(1).getInfo_key());
                stringObjectMap.put("infoArray_values1", infoArray.get(1).getInfo_values());
            }
            Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.location);
            if (weatherDes.equals("冰雹"))
                bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.bingbao);
            if (weatherDes.equals("积冰"))
                bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.jibing);
            if (weatherDes.equals("积雪"))
                bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.jixue);
            if (weatherDes.equals("极大风"))
                bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.jidafeng);
            if (weatherDes.equals("降水"))
                bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.jiangshui);
            if (weatherDes.equals("雷暴"))
                bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.leibao);
            if (weatherDes.equals("龙卷风"))
                bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.longjuanfeng);
            if (weatherDes.equals("雾"))
                bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.wu);


            View parentView = LayoutInflater.from(context).inflate(R.layout.gaode_marker_image, null, false);
            ImageView markerView =parentView.findViewById(R.id.mark_view);
            bitmap=PicUtils.setImgSize(bitmap,60,80);
            markerView.setImageBitmap(bitmap);

            JSONObject makerJson = JSONObject.parseObject(JSON.toJSONString(stringObjectMap));
            Marker marker = mAMap.addMarker(new MarkerOptions().anchor(0.5f, 0.5f)
                    .position(new LatLng(aDouble,//设置纬度
                            aDouble1))//设置经度
                    .title("")//设置标题
                    .snippet(makerJson.toJSONString())//设置内容
                    .draggable(false) //设置Marker可拖动
                    .icon(BitmapDescriptorFactory.fromView(parentView)));

            markerList.add(marker);
        }
    }

    private void initBottomTime() {
        Date now = new Date();
        long nowLong = now.getTime();
        long oneDayBeforeLong = nowLong - 24 * 3600 * 1000;
        Date oneDayBefore = new Date(oneDayBeforeLong);
        String oneDayBeforeStr = getTime(oneDayBefore);
        String nowTimeStr = getTime(now);

        data_qi_bottom.setText(oneDayBeforeStr + " 00:00:00");
        data_zhi_bottom.setText(nowTimeStr + " 23:59:59");
        bottom_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rili_lay.setVisibility(View.VISIBLE);
            }
        });
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
                getZytqyb(data_qi_bottom.getText().toString(), data_zhi_bottom.getText().toString());
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
    public View getInfoWindow(Marker marker) {
        View infoWindow = getLayoutInflater().inflate(
                R.layout.zytqyb_xq_layout, null);
        render(marker, infoWindow);
        return infoWindow;
    }

    //江西省居中显示
    private void centerJiangxi(){
        LatLngBounds bounds = new LatLngBounds.Builder()
                .include(new LatLng(24.43704147338867, 118.68101043701172))
                .include(new LatLng(30.1299808502, 113.52340240478516)).build();
        mAMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 40));
    }


    /**
     * 自定义infowinfow窗口
     */
    public void render(Marker marker, View view) {
        String snippet = marker.getSnippet();
        JSONObject jsonObject = JSONObject.parseObject(snippet);
        Map<String, Object> itemMap = JSONObject.toJavaObject(jsonObject, Map.class);
        String infoArray_key = (String) itemMap.get("infoArray_key");
        String infoArray_values = (String)  itemMap.get("infoArray_values");
        String infoArray_key1 = (String)  itemMap.get("infoArray_key1");
        String infoArray_values1 = (String)  itemMap.get("infoArray_values1");
        List<ZytqybBeen.DataBean.InfoArrayBean> infoArray = new ArrayList<>();
        if (infoArray_key != null) {
            ZytqybBeen.DataBean.InfoArrayBean infoData = new ZytqybBeen.DataBean.InfoArrayBean();
            infoData.setInfo_key(infoArray_key + ":");
            infoData.setInfo_values(infoArray_values);
            infoArray.add(infoData);
        }

        if (infoArray_key1 != null) {
            ZytqybBeen.DataBean.InfoArrayBean infoData = new ZytqybBeen.DataBean.InfoArrayBean();
            infoData.setInfo_key(infoArray_key1 + ":");
            infoData.setInfo_values(infoArray_values1);
            infoArray.add(infoData);
        }
        RecyclerView recyclerView = view.findViewById(R.id.zytq_xq_recycle);
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) recyclerView.getLayoutParams();
        int height = ShipeiUtils.getHeight(context);
        double v = height * 0.5;
        layoutParams.height = Integer.parseInt(new java.text.DecimalFormat("0").format(v));
        recyclerView.setLayoutParams(layoutParams);
        LinearLayoutManager manager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        ZytqybAdapter adapter = new ZytqybAdapter(infoArray);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);

        String shi = (String) itemMap.get("shi");
        String forecast_time = (String)  itemMap.get("forecast_time");
        TextView shi_tv = view.findViewById(R.id.shi);
        shi_tv.setText(shi);
        TextView forecast_time_tv = view.findViewById(R.id.forecast_time);
        forecast_time_tv.setText("起报时间:" + forecast_time);
        ImageView windowCancle = view.findViewById(R.id.cancle_xq);
        windowCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                marker.hideInfoWindow();
                centerJiangxi();
            }
        });

    }

    @Override
    public View getInfoContents(Marker marker) {
        return null;
    }
}
