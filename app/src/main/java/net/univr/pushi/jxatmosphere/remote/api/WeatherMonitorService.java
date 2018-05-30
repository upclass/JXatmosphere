package net.univr.pushi.jxatmosphere.remote.api;

import net.univr.pushi.jxatmosphere.beens.DmcgjcBeen;
import net.univr.pushi.jxatmosphere.beens.DmcgjcmenuBeen;
import net.univr.pushi.jxatmosphere.beens.GdybBeen;
import net.univr.pushi.jxatmosphere.beens.GkdmgcBeen;
import net.univr.pushi.jxatmosphere.beens.LdptBeen;
import net.univr.pushi.jxatmosphere.beens.SwzdBeen;
import net.univr.pushi.jxatmosphere.beens.WxytBeen;

import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * author : Administrator wl
 * e-mail : 389456264@qq.com
 * time   : 2018/05/03
 * desc   :
 * version: 1.0
 */


public interface WeatherMonitorService {
    //高空地面图
    @POST("gkdmgc!getAjax.do")
    Observable<GkdmgcBeen> getGKDMGC(@Query("item")String item);
    //气象水文站点雨量
    @POST("sk.do")
    Observable<SwzdBeen> getSWZD();
    //得到地面监测菜单
    @POST("dmcgjc!getMenu.do")
    Observable<DmcgjcmenuBeen> getDmcgjcMenu(@Query("type")String type);
    //地面监测数据
    @POST("dmcgjc!getUrlByIndex.do")
    Observable<DmcgjcBeen> getDmcgjc(@Query("type")String type,@Query("ctype")String ctype);
    //卫星云图
    @POST("wxyt.do")
    Observable<WxytBeen> getWxyt();
    //雷达拼图
    @POST("ldpt.do")
    Observable<LdptBeen> getLdpt();
    //格点预报
    @POST("gdybAction.do")
    Observable<GdybBeen> getGdyb(@Query("lon")String lon, @Query("lat")String lat, @Query("interval")String interval,@Query("coordType")String coordType ,@Query("datetime")String datetime);

}
