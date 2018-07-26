package net.univr.pushi.jxatmosphere.feature;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.percent.PercentRelativeLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
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
import com.bumptech.glide.Glide;
import com.vector.update_app.UpdateAppBean;
import com.vector.update_app.UpdateAppManager;
import com.vector.update_app.UpdateCallback;
import com.vector.update_app.service.DownloadService;

import net.univr.pushi.jxatmosphere.R;
import net.univr.pushi.jxatmosphere.base.BaseActivity;
import net.univr.pushi.jxatmosphere.beens.BdskBeen;
import net.univr.pushi.jxatmosphere.beens.DutyBeen;
import net.univr.pushi.jxatmosphere.remote.ApiConstants;
import net.univr.pushi.jxatmosphere.remote.RetrofitHelper;
import net.univr.pushi.jxatmosphere.utils.CProgressDialogUtils;
import net.univr.pushi.jxatmosphere.utils.HProgressDialogUtils;
import net.univr.pushi.jxatmosphere.utils.OkGoUpdateHttpUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends BaseActivity implements View.OnClickListener, View.OnTouchListener {
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
    @BindView(R.id.main_content_item0_2)
    PercentRelativeLayout grapes_ms;
    @BindView(R.id.main_content_item0_3)
    PercentRelativeLayout hdqyms;
    @BindView(R.id.notice_img)
    ImageView notice;


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

    private boolean isShowDownloadProgress;

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
//        PgyUpdateManager.register(this);
        cheackVersion();
        initDuty();
        initHeight();
        initNotice();
        rememberLoginState();
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        imageView.setOnClickListener(this);
        see_more_chedule.setOnClickListener(this);
        info_feddback.setOnClickListener(this);
        info_feddback.setOnTouchListener(this);
        forcast_score.setOnClickListener(this);
        forcast_score.setOnTouchListener(this);
        decision_service.setOnClickListener(this);
        decision_service.setOnTouchListener(this);
        typhoonMonitoring.setOnClickListener(this);
        typhoonMonitoring.setOnTouchListener(this);
        gdyb.setOnClickListener(this);
        gdyb.setOnTouchListener(this);
        help.setOnClickListener(this);
        help.setOnTouchListener(this);
        gkdmgc.setOnClickListener(this);
        gkdmgc.setOnTouchListener(this);
        dmcgjc.setOnClickListener(this);
        dmcgjc.setOnTouchListener(this);
        ldpt_Wx.setOnClickListener(this);
        ldpt_Wx.setOnTouchListener(this);
        ecxwg.setOnClickListener(this);
        ecxwg.setOnTouchListener(this);
        qxfx.setOnClickListener(this);
        qxfx.setOnTouchListener(this);
        genefore.setOnClickListener(this);
        genefore.setOnTouchListener(this);
        gdybtx.setOnClickListener(this);
        gdybtx.setOnTouchListener(this);
        wtfdlxh.setOnClickListener(this);
        wtfdlxh.setOnTouchListener(this);
        more_weath.setOnClickListener(this);
        radarForecast.setOnClickListener(this);
        radarForecast.setOnTouchListener(this);
        YujinXinhao.setOnClickListener(this);
        YujinXinhao.setOnTouchListener(this);
        Zytqyb.setOnClickListener(this);
        Zytqyb.setOnTouchListener(this);
        dsljyb.setOnClickListener(this);
        dsljyb.setOnTouchListener(this);
        grapes_ms.setOnClickListener(this);
        grapes_ms.setOnTouchListener(this);
        hdqyms.setOnClickListener(this);
        hdqyms.setOnTouchListener(this);
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

    private void initNotice() {
        Glide.with(context).load(R.drawable.noti).into(notice);
    }


    private void cheackVersion() {
        String versionName = getVersionName();
        RetrofitHelper.getUserAPI()
                .cheackAppVersion(versionName)
                .compose(bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(appVersionBeen -> {
                    if (appVersionBeen.getErrcode().equals("0"))
                        update();
                    else ;
                }, throwable -> {
                    LogUtils.e(throwable);
//                    ToastUtils.showShort(getString(R.string.getInfo_error_toast));
                });
    }

    private void update() {
        isShowDownloadProgress = true;
        String path = Environment.getExternalStorageDirectory().getAbsolutePath();
        Map<String, String> params = new HashMap<String, String>();
        params.put("version",
                getVersionName());
        new UpdateAppManager
                .Builder()
                //必须设置，当前Activity
                .setActivity(this)
                //必须设置，实现httpManager接口的对象
                .setHttpManager(new OkGoUpdateHttpUtil())
                //必须设置，更新地址
                .setUpdateUrl(ApiConstants.API_BASE_URL + "appVersionCheackAction.do")
                //以下设置，都是可选
                //设置请求方式，默认get
                .setPost(false)
                //添加自定义参数，默认version=1.0.0（app的versionName）；apkKey=唯一表示（在AndroidManifest.xml配置）
                .setParams(params)
                //设置点击升级后，消失对话框，默认点击升级后，对话框显示下载进度
//                .hideDialogOnDownloading(false)
                //设置头部，不设置显示默认的图片，设置图片后自动识别主色调，然后为按钮，进度条设置颜色
//                .setTopPic(R.drawable.top_8)
                //为按钮，进度条设置颜色，默认从顶部图片自动识别。
//                .setThemeColor(0xffffac5d)
                //设置apk下砸路径，默认是在下载到sd卡下/Download/1.0.0/test.apk
                .setTargetPath(path)
                //设置appKey，默认从AndroidManifest.xml获取，如果，使用自定义参数，则此项无效
                //.setAppKey("ab55ce55Ac4bcP408cPb8c1Aaeac179c5f6f")
                //不显示通知栏进度条
//                .dismissNotificationProgress()
                //是否忽略版本
                //.showIgnoreVersion()
                .build()
                //检测是否有新版本
                .checkNewApp(new UpdateCallback() {
                    /**
                     * 解析json,自定义协议
                     *
                     * @param json 服务器返回的json
                     * @return UpdateAppBean
                     */
                    @Override
                    protected UpdateAppBean parseJson(String json) {
                        UpdateAppBean updateAppBean = new UpdateAppBean();
                        try {
                            JSONObject jsonObject = new JSONObject(json);
                            JSONObject object = jsonObject.optJSONObject("data");
                            String url = object.optString("url");
                            String describe = object.optString("describe");
                            String version = object.optString("version");
                            String update;
                            if (Double.valueOf(getVersionName()) < Double.valueOf(version)) {
                                update = "Yes";
                            } else {
                                update = "No";
                            }
                            updateAppBean
                                    //（必须）是否更新Yes,No
                                    .setUpdate(update)
                                    //（必须）新版本号，
                                    .setNewVersion(version)
                                    //（必须）下载地址
                                    .setApkFileUrl(url)
                                    //（必须）更新内容
                                    .setUpdateLog(describe)
                                    //大小，不设置不显示大小，可以不设置
//                                    .setTargetSize(jsonObject.optString("target_size"))
                                    //是否强制更新，可以不设置
                                    .setConstraint(false);
                            //设置md5，可以不设置
//                                    .setNewMd5(jsonObject.optString("new_md51"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        return updateAppBean;
                    }

                    /**
                     * 有新版本
                     *
                     * @param updateApp        新版本信息
                     * @param updateAppManager app更新管理器
                     */
                    @Override
                    public void hasNewApp(UpdateAppBean updateApp, UpdateAppManager updateAppManager) {
                        //自定义对话框
                        showDiyDialog(updateApp, updateAppManager);

                    }


                    /**
                     @Override protected void hasNewApp(UpdateAppBean updateApp, UpdateAppManager updateAppManager) {
                     updateAppManager.showDialogFragment();
                     }

                     /**
                      * 网络请求之前
                     */
                    @Override
                    public void onBefore() {
                        CProgressDialogUtils.showProgressDialog(MainActivity.this);
                    }

                    /**
                     * 网路请求之后
                     */
                    @Override
                    public void onAfter() {
                        CProgressDialogUtils.cancelProgressDialog(MainActivity.this);
                    }

                    /**
                     * 没有新版本
                     */
                    @Override
                    public void noNewApp(String error) {
                        Toast.makeText(MainActivity.this, "没有新版本", Toast.LENGTH_SHORT).show();
                    }
                });

    }

    private void showDiyDialog(final UpdateAppBean updateApp, final UpdateAppManager updateAppManager) {
//        String targetSize = updateApp.getTargetSize();
        String updateLog = updateApp.getUpdateLog();

        String msg = "";

//        if (!TextUtils.isEmpty(targetSize)) {
//            msg = "新版本大小：" + targetSize + "\n\n";
//        }

        if (!TextUtils.isEmpty(updateLog)) {
            msg += updateLog;
        }

        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle(String.format("检测到新版本" + updateApp.getNewVersion() + "是否更新？"))
                .setMessage(msg)
                .setPositiveButton("升级", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //显示下载进度
                        if (isShowDownloadProgress) {
                            updateAppManager.download(new DownloadService.DownloadCallback() {
                                @Override
                                public void onStart() {
                                    HProgressDialogUtils.showHorizontalProgressDialog(MainActivity.this, "下载进度", false);
                                }

                                /**
                                 * 进度
                                 *
                                 * @param progress  进度 0.00 -1.00 ，总大小
                                 * @param totalSize 总大小 单位B
                                 */
                                @Override
                                public void onProgress(float progress, long totalSize) {
                                    HProgressDialogUtils.setProgress(Math.round(progress * 100));
                                }

                                /**
                                 *
                                 * @param total 总大小 单位B
                                 */
                                @Override
                                public void setMax(long total) {

                                }


                                @Override
                                public boolean onFinish(File file) {
                                    HProgressDialogUtils.cancel();
                                    return true;
                                }

                                @Override
                                public void onError(String msg) {
                                    Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                                    HProgressDialogUtils.cancel();

                                }

                                @Override
                                public boolean onInstallAppAndAppOnForeground(File file) {
                                    return false;
                                }
                            });
                        } else {
                            //不显示下载进度
                            updateAppManager.download();
                        }
                        dialog.dismiss();
                    }
                })
                .setNegativeButton("暂不升级", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .create();
        dialog.show();
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.BLUE);
        dialog.getButton(DialogInterface.BUTTON_NEGATIVE).setTextColor(Color.BLACK);
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
//                    ToastUtils.showShort(getString(R.string.getInfo_error_toast));
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

//                        address = aMapLocation.getProvince() + aMapLocation.getCity();
                        address = aMapLocation.getAoiName();
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
//                    ToastUtils.showShort(getString(R.string.getInfo_error_toast));
                });
    }

    private String getVersionName() {
        // 获取packagemanager的实例
        PackageManager packageManager = getPackageManager();
        // getPackageName()是你当前类的包名，0代表是获取版本信息
        PackageInfo packInfo = null;
        try {
            packInfo = packageManager.getPackageInfo(getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        String version = packInfo.versionName;
        return version;
    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (v.getId()) {
            case R.id.main_content_item1_1:
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    ((PercentRelativeLayout) v).setBackground(getResources().getDrawable(R.drawable.main_layout_select_bg));
                    ImageView childAt = (ImageView) ldpt_Wx.getChildAt(0);
                    childAt.setImageDrawable(getResources().getDrawable(R.drawable.wt_monitor_cl_1));
                    TextView childAt1 = (TextView) ldpt_Wx.getChildAt(1);
                    childAt1.setTextColor(getResources().getColor(R.color.white));
                }
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    ((PercentRelativeLayout) v).setBackground(getResources().getDrawable(R.drawable.main_layout_select_bg1));
                    ImageView childAt = (ImageView) ldpt_Wx.getChildAt(0);
                    childAt.setImageDrawable(getResources().getDrawable(R.drawable.wt_monitor_1));
                    TextView childAt1 = (TextView) ldpt_Wx.getChildAt(1);
                    childAt1.setTextColor(getResources().getColor(R.color.black));
                }
                if (event.getAction() == MotionEvent.ACTION_MOVE) {
                    ((PercentRelativeLayout) v).setBackground(getResources().getDrawable(R.drawable.main_layout_select_bg1));
                    ImageView childAt = (ImageView) ldpt_Wx.getChildAt(0);
                    childAt.setImageDrawable(getResources().getDrawable(R.drawable.wt_monitor_1));
                    TextView childAt1 = (TextView) ldpt_Wx.getChildAt(1);
                    childAt1.setTextColor(getResources().getColor(R.color.black));
                }
                if (event.getAction() == MotionEvent.ACTION_CANCEL) {
                    ((PercentRelativeLayout) v).setBackground(getResources().getDrawable(R.drawable.main_layout_select_bg1));
                    ImageView childAt = (ImageView) ldpt_Wx.getChildAt(0);
                    childAt.setImageDrawable(getResources().getDrawable(R.drawable.wt_monitor_1));
                    TextView childAt1 = (TextView) ldpt_Wx.getChildAt(1);
                    childAt1.setTextColor(getResources().getColor(R.color.black));
                }
                break;
            case R.id.main_content_item1_2:
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    ((PercentRelativeLayout) v).setBackground(getResources().getDrawable(R.drawable.main_layout_select_bg));
                    ImageView childAt = (ImageView) typhoonMonitoring.getChildAt(0);
                    childAt.setImageDrawable(getResources().getDrawable(R.drawable.wt_monitor_cl_2));
                    TextView childAt1 = (TextView) typhoonMonitoring.getChildAt(1);
                    childAt1.setTextColor(getResources().getColor(R.color.white));
                }
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    ((PercentRelativeLayout) v).setBackground(getResources().getDrawable(R.drawable.main_layout_select_bg1));
                    ImageView childAt = (ImageView) typhoonMonitoring.getChildAt(0);
                    childAt.setImageDrawable(getResources().getDrawable(R.drawable.wt_monitor_2));
                    TextView childAt1 = (TextView) typhoonMonitoring.getChildAt(1);
                    childAt1.setTextColor(getResources().getColor(R.color.black));
                }
                if (event.getAction() == MotionEvent.ACTION_MOVE) {
                    ((PercentRelativeLayout) v).setBackground(getResources().getDrawable(R.drawable.main_layout_select_bg1));
                    ImageView childAt = (ImageView) typhoonMonitoring.getChildAt(0);
                    childAt.setImageDrawable(getResources().getDrawable(R.drawable.wt_monitor_2));
                    TextView childAt1 = (TextView) typhoonMonitoring.getChildAt(1);
                    childAt1.setTextColor(getResources().getColor(R.color.black));
                }
                if (event.getAction() == MotionEvent.ACTION_CANCEL) {
                    ((PercentRelativeLayout) v).setBackground(getResources().getDrawable(R.drawable.main_layout_select_bg1));
                    ImageView childAt = (ImageView) typhoonMonitoring.getChildAt(0);
                    childAt.setImageDrawable(getResources().getDrawable(R.drawable.wt_monitor_2));
                    TextView childAt1 = (TextView) typhoonMonitoring.getChildAt(1);
                    childAt1.setTextColor(getResources().getColor(R.color.black));
                }
                break;
            case R.id.main_content_item1_3:
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    ((PercentRelativeLayout) v).setBackground(getResources().getDrawable(R.drawable.main_layout_select_bg));
                    ImageView childAt = (ImageView) dmcgjc.getChildAt(0);
                    childAt.setImageDrawable(getResources().getDrawable(R.drawable.wt_monitor_cl_3));
                    TextView childAt1 = (TextView) dmcgjc.getChildAt(1);
                    childAt1.setTextColor(getResources().getColor(R.color.white));
                }
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    ((PercentRelativeLayout) v).setBackground(getResources().getDrawable(R.drawable.main_layout_select_bg1));
                    ImageView childAt = (ImageView) dmcgjc.getChildAt(0);
                    childAt.setImageDrawable(getResources().getDrawable(R.drawable.wt_monitor_3));
                    TextView childAt1 = (TextView) dmcgjc.getChildAt(1);
                    childAt1.setTextColor(getResources().getColor(R.color.black));
                }
                if (event.getAction() == MotionEvent.ACTION_MOVE) {
                    ((PercentRelativeLayout) v).setBackground(getResources().getDrawable(R.drawable.main_layout_select_bg1));
                    ImageView childAt = (ImageView) dmcgjc.getChildAt(0);
                    childAt.setImageDrawable(getResources().getDrawable(R.drawable.wt_monitor_3));
                    TextView childAt1 = (TextView) dmcgjc.getChildAt(1);
                    childAt1.setTextColor(getResources().getColor(R.color.black));
                }
                if (event.getAction() == MotionEvent.ACTION_CANCEL) {
                    ((PercentRelativeLayout) v).setBackground(getResources().getDrawable(R.drawable.main_layout_select_bg1));
                    ImageView childAt = (ImageView) dmcgjc.getChildAt(0);
                    childAt.setImageDrawable(getResources().getDrawable(R.drawable.wt_monitor_3));
                    TextView childAt1 = (TextView) dmcgjc.getChildAt(1);
                    childAt1.setTextColor(getResources().getColor(R.color.black));
                }
                break;
            case R.id.main_content_item1_4:
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    ((PercentRelativeLayout) v).setBackground(getResources().getDrawable(R.drawable.main_layout_select_bg));
                    ImageView childAt = (ImageView) gkdmgc.getChildAt(0);
                    childAt.setImageDrawable(getResources().getDrawable(R.drawable.wt_monitor_cl_4));
                    TextView childAt1 = (TextView) gkdmgc.getChildAt(1);
                    childAt1.setTextColor(getResources().getColor(R.color.white));
                }
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    ((PercentRelativeLayout) v).setBackground(getResources().getDrawable(R.drawable.main_layout_select_bg1));
                    ImageView childAt = (ImageView) gkdmgc.getChildAt(0);
                    childAt.setImageDrawable(getResources().getDrawable(R.drawable.wt_monitor_4));
                    TextView childAt1 = (TextView) gkdmgc.getChildAt(1);
                    childAt1.setTextColor(getResources().getColor(R.color.black));
                }
                if (event.getAction() == MotionEvent.ACTION_MOVE) {
                    ((PercentRelativeLayout) v).setBackground(getResources().getDrawable(R.drawable.main_layout_select_bg1));
                    ImageView childAt = (ImageView) gkdmgc.getChildAt(0);
                    childAt.setImageDrawable(getResources().getDrawable(R.drawable.wt_monitor_4));
                    TextView childAt1 = (TextView) gkdmgc.getChildAt(1);
                    childAt1.setTextColor(getResources().getColor(R.color.black));
                }
                if (event.getAction() == MotionEvent.ACTION_CANCEL) {
                    ((PercentRelativeLayout) v).setBackground(getResources().getDrawable(R.drawable.main_layout_select_bg1));
                    ImageView childAt = (ImageView) gkdmgc.getChildAt(0);
                    childAt.setImageDrawable(getResources().getDrawable(R.drawable.wt_monitor_4));
                    TextView childAt1 = (TextView) gkdmgc.getChildAt(1);
                    childAt1.setTextColor(getResources().getColor(R.color.black));
                }
                break;
            case R.id.main_content_item2_1:
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    ((PercentRelativeLayout) v).setBackground(getResources().getDrawable(R.drawable.main_layout_select_bg));
                    ImageView childAt = (ImageView) YujinXinhao.getChildAt(0);
                    childAt.setImageDrawable(getResources().getDrawable(R.drawable.fc_warn_cl_1));
                    TextView childAt1 = (TextView) YujinXinhao.getChildAt(1);
                    childAt1.setTextColor(getResources().getColor(R.color.white));
                }
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    ((PercentRelativeLayout) v).setBackground(getResources().getDrawable(R.drawable.main_layout_select_bg1));
                    ImageView childAt = (ImageView) YujinXinhao.getChildAt(0);
                    childAt.setImageDrawable(getResources().getDrawable(R.drawable.fc_warn_1));
                    TextView childAt1 = (TextView) YujinXinhao.getChildAt(1);
                    childAt1.setTextColor(getResources().getColor(R.color.black));
                }
                if (event.getAction() == MotionEvent.ACTION_MOVE) {
                    ((PercentRelativeLayout) v).setBackground(getResources().getDrawable(R.drawable.main_layout_select_bg1));
                    ImageView childAt = (ImageView) YujinXinhao.getChildAt(0);
                    childAt.setImageDrawable(getResources().getDrawable(R.drawable.fc_warn_1));
                    TextView childAt1 = (TextView) YujinXinhao.getChildAt(1);
                    childAt1.setTextColor(getResources().getColor(R.color.black));
                }
                if (event.getAction() == MotionEvent.ACTION_CANCEL) {
                    ((PercentRelativeLayout) v).setBackground(getResources().getDrawable(R.drawable.main_layout_select_bg1));
                    ImageView childAt = (ImageView) YujinXinhao.getChildAt(0);
                    childAt.setImageDrawable(getResources().getDrawable(R.drawable.fc_warn_1));
                    TextView childAt1 = (TextView) YujinXinhao.getChildAt(1);
                    childAt1.setTextColor(getResources().getColor(R.color.black));
                }
                break;
            case R.id.main_content_item2_2:
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    ((PercentRelativeLayout) v).setBackground(getResources().getDrawable(R.drawable.main_layout_select_bg));
                    ImageView childAt = (ImageView) Zytqyb.getChildAt(0);
                    childAt.setImageDrawable(getResources().getDrawable(R.drawable.fc_warn_cl_2));
                    TextView childAt1 = (TextView) Zytqyb.getChildAt(1);
                    childAt1.setTextColor(getResources().getColor(R.color.white));
                }
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    ((PercentRelativeLayout) v).setBackground(getResources().getDrawable(R.drawable.main_layout_select_bg1));
                    ImageView childAt = (ImageView) Zytqyb.getChildAt(0);
                    childAt.setImageDrawable(getResources().getDrawable(R.drawable.fc_warn_2));
                    TextView childAt1 = (TextView) Zytqyb.getChildAt(1);
                    childAt1.setTextColor(getResources().getColor(R.color.black));
                }
                if (event.getAction() == MotionEvent.ACTION_MOVE) {
                    ((PercentRelativeLayout) v).setBackground(getResources().getDrawable(R.drawable.main_layout_select_bg1));
                    ImageView childAt = (ImageView) Zytqyb.getChildAt(0);
                    childAt.setImageDrawable(getResources().getDrawable(R.drawable.fc_warn_2));
                    TextView childAt1 = (TextView) Zytqyb.getChildAt(1);
                    childAt1.setTextColor(getResources().getColor(R.color.black));
                }
                if (event.getAction() == MotionEvent.ACTION_CANCEL) {
                    ((PercentRelativeLayout) v).setBackground(getResources().getDrawable(R.drawable.main_layout_select_bg1));
                    ImageView childAt = (ImageView) Zytqyb.getChildAt(0);
                    childAt.setImageDrawable(getResources().getDrawable(R.drawable.fc_warn_2));
                    TextView childAt1 = (TextView) Zytqyb.getChildAt(1);
                    childAt1.setTextColor(getResources().getColor(R.color.black));
                }
                break;
            case R.id.main_content_item2_3:
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    ((PercentRelativeLayout) v).setBackground(getResources().getDrawable(R.drawable.main_layout_select_bg));
                    ImageView childAt = (ImageView) radarForecast.getChildAt(0);
                    childAt.setImageDrawable(getResources().getDrawable(R.drawable.fc_warn_cl_3));
                    TextView childAt1 = (TextView) radarForecast.getChildAt(1);
                    childAt1.setTextColor(getResources().getColor(R.color.white));
                }
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    ((PercentRelativeLayout) v).setBackground(getResources().getDrawable(R.drawable.main_layout_select_bg1));
                    ImageView childAt = (ImageView) radarForecast.getChildAt(0);
                    childAt.setImageDrawable(getResources().getDrawable(R.drawable.fc_warn_3));
                    TextView childAt1 = (TextView) radarForecast.getChildAt(1);
                    childAt1.setTextColor(getResources().getColor(R.color.black));
                }
                if (event.getAction() == MotionEvent.ACTION_MOVE) {
                    ((PercentRelativeLayout) v).setBackground(getResources().getDrawable(R.drawable.main_layout_select_bg1));
                    ImageView childAt = (ImageView) radarForecast.getChildAt(0);
                    childAt.setImageDrawable(getResources().getDrawable(R.drawable.fc_warn_3));
                    TextView childAt1 = (TextView) radarForecast.getChildAt(1);
                    childAt1.setTextColor(getResources().getColor(R.color.black));
                }
                if (event.getAction() == MotionEvent.ACTION_CANCEL) {
                    ((PercentRelativeLayout) v).setBackground(getResources().getDrawable(R.drawable.main_layout_select_bg1));
                    ImageView childAt = (ImageView) radarForecast.getChildAt(0);
                    childAt.setImageDrawable(getResources().getDrawable(R.drawable.fc_warn_3));
                    TextView childAt1 = (TextView) radarForecast.getChildAt(1);
                    childAt1.setTextColor(getResources().getColor(R.color.black));
                }
                break;
            case R.id.main_content_item2_4:
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    ((PercentRelativeLayout) v).setBackground(getResources().getDrawable(R.drawable.main_layout_select_bg));
                    ImageView childAt = (ImageView) gdyb.getChildAt(0);
                    childAt.setImageDrawable(getResources().getDrawable(R.drawable.fc_warn_cl_4));
                    TextView childAt1 = (TextView) gdyb.getChildAt(1);
                    childAt1.setTextColor(getResources().getColor(R.color.white));
                }
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    ((PercentRelativeLayout) v).setBackground(getResources().getDrawable(R.drawable.main_layout_select_bg1));
                    ImageView childAt = (ImageView) gdyb.getChildAt(0);
                    childAt.setImageDrawable(getResources().getDrawable(R.drawable.fc_warn_4));
                    TextView childAt1 = (TextView) gdyb.getChildAt(1);
                    childAt1.setTextColor(getResources().getColor(R.color.black));
                }
                if (event.getAction() == MotionEvent.ACTION_MOVE) {
                    ((PercentRelativeLayout) v).setBackground(getResources().getDrawable(R.drawable.main_layout_select_bg1));
                    ImageView childAt = (ImageView) gdyb.getChildAt(0);
                    childAt.setImageDrawable(getResources().getDrawable(R.drawable.fc_warn_4));
                    TextView childAt1 = (TextView) gdyb.getChildAt(1);
                    childAt1.setTextColor(getResources().getColor(R.color.black));
                }
                if (event.getAction() == MotionEvent.ACTION_CANCEL) {
                    ((PercentRelativeLayout) v).setBackground(getResources().getDrawable(R.drawable.main_layout_select_bg1));
                    ImageView childAt = (ImageView) gdyb.getChildAt(0);
                    childAt.setImageDrawable(getResources().getDrawable(R.drawable.fc_warn_4));
                    TextView childAt1 = (TextView) gdyb.getChildAt(1);
                    childAt1.setTextColor(getResources().getColor(R.color.black));
                }
                break;
            case R.id.main_content_item3_1:
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    ((PercentRelativeLayout) v).setBackground(getResources().getDrawable(R.drawable.main_layout_select_bg));
                    ImageView childAt = (ImageView) qxfx.getChildAt(0);
                    childAt.setImageDrawable(getResources().getDrawable(R.drawable.fc_warn_cl_5));
                    TextView childAt1 = (TextView) qxfx.getChildAt(1);
                    childAt1.setTextColor(getResources().getColor(R.color.white));
                }
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    ((PercentRelativeLayout) v).setBackground(getResources().getDrawable(R.drawable.main_layout_select_bg1));
                    ImageView childAt = (ImageView) qxfx.getChildAt(0);
                    childAt.setImageDrawable(getResources().getDrawable(R.drawable.fc_warn_5));
                    TextView childAt1 = (TextView) qxfx.getChildAt(1);
                    childAt1.setTextColor(getResources().getColor(R.color.black));
                }
                if (event.getAction() == MotionEvent.ACTION_MOVE) {
                    ((PercentRelativeLayout) v).setBackground(getResources().getDrawable(R.drawable.main_layout_select_bg1));
                    ImageView childAt = (ImageView) qxfx.getChildAt(0);
                    childAt.setImageDrawable(getResources().getDrawable(R.drawable.fc_warn_5));
                    TextView childAt1 = (TextView) qxfx.getChildAt(1);
                    childAt1.setTextColor(getResources().getColor(R.color.black));
                }
                if (event.getAction() == MotionEvent.ACTION_CANCEL) {
                    ((PercentRelativeLayout) v).setBackground(getResources().getDrawable(R.drawable.main_layout_select_bg1));
                    ImageView childAt = (ImageView) qxfx.getChildAt(0);
                    childAt.setImageDrawable(getResources().getDrawable(R.drawable.fc_warn_5));
                    TextView childAt1 = (TextView) qxfx.getChildAt(1);
                    childAt1.setTextColor(getResources().getColor(R.color.black));
                }
                break;
            case R.id.main_content_item3_2:
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    ((PercentRelativeLayout) v).setBackground(getResources().getDrawable(R.drawable.main_layout_select_bg));
                    ImageView childAt = (ImageView) genefore.getChildAt(0);
                    childAt.setImageDrawable(getResources().getDrawable(R.drawable.fc_warn_cl_6));
                    TextView childAt1 = (TextView) genefore.getChildAt(1);
                    childAt1.setTextColor(getResources().getColor(R.color.white));
                }
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    ((PercentRelativeLayout) v).setBackground(getResources().getDrawable(R.drawable.main_layout_select_bg1));
                    ImageView childAt = (ImageView) genefore.getChildAt(0);
                    childAt.setImageDrawable(getResources().getDrawable(R.drawable.fc_warn_6));
                    TextView childAt1 = (TextView) genefore.getChildAt(1);
                    childAt1.setTextColor(getResources().getColor(R.color.black));
                }
                if (event.getAction() == MotionEvent.ACTION_MOVE) {
                    ((PercentRelativeLayout) v).setBackground(getResources().getDrawable(R.drawable.main_layout_select_bg1));
                    ImageView childAt = (ImageView) genefore.getChildAt(0);
                    childAt.setImageDrawable(getResources().getDrawable(R.drawable.fc_warn_6));
                    TextView childAt1 = (TextView) genefore.getChildAt(1);
                    childAt1.setTextColor(getResources().getColor(R.color.black));
                }
                if (event.getAction() == MotionEvent.ACTION_CANCEL) {
                    ((PercentRelativeLayout) v).setBackground(getResources().getDrawable(R.drawable.main_layout_select_bg1));
                    ImageView childAt = (ImageView) genefore.getChildAt(0);
                    childAt.setImageDrawable(getResources().getDrawable(R.drawable.fc_warn_6));
                    TextView childAt1 = (TextView) genefore.getChildAt(1);
                    childAt1.setTextColor(getResources().getColor(R.color.black));
                }
                break;
            case R.id.main_content_item3_3:
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    ((PercentRelativeLayout) v).setBackground(getResources().getDrawable(R.drawable.main_layout_select_bg));
                    ImageView childAt = (ImageView) gdybtx.getChildAt(0);
                    childAt.setImageDrawable(getResources().getDrawable(R.drawable.fc_warn_cl_7));
                    TextView childAt1 = (TextView) gdybtx.getChildAt(1);
                    childAt1.setTextColor(getResources().getColor(R.color.white));
                }
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    ((PercentRelativeLayout) v).setBackground(getResources().getDrawable(R.drawable.main_layout_select_bg1));
                    ImageView childAt = (ImageView) gdybtx.getChildAt(0);
                    childAt.setImageDrawable(getResources().getDrawable(R.drawable.fc_warn_7));
                    TextView childAt1 = (TextView) gdybtx.getChildAt(1);
                    childAt1.setTextColor(getResources().getColor(R.color.black));
                }
                if (event.getAction() == MotionEvent.ACTION_MOVE) {
                    ((PercentRelativeLayout) v).setBackground(getResources().getDrawable(R.drawable.main_layout_select_bg1));
                    ImageView childAt = (ImageView) gdybtx.getChildAt(0);
                    childAt.setImageDrawable(getResources().getDrawable(R.drawable.fc_warn_7));
                    TextView childAt1 = (TextView) gdybtx.getChildAt(1);
                    childAt1.setTextColor(getResources().getColor(R.color.black));
                }
                if (event.getAction() == MotionEvent.ACTION_CANCEL) {
                    ((PercentRelativeLayout) v).setBackground(getResources().getDrawable(R.drawable.main_layout_select_bg1));
                    ImageView childAt = (ImageView) gdybtx.getChildAt(0);
                    childAt.setImageDrawable(getResources().getDrawable(R.drawable.fc_warn_7));
                    TextView childAt1 = (TextView) gdybtx.getChildAt(1);
                    childAt1.setTextColor(getResources().getColor(R.color.black));
                }
                break;
            case R.id.main_content_item3_4:
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    ((PercentRelativeLayout) v).setBackground(getResources().getDrawable(R.drawable.main_layout_select_bg));
                    ImageView childAt = (ImageView) dsljyb.getChildAt(0);
                    childAt.setImageDrawable(getResources().getDrawable(R.drawable.fc_warn_cl_8));
                    TextView childAt1 = (TextView) dsljyb.getChildAt(1);
                    childAt1.setTextColor(getResources().getColor(R.color.white));
                }
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    ((PercentRelativeLayout) v).setBackground(getResources().getDrawable(R.drawable.main_layout_select_bg1));
                    ImageView childAt = (ImageView) dsljyb.getChildAt(0);
                    childAt.setImageDrawable(getResources().getDrawable(R.drawable.fc_warn_8));
                    TextView childAt1 = (TextView) dsljyb.getChildAt(1);
                    childAt1.setTextColor(getResources().getColor(R.color.black));
                }
                if (event.getAction() == MotionEvent.ACTION_MOVE) {
                    ((PercentRelativeLayout) v).setBackground(getResources().getDrawable(R.drawable.main_layout_select_bg1));
                    ImageView childAt = (ImageView) dsljyb.getChildAt(0);
                    childAt.setImageDrawable(getResources().getDrawable(R.drawable.fc_warn_8));
                    TextView childAt1 = (TextView) dsljyb.getChildAt(1);
                    childAt1.setTextColor(getResources().getColor(R.color.black));
                }
                if (event.getAction() == MotionEvent.ACTION_CANCEL) {
                    ((PercentRelativeLayout) v).setBackground(getResources().getDrawable(R.drawable.main_layout_select_bg1));
                    ImageView childAt = (ImageView) dsljyb.getChildAt(0);
                    childAt.setImageDrawable(getResources().getDrawable(R.drawable.fc_warn_8));
                    TextView childAt1 = (TextView) dsljyb.getChildAt(1);
                    childAt1.setTextColor(getResources().getColor(R.color.black));
                }
                break;
            case R.id.main_content_item0_1:
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    ((PercentRelativeLayout) v).setBackground(getResources().getDrawable(R.drawable.main_layout_select_bg));
                    ImageView childAt = (ImageView) ecxwg.getChildAt(0);
                    childAt.setImageDrawable(getResources().getDrawable(R.drawable.data_forecast_cl_1));
                    TextView childAt1 = (TextView) ecxwg.getChildAt(1);
                    childAt1.setTextColor(getResources().getColor(R.color.white));
                }
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    ((PercentRelativeLayout) v).setBackground(getResources().getDrawable(R.drawable.main_layout_select_bg1));
                    ImageView childAt = (ImageView) ecxwg.getChildAt(0);
                    childAt.setImageDrawable(getResources().getDrawable(R.drawable.data_forecast_1));
                    TextView childAt1 = (TextView) ecxwg.getChildAt(1);
                    childAt1.setTextColor(getResources().getColor(R.color.black));
                }
                if (event.getAction() == MotionEvent.ACTION_MOVE) {
                    ((PercentRelativeLayout) v).setBackground(getResources().getDrawable(R.drawable.main_layout_select_bg1));
                    ImageView childAt = (ImageView) ecxwg.getChildAt(0);
                    childAt.setImageDrawable(getResources().getDrawable(R.drawable.data_forecast_1));
                    TextView childAt1 = (TextView) ecxwg.getChildAt(1);
                    childAt1.setTextColor(getResources().getColor(R.color.black));
                }
                if (event.getAction() == MotionEvent.ACTION_CANCEL) {
                    ((PercentRelativeLayout) v).setBackground(getResources().getDrawable(R.drawable.main_layout_select_bg1));
                    ImageView childAt = (ImageView) ecxwg.getChildAt(0);
                    childAt.setImageDrawable(getResources().getDrawable(R.drawable.data_forecast_1));
                    TextView childAt1 = (TextView) ecxwg.getChildAt(1);
                    childAt1.setTextColor(getResources().getColor(R.color.black));
                }
                break;
            case R.id.main_content_item0_2:
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    ((PercentRelativeLayout) v).setBackground(getResources().getDrawable(R.drawable.main_layout_select_bg));
                    ImageView childAt = (ImageView) grapes_ms.getChildAt(0);
                    childAt.setImageDrawable(getResources().getDrawable(R.drawable.data_forecast_cl_2));
                    TextView childAt1 = (TextView) grapes_ms.getChildAt(1);
                    childAt1.setTextColor(getResources().getColor(R.color.white));
                }
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    ((PercentRelativeLayout) v).setBackground(getResources().getDrawable(R.drawable.main_layout_select_bg1));
                    ImageView childAt = (ImageView) grapes_ms.getChildAt(0);
                    childAt.setImageDrawable(getResources().getDrawable(R.drawable.data_forecast_2));
                    TextView childAt1 = (TextView) grapes_ms.getChildAt(1);
                    childAt1.setTextColor(getResources().getColor(R.color.black));
                }
                if (event.getAction() == MotionEvent.ACTION_MOVE) {
                    ((PercentRelativeLayout) v).setBackground(getResources().getDrawable(R.drawable.main_layout_select_bg1));
                    ImageView childAt = (ImageView) grapes_ms.getChildAt(0);
                    childAt.setImageDrawable(getResources().getDrawable(R.drawable.data_forecast_2));
                    TextView childAt1 = (TextView) grapes_ms.getChildAt(1);
                    childAt1.setTextColor(getResources().getColor(R.color.black));
                }
                if (event.getAction() == MotionEvent.ACTION_CANCEL) {
                    ((PercentRelativeLayout) v).setBackground(getResources().getDrawable(R.drawable.main_layout_select_bg1));
                    ImageView childAt = (ImageView) grapes_ms.getChildAt(0);
                    childAt.setImageDrawable(getResources().getDrawable(R.drawable.data_forecast_2));
                    TextView childAt1 = (TextView) grapes_ms.getChildAt(1);
                    childAt1.setTextColor(getResources().getColor(R.color.black));
                }
                break;
            case R.id.main_content_item0_3:
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    ((PercentRelativeLayout) v).setBackground(getResources().getDrawable(R.drawable.main_layout_select_bg));
                    ImageView childAt = (ImageView) hdqyms.getChildAt(0);
                    childAt.setImageDrawable(getResources().getDrawable(R.drawable.data_forecast_cl_3));
                    TextView childAt1 = (TextView) hdqyms.getChildAt(1);
                    childAt1.setTextColor(getResources().getColor(R.color.white));
                }
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    ((PercentRelativeLayout) v).setBackground(getResources().getDrawable(R.drawable.main_layout_select_bg1));
                    ImageView childAt = (ImageView) hdqyms.getChildAt(0);
                    childAt.setImageDrawable(getResources().getDrawable(R.drawable.data_forecast_3));
                    TextView childAt1 = (TextView) hdqyms.getChildAt(1);
                    childAt1.setTextColor(getResources().getColor(R.color.black));
                }
                if (event.getAction() == MotionEvent.ACTION_MOVE) {
                    ((PercentRelativeLayout) v).setBackground(getResources().getDrawable(R.drawable.main_layout_select_bg1));
                    ImageView childAt = (ImageView) hdqyms.getChildAt(0);
                    childAt.setImageDrawable(getResources().getDrawable(R.drawable.data_forecast_3));
                    TextView childAt1 = (TextView) hdqyms.getChildAt(1);
                    childAt1.setTextColor(getResources().getColor(R.color.black));
                }
                if (event.getAction() == MotionEvent.ACTION_CANCEL) {
                    ((PercentRelativeLayout) v).setBackground(getResources().getDrawable(R.drawable.main_layout_select_bg1));
                    ImageView childAt = (ImageView) hdqyms.getChildAt(0);
                    childAt.setImageDrawable(getResources().getDrawable(R.drawable.data_forecast_3));
                    TextView childAt1 = (TextView) hdqyms.getChildAt(1);
                    childAt1.setTextColor(getResources().getColor(R.color.black));
                }
                break;
            case R.id.main_content_item0_4:
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    ((PercentRelativeLayout) v).setBackground(getResources().getDrawable(R.drawable.main_layout_select_bg));
                    ImageView childAt = (ImageView) wtfdlxh.getChildAt(0);
                    childAt.setImageDrawable(getResources().getDrawable(R.drawable.data_forecast_cl_4));
                    TextView childAt1 = (TextView) wtfdlxh.getChildAt(1);
                    childAt1.setTextColor(getResources().getColor(R.color.white));
                }
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    ((PercentRelativeLayout) v).setBackground(getResources().getDrawable(R.drawable.main_layout_select_bg1));
                    ImageView childAt = (ImageView) wtfdlxh.getChildAt(0);
                    childAt.setImageDrawable(getResources().getDrawable(R.drawable.data_forecast_4));
                    TextView childAt1 = (TextView) wtfdlxh.getChildAt(1);
                    childAt1.setTextColor(getResources().getColor(R.color.black));
                }
                if (event.getAction() == MotionEvent.ACTION_MOVE) {
                    ((PercentRelativeLayout) v).setBackground(getResources().getDrawable(R.drawable.main_layout_select_bg1));
                    ImageView childAt = (ImageView) wtfdlxh.getChildAt(0);
                    childAt.setImageDrawable(getResources().getDrawable(R.drawable.data_forecast_4));
                    TextView childAt1 = (TextView) wtfdlxh.getChildAt(1);
                    childAt1.setTextColor(getResources().getColor(R.color.black));
                }
                if (event.getAction() == MotionEvent.ACTION_CANCEL) {
                    ((PercentRelativeLayout) v).setBackground(getResources().getDrawable(R.drawable.main_layout_select_bg1));
                    ImageView childAt = (ImageView) wtfdlxh.getChildAt(0);
                    childAt.setImageDrawable(getResources().getDrawable(R.drawable.data_forecast_4));
                    TextView childAt1 = (TextView) wtfdlxh.getChildAt(1);
                    childAt1.setTextColor(getResources().getColor(R.color.black));
                }
                break;
            case R.id.main_content_item4_1:
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    ((PercentRelativeLayout) v).setBackground(getResources().getDrawable(R.drawable.main_layout_select_bg));
                    ImageView childAt = (ImageView) decision_service.getChildAt(0);
                    childAt.setImageDrawable(getResources().getDrawable(R.drawable.sercice_feedback_cl_1));
                    TextView childAt1 = (TextView) decision_service.getChildAt(1);
                    childAt1.setTextColor(getResources().getColor(R.color.white));
                }
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    ((PercentRelativeLayout) v).setBackground(getResources().getDrawable(R.drawable.main_layout_select_bg1));
                    ImageView childAt = (ImageView) decision_service.getChildAt(0);
                    childAt.setImageDrawable(getResources().getDrawable(R.drawable.sercice_feedback_1));
                    TextView childAt1 = (TextView) decision_service.getChildAt(1);
                    childAt1.setTextColor(getResources().getColor(R.color.black));
                }
                if (event.getAction() == MotionEvent.ACTION_MOVE) {
                    ((PercentRelativeLayout) v).setBackground(getResources().getDrawable(R.drawable.main_layout_select_bg1));
                    ImageView childAt = (ImageView) decision_service.getChildAt(0);
                    childAt.setImageDrawable(getResources().getDrawable(R.drawable.sercice_feedback_1));
                    TextView childAt1 = (TextView) decision_service.getChildAt(1);
                    childAt1.setTextColor(getResources().getColor(R.color.black));
                }
                if (event.getAction() == MotionEvent.ACTION_CANCEL) {
                    ((PercentRelativeLayout) v).setBackground(getResources().getDrawable(R.drawable.main_layout_select_bg1));
                    ImageView childAt = (ImageView) decision_service.getChildAt(0);
                    childAt.setImageDrawable(getResources().getDrawable(R.drawable.sercice_feedback_1));
                    TextView childAt1 = (TextView) decision_service.getChildAt(1);
                    childAt1.setTextColor(getResources().getColor(R.color.black));
                }
                break;
            case R.id.main_content_item4_2:
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    ((PercentRelativeLayout) v).setBackground(getResources().getDrawable(R.drawable.main_layout_select_bg));
                    ImageView childAt = (ImageView) forcast_score.getChildAt(0);
                    childAt.setImageDrawable(getResources().getDrawable(R.drawable.sercice_feedback_cl_2));
                    TextView childAt1 = (TextView) forcast_score.getChildAt(1);
                    childAt1.setTextColor(getResources().getColor(R.color.white));
                }
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    ((PercentRelativeLayout) v).setBackground(getResources().getDrawable(R.drawable.main_layout_select_bg1));
                    ImageView childAt = (ImageView) forcast_score.getChildAt(0);
                    childAt.setImageDrawable(getResources().getDrawable(R.drawable.sercice_feedback_2));
                    TextView childAt1 = (TextView) forcast_score.getChildAt(1);
                    childAt1.setTextColor(getResources().getColor(R.color.black));
                }
                if (event.getAction() == MotionEvent.ACTION_MOVE) {
                    ((PercentRelativeLayout) v).setBackground(getResources().getDrawable(R.drawable.main_layout_select_bg1));
                    ImageView childAt = (ImageView) forcast_score.getChildAt(0);
                    childAt.setImageDrawable(getResources().getDrawable(R.drawable.sercice_feedback_2));
                    TextView childAt1 = (TextView) forcast_score.getChildAt(1);
                    childAt1.setTextColor(getResources().getColor(R.color.black));
                }
                if (event.getAction() == MotionEvent.ACTION_CANCEL) {
                    ((PercentRelativeLayout) v).setBackground(getResources().getDrawable(R.drawable.main_layout_select_bg1));
                    ImageView childAt = (ImageView) forcast_score.getChildAt(0);
                    childAt.setImageDrawable(getResources().getDrawable(R.drawable.sercice_feedback_2));
                    TextView childAt1 = (TextView) forcast_score.getChildAt(1);
                    childAt1.setTextColor(getResources().getColor(R.color.black));
                }
                break;
            case R.id.main_content_item4_3:
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    ((PercentRelativeLayout) v).setBackground(getResources().getDrawable(R.drawable.main_layout_select_bg));
                    ImageView childAt = (ImageView) info_feddback.getChildAt(0);
                    childAt.setImageDrawable(getResources().getDrawable(R.drawable.sercice_feedback_cl_3));
                    TextView childAt1 = (TextView) info_feddback.getChildAt(1);
                    childAt1.setTextColor(getResources().getColor(R.color.white));
                }
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    ((PercentRelativeLayout) v).setBackground(getResources().getDrawable(R.drawable.main_layout_select_bg1));
                    ImageView childAt = (ImageView) info_feddback.getChildAt(0);
                    childAt.setImageDrawable(getResources().getDrawable(R.drawable.sercice_feedback_3));
                    TextView childAt1 = (TextView) info_feddback.getChildAt(1);
                    childAt1.setTextColor(getResources().getColor(R.color.black));
                }
                if (event.getAction() == MotionEvent.ACTION_MOVE) {
                    ((PercentRelativeLayout) v).setBackground(getResources().getDrawable(R.drawable.main_layout_select_bg1));
                    ImageView childAt = (ImageView) info_feddback.getChildAt(0);
                    childAt.setImageDrawable(getResources().getDrawable(R.drawable.sercice_feedback_3));
                    TextView childAt1 = (TextView) info_feddback.getChildAt(1);
                    childAt1.setTextColor(getResources().getColor(R.color.black));
                }
                if (event.getAction() == MotionEvent.ACTION_CANCEL) {
                    ((PercentRelativeLayout) v).setBackground(getResources().getDrawable(R.drawable.main_layout_select_bg1));
                    ImageView childAt = (ImageView) info_feddback.getChildAt(0);
                    childAt.setImageDrawable(getResources().getDrawable(R.drawable.sercice_feedback_3));
                    TextView childAt1 = (TextView) info_feddback.getChildAt(1);
                    childAt1.setTextColor(getResources().getColor(R.color.black));
                }
                break;
            case R.id.main_content_item4_4:
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    ((PercentRelativeLayout) v).setBackground(getResources().getDrawable(R.drawable.main_layout_select_bg));
                    ImageView childAt = (ImageView) help.getChildAt(0);
                    childAt.setImageDrawable(getResources().getDrawable(R.drawable.sercice_feedback_cl_4));
                    TextView childAt1 = (TextView) help.getChildAt(1);
                    childAt1.setTextColor(getResources().getColor(R.color.white));
                }
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    ((PercentRelativeLayout) v).setBackground(getResources().getDrawable(R.drawable.main_layout_select_bg1));
                    ImageView childAt = (ImageView) help.getChildAt(0);
                    childAt.setImageDrawable(getResources().getDrawable(R.drawable.sercice_feedback_4));
                    TextView childAt1 = (TextView) help.getChildAt(1);
                    childAt1.setTextColor(getResources().getColor(R.color.black));
                }
                if (event.getAction() == MotionEvent.ACTION_MOVE) {
                    ((PercentRelativeLayout) v).setBackground(getResources().getDrawable(R.drawable.main_layout_select_bg1));
                    ImageView childAt = (ImageView) help.getChildAt(0);
                    childAt.setImageDrawable(getResources().getDrawable(R.drawable.sercice_feedback_4));
                    TextView childAt1 = (TextView) help.getChildAt(1);
                    childAt1.setTextColor(getResources().getColor(R.color.black));
                }
                if (event.getAction() == MotionEvent.ACTION_CANCEL) {
                    ((PercentRelativeLayout) v).setBackground(getResources().getDrawable(R.drawable.main_layout_select_bg1));
                    ImageView childAt = (ImageView) help.getChildAt(0);
                    childAt.setImageDrawable(getResources().getDrawable(R.drawable.sercice_feedback_4));
                    TextView childAt1 = (TextView) help.getChildAt(1);
                    childAt1.setTextColor(getResources().getColor(R.color.black));
                }
                break;
        }
        return false;
    }
}
