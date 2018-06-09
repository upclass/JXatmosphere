package net.univr.pushi.jxatmosphere.adapter;

import android.content.Context;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import net.univr.pushi.jxatmosphere.R;
import net.univr.pushi.jxatmosphere.beens.YuJinXinhaoBeen;
import net.univr.pushi.jxatmosphere.utils.GetResourceInt;

import java.util.List;

/**
 * author : Administrator wl
 * e-mail : 389456264@qq.com
 * time   : 2018/05/31
 * desc   :
 * version: 1.0
 */


public class YunjinAdapter extends BaseQuickAdapter<YuJinXinhaoBeen.DataBean, BaseViewHolder> {
     private Context context;
    public YunjinAdapter(@Nullable List<YuJinXinhaoBeen.DataBean> data, Context context) {
        super(R.layout.item_yujin_recycle_layout, data);
        this.context=context;
    }

    @Override
    protected void convert(BaseViewHolder helper, YuJinXinhaoBeen.DataBean item) {
        helper.setText(R.id.zhandian, item.getDanwei());
        helper.setText(R.id.yujin_time, item.getTtime());
        helper.setText(R.id.yujin_subclass, item.getSubclass());

        String subclass = item.getSubclass();
        String substring = subclass.substring(0, subclass.length()-4);
        String picName = getPicName(substring);
        String picNameDest = picName ;
        int resource = GetResourceInt.getResource(picNameDest, context);
        helper.setImageResource(R.id.image,resource);
        helper.addOnClickListener(R.id.layout);
    }

    String getPicName(String name) {
        String josnStr = "{\n" +
                "\t\"暴雪橙色\":\"orange_snowstorm\",\n" +
                "\t\"暴雨橙色\":\"orange_rainstorm\",\n" +
                "\t\"冰雹橙色\":\"orange_hailstone\",\n" +
                "\t\"大风橙色\":\"orange_gale\",\n" +
                "\t\"大雾橙色\":\"orange_heavy_fog\",\n" +
                "\t\"道路结冰橙色\":\"orange_icy_road\",\n" +
                "\t\"干旱橙色\":\"orange_dry\",\n" +
                "\t\"高温橙色\":\"orange_high_temperature\",\n" +
                "\t\"寒潮橙色\":\"orange_code_wave\",\n" +
                "\t\"雷电橙色\":\"orange_thunder\",\n" +
                "\t\"霾橙色\":\"orange_haze\",\n" +
                "\t\"霜冻橙色\":\"orange_frost\",\n" +
                "\t\"台风橙色\":\"orange_typhoon\",\n" +
                "\n" +
                "\n" +
                "\t\"暴雪红色\":\"red_snowstorm\",\n" +
                "\t\"暴雨红色\":\"red_rainstorm\",\n" +
                "\t\"冰雹红色\":\"red_hailstone\",\n" +
                "\t\"大风红色\":\"red_gale\",\n" +
                "\t\"大雾红色\":\"red_heavy_fog\",\n" +
                "\t\"道路结冰红色\":\"red_icy_road\",\n" +
                "\t\"干旱红色\":\"red_dry\",\n" +
                "\t\"高温红色\":\"red_high_temperature\",\n" +
                "\t\"寒潮红色\":\"red_code_wave\",\n" +
                "\t\"雷电红色\":\"red_thunder\",\n" +
                "\t\"霾红色\":\"red_haze\",\n" +
                "\t\"台风红色\":\"red_typhoon\",\n" +
                "\n" +
                "\n" +
                "\t\"暴雪黄色\":\"yellow_snowstorm\",\n" +
                "\t\"暴雨黄色\":\"yellow_rainstorm\",\n" +
                "\t\"大风黄色\":\"yellow_gale\",\n" +
                "\t\"大雾黄色\":\"yellow_heavy_fog\",\n" +
                "\t\"道路结冰黄色\":\"yellow_icy_road\",\n" +
                "\t\"高温黄色\":\"yellow_high_temperature\",\n" +
                "\t\"寒潮黄色\":\"yellow_code_wave\",\n" +
                "\t\"雷电黄色\":\"yellow_thunder\",\n" +
                "\t\"霾黄色\":\"yellow_haze\",\n" +
                "\t\"霜冻黄色\":\"yellow_frost\",\n" +
                "\t\"台风黄色\":\"yellow_typhoon\",\n" +
                "\t\n" +
                "\n" +
                "\n" +
                " \t\"暴雪蓝色\":\"bule_snowstorm\",\n" +
                "\t\"暴雨蓝色\":\"bule_rainstorm\",\n" +
                "\t\"大风蓝色\":\"bule_gale\",\n" +
                "\t\"寒潮蓝色\":\"bule_code_wave\",\n" +
                "\t\"霜冻蓝色\":\"bule_frost\",\n" +
                "\t\"台风蓝色\":\"bule_typhoon\"\n" +
                "\n" +
                "\n" +
                "\t\n" +
                "}";
        JsonObject jsonObject = new JsonParser().parse(josnStr).getAsJsonObject();
        String ret = jsonObject.get(name).getAsString();
        return ret;
    }
}
