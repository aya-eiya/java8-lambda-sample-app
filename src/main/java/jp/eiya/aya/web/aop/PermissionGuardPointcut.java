package jp.eiya.aya.web.aop;

import jp.eiya.aya.web.annotation.PermissionFilter;
import jp.eiya.aya.web.util.security.PermissionManager;
import jp.eiya.aya.web.util.security.base.AbstractPermissionManager;
import jp.eiya.aya.web.util.security.base.AbstractPermissionManager.Permission;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.ClassFilter;
import org.springframework.aop.support.StaticMethodMatcherPointcut;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.inject.Inject;
import javax.inject.Named;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@Named
@Aspect
public class PermissionGuardPointcut {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Inject
    private PermissionManager permissionManager;

    @Around("execution(* jp.eiya.aya.web.controller..*.*(..)) && @annotation(permissionFilter)")
    public Object pointcut(ProceedingJoinPoint point, PermissionFilter permissionFilter) throws Throwable {
        Permission permission = (Permission) PermissionManager.class.getDeclaredMethod(permissionFilter.permission()).invoke(permissionManager);
        if(permission.isGranted()){
            logger.info("{}#{} : permission {} allowed.",
                    point.getSignature().getDeclaringTypeName(),
                    point.getSignature().getName(),
                    permissionFilter.permission()
            );
            return point.proceed(point.getArgs());
        }else{
            return "redirect:/";
        }
    }
}
