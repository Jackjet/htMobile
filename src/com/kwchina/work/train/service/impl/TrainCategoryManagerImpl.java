package com.kwchina.work.train.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kwchina.core.common.service.BasicManagerImpl;
import com.kwchina.work.train.dao.TrainCategoryDAO;
import com.kwchina.work.train.entity.TrainCategory;
import com.kwchina.work.train.service.TrainCategoryManager;

@Service("trainCategoryManager")
public class TrainCategoryManagerImpl extends BasicManagerImpl<TrainCategory> implements TrainCategoryManager{

	private TrainCategoryDAO trainCategoryDAO;

	@Autowired
	public void setSystemTrainCategoryDAO(TrainCategoryDAO trainCategoryDAO) {
		this.trainCategoryDAO = trainCategoryDAO;
		super.setDao(trainCategoryDAO);
	}


	@Override
	public List<TrainCategory> getAllCategories(int valid) {
		List<TrainCategory> list = new ArrayList<TrainCategory>();
		String hql = "from TrainCategory category where category.valid = " + valid + " order by orderNo";
		list = getResultByQueryString(hql);

		return list;
	}

}
