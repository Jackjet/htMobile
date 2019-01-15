package com.kwchina.work.errorWork.controller;

import com.kwchina.core.base.entity.Department;
import com.kwchina.core.base.entity.User;
import com.kwchina.core.base.service.DepartmentManager;
import com.kwchina.core.base.service.UserManager;
import com.kwchina.core.common.controller.BasicController;
import com.kwchina.core.sys.CoreConstant;
import com.kwchina.core.util.string.StringUtil;
import com.kwchina.work.errorWork.entity.ErrorWork;
import com.kwchina.work.errorWork.service.ErrorWorkManager;
import com.kwchina.work.errorWork.utils.convert.Convert;
import com.kwchina.work.errorWork.vo.ErrorWorkVO;
import com.kwchina.work.reform.entity.ReformArea;
import com.kwchina.work.reform.entity.ReformDetail;
import com.kwchina.work.reform.entity.Schedule;
import com.kwchina.work.reform.enums.ReformStatusEnum;
import com.kwchina.work.reform.service.AreaManage;
import com.kwchina.work.reform.service.ChildCategoryManager;
import com.kwchina.work.reform.service.ReformManager;
import com.kwchina.work.sys.LoginConfirm;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.support.DefaultMultipartHttpServletRequest;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("/errorWork.htm")
public class ErrorWorkController extends BasicController {
    Logger logger = Logger.getLogger(ErrorWorkController.class);

    @Resource
    private UserManager userManager;

    @Resource
    private DepartmentManager departmentManager;

    @Resource
    private ErrorWorkManager errorWorkManager;

    @Resource
    private ReformManager reformManager;
    @Resource
    private AreaManage areaManage;
    @Resource
    private ChildCategoryManager childCategoryManager;

