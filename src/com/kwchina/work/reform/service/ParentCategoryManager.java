package com.kwchina.work.reform.service;

import com.kwchina.core.common.service.BasicManager;
import com.kwchina.work.reform.entity.ParentCategory;

import java.util.List;

public interface ParentCategoryManager extends BasicManager{
	public ParentCategory getParentCategoryByParentId(Integer parentId);
	public List<ParentCategory> getAllValid();
}
