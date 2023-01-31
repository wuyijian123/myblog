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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
//标识该类为切面
@Aspect
public class ArticleControllerAspect {

    @Pointcut("execution(* com.example.blog.web.article.ArticleApiController.addArticle(..)) ||"
            + "execution(* com.example.blog.web.article.ArticleApiController.updateArticle(..)) ||"
            +"execution(* com.example.blog.web.article.ArticleApiController.deleteArticle(..)) "
    )
    private void articleAspect() {
    }
    @Pointcut("execution(* com.example.blog.web.article.ArticleApiController.adminDeleteArticle(..))")
    private  void  adminArticleAspect(){
    }

    @Around("adminArticleAspect()")
    public ApiResult adminArticleMethod(ProceedingJoinPoint point) throws  Throwable{
        String token = AspectUtils.getToken(point);
        if (AuthUtils.illegalUser(token, new int[]{User.TYPE_ADMIN})) {
            return new ApiResult(false, Messages.NO_PERMISSION.toString(), null);
        }
        return (ApiResult) point.proceed();
    }

    @Around("articleAspect()")
    public ApiResult aroundArticleMethod(ProceedingJoinPoint point) throws Throwable {
        String token = AspectUtils.getToken(point);
        if (AuthUtils.illegalUser(token, new int[]{User.TYPE_ADMIN,User.TYPE_CREATOR})) {
            return new ApiResult(false, Messages.NO_PERMISSION.toString(), null);
        }
        return (ApiResult) point.proceed();
    }

}
