package jp.eiya.aya.web.util.security;

import javax.inject.Named;
import javax.inject.Singleton;

import jp.eiya.aya.web.model.dao.PaymentStatus;
import jp.eiya.aya.web.model.session.LoginUser;
import jp.eiya.aya.web.util.security.base.AbstractPermissionManager;

import java.util.Optional;

@Named
@Singleton
public class PermissionManager extends AbstractPermissionManager {
    public static final class Permissions{
        public static final String NOT_LOGIN_SESSION = "notLoginSession";
        public static final String HAS_ACTIVE_LOGIN_SESSION = "hasActiveLoginSession";
        public static final String HAS_PAID_TICKET = "hasPaidTicket_computed";
        public static final String ACCOUNT_INFO_IS_COMPLETED = "accountInfoIsCompleted";
        public static final String OVER_18_AND_MORE = "over18AndMore";
        public static final String UNDER_18_LESS = "under18Less";
        public static final String OVER_20_AND_MORE = "over20AndMore";
    }

    @Named(Permissions.HAS_ACTIVE_LOGIN_SESSION)
    public Permission hasActiveLoginSession() {
        return () -> loginService.getLoginUser().isPresent();
    }

    @Named(Permissions.NOT_LOGIN_SESSION)
    public Permission notLoginSession()  { return ()-> !hasActiveLoginSession().isGranted(); }

    @Named(Permissions.HAS_PAID_TICKET)
    public Permission hasPaidTicket() {
        return () ->
            checkIfPresent(
                loginService.getLoginUser(),
                (loginUser) ->
                    paymentService.getPaymentStatuses(loginUser).stream()
                    .filter(PaymentStatus::hasPaidTicket_computed)
                    .findAny()
                    .isPresent()
            );
    }

    @Named(Permissions.ACCOUNT_INFO_IS_COMPLETED)
    public Permission accountInfoIsCompleted() {
        return () ->
            checkIfPresent(
                loginService.getLoginUser(),
                (loginUser)->accountInfoService.getAccountInfo(loginUser).isCompleted_computed()
            );
    }

    @Named(Permissions.OVER_18_AND_MORE)
    public Permission over18AndMore() {
        return () ->
            checkIfPresent(
                loginService.getLoginUser(),
                (loginUser)->
                    checkIfPresent(accountInfoService.getAccountInfo(loginUser).getAge_computed(),
                    (age) -> age >= 18
                )
            );
    }

    @Named(Permissions.UNDER_18_LESS)
    public Permission under18Less() {
        return () ->
            checkIfPresent(
                loginService.getLoginUser(),
                (loginUser)->
                    checkIfPresent(accountInfoService.getAccountInfo(loginUser).getAge_computed(),
                    (age) -> age < 18
                )
            );
    }

    @Named(Permissions.OVER_20_AND_MORE)
    public Permission over20AndMore() {
        return () ->
            checkIfPresent(
                loginService.getLoginUser(),
                (loginUser)->
                    checkIfPresent(accountInfoService.getAccountInfo(loginUser).getAge_computed(),
                    (age) -> age >= 20
                )
            );
    }
}
