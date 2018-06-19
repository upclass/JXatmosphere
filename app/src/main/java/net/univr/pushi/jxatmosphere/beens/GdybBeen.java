package net.univr.pushi.jxatmosphere.beens;

import java.util.List;

/**
 * author : Administrator wl
 * e-mail : 389456264@qq.com
 * time   : 2018/05/09
 * desc   :
 * version: 1.0
 */


public class GdybBeen {


    /**
     * data : [{"allCloud":"90","forecastTime":"2018/5/8 11:00:00","heightTemper":"0","humidity":"78","lowTemper":"0","rain":"0","temper":"22.7","visible":"0","weatherDesc":"阴","windDir":"40.6","windSpeed":"0.9"},{"allCloud":"91","forecastTime":"2018/5/8 14:00:00","heightTemper":"0","humidity":"72.8","lowTemper":"0","rain":"0","temper":"24.7","visible":"0","weatherDesc":"阴","windDir":"34.7","windSpeed":"1.6"},{"allCloud":"68","forecastTime":"2018/5/8 17:00:00","heightTemper":"0","humidity":"71.8","lowTemper":"0","rain":"0","temper":"23.9","visible":"0","weatherDesc":"多云","windDir":"42","windSpeed":"1.3"},{"allCloud":"91","forecastTime":"2018/5/8 20:00:00","heightTemper":"0","humidity":"77.7","lowTemper":"0","rain":"0","temper":"20","visible":"0","weatherDesc":"阴","windDir":"47.7","windSpeed":"1.5"},{"allCloud":"74","forecastTime":"2018/5/8 23:00:00","heightTemper":"0","humidity":"84.1","lowTemper":"0","rain":"0","temper":"17.6","visible":"0","weatherDesc":"多云","windDir":"40.6","windSpeed":"0.9"},{"allCloud":"92","forecastTime":"2018/5/9 2:00:00","heightTemper":"0","humidity":"87.7","lowTemper":"0","rain":"0","temper":"16.9","visible":"0","weatherDesc":"阴","windDir":"26.6","windSpeed":"0.7"},{"allCloud":"80","forecastTime":"2018/5/9 5:00:00","heightTemper":"0","humidity":"85.8","lowTemper":"0","rain":"0","temper":"16.2","visible":"0","weatherDesc":"阴","windDir":"33.7","windSpeed":"1.1"},{"allCloud":"92","forecastTime":"2018/5/9 8:00:00","heightTemper":"24.7","humidity":"77.5","lowTemper":"16.2","rain":"0","temper":"17.9","visible":"0","weatherDesc":"阴","windDir":"55.3","windSpeed":"1.6"},{"allCloud":"24","forecastTime":"2018/5/9 11:00:00","heightTemper":"0","humidity":"58.2","lowTemper":"0","rain":"0","temper":"22.1","visible":"0","weatherDesc":"晴","windDir":"45","windSpeed":"1.6"},{"allCloud":"5","forecastTime":"2018/5/9 14:00:00","heightTemper":"0","humidity":"65","lowTemper":"0","rain":"0","temper":"24.7","visible":"0","weatherDesc":"晴","windDir":"56.3","windSpeed":"1.8"},{"allCloud":"11","forecastTime":"2018/5/9 17:00:00","heightTemper":"0","humidity":"64.2","lowTemper":"0","rain":"0","temper":"24.7","visible":"0","weatherDesc":"晴","windDir":"54","windSpeed":"1.4"},{"allCloud":"55","forecastTime":"2018/5/9 20:00:00","heightTemper":"0","humidity":"76.9","lowTemper":"0","rain":"0","temper":"19.5","visible":"0","weatherDesc":"多云","windDir":"36.3","windSpeed":"1.9"},{"allCloud":"40","forecastTime":"2018/5/9 23:00:00","heightTemper":"0","humidity":"80.2","lowTemper":"0","rain":"0","temper":"16.9","visible":"0","weatherDesc":"多云","windDir":"41.6","windSpeed":"1.2"},{"allCloud":"76","forecastTime":"2018/5/10 2:00:00","heightTemper":"0","humidity":"85.5","lowTemper":"0","rain":"0","temper":"16.3","visible":"0","weatherDesc":"多云","windDir":"45","windSpeed":"1"},{"allCloud":"54","forecastTime":"2018/5/10 5:00:00","heightTemper":"0","humidity":"89.3","lowTemper":"0","rain":"0","temper":"16.3","visible":"0","weatherDesc":"多云","windDir":"49.4","windSpeed":"0.9"},{"allCloud":"76","forecastTime":"2018/5/10 8:00:00","heightTemper":"24.7","humidity":"83.7","lowTemper":"16.3","rain":"0","temper":"17","visible":"0","weatherDesc":"多云","windDir":"55","windSpeed":"1.2"},{"allCloud":"81","forecastTime":"2018/5/10 11:00:00","heightTemper":"0","humidity":"69.7","lowTemper":"0","rain":"0","temper":"22.1","visible":"0","weatherDesc":"阴","windDir":"0","windSpeed":"0"},{"allCloud":"85","forecastTime":"2018/5/10 14:00:00","heightTemper":"0","humidity":"76.5","lowTemper":"0","rain":"0","temper":"24.8","visible":"0","weatherDesc":"阴","windDir":"0","windSpeed":"0"},{"allCloud":"67","forecastTime":"2018/5/10 17:00:00","heightTemper":"0","humidity":"76.3","lowTemper":"0","rain":"0","temper":"24.4","visible":"0","weatherDesc":"多云","windDir":"0","windSpeed":"0"},{"allCloud":"85","forecastTime":"2018/5/10 20:00:00","heightTemper":"0","humidity":"85.9","lowTemper":"0","rain":"0","temper":"22.1","visible":"0","weatherDesc":"阴","windDir":"0","windSpeed":"0"},{"allCloud":"26","forecastTime":"2018/5/10 23:00:00","heightTemper":"0","humidity":"91.8","lowTemper":"0","rain":"0","temper":"20.1","visible":"0","weatherDesc":"晴","windDir":"0","windSpeed":"0"},{"allCloud":"10","forecastTime":"2018/5/11 2:00:00","heightTemper":"0","humidity":"93.3","lowTemper":"0","rain":"0","temper":"19","visible":"0","weatherDesc":"晴","windDir":"0","windSpeed":"0"},{"allCloud":"50","forecastTime":"2018/5/11 5:00:00","heightTemper":"0","humidity":"92.8","lowTemper":"0","rain":"0","temper":"18.2","visible":"0","weatherDesc":"多云","windDir":"0","windSpeed":"0"},{"allCloud":"50","forecastTime":"2018/5/11 8:00:00","heightTemper":"24.8","humidity":"83.9","lowTemper":"18.2","rain":"0","temper":"19.9","visible":"0","weatherDesc":"多云","windDir":"0","windSpeed":"0"}]
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
         * allCloud : 90
         * forecastTime : 2018/5/8 11:00:00
         * heightTemper : 0
         * humidity : 78
         * lowTemper : 0
         * rain : 0
         * temper : 22.7
         * visible : 0
         * weatherDesc : 阴
         * windDir : 40.6
         * windSpeed : 0.9
         */

