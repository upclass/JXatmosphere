package net.univr.pushi.jxatmosphere.feature;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jzxiang.pickerview.TimePickerDialog;
import com.jzxiang.pickerview.data.Type;
import com.jzxiang.pickerview.listener.OnDateSetListener;

import net.univr.pushi.jxatmosphere.R;
import net.univr.pushi.jxatmosphere.adapter.DecisionAdapter;
import net.univr.pushi.jxatmosphere.base.BaseActivity;
import net.univr.pushi.jxatmosphere.beens.DecisionBeen1;
import net.univr.pushi.jxatmosphere.remote.RetrofitHelper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class DecisionActivity extends BaseActivity implements View.OnClickListener {


    @BindView(R.id.main_tv)
    TextView main_tv;//气象情况反馈
    @BindView(R.id.vice_tv)
    TextView vice_tv; //气象呈阅件
    @BindView(R.id.back)
    ImageView leave;
    @BindView(R.id.content)
    RecyclerView  recyclerView;
    @BindView(R.id.time_select)
    TextView time;
    @BindView(R.id.view_fk)
    View view_fk;
    @BindView(R.id.view_cyj)
    View view_cyj;
    TimePickerDialog mDialogAll;
    String tag="qkfy";
    DecisionAdapter decisionAdapter;
    ProgressDialog  progressDialog;
    @Override
    public int getLayoutId() {
        return R.layout.activity_decision;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        super.initViews(savedInstanceState);
        initView();
        initTimePick();
        getTestdata();
    }




    private void initTimePick() {
        time.setText(String.valueOf(getNowYear(System.currentTimeMillis())));
         mDialogAll= new TimePickerDialog.Builder()
                .setCallBack(new OnDateSetListener() {
                    @Override
                    public void onDateSet(TimePickerDialog timePickerView, long millseconds) {
                        time.setText(String.valueOf(getNowYear(millseconds)));
                        getTestdata();
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
                .setType(Type.YEAR)
                .setWheelItemTextNormalColor(getResources().getColor(R.color.timetimepicker_default_text_color))
                .setWheelItemTextSelectorColor(getResources().getColor(R.color.timepicker_toolbar_bg))
                .setWheelItemTextSize(12)
                .build();
    }

    public int getNowYear(long millseconds){
        Date date=new Date(millseconds);
        Calendar calendar=Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.YEAR);
    }

    public DecisionAdapter  getDecisionAdapter(){
        if(decisionAdapter==null){
            LinearLayoutManager manager=new LinearLayoutManager(DecisionActivity.this,LinearLayoutManager.VERTICAL,false);
            List<DecisionBeen1.DataBean> dataBeans=new ArrayList<>();
            decisionAdapter=new DecisionAdapter(dataBeans);
            decisionAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
                @Override
                public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                    List data = ( adapter.getData());
                    DecisionBeen1.DataBean dataBean = (DecisionBeen1.DataBean) data.get(position);
                    Intent intent=new Intent(DecisionActivity.this,DecisionUrlActivity.class);
                    intent.putExtra("url",dataBean.getUrl());
                    startActivity(intent);
                }
            });
            recyclerView.setLayoutManager(manager);
            recyclerView.setAdapter(decisionAdapter);
        }
        return  decisionAdapter;
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

    private void initView() {
        main_tv.setOnClickListener(this);
        vice_tv.setOnClickListener(this);
        leave.setOnClickListener(this);
        time.setOnClickListener(this);
    }

    void getTestdata() {
        progressDialog = ProgressDialog.show(context, "请稍等...", "获取数据中...", true);
        progressDialog.setCancelable(true);
        RetrofitHelper.getFeedbackAPI()
                .getDecision1(time.getText().toString(),tag)
                .compose(bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(decisionBeen1 -> {
                    progressDialog.dismiss();
                    getDecisionAdapter().setNewData(decisionBeen1.getData());
                },throwable ->
                {
                    progressDialog.dismiss();
                    LogUtils.e(throwable);
                    ToastUtils.showShort(getString(R.string.getInfo_error_toast));
                });
        }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.main_tv:
                tag="qkfy";
                getTestdata();
                changeSize(0);
                view_fk.setVisibility(View.VISIBLE);
                view_cyj.setVisibility(View.INVISIBLE);
                break;
            case R.id.vice_tv:
                 tag="qxcyj";
                getTestdata();
                changeSize(1);
                view_fk.setVisibility(View.INVISIBLE);
                view_cyj.setVisibility(View.VISIBLE);
                break;
            case R.id.back:
                finish();
                break;
            case  R.id.time_select:
                mDialogAll.show(getSupportFragmentManager(),"");
                break;
        }

    }

    public void changeSize(int flag) {
        if (flag == 0) {
            vice_tv.setTextSize(15);
            main_tv.setTextSize(17);
        }
        if (flag == 1) {
            main_tv.setTextSize(15);
            vice_tv.setTextSize(17);
        }
    }


}
