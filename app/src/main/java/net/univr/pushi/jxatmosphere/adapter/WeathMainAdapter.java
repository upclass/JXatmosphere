package net.univr.pushi.jxatmosphere.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import net.univr.pushi.jxatmosphere.R;
import net.univr.pushi.jxatmosphere.beens.GdybBeen;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * author : Administrator wl
 * e-mail : 389456264@qq.com
 * time   : 2018/05/16
 * desc   :
 * version: 1.0
 */


public class WeathMainAdapter extends BaseQuickAdapter<GdybBeen.DataBean, BaseViewHolder> {
    String fbTime;
    Context mContext;


    public WeathMainAdapter(Context context, @Nullable List<GdybBeen.DataBean> data) {
        super(R.layout.item_weath_main_layout, data);
//        this.fbTime = fbTime;
        mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, GdybBeen.DataBean item) {
//        当前日期号
//        Calendar c = Calendar.getInstance();
//        int mDay = c.get(Calendar.DAY_OF_MONTH);
        Date date = null;
        Date nowDate = null;
        String forecastTime = item.getForecastTime();
        try {
            date = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").parse(forecastTime);
            SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            String NowTime = df.format(System.currentTimeMillis());
            nowDate = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").parse(NowTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        int month = date.getMonth() + 1;
        int day = date.getDate();
        int hours = date.getHours();
        int nowMonth = nowDate.getMonth() + 1;
        int nowDay = nowDate.getDate();
        String temp;
        if (nowMonth == month && nowDay == day) {

            if (hours < 10)
                temp = "今天0" + hours + "时";
            else temp = "今天" + hours + "时";
        } else if (nowMonth == month && (day - nowDay) == 1) {

            if (hours < 10)
                temp = "明天0" + hours + "时";
            else temp = "明天" + hours + "时";
        } else {
            if (hours < 10)
                temp = month + "月" + day + "日0" + hours + "时";
            else temp = month + "月" + day + "日" + hours + "时";
        }


//        String temp=month+"月"+day+"日"+hours+"时";
//        int foretime = item.getForetime();
//        long fbTimeStamp = dateToStamp(fbTime);
//        long forecastStamp = foretime * 3600 * 1000 + fbTimeStamp;
//        Date dateTime = stampToDate(forecastStamp);
//        int month = dateTime.getMonth()+1;
//        int day = dateTime.getDate();
//        int hours = dateTime.getHours();
//        String temp=month+"月"+day+"日"+hours+"时";

//        Date dateTime = stampToDate(forecastStamp);
//        int date = dateTime.getDate();
//        String tempDate = null;
//        if (date ==mDay ) tempDate = "今天" + dateTime.getHours() + "时";
//        if (date > mDay) {
//            if (date - mDay == 1)
//                tempDate = "明天" + dateTime.getHours() + "时";
//        }
//        if (date > mDay) {
//            if (date - mDay == 2)
//                tempDate = "后天" + dateTime.getHours() + "时";
//        }
//        helper.setText(R.id.forecast_time,tempDate);

        helper.setText(R.id.forecast_time, temp);


        //得到预报图片
        String tqxx = (String) item.getWeatherDesc();
        String picBiaoJi = "";
        JsonArray jsonElements = initWeathTuPianJson();
        for (int i = 0; i < jsonElements.size(); i++) {
            JsonObject jsonObject = jsonElements.get(i).getAsJsonObject();
            JsonElement picInfo = jsonObject.get("name");
            if (picInfo.getAsString().equals(tqxx)) {
                picBiaoJi = jsonObject.get("image").getAsString();
                break;
            } else continue;
        }
//        String imageName = "weath_" + picBiaoJi;
        String imageName = picBiaoJi;
        helper.setImageResource(R.id.forecast_image, getResource(imageName));
        helper.setText(R.id.forecast_weath, tqxx);
        helper.setText(R.id.forecast_temp, item.getTemper()+"℃");

    }


//    public static JsonArray initWeathTuPianJson() {
//        String jsonArrayStr = " [\n" +
//                "  {\"name\":\"晴\",\"image\":\"100\"},\n" +
//                "  {\"name\":\"多云\",\"image\":\"101\"},\n" +
//                "  {\"name\":\"少云\",\"image\":\"102\"},\n" +
//                "  {\"name\":\"晴间多云\",\"image\":\"103\"},\n" +
//                "  {\"name\":\"阴\",\"image\":\"104\"},\n" +
//                "  {\"name\":\"有风\",\"image\":\"200\"},\n" +
//                "  {\"name\":\"平静\",\"image\":\"201\"},\n" +
//                "  {\"name\":\"微风\",\"image\":\"202\"},\n" +
//                "  {\"name\":\"和风\",\"image\":\"203\"},\n" +
//                "  {\"name\":\"清风\",\"image\":\"204\"},\n" +
//                "  {\"name\":\"强风/劲风\",\"image\":\"205\"},\n" +
//                "  {\"name\":\"疾风\",\"image\":\"206\"},\n" +
//                "  {\"name\":\"大风\",\"image\":\"207\"},\n" +
//                "  {\"name\":\"烈风\",\"image\":\"208\"},\n" +
//                "  {\"name\":\"风暴\",\"image\":\"209\"},\n" +
//                "  {\"name\":\"狂爆风\",\"image\":\"210\"},\n" +
//                "  {\"name\":\"飓风\",\"image\":\"211\"},\n" +
//                "  {\"name\":\"龙卷风\",\"image\":\"212\"},\n" +
//                "  {\"name\":\"热带风暴\",\"image\":\"213\"},\n" +
//                "  {\"name\":\"阵雨\",\"image\":\"300\"},\n" +
//                "  {\"name\":\"强阵雨\",\"image\":\"301\"},\n" +
//                "  {\"name\":\"雷阵雨\",\"image\":\"302\"},\n" +
//                "  {\"name\":\"强雷阵雨\",\"image\":\"303\"},\n" +
//                "  {\"name\":\"雷阵雨伴有冰雹\",\"image\":\"304\"},\n" +
//                "  {\"name\":\"雨\",\"image\":\"305\"},\n" +
//                "  {\"name\":\"中雨\",\"image\":\"306\"},\n" +
//                "  {\"name\":\"大雨\",\"image\":\"307\"},\n" +
//                "  {\"name\":\"极端降雨\",\"image\":\"308\"},\n" +
//                "  {\"name\":\"毛毛雨/细雨\",\"image\":\"309\"},\n" +
//                "  {\"name\":\"暴雨\",\"image\":\"310\"},\n" +
//                "  {\"name\":\"大暴雨\",\"image\":\"311\"},\n" +
//                "  {\"name\":\"特大暴雨\",\"image\":\"312\"},\n" +
//                "  {\"name\":\"冻雨\",\"image\":\"313\"},\n" +
//                "  {\"name\":\"小雪\",\"image\":\"400\"},\n" +
//                "  {\"name\":\"中雪\",\"image\":\"401\"},\n" +
//                "  {\"name\":\"大雪\",\"image\":\"402\"},\n" +
//                "  {\"name\":\"暴雪\",\"image\":\"403\"},\n" +
//                "  {\"name\":\"雨夹雪\",\"image\":\"404\"},\n" +
//                "  {\"name\":\"雨雪天气\",\"image\":\"405\"},\n" +
//                "  {\"name\":\"阵雨夹雪\",\"image\":\"406\"},\n" +
//                "  {\"name\":\"阵雪\",\"image\":\"407\"},\n" +
//                "  {\"name\":\"薄雾\",\"image\":\"500\"},\n" +
//                "  {\"name\":\"雾\",\"image\":\"501\"},\n" +
//                "  {\"name\":\"霾\",\"image\":\"502\"},\n" +
//                "  {\"name\":\"扬沙\",\"image\":\"503\"},\n" +
//                "  {\"name\":\"浮尘\",\"image\":\"504\"},\n" +
//                "  {\"name\":\"沙尘暴\",\"image\":\"507\"},\n" +
//                "  {\"name\":\"强沙尘暴\",\"image\":\"508\"},\n" +
//                "  {\"name\":\"热\",\"image\":\"900\"},\n" +
//                "  {\"name\":\"冷\",\"image\":\"901\"},\n" +
//                "  {\"name\":\"未知\",\"image\":\"999\"}\n" +
//                "  ]\n";
//        JsonParser parser = new JsonParser();
//        JsonArray JsonArray = parser.parse(jsonArrayStr).getAsJsonArray();
//        return JsonArray;
//    }


    public static JsonArray initWeathTuPianJson() {
        String jsonArrayStr = " [\n" +
                "  {\"name\":\"大雪\",\"image\":\"weather_daxue_2x\"},\n" +
                "  {\"name\":\"大雨\",\"image\":\"weather_dayu_2x\"},\n" +
                "  {\"name\":\"冻雨\",\"image\":\"weather_dongyu_2x\"},\n" +
                "  {\"name\":\"多云夜\",\"image\":\"weather_duoyun_ye_2x\"},\n" +
                "  {\"name\":\"多云\",\"image\":\"weather_duoyun_2x\"},\n" +
                "  {\"name\":\"风\",\"image\":\"weather_feng_2x\"},\n" +
                "  {\"name\":\"霾\",\"image\":\"weather_mai_2x\"},\n" +
                "  {\"name\":\"晴\",\"image\":\"weather_qing_2x\"},\n" +
                "  {\"name\":\"晴夜\",\"image\":\"weather_qingye_2x\"},\n" +
                "  {\"name\":\"雾\",\"image\":\"weather_wu_2x\"},\n" +
                "  {\"name\":\"小雪\",\"image\":\"weather_xiaoxue_2x\"},\n" +
                "  {\"name\":\"小雨\",\"image\":\"weather_xiaoyu_2x\"},\n" +
                "  {\"name\":\"雨\",\"image\":\"weather_xiaoyu_2x\"},\n" +
                "  {\"name\":\"雪\",\"image\":\"weather_xiaoxue_2x\"},\n" +
                "  {\"name\":\"阴\",\"image\":\"weather_yin_2x\"},\n" +
                "  {\"name\":\"中雪\",\"image\":\"weather_zhongxue_2x\"},\n" +
                "  {\"name\":\"中雨\",\"image\":\"weather_zhongyu_2x\"}\n" +
                "  ]\n";
        JsonParser parser = new JsonParser();
        JsonArray JsonArray = parser.parse(jsonArrayStr).getAsJsonArray();
        return JsonArray;
    }

    public int getResource(String imageName) {
        Context ctx = ((Activity) mContext).getBaseContext();
        int resId = mContext.getResources().getIdentifier(imageName, "drawable", ctx.getPackageName());
        //如果没有在"mipmap"下找到imageName,将会返回0
        return resId;
    }
}
