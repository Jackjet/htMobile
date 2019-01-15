package com.kwchina.work.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import cn.jpush.api.schedule.ScheduleResult;

/**
 * 
 * @author zhusl
 * @Time 2017-5-31上午11:26:15
 * @包名 com.kwchina.xwork2.utils.push
 * @TODO 极光推送的帮助类
 */
public class JPushUtils {

	private static JPushUtils jPushUtils;

	private String jpushKey = "2f69e4a247323f86cb4e189e";// PropertiesUtils.getInstance().getJpushKey();

	private String jpush_masterSecret = "726c73a9f50c6bbd115460c2";// PropertiesUtils.getInstance().getJpushSecret();

	// 极光推送的生产环境和开发环境( true为生产　　False为开发环境)
	private boolean APNS_PRODUCTION = false;
	// 推送的标题 一般为项目名称
	private String title = "国客";// PropertiesUtils.getInstance().getPushTitle();
	private JPushClient jPushClient;

	public JPushUtils() {
		//jPushClient = new JPushClient(jpush_masterSecret, jpushKey);
		jPushClient = new JPushClient(jpush_masterSecret,jpushKey);
	}

	public static JPushUtils getInstance() {
		if (jPushUtils == null) {
			synchronized (JPushUtils.class) {
				if (jPushUtils == null) {
					jPushUtils = new JPushUtils();
				}
			}
		}
		return jPushUtils;
	}

	/**
	 * 
	 * @author zhusl
	 * @time 2017-6-1 下午12:44:38
	 * @TODO 全推
	 */
	public void pushAll(String content) {
		try {
			PushPayload all = PushPayload.alertAll(content);
			PushResult result = jPushClient.sendPush(all);
			System.out.println("极光全推的推送结果:" + result);
		} catch (Exception e) {
			System.out.println("APIRequestException   APIConnectionException");
		}
	}

	/**
	 * --------------------------------------- 极光的定时推送
	 * --------------------------------------------------
	 */

	/**
	 * 
	 * @author zhusl
	 * @time 2017-6-1 下午1:33:42
	 * @params 定时推送
	 */
	public void pushBytime(String content, String platform, String time,
			String alias) {
		try {
			PushPayload all = buildPushObject_alias_alertWithTitle(content,
					platform, alias, null, 1);
			ScheduleResult siResult = jPushClient.createSingleSchedule("时间推送",
					time, all);
			System.out.println("极光定时推送的结果" + siResult + "推送别名---------------"
					+ alias);
		} catch (APIConnectionException e) {
			e.printStackTrace();
		} catch (APIRequestException e) {
			e.printStackTrace();
		}
	}

	public PushPayload buildPushObject_alias_alertWithTitle(String content,
			String platForm, String alias, Map<String, String> extras, int badge) {

		if (platForm.equals("android")) {
			return PushPayload
					.newBuilder()
					.setPlatform(Platform.android())
					.setAudience(Audience.alias(alias))
					.setNotification(
							Notification.android(content, title, extras))
					.build();
		} else if (platForm.equals("ios")) {
			return PushPayload
					.newBuilder()
					.setPlatform(Platform.ios())
					.setAudience(Audience.alias(alias))
					.setNotification(
							Notification
									.newBuilder()
									.addPlatformNotification(
											IosNotification.newBuilder()
													.setAlert(content)
													.setBadge(1)
													.setSound("happy.caf")
													.addExtras(null).build())
									.build())
					.setOptions(
							Options.newBuilder()
									.setApnsProduction(APNS_PRODUCTION).build())
					.build();
		}
		return null;
	}

	/**
	 * -------------------------------------------- --------------------
	 * 以別名推送所有平台 --------------------------------------------
	 * --------------------------------------------
	 */

	/**
	 * 
	 * @author zhusl
	 * @time 2017-6-1 下午1:36:49
	 * @TODO 以别名推送所有平台
	 */
	public void pushAllByAlias(String alias, String content) {

		try {
			PushPayload all = buildAllByAlias(alias, content);
			PushResult result = jPushClient.sendPush(all);
			System.out.println("极光以別名推送 android  ios   结果:" + result);
		} catch (Exception e) {
			System.out.println("APIRequestException   APIConnectionException ");
		}
	}

	private PushPayload buildAllByAlias(String alias, String content) {
		return PushPayload.newBuilder().setPlatform(Platform.all())
				.setAudience(Audience.alias(alias))
				.setNotification(Notification.alert(content)).build();
	}

	/**
	 * -------------------------------------------- -------------------- 群推
	 * 根据别名列表进行推送 --------------------------------------------
	 * --------------------------------------------
	 */

