package net.univr.pushi.jxatmosphere.remote.api;

import net.univr.pushi.jxatmosphere.beens.AppVersionBeen;
import net.univr.pushi.jxatmosphere.beens.CollectionListBeen;
import net.univr.pushi.jxatmosphere.beens.CollectionRet;
import net.univr.pushi.jxatmosphere.beens.UserRetBeen;

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
    Observable<UserRetBeen> verifyUser(@Query("phone") String phone, @Query("password") String password);

    @GET("userRegist.do")
    Observable<UserRetBeen> registUser(@Query("phone") String phone, @Query("password") String password);

    @GET("userLogin.do")
    Observable<UserRetBeen> userVerify(@Query("phone") String phone);

    @GET("userPSModify.do")
    Observable<UserRetBeen> userPSModify(@Query("phone") String phone, @Query("password") String password);

    @GET("appVersionCheackAction.do")
    Observable<AppVersionBeen> cheackAppVersion(@Query("version") String version);

    //新增用户收藏
    @GET("collecionAction.do")
    Observable<CollectionRet> addCollection(@Query("phone") String phone, @Query("url") String url,@Query("type")String type);

    //删除用户收藏
    @GET("collecionAction!delete.do")
    Observable<CollectionRet> deleteCollection(@Query("collectionId") Integer collectionId);

    //收藏列表
    @GET("collecionAction!getCurrentUserList.do")
    Observable<CollectionListBeen> collectionList(@Query("phone") String phone);
}
