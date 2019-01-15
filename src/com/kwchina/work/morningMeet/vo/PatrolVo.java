package com.kwchina.work.morningMeet.vo;


import com.kwchina.work.patrol.entity.ItemDetail;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 巡检大项
 * @author suguan
 *
 */
@Data
public class PatrolVo {
    private String bigName;
    private List<ItemDetailVo> items=new ArrayList<>();
}



