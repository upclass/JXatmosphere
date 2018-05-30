package net.univr.pushi.jxatmosphere.fragments;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;

import net.univr.pushi.jxatmosphere.R;
import net.univr.pushi.jxatmosphere.adapter.GeneforeAdapter;
import net.univr.pushi.jxatmosphere.base.RxLazyFragment;
import net.univr.pushi.jxatmosphere.feature.GeneforeActivity;
import net.univr.pushi.jxatmosphere.remote.RetrofitHelper;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * A simple {@link Fragment} subclass.
 */
public class GeneforeFragment extends RxLazyFragment {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    String tag;
    String time;
    GeneforeAdapter geneforeAdapter;
    List<String> mData;
    View header;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    ProgressDialog progressDialog;


    public static GeneforeFragment newInstance(String time, String tag) {
        GeneforeFragment fragment = new GeneforeFragment();
        Bundle bundle = new Bundle();
        bundle.putString("time", time);
        bundle.putString("tag", tag);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_genefore;
    }

    @Override
    public void finishCreateView(Bundle state) {

        if (getArguments() != null) {
            time = getArguments().getString("time");
            tag = getArguments().getString("tag");
            getTestdata();
        }

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tempTime;
                Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR);
                int mMonth = c.get(Calendar.MONTH) + 1;
                int mDay = c.get(Calendar.DAY_OF_MONTH);
                if (mMonth < 10)
                    tempTime = mYear + "-0" + mMonth + "-" + mDay;
                else
                    tempTime = mYear + "-" + mMonth + "-" + mDay;
                getNewData();
                ((GeneforeActivity) getActivity()).setTime(tempTime);
            }
        });
    }


    private void getTestdata() {
        progressDialog = ProgressDialog.show(getContext(), "请稍等...", "获取数据中...", true);
        progressDialog.setCancelable(true);

        getAdapter();
        RetrofitHelper.getForecastWarn()
                .getGenefore(tag, time)
                .compose(bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(geneforeBeen -> {
                    progressDialog.dismiss();
                    getAdapter().setNewData(geneforeBeen.getData().getPic());
                    TextView content = header.findViewById(R.id.content);
                    content.setText(geneforeBeen.getData().getCont());
//                    GeneforeActivity geneforeActivity = (GeneforeActivity) getActivity();
//                    geneforeActivity.FragmentAdd();
                }, throwable -> {
                    progressDialog.dismiss();
                    LogUtils.e(throwable);
                    ToastUtils.showShort(getString(R.string.getInfo_error_toast));
                });
    }

    private GeneforeAdapter getAdapter() {
        if (geneforeAdapter == null) {
            LayoutInflater inflater = getLayoutInflater();
            header = inflater.inflate(R.layout.item_recycle_genefore_header_layout, null, false);
            LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
            mData = new ArrayList<>();
            geneforeAdapter = new GeneforeAdapter(mData);
            geneforeAdapter.addHeaderView(header);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(geneforeAdapter);
        }
        return geneforeAdapter;
    }

    private void getNewData() {

        progressDialog = ProgressDialog.show(getContext(), "请稍等...", "获取数据中...", true);
        progressDialog.setCancelable(true);
        getAdapter();
        RetrofitHelper.getForecastWarn()
                .getGeneforedefault()
                .compose(bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(geneforeBeen -> {
                    progressDialog.dismiss();
                    getAdapter().setNewData(geneforeBeen.getData().getPic());
                    TextView content = header.findViewById(R.id.content);
                    content.setText(geneforeBeen.getData().getCont());
                }, throwable -> {
                    progressDialog.dismiss();
                    LogUtils.e(throwable);
                    ToastUtils.showShort(getString(R.string.getInfo_error_toast));
                });
    }


}
