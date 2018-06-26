package net.univr.pushi.jxatmosphere.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import net.univr.pushi.jxatmosphere.R;
import net.univr.pushi.jxatmosphere.beens.GdybBeen;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * author : Administrator wl
 * e-mail : 389456264@qq.com
 * time   : 2018/06/22
 * desc   :
 * version: 1.0
 */


public class PiaoGeAdapter extends BaseQuickAdapter<GdybBeen.DataBean,BaseViewHolder> {
    public PiaoGeAdapter(@Nullable List<GdybBeen.DataBean> data) {
        super(R.layout.biaoge_item, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, GdybBeen.DataBean item) {
        String forecastTime = item.getForecastTime();
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = null;
        try {
            date = format.parse(forecastTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String shijian = date.getDate() + "日" + date.getHours() + "时";
        helper.setText(R.id.time,shijian);
        helper.setText(R.id.tqms,item.getWeatherDesc());
        helper.setText(R.id.temp,item.getTemper());
        helper.setText(R.id.rain,item.getRain());
        helper.setText(R.id.windspeed,item.getWindSpeed());
        helper.setText(R.id.humitity,item.getHumidity());
    }
}
