package com.kwchina.work.monthReport.controller;

import com.kwchina.core.base.service.DepartmentManager;
import com.kwchina.core.common.controller.BasicController;
import com.kwchina.core.util.DateHelper;
import com.kwchina.core.util.string.StringUtil;
import com.kwchina.work.errorWork.utils.convert.Convert;
import com.kwchina.work.monthReport.entity.Accident;
import com.kwchina.work.monthReport.entity.Focus;
import com.kwchina.work.monthReport.entity.Issue;
import com.kwchina.work.monthReport.service.AccidentManage;
import com.kwchina.work.monthReport.service.FocusManage;
import com.kwchina.work.monthReport.service.IssueManage;
import com.kwchina.work.monthReport.vo.*;
import com.kwchina.work.peccancy.service.PeccancyDetailManager;
import com.kwchina.work.reform.entity.ChildCategory;
import com.kwchina.work.reform.entity.ParentCategory;
import com.kwchina.work.reform.service.ChildCategoryManager;
import com.kwchina.work.reform.service.ParentCategoryManager;
import com.kwchina.work.reform.service.ReformManager;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.support.DefaultMultipartHttpServletRequest;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("/monthReport.htm")
public class MonthController extends BasicController {
    Logger logger = Logger.getLogger(MonthController.class);
    @Resource
    private AccidentManage accidentManage;
    @Resource
    private DepartmentManager departmentManager;
    @Resource
    private ChildCategoryManager childCategoryManager;
    @Resource
    private IssueManage issueManage;
    @Resource
    private ReformManager reformManager;
    @Resource
    private PeccancyDetailManager peccancyDetailManager;
    @Resource
    private ParentCategoryManager parentCategoryManager;
    @Resource
    private FocusManage focusManage;

    //保存交班重点
    @RequestMapping(params = "method=saveAccident")
    public String saveAccident(AccidentVO accidentVO, DefaultMultipartHttpServletRequest multipartRequest) throws Exception {
        Accident accident = new Accident();
        accident.setAccidentTime(Convert.stringToDate(accidentVO.getTime(), "yyyy-MM-dd"));
        accident.setAccidentAddress(accidentVO.getAddress());
        logger.info(accidentVO.getDepartmentName());
        accident.setDutyDept(this.departmentManager.getDepartmentByName(accidentVO.getDepartmentName()));
        accident.setContent(accidentVO.getContent());
        String attach = this.uploadAttachmentBySize(multipartRequest, "accident");
        accident.setAccidentAttach(attach);
        accident.setValid(true);
        this.accidentManage.save(accident);
        return "core/success";
    }

    @RequestMapping(params = "method=saveIssue")
    public String saveIssue(IssueVO issueVO) throws Exception {
        Issue issue = new Issue();
        issue.setCategory(this.childCategoryManager.getChildCategoryByCategoryId(issueVO.getCategoryId()));
        issue.setContent(issueVO.getContent());
        issue.setMonth(issueVO.getMonth());
        issue.setYear(issueVO.getYear());
        issue.setValid(true);
        this.issueManage.save(issue);
        return "core/success";
    }

    @RequestMapping(params = "method=showIssue", method = RequestMethod.POST)
    @ResponseBody
    public String showIssue(HttpServletRequest request) throws Exception {
        String year = request.getParameter("year");
        String id = request.getParameter("categoryId");
        String month = request.getParameter("month");
        Integer categoryId = Integer.parseInt(id);
        Issue issue = this.issueManage.getIssue(year, categoryId, month);
        String content = "";
        if (issue != null) {
            content = issue.getContent();
        }
        return content;
    }

