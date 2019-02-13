package net.univr.pushi.jxatmosphere.feature;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.chad.library.adapter.base.BaseQuickAdapter;

import net.univr.pushi.jxatmosphere.R;
import net.univr.pushi.jxatmosphere.adapter.LocationAdapter;
import net.univr.pushi.jxatmosphere.base.BaseActivity;
import net.univr.pushi.jxatmosphere.beens.Locationbeen;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class LocationChangeActivity extends BaseActivity {

    @BindView(R.id.app_leave)
    ImageView appLeave;
    @BindView(R.id.app_leave_contain)
    LinearLayout appLeaveContain;
    @BindView(R.id.titleBar_title)
    EditText sousuo_tv;
    @BindView(R.id.app_location)
    ImageView appLocation;
    @BindView(R.id.location_contain)
    LinearLayout locationContain;
    @BindView(R.id.recyclerView_search)
    RecyclerView recyclerView_search;
    LocationAdapter locationAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_location_change;
    }


    LocationAdapter getLocationAdapter() {
        if (locationAdapter == null) {
            LinearLayoutManager manager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
            List<Locationbeen> locationbeenList = new ArrayList<>();
            locationAdapter = new LocationAdapter(locationbeenList);
            recyclerView_search.setAdapter(locationAdapter);
            recyclerView_search.setLayoutManager(manager);
            locationAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
                @Override
                public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    boolean isOpen = imm.isActive();//isOpen若返回true，则表示输入法打开
                    if (isOpen) imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                    List data = adapter.getData();
                    Locationbeen locationbeen = (Locationbeen) data.get(position);
                    Double lat = locationbeen.getLat();
                    Double lon = locationbeen.getLon();
                    String weizhi = locationbeen.getWeizhi();
                    String city = locationbeen.getCity();
                    Intent intent = new Intent();
                    intent.putExtra("lat", String.valueOf(lat));
                    intent.putExtra("lon", String.valueOf(lon));
                    intent.putExtra("weizhi", weizhi);
                    intent.putExtra("city", city);
                    setResult(1, intent);
                    finish();
                }
            });
            return locationAdapter;
        }
        return locationAdapter;
    }


    @Override
    public void initViews(Bundle savedInstanceState) {
        sousuo_tv.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String s1 = s.toString();
                PoiSearch.Query query = new PoiSearch.Query(s1, "", "360000");
                query.setPageSize(20);
                PoiSearch poiSearch = new PoiSearch(context, query);
                poiSearch.setOnPoiSearchListener(new PoiSearch.OnPoiSearchListener() {
                    @Override
                    public void onPoiSearched(PoiResult poiResult, int i) {
                        List<Locationbeen> data = new ArrayList();
                        ArrayList<PoiItem> pois = poiResult.getPois();
                        for (int j = 0; j < pois.size(); j++) {
                            PoiItem poiItem = pois.get(j);
                            LatLonPoint latLonPoint = poiItem.getLatLonPoint();
                            double latitude = latLonPoint.getLatitude();
                            double longitude = latLonPoint.getLongitude();
                            String s = poiItem.toString();
//                            String s2 = poiItem.getProvinceName() + poiItem.getCityName() + poiItem.getAdName();
                            String s2 = poiItem.getCityName() + poiItem.getAdName();
                            Locationbeen locationBean = new Locationbeen();
                            locationBean.setContent(s);
                            locationBean.setWeizhi(s2);
                            String cityName = poiItem.getCityName();
                            cityName = cityName.replace("市", "");
                            locationBean.setCity(cityName);
                            locationBean.setLat(latitude);
                            locationBean.setLon(longitude);
                            data.add(locationBean);
                        }
                        getLocationAdapter();
                        locationAdapter.setNewData(data);
                    }

                    @Override
                    public void onPoiItemSearched(PoiItem poiItem, int i) {

                    }
                });
                poiSearch.searchPOIAsyn();
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    @OnClick({R.id.app_leave, R.id.app_location})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.app_leave:
                finish();
                break;
            case R.id.app_location:
                Intent intent = new Intent();
                setResult(2, intent);
                finish();
                break;
        }
    }


}
