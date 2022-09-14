package com.zzx.aspect;

import com.alibaba.fastjson.JSON;
import com.zzx.annotation.SystemLog;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.servlet.ServletRequestContext;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @program: SGBlog
 * @Description 日志切面
 * @Author: 那个小楠瓜
 * @create: 2022-03-16 13:31
 **/
@Component
@Aspect
@Slf4j
public class LogAspect {

    @Pointcut("@annotation(com.zzx.annotation.SystemLog)")
    public void pt() {}

    @Around("pt()")
    public Object printLog(ProceedingJoinPoint joinPoint) throws Throwable{
        Object ret;
        try {
            HandlerBefore(joinPoint);
            ret = joinPoint.proceed();
            HandlerAfter(ret);
        } finally {
            //结束后换行 lineSeparator：当前所执行系统的换行
            log.info("=======End=======" + System.lineSeparator());
        }
        return ret;
    }

    private void HandlerAfter(Object ret) {
        log.info("Response         :  {}", JSON.toJSONString(ret));
    }

    private void HandlerBefore(ProceedingJoinPoint joinPoint) {

        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();

        //获取被增强方法上的注解对象
        SystemLog systemLog = getSystemLog(joinPoint);
        log.info("=======Start=======");
        //打印请求
        log.info("URL               :  {}", request.getRequestURL());
        //打印描述信息
        log.info("BusinessName      :  {}", systemLog.businessName());
        //打印 Http Method
        log.info("Http Method       :  {}", request.getMethod());
        //打印调用 controller 的全路径以及执行方法
        log.info("Class Method      :  {}.{}", joinPoint.getSignature().getDeclaringTypeName(), ((MethodSignature) joinPoint.getSignature()).getName());
        //打印请求的 IP
        log.info("IP                :  {}", request.getRemoteHost());
        //打印请求的参数
        log.info("Request Args      :  {}", JSON.toJSONString(joinPoint.getArgs()));
    }

    private SystemLog getSystemLog(ProceedingJoinPoint joinPoint) {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        SystemLog systemLog = methodSignature.getMethod().getAnnotation(SystemLog.class);
        return systemLog;
    }
}