    @RequestMapping(params = "method=monthList")
    public String morningList(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        List<AccidentVO> accidentVOS = new ArrayList<>();
        List<SafeCheckVO> safeCheckVOS = new ArrayList<>();
        List<DetailVO> detailVOS = new ArrayList<>();
        List<Integer> peccancyNow = new ArrayList<>();
        List<Integer> peccancylast = new ArrayList<>();
        List<Integer> reformNow = new ArrayList<>();
        List<Integer> reformLast = new ArrayList<>();
        List<Integer> categoryLast = new ArrayList<>();
        List<Integer> categoryNow = new ArrayList<>();
        String dataDateStr = request.getParameter("dataDateStr");
        String yearStr;
        String nowYearMonth;
        String lastYearMonth;
        String lastDataDateStr;
        SimpleDateFormat ymSf = new SimpleDateFormat("yyyy-MM");
        if (StringUtil.isNotEmpty(dataDateStr)) {
            Date date = Convert.stringToDate(dataDateStr, "yyyy-MM");
            Calendar c = Calendar.getInstance();
            c.setTime(date);
            yearStr = String.valueOf(c.get(Calendar.YEAR));
            nowYearMonth = String.valueOf(c.get(Calendar.MONTH) + 1) + "月";
            c.set(Calendar.MONTH, c.get(Calendar.MONTH) - 1);
            lastYearMonth = String.valueOf(c.get(Calendar.MONTH) + 1) + "月";
            lastDataDateStr = ymSf.format(c.getTime());
        } else {
            Date date = new Date();
            String format = ymSf.format(date);
            Date date1 = Convert.stringToDate(format, "yyyy-MM");
            Calendar cale = Calendar.getInstance();
            cale.setTime(date1);
            int year = cale.get(Calendar.YEAR);
            int month = cale.get(Calendar.MONTH) + 1;
            cale.set(Calendar.MONTH, cale.get(Calendar.MONTH) - 1);
            int lastMonth = cale.get(Calendar.MONTH) + 1;
            yearStr = String.valueOf(year);
            nowYearMonth = String.valueOf(month) + "月";
            lastYearMonth = String.valueOf(lastMonth) + "月";
            dataDateStr = ymSf.format(new Date());
            lastDataDateStr = ymSf.format(cale.getTime());
        }
        String monthBeginDate = DateHelper.getString(DateHelper.getFirstBeginDateOfMonth(dataDateStr + "-01"));
        String monthEndDate = DateHelper.getString(DateHelper.getFirstEndDateOfMonth(dataDateStr + "-01"));

        String monthBeginTime = monthBeginDate + " 00:00:00";
        String monthEndTime = monthEndDate + " 23:59:59";

        String monthBeginDate1 = DateHelper.getString(DateHelper.getFirstBeginDateOfMonth(lastDataDateStr + "-01"));
        String monthEndDate1 = DateHelper.getString(DateHelper.getFirstEndDateOfMonth(lastDataDateStr + "-01"));

        String monthBeginTime1 = monthBeginDate1 + " 00:00:00";
        String monthEndTime1 = monthEndDate1 + " 23:59:59";
        /**********生产事故*********/
        String accidentHql = " from Accident detail where valid=1 and accidentTime >= '" + monthBeginTime + "' and accidentTime <= '" + monthEndTime + "' order by accidentTime";
        List<Accident> monthAccidents = this.accidentManage.getResultByQueryString(accidentHql);
        SimpleDateFormat mdSf = new SimpleDateFormat("MM-dd");
        for (Accident accident : monthAccidents) {
            AccidentVO accidentVo = this.accidentManage.getAccidentVo(accident.getAccidentId());
            accidentVOS.add(accidentVo);
        }
        /**********安检汇总*********/
        List<String> projectNames = new ArrayList<String>();
        projectNames.add("整改单");
        projectNames.add("码头运营部");
        projectNames.add("整车物流部");
        projectNames.add("零部件物流部");
        projectNames.add("技术规划部");
        projectNames.add("隐患及违章数");
        int zgCount1 = 0;
        int wzCount1 = 0;
        int zgCount2 = 0;
        int wzCount2 = 0;
        int zgOther1 = 0;
        int zgOther2 = 0;
        int wzOther1 = 0;
        int wzOther2 = 0;
        for (int i = 0; i < projectNames.size(); i++) {
            if ("隐患及违章数".equals(projectNames.get(i))) {
                String zgHql1 = "from ReformDetail where valid=1 and findTime>= '" + monthBeginTime + "' and findTime <= '" + monthEndTime + "'";
                String zgHql2 = "from ReformDetail where valid=1 and findTime>= '" + monthBeginTime1 + "' and findTime <= '" + monthEndTime1 + "'";
                int zgNowCount = this.reformManager.getResultByQueryString(zgHql1).size();
                int zgLastCount = this.reformManager.getResultByQueryString(zgHql2).size();
                SafeCheckVO checkVO = new SafeCheckVO();
                checkVO.setProjectName("隐患及违章数");
                checkVO.setNowCount(zgNowCount);
                checkVO.setLastCount(zgLastCount);
                checkVO.setRingCount(zgNowCount - zgLastCount);
                checkVO.setOrderNo(6);
                safeCheckVOS.add(checkVO);
                zgCount1 = zgNowCount;
                zgCount2 = zgLastCount;
            } else if ("整改单".equals(projectNames.get(i))) {
                String hql1 = "from PeccancyDetail where valid=1 and markTime>= '" + monthBeginTime + "' and markTime <= '" + monthEndTime + "'";
                String hql2 = "from PeccancyDetail where valid=1 and markTime>= '" + monthBeginTime1 + "' and markTime <= '" + monthEndTime1 + "'";
                int nowCount = this.peccancyDetailManager.getResultByQueryString(hql1).size();
                int lastCount = this.peccancyDetailManager.getResultByQueryString(hql2).size();
                SafeCheckVO checkVO = new SafeCheckVO();
                checkVO.setProjectName("整改单");
                checkVO.setNowCount(nowCount);
                checkVO.setLastCount(lastCount);
                checkVO.setRingCount(nowCount - lastCount);
                checkVO.setOrderNo(0);
                safeCheckVOS.add(checkVO);
                wzCount1 = nowCount;
                wzCount2 = lastCount;
            } else {
                String zgHql1 = "from ReformDetail detail where valid=1 and detail.department.departmentName='" + projectNames.get(i) + "' and findTime>= '" + monthBeginTime + "' and findTime <= '" + monthEndTime + "'";
                String zgHql2 = "from ReformDetail detail where valid=1 and detail.department.departmentName='" + projectNames.get(i) + "' and findTime>= '" + monthBeginTime1 + "' and findTime <= '" + monthEndTime1 + "'";
                int zgNowCount = this.reformManager.getResultByQueryString(zgHql1).size();
                int zgLastCount = this.reformManager.getResultByQueryString(zgHql2).size();
                SafeCheckVO checkVO = new SafeCheckVO();
                checkVO.setProjectName(projectNames.get(i));
                checkVO.setNowCount(zgNowCount);
                checkVO.setLastCount(zgLastCount);
                checkVO.setRingCount(zgNowCount - zgLastCount);
                checkVO.setOrderNo(i + 6);
                safeCheckVOS.add(checkVO);
                zgOther1 += zgNowCount;
                zgOther2 += zgLastCount;
                String hql1 = "from PeccancyDetail detail where valid=1 and detail.department.departmentName='" + projectNames.get(i) + "' and  markTime>= '" + monthBeginTime + "' and markTime <= '" + monthEndTime + "'";
                String hql2 = "from PeccancyDetail detail where valid=1 and detail.department.departmentName='" + projectNames.get(i) + "' and  markTime>= '" + monthBeginTime1 + "' and markTime <= '" + monthEndTime1 + "'";
                int nowCount = this.peccancyDetailManager.getResultByQueryString(hql1).size();
                int lastCount = this.peccancyDetailManager.getResultByQueryString(hql2).size();
                SafeCheckVO checkVO1 = new SafeCheckVO();
                checkVO1.setProjectName(projectNames.get(i));
                checkVO1.setNowCount(nowCount);
                checkVO1.setLastCount(lastCount);
                checkVO1.setRingCount(nowCount - lastCount);
                checkVO1.setOrderNo(i);
                safeCheckVOS.add(checkVO1);
                wzOther1 += nowCount;
                wzOther2 += lastCount;
            }
        }
        SafeCheckVO zgOther = new SafeCheckVO();
        zgOther.setProjectName("其他");
        zgOther.setNowCount(zgCount1 - zgOther1);
        zgOther.setLastCount(zgCount2 - zgOther2);
        zgOther.setRingCount(zgCount1 - zgOther1 - zgCount2 + zgOther2);
        zgOther.setOrderNo(11);
        safeCheckVOS.add(zgOther);
        SafeCheckVO wzOther = new SafeCheckVO();
        wzOther.setProjectName("其他");
        wzOther.setNowCount(wzCount1 - wzOther1);
        wzOther.setLastCount(wzCount2 - wzOther2);
        wzOther.setRingCount(wzCount1 - wzOther1 - wzCount2 + wzOther2);
        wzOther.setOrderNo(5);
        safeCheckVOS.add(wzOther);

        /**********隐患违章明细*********/

        List<ParentCategory> allCategory = this.parentCategoryManager.getAllValid();
        for (int i = 0; i < allCategory.size(); i++) {
            DetailVO detailVO = new DetailVO();
            detailVO.setBigName(allCategory.get(i).getPName());
            List<CountVO> countVOS = new ArrayList<>();
            List<ChildCategory> childCategories = this.childCategoryManager.getChildCategoriesByParentId(allCategory.get(i).getPId());
            for (int j = 0; j < childCategories.size(); j++) {
                CountVO countVO = new CountVO();
                countVO.setCategoryName(childCategories.get(j).getCategoryName());
                String hql1 = "from ReformDetail detail where valid=1 and detail.errorTypeId='" + childCategories.get(j).getCategoryId() + "' and  findTime>= '" + monthBeginTime + "' and findTime <= '" + monthEndTime + "'";
                String hql2 = "from ReformDetail detail where valid=1 and detail.errorTypeId='" + childCategories.get(j).getCategoryId() + "' and  findTime>= '" + monthBeginTime1 + "' and findTime <= '" + monthEndTime1 + "'";
                int nowCount = this.reformManager.getResultByQueryString(hql1).size();
                int lastCount = this.reformManager.getResultByQueryString(hql2).size();
                countVO.setNowCount(nowCount);
                countVO.setLastCount(lastCount);
                countVO.setRingCount(nowCount - lastCount);
                Issue issue = this.issueManage.getIssue(yearStr, childCategories.get(j).getCategoryId(), nowYearMonth);
                if (issue != null) {
                    countVO.setIssueDetail(issue.getContent());
                }
                countVOS.add(countVO);
            }
            detailVO.setCountVOS(countVOS);
            detailVOS.add(detailVO);
        }
        /*******************上月反馈****************/
        Focus focus = this.focusManage.getFocus(lastDataDateStr, 1);
        FocusVO vo = null;
        if (focus != null) {
            vo = this.focusManage.getVoById(focus.getFocusId());
        }


        /*******************本月重点****************/
        Focus focus1 = this.focusManage.getFocus(dataDateStr, 2);
        FocusVO vo1 = null;
        if (focus1 != null) {
            vo1 = this.focusManage.getVoById(focus1.getFocusId());
        }

        Collections.sort(safeCheckVOS);
        for (int i = 0; i < safeCheckVOS.size(); i++) {
            if (i < 6) {
                peccancyNow.add(safeCheckVOS.get(i).getNowCount());
                peccancylast.add(safeCheckVOS.get(i).getLastCount());
            } else if (i < 12) {
                reformNow.add(safeCheckVOS.get(i).getNowCount());
                reformLast.add(safeCheckVOS.get(i).getLastCount());
            }
        }
        for (int i = 0; i < detailVOS.size(); i++) {
            int last = 0;
            int now = 0;
            for (CountVO countVO : detailVOS.get(i).getCountVOS()) {
                last += countVO.getLastCount();
                now += countVO.getNowCount();
            }
            categoryLast.add(last);
            categoryNow.add(now);
        }

        model.addAttribute("nowYearMonth", nowYearMonth);
        model.addAttribute("lastYearMonth", lastYearMonth);
        model.addAttribute("dataDateStr", dataDateStr);
        model.addAttribute("accidents", accidentVOS);
        model.addAttribute("safeChecks", safeCheckVOS);
        model.addAttribute("details", detailVOS);
        model.addAttribute("feedbackVo", vo);
        model.addAttribute("checkFocusVo", vo1);
        model.addAttribute("pNow", peccancyNow);
        model.addAttribute("pLast", peccancylast);
        model.addAttribute("rNow", reformNow);
        model.addAttribute("rLast", reformLast);
        model.addAttribute("cNow", categoryNow);
        model.addAttribute("cLast", categoryLast);
        return "/monthReport/safeCheck";
    }

