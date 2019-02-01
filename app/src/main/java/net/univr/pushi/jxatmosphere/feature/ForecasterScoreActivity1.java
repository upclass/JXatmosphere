package net.univr.pushi.jxatmosphere.feature;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListPopupWindow;
import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jzxiang.pickerview.TimePickerDialog;
import com.jzxiang.pickerview.data.Type;
import com.jzxiang.pickerview.listener.OnDateSetListener;

import net.univr.pushi.jxatmosphere.R;
import net.univr.pushi.jxatmosphere.adapter.ContentForecasterAdapter;
import net.univr.pushi.jxatmosphere.adapter.TimeForecastAdapert;
import net.univr.pushi.jxatmosphere.base.BaseActivity;
import net.univr.pushi.jxatmosphere.beens.Content_ForecastScore_Been;
import net.univr.pushi.jxatmosphere.beens.GkdmClickBeen;
import net.univr.pushi.jxatmosphere.beens.WebForecastScore;
import net.univr.pushi.jxatmosphere.remote.RetrofitHelper;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ForecasterScoreActivity1 extends BaseActivity {


    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.reload)
    ImageView reload;
    @BindView(R.id.recycle_content)
    RecyclerView content_recycleView;
    @BindView(R.id.recycle_time)
    RecyclerView time_recycleView;
    @BindView(R.id.startTime)
    TextView startTime;
    @BindView(R.id.endTime)
    TextView endTime;
    ProgressDialog progressDialog;
    ListPopupWindow popupWindow;
    List<String> menu;//Listpopwind的菜单
    @BindView(R.id.bottom_lay)
    LinearLayout bottom_lay;
    @BindView(R.id.menu_text)
    TextView menu_text;

    Map<String, List<String>> jiangxiMap = new HashMap<>();
    Map<String, List<String>> ffuzhouMap = new HashMap<>();
    Map<String, List<String>> ganzhouMap = new HashMap<>();
    Map<String, List<String>> jianMap = new HashMap<>();
    Map<String, List<String>> jingdezhenMap = new HashMap<>();
    Map<String, List<String>> jiujiangMap = new HashMap<>();
    Map<String, List<String>> nanchangMap = new HashMap<>();
    Map<String, List<String>> pingxiangMap = new HashMap<>();
    Map<String, List<String>> shangraoMap = new HashMap<>();
    Map<String, List<String>> xinyuMap = new HashMap<>();
    Map<String, List<String>> yichunMap = new HashMap<>();
    Map<String, List<String>> yingtanMap = new HashMap<>();

    List<Map<String, List<String>>> cityMapList = new ArrayList<>();

    ContentForecasterAdapter contentForecasterAdapter;
    TimeForecastAdapert timeForecastAdapert;
    ArrayAdapter menuAdapter;
    int menuType = 0;
    List<GkdmClickBeen> gkdmClickBeenList;

    @Override
    public int getLayoutId() {
        return R.layout.activity_forecaster_score1;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        super.initViews(savedInstanceState);
        initBottomTime();
        initTimePick();
        initCityMapList();
        initSpinner();
        getContentAdapter();
        getTimeAdapter();
        getTestdata();
    }


    private void initBottomTime() {
        Date now = new Date();
        long nowLong = now.getTime();
        long oneDayBeforeLong = nowLong - 24 * 3600 * 1000;
        Date oneDayBefore = new Date(oneDayBeforeLong);
        String oneDayBeforeStr = getTime(oneDayBefore);
        String nowTimeStr = getTime(now);
        startTime.setText(oneDayBeforeStr);
        endTime.setText(nowTimeStr);
    }

    private void initTimePick() {
        TimePickerDialog mDialogAll = new TimePickerDialog.Builder()
                .setCallBack(new OnDateSetListener() {
                    @Override
                    public void onDateSet(TimePickerDialog timePickerView, long millseconds) {
                        String tag = timePickerView.getTag();
                        if (tag.equals("start")) {
                            startTime.setText(stampToDate(millseconds));
                        }
                        if (tag.equals("end")) {
                            endTime.setText(stampToDate(millseconds));
                        }
                    }
                })
                .setCancelStringId("取消")
                .setSureStringId("确定")
                .setTitleStringId("选择时间")
                .setYearText("年")
                .setMonthText("月")
                .setDayText("日")
                .setHourText("时")
                .setMinuteText("分")
                .setCyclic(true)
                .setMinMillseconds(dateToStamp("1990-01-01"))
                .setMaxMillseconds(dateToStamp("2030-01-01"))
                .setCurrentMillseconds(System.currentTimeMillis())
                .setThemeColor(getResources().getColor(R.color.timepicker_dialog_bg))
                .setType(Type.YEAR_MONTH_DAY)
                .setWheelItemTextNormalColor(getResources().getColor(R.color.timetimepicker_default_text_color))
                .setWheelItemTextSelectorColor(getResources().getColor(R.color.timepicker_toolbar_bg))
                .setWheelItemTextSize(12)
                .build();
        startTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialogAll.show(getSupportFragmentManager(), "start");
            }
        });
        endTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialogAll.show(getSupportFragmentManager(), "end");
            }
        });
    }

    private void initCityMapList() {
        cityMapList.add(jiangxiMap);
        cityMapList.add(ffuzhouMap);
        cityMapList.add(ganzhouMap);
        cityMapList.add(jianMap);
        cityMapList.add(jingdezhenMap);
        cityMapList.add(jiujiangMap);
        cityMapList.add(nanchangMap);
        cityMapList.add(pingxiangMap);
        cityMapList.add(shangraoMap);
        cityMapList.add(xinyuMap);
        cityMapList.add(yichunMap);
        cityMapList.add(yingtanMap);
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    private void initSpinner() {
        popupWindow = new ListPopupWindow(context);
        menu = new ArrayList<>();
        menu.add("08时");
        menu.add("20时");
        menu.add("08+20");
        menuAdapter = new ArrayAdapter(context, android.R.layout.simple_list_item_1, menu);
        popupWindow.setAdapter(menuAdapter);
        popupWindow.setAnchorView(bottom_lay);
        popupWindow.setWidth(240);   //WRAP_CONTENT会出错
        popupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setDropDownGravity(Gravity.START);
        popupWindow.setModal(true);
        popupWindow.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                if (position == 0) {
                    menu_text.setText("08时");
                    menuType = 0;
                    getTestdata();
                }
                if (position == 1) {
                    menu_text.setText("20时");
                    menuType = 1;
                    getTestdata();
                }
                if (position == 2) {
                    menu_text.setText("08+20");
                    menuType = 2;
                    getTestdata();
                }
                popupWindow.dismiss();
            }
        });
    }

    private ContentForecasterAdapter getContentAdapter() {
        if (contentForecasterAdapter == null) {
            LinearLayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
            List<Content_ForecastScore_Been> mData = new ArrayList<>();
            contentForecasterAdapter = new ContentForecasterAdapter(mData);
            content_recycleView.setLayoutManager(layoutManager);
            content_recycleView.setAdapter(contentForecasterAdapter);
        }
        return contentForecasterAdapter;
    }

    private TimeForecastAdapert getTimeAdapter() {
        if (timeForecastAdapert == null) {
            LinearLayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
            gkdmClickBeenList = new ArrayList<>();
            timeForecastAdapert = new TimeForecastAdapert(gkdmClickBeenList, context);
            time_recycleView.setLayoutManager(layoutManager);
            time_recycleView.setAdapter(timeForecastAdapert);
            timeForecastAdapert.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
                @Override
                public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                    int lastClick = ((TimeForecastAdapert) adapter).getLastClick();
                    List data = adapter.getData();
                    GkdmClickBeen gkdmClickBeen = (GkdmClickBeen) data.get(lastClick);
                    GkdmClickBeen gkdmClickBeen1 = (GkdmClickBeen) data.get(position);
                    gkdmClickBeen.setOnclick(false);
                    gkdmClickBeen1.setOnclick(true);
                    ((TimeForecastAdapert) adapter).setLastClick(position);
                    adapter.notifyItemChanged(lastClick, gkdmClickBeen);
                    adapter.notifyItemChanged(position, gkdmClickBeen1);
                    setContenData(position);
                }
            });
        }
        return timeForecastAdapert;
    }


    @OnClick({R.id.back, R.id.reload, R.id.search, R.id.menu_text})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.reload:
                getTestdata();
                break;
            case R.id.search:
                getTestdata();
                break;
            case R.id.menu_text:
                popupWindow.show();
                break;
        }
    }

    public void getTestdata() {
        progressDialog = ProgressDialog.show(context, "请稍等...", "获取数据中...", true);
        progressDialog.setCancelable(true);
        RetrofitHelper.getFeedbackAPI()
                .getForecastScore(getFullTime(startTime.getText().toString(), menuType), getFullTime(endTime.getText().toString(), menuType))
                .compose(bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(forecasterScore -> {
                    //设置时间recycle数据
                    List<Integer> agings = forecasterScore.getData().getAgings();
                    gkdmClickBeenList.clear();
                    for (int i = 0; i < agings.size(); i++) {
                        GkdmClickBeen gkdmClickBeen = new GkdmClickBeen();
                        if (i == 0) gkdmClickBeen.setOnclick(true);
                        else gkdmClickBeen.setOnclick(false);
                        gkdmClickBeen.setText(String.valueOf(agings.get(i)) + "h");
                        gkdmClickBeenList.add(gkdmClickBeen);
                    }
                    getTimeAdapter().setNewData(gkdmClickBeenList);
                    //设置内容数据
                    clearBeforeMap();
                    List<WebForecastScore.DataBean.ForecasBean> forecas = forecasterScore.getData().getForecas();
                    for (int i = 0; i < forecas.size(); i++) {
                        WebForecastScore.DataBean.ForecasBean forecasBean = forecas.get(i);
                        String forecasid = forecasBean.getForecasid();
                        List<String> value = forecasBean.getValue();
                        if (forecasBean.getAlgorithmname().equals("ts") && forecasBean.getAreaid().equals("jiangxi")) {
                            cityMapList.get(0).put(forecasid, value);
                        }
                        if (forecasBean.getAlgorithmname().equals("ts") && forecasBean.getAreaid().equals("ffuzhou")) {
                            cityMapList.get(1).put(forecasid, value);
                        }
                        if (forecasBean.getAlgorithmname().equals("ts") && forecasBean.getAreaid().equals("ganzhou")) {
                            cityMapList.get(2).put(forecasid, value);
                        }
                        if (forecasBean.getAlgorithmname().equals("ts") && forecasBean.getAreaid().equals("jian")) {
                            cityMapList.get(3).put(forecasid, value);
                        }
                        if (forecasBean.getAlgorithmname().equals("ts") && forecasBean.getAreaid().equals("jingdezhen")) {
                            cityMapList.get(4).put(forecasid, value);
                        }
                        if (forecasBean.getAlgorithmname().equals("ts") && forecasBean.getAreaid().equals("jiujiang")) {
                            cityMapList.get(5).put(forecasid, value);
                        }
                        if (forecasBean.getAlgorithmname().equals("ts") && forecasBean.getAreaid().equals("nanchang")) {
                            cityMapList.get(6).put(forecasid, value);
                        }
                        if (forecasBean.getAlgorithmname().equals("ts") && forecasBean.getAreaid().equals("pingxiang")) {
                            cityMapList.get(7).put(forecasid, value);
                        }
                        if (forecasBean.getAlgorithmname().equals("ts") && forecasBean.getAreaid().equals("shangrao")) {
                            cityMapList.get(8).put(forecasid, value);
                        }
                        if (forecasBean.getAlgorithmname().equals("ts") && forecasBean.getAreaid().equals("xinyu")) {
                            cityMapList.get(9).put(forecasid, value);
                        }
                        if (forecasBean.getAlgorithmname().equals("ts") && forecasBean.getAreaid().equals("yichun")) {
                            cityMapList.get(10).put(forecasid, value);
                        }
                        if (forecasBean.getAlgorithmname().equals("ts") && forecasBean.getAreaid().equals("yingtan")) {
                            cityMapList.get(11).put(forecasid, value);
                        }
                        //默认选择第一个时间的数据
                    }
                    setContenData(0);
                    progressDialog.dismiss();

                }, throwable -> {
                    progressDialog.dismiss();
                    LogUtils.e(throwable);
                    ToastUtils.showShort(getString(R.string.getInfo_error_toast));
                });
    }

    private void setContenData(Integer index) {
        List<Content_ForecastScore_Been> contentList = new ArrayList<>();
        for (int i = 0; i < cityMapList.size(); i++) {
            List<String> ybmode3 = cityMapList.get(i).get("ybmode3");
            List<String> ybmode7 = cityMapList.get(i).get("ybmode7");
            List<String> scmoc = cityMapList.get(i).get("scmoc");
            List<String> guidance = cityMapList.get(i).get("guidance");
            Content_ForecastScore_Been content_forecastScore_been = new Content_ForecastScore_Been();
            if (i == 0)
                content_forecastScore_been.setArea("江西省");
            if (i == 1)
                content_forecastScore_been.setArea("抚州市");
            if (i == 2)
                content_forecastScore_been.setArea("赣州市");
            if (i == 3)
                content_forecastScore_been.setArea("吉安市");
            if (i == 4)
                content_forecastScore_been.setArea("景德镇");
            if (i == 5)
                content_forecastScore_been.setArea("九江市");
            if (i == 6)
                content_forecastScore_been.setArea("南昌市");
            if (i == 7)
                content_forecastScore_been.setArea("萍乡市");
            if (i == 8)
                content_forecastScore_been.setArea("上饶市");
            if (i == 9)
                content_forecastScore_been.setArea("新余市");
            if (i == 10)
                content_forecastScore_been.setArea("宜春市");
            if (i == 11)
                content_forecastScore_been.setArea("鹰潭市");
            if (null != guidance) {
                String str = guidance.get(index);
                if (str.contains(".")) {
                    Double dou = Double.valueOf(str);
                    dou = dou * 100;
                    DecimalFormat df = new DecimalFormat("#.00");
                    content_forecastScore_been.setGuidance(df.format(dou));
                } else
                    content_forecastScore_been.setGuidance(str);
            } else content_forecastScore_been.setGuidance("");
            if (null != ybmode7) {
                String str = ybmode7.get(index);
                if (str.contains(".")) {
                    Double dou = Double.valueOf(str);
                    dou = dou * 100;
                    DecimalFormat df = new DecimalFormat("#.00");
                    content_forecastScore_been.setYbmode7(df.format(dou));
                } else
                    content_forecastScore_been.setYbmode7(str);
            } else content_forecastScore_been.setYbmode7("");
            if (null != ybmode3) {
                String str = ybmode3.get(index);
                if (str.contains(".")) {
                    Double dou = Double.valueOf(str);
                    DecimalFormat df = new DecimalFormat("#.00");
                    dou = dou * 100;
                    content_forecastScore_been.setYbmode3(df.format(dou));
                } else
                    content_forecastScore_been.setYbmode3(str);
            } else content_forecastScore_been.setYbmode3("");
            if (null != scmoc) {
                String str = scmoc.get(index);
                if (str.contains(".")) {
                    Double dou = Double.valueOf(str);
                    dou = dou * 100;
                    DecimalFormat df = new DecimalFormat("#.00");
                    content_forecastScore_been.setScmoc(df.format(dou));
                } else
                    content_forecastScore_been.setScmoc(str);
            } else content_forecastScore_been.setScmoc("");
            contentList.add(content_forecastScore_been);
        }
        getContentAdapter().setNewData(contentList);
    }

    private void clearBeforeMap() {
        jiangxiMap.clear();
        ganzhouMap.clear();
        jianMap.clear();
        jingdezhenMap.clear();
        jiujiangMap.clear();
        nanchangMap.clear();
        pingxiangMap.clear();
        shangraoMap.clear();
        xinyuMap.clear();
        yichunMap.clear();
        yingtanMap.clear();
    }

    private String getFullTime(String time, int type) {
        if (type == 0) {
            time = time.concat(" 08:00");
        }
        if (type == 1) {
            time = time.concat(" 20:00");
        }
        if (type == 2) {
            time = time.concat(" 00:00");
        }
        return time;
    }

    public String getTime(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");// 可以方便地修改日期格式
        String nowStr = dateFormat.format(date);
        return nowStr;
    }

    public long dateToStamp(String s) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = simpleDateFormat.parse(s);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date.getTime();
    }

    public String stampToDate(long stamp) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String date = simpleDateFormat.format(stamp);
        return date;
    }
}
