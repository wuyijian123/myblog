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
public class CategoryControllerAspect {

    @Pointcut("execution(* com.example.blog.web.category.CategoryApiController.addCategory(..)) ||"
            + "execution(* com.example.blog.web.category.CategoryApiController.updateCategory(..)) ||" +
            "execution(* com.example.blog.web.category.CategoryApiController.delete(..)) ")
    private void categoryAspect() {

    }
    @Around("categoryAspect()")
    public ApiResult aroundCategoryMethod(ProceedingJoinPoint point) throws  Throwable{

        String token = AspectUtils.getToken(point);
        if (AuthUtils.illegalUser(token, new int[]{User.TYPE_ADMIN})){
            return new ApiResult(false, Messages.NO_PERMISSION.toString(), null);
        }

        return (ApiResult) point.proceed();
    }


}
