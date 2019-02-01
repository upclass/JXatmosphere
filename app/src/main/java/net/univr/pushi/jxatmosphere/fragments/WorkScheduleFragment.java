package net.univr.pushi.jxatmosphere.fragments;

import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;

import net.univr.pushi.jxatmosphere.R;
import net.univr.pushi.jxatmosphere.adapter.WorkScheduleAdapter;
import net.univr.pushi.jxatmosphere.base.RxLazyFragment;
import net.univr.pushi.jxatmosphere.beens.DutyBeen1;
import net.univr.pushi.jxatmosphere.beens.LocalDutyBeen;
import net.univr.pushi.jxatmosphere.feature.WorkScheduleActivity;
import net.univr.pushi.jxatmosphere.remote.RetrofitHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class WorkScheduleFragment extends RxLazyFragment {

    @BindView(R.id.work_chedule_recycle)
    RecyclerView work_chedule_recycle;

    private Context mcontext;
    private WorkScheduleAdapter mAdapter;
    ProgressDialog progressDialog;
    Dialog mDialog;

    String tag;
    public static final int REQUEST_CALL_PERMISSION = 10111; //拨号请求码

    public static WorkScheduleFragment newInstance(String tag) {
        WorkScheduleFragment workScheduleFragment = new WorkScheduleFragment();
        Bundle bundle = new Bundle();
        bundle.putString("tag", tag);
        workScheduleFragment.setArguments(bundle);
        return workScheduleFragment;
    }


    @Override
    public int getLayoutResId() {
        return R.layout.fragment_work_schedule;
    }


    @Override
    public void finishCreateView(Bundle state) {

        mcontext = getActivity();
        if (getArguments() != null) {
            //取出保存的值
            tag = getArguments().getString("tag");
        }
        getTestdata();
    }


    private WorkScheduleAdapter getAdapter() {
        if (mAdapter == null) {
            LinearLayoutManager layoutManager = new LinearLayoutManager(mcontext, LinearLayoutManager.VERTICAL, false);
            List<LocalDutyBeen.DataBean.DutysBean> mData = new ArrayList<>();
            mAdapter = new WorkScheduleAdapter(getContext(), mData);
            mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
                @Override
                public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                    int id = view.getId();
                    switch (id) {
                        case R.id.phone:
                            List<LocalDutyBeen.DataBean.DutysBean> list = mAdapter.getData();
                            LocalDutyBeen.DataBean.DutysBean dutysBean = list.get(position);
                            if (((WorkScheduleActivity) getActivity()).checkReadPermission(Manifest.permission.CALL_PHONE, REQUEST_CALL_PERMISSION))
                                showDialog(dutysBean.getPhone());
//                                intentToCall(dutysBean.getPhone());
//                            ToastUtils.showShort(dutysBean.getPhone());
                            break;
                        case R.id.telePhone:
                            List<LocalDutyBeen.DataBean.DutysBean> list1 = mAdapter.getData();
                            LocalDutyBeen.DataBean.DutysBean dutysBean1 = list1.get(position);
                            if (((WorkScheduleActivity) getActivity()).checkReadPermission(Manifest.permission.CALL_PHONE, REQUEST_CALL_PERMISSION))
                                showDialog(dutysBean1.getTelePhone());
//                            intentToCall(dutysBean1.getTelePhone());
//                            ToastUtils.showShort(dutysBean1.getTelePhone());
                            break;

                    }

                }
            });
            work_chedule_recycle.setLayoutManager(layoutManager);
            work_chedule_recycle.setAdapter(mAdapter);
