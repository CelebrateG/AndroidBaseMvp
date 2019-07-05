package com.hazz.example.data.entity;

/**
 * @description:
 * @author: xhh
 * @date: 2018/01/12 11:27
 */

public class Respond extends Throwable{
    //机器是否准备好
    private boolean isSuccess;
    //机器异常说明
    private String reason;
    private int time;
    //机器返回的结果
    private String result;


    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }


    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }


    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "Respond{" +
                "isSuccess=" + isSuccess +
                ", reason='" + reason + '\'' +
                ", time=" + time +
                ", result='" + result + '\'' +
                '}';
    }
}
