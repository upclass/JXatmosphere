package net.univr.pushi.jxatmosphere.widget;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;

import com.esri.arcgisruntime.geometry.Point;
import com.esri.arcgisruntime.mapping.view.DefaultMapViewOnTouchListener;
import com.esri.arcgisruntime.mapping.view.MapView;

import net.univr.pushi.jxatmosphere.interfaces.MapCall;

/**
 * author : Administrator wl
 * e-mail : 389456264@qq.com
 * time   : 2018/07/03
 * desc   :
 * version: 1.0
 */


public class MapScreenClickListen extends DefaultMapViewOnTouchListener {

    private MapView mapView;
    private Point point;
    private MapCall callBack;



    public MapScreenClickListen(Context context, MapView mapView,MapCall callBack) {
        super(context, mapView);
        this.mapView = mapView;
        this.callBack=callBack;
    }


    @Override
    public boolean onTouch(View view, MotionEvent event) {
        return super.onTouch(view, event);
    }

    @Override
    public boolean onSingleTapConfirmed(MotionEvent e) {
        android.graphics.Point screenPoint = new android.graphics.Point((int) e.getX(), (int) e
                .getY());
        point = mMapView.screenToLocation(screenPoint);
        callBack.getNewData(point);
        return false;
    }


}

