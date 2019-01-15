package com.kwchina.work.train.controller;

import com.kwchina.core.common.controller.BasicController;
import com.kwchina.core.sys.CoreConstant;
import com.kwchina.core.util.DateHelper;
import com.kwchina.core.util.string.StringUtil;
import com.kwchina.work.errorWork.utils.convert.Convert;
import com.kwchina.work.reform.entity.ReformDetail;
import com.kwchina.work.sys.SysCommonMethod;
import com.kwchina.work.train.entity.Train;
import com.kwchina.work.train.entity.TrainCategory;
import com.kwchina.work.train.enums.TrainDeptEnum;
import com.kwchina.work.train.service.TrainCategoryManager;
import com.kwchina.work.train.service.TrainManager;
import com.kwchina.work.train.vo.TrainListVo;
import com.kwchina.work.train.vo.TrainVo;
import com.kwchina.work.util.EnumUtil;
import net.sf.json.JSONObject;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
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
@RequestMapping("/train.htm")
public class TrainController extends BasicController {
    @Resource
    private TrainCategoryManager trainCategoryManager;
    @Resource
    private TrainManager trainManager;

    /**
     * 编辑培训次数
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(params = "method=addTrain")
    public String addTrain(TrainVo trainVo, HttpServletRequest request, DefaultMultipartHttpServletRequest multipartRequest) {
        try {
            Train train;
            if (trainVo.getTrainId() != null) {
                train = (Train) this.trainManager.get(trainVo.getTrainId());
                train.setDepartmentNum(trainVo.getDepartmentNum());
                train.setDepartmentName(trainVo.getDepartmentName());
                train.setPersonCount(trainVo.getPersonCount());
                train.setTrainTime(Convert.stringToDate(trainVo.getTrainTime(), "yyyy-MM-dd HH:mm:ss"));
                train.setTrainerName(trainVo.getTrainerName());
                train.setTitle(trainVo.getTitle());

                String attach = train.getAttach();
                List<String> attList = new ArrayList<>();
                if (StringUtil.isNotEmpty(attach)) {
                    String[] attachs = attach.split(";");
                    List<String> list = Arrays.asList(attachs);
                    attList = new ArrayList<>(list);
                }
                String[] images = request.getParameterValues("image");
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
                newAttach += this.uploadAttachmentBySize(multipartRequest, "train");
                train.setAttach(newAttach);
                TrainCategory trainCategory = (TrainCategory) this.trainCategoryManager.get(trainVo.getCategoryId());
                train.setCategory(trainCategory);
            } else {
                train = new Train();
                train.setDepartmentNum(trainVo.getDepartmentNum());
                train.setDepartmentName(trainVo.getDepartmentName());
                train.setPersonCount(trainVo.getPersonCount());
                train.setTrainTime(Convert.stringToDate(trainVo.getTrainTime(), "yyyy-MM-dd HH:mm:ss"));
                train.setTrainerName(trainVo.getTrainerName());
                train.setValid(true);
                train.setTitle(trainVo.getTitle());
                train.setAttach(this.uploadAttachmentBySize(multipartRequest, "train"));
                TrainCategory trainCategory = (TrainCategory) this.trainCategoryManager.get(trainVo.getCategoryId());
                train.setCategory(trainCategory);
                train.setRecordTime(new Date());
                train.setRecorder(SysCommonMethod.getSystemUser(request));
            }
            this.trainManager.save(train);
            return "/core/success";
        } catch (BeansException e) {
            e.printStackTrace();
            return "/core/error";
        } catch (DataAccessException e) {
            e.printStackTrace();
            return "/core/error";
        }
    }

    /**
     * 培训报表
     *
     * @return
     * @throws Exception
     */

