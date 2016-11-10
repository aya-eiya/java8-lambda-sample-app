package jp.eiya.aya.web.util.session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import javax.servlet.http.HttpSession;
import java.io.Serializable;

@Named
@Singleton
public class HttpSessionKeyUtils {

    @Inject
    HttpSession session;

    static final public String LOGIN_USER_SESSION_KEY = HttpSessionKeyUtils.class.getTypeName() + "#LOGIN_USER_SESSION_KEY#";

    public <T extends Serializable> T get(Class<T> clz, String sessionKey){
        return _castTo(clz,session.getAttribute(LOGIN_USER_SESSION_KEY));
    }

    static private <T extends Serializable> T _castTo(Class<T> clz,Object sessionObj){
        if(sessionObj == null){ return null; }
        if(clz.isInstance(sessionObj)) { return clz.cast(sessionObj); }
        return null;
    }

}
