package net.univr.pushi.jxatmosphere.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.view.MotionEvent;
import android.view.View;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.esri.arcgisruntime.concurrent.ListenableFuture;
import com.esri.arcgisruntime.geometry.Point;
import com.esri.arcgisruntime.mapping.GeoElement;
import com.esri.arcgisruntime.mapping.view.DefaultMapViewOnTouchListener;
import com.esri.arcgisruntime.mapping.view.Graphic;
import com.esri.arcgisruntime.mapping.view.GraphicsOverlay;
import com.esri.arcgisruntime.mapping.view.IdentifyGraphicsOverlayResult;
import com.esri.arcgisruntime.mapping.view.IdentifyLayerResult;
import com.esri.arcgisruntime.mapping.view.MapView;
import com.esri.arcgisruntime.symbology.PictureMarkerSymbol;
import com.facebook.stetho.common.LogUtil;

import net.univr.pushi.jxatmosphere.R;
import net.univr.pushi.jxatmosphere.interfaces.MapCall;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

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
    Context context;
    GraphicsOverlay mMissionGraphicLayer;


    public MapScreenClickListen(Context context, MapView mapView, MapCall callBack) {
        super(context, mapView);
        this.mapView = mapView;
        this.callBack = callBack;
        this.context = context;
    }


    @Override
    public boolean onTouch(View view, MotionEvent event) {
        return super.onTouch(view, event);
    }

    @Override
    public boolean onSingleTapConfirmed(MotionEvent e) {
        if (getMissionGraphicLayer() != null)
            getMissionGraphicLayer().getGraphics().clear();
        android.graphics.Point screenPoint = new android.graphics.Point((int) e.getX(), (int) e
                .getY());
//        point = mMapView.screenToLocation(screenPoint);
//        callBack.getNewData(point);
        point = mMapView.screenToLocation(screenPoint);
        identifyGraphicsOverlaysAsync(screenPoint, 5, false, 1);
        return false;
    }

    private void addLayout() {
        Double lon = point.getX();
        Double  lat= point.getY();

        Map<String, Object> stringObjectMap = new HashMap<>();
        stringObjectMap.put("location", lat + " " + lon);
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.location);
        PictureMarkerSymbol pictureMarkerSymbol = new PictureMarkerSymbol(new BitmapDrawable(bitmap));
        pictureMarkerSymbol.setWidth(30);
        pictureMarkerSymbol.setHeight(30);
        try {
            Graphic graphic = new Graphic(new Point(lon,
                    lat,
                    mapView.getSpatialReference()), stringObjectMap, pictureMarkerSymbol);
            getMissionGraphicLayer().getGraphics().add(graphic);
        } catch (NumberFormatException exepction) {
            exepction.printStackTrace();
        }
    }

    public GraphicsOverlay getMissionGraphicLayer() {
        if (!mapView.getGraphicsOverlays().contains(mMissionGraphicLayer)) {
            if (mMissionGraphicLayer == null) {
                mMissionGraphicLayer = new GraphicsOverlay();
            }
            mapView.getGraphicsOverlays().add(mMissionGraphicLayer);
        }
        return mMissionGraphicLayer;
    }

    /**
     * i查询地图上所有可见的layer
     *
     * @param screenPoint      查询点
     * @param tolerance        缓冲范围1-100
     * @param returnPopupsOnly true不返回弹出窗口 false返回
     */
    public void identifyLayerAsync(final android.graphics.Point screenPoint, double
            tolerance, boolean returnPopupsOnly, int max) {
        final Point point = mapView.screenToLocation(screenPoint);
        LogUtils.i(point.toJson());
        LogUtils.i(mapView.getMap().getBasemap().getBaseLayers().get(0).getFullExtent());
        final ListenableFuture<List<IdentifyLayerResult>> future = mMapView.identifyLayersAsync(
                screenPoint, tolerance, returnPopupsOnly, max);
        future.addDoneListener(() -> {
            try {
                List<IdentifyLayerResult> identifyLayerResults = future.get();
                if (identifyLayerResults != null && identifyLayerResults.size() > 0) {
                    GeoElement geoElement = getIdentifyLayerResultsGeo(identifyLayerResults);
                    if (geoElement != null) {
                        LogUtils.i(geoElement.getAttributes().toString());
                    }
                    //点击图内添加覆盖物
                    addLayout();
                    callBack.getNewData(point);
                }
            } catch (InterruptedException e) {
                LogUtils.e(e.getMessage());
            } catch (ExecutionException e) {
                LogUtils.e(e.getMessage());
            } catch (Exception e) {
                e.printStackTrace();
                ToastUtils.showShort("获取数据异常");
            }
        });
    }

    private GeoElement getIdentifyLayerResultsGeo(List<IdentifyLayerResult> identifyLayerResults) {
        GeoElement AGeoElement = null;
        a:
        for (IdentifyLayerResult identifyLayerResult :
                identifyLayerResults) {
            LogUtils.e(identifyLayerResult.getError());
            List<GeoElement> geoElements = getGeoElements(identifyLayerResult);
            if (geoElements != null) {
                for (GeoElement geoElement : geoElements) {
                    AGeoElement = geoElement;
                    break a;
                }
            }
        }
        return AGeoElement;
    }

    /**
     * 递归获取有值的GeoElements
     *
     * @param identifyLayerResult
     * @return
     */
    private List<GeoElement> getGeoElements(IdentifyLayerResult identifyLayerResult) {
        if (identifyLayerResult.getElements() == null || identifyLayerResult.getElements().size() == 0) {
            if (identifyLayerResult.getSublayerResults() != null && identifyLayerResult.getSublayerResults().size() >
                    0) {
                for (IdentifyLayerResult layerResult :
                        identifyLayerResult.getSublayerResults()) {
                    List<GeoElement> geoElements = getGeoElements(layerResult);
                    if (geoElements != null && geoElements.size() > 0) {
                        return geoElements;
                    } else {
                        return null;
                    }
                }
                return null;
            } else {
                return null;
            }
        } else {
            return identifyLayerResult.getElements();
        }

    }

    /**
     * i查询地图上所有可见的 graphicsOverlay
     *
     * @param screenPoint      查询点
     * @param tolerance        缓冲范围1-100
     * @param returnPopupsOnly 是否返回弹出窗口
     */
    private void identifyGraphicsOverlaysAsync(final android.graphics.Point screenPoint, double
            tolerance, boolean returnPopupsOnly, int max) {
        final ListenableFuture<List<IdentifyGraphicsOverlayResult>> future = mMapView.identifyGraphicsOverlaysAsync(
                screenPoint, tolerance, returnPopupsOnly, max);
        future.addDoneListener(() -> {
            try {
                List<IdentifyGraphicsOverlayResult> graphicsOverlayResults = future.get();
                if (graphicsOverlayResults != null && graphicsOverlayResults.size() > 0) {
                    Graphic graphic = getGraphicsOverlayResultsGraphic(graphicsOverlayResults);
                    if (graphic != null) {
                        LogUtil.e("aaaa");
                    } else {
                        identifyLayerAsync(screenPoint, 5, false, 1);
                    }
                } else {
                    identifyLayerAsync(screenPoint, 5, false, 1);
                }
            } catch (InterruptedException e) {
                LogUtils.e(e.getMessage());
            } catch (ExecutionException e) {
                LogUtils.e(e.getMessage());
            } catch (Exception e) {
                LogUtils.e(e.getMessage());
            }
        });
    }

    /**
     * 获取可用的Graphic
     *
     * @param graphicsOverlayResults
     * @return
     */
    private Graphic getGraphicsOverlayResultsGraphic(List<IdentifyGraphicsOverlayResult>
                                                             graphicsOverlayResults) {
        Graphic aGraphic = null;
        a:
        for (IdentifyGraphicsOverlayResult identifyGraphicsOverlayResult :
                graphicsOverlayResults) {
            List<Graphic> graphics = identifyGraphicsOverlayResult.getGraphics();
            for (Graphic graphic :
                    graphics) {
                aGraphic = graphic;
                break a;
            }
        }
        return aGraphic;
    }

}

