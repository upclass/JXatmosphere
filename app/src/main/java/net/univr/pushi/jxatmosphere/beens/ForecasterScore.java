package net.univr.pushi.jxatmosphere.beens;

import java.util.List;

/**
 * author : Administrator wl
 * e-mail : 389456264@qq.com
 * time   : 2018/04/24
 * desc   :
 * version: 1.0
 */


public class ForecasterScore {
    /**
     * data : {"tittleName":"主班","dutygrade":[{"duty":"zb","id":1,"name":"毛连海","score":"80.96","times":"4","yearMon":"201504"},{"duty":"zb","id":3,"name":"吴静","score":"75.87","times":"10","yearMon":"201504"},{"duty":"zb","id":4,"name":"王海","score":"75.05","times":"15","yearMon":"201504"},{"duty":"zb","id":6,"name":"陈云辉","score":"73.31","times":"15","yearMon":"201504"},{"duty":"zb","id":9,"name":"周芳","score":"70.59","times":"16","yearMon":"201504"}]}
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
         * tittleName : 主班
         * dutygrade : [{"duty":"zb","id":1,"name":"毛连海","score":"80.96","times":"4","yearMon":"201504"},{"duty":"zb","id":3,"name":"吴静","score":"75.87","times":"10","yearMon":"201504"},{"duty":"zb","id":4,"name":"王海","score":"75.05","times":"15","yearMon":"201504"},{"duty":"zb","id":6,"name":"陈云辉","score":"73.31","times":"15","yearMon":"201504"},{"duty":"zb","id":9,"name":"周芳","score":"70.59","times":"16","yearMon":"201504"}]
         */

        private String tittleName;
        private List<DutygradeBean> dutygrade;

        public String getTittleName() {
            return tittleName;
        }

        public void setTittleName(String tittleName) {
            this.tittleName = tittleName;
        }

        public List<DutygradeBean> getDutygrade() {
            return dutygrade;
        }

        public void setDutygrade(List<DutygradeBean> dutygrade) {
            this.dutygrade = dutygrade;
        }

        public static class DutygradeBean {
            /**
             * duty : zb
             * id : 1
             * name : 毛连海
             * score : 80.96
             * times : 4
             * yearMon : 201504
             */

            private String duty;
            private int id;
            private String name;
            private String score;
            private String times;
            private String yearMon;

            public String getDuty() {
                return duty;
            }

            public void setDuty(String duty) {
                this.duty = duty;
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

            public String getScore() {
                return score;
            }

            public void setScore(String score) {
                this.score = score;
            }

            public String getTimes() {
                return times;
            }

            public void setTimes(String times) {
                this.times = times;
            }

            public String getYearMon() {
                return yearMon;
            }

            public void setYearMon(String yearMon) {
                this.yearMon = yearMon;
            }
        }
    }
}