    /**
     * 巡检上报
     *
     * @param request
     * @param response
     * @param
     */
    @RequestMapping(params = "method=newErrorWork")
    @Transactional
    public void newErrorWork(HttpServletRequest request,
                             HttpServletResponse response, DefaultMultipartHttpServletRequest multipartRequest) throws Exception {
        String message = "";
        boolean success = true;

        JSONObject jsonObj = new JSONObject();
        // 用户
        String token = request.getParameter("token");
        User user = LoginConfirm.loginUserMap.get(token);

        // 参数：
        String errorTitle = request.getParameter("title");
        String errorTypeId = request.getParameter("typeId");
        String areaId = request.getParameter("areaId");
        String executerId = request.getParameter("executerId"); // 执a行人
        String result = request.getParameter("result");
        String errorInfo = request.getParameter("errorInfo"); //
        String memo = request.getParameter("memo");
        String taskId = request.getParameter("taskId");
        String department1 = request.getParameter("department1");
        String department2 = request.getParameter("department2");
        String checkTime = request.getParameter("checkTime");

        if (user != null) {
            executerId = StringUtil.isNotEmpty(executerId) ? executerId : user
                    .getUserId().toString();
            taskId = StringUtil.isNotEmpty(taskId) ? taskId : UUID.randomUUID()
                    .toString().replaceAll("-", "");
            ErrorWork errorWork = new ErrorWork();
            errorWork.setValid(true);
            errorWork.setCheckTime(Convert.stringToDate(checkTime, "yyyy-MM-dd HH:mm:ss"));
            if (StringUtil.isNotEmpty(department1)) {
                errorWork.setDepartment1((Department) departmentManager.getDepartmentByDeparmentId(Integer.parseInt(department1)));
            }
            if (StringUtil.isNotEmpty(department2)) {
                errorWork.setDepartment2((Department) departmentManager.getDepartmentByDeparmentId(Integer.parseInt(department2)));
            }
            Map<String, String> errorAttachs = uploadAttachment2(multipartRequest, "errorWork");
            String erroAttach = "";
            if (errorAttachs != null && errorAttachs.size() > 0) {
                for (String key : errorAttachs.keySet()) {
                    if (StringUtil.isNotEmpty(key) && key.startsWith("error_")) {
                        erroAttach = erroAttach + errorAttachs.get(key) + ";";
                    }
                }
                errorWork.setErrorAttachs(erroAttach);
            }

            errorWork.setErrorInfo(errorInfo);
            errorWork.setExecuter(userManager.getUserByUserId(Integer.parseInt(executerId)));

            errorWork.setMemo(memo);

            String memoAttach = "";
            if (errorAttachs != null && errorAttachs.size() > 0) {
                for (String key : errorAttachs.keySet()) {
                    if (StringUtil.isNotEmpty(key) && key.startsWith("memo_")) {
                        memoAttach = memoAttach + errorAttachs.get(key) + ";";
                    }
                }
                errorWork.setMemoAttachs(memoAttach);
            }
            errorWork.setResult(result);
            errorWork.setTaskId(taskId);
            errorWork.setTitle(errorTitle);
            if (StringUtil.isNotEmpty(errorTypeId)) {
                errorWork.setErrorType(Integer.parseInt(errorTypeId));
            }
            if (StringUtil.isNotEmpty(areaId)) {
                errorWork.setArea((ReformArea) areaManage.get(Integer.parseInt(areaId)));
            }

//			if(taskType!=null&&taskId!=null){
//				if(taskType==1){
//					ReformDetail reformDetail=new ReformDetail();
//					reformDetail.setDepartment(departmentManager
//							.getDepartmentByName(department));
//					reformDetail.setErrorContent(errorInfo);
//					reformDetail.setErrorTitle(errorTitle);
//					reformDetail.setErrorTypeId(Integer.parseInt(reformTypeId));
//					reformDetail.setErrorAreaId(Integer.parseInt(reformAreaId));
//					reformDetail.setDutyPerson(userManager.getUserByUserName(dutyPersonName));
//					reformDetail.setFindTime(Convert.stringToDate(checkTime, "yyyy-MM-dd HH:mm:ss"));
//					reformDetail.setFindUser(userManager.getUserByUserId(Integer
//							.parseInt(executerId)));
//					reformDetail.setTaskId(taskId);
//					List<Schedule> scheduleList = new ArrayList<Schedule>();
//					Schedule schedule=new Schedule();
//					if(dutyPersonName!=null&&!dutyPersonName.equals("")){
//						reformDetail.setReformStatus(ReformStatusEnum.IN_RECTIFICATION.getCode());
//						schedule.setReformUser(userManager.getUserByUserName(dutyPersonName));
//						schedule.setReformStatus(ReformStatusEnum.IN_RECTIFICATION.getCode());
//					}else{
//						reformDetail.setReformStatus(ReformStatusEnum.TO_BE_RETIFIED.getCode());
//						schedule.setReformStatus(ReformStatusEnum.TO_BE_RETIFIED.getCode());
//					}
//					schedule.setReformInfo(errorInfo);
//					schedule.setReformAttach(erroAttach);
//					schedule.setReformDetail(reformDetail);
//					scheduleList.add(schedule);
//					reformDetail.setScheduleList(scheduleList);
//					this.reformManager.save(reformDetail);
//				}else if(taskType==2){
//					PeccancyDetail peccancyDetail=new PeccancyDetail();
//					peccancyDetail.setAttachment(erroAttach);
//					peccancyDetail.setContent(memo);
//					peccancyDetail.setDutyPersonName(dutyPersonName);
//					peccancyDetail.setDepartment(departmentManager
//							.getDepartmentByName(department));
//					peccancyDetail.setMarkTime(Convert.stringToDate(checkTime, "yyyy-MM-dd HH:mm:ss"));
//					peccancyDetail.setOffender(userManager.getUserByUserId(Integer
//							.parseInt(executerId)));
//					peccancyDetail.setValid(true);
//					peccancyDetailManager.save(peccancyDetail);
//				}
//			}
            this.errorWorkManager.save(errorWork);
            success = true;
        }

        jsonObj.put("success", success);
        jsonObj.put("message", message);

        // 设置字符编码
        response.setContentType(CoreConstant.CONTENT_TYPE);
        response.getWriter().print(jsonObj);

    }

    //派发任务
    @RequestMapping(params = "method=dispatch")
    @Transactional
    public void disparch(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String dutyPerson = request.getParameter("dutyPerson");
        String reformId = request.getParameter("reformId");
        ReformDetail reformDetail = (ReformDetail) reformManager.get(Integer.parseInt(reformId));
        List<Schedule> scheduleList = reformDetail.getScheduleList();
        scheduleList.get(0).setReformUser(userManager.getUserByUserName(dutyPerson));
        scheduleList.get(0).setReformStatus(ReformStatusEnum.IN_RECTIFICATION.getCode());
    }

