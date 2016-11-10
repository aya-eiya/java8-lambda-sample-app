package jp.eiya.aya.web.annotation;

import jp.eiya.aya.web.controller.util.TopRedirector;
import jp.eiya.aya.web.util.security.PermissionManager;

import java.lang.annotation.*;

@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE,ElementType.METHOD})
public @interface PermissionFilter {

    public boolean enabled() default true;

    public String permission() default PermissionManager.Permissions.HAS_ACTIVE_LOGIN_SESSION;

    public Class errorHandler() default TopRedirector.class;
}
