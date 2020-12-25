package cn.itcast.domain;

import java.io.Serializable;

public class TpUser implements Serializable {
    private Integer uid;
    private String username;
    private String password;
    private String email;
    private String vip;
    private String isReg;

    public TpUser() {
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getVip() {
        return vip;
    }

    public void setVip(String vip) {
        this.vip = vip;
    }

    public String getIsReg() {
        return isReg;
    }

    public void setIsReg(String isReg) {
        this.isReg = isReg;
    }

    @Override
    public String toString() {
        return "TpUser{" +
                "uid=" + uid +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", vip='" + vip + '\'' +
                ", isReg='" + isReg + '\'' +
                '}';
    }
}
