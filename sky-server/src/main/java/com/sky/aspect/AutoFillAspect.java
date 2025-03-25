package com.sky.aspect;

import com.sky.annotation.AutoFill;
import com.sky.context.BaseContext;
import com.sky.enumeration.OperationType;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @author zrh
 * @version 1.0.0
 * @title AutoFillAspect
 * @description <通过aop实现一些公共字段的自动注入>
 * @create 2025/3/25 22:13
 **/
@Slf4j
@Aspect
@Component
public class AutoFillAspect {
    // 切入点: 所有mapper包下所有方法上加了AutoFill注解的方法
    @Pointcut("execution(* com.sky.mapper.*.*(..)) && @annotation(com.sky.annotation.AutoFill)")
    public void autoFillPointcut() {
    }

    //前置通知
    @Before("autoFillPointcut()")
    public void autoFill(JoinPoint joinPoint) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        log.info("开始进行公共字段的自动填充");

        //获取到注解的值
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();//获取签名对象
        AutoFill autoFill = signature.getMethod().getAnnotation(AutoFill.class);//通过签名对象获取到拦截的方法上的注解
        OperationType operationType = autoFill.value();

        //获取到当前被拦截的方法的参数
        Object[] args = joinPoint.getArgs();
        if (args == null || args.length == 0) {
            return;
        }
        Object entity = args[0];

        //准备赋值数据
        Long currentId = BaseContext.getCurrentId();
        LocalDateTime now = LocalDateTime.now();

        //根据不同的操作类型，对实体类进行赋值
        if (OperationType.INSERT.equals(operationType)) {
            entity.getClass().getDeclaredMethod("setCreateTime", LocalDateTime.class).invoke(entity, now);
            entity.getClass().getDeclaredMethod("setUpdateTime", LocalDateTime.class).invoke(entity, now);
            entity.getClass().getDeclaredMethod("setCreateUser", Long.class).invoke(entity, currentId);
            entity.getClass().getDeclaredMethod("setUpdateUser", Long.class).invoke(entity, currentId);
        }else if(operationType.equals(OperationType.UPDATE))
        {
            entity.getClass().getDeclaredMethod("setUpdateTime", LocalDateTime.class).invoke(entity, now);
            entity.getClass().getDeclaredMethod("setUpdateUser", Long.class).invoke(entity, currentId);
        }
    }
}
