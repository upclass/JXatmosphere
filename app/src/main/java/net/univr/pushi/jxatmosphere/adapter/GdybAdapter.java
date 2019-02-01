package net.univr.pushi.jxatmosphere.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import net.univr.pushi.jxatmosphere.R;
import net.univr.pushi.jxatmosphere.beens.GdybBeen1;

import java.math.BigDecimal;
import java.util.List;

import static net.univr.pushi.jxatmosphere.adapter.WeathMainAdapter.initWeathTuPianJson;

/**
 * author : Administrator wl
 * e-mail : 389456264@qq.com
 * time   : 2019/01/25
 * desc   :
 * version: 1.0
 */


public class GdybAdapter extends BaseQuickAdapter<GdybBeen1.DataBean.ContentBean,BaseViewHolder> {
    private Context context;
    public GdybAdapter(Context context,@Nullable List<GdybBeen1.DataBean.ContentBean> data) {
        super(R.layout.gdyb_recycle_item, data);
        this.context=context;
    }

    @Override
    protected void convert(BaseViewHolder helper, GdybBeen1.DataBean.ContentBean item) {
        TextView hour = helper.getView(R.id.hour);
        TextView weather_desc = helper.getView(R.id.weather_desc);
        ImageView weather_img = helper.getView(R.id.weather_img);
        TextView rainfall = helper.getView(R.id.rainfall);
        TextView wind = helper.getView(R.id.wind);
        TextView xdsd = helper.getView(R.id.xdsd);
        TextView temper = helper.getView(R.id.temper);
        hour.setText(item.getForecastTime());
        weather_desc.setText(item.getWeatherDesc());
        //得到天气图片
        String tqxx = item.getWeatherDesc();
        String picBiaoJi = "";
        JsonArray jsonElements = initWeathTuPianJson();
        for (int j = 0; j < jsonElements.size(); j++) {
            JsonObject jsonObject = jsonElements.get(j).getAsJsonObject();
            JsonElement picInfo = jsonObject.get("name");
            if (picInfo.getAsString().equals(tqxx)) {
                picBiaoJi = jsonObject.get("image").getAsString();
                break;
            } else continue;
        }
        String imageName = picBiaoJi;
        weather_img.setImageResource(getResource(imageName));
        rainfall.setText(item.getRain()+"mm");
        //设置风速
        Double windSpeed = Double.valueOf(item.getWindSpeed());
        String windSpeedStr = "";
        if (windSpeed >= 0 && windSpeed < 0.3) {
            windSpeedStr = "无风";
        } else if (windSpeed >= 0.3 && windSpeed < 3.4) {
            windSpeedStr = "微风";
        } else if (windSpeed >= 3.4 && windSpeed < 5.5) {
            windSpeedStr = "软风";
        } else if (windSpeed >= 5.5 && windSpeed < 8.0) {
            windSpeedStr = "和风";
        } else if (windSpeed >= 8.0 && windSpeed < 10.8) {
            windSpeedStr = "青劲风";
        } else if (windSpeed >= 10.8 && windSpeed < 13.9) {
            windSpeedStr = "强风";
        } else if (windSpeed >= 13.9 && windSpeed < 17.2) {
            windSpeedStr = "疾风";
        } else if (windSpeed >= 17.2 && windSpeed < 20.8) {
            windSpeedStr = "大风";
        } else if (windSpeed >= 20.8 && windSpeed < 24.5) {
            windSpeedStr = "烈风";
        } else if (windSpeed >= 24.5 && windSpeed < 28.5) {
            windSpeedStr = "狂风";
        } else {
            windSpeedStr = "飓风";
        }
        wind.setText(windSpeedStr);
        //设置相对湿度
        xdsd.setText(item.getHumidity()+"%");
        String temperStr = item.getTemper();
        Double aDouble = Double.valueOf(temperStr);
        BigDecimal bd = new BigDecimal(aDouble).setScale(0, BigDecimal.ROUND_HALF_UP);
        temper.setText(String.valueOf(Integer.parseInt(bd.toString()))+"℃");
    }

    public int getResource(String imageName) {
        Context ctx = ((Activity) context).getBaseContext();
        int resId = context.getResources().getIdentifier(imageName, "drawable", ctx.getPackageName());
        //如果没有在"mipmap"下找到imageName,将会返回0
        return resId;
    }
}
