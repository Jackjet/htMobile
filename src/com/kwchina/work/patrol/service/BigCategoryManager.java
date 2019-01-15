package com.kwchina.work.patrol.service;

import java.util.List;

import com.kwchina.core.common.service.BasicManager;
import com.kwchina.work.patrol.entity.BigCategory;

public interface BigCategoryManager extends BasicManager{
	public BigCategory getBigCategoryByBigId(Integer bigId);
	public List<BigCategory> getAllValid();
}
