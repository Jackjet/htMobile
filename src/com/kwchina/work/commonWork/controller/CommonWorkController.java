package com.kwchina.work.commonWork.controller;

import com.kwchina.core.base.entity.User;
import com.kwchina.core.base.service.UserManager;
import com.kwchina.core.common.controller.BasicController;
import com.kwchina.core.common.page.PageList;
import com.kwchina.core.common.page.Pages;
import com.kwchina.core.sys.CoreConstant;
import com.kwchina.core.util.DateHelper;
import com.kwchina.core.util.json.JSONConvert;
import com.kwchina.core.util.string.StringUtil;
import com.kwchina.work.commonWork.entity.XWork;
import com.kwchina.work.commonWork.entity.XWorkTrace;
import com.kwchina.work.commonWork.service.XWorkManager;
import com.kwchina.work.commonWork.service.XWorkTraceManager;
import com.kwchina.work.commonWork.vo.TodayWorkVo;
import com.kwchina.work.commonWork.vo.XWorkVo;
import com.kwchina.work.nightWatch.service.NwListManager;
import com.kwchina.work.nightWatch.service.NwWorkDetailManager;
import com.kwchina.work.nightWatch.service.NwWorkManager;
import com.kwchina.work.patrol.entity.WorkItem;
import com.kwchina.work.patrol.entity.WorkList;
import com.kwchina.work.patrol.service.ItemDetailManager;
import com.kwchina.work.patrol.service.WorkItemManager;
import com.kwchina.work.patrol.service.WorkListManager;
import com.kwchina.work.patrol.vo.OpPatrolVo;
import com.kwchina.work.reform.entity.ReformDetail;
import com.kwchina.work.reform.service.ReformManager;
import com.kwchina.work.sys.LoginConfirm;
import com.kwchina.work.sys.SysCommonMethod;
import com.kwchina.work.util.JPushUtils;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.beanutils.BeanComparator;
import org.apache.commons.collections.ComparatorUtils;
import org.apache.commons.collections.comparators.ComparableComparator;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.support.DefaultMultipartHttpServletRequest;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;


@Controller
@RequestMapping("/commonWork.htm")
public class CommonWorkController extends BasicController {
    Logger logger = Logger.getLogger(CommonWorkController.class);


    @Resource
    private XWorkManager xWorkManager;

    @Resource
    private ReformManager reformManager;

    @Resource
    private XWorkTraceManager xWorkTraceManager;

    @Resource
    private UserManager userManager;

    @Resource
    private WorkItemManager workItemManager;

    @Resource
    private WorkListManager workListManager;

    @Resource
    private NwWorkManager nwWorkManager;

    @Resource
    private NwListManager nwListManager;

    @Resource
    private ItemDetailManager itemDetailManager;

    @Resource
    private NwWorkDetailManager nwWorkDetailManager;


    /***
     * PC端任务列表
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(params = "method=workList")
    public void workList(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {

        // 构造查询语句
//		String areaId = request.getParameter("areaId");
        String[] queryString = new String[2];
        queryString[0] = "from XWork instance where 1=1 ";
        queryString[1] = "select count(*) from  XWork instance where 1=1 ";


//		queryString = this.xWorkManager.generateQueryString(queryString, getSearchParams(request));
        //构造查询条件
        String mainCondition = "";
        String[] params = getSearchParams(request);
        String conditions = this.xWorkManager.generateCondition(params[3]);
        //查询操作时,加入查询条件
        if (params[2].equals("true") && conditions != null && conditions.length() > 0) {
            mainCondition += " and " + conditions;
        }

        queryString[0] += mainCondition;
        queryString[1] += mainCondition;

        queryString[0] += " order by " + params[0] + " " + params[1];

        String page = request.getParameter("page"); // 当前页
        String rowsNum = request.getParameter("rows"); // 每页显示的行数
        Pages pages = new Pages(request);
        pages.setPage(Integer.valueOf(page));
        pages.setPerPageNum(Integer.valueOf(rowsNum));

        PageList pl = this.xWorkManager.getResultByQueryString(queryString[0], queryString[1], true, pages);
        List<XWork> list = pl.getObjectList();
        List<XWorkVo> vos = new ArrayList<XWorkVo>();
        //转vo
        for (XWork tmpWork : list) {
            XWorkVo vo = new XWorkVo();
            vo = this.xWorkManager.transferToVo(tmpWork);
            vos.add(vo);
        }

        //排序
//		Comparator<XWorkVo> mycmp = ComparableComparator.getInstance();
//        mycmp = ComparatorUtils.nullLowComparator(mycmp); // 允许null
//        if (params[1].equals("desc")) {
//            mycmp = ComparatorUtils.reversedComparator(mycmp); // 逆序
//        }
//
//        String orderStr = params[0];
//        if(orderStr.equals("sta")){
//        	orderStr = "workState";
//        }
//        Collections.sort(vos, new BeanComparator(orderStr, mycmp));

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
     * 新建工作
     *
     * @param request
     * @param response
     * @param vo
     */
    @RequestMapping(params = "method=newWork")
    @Transactional
    public void newWork(HttpServletRequest request, HttpServletResponse response, DefaultMultipartHttpServletRequest multipartRequest) throws Exception {
        String message = "";
        boolean success = true;

        JSONObject jsonObj = new JSONObject();
        //用户
        String token = request.getParameter("token");
        User user = LoginConfirm.loginUserMap.get(token);

        //参数：
        String workTitle = request.getParameter("workTitle");
        String beginTime = request.getParameter("beginTime");
        String endTime = request.getParameter("endTime");
        String executerId = request.getParameter("executerId");
        String copyTo = request.getParameter("copyTo");
        String location = request.getParameter("location");
        String content = request.getParameter("content");

        workTitle = StringUtil.isNotEmpty(workTitle) ? workTitle : "临时工作";
//		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Timestamp now = new Timestamp(System.currentTimeMillis());
        try {

            if (user != null) {
                executerId = StringUtil.isNotEmpty(executerId) ? executerId : user.getUserId().toString();

                if (StringUtil.isNotEmpty(beginTime) && StringUtil.isNotEmpty(endTime) && StringUtil.isNotEmpty(executerId)) {
                    XWork xWork = new XWork();
                    xWork.setWorkTitle(workTitle);
                    xWork.setBeginTime(Timestamp.valueOf(beginTime));
                    xWork.setEndTime(Timestamp.valueOf(endTime));
                    xWork.setExecuterId(Integer.valueOf(executerId));
                    xWork.setCopyTo(copyTo);
                    xWork.setLocation(location);
                    xWork.setContent(content);
                    xWork.setSource("一次");
                    xWork.setWorkState(0);
                    xWork.setCreateTime(now);
                    xWork.setValid(true);
                    xWork.setCreaterId(user.getUserId());

                    //附件
                    String attachs = uploadAttachmentBySize(multipartRequest, "commonWork");
                    xWork.setAttachs(attachs);

                    //workId
                    Integer workId = this.xWorkManager.getMaxId("workId");
                    xWork.setWorkId(workId);

                    XWork newWork = (XWork) this.xWorkManager.save(xWork);

                    //保存新增记录
                    XWorkTrace trace = new XWorkTrace();
                    trace.setOperateTime(new Timestamp(System.currentTimeMillis()));
                    trace.setOperatorId(user.getUserId());
                    trace.setOperatorName(user.getName());
                    trace.setWorkId(newWork.getWorkId());
                    trace.setContent("创建任务");

                    this.xWorkTraceManager.save(trace);

                    /**************新建工作后，给执行人发送一条推送*****************/
//                    SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//                    String pushContent = user.getName() + " 于 " + sf.format(now) + " 创建了一条任务【" + workTitle + "】给您，请及时处理！";
//                    JPushUtils pushUtil = JPushUtils.getInstance();
//
//                    User executer = this.userManager.getUserByUserId(Integer.valueOf(executerId));
//                    pushUtil.pushCommonWork(newWork.getWorkId(), pushContent, 1, executer.getUserName());
//
//
//                    //抄送推送
//                    if (StringUtil.isNotEmpty(copyTo)) {
//                        String[] copyToIds = copyTo.split(",");
//                        for (String tmpCopyToId : copyToIds) {
//                            if (StringUtil.isNotEmpty(tmpCopyToId)) {
//                                String pushCopyToContent = user.getName() + " 于 " + sf.format(now) + " 创建了一条任务【" + workTitle + "】，并抄送给您，请知悉！";
//
//                                User copyToer = this.userManager.getUserByUserId(Integer.valueOf(tmpCopyToId));
//                                pushUtil.pushCommonWork(newWork.getWorkId(), pushContent, 1, copyToer.getUserName());
//                            }
//                        }
//                    }

                } else {
                    message = "执行人、开始时间、结束时间三项必填！";
                    success = false;
                }

            } else {
                message = "请登录！";
                success = false;
            }


        } catch (Exception e) {
            message = "后台出错，请重试！";
            success = false;
            e.printStackTrace();
            logger.error("出现错误=================" + e.getLocalizedMessage());
            logger.error(e, e.fillInStackTrace());
        }

        jsonObj.put("success", success);
        jsonObj.put("message", message);

        //设置字符编码
        response.setContentType(CoreConstant.CONTENT_TYPE);
        response.getWriter().print(jsonObj);
    }


