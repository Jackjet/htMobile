package com.kwchina.work.train.enums;

import com.kwchina.work.reform.enums.CodeEnum;

/**
 * Created by asus on 2018/6/12.
 */

public enum TrainDeptEnum implements CodeEnum {
    JL_DCH(1,"技劳堆场"),
    DONG_LEI (2,"东雷公司"),
    ZH_CH_W_L  (3,"整车物流"),
    ZHW_NET (4,"驻外网点"),
    ZHG_UNIT (5,"驻港单位"),
    OTHERS (6,"其他"),
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

    TrainDeptEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    @Override
    public Integer getCode() {
        return this.code;
    }
}
