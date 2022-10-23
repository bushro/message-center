package com.bushro.message.utils;

import cn.hutool.cache.CacheUtil;
import cn.hutool.cache.impl.TimedCache;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiGettokenRequest;
import com.dingtalk.api.request.OapiMessageCorpconversationAsyncsendV2Request;
import com.dingtalk.api.response.OapiGettokenResponse;
import com.dingtalk.api.response.OapiMessageCorpconversationAsyncsendV2Response;
import com.taobao.api.ApiException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 钉钉token获取工具类
 */

@Slf4j
public class AccessTokenUtils {

    public static TimedCache<String, String> accessTokenTimedCache;

    private static TimedCache<String, String> getAccessTokenTimedCache(String appKey, String appSecret) throws ApiException {
        if (accessTokenTimedCache == null || StringUtils.isEmpty(accessTokenTimedCache.get("accessToken"))) {
            synchronized (AccessTokenUtils.class) {
                if (accessTokenTimedCache == null || StringUtils.isEmpty(accessTokenTimedCache.get("accessToken"))) {
                    DefaultDingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/gettoken");
                    OapiGettokenRequest request = new OapiGettokenRequest();
                    request.setAppkey(appKey);
                    request.setAppsecret(appSecret);
                    request.setHttpMethod("GET");
                    OapiGettokenResponse response;
                    response = client.execute(request);
                    log.info("获取到的钉钉token信息：{}", response.getBody());
                    accessTokenTimedCache = CacheUtil.newTimedCache((response.getExpiresIn() - 60) * 1000);
                    accessTokenTimedCache.put("accessToken", response.getAccessToken());
                }
            }
        }
        return accessTokenTimedCache;
    }

    public static String getAccessToken(String appKey, String appSecret) throws ApiException {
        return getAccessTokenTimedCache(appKey, appSecret).get("accessToken");
    }