    /**
     * 查看工作详情
     *
     * @param request
     * @param response
     * @param vo
     */
    @RequestMapping(params = "method=viewWork")
    @Transactional
    public void viewWork(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String message = "";
        boolean success = true;
        XWorkVo vo = new XWorkVo();

        JSONObject jsonObj = new JSONObject();
        //用户
        String token = request.getParameter("token");
        User user = LoginConfirm.loginUserMap.get(token);

        //参数：
        String workId = request.getParameter("workId");

        try {

            if (user != null) {

                if (StringUtil.isNotEmpty(workId)) {
                    XWork xWork = new XWork();
                    xWork = this.xWorkManager.getWorkByWorkId(Integer.valueOf(workId), true);

                    vo = this.xWorkManager.transferToVo(xWork);
                } else {
                    message = "workId必填！";
                    success = false;
                }

            } else {
                message = "请登录！";
                success = false;
            }


        } catch (Exception e) {
            message = "后台出错，请重试！";
            success = false;
            e.printStackTrace();
            logger.error("出现错误=================" + e.getLocalizedMessage());
            logger.error(e, e.fillInStackTrace());
        }

        jsonObj.put("success", success);
        jsonObj.put("message", message);
        jsonObj.put("result", vo);

        //设置字符编码
        response.setContentType(CoreConstant.CONTENT_TYPE);
        response.getWriter().print(jsonObj);
    }

    /**
     * 任务移交
     *
     * @param request
     * @param response
     * @param vo
     */
    @RequestMapping(params = "method=handOverWork")
    @Transactional
    public void handOverWork(HttpServletRequest request, HttpServletResponse response, DefaultMultipartHttpServletRequest multipartRequest) throws Exception {
        String message = "";
        boolean success = true;

        JSONObject jsonObj = new JSONObject();
        //用户
        String token = request.getParameter("token");
        User user = LoginConfirm.loginUserMap.get(token);

        //参数：
        String workId = request.getParameter("workId");
        String newUserId = request.getParameter("newUserId");
        String memo = request.getParameter("memo");
        Timestamp now = new Timestamp(System.currentTimeMillis());
        try {

            if (user != null) {

                if (StringUtil.isNotEmpty(workId) && StringUtil.isNotEmpty(newUserId)) {
                    XWork xWork = new XWork();
                    xWork = this.xWorkManager.getWorkByWorkId(Integer.valueOf(workId), true);
//					Integer oldExecuterId = xWork.getExecuterId();
                    xWork.setExecuterId(Integer.valueOf(newUserId));

                    this.xWorkManager.save(xWork);

                    //保存移交记录
                    User newExecuter = this.userManager.getUserByUserId(Integer.valueOf(newUserId));

                    XWorkTrace trace = new XWorkTrace();
                    trace.setOperateTime(new Timestamp(System.currentTimeMillis()));
                    trace.setOperatorId(user.getUserId());
                    trace.setOperatorName(user.getName());
                    trace.setToUserId(Integer.valueOf(newUserId));
                    trace.setToUserName(newExecuter.getName());

                    trace.setWorkId(Integer.valueOf(workId));

//					User oldExecuter = this.userManager.getUserByUserId(oldExecuterId);
                    trace.setContent("移交任务给 " + newExecuter.getName());

                    trace.setMemo(memo);

                    //附件
                    String attachs = uploadAttachmentBySize(multipartRequest, "workTrace");
                    trace.setAttachs(attachs);

                    this.xWorkTraceManager.save(trace);

                    /**************移交工作后，给新执行人发送一条推送*****************/
                    SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String pushContent = user.getName() + " 于 " + sf.format(now) + " 移交给您了一条任务【" + xWork.getWorkTitle() + "】，请及时处理！";
                    JPushUtils pushUtil = JPushUtils.getInstance();

                    pushUtil.pushCommonWork(Integer.valueOf(workId), pushContent, 1, newExecuter.getUserName());
                } else {
                    message = "参数不完整！";
                    success = false;
                }

            } else {
                message = "请登录！";
                success = false;
            }


        } catch (Exception e) {
            message = "后台出错，请重试！";
            success = false;
            e.printStackTrace();
            logger.error("出现错误=================" + e.getLocalizedMessage());
            logger.error(e, e.fillInStackTrace());
        }

        jsonObj.put("success", success);
        jsonObj.put("message", message);

        //设置字符编码
        response.setContentType(CoreConstant.CONTENT_TYPE);
        response.getWriter().print(jsonObj);
    }


    /**
     * 任务完成
     *
     * @param request
     * @param response
     * @param vo
     */
    @RequestMapping(params = "method=finishWork")
    @Transactional
    public void finishWork(HttpServletRequest request, HttpServletResponse response, DefaultMultipartHttpServletRequest multipartRequest) throws Exception {
        String message = "";
        boolean success = true;

        JSONObject jsonObj = new JSONObject();
        //用户
        String token = request.getParameter("token");
        User user = LoginConfirm.loginUserMap.get(token);

        //参数：
        String workId = request.getParameter("workId");
        String memo = request.getParameter("memo");
        Timestamp now = new Timestamp(System.currentTimeMillis());
        try {

            if (user != null) {

                if (StringUtil.isNotEmpty(workId)) {
                    XWork xWork = new XWork();
                    xWork = this.xWorkManager.getWorkByWorkId(Integer.valueOf(workId), true);
                    xWork.setWorkState(1);
                    xWork.setFinisherId(user.getUserId());
                    xWork.setFinishTime(new Timestamp(System.currentTimeMillis()));

                    this.xWorkManager.save(xWork);

                    //保存完成记录
                    XWorkTrace trace = new XWorkTrace();
                    trace.setOperateTime(new Timestamp(System.currentTimeMillis()));
                    trace.setOperatorId(user.getUserId());
                    trace.setOperatorName(user.getName());
                    trace.setWorkId(Integer.valueOf(workId));

                    trace.setContent("完成任务");

                    trace.setMemo(memo);

                    //附件
                    String attachs = uploadAttachmentBySize(multipartRequest, "workTrace");
                    trace.setAttachs(attachs);

                    this.xWorkTraceManager.save(trace);

                    /**************完成工作后，给发布人发送一条推送*****************/
                    SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String pushContent = user.getName() + " 于 " + sf.format(now) + " 完成了任务【" + xWork.getWorkTitle() + "】，请知悉！";
                    JPushUtils pushUtil = JPushUtils.getInstance();

                    User creater = this.userManager.getUserByUserId(xWork.getCreaterId());
                    pushUtil.pushCommonWork(Integer.valueOf(workId), pushContent, 1, creater.getUserName());
                } else {
                    message = "参数不完整！";
                    success = false;
                }

            } else {
                message = "请登录！";
                success = false;
            }


        } catch (Exception e) {
            message = "后台出错，请重试！";
            success = false;
            e.printStackTrace();
            logger.error("出现错误=================" + e.getLocalizedMessage());
            logger.error(e, e.fillInStackTrace());
        }

        jsonObj.put("success", success);
        jsonObj.put("message", message);

        //设置字符编码
        response.setContentType(CoreConstant.CONTENT_TYPE);
        response.getWriter().print(jsonObj);
    }

