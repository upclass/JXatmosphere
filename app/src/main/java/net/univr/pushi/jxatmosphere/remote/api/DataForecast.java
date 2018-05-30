package net.univr.pushi.jxatmosphere.remote.api;

import net.univr.pushi.jxatmosphere.beens.DateMenuOneBeen;
import net.univr.pushi.jxatmosphere.beens.DmcgjcmenuBeen;
import net.univr.pushi.jxatmosphere.beens.EcBeen;
import net.univr.pushi.jxatmosphere.beens.EcOneMenu;

import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * author : Administrator wl
 * e-mail : 389456264@qq.com
 * time   : 2018/05/09
 * desc   :
 * version: 1.0
 */


public interface DataForecast {

    //数值预报一级菜单
    @POST("szyb.do")
    Observable<EcOneMenu> getOneMenu(@Query("type")String type);

    //数值预报二级菜单
    @POST("szyb!getChildName.do")
    Observable<DmcgjcmenuBeen> getTwoMenu(@Query("type")String type, @Query("pname")String pname);

    //得到数值预报页面
    @POST("szyb!getUrl.do")
    Observable<EcBeen> getEcContent(@Query("type")String type, @Query("ctype1")String type1,@Query("ctype2")String type2);

    //得到数值预报页面
    @POST("szyb!getUrl.do")
    Observable<EcBeen> getEcContent3(@Query("type")String type, @Query("ctype1")String type1,@Query("ctype2")String type2,@Query("time")String time);

    //得到数值预报页面
    @POST("szyb!getUrl.do")
    Observable<EcBeen> getEcContent1(@Query("type")String type, @Query("ctype1")String type1);

    //得到数值预报页面
    @POST("szyb!getUrl.do")
    Observable<EcBeen> getEcContent2(@Query("type")String type, @Query("ctype1")String type1,@Query("time")String time);
    //得到一级时间菜单
    @POST("szyb!getDays.do")
    Observable<DateMenuOneBeen> getDataForecastContentBytime(@Query("type")String type);
    //得到二级时间菜单
    @POST("szyb!getTimes.do")
    Observable<DateMenuOneBeen> getDataForecastContentBytime1(@Query("type")String type,@Query("date")String date);

}