    public static void main(String[] args) throws ApiException {
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/topapi/message/corpconversation/asyncsend_v2");
        OapiMessageCorpconversationAsyncsendV2Request request = new OapiMessageCorpconversationAsyncsendV2Request();
        request.setAgentId(1328108759L);
        request.setUseridList("manager1760");
        request.setToAllUser(false);

        OapiMessageCorpconversationAsyncsendV2Request.Msg msg = new OapiMessageCorpconversationAsyncsendV2Request.Msg();
        OapiMessageCorpconversationAsyncsendV2Request.OA oa = new OapiMessageCorpconversationAsyncsendV2Request.OA();


        OapiMessageCorpconversationAsyncsendV2Request.Head head = new OapiMessageCorpconversationAsyncsendV2Request.Head();
        head.setBgcolor("FFFF6A00");
        head.setText("测试");
        OapiMessageCorpconversationAsyncsendV2Request.Body body = new OapiMessageCorpconversationAsyncsendV2Request.Body();
        List<OapiMessageCorpconversationAsyncsendV2Request.Form> list = new ArrayList<>();
        OapiMessageCorpconversationAsyncsendV2Request.Form form1 = new OapiMessageCorpconversationAsyncsendV2Request.Form();
        form1.setKey("姓名:");
        form1.setValue("小钉");
        OapiMessageCorpconversationAsyncsendV2Request.Form form2 = new OapiMessageCorpconversationAsyncsendV2Request.Form();
        form2.setKey("年龄:");
        form2.setValue("21");
        OapiMessageCorpconversationAsyncsendV2Request.Form form3 = new OapiMessageCorpconversationAsyncsendV2Request.Form();
        form3.setKey("身高:");
        form3.setValue("1.8米");
        OapiMessageCorpconversationAsyncsendV2Request.Form form4 = new OapiMessageCorpconversationAsyncsendV2Request.Form();
        form4.setKey("体重:");
        form4.setValue("130斤");
        OapiMessageCorpconversationAsyncsendV2Request.Form form5 = new OapiMessageCorpconversationAsyncsendV2Request.Form();
        form5.setKey("学历:");
        form5.setValue("本科");
        OapiMessageCorpconversationAsyncsendV2Request.Form form6 = new OapiMessageCorpconversationAsyncsendV2Request.Form();
        form6.setKey("爱好:");
        form6.setValue("打球、听音乐");

        OapiMessageCorpconversationAsyncsendV2Request.Rich rich = new OapiMessageCorpconversationAsyncsendV2Request.Rich();
        rich.setNum("10.6");
        rich.setUnit("元");

        list.add(form1);
        list.add(form2);
        list.add(form3);
        list.add(form4);
        list.add(form5);
        list.add(form6);



        body.setForm(list);
        body.setRich(rich);
        body.setTitle("正文标题5");
        body.setImage("@lADOADmaWMzazQKA");
        body.setFileCount("3");
        body.setAuthor("小钉");


        OapiMessageCorpconversationAsyncsendV2Request.StatusBar statusBar = new OapiMessageCorpconversationAsyncsendV2Request.StatusBar();
        statusBar.setStatusBg("0xFFFF6A00");
        statusBar.setStatusValue("进行中");

        oa.setMessageUrl("https://dingtalk.com");
        oa.setHead(head);
        oa.setBody(body);
        oa.setStatusBar(statusBar);
        msg.setOa(oa);
        msg.setMsgtype("oa");
        msg.setOa(oa);
        request.setMsg(msg);
        OapiMessageCorpconversationAsyncsendV2Response rsp = client.execute(request, getAccessToken("xxx","xxx"));
        System.out.println(rsp.getBody());


//        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/topapi/message/corpconversation/asyncsend_v2");
//        OapiMessageCorpconversationAsyncsendV2Request request = new OapiMessageCorpconversationAsyncsendV2Request();
//        request.setAgentId(1328108759L);
//        request.setUseridList("manager1760");
//        request.setToAllUser(false);
//
//        OapiMessageCorpconversationAsyncsendV2Request.Msg msg = new OapiMessageCorpconversationAsyncsendV2Request.Msg();
//        msg.setMsgtype("text");
//        msg.setText(new OapiMessageCorpconversationAsyncsendV2Request.Text());
//        msg.getText().setContent("test123");
//        request.setMsg(msg);
//
//        OapiMessageCorpconversationAsyncsendV2Response rsp = client.execute(request, getAccessToken("dingrvhrnylxomi8wmsm","LQokzCwnCQjeocWiIX0CMh3ynY4VgsVxsj2WVh8mOd27yVdDmjs9Km3QlLHjpq1H"));

//        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/topapi/workrecord/add");
//        OapiWorkrecordAddRequest req = new OapiWorkrecordAddRequest();
//        req.setUserid("manager1760");
//        req.setCreateTime(1599580799000L);
//        req.setTitle("学习任务");
//        req.setUrl("https://oa.dingtalk.com");
//        req.setPcUrl("https://oa.dingtalk.com");
//        List<OapiWorkrecordAddRequest.FormItemVo> list2 = new ArrayList<OapiWorkrecordAddRequest.FormItemVo>();
//        OapiWorkrecordAddRequest.FormItemVo obj3 = new OapiWorkrecordAddRequest.FormItemVo();
//        list2.add(obj3);
//        obj3.setTitle("新人学习2");
//        obj3.setContent("产品学习");
//        req.setFormItemList(list2);
//        req.setOriginatorUserId("manager1760");
//        req.setSourceName("学习");
//        req.setPcOpenType(2L);
//        req.setBizId("1112");
//        OapiWorkrecordAddResponse rsp = client.execute(req, getAccessToken("dingrvhrnylxomi8wmsm","LQokzCwnCQjeocWiIX0CMh3ynY4VgsVxsj2WVh8mOd27yVdDmjs9Km3QlLHjpq1H"));
//        System.out.println(rsp.getBody());

//        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/topapi/message/corpconversation/asyncsend_v2");
//        OapiMessageCorpconversationAsyncsendV2Request request = new OapiMessageCorpconversationAsyncsendV2Request();
//        request.setAgentId(1328108759L);
//        request.setUseridList("manager1760");
//        request.setToAllUser(false);
//
//        OapiMessageCorpconversationAsyncsendV2Request.Msg msg = new OapiMessageCorpconversationAsyncsendV2Request.Msg();
//        msg.setMsgtype("text");
//        msg.setText(new OapiMessageCorpconversationAsyncsendV2Request.Text());
//        msg.getText().setContent("test123");
//        request.setMsg(msg);
//
//        msg.setMsgtype("image");
//        msg.setImage(new OapiMessageCorpconversationAsyncsendV2Request.Image());
//        msg.getImage().setMediaId("@lADOdvRYes0CbM0CbA");
//        request.setMsg(msg);
//
//        msg.setMsgtype("file");
//        msg.setFile(new OapiMessageCorpconversationAsyncsendV2Request.File());
//        msg.getFile().setMediaId("@lADOdvRYes0CbM0CbA");
//        request.setMsg(msg);

//        msg.setMsgtype("link");
//        msg.setLink(new OapiMessageCorpconversationAsyncsendV2Request.Link());
//        msg.getLink().setTitle("test");
//        msg.getLink().setText("test");
//        msg.getLink().setMessageUrl("test");
//        msg.getLink().setPicUrl("test");
//        request.setMsg(msg);
//
//        msg.setMsgtype("markdown");
//        msg.setMarkdown(new OapiMessageCorpconversationAsyncsendV2Request.Markdown());
//        msg.getMarkdown().setText("##### text");
//        msg.getMarkdown().setTitle("### Title");
//        request.setMsg(msg);
//
//        msg.setOa(new OapiMessageCorpconversationAsyncsendV2Request.OA());
//        msg.getOa().setHead(new OapiMessageCorpconversationAsyncsendV2Request.Head());
//        msg.getOa().getHead().setText("head");
//        msg.getOa().setBody(new OapiMessageCorpconversationAsyncsendV2Request.Body());
//        msg.getOa().getBody().setContent("xxx");
//        msg.setMsgtype("oa");
//        request.setMsg(msg);
//
//        msg.setActionCard(new OapiMessageCorpconversationAsyncsendV2Request.ActionCard());
//        msg.getActionCard().setTitle("xxx123411111");
//        msg.getActionCard().setMarkdown("### 测试123111");
//        msg.getActionCard().setSingleTitle("测试测试");
//        msg.getActionCard().setSingleUrl("https://www.dingtalk.com");
//        msg.setMsgtype("action_card");
//        request.setMsg(msg);
//        OapiMessageCorpconversationAsyncsendV2Response execute = client.execute(request, getAccessToken("dingrvhrnylxomi8wmsm", "LQokzCwnCQjeocWiIX0CMh3ynY4VgsVxsj2WVh8mOd27yVdDmjs9Km3QlLHjpq1H"));
//
//        System.out.println(execute.getBody());
    }

}
