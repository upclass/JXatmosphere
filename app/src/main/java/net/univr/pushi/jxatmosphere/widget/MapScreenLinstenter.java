package net.univr.pushi.jxatmosphere.widget;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.MotionEvent;
import android.view.View;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.esri.arcgisruntime.concurrent.ListenableFuture;
import com.esri.arcgisruntime.geometry.Point;
import com.esri.arcgisruntime.mapping.GeoElement;
import com.esri.arcgisruntime.mapping.view.DefaultMapViewOnTouchListener;
import com.esri.arcgisruntime.mapping.view.Graphic;
import com.esri.arcgisruntime.mapping.view.IdentifyGraphicsOverlayResult;
import com.esri.arcgisruntime.mapping.view.IdentifyLayerResult;
import com.esri.arcgisruntime.mapping.view.MapView;

import net.univr.pushi.jxatmosphere.interfaces.MapI;

import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * author : Administrator wl
 * e-mail : 389456264@qq.com
 * time   : 2018/06/27
 * desc   :
 * version: 1.0
 */


public class MapScreenLinstenter extends DefaultMapViewOnTouchListener {
    private MapView mapView;
    private Point point;
    private Point collPoint;
    private String name;
    private Point radarPoint;
    private MapI mapI;


    public MapScreenLinstenter(Context context, MapView mapView, MapI mapI) {
        super(context, mapView);
        this.mapView = mapView;
        this.mapI = mapI;
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
        identifyGraphicsOverlaysAsync(screenPoint, 5, false, 1);
        return false;
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
//                        mapView.getCallout().dismiss();
//                        Graphic graphic = new Graphic(geoElement.getGeometry(), geoElement.getAttributes());
//                        Map<String, Object> selectMap = graphic.getAttributes();
//                        if(selectMap.get("isOverPlay")!=null)
//                        mapI.showIdentQueryPoint(geoElement);
//                        else ;
                    } else {
                        handler.sendEmptyMessage(0);
                    }
                } else {
                    handler.sendEmptyMessage(0);
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
                name = identifyLayerResult.getLayerContent().getName();
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
                        mapI.showIdentQueryPoint(graphic);
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

    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            mapI.updateBottomState(true);
            return false;
        }
    });

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

