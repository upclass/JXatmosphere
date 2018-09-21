package net.univr.pushi.jxatmosphere.beens;

import java.util.List;

/**
 * author : Administrator wl
 * e-mail : 389456264@qq.com
 * time   : 2018/09/04
 * desc   :
 * version: 1.0
 */


public class DmcgjcCustomMenu {


    /**
     * data : [{"twoMenu":[{},{"id":4,"name":"rain_sum3","paname":"降水","type":"rain","znName":"3h"},{"id":5,"name":"rain_sum6","paname":"降水","type":"rain","znName":"6h"},{"id":6,"name":"rain_sum12","paname":"降水","type":"rain","znName":"12h"},{"id":7,"name":"rain_sum_sw24","paname":"降水","type":"rain","znName":"sw24"},{"id":1,"name":"rain_sum","paname":"降水","type":"rain","znName":"24h"},{"id":2,"name":"rain_sum_6","paname":"降水","type":"rain","znName":"6min"}],"paname":"降水","type":"rain"},{"twoMenu":[{"id":8,"name":"temp","paname":"气温","type":"temp","znName":"气温"},{"id":9,"name":"temp_avg_20","paname":"气温","type":"temp","znName":"平均气温（20时-20时）"},{"id":10,"name":"temp_max","paname":"气温","type":"temp","znName":"最高气温"},{"id":11,"name":"temp_max_20","paname":"气温","type":"temp","znName":"最高气温（20时-20时）"},{"id":12,"name":"temp_min","paname":"气温","type":"temp","znName":"最低气温"},{"id":13,"name":"temp_min_20","paname":"气温","type":"temp","znName":"最低气温（20时-20时）"},{"id":14,"name":"temp_deta24","paname":"气温","type":"temp","znName":"24小时变温"},{"id":15,"name":"temp_deta1","paname":"气温","type":"temp","znName":"1小时变温"},{"id":16,"name":"body_feeling_temp","paname":"气温","type":"temp","znName":"体感温度"},{"id":18,"name":"surface_temp","paname":"气温","type":"temp","znName":"地面温度"}],"paname":"气温","type":"temp"},{"twoMenu":[{"id":19,"name":"wind_2minute_avg","paname":"风","type":"wind","znName":"二分钟平均风速"},{"id":20,"name":"wind_10minute_avg","paname":"风","type":"wind","znName":"十分钟平均风速"},{"id":21,"name":"wind_great","paname":"风","type":"wind","znName":"一小时内极大风速"},{"id":22,"name":"wind_great_20","paname":"风","type":"wind","znName":"极大风速（20时-20时）"},{"id":23,"name":"wind_max","paname":"风","type":"wind","znName":"一小时内最大风速"},{"id":24,"name":"wind_inst","paname":"风","type":"wind","znName":"瞬时风速"},{"id":25,"name":"wind_inst_max","paname":"风","type":"wind","znName":"极大风速"},{"id":26,"name":"wind_max_5","paname":"风","type":"wind","znName":"最大风速"},{"id":27,"name":"wind_1minute_avg","paname":"风","type":"wind","znName":"一分钟平均风速"}],"paname":"风","type":"wind"},{"twoMenu":[{"id":28,"name":"humidity","paname":"湿度","type":"humidity","znName":"相对湿度"},{"id":29,"name":"humidity_min","paname":"湿度","type":"humidity","znName":"最小相对湿度"}],"paname":"湿度","type":"humidity"},{"twoMenu":[{"id":30,"name":"pressure","paname":"气压","type":"pressure","znName":"本站气压"},{"id":31,"name":"pressure_deta3","paname":"气压","type":"pressure","znName":"3小时变压"},{"id":32,"name":"pressure_deta24","paname":"气压","type":"pressure","znName":"24小时变压"},{"id":33,"name":"pressure_max","paname":"气压","type":"pressure","znName":"1小时内最高气压"},{"id":34,"name":"pressure_min","paname":"气压","type":"pressure","znName":"1小时内最低气压"},{"id":35,"name":"sea_level_pressure","paname":"气压","type":"pressure","znName":"海平面气压"}],"paname":"气压","type":"pressure"}]
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
         * twoMenu : [{},{"id":4,"name":"rain_sum3","paname":"降水","type":"rain","znName":"3h"},{"id":5,"name":"rain_sum6","paname":"降水","type":"rain","znName":"6h"},{"id":6,"name":"rain_sum12","paname":"降水","type":"rain","znName":"12h"},{"id":7,"name":"rain_sum_sw24","paname":"降水","type":"rain","znName":"sw24"},{"id":1,"name":"rain_sum","paname":"降水","type":"rain","znName":"24h"},{"id":2,"name":"rain_sum_6","paname":"降水","type":"rain","znName":"6min"}]
         * paname : 降水
         * type : rain
         */

        private String paname;
        private String type;
        private List<TwoMenuBean> twoMenu;

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

        public List<TwoMenuBean> getTwoMenu() {
            return twoMenu;
        }

        public void setTwoMenu(List<TwoMenuBean> twoMenu) {
            this.twoMenu = twoMenu;
        }

        public static class TwoMenuBean {
            /**
             * id : 4
             * name : rain_sum3
             * paname : 降水
             * type : rain
             * znName : 3h
             */

            private int id;
            private String name;
            private String paname;
            private String type;
            private String znName;

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
}
