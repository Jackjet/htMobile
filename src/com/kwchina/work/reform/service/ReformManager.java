package com.kwchina.work.reform.service;

import com.kwchina.core.common.service.BasicManager;
import com.kwchina.work.reform.entity.ReformDetail;
import com.kwchina.work.reform.vo.ReformVo;


public interface ReformManager extends BasicManager{
	public ReformDetail getReformDetailBytaskId(String taskId);

	public ReformVo getReformDetail(Integer reformId);
}
