package com.kwchina.work.monthReport.service.impl;

import com.kwchina.core.common.service.BasicManagerImpl;
import com.kwchina.core.util.string.StringUtil;
import com.kwchina.work.monthReport.dao.AccidentDAO;
import com.kwchina.work.monthReport.entity.Accident;
import com.kwchina.work.monthReport.service.AccidentManage;
import com.kwchina.work.monthReport.vo.AccidentVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Service("accidentManage")
public class AccidentManageImpl extends BasicManagerImpl<Accident> implements AccidentManage {


    private AccidentDAO accidentDAO;

    @Autowired
    public void setSystemAccidentDAO(AccidentDAO accidentDAO) {
        this.accidentDAO = accidentDAO;
        super.setDao(accidentDAO);
    }


    @Override
    public AccidentVO getAccidentVo(Integer accidentId) {
        AccidentVO vo = new AccidentVO();
        try {
            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            //根据itemId得到信息
            Accident accident = get(accidentId);
            vo.setAccidentId(accidentId);
            if (accident.getAccidentTime() != null) {
                vo.setTime(sf.format(accident.getAccidentTime()));
            }
            if (accident.getDutyDept() != null) {
                vo.setDepartmentId(accident.getDutyDept().getDepartmentId());
                vo.setDepartmentName(accident.getDutyDept().getDepartmentName());

            }
            vo.setAddress(accident.getAccidentAddress());
            vo.setContent(accident.getContent());
            List<String> attaches = new ArrayList<>();
            if (StringUtil.isNotEmpty(accident.getAccidentAttach())) {
                for (int i = 0; i < accident.getAccidentAttach().split(";").length; i++) {
                    String s = accident.getAccidentAttach().split(";")[i];
                    attaches.add(s.split("\\|")[0]);
                }
            }
            vo.setAttaches(attaches);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return vo;
    }
}
