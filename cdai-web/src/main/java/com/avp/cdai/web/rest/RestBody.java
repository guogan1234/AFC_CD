package com.avp.cdai.web.rest;

import java.util.Date;

/**
 * Created by boris feng on 2017/5/27.
 */
public class RestBody<T> {
    private Integer code = -1;
    private String message = "unknown";
    private Long timestamp = new Date().getTime();

    RestBody(ResponseCode code) {
        this.code = code.value();
        this.message = code.phrase();
    }


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getCode() {
        return this.code;
    }

    public Long getTimestamp() {
        return this.timestamp;
    }
}