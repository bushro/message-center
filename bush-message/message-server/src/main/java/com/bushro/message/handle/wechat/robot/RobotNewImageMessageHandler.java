package com.bushro.message.handle.wechat.robot;

import cn.hutool.core.bean.BeanUtil;
import com.bushro.message.dto.wechat.robot.ArticleDTO;
import com.bushro.message.dto.wechat.robot.NewsMessageDTO;
import com.bushro.message.enums.MessageTypeEnum;
import com.bushro.message.handle.wechat.AbstractWechatRobotHandler;
import com.bushro.message.service.IMessageConfigService;
import com.bushro.message.service.IMessageRequestDetailService;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.cp.api.WxCpGroupRobotService;
import me.chanjar.weixin.cp.bean.article.NewArticle;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 企业微信-群机器人-图文消息
 *
 * @author luo.qiang
 * @date 2022/10/28
 */
@Slf4j
@Component
public class RobotNewImageMessageHandler extends AbstractWechatRobotHandler<NewsMessageDTO> implements Runnable {

    private NewsMessageDTO param;

    @Resource
    private IMessageConfigService messageConfigService;

    @Resource
    private IMessageRequestDetailService messageRequestDetailService;

    @Override
    public void setBaseMessage(Object object) {
        this.param = (NewsMessageDTO) object;
    }

    @Override
    public Runnable getRunnable() {
        return this;
    }

    @Override
    public MessageTypeEnum messageType() {
        this.messageTypeEnum = MessageTypeEnum.WECHAT_WORK_ROBOT_NEWS;
        return MessageTypeEnum.WECHAT_WORK_ROBOT_NEWS;
    }

    @Override
    public void run() {
        handle(messageConfigService, messageRequestDetailService, param);
    }

    @Override
    public void sendMessage() throws WxErrorException {
        WxCpGroupRobotService robotService = this.getService().getGroupRobotService();
        List<ArticleDTO> articles = param.getArticles();
        List<NewArticle> newArticles = new ArrayList<>();
        for (ArticleDTO article : articles) {
            NewArticle newArticle = BeanUtil.toBean(article, NewArticle.class);
            newArticles.add(newArticle);
        }
        robotService.sendNews(newArticles);
    }
}
