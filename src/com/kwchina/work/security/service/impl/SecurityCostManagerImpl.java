package com.kwchina.work.security.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kwchina.core.common.service.BasicManagerImpl;
import com.kwchina.work.security.dao.SecurityCostDAO;
import com.kwchina.work.security.entity.SecurityCost;
import com.kwchina.work.security.service.SecurityCostManager;

@Service("securityCostManager")
public class SecurityCostManagerImpl extends BasicManagerImpl<SecurityCost> implements SecurityCostManager{

	private SecurityCostDAO securityCostDAO;

	@Autowired
	public void setSystemSecurityCostDAO(SecurityCostDAO securityCostDAO) {
		this.securityCostDAO = securityCostDAO;
		super.setDao(securityCostDAO);
	}
	
	public List<SecurityCost> getYearCosts(int dataYear){
		List<SecurityCost> list = new ArrayList<SecurityCost>();
		String hql = "from SecurityCost cost where cost.dataYear = " + dataYear + " order by costType,dataMonth";
		list = getResultByQueryString(hql);
		
		return list;
	}
	

	public SecurityCost getInstance(int dataYear,int month,int costType){
		SecurityCost cost = new SecurityCost();
		String hql = " from SecurityCost cost where 1=1 ";
		if(dataYear > 0){
			hql += " and cost.dataYear = " + dataYear;
		}
		if(month > 0){
			hql += " and cost.dataMonth = " + month;
		}
		if(costType > 0){
			hql += " and cost.costType = " + costType; 
		}
		
		hql += " order by cost.costId desc";
		
		List<SecurityCost> list = getResultByQueryString(hql);
		if(list != null && list.size() > 0){
			cost = list.get(0);
		}
		
		return cost;
	}
}
