package com.kwchina.work.monthReport.service;

import com.kwchina.core.common.service.BasicManager;
import com.kwchina.work.monthReport.entity.Issue;

public interface IssueManage extends BasicManager {
    Issue getIssue(String year,Integer categoryId,String month);
}
