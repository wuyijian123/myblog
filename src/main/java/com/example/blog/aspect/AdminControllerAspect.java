package com.example.blog.aspect;

import com.example.blog.constant.Messages;
import com.example.blog.entity.User;
import com.example.blog.util.AspectUtils;
import com.example.blog.util.AuthUtils;
import com.example.blog.web.model.ApiResult;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * @author lzzy on 2022/11/17.
 * Description:
 */
////配置该类为组件类，Spring Boot在组件扫描时会扫描所有组件类
@Component
//标识该类为切面
@Aspect
public class AdminControllerAspect {

    @Pointcut("execution(* com.example.blog.web.admin.*.*(..))")
    private void adminAspect(){}


    @Around("adminAspect()")
    public ApiResult aroundAdminController(ProceedingJoinPoint point) throws Throwable {

        String token = AspectUtils.getToken(point);

        if (AuthUtils.illegalUser(token, new int[]{User.TYPE_ADMIN})){
//

            return new ApiResult(false, Messages.NO_PERMISSION.toString(), null);
        }

        return (ApiResult) point.proceed();
    }
}
