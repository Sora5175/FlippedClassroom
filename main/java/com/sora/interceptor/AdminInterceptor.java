package com.sora.interceptor;

import com.auth0.jwt.interfaces.Claim;
import com.sora.dao.IUserDao;
import com.sora.domain.User;
import com.sora.service.IUserService;
import com.sora.utils.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
 * @date 2020/4/16 21:25
 */
public class AdminInterceptor implements HandlerInterceptor {
    @Autowired
    private IUserService userService;

    public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
            throws Exception {
        // TODO Auto-generated method stub

    }

    public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
            throws Exception {
        // TODO Auto-generated method stub

    }

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object arg2) throws Exception {
        long id = (long)request.getAttribute("id");
        User user = userService.findInfoById(id);
        if(user.getRole()!=1){
            return false;
        }
        return true;
    }
}
