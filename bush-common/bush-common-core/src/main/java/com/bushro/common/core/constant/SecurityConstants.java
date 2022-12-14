package com.bushro.common.core.constant;

/**
 * @author bushro
 * @date 2017-12-18
 */
public interface SecurityConstants {

	/**
	 * 启动时是否检查Inner注解安全性
	 */
	boolean INNER_CHECK = true;

	/**
	 * 刷新
	 */
	String REFRESH_TOKEN = "refresh_token";

	/**
	 * 验证码有效期
	 */
	int CODE_TIME = 60;

	/**
	 * 验证码长度
	 */
	String CODE_SIZE = "4";

	/**
	 * 角色前缀
	 */
	String ROLE = "ROLE_";

	/**
	 * 前缀
	 */
	String PIGX_PREFIX = "ssa_";

	/**
	 * token 相关前缀
	 */
	String TOKEN_PREFIX = "token:";

	/**
	 * oauth 相关前缀
	 */
	String OAUTH_PREFIX = "oauth:";

	/**
	 * 授权码模式code key 前缀
	 */
	String OAUTH_CODE_PREFIX = "oauth:code:";

	/**
	 * 项目的license
	 */
	String PIGX_LICENSE = "made by ssa";

	/**
	 * 内部
	 */
	String FROM_IN = "Y";

	/**
	 * 标志
	 */
	String FROM = "from";

	/**
	 * OAUTH URL
	 */
	String OAUTH_TOKEN_URL = "/oauth/token";

	/**
	 * 移动端授权
	 */
	String GRANT_MOBILE = "mobile";

	/**
	 * QQ获取token
	 */
	String QQ_AUTHORIZATION_CODE_URL = "https://graph.qq.com/oauth2.0/token?grant_type="
			+ "authorization_code&code=%S&client_id=%s&redirect_uri=" + "%s&client_secret=%s";

	/**
	 * 微信获取OPENID
	 */
	String WX_AUTHORIZATION_CODE_URL = "https://api.weixin.qq.com/sns/oauth2/access_token"
			+ "?appid=%s&secret=%s&code=%s&grant_type=authorization_code";

	/**
	 * 微信小程序OPENID
	 */
	String MINI_APP_AUTHORIZATION_CODE_URL = "https://api.weixin.qq.com/sns/jscode2session"
			+ "?appid=%s&secret=%s&js_code=%s&grant_type=authorization_code";

	/**
	 * 码云获取token
	 */
	String GITEE_AUTHORIZATION_CODE_URL = "https://gitee.com/oauth/token?grant_type="
			+ "authorization_code&code=%S&client_id=%s&redirect_uri=" + "%s&client_secret=%s";

	/**
	 * 开源中国获取token
	 */
	String OSC_AUTHORIZATION_CODE_URL = "https://www.oschina.net/action/openapi/token";

	/**
	 * QQ获取用户信息
	 */
	String QQ_USER_INFO_URL = "https://graph.qq.com/oauth2.0/me?access_token=%s";

	/**
	 * 码云获取用户信息
	 */
	String GITEE_USER_INFO_URL = "https://gitee.com/api/v5/user?access_token=%s";

	/**
	 * 开源中国用户信息
	 */
	String OSC_USER_INFO_URL = "https://www.oschina.net/action/openapi/user?access_token=%s&dataType=json";

	/**
	 * {bcrypt} 加密的特征码
	 */
	String BCRYPT = "{bcrypt}";

	/**
	 * sys_oauth_client_details 表的字段，不包括client_id、client_secret
	 */
	String CLIENT_FIELDS = "client_id, CONCAT('{noop}',client_secret) as client_secret, resource_ids, scope, "
			+ "authorized_grant_types, web_server_redirect_uri, authorities, access_token_validity, "
			+ "refresh_token_validity, additional_information, autoapprove";

	/**
	 * JdbcClientDetailsService 查询语句
	 */
	String BASE_FIND_STATEMENT = "select " + CLIENT_FIELDS + " from sys_oauth_client_details";

	/**
	 * 按条件client_id 查询
	 */
	String DEFAULT_SELECT_STATEMENT = BASE_FIND_STATEMENT + " where client_id = ? and del_flag = 0 and tenant_id = %s";

	/**
	 * 资源服务器默认bean名称
	 */
	String RESOURCE_SERVER_CONFIGURER = "resourceServerConfigurerAdapter";

	/**
	 * 客户端模式
	 */
	String CLIENT_CREDENTIALS = "client_credentials";

	/**
	 * 客户端编号
	 */
	String CLIENT_ID = "client_id";

	/**
	 * 客户端唯一令牌
	 */
	String CLIENT_RECREATE = "recreate_flag";

	/**
	 * 用户ID字段
	 */
	String DETAILS_USER_ID = "user_id";

	/**
	 * 用户名
	 */
	String DETAILS_USERNAME = "username";

	/**
	 * 姓名
	 */
	String NAME = "name";

	/**
	 * 邮箱
	 */
	String EMAIL = "email";

	/**
	 * 用户部门字段
	 */
	String DETAILS_DEPT_ID = "deptId";

	/**
	 * 租户ID 字段
	 */
	String DETAILS_TENANT_ID = "tenantId";

	/**
	 * 协议字段
	 */
	String DETAILS_LICENSE = "license";

	/**
	 * 激活字段 兼容外围系统接入
	 */
	String ACTIVE = "active";

	/**
	 * AES 加密
	 */
	String AES = "aes";


	/**
	 * 认证请求头key
	 */
	String AUTHORIZATION_KEY = "Authorization";

	/**
	 * Basic认证前缀
	 */
	String BASIC_PREFIX = "Basic ";

	/**
	 * JWT令牌前缀
	 */
	String JWT_PREFIX = "Bearer ";

	/**
	 * JWT存储权限前缀
	 */
	String AUTHORITY_PREFIX = "ROLE_";

	/**
	 * JWT存储权限属性
	 */
	String JWT_AUTHORITIES_KEY = "authorities";

	/**
	 * 客户端ID
	 */
	String CLIENT_ID_KEY = "client_id";

	/**
	 * 刷新token
	 */
	String REFRESH_TOKEN_KEY = "refresh_token";

	/**
	 * 认证身份标识
	 */
	String GRANT_TYPE = "authGrantType";

	/**
	 * 超级管理员角色标识
	 */
	String SUPER_ADMIN_ROLE_CODE = "super_admin";


	// ===============================================================================
	// ============================ ↓↓↓↓↓↓ redis缓存 ↓↓↓↓↓↓ ============================
	// ===============================================================================

	/**
	 * jwt自定义用户信息
	 */
	String JWT_CUSTOM_USER = "system:jwt_custom_user:";

	/**
	 * url权限关联角色
	 * [ {接口路径:[角色编码]},...]
	 */
	String URL_PERM_RE_ROLES = "system:perm_rule:url";

	/**
	 * 验证码
	 */
	String CAPTCHA_CODE = "auth:captcha:";
}
