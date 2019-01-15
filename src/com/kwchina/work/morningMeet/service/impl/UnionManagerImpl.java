package com.kwchina.work.morningMeet.service.impl;

import com.kwchina.core.common.service.BasicManagerImpl;
import com.kwchina.work.morningMeet.dao.UnionDAO;
import com.kwchina.work.morningMeet.entity.UnionCheck;
import com.kwchina.work.morningMeet.service.UnionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("unionManager")
public class UnionManagerImpl extends BasicManagerImpl<UnionCheck> implements UnionManager {

    private UnionDAO unionDAO;

    @Autowired
    public void setSystemErrorWorkDAO(UnionDAO unionDAO) {
        this.unionDAO = unionDAO;
        super.setDao(unionDAO);
    }
}
