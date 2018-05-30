package net.univr.pushi.jxatmosphere.beens;

/**
 * author : Administrator wl
 * e-mail : 389456264@qq.com
 * time   : 2018/05/02
 * desc   :
 * version: 1.0
 */


public class DecisionBeen {

    /**
     * data : {"strEncode":"utf-8","urlPath":"qkfy","url":"http://10.116.36.39/yb/agriec/qkfy_R.htm"}
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
         * strEncode : utf-8
         * urlPath : qkfy
         * url : http://10.116.36.39/yb/agriec/qkfy_R.htm
         */

        private String strEncode;
        private String urlPath;
        private String url;

        public String getStrEncode() {
            return strEncode;
        }

        public void setStrEncode(String strEncode) {
            this.strEncode = strEncode;
        }

        public String getUrlPath() {
            return urlPath;
        }

        public void setUrlPath(String urlPath) {
            this.urlPath = urlPath;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
