package net.univr.pushi.jxatmosphere.beens;

import java.util.List;

/**
 * author : Administrator wl
 * e-mail : 389456264@qq.com
 * time   : 2018/05/16
 * desc   :
 * version: 1.0
 */


public class BdybBeen {
    /**
     * data : {"adress":"江西省南昌市青山湖区艾溪湖管理处艾溪村东南方向","ybList":[{"foretime":15,"id":1008195067,"lat":"28.625 ","lon":"116.0 ","rain":"0.0 ","rh":"59 ","temp":"31.7 ","tqxx":"晴","winddirect":"西南","windspeed":3.1,"x":24,"y":37}],"fbTime":"2018-05-15 20:00:00"}
     * errorcode : 0
     * errmsg : success
     */

    private DataBean data;
    private String errorcode;
    private String errmsg;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getErrorcode() {
        return errorcode;
    }

    public void setErrorcode(String errorcode) {
        this.errorcode = errorcode;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }

    public static class DataBean {
        /**
         * adress : 江西省南昌市青山湖区艾溪湖管理处艾溪村东南方向
         * ybList : [{"foretime":15,"id":1008195067,"lat":"28.625 ","lon":"116.0 ","rain":"0.0 ","rh":"59 ","temp":"31.7 ","tqxx":"晴","winddirect":"西南","windspeed":3.1,"x":24,"y":37}]
         * fbTime : 2018-05-15 20:00:00
         */

        private String adress;
        private String fbTime;
        private List<YbListBean> ybList;

        public String getAdress() {
            return adress;
        }

        public void setAdress(String adress) {
            this.adress = adress;
        }

        public String getFbTime() {
            return fbTime;
        }

        public void setFbTime(String fbTime) {
            this.fbTime = fbTime;
        }

        public List<YbListBean> getYbList() {
            return ybList;
        }

        public void setYbList(List<YbListBean> ybList) {
            this.ybList = ybList;
        }

        public static class YbListBean {
            /**
             * foretime : 15
             * id : 1008195067
             * lat : 28.625
             * lon : 116.0
             * rain : 0.0
             * rh : 59
             * temp : 31.7
             * tqxx : 晴
             * winddirect : 西南
             * windspeed : 3.1
             * x : 24
             * y : 37
             */

            private int foretime;
            private int id;
            private String lat;
            private String lon;
            private String rain;
            private String rh;
            private String temp;
            private String tqxx;
            private String winddirect;
            private double windspeed;
            private int x;
            private int y;

            public int getForetime() {
                return foretime;
            }

            public void setForetime(int foretime) {
                this.foretime = foretime;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getLat() {
                return lat;
            }

            public void setLat(String lat) {
                this.lat = lat;
            }

            public String getLon() {
                return lon;
            }

            public void setLon(String lon) {
                this.lon = lon;
            }

            public String getRain() {
                return rain;
            }

            public void setRain(String rain) {
                this.rain = rain;
            }

            public String getRh() {
                return rh;
            }

            public void setRh(String rh) {
                this.rh = rh;
            }

            public String getTemp() {
                return temp;
            }

            public void setTemp(String temp) {
                this.temp = temp;
            }

            public String getTqxx() {
                return tqxx;
            }

            public void setTqxx(String tqxx) {
                this.tqxx = tqxx;
            }

            public String getWinddirect() {
                return winddirect;
            }

            public void setWinddirect(String winddirect) {
                this.winddirect = winddirect;
            }

            public double getWindspeed() {
                return windspeed;
            }

            public void setWindspeed(double windspeed) {
                this.windspeed = windspeed;
            }

            public int getX() {
                return x;
            }

            public void setX(int x) {
                this.x = x;
            }

            public int getY() {
                return y;
            }

            public void setY(int y) {
                this.y = y;
            }
        }
    }
}
