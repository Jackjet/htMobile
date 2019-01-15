package com.kwchina.work.monthReport.service.impl;

import com.kwchina.core.common.service.BasicManagerImpl;
import com.kwchina.work.monthReport.dao.AccidentDAO;
import com.kwchina.work.monthReport.dao.IssueDAO;
import com.kwchina.work.monthReport.entity.Accident;
import com.kwchina.work.monthReport.entity.Issue;
import com.kwchina.work.monthReport.service.AccidentManage;
import com.kwchina.work.monthReport.service.IssueManage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("issueManage")
public class IssueManageImpl extends BasicManagerImpl<Issue> implements IssueManage {


    private IssueDAO issueDAO;

    @Autowired
    public void setSystemAccidentDAO(IssueDAO issueDAO) {
        this.issueDAO = issueDAO;
        super.setDao(issueDAO);
    }


    @Override
    public Issue getIssue(String year, Integer categoryId, String month) {
        String hql="from Issue where year= '"+year+"' and category.categoryId= "+categoryId+" and month= '"+month+"'";
        List<Issue> result = getResultByQueryString(hql);
        if(result!=null&&result.size()>0){
            return result.get(0);
        }else {
            return null;
        }
    }
}
