package cn.itcast.domain;

import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
public class ErrorMsg implements Serializable{
    private String errMsg;
    private boolean status;

    public ErrorMsg() {
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "ErrorMsg{" +
                "errMsg='" + errMsg + '\'' +
                ", status=" + status +
                '}';
    }
}
