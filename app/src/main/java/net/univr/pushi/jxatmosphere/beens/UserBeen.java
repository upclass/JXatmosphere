package net.univr.pushi.jxatmosphere.beens;

/**
 * author : Administrator wl
 * e-mail : 389456264@qq.com
 * time   : 2018/04/09
 * desc   :
 * version: 1.0
 */


public class UserBeen {


    private String errmsg;
    private String errcode;


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

    @Override
    public String toString() {
        return "UserBeen{" +
                "errmsg='" + errmsg + '\'' +
                ", errcode='" + errcode + '\'' +
                '}';
    }
}