//            work_chedule_recycle.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        }
        return mAdapter;
    }

    public void showDialog(String phone) {
        if (mDialog == null) {
            mDialog = new Dialog(getContext(), R.style.Teldialog);
            mDialog.setContentView(R.layout.layout_tel_phone);
            mDialog.setCanceledOnTouchOutside(false);
            TextView mTextTelPhone = (TextView) mDialog.findViewById(R.id.text_tel_phone);
            mTextTelPhone.setText(phone);
            TextView mTextTelCancel = (TextView) mDialog.findViewById(R.id.text_tel_cancel);
            mTextTelCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mDialog.dismiss();
                }
            });
            TextView mTextCallPhone = (TextView) mDialog.findViewById(R.id.text_tel_call);
            mTextCallPhone.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    intentToCall(phone);
                }
            });
        }
        mDialog.show();
    }


    public void getTestdata() {
        mAdapter = null;
        progressDialog = ProgressDialog.show(getContext(), "请稍等...", "获取数据中...", true);
        progressDialog.setCancelable(true);
        getAdapter().openLoadAnimation();
        RetrofitHelper.getFeedbackAPI()
                .getDuty(tag)
                .compose(bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(DutyBeen -> {
                    progressDialog.dismiss();
                    List<Integer> lastList = getLastList(DutyBeen);
                    List<net.univr.pushi.jxatmosphere.beens.DutyBeen1.DataBean.DutysBean> dutys = DutyBeen.getData().getDutys();
                    List<LocalDutyBeen.DataBean.DutysBean> dutys1 = new ArrayList<>();
                    for (int i = 0; i < dutys.size(); i++) {
                        LocalDutyBeen.DataBean.DutysBean dutysBean = new LocalDutyBeen.DataBean.DutysBean();
                        dutysBean.setDate(dutys.get(i).getDate());
                        dutysBean.setDuty(dutys.get(i).getDuty());
                        dutysBean.setId(dutys.get(i).getId());
                        dutysBean.setName(dutys.get(i).getName());
                        dutysBean.setProperty(dutys.get(i).getProperty());
                        dutysBean.setPhone(dutys.get(i).getPhone());
                        dutysBean.setTelePhone(dutys.get(i).getTelePhone());
                        if (lastList.contains(i)) {
                            dutysBean.setDiver(true);
                        } else dutysBean.setDiver(false);
                        dutys1.add(dutysBean);
                    }
                    if (tag.equals("week")) {
                        int weizhi = 0;
                        long time = System.currentTimeMillis();
                        Date nowdate = new Date(time);
                        String mat = "yyyy-MM-dd";
                        SimpleDateFormat format = new SimpleDateFormat(mat);
                        String nowdate1 = format.format(nowdate);
//                        List<LocalDutyBeen.DataBean.DutysBean> dutys = DutyBeen.getData().getDutys();
                        for (int i = 0; i < dutys1.size(); i++) {
                            String date = dutys1.get(i).getDate();
                            if (date.equals(nowdate1)) {
                                weizhi = i;
                                break;
                            }
                        }
                        getAdapter().setNewData(dutys1);
                        work_chedule_recycle.scrollToPosition(weizhi);
                    } else {
                        getAdapter().setNewData(dutys1);
                    }

                }, throwable -> {
                    progressDialog.dismiss();
                    LogUtils.e(throwable);
                    ToastUtils.showShort(getString(R.string.getInfo_error_toast));
                });
    }

    public void scrollTo0() {
        if (work_chedule_recycle != null && mAdapter.getData() != null && mAdapter.getData().size() > 0)
            work_chedule_recycle.scrollToPosition(0);
    }


    public List<Integer> findDiver(DutyBeen1 dutyBeen) {
        String init = "";
        List<Integer> retList = new ArrayList<>();
        List<DutyBeen1.DataBean.DutysBean> dutys = dutyBeen.getData().getDutys();
        for (int i = 0; i < dutys.size(); i++) {
            String date = dutys.get(i).getDate();
            if (!date.equals(init)) {
                init = date;
                retList.add(i);
            }
        }
        return retList;
    }


    public List<Integer> getLastList(DutyBeen1 dutyBeen) {
        List<Integer> diver = findDiver(dutyBeen);
        diver.remove(0);
        for (int i = 0; i < diver.size(); i++) {
            Integer integer = diver.get(i);
            diver.set(i, integer - 1);
        }
        return diver;
    }


    private void intentToCall(String phoneNumber) {
        Intent intent = new Intent(Intent.ACTION_CALL);
        Uri data = Uri.parse("tel:" + phoneNumber);
        intent.setData(data);
        startActivity(intent);
    }


}
