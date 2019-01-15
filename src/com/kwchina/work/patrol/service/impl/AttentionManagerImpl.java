package com.kwchina.work.patrol.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kwchina.core.common.service.BasicManagerImpl;
import com.kwchina.work.patrol.dao.AttentionDAO;
import com.kwchina.work.patrol.entity.Attention;
import com.kwchina.work.patrol.service.AttentionManager;

@Service("attentionManager")
public class AttentionManagerImpl extends BasicManagerImpl<Attention> implements AttentionManager{

	private AttentionDAO attentionDAO;

	@Autowired
	public void setSystemAttentionDAO(AttentionDAO attentionDAO) {
		this.attentionDAO = attentionDAO;
		super.setDao(attentionDAO);
	}
	
	
}
