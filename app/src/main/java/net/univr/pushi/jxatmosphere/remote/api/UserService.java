package net.univr.pushi.jxatmosphere.remote.api;

import net.univr.pushi.jxatmosphere.beens.AppVersionBeen;
import net.univr.pushi.jxatmosphere.beens.UserBeen;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * author : Administrator wl
 * e-mail : 389456264@qq.com
 * time   : 2018/04/09
 * desc   :
 * version: 1.0
 */


public interface UserService {
    @GET("userLogin.do")
    Observable<UserBeen> verifyUser(@Query("phone") String phone,@Query("password") String password);

    @GET("userRegist.do")
    Observable<UserBeen> registUser(@Query("phone") String phone,@Query("password") String password);

    @GET("userLogin.do")
    Observable<UserBeen> userVerify(@Query("phone") String phone);

    @GET("userPSModify.do")
    Observable<UserBeen> userPSModify(@Query("phone") String phone,@Query("password") String password);

    @GET("appVersionCheackAction.do")
    Observable<AppVersionBeen> cheackAppVersion(@Query("version") String version);

}
