package com.kwchina.work.reform.service;

import com.kwchina.core.common.service.BasicManager;
import com.kwchina.work.reform.entity.ChildCategory;

import java.util.List;

public interface ChildCategoryManager extends BasicManager{
	public ChildCategory getChildCategoryByCategoryId(Integer categoryId);
	public List<ChildCategory> getChildCategoriesByParentId(Integer parentId);
}
