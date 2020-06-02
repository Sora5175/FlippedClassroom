package com.sora.websocket;

import com.alibaba.fastjson.JSONObject;
import com.sora.service.IUserService;
import com.sora.vo.WebLoginVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Sora
 * @version 1.0
 * @date 2020/4/15 15:38
 */
public class WebDeleteCourseHandler extends TextWebSocketHandler {
    @Autowired
    private IUserService userService;

    private static Map<String, WebSocketSession> ipAndSession = new ConcurrentHashMap<>();

    // 建立连接时候触发
    @Override
    public void afterConnectionEstablished(WebSocketSession session)  {
        String ip = (String) session.getAttributes().get("ip");
        if(ip!=null){
            ipAndSession.put(ip,session);
        }
    }


    // 关闭连接时候触发
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        String ip = (String) session.getAttributes().get("ip");
        if(ip!=null){
            ipAndSession.remove(ip);
        }
    }

    // 处理消息
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        WebLoginVO webLoginVO = JSONObject.parseObject(payload,WebLoginVO.class);
        String ip = webLoginVO.getIpAddress();
        String token = webLoginVO.getToken();
        TextMessage textMessage = new TextMessage(token);
        ipAndSession.get(ip).sendMessage(textMessage);
        session.close();
    }
}