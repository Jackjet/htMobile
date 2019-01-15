package com.kwchina.work.monthReport.vo;


import com.kwchina.work.morningMeet.vo.ItemDetailVo;
import com.kwchina.work.reform.entity.ChildCategory;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 巡检大项
 * @author suguan
 *
 */
@Data
public class DetailVO {
    private String bigName;
    private List<CountVO> countVOS=new ArrayList<>();

}



