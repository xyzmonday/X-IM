package com.yff.xim.module.user.controller;

import com.yff.xim.model.*;
import com.yff.xim.model.proto.MessageBodyProto;
import com.yff.xim.model.proto.MessageProto;
import com.yff.xim.module.user.service.IUserAccountService;
import com.yff.xim.module.user.service.IUserMessageService;
import com.yff.xim.server.connector.IConnectorService;
import com.yff.xim.server.sesssion.ISessionService;
import com.yff.xim.util.GlobalConstant;
import com.yff.xim.util.ImUtils;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * 单聊
 */
@Controller
@Log4j2
public class ChatController {

    @Autowired
    ISessionService sessionService;

    @Autowired
    IUserMessageService userMessageService;

    @Autowired
    IConnectorService connectorService;


    /**
     * 显示chat.html页面
     *
     * @return
     */
    @GetMapping("/chat")
    public String chat(Model model, HttpServletRequest req) {
        String id = req.getSession().getId();
        model.addAttribute("sessionId", id);
        //当前用户看到其他用户上线的情况
        List<Session> sessions = sessionService.selectAllSession();
        log.info("当前用户数:{}", sessions.size());
        model.addAttribute("sessions", sessions);
        return "chat";
    }

    /**
     * 群聊
     */
    @RequestMapping("/groupChat")
    public String group(Model model, HttpServletRequest req) {
        String id = req.getSession().getId();
        model.addAttribute("sessionId", UUID.randomUUID().toString().replace("-", ""));
        //当前用户看到其他用户上线的情况
        List<Session> sessions = sessionService.selectAllSession();
        log.info("当前用户数:{}", sessions.size());
        model.addAttribute("sessions", sessions);
        return "groupChat";
    }

    @GetMapping("/hello")
    public String hello(Model model) {
        List<User> users = new ArrayList<>();
        User user = new User();
        user.setId(1);
        user.setName("yuanfengfan");
        users.add(user);
        user = new User();
        user.setId(3);
        user.setName("yuansanyi");
        users.add(user);
        model.addAttribute("users", users);
        return "hello";
    }

    private static class User {
        private int id;
        private String name;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    /**
     * 模拟最新系统消息
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/message", method = RequestMethod.GET)
    public String userMessage(Model model) throws Exception {

        List<UserMessageEntity> list = new ArrayList<UserMessageEntity>();
        UserMessageEntity msg = new UserMessageEntity();
        msg.setContent("模拟系统消息");
        msg.setCreatedate(DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
        list.add(msg);
        UserMessageEntity msgTwo = new UserMessageEntity();
        msgTwo.setContent("模拟系统消息1");
        msgTwo.setCreatedate(DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
        list.add(msgTwo);
        model.addAttribute("msgList", list);
        return "message";
    }


    /**
     * 取得离线消息
     *
     * @param response
     * @param request
     * @return
     * @throws Exception
     */
    @PostMapping("/getofflinemsg")
    @ResponseBody
    public List<UserMessageEntity> userMessageCount(HttpServletResponse response, HttpServletRequest request) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        UserAccountEntity loginUser = ImUtils.getLoginUser(request);
        if (loginUser != null) {
            map.put("receiveuser", loginUser.getId().toString());
        } else {
            map.put("receiveuser", request.getSession().getId());
        }
        List<UserMessageEntity> list = userMessageService.getOfflineMessageList(map);
        return list;
    }

    /**
     * 聊天记录
     *
     * @param response
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/historymessageajax", produces = "text/html;charset=UTF-8", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, List<UserMessageEntity>> userHistoryMessages(HttpServletResponse response, HttpServletRequest request) throws Exception {
        Map<String, Object> map = new HashMap<>();
        map.put("page", ImUtils.getSkipToPage(request));
        map.put("limit", 10);
        map.put("senduser", ImUtils.getLoginUser(request).getId());
        map.put("receiveuser", Long.parseLong(request.getParameter("id")));
        List<UserMessageEntity> list = userMessageService.getHistoryMessageList(new Query(map));
        Map<String, List<UserMessageEntity>> resultMap = new HashMap();
        resultMap.put("data", list);
        return resultMap;
    }

    /**
     * 聊天记录页面
     *
     * @param response
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/historymessage", method = RequestMethod.GET)
    public String userHistoryMessagesPage(HttpServletResponse response, HttpServletRequest request) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("senduser", ImUtils.getLoginUser(request).getId());
        map.put("receiveuser", Long.parseLong(request.getParameter("id")));
        int totalsize = userMessageService.getHistoryMessageCount(map);
        Pager pager = new Pager(ImUtils.getSkipToPage(request), 10, totalsize);
        request.setAttribute("pager", pager);
        return "historymessage";
    }


    @GetMapping("/sendmsg")
    @ResponseBody
    public String sendMsg(HttpServletResponse response, HttpServletRequest request) throws Exception {
        String sessionId = request.getSession().getId();
        UserAccountEntity loginUser = ImUtils.getLoginUser(request);
        if (loginUser != null) {
            sessionId = loginUser.getId().toString();
        }
        MessageProto.Model.Builder builder = MessageProto.Model.newBuilder();
        builder.setCmd(GlobalConstant.CmdType.MESSAGE);
        builder.setSender(sessionId);
        builder.setReceiver((String) request.getParameter("receiver"));
        builder.setMsgtype(GlobalConstant.MsgType.REPLY);
        //消息体
        MessageBodyProto.MessageBody.Builder body = MessageBodyProto.MessageBody.newBuilder();
        body.setContent((String) request.getParameter("content"));
        builder.setContent(body.build().toByteString());
        connectorService.sendMessage(sessionId, builder.build());
        return "success";
    }
}
