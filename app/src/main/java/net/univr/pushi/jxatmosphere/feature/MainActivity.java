package net.univr.pushi.jxatmosphere.feature;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.percent.PercentRelativeLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;

import net.univr.pushi.jxatmosphere.R;
import net.univr.pushi.jxatmosphere.base.BaseActivity;
import net.univr.pushi.jxatmosphere.beens.BdskBeen;
import net.univr.pushi.jxatmosphere.beens.DutyBeen;
import net.univr.pushi.jxatmosphere.remote.RetrofitHelper;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.home_drawer_layout)
    DrawerLayout drawerLayout;
    @BindView(R.id.nav)
    NavigationView navigationView;
    @BindView(R.id.home_toolbar)
    Toolbar toolbar;
    @BindView(R.id.more_info)
    ImageView imageView;
    @BindView(R.id.see_more_schedule)
    TextView see_more_chedule;
    @BindView(R.id.temp)
    TextView temp;

    @BindView(R.id.main_feture_item0)
    PercentRelativeLayout mainFetureItem0;
    @BindView(R.id.main_feture_item1)
    PercentRelativeLayout mainFetureItem1;
    @BindView(R.id.main_feture_item2)
    PercentRelativeLayout mainFetureItem2;
    @BindView(R.id.main_feture_item3)
    PercentRelativeLayout mainFetureItem3;
    @BindView(R.id.main_feture_item4)
    PercentRelativeLayout mainFetureItem4;

    @BindView(R.id.main_content_item1_2)
    PercentRelativeLayout typhoonMonitoring;

    //信息员反馈
    @BindView(R.id.main_content_item4_3)
    PercentRelativeLayout info_feddback;
    //预报员评分
    @BindView(R.id.main_content_item4_2)
    PercentRelativeLayout forcast_score;
    //决策服务
    @BindView(R.id.main_content_item4_1)
    PercentRelativeLayout decision_service;
    //帮助
    @BindView(R.id.main_content_item4_4)
    PercentRelativeLayout help;
    //高空地面观测
    @BindView(R.id.main_content_item1_4)
    PercentRelativeLayout gkdmgc;
    //地面常规监测
    @BindView(R.id.main_content_item1_3)
    PercentRelativeLayout dmcgjc;
    //卫星雷达
    @BindView(R.id.main_content_item1_1)
    PercentRelativeLayout ldpt_Wx;
    @BindView(R.id.main_content_item2_2)
    PercentRelativeLayout Zytqyb;
    //ec细网格
    @BindView(R.id.main_content_item0_1)
    PercentRelativeLayout ecxwg;
    //格点预报
    @BindView(R.id.main_content_item2_4)
    PercentRelativeLayout gdyb;
    //气象风险预报
    @BindView(R.id.main_content_item3_1)
    PercentRelativeLayout qxfx;
    //全省文字报
    @BindView(R.id.main_content_item3_2)
    PercentRelativeLayout genefore;
    //格点图形预报
    @BindView(R.id.main_content_item3_3)
    PercentRelativeLayout gdybtx;
    //短时临近预报
    @BindView(R.id.main_content_item3_4)
    PercentRelativeLayout dsljyb;
    //wtf快速循环
    @BindView(R.id.main_content_item0_4)
    PercentRelativeLayout wtfdlxh;
    //雷达预报
    @BindView(R.id.main_content_item2_3)
    PercentRelativeLayout radarForecast;
    //预警信号
    @BindView(R.id.main_content_item2_1)
    PercentRelativeLayout YujinXinhao;


    @BindView(R.id.duty_name)
    TextView leader;
    @BindView(R.id.duty_name1)
    TextView chief;
    @BindView(R.id.duty_time)
    TextView leader_time;
    @BindView(R.id.duty_time1)
    TextView chief_time;
    @BindView(R.id.duty_date)
    TextView leader_date;
    @BindView(R.id.duty_date1)
    TextView chief_date;
    @BindView(R.id.main_loc)
    TextView location;
    @BindView(R.id.more_weath)
    LinearLayout more_weath;

    //声明mlocationClient对象
    public AMapLocationClient mlocationClient;
    //声明mLocationOption对象
    public AMapLocationClientOption mLocationOption;
    String lat;
    String lon;
    String address;

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        final float scale = context.getResources().getDisplayMetrics().density;
        int i = (int) (55 * scale + 0.5f);

