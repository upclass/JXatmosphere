package net.univr.pushi.jxatmosphere.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import net.univr.pushi.jxatmosphere.R;
import net.univr.pushi.jxatmosphere.beens.GdybBeen;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static net.univr.pushi.jxatmosphere.adapter.WeathMainAdapter.initWeathTuPianJson;

/**
 * author : Administrator wl
 * e-mail : 389456264@qq.com
 * time   : 2018/05/17
 * desc   :
 * version: 1.0
 */


public class WeathMainBdybAdapter extends BaseQuickAdapter<GdybBeen.DataBean, BaseViewHolder> {
    //    String fbTime;
    Context mContext;

    public WeathMainBdybAdapter(Context context, @Nullable List<GdybBeen.DataBean> data) {
        super(R.layout.item_weath_bdyb_main_layout, data);
//        this.fbTime = fbTime;
        mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, GdybBeen.DataBean item) {
//
//        int foretime = item.getForetime();
//        long fbTimeStamp = dateToStamp(fbTime);
//        long forecastStamp = foretime * 3600 * 1000 + fbTimeStamp;
//        Date dateTime = stampToDate(forecastStamp);
//        int day = dateTime.getDate();
//        String temp=day+"日";

        Date date = null;
        String forecastTime = item.getForecastTime();
        try {
            date = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").parse(forecastTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        int month = date.getMonth() + 1;
        int day = date.getDate();
        int hours = date.getHours();
        int day1 = date.getDay();
        String day1str = null;
        if (day1 == 0) day1str = "星期天";
        if (day1 == 1) day1str = "星期一";
        if (day1 == 2) day1str = "星期二";
        if (day1 == 3) day1str = "星期三";
        if (day1 == 4) day1str = "星期四";
        if (day1 == 5) day1str = "星期五";
        if (day1 == 6) day1str = "星期六";
        String temp = month + "月" + day + "日";
        helper.setText(R.id.time, day1str);

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
        helper.setImageResource(R.id.weath, getResource(imageName));
//        helper.setText(R.id.temper_max, (String) item.getHeightTemper());
        helper.setText(R.id.temper_min, item.getTemper()+"~"+item.getHeightTemper()+"℃");
        helper.setText(R.id.tqms, tqxx);


    }

    public int getResource(String imageName) {
        Context ctx = ((Activity) mContext).getBaseContext();
        int resId = mContext.getResources().getIdentifier(imageName, "drawable", ctx.getPackageName());
        //如果没有在"mipmap"下找到imageName,将会返回0
        return resId;
    }
}
