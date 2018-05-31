package net.univr.pushi.jxatmosphere.beens;

import java.util.List;

/**
 * author : Administrator wl
 * e-mail : 389456264@qq.com
 * time   : 2018/05/31
 * desc   :
 * version: 1.0
 */


public class RadarForecastBeen {

    /**
     * data : {"urlList":["http://stwx.jxgis.cn/JXST_APP_V1.0/source/rainForecast/pictures/20180530/20180530234200.F006.png","http://stwx.jxgis.cn/JXST_APP_V1.0/source/rainForecast/pictures/20180530/20180530234200.F012.png","http://stwx.jxgis.cn/JXST_APP_V1.0/source/rainForecast/pictures/20180530/20180530234200.F018.png","http://stwx.jxgis.cn/JXST_APP_V1.0/source/rainForecast/pictures/20180530/20180530234200.F024.png","http://stwx.jxgis.cn/JXST_APP_V1.0/source/rainForecast/pictures/20180530/20180530234200.F030.png","http://stwx.jxgis.cn/JXST_APP_V1.0/source/rainForecast/pictures/20180530/20180530234200.F036.png","http://stwx.jxgis.cn/JXST_APP_V1.0/source/rainForecast/pictures/20180530/20180530234200.F042.png","http://stwx.jxgis.cn/JXST_APP_V1.0/source/rainForecast/pictures/20180530/20180530234200.F048.png","http://stwx.jxgis.cn/JXST_APP_V1.0/source/rainForecast/pictures/20180530/20180530234200.F054.png","http://stwx.jxgis.cn/JXST_APP_V1.0/source/rainForecast/pictures/20180530/20180530234200.F060.png","http://stwx.jxgis.cn/JXST_APP_V1.0/source/rainForecast/pictures/20180530/20180530234200.F066.png","http://stwx.jxgis.cn/JXST_APP_V1.0/source/rainForecast/pictures/20180530/20180530234200.F072.png","http://stwx.jxgis.cn/JXST_APP_V1.0/source/rainForecast/pictures/20180530/20180530234200.F078.png","http://stwx.jxgis.cn/JXST_APP_V1.0/source/rainForecast/pictures/20180530/20180530234200.F084.png","http://stwx.jxgis.cn/JXST_APP_V1.0/source/rainForecast/pictures/20180530/20180530234200.F090.png","http://stwx.jxgis.cn/JXST_APP_V1.0/source/rainForecast/pictures/20180530/20180530234200.F096.png","http://stwx.jxgis.cn/JXST_APP_V1.0/source/rainForecast/pictures/20180530/20180530234200.F102.png","http://stwx.jxgis.cn/JXST_APP_V1.0/source/rainForecast/pictures/20180530/20180530234200.F108.png","http://stwx.jxgis.cn/JXST_APP_V1.0/source/rainForecast/pictures/20180530/20180530234200.F114.png","http://stwx.jxgis.cn/JXST_APP_V1.0/source/rainForecast/pictures/20180530/20180530234200.F120.png","http://stwx.jxgis.cn/JXST_APP_V1.0/source/rainForecast/pictures/20180530/20180530234200.F126.png","http://stwx.jxgis.cn/JXST_APP_V1.0/source/rainForecast/pictures/20180530/20180530234200.F132.png","http://stwx.jxgis.cn/JXST_APP_V1.0/source/rainForecast/pictures/20180530/20180530234200.F138.png","http://stwx.jxgis.cn/JXST_APP_V1.0/source/rainForecast/pictures/20180530/20180530234200.F144.png","http://stwx.jxgis.cn/JXST_APP_V1.0/source/rainForecast/pictures/20180530/20180530234200.F150.png","http://stwx.jxgis.cn/JXST_APP_V1.0/source/rainForecast/pictures/20180530/20180530234200.F156.png","http://stwx.jxgis.cn/JXST_APP_V1.0/source/rainForecast/pictures/20180530/20180530234200.F162.png","http://stwx.jxgis.cn/JXST_APP_V1.0/source/rainForecast/pictures/20180530/20180530234200.F168.png","http://stwx.jxgis.cn/JXST_APP_V1.0/source/rainForecast/pictures/20180530/20180530234200.F174.png","http://stwx.jxgis.cn/JXST_APP_V1.0/source/rainForecast/pictures/20180530/20180530234200.F180.png"],"timeList":["7:48","7:54","7:60","8:24","8:24","8:24","8:24","8:24","8:24","8:24","8:24","8:24","9:24","9:24","9:24","9:24","9:24","9:24","9:24","9:24","9:24","9:24","10:24","10:24","10:24","10:24","10:24","10:24","10:24","10:24"]}
     * errmsg : success
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
        private List<String> urlList;
        private List<String> timeList;

        public List<String> getUrlList() {
            return urlList;
        }

        public void setUrlList(List<String> urlList) {
            this.urlList = urlList;
        }

        public List<String> getTimeList() {
            return timeList;
        }

        public void setTimeList(List<String> timeList) {
            this.timeList = timeList;
        }
    }
}
