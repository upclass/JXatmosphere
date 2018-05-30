package net.univr.pushi.jxatmosphere.beens;

import java.util.List;

/**
 * author : Administrator wl
 * e-mail : 389456264@qq.com
 * time   : 2018/05/03
 * desc   :
 * version: 1.0
 */


public class GkdmgcBeen {
    /**
     * data : {"title":["2018年04月27日20时百帕高空观测图","2018年04月28日08时百帕高空观测图","2018年04月28日20时百帕高空观测图","2018年04月29日08时百帕高空观测图","2018年04月29日20时百帕高空观测图","2018年04月30日08时百帕高空观测图","2018年04月30日20时百帕高空观测图","2018年05月01日08时百帕高空观测图","2018年05月01日20时百帕高空观测图","2018年05月02日08时百帕高空观测图","2018年05月02日20时百帕高空观测图","2018年05月03日08时百帕高空观测图"],"time":["18042720","18042808","18042820","18042908","18042920","18043008","18043020","18050108","18050120","18050208","18050220","18050308"],"url":["http://10.116.36.74/sk/500/18042720.jpg","http://10.116.36.74/sk/500/18042808.jpg","http://10.116.36.74/sk/500/18042820.jpg","http://10.116.36.74/sk/500/18042908.jpg","http://10.116.36.74/sk/500/18042920.jpg","http://10.116.36.74/sk/500/18043008.jpg","http://10.116.36.74/sk/500/18043020.jpg","http://10.116.36.74/sk/500/18050108.jpg","http://10.116.36.74/sk/500/18050120.jpg","http://10.116.36.74/sk/500/18050208.jpg","http://10.116.36.74/sk/500/18050220.jpg","http://10.116.36.74/sk/500/18050308.jpg"]}
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
        private List<String> title;
        private List<String> time;
        private List<String> url;

        public List<String> getTitle() {
            return title;
        }

        public void setTitle(List<String> title) {
            this.title = title;
        }

        public List<String> getTime() {
            return time;
        }

        public void setTime(List<String> time) {
            this.time = time;
        }

        public List<String> getUrl() {
            return url;
        }

        public void setUrl(List<String> url) {
            this.url = url;
        }
    }
}