    @RequestMapping(params = "method=dailyReport")
    public String dailyReport(ModelMap modelMap, HttpServletRequest request) {
        List<ErrorWork> details = new ArrayList<>();
        List<ErrorWorkVO> detailVos = new ArrayList<>();
        int errorCount;
        //参数：
        String beginTime = request.getParameter("beginDate");
        String endTime = request.getParameter("endDate");
        String dept = request.getParameter("dept");
        String category = request.getParameter("category");
        String findUserName = request.getParameter("findUserName");
        Date date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, +1);
        Date time = cal.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        if (!StringUtil.isNotEmpty(beginTime)) {
            beginTime = sdf.format(date);
        }
        if (!StringUtil.isNotEmpty(endTime)) {
            endTime = sdf.format(time);
        }
        modelMap.addAttribute("beginDate", beginTime);
        modelMap.addAttribute("endDate", endTime);
        if (StringUtil.isNotEmpty(dept)) {
            modelMap.addAttribute("dept", this.departmentManager.getDepartmentByName(dept));
        }
        if (StringUtil.isNotEmpty(category)) {
            modelMap.addAttribute("category", category);
        }
        if (StringUtil.isNotEmpty(findUserName)) {
            modelMap.addAttribute("findUserName", findUserName);
        }
        Map map = new HashMap();
        map.put(1, "部门自查");
        map.put(2, "安全巡检");
        map.put(3, "专项检查");
        map.put(4, "领导巡查");
        modelMap.addAttribute("_Category", map);

