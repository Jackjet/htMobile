package com.kwchina.work.commonWork.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kwchina.core.common.service.BasicManagerImpl;
import com.kwchina.work.commonWork.dao.XWorkReplyDAO;
import com.kwchina.work.commonWork.entity.XWorkReply;
import com.kwchina.work.commonWork.service.XWorkReplyManager;

@Service("xWorkReplyManager")
public class XWorkReplyManagerImpl extends BasicManagerImpl<XWorkReply> implements XWorkReplyManager{

	private XWorkReplyDAO xWorkReplyDAO;

	@Autowired
	public void setSystemXWorkReplyDAO(XWorkReplyDAO xWorkReplyDAO) {
		this.xWorkReplyDAO = xWorkReplyDAO;
		super.setDao(xWorkReplyDAO);
	}
	

	public List<XWorkReply> getReplysByWorkId(Integer workId){
		List<XWorkReply> list = new ArrayList<XWorkReply>();
		String hql = " from XWorkReply reply where reply.workId = '" + workId + "' order by reply.operateTime desc";
		list = this.getResultByQueryString(hql);
		return list;
		
	}
}
