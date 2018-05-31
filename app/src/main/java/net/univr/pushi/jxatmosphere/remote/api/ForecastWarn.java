package net.univr.pushi.jxatmosphere.remote.api;

import net.univr.pushi.jxatmosphere.beens.BdskBeen;
import net.univr.pushi.jxatmosphere.beens.BdybBeen;
import net.univr.pushi.jxatmosphere.beens.GdybtxBeen;
import net.univr.pushi.jxatmosphere.beens.GdybtxMenuBeen;
import net.univr.pushi.jxatmosphere.beens.GeneforeBeen;
import net.univr.pushi.jxatmosphere.beens.QxfxBeen;
import net.univr.pushi.jxatmosphere.beens.RadarForecastBeen;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * author : Administrator wl
 * e-mail : 389456264@qq.com
 * time   : 2018/05/10
 * desc   :
 * version: 1.0
 */


public interface ForecastWarn {
    //气象分析预报
    @GET("EnvironmentalPrediction.do")
    Observable<QxfxBeen> getXqfx(@Query("type") String type);

    //全省文字报
    @GET("genefore.do")
    Observable<GeneforeBeen> getGenefore(@Query("class") String geneclass, @Query("time") String time);

    @GET("genefore.do")
    Observable<GeneforeBeen> getGeneforedefault();

    //格点预报图形
    @GET("gdyutxAction.do")
    Observable<GdybtxBeen> getGdybt(@Query("type")String type);

    @GET("gdyutxAction!getOneMenu.do")
    Observable<GdybtxMenuBeen> getGdybtOneMenu();

    @GET("gdyutxAction!getTwoMenu.do")
    Observable<GdybtxMenuBeen> getGdybtTwoMenu(@Query("type")String type);


    //本地实况
    @GET("bdskFromCimiss.do")
    Observable<BdskBeen> getbdsk(@Query("lat")String lattitude,@Query("lon")String lontitude);
    @GET("bdyb.do")
    Observable<BdybBeen> getbdyb(@Query("lattitude")String lattitude, @Query("lontitude")String lontitude, @Query("adress")String adress);
    //雷达预报
    @GET("radarForecastFrom20.do")
    Observable<RadarForecastBeen> radarForecastFrom20(@Query("type")String type);
}