    /******上月反馈*******/
    @RequestMapping(params = "method=editFeedBack")
    public String editFeedBack(Model model, HttpServletRequest request) throws Exception {
        String lastMonth = request.getParameter("month");
        SimpleDateFormat ymSf = new SimpleDateFormat("yyyy-MM");
        if (!StringUtil.isNotEmpty(lastMonth)) {
            Date date = new Date();
            String format = ymSf.format(date);
            Date date1 = Convert.stringToDate(format, "yyyy-MM");
            Calendar cale = Calendar.getInstance();
            cale.setTime(date1);
            cale.set(Calendar.MONTH, cale.get(Calendar.MONTH) - 1);
            lastMonth = ymSf.format(cale.getTime());
        }
        Focus focus = this.focusManage.getFocus(lastMonth, 1);
        FocusVO vo = null;
        if (focus != null) {
            vo = this.focusManage.getVoById(focus.getFocusId());
            model.addAttribute("time", vo.getTime());
        } else {
            model.addAttribute("time", lastMonth);
        }
        model.addAttribute("feedback", vo);
        return "/monthReport/feedback";
    }

    /******上月反馈保存*******/
    @RequestMapping(params = "method=safeFeedBack")
    public String safeFeedBack(HttpServletRequest request, DefaultMultipartHttpServletRequest multipartRequest) {
        try {
            String time = request.getParameter("time");
            String id = request.getParameter("focusId");
            String feedback = this.uploadAttachmentBySize(multipartRequest, "feedback");
            String[] contents = request.getParameterValues("content");
            String[] problems = request.getParameterValues("problem");
            String[] images = request.getParameterValues("image");
            String s = "";
            Focus focus;
            if (contents != null && contents.length > 0) {
                for (int i = 0; i < contents.length; i++) {
                    if (StringUtil.isNotEmpty(contents[i]) && StringUtil.isNotEmpty(problems[i])) {
                        s += contents[i] + "&%&" + problems[i];
                        s += "&#&";
                    }
                }
            }
            if (StringUtil.isNotEmpty(id)) {
                focus = (Focus) this.focusManage.get(Integer.parseInt(id));

                String attach = focus.getAttach();
                String[] attachs = attach.split(";");


                List<String> list = Arrays.asList(attachs);
                List<String> allList = new ArrayList<>(list);

                List<String> atts = null;
                if (images != null && images.length > 0) {
                    List<String> removeDel = Arrays.asList(images);
                    List<String> delAttachs = new ArrayList<>(removeDel);
                    atts = removeAttaach(allList, delAttachs);
                } else {
                    atts = allList;
                }

                String newAttach = "";
                for (int i = 0; i < atts.size(); i++) {
                    if (StringUtil.isNotEmpty(atts.get(i))) {
                        newAttach += atts.get(i);
                        newAttach += ";";
                    }
                }
                newAttach += feedback;
                focus.setAttach(newAttach);
            } else {
                focus = new Focus();
                focus.setAttach(feedback);
            }
            focus.setType(1);
            focus.setContent(s);
            focus.setValid(true);
            focus.setRecordTime(Convert.stringToDate(time, "yyyy-MM"));
            this.focusManage.save(focus);
            return "/core/success";
        } catch (Exception e) {
            e.printStackTrace();
            return "/core/error";
        }
    }

