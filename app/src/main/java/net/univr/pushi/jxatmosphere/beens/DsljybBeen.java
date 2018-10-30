package net.univr.pushi.jxatmosphere.beens;

import java.util.List;

/**
 * author : Administrator wl
 * e-mail : 389456264@qq.com
 * time   : 2018/07/02
 * desc   :
 * version: 1.0
 */


public class DsljybBeen {

    /**
     * data : ["0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0"]
     * errmsg : success
     * forecast_time : 2018-07-14 06:00
     * errcode : 0
     */

    private String errmsg;
    private String forecast_time;
    private int errcode;
    private List<String> data;
    private List<String> picList;

    public List<String> getPicList() {
        return picList;
    }

    public void setPicList(List<String> picList) {
        this.picList = picList;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }

    public String getForecast_time() {
        return forecast_time;
    }

    public void setForecast_time(String forecast_time) {
        this.forecast_time = forecast_time;
    }

    public int getErrcode() {
        return errcode;
    }

    public void setErrcode(int errcode) {
        this.errcode = errcode;
    }

    public List<String> getData() {
        return data;
    }

    public void setData(List<String> data) {
        this.data = data;
    }
}