    /**
     * 删除任务
     *
     * @param request
     * @param response
     * @param vo
     */
    @RequestMapping(params = "method=delWork")
    @Transactional
    public void delWork(HttpServletRequest request, HttpServletResponse response, DefaultMultipartHttpServletRequest multipartRequest) throws Exception {
        String message = "";
        boolean success = true;

        JSONObject jsonObj = new JSONObject();
        //用户
        String token = request.getParameter("token");
        User user = LoginConfirm.loginUserMap.get(token);

        //参数：
        String workId = request.getParameter("workId");
        String memo = request.getParameter("memo");

        try {

            if (user != null) {

                if (StringUtil.isNotEmpty(workId)) {
                    XWork xWork = new XWork();
                    xWork = this.xWorkManager.getWorkByWorkId(Integer.valueOf(workId), true);
                    xWork.setValid(false);

                    this.xWorkManager.save(xWork);

                    //保存删除记录
                    XWorkTrace trace = new XWorkTrace();
                    trace.setOperateTime(new Timestamp(System.currentTimeMillis()));
                    trace.setOperatorId(user.getUserId());
                    trace.setOperatorName(user.getName());
                    trace.setWorkId(Integer.valueOf(workId));

                    trace.setContent("删除任务");

                    trace.setMemo(memo);

                    //附件
                    String attachs = uploadAttachmentBySize(multipartRequest, "workTrace");
                    trace.setAttachs(attachs);

                    this.xWorkTraceManager.save(trace);

                    /**************完成删除后，给执行人发送一条推送*****************/
                    SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String pushContent = user.getName() + " 取消了任务【" + xWork.getWorkTitle() + "】，请点击本通知更新！";
                    JPushUtils pushUtil = JPushUtils.getInstance();

                    User executer = this.userManager.getUserByUserId(xWork.getExecuterId());
                    pushUtil.pushCommonWork(Integer.valueOf(workId), pushContent, 1, executer.getUserName());
                } else {
                    message = "参数不完整！";
                    success = false;
                }

            } else {
                message = "请登录！";
                success = false;
            }


        } catch (Exception e) {
            message = "后台出错，请重试！";
            success = false;
            e.printStackTrace();
            logger.error("出现错误=================" + e.getLocalizedMessage());
            logger.error(e, e.fillInStackTrace());
        }

        jsonObj.put("success", success);
        jsonObj.put("message", message);

        //设置字符编码
        response.setContentType(CoreConstant.CONTENT_TYPE);
        response.getWriter().print(jsonObj);
    }

    /**
     * 变更记录
     *
     * @param request
     * @param response
     * @param vo
     */
    @RequestMapping(params = "method=getTraces")
    @Transactional
    public void getTraces(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String message = "";
        boolean success = true;
        List<XWorkTrace> rtnList = new ArrayList<XWorkTrace>();
        JSONObject jsonObj = new JSONObject();
        //用户
        String token = request.getParameter("token");
        User user = LoginConfirm.loginUserMap.get(token);

        if (user == null) {
            user = SysCommonMethod.getSystemUser(request);
        }

        //参数：
        String workId = request.getParameter("workId");

        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        try {

            if (user != null) {

                if (StringUtil.isNotEmpty(workId)) {
//					XWork xWork = new XWork();
//					xWork = this.xWorkManager.getWorkByWorkId(Integer.valueOf(workId), true);
                    List<XWorkTrace> tmpList = this.xWorkTraceManager.getTracesByWorkId(Integer.valueOf(workId));
                    for (XWorkTrace tmpTrace : tmpList) {
                        if (tmpTrace.getOperateTime() != null) {
                            tmpTrace.setOpTimeStr(sf.format(tmpTrace.getOperateTime()));
//							tmpTrace.setOperateTime(null);
                        }
                        rtnList.add(tmpTrace);
                    }

                } else {
                    message = "参数不完整！";
                    success = false;
                }

            } else {
                message = "请登录！";
                success = false;
            }


        } catch (Exception e) {
            message = "后台出错，请重试！";
            success = false;
            e.printStackTrace();
            logger.error("出现错误=================" + e.getLocalizedMessage());
            logger.error(e, e.fillInStackTrace());
        }

        jsonObj.put("success", success);
        jsonObj.put("message", message);
        jsonObj.put("result", rtnList);

        //设置字符编码
        response.setContentType(CoreConstant.CONTENT_TYPE);
        response.getWriter().print(jsonObj);
    }

