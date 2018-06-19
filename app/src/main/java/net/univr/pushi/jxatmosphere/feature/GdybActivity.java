package net.univr.pushi.jxatmosphere.feature;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.github.abel533.echarts.axis.CategoryAxis;
import com.github.abel533.echarts.axis.SplitLine;
import com.github.abel533.echarts.axis.ValueAxis;
import com.github.abel533.echarts.code.Trigger;
import com.github.abel533.echarts.json.GsonOption;
import com.github.abel533.echarts.series.Line;

import net.univr.pushi.jxatmosphere.R;
import net.univr.pushi.jxatmosphere.base.BaseActivity;
import net.univr.pushi.jxatmosphere.beens.GdybBeen;
import net.univr.pushi.jxatmosphere.remote.RetrofitHelper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class GdybActivity extends BaseActivity {
    @BindView(R.id.webView)
    WebView mWebView;
    ProgressDialog dialog = null;
    private Object times[]=new Object[50];
    private  Object temps[]=new Object[50];
    private Object rains[]=new Object[50];
    private Object winds[]=new Object[50];
    private Object humiditys[]=new Object[50];

    @Override
    public int getLayoutId() {
        return R.layout.activity_gdyb;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        super.initViews(savedInstanceState);
        initView();
        getTestdata();
    }

    private void initView() {
        dialog = new ProgressDialog(this);
        dialog.setMessage("正在查询...");

        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setSupportZoom(true);
        webSettings.setDisplayZoomControls(true);
        mWebView.addJavascriptInterface(new WebAppInterface(this), "Android");
        mWebView.loadUrl("file:///android_asset/jsWeb/echart.html");

    }

    private void getTestdata() {
        RetrofitHelper.getWeatherMonitorAPI()
                .getGdyb("115.12573242187501", "28.555576049185973", "3", "bd09ll", "2018-05-08 08:00:00")
                .compose(bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(gdybBeen -> {
                    List<GdybBeen.DataBean> data = gdybBeen.getData();
                    for (int i = 0; i < data.size(); i++) {
                        GdybBeen.DataBean dataBean = data.get(i);
                        String forecastTime = dataBean.getForecastTime();
                        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                        try {
                            Date date = format.parse(forecastTime);
                            String shijian = date.getDate() + "日" + date.getHours() + "时";
                            times[i]=shijian;
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        temps[i]=dataBean.getTemper();
                        rains[i]=dataBean.getRain();
                        winds[i]=dataBean.getWindSpeed();
                        humiditys[i]=dataBean.getHumidity();
                    }
//                    String option=new WebAppInterface(context).getLineChartOptions(times, temps, rains, winds, humiditys);

//                    mWebView.loadUrl("javascript:loadALineChart('\"+Arrays.toString(times)+\"','\"+ Arrays.toString(temps)+\"','\"+Arrays.toString(rains)+\"','\"+Arrays.toString(winds)+\"','\"+Arrays.toString(winds)+\"')");
//                    mWebView.loadUrl("javascript:loadALineChart('\" + option + \"')");

//                    ('"+Arrays.toString(hzl_array)+"')");
                }, throwable -> {
                    LogUtils.e(throwable);
                    ToastUtils.showShort(getString(R.string.getInfo_error_toast));
                });
    }


    /**
     * 注入到JS里的对象接口
     */
    class WebAppInterface {
        Context mContext;

        public WebAppInterface(Context c) {
            mContext = c;
        }

        /**
         * 获取
         *
         * @return
         */
        @JavascriptInterface
        public String getLineChartOptions() {
            GsonOption option = markLineChartOptions();
            return option.toString();
        }


        @JavascriptInterface
        public GsonOption markLineChartOptions() {
            GsonOption option = new GsonOption();
            option.calculable(false);
            option.tooltip().trigger(Trigger.axis);
            ValueAxis valueAxis = new ValueAxis();
            option.xAxis(valueAxis);

            CategoryAxis categoryAxis = new CategoryAxis();
            categoryAxis.axisLine().onZero(false);
            categoryAxis.boundaryGap(false);
            categoryAxis.data(times[0],times[1],times[2],times[3],times[4],times[5],times[6],times[7],times[8]);
            SplitLine splitLine1 = categoryAxis.splitLine();
            splitLine1.show(false);
            option.yAxis(categoryAxis);

            Line line = new Line();
            line.smooth(true).data(20,25,48,25,2,4,45).itemStyle().normal().lineStyle().shadowColor("rgba(0,0,0,0.4)");


            Line line1= new Line();
            line1.smooth(true).data(temps).itemStyle().normal().lineStyle().shadowColor("rgba(0,0,0,0.4)");

            Line line2 = new Line();
            line2.smooth(true).data(rains).itemStyle().normal().lineStyle().shadowColor("rgba(3,2,2,2.7)");
            Line line3 = new Line();
            line3.smooth(true).data(winds).itemStyle().normal().lineStyle().shadowColor("rgba(3,2,2,2.7)");
            Line line4 = new Line();
            line4.smooth(true).data(humiditys).itemStyle().normal().lineStyle().shadowColor("rgba(3,2,2,2.7)");
            option.series(line);
            option.series(line1);
            option.series(line2);
            option.series(line3);
            option.series(line4);
            return option;
        }
    }
}

