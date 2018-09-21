package net.univr.pushi.jxatmosphere.beens;

/**
 * author : Administrator wl
 * e-mail : 389456264@qq.com
 * time   : 2018/04/09
 * desc   :
 * version: 1.0
 */


public class UserRetBeen {


    /**
     * errmsg : 登录成功
     * user : {"avatar":"http://shp.qpic.cn/bizmp/5ibPPDmuDJpOJ48MWmZAPx3yGVHPiazyJBsAEM2Z8VtoNHYWfwnak2cw/","department":1,"email":"","enablecloumn":1,"englishName":"","gender":"1","hideMobile":0,"isleader":0,"mobile":"13803542502","namecloumn":"许爱华","ordercloumn":0,"position":"","qrCode":"http://open.work.weixin.qq.com/wwopen/userQRCode?vcode=vcf51648efc3c280f6","statuscloumn":1,"telephone":"","userid":"xuah"}
     * errcode : 0
     */

    private String errmsg;
    private UserBean user;
    private String errcode;

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }

    public UserBean getUser() {
        return user;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }

    public String getErrcode() {
        return errcode;
    }

    public void setErrcode(String errcode) {
        this.errcode = errcode;
    }

    public static class UserBean {
        /**
         * avatar : http://shp.qpic.cn/bizmp/5ibPPDmuDJpOJ48MWmZAPx3yGVHPiazyJBsAEM2Z8VtoNHYWfwnak2cw/
         * department : 1
         * email :
         * enablecloumn : 1
         * englishName :
         * gender : 1
         * hideMobile : 0
         * isleader : 0
         * mobile : 13803542502
         * namecloumn : 许爱华
         * ordercloumn : 0
         * position :
         * qrCode : http://open.work.weixin.qq.com/wwopen/userQRCode?vcode=vcf51648efc3c280f6
         * statuscloumn : 1
         * telephone :
         * userid : xuah
         */

        private String avatar;
        private int department;
        private String email;
        private int enablecloumn;
        private String englishName;
        private String gender;
        private int hideMobile;
        private int isleader;
        private String mobile;
        private String namecloumn;
        private int ordercloumn;
        private String position;
        private String qrCode;
        private int statuscloumn;
        private String telephone;
        private String userid;

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public int getDepartment() {
            return department;
        }

        public void setDepartment(int department) {
            this.department = department;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public int getEnablecloumn() {
            return enablecloumn;
        }

        public void setEnablecloumn(int enablecloumn) {
            this.enablecloumn = enablecloumn;
        }

        public String getEnglishName() {
            return englishName;
        }

        public void setEnglishName(String englishName) {
            this.englishName = englishName;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public int getHideMobile() {
            return hideMobile;
        }

        public void setHideMobile(int hideMobile) {
            this.hideMobile = hideMobile;
        }

        public int getIsleader() {
            return isleader;
        }

        public void setIsleader(int isleader) {
            this.isleader = isleader;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getNamecloumn() {
            return namecloumn;
        }

        public void setNamecloumn(String namecloumn) {
            this.namecloumn = namecloumn;
        }

        public int getOrdercloumn() {
            return ordercloumn;
        }

        public void setOrdercloumn(int ordercloumn) {
            this.ordercloumn = ordercloumn;
        }

        public String getPosition() {
            return position;
        }

        public void setPosition(String position) {
            this.position = position;
        }

        public String getQrCode() {
            return qrCode;
        }

        public void setQrCode(String qrCode) {
            this.qrCode = qrCode;
        }

        public int getStatuscloumn() {
            return statuscloumn;
        }

        public void setStatuscloumn(int statuscloumn) {
            this.statuscloumn = statuscloumn;
        }

        public String getTelephone() {
            return telephone;
        }

        public void setTelephone(String telephone) {
            this.telephone = telephone;
        }

        public String getUserid() {
            return userid;
        }

        public void setUserid(String userid) {
            this.userid = userid;
        }
    }
}