    /**
     * 已完成任务
     * <p>
     * 有个用户类型为：userType=10，可得到今日所有
     *
     * @param request
     * @param response
     * @param vo
     */
    @RequestMapping(params = "method=getDoneWorks")
    @Transactional
    public void getDoneWorks(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String message = "";
        boolean success = true;
        List<TodayWorkVo> rtnList = new ArrayList<TodayWorkVo>();
        JSONObject jsonObj = new JSONObject();
        //用户
        String token = request.getParameter("token");
        User user = LoginConfirm.loginUserMap.get(token);

        //参数：
        String queryDate = request.getParameter("queryDate");
        if (!StringUtil.isNotEmpty(queryDate)) {
            queryDate = DateHelper.getString(new Date());
        }
        try {

            if (user != null) {

//				if(StringUtil.isNotEmpty(queryDate)){
//					List<XWorkVo> works = this.xWorkManager.getWorkByDate(queryDate);
//					for(XWorkVo vo : works){
//						if(vo.getWorkState() == 1 && StringUtil.isNotEmpty(vo.getFinishTimeStr())){
//							rtnList.add(vo);
//						}
//					}
//				}else{
//					message = "参数不完整！";
//					success = false;
//				}

                String todayB = queryDate + " 00:00:00";
                String todayE = queryDate + " 23:59:59";

                SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                SimpleDateFormat sf_date = new SimpleDateFormat("MM-dd HH:mm");
                SimpleDateFormat sf_time = new SimpleDateFormat("HH:mm");
                String now = sf.format(new Date());

                Integer userId = user.getUserId();

                int userType = user.getUserType();
                boolean canGetAll = false;
                if (userType > 5) {
                    canGetAll = true;
                }


                //非今日
                List<TodayWorkVo> notTodayList = new ArrayList<TodayWorkVo>();
                //今日
                List<TodayWorkVo> todayList = new ArrayList<TodayWorkVo>();

                /********* 1、取完成时间位于今天的日常工作 *********/
                String hql1 = "from XWork where valid=1 and workState=1 ";
                if (!canGetAll) {
                    hql1 += " and executerId=" + userId;
                }
                hql1 += " and (finishTime <= '" + todayE + "' and finishTime >='" + todayB + "')";

                List<XWork> xworkList = this.xWorkManager.getResultByQueryString(hql1);

                for (XWork tmpWork : xworkList) {
                    TodayWorkVo vo = new TodayWorkVo();
                    vo.setWorkName(tmpWork.getWorkTitle());
                    vo.setExecuterId(tmpWork.getExecuterId());
                    User executer = this.userManager.getUserByUserId(tmpWork.getExecuterId());
                    vo.setExecuterName(executer.getName());

                    vo.setCreaterId(tmpWork.getCreaterId());
                    User creater = this.userManager.getUserByUserId(tmpWork.getCreaterId());
                    vo.setCreaterName(creater.getName());

                    String workTime = "";

                    Timestamp beginTimestamp = tmpWork.getBeginTime();
                    String beginDate = DateHelper.getString((beginTimestamp));

                    boolean notToday = false;
                    if (beginDate.equals(queryDate)) { //今日的,直接写时间
                        workTime = sf_time.format(beginTimestamp);
                    } else { //非今日的，写上日期
                        notToday = true;
                        workTime = sf_date.format(beginTimestamp);
                    }

                    vo.setWorkTime(workTime);
                    vo.setCommonWorkId(tmpWork.getWorkId());
                    vo.setPatrolItemId(0);
                    vo.setNightWatchWorkId(0);

                    if (notToday) {
                        notTodayList.add(vo);
                    } else {
                        todayList.add(vo);
                    }
                }

                /********* 2、取完成时间位于今天的巡检工作 *********/
                String hql2 = "from WorkItem where valid=1 and workState=1 ";
                if (!canGetAll) {
                    hql2 += " and operatorId=" + userId;
                }
                hql2 += " and (finishTime <= '" + todayE + "' and finishTime >='" + todayB + "')";

                List<WorkItem> patrolWorkList = this.workItemManager.getResultByQueryString(hql2);

                for (WorkItem tmpWork : patrolWorkList) {
                    TodayWorkVo vo = new TodayWorkVo();

                    WorkList workList = this.workListManager.getWorkListByListId(tmpWork.getListId());
                    vo.setWorkName(workList.getWorkTitle());
                    vo.setExecuterId(tmpWork.getOperatorId());
                    User executer = this.userManager.getUserByUserId(tmpWork.getOperatorId());
                    vo.setExecuterName(executer.getName());
                    vo.setReadSafeRules(tmpWork.getReadSafeRules());

                    vo.setCreaterId(workList.getCreaterId());
                    User creater = this.userManager.getUserByUserId(workList.getCreaterId());
                    vo.setCreaterName(creater.getName());

                    String workTime = "";

                    Timestamp beginTimestamp = tmpWork.getBeginTime();
                    String beginDate = DateHelper.getString((beginTimestamp));

                    boolean notToday = false;
                    if (beginDate.equals(queryDate)) { //今日的,直接写时间
                        workTime = sf_time.format(beginTimestamp);
                    } else { //非今日的，写上日期
                        notToday = true;
                        workTime = sf_date.format(beginTimestamp);
                    }

                    vo.setWorkTime(workTime);
                    vo.setCommonWorkId(0);
                    vo.setPatrolItemId(tmpWork.getItemId());
                    vo.setNightWatchWorkId(0);

                    if (notToday) {
                        notTodayList.add(vo);
                    } else {
                        todayList.add(vo);
                    }
                }
//
//                /********* 3、取完成时间位于今天的巡更工作 *********/
//                String hql3 = "from NwWork where valid=1 and workState=1 ";
//                if (!canGetAll) {
//                    hql3 += " and executerId=" + userId;
//                }
//                hql3 += " and (finishTime <= '" + todayE + "' and finishTime >='" + todayB + "')";
//
//                List<NwWork> nwWorkList = this.nwWorkManager.getResultByQueryString(hql3);
//
//                for (NwWork tmpWork : nwWorkList) {
//                    TodayWorkVo vo = new TodayWorkVo();
//
//                    NwList workList = this.nwListManager.getListByListId(tmpWork.getListId());
//                    vo.setWorkName(workList.getWorkTitle());
//                    vo.setExecuterId(tmpWork.getExecuterId());
//                    User executer = this.userManager.getUserByUserId(tmpWork.getExecuterId());
//                    vo.setExecuterName(executer.getName());
//                    vo.setReadSafeRules(tmpWork.getReadSafeRules());
//
//                    vo.setCreaterId(workList.getCreaterId());
//                    User creater = this.userManager.getUserByUserId(workList.getCreaterId());
//                    vo.setCreaterName(creater.getName());
//
//                    String workTime = "";
//
//                    Timestamp beginTimestamp = tmpWork.getBeginTime();
//                    String beginDate = DateHelper.getString((beginTimestamp));
//
//                    boolean notToday = false;
//                    if (beginDate.equals(queryDate)) { //今日的,直接写时间
//                        workTime = sf_time.format(beginTimestamp);
//                    } else { //非今日的，写上日期
//                        notToday = true;
//                        workTime = sf_date.format(beginTimestamp);
//                    }
//
//                    vo.setWorkTime(workTime);
//                    vo.setCommonWorkId(0);
//                    vo.setPatrolItemId(0);
//                    vo.setNightWatchWorkId(tmpWork.getWorkId());
//
//                    if (notToday) {
//                        notTodayList.add(vo);
//                    } else {
//                        todayList.add(vo);
//                    }
//                }
                /********* 4、取完成时间位于今天的隐患工作 *********/
                String hql4 = "from ReformDetail detail where valid=1 and reformStatus=4 ";
                if (!canGetAll) {
                    hql4 += " and (findUser.userId=" + userId+") or (dutyPerson.userId="+userId+")";
                }
                hql4 += " and (finishTime <= '" + todayE + "' and finishTime >='" + todayB + "')";
                List<ReformDetail> reformList = this.reformManager.getResultByQueryString(hql4);

                for (ReformDetail tmpWork : reformList) {
                    TodayWorkVo vo = new TodayWorkVo();

                    vo.setWorkName(tmpWork.getErrorTitle());
                    vo.setExecuterId(tmpWork.getDutyPerson().getUserId());
                    vo.setExecuterName(tmpWork.getDutyPerson().getName());
                    vo.setCreaterId(tmpWork.getFindUser().getUserId());
                    vo.setCreaterName(tmpWork.getFindUser().getName());
                    String workTime = "";
                    String beginDate = DateHelper.getString(tmpWork.getFindTime());

                    boolean notToday = false;
                    if (beginDate.equals(queryDate)) { //今日的,直接写时间
                        workTime = sf_time.format(tmpWork.getFindTime());
                    } else { //非今日的，写上日期
                        notToday = true;
                        workTime = sf_date.format(tmpWork.getFindTime());
                    }
                    vo.setWorkState(4);
                    vo.setWorkTime(workTime);
                    vo.setCommonWorkId(0);
                    vo.setPatrolItemId(0);
                    vo.setNightWatchWorkId(0);
                    vo.setReformId(tmpWork.getReformId());
                    vo.setReformVo(this.reformManager.getReformDetail(tmpWork.getReformId()));

                    if (notToday) {
                        notTodayList.add(vo);
                    } else {
                        todayList.add(vo);
                    }
                }


                //将非今日先排序，排序后加入rtnList
                Comparator<?> mycmp = ComparableComparator.getInstance();
                mycmp = ComparatorUtils.nullLowComparator(mycmp); // 允许null
//		        if (!asc) {
//		            mycmp = ComparatorUtils.reversedComparator(mycmp); // 逆序
//		        }
                Collections.sort(notTodayList, new BeanComparator("workTime", mycmp));
                rtnList.addAll(notTodayList);

                //再将今日排序，排序后加入rtnList
                Collections.sort(todayList, new BeanComparator("workTime", mycmp));
                rtnList.addAll(todayList);

            } else {
                message = "请登录！";
                success = false;
            }


        } catch (Exception e) {
            message = "后台出错，请重试！";
            success = false;
            e.printStackTrace();
            logger.error("出现错误=================" + e.getLocalizedMessage());
            logger.error(e, e.fillInStackTrace());
        }

        jsonObj.put("success", success);
        jsonObj.put("message", message);
        jsonObj.put("result", rtnList);

        //设置字符编码
        response.setContentType(CoreConstant.CONTENT_TYPE);
        response.getWriter().print(jsonObj);
    }


