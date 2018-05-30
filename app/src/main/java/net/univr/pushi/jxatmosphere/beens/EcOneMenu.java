package net.univr.pushi.jxatmosphere.beens;

import java.util.List;

/**
 * author : Administrator wl
 * e-mail : 389456264@qq.com
 * time   : 2018/05/09
 * desc   :
 * version: 1.0
 */


public class EcOneMenu {
    /**
     * data : {"menu":[{"id":1,"name":"rain","paname":"ecmwf_thin","type":"ecmwf_thin","znName":"降水预报"},{"id":2,"name":"10mwind","paname":"ecmwf_thin","type":"ecmwf_thin","znName":"10米风场预报"},{"id":3,"name":"cape","paname":"ecmwf_thin","type":"ecmwf_thin","znName":"对流有效位能和850hPa风场"},{"id":6,"name":"cloud","paname":"ecmwf_thin","type":"ecmwf_thin","znName":"云量预报"},{"id":7,"name":"dslp","paname":"ecmwf_thin","type":"ecmwf_thin","znName":"海平面24小时变压"},{"id":8,"name":"dt","paname":"ecmwf_thin","type":"ecmwf_thin","znName":"24小时变温"},{"id":11,"name":"dt48","paname":"ecmwf_thin","type":"ecmwf_thin","znName":"48小时变温"},{"id":14,"name":"frost","paname":"ecmwf_thin","type":"ecmwf_thin","znName":"地表24小时间隔最低气温"},{"id":15,"name":"h500w","paname":"ecmwf_thin","type":"ecmwf_thin","znName":"位势高度和风场"},{"id":21,"name":"hl","paname":"ecmwf_thin","type":"ecmwf_thin","znName":"500hPa位势高度和海平面气压场"},{"id":22,"name":"kindex","paname":"ecmwf_thin","type":"ecmwf_thin","znName":"K指数和850hPa风场"},{"id":23,"name":"pwat","paname":"ecmwf_thin","type":"ecmwf_thin","znName":"整层水汽含量"},{"id":24,"name":"q","paname":"ecmwf_thin","type":"ecmwf_thin","znName":"比湿场"},{"id":28,"name":"qflux","paname":"ecmwf_thin","type":"ecmwf_thin","znName":"水汽通量"},{"id":40,"name":"rh","paname":"ecmwf_thin","type":"ecmwf_thin","znName":"相对湿度场"},{"id":46,"name":"slp","paname":"ecmwf_thin","type":"ecmwf_thin","znName":"海平面气压场"},{"id":47,"name":"swind","paname":"ecmwf_thin","type":"ecmwf_thin","znName":"流场和风速"},{"id":52,"name":"thse","paname":"ecmwf_thin","type":"ecmwf_thin","znName":"假相当位温差和风场"},{"id":56,"name":"tmp","paname":"ecmwf_thin","type":"ecmwf_thin","znName":"温度预报"},{"id":62,"name":"windchill","paname":"ecmwf_thin","type":"ecmwf_thin","znName":"风寒指数预报"}],"tittleName":"ec细网格数值预报"}
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
        /**
         * menu : [{"id":1,"name":"rain","paname":"ecmwf_thin","type":"ecmwf_thin","znName":"降水预报"},{"id":2,"name":"10mwind","paname":"ecmwf_thin","type":"ecmwf_thin","znName":"10米风场预报"},{"id":3,"name":"cape","paname":"ecmwf_thin","type":"ecmwf_thin","znName":"对流有效位能和850hPa风场"},{"id":6,"name":"cloud","paname":"ecmwf_thin","type":"ecmwf_thin","znName":"云量预报"},{"id":7,"name":"dslp","paname":"ecmwf_thin","type":"ecmwf_thin","znName":"海平面24小时变压"},{"id":8,"name":"dt","paname":"ecmwf_thin","type":"ecmwf_thin","znName":"24小时变温"},{"id":11,"name":"dt48","paname":"ecmwf_thin","type":"ecmwf_thin","znName":"48小时变温"},{"id":14,"name":"frost","paname":"ecmwf_thin","type":"ecmwf_thin","znName":"地表24小时间隔最低气温"},{"id":15,"name":"h500w","paname":"ecmwf_thin","type":"ecmwf_thin","znName":"位势高度和风场"},{"id":21,"name":"hl","paname":"ecmwf_thin","type":"ecmwf_thin","znName":"500hPa位势高度和海平面气压场"},{"id":22,"name":"kindex","paname":"ecmwf_thin","type":"ecmwf_thin","znName":"K指数和850hPa风场"},{"id":23,"name":"pwat","paname":"ecmwf_thin","type":"ecmwf_thin","znName":"整层水汽含量"},{"id":24,"name":"q","paname":"ecmwf_thin","type":"ecmwf_thin","znName":"比湿场"},{"id":28,"name":"qflux","paname":"ecmwf_thin","type":"ecmwf_thin","znName":"水汽通量"},{"id":40,"name":"rh","paname":"ecmwf_thin","type":"ecmwf_thin","znName":"相对湿度场"},{"id":46,"name":"slp","paname":"ecmwf_thin","type":"ecmwf_thin","znName":"海平面气压场"},{"id":47,"name":"swind","paname":"ecmwf_thin","type":"ecmwf_thin","znName":"流场和风速"},{"id":52,"name":"thse","paname":"ecmwf_thin","type":"ecmwf_thin","znName":"假相当位温差和风场"},{"id":56,"name":"tmp","paname":"ecmwf_thin","type":"ecmwf_thin","znName":"温度预报"},{"id":62,"name":"windchill","paname":"ecmwf_thin","type":"ecmwf_thin","znName":"风寒指数预报"}]
         * tittleName : ec细网格数值预报
         */

        private String tittleName;
        private List<MenuBean> menu;

        public String getTittleName() {
            return tittleName;
        }

        public void setTittleName(String tittleName) {
            this.tittleName = tittleName;
        }

        public List<MenuBean> getMenu() {
            return menu;
        }

        public void setMenu(List<MenuBean> menu) {
            this.menu = menu;
        }

        public static class MenuBean {
            /**
             * id : 1
             * name : rain
             * paname : ecmwf_thin
             * type : ecmwf_thin
             * znName : 降水预报
             */

            private int id;
            private String name;
            private String paname;
            private String type;
            private String znName;
            private Boolean select;

            public Boolean getSelect() {
                return select;
            }

            public void setSelect(Boolean select) {
                this.select = select;
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
}
