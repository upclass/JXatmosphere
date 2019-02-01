package net.univr.pushi.jxatmosphere.beens;

import java.util.List;

/**
 * author : Administrator wl
 * e-mail : 389456264@qq.com
 * time   : 2019/01/19
 * desc   :
 * version: 1.0
 */


public class RefReturn {


    /**
     * errmsg : success
     * picList : ["http://stwx.jxgis.cn/dsljyb/refnow/20181225/20181225071800.F006.png","http://stwx.jxgis.cn/dsljyb/refnow/20181225/20181225071800.F012.png","http://stwx.jxgis.cn/dsljyb/refnow/20181225/20181225071800.F018.png","http://stwx.jxgis.cn/dsljyb/refnow/20181225/20181225071800.F024.png","http://stwx.jxgis.cn/dsljyb/refnow/20181225/20181225071800.F030.png","http://stwx.jxgis.cn/dsljyb/refnow/20181225/20181225071800.F036.png","http://stwx.jxgis.cn/dsljyb/refnow/20181225/20181225071800.F042.png","http://stwx.jxgis.cn/dsljyb/refnow/20181225/20181225071800.F048.png","http://stwx.jxgis.cn/dsljyb/refnow/20181225/20181225071800.F054.png","http://stwx.jxgis.cn/dsljyb/refnow/20181225/20181225071800.F060.png","http://stwx.jxgis.cn/dsljyb/refnow/20181225/20181225071800.F066.png","http://stwx.jxgis.cn/dsljyb/refnow/20181225/20181225071800.F072.png","http://stwx.jxgis.cn/dsljyb/refnow/20181225/20181225071800.F078.png","http://stwx.jxgis.cn/dsljyb/refnow/20181225/20181225071800.F084.png","http://stwx.jxgis.cn/dsljyb/refnow/20181225/20181225071800.F090.png","http://stwx.jxgis.cn/dsljyb/refnow/20181225/20181225071800.F096.png","http://stwx.jxgis.cn/dsljyb/refnow/20181225/20181225071800.F102.png","http://stwx.jxgis.cn/dsljyb/refnow/20181225/20181225071800.F108.png","http://stwx.jxgis.cn/dsljyb/refnow/20181225/20181225071800.F114.png","http://stwx.jxgis.cn/dsljyb/refnow/20181225/20181225071800.F120.png"]
     * errcode : 0
     */

    private String errmsg;
    private int errcode;
    private List<String> picList;

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

    public List<String> getPicList() {
        return picList;
    }

    public void setPicList(List<String> picList) {
        this.picList = picList;
    }
}
