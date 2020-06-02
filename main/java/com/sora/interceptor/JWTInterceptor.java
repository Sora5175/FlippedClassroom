package com.sora.interceptor;

import com.auth0.jwt.interfaces.Claim;
import com.sora.utils.JWTUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Sora
 * @version 1.0
 * @date 2020/3/30 21:27
 */
@Component
public class JWTInterceptor implements HandlerInterceptor {

    public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
            throws Exception {
        // TODO Auto-generated method stub

    }

    public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
            throws Exception {
        // TODO Auto-generated method stub

    }

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object arg2) throws IOException {
        String token = request.getHeader("token");
        if(token==null||token==""){
            token = request.getHeader("Sec-WebSocket-Protocol");
            if(token==null||token==""){
                return false;
            }else{
                response.setHeader("Sec-WebSocket-Protocol",token);
            }
        }
        Map<String, Claim> verifiedToken = new HashMap<String, Claim>();
        try {
            verifiedToken = JWTUtils.verifyToken(token);
            if(verifiedToken.get("id")== null){
                return false;
            }else{
                long id = verifiedToken.get("id").asLong();
                request.setAttribute("id",id);
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }

}
