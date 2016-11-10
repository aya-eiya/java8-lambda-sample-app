package jp.eiya.aya.web.util.security.base;

import jp.eiya.aya.web.service.AccountInfoService;
import jp.eiya.aya.web.service.LoginService;
import jp.eiya.aya.web.service.PaymentService;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

public abstract class AbstractPermissionManager {

    @Inject
    protected LoginService loginService;
    @Inject
    protected PaymentService paymentService;
    @Inject
    protected AccountInfoService accountInfoService;
    @Inject
    protected HttpServletRequest request;


    protected void setLoginService(LoginService loginService){
        this.loginService = loginService;
    }

    protected void setPaymentService(PaymentService paymentService){
        this.paymentService = paymentService;
    }

    protected void setAccountInfoService(AccountInfoService accountInfoService){
        this.accountInfoService = accountInfoService;
    }

    /**
     * provide a permission has the way to check it is granted.
     **/
    public interface Permission {
        boolean isGranted();
    }

    /***
     * check the opt if it's present,else returns false
     */
    protected <T> boolean checkIfPresent(Optional<T> opt, Check<T> check){
        if(!opt.isPresent()){return false;}
        return check.check(opt.get());
    }

    protected interface Check<T>{ boolean check(T obj); }
}
