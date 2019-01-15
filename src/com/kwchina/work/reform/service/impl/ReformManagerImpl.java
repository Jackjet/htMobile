package com.kwchina.work.reform.service.impl;

import com.kwchina.core.base.service.DepartmentManager;
import com.kwchina.core.common.service.BasicManagerImpl;
import com.kwchina.core.util.string.StringUtil;
import com.kwchina.work.reform.dao.ReformDAO;
import com.kwchina.work.reform.entity.ReformDetail;
import com.kwchina.work.reform.entity.Schedule;
import com.kwchina.work.reform.enums.ReformStatusEnum;
import com.kwchina.work.reform.service.AreaManage;
import com.kwchina.work.reform.service.ChildCategoryManager;
import com.kwchina.work.reform.service.ReformManager;
import com.kwchina.work.reform.vo.ReformVo;
import com.kwchina.work.reform.vo.ScheduleVo;
import com.kwchina.work.util.EnumUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Service("reformManager")
public class ReformManagerImpl extends BasicManagerImpl<ReformDetail> implements ReformManager {

    private ReformDAO reformDAO;
    @Autowired
    public void setSystemBigCategoryDAO(ReformDAO reformDAO) {
        this.reformDAO = reformDAO;
        super.setDao(reformDAO);
    }
    @Autowired
    private AreaManage areaManage;

    @Autowired
    private ChildCategoryManager childCategoryManager;

    @Autowired
    private DepartmentManager departmentManager;
    @Override
    public ReformDetail getReformDetailBytaskId(String taskId) {
        ReformDetail reformDetail = new ReformDetail();
        String sql = "from ReformDetail reform where reform.taskId='" + taskId + "'";
        List<ReformDetail> list = this.reformDAO.getResultByQueryString(sql);
        if (list != null && list.size() > 0) {
            reformDetail = list.get(0);
        }
        return reformDetail;
    }
    @Override
    public ReformVo getReformDetail(Integer reformId) {
        ReformVo vo = new ReformVo();
        try {
            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            //根据itemId得到信息
            ReformDetail reformDetail = get(reformId);
            //能进来说明已读过安全说明
            vo.setReformId(reformDetail.getReformId());
            vo.setMorningReport(reformDetail.isMorningReport());
            if (reformDetail.getFindTime() != null) {
                vo.setFindTime(sf.format(reformDetail.getFindTime()));
            }
            if (reformDetail.getReformTime() != null) {
                vo.setReformTime(sf.format(reformDetail.getReformTime()));
            }
            if (reformDetail.getFindUser() != null) {
                vo.setFindUserId(reformDetail.getFindUser().getUserId());
                vo.setFindUserName(reformDetail.getFindUser().getName());
            }
            if (reformDetail.getFinishTime()!=null) {
                vo.setFinishTime(sf.format(reformDetail.getFinishTime()));
            }
            if(reformDetail.getDutyPerson()!=null){
                vo.setReformUserId(reformDetail.getDutyPerson().getUserId());
                vo.setReformUserName(reformDetail.getDutyPerson().getName());
            }
            vo.setTaskId(reformDetail.getTaskId());
            List<ScheduleVo> scheduleVos = new ArrayList<>();
            for (Schedule schedule : reformDetail.getScheduleList()) {
                ScheduleVo sVo = new ScheduleVo();
                sVo.setExecuterId(schedule.getReformUser().getUserId());
                sVo.setExecuterName(schedule.getReformUser().getName());
                List<String> arrAttach=new ArrayList<>();
                for (int i=0;i<schedule.getReformAttach().split(";").length;i++){
                    String s = (schedule.getReformAttach().split(";")[i].split("\\|"))[0];
                    if(StringUtil.isNotEmpty(s)){
                        arrAttach.add(s);
                    }
                }
                sVo.setReformAttachs(arrAttach);
                sVo.setReformInfo(schedule.getReformInfo());
                sVo.setReformStatus(schedule.getReformStatus());
                sVo.setScheduleId(schedule.getScheduleId());
                sVo.setReformTime(sf.format(schedule.getReformTime()));
                scheduleVos.add(sVo);
            }
            vo.setScheduleVos(scheduleVos);
            vo.setErrorTitle(reformDetail.getErrorTitle());
            vo.setReformStatus(reformDetail.getReformStatus());
            vo.setAreaName(areaManage.getAreaNameById(reformDetail.getErrorAreaId()));
            vo.setAreaId(reformDetail.getErrorAreaId());
            vo.setErrorType(childCategoryManager.getChildCategoryByCategoryId(reformDetail.getErrorTypeId()).getCategoryName());
            vo.setErrorTypeId(reformDetail.getErrorTypeId());
            vo.setErrorInfo(reformDetail.getErrorContent());
            vo.setStatusStr(EnumUtil.getByCode(reformDetail.getReformStatus(),ReformStatusEnum.class).getMsg());
            List<String> arrAtach=new ArrayList<>();
            for (int i=0;i<reformDetail.getErrorAttach().split(";").length;i++){
                String s = reformDetail.getErrorAttach().split(";")[i];
                arrAtach.add(s.split("\\|")[0]);
            }
            vo.setErrorAttachs(arrAtach);
            vo.setDutyDeptName(reformDetail.getDepartment().getDepartmentName());
            if (reformDetail.getSuperviseDeptId()!=null){
                vo.setSuperviseDeptName(this.departmentManager.getDepartment(reformDetail.getSuperviseDeptId()).getDepartmentName());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return vo;

    }

}
