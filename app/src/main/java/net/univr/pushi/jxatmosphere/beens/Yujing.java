package net.univr.pushi.jxatmosphere.beens;

import java.util.List;

/**
 * <pre>
 *     author : Administrator zxm
 *     e-mail : 1198190018@qq.com
 *     time   : 2018/04/04
 *     desc   :
 *     version: 1.0
 * </pre>
 */

public class Yujing {

    /**
     * data : [{"alarmType":"06","cexiao":"2007-06-04T17:35:11","citySelect":"寻乌县","clickFlag":"172.20.112.33",
     * "danwei":"寻乌","fabu":"2007-06-04T17:30:00","fanwei":null,"id":111,"jielun":null,"noticeIp":"172.20.112.33",
     * "provinceSelect":"赣州","qianfaren":"林山源","shangji":"1","subclass":"暴雨黄色预警信号","ttime":"2007-06-04T17:25:46",
     * "yonghu":"ldgxw","yubaoyuan":"曾令辉","ztname":"暴雨"}]
     * errmsg : ok
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
         * alarmType : 06
         * cexiao : 2007-06-04T17:35:11
         * citySelect : 寻乌县
         * clickFlag : 172.20.112.33
         * danwei : 寻乌
         * fabu : 2007-06-04T17:30:00
         * fanwei : null
         * id : 111
         * jielun : null
         * noticeIp : 172.20.112.33
         * provinceSelect : 赣州
         * qianfaren : 林山源
         * shangji : 1
         * subclass : 暴雨黄色预警信号
         * ttime : 2007-06-04T17:25:46
         * yonghu : ldgxw
         * yubaoyuan : 曾令辉
         * ztname : 暴雨
         */

        private String alarmType;
        private String cexiao;
        private String citySelect;
        private String clickFlag;
        private String danwei;
        private String fabu;
        private Object fanwei;
        private int id;
        private Object jielun;
        private String noticeIp;
        private String provinceSelect;
        private String qianfaren;
        private String shangji;
        private String subclass;
        private String ttime;
        private String yonghu;
        private String yubaoyuan;
        private String ztname;

        public String getAlarmType() {
            return alarmType;
        }

        public void setAlarmType(String alarmType) {
            this.alarmType = alarmType;
        }

        public String getCexiao() {
            return cexiao;
        }

        public void setCexiao(String cexiao) {
            this.cexiao = cexiao;
        }

        public String getCitySelect() {
            return citySelect;
        }

        public void setCitySelect(String citySelect) {
            this.citySelect = citySelect;
        }

        public String getClickFlag() {
            return clickFlag;
        }

        public void setClickFlag(String clickFlag) {
            this.clickFlag = clickFlag;
        }

        public String getDanwei() {
            return danwei;
        }

        public void setDanwei(String danwei) {
            this.danwei = danwei;
        }

        public String getFabu() {
            return fabu;
        }

        public void setFabu(String fabu) {
            this.fabu = fabu;
        }

        public Object getFanwei() {
            return fanwei;
        }

        public void setFanwei(Object fanwei) {
            this.fanwei = fanwei;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public Object getJielun() {
            return jielun;
        }

        public void setJielun(Object jielun) {
            this.jielun = jielun;
        }

        public String getNoticeIp() {
            return noticeIp;
        }

        public void setNoticeIp(String noticeIp) {
            this.noticeIp = noticeIp;
        }

        public String getProvinceSelect() {
            return provinceSelect;
        }

        public void setProvinceSelect(String provinceSelect) {
            this.provinceSelect = provinceSelect;
        }

        public String getQianfaren() {
            return qianfaren;
        }

        public void setQianfaren(String qianfaren) {
            this.qianfaren = qianfaren;
        }

        public String getShangji() {
            return shangji;
        }

        public void setShangji(String shangji) {
            this.shangji = shangji;
        }

        public String getSubclass() {
            return subclass;
        }

        public void setSubclass(String subclass) {
            this.subclass = subclass;
        }

        public String getTtime() {
            return ttime;
        }

        public void setTtime(String ttime) {
            this.ttime = ttime;
        }

        public String getYonghu() {
            return yonghu;
        }

        public void setYonghu(String yonghu) {
            this.yonghu = yonghu;
        }

        public String getYubaoyuan() {
            return yubaoyuan;
        }

        public void setYubaoyuan(String yubaoyuan) {
            this.yubaoyuan = yubaoyuan;
        }

        public String getZtname() {
            return ztname;
        }

        public void setZtname(String ztname) {
            this.ztname = ztname;
        }
    }
}
