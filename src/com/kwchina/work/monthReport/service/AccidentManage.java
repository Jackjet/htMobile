package com.kwchina.work.monthReport.service;

import com.kwchina.core.common.service.BasicManager;
import com.kwchina.work.monthReport.vo.AccidentVO;

public interface AccidentManage extends BasicManager {
    public AccidentVO getAccidentVo(Integer accidentId);
}
