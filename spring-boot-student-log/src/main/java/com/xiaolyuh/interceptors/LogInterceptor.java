package com.xiaolyuh.interceptors;

import com.xiaolyuh.constants.MdcConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

/**
 * 日志拦截器组件，在输出日志中加上sessionId
 *
 * @author yuhao.wang3
 */
public class LogInterceptor extends HandlerInterceptorAdapter {
    Logger logger = LoggerFactory.getLogger(LogInterceptor.class);

    @Override
    public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
            throws Exception {

        try {
            // 删除SessionId
            MDC.remove(MdcConstant.SESSION_KEY);
            MDC.remove(MdcConstant.REQUEST_KEY);
        } catch (Exception e) {
            logger.warn(e.getMessage(), e);
        }
    }

    @Override
    public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1,
                           Object arg2, ModelAndView arg3) throws Exception {
    }

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {

        try {
            // 设置SessionId
            String requestId = UUID.randomUUID().toString().replace("-", "");
            MDC.put(MdcConstant.SESSION_KEY, request.getSession().getId());
            MDC.put(MdcConstant.REQUEST_KEY, requestId);
            return true;
        } catch (Exception e) {
            logger.warn(e.getMessage(), e);
            return false;
        }

    }
}