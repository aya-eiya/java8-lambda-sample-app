package jp.eiya.aya.web.aop.run.base;

import java.lang.reflect.Method;

public interface Invoker {
    Object getTargetInstance();

    Method getMethod();

    Object[] getArguments();

    default Object invoke() throws Throwable {
        return getMethod().invoke(getTargetInstance(), getArguments());
    }
}
