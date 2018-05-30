package net.univr.pushi.jxatmosphere.remote;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.NetworkUtils;
import com.facebook.stetho.okhttp3.StethoInterceptor;

import net.univr.pushi.jxatmosphere.MyApplication;
import net.univr.pushi.jxatmosphere.remote.api.DataForecast;
import net.univr.pushi.jxatmosphere.remote.api.FeedbackService;
import net.univr.pushi.jxatmosphere.remote.api.ForecastWarn;
import net.univr.pushi.jxatmosphere.remote.api.UserService;
import net.univr.pushi.jxatmosphere.remote.api.WeatherMonitorService;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


public class RetrofitHelper {
    private static OkHttpClient mOkHttpClient;

    static {
        initOkHttpClient();
    }

    //用户api
    public static UserService getUserAPI() {
        return createApi(UserService.class, ApiConstants.API_BASE_URL);
    }

    //服务反馈api
    public static FeedbackService getFeedbackAPI() {
        return createApi(FeedbackService.class, ApiConstants.API_BASE_URL);
    }
    //天气监测
    public static WeatherMonitorService getWeatherMonitorAPI() {
        return createApi(WeatherMonitorService.class, ApiConstants.API_BASE_URL);
    }
    //数值预报
    public static DataForecast getDataForecastAPI() {
        return createApi(DataForecast.class, ApiConstants.API_BASE_URL);
    }
    //预报预警
    public static ForecastWarn getForecastWarn() {
        return createApi(ForecastWarn.class, ApiConstants.API_BASE_URL);
    }
    /**
     * 根据传入的baseUrl，和api创建retrofit
     */
    private static <T> T createApi(Class<T> clazz, String baseUrl) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(mOkHttpClient)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(clazz);
    }

    /**
     * 初始化OKHttpClient,设置缓存,设置超时时间,设置打印日志,设置UA拦截器
     */
    private static void initOkHttpClient() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        if (mOkHttpClient == null) {
            synchronized (RetrofitHelper.class) {
                if (mOkHttpClient == null) {
                    //设置Http缓存
                    Cache cache = new Cache(new File(MyApplication.getInstance().getCacheDir(), "HttpCache"), 1024 * 1024 * 100);
                    mOkHttpClient = new OkHttpClient.Builder()
                            .cache(cache)
                            .addInterceptor(interceptor)
//                            .addNetworkInterceptor(new NetInterceptor())
                            .addNetworkInterceptor(new CacheInterceptor())
                            .addNetworkInterceptor(new StethoInterceptor())
                            .retryOnConnectionFailure(true)
                            .connectTimeout(10, TimeUnit.SECONDS)
                            .writeTimeout(20, TimeUnit.SECONDS)
                            .readTimeout(20, TimeUnit.SECONDS)
                            .build();
                }
            }
        }
    }



    /**
     * 为okhttp添加缓存，这里是考虑到服务器不支持缓存时，从而让okhttp支持缓存
     */
    private static class CacheInterceptor implements Interceptor {
        @Override
        public Response intercept(Chain chain) throws IOException {
            // 有网络时 设置缓存超时时间 10 s
            int maxAge = 10;
            // 无网络时，设置超时也为10 s
            int maxStale = 10;
            Request request = chain.request();
            if (NetworkUtils.isAvailableByPing()) {
                //有网络时只从网络获取
                request = request.newBuilder().cacheControl(CacheControl.FORCE_NETWORK).build();
            } else {
                //无网络时只从缓存中读取
                request = request.newBuilder().cacheControl(CacheControl.FORCE_CACHE).build();
//                request = request.newBuilder().cacheControl(CacheControl.FORCE_NETWORK).build();
            }
            LogUtils.i("aaaa",request.url());
            Response response = chain.proceed(request);
            if (NetworkUtils.isAvailableByPing()) {
                response = response.newBuilder()
                        .removeHeader("Pragma")
                        .header("Cache-Control", "public, max-age=" + maxAge)
                        .build();
            } else {
                response = response.newBuilder()
                        .removeHeader("Pragma")
                        .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                        .build();
            }
            return response;
        }
    }


//    private  static class  NetInterceptor implements Interceptor {
//
//
//        @Override
//        public Response intercept(Chain chain) throws IOException {
//            Request request = chain.request().newBuilder()
//                    .addHeader("Connection","close").build();
//            return  chain.proceed(request);
//        }
//    }



}
