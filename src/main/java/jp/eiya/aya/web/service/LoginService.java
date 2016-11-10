package jp.eiya.aya.web.service;

import jp.eiya.aya.web.model.dao.Account;
import jp.eiya.aya.web.model.dao.PasswordTable;
import jp.eiya.aya.web.model.session.LoginUser;
import jp.eiya.aya.web.service.base.ServiceBase;
import jp.eiya.aya.web.util.session.HttpSessionKeyUtils;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

import java.util.Optional;

import static jp.eiya.aya.web.util.session.HttpSessionKeyUtils.LOGIN_USER_SESSION_KEY;

@Service
public class LoginService extends ServiceBase {

    @Inject
    private HttpSessionKeyUtils httpSessionKeyUtils;

    @Inject
    private AccountService accountService;

    public boolean tryLogin(String accountName,String password){
        Optional<Account> account = accountService.getAccount(accountName);
        if(account.isPresent()){
            Optional<PasswordTable> passwordTable = myDAO.findPasswordTable(account.get().getId());
            if(passwordTable.isPresent())
            return true;
        }
        return false;
    }

    public boolean tryLogin(Account account,String password){
        return false;
    }

    public Optional<LoginUser> getLoginUser() {
        return Optional.ofNullable(httpSessionKeyUtils.get(LoginUser.class,LOGIN_USER_SESSION_KEY));
    }
}
