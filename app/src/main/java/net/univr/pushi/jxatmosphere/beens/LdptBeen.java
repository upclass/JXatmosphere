package net.univr.pushi.jxatmosphere.beens;

import java.util.List;

/**
 * author : Administrator wl
 * e-mail : 389456264@qq.com
 * time   : 2018/05/09
 * desc   :
 * version: 1.0
 */


public class LdptBeen {
    /**
     * data : {"btnName":["15:00","15:06","15:12","15:18","15:24","15:30","15:36","15:42","15:48","15:54","16:00","16:06"],"imageUrl":["http://stwx.jxgis.cn/sk/radar/20180509/201805091500.png","http://stwx.jxgis.cn/sk/radar/20180509/201805091506.png","http://stwx.jxgis.cn/sk/radar/20180509/201805091512.png","http://stwx.jxgis.cn/sk/radar/20180509/201805091518.png","http://stwx.jxgis.cn/sk/radar/20180509/201805091524.png","http://stwx.jxgis.cn/sk/radar/20180509/201805091530.png","http://stwx.jxgis.cn/sk/radar/20180509/201805091536.png","http://stwx.jxgis.cn/sk/radar/20180509/201805091542.png","http://stwx.jxgis.cn/sk/radar/20180509/201805091548.png","http://stwx.jxgis.cn/sk/radar/20180509/201805091554.png","http://stwx.jxgis.cn/sk/radar/20180509/201805091600.png","http://stwx.jxgis.cn/sk/radar/20180509/201805091606.png"]}
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
        private List<String> btnName;
        private List<String> imageUrl;

        public List<String> getBtnName() {
            return btnName;
        }

        public void setBtnName(List<String> btnName) {
            this.btnName = btnName;
        }

        public List<String> getImageUrl() {
            return imageUrl;
        }

        public void setImageUrl(List<String> imageUrl) {
            this.imageUrl = imageUrl;
        }
    }
}