    @RequestMapping(params = "method=trainReport")
    public String trainReport(HttpServletRequest request, ModelMap modelMap) {
        List<TrainListVo> vos = new ArrayList<>();
        String year = request.getParameter("year");
        if (!StringUtil.isNotEmpty(year)) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
            year = sdf.format(new Date());
        }
        for (int i = 1; i < 13; i++) {
            TrainListVo trainListVo = new TrainListVo();
            String dataDateStr = year + "-" + (i < 10 ? "0" + i : i);
            trainListVo.setMonth(i + "月");
            String monthBeginDate = DateHelper.getString(DateHelper.getFirstBeginDateOfMonth(dataDateStr + "-01"));
            String monthEndDate = DateHelper.getString(DateHelper.getFirstEndDateOfMonth(dataDateStr + "-01"));

            String monthBeginTime = monthBeginDate + " 00:00:00";
            String monthEndTime = monthEndDate + " 23:59:59";
            String hql1 = "from Train where valid=1 and departmentNum=1 and trainTime>= '" + monthBeginTime + "' and trainTime <= '" + monthEndTime + "' order by trainTime";
            String hql2 = "from Train where valid=1 and departmentNum=2 and trainTime>= '" + monthBeginTime + "' and trainTime <= '" + monthEndTime + "' order by trainTime";
            String hql3 = "from Train where valid=1 and departmentNum=3 and trainTime>= '" + monthBeginTime + "' and trainTime <= '" + monthEndTime + "' order by trainTime";
            String hql4 = "from Train where valid=1 and departmentNum=4 and trainTime>= '" + monthBeginTime + "' and trainTime <= '" + monthEndTime + "' order by trainTime";
            String hql5 = "from Train where valid=1 and departmentNum=5 and trainTime>= '" + monthBeginTime + "' and trainTime <= '" + monthEndTime + "' order by trainTime";
            String hql6 = "from Train where valid=1 and departmentNum=6 and trainTime>= '" + monthBeginTime + "' and trainTime <= '" + monthEndTime + "' order by trainTime";
            int c1 = 0, c2 = 0, c3 = 0, c4 = 0, c5 = 0, c6 = 0, all = 0;
            List<Train> result1 = this.trainManager.getResultByQueryString(hql1);
            List<Train> result2 = this.trainManager.getResultByQueryString(hql2);
            List<Train> result3 = this.trainManager.getResultByQueryString(hql3);
            List<Train> result4 = this.trainManager.getResultByQueryString(hql4);
            List<Train> result5 = this.trainManager.getResultByQueryString(hql5);
            List<Train> result6 = this.trainManager.getResultByQueryString(hql6);
            for (Train train : result1) {
                c1 += train.getPersonCount();
            }
            for (Train train : result2) {
                c2 += train.getPersonCount();
            }
            for (Train train : result3) {
                c3 += train.getPersonCount();
            }
            for (Train train : result4) {
                c4 += train.getPersonCount();
            }
            for (Train train : result5) {
                c5 += train.getPersonCount();
            }
            for (Train train : result6) {
                c6 += train.getPersonCount();
            }
            all = c1 + c2 + c3 + c4 + c5 + c6;
            trainListVo.setDcCount(c1);
            trainListVo.setDlCount(c2);
            trainListVo.setWlCount(c3);
            trainListVo.setNetCount(c4);
            trainListVo.setUnitCount(c5);
            trainListVo.setOtherCount(c6);
            trainListVo.setAllCount(all);
            vos.add(trainListVo);
        }
        int ca1 = 0, ca2 = 0, ca3 = 0, ca4 = 0, ca5 = 0, ca6 = 0, c_all = 0;
        for (TrainListVo vo : vos) {
            ca1 += vo.getDcCount();
            ca2 += vo.getDlCount();
            ca3 += vo.getWlCount();
            ca4 += vo.getNetCount();
            ca5 += vo.getUnitCount();
            ca6 += vo.getOtherCount();
            c_all += vo.getAllCount();
        }
        TrainListVo listVo = new TrainListVo();
        listVo.setMonth("总数");
        listVo.setDcCount(ca1);
        listVo.setDlCount(ca2);
        listVo.setWlCount(ca3);
        listVo.setNetCount(ca4);
        listVo.setUnitCount(ca5);
        listVo.setOtherCount(ca6);
        listVo.setAllCount(c_all);
        vos.add(listVo);
        modelMap.addAttribute("trainList", vos);
        modelMap.addAttribute("year", year);
        return "train/trainReport";
    }

    /**
     * 获取首页需要显示的数据(柱状图)
     *
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(params = "method=getYearTrainData")
    public void getYearTrainData(HttpServletRequest request, HttpServletResponse response) throws Exception {

        JSONObject jsonObj = new JSONObject();


        List<Integer> pxCountList = new ArrayList<Integer>();  //培训量
        String currentYearStr = request.getParameter("currentYear");
        int thisYear = 0;
        int realYear = 0;
        //默认显示当年
        java.util.Date now = new java.util.Date();
        SimpleDateFormat sf_y = new SimpleDateFormat("yyyy");
        realYear = Integer.valueOf(sf_y.format(now));

        if (StringUtil.isNotEmpty(currentYearStr)) {

            thisYear = Integer.valueOf(currentYearStr);

        } else {
            thisYear = realYear;
        }
        jsonObj.put("_Year", thisYear);
        jsonObj.put("_RealYear", realYear);
        String thisYearBegin = thisYear + "-01-01 00:00:00";
        String thisYearEnd = thisYear + "-12-31 23:59:59";

        String categoryIdStr = request.getParameter("categoryId");

        try {
            if (StringUtil.isNotEmpty(categoryIdStr)) {
                int categoryId = Integer.valueOf(categoryIdStr);

                //得到年下的所有数据
                List<Train> resultList;
                if (categoryId == 0) {
                    String hql = "from Train where valid=1 and trainTime  >= '" + thisYearBegin + "' and trainTime <= '" + thisYearEnd + "'";
                    resultList = this.trainManager.getResultByQueryString(hql);
                } else {
                    String hql = "from Train train where valid=1 and train.category.categoryId = " + categoryId + " and trainTime >= '" + thisYearBegin + "' and trainTime <= '" + thisYearEnd + "'";
                    resultList = this.trainManager.getResultByQueryString(hql);
                }

                for (int i = 1; i < 13; i++) {
                    int pxCount = 0;
                    for (Train tmp : resultList) {
                        Calendar cal = Calendar.getInstance();
                        cal.setTime(tmp.getTrainTime());
                        if (cal.get(Calendar.MONTH) + 1 == i) {
                            pxCount++;
                        }
                    }
                    pxCountList.add(pxCount);
                }
            }
            jsonObj.put("_PxCountList", pxCountList);

            //设置字符编码
            response.setContentType(CoreConstant.CONTENT_TYPE);
            response.getWriter().print(jsonObj);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取首页需要显示的数据(饼图)
     *
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(params = "method=getMonthTrainData")
    public void getMonthTrainData(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String dataDateStr = request.getParameter("dataDate");  //格式为yyyy-MM
        SimpleDateFormat ymSf = new SimpleDateFormat("yyyy-MM");
        List<Integer> pxCountList_category = new ArrayList<>();    //培训类型总项
        List<String> cNames = new ArrayList<>();

        if (StringUtil.isNotEmpty(dataDateStr)) {
            String tag = request.getParameter("tag");
            if (StringUtil.isNotEmpty(tag)) {
                if (tag.equals("0")) {  //0为上一月
                    dataDateStr = ymSf.format(DateHelper.addMonth(DateHelper.getDate(dataDateStr + "-01"), -1));
                } else if (tag.equals("1")) {  //1为下一月
                    dataDateStr = ymSf.format(DateHelper.addMonth(DateHelper.getDate(dataDateStr + "-01"), 1));
                }
            } else {
                dataDateStr = ymSf.format(new java.util.Date());
            }

        } else {
            dataDateStr = ymSf.format(new java.util.Date());
        }

        //为空时，默认取当月
        if (!StringUtil.isNotEmpty(dataDateStr) || dataDateStr.toLowerCase().contains("undefined")) {
            dataDateStr = ymSf.format(new java.util.Date());
        }

        try {
            JSONObject jsonObj = new JSONObject();
            List<Train> resultList = new ArrayList<>();
            String monthBeginDate = DateHelper.getString(DateHelper.getFirstBeginDateOfMonth(dataDateStr + "-01"));
            String monthEndDate = DateHelper.getString(DateHelper.getFirstEndDateOfMonth(dataDateStr + "-01"));

            String monthBeginTime = monthBeginDate + " 00:00:00";
            String monthEndTime = monthEndDate + " 23:59:59";
            String hql = "from Train where valid=1 and trainTime >= '" + monthBeginTime + "' and trainTime <= '" + monthEndTime + "'";
            resultList = this.trainManager.getResultByQueryString(hql);

            /*********按类别统计*********/
            //得到所有类别
            List<TrainCategory> cList = this.trainCategoryManager.getAllCategories(1);


            if (cList != null && cList.size() > 0) {
                for (TrainCategory tmpCategory : cList) {
                    int pxCount = 0;
                    for (Train tmpTrain : resultList) {
                        if (tmpTrain.getCategory().getCategoryId() == tmpCategory.getCategoryId()) {
                            pxCount++;
                        }
                    }
                    pxCountList_category.add(pxCount);
                    cNames.add("'" + tmpCategory.getCategoryName() + "'");
                }
            }
            SimpleDateFormat sf = new SimpleDateFormat("yyyy年MM月");
            int allPxCount = resultList.size();
            jsonObj.put("_NowDate", dataDateStr);
            jsonObj.put("_NowYearMonth", sf.format(DateHelper.getDate(dataDateStr + "-01")));
            jsonObj.put("_AllPxCount", allPxCount);
            jsonObj.put("_PxCountList_category", pxCountList_category);
            jsonObj.put("_CategoryNames", cNames);

            //设置字符编码
            response.setContentType(CoreConstant.CONTENT_TYPE);
            response.getWriter().print(jsonObj);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取首页需要显示的数据(饼图)
     *
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(params = "method=getYearTrain")
    public void getYearTrain(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String dataDateStr = request.getParameter("dataDate");  //格式为yyyy-MM
        SimpleDateFormat ymSf = new SimpleDateFormat("yyyy");
        List<Integer> pxCountList_category = new ArrayList<>();    //培训类型总项
        List<String> cNames = new ArrayList<>();

        if (StringUtil.isNotEmpty(dataDateStr)) {
            String tag = request.getParameter("tag");
            if (StringUtil.isNotEmpty(tag)) {
                if (tag.equals("0")) {  //0为上一月
                    dataDateStr = ymSf.format(DateHelper.addYear(DateHelper.getDate(dataDateStr + "-01-01"), -1));
                } else if (tag.equals("1")) {  //1为下一月
                    dataDateStr = ymSf.format(DateHelper.addYear(DateHelper.getDate(dataDateStr + "-01-01"), 1));
                }
            } else {
                dataDateStr = ymSf.format(new java.util.Date());
            }

        } else {
            dataDateStr = ymSf.format(new java.util.Date());
        }

        //为空时，默认取当月
        if (!StringUtil.isNotEmpty(dataDateStr) || dataDateStr.toLowerCase().contains("undefined")) {
            dataDateStr = ymSf.format(new java.util.Date());
        }

        try {
            JSONObject jsonObj = new JSONObject();
            List<Train> resultList = new ArrayList<>();
            String beginDate = dataDateStr + "-01-01 00:00:00";
            String endDate = dataDateStr + "-12-31 23:59:59";

            String hql = "from Train where valid=1 and trainTime >= '" + beginDate + "' and trainTime <= '" + endDate + "'";
            resultList = this.trainManager.getResultByQueryString(hql);

            /*********按类别统计*********/
            //得到所有类别
            List<TrainCategory> cList = this.trainCategoryManager.getAllCategories(1);


            if (cList != null && cList.size() > 0) {
                for (TrainCategory tmpCategory : cList) {
                    int pxCount = 0;
                    for (Train tmpTrain : resultList) {
                        if (tmpTrain.getCategory().getCategoryId() == tmpCategory.getCategoryId()) {
                            pxCount++;
                        }
                    }
                    pxCountList_category.add(pxCount);
                    cNames.add("'" + tmpCategory.getCategoryName() + "'");
                }
            }
            int allPxCount = resultList.size();
            jsonObj.put("_NowYear", dataDateStr + "年");
            jsonObj.put("_NowDate", dataDateStr);
            jsonObj.put("_AllPxCount", allPxCount);
            jsonObj.put("_PxCountList_category", pxCountList_category);
            jsonObj.put("_CategoryNames", cNames);

            //设置字符编码
            response.setContentType(CoreConstant.CONTENT_TYPE);
            response.getWriter().print(jsonObj);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 培训报表
     *
     * @param request
     * @param
     * @throws Exception
     */
    @RequestMapping(params = "method=dailyReport")
    public String dailyReport(ModelMap modelMap, HttpServletRequest request) {
        List<TrainVo> trainVos = new ArrayList<>();
        int trainCount;

        String beginTime = request.getParameter("beginDate");
        String endTime = request.getParameter("endDate");
        String dept = request.getParameter("dept");
        String category = request.getParameter("category");
        Integer categoryId = null;
        Integer deptNum = null;
        Date date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_MONTH, 1);
        Date time = cal.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        if (!StringUtil.isNotEmpty(beginTime)) {
            beginTime = sdf.format(time);
        }
        if (!StringUtil.isNotEmpty(endTime)) {
            endTime = sdf.format(date);
        }
        modelMap.addAttribute("beginDate", beginTime);
        modelMap.addAttribute("endDate", endTime);
        if (StringUtil.isNotEmpty(dept)) {
            deptNum = Integer.valueOf(dept);
            modelMap.addAttribute("dept", EnumUtil.getByCode(deptNum, TrainDeptEnum.class).getMsg());
        }
        if (StringUtil.isNotEmpty(category)) {
            categoryId = Integer.parseInt(category);
            modelMap.addAttribute("category", this.trainCategoryManager.get(categoryId));
        }
        List allCategory = this.trainCategoryManager.getAllCategories(1);
        modelMap.addAttribute("_Category", allCategory);

        String hql = " from Train where valid=1 and trainTime >= '" + beginTime + " 00:00:00 'and trainTime <= '" + endTime + " 23:59:59' order by trainTime";
        @SuppressWarnings("unchecked")
        List<Train> errorList = this.trainManager.getResultByQueryString(hql);
        //得到所有大项（按顺序）
        for (Train tmp : errorList) {
            if (StringUtil.isNotEmpty(category)) {
                if (categoryId == tmp.getCategory().getCategoryId()) {
                    if (StringUtil.isNotEmpty(dept)) {
                        if (deptNum.intValue() == tmp.getDepartmentNum()) {
                            TrainVo trainVo = this.trainManager.transToVo(tmp);
                            trainVos.add(trainVo);
                        }
                    } else if (!StringUtil.isNotEmpty(dept)) {
                        TrainVo trainVo = this.trainManager.transToVo(tmp);
                        trainVos.add(trainVo);
                    }
                }
            } else if (!StringUtil.isNotEmpty(category)) {
                if (StringUtil.isNotEmpty(dept)) {
                    if (deptNum.intValue() == tmp.getDepartmentNum()) {
                        TrainVo trainVo = this.trainManager.transToVo(tmp);
                        trainVos.add(trainVo);
                    }
                } else if (!StringUtil.isNotEmpty(dept)) {
                    TrainVo trainVo = this.trainManager.transToVo(tmp);
                    trainVos.add(trainVo);
                }
            }
        }

        trainCount = trainVos.size();
        modelMap.addAttribute("trainCount", trainCount);
        modelMap.addAttribute("trainVos", trainVos);

        return "/train/dailyReport";

    }

    @RequestMapping(params = "method=detail")
    public String detail(HttpServletRequest request, ModelMap modelMap) {
        String Id = request.getParameter("trainId");
        int trainId = Integer.parseInt(Id);
        Train train = (Train) this.trainManager.get(trainId);
        TrainVo trainVo = this.trainManager.transToVo(train);
        modelMap.addAttribute("trainVo", trainVo);
        return "/train/detail";
    }

    @RequestMapping(params = "method=edit")
    public String edit(HttpServletRequest request, ModelMap modelMap) {
        String Id = request.getParameter("trainId");
        Train train = (Train) this.trainManager.get(Integer.parseInt(Id));
        TrainVo trainVo = this.trainManager.transToVo(train);
        modelMap.addAttribute("trainVo", trainVo);
        return "/train/addTrain";
    }

    @RequestMapping(params = "method=delete")
    @ResponseBody
    public String delete(HttpServletRequest request) {
        String Id = request.getParameter("trainId");
        Train train = (Train) this.trainManager.get(Integer.parseInt(Id));
        train.setValid(false);
        this.trainManager.save(train);
        return "success";
    }

}