        String userHql = "from User where valid=1 and reformFinder=1";
        List result = this.userManager.getResultByQueryString(userHql);
        modelMap.addAttribute("findUsers", result);

        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String hql = " from ErrorWork detail where valid=1 and checkTime >= '" + beginTime + " 08:00:00 'and checkTime <= '" + endTime + " 08:00:00' order by checkTime";
        @SuppressWarnings("unchecked")
        List<ErrorWork> errorList = this.errorWorkManager.getResultByQueryString(hql);
        //得到所有大项（按顺序）
        for (ErrorWork tmpDetail : errorList) {
            if (StringUtil.isNotEmpty(category)) {
                if (category.equals(tmpDetail.getErrorType().toString())) {
                    if (StringUtil.isNotEmpty(dept)) {
                        if (dept.equals(tmpDetail.getDepartment1().getDepartmentName()) || dept.equals(tmpDetail.getDepartment2().getDepartmentName())) {
                            if (StringUtil.isNotEmpty(findUserName) && findUserName.equals(tmpDetail.getExecuter().getName())) {
                                details.add(tmpDetail);
                            } else if (!StringUtil.isNotEmpty(findUserName)) {
                                details.add(tmpDetail);
                            }
                        }

                    } else if (!StringUtil.isNotEmpty(dept)) {
                        if (StringUtil.isNotEmpty(findUserName) && findUserName.equals(tmpDetail.getExecuter().getName())) {
                            details.add(tmpDetail);
                        } else if (!StringUtil.isNotEmpty(findUserName)) {
                            details.add(tmpDetail);
                        }
                    }
                }
            } else if (!StringUtil.isNotEmpty(category)) {
                if (StringUtil.isNotEmpty(dept)) {
                    if (dept.equals(tmpDetail.getDepartment1().getDepartmentName()) || dept.equals(tmpDetail.getDepartment2().getDepartmentName())) {
                        if (StringUtil.isNotEmpty(findUserName) && findUserName.equals(tmpDetail.getExecuter().getName())) {
                            details.add(tmpDetail);
                        } else if (!StringUtil.isNotEmpty(findUserName)) {
                            details.add(tmpDetail);
                        }
                    }

                } else if (!StringUtil.isNotEmpty(dept)) {
                    if (StringUtil.isNotEmpty(findUserName) && findUserName.equals(tmpDetail.getExecuter().getName())) {
                        details.add(tmpDetail);
                    } else if (!StringUtil.isNotEmpty(findUserName)) {
                        details.add(tmpDetail);
                    }
                }
            }

        }
        errorCount = details.size();
        for (ErrorWork errorWork : details) {
            ErrorWorkVO vo = this.errorWorkManager.getVoById(errorWork.getErrorWorkId());
            detailVos.add(vo);
        }
        modelMap.addAttribute("errorCount", errorCount);
        modelMap.addAttribute("details", detailVos);
        return "/errorWork/dailyReport";

    }


    @RequestMapping(params = "method=detail")
    public String detail(HttpServletRequest request, ModelMap modelMap) {
        String Id = request.getParameter("errorWorkId");
        int errorWorkId = Integer.parseInt(Id);
        ErrorWorkVO errorWorkVo = this.errorWorkManager.getVoById(errorWorkId);
        modelMap.addAttribute("errorWork", errorWorkVo);
        return "/errorWork/detail";
    }

    @RequestMapping(params = "method=edit")
    public String edit(HttpServletRequest request, ModelMap modelMap) {
        String Id = request.getParameter("errorWorkId");
        int errorWorkId = Integer.parseInt(Id);
        ErrorWorkVO errorWorkVo = this.errorWorkManager.getVoById(errorWorkId);
        modelMap.addAttribute("errorWork", errorWorkVo);
        return "/errorWork/editDetail";
    }

    /**
     * 隐患编辑
     */
    @RequestMapping(params = "method=saveErrorWork")
    @Transactional
    public String saveErrorWork(ErrorWorkVO vo, ModelMap modelMap, HttpServletRequest request, DefaultMultipartHttpServletRequest multipartRequest) throws Exception {
        ErrorWork errorWork = (ErrorWork) this.errorWorkManager.get(vo.getErrorWorkId());
        errorWork.setErrorInfo(vo.getErrorInfo());
        errorWork.setMemo(vo.getMemo());
        if (StringUtil.isNotEmpty(vo.getDutyDept1Name())) {
            errorWork.setDepartment1(this.departmentManager.getDepartmentByName(vo.getDutyDept1Name()));
        }
        if (StringUtil.isNotEmpty(vo.getDutyDept2Name())) {
            errorWork.setDepartment2(this.departmentManager.getDepartmentByName(vo.getDutyDept2Name()));
        }
        if(vo.getAreaId()!=null){
            errorWork.setArea((ReformArea) this.areaManage.get(vo.getAreaId()));
        }
        String errorAttach = errorWork.getErrorAttachs();
        String memoAttach = errorWork.getMemoAttachs();
        List<String> eAttList = new ArrayList<>();
        List<String> mAttList = new ArrayList<>();
        if (StringUtil.isNotEmpty(errorAttach)) {
            String[] eAttachs = errorAttach.split(";");
            List<String> eList = Arrays.asList(eAttachs);
            eAttList = new ArrayList<>(eList);
        }
        if (StringUtil.isNotEmpty(memoAttach)) {
            String[] mAttachs = memoAttach.split(";");
            List<String> mList = Arrays.asList(mAttachs);
            mAttList = new ArrayList<>(mList);
        }
        String[] eImages = request.getParameterValues("eImage");
        List<String> eResult;
        if (eImages != null && eImages.length > 0) {
            List<String> eList = Arrays.asList(eImages);
            List<String> eImageList = new ArrayList<>(eList);
            eResult = removeAttaach(eAttList, eImageList);
        } else {
            eResult = eAttList;
        }

        String[] mImages = request.getParameterValues("mImage");
        List<String> mResult;
        if (mImages != null && mImages.length > 0) {
            List<String> mList = Arrays.asList(mImages);
            List<String> mImageList = new ArrayList<>(mList);
            mResult = removeAttaach(mAttList, mImageList);
        } else {
            mResult = mAttList;
        }


        String newAttach1 = "";
        for (int i = 0; i < eResult.size(); i++) {
            if (StringUtil.isNotEmpty(eResult.get(i))) {
                newAttach1 += eResult.get(i);
                newAttach1 += ";";
            }
        }
        String newAttach2 = "";
        for (int i = 0; i < mResult.size(); i++) {
            if (StringUtil.isNotEmpty(mResult.get(i))) {
                newAttach2 += mResult.get(i);
                newAttach2 += ";";
            }
        }


        Map<String, String> errorAttachs = uploadAttachment2(multipartRequest, "errorWork");
        if (errorAttachs != null && errorAttachs.size() > 0) {
            for (String key : errorAttachs.keySet()) {
                if (StringUtil.isNotEmpty(key) && key.startsWith("error_")) {
                    newAttach1 = newAttach1 + errorAttachs.get(key) + ";";
                }
            }
        }
        if (errorAttachs != null && errorAttachs.size() > 0) {
            for (String key : errorAttachs.keySet()) {
                if (StringUtil.isNotEmpty(key) && key.startsWith("memo_")) {
                    newAttach2 = newAttach2 + errorAttachs.get(key) + ";";
                }
            }
        }
        errorWork.setErrorAttachs(newAttach1);
        errorWork.setMemoAttachs(newAttach2);
        ErrorWork work = (ErrorWork) this.errorWorkManager.save(errorWork);
        ErrorWorkVO errorWorkVo = this.errorWorkManager.getVoById(work.getErrorWorkId());
        modelMap.addAttribute("errorWork", errorWorkVo);
        return "errorWork/editDetail";
    }

    @RequestMapping(params = "method=delete")
    @ResponseBody
    public String delete(HttpServletRequest request) {
        String Id = request.getParameter("errorWorkId");
        ErrorWork errorWork = (ErrorWork) this.errorWorkManager.get(Integer.parseInt(Id));
        errorWork.setValid(false);
        this.reformManager.save(errorWork);
        return "success";
    }
}
