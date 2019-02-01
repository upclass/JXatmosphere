package net.univr.pushi.jxatmosphere.beens;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * author : Administrator wl
 * e-mail : 389456264@qq.com
 * time   : 2019/01/18
 * desc   :
 * version: 1.0
 */


public class YujinInfo {


    /**
     * data : [{"acodes":"","content":"玉山县气象局发布大雾黄色预警信号：12小时内玉山县可能出现能见度小于500米的雾，或者已经出现能见度小于500米、大于等于200米的雾并将持续。","danwei":"玉山县气象局","fabu":"2019-01-18 09:35:52","yuBaoYuan":"张珊伟","yujinInfo":"大雾黄色预警信号"},{"acodes":"","content":"铜鼓县气象局发布大雾黄色预警信号：目前铜鼓县已经出现大雾，预计未来2到3小时大雾仍将持续，请注意防范。","danwei":"铜鼓县气象局","fabu":"2019-01-18 07:40:00","yuBaoYuan":"刘娟","yujinInfo":"大雾黄色预警信号"},{"acodes":"","content":"我县已出现能见度小于500米的大雾天气，预计大雾仍将持续，请注意防范。","danwei":"余干县气象局","fabu":"2019-01-18 07:05:00","yuBaoYuan":"管慧珍","yujinInfo":"大雾黄色预警信号"},{"acodes":"","content":"乐安县气象局发布大雾黄色预警信号：12小时内我县可能出现能见度小于500米的雾，或者已经出现能见度小于500米的雾并将持续。","danwei":"乐安县气象局","fabu":"2019-01-18 06:26:48","yuBaoYuan":"陈维娜","yujinInfo":"大雾黄色预警信号"},{"acodes":"","content":"浮梁县气象局发布大雾黄色预警信号：12小时内浮梁县可能出现能见度小于500米的雾，或者已经出现能见度小于500米、大于等于200米的雾并将持续。","danwei":"浮梁县气象局","fabu":"2019-01-18 00:12:07","yuBaoYuan":"徐园","yujinInfo":"大雾黄色预警信号"},{"acodes":"","content":"黎川县气象局发布大雾黄色预警信号：12小时内黎川县可能出现能见度小于500米的雾，请注意交通安全。","danwei":"黎川县气象局","fabu":"2019-01-17 23:51:10","yuBaoYuan":"朱斌","yujinInfo":"大雾黄色预警信号"},{"acodes":"","content":"南丰县气象局发布大雾黄色预警信号：预计未来12小时内南丰县可能出现能见度小于500米的雾，并将持续；请注意交通安全。","danwei":"南丰县气象局","fabu":"2019-01-17 23:43:08","yuBaoYuan":"吴欣怡","yujinInfo":"大雾黄色预警信号"},{"acodes":"","content":"万安县气象局发布大雾黄色预警信号：预计未来12小时内万安县可能出现能见度小于500米的雾，部分山区能见度可能小于200米，请注意防范。","danwei":"万安县气象局","fabu":"2019-01-17 22:56:51","yuBaoYuan":"陈彦超","yujinInfo":"大雾黄色预警信号"},{"acodes":"","content":"万年县气象局发布大雾黄色预警信号：12小时内万年县可能出现能见度小于500米的雾，或者已经出现能见度小于500米、大于等于200米的雾并将持续。","danwei":"万年县气象局","fabu":"2019-01-17 21:27:05","yuBaoYuan":"曾艳婷","yujinInfo":"大雾黄色预警信号"},{"acodes":"","content":"宜丰县气象局发布大雾黄色预警信号：宜丰县已出现能见度小于500米的雾，并将持续。请有关部门注意防范！","danwei":"宜丰县气象局","fabu":"2019-01-12 07:32:31","yuBaoYuan":"罗燕","yujinInfo":"大雾黄色预警信号"}]
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

    public static class DataBean implements Parcelable {
        /**
         * acodes :
         * content : 玉山县气象局发布大雾黄色预警信号：12小时内玉山县可能出现能见度小于500米的雾，或者已经出现能见度小于500米、大于等于200米的雾并将持续。
         * danwei : 玉山县气象局
         * fabu : 2019-01-18 09:35:52
         * yuBaoYuan : 张珊伟
         * yujinInfo : 大雾黄色预警信号
         */

        private String acodes;
        private String content;
        private String danwei;
        private String fabu;
        private String yuBaoYuan;
        private String yujinInfo;

        protected DataBean(Parcel in) {
            acodes = in.readString();
            content = in.readString();
            danwei = in.readString();
            fabu = in.readString();
            yuBaoYuan = in.readString();
            yujinInfo = in.readString();
        }

        public static final Creator<DataBean> CREATOR = new Creator<DataBean>() {
            @Override
            public DataBean createFromParcel(Parcel in) {
                return new DataBean(in);
            }

            @Override
            public DataBean[] newArray(int size) {
                return new DataBean[size];
            }
        };

        public String getAcodes() {
            return acodes;
        }

        public void setAcodes(String acodes) {
            this.acodes = acodes;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
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

        public String getYuBaoYuan() {
            return yuBaoYuan;
        }

        public void setYuBaoYuan(String yuBaoYuan) {
            this.yuBaoYuan = yuBaoYuan;
        }

        public String getYujinInfo() {
            return yujinInfo;
        }

        public void setYujinInfo(String yujinInfo) {
            this.yujinInfo = yujinInfo;
        }


        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(acodes);
            dest.writeString(content);
            dest.writeString(danwei);
            dest.writeString(fabu);
            dest.writeString(yuBaoYuan);
            dest.writeString(yujinInfo);
        }
    }
}
