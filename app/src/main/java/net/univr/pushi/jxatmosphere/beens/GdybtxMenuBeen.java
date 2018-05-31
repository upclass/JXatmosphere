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
     * data : {"menu":[{"name":"降水量","type":"rain"},{"name":"最高温","type":"tmax24"},{"name":"最低温","type":"tmin24"},{"name":"天气现象","type":"wp"}]}
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
        private List<MenuBean> menu;

        public List<MenuBean> getMenu() {
            return menu;
        }

        public void setMenu(List<MenuBean> menu) {
            this.menu = menu;
        }

        public static class MenuBean {
            /**
             * name : 降水量
             * type : rain
             */

            private String name;
            private String type;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }
        }
    }
}