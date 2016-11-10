package jp.eiya.aya.web.service;

import jp.eiya.aya.web.model.dao.Account;
import jp.eiya.aya.web.model.dao.AccountInfo;
import jp.eiya.aya.web.model.session.LoginUser;
import jp.eiya.aya.web.service.base.ServiceBase;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class AccountInfoService extends ServiceBase {

    public AccountInfo getAccountInfo(LoginUser loginUser) {
        return null;
    }

    public boolean upsert(Account account, Map<String, String> params) {
        AccountInfo accountInfo = myDAO.findAccountInfo(account.getId()).orElse(new AccountInfo());
        myDAO.upsert(()->{
            accountInfo.setAccountId(account.getId());
            if(params.containsKey(AccountInfo.FIELD_USER_NAME)) accountInfo.setUserName(params.get(AccountInfo.FIELD_USER_NAME));
            if(params.containsKey(AccountInfo.FIELD_DISPLAY_NAME)) accountInfo.setDisplayName(params.get(AccountInfo.FIELD_DISPLAY_NAME));
            if(params.containsKey(AccountInfo.FIELD_BIRTH_DAY)) accountInfo.setBirthDay(params.get(AccountInfo.FIELD_BIRTH_DAY));
            return accountInfo;
        });
        return false;
    }
}
