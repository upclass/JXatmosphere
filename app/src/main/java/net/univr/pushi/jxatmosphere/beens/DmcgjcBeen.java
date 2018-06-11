package net.univr.pushi.jxatmosphere.beens;

import java.util.List;

/**
 * author : Administrator wl
 * e-mail : 389456264@qq.com
 * time   : 2018/05/05
 * desc   :
 * version: 1.0
 */


public class DmcgjcBeen {


    /**
     * data : {"times":["10:40","10:35","10:30","10:25","10:20","10:15","10:10","10:05","10:00","09:55","09:50"],"urls":["http://stwx.jxgis.cn/sk/rain/rain_sum1/201806111040_01.png","http://stwx.jxgis.cn/sk/rain/rain_sum1/201806111035_01.png","http://stwx.jxgis.cn/sk/rain/rain_sum1/201806111030_01.png","http://stwx.jxgis.cn/sk/rain/rain_sum1/201806111025_01.png","http://stwx.jxgis.cn/sk/rain/rain_sum1/201806111020_01.png","http://stwx.jxgis.cn/sk/rain/rain_sum1/201806111015_01.png","http://stwx.jxgis.cn/sk/rain/rain_sum1/201806111010_01.png","http://stwx.jxgis.cn/sk/rain/rain_sum1/201806111005_01.png","http://stwx.jxgis.cn/sk/rain/rain_sum1/2018061110_01.png","http://stwx.jxgis.cn/sk/rain/rain_sum1/201806110955_01.png","http://stwx.jxgis.cn/sk/rain/rain_sum1/201806110950_01.png","http://stwx.jxgis.cn/sk/rain/rain_sum1/201806110945_01.png"]}
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
        private List<String> times;
        private List<String> urls;

        public List<String> getTimes() {
            return times;
        }

        public void setTimes(List<String> times) {
            this.times = times;
        }

        public List<String> getUrls() {
            return urls;
        }

        public void setUrls(List<String> urls) {
            this.urls = urls;
        }
    }
}
