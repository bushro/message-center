package com.bushro.common.core.constant.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author bushro
 * @date 2018/8/15 社交登录类型
 */
@Getter
@AllArgsConstructor
public enum LoginTypeEnum {

	/**
	 * 账号密码登录
	 */
	PWD("PWD", "账号密码登录"),

	/**
	 * 验证码登录
	 */
	SMS("SMS", "验证码登录"),

	/**
	 * QQ登录
	 */
	QQ("QQ", "QQ登录"),

	/**
	 * 微信登录
	 */
	WECHAT("WX", "微信登录"),

	/**
	 * 微信小程序
	 */
	MINI_APP("MINI", "微信小程序"),

	/**
	 * 码云登录
	 */
	GITEE("GITEE", "码云登录"),

	/**
	 * 开源中国登录
	 */
	OSC("OSC", "开源中国登录"),

	/**
	 * CAS 登录
	 */
	CAS("CAS", "CAS 登录");

	/**
	 * 类型
	 */
	private String type;

	/**
	 * 描述
	 */
	private String description;

}