    /******本月检查重点*******/
    @RequestMapping(params = "method=editCheckFocus")
    public String editCheckFocus(Model model, HttpServletRequest request) throws Exception {
        String nowMonth = request.getParameter("month");
        if (!StringUtil.isNotEmpty(nowMonth)) {
            SimpleDateFormat ymsf = new SimpleDateFormat("yyyy-MM");
            Calendar cale = Calendar.getInstance();
            nowMonth = ymsf.format(cale.getTime());
        }
        Focus focus = this.focusManage.getFocus(nowMonth, 2);
        FocusVO vo = null;
        if (focus != null) {
            vo = this.focusManage.getVoById(focus.getFocusId());
            model.addAttribute("time", vo.getTime());
        } else {
            model.addAttribute("time", nowMonth);
        }
        model.addAttribute("checkFocus", vo);
        return "/monthReport/checkFocus";
    }

    /******本月检查重点保存*******/
    @RequestMapping(params = "method=safeCheckFocus")
    public String safeCheckFocus(HttpServletRequest request, DefaultMultipartHttpServletRequest multipartRequest) throws Exception {
        String time = request.getParameter("time");
        String id = request.getParameter("focusId");
        String checkAttach = this.uploadAttachmentBySize(multipartRequest, "checkFocus");
        String[] contents = request.getParameterValues("content");
        String[] problems = request.getParameterValues("problem");
        String[] images = request.getParameterValues("image");
        String s = "";
        Focus focus;
        for (int i = 0; i < contents.length; i++) {
            if (StringUtil.isNotEmpty(contents[i]) && StringUtil.isNotEmpty(problems[i])) {
                s += contents[i] + "&%&" + problems[i];
                s += "&#&";
            }
        }
        if (StringUtil.isNotEmpty(id)) {
            focus = (Focus) this.focusManage.get(Integer.parseInt(id));
            String attach = focus.getAttach();
            String[] attachs = attach.split(";");
            List<String> alist = Arrays.asList(attachs);
            List<String> attList = new ArrayList<>(alist);
            List<String> result;
            if (images != null && images.length > 0) {
                List<String> mList = Arrays.asList(images);
                List<String> imageList = new ArrayList<>(mList);
                result = removeAttaach(attList, imageList);

            } else {
                result = attList;
            }
            String newAttach = "";
            for (int i = 0; i < result.size(); i++) {
                if (StringUtil.isNotEmpty(result.get(i))) {
                    newAttach += result.get(i);
                    newAttach += ";";
                }
            }
            newAttach += checkAttach;
            focus.setAttach(newAttach);
        } else {
            focus = new Focus();
            focus.setAttach(checkAttach);
        }
        focus.setType(2);
        focus.setValid(true);
        focus.setContent(s);
        focus.setRecordTime(Convert.stringToDate(time, "yyyy-MM"));
        this.focusManage.save(focus);
        return "/core/success";
    }

