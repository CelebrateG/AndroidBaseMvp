package com.hazz.example.data.entity;

/**
 * @Auther:gq
 * @Desc:
 * @Date:2019/5/24
 */
public class BaseResult {
    public String code;
    public String result;

    public BaseResult(String code, String result) {
        this.code = code;
        this.result = result;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "BaseResult{" +
                "code='" + code + '\'' +
                ", result='" + result + '\'' +
                '}';
    }
}
