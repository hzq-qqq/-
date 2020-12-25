package cn.itcast.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 文件
 */
public class TpDoc implements Serializable {
    private Integer did;
    private String dname;
    private Integer dsize;
    private String dupload;
    private Integer dfid;
    private Integer duid;

    public TpDoc() {
    }

    public Integer getDid() {
        return did;
    }

    public void setDid(Integer did) {
        this.did = did;
    }

    public String getDname() {
        return dname;
    }

    public void setDname(String dname) {
        this.dname = dname;
    }

    public Integer getDsize() {
        return dsize;
    }

    public void setDsize(Integer dsize) {
        this.dsize = dsize;
    }

    public String getDupload() {
        return dupload;
    }

    public void setDupload(String dupload) {
        this.dupload = dupload;
    }

    public Integer getDfid() {
        return dfid;
    }

    public void setDfid(Integer dfid) {
        this.dfid = dfid;
    }

    public Integer getDuid() {
        return duid;
    }

    public void setDuid(Integer duid) {
        this.duid = duid;
    }

    @Override
    public String toString() {
        return "TpDoc{" +
                "did=" + did +
                ", dname='" + dname + '\'' +
                ", dsize=" + dsize +
                ", dupload='" + dupload + '\'' +
                ", dfid=" + dfid +
                ", duid=" + duid +
                '}';
    }
}
