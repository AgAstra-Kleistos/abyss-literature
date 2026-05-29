package com.abyss.interceptor;

import com.abyss.constant.JwtClaimsConstant;
import com.abyss.context.BaseContext;
import com.abyss.properties.JwtProperties;
import com.abyss.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * jwt令牌校验的拦截器
 */
@Slf4j
@Component
public class JwtTokenAdminInterceptor implements HandlerInterceptor {

    @Autowired
    private JwtProperties jwtProperties;

    /**
     * 在Controller方法执行之前 进行拦截处理
     * @param request  请求对象（可以获取请求头、参数等）
     * @param response 响应对象
     * @param handler  即将要执行的目标方法（Controller里的接口）
     * @return true=放行；false=拦截
     * @throws Exception 抛出异常统一在catch里处理
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception{
        //判断当前拦截到的是Controller的方法还是其他资源
        if (!(handler instanceof HandlerMethod)) {
            //当前拦截到的不是动态方法，直接放行
            return true;
        }

        //从请求头中获取令牌
        String token = request.getHeader(jwtProperties.getAdminTokenName());

        //校验令牌
        try{
            log.info("jwt校验:{}", token);
            Claims claims = JwtUtils.parseJWT(jwtProperties.getAdminSecretKey(), token);
            Long empId = Long.valueOf(claims.get(JwtClaimsConstant.EMP_ID).toString());
            log.info("当前员工id: {}", empId);
            //将当前登录操作的Id放入线程空间threadLocal中
            BaseContext.setCurrentId(empId);
            //通过，放行
            return true;
        }catch(Exception ex){
            response.setStatus(401);
            return false;
        }
    }
}
