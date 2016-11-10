package jp.eiya.aya.web.aop.run;

import jp.eiya.aya.web.aop.run.base.Invoker;
import org.aopalliance.intercept.MethodInvocation;

import java.lang.reflect.Method;

/**
 * Created by h.ayabe on 2016/11/08.
 */
public class DefaultInVoker implements Invoker {

    private final MethodInvocation invokeTarget;

    public DefaultInVoker(MethodInvocation invokeTarget){
        this.invokeTarget = invokeTarget;
    }

    @Override
    public Object getTargetInstance() {
        return invokeTarget.getThis();
    }

    @Override
    public Method getMethod() {
        return invokeTarget.getMethod();
    }

    @Override
    public Object[] getArguments() {
        return invokeTarget.getArguments();
    }

    @Override
    public Object invoke() throws Throwable {
        return invokeTarget.proceed();
    }
}