    /**
     * 今日任务（日常任务、巡检、巡更综合到一起）
     *
     * @param request
     * @param response
     * @param vo
     */
    @RequestMapping(params = "method=todayWorks")
    @Transactional
    public void todayWorks(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String message = "";
        boolean success = true;
        List<TodayWorkVo> rtnList_todo = new ArrayList<TodayWorkVo>();//待办
        List<TodayWorkVo> rtnList_mine = new ArrayList<TodayWorkVo>();//我发出的
        List<TodayWorkVo> rtnList_todayAll = new ArrayList<TodayWorkVo>();//今日所有未完成的
        JSONObject jsonObj = new JSONObject();
        //用户
        String token = request.getParameter("token");
        User user = LoginConfirm.loginUserMap.get(token);

        //参数：
//		String queryDate = request.getParameter("queryDate");

        try {
            if (user != null) {
                String today = DateHelper.getString(new Date());
                String todayB = today + " 00:00:00";
                String todayE = today + " 23:59:59";

                SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//				SimpleDateFormat sf_date = new SimpleDateFormat("yyyy-MM-dd");
                SimpleDateFormat sf_date = new SimpleDateFormat("MM-dd HH:mm");
                SimpleDateFormat sf_time = new SimpleDateFormat("HH:mm");
                String now = sf.format(new Date());

                Integer userId = user.getUserId();
                Integer userDeptId = user.getDepartmentId();
                int userType = user.getUserType();

                boolean isGrade1 = false;
                boolean isGrade2 = false;
                boolean isGrade3 = false;
                if (userType <= 5) {  //一级执行人员
                    isGrade1 = true;
                } else if (userType > 5 && userType <= 10) {
                    isGrade2 = true;
                    isGrade3 = true;
                } else if (userType > 10) {
                    isGrade3 = true;
                } else {
                    isGrade1 = true;
                }

                /****************今日待办*****************/

                //非今日
                List<TodayWorkVo> notTodayList_todo = new ArrayList<TodayWorkVo>();
                //今日
                List<TodayWorkVo> todayList_todo = new ArrayList<TodayWorkVo>();

                /********* 1、取时间段位于今天的、未完成的日常工作 *********/
                String hql1_todo = "from XWork where valid=1 and workState=0 ";
//				if(!canGetAll){
                hql1_todo += " and (executerId=" + userId + " or copyTo like '" + userId + ",%' or copyTo like '%," + userId + ",%')";
//				}
                hql1_todo += " and (beginTime <= '" + todayE + "' or endTime >='" + todayB + "')";

                List<XWork> xworkList_todo = this.xWorkManager.getResultByQueryString(hql1_todo);

                for (XWork tmpWork : xworkList_todo) {
                    TodayWorkVo vo = new TodayWorkVo();
                    vo.setWorkName(tmpWork.getWorkTitle());
                    vo.setExecuterId(tmpWork.getExecuterId());
                    User executer = this.userManager.getUserByUserId(tmpWork.getExecuterId());
                    vo.setExecuterName(executer.getName());
                    vo.setWorkState(0);

                    vo.setCreaterId(tmpWork.getCreaterId());
                    User creater = this.userManager.getUserByUserId(tmpWork.getCreaterId());
                    vo.setCreaterName(creater.getName());

                    String workTime = "";

                    Timestamp beginTimestamp = tmpWork.getBeginTime();
                    String beginDate = DateHelper.getString((beginTimestamp));

                    boolean notToday = false;
                    if (beginDate.equals(today)) { //今日的,直接写时间
                        workTime = sf_time.format(beginTimestamp);
                    } else { //非今日的，写上日期
                        notToday = true;
                        workTime = sf_date.format(beginTimestamp);
                    }

                    vo.setWorkTime(workTime);
                    vo.setCommonWorkId(tmpWork.getWorkId());
                    vo.setPatrolItemId(0);
                    vo.setNightWatchWorkId(0);
                    vo.setReformId(0);

                    if (notToday) {
                        notTodayList_todo.add(vo);
                    } else {
                        todayList_todo.add(vo);
                    }
                }

                /********* 2、取时间段位于今天的、未完成的巡检工作 *********/
                String hql2_todo = "from WorkItem where valid=1 and workState=0 ";
                hql2_todo += " and operatorId=" + userId;
                hql2_todo += " and (beginTime <= '" + todayE + "')";// or endTime >='" + now + "'

                List<WorkItem> patrolWorkList_todo = this.workItemManager.getResultByQueryString(hql2_todo);

                for (WorkItem tmpWork : patrolWorkList_todo) {
                    TodayWorkVo vo = new TodayWorkVo();

                    WorkList workList = this.workListManager.getWorkListByListId(tmpWork.getListId());
                    vo.setWorkName(workList.getWorkTitle());
                    vo.setExecuterId(tmpWork.getOperatorId());
                    User executer = this.userManager.getUserByUserId(tmpWork.getOperatorId());
                    vo.setExecuterName(executer.getName());
                    vo.setReadSafeRules(tmpWork.getReadSafeRules());
                    vo.setWorkState(0);

                    vo.setCreaterId(workList.getCreaterId());
                    User creater = this.userManager.getUserByUserId(workList.getCreaterId());
                    vo.setCreaterName(creater.getName());

                    String workTime = "";

                    Timestamp beginTimestamp = tmpWork.getBeginTime();
                    String beginDate = DateHelper.getString((beginTimestamp));

                    boolean notToday = false;
                    if (beginDate.equals(today)) { //今日的,直接写时间
                        workTime = sf_time.format(beginTimestamp);
                    } else { //非今日的，写上日期
                        notToday = true;
                        workTime = sf_date.format(beginTimestamp);
                    }

                    vo.setWorkTime(workTime);
                    vo.setCommonWorkId(0);
                    vo.setPatrolItemId(tmpWork.getItemId());
                    vo.setNightWatchWorkId(0);
                    vo.setReformId(0);

                    // 详情
                    OpPatrolVo patrolVo = this.workItemManager.getPatrolDetail(tmpWork.getItemId());
                    vo.setPatrolVo(patrolVo);

                    if (notToday) {
                        notTodayList_todo.add(vo);
                    } else {
                        todayList_todo.add(vo);
                    }
//					}


                }

//				/********* 3、取时间段位于今天的，未完成的巡更工作 *********/
//				String hql3_todo = "from NwWork where valid=1 and workState=0 ";
////				if(!canGetAll){
//				hql3_todo += " and executerId=" + userId;
////				}
//				hql3_todo += " and (beginTime <= '" + todayE + "')"; // or endTime >='" + now + "'
//
//				List<NwWork> nwWorkList_todo = this.nwWorkManager.getResultByQueryString(hql3_todo);
//
//				for(NwWork tmpWork : nwWorkList_todo){
//
//					TodayWorkVo vo = new TodayWorkVo();
//
//					NwList workList = this.nwListManager.getListByListId(tmpWork.getListId());
//					vo.setWorkName(workList.getWorkTitle());
//					vo.setExecuterId(tmpWork.getExecuterId());
//					User executer = this.userManager.getUserByUserId(tmpWork.getExecuterId());
//					vo.setExecuterName(executer.getName());
//					vo.setWorkState(0);
//					vo.setReadSafeRules(tmpWork.getReadSafeRules());
//
//					vo.setCreaterId(workList.getCreaterId());
//					User creater = this.userManager.getUserByUserId(workList.getCreaterId());
//					vo.setCreaterName(creater.getName());
//
//					String workTime = "";
//
//					Timestamp beginTimestamp = tmpWork.getBeginTime();
//					String beginDate = DateHelper.getString((beginTimestamp));
//
//					boolean notToday = false;
//					if(beginDate.equals(today)){ //今日的,直接写时间
//						workTime = sf_time.format(beginTimestamp);
//					}else{ //非今日的，写上日期
//						notToday = true;
//						workTime = sf_date.format(beginTimestamp);
//					}
//
//					vo.setWorkTime(workTime);
//					vo.setCommonWorkId(0);
//					vo.setPatrolItemId(0);
//					vo.setNightWatchWorkId(tmpWork.getWorkId());
//
//					// 详情
//					OpNightWatchVo nwVo = this.nwWorkManager.getNWDetail(tmpWork.getWorkId());
//					vo.setNwVo(nwVo);
//
//					if(notToday){
//						notTodayList_todo.add(vo);
//					}else {
//						todayList_todo.add(vo);
//					}
//				}
//
//				//将非今日先排序，排序后加入rtnList
//				Comparator<?> mycmp = ComparableComparator.getInstance();
//				mycmp = ComparatorUtils.nullLowComparator(mycmp); // 允许null
////		        if (!asc) {
//				mycmp = ComparatorUtils.reversedComparator(mycmp); // 逆序
////		        }
//				Collections.sort(notTodayList_todo, new BeanComparator("workTime", mycmp));
//				rtnList_todo.addAll(notTodayList_todo);
//
//				//再将今日排序，排序后加入rtnList
//				Collections.sort(todayList_todo, new BeanComparator("workTime", mycmp));
//				rtnList_todo.addAll(todayList_todo);
//
//				Collections.sort(rtnList_todo, new BeanComparator("workTime", mycmp));

                //
                /********* 3、未完成的整改工作 *********/
                String hql3_todo = "from ReformDetail reform where valid=1 and  findTime <= '" + todayE + "'";
                //  "and ((reformStatus=2";
//                hql3_todo += " and reform.dutyPerson.userId=" + userId;
//                hql3_todo += ") or (reformStatus=3 and findUser.userId=" + userId + ")";
//                if (isGrade2 || isGrade3) {
//                    hql3_todo += " or (reformStatus=1 and department.departmentId=" + userDeptId + ")";
//                }
//                hql3_todo += ") and (findTime <= '" + todayE + "')";

                List<ReformDetail> reformList = this.reformManager.getResultByQueryString(hql3_todo);
                List<ReformDetail> reformList_todo=new ArrayList<>();
                for (ReformDetail reformDetail:reformList){
                    if((reformDetail.getDutyPerson()!=null&&reformDetail.getReformStatus().equals
                            (2)&&reformDetail.getDutyPerson().getUserId().equals(userId))||
                            (reformDetail.getReformStatus().equals(1)&&reformDetail.getDepartment
                                    ().getDepartmentId().equals(userDeptId)&&(isGrade2||isGrade3))||
                            (reformDetail.getReformStatus().equals(3)&&reformDetail.getFindUser().getUserId
                                    ().equals(userId))){
                        reformList_todo.add(reformDetail);
                    }
                }

                for (ReformDetail reform : reformList_todo) {
                    TodayWorkVo vo = new TodayWorkVo();
                    vo.setWorkName(reform.getErrorTitle());
                    if (reform.getDutyPerson() != null) {
                        vo.setExecuterId(reform.getDutyPerson().getUserId());
                        vo.setExecuterName(reform.getDutyPerson().getName());
                    }
                    vo.setWorkState(reform.getReformStatus());
                    vo.setCreaterId(reform.getFindUser().getUserId());
                    vo.setCreaterName(reform.getFindUser().getName());

                    String workTime = "";

                    Date findTime = reform.getFindTime();
                    String beginDate = DateHelper.getString((findTime));
                    boolean notToday = false;
                    if (beginDate.equals(today)) { //今日的,直接写时间
                        workTime = sf_time.format(findTime);
                    } else { //非今日的，写上日期
                        notToday = true;
                        workTime = sf_date.format(findTime);
                    }

                    vo.setWorkTime(workTime);
                    vo.setCommonWorkId(0);
                    vo.setPatrolItemId(0);
                    vo.setNightWatchWorkId(0);
                    vo.setReformId(reform.getReformId());
                    // 详情
                    vo.setReformVo(this.reformManager.getReformDetail(reform.getReformId()));
                    if (notToday) {
                        notTodayList_todo.add(vo);
                    } else {
                        todayList_todo.add(vo);
                    }

                }

                //将非今日先排序，排序后加入rtnList
                Comparator<?> mycmp = ComparableComparator.getInstance();
                mycmp = ComparatorUtils.nullLowComparator(mycmp); // 允许null
//		        if (!asc) {
                mycmp = ComparatorUtils.reversedComparator(mycmp); // 逆序
//		        }
                Collections.sort(notTodayList_todo, new BeanComparator("workTime", mycmp));
                rtnList_todo.addAll(notTodayList_todo);

                //再将今日排序，排序后加入rtnList
                Collections.sort(todayList_todo, new BeanComparator("workTime", mycmp));
                rtnList_todo.addAll(todayList_todo);

                Collections.sort(rtnList_todo, new BeanComparator("workTime", mycmp));

                /**************我发出的**************/
                if (isGrade2 || isGrade3) {
                    //非今日
                    List<TodayWorkVo> notTodayList_mine = new ArrayList<TodayWorkVo>();
                    //今日
                    List<TodayWorkVo> todayList_mine = new ArrayList<TodayWorkVo>();

                    /********* 1、取今天以前发出的、但未完成的，及今日发出的所有的日常工作 *********/
                    String hql1_mine = "from XWork where valid=1 ";
//					if(!canGetAll){
                    hql1_mine += " and createrId=" + userId;
//					}
                    hql1_mine += " and ((workState=0 and beginTime < '" + todayB + "')";
                    hql1_mine += " or (beginTime >= '" + todayB + "')";
                    hql1_mine += ")";

                    List<XWork> xworkList_mine = this.xWorkManager.getResultByQueryString(hql1_mine);

                    for (XWork tmpWork : xworkList_mine) {
                        TodayWorkVo vo = new TodayWorkVo();
                        vo.setWorkName(tmpWork.getWorkTitle());
                        vo.setExecuterId(tmpWork.getExecuterId());
                        User executer = this.userManager.getUserByUserId(tmpWork.getExecuterId());
                        vo.setExecuterName(executer.getName());
                        vo.setWorkState(tmpWork.getWorkState());

                        vo.setCreaterId(tmpWork.getCreaterId());
                        User creater = this.userManager.getUserByUserId(tmpWork.getCreaterId());
                        vo.setCreaterName(creater.getName());

                        String workTime = "";

                        Timestamp beginTimestamp = tmpWork.getBeginTime();
                        String beginDate = DateHelper.getString((beginTimestamp));

                        boolean notToday = false;
                        if (beginDate.equals(today)) { //今日的,直接写时间
                            workTime = sf_time.format(beginTimestamp);
                        } else { //非今日的，写上日期
                            notToday = true;
                            workTime = sf_date.format(beginTimestamp);
                        }

                        vo.setWorkTime(workTime);
                        vo.setCommonWorkId(tmpWork.getWorkId());
                        vo.setPatrolItemId(0);
                        vo.setNightWatchWorkId(0);
                        vo.setReformId(0);

                        if (notToday) {
                            notTodayList_mine.add(vo);
                        } else {
                            todayList_mine.add(vo);
                        }
                    }

                    /********* 2、取今天以前发出的、但未完成的，及今日发出的所有的巡检工作 *********/
                    String hql2_mine = "from WorkItem where valid=1 ";
                    hql2_mine += " and listId in (select listId from WorkList where valid=1 and createrId=" + userId + ")";
                    hql2_mine += " and ((workState=0 and beginTime < '" + todayB + "')";
                    hql2_mine += " or (beginTime >= '" + todayB + "')";
                    hql2_mine += ")";


                    List<WorkItem> patrolWorkList_mine = this.workItemManager.getResultByQueryString(hql2_mine);

                    for (WorkItem tmpWork : patrolWorkList_mine) {
                        TodayWorkVo vo = new TodayWorkVo();

                        WorkList workList = this.workListManager.getWorkListByListId(tmpWork.getListId());
                        vo.setWorkName(workList.getWorkTitle());
                        vo.setExecuterId(tmpWork.getOperatorId());
                        User executer = this.userManager.getUserByUserId(tmpWork.getOperatorId());
                        vo.setExecuterName(executer.getName());
                        vo.setReadSafeRules(tmpWork.getReadSafeRules());
                        vo.setWorkState(tmpWork.getWorkState());

                        vo.setCreaterId(workList.getCreaterId());
                        User creater = this.userManager.getUserByUserId(workList.getCreaterId());
                        vo.setCreaterName(creater.getName());

                        String workTime = "";

                        Timestamp beginTimestamp = tmpWork.getBeginTime();
                        String beginDate = DateHelper.getString((beginTimestamp));

                        boolean notToday = false;
                        if (beginDate.equals(today)) { //今日的,直接写时间
                            workTime = sf_time.format(beginTimestamp);
                        } else { //非今日的，写上日期
                            notToday = true;
                            workTime = sf_date.format(beginTimestamp);
                        }

                        vo.setWorkTime(workTime);
                        vo.setCommonWorkId(0);
                        vo.setPatrolItemId(tmpWork.getItemId());
                        vo.setNightWatchWorkId(0);
                        vo.setReformId(0);

                        // 详情
//						OpPatrolVo patrolVo = this.workItemManager.getPatrolDetail(tmpWork.getItemId());
//						vo.setPatrolVo(patrolVo);

                        if (notToday) {
                            notTodayList_mine.add(vo);
                        } else {
                            todayList_mine.add(vo);
                        }
                    }

//					/********* 3、取今天以前发出的、但未完成的，及今日发出的所有的巡更工作 *********/
//					String hql3_mine = "from NwWork where valid=1 ";
//					hql3_mine += " and listId in (select listId from NwList where valid=1 and createrId=" + userId + ")";
//					hql3_mine += " and ((workState=0 and beginTime < '" + todayB + "')";
//					hql3_mine += " or (beginTime >= '" + todayB + "')";
//					hql3_mine += ")";
//
//
//
//					List<NwWork> nwWorkList_mine = this.nwWorkManager.getResultByQueryString(hql3_mine);
//
//					for(NwWork tmpWork : nwWorkList_mine){
//						TodayWorkVo vo = new TodayWorkVo();
//
//						NwList workList = this.nwListManager.getListByListId(tmpWork.getListId());
//						vo.setWorkName(workList.getWorkTitle());
//						vo.setExecuterId(tmpWork.getExecuterId());
//						User executer = this.userManager.getUserByUserId(tmpWork.getExecuterId());
//						vo.setExecuterName(executer.getName());
//						vo.setWorkState(tmpWork.getWorkState());
//
//						vo.setCreaterId(workList.getCreaterId());
//						User creater = this.userManager.getUserByUserId(workList.getCreaterId());
//						vo.setCreaterName(creater.getName());
//						vo.setReadSafeRules(tmpWork.getReadSafeRules());
//
//						String workTime = "";
//
//						Timestamp beginTimestamp = tmpWork.getBeginTime();
//						String beginDate = DateHelper.getString((beginTimestamp));
//
//						boolean notToday = false;
//						if(beginDate.equals(today)){ //今日的,直接写时间
//							workTime = sf_time.format(beginTimestamp);
//						}else{ //非今日的，写上日期
//							notToday = true;
//							workTime = sf_date.format(beginTimestamp);
//						}
//
//						vo.setWorkTime(workTime);
//						vo.setCommonWorkId(0);
//						vo.setPatrolItemId(0);
//						vo.setNightWatchWorkId(tmpWork.getWorkId());
//
//						// 详情
////						OpNightWatchVo nwVo = this.nwWorkManager.getNWDetail(tmpWork.getWorkId());
////						vo.setNwVo(nwVo);
//
//						if(notToday){
//							notTodayList_mine.add(vo);
//						}else {
//							todayList_mine.add(vo);
//						}
//					}
                    /********* 3、发出的整改工作 *********/
                    String hql3_mine = "from ReformDetail where valid=1 ";
                    hql3_mine += " and superviseDeptId=" + userDeptId;
                    hql3_mine += " and ((reformStatus<4 and findTime < '" + todayB + "')";
                    hql3_mine += " or (findTime >= '" + todayB + "'))";

                    List<ReformDetail> reformList_mine = this.reformManager.getResultByQueryString(hql3_mine);

                    for (ReformDetail tmpWork : reformList_mine) {
                        TodayWorkVo vo = new TodayWorkVo();
                        vo.setWorkName(tmpWork.getErrorTitle());
                        if (tmpWork.getDutyPerson() != null) {
                            vo.setExecuterId(tmpWork.getDutyPerson().getUserId());
                            vo.setExecuterName(tmpWork.getDutyPerson().getName());
                        }
                        vo.setCreaterId(tmpWork.getFindUser().getUserId());
                        vo.setCreaterName(tmpWork.getFindUser().getName());
                        vo.setWorkState(tmpWork.getReformStatus());

                        String workTime = "";

                        Date findTime = tmpWork.getFindTime();
                        String beginDate = DateHelper.getString((findTime));
//
                        boolean notToday = false;
                        if (beginDate.equals(today)) { //今日的,直接写时间
                            workTime = sf_time.format(findTime);
                        } else { //非今日的，写上日期
                            notToday = true;
                            workTime = sf_date.format(findTime);
                        }

                        vo.setWorkTime(workTime);
                        vo.setCommonWorkId(0);
                        vo.setPatrolItemId(0);
                        vo.setNightWatchWorkId(0);
                        vo.setReformId(tmpWork.getReformId());
                        // 详情
                        vo.setReformVo(this.reformManager.getReformDetail(tmpWork.getReformId()));
//
                        if (notToday) {
                            notTodayList_mine.add(vo);
                        } else {
                            todayList_mine.add(vo);
                        }
                    }
//

                    Collections.sort(notTodayList_mine, new BeanComparator("workTime", mycmp));
                    rtnList_mine.addAll(notTodayList_mine);

                    //再将今日排序，排序后加入rtnList
                    Collections.sort(todayList_mine, new BeanComparator("workTime", mycmp));
                    rtnList_mine.addAll(todayList_mine);


                    Collections.sort(rtnList_mine, new BeanComparator("workTime", mycmp));
                }


                /****************今日所有未完成及已完成的*****************/
                if (isGrade3) {
                    //非今日
                    List<TodayWorkVo> notTodayList_todayAll = new ArrayList<TodayWorkVo>();
                    //今日
                    List<TodayWorkVo> todayList_todayAll = new ArrayList<TodayWorkVo>();

                    /********* 1、取时间段位于今天的、未完成及已完成的日常工作 *********/
                    String hql1_todayAll = "from XWork where valid=1 ";
                    hql1_todayAll += " and ((workState=0 and (beginTime <= '" + todayE + "' or endTime >='" + todayB + "'))";
                    hql1_todayAll += " or (workState = 1 and finishTime >= '" + todayB + "')";
                    hql1_todayAll += ")";

                    List<XWork> xworkList_todayAll = this.xWorkManager.getResultByQueryString(hql1_todayAll);

                    for (XWork tmpWork : xworkList_todayAll) {
                        TodayWorkVo vo = new TodayWorkVo();
                        vo.setWorkName(tmpWork.getWorkTitle());
                        vo.setExecuterId(tmpWork.getExecuterId());
                        User executer = this.userManager.getUserByUserId(tmpWork.getExecuterId());
                        vo.setExecuterName(executer.getName());
                        vo.setWorkState(tmpWork.getWorkState());

                        vo.setCreaterId(tmpWork.getCreaterId());
                        User creater = this.userManager.getUserByUserId(tmpWork.getCreaterId());
                        vo.setCreaterName(creater.getName());

                        String workTime = "";

                        Timestamp beginTimestamp = tmpWork.getBeginTime();
                        String beginDate = DateHelper.getString((beginTimestamp));

                        boolean notToday = false;
                        if (beginDate.equals(today)) { //今日的,直接写时间
                            workTime = sf_time.format(beginTimestamp);
                        } else { //非今日的，写上日期
                            notToday = true;
                            workTime = sf_date.format(beginTimestamp);
                        }

                        vo.setWorkTime(workTime);
                        vo.setCommonWorkId(tmpWork.getWorkId());
                        vo.setPatrolItemId(0);
                        vo.setNightWatchWorkId(0);
                        vo.setReformId(0);

                        if (notToday) {
                            notTodayList_todayAll.add(vo);
                        } else {
                            todayList_todayAll.add(vo);
                        }
                    }

                    /********* 2、取时间段位于今天的、未完成及已完成的巡检工作 *********/
                    String hql2_todayAll = "from WorkItem where valid=1 ";
                    hql2_todayAll += " and ((workState=0 and beginTime <= '" + todayE + "')";// or endTime >='" + now + "'
                    hql2_todayAll += " or (workState = 1 and finishTime >= '" + todayB + "')";
                    hql2_todayAll += ")";
                    List<WorkItem> patrolWorkList_todayAll = this.workItemManager.getResultByQueryString(hql2_todayAll);

                    for (WorkItem tmpWork : patrolWorkList_todayAll) {
                        TodayWorkVo vo = new TodayWorkVo();

                        WorkList workList = this.workListManager.getWorkListByListId(tmpWork.getListId());
                        vo.setWorkName(workList.getWorkTitle());
                        vo.setExecuterId(tmpWork.getOperatorId());
                        User executer = this.userManager.getUserByUserId(tmpWork.getOperatorId());
                        vo.setExecuterName(executer.getName());
                        vo.setReadSafeRules(tmpWork.getReadSafeRules());
                        vo.setWorkState(tmpWork.getWorkState());

                        vo.setCreaterId(workList.getCreaterId());
                        User creater = this.userManager.getUserByUserId(workList.getCreaterId());
                        vo.setCreaterName(creater.getName());

                        String workTime = "";

                        Timestamp beginTimestamp = tmpWork.getBeginTime();
                        String beginDate = DateHelper.getString((beginTimestamp));

                        boolean notToday = false;
                        if (beginDate.equals(today)) { //今日的,直接写时间
                            workTime = sf_time.format(beginTimestamp);
                        } else { //非今日的，写上日期
                            notToday = true;
                            workTime = sf_date.format(beginTimestamp);
                        }

                        vo.setWorkTime(workTime);
                        vo.setCommonWorkId(0);
                        vo.setPatrolItemId(tmpWork.getItemId());
                        vo.setNightWatchWorkId(0);
                        vo.setReformId(0);

                        // 详情
//						OpPatrolVo patrolVo = this.workItemManager.getPatrolDetail(tmpWork.getItemId());
//						vo.setPatrolVo(patrolVo);

                        if (notToday) {
                            notTodayList_todayAll.add(vo);
                        } else {
                            todayList_todayAll.add(vo);
                        }
                    }

                    //********* 3、取时间段位于今天的，未完成的巡更工作 *********/
//					String hql3_todayAll = "from NwWork where valid=1 ";
//					hql3_todayAll += " and ((workState=0 and beginTime <= '" + todayE + "')"; // or endTime >='" + now + "'
//					hql3_todayAll += " or (workState = 1 and finishTime >= '" + todayB + "')";
//					hql3_todayAll += " )";
//
//					List<NwWork> nwWorkList_todayAll = this.nwWorkManager.getResultByQueryString(hql3_todayAll);
//
//					for(NwWork tmpWork : nwWorkList_todayAll){
//						TodayWorkVo vo = new TodayWorkVo();
//
//						NwList workList = this.nwListManager.getListByListId(tmpWork.getListId());
//						vo.setWorkName(workList.getWorkTitle());
//						vo.setExecuterId(tmpWork.getExecuterId());
//						User executer = this.userManager.getUserByUserId(tmpWork.getExecuterId());
//						vo.setExecuterName(executer.getName());
//						vo.setWorkState(tmpWork.getWorkState());
//
//						vo.setCreaterId(workList.getCreaterId());
//						User creater = this.userManager.getUserByUserId(workList.getCreaterId());
//						vo.setCreaterName(creater.getName());
//						vo.setReadSafeRules(tmpWork.getReadSafeRules());
//
//						String workTime = "";
//
//						Timestamp beginTimestamp = tmpWork.getBeginTime();
//						String beginDate = DateHelper.getString((beginTimestamp));
//
//						boolean notToday = false;
//						if(beginDate.equals(today)){ //今日的,直接写时间
//							workTime = sf_time.format(beginTimestamp);
//						}else{ //非今日的，写上日期
//							notToday = true;
//							workTime = sf_date.format(beginTimestamp);
//						}
//
//						vo.setWorkTime(workTime);
//						vo.setCommonWorkId(0);
//						vo.setPatrolItemId(0);
//						vo.setNightWatchWorkId(tmpWork.getWorkId());
//
//						// 详情
////						OpNightWatchVo nwVo = this.nwWorkManager.getNWDetail(tmpWork.getWorkId());
////						vo.setNwVo(nwVo);
//
//						if(notToday){
//							notTodayList_todayAll.add(vo);
//						}else {
//							todayList_todayAll.add(vo);
//						}
//					}


                    /********* 3、今日所有整改工作（今日以前未完成及今日所有） *********/
                    String hql3_todayAll = "from ReformDetail where valid=1 ";
                    hql3_todayAll += " and ((reformStatus<4 and findTime <= '" + todayE + "')"; // or endTime >='" + now + "'
                    hql3_todayAll += " or (reformStatus = 4 and findTime >= '" + todayB + "')";
                    hql3_todayAll += " )";
//
                    List<ReformDetail> reform_todayAll = this.reformManager.getResultByQueryString(hql3_todayAll);
//
                    for (ReformDetail tmpWork : reform_todayAll) {
                        TodayWorkVo vo = new TodayWorkVo();
//
                        vo.setWorkName(tmpWork.getErrorTitle());
                        if (tmpWork.getDutyPerson() != null) {
                            vo.setExecuterId(tmpWork.getDutyPerson().getUserId());
                            vo.setExecuterName(tmpWork.getDutyPerson().getName());
                        }
                        vo.setWorkState(tmpWork.getReformStatus());
                        vo.setCreaterId(tmpWork.getFindUser().getUserId());
                        vo.setCreaterName(tmpWork.getFindUser().getName());

                        String workTime = "";

                        Date findTime = tmpWork.getFindTime();
                        String beginDate = DateHelper.getString((findTime));

                        boolean notToday = false;
                        if (beginDate.equals(today)) { //今日的,直接写时间
                            workTime = sf_time.format(findTime);
                        } else { //非今日的，写上日期
                            notToday = true;
                            workTime = sf_date.format(findTime);
                        }

                        vo.setWorkTime(workTime);
                        vo.setCommonWorkId(0);
                        vo.setPatrolItemId(0);
                        vo.setNightWatchWorkId(0);
                        vo.setReformId(tmpWork.getReformId());
                        // 详情
                        vo.setReformVo(this.reformManager.getReformDetail(tmpWork.getReformId()));

                        if (notToday) {
                            notTodayList_todayAll.add(vo);
                        } else {
                            todayList_todayAll.add(vo);
                        }
                    }
//
                    Collections.sort(notTodayList_todayAll, new BeanComparator("workTime", mycmp));
                    rtnList_todayAll.addAll(notTodayList_todayAll);

                    //再将今日排序，排序后加入rtnList
                    Collections.sort(todayList_todayAll, new BeanComparator("workTime", mycmp));
                    rtnList_todayAll.addAll(todayList_todayAll);

                    Collections.sort(rtnList_todayAll, new BeanComparator("workTime", mycmp));
                }


            } else {
                message = "请登录！";
                success = false;
            }


        } catch (Exception e) {
            message = "后台出错，请重试！";
            success = false;
            e.printStackTrace();
            logger.error("出现错误=================" + e.getLocalizedMessage());
            logger.error(e, e.fillInStackTrace());
        }
        System.out.println(rtnList_mine);
        System.out.println(rtnList_todayAll);
        System.out.println(rtnList_todo);
        jsonObj.put("success", success);
        jsonObj.put("message", message);
        jsonObj.put("result_todo", rtnList_todo);
        jsonObj.put("result_mine", rtnList_mine);
        jsonObj.put("result_todayAll", rtnList_todayAll);
        //设置字符编码
        response.setContentType(CoreConstant.CONTENT_TYPE);
        response.getWriter().print(jsonObj);
    }
}
