package com.avp.cdai.web.rest;

/**
 * Created by boris feng on 2017/5/23.
 */
public enum ResponseCode {
    OK (0, "成功"),
    FAILED (-1, "失败"),

    CREATE_SUCCEED(10000, "新建成功"),
    CREATE_FAILED(-10000, "新建失败"),

    DELETE_SUCCEED(20000, "删除成功"),
    DELETE_FAILED(-20000, "删除失败"),

    UPDATE_SUCCEED(30000, "修改成功"),
    UPDATE_FAILED(-30000, "修改失败"),

    RETRIEVE_SUCCEED(40000, "查询成功"),
    RETRIEVE_FAILED(-40000, "查询失败"),

    ALREADY_EXIST(-50000, "数据已存在"),
    NOT_EXIST(-50001, "数据不存在"),
    BAD_REQUEST(-60000, "错误请求"),
    PARAM_MISSING(-70000, "参数缺失"),
    NOT_IMPLEMENTED(-80000, "未实现");

    private final Integer value;
    private final String phrase;

    private ResponseCode(int value, String phrase) {
        this.value = value;
        this.phrase = phrase;
    }

    public Integer value() {
        return this.value;
    }

    public String phrase() {
        return this.phrase;
    }
}
