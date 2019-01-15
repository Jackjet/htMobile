package com.kwchina.work.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jpush.api.JPushClient;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Options;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.IosNotification;
import cn.jpush.api.push.model.notification.Notification;

public class PushUtil {

	private static Log log = LogFactory.getLog(PushUtil.class);

	private static final String appKey = "2f69e4a247323f86cb4e189e";
	private static final String masterSecret = "726c73a9f50c6bbd115460c2";
	private JPushClient jpushClient;
	private String title;
	private String content;
	
	public PushUtil(){}

	public PushUtil(String content) {
		try {
			this.content = content;
			jpushClient = new JPushClient(masterSecret, appKey);
		} catch (RuntimeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public PushUtil(String content, String title) {
		this(content);
		this.title = title;
	}

	/**
	 * 向所有人发送消息
	 * 
	 * @return 消息id
	 */
	public long sendPushAll() {
		PushPayload payload = buildPushObject_all_all_alert();
//		System.out.println(payload.toString());
		long msgId = 0;
		try {
			PushResult result = jpushClient.sendPush(payload);
			msgId = result.msg_id;
		} catch (APIConnectionException e) {
			// TODO Auto-generated catch block
			log.error("Connection error. Should retry later. ", e);
		} catch (APIRequestException e) {
			log.info("HTTP Status: " + e.getStatus());
			msgId = e.getMsgId();
		}
		return msgId;
	}

	/**
	 * 向指定别名的客户端发送消息
	 * 
	 * @param alias
	 *            所有别名信息集合，这里表示发送所有学生编号
	 * @return 消息id
	 */
	public long sendPushAlias(String platForm,List<String> aliasList,Map<String, String> extras,int badge) {
		PushPayload payloadAlias = buildPushObject_aliasList_alertWithTitle(platForm,aliasList,extras,badge);
		//PushPayload payloadAlias = buildPushObject_all_alias_alertWithTitle(alias);
		long msgId = 0;
		try {
			PushResult result = jpushClient.sendPush(payloadAlias);
			msgId = result.msg_id;

		} catch (APIConnectionException e) {
			log.error("Connection error. Should retry later. ", e);
		} catch (APIRequestException e) {
			log.info("HTTP Status: " + e.getStatus());
			log.info("Error Code: " + e.getErrorCode());
			log.info("Error Message: " + e.getErrorMessage());
			log.info("Msg ID: " + e.getMsgId());
			msgId = e.getMsgId();
		}
		return msgId;
	}

	/**
	 * 向指定组发送消息
	 * 
	 * @param tag
	 *            组名称
	 * @return 消息id
	 */
	public long sendPushTag(String tag) {
		PushPayload payloadtag = buildPushObject_tag_alertWithTitle("android",tag);
		long msgId = 0;
		try {
			PushResult result = jpushClient.sendPush(payloadtag);
			msgId = result.msg_id;
			log.info("Got result - " + result);
		} catch (APIConnectionException e) {
			log.error("Connection error. Should retry later. ", e);

		} catch (APIRequestException e) {
			log.info("HTTP Status: " + e.getStatus());
			log.info("Error Code: " + e.getErrorCode());
			log.info("Error Message: " + e.getErrorMessage());
			log.info("Msg ID: " + e.getMsgId());
			msgId = e.getMsgId();
		}
		return msgId;
	}

	/**
	 * 下列封装了三种获得消息推送对象（PushPayload）的方法
	 * buildPushObject_android_alias_alertWithTitle、
	 * buildPushObject_android_tag_alertWithTitle、 buildPushObject_all_all_alert
	 */
	public PushPayload buildPushObject_alias_alertWithTitle(String platForm,String alias,Map<String, String> extras,int badge) {
		
		
		if(platForm.equals("android")){
			return PushPayload.newBuilder().setPlatform(Platform.android())
			.setAudience(Audience.alias(alias)).setNotification(
					Notification.android("", title, extras)).build();
		}else if(platForm.equals("ios")){
			/*.setNotification(Notification.ios_set_badge(badge))
			return PushPayload.newBuilder().setPlatform(Platform.ios())
			.setAudience(Audience.alias(alias)).setNotification(Notification.ios_set_badge(badge)).setNotification(
					Notification.ios(content, extras)).setOptions(Options.newBuilder().setApnsProduction(true).build()).build();*/
			
			return PushPayload.newBuilder()
            .setPlatform(Platform.ios())
            .setAudience(Audience.alias(alias))
            .setNotification(Notification.newBuilder()
                    .addPlatformNotification(IosNotification.newBuilder()
                    .setAlert(content)
                    .setBadge(badge)
                    .setSound("happy.caf")
                    .addExtras(extras).build()).build())
             //.setMessage(Message.content(MSG_CONTENT))
            .setOptions(Options.newBuilder().setApnsProduction(true).build())
            .build();
			//True 表示推送生产环境，False 表示要推送开发环境

		}
		
		return null;
	}
	
	
	/**
	 * alias集合
	 * @param platForm
	 * @param alias
	 * @param extras
	 * @param badge
	 * @return
	 */
	public PushPayload buildPushObject_aliasList_alertWithTitle(String platForm,List<String> aliasList,Map<String, String> extras,int badge) {
		
		
		if(platForm.equals("android")){
			return PushPayload.newBuilder().setPlatform(Platform.android())
			.setAudience(Audience.alias(aliasList)).setNotification(
					Notification.android("", title, extras)).build();
		}else if(platForm.equals("ios")){
			/*.setNotification(Notification.ios_set_badge(badge))
			return PushPayload.newBuilder().setPlatform(Platform.ios())
			.setAudience(Audience.alias(alias)).setNotification(Notification.ios_set_badge(badge)).setNotification(
					Notification.ios(content, extras)).setOptions(Options.newBuilder().setApnsProduction(true).build()).build();*/
			
			return PushPayload.newBuilder()
            .setPlatform(Platform.ios())
            .setAudience(Audience.alias(aliasList))
            .setNotification(Notification.newBuilder()
                    .addPlatformNotification(IosNotification.newBuilder()
                    .setAlert(content)
                    .setBadge(badge)
                    .setSound("happy.caf")
                    .addExtras(extras).build()).build())
             //.setMessage(Message.content(MSG_CONTENT))
            .setOptions(Options.newBuilder().setApnsProduction(false).build())
            .build();
			//True 表示推送生产环境，False 表示要推送开发环境

		}
		
		return null;
	}
	
	public PushPayload buildPushObject_all_alias_alertWithTitle(String alias) {
		
			//return PushPayload.newBuilder().setAudience(Audience.alias(alias)).setNotification(Notification.android(content, title, null)).build();
		return PushPayload.newBuilder()
		.setPlatform(Platform.all())// 所有平台
		.setAudience(Audience.alias(alias))// 向选定的人推送
		.setNotification(Notification.alert(content))// 消息内容
		.build();
	}

	public PushPayload buildPushObject_tag_alertWithTitle(String platForm,String tag) {
		if(platForm.equals("android")){
			return PushPayload.newBuilder().setPlatform(Platform.android())
			.setAudience(Audience.tag(tag)).setNotification(
					Notification.android(content, title, null)).build();
		}else if(platForm.equals("ios")){
			return PushPayload.newBuilder().setPlatform(Platform.ios())
			.setAudience(Audience.tag(tag)).setNotification(
					Notification.android(content, title, null)).build();
		}
		return null;
	}

	public PushPayload buildPushObject_all_all_alert() {
		return PushPayload.alertAll(content);
	}
	
	
	/**
	 * 推送 "异常上报"
	 * @param instance
	 * @param badge
	 * @param alias
	 */
	public void pushErrorSubmit(Integer nwWorkId,String content,int badge,String alias){
		//每个用户推两个平台//"【"+flowName+"】"+
		PushUtil androidPushUtil = new PushUtil(content,"【巡更异常上报】");
		PushUtil iosPushUtil = new PushUtil("【巡更异常上报】" + content,"");
		
		Map<String, String> extras = new HashMap<String, String>();
		extras.put("badge", String.valueOf(badge));//ios用的角标
		extras.put("nwWorkId", String.valueOf(nwWorkId));//instanceId
		
		
		List<String> aliasList = new ArrayList<String>();
		aliasList.add(alias);
		long msgId_android = androidPushUtil.sendPushAlias("android", aliasList, extras, badge);
		long msgId_ios = iosPushUtil.sendPushAlias("ios", aliasList, extras, badge);
	}
	
	
	
	
	
	
	/**
	 * 通用推送
	 * @param instance
	 * @param badge
	 * @param alias
	 */
	public void commonPush(String pushTitle, String pushContent,int badge,List<String> aliasList,Map<String, String> extras){

		PushUtil androidPushUtil = new PushUtil(pushContent,pushTitle);
		PushUtil iosPushUtil = new PushUtil(pushContent,pushTitle);
		
		extras.put("badge", String.valueOf(badge));//ios用的角标
		
		long msgId_android = androidPushUtil.sendPushAlias("android", aliasList, extras, badge);
		long msgId_ios = iosPushUtil.sendPushAlias("ios", aliasList, extras, badge);
		sendPushAll();
	}
	
	public static void main(String[] args) {
		try {
			List<String> aliasList = new ArrayList<String>();
			aliasList.add("test");
			PushUtil androidUtil = new PushUtil("内容啊2","标题2");
			Map<String, String> map = new HashMap<String, String>();
			map.put("badge", "23");
			androidUtil.sendPushAlias("android", aliasList, map, 23);
//			androidUtil.sendPushAll();
			
//			PushUtil iosUtil = new PushUtil("内容啊2");
//			iosUtil.sendPushAlias("ios", aliasList, map, 23);
//			iosUtil.sendPushAll();
		} catch (RuntimeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}