package com.kwchina.work.train.service;

import java.util.List;

import com.kwchina.core.common.service.BasicManager;
import com.kwchina.work.train.entity.Train;
import com.kwchina.work.train.vo.TrainVo;

public interface TrainManager extends BasicManager{

	public TrainVo transToVo(Train train);
}
