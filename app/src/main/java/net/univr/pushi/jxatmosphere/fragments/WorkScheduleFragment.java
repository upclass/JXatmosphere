package net.univr.pushi.jxatmosphere.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;

import net.univr.pushi.jxatmosphere.R;
import net.univr.pushi.jxatmosphere.adapter.WorkScheduleAdapter;
import net.univr.pushi.jxatmosphere.base.RxLazyFragment;
import net.univr.pushi.jxatmosphere.beens.DutyBeen;
import net.univr.pushi.jxatmosphere.remote.RetrofitHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class WorkScheduleFragment extends RxLazyFragment{

    @BindView(R.id.work_chedule_recycle)
    RecyclerView work_chedule_recycle;

    private Context mcontext;
    private WorkScheduleAdapter mAdapter;
    ProgressDialog progressDialog;


    String tag;


    public static  WorkScheduleFragment newInstance(String tag){
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

        mcontext=getActivity();
        if(getArguments()!=null){
            //取出保存的值
            tag=getArguments().getString("tag");
        }
        progressDialog = ProgressDialog.show(getContext(), "请稍等...", "获取数据中...", true);
        progressDialog.setCancelable(true);
        getTestdata();
    }



    private WorkScheduleAdapter getAdapter() {
        if (mAdapter == null) {
            LinearLayoutManager layoutManager = new LinearLayoutManager(mcontext, LinearLayoutManager.VERTICAL, false);
            ArrayList<DutyBeen.DataBean.DutysBean> mData=new ArrayList<>();
            mAdapter=new WorkScheduleAdapter(mData);
            work_chedule_recycle.setLayoutManager(layoutManager);
            work_chedule_recycle.setAdapter(mAdapter);
            work_chedule_recycle.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL));

        }
        return mAdapter;
    }

    private void getTestdata() {

        getAdapter().openLoadAnimation();
        RetrofitHelper.getFeedbackAPI()
                .getDuty(tag)
                .compose(bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(DutyBeen -> {
                    progressDialog.dismiss();
                    if(tag.equals("week")){
                        int weizhi=0;
                        long time=System.currentTimeMillis();
                        Date nowdate=new Date(time);
                        String mat="yyyy-MM-dd";
                        SimpleDateFormat format=new SimpleDateFormat(mat);
                        String nowdate1 = format.format(nowdate);
                        List<DutyBeen.DataBean.DutysBean> dutys = DutyBeen.getData().getDutys();
                        for (int i = 0; i < dutys.size(); i++) {
                            String date = dutys.get(i).getDate();
                            if (date.equals(nowdate1)){
                                weizhi=i;
                                break;
                            }
                        }
                        getAdapter().setNewData(DutyBeen.getData().getDutys());
                        work_chedule_recycle.scrollToPosition(weizhi);
                    }else getAdapter().setNewData(DutyBeen.getData().getDutys());


//                    LogUtils.i(ForecasterScore.getData().getTittleName());
                }, throwable -> {
                    progressDialog.dismiss();
                    LogUtils.e(throwable);
                    ToastUtils.showShort(getString(R.string.getInfo_error_toast));
                });
    }

}
