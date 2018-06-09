package net.univr.pushi.jxatmosphere.feature;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;

import net.univr.pushi.jxatmosphere.R;
import net.univr.pushi.jxatmosphere.adapter.YunjinAdapter;
import net.univr.pushi.jxatmosphere.base.BaseActivity;
import net.univr.pushi.jxatmosphere.beens.YuJinXinhaoBeen;
import net.univr.pushi.jxatmosphere.remote.RetrofitHelper;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class YujingActivity extends BaseActivity {
    @BindView(R.id.yujinRecycle)
    RecyclerView mRecyclerView;
    YunjinAdapter mAdapter;


//    @BindView(R.id.map_view)
//    MapView mapView;
//    Basemap slbasemap;

    @BindView(R.id.back)
    ImageView back;


    @Override
    public int getLayoutId() {
        return R.layout.activity_yujing;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        super.initViews(savedInstanceState);
        initRecycleView();
        getYujinInfo();
//        initArcgis();
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

//    private void initArcgis() {
//        initArcgisUtils();
//        initBaseMap();//初始化底图
//        initGps();//初始化gps
//    }

//    private void initBaseMap() {
//        if (slbasemap == null) {
//            slbasemap = new Basemap();
//            WebTiledLayer webTiledLayer = TianDiTuMethodsClass.CreateTianDiTuTiledLayer
//                    (TianDiTuMethodsClass.LayerType.TIANDITU_VECTOR_2000);
//            WebTiledLayer webTiledLayer1 = TianDiTuMethodsClass.CreateTianDiTuTiledLayer
//                    (TianDiTuMethodsClass.LayerType
//                            .TIANDITU_VECTOR_ANNOTATION_CHINESE_2000);
//            slbasemap.getBaseLayers().add(webTiledLayer);
//            slbasemap.getBaseLayers().add(webTiledLayer1);
//            if (mapView.getMap() == null) {
//                mapView.setMap(new ArcGISMap());
//            }
//            if (mapView.getMap().getBasemap() != slbasemap) {
//                mapView.getMap().setBasemap(slbasemap);
//            }
//        }
//    }

    private void initRecycleView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        List<YuJinXinhaoBeen.DataBean> dataBeans = new ArrayList<>();
        mAdapter = new YunjinAdapter(dataBeans, context);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.layout:
                        List data = adapter.getData();
                        YuJinXinhaoBeen.DataBean o = ((YuJinXinhaoBeen.DataBean) data.get(position));
                        Intent intent = new Intent(YujingActivity.this, YujinInfoActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putParcelable("data", o);
                        intent.putExtras(bundle);
                        startActivity(intent);
                }
            }
        });
    }

    private void getYujinInfo() {
        RetrofitHelper.getForecastWarn()
                .getYujinInfo()
                .compose(bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(yunjinInfo -> {
                    List<YuJinXinhaoBeen.DataBean> data = yunjinInfo.getData();
                    mAdapter.setNewData(data);
                }, throwable -> {
                    LogUtils.e(throwable);
                    ToastUtils.showShort(getString(R.string.getInfo_error_toast));
                });
    }


//    private void initArcgisLocation() {
//        LogUtils.i("initArcgisLocation");
//        //获取arcgis定位管理器
//        final LocationDisplay locationDisplay = mapView.getLocationDisplay();
//        //设置arcgis定位显示方式
//        locationDisplay.setAutoPanMode(LocationDisplay.AutoPanMode.NAVIGATION);
//        //设置比例尺
//        locationDisplay.setInitialZoomScale(50000);
////        设置定位点在屏幕百分比
//        locationDisplay.setNavigationPointHeightFactor(0.5f);
//        //启动定位
//        locationDisplay.startAsync();
//        locationDisplay.addLocationChangedListener(new LocationDisplay.LocationChangedListener() {
//            @Override
//            public void onLocationChanged(LocationDisplay.LocationChangedEvent locationChangedEvent) {
//                Point mapLocation = locationDisplay.getMapLocation();
//                mapView.setViewpointCenterAsync(mapLocation);
//            }
//        });

}

/**
 * 初始化gps
 */
//    private void initGps() {
//        LogUtils.i("initGps");
//        LocationManager locationManager = (LocationManager) context.getSystemService(Context
//                .LOCATION_SERVICE);
//        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) &&
//                locationManager.isProviderEnabled(locationManager.NETWORK_PROVIDER)) {
//            initArcgisLocation();
//            return;
//        }
//        new AlertDialog.Builder(context)
//                .setTitle("GPS定位未开启")
//                .setMessage(("是否打开GPS定位"))
//                .setPositiveButton("确定", (dialog, which) -> {
//                    Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
//                    startActivityForResult(intent, 1); // 此为设置完成后返回到获取界面
//                })
//                .setNegativeButton("取消", null)
//                .show();
//    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        switch (requestCode) {
//            case 1:
//                initGps();
//                break;
//        }
//    }
//
//    private void initArcgisUtils() {
//        ArcGISRuntimeEnvironment.setLicense("runtimelite,1000,rud8065403504,none," +
//                "RP5X0H4AH7CLJ9HSX018");
//        mapView.setAttributionTextVisible(false);
//    }
//}