//        PgyUpdateManager.register(this);
        initDuty();
//        cheackVersion();
        initHeight();
        rememberLoginState();
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        imageView.setOnClickListener(this);
        see_more_chedule.setOnClickListener(this);
        info_feddback.setOnClickListener(this);
        forcast_score.setOnClickListener(this);
        decision_service.setOnClickListener(this);
        typhoonMonitoring.setOnClickListener(this);
        gdyb.setOnClickListener(this);
        help.setOnClickListener(this);
        gkdmgc.setOnClickListener(this);
        dmcgjc.setOnClickListener(this);
        ldpt_Wx.setOnClickListener(this);
        ecxwg.setOnClickListener(this);
        qxfx.setOnClickListener(this);
        genefore.setOnClickListener(this);
        gdybtx.setOnClickListener(this);
        wtfdlxh.setOnClickListener(this);
        more_weath.setOnClickListener(this);
        radarForecast.setOnClickListener(this);
        YujinXinhao.setOnClickListener(this);
        Zytqyb.setOnClickListener(this);
        dsljyb.setOnClickListener(this);
        navigationView.setNavigationItemSelectedListener(item -> {
            int itemId = item.getItemId();

            if (itemId == R.id.nav_set) {
                Intent intent = new Intent(context, SettingActivity.class);
                startActivity(intent);
            }
            if (itemId == R.id.nav_about) {
                Intent intent = new Intent(context, AboutOursActivity.class);
                startActivity(intent);
            }
            drawerLayout.closeDrawer(navigationView);
            return true;
        });

        View nav_header = navigationView.inflateHeaderView(R.layout.head_slip_layout);
        LinearLayout user_linear = nav_header.findViewById(R.id.user_linear);
        user_linear.setOnClickListener(v -> {
            Intent intent = new Intent(context, PersonalInfoActivity.class);
            startActivity(intent);
            drawerLayout.closeDrawer(navigationView);
        });


        List<String> permissionList = new ArrayList<>();

        //判断是否已经获取相应权限                                                              对应权限
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            //相应操作
            initLocation();
        }
// 若没有获得相应权限，则弹出对话框获取
        else {
            permissionList.add(Manifest.permission.ACCESS_COARSE_LOCATION);//申请权限
//            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 0);
        }

//        //判断是否已经获取相应权限                                                              对应权限
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            //相应操作
        }
// 若没有获得相应权限，则弹出对话框获取
        else {
            permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);//申请权限//申请权限
