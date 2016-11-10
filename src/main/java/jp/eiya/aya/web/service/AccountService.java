package jp.eiya.aya.web.service;

import jp.eiya.aya.web.model.dao.Account;
import jp.eiya.aya.web.model.dao.PasswordTable;
import jp.eiya.aya.web.service.base.ServiceBase;
import jp.eiya.aya.web.util.security.HashUtils;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.Optional;

@Service
public class AccountService  extends ServiceBase {

    @Inject
    HashUtils hashUtils;

    public Optional<Account> getAccount(Integer accountId) {
        return myDAO.findAccount(accountId);
    }

    public Optional<Account> getAccount(String accountName) {
        return myDAO.findAccount(accountName);
    }

    public boolean create(String accountName, String password) {
        Account account = new Account();
        PasswordTable passTable = new PasswordTable();
        return myDAO.insert(()->{
            account.setAccountName(accountName);
            return account;
        }).isSuccess() &&
        myDAO.insert(()->{
            String salt = hashUtils.salt();
            passTable.setAccountId(account.getId());
            passTable.setPassword(hashUtils.hash(password,salt));
            passTable.setSalt(salt);
            return passTable;
        }).isSuccess();
    }
}
