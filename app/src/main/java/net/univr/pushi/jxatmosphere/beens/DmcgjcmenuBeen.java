package net.univr.pushi.jxatmosphere.beens;

import java.util.List;

/**
 * author : Administrator wl
 * e-mail : 389456264@qq.com
 * time   : 2018/05/05
 * desc   :
 * version: 1.0
 */


public class DmcgjcmenuBeen {
    /**
     * data : [{"id":186,"name":"rain_sum","paname":"rain","type":"rain","znName":"累计降水"},{"id":187,"name":"rain_sum_6","paname":"rain","type":"rain","znName":"6分钟累计降水"}]
     * errmsg : success
     * errcode : 0
     */

    private String errmsg;
    private String errcode;
    private List<DataBean> data;

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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 186
         * name : rain_sum
         * paname : rain
         * type : rain
         * znName : 累计降水
         */

        private int id;
        private String name;
        private String paname;
        private String type;
        private String znName;
        private boolean isSelect;

        public void setSelect(boolean select) {
            isSelect = select;
        }



        public boolean isSelect() {
            return isSelect;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPaname() {
            return paname;
        }

        public void setPaname(String paname) {
            this.paname = paname;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getZnName() {
            return znName;
        }

        public void setZnName(String znName) {
            this.znName = znName;
        }
    }
}
