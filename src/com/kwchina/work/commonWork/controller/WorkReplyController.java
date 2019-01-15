package com.kwchina.work.commonWork.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kwchina.work.sys.LoginConfirm;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.support.DefaultMultipartHttpServletRequest;

import com.kwchina.core.base.entity.User;
import com.kwchina.core.common.controller.BasicController;
import com.kwchina.core.sys.CoreConstant;
import com.kwchina.core.util.string.StringUtil;
import com.kwchina.work.commonWork.entity.XWorkReply;
import com.kwchina.work.commonWork.service.XWorkReplyManager;
import com.kwchina.work.sys.SysCommonMethod;


@Controller
@RequestMapping("/workReply.htm")
public class WorkReplyController extends BasicController {
	Logger logger = Logger.getLogger(WorkReplyController.class);
	
	
	
	@Resource
	private XWorkReplyManager xWorkReplyManager;
	
	
	
	/**
	 * 新建回复
	 * 
	 * @param request
	 * @param response
	 * @param vo
	 */
	@RequestMapping(params="method=newApply")
	@Transactional
	public void newApply(HttpServletRequest request,HttpServletResponse response,DefaultMultipartHttpServletRequest multipartRequest)throws Exception {
		String message = "";
		boolean success = true;

		JSONObject jsonObj = new JSONObject();
		//用户
		String token = request.getParameter("token");
		User user = LoginConfirm.loginUserMap.get(token);
		
		//参数：
		String workId = request.getParameter("workId");
		String content = request.getParameter("content");
		
//		Timestamp now = new Timestamp(System.currentTimeMillis());
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			
			if(user != null && StringUtil.isNotEmpty(workId)){
				XWorkReply reply = new XWorkReply();
				reply.setWorkId(Integer.valueOf(workId));
				reply.setOperateTime(sf.format(new Date()));
				reply.setOperatorId(user.getUserId());
				reply.setOperatorName(user.getName());
				reply.setContent(content);
				
				//附件
				String attachs = uploadAttachmentBySize(multipartRequest, "workReply");
				reply.setAttachs(attachs);
				
				this.xWorkReplyManager.save(reply);
			
			}else {
				message = "请登录！";
				success = false;
			}
			
			
		} catch (Exception e) {
			message = "后台出错，请重试！";
			success = false;
			e.printStackTrace();
			logger.error("出现错误================="+e.getLocalizedMessage());
			logger.error(e,e.fillInStackTrace());
		}
		
		jsonObj.put("success", success);
		jsonObj.put("message", message);
		
		//设置字符编码
        response.setContentType(CoreConstant.CONTENT_TYPE);
		response.getWriter().print(jsonObj);
	}
	
	/**
	 * 所有回复列表
	 * 
	 * @param request
	 * @param response
	 * @param vo
	 */
	@RequestMapping(params="method=listInfor")
	@Transactional
	public void listInfor(HttpServletRequest request,HttpServletResponse response)throws Exception {
		String message = "";
		boolean success = true;
		List<XWorkReply> rtnList = new ArrayList<XWorkReply>();

		JSONObject jsonObj = new JSONObject();
		//用户
		String token = request.getParameter("token");
		User user = LoginConfirm.loginUserMap.get(token);
		
		if(user == null){
			user = SysCommonMethod.getSystemUser(request);
		}
		
		//参数：
		String workId = request.getParameter("workId");
		
		try {
			
			if(user != null && StringUtil.isNotEmpty(workId)){
				rtnList = this.xWorkReplyManager.getReplysByWorkId(Integer.valueOf(workId));
				
				
			}else {
				message = "请登录！";
				success = false;
			}
			
			
		} catch (Exception e) {
			message = "后台出错，请重试！";
			success = false;
			e.printStackTrace();
			logger.error("出现错误================="+e.getLocalizedMessage());
			logger.error(e,e.fillInStackTrace());
		}
		
		jsonObj.put("success", success);
		jsonObj.put("message", message);
		jsonObj.put("result", rtnList);
		
		//设置字符编码
        response.setContentType(CoreConstant.CONTENT_TYPE);
		response.getWriter().print(jsonObj);
	}
	
}
