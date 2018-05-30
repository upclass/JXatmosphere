package net.univr.pushi.jxatmosphere.beens;

/**
 * author : Administrator wl
 * e-mail : 389456264@qq.com
 * time   : 2018/04/03
 * desc   :
 * version: 1.0
 */


public class WorkSchedule {
    private String datatime;
    private String watcherkeeper;
    private String post;
    private String ondutytime;

    public WorkSchedule() {
    }

    public String getDatatime() {
        return datatime;
    }

    public void setDatatime(String datatime) {
        this.datatime = datatime;
    }

    public String getWatcherkeeper() {
        return watcherkeeper;
    }

    public void setWatcherkeeper(String watcherkeeper) {
        this.watcherkeeper = watcherkeeper;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public String getOndutytime() {
        return ondutytime;
    }

    public void setOndutytime(String ondutytime) {
        this.ondutytime = ondutytime;
    }

    @Override
    public String toString() {
        return "WorkSchedule{" +
                "datatime='" + datatime + '\'' +
                ", watcherkeeper='" + watcherkeeper + '\'' +
                ", post='" + post + '\'' +
                ", ondutytime='" + ondutytime + '\'' +
                '}';
    }
}
