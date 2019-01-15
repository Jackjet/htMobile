package com.kwchina.work.reform.controller;

import com.kwchina.core.base.entity.Department;
import com.kwchina.core.base.entity.User;
import com.kwchina.core.base.service.DepartmentManager;
import com.kwchina.core.base.service.UserManager;
import com.kwchina.core.common.controller.BasicController;
import com.kwchina.core.common.page.PageList;
import com.kwchina.core.common.page.Pages;
import com.kwchina.core.sys.CoreConstant;
import com.kwchina.core.util.DateHelper;
import com.kwchina.core.util.ExcelObject;
import com.kwchina.core.util.ExcelOperate;
import com.kwchina.core.util.json.JSONConvert;
import com.kwchina.core.util.string.StringUtil;
import com.kwchina.work.errorWork.utils.convert.Convert;
import com.kwchina.work.reform.entity.ChildCategory;
import com.kwchina.work.reform.entity.ParentCategory;
import com.kwchina.work.reform.entity.ReformDetail;
import com.kwchina.work.reform.entity.Schedule;
import com.kwchina.work.reform.enums.ReformStatusEnum;
import com.kwchina.work.reform.service.ChildCategoryManager;
import com.kwchina.work.reform.service.ParentCategoryManager;
import com.kwchina.work.reform.service.ReformManager;
import com.kwchina.work.reform.vo.ReformVo;
import com.kwchina.work.reform.vo.YearItemVo;
import com.kwchina.work.reform.vo.YearListVo;
import com.kwchina.work.sys.LoginConfirm;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.support.DefaultMultipartHttpServletRequest;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("/reform.htm")
public class ReformController extends BasicController {
    Logger logger = Logger.getLogger(ReformController.class);
    @Resource
    private ReformManager reformManager;
    @Resource
    private ChildCategoryManager childCategoryManager;
    @Resource
    private ParentCategoryManager parentCategoryManager;
    @Resource
    private DepartmentManager departmentManager;
    @Resource
    private UserManager userManager;

