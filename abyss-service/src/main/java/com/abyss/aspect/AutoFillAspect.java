package com.abyss.aspect;

import com.abyss.annotation.AutoFill;
import com.abyss.constant.AutoFillConstant;
import com.abyss.context.BaseContext;
import com.abyss.enumeration.OperationType;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.type.LocalDateTimeTypeHandler;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

import javax.swing.text.html.parser.Entity;
import java.lang.reflect.Method;
import java.time.LocalDateTime;

/**
 * 自定义切面，实现公共字段自动填充逻辑处理
 */
@Slf4j
@Component
@Aspect
public class AutoFillAspect {

    /**
     * 切入点
     */
    @Pointcut("execution( * com.abyss.mapper.*.*(..)) && @annotation(com.abyss.annotation.AutoFill)")
    //execution:指定包的的类的方法 && @annotation注解限制：需要添加annocation这个包下定义的AutoFill注解
    public void autoFillPointCut(){}

    /**
     * 前置通知
     * @param joinPoint
     */
    @Before("autoFillPointCut()")
    //@Before:前置通知； autoFillPointCut:指定切入点
    public void autoFill(JoinPoint joinPoint){
        //JoinPoint:封装了连接点方法调用的详细信息

        log.info("开始进行公共字段填充。。。");

        //获取当前被拦截的方法上的数据库操作类型
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();  //获取方法签名
        //signature调用getMethod()方法获取，调用getAnnotation()方法,通过参数【字节码文件，里面包含了注解详细信息】获取注解
        AutoFill autoFill = signature.getMethod().getAnnotation(AutoFill.class);  //获取方法上的注解对象
        OperationType operationType = autoFill.value();

        //获取当前被拦截方法的参数--实体对象
        Object[] args = joinPoint.getArgs();  //获取所有参数，约定：实体对象置于第一个参数
        Object entity = args[0];  // 获取实体参数

        //空指针判断
        if(args == null || args.length == 0){

            return ;
        }

        // TODO: 只处理实体对象，跳过List/集合

        //准备赋值数据
        LocalDateTime now = LocalDateTime.now();
        Long currentId = BaseContext.getCurrentId();

        //根据操作类型通过反射进行赋值
        if(operationType == OperationType.INSERT){

            try{

                Method setCreateTime = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_CREATE_TIME);
                Method setCreateUser = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_CREATE_USER);
                Method setUpdateTime = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_TIME);
                Method setUpdateUser = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_USER);

                //通过反射为对象赋值
                setCreateTime.invoke(entity, now);
                setUpdateTime.invoke(entity, now);
                setCreateUser.invoke(entity, currentId);
                setUpdateUser.invoke(entity, currentId);

            }catch(Exception e){
                e.printStackTrace();
            }

        }else if(operationType == OperationType.UPDATE){

            try{

                Method setUpdateTime = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_TIME);
                Method setUpdateUser = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_USER);

                //通过反射为对象赋值
                setUpdateTime.invoke(entity, now);
                setUpdateUser.invoke(entity, currentId);

            }catch(Exception e){
                e.printStackTrace();
            }
        }


    }
}
