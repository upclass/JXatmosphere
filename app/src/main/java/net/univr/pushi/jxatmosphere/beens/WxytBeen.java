package net.univr.pushi.jxatmosphere.beens;

import java.util.List;

/**
 * author : Administrator wl
 * e-mail : 389456264@qq.com
 * time   : 2018/05/09
 * desc   :
 * version: 1.0
 */


public class WxytBeen {
    /**
     * data : {"imageUrl":["http://10.116.36.74/sk/IR_low/1805090300.png","http://10.116.36.74/sk/IR_low/1805090400.png","http://10.116.36.74/sk/IR_low/1805090500.png","http://10.116.36.74/sk/IR_low/1805090600.png","http://10.116.36.74/sk/IR_low/1805090700.png","http://10.116.36.74/sk/IR_low/1805090800.png","http://10.116.36.74/sk/IR_low/1805090900.png","http://10.116.36.74/sk/IR_low/1805091000.png","http://10.116.36.74/sk/IR_low/1805091100.png","http://10.116.36.74/sk/IR_low/1805091200.png","http://10.116.36.74/sk/IR_low/1805091300.png","http://10.116.36.74/sk/IR_low/1805091400.png"],"timeNameList":["2018年05月09日03时00分FY2G红外线图像","2018年05月09日04时00分FY2G红外线图像","2018年05月09日05时00分FY2G红外线图像","2018年05月09日06时00分FY2G红外线图像","2018年05月09日07时00分FY2G红外线图像","2018年05月09日08时00分FY2G红外线图像","2018年05月09日09时00分FY2G红外线图像","2018年05月09日10时00分FY2G红外线图像","2018年05月09日11时00分FY2G红外线图像","2018年05月09日12时00分FY2G红外线图像","2018年05月09日13时00分FY2G红外线图像","2018年05月09日14时00分FY2G红外线图像"],"btnNameList":["03:00","04:00","05:00","06:00","07:00","08:00","09:00","10:00","11:00","12:00","13:00","14:00"]}
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
        private List<String> imageUrl;
        private List<String> timeNameList;
        private List<String> btnNameList;

        public List<String> getImageUrl() {
            return imageUrl;
        }

        public void setImageUrl(List<String> imageUrl) {
            this.imageUrl = imageUrl;
        }

        public List<String> getTimeNameList() {
            return timeNameList;
        }

        public void setTimeNameList(List<String> timeNameList) {
            this.timeNameList = timeNameList;
        }

        public List<String> getBtnNameList() {
            return btnNameList;
        }

        public void setBtnNameList(List<String> btnNameList) {
            this.btnNameList = btnNameList;
        }
    }
}
