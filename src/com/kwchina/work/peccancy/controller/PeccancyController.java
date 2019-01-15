package com.kwchina.work.peccancy.controller;

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
import com.kwchina.work.errorWork.entity.ErrorWork;
import com.kwchina.work.errorWork.utils.convert.Convert;
import com.kwchina.work.errorWork.vo.ErrorWorkVO;
import com.kwchina.work.peccancy.entity.PeccancyDetail;
import com.kwchina.work.peccancy.service.PeccancyDetailManager;
import com.kwchina.work.peccancy.vo.PeccancyVo;
import com.kwchina.work.reform.entity.ChildCategory;
import com.kwchina.work.reform.entity.ReformDetail;
import com.kwchina.work.reform.service.ChildCategoryManager;
import com.kwchina.work.reform.vo.ReformVo;
import com.kwchina.work.sys.LoginConfirm;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.support.DefaultMultipartHttpServletRequest;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("/peccancy.htm")
public class PeccancyController extends BasicController {
    @Resource
    private PeccancyDetailManager peccancyDetailManager;
    @Resource
    private DepartmentManager departmentManager;
    @Resource
    private ChildCategoryManager childCategoryManager;
    @Resource
    private UserManager userManager;

    @RequestMapping(params = "method=listPeccancy")
    public String dailyReport(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) {
        List<PeccancyDetail> details = new ArrayList<PeccancyDetail>();
        int errorCount;

        //参数：
        String beginTime = request.getParameter("beginDate");
        String endTime = request.getParameter("endDate");
        String dept = request.getParameter("dept");
        String category = request.getParameter("category");
        String findUserName = request.getParameter("findUserName");
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, +1);
        Date time = cal.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        if (!StringUtil.isNotEmpty(beginTime)) {
            beginTime = sdf.format(new Date());
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

        String hql = " from PeccancyDetail detail where valid=1 and markTime >= '" + beginTime + " 08:00:00 'and markTime <= '" + endTime + " 08:00:00' order by markTime";
        @SuppressWarnings("unchecked")
        List<PeccancyDetail> errorList = this.peccancyDetailManager.getResultByQueryString(hql);
        //得到所有大项（按顺序）
        for (PeccancyDetail tmpDetail : errorList) {
            if (StringUtil.isNotEmpty(category)) {
                if (category.equals(tmpDetail.getCategory().getCategoryId().toString())) {
                    if (StringUtil.isNotEmpty(dept)) {
                        if (dept.equals(tmpDetail.getDepartment().getDepartmentName())) {
                            if (StringUtil.isNotEmpty(findUserName) && findUserName.equals(tmpDetail.getOffender().getName())) {
                                details.add(tmpDetail);
                            } else if (!StringUtil.isNotEmpty(findUserName)) {
                                details.add(tmpDetail);
                            }
                        }

                    } else if (!StringUtil.isNotEmpty(dept)) {
                        if (StringUtil.isNotEmpty(findUserName) && findUserName.equals(tmpDetail.getOffender().getName())) {
                            details.add(tmpDetail);
                        } else if (!StringUtil.isNotEmpty(findUserName)) {
                            details.add(tmpDetail);
                        }
                    }
                }
            } else if (!StringUtil.isNotEmpty(category)) {
                if (StringUtil.isNotEmpty(dept)) {
                    if (dept.equals(tmpDetail.getDepartment().getDepartmentName())) {
                        if (StringUtil.isNotEmpty(findUserName) && findUserName.equals(tmpDetail.getOffender().getName())) {
                            details.add(tmpDetail);
                        } else if (!StringUtil.isNotEmpty(findUserName)) {
                            details.add(tmpDetail);
                        }
                    }

                } else if (!StringUtil.isNotEmpty(dept)) {
                    if (StringUtil.isNotEmpty(findUserName) && findUserName.equals(tmpDetail.getOffender().getName())) {
                        details.add(tmpDetail);
                    } else if (!StringUtil.isNotEmpty(findUserName)) {
                        details.add(tmpDetail);
                    }
                }
            }

        }
        errorCount = details.size();
        modelMap.addAttribute("errorCount", errorCount);
        modelMap.addAttribute("details", details);

        return "/peccancy/listPeccancy";

    }

