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
     * data : [{"tittleName":"中小河流洪水气象风险预报","url":"http://stwx.jxgis.cn/JXST_WeiXin_V3.1/source/image/hl201805071600.png"}]
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
         * tittleName : 中小河流洪水气象风险预报
         * url : http://stwx.jxgis.cn/JXST_WeiXin_V3.1/source/image/hl201805071600.png
         */

        private String tittleName;
        private String url;

        public String getTittleName() {
            return tittleName;
        }

        public void setTittleName(String tittleName) {
            this.tittleName = tittleName;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
