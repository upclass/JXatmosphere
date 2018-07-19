package net.univr.pushi.jxatmosphere.fragments;


import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;

import net.univr.pushi.jxatmosphere.R;
import net.univr.pushi.jxatmosphere.adapter.ForecasterAdapter;
import net.univr.pushi.jxatmosphere.base.RxLazyFragment;
import net.univr.pushi.jxatmosphere.beens.ForecasterScore;
import net.univr.pushi.jxatmosphere.remote.RetrofitHelper;

import java.util.ArrayList;

import butterknife.BindView;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * A simple {@link Fragment} subclass.
 */
public class ForecasterScoreFragment extends RxLazyFragment {
    @BindView(R.id.forecaster_score_recycle_view)
    RecyclerView mRecyclerView;
    String grade;
    private ProgressDialog progressDialog = null;


    private Context mcontext;
    private ForecasterAdapter mAdapter;

    public static ForecasterScoreFragment newInstance(String grade) {
        ForecasterScoreFragment forecasterScoreFragment = new ForecasterScoreFragment();
        Bundle bundle = new Bundle();
        bundle.putString("grade", grade);
        forecasterScoreFragment.setArguments(bundle);
        return forecasterScoreFragment;
    }


    @Override
    public int getLayoutResId() {
        return R.layout.fragment_forecaster_score;
    }

    @Override
    public void finishCreateView(Bundle state) {

        mcontext = getActivity();
        if (getArguments() != null) {
            //取出保存的值
            grade = getArguments().getString("grade");
        }
        getTestdata();
    }


    private ForecasterAdapter getAdapter() {
        if (mAdapter == null) {
            LinearLayoutManager layoutManager = new LinearLayoutManager(mcontext, LinearLayoutManager.VERTICAL, false);
            ArrayList<ForecasterScore.DataBean.DutygradeBean> mData = new ArrayList<>();
            mAdapter = new ForecasterAdapter(mData);
            mRecyclerView.setLayoutManager(layoutManager);
            mRecyclerView.setAdapter(mAdapter);
            mAdapter.addHeaderView(LayoutInflater.from(mcontext).inflate(R.layout.head_forecaster_score_layout, null));
            mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        }
        return mAdapter;
    }

    public void getTestdata() {
//        if (grade.equals("main")) {
        progressDialog = ProgressDialog.show(getActivity(), "请稍等...", "获取数据中...", true);
        progressDialog.setCancelable(true);
//        }
        getAdapter().openLoadAnimation();
        RetrofitHelper.getFeedbackAPI()
                .getScore(grade)
                .compose(bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(ForecasterScore -> {
                    getAdapter().setNewData(ForecasterScore.getData().getDutygrade());
//                    if (progressDialog != null) {
                    progressDialog.dismiss();
//                    }
//                    LogUtils.i(ForecasterScore.getData().getTittleName());
                }, throwable -> {
//                    if (progressDialog != null) {
                    progressDialog.dismiss();
//                    }
                    LogUtils.e(throwable);
                    ToastUtils.showShort(getString(R.string.getInfo_error_toast));
                });
    }

}