    @RequestMapping(params = "method=statistics")
    public void statistics(HttpServletRequest request, HttpServletResponse response) throws Exception {

        JSONObject jsonObj = new JSONObject();

        List<Integer> CountList = new ArrayList<Integer>();  //正常量

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

        String peccancyType = request.getParameter("peccancyType");
        try {
            if (StringUtil.isNotEmpty(peccancyType)) {
                int categoryId = Integer.valueOf(peccancyType);
                List<PeccancyDetail> peccancyList = new ArrayList<>();
                if (categoryId == 0) {
                    String Hql = " from PeccancyDetail detail where valid=1 and markTime >= '" + currentYearStr + "-01-01 00:00:00 ' and markTime <= '" + currentYearStr + "-12-31 23:59:59' order by markTime";
                    peccancyList = this.peccancyDetailManager.getResultByQueryString(Hql);

                } else {
                    String Hql = " from PeccancyDetail detail where valid=1 and detail.category.categoryId = " + categoryId + " and markTime >= '" + currentYearStr + "-01-01 00:00:00 ' and markTime <= '" + currentYearStr + "-12-31 23:59:59' order by markTime";
                    peccancyList = this.peccancyDetailManager.getResultByQueryString(Hql);
                }

                for (int i = 1; i < 13; i++) {
                    int rightCount = 0;

                    String eachMonthStr = thisYear + "-" + (i < 10 ? "0" + i : i) + "-01";
                    //				java.util.Date eachMonthBeginDate = DateHelper.getFirstBeginDateOfMonth(eachMonthStr);
                    Date eachMonthEndDate = DateHelper.getFirstEndDateOfMonth(eachMonthStr);

                    String eachMonthBeginStr = eachMonthStr + " 00:00:00";
                    String eachMonthEndStr = DateHelper.getString(eachMonthEndDate) + " 23:59:59";

                    for (PeccancyDetail peccancyDetail : peccancyList) {
                        if (peccancyDetail.getMarkTime().after(Timestamp.valueOf(eachMonthBeginStr)) && peccancyDetail.getMarkTime().before(Timestamp.valueOf(eachMonthEndStr))) {
                            rightCount += 1;
                        }
                    }
                    CountList.add(rightCount);
                }
            }
            jsonObj.put("_CountList", CountList);

            //设置字符编码
            response.setContentType(CoreConstant.CONTENT_TYPE);
            response.getWriter().print(jsonObj);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(params = "method=getPie")
    public void getMonthCount(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String dataDateStr = request.getParameter("dataDate");  //格式为yyyy-MM
        SimpleDateFormat ymSf = new SimpleDateFormat("yyyy-MM");

        List<Integer> CountArray_dpt = new ArrayList<Integer>();    //部门次数
        List<Integer> pTypeCountArray = new ArrayList<Integer>();    //类型次数
        List<String> pTypeNameArray = new ArrayList<String>();        //类型名字
        List<String> DptNameArray = new ArrayList<String>();        //部门名字


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
            List<PeccancyDetail> allList = new ArrayList<PeccancyDetail>();    //总项

            String monthBeginDate = DateHelper.getString(DateHelper.getFirstBeginDateOfMonth(dataDateStr + "-01"));
            String monthEndDate = DateHelper.getString(DateHelper.getFirstEndDateOfMonth(dataDateStr + "-01"));

            String monthBeginTime = monthBeginDate + " 00:00:00";
            String monthEndTime = monthEndDate + " 23:59:59";

            String allHql = " from PeccancyDetail detail where valid=1 and markTime >= '" + monthBeginTime + "' and markTime <= '" + monthEndTime + "' order by markTime";
            allList = this.peccancyDetailManager.getResultByQueryString(allHql);
            /*********按类型统计*********/
            List<ChildCategory> typeList;
            typeList = this.childCategoryManager.getAll();
            if (typeList != null && typeList.size() > 0) {
                for (ChildCategory category : typeList) {
                    int typeCount = 0;
                    Integer categotyId = category.getCategoryId();
                    for (PeccancyDetail tmpList : allList) {
                        if (tmpList.getCategory().getCategoryId().equals(categotyId)) {
                            typeCount += 1;
                        }
                    }
                    pTypeCountArray.add(typeCount);
                    pTypeNameArray.add("'" + category.getCategoryName() + "'");
                }
            }

            /*********按部门统计*********/
            List<Department> dptList = new ArrayList<Department>();
            //得到所有部门（按顺序）
            String dptHql = " from Department where valid=1 and type=1 and parent.xId = 1 order by orderNo";
            dptList = this.departmentManager.getResultByQueryString(dptHql);

            if (dptList != null && dptList.size() > 0) {
                for (Department tmpDepartment : dptList) {
                    int dptCount = 0;
                    Integer dptId = tmpDepartment.getDepartmentId();
                    for (PeccancyDetail tmpList : allList) {
                        if (tmpList.getDepartment().getDepartmentId().equals(dptId)) {
                            dptCount += 1;
                        }
                    }
                    CountArray_dpt.add(dptCount);
                    DptNameArray.add("'" + tmpDepartment.getDepartmentName() + "'");
                }

            }

            SimpleDateFormat sf = new SimpleDateFormat("yyyy年MM月");
            int allCount = allList.size();

            jsonObj.put("_NowDate", dataDateStr);
            jsonObj.put("_NowYearMonth", sf.format(DateHelper.getDate(dataDateStr + "-01")));
            jsonObj.put("_AllCount", allCount);

            jsonObj.put("_Count_dpt", CountArray_dpt);
            jsonObj.put("_Count_type", pTypeCountArray);
            jsonObj.put("_DptNames", DptNameArray);
            jsonObj.put("_typeNames", pTypeNameArray);

            //设置字符编码
            response.setContentType(CoreConstant.CONTENT_TYPE);
            response.getWriter().print(jsonObj);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**************违章上报*****************/
    @RequestMapping(params = "method=newPeccancy")
    @Transactional
    public void newPeccancy(HttpServletRequest request,
                            HttpServletResponse response, DefaultMultipartHttpServletRequest multipartRequest) throws Exception {
        String message = "";
        boolean success = true;

        JSONObject jsonObj = new JSONObject();
        // 用户
        String token = request.getParameter("token");
        User user = LoginConfirm.loginUserMap.get(token);

        //ruleDepart,ruleUser,ruleType,ruleContent,ruleAttachs,ruleTime，memo
        // 参数：
        String ruleDepartId = request.getParameter("ruleDepartId");//责任部门
        String ruleUserName = request.getParameter("ruleUserName");//责任人
        String ruleType = request.getParameter("ruleType"); //违章类型
        String ruleContent = request.getParameter("ruleContent");//违章描述
        String ruleTime = request.getParameter("ruleTime");//违章时间
        String memo = request.getParameter("memo");//备注
        String rules = request.getParameter("rules");//条例

        if (user != null) {
            PeccancyDetail peccancyDetail = new PeccancyDetail();
            Map<String, String> peccancyAttachs = uploadAttachment2(multipartRequest, "peccancy");
            String ruleAttach = "";
            if (peccancyAttachs != null && peccancyAttachs.size() > 0) {
                for (String key : peccancyAttachs.keySet()) {
                    if (StringUtil.isNotEmpty(key) && key.startsWith("rule_")) {
                        ruleAttach = ruleAttach + peccancyAttachs.get(key) + ";";
                    }
                }
                peccancyDetail.setAttachment(ruleAttach);
            }
            String reformAttach = "";
            if (peccancyAttachs != null && peccancyAttachs.size() > 0) {
                for (String key : peccancyAttachs.keySet()) {
                    if (StringUtil.isNotEmpty(key) && key.startsWith("reform_")) {
                        reformAttach = reformAttach + peccancyAttachs.get(key) + ";";
                    }
                }
                peccancyDetail.setReformAttach(reformAttach);
            }
            peccancyDetail.setPeccancyRules(rules);
            peccancyDetail.setContent(ruleContent);
            peccancyDetail.setDepartment(departmentManager.getDepartmentByDeparmentId(Integer.parseInt(ruleDepartId)));
            peccancyDetail.setMarkTime(Convert.stringToDate(ruleTime, "yyyy-MM-dd HH:mm:ss"));
            peccancyDetail.setOffender(user);
            peccancyDetail.setCategory((ChildCategory) childCategoryManager.get(Integer.parseInt(ruleType)));
            peccancyDetail.setDutyPersonName(ruleUserName);
            peccancyDetail.setRemark(memo);
            peccancyDetail.setValid(true);
            peccancyDetail.setMorningReport(true);
            peccancyDetailManager.save(peccancyDetail);
        }
        jsonObj.put("success", success);
        jsonObj.put("message", message);

        // 设置字符编码
        response.setContentType(CoreConstant.CONTENT_TYPE);
        response.getWriter().print(jsonObj);
    }

    /**************违章任务列表************/
    @RequestMapping(params = "method=peccancyList")
    public void peccancyList(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String[] queryString = new String[2];
        queryString[0] = "from PeccancyDetail peccancy where valid=1 ";
        queryString[1] = "select count(*) from  PeccancyDetail peccancy where valid=1 ";
        queryString = this.peccancyDetailManager.generateQueryString(queryString, getSearchParams(request));
        String page = request.getParameter("page"); // 当前页
        String rowsNum = request.getParameter("rows"); // 每页显示的行数
        Pages pages = new Pages(request);
        pages.setPage(Integer.valueOf(page));
        pages.setPerPageNum(Integer.valueOf(rowsNum));

        PageList pl = this.peccancyDetailManager.getResultByQueryString(queryString[0], queryString[1], true, pages);
        List<PeccancyDetail> list = pl.getObjectList();
        List<PeccancyVo> vos = new ArrayList<>();

        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //转vo
        for (PeccancyDetail tmpWork : list) {
            PeccancyVo vo = this.peccancyDetailManager.getPeccancyVo(tmpWork.getPeccancyId());
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

    /*********违章详情*******************/
    @RequestMapping(params = "method=detail")
    public String detail(HttpServletRequest request, ModelMap modelMap) {
        String Id = request.getParameter("peccancyId");
        int peccancyId = Integer.parseInt(Id);
        PeccancyVo peccancyVo = this.peccancyDetailManager.getPeccancyVo(peccancyId);
        modelMap.addAttribute("peccancyDetail", peccancyVo);
        return "/peccancy/detail";
    }

    @RequestMapping(params = "method=edit")
    public String edit(HttpServletRequest request, ModelMap modelMap) {
        String Id = request.getParameter("peccancyId");
        int peccancyId = Integer.parseInt(Id);
        PeccancyVo peccancyVo = this.peccancyDetailManager.getPeccancyVo(peccancyId);
        modelMap.addAttribute("peccancyDetail", peccancyVo);
        return "/peccancy/editDetail";
    }

    /**
     * 隐患编辑
     */
    @RequestMapping(params = "method=savePeccancy")
    @Transactional
    public String savePeccancy(PeccancyVo vo, ModelMap modelMap, HttpServletRequest request, DefaultMultipartHttpServletRequest multipartRequest) throws Exception {
        PeccancyDetail peccancyDetail = (PeccancyDetail) this.peccancyDetailManager.get(vo.getPeccancyId());
        peccancyDetail.setContent(vo.getContent());
        peccancyDetail.setCategory(this.childCategoryManager.getChildCategoryByCategoryId(vo.getPeccancyTypeId()));
        peccancyDetail.setDepartment(this.departmentManager.getDepartmentByName(vo.getDutyDeptName()));
        peccancyDetail.setMorningReport(vo.isMorningReport());
        String ruleAttach = peccancyDetail.getAttachment();
        String reformAttach = peccancyDetail.getReformAttach();
        List<String> ruleAttList = new ArrayList<>();
        List<String> reformAttList = new ArrayList<>();
        if (StringUtil.isNotEmpty(reformAttach)) {
            String[] reformAttachs = reformAttach.split(";");
            List<String> reformList = Arrays.asList(reformAttachs);
            reformAttList = new ArrayList<>(reformList);
        }
        if (StringUtil.isNotEmpty(ruleAttach)) {
            String[] ruleAttachs = ruleAttach.split(";");
            List<String> ruleList = Arrays.asList(ruleAttachs);
            ruleAttList = new ArrayList<>(ruleList);
        }
        String[] ruleImages = request.getParameterValues("ruleImage");
        List<String> ruleResult;
        if (ruleImages != null && ruleImages.length > 0) {
            List<String> rmList = Arrays.asList(ruleImages);
            List<String> rImageList = new ArrayList<>(rmList);
            ruleResult = removeAttaach(ruleAttList, rImageList);
        }else{
            ruleResult=ruleAttList;
        }

        String[] reformImages = request.getParameterValues("reformImage");
        List<String> reformResult;
        if (reformImages != null && reformImages.length > 0) {
            List<String> rmList = Arrays.asList(reformImages);
            List<String> rImageList = new ArrayList<>(rmList);
            reformResult = removeAttaach(reformAttList, rImageList);
        }else{
            reformResult=reformAttList;
        }


        String newAttach1 = "";
        for (int i = 0; i < ruleResult.size(); i++) {
            if (StringUtil.isNotEmpty(ruleResult.get(i))) {
                newAttach1 += ruleResult.get(i);
                newAttach1 += ";";
            }
        }
        String newAttach2 = "";
        for (int i = 0; i < reformResult.size(); i++) {
            if (StringUtil.isNotEmpty(reformResult.get(i))) {
                newAttach2 += reformResult.get(i);
                newAttach2 += ";";
            }
        }


        Map<String, String> peccancyAttachs = uploadAttachment2(multipartRequest, "peccancy");
        if (peccancyAttachs != null && peccancyAttachs.size() > 0) {
            for (String key : peccancyAttachs.keySet()) {
                if (StringUtil.isNotEmpty(key) && key.startsWith("rule_")) {
                    newAttach1 = newAttach1 + peccancyAttachs.get(key) + ";";
                }
            }
        }
        if (peccancyAttachs != null && peccancyAttachs.size() > 0) {
            for (String key : peccancyAttachs.keySet()) {
                if (StringUtil.isNotEmpty(key) && key.startsWith("reform_")) {
                    newAttach2 = newAttach2 + peccancyAttachs.get(key) + ";";
                }
            }
        }
        peccancyDetail.setAttachment(newAttach1);
        peccancyDetail.setReformAttach(newAttach2);
        PeccancyDetail peccancy = (PeccancyDetail) this.peccancyDetailManager.save(peccancyDetail);
        PeccancyVo peccancyVo = this.peccancyDetailManager.getPeccancyVo(peccancy.getPeccancyId());
        modelMap.addAttribute("peccancyDetail", peccancyVo);
        return "peccancy/editDetail";
    }


    @RequestMapping(params = "method=delete")
    @ResponseBody
    public String delete(HttpServletRequest request) {
        String Id = request.getParameter("peccancyId");
        PeccancyDetail detail = (PeccancyDetail) this.peccancyDetailManager.get(Integer.parseInt(Id));
        detail.setValid(false);
        this.peccancyDetailManager.save(detail);
        return "success";
    }

    @RequestMapping(params = "method=exportExcel")
    public String exportExcel(HttpServletRequest request, HttpServletResponse response) throws Exception {
        try {
            String[] queryString = new String[2];
            queryString[0] = "from PeccancyDetail peccancy where 1=1 and valid=1";
            queryString[1] = "select count(peccancyId) from PeccancyDetail peccancy where 1=1 and valid=1";
            String[] params=getSearchParams(request);
            queryString = this.peccancyDetailManager.generateQueryString(queryString, params);
            String page = request.getParameter("page");        //当前页
            String rowsNum = request.getParameter("rows");    //每页显示的行数
            Pages pages = new Pages(request);
            pages.setPage(Integer.valueOf(page));
            pages.setPerPageNum(Integer.valueOf(rowsNum));

            PageList pl = this.peccancyDetailManager.getResultByQueryString(queryString[0], queryString[1], true, pages);
            List<PeccancyDetail> list = pl.getObjectList();
            //转为以用户名为索引，计数总次数
            /******************导出Excel********************/
            String filePath = "/" + CoreConstant.Attachment_Path + "reformList/";
            String fileTitle = "违章列表";

            ExcelObject object = new ExcelObject();
            object.setFilePath(filePath);
            object.setFileName(fileTitle);
            object.setTitle(fileTitle);

            List rowName = new ArrayList();
            String[][] data = new String[6][list.size()];
            int k = 6;
            rowName.add("序号");
            rowName.add("违章时间");
            rowName.add("违章描述");
            rowName.add("责任部门");
            rowName.add("责任人");
            rowName.add("发现人");
            for (int i = 0; i < list.size(); i++) {
                PeccancyDetail peccancyDetail= list.get(i);

                PeccancyVo peccancyVo = this.peccancyDetailManager.getPeccancyVo(peccancyDetail.getPeccancyId());
                data[0][i] = String.valueOf(i + 1);
                data[1][i] = peccancyVo.getFindTime();
                data[2][i] = peccancyVo.getContent();
                data[3][i] = peccancyVo.getDutyDeptName();
                data[4][i] = peccancyVo.getDutyPersonName();
                data[5][i] = peccancyVo.getOffenderName();
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