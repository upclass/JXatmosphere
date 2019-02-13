package net.univr.pushi.jxatmosphere.feature;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.SPUtils;

import net.univr.pushi.jxatmosphere.R;
import net.univr.pushi.jxatmosphere.adapter.RecycleSlipCityAdapter;
import net.univr.pushi.jxatmosphere.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class CitySelectActivity extends BaseActivity implements View.OnClickListener, RecycleSlipCityAdapter.IonSlidingViewClickListener {

    @BindView(R.id.work_schedule_leave)
    ImageView workScheduleLeave;
    @BindView(R.id.city)
    RecyclerView recyclerView;
    @BindView(R.id.location)
    TextView location;
    @BindView(R.id.re_location)
    TextView reLocation;
    List<String> mDatas;
    RecycleSlipCityAdapter adapter;
    Boolean addOrRemove = false;

    @Override

    public int getLayoutId() {
        return R.layout.activity_city_select;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        super.initViews(savedInstanceState);
        String cityTown = getIntent().getStringExtra("cityTown");
        location.setText(cityTown);
        reLocation.setOnClickListener(this);
        workScheduleLeave.setOnClickListener(this);
        getAdapter();
    }



    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.work_schedule_leave:
                if (addOrRemove) {
                    intent.putExtra("addOrRemove", true);
                }
                setResult(3, intent);
                finish();
                break;
            case R.id.re_location:
                if (addOrRemove) {
                    intent.putExtra("addOrRemove", true);
                }
                intent.putExtra("relocation", true);
                setResult(2,intent);
                finish();
                break;
        }
    }

    @Override
    //安卓重写返回键事件
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK){
            Intent intent = new Intent();
            if (addOrRemove) {
                intent.putExtra("addOrRemove", true);
            }
            setResult(3, intent);
            finish();
        }
        return true;
    }


    RecycleSlipCityAdapter getAdapter() {
        mDatas = new ArrayList<>();
        String local_weizhi = SPUtils.getInstance().getString("local_weizhi", "");
        if (null != local_weizhi && !local_weizhi.equals("")) {
            String[] split = local_weizhi.split(",");
            for (int i = 0; i < split.length; i++) {
                mDatas.add(split[i]);
            }
        }
        adapter = new RecycleSlipCityAdapter(context, mDatas);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(linearLayoutManager);
//        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        return adapter;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 11) {
            if (resultCode == 1) {
                Bundle extras = data.getExtras();
                String lat = extras.getString("lat");
                String lon = extras.getString("lon");
                String city = extras.getString("city");
                String weizhi = extras.getString("weizhi");
                String local_lat = SPUtils.getInstance().getString("local_lat", "");
                String local_lon = SPUtils.getInstance().getString("local_lon", "");
                String local_city = SPUtils.getInstance().getString("local_city", "");
                String local_weizhi = SPUtils.getInstance().getString("local_weizhi", "");
                if (local_weizhi.equals("")) {
                    SPUtils.getInstance().put("local_lat", lat);
                    SPUtils.getInstance().put("local_lon", lon);
                    SPUtils.getInstance().put("local_city", city);
                    SPUtils.getInstance().put("local_weizhi", weizhi);
                } else {
                    local_lat = local_lat + "," + lat;
                    local_lon = local_lon + "," + lon;
                    local_city = local_city + "," + city;
                    local_weizhi = local_weizhi + "," + weizhi;
                    SPUtils.getInstance().put("local_lat", local_lat);
                    SPUtils.getInstance().put("local_lon", local_lon);
                    SPUtils.getInstance().put("local_city", local_city);
                    SPUtils.getInstance().put("local_weizhi", local_weizhi);
                }
                addOrRemove=true;
            }
        }
        getAdapter().notifyDataSetChanged();
    }

    @Override
    public void onItemClick(View view, int position) {
        String local_lat = SPUtils.getInstance().getString("local_lat", "");
        String local_lon = SPUtils.getInstance().getString("local_lon", "");
        String local_city = SPUtils.getInstance().getString("local_city", "");
        String local_weizhi = SPUtils.getInstance().getString("local_weizhi", "");
        if (null != local_weizhi && !local_weizhi.equals("")) {
            String[] lat_split = local_lat.split(",");
            String[] lon_split = local_lon.split(",");
            String[] city_split = local_city.split(",");
            String[] weizhi_split = local_weizhi.split(",");
            String lat = lat_split[position];
            String lon = lon_split[position];
            String city = city_split[position];
            String weizhi = weizhi_split[position];
            Intent intent = new Intent();
            intent.putExtra("lat", String.valueOf(lat));
            intent.putExtra("lon", String.valueOf(lon));
            intent.putExtra("weizhi", weizhi);
            intent.putExtra("city", city);
            intent.putExtra("position", position + 1);//点击了那个位置，用于刷新上一界面视图
            if (addOrRemove) {
                intent.putExtra("addOrRemove", true);
            }
            setResult(1, intent);
            finish();
        }
    }

    @Override
    public void onDeleteBtnCilck(View view, int position) {
        addOrRemove=true;
        String local_lat = SPUtils.getInstance().getString("local_lat", "");
        String local_lon = SPUtils.getInstance().getString("local_lon", "");
        String local_city = SPUtils.getInstance().getString("local_city", "");
        String local_weizhi = SPUtils.getInstance().getString("local_weizhi", "");
        if (null != local_weizhi && !local_weizhi.equals("")) {
            String[] lat_split = local_lat.split(",");
            String[] lon_split = local_lon.split(",");
            String[] city_split = local_city.split(",");
            String[] weizhi_split = local_weizhi.split(",");
            local_lat="";
            local_lon="";
            local_city ="";
            local_weizhi ="";
            for (int i = 0; i < lat_split.length; i++) {
                if (position == i) continue;
                else {
                    local_lat =local_lat+ lat_split[i] + ",";
                    local_lon = local_lon+lon_split[i] + ",";
                    local_city = local_city+city_split[i] + ",";
                    local_weizhi = local_weizhi+weizhi_split[i] + ",";
                }
            }
            if(!local_lat.equals("")){
                local_lat = local_lat.substring(0, local_lat.length() - 1);
                local_lon = local_lon.substring(0, local_lon.length() - 1);
                local_city = local_city.substring(0, local_city.length() - 1);
                local_weizhi = local_weizhi.substring(0, local_weizhi.length() - 1);
            }
            SPUtils.getInstance().put("local_lat", local_lat);
            SPUtils.getInstance().put("local_lon", local_lon);
            SPUtils.getInstance().put("local_city", local_city);
            SPUtils.getInstance().put("local_weizhi", local_weizhi);
        }
        adapter.removeData(position);
    }
}
