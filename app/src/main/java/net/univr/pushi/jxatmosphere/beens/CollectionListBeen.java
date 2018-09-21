package net.univr.pushi.jxatmosphere.beens;

import java.util.List;

/**
 * author : Administrator wl
 * e-mail : 389456264@qq.com
 * time   : 2018/09/15
 * desc   :
 * version: 1.0
 */


public class CollectionListBeen {

    /**
     * data : [{"collectId":9,"ctype":null,"time":"2018-09-14T18:09:33","type":null,"url":"http://stwx.jxgis.cn/sk/radar/20180914/201809141624.png","userId":"13247099085"},{"collectId":10,"ctype":null,"time":"2018-09-14T18:11:14","type":null,"url":"http://stwx.jxgis.cn/sk/radar/20180914/201809141636.png","userId":"13247099085"},{"collectId":11,"ctype":null,"time":"2018-09-14T18:11:22","type":null,"url":"http://stwx.jxgis.cn/sk/radar/20180914/201809141642.png","userId":"13247099085"}]
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
         * collectId : 9
         * ctype : null
         * time : 2018-09-14T18:09:33
         * type : null
         * url : http://stwx.jxgis.cn/sk/radar/20180914/201809141624.png
         * userId : 13247099085
         */

        private int collectId;
        private String ctype;
        private String time;
        private String type;
        private String url;
        private String userId;

        public int getCollectId() {
            return collectId;
        }

        public void setCollectId(int collectId) {
            this.collectId = collectId;
        }

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

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }


        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }
    }
}
