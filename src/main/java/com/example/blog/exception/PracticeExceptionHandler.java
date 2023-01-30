package com.example.blog.exception;

import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;


@ControllerAdvice
public class PracticeExceptionHandler {
    @ExceptionHandler(BusinessException.class)
    public ModelAndView businessExceptionHandler(HttpServletRequest request, BusinessException e){
        String errInfo = "业务异常：" + e.getErrInfo();
        ModelAndView mv = new ModelAndView();
        mv.addObject("title", errInfo);
        mv.addObject("url", request.getRequestURL().toString());
        mv.addObject("exception", e);
        mv.setViewName("error/error");
        return mv;
    }

    @ExceptionHandler(Exception.class)
    public ModelAndView exceptionHandler(HttpServletRequest request, Exception e) throws Exception {
        if (AnnotationUtils.findAnnotation(e.getClass(), ResponseStatus.class) != null){
            throw e;
            //有返回状态注解的异常，不在这里处理
        }
        ModelAndView mv = new ModelAndView();
        mv.addObject("title", "非业务异常");
        mv.addObject("url", request.getRequestURL().toString());
        mv.addObject("exception", e);
        mv.setViewName("error/error");
        return mv;
    }
}
