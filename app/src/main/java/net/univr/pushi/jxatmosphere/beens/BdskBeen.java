package net.univr.pushi.jxatmosphere.beens;

import java.util.List;

/**
 * author : Administrator wl
 * e-mail : 389456264@qq.com
 * time   : 2018/05/15
 * desc   :
 * version: 1.0
 */


public class BdskBeen {

    /**
     * data : [{"Station_ID_C":"J0007","TEM":"22.2","WIN_D_INST":"0","PRE":"999998","RHU":"999999","WIN_S_INST":"0"},{"Station_ID_C":"J0021","TEM":"22.1","WIN_D_INST":"133","PRE":"999998","RHU":"999999","WIN_S_INST":"1.2"},{"Station_ID_C":"J0024","TEM":"22.6","WIN_D_INST":"9","PRE":"999998","RHU":"999999","WIN_S_INST":"1.4"},{"Station_ID_C":"J0025","TEM":"22.7","WIN_D_INST":"83","PRE":"999998","RHU":"999999","WIN_S_INST":"1.8"},{"Station_ID_C":"J0026","TEM":"22.2","WIN_D_INST":"294","PRE":"999998","RHU":"999999","WIN_S_INST":"7.9"},{"Station_ID_C":"J0035","TEM":"22.1","WIN_D_INST":"72","PRE":"999998","RHU":"999999","WIN_S_INST":"4.2"},{"Station_ID_C":"J0041","TEM":"22.4","WIN_D_INST":"335","PRE":"999998","RHU":"65","WIN_S_INST":"4.5"},{"Station_ID_C":"J0044","TEM":"22.4","WIN_D_INST":"30","PRE":"999998","RHU":"66","WIN_S_INST":"5.6"},{"Station_ID_C":"J0047","TEM":"22.5","WIN_D_INST":"332","PRE":"999998","RHU":"999999","WIN_S_INST":"8.3"},{"Station_ID_C":"J0110","TEM":"22","WIN_D_INST":"261","PRE":"999998","RHU":"999999","WIN_S_INST":"5.3"},{"Station_ID_C":"J0112","TEM":"21.9","WIN_D_INST":"319","PRE":"999998","RHU":"999999","WIN_S_INST":"4.7"}]
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
         * Station_ID_C : J0007
         * TEM : 22.2
         * WIN_D_INST : 0
         * PRE : 999998
         * RHU : 999999
         * WIN_S_INST : 0
         */

        private String Station_ID_C;
        private String TEM;
        private String WIN_D_INST;
        private String PRE;
        private String RHU;
        private String WIN_S_INST;

        public String getStation_ID_C() {
            return Station_ID_C;
        }

        public void setStation_ID_C(String Station_ID_C) {
            this.Station_ID_C = Station_ID_C;
        }

        public String getTEM() {
            return TEM;
        }

        public void setTEM(String TEM) {
            this.TEM = TEM;
        }

        public String getWIN_D_INST() {
            return WIN_D_INST;
        }

        public void setWIN_D_INST(String WIN_D_INST) {
            this.WIN_D_INST = WIN_D_INST;
        }

        public String getPRE() {
            return PRE;
        }

        public void setPRE(String PRE) {
            this.PRE = PRE;
        }

        public String getRHU() {
            return RHU;
        }

        public void setRHU(String RHU) {
            this.RHU = RHU;
        }

        public String getWIN_S_INST() {
            return WIN_S_INST;
        }

        public void setWIN_S_INST(String WIN_S_INST) {
            this.WIN_S_INST = WIN_S_INST;
        }
    }
}
