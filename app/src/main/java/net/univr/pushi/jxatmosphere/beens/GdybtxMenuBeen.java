package net.univr.pushi.jxatmosphere.beens;

import java.util.List;

/**
 * author : Administrator wl
 * e-mail : 389456264@qq.com
 * time   : 2018/05/23
 * desc   :
 * version: 1.0
 */


public class GdybtxMenuBeen {
    /**
     * data : {"menu":["天气现象","气温","降水量","风向风速","相对湿度"]}
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
        private List<String> menu;

        public List<String> getMenu() {
            return menu;
        }

        public void setMenu(List<String> menu) {
            this.menu = menu;
        }
    }
}