package com.kwchina.work.reform.enums;
/**
 * Created by asus on 2018/6/12.
 */

public enum ReformStatusEnum implements CodeEnum{
	TO_BE_RETIFIED(1,"待整改"),
	IN_RECTIFICATION (2,"整改中"),
	PENDING_REVIEW  (3,"待复查"),
	HAS_BEEN_RECTIFIED (4,"已整改"),
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

    ReformStatusEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    @Override
    public Integer getCode() {
        return this.code;
    }
}
