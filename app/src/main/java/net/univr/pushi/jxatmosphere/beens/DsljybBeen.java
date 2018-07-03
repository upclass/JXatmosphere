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
     * data : ["0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","1","1"]
     * errmsg : success
     * errcode : 0
     */

    private String errmsg;
    private int errcode;
    private List<String> data;

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
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
