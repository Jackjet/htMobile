package com.kwchina.work.monthReport.service;

import com.kwchina.core.common.service.BasicManager;
import com.kwchina.work.monthReport.entity.Focus;
import com.kwchina.work.monthReport.vo.FocusVO;

public interface FocusManage extends BasicManager {
    Focus getFocus(String time, Integer type);
    FocusVO getVoById(Integer focusId);
}
