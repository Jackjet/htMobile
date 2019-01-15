package com.kwchina.work.reform.controller;

import com.kwchina.core.base.service.DepartmentManager;
import com.kwchina.core.base.service.UserManager;
import com.kwchina.core.common.controller.BasicController;
import com.kwchina.core.common.page.PageList;
import com.kwchina.core.common.page.Pages;
import com.kwchina.core.sys.CoreConstant;
import com.kwchina.core.util.ExcelObject;
import com.kwchina.core.util.ExcelOperate;
import com.kwchina.core.util.string.StringUtil;
import com.kwchina.work.reform.entity.ReformDetail;
import com.kwchina.work.reform.service.ChildCategoryManager;
import com.kwchina.work.reform.service.ReformManager;
import com.kwchina.work.reform.vo.ReformVo;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/DailyReport.htm")
public class DailyReportController extends BasicController {
    Logger logger = Logger.getLogger(DailyReportController.class);
    @Resource
    private ReformManager reformManager;
    @Resource
    private DepartmentManager departmentManager;
    @Resource
    private ChildCategoryManager childCategoryManager;
    @Resource
    private UserManager userManager;

    @RequestMapping(params = "method=dailyReport")
    public String dailyReport(ModelMap modelMap, HttpServletRequest request) {
        List<ReformDetail> details = new ArrayList<>();
        List<ReformVo> detailVos = new ArrayList<>();
        int errorCount;
        int completeCount = 0;

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
            modelMap.addAttribute("category", this.childCategoryManager.get(Integer.parseInt(category)));
        }
        if (StringUtil.isNotEmpty(findUserName)) {
            modelMap.addAttribute("findUserName", findUserName);
        }
        List allCategory = this.childCategoryManager.getAll();
        modelMap.addAttribute("_Category", allCategory);

        String userHql = "from User where valid=1 and reformFinder=1";
        List result = this.userManager.getResultByQueryString(userHql);
        modelMap.addAttribute("findUsers", result);

        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String hql = " from ReformDetail detail where valid=1 and findTime >= '" + beginTime + " 08:00:00 'and findTime <= '" + endTime + " 08:00:00' order by findTime";
        @SuppressWarnings("unchecked")
        List<ReformDetail> errorList = this.reformManager.getResultByQueryString(hql);
        //得到所有大项（按顺序）
        for (ReformDetail tmpDetail : errorList) {
            if (StringUtil.isNotEmpty(category)) {
                if (category.equals(tmpDetail.getErrorTypeId().toString())) {
                    if (StringUtil.isNotEmpty(dept)) {
                        if (dept.equals(tmpDetail.getDepartment().getDepartmentName())) {
                            if (StringUtil.isNotEmpty(findUserName) && findUserName.equals(tmpDetail.getFindUser().getName())) {
                                details.add(tmpDetail);
                            } else if (!StringUtil.isNotEmpty(findUserName)) {
                                details.add(tmpDetail);
                            }
                        }

                    }else if(!StringUtil.isNotEmpty(dept)){
                        if (StringUtil.isNotEmpty(findUserName) && findUserName.equals(tmpDetail.getFindUser().getName())) {
                            details.add(tmpDetail);
                        } else if (!StringUtil.isNotEmpty(findUserName)) {
                            details.add(tmpDetail);
                        }
                    }
                }
            }else if(!StringUtil.isNotEmpty(category)){
                if (StringUtil.isNotEmpty(dept)) {
                    if (dept.equals(tmpDetail.getDepartment().getDepartmentName())) {
                        if (StringUtil.isNotEmpty(findUserName) && findUserName.equals(tmpDetail.getFindUser().getName())) {
                            details.add(tmpDetail);
                        } else if (!StringUtil.isNotEmpty(findUserName)) {
                            details.add(tmpDetail);
                        }
                    }

                }else if(!StringUtil.isNotEmpty(dept)){
                    if (StringUtil.isNotEmpty(findUserName) && findUserName.equals(tmpDetail.getFindUser().getName())) {
                        details.add(tmpDetail);
                    } else if (!StringUtil.isNotEmpty(findUserName)) {
                        details.add(tmpDetail);
                    }
                }
            }
        }
            for (ReformDetail reform : details) {
                if (reform.getReformStatus() == 4) {
                    completeCount++;
                }
            }
            errorCount = details.size();
            for (ReformDetail reformDetail : details) {
                ReformVo vo = this.reformManager.getReformDetail(reformDetail.getReformId());
                detailVos.add(vo);

            }
            modelMap.addAttribute("completeCount", completeCount);
            modelMap.addAttribute("errorCount", errorCount);
            modelMap.addAttribute("details", detailVos);

            return "/reform/dailyReport";

        }

        @RequestMapping(params = "method=statistic")
        public void statistic (HttpServletResponse response, HttpServletRequest request){

            List<ReformVo> details = new ArrayList<>();
            int errorCount = 0;
            int completeCount = 0;
            JSONObject jObject = new JSONObject();
            String message = "";
            boolean success = true;
            try {
                //参数：
                String beginTime = request.getParameter("beginDate");
                String endTime = request.getParameter("endDate");
                SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String hql = " from ReformDetail detail where valid=1 and findTime >= '" + beginTime + "' and findTime <= '" + endTime + "' order by findTime";
                List<ReformDetail> list = this.reformManager.getResultByQueryString(hql);

                for (ReformDetail reform : list) {
                    ReformVo reformVo = this.reformManager.getReformDetail(reform.getReformId());
                    if (reform.getReformStatus() == 4) {
                        completeCount++;
                    }
                    details.add(reformVo);
                }
                errorCount = details.size();

                jObject.put("completeCount", completeCount);
                jObject.put("errorCount", errorCount);
                jObject.put("details", details);
                jObject.put("success", success);
                jObject.put("message", message);

            } catch (Exception e) {
                message = "后台出错,请重试";
                success = false;
                e.printStackTrace();
                jObject.put("success", success);
                jObject.put("message", message);
            }
            try {
                response.getWriter().print(jObject);
            } catch (IOException e) {

            }
        }

        @RequestMapping(params = "method=detail")
        public String detail (HttpServletRequest request, ModelMap modelMap){
            String Id = request.getParameter("reformId");
            int reformId = Integer.parseInt(Id);
            ReformVo reformVo = this.reformManager.getReformDetail(reformId);
            modelMap.addAttribute("reformDetail", reformVo);
            return "/reform/detail";
        }

        @RequestMapping(params = "method=edit")
        public String edit (HttpServletRequest request, ModelMap modelMap){
            String Id = request.getParameter("reformId");
            int reformId = Integer.parseInt(Id);
            ReformVo reformVo = this.reformManager.getReformDetail(reformId);
            modelMap.addAttribute("reformDetail", reformVo);
            return "/reform/editDetail";
        }
        @RequestMapping(params = "method=delete")
        @ResponseBody
        public String delete (HttpServletRequest request){
            String Id = request.getParameter("reformId");
            ReformDetail reformDetail = (ReformDetail) this.reformManager.get(Integer.parseInt(Id));
            reformDetail.setValid(false);
            this.reformManager.save(reformDetail);
            return "success";
        }
    }