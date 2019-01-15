package com.kwchina.work.errorWork.service.impl;

import com.kwchina.core.base.service.DepartmentManager;
import com.kwchina.core.common.service.BasicManagerImpl;
import com.kwchina.core.util.string.StringUtil;
import com.kwchina.work.errorWork.dao.ErrorWorkDAO;
import com.kwchina.work.errorWork.entity.ErrorWork;
import com.kwchina.work.errorWork.enums.ErrorTypeEnum;
import com.kwchina.work.errorWork.service.ErrorWorkManager;
import com.kwchina.work.errorWork.vo.ErrorWorkVO;
import com.kwchina.work.reform.service.ChildCategoryManager;
import com.kwchina.work.util.EnumUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Service("errorWorkManager")
public class ErrorWorkManagerImpl extends BasicManagerImpl<ErrorWork> implements ErrorWorkManager {

    private ErrorWorkDAO errorWorkDAO;

    @Autowired
    public void setSystemErrorWorkDAO(ErrorWorkDAO errorWorkDAO) {
        this.errorWorkDAO = errorWorkDAO;
        super.setDao(errorWorkDAO);
    }

    @Autowired
    private ChildCategoryManager childCategoryManager;

    @Autowired
    private DepartmentManager departmentManager;

    @Override
    public ErrorWorkVO getVoById(Integer errorWorkId) {
        ErrorWorkVO vo = new ErrorWorkVO();
        try {
            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            //根据itemId得到信息
            ErrorWork errorWork = get(Integer.valueOf(errorWorkId));
            //能进来说明已读过安全说明
            vo.setErrorWorkId(errorWorkId);
            if (errorWork.getCheckTime() != null) {
                vo.setCheckTime(sf.format(errorWork.getCheckTime()));
            }
            if (errorWork.getExecuter() != null) {
                vo.setExecuterId(errorWork.getExecuter().getUserId());
                vo.setExecuterName(errorWork.getExecuter().getName());
            }
            if (errorWork.getDepartment1() != null) {
                vo.setDutyDept1Name(errorWork.getDepartment1().getDepartmentName());
            }
            if (errorWork.getDepartment2() != null) {
                vo.setDutyDept2Name(errorWork.getDepartment2().getDepartmentName());
            }
            vo.setTaskId(errorWork.getTaskId());
            vo.setErrorTitle(errorWork.getTitle());
            if (errorWork.getArea() != null) {
                vo.setAreaName(errorWork.getArea().getAreaName());
                vo.setAreaId(errorWork.getArea().getAreaId());
            }
            vo.setErrorType(EnumUtil.getByCode(errorWork.getErrorType(), ErrorTypeEnum.class).getMsg());
            vo.setErrorInfo(errorWork.getErrorInfo());
            List<String> eAtach = new ArrayList<>();
            if (StringUtil.isNotEmpty(errorWork.getErrorAttachs())) {
                for (int i = 0; i < errorWork.getErrorAttachs().split(";").length; i++) {
                    String s = errorWork.getErrorAttachs().split(";")[i];
                    eAtach.add(s.split("\\|")[0]);
                }
            }
            vo.setErrorAttachs(eAtach);
            List<String> mAtach = new ArrayList<>();
            if (StringUtil.isNotEmpty(errorWork.getMemoAttachs())) {
                for (int i = 0; i < errorWork.getMemoAttachs().split(";").length; i++) {
                    String s = errorWork.getMemoAttachs().split(";")[i];
                    mAtach.add(s.split("\\|")[0]);
                }
            }
            vo.setMemoAttachs(mAtach);
            vo.setMemo(errorWork.getMemo());
            if (errorWork.getResult() != null) {
                vo.setResult(errorWork.getResult().equals(1) ? "正常" : "异常");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return vo;
    }
}
