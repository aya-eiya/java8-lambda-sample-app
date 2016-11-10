package jp.eiya.aya.web.controller.register;

import com.google.common.collect.ImmutableMap;
import jp.eiya.aya.web.model.dao.Account;
import jp.eiya.aya.web.model.dao.AccountInfo;
import jp.eiya.aya.web.model.form.RegisterForm;
import jp.eiya.aya.web.service.AccountInfoService;
import jp.eiya.aya.web.service.AccountService;
import jp.eiya.aya.web.util.rest.APIResponse;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.inject.Inject;
import javax.transaction.TransactionManager;
import java.util.Optional;


@Controller
@RequestMapping("register/api")
public class RegisterPostAPI {
    @Inject
    private AccountService accountService;

    @Inject
    private AccountInfoService accountInfoService;

    private TransactionTemplate tx;

    @Inject
    private void setTx(PlatformTransactionManager java8LambdaSampleTxManager){
        this.tx = new TransactionTemplate(java8LambdaSampleTxManager);
    }

    @RequestMapping(method = RequestMethod.POST)
    public @ResponseBody APIResponse<RegisterForm> register(@RequestBody RegisterForm form) {
        return tx.execute(status ->{
            boolean success = accountService.create(form.getAccountName(), form.getPassword());
            if (success) {
                Optional<Account> account = accountService.getAccount(form.getAccountName());
                if (account.isPresent()) {
                    success = accountInfoService.upsert(account.get(), ImmutableMap.of(
                            AccountInfo.FIELD_USER_NAME, form.getUserName(),
                            AccountInfo.FIELD_DISPLAY_NAME, form.getDisplayName()
                    ));
                }
            }
            if(!success){
                status.setRollbackOnly();
            }
            return success;
        }) ? () -> form:
             APIResponse.failBy("fail to registration", () -> form );
    }
}
