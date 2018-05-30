package net.univr.pushi.jxatmosphere.beens;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * author : Administrator wl
 * e-mail : 389456264@qq.com
 * time   : 2018/05/11
 * desc   :
 * version: 1.0
 */


public class GeneforeBeen {
    /**
     * data : {"id":7784,"time":"18-05-11","class":"下午","pic":["http://stwx.jxgis.cnProductimagesProductGuidance下午RainRain12\u00018051120.012.png","http://stwx.jxgis.cnProductimagesProductGuidance下午RainRain12\u00018051120.024.png","http://stwx.jxgis.cnProductimagesProductGuidance下午RainRain12\u00018051120.036.png","http://stwx.jxgis.cnProductimagesProductGuidance下午RainRain12\u00018051120.048.png","http://stwx.jxgis.cnProductimagesProductGuidance下午RainRain12\u00018051120.060.png","http://stwx.jxgis.cnProductimagesProductGuidance下午RainRain12\u00018051120.072.png"],"cont":"(2018-05-11)江西省气象台今天下午六点钟发布天气预报：全省天气：今天晚上到明天吉安、抚州、赣州三市多云转阴，部分有阵雨或雷阵雨，局部中到大雨；全省其他地区阴天多云有阵雨或雷阵雨转中雨，部分大雨，局地暴雨，雷雨来时，局地伴有强雷电、短时强降水等强对流天气。风向：偏北转偏南风力：2～3级今晚到明天各地天气站名内容天气气温南昌雷阵雨23～28℃,降水概率:90%九江小雨转中雨21～26℃,瑞昌小雨转中雨21～26℃,庐山小雨转中雨16～22℃,共青城小雨转中雨21～26℃,景德镇阵雨转小到中雨22～26℃,乐平阵雨转小到中雨22～27℃,德兴阵雨21～27℃,宜春阵雨22～28℃,萍乡阵雨22～27℃,新余小雨转中雨20～27℃,吉安多云转阵雨23～31℃,高安阵雨21～28℃,樟树阵雨22～28℃,丰城阵雨22～28℃,上饶阴转阵雨21～30℃,鹰潭多云转小雨21～29℃,贵溪多云转小雨21～29℃,井冈山多云转中雨18～25℃,抚州多云转小雨22～29℃,瑞金多云转雷阵雨20～30℃,赣州多云转雷阵雨21～30℃,婺源阵雨20～27℃,许彬、周芳、刘春、肖安、罗音浩写稿值班室电话:８６２２５４５８"}
     * errmsg : 第一条数据
     * errcode : 0
     */

    private DataBean data;
    private String errmsg;
    private String errcode;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }

    public String getErrcode() {
        return errcode;
    }

    public void setErrcode(String errcode) {
        this.errcode = errcode;
    }

    public static class DataBean {
        /**
         * id : 7784
         * time : 18-05-11
         * class : 下午
         * pic : ["http://stwx.jxgis.cnProductimagesProductGuidance下午RainRain12\u00018051120.012.png","http://stwx.jxgis.cnProductimagesProductGuidance下午RainRain12\u00018051120.024.png","http://stwx.jxgis.cnProductimagesProductGuidance下午RainRain12\u00018051120.036.png","http://stwx.jxgis.cnProductimagesProductGuidance下午RainRain12\u00018051120.048.png","http://stwx.jxgis.cnProductimagesProductGuidance下午RainRain12\u00018051120.060.png","http://stwx.jxgis.cnProductimagesProductGuidance下午RainRain12\u00018051120.072.png"]
         * cont : (2018-05-11)江西省气象台今天下午六点钟发布天气预报：全省天气：今天晚上到明天吉安、抚州、赣州三市多云转阴，部分有阵雨或雷阵雨，局部中到大雨；全省其他地区阴天多云有阵雨或雷阵雨转中雨，部分大雨，局地暴雨，雷雨来时，局地伴有强雷电、短时强降水等强对流天气。风向：偏北转偏南风力：2～3级今晚到明天各地天气站名内容天气气温南昌雷阵雨23～28℃,降水概率:90%九江小雨转中雨21～26℃,瑞昌小雨转中雨21～26℃,庐山小雨转中雨16～22℃,共青城小雨转中雨21～26℃,景德镇阵雨转小到中雨22～26℃,乐平阵雨转小到中雨22～27℃,德兴阵雨21～27℃,宜春阵雨22～28℃,萍乡阵雨22～27℃,新余小雨转中雨20～27℃,吉安多云转阵雨23～31℃,高安阵雨21～28℃,樟树阵雨22～28℃,丰城阵雨22～28℃,上饶阴转阵雨21～30℃,鹰潭多云转小雨21～29℃,贵溪多云转小雨21～29℃,井冈山多云转中雨18～25℃,抚州多云转小雨22～29℃,瑞金多云转雷阵雨20～30℃,赣州多云转雷阵雨21～30℃,婺源阵雨20～27℃,许彬、周芳、刘春、肖安、罗音浩写稿值班室电话:８６２２５４５８
         */

        private int id;
        private String time;
        @SerializedName("class")
        private String classX;
        private String cont;
        private List<String> pic;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getClassX() {
            return classX;
        }

        public void setClassX(String classX) {
            this.classX = classX;
        }

        public String getCont() {
            return cont;
        }

        public void setCont(String cont) {
            this.cont = cont;
        }

        public List<String> getPic() {
            return pic;
        }

        public void setPic(List<String> pic) {
            this.pic = pic;
        }
    }
}
