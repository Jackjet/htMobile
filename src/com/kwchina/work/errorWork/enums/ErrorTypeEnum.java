package com.kwchina.work.errorWork.enums;

import com.kwchina.work.reform.enums.CodeEnum;

/**
 * Created by asus on 2018/6/12.
 */

public enum ErrorTypeEnum implements CodeEnum {
    TYPE_DEPART(1,"部门自查"),
    TYPE_SAFE_CHECK (2,"安全巡检"),
    TYPE_SPECIAL  (3,"专项检查"),
    TYPE_LEADER (4,"领导巡查"),
    ;
    private Integer code;
    private String msg;

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    ErrorTypeEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    @Override
    public Integer getCode() {
        return this.code;
    }
}
