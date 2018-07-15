package com.common.app.database.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Unique;

/**
 * @author: zhengjr
 * @since: 2018/6/21
 * @describe:
 */
@Entity
public class UserInfoBean {

    @Id
    @Unique
    @Property
    public String phone;//手机号作为唯一的字段

    @Property
    private String photo;

    @Property
    private long createTime;

    @Property
    private String createTimeStr;

    @Property
    private String customerServiceId;

    @Property
    private String id;

    @Property
    private int isActiveStatus;

    @Property
    private String lastProductId;

    @Property
    private String openImPassword;

    @Property
    private String password;

    @Property
    private String safeToken;

    @Property
    private int signedStatus;

    @Property
    private int status;

    @Property
    private int superiorId;

    @Property
    private int verifiCode;

    @Property
    public boolean isLogin;

    @Property
    public String firstLoginFlag;

    @Generated(hash = 257876801)
    public UserInfoBean(String phone, String photo, long createTime,
            String createTimeStr, String customerServiceId, String id,
            int isActiveStatus, String lastProductId, String openImPassword,
            String password, String safeToken, int signedStatus, int status,
            int superiorId, int verifiCode, boolean isLogin,
            String firstLoginFlag) {
        this.phone = phone;
        this.photo = photo;
        this.createTime = createTime;
        this.createTimeStr = createTimeStr;
        this.customerServiceId = customerServiceId;
        this.id = id;
        this.isActiveStatus = isActiveStatus;
        this.lastProductId = lastProductId;
        this.openImPassword = openImPassword;
        this.password = password;
        this.safeToken = safeToken;
        this.signedStatus = signedStatus;
        this.status = status;
        this.superiorId = superiorId;
        this.verifiCode = verifiCode;
        this.isLogin = isLogin;
        this.firstLoginFlag = firstLoginFlag;
    }

    @Generated(hash = 1818808915)
    public UserInfoBean() {
    }

    public String getPhone() {
        return this.phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public long getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public String getCreateTimeStr() {
        return this.createTimeStr;
    }

    public void setCreateTimeStr(String createTimeStr) {
        this.createTimeStr = createTimeStr;
    }

    public String getCustomerServiceId() {
        return this.customerServiceId;
    }

    public void setCustomerServiceId(String customerServiceId) {
        this.customerServiceId = customerServiceId;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getIsActiveStatus() {
        return this.isActiveStatus;
    }

    public void setIsActiveStatus(int isActiveStatus) {
        this.isActiveStatus = isActiveStatus;
    }

    public String getLastProductId() {
        return this.lastProductId;
    }

    public void setLastProductId(String lastProductId) {
        this.lastProductId = lastProductId;
    }

    public String getOpenImPassword() {
        return this.openImPassword;
    }

    public void setOpenImPassword(String openImPassword) {
        this.openImPassword = openImPassword;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSafeToken() {
        return this.safeToken;
    }

    public void setSafeToken(String safeToken) {
        this.safeToken = safeToken;
    }

    public int getSignedStatus() {
        return this.signedStatus;
    }

    public void setSignedStatus(int signedStatus) {
        this.signedStatus = signedStatus;
    }

    public int getStatus() {
        return this.status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getSuperiorId() {
        return this.superiorId;
    }

    public void setSuperiorId(int superiorId) {
        this.superiorId = superiorId;
    }

    public int getVerifiCode() {
        return this.verifiCode;
    }

    public void setVerifiCode(int verifiCode) {
        this.verifiCode = verifiCode;
    }

    public boolean getIsLogin() {
        return this.isLogin;
    }

    public void setIsLogin(boolean isLogin) {
        this.isLogin = isLogin;
    }

    public String getPhoto() {
        return this.photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getFirstLoginFlag() {
        return this.firstLoginFlag;
    }

    public void setFirstLoginFlag(String firstLoginFlag) {
        this.firstLoginFlag = firstLoginFlag;
    }



}
