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

@Component
//标识该类为切面
@Aspect
public class CommentControllerAspect {
    @Pointcut("execution(* com.example.blog.web.comment.CommentApiController.adminDelete(..))")
    private  void  adminCommentAspect(){
    }
    @Around("adminCommentAspect()")
    public ApiResult aroundAdminController(ProceedingJoinPoint point) throws Throwable {
        //从连接点上获取token
        String token = AspectUtils.getToken(point);
        //利用tokend检验用户是否合法，我们这里是校验类型是否为admin
        if (AuthUtils.illegalUser(token, new int[]{User.TYPE_ADMIN})){
//            如果是非法用户，返回封装错误消息

            return new ApiResult(false, Messages.NO_PERMISSION.toString(), null);
        }
        //如果合法用户，把请求数据封装为ApiResult格式返回
        return (ApiResult) point.proceed();
    }
}