	/**
	 * 
	 * @author zhusl
	 * @time 2017-6-1 下午1:42:21
	 * @params 根据别名列表进行推送
	 */
	public void pushAllByAliasList(String platForm, List<String> aliasList,
			Map<String, String> extras, int badge, boolean hasSound,
			String content) {

		PushPayload pushList = buildAllByAliasList(platForm, aliasList, extras,
				badge, hasSound, content);
		try {
			PushResult result = jPushClient.sendPush(pushList);
			System.out.println(" JpushUtile   pushAllByAliasList  推送结果:"
					+ result);
		} catch (Exception e) {
			System.out.println("APIRequestException APIConnectionException");
			e.printStackTrace();
		}
	}

	public PushPayload buildAllByAliasList(String platForm,
			List<String> aliasList, Map<String, String> extras, int badge,
			boolean hasSound, String content) {
		if (platForm.equals("android")) {
			return PushPayload
					.newBuilder()
					.setPlatform(Platform.android())
					.setAudience(Audience.alias(aliasList))
					.setNotification(
							Notification.android(content, title, extras))
					.build();
		} else if (platForm.equals("ios")) {
			return PushPayload
					.newBuilder()
					.setPlatform(Platform.ios())
					.setAudience(Audience.alias(aliasList))
					.setNotification(
							Notification
									.newBuilder()
									.addPlatformNotification(
											IosNotification
													.newBuilder()
													.setAlert(content)
													.incrBadge(badge)
													.setSound(
															hasSound ? "happy.caf"
																	: null)
													.addExtras(extras).build())
									.build())
					.setOptions(
							Options.newBuilder()
									.setApnsProduction(APNS_PRODUCTION).build())
					.build();
		}
		return null;
	}

	/**
	 * -------------------------------------------- --------------------
	 * 以別名推送所有平台 --------------------------------------------
	 * --------------------------------------------
	 */

	public void pushAllByAliasList(String content, String platForm,
			List<String> aliasList, Map<String, String> extras, int badge,
			boolean hasSound) {

		PushPayload pushList = buildPushObject_aliasList_alertWithTitle(
				content, platForm, aliasList, extras, badge, hasSound);

		try {
			PushResult result = jPushClient.sendPush(pushList);
			System.out.println("推送结果:" + result);
		} catch (APIConnectionException e) {
			System.out.println("APIConnectionException ");
			e.printStackTrace();
		} catch (APIRequestException e) {
			System.out.println("APIRequestException ");
			e.printStackTrace();
		}
	}

	/**
	 * alias集合
	 * 
	 * @param platForm
	 * @param alias
	 * @param extras
	 * @param badge
	 * @return
	 */
	public PushPayload buildPushObject_aliasList_alertWithTitle(String content,
			String platForm, List<String> aliasList,
			Map<String, String> extras, int badge, boolean hasSound) {

		if (platForm.equals("android")) {
			return PushPayload
					.newBuilder()
					.setPlatform(Platform.android())
					.setAudience(Audience.alias(aliasList))
					.setNotification(
							Notification.android(content, title, extras))
					.build();
		} else if (platForm.equals("ios")) {
			return PushPayload
					.newBuilder()
					.setPlatform(Platform.ios())
					.setAudience(Audience.alias(aliasList))
					.setNotification(
							Notification
									.newBuilder()
									.addPlatformNotification(
											IosNotification
													.newBuilder()
													.setAlert(content)
													.incrBadge(badge)
													.setSound(
															hasSound ? "happy.caf"
																	: null)
													.addExtras(extras).build())
									.build())
					.setOptions(
							Options.newBuilder().setApnsProduction(true)
									.build()).build();
			// True 表示推送生产环境，False 表示要推送开发环境
		}
		return null;
	}

	public long sendPushAlias(String content, String platForm, String alias,
			Map<String, String> extras, int badge) {
		PushPayload payloadAlias = buildPushObject_alias_alertWithTitle(
				content, platForm, alias, extras, badge);
		long msgId = 0;
		try {
			PushResult result = jPushClient.sendPush(payloadAlias);

			System.out.println("推送结果:" + result);
			msgId = result.msg_id;

		} catch (Exception e) {
		}
		return msgId;
	}
	
	/**
	 * 推送 "异常上报"（巡更）
	 * @param instance
	 * @param badge
	 * @param alias
	 */
	public void pushErrorSubmit(Integer nwWorkId,String content,int badge,String alias){
		//每个用户推两个平台//"【"+flowName+"】"+
		JPushUtils pushUtils = JPushUtils.getInstance();
//		JPushUtils androidPushUtil = new JPushUtils(content,"【巡更异常上报】");
//		PushUtil iosPushUtil = new PushUtil("【巡更异常上报】" + content,"");
		
		Map<String, String> extras = new HashMap<String, String>();
		extras.put("badge", String.valueOf(badge));//ios用的角标
		extras.put("nwWorkId", String.valueOf(nwWorkId));//instanceId
		
		
		List<String> aliasList = new ArrayList<String>();
		aliasList.add(alias);
		pushUtils.pushAllByAliasList("android", aliasList, extras, badge, true, content);
		pushUtils.pushAllByAliasList("ios", aliasList, extras, badge, true, content);
//		long msgId_ios = iosPushUtil.sendPushAlias("ios", aliasList, extras, badge);
	}
	
