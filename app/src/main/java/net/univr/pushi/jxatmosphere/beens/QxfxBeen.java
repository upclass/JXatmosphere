package net.univr.pushi.jxatmosphere.beens;

import java.util.List;

/**
 * author : Administrator wl
 * e-mail : 389456264@qq.com
 * time   : 2018/05/10
 * desc   :
 * version: 1.0
 */


public class QxfxBeen {


    /**
     * data : {"time":["201805301800","201805260900","201805260800","201805182200","201805071600","201805071500","201804231000","201804230500","201804230400","201804230300","201804221800","201804150100"],"tittleName":"中小河流洪水气象风险预报","url":["http://stwx.jxgis.cn/JXST_APP_V1.0/source/image/hl201805301800.png","http://stwx.jxgis.cn/JXST_APP_V1.0/source/image/hl201805260900.png","http://stwx.jxgis.cn/JXST_APP_V1.0/source/image/hl201805260800.png","http://stwx.jxgis.cn/JXST_APP_V1.0/source/image/hl201805182200.png","http://stwx.jxgis.cn/JXST_APP_V1.0/source/image/hl201805071600.png","http://stwx.jxgis.cn/JXST_APP_V1.0/source/image/hl201805071500.png","http://stwx.jxgis.cn/JXST_APP_V1.0/source/image/hl201804231000.png","http://stwx.jxgis.cn/JXST_APP_V1.0/source/image/hl201804230500.png","http://stwx.jxgis.cn/JXST_APP_V1.0/source/image/hl201804230400.png","http://stwx.jxgis.cn/JXST_APP_V1.0/source/image/hl201804230300.png","http://stwx.jxgis.cn/JXST_APP_V1.0/source/image/hl201804221800.png","http://stwx.jxgis.cn/JXST_APP_V1.0/source/image/hl201804150100.png"]}
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
        /**
         * time : ["201805301800","201805260900","201805260800","201805182200","201805071600","201805071500","201804231000","201804230500","201804230400","201804230300","201804221800","201804150100"]
         * tittleName : 中小河流洪水气象风险预报
         * url : ["http://stwx.jxgis.cn/JXST_APP_V1.0/source/image/hl201805301800.png","http://stwx.jxgis.cn/JXST_APP_V1.0/source/image/hl201805260900.png","http://stwx.jxgis.cn/JXST_APP_V1.0/source/image/hl201805260800.png","http://stwx.jxgis.cn/JXST_APP_V1.0/source/image/hl201805182200.png","http://stwx.jxgis.cn/JXST_APP_V1.0/source/image/hl201805071600.png","http://stwx.jxgis.cn/JXST_APP_V1.0/source/image/hl201805071500.png","http://stwx.jxgis.cn/JXST_APP_V1.0/source/image/hl201804231000.png","http://stwx.jxgis.cn/JXST_APP_V1.0/source/image/hl201804230500.png","http://stwx.jxgis.cn/JXST_APP_V1.0/source/image/hl201804230400.png","http://stwx.jxgis.cn/JXST_APP_V1.0/source/image/hl201804230300.png","http://stwx.jxgis.cn/JXST_APP_V1.0/source/image/hl201804221800.png","http://stwx.jxgis.cn/JXST_APP_V1.0/source/image/hl201804150100.png"]
         */

        private String tittleName;
        private List<String> time;
        private List<String> url;

        public String getTittleName() {
            return tittleName;
        }

        public void setTittleName(String tittleName) {
            this.tittleName = tittleName;
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