        private String allCloud;
        private String forecastTime;
        private String heightTemper;
        private String humidity;
        private String lowTemper;
        private String rain;
        private String temper;
        private String visible;
        private String weatherDesc;
        private String windDir;
        private String windSpeed;

        public String getAllCloud() {
            return allCloud;
        }

        public void setAllCloud(String allCloud) {
            this.allCloud = allCloud;
        }

        public String getForecastTime() {
            return forecastTime;
        }

        public void setForecastTime(String forecastTime) {
            this.forecastTime = forecastTime;
        }

        public String getHeightTemper() {
            return heightTemper;
        }

        public void setHeightTemper(String heightTemper) {
            this.heightTemper = heightTemper;
        }

        public String getHumidity() {
            return humidity;
        }

        public void setHumidity(String humidity) {
            this.humidity = humidity;
        }

        public String getLowTemper() {
            return lowTemper;
        }

        public void setLowTemper(String lowTemper) {
            this.lowTemper = lowTemper;
        }

        public String getRain() {
            return rain;
        }

        public void setRain(String rain) {
            this.rain = rain;
        }

        public String getTemper() {
            return temper;
        }

        public void setTemper(String temper) {
            this.temper = temper;
        }

        public String getVisible() {
            return visible;
        }

        public void setVisible(String visible) {
            this.visible = visible;
        }

        public String getWeatherDesc() {
            return weatherDesc;
        }

        public void setWeatherDesc(String weatherDesc) {
            this.weatherDesc = weatherDesc;
        }

        public String getWindDir() {
            return windDir;
        }

        public void setWindDir(String windDir) {
            this.windDir = windDir;
        }

        public String getWindSpeed() {
            return windSpeed;
        }

        public void setWindSpeed(String windSpeed) {
            this.windSpeed = windSpeed;
        }
    }
}
