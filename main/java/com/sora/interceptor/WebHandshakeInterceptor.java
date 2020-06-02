package com.sora.interceptor;

import com.auth0.jwt.interfaces.Claim;
import com.sora.utils.JWTUtils;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Sora
 * @version 1.0
 * @date 2020/4/14 10:35
 */
public class WebHandshakeInterceptor extends HttpSessionHandshakeInterceptor {

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
        System.out.println("拦截器启动了");
        String ip = ((ServletServerHttpRequest) request).getServletRequest().getParameter("ip");
        if(ip!=null){
            attributes.put("ip",ip);
        }
        return true;
    }
    @Override
    public void afterHandshake(ServerHttpRequest arg0, ServerHttpResponse arg1, WebSocketHandler arg2, Exception arg3) {
        // TODO Auto-generated method stub
        System.out.println("拦截器结束了");
    }
}