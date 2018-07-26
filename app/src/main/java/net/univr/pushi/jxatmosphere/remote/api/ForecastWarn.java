package net.univr.pushi.jxatmosphere.remote.api;

import net.univr.pushi.jxatmosphere.beens.BdskBeen;
import net.univr.pushi.jxatmosphere.beens.BdybBeen;
import net.univr.pushi.jxatmosphere.beens.DsljybBeen;
import net.univr.pushi.jxatmosphere.beens.GdybtxBeen;
import net.univr.pushi.jxatmosphere.beens.GdybtxMenuBeen;
import net.univr.pushi.jxatmosphere.beens.GeneforeBeen;
import net.univr.pushi.jxatmosphere.beens.QxfxBeen;
import net.univr.pushi.jxatmosphere.beens.RadarForecastBeen;
import net.univr.pushi.jxatmosphere.beens.YuJinXinhaoBeen;
import net.univr.pushi.jxatmosphere.beens.ZytqybBeen;

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
    Observable<QxfxBeen> getQxfx(@Query("type") String type);

    //全省文字报
    @GET("genefore.do")
    Observable<GeneforeBeen> getGenefore(@Query("class") String geneclass, @Query("time") String time);

    @GET("genefore.do")
    Observable<GeneforeBeen> getGeneforedefault();

    //格点预报图形
    @GET("gdyutxAction.do")
    Observable<GdybtxBeen> getGdybt(@Query("type") String type);

    @GET("gdyutxAction!getOneMenu.do")
    Observable<GdybtxMenuBeen> getGdybtOneMenu();

    @GET("gdyutxAction!getTwoMenu.do")
    Observable<GdybtxMenuBeen> getGdybtTwoMenu(@Query("type") String type);


    //本地实况
    @GET("bdskFromCimiss.do")
    Observable<BdskBeen> getbdsk(@Query("lat") String lattitude, @Query("lon") String lontitude);

    @GET("bdyb.do")
    Observable<BdybBeen> getbdyb(@Query("lattitude") String lattitude, @Query("lontitude") String lontitude, @Query("adress") String adress);

    //雷达预报
    @GET("radarForecastFrom20.do")
    Observable<RadarForecastBeen> radarForecastFrom20(@Query("type") String type);

    //预警信号
    @GET("yujin.do")
    Observable<YuJinXinhaoBeen> getYujinInfo(@Query("tag") String tag);

    //本地实况getTest3所需预警信号数据
    @GET("yujin!getYujingbycity.do")
    Observable<YuJinXinhaoBeen> getYujinInfoToBdsk(@Query("city") String city);

    //重要天气预
    @GET("zytqybAction.do")
    Observable<ZytqybBeen> getZytqyb(@Query("startTime") String startTime, @Query("endTime") String endTime);

    //短时临近预报
//    @GET("rainForecastGirdFrom20Action.do")
//    Observable<DsljybBeen> getRainGird(@Query("type")String type,@Query("lat")String lattitude, @Query("lon")String lontitude);
    //短时临近预报
    @GET("rainForecastGirdFrom20Action!getDataList.do")
    Observable<DsljybBeen> getRainGird(@Query("lat") String lattitude, @Query("lon") String lontitude);

    @GET("rainForecastGirdFrom20Action!getDataListSum.do")
        //短时临近预报读所有数据
    Observable<DsljybBeen> getRainSumGird(@Query("lat") String lattitude, @Query("lon") String lontitude);
}
