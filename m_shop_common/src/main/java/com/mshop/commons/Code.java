package com.mshop.commons;

/**
 * 请求响应状态码
 */
public enum Code {
    OK("10000", "请求成功"),
    ERROR("10001", "请求失败"),
    NO_CONFIRM_ORDERS("11000", "没有确认订单");

    Code(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    private String code;
    private String msg;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
