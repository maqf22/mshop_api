package com.mshop.commons;

/**
 * 请求响应状态码
 */
public enum Code {
    OK("200", "请求成功"),
    ERROR("-1", "请求失败"),
    NOT_LOGIN("401", "用户未登录"),
    NO_CONFIRM_ORDERS("11000", "没有确认订单"),
    NO_STORE("11001", "没有库存");

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
