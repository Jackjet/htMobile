package com.kwchina.work.reform.vo;

import lombok.Data;

import java.util.List;

/**
 * @author 俊杰
 */
@Data
public class YearListVo {
    /** 类*/
    private String parentCategory;
    /**Item*/
    private List<YearItemVo> childItems;

}
