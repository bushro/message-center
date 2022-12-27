/*
 Navicat Premium Data Transfer

 Source Server         : mysql_127.0.0.1
 Source Server Type    : MySQL
 Source Server Version : 50717
 Source Host           : localhost:3306
 Source Schema         : message_core

 Target Server Type    : MySQL
 Target Server Version : 50717
 File Encoding         : 65001

 Date: 27/12/2022 12:55:11
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for message_config
-- ----------------------------
DROP TABLE IF EXISTS `message_config`;
CREATE TABLE `message_config`  (
  `CONFIG_ID` bigint(20) NOT NULL COMMENT '主键',
  `CONFIG_NAME` varchar(60) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '配置名称',
  `PLATFORM` varchar(60) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '平台类型',
  `TENANT_ID` bigint(20) NULL DEFAULT NULL COMMENT '租户号',
  `CREATE_TIME` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `UPDATE_TIME` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `USE_FLAG` int(11) NOT NULL DEFAULT 1 COMMENT '是否有效 1有效，0无效',
  PRIMARY KEY (`CONFIG_ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '消息配置' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for message_config_value
-- ----------------------------
DROP TABLE IF EXISTS `message_config_value`;
CREATE TABLE `message_config_value`  (
  `ID` bigint(20) NOT NULL COMMENT '主键',
  `KEY_NAME` varchar(60) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '属性',
  `VALUE` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '值',
  `CONFIG_ID` bigint(60) NOT NULL COMMENT '配置id',
  `TENANT_ID` bigint(20) NULL DEFAULT NULL COMMENT '租户号',
  `CREATE_TIME` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `UPDATE_TIME` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '消息配置值' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for message_request
-- ----------------------------
DROP TABLE IF EXISTS `message_request`;
CREATE TABLE `message_request`  (
  `ID` bigint(20) NOT NULL COMMENT '主键',
  `PARAM` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'json参数',
  `CREATE_TIME` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `UPDATE_TIME` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `REQUEST_NO` varchar(60) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '请求号，唯一',
  `PLATFORM` varchar(60) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '平台类型，如邮件，企业微信等',
  `MESSAGE_TYPE` varchar(60) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '消息类型，如企业微信文本、图片等',
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '消息发送请求' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for message_request_detail
-- ----------------------------
DROP TABLE IF EXISTS `message_request_detail`;
CREATE TABLE `message_request_detail`  (
  `ID` bigint(20) NOT NULL COMMENT '主键',
  `PLATFORM` varchar(60) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '平台类型，如邮件，企业微信等',
  `MESSAGE_TYPE` varchar(60) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '消息类型，如企业微信文本、图片等',
  `CREATE_TIME` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `UPDATE_TIME` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `RECEIVER_ID` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '接收人',
  `CONFIG_ID` bigint(20) NOT NULL COMMENT '配置id',
  `SEND_STATUS` int(11) NOT NULL DEFAULT 0 COMMENT '发送状态，0 未开始；1 发送成功；2 发送失败',
  `MSG_TEST` varchar(900) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '发送成功' COMMENT '消息发送反馈信息',
  `REQUEST_NO` varchar(60) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '所属请求号，对应表MESSAGE_REQUEST的字段request_no',
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '消息发送结果详细' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_file
-- ----------------------------
DROP TABLE IF EXISTS `sys_file`;
CREATE TABLE `sys_file`  (
  `id` bigint(20) NOT NULL COMMENT '编号',
  `file_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '文件名称',
  `bucket_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '存储库名称',
  `original` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '原文件',
  `type` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '文件类型',
  `file_size` bigint(20) NULL DEFAULT NULL COMMENT '文件大小',
  `create_by` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT ' ' COMMENT '创建人',
  `update_by` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT ' ' COMMENT '修改人',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '上传时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0',
  `tenant_id` bigint(20) NULL DEFAULT NULL COMMENT '所属租户',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '文件管理表' ROW_FORMAT = Dynamic;


-- ----------------------------
-- Records of sys_file
-- ----------------------------
INSERT INTO `sys_file` VALUES (1594710096099672066, 'b8ede2a571df45b59864852363bda3fe.png', 'bushro-oos', 'logo.png', 'png', 6247, 'admin', 'admin', '2022-11-21 23:11:30', NULL, '0', NULL);
INSERT INTO `sys_file` VALUES (1594722868787232770, 'd383cb0d1dab4356a832e68be6355911.png', 'bushro-oos', 'logo.png', 'png', 6247, 'admin', 'admin', '2022-11-22 00:02:15', NULL, '0', NULL);

-- ----------------------------
-- Table structure for sys_oauth_client
-- ----------------------------
DROP TABLE IF EXISTS `sys_oauth_client`;
CREATE TABLE `sys_oauth_client`  (
  `id` bigint(20) NOT NULL COMMENT 'ID',
  `client_id` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '客户端ID，唯一标识',
  `client_secret` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '客户端访问秘钥，BCryptPasswordEncoder加密算法加密',
  `resource_ids` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '可访问资源id(英文逗号分隔)',
  `scope` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '授权范围(英文逗号分隔)',
  `authorized_grant_types` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '授权类型(英文逗号分隔)',
  `web_server_redirect_uri` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '重定向uri',
  `authorities` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '@PreAuthorize(\"hasAuthority(\' admin \')\")可以在方法上标志 用户或者说client 需要说明样的权限\r\n\n\n指定客户端所拥有的Spring Security的权限值\r\n(英文逗号分隔)',
  `access_token_validity` int(11) NOT NULL COMMENT '令牌有效期(单位:秒)',
  `refresh_token_validity` int(11) NOT NULL COMMENT '刷新令牌有效期(单位:秒)',
  `additional_information` varchar(4096) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '预留字段,在Oauth的流程中没有实际的使用(JSON格式数据)',
  `autoapprove` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '设置用户是否自动Approval操作, 默认值为 \' false \'\r\n可选值包括 \' true \',\' false \', \' read \',\' write \'.\r\n该字段只适用于grant_type=\"authorization_code\"的情况,当用户登录成功后,若该值为\' true \'或支持的scope值,则会跳过用户Approve的页面, 直接授权',
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`client_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '系统管理-oauth2授权客户端' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_oauth_client
-- ----------------------------
INSERT INTO `sys_oauth_client` VALUES (1, 'app', '123456', '', 'all', 'password,refresh_token', '', NULL, 3600, 259200, NULL, 'true', '2022-04-02 09:31:10', '2022-06-16 12:25:03');
INSERT INTO `sys_oauth_client` VALUES (4, 'appId', '123456', '', 'all', 'password,refresh_token,captcha', '', NULL, 3600, 259200, NULL, NULL, '2022-04-02 09:31:10', '2022-06-16 12:39:37');
INSERT INTO `sys_oauth_client` VALUES (2, 'client', '123456', '', 'all', 'password,refresh_token', '', NULL, 3600, 259200, NULL, 'true', '2022-04-02 09:31:10', '2022-06-16 12:25:03');
INSERT INTO `sys_oauth_client` VALUES (3, 'web', '123456', '', 'all', 'password,refresh_token,captcha', '', NULL, 3600, 259200, NULL, NULL, '2022-04-02 09:31:10', '2022-06-16 12:39:37');

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
  `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `USERNAME` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户名称',
  `NICKNAME` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '昵称',
  `PHONE` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '手机',
  `EMAIL` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '邮箱',
  `PASSWORD` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '密码',
  `AVATAR_URL` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '头像',
  `ROLES` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '角色',
  `IS_VALID` tinyint(1) NULL DEFAULT 1 COMMENT '是否可用',
  `CREATE_DATE` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `UPDATE_DATE` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES (1, 'admin', 'admin', '13666666667', NULL, 'e10adc3949ba59abbe56e057f20f883e', '/test2', 'ROLE_USER', 1, '2022-10-12 17:47:12', '2022-10-12 17:47:12');

SET FOREIGN_KEY_CHECKS = 1;
