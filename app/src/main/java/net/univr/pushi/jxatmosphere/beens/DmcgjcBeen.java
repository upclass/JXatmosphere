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
     * data : [{"time":["06:00","12:00","18:00","24:00","30:00","36:00","42:00","48:00","54:00","00:00","06:00","12:00"],"ctype":"rain_sum_6","type":"rain","url":["http://stwx.jxgis.cn/sk/rain/rain_sum_6/201805181506.png","http://stwx.jxgis.cn/sk/rain/rain_sum_6/201805181512.png","http://stwx.jxgis.cn/sk/rain/rain_sum_6/201805181518.png","http://stwx.jxgis.cn/sk/rain/rain_sum_6/201805181524.png","http://stwx.jxgis.cn/sk/rain/rain_sum_6/201805181530.png","http://stwx.jxgis.cn/sk/rain/rain_sum_6/201805181536.png","http://stwx.jxgis.cn/sk/rain/rain_sum_6/201805181542.png","http://stwx.jxgis.cn/sk/rain/rain_sum_6/201805181548.png","http://stwx.jxgis.cn/sk/rain/rain_sum_6/201805181554.png","http://stwx.jxgis.cn/sk/rain/rain_sum_6/201805181600.png","http://stwx.jxgis.cn/sk/rain/rain_sum_6/201805181606.png","http://stwx.jxgis.cn/sk/rain/rain_sum_6/201805181612.png"]}]
     * errmsg : success
     * errcode : 0
     */

    private String errmsg;
    private String errcode;
    private List<DataBean> data;

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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * time : ["06:00","12:00","18:00","24:00","30:00","36:00","42:00","48:00","54:00","00:00","06:00","12:00"]
         * ctype : rain_sum_6
         * type : rain
         * url : ["http://stwx.jxgis.cn/sk/rain/rain_sum_6/201805181506.png","http://stwx.jxgis.cn/sk/rain/rain_sum_6/201805181512.png","http://stwx.jxgis.cn/sk/rain/rain_sum_6/201805181518.png","http://stwx.jxgis.cn/sk/rain/rain_sum_6/201805181524.png","http://stwx.jxgis.cn/sk/rain/rain_sum_6/201805181530.png","http://stwx.jxgis.cn/sk/rain/rain_sum_6/201805181536.png","http://stwx.jxgis.cn/sk/rain/rain_sum_6/201805181542.png","http://stwx.jxgis.cn/sk/rain/rain_sum_6/201805181548.png","http://stwx.jxgis.cn/sk/rain/rain_sum_6/201805181554.png","http://stwx.jxgis.cn/sk/rain/rain_sum_6/201805181600.png","http://stwx.jxgis.cn/sk/rain/rain_sum_6/201805181606.png","http://stwx.jxgis.cn/sk/rain/rain_sum_6/201805181612.png"]
         */

        private String ctype;
        private String type;
        private List<String> time;
        private List<String> url;

        public String getCtype() {
            return ctype;
        }

        public void setCtype(String ctype) {
            this.ctype = ctype;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public List<String> getTime() {
            return time;
        }

        public void setTime(List<String> time) {
            this.time = time;
        }

        public List<String> getUrl() {
            return url;
        }

        public void setUrl(List<String> url) {
            this.url = url;
        }
    }
}
