package net.univr.pushi.jxatmosphere.remote.api;

import net.univr.pushi.jxatmosphere.beens.DecisionBeen;
import net.univr.pushi.jxatmosphere.beens.DecisionBeen1;
import net.univr.pushi.jxatmosphere.beens.DutyBeen1;
import net.univr.pushi.jxatmosphere.beens.ForecasterScore;
import net.univr.pushi.jxatmosphere.beens.WebForecastScore;

import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * author : Administrator wl
 * e-mail : 389456264@qq.com
 * time   : 2018/04/24
 * desc   :
 * version: 1.0
 */


public interface FeedbackService {
    //预报员评分表
    @POST("forecastScoreAction.do")
    Observable<WebForecastScore> getForecastScore(@Query("startTime")String startTime, @Query("endTime")String endTime);

    @POST("grade.do")
    Observable<ForecasterScore> getScore(@Query("grade")String grade);
    //决策服务
    @POST("decesionAction1.do")
    Observable<DecisionBeen1> getDecision1(@Query("time")String time, @Query("tag")String tag);

    @POST("decision.do")
    Observable<DecisionBeen> getDecision(@Query("type")String type);
    //排班表
    @POST("duty.do")
//    Observable<DutyBeen> getDuty(@Query("tag")String tag);
    Observable<DutyBeen1> getDuty(@Query("tag")String tag);
}
