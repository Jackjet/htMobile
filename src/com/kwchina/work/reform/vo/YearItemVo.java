package com.kwchina.work.reform.vo;

import lombok.Data;

import java.util.List;

/**
 * @author 俊杰
 */
@Data
public class YearItemVo {
    /** 项*/
    private String categoryName;
    /**次数*/
    private List<Integer> counts;
}
