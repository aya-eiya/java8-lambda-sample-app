package jp.eiya.aya.web.service;

import com.google.common.collect.ImmutableList;
import jp.eiya.aya.web.model.dao.Account;
import jp.eiya.aya.web.model.dao.PaymentStatus;
import jp.eiya.aya.web.model.session.LoginUser;
import jp.eiya.aya.web.service.base.ServiceBase;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PaymentService extends ServiceBase {
    private AccountService accountService;

    public List<PaymentStatus> getPaymentStatuses(LoginUser loginUser){
        Optional<Account> account = accountService.getAccount(loginUser.getAccountId());
        if(!account.isPresent()) return ImmutableList.of();
        return myDAO.findPaymentStatuses(account.get().getId());
    }
}
