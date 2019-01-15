package com.kwchina.work.peccancy.service;

import com.kwchina.core.common.service.BasicManager;
import com.kwchina.work.peccancy.vo.PeccancyVo;

public interface PeccancyDetailManager extends BasicManager{
    public PeccancyVo getPeccancyVo(Integer peccancyId);

}
