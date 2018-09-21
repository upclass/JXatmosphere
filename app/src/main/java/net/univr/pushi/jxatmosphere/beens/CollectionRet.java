package net.univr.pushi.jxatmosphere.beens;

/**
 * author : Administrator wl
 * e-mail : 389456264@qq.com
 * time   : 2018/09/14
 * desc   :
 * version: 1.0
 */


public class CollectionRet {

    /**
     * data : 新增成功
     * errmsg : success
     * errcode : 0
     */

    private String data;
    private String errmsg;
    private String errcode;

    public String getData() {
        return data;
    }

    public void setData(String data) {
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
}
