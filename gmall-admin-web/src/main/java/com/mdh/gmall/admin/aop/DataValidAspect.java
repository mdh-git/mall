package com.mdh.gmall.admin.aop;

import com.mdh.gmall.to.CommonResult;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;

/**
 * 校验的切面
 * 1、引入pom文件
 *      spring-boot-starter-aop
 * 2、编写切面
 *      1)、@Aspect
 *      2)、切入点表达式
 *      3)、通知
 *              前置通知：方法执行之前触发
 *              后置通知：方法执行之后触发
 *              返回通知：方法正常返回之后触发
 *              异常通知：方法出现异常触发
 *
 *            正常执行：  前置 --> 返回 --> 后置
 *            异常执行：  前置 --> 异常 --> 后置
 *              环绕通知： 上面四个的集合
 *
 * 利用aop完成统一的数据校验,数据校验出错就返回给前端错误的提示
 *
 * @author madonghao
 * @create 2019-12-22 16:42
 **/
@Aspect
@Component
public class DataValidAspect {

    @Around("execution( * com.mdh.gmall.admin..*Controller.*(..))")
    public Object validAround(ProceedingJoinPoint point){
        Object proceed = null;
        // 反射的method.invoke();
        try {
            Object[] args = point.getArgs();
            for (Object obj : args){
                if(obj instanceof BindingResult){
                    BindingResult result = (BindingResult) obj;
                    if(result.getErrorCount() > 0){
                        // 校验检测到有错误
                        return new CommonResult().validateFailed(result);
                    }
                }
            }

            System.out.println("前置通知：");
            proceed = point.proceed(point.getArgs());
            System.out.println("返回通知：");
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            System.out.println("异常通知：");
            throw new RuntimeException(throwable);
        } finally {
            System.out.println("后置通知：");
        }
        return proceed;
    }
}
