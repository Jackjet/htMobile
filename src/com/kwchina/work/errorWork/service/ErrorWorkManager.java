package com.kwchina.work.errorWork.service;

import com.kwchina.core.common.service.BasicManager;
import com.kwchina.work.errorWork.vo.ErrorWorkVO;

public interface ErrorWorkManager extends BasicManager {
    public ErrorWorkVO getVoById(Integer errorWorkId);
}