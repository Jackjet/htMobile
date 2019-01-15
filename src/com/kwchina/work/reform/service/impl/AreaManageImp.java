package com.kwchina.work.reform.service.impl;

import com.kwchina.core.common.service.BasicManagerImpl;
import com.kwchina.work.reform.dao.AreaDao;
import com.kwchina.work.reform.entity.ReformArea;
import com.kwchina.work.reform.service.AreaManage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("areaManage")
public class AreaManageImp extends BasicManagerImpl<ReformArea> implements AreaManage {


    private AreaDao areaDao;

    @Autowired
    public void setSystemAreaDao(AreaDao areaDao) {
        this.areaDao = areaDao;
        super.setDao(areaDao);
    }

    public String getAreaNameById(Integer areaId){
        String areaName="";
        String hql="from ReformArea area where area.areaId="+areaId;
        List<ReformArea> areas = this.areaDao.getResultByQueryString(hql);
        if(areas.size()>0){
            areaName=areas.get(0).getAreaName();
        }
        return areaName;
    }
}
