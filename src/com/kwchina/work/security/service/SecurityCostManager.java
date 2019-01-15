package com.kwchina.work.security.service;

import java.util.List;

import com.kwchina.core.common.service.BasicManager;
import com.kwchina.work.security.entity.SecurityCost;

public interface SecurityCostManager extends BasicManager{
	public List<SecurityCost> getYearCosts(int dataYear);
	
	public SecurityCost getInstance(int dataYear,int month,int costType);
}
