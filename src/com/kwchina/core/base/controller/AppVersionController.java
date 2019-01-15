package com.kwchina.core.base.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.kwchina.core.base.entity.AppVersion;
import com.kwchina.core.base.service.AppVersionManager;
import com.kwchina.core.common.controller.BasicController;
import com.kwchina.core.sys.CoreConstant;
import com.kwchina.core.util.json.JSONConvert;

/**
 * 检查版本更新
 * @author suguan
 *
 */
@Controller
@RequestMapping("/version.htm")
public class AppVersionController extends BasicController {
	
	@Resource
	private AppVersionManager appVersionManager;
	
	
	
	/**
	 * 最新版本
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params="method=checkUpdate")
	public void list(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		JSONObject jsonObj = new JSONObject();
		
		//返回值
		boolean success = true;
		String message = "";
		
		//部门信息
//		JSONArray versionArray = new JSONArray();
		AppVersion newestVersionEntity = new AppVersion();
		
		//参数：客户端带入的version
		String oldVersion = request.getParameter("version");
		try {
			if(oldVersion != null && !oldVersion.equals("")){
				String queryString = "from AppVersion version order by version.updateTime desc";
				List allVersions = this.appVersionManager.getResultByQueryString(queryString);
				if(allVersions != null && allVersions.size() > 0){
					AppVersion newVersionEntity = (AppVersion)allVersions.get(0);
					if(newVersionEntity != null){
						String newVersion = newVersionEntity.getVersion();
						if(oldVersion.equals(newVersion)){
							success = false;
							message = "当前已是最新版本！";
						}else {
							success = true;
							message = "检测到新版本"+newVersion+"！";
							newestVersionEntity = newVersionEntity;
						}
					}else {
						success = false;
						message = "未检测到更新版本！";
					}
				}else {
					success = false;
					message = "未检测到更新版本！";
				}
				
			}else {
				success = false;
				message = "未获得当前版本，请检查后重试！";
			}
			
			
		} catch (RuntimeException e) {
			success = false;
			message = "后台出错，请重试！";
			e.printStackTrace();
		}
		//jsonObj.put("_LoginUsers", userArray);
		//返回值
		jsonObj.put("success", success);
		jsonObj.put("message", message);
		jsonObj.put("result", newestVersionEntity);
		
		//设置字符编码
        response.setContentType(CoreConstant.CONTENT_TYPE);
        response.getWriter().print(jsonObj);		
	}
	
	
	
	
}
