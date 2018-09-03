package net.univr.pushi.jxatmosphere.interfaces;


import com.esri.arcgisruntime.geometry.Point;
import com.esri.arcgisruntime.mapping.view.Graphic;

/**
 * author : Administrator wl
 * e-mail : 389456264@qq.com
 * time   : 2018/07/03
 * desc   :
 * version: 1.0
 */


public interface MapCall {
   void  getNewData(Point point);
   void clearGra();
   void addGra(Graphic graphic);

}
