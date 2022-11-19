## message-push

#### 项目文档

文档地址： https://message-push.pages.dev/

#### 项目介绍

消息推送平台、消息中心。实现对企业微信、钉钉、邮箱等发送消息，一个接口触及多平台。

核心依赖

| 依赖名称               | 版本   |
| ---------------------- | ------ |
| Spring Cloud Alibaba   | 2021.1 |
| Spring Boot            | 2.7.4  |
| Spring-security-oauth2 | 2.3.6  |
| swagger2               | 3.0.0  |
| mybatis-plus           | 3.5.1  |



#### 系统架构

项目的核心流程：所有的消息都由统一的消息接入层处理，封装成一致的数据结构然后放到Mq中。消费者根据不同的消息类型找到对应的消息处理器然后放到线程池中去执行（一种消息处理器就是一个线程）。

![在这里插入图片描述](https://img-blog.csdnimg.cn/e5e27065bff14778bb30a7ee376b0edc.png)

#### 前端界面展示

平台配置页面
![在这里插入图片描述](https://img-blog.csdnimg.cn/8d9f854eb37c486cbb48484aacba27ff.png)


消息发送页面
![在这里插入图片描述](https://img-blog.csdnimg.cn/a6d1a9c578514c24a4406be2eb125e23.png)

