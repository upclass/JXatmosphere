package net.univr.pushi.jxatmosphere.feature;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.jzxiang.pickerview.TimePickerDialog;
import com.jzxiang.pickerview.data.Type;
import com.jzxiang.pickerview.listener.OnDateSetListener;

import net.univr.pushi.jxatmosphere.R;
import net.univr.pushi.jxatmosphere.adapter.GeneforeAdapter;
import net.univr.pushi.jxatmosphere.base.BaseActivity;
import net.univr.pushi.jxatmosphere.beens.GeneforeBeen;
import net.univr.pushi.jxatmosphere.remote.RetrofitHelper;
import net.univr.pushi.jxatmosphere.widget.FullyLinearLayoutManager;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class GeneforeActivity1 extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.time)
    TextView time;
    String timeStr;
    int mYear;
    int mMonth;
    int mDay;
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.reload)
    ImageView reload;

    @BindView(R.id.relative2)
    RelativeLayout relative2;
    @BindView(R.id.relative3)
    RelativeLayout relative3;
    @BindView(R.id.relative4)
    RelativeLayout relative4;


    @BindView(R.id.title2)
    TextView title2;
    @BindView(R.id.tabline2)
    View tabline2;
    @BindView(R.id.title3)
    TextView title3;
    @BindView(R.id.tabline3)
    View tabline3;
    @BindView(R.id.title4)
    TextView title4;
    @BindView(R.id.tabline4)
    View tabline4;

    @BindView(R.id.content)
    TextView content;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    private String tag;
    @BindView(R.id.left)
    ImageView left;
    @BindView(R.id.right)
    ImageView right;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    TimePickerDialog mDialogAll;
    List<String> mData;
    GeneforeAdapter geneforeAdapter;
    ProgressDialog progressDialog;

    @Override
    public int getLayoutId() {
        return R.layout.activity_genefore1;
    }


    @Override
    public void initViews(Bundle savedInstanceState) {
        getNowTime();
        time.setText(timeStr);
        time.setOnClickListener(this);
        reload.setOnClickListener(this);
        back.setOnClickListener(this);
        relative2.setOnClickListener(this);
        relative3.setOnClickListener(this);
        relative4.setOnClickListener(this);
        left.setOnClickListener(this);
        right.setOnClickListener(this);
        fab.setOnClickListener(this);
        getTestdata();
    }

    private void getNowTime() {
        Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH) + 1;
        mDay = c.get(Calendar.DAY_OF_MONTH);
        if (mMonth < 10)
            timeStr = mYear + "-0" + mMonth + "-";
        else
            timeStr = mYear + "-" + mMonth + "-";
        if (mDay < 10)
            timeStr = timeStr + "0" + mDay;
        else
            timeStr = timeStr + mDay;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.reload:
                if(tag==null||tag.equals("")){
                    getTestdata();
                }else{
                    String timeParams = timeStr.substring(2, timeStr.length());
                    getTestdataByTime(timeParams);
                }
                break;
            case R.id.back:
                finish();
                break;
            case R.id.relative2:
                changeMenuStyle(0);
                String timeParams = timeStr.substring(2, timeStr.length());
                tag="早晨";
                getTestdataByTime(timeParams);

                break;
            case R.id.relative3:
                changeMenuStyle(1);
                timeParams = timeStr.substring(2, timeStr.length());
                tag="中午";
                getTestdataByTime(timeParams);
                break;
            case R.id.relative4:
                changeMenuStyle(2);
                timeParams = timeStr.substring(2, timeStr.length());
                tag="下午";
                getTestdataByTime(timeParams);
                break;
            case R.id.time:
                initTimePick().show(getSupportFragmentManager(), "null");
                break;
            case R.id.left:
                long l = dateToStamp(timeStr);
                long l1 = l - 3600 * 1000 * 24;
                time.setText(stampToDate(l1));
                timeStr = stampToDate(l1);
                timeParams = timeStr.substring(2, timeStr.length());
                getTestdataByTime(timeParams);
                break;
            case R.id.right:
                String s = time.getText().toString();
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                String format1 = format.format(new Date());
                if (format1.equals(s)) {
                    ;
                } else {
                    long l2 = dateToStamp(timeStr);
                    long l3 = l2 + 3600 * 1000 * 24;
                    time.setText(stampToDate(l3));
                    timeStr = stampToDate(l3);
                    timeParams = timeStr.substring(2, timeStr.length());
                    getTestdataByTime(timeParams);
                }
                break;
            case R.id.fab:
                String tempTime;
                Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR);
                int mMonth = c.get(Calendar.MONTH) + 1;
                int mDay = c.get(Calendar.DAY_OF_MONTH);
                if (mMonth < 10)
                    tempTime = mYear + "-0" + mMonth + "-" + mDay;
                else
                    tempTime = mYear + "-" + mMonth + "-" + mDay;
                getTestdata();
                setTime(tempTime);
                timeStr = time.getText().toString();
                break;
        }
    }


    private GeneforeAdapter getAdapter() {
        if (geneforeAdapter == null) {
            FullyLinearLayoutManager layoutManager = new FullyLinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
            mData = new ArrayList<>();
            geneforeAdapter = new GeneforeAdapter(mData, context);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(geneforeAdapter);
            recyclerView.setHasFixedSize(true);
            recyclerView.setNestedScrollingEnabled(false);
        }
        return geneforeAdapter;
    }

    private TimePickerDialog initTimePick() {
        if (mDialogAll == null) {
            mDialogAll = new TimePickerDialog.Builder()
                    .setCallBack(new OnDateSetListener() {
                        @Override
                        public void onDateSet(TimePickerDialog timePickerView, long millseconds) {
                            time.setText(stampToDate(millseconds));
                            timeStr = time.getText().toString();
                            String timeParams = timeStr.substring(2, timeStr.length());
                            getTestdataByTime(timeParams);
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
            return mDialogAll;
        }
        return mDialogAll;
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


    void getTestdata() {
        progressDialog = ProgressDialog.show(context, "请稍等...", "获取数据中...", true);
        progressDialog.setCancelable(true);
        getAdapter();
        RetrofitHelper.getForecastWarn()
                .getGeneforedefault()
                .compose(bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(geneforeBeen -> {
                    progressDialog.dismiss();
                    GeneforeBeen.DataBean data = geneforeBeen.getData();
                    tag = data.getClassX();
                    content.setText(data.getCont());
                    getAdapter().setNewData(data.getPic());
                    if(tag.equals("早晨")){
                        tabline2.setVisibility(View.VISIBLE);
                        tabline3.setVisibility(View.INVISIBLE);
                        tabline4.setVisibility(View.INVISIBLE);
                    }
                    if(tag.equals("中午")){
                        tabline2.setVisibility(View.INVISIBLE);
                        tabline3.setVisibility(View.VISIBLE);
                        tabline4.setVisibility(View.INVISIBLE);
                    }
                    if(tag.equals("下午")){
                        tabline2.setVisibility(View.INVISIBLE);
                        tabline3.setVisibility(View.INVISIBLE);
                        tabline4.setVisibility(View.VISIBLE);
                    }
                }, throwable ->
                {
                    progressDialog.dismiss();
                    LogUtils.e(throwable);
                    ToastUtils.showShort(getString(R.string.getInfo_error_toast));
                });
    }

    void getTestdataByTime(String time) {
        progressDialog = ProgressDialog.show(context, "请稍等...", "获取数据中...", true);
        progressDialog.setCancelable(true);
        getAdapter();
        RetrofitHelper.getForecastWarn()
                .getGenefore(tag, time)
                .compose(bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(geneforeBeen -> {
                    progressDialog.dismiss();
                    GeneforeBeen.DataBean data = geneforeBeen.getData();
                    content.setText(data.getCont());
                    getAdapter().setNewData(data.getPic());
                }, throwable ->
                {
                    progressDialog.dismiss();
                    LogUtils.e(throwable);
                    ToastUtils.showShort(getString(R.string.getInfo_error_toast));
                });
    }



    void changeMenuStyle(int i) {
                if (i == 0) {
                    title2.setTextSize(17);
                    title3.setTextSize(15);
                    title4.setTextSize(15);
                    tabline2.setVisibility(View.VISIBLE);
                    tabline3.setVisibility(View.INVISIBLE);
                    tabline4.setVisibility(View.INVISIBLE);
                }
                if (i == 1) {
                    title2.setTextSize(15);
                    title3.setTextSize(17);
                    title4.setTextSize(15);
                    tabline2.setVisibility(View.INVISIBLE);
                    tabline3.setVisibility(View.VISIBLE);
                    tabline4.setVisibility(View.INVISIBLE);
                }
                if (i == 2) {
                    title2.setTextSize(15);
                    title3.setTextSize(15);
                    title4.setTextSize(17);
                    tabline2.setVisibility(View.INVISIBLE);
                    tabline3.setVisibility(View.INVISIBLE);
                    tabline4.setVisibility(View.VISIBLE);
                }

    }


    public void setTime(String shijian) {
        time.setText(shijian);
    }

}
