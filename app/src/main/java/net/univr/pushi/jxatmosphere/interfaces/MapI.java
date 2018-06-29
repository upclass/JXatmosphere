package net.univr.pushi.jxatmosphere.interfaces;

import com.esri.arcgisruntime.mapping.GeoElement;
import com.esri.arcgisruntime.mapping.view.Graphic;

/**
 * author : Administrator wl
 * e-mail : 389456264@qq.com
 * time   : 2018/06/27
 * desc   :
 * version: 1.0
 */


public interface MapI {
    /**
     * 更改底部菜单栏状态
     * @param b
     */
    public void updateBottomState(Boolean b);

    /**
     * 显示i查询点结果
     * @param geoElement
     */
    void showIdentQueryPoint(GeoElement geoElement);
    /**
     * 显示i查询点结果
     * @param graphic
     */
    void showIdentQueryPoint(Graphic graphic);
}
