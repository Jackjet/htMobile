package com.kwchina.work.monthReport.service.impl;

import com.kwchina.core.common.service.BasicManagerImpl;
import com.kwchina.core.util.string.StringUtil;
import com.kwchina.work.monthReport.dao.FocusDAO;
import com.kwchina.work.monthReport.dao.IssueDAO;
import com.kwchina.work.monthReport.entity.Focus;
import com.kwchina.work.monthReport.entity.Issue;
import com.kwchina.work.monthReport.service.FocusManage;
import com.kwchina.work.monthReport.vo.FocusVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("focusManage")
public class FocusManageImpl extends BasicManagerImpl<Focus> implements FocusManage {


    private FocusDAO focusDAO;

    @Autowired
    public void setSystemAccidentDAO(FocusDAO focusDAO) {
        this.focusDAO = focusDAO;
        super.setDao(focusDAO);
    }


    @Override
    public Focus getFocus(String time, Integer type) {
        String realTime=time+"-01";
        String hql = "from Focus where valid=1 and type=" + type + " and recordTime='" + realTime + "'";
        List<Focus> result = getResultByQueryString(hql);
        if (result != null && result.size() > 0) {
            return result.get(0);
        } else {
            return null;
        }
    }

    @Override
    public FocusVO getVoById(Integer focusId) {
        FocusVO focusVO = new FocusVO();
        Focus focus = get(focusId);
        SimpleDateFormat ymsf = new SimpleDateFormat("yyyy-MM");
        Map<String, String> map = new HashMap<>();
        if (StringUtil.isNotEmpty(focus.getContent())) {
            String[] split = focus.getContent().split("&#&");
            for (int i = 0; i < split.length; i++) {
                map.put(split[i].split("&%&")[0], split[i].split("&%&")[1]);
            }
        }
        focusVO.setRecords(map);
        focusVO.setFocusId(focus.getFocusId());
        focusVO.setTime(ymsf.format(focus.getRecordTime()));
        List<String> arrAtach = new ArrayList<>();
        if (StringUtil.isNotEmpty(focus.getAttach())) {
            for (int i = 0; i < focus.getAttach().split(";").length; i++) {
                String s = focus.getAttach().split(";")[i];
                arrAtach.add(s.split("\\|")[0]);
            }
        }
        focusVO.setAttaches(arrAtach);
        return focusVO;
    }
}
