package com.kwchina.work.patrol.service;

import java.util.List;

import com.kwchina.core.common.service.BasicManager;
import com.kwchina.work.patrol.entity.SmallCategory;

public interface SmallCategoryManager extends BasicManager{
	public SmallCategory getSmallCategoryBySmallId(Integer smallId);
	public List<SmallCategory> getSmallCategoriesByBigId(Integer bigId);
}
