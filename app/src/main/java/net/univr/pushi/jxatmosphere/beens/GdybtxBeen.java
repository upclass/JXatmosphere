package net.univr.pushi.jxatmosphere.beens;

import java.util.List;

/**
 * author : Administrator wl
 * e-mail : 389456264@qq.com
 * time   : 2018/05/14
 * desc   :
 * version: 1.0
 */


public class GdybtxBeen {
    /**
     * data : {"timeList":["012","024","036","048","060","072","084","096","108","120","132","144","156","168","180","192","204","216","228","240"],"picList":["http://10.116.36.74/Product/images/Product/Guidance/rain12/999/2018/05/14/早晨/18051408.012.png","http://10.116.36.74/Product/images/Product/Guidance/rain12/999/2018/05/14/早晨/18051408.024.png","http://10.116.36.74/Product/images/Product/Guidance/rain12/999/2018/05/14/早晨/18051408.036.png","http://10.116.36.74/Product/images/Product/Guidance/rain12/999/2018/05/14/早晨/18051408.048.png","http://10.116.36.74/Product/images/Product/Guidance/rain12/999/2018/05/14/早晨/18051408.060.png","http://10.116.36.74/Product/images/Product/Guidance/rain12/999/2018/05/14/早晨/18051408.072.png","http://10.116.36.74/Product/images/Product/Guidance/rain12/999/2018/05/14/早晨/18051408.084.png","http://10.116.36.74/Product/images/Product/Guidance/rain12/999/2018/05/14/早晨/18051408.096.png","http://10.116.36.74/Product/images/Product/Guidance/rain12/999/2018/05/14/早晨/18051408.108.png","http://10.116.36.74/Product/images/Product/Guidance/rain12/999/2018/05/14/早晨/18051408.120.png","http://10.116.36.74/Product/images/Product/Guidance/rain12/999/2018/05/14/早晨/18051408.132.png","http://10.116.36.74/Product/images/Product/Guidance/rain12/999/2018/05/14/早晨/18051408.144.png","http://10.116.36.74/Product/images/Product/Guidance/rain12/999/2018/05/14/早晨/18051408.156.png","http://10.116.36.74/Product/images/Product/Guidance/rain12/999/2018/05/14/早晨/18051408.168.png","http://10.116.36.74/Product/images/Product/Guidance/rain12/999/2018/05/14/早晨/18051408.180.png","http://10.116.36.74/Product/images/Product/Guidance/rain12/999/2018/05/14/早晨/18051408.192.png","http://10.116.36.74/Product/images/Product/Guidance/rain12/999/2018/05/14/早晨/18051408.204.png","http://10.116.36.74/Product/images/Product/Guidance/rain12/999/2018/05/14/早晨/18051408.216.png","http://10.116.36.74/Product/images/Product/Guidance/rain12/999/2018/05/14/早晨/18051408.228.png","http://10.116.36.74/Product/images/Product/Guidance/rain12/999/2018/05/14/早晨/18051408.240.png"]}
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
        private List<String> timeList;
        private List<String> picList;

        public List<String> getTimeList() {
            return timeList;
        }

        public void setTimeList(List<String> timeList) {
            this.timeList = timeList;
        }

        public List<String> getPicList() {
            return picList;
        }

        public void setPicList(List<String> picList) {
            this.picList = picList;
        }
    }
}
