package com.kwchina.work.handOver.controller;

import com.kwchina.core.base.entity.User;
import com.kwchina.core.base.service.UserManager;
import com.kwchina.core.common.controller.BasicController;
import com.kwchina.core.util.string.StringUtil;
import com.kwchina.work.errorWork.utils.convert.Convert;
import com.kwchina.work.handOver.entity.HandDetail;
import com.kwchina.work.handOver.service.HandOverManager;
import com.kwchina.work.handOver.vo.HandOverVO;
import com.kwchina.work.reform.vo.ReformVo;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.support.DefaultMultipartHttpServletRequest;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/handOver.htm")
public class HandOverController extends BasicController {
	Logger logger = Logger.getLogger(HandOverController.class);

	@Resource
	private UserManager userManager;
	@Resource
    private HandOverManager handOverManager;

	//保存交班重点
	@RequestMapping(params = "method=save")
	public String save(HandOverVO handOverVO, DefaultMultipartHttpServletRequest multipartRequest)throws Exception{
		HandDetail handDetail=new HandDetail();
		User hander=this.userManager.getUserByUserId(handOverVO.getHanderId());
        BeanUtils.copyProperties(handDetail,handOverVO);
        handDetail.setHander(hander);
        handDetail.setValid(true);
        String attach=this.uploadAttachmentBySize(multipartRequest,"handOver");
        handDetail.setAttach(attach);
        handDetail.setHandDate(Convert.stringToDate(handOverVO.getHandTime(),"yyyy-MM-dd HH:mm:ss"));
        this.handOverManager.save(handDetail);
        return "core/success";
	}

    /***
     * 编辑交班重点
     * @throws Exception
     */
    @RequestMapping(params = "method=editHand")
    public String editHand(HttpServletRequest request, ModelMap modelMap) {
        String filepath = request.getParameter("filepath");
        String handId = request.getParameter("handId");
        HandOverVO vo = this.handOverManager.getVoById(Integer.parseInt(handId));
        modelMap.addAttribute("handDetail", vo);
        return "handOver/editHand";

    }
    /***
     * 保存修改
     * @throws Exception
     */
	@RequestMapping(params = "method=saveChange")
	public  String saveChange(HandOverVO handOverVO, ModelMap modelMap, HttpServletRequest request, DefaultMultipartHttpServletRequest multipartRequest){
		HandDetail handDetail= (HandDetail) this.handOverManager.get(handOverVO.getHandId());
		handDetail.setTitle(handOverVO.getTitle());
		handDetail.setHandDate(Convert.stringToDate(handOverVO.getHandTime(),"yyyy-MM-dd HH:mm:ss"));
		handDetail.setHander(this.userManager.getUserByUserId(handOverVO.getHanderId()));
		handDetail.setContent(handOverVO.getContent());
		String attach = handDetail.getAttach();
		String[] attachs=attach.split(";");
		List<String> list=Arrays.asList(attachs);
		List<String> attList=new ArrayList<>(list);

		String[] images = request.getParameterValues("image");
		List<String> result;
		if(images!=null&&images.length>0){
			List<String> mList=Arrays.asList(images);
			List<String> imageList=new ArrayList<>(mList);
			result=removeAttaach(attList,imageList);
		}else{
			result=attList;
		}
		String newAttach="";
		for(int i=0;i<result.size();i++){
		    if (StringUtil.isNotEmpty(result.get(i))){
                newAttach+=result.get(i);
                newAttach+=";";
            }
		}
		newAttach+= this.uploadAttachmentBySize(multipartRequest, "handOver");
		handDetail.setAttach(newAttach);
        HandDetail detail = (HandDetail) this.handOverManager.save(handDetail);
        HandOverVO vo=this.handOverManager.getVoById(detail.getHandId());
        modelMap.addAttribute("handDetail",vo);
        return "handOver/editHand";
	}

    /***
     * 删除交班重点
     * @throws Exception
     */
    @RequestMapping(params = "method=deleteHand")
    @ResponseBody
    public String deleteHand(HttpServletRequest request) {
        String handId = request.getParameter("handId");
        HandDetail handDetail = (HandDetail) this.handOverManager.get(Integer.parseInt(handId));
        handDetail.setValid(false);
        this.handOverManager.save(handDetail);
        return "success";
    }
}
