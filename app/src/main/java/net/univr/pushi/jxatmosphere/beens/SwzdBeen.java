package net.univr.pushi.jxatmosphere.beens;

/**
 * author : Administrator wl
 * e-mail : 389456264@qq.com
 * time   : 2018/05/05
 * desc   :
 * version: 1.0
 */


public class SwzdBeen {
    /**
     * data : {"textStr":"【江西省气象台】5月4日14时至5日14时，全省共有2县（市、区）的2个测站（3%）出现暴雨，2县（市、区）的7个测站（5%）出现大雨，以吉安吉州区长塘51.5毫米为最大，上饶横峰县下阳村51.2毫米次之。全省平均雨量2.8毫米，设区市平均雨量景德镇市5.8毫米最大，九江市4.7毫米次之，县（市、区）平均雨量石城县10.5毫米最大，宁都县9.3毫米次之。","url":"http://stwx.jxgis.cn/JXST_WeiXin_V3.1/source/image/20180504060000-20180505060000.png"}
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
         * textStr : 【江西省气象台】5月4日14时至5日14时，全省共有2县（市、区）的2个测站（3%）出现暴雨，2县（市、区）的7个测站（5%）出现大雨，以吉安吉州区长塘51.5毫米为最大，上饶横峰县下阳村51.2毫米次之。全省平均雨量2.8毫米，设区市平均雨量景德镇市5.8毫米最大，九江市4.7毫米次之，县（市、区）平均雨量石城县10.5毫米最大，宁都县9.3毫米次之。
         * url : http://stwx.jxgis.cn/JXST_WeiXin_V3.1/source/image/20180504060000-20180505060000.png
         */

        private String textStr;
        private String url;

        public String getTextStr() {
            return textStr;
        }

        public void setTextStr(String textStr) {
            this.textStr = textStr;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