    /***
     * 删除生产事故
     * @throws Exception
     */
    @RequestMapping(params = "method=deleteAccident")
    @ResponseBody
    public String deleteAccident(HttpServletRequest request) {
        String accidentId = request.getParameter("accidentId");
        Accident accident = (Accident) this.accidentManage.get(Integer.parseInt(accidentId));
        accident.setValid(false);
        this.accidentManage.save(accident);
        return "success";
    }

    /***
     * 编辑生产事故
     * @throws Exception
     */
    @RequestMapping(params = "method=editAccident")
    public String editAccident(HttpServletRequest request, ModelMap modelMap) {
        String accidentId = request.getParameter("accidentId");
        AccidentVO vo = this.accidentManage.getAccidentVo(Integer.parseInt(accidentId));
        modelMap.addAttribute("accidentVo", vo);
        return "monthReport/editAccident";

    }

    /***
     * 保存修改
     * @throws Exception
     */
    @RequestMapping(params = "method=saveEdit")
    public String saveEdit(AccidentVO accidentVO, ModelMap modelMap, HttpServletRequest request, DefaultMultipartHttpServletRequest multipartRequest) {
        Accident accident = (Accident) this.accidentManage.get(accidentVO.getAccidentId());
        accident.setContent(accidentVO.getContent());
        accident.setAccidentAddress(accidentVO.getAddress());
        accident.setDutyDept(this.departmentManager.getDepartmentByName(accidentVO.getDepartmentName()));
        accident.setAccidentTime(Convert.stringToDate(accidentVO.getTime(), "yyyy-MM-dd HH:mm:ss"));
        String attach = accident.getAccidentAttach();
        List<String> attList = new ArrayList<>();
        if (StringUtil.isNotEmpty(attach)) {
            String[] attachs = attach.split(";");
            List<String> list = Arrays.asList(attachs);
            attList = new ArrayList<>(list);
        }
        String[] images = request.getParameterValues("image");
        List<String> result;
        if(images!=null&&images.length>0){
            List<String> mList = Arrays.asList(images);
            List<String> imageList=new ArrayList<>(mList);
            result=removeAttaach(attList,imageList);
        }else{
            result=attList;
        }
        String newAttach = "";
        for (int i = 0; i < result.size(); i++) {
            if (StringUtil.isNotEmpty(result.get(i))) {
                newAttach += result.get(i);
                newAttach += ";";
            }
        }
        newAttach += this.uploadAttachmentBySize(multipartRequest, "accident");
        accident.setAccidentAttach(newAttach);

        Accident detail = (Accident) this.accidentManage.save(accident);
        AccidentVO vo = this.accidentManage.getAccidentVo(detail.getAccidentId());
        modelMap.addAttribute("accidentVo", vo);
        return "/monthReport/editAccident";
    }

    /******删除*******/
    @RequestMapping(params = "method=deleteFocus")
    @ResponseBody
    public String deleteFocus(HttpServletRequest request) throws Exception {
        String id = request.getParameter("focusId");
        Focus focus = (Focus) this.focusManage.get(Integer.parseInt(id));
        focus.setValid(false);
        this.focusManage.save(focus);
        return "success";
    }


}

