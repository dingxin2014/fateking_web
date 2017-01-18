package com.fateking.interceptor;

import com.fateking.base.Constants;
import com.fateking.base.Global;
import com.fateking.model.User;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Created by dingxin on 16/12/12.
 */
@Component
public class LoginInterceptor extends HandlerInterceptorAdapter implements Global {

    private static final Log logger = LogFactory.getLog(LoginInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        User user = getUser(request);
        if(user == null){
            //TODO
            response.sendRedirect("/login");
            return false;
        }
        setCurrentUser(user);

        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        remove();
        super.afterCompletion(request, response, handler, ex);
    }

    private User getUser(HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(Constants.SESSION_USER);
        return user;
    }
}
