package com.memorizeme;

import com.google.gson.Gson;
import com.memorizeme.constant.AppConstant;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Arrays;

@Aspect
@Component
@Slf4j
public class LogginAdvice {

    //TODO: IMPLEMENTAR OFUSCATOR

    @Pointcut("@within(org.springframework.web.bind.annotation.RestController)")
    public void restControllers() {
    }

    @Before("restControllers()")
    public void onExecute(JoinPoint joinPoint) {

        MethodSignature signature = (MethodSignature) joinPoint.getSignature();

        if (!signature.getMethod().getName().equals("openapiJson")) {

            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

            log.info("");
            log.info(AppConstant.TEXT_LOG_RULER);
            log.info(String.format("DESCRIPTION: %s", signature.getMethod()));
            log.info(String.format("ENDPOINT...: %s - %s", request.getMethod(), request.getRequestURI()));

            if (joinPoint.getArgs().length > 0) {
                Arrays.stream(joinPoint.getArgs())
                        .forEach(element -> {
                            log.info( String.format("REQUEST....: %s", new Gson().toJson(element)) );
                        });
            } else {
                log.info("REQUEST....: NO VALUES");
            }
        }
    }

    @AfterReturning(pointcut = "restControllers()", returning = "response")
    public void logResponse(JoinPoint joinPoint, Object response) {

        MethodSignature signature = (MethodSignature) joinPoint.getSignature();

        if (!signature.getMethod().getName().equals("openapiJson")) {
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            if (attributes != null) {
                log.info( String.format("RESPONSE...: %s", response));
            }
        }
    }
}
