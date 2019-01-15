package com.kwchina.work.morningMeet.controller;

import com.kwchina.core.base.service.UserManager;
import com.kwchina.core.common.controller.BasicController;
import com.kwchina.core.util.DateHelper;
import com.kwchina.core.util.string.StringUtil;
import com.kwchina.work.errorWork.entity.ErrorWork;
import com.kwchina.work.errorWork.enums.ErrorTypeEnum;
import com.kwchina.work.errorWork.service.ErrorWorkManager;
import com.kwchina.work.errorWork.utils.convert.Convert;
import com.kwchina.work.handOver.entity.HandDetail;
import com.kwchina.work.handOver.service.HandOverManager;
import com.kwchina.work.handOver.vo.HandOverVO;
import com.kwchina.work.morningMeet.entity.UnionCheck;
import com.kwchina.work.morningMeet.service.UnionManager;
import com.kwchina.work.morningMeet.vo.*;
import com.kwchina.work.patrol.entity.BigCategory;
import com.kwchina.work.patrol.entity.ItemDetail;
import com.kwchina.work.patrol.entity.WorkItem;
import com.kwchina.work.patrol.entity.WorkList;
import com.kwchina.work.patrol.service.*;
import com.kwchina.work.peccancy.entity.PeccancyDetail;
import com.kwchina.work.peccancy.service.PeccancyDetailManager;
import com.kwchina.work.reform.entity.ReformDetail;
import com.kwchina.work.reform.service.ReformManager;
import com.kwchina.work.util.EnumUtil;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.support.DefaultMultipartHttpServletRequest;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.*;


@Controller
@RequestMapping("/morning.htm")
public class MorningController extends BasicController {
    Logger logger = Logger.getLogger(MorningController.class);

    @Resource
    private ItemDetailManager itemDetailManager;

    @Resource
    private BigCategoryManager bigCategoryManager;

    @Resource
    private PeccancyDetailManager peccancyDetailManager;

    @Resource
    private ReformManager reformManager;
    @Resource
    private WorkItemManager workItemManager;

    @Resource
    private ErrorWorkManager errorWorkManager;
    @Resource
    private SmallCategoryManager smallCategoryManager;
    @Resource
    private WorkListManager workListManager;
    @Resource
    private HandOverManager handOverManager;
    @Resource
    private UserManager userManager;
    @Resource
    private UnionManager unionManager;


