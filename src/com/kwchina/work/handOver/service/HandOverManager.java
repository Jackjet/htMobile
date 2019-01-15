package com.kwchina.work.handOver.service;

import com.kwchina.core.common.service.BasicManager;
import com.kwchina.work.handOver.vo.HandOverVO;

public interface HandOverManager extends BasicManager{
    public HandOverVO getVoById(Integer handId);
}
