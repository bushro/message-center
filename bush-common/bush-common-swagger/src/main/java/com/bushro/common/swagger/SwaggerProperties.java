package com.bushro.common.swagger;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * SwaggerProperties
 *
 * @author bushro
 * @date: 2022-10-19 14:00
 */
@Data
@Component
@ConfigurationProperties("swagger")
public class SwaggerProperties {

	/**
	 * 是否开启swagger
	 */
	private Boolean enabled = true;

	/**
	 * swagger会解析的包路径
	 **/
	private String basePackage = "";

	/**
	 * 应用名称
	 */
	private String groupName;
	/**
	 * 路径包含
	 */
	private String pathIncludePattern;

	/**
	 * swagger会解析的url规则
	 **/
	private List<String> basePath = new ArrayList<>();

	/**
	 * 在basePath基础上需要排除的url规则
	 **/
	private List<String> excludePath = new ArrayList<>();
	/**
	 * 标题
	 */
	private String title;
	/**
	 * 描述
	 */
	private String description;
	/**
	 * 版本
	 */
	private String version;
	private String termsOfServiceUrl;
	private String license;
	private String licenseUrl;
	/**
	 * 联系人
	 */
	private Contact contact;

	@Data
	public static class Contact {
		private String name;
		private String url;
		private String email;
	}
}
