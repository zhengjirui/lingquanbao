package com.lechuang.lingquanbao.bean;

/**
 * Created by cmd on 2018/7/15.
 */

public class LoginBean {


    /**
     * user : {"createTime":1531189861000,"createTimeStr":"2018-07-10 10:31:01","customerServiceId":"领券宝","id":"19b78","isActiveStatus":0,"lastProductId":"43823895839","openImPassword":"f0f901480855bff8eecd0e265660dd71","password":"8debebbcf7d158c175813ca97ebf0e25525dd5e03bbe61d173a048099f18e27cb3a2074f5950a08c","phone":"13221035103","safeToken":"A121D529805FA95AABEA0BCB8AAB3BE7","signedStatus":0,"status":1,"superiorId":1257,"verifiCode":271855}
     */

    private UserBean user;

    public UserBean getUser() {
        return user;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }

    public static class UserBean {
        /**
         * createTime : 1531189861000
         * createTimeStr : 2018-07-10 10:31:01
         * customerServiceId : 领券宝
         * id : 19b78
         * isActiveStatus : 0
         * lastProductId : 43823895839
         * openImPassword : f0f901480855bff8eecd0e265660dd71
         * password : 8debebbcf7d158c175813ca97ebf0e25525dd5e03bbe61d173a048099f18e27cb3a2074f5950a08c
         * phone : 13221035103
         * safeToken : A121D529805FA95AABEA0BCB8AAB3BE7
         * signedStatus : 0
         * status : 1
         * superiorId : 1257
         * verifiCode : 271855
         */

        private long createTime;
        private String createTimeStr;
        private String customerServiceId;
        private String id;
        private int isActiveStatus;
        private String lastProductId;
        private String openImPassword;
        private String password;
        private String phone;
        private String safeToken;
        public String firstLoginFlag;
        private int signedStatus;
        private int status;
        private int superiorId;
        private int verifiCode;

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }

        public String getCreateTimeStr() {
            return createTimeStr;
        }

        public void setCreateTimeStr(String createTimeStr) {
            this.createTimeStr = createTimeStr;
        }

        public String getCustomerServiceId() {
            return customerServiceId;
        }

        public void setCustomerServiceId(String customerServiceId) {
            this.customerServiceId = customerServiceId;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public int getIsActiveStatus() {
            return isActiveStatus;
        }

        public void setIsActiveStatus(int isActiveStatus) {
            this.isActiveStatus = isActiveStatus;
        }

        public String getLastProductId() {
            return lastProductId;
        }

        public void setLastProductId(String lastProductId) {
            this.lastProductId = lastProductId;
        }

        public String getOpenImPassword() {
            return openImPassword;
        }

        public void setOpenImPassword(String openImPassword) {
            this.openImPassword = openImPassword;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getSafeToken() {
            return safeToken;
        }

        public void setSafeToken(String safeToken) {
            this.safeToken = safeToken;
        }

        public int getSignedStatus() {
            return signedStatus;
        }

        public void setSignedStatus(int signedStatus) {
            this.signedStatus = signedStatus;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getSuperiorId() {
            return superiorId;
        }

        public void setSuperiorId(int superiorId) {
            this.superiorId = superiorId;
        }

        public int getVerifiCode() {
            return verifiCode;
        }

        public void setVerifiCode(int verifiCode) {
            this.verifiCode = verifiCode;
        }

        public String getFirstLoginFlag() {
            return firstLoginFlag;
        }

        public void setFirstLoginFlag(String firstLoginFlag) {
            this.firstLoginFlag = firstLoginFlag;
        }
    }
}
