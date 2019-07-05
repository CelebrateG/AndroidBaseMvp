package com.hazz.example.data.entity;

/**
 * @Auther:gq
 * @Desc:
 * @Date:2019/5/29
 */
public class BaseEntity {
    private int ret;
    private String err;

    public int getRet() {
        return ret;
    }

    public void setRet(int ret) {
        this.ret = ret;
    }

    public String getErr() {
        return err;
    }

    public void setErr(String err) {
        this.err = err;
    }

    @Override
    public String toString() {
        return "BaseEntity{" +
                "ret=" + ret +
                ", err='" + err + '\'' +
                '}';
    }
}