    /***
     *
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(params = "method=getYearReforms")
    public void list(HttpServletRequest request, HttpServletResponse response) throws Exception {
        JSONObject jsonObj = new JSONObject();
        List<Integer> errorCountList = new ArrayList<Integer>();  //隐患数
        List<Integer> reformCountList = new ArrayList<Integer>();  //整改数


        String currentYearStr = request.getParameter("currentYear");
        int thisYear = 0;
        int realYear = 0;
        //默认显示当年
        Date now = new Date();
        SimpleDateFormat sf_y = new SimpleDateFormat("yyyy");
        realYear = Integer.valueOf(sf_y.format(now));

        if (StringUtil.isNotEmpty(currentYearStr)) {

            thisYear = Integer.valueOf(currentYearStr);

        } else {
            thisYear = realYear;
        }
        jsonObj.put("_Year", thisYear);
        jsonObj.put("_RealYear", realYear);

        String departmentIdStr = request.getParameter("departmentId");
        try {
            if (StringUtil.isNotEmpty(departmentIdStr)) {
                int departmentId = Integer.valueOf(departmentIdStr);
                List<ReformDetail> errorList = new ArrayList<ReformDetail>();
                List<ReformDetail> reformList = new ArrayList<ReformDetail>();
                if (departmentId == 0) {
                    String errorHql = " from ReformDetail detail where valid=1 and findTime >= '" + currentYearStr + "-01-01 00:00:00 ' and findTime <= '" + currentYearStr + "-12-31 23:59:59' order by findTime";
                    errorList = this.reformManager.getResultByQueryString(errorHql);

                    String reformHql = " from ReformDetail detail where valid=1 and detail.reformStatus=4 and  findTime >= '" + currentYearStr + "-01-01 00:00:00 ' and findTime <= '" + currentYearStr + "-12-31 23:59:59' order by findTime";
                    reformList = this.reformManager.getResultByQueryString(reformHql);

                } else {

                    String errorHql = " from ReformDetail detail where valid=1 and departmentId = " + departmentId + " and findTime >= '" + currentYearStr + "-01-01 00:00:00 ' and findTime <= '" + currentYearStr + "-12-31 23:59:59' order by findTime";
                    errorList = this.reformManager.getResultByQueryString(errorHql);

                    String reformHql = " from ReformDetail detail where valid=1 and detail.reformStatus=4 and departmentId = " + departmentId + " and  findTime >= '" + currentYearStr + "-01-01 00:00:00 ' and findTime <= '" + currentYearStr + "-12-31 23:59:59' order by findTime";
                    reformList = this.reformManager.getResultByQueryString(reformHql);
                }
                for (int i = 1; i < 13; i++) {
                    int reformCount = 0;
                    int errorCount = 0;

                    String eachMonthStr = thisYear + "-" + (i < 10 ? "0" + i : i) + "-01";
                    //				java.util.Date eachMonthBeginDate = DateHelper.getFirstBeginDateOfMonth(eachMonthStr);
                    Date eachMonthEndDate = DateHelper.getFirstEndDateOfMonth(eachMonthStr);

                    String eachMonthBeginStr = eachMonthStr + " 00:00:00";
                    String eachMonthEndStr = DateHelper.getString(eachMonthEndDate) + " 23:59:59";

                    for (ReformDetail tmpDetail : reformList) {
                        if (tmpDetail.getFindTime().after(Timestamp.valueOf(eachMonthBeginStr)) && tmpDetail.getFindTime().before(Timestamp.valueOf(eachMonthEndStr))) {
                            reformCount += 1;
                        }
                    }

                    for (ReformDetail tmpDetail : errorList) {
                        if (tmpDetail.getFindTime().after(Timestamp.valueOf(eachMonthBeginStr)) && tmpDetail.getFindTime().before(Timestamp.valueOf(eachMonthEndStr))) {
                            errorCount += 1;
                        }
                    }
                    reformCountList.add(reformCount);
                    errorCountList.add(errorCount);
                }
            }


            jsonObj.put("_ReformCountList", reformCountList);
            jsonObj.put("_ErrorCountList", errorCountList);
            //设置字符编码
            response.setContentType(CoreConstant.CONTENT_TYPE);
            response.getWriter().print(jsonObj);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**首页饼图*/
    @RequestMapping(params = "method=getMonthCount")
    public void getMonthCount(HttpServletRequest request, HttpServletResponse response) throws Exception {
        //格式为yyyy-MM
        String dataDateStr = request.getParameter("dataDate");
        SimpleDateFormat ymSf = new SimpleDateFormat("yyyy-MM");
        //部门隐患数量
        List<Integer> DptCounts = new ArrayList<Integer>();
        //部门名字
        List<String> DptNames = new ArrayList<String>();
        //部门整改数量
        List<Float> ReformRates = new ArrayList<Float>();
        double allRate = 0;
        if (StringUtil.isNotEmpty(dataDateStr)) {
            String tag = request.getParameter("tag");
            if (StringUtil.isNotEmpty(tag)) {
                if (tag.equals("0")) {  //0为上一月
                    dataDateStr = ymSf.format(DateHelper.addMonth(DateHelper.getDate(dataDateStr + "-01"), -1));
                } else if (tag.equals("1")) {  //1为下一月
                    dataDateStr = ymSf.format(DateHelper.addMonth(DateHelper.getDate(dataDateStr + "-01"), 1));
                }
            } else {
                dataDateStr = ymSf.format(new Date());
            }

        } else {
            dataDateStr = ymSf.format(new Date());
        }

        //为空时，默认取当月
        if (!StringUtil.isNotEmpty(dataDateStr) || dataDateStr.toLowerCase().contains("undefined")) {
            dataDateStr = ymSf.format(new Date());
        }


        try {
            JSONObject jsonObj = new JSONObject();
            List<ReformDetail> allList = new ArrayList<ReformDetail>();
            List<ReformDetail> reformList = new ArrayList<ReformDetail>();
            String monthBeginDate = DateHelper.getString(DateHelper.getFirstBeginDateOfMonth(dataDateStr + "-01"));
            String monthEndDate = DateHelper.getString(DateHelper.getFirstEndDateOfMonth(dataDateStr + "-01"));

            String monthBeginTime = monthBeginDate + " 00:00:00";
            String monthEndTime = monthEndDate + " 23:59:59";

            String allHql = " from ReformDetail detail where valid=1 and findTime >= '" + monthBeginTime + "' and findTime <= '" + monthEndTime + "' order by findTime";
            allList = this.reformManager.getResultByQueryString(allHql);
            String reformHql = " from ReformDetail detail where valid=1 and detail.reformStatus=4 and  findTime >= '" + monthBeginTime + "' and findTime <= '" + monthEndTime + "' order by findTime";
            reformList = this.reformManager.getResultByQueryString(reformHql);
            if (allList.size() > 0) {
                allRate = 100 * reformList.size() / allList.size();
            }
            /*********按部门统计*********/
            List<Department> dptList = new ArrayList<Department>();
            //得到所有部门（按顺序）
            String dptHql = " from Department where valid=1 and parent.xId = 1 and type=1 order by orderNo";
            dptList = this.departmentManager.getResultByQueryString(dptHql);
            for (Department tmpDepartment : dptList) {
                int dptCount = 0;
                int reformCount = 0;
                float rate = 0.00f;
                int dptId = tmpDepartment.getDepartmentId();
                for (ReformDetail tmpList : allList) {
                    if (tmpList.getDepartment().getDepartmentId() == dptId) {
                        dptCount += 1;
                    }
                }
                for (ReformDetail tmpList : reformList) {
                    if (tmpList.getDepartment().getDepartmentId() == dptId) {
                        reformCount += 1;
                    }
                }
                if (dptCount > 0) {
                    rate = reformCount * 100/ dptCount ;
                }
                ReformRates.add(rate);
                DptCounts.add(dptCount);
                DptNames.add("'" + tmpDepartment.getDepartmentName() + "'");
            }
            SimpleDateFormat sf = new SimpleDateFormat("yyyy年MM月");

            int allCount = allList.size();
            jsonObj.put("_NowDate", dataDateStr);
            jsonObj.put("_NowYearMonth", sf.format(DateHelper.getDate(dataDateStr + "-01")));
            jsonObj.put("_AllCount", allCount);
            jsonObj.put("_AllRate", allRate);


            jsonObj.put("_DptCount", DptCounts);
            jsonObj.put("_DptNames", DptNames);
            jsonObj.put("_ReformRates", ReformRates);

            //设置字符编码
            response.setContentType(CoreConstant.CONTENT_TYPE);
            response.getWriter().print(jsonObj);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(params = "method=reformList")
    public void reformList(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String[] queryString = new String[2];
        queryString[0] = "from ReformDetail reform where valid=1 ";
        queryString[1] = "select count(*) from  ReformDetail reform where valid=1 ";
        queryString = this.reformManager.generateQueryString(queryString, getSearchParams(request));
        String page = request.getParameter("page"); // 当前页
        String rowsNum = request.getParameter("rows"); // 每页显示的行数
        Pages pages = new Pages(request);
        pages.setPage(Integer.valueOf(page));
        pages.setPerPageNum(Integer.valueOf(rowsNum));

        PageList pl = this.reformManager.getResultByQueryString(queryString[0], queryString[1], true, pages);
        List<ReformDetail> list = pl.getObjectList();
        List<ReformVo> vos = new ArrayList<>();

        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //转vo
        for (ReformDetail tmpWork : list) {
            ReformVo vo = this.reformManager.getReformDetail(tmpWork.getReformId());
            vos.add(vo);
        }
        // 定义返回的数据类型：json，使用了json-lib
        JSONObject jsonObj = new JSONObject();

        // 定义rows，存放数据
        JSONArray rows = new JSONArray();
        jsonObj.put("page", pl.getPages().getCurrPage()); // 当前页(名称必须为page)
        jsonObj.put("total", pl.getPages().getTotalPage()); // 总页数(名称必须为total)
        jsonObj.put("records", pl.getPages().getTotals()); // 总记录数(名称必须为records)

        JSONConvert convert = new JSONConvert();
        //通知Convert，哪些关联对象需要获取
        List awareObject = new ArrayList();
        rows = convert.modelCollect2JSONArray(vos, awareObject);
        jsonObj.put("rows", rows); // 返回到前台每页显示的数据(名称必须为rows)
        // 设置字符编码
        response.setContentType(CoreConstant.CONTENT_TYPE);
        response.getWriter().print(jsonObj);
    }

    /**
     * 网页端获取年度按部门统计
     *
     * @param request
     * @param response
     * @param
     */
    @RequestMapping(params = "method=reformYearReport")
    @Transactional
    public void reformYearReport(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String message = "";
        boolean success = true;

        //int rightCount = 0;  //正常数
        //int errorCount = 0;  //异常数

        List<Map<String, List<Map<String, Integer>>>> resultList = new ArrayList<Map<String, List<Map<String, Integer>>>>();

        JSONObject jsonObj = new JSONObject();
        //用户
        //String token = request.getParameter("token");
        //User user = LoginConfirm.loginUserMap.get(token);

        //参数：
        //String beginDate = request.getParameter("beginDate");
        //String endDate = request.getParameter("endDate");

        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat sf_y = new SimpleDateFormat("yyyy");
//		SimpleDateFormat sf_m = new SimpleDateFormat("m");

        //得到当前年
        String thisYear = sf_y.format(new Date());
        String yearBegin = thisYear + "-01-01 00:00:00";
        String yearEnd = thisYear + "-12-31 23:59:59";


        try {

            //if(user != null){
            //查询出时间段内所有的异常项
//				String rightHql = " from ItemDetail detail where detail.valid=1 and detail.workState=1 and detail.opResult=1 and endTime >= '" + yearBegin + "' and endTime <= '" + yearEnd + "'";
//				List<ItemDetail> rightList = this.itemDetailManager.getResultByQueryString(rightHql);
//				rightCount = rightList.size();

            String errorHql = " from ReformDetail detail where detail.valid=1 and findTime >= '" + yearBegin + "' and findTime <= '" + yearEnd + "'";
            List<ReformDetail> errorList = this.reformManager.getResultByQueryString(errorHql);

            //月份所有部门异常数量
            int allCount = 0;

            //第一层循环，部门
            for (int i = 11; i < 24; i++) {
                Map<String, List<Map<String, Integer>>> dMap = new HashMap<String, List<Map<String, Integer>>>();

                //所有月份及合计
                List<Map<String, Integer>> list = new ArrayList<Map<String, Integer>>();

                //第二层循环，月份及合计
                int allMonthCount = 0;
                for (int j = 1; j < 13; j++) {
                    int monthErrorCount = 0;

                    //异常项
                    if (errorList != null && errorList.size() > 0) {
                        for (ReformDetail tmpDetail : errorList) {
                            String departmentNo = "";

                            int opMonth = tmpDetail.getFindTime().getMonth() + 1;
                            if (tmpDetail.getDepartment() != null) {
                                departmentNo = tmpDetail.getDepartment().getDepartmentNo();
                                if (departmentNo.equals(String.valueOf(i)) && opMonth == j) {
                                    monthErrorCount += 1;
                                }
                            }
                        }
                    }


                    allMonthCount += monthErrorCount;

                    Map<String, Integer> monthMap = new HashMap<String, Integer>();
                    monthMap.put(String.valueOf(j), monthErrorCount);
                    list.add(monthMap);
                }
                Map<String, Integer> monthMap = new HashMap<String, Integer>();
                monthMap.put("13", allMonthCount);
                list.add(monthMap);

                dMap.put(String.valueOf(i), list);

                resultList.add(dMap);
            }


            int allMonthCount = 0;
            List<Map<String, Integer>> hjList = new ArrayList<Map<String, Integer>>();
            for (int j = 1; j < 13; j++) {
                int monthErrorCount = 0;

                //异常项
                if (errorList != null && errorList.size() > 0) {
                    for (ReformDetail tmpDetail : errorList) {
                        int opMonth = tmpDetail.getFindTime().getMonth() + 1;
                        if (opMonth == j) {
                            monthErrorCount += 1;
                        }
                    }
                }

                allMonthCount += monthErrorCount;

                Map<String, Integer> monthAllMap = new HashMap<String, Integer>();
                monthAllMap.put(String.valueOf(j), monthErrorCount);
                hjList.add(monthAllMap);
            }

            Map<String, Integer> allAllMap = new HashMap<String, Integer>();
            allAllMap.put("13", allMonthCount);
            hjList.add(allAllMap);

            Map<String, List<Map<String, Integer>>> fMap = new HashMap<String, List<Map<String, Integer>>>();
            fMap.put("99", hjList);

            resultList.add(fMap);


            //}else {
            //	message = "请登录！";
            //	success = false;
            //}


        } catch (Exception e) {
            message = "后台出错，请重试！";
            success = false;
            e.printStackTrace();
            logger.error("出现错误================" + e.getLocalizedMessage());
            logger.error(e, e.fillInStackTrace());
        }

        jsonObj.put("success", success);
        jsonObj.put("message", message);
        jsonObj.put("result", resultList);
        jsonObj.put("thisYear", thisYear);

        //设置字符编码
        response.setContentType(CoreConstant.CONTENT_TYPE);
        response.getWriter().print(jsonObj);
    }


    /**
     * 网页端获取月度按部门统计
     *
     * @param request
     * @param response
     * @param
     */
    @RequestMapping(params = "method=reformMonthReport")
    @Transactional
    public String patrolMonthReport(HttpServletRequest request, HttpServletResponse response) throws Exception {

        List<Map<String, List<Integer>>> resultList = new ArrayList<Map<String, List<Integer>>>();
        List<ChildCategory> categories = new ArrayList<>();
        List<ReformVo> details = new ArrayList<>();

        //参数：
        String dataYearStr = request.getParameter("year");
        String dataMonthStr = request.getParameter("month");

        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {

            if (StringUtil.isNotEmpty(dataYearStr) && StringUtil.isNotEmpty(dataMonthStr)) {
                int dataYear = Integer.valueOf(dataYearStr);
                int dataMonth = Integer.valueOf(dataMonthStr);


                String monthBeginDate = DateHelper.getString(DateHelper.getFirstBeginDateOfMonth(DateHelper.getDate(dataYear + "-" + (dataMonth < 10 ? "0" + dataMonth : dataMonth) + "-01")));
                String monthEndDate = DateHelper.getString(DateHelper.getFirstEndDateOfMonth(DateHelper.getDate(dataYear + "-" + (dataMonth < 10 ? "0" + dataMonth : dataMonth) + "-01")));
                ;

                String monthBeginTime = monthBeginDate + " 00:00:00";
                String monthEndTime = monthEndDate + " 23:59:59";

                String errorHql = " from ReformDetail detail where detail.valid=1 and findTime >= '" + monthBeginTime + "' and findTime <= '" + monthEndTime + "' order by findTime";
                List<ReformDetail> errorList = this.reformManager.getResultByQueryString(errorHql);


                //得到所有大项（按顺序）
                categories = this.childCategoryManager.getAll();


                //大项所有部门异常数量
                int allCount = 0;

                String[] departIds = {"11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23"};
                String[] departs = {"市场营销部", "人力资源部", "整车物流部", "质量安全部", "码头运营部", "党群工作部", "总经理办公室", "计划财务部", "采购部", "技术规划部", "信息技术部", "数据业务部", "零部件物流部"};

                //第一层循环，部门
                for (int i = 0; i < departs.length; i++) {

                    Map<String, List<Integer>> map = new HashMap<String, List<Integer>>();
                    //部门下，每个大项的异常量
                    List<Integer> eachDepartCountList = new ArrayList<Integer>();
                    if (categories != null && categories.size() > 0) {

                        //if(i<departs.length - 1){
                        for (ChildCategory tmpCategory : categories) {

                            int eachBigCount = 0;
                            Integer categoryId = tmpCategory.getCategoryId();
                            if (errorList != null && errorList.size() > 0) {
                                for (ReformDetail tmpDetail : errorList) {
                                    Integer tmpCategoryId = tmpDetail.getErrorTypeId();
                                    if (tmpDetail.getDepartment() != null) {
                                        String departmentNo = tmpDetail.getDepartment().getDepartmentNo();
                                        if (departmentNo.endsWith(departIds[i]) && tmpCategoryId.intValue() == categoryId) {
                                            eachBigCount += 1;
                                        }
                                    }

                                }
                            }

                            eachDepartCountList.add(eachBigCount);
                        }

                        int eachDepCategoryAllCount = 0;
                        for (Integer tmpCount : eachDepartCountList) {
                            eachDepCategoryAllCount += tmpCount;
                        }
                        eachDepartCountList.add(eachDepCategoryAllCount);
                        map.put(departs[i], eachDepartCountList);
                        resultList.add(map);
                    }

                }


                //底部合计
                List<Integer> eachBigCountList = new ArrayList<Integer>();
                if (categories != null && categories.size() > 0) {

                    for (ChildCategory tmpCategory : categories) {

                        int eachBigCount = 0;
                        Integer bigCategoryId = tmpCategory.getCategoryId();
                        if (errorList != null && errorList.size() > 0) {
                            for (ReformDetail tmpDetail : errorList) {
                                Integer tmpBigId = tmpDetail.getErrorTypeId();

                                if (tmpBigId.intValue() == bigCategoryId) {
                                    eachBigCount += 1;
                                }
                            }
                        }

                        eachBigCountList.add(eachBigCount);
                    }

                    int eachCategoryAllCount = 0;
                    for (Integer tmpCount : eachBigCountList) {
                        eachCategoryAllCount += tmpCount;
                    }
                    eachBigCountList.add(eachCategoryAllCount);
                }

                Map<String, List<Integer>> allMap = new HashMap<String, List<Integer>>();
                allMap.put("合计", eachBigCountList);
                resultList.add(allMap);
                for (ReformDetail tmpDetail : errorList) {
                    ReformVo detailVo = reformManager.getReformDetail(tmpDetail.getReformId());
                    details.add(detailVo);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("出现错误=================" + e.getLocalizedMessage());
            logger.error(e, e.fillInStackTrace());
        }
        request.setAttribute("resultReport", resultList);
        request.setAttribute("categories", categories);
        request.setAttribute("dataYear", dataYearStr);
        request.setAttribute("dataMonth", dataMonthStr);
        request.setAttribute("details", details);
        return "/security/reformMonthReport";
    }


    /**************隐患上报*****************/
    @RequestMapping(params = "method=newReform")
    @Transactional(rollbackFor = java.lang.Exception.class)
    public void newReform(HttpServletRequest request,
                          HttpServletResponse response, DefaultMultipartHttpServletRequest multipartRequest) throws Exception {
        String message = "";
        boolean success = true;
        try {
            JSONObject jsonObj = new JSONObject();
            // 用户
            String token = request.getParameter("token");
            User user = LoginConfirm.loginUserMap.get(token);
            // 参数：
            String errorTitle = request.getParameter("errorTitle");
            String errorType = request.getParameter("errorType");
            String errorArea = request.getParameter("errorArea");
            String dutyDepartName = request.getParameter("dutyDepartName");
            String rTime = request.getParameter("reformTime");
            if(StringUtil.isNotEmpty(rTime)&&rTime.length()<12){
                rTime= rTime+ " 12:00:00";
            }
            String errorContent = request.getParameter("errorContent");
            String findTime = request.getParameter("findTime");
            String findUserId = request.getParameter("findUserId");
            String taskId = request.getParameter("taskId");
            String dutyPersonId = request.getParameter("dutyPersonId");
            // 解决信息
            String sloveInfo = request.getParameter("solveInfo");
            // 解决的时间
            String sloveTime = request.getParameter("solveTime");

            if (user != null) {
                findUserId = StringUtil.isNotEmpty(findUserId) ? findUserId : user.getUserId().toString();
                taskId = StringUtil.isNotEmpty(taskId) ? taskId : UUID.randomUUID().toString().replaceAll("-", "");
                ReformDetail reformDetail = new ReformDetail();
                if (StringUtil.isNotEmpty(dutyDepartName)) {
                    reformDetail.setDepartment(departmentManager.getDepartmentByDeparmentId(Integer.parseInt(dutyDepartName)));
                }
                Map<String, String> errorAttachs = uploadAttachment2(multipartRequest, "reform");
                String erroAttach = "";
                if (errorAttachs != null && errorAttachs.size() > 0) {
                    for (String key : errorAttachs.keySet()) {
                        if (StringUtil.isNotEmpty(key) && key.startsWith("error_")) {
                            erroAttach = erroAttach + errorAttachs.get(key) + ";";
                        }
                    }
                    reformDetail.setErrorAttach(erroAttach);
                }
                reformDetail.setErrorContent(errorContent);
                reformDetail.setErrorTitle(errorTitle);

                reformDetail.setReformTime(Convert.stringToDate(rTime, "yyyy-MM-dd HH:mm:ss"));
                reformDetail.setErrorTypeId(Integer.parseInt(errorType));
                reformDetail.setErrorAreaId(Integer.parseInt(errorArea));
                reformDetail.setFindTime(Convert.stringToDate(findTime, "yyyy-MM-dd HH:mm:ss"));
                reformDetail.setFindUser(userManager.getUserByUserId(Integer.parseInt(findUserId)));
                reformDetail.setReformStatus(ReformStatusEnum.TO_BE_RETIFIED.getCode());
                reformDetail.setTaskId(taskId);
                reformDetail.setSuperviseDeptId(userManager.getUserByUserId(Integer.parseInt(findUserId)).getDepartmentId());
                reformDetail.setValid(true);
                reformDetail.setMorningReport(true);

                if (StringUtil.isNotEmpty(dutyPersonId)) {
                    reformDetail.setDutyPerson(userManager.getUser(Integer.parseInt(dutyPersonId)));
                }

                if (StringUtil.isNotEmpty(dutyPersonId)) {
                    reformDetail.setDutyPerson(userManager.getUser(Integer.parseInt(dutyPersonId)));
                }
                List<Schedule> scheduleList = new ArrayList<Schedule>();

                String solveAttach = "";
                if (errorAttachs != null && errorAttachs.size() > 0) {
                    for (String key : errorAttachs.keySet()) {
                        if (StringUtil.isNotEmpty(key) && key.startsWith("solve_")) {
                            solveAttach = solveAttach + errorAttachs.get(key) + ";";
                        }
                    }
                }
                if (StringUtil.isNotEmpty(sloveTime)) {
                    if (StringUtil.isNotEmpty(solveAttach) || StringUtil.isNotEmpty(sloveInfo)) {
                        Integer code = ReformStatusEnum.HAS_BEEN_RECTIFIED.getCode();
                        Schedule schedule = getSchedule(solveAttach, reformDetail, sloveInfo, code + "", sloveTime, user);
                        scheduleList.add(schedule);
                        reformDetail.setReformStatus(code);
                        reformDetail.setDutyPerson(user);
                    }
                }
                reformDetail.setScheduleList(scheduleList);
                this.reformManager.save(reformDetail);
            }
            jsonObj.put("success", success);
            jsonObj.put("message", message);

            // 设置字符编码
            response.setContentType(CoreConstant.CONTENT_TYPE);
            response.getWriter().print(jsonObj);

        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            e.printStackTrace();
        }
    }

    private Schedule getSchedule(String reformAttachs, ReformDetail reformDetail, String Info, String reformState, String reformTime, User user) {
        Schedule schedule = new Schedule();
        schedule.setReformAttach(reformAttachs);
        schedule.setReformDetail(reformDetail);
        schedule.setReformInfo(Info);
        schedule.setReformStatus(Integer.parseInt(reformState));
        schedule.setReformTime(Convert.stringToDate(reformTime, "yyyy-MM-dd HH:mm:ss"));
        schedule.setReformUser(user);

        return schedule;
    }


    /**************隐患整改*****************/
    @RequestMapping(params = "method=updateReform")
    @Transactional(rollbackFor = Exception.class)
    public void updateReform(HttpServletRequest request,
                             HttpServletResponse response, DefaultMultipartHttpServletRequest multipartRequest) {
        try {
            String message = "";
            boolean success = true;
            String token = request.getParameter("token");
            User user = LoginConfirm.loginUserMap.get(token);

            //taskId,reformTime,reformState;Info,reformAttach,reform
            String taskId = request.getParameter("taskId");
            String reformTime = request.getParameter("reformTime");
            String reformState = request.getParameter("reformState");
            String Info = request.getParameter("Info");
            String dutyUserId = request.getParameter("dutyUserId");
            //String reformAttach = request.getParameter("reformAttach");
            ReformDetail reformDetail = this.reformManager.getReformDetailBytaskId(taskId);
            if (reformDetail.getDutyPerson() == null) {
                if (StringUtil.isNotEmpty(dutyUserId)) {
                    reformDetail.setDutyPerson(userManager.getUserByUserId(Integer.parseInt(dutyUserId)));
                }
            }
            List<Schedule> scheduleList = reformDetail.getScheduleList();
            Schedule schedule = new Schedule();
            String reformAttachs = uploadAttachmentBySize(multipartRequest, "schedule");
            schedule.setReformAttach(reformAttachs);
            schedule.setReformDetail(reformDetail);
            schedule.setReformInfo(Info);
            schedule.setReformStatus(Integer.parseInt(reformState));
            schedule.setReformTime(Convert.stringToDate(reformTime, "yyyy-MM-dd HH:mm:ss"));
            schedule.setReformUser(user);
            scheduleList.add(schedule);
            reformDetail.setReformStatus(Integer.parseInt(reformState));
            if (reformState.equals("4")) {
                reformDetail.setFinishTime(Convert.stringToDate(reformTime, "yyyy-MM-dd HH:mm:ss"));
            }
            this.reformManager.save(reformDetail);
            JSONObject jsonObj = new JSONObject();
            jsonObj.put("success", success);
            jsonObj.put("message", message);

            // 设置字符编码
            response.setContentType(CoreConstant.CONTENT_TYPE);
            response.getWriter().print(jsonObj);
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            e.printStackTrace();
        }
    }

    /**
     * 隐患编辑
     */
    @RequestMapping(params = "method=saveReform")
    @Transactional
    public String saveReform(ReformVo reformVo, ModelMap modelMap, HttpServletRequest request, DefaultMultipartHttpServletRequest multipartRequest) throws Exception {
        ReformDetail reformDetail = (ReformDetail) this.reformManager.get(reformVo.getReformId());
        reformDetail.setErrorContent(reformVo.getErrorInfo());
        reformDetail.setErrorTypeId(reformVo.getErrorTypeId());
        reformDetail.setErrorAreaId(reformVo.getAreaId());
        reformDetail.setDepartment(this.departmentManager.getDepartmentByName(reformVo.getDutyDeptName()));
        reformDetail.setReformTime(Convert.stringToDate(reformVo.getReformTime(), "yyyy-MM-dd"));
        reformDetail.setMorningReport(reformVo.isMorningReport());
        String errorAttach = reformDetail.getErrorAttach();
        List<String> attList=new ArrayList<>();
        if(StringUtil.isNotEmpty(errorAttach)){
            String[] attachs = errorAttach.split(";");
            List<String> list = Arrays.asList(attachs);
            attList = new ArrayList<>(list);
        }
        String[] images = request.getParameterValues("image");
        List<String> result;
        if(images!=null&&images.length>0){
            List<String> mList = Arrays.asList(images);
            List<String> imageList = new ArrayList<>(mList);
            result = removeAttaach(attList, imageList);
        }else {
            result=attList;
        }
        String newAttach = "";
        for (int i = 0; i < result.size(); i++) {
            if (StringUtil.isNotEmpty(result.get(i))) {
                newAttach += result.get(i);
                newAttach += ";";
            }
        }
        newAttach += this.uploadAttachmentBySize(multipartRequest, "reform");
        reformDetail.setErrorAttach(newAttach);
        ReformDetail reform = (ReformDetail) this.reformManager.save(reformDetail);
        ReformVo vo = this.reformManager.getReformDetail(reform.getReformId());
        modelMap.addAttribute("reformDetail", vo);
        return "reform/editDetail";
    }
      /**
     * web端新增
     */
    @RequestMapping(params = "method=addReform")
    @Transactional
    public String addReform(ReformVo reformVo, ModelMap modelMap, HttpServletRequest request, DefaultMultipartHttpServletRequest multipartRequest) throws Exception {
        ReformDetail reformDetail = new ReformDetail();
        reformDetail.setValid(true);
        reformDetail.setErrorAreaId(reformVo.getAreaId());
        reformDetail.setErrorTypeId(reformVo.getErrorTypeId());
        reformDetail.setDepartment(this.departmentManager.getDepartmentByName(reformVo.getDutyDeptName()));
        reformDetail.setErrorContent(reformVo.getErrorInfo());
        reformDetail.setReformTime(Convert.stringToDate(reformVo.getReformTime(),"yyyy-MM-dd HH:mm:ss"));
        reformDetail.setFindTime(Convert.stringToDate(reformVo.getFindTime(),"yyyy-MM-dd HH:mm:ss"));
        reformDetail.setFinishTime(Convert.stringToDate(reformVo.getFinishTime(),"yyyy-MM-dd HH:mm:ss"));
        reformDetail.setReformStatus(ReformStatusEnum.HAS_BEEN_RECTIFIED.getCode());
        reformDetail.setErrorContent(reformVo.getErrorInfo());
        reformDetail.setErrorTitle(reformVo.getErrorTitle());
        reformDetail.setFindUser(this.userManager.getUserByUserId(reformVo.getFindUserId()));
        String attach = this.uploadAttachmentBySize(multipartRequest, "reform");
        reformDetail.setErrorAttach(attach);
        reformDetail.setMorningReport(true);
        this.reformManager.save(reformDetail);
        return "core/success";
    }


    @RequestMapping(params = "method=yearReport")
    public String yearReport(HttpServletRequest request, ModelMap modelMap)throws Exception {
        List<YearListVo> vos = new ArrayList<>();
        String year = request.getParameter("year");
        if (!StringUtil.isNotEmpty(year)) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
            year = sdf.format(new Date());
        }
        List<ParentCategory> allBig= this.parentCategoryManager.getAllValid();
        for (ParentCategory parentCategory:allBig){
            YearListVo yearListVo=new YearListVo();
            yearListVo.setParentCategory(parentCategory.getPName());
            List<YearItemVo> childItems = new ArrayList<>();
            List<ChildCategory> childs = this.childCategoryManager.getChildCategoriesByParentId(parentCategory.getPId());
            for(ChildCategory childCategory:childs){
                YearItemVo itemVo=new YearItemVo();
                itemVo.setCategoryName(childCategory.getCategoryName());
                List<Integer> counts = new ArrayList<>();
                int monthCount=0;
                for (int i = 1; i < 13; i++) {
                    String dataDateStr = year + "-" + (i < 10 ? "0" + i : i);
                    String monthBeginDate = DateHelper.getString(DateHelper.getFirstBeginDateOfMonth(dataDateStr + "-01"));
                    String monthEndDate = DateHelper.getString(DateHelper.getFirstEndDateOfMonth(dataDateStr + "-01"));
                    String monthBeginTime = monthBeginDate + " 00:00:00";
                    String monthEndTime = monthEndDate + " 23:59:59";
                    Integer typeId=childCategory.getCategoryId();
                    String hql = "from ReformDetail where valid=1 and errorTypeId="+typeId+" and findTime>= '" + monthBeginTime + "' and findTime <= '" + monthEndTime + "'";
                    List<ReformDetail> result = this.reformManager.getResultByQueryString(hql);
                    Integer count=result.size();
                    counts.add(count);
                    monthCount+=count.intValue();
                }
                counts.add(monthCount);
                itemVo.setCounts(counts);
                childItems.add(itemVo);
            }
            yearListVo.setChildItems(childItems);
            vos.add(yearListVo);
        }
        modelMap.addAttribute("yearList", vos);
        modelMap.addAttribute("year", year);
        return "reform/yearReport";
    }

    @RequestMapping(params = "method=exportExcel")
    public String exportExcel(HttpServletRequest request, HttpServletResponse response) throws Exception {
        try {
            String[] queryString = new String[2];
            queryString[0] = "from ReformDetail reform where 1=1 and valid=1";
            queryString[1] = "select count(reformId) from ReformDetail reform where 1=1 and valid=1";
            String[] params=getSearchParams(request);
            queryString = this.reformManager.generateQueryString(queryString, params);
            String page = request.getParameter("page");        //当前页
            String rowsNum = request.getParameter("rows");    //每页显示的行数
            Pages pages = new Pages(request);
            pages.setPage(Integer.valueOf(page));
            pages.setPerPageNum(Integer.valueOf(rowsNum));

            PageList pl = this.reformManager.getResultByQueryString(queryString[0], queryString[1], true, pages);
            List<ReformDetail> list = pl.getObjectList();
            //转为以用户名为索引，计数总次数
            /******************导出Excel********************/
            String filePath = "/" + CoreConstant.Attachment_Path + "reformList/";
            String fileTitle = "隐患任务列表";

            ExcelObject object = new ExcelObject();
            object.setFilePath(filePath);
            object.setFileName(fileTitle);
            object.setTitle(fileTitle);

            List rowName = new ArrayList();
            String[][] data = new String[10][list.size()];
            int k = 10;

            rowName.add("序号");
            rowName.add("隐患内容");
            rowName.add("发现时间");
            rowName.add("整改期限");
            rowName.add("隐患类型");
            rowName.add("责任部门");
            rowName.add("隐患区域");
            rowName.add("发现人");
            rowName.add("状态");
            rowName.add("完成时间");

            for (int i = 0; i < list.size(); i++) {
                ReformDetail reformDetail= list.get(i);
                ReformVo reformVO = this.reformManager.getReformDetail(reformDetail.getReformId());
                data[0][i] = String.valueOf(i + 1);
                data[1][i] = reformVO.getErrorInfo();
                data[2][i] = reformVO.getFindTime();
                data[3][i] = reformVO.getReformTime();
                data[4][i] = reformVO.getErrorType();
                data[5][i] = reformVO.getDutyDeptName();
                data[6][i] = reformVO.getAreaName();
                data[7][i] = reformVO.getFindUserName();
                data[8][i] = reformVO.getStatusStr();
                data[9][i] = reformVO.getFinishTime();
            }
            for (int i = 0; i < k; i++) {
                object.addContentListByList(data[i]);
            }
            object.setRowName(rowName);
            ExcelOperate operate = new ExcelOperate();
            try {
                operate.exportExcel(object, list.size(), true, request);
            } catch (IOException e) {
                e.printStackTrace();
            }

            filePath = filePath + fileTitle + ".xls";
            request.getSession().removeAttribute("_File_Path");
            request.getSession().setAttribute("_File_Path", filePath);

        } catch (RuntimeException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return "/common/downloadExcel";
    }


}