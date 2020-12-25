package cn.itcast.domain;

import java.io.Serializable;

/**
 * 文件夹
 */
public class TpFol implements Serializable {
    private Integer fid;
    private String fname;
    private Integer f_uid;
    private String f_path;

    public TpFol() {
    }

    public Integer getFid() {
        return fid;
    }

    public void setFid(Integer fid) {
        this.fid = fid;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public Integer getF_uid() {
        return f_uid;
    }

    public void setF_uid(Integer f_uid) {
        this.f_uid = f_uid;
    }

    public String getF_path() {
        return f_path;
    }

    public void setF_path(String f_path) {
        this.f_path = f_path;
    }

    @Override
    public String toString() {
        return "TpFol{" +
                "fid=" + fid +
                ", fname='" + fname + '\'' +
                ", f_uid=" + f_uid +
                ", f_path='" + f_path + '\'' +
                '}';
    }
}
