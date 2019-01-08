package net.univr.pushi.jxatmosphere.beens;

import java.util.List;

/**
 * author : Administrator wl
 * e-mail : 389456264@qq.com
 * time   : 2019/01/07
 * desc   :
 * version: 1.0
 */


public class GdyutxTagBeen {

    /**
     * data : ["上午","早晨"]
     * errmsg : success
     * errcode : 0
     */

    private String errmsg;
    private String errcode;
    private List<String> data;

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

    public List<String> getData() {
        return data;
    }

    public void setData(List<String> data) {
        this.data = data;
    }
}