    /***
     * 晨会报表
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(params = "method=morningList")
    public String morningList(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        List<UnionVo> unionVos = new ArrayList<>();
        List<ReformVo> reformVos = new ArrayList<>();
        List<PeccancyVo> peccancyVos = new ArrayList<>();
        List<WorkItemVo> workItemVos = new ArrayList<>();
        List<HandleVo> handleVos = new ArrayList<>();
        String beginTime = request.getParameter("beginDate");
        String endTime = request.getParameter("endDate");
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        Date date = new Date();
        Date time = cal.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        if (!StringUtil.isNotEmpty(beginTime)) {
            beginTime = sdf.format(time);
        }
        if (!StringUtil.isNotEmpty(endTime)) {
            endTime = sdf.format(date);
        }
        model.addAttribute("beginDate", beginTime);
        model.addAttribute("endDate", endTime);
        String hql1 = " from UnionCheck detail where reportDate >= '" + beginTime + " 08:00:00' and reportDate <= '" + endTime + " 08:00:00 '";
        String hql2 = " from ReformDetail detail where valid=1 and morningReport=1 and findTime >= '" + beginTime + " 08:00:00 'and findTime <= '" + endTime + " 08:00:00' order by findTime";
        String hql3 = " from PeccancyDetail detail where valid=1 and morningReport=1 and markTime >= '" + beginTime + " 08:00:00 'and markTime <= '" + endTime + " 08:00:00' order by markTime";
        String hql4 = " from WorkItem item where finishTime >= '" + beginTime + " 08:00:00 'and finishTime <= '" + endTime + " 08:00:00' order by finishTime";
        String hql5 = "from HandDetail detail where valid=1 and handDate >= '" + beginTime + " 08:00:00 'and handDate <= '" + endTime + " 08:00:00' order by handDate";
        List<UnionCheck> checks = this.unionManager.getResultByQueryString(hql1);
        List<ReformDetail> reforms = this.reformManager.getResultByQueryString(hql2);
        List<PeccancyDetail> peccancys = this.peccancyDetailManager.getResultByQueryString(hql3);
        List<WorkItem> workItems = this.workItemManager.getResultByQueryString(hql4);
        List<HandDetail> handDetails = this.handOverManager.getResultByQueryString(hql5);

        for (UnionCheck union : checks) {
            UnionVo unionVo = new UnionVo();
            unionVo.setUnionId(union.getUnionId());
            unionVo.setReporterId(union.getUnionId());
            unionVo.setReporterName(union.getReporter().getName());
            unionVo.setCategory(union.getCategory());
            unionVo.setReportTime(DateHelper.getString(union.getReportDate()));
            List<String> attachs = new ArrayList<>();
            if (StringUtil.isNotEmpty(union.getAttach())) {
                for (int i = 0; i < union.getAttach().split(";").length; i++) {
                    String s = union.getAttach().split(";")[i];
                    attachs.add(s.split("\\|")[0]);
                }
            }
            unionVo.setUnionAttachs(attachs);
            unionVos.add(unionVo);
        }
        for (ReformDetail reformDetail : reforms) {
            ReformVo reformVo = new ReformVo();
            reformVo.setReformId(reformDetail.getReformId());
            reformVo.setContent(reformDetail.getErrorContent());
            List<String> arrAttach = new ArrayList<>();
            if (StringUtil.isNotEmpty(reformDetail.getErrorAttach())) {
                for (int i = 0; i < reformDetail.getErrorAttach().split(";").length; i++) {
                    String s = reformDetail.getErrorAttach().split(";")[i];
                    arrAttach.add(s.split("\\|")[0]);
                }
            }
            reformVo.setErrorAttachs(arrAttach);
            reformVos.add(reformVo);
        }
        for (PeccancyDetail peccancyDetail : peccancys) {
            PeccancyVo peccancyVo = new PeccancyVo();
            peccancyVo.setPeccancyId(peccancyDetail.getPeccancyId());
            peccancyVo.setContent(peccancyDetail.getContent());
            List<String> arrAttach = new ArrayList<>();
            if (StringUtil.isNotEmpty(peccancyDetail.getAttachment())) {
                for (int i = 0; i < peccancyDetail.getAttachment().split(";").length; i++) {
                    String s = peccancyDetail.getAttachment().split(";")[i];
                    arrAttach.add(s.split("\\|")[0]);
                }
            }
            peccancyVo.setErrorAttachs(arrAttach);
            peccancyVos.add(peccancyVo);
        }
        for (WorkItem workItem : workItems) {
            WorkItemVo itemVo = new WorkItemVo();
            WorkList workList = this.workListManager.getWorkListByListId(workItem.getListId());
            itemVo.setWorkId(workList.getListId());
            itemVo.setWorkName(workList.getWorkTitle());
            List<PatrolVo> patrolVos = new ArrayList<>();
            if (workList.getBigItems() != null) {
                for (int i = 0; i < workList.getBigItems().split(",").length; i++) {
                    String bigIdStr = workList.getBigItems().split(",")[i];
                    Integer bigId = Integer.parseInt(bigIdStr);
                    BigCategory bigCategory = this.bigCategoryManager.getBigCategoryByBigId(bigId);
                    PatrolVo patrolVo = new PatrolVo();
                    patrolVo.setBigName(bigCategory.getCategoryName());
                    List<ItemDetail> items = this.itemDetailManager.getDetailsByItemId(workItem.getItemId());
                    List<ItemDetailVo> itemVos = new ArrayList<>();
                    for (ItemDetail itemDetail : items) {
                        if (itemDetail.getBigId().equals(bigId)) {
                            ItemDetailVo detailVo = new ItemDetailVo();
                            detailVo.setSmallName(this.smallCategoryManager.getSmallCategoryBySmallId(itemDetail.getSmallId()).getCategoryName());
                            detailVo.setResult(itemDetail.getOpResult());
                            itemVos.add(detailVo);
                            patrolVo.setItems(itemVos);
                        }
                    }
                    patrolVos.add(patrolVo);
                    itemVo.setPatrolVos(patrolVos);
                }
                workItemVos.add(itemVo);
            }
        }
        for (HandDetail handDetail : handDetails) {
            HandleVo handleVo = new HandleVo();
            handleVo.setHandleId(handDetail.getHandId());
            handleVo.setTitle(handDetail.getTitle());
            handleVo.setContent(handDetail.getContent());
            List<String> attaches = new ArrayList<>();
            if (StringUtil.isNotEmpty(handDetail.getAttach())) {
                for (int i = 0; i < handDetail.getAttach().split(";").length; i++) {
                    String s = handDetail.getAttach().split(";")[i];
                    attaches.add(s.split("\\|")[0]);
                }
            }
            handleVo.setAttaches(attaches);
            handleVos.add(handleVo);
        }
        System.out.println(workItemVos);
        model.addAttribute("unionVos", unionVos);
        model.addAttribute("reformVos", reformVos);
        model.addAttribute("peccancyVos", peccancyVos);
        model.addAttribute("workItemVos", workItemVos);
        model.addAttribute("handleVos", handleVos);
        return "chenhui/morning";
    }

    /***
     * 汇报内容录入
     * @throws Exception
     */
    @RequestMapping(params = "method=addUnion")
    public String addUnion(UnionVo unionVo, DefaultMultipartHttpServletRequest multipartRequest) {
        UnionCheck unionCheck = new UnionCheck();
        unionCheck.setReporter(this.userManager.getUserByUserId(unionVo.getReporterId()));
        if (StringUtil.isNotEmpty(unionVo.getReportTime())) {
            unionCheck.setReportDate(Convert.stringToDate(unionVo.getReportTime(), "yyyy-MM-dd HH:mm:ss"));
        }
        unionCheck.setCategory(unionVo.getCategory());
        unionCheck.setAttach(this.uploadAttachmentBySize(multipartRequest, "union"));
        this.unionManager.save(unionCheck);
        return "core/success";

    }

    /***
     * 删除图片
     * @throws Exception
     */
    @RequestMapping(params = "method=delImg")
    public String delImg(HttpServletRequest request) {
        String filepath = request.getParameter("filepath");
        String unionId = request.getParameter("unionId");
        UnionCheck unionCheck = (UnionCheck) this.unionManager.get(Integer.parseInt(unionId));
        String attach = unionCheck.getAttach();
        String[] attachs = attach.split(";");
        List<String> list = Arrays.asList(attachs);
        List<String> attList = new ArrayList<>(list);

        if (StringUtil.isNotEmpty(attach)) {
            for (int i = 0; i < attList.size(); i++) {
                String s = attList.get(i);
                if (StringUtil.isNotEmpty(filepath) && filepath.equals(s.split("\\|")[0])) {
                    attList.remove(i);
                }
            }
        }
        String newAttach = "";
        for(int i=0;i<attList.size();i++)
        {
            newAttach+=attList.get(i);
            newAttach+=";";
        }
        unionCheck.setAttach(newAttach);
        this.unionManager.save(unionCheck);
        return "redirect:morning.htm?method=morningList";

    }

}