//            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }

        if (!permissionList.isEmpty()) {  //申请的集合不为空时，表示有需要申请的权限
            ActivityCompat.requestPermissions(MainActivity.this, permissionList.toArray(new String[permissionList.size()]), 1);
        }

    }

    private void cheackVersion() throws Exception {
        String versionName = getVersionName();
        RetrofitHelper.getUserAPI()
                .cheackAppVersion(versionName)
                .compose(bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(appVersionBeen -> {
                    String version = appVersionBeen.getData().getVersion();
                    String versionName1 = null;
                    try {
                        versionName1 = getVersionName();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if(Double.valueOf(versionName1)<Double.valueOf(version)){
                    }
                    appVersionBeen.getData().getUrl();
                }, throwable -> {
                    LogUtils.e(throwable);
                    ToastUtils.showShort(getString(R.string.getInfo_error_toast));
                });
    }

    private void rememberLoginState() {
        SPUtils.getInstance().put("isFirstLogin", false);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            //相应操作
            initLocation();
        }
    }

    private void initDuty() {
        RetrofitHelper.getFeedbackAPI()
                .getDuty("today")
                .compose(bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(DutyBeen -> {
                    List<DutyBeen.DataBean.DutysBean> dutys = DutyBeen.getData().getDutys();
                    for (int i = 0; i < dutys.size(); i++) {
                        String duty = dutys.get(i).getDuty();
                        String name = dutys.get(i).getName();
                        String property = dutys.get(i).getProperty();
                        String date = dutys.get(i).getDate();
                        if (duty.equals("带班领导")) {
                            leader.setText(name + "(" + duty + ")");
                            leader_time.setText(property);
                            leader_date.setText(date);
                        }
                        if (duty.equals("预报首席")) {
                            chief.setText(name + "(" + duty + ")");
                            chief_time.setText(property);
                            chief_date.setText(date);
                        }
                    }


                }, throwable -> {
                    LogUtils.e(throwable);
                    ToastUtils.showShort(getString(R.string.getInfo_error_toast));
                });
    }


    private void initHeight() {
        DisplayMetrics dm = getResources().getDisplayMetrics();
        int width = dm.widthPixels;
        int itemWidth = getInt(width * 0.235);
        ViewGroup.LayoutParams layoutParams0 = mainFetureItem0.getLayoutParams();
        layoutParams0.height = itemWidth;
        ViewGroup.LayoutParams layoutParams1 = mainFetureItem1.getLayoutParams();
        layoutParams1.height = itemWidth;
        ViewGroup.LayoutParams layoutParams2 = mainFetureItem2.getLayoutParams();
        layoutParams2.height = itemWidth;
        ViewGroup.LayoutParams layoutParams3 = mainFetureItem3.getLayoutParams();
        layoutParams3.height = itemWidth;
        ViewGroup.LayoutParams layoutParams4 = mainFetureItem4.getLayoutParams();
        layoutParams4.height = itemWidth;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.more_info:
                if (drawerLayout.isDrawerOpen(navigationView)) {
                    drawerLayout.closeDrawer(navigationView);
                } else {
                    drawerLayout.openDrawer(navigationView);
                }
                break;

            case R.id.see_more_schedule:
                Intent intent = new Intent(context, WorkScheduleActivity.class);
                startActivity(intent);
                break;
            case R.id.main_content_item4_4:
                Intent intent44 = new Intent(context, HelpActivity.class);
                startActivity(intent44);
                break;

            case R.id.main_content_item4_3:
                Intent intent43 = new Intent(context, MessengerFeedbackActivity.class);
                startActivity(intent43);
                break;
            case R.id.main_content_item4_2:
                Intent intent42 = new Intent(context, ForecasterScoreActivity.class);
                startActivity(intent42);
                break;
            case R.id.main_content_item4_1:
                Intent intent41 = new Intent(context, DecisionActivity.class);
                startActivity(intent41);
                break;
            case R.id.main_content_item1_2:
                Intent intent12 = new Intent(context, TyphoonMonitoringActivity.class);
                startActivity(intent12);
                break;
            case R.id.main_content_item1_4:
                Intent intent14 = new Intent(context, GKDMGCActivity.class);
                startActivity(intent14);
                break;
            case R.id.main_content_item1_3:
                Intent intent13 = new Intent(context, DMCGJCActivity.class);
                startActivity(intent13);
                break;
            case R.id.main_content_item1_1:
                Intent intent11 = new Intent(context, LdptRadarActivity.class);
                startActivity(intent11);
                break;
            case R.id.main_content_item2_4:
                Intent intent24 = new Intent(context, GdybActivity.class);
                startActivity(intent24);
                break;
            case R.id.main_content_item0_1:
                Intent intent01 = new Intent(context, EcxwgActivity.class);
                startActivity(intent01);
                break;
            case R.id.main_content_item3_1:
                Intent intent31 = new Intent(context, WeathWarnActivity.class);
                startActivity(intent31);
                break;
            case R.id.main_content_item3_2:
                Intent intent32 = new Intent(context, GeneforeActivity.class);
                startActivity(intent32);
                break;
            case R.id.main_content_item3_3:
                Intent intent33 = new Intent(context, GdybtxActivity.class);
                startActivity(intent33);
                break;
            case R.id.main_content_item0_4:
                Intent intent04 = new Intent(context, WtfRapidActivity.class);
                startActivity(intent04);
                break;
            case R.id.more_weath:
                Intent intentMoreWeath = new Intent(context, WeathMainActivity.class);
                intentMoreWeath.putExtra("address", address);
                intentMoreWeath.putExtra("lat", lat);
                intentMoreWeath.putExtra("lon", lon);
                startActivity(intentMoreWeath);
                break;
            case R.id.main_content_item2_2:
                Intent zytqyb = new Intent(context, ZytqybActivity.class);
                startActivity(zytqyb);
                break;
            case R.id.main_content_item2_3:
                Intent radarForecast = new Intent(context, RadarForecastActivity.class);
                startActivity(radarForecast);
                break;
            case R.id.main_content_item2_1:
                Intent yujinXinhao = new Intent(context, YujingActivity.class);
                startActivity(yujinXinhao);
                break;
            case R.id.main_content_item3_4:
                Intent dsljyb = new Intent(context, DsljybActivity.class);
                startActivity(dsljyb);
                break;
        }

    }


    //四舍五入把double转化int整型，0.5进一，小于0.5不进一
    public static int getInt(double number) {
        BigDecimal bd = new BigDecimal(number).setScale(0, BigDecimal.ROUND_HALF_UP);
        return Integer.parseInt(bd.toString());
    }

    //四舍五入把double转化为int类型整数,0.5也舍去,0.51进一
//        public static int DoubleFormatInt(Double dou){
//            DecimalFormat df = new DecimalFormat("######0"); //四色五入转换成整数
//            return Integer.parseInt(df.format(dou));
//        }

    //去掉小数凑整:不管小数是多少，都进一
//        public static int ceilInt(double number){
//            return (int) Math.ceil(number);
//        }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0) { //安全写法，如果小于0，肯定会出错了
            for (int i = 0; i < grantResults.length; i++) {

                int grantResult = grantResults[i];
                if (grantResult == PackageManager.PERMISSION_DENIED) { //这个是权限拒绝
                    String s = permissions[i];
                    Toast.makeText(this, s + "权限被拒绝了", Toast.LENGTH_SHORT).show();
                } else { //授权成功了
                    //do Something
                    if (i == 0) initLocation();
                }
            }
        }
    }

    private void initLocation() {
        mlocationClient = new AMapLocationClient(this);
//初始化定位参数
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
                        //解析定位结果
//                        aMapLocation.getLocationType();//获取当前定位结果来源，如网络定位结果，详见定位类型表
//                        aMapLocation.getLatitude();//获取纬度
//                        aMapLocation.getLongitude();//获取经度
//                        aMapLocation.getAccuracy();//获取精度信息

                        address = aMapLocation.getProvince() + aMapLocation.getCity();
                        lat = String.valueOf(aMapLocation.getLatitude());//获取纬度
                        lon = String.valueOf(aMapLocation.getLongitude());//获取经度
                        location.setText(address);
                        getTemp();
//                        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//                        Date date = new Date(aMapLocation.getTime());
//                        df.format(date);//定位时间
                    } else {
                        //显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
                        Log.e("AmapError", "location Error, ErrCode:"
                                + aMapLocation.getErrorCode() + ", errInfo:"
                                + aMapLocation.getErrorInfo());
                        location.setText("定位失败||请检查定位权限和位置信息");
                    }
                }
            }
        });


    }


    public void getTemp() {

        RetrofitHelper.getForecastWarn()
                .getbdsk(String.valueOf(lat), String.valueOf(lon))
                .compose(bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(bdskBeen -> {
                    BdskBeen.DataBean data = bdskBeen.getData().get(0);
                    temp.setText(data.getTEM() + "℃");

                }, throwable -> {
                    LogUtils.e(throwable);
                    ToastUtils.showShort(getString(R.string.getInfo_error_toast));
                });
    }

    private String getVersionName() throws Exception {
        // 获取packagemanager的实例
        PackageManager packageManager = getPackageManager();
        // getPackageName()是你当前类的包名，0代表是获取版本信息
        PackageInfo packInfo = packageManager.getPackageInfo(getPackageName(), 0);
        String version = packInfo.versionName;
        return version;
    }


}
