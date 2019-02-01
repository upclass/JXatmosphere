package net.univr.pushi.jxatmosphere.beens;

import java.util.List;

/**
 * author : Administrator wl
 * e-mail : 389456264@qq.com
 * time   : 2019/01/14
 * desc   :
 * version: 1.0
 */


public class LocalDutyBeen {
    private LocalDutyBeen.DataBean data;
    private String errmsg;
    private String errcode;

    public LocalDutyBeen.DataBean getData() {
        return data;
    }

    public void setData(LocalDutyBeen.DataBean data) {
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
         * tittleName : 当天排班表
         * dutys : [{"date":"2018-04-29","duty":"带班领导","id":11956,"name":"殷剑敏","property":"白班"},{"date":"2018-04-29","duty":"预报首席","id":12006,"name":"许爱华","property":"白班"},{"date":"2018-04-29","duty":"短时晚班","id":12300,"name":"李婕","property":"晚班"},{"date":"2018-04-29","duty":"短期主班","id":12327,"name":"毛连海","property":"全天"},{"date":"2018-04-29","duty":"中期预报","id":12338,"name":"陈娟","property":"白班"},{"date":"2018-04-29","duty":"环境预报","id":12477,"name":"徐星生","property":"白班"},{"date":"2018-04-29","duty":"短时晚班","id":12519,"name":"包慧濛","property":"晚班"},{"date":"2018-04-29","duty":"决策服务","id":12564,"name":"盛志军","property":"白班"},{"date":"2018-04-29","duty":"短期副班","id":12391,"name":"张娟娟","property":"全天"},{"date":"2018-04-30","duty":"环境预报","id":12448,"name":"刘波","property":"白班"},{"date":"2018-04-30","duty":"短时白班","id":12593,"name":"肖潇","property":"白班"},{"date":"2018-04-30","duty":"中期预报","id":12609,"name":"周芳","property":"白班"},{"date":"2018-04-30","duty":"决策服务","id":12565,"name":"盛志军","property":"白班"},{"date":"2018-04-30","duty":"短期副班","id":12376,"name":"付彧","property":"全天"},{"date":"2018-04-30","duty":"短期主班","id":12309,"name":"陈娟","property":"全天"},{"date":"2018-04-30","duty":"预报首席","id":12007,"name":"许爱华","property":"白班"},{"date":"2018-04-30","duty":"带班领导","id":11957,"name":"殷剑敏","property":"白班"},{"date":"2018-05-01","duty":"决策服务","id":12566,"name":"盛志军","property":"白班"},{"date":"2018-05-01","duty":"中期预报","id":12641,"name":"金米娜","property":"白班"},{"date":"2018-05-01","duty":"短期副班","id":12669,"name":"夏侯杰","property":"全天"},{"date":"2018-05-01","duty":"短期主班","id":12700,"name":"周芳","property":"全天"},{"date":"2018-05-01","duty":"预报首席","id":12778,"name":"许爱华","property":"白班"},{"date":"2018-05-01","duty":"带班领导","id":12855,"name":"殷剑敏","property":"白班"},{"date":"2018-05-01","duty":"环境预报","id":12914,"name":"刘波","property":"白班"},{"date":"2018-05-02","duty":"环境预报","id":12915,"name":"刘波","property":"白班"},{"date":"2018-05-02","duty":"带班领导","id":12899,"name":"许彬","property":"白班"},{"date":"2018-05-02","duty":"短期副班","id":12677,"name":"王海","property":"全天"},{"date":"2018-05-02","duty":"预报首席","id":12793,"name":"许彬","property":"白班"},{"date":"2018-05-02","duty":"中期预报","id":12649,"name":"陈娟","property":"白班"},{"date":"2018-05-02","duty":"短期主班","id":12610,"name":"金米娜","property":"全天"},{"date":"2018-05-02","duty":"决策服务","id":12534,"name":"朱星球","property":"白班"},{"date":"2018-05-03","duty":"决策服务","id":12535,"name":"朱星球","property":"白班"},{"date":"2018-05-03","duty":"短期主班","id":12618,"name":"陈娟","property":"全天"},{"date":"2018-05-03","duty":"中期预报","id":12650,"name":"肖安","property":"白班"},{"date":"2018-05-03","duty":"预报首席","id":12794,"name":"许彬","property":"白班"},{"date":"2018-05-03","duty":"短期副班","id":12685,"name":"刘春","property":"全天"},{"date":"2018-05-03","duty":"带班领导","id":12900,"name":"许彬","property":"白班"},{"date":"2018-05-03","duty":"环境预报","id":12916,"name":"刘波","property":"白班"},{"date":"2018-05-04","duty":"环境预报","id":12917,"name":"刘波","property":"白班"},{"date":"2018-05-04","duty":"带班领导","id":12901,"name":"许彬","property":"白班"},{"date":"2018-05-04","duty":"短期副班","id":12693,"name":"付彧","property":"全天"},{"date":"2018-05-04","duty":"预报首席","id":12795,"name":"许彬","property":"白班"},{"date":"2018-05-04","duty":"中期预报","id":12652,"name":"张娟娟","property":"白班"},{"date":"2018-05-04","duty":"短期主班","id":12619,"name":"肖安","property":"全天"},{"date":"2018-05-04","duty":"决策服务","id":12536,"name":"朱星球","property":"白班"},{"date":"2018-05-05","duty":"决策服务","id":12567,"name":"盛志军","property":"白班"},{"date":"2018-05-05","duty":"短期主班","id":12621,"name":"张娟娟","property":"全天"},{"date":"2018-05-05","duty":"中期预报","id":12642,"name":"金米娜","property":"白班"},{"date":"2018-05-05","duty":"短期副班","id":12670,"name":"夏侯杰","property":"全天"},{"date":"2018-05-05","duty":"预报首席","id":12796,"name":"许彬","property":"白班"},{"date":"2018-05-05","duty":"带班领导","id":12902,"name":"许彬","property":"白班"},{"date":"2018-05-05","duty":"环境预报","id":12918,"name":"刘波","property":"白班"}]
         */

        private String tittleName;
        private List<LocalDutyBeen.DataBean.DutysBean> dutys;

        public String getTittleName() {
            return tittleName;
        }

        public void setTittleName(String tittleName) {
            this.tittleName = tittleName;
        }

        public List<LocalDutyBeen.DataBean.DutysBean> getDutys() {
            return dutys;
        }

        public void setDutys(List<LocalDutyBeen.DataBean.DutysBean> dutys) {
            this.dutys = dutys;
        }

        public static class DutysBean {
            /**
             * date : 2018-04-29
             * duty : 带班领导
             * id : 11956
             * name : 殷剑敏
             * property : 白班
             */

            private String date;
            private String duty;
            private int id;
            private String name;
            Boolean isDiver;
            private String property;
            private String telePhone;
            private String phone;

            public String getDate() {
                return date;
            }

            public void setDate(String date) {
                this.date = date;
            }

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

            public String getProperty() {
                return property;
            }

            public void setProperty(String property) {
                this.property = property;
            }

            public Boolean getDiver() {
                return isDiver;
            }

            public void setDiver(Boolean diver) {
                isDiver = diver;
            }

            public String getTelePhone() {
                return telePhone;
            }

            public void setTelePhone(String telePhone) {
                this.telePhone = telePhone;
            }

            public String getPhone() {
                return phone;
            }

            public void setPhone(String phone) {
                this.phone = phone;
            }
        }
    }
}