	/**
	 * 推送删除（巡更）
	 * @param instance
	 * @param badge
	 * @param alias
	 */
	public void pushDelNightWatch(Integer nwWorkId,String content,int badge,String alias){
		//每个用户推两个平台//"【"+flowName+"】"+
		JPushUtils pushUtils = JPushUtils.getInstance();
//		JPushUtils androidPushUtil = new JPushUtils(content,"【巡更异常上报】");
//		PushUtil iosPushUtil = new PushUtil("【巡更异常上报】" + content,"");
		
		Map<String, String> extras = new HashMap<String, String>();
		extras.put("badge", String.valueOf(badge));//ios用的角标
		extras.put("action", "delete");
		extras.put("nwWorkId", String.valueOf(nwWorkId));//instanceId
		
		
		List<String> aliasList = new ArrayList<String>();
		aliasList.add(alias);
		pushUtils.pushAllByAliasList("android", aliasList, extras, badge, true, content);
		pushUtils.pushAllByAliasList("ios", aliasList, extras, badge, true, content);
//		long msgId_ios = iosPushUtil.sendPushAlias("ios", aliasList, extras, badge);
	}
	
	/**
	 * 推送删除（巡检）
	 * @param instance
	 * @param badge
	 * @param alias
	 */
	public void pushDelPatrol(Integer itemId,String content,int badge,String alias){
		//每个用户推两个平台//"【"+flowName+"】"+
		JPushUtils pushUtils = JPushUtils.getInstance();
//		JPushUtils androidPushUtil = new JPushUtils(content,"【巡更异常上报】");
//		PushUtil iosPushUtil = new PushUtil("【巡更异常上报】" + content,"");
		
		Map<String, String> extras = new HashMap<String, String>();
		extras.put("badge", String.valueOf(badge));//ios用的角标
		extras.put("itemId", String.valueOf(itemId));//instanceId
		extras.put("action", "delete");
		
		
		List<String> aliasList = new ArrayList<String>();
		aliasList.add(alias);
		pushUtils.pushAllByAliasList("android", aliasList, extras, badge, true, content);
		pushUtils.pushAllByAliasList("ios", aliasList, extras, badge, true, content);
//		long msgId_ios = iosPushUtil.sendPushAlias("ios", aliasList, extras, badge);
	}
	
	/**
	 * 推送 "普通任务"
	 * @param instance
	 * @param badge
	 * @param alias
	 */
	public void pushCommonWork(Integer commonWorkId,String content,int badge,String alias){
		//每个用户推两个平台//"【"+flowName+"】"+
		JPushUtils pushUtils = JPushUtils.getInstance();
		
		Map<String, String> extras = new HashMap<String, String>();
		extras.put("badge", String.valueOf(badge));//ios用的角标
		extras.put("commonWorkId", String.valueOf(commonWorkId));//instanceId
		
		
		List<String> aliasList = new ArrayList<String>();
		aliasList.add(alias);
		pushUtils.pushAllByAliasList("android", aliasList, extras, badge, true, content);
		pushUtils.pushAllByAliasList("ios", aliasList, extras, badge, true, content);
//		long msgId_ios = iosPushUtil.sendPushAlias("ios", aliasList, extras, badge);
	}
	
	/**
	 * 新巡更或巡检任务推送
	 * @param instance
	 * @param badge
	 * @param alias
	 */
	public void pushNewWork(String content,int badge,String alias){
		//每个用户推两个平台//"【"+flowName+"】"+
		JPushUtils pushUtils = JPushUtils.getInstance();
//		JPushUtils androidPushUtil = new JPushUtils(content,"【巡更异常上报】");
//		PushUtil iosPushUtil = new PushUtil("【巡更异常上报】" + content,"");
		
		Map<String, String> extras = new HashMap<String, String>();
		extras.put("badge", String.valueOf(badge));//ios用的角标
		extras.put("action", "newWork");
		
		
		List<String> aliasList = new ArrayList<String>();
		aliasList.add(alias);
		pushUtils.pushAllByAliasList("android", aliasList, extras, badge, true, content);
		pushUtils.pushAllByAliasList("ios", aliasList, extras, badge, true, content);
//		long msgId_ios = iosPushUtil.sendPushAlias("ios", aliasList, extras, badge);
	}
	
	public static void main(String[] args) {

		JPushUtils jPushUtils = JPushUtils.getInstance();
//		jPushUtils.sendPushAlias("异常567", "android", "test", null, 1);
		jPushUtils.pushErrorSubmit(2, "呵呵哈哈", 6, "test");

	}

}
