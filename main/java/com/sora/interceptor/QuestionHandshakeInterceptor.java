package com.sora.interceptor;

import com.auth0.jwt.interfaces.Claim;
import com.sora.utils.JWTUtils;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Sora
 * @version 1.0
 * @date 2020/4/11 12:58
 */
public class QuestionHandshakeInterceptor extends HttpSessionHandshakeInterceptor {

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
        System.out.println("拦截器启动了");
        String token = request.getHeaders().getFirst("token");
        System.out.println("token="+token);
        if(token==null||token==""){
            return false;
        }
        Map<String, Claim> verifiedToken = new HashMap<String, Claim>();
        try {
            verifiedToken = JWTUtils.verifyToken(token);
            if(verifiedToken.get("id")== null){
                return false;
            }else{
                long id = verifiedToken.get("id").asLong();
                attributes.put("id",id);
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }
    @Override
    public void afterHandshake(ServerHttpRequest arg0, ServerHttpResponse arg1, WebSocketHandler arg2, Exception arg3) {
        // TODO Auto-generated method stub
        System.out.println("拦截器结束了");
    }
}
