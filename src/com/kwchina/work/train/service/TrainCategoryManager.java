package com.kwchina.work.train.service;

import java.util.List;

import com.kwchina.core.common.service.BasicManager;
import com.kwchina.work.train.entity.TrainCategory;

public interface TrainCategoryManager extends BasicManager{
	public List<TrainCategory> getAllCategories(int valid);
}
