package jp.eiya.aya.web.controller.login;


import jp.eiya.aya.web.model.form.LoginForm;
import jp.eiya.aya.web.service.LoginService;
import jp.eiya.aya.web.util.rest.APIResponse;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;

@Controller
@RequestMapping("login/api")
public class LoginPostAPI extends LoginController{
    @Inject
    private LoginService loginService;

    private TransactionTemplate tx;

    @Inject
    private void setTx(PlatformTransactionManager java8LambdaSampleTxManager){
        this.tx = new TransactionTemplate(java8LambdaSampleTxManager);
    }

    @RequestMapping(path = "tryLogin",method = RequestMethod.POST)
    public @ResponseBody APIResponse<LoginForm> tryLogin(@RequestBody LoginForm form){
        return tx.execute(status->{
            boolean success = loginService.tryLogin(form.getAccountName(),form.getPassword());
            if(!success){
                status.setRollbackOnly();
            }
            return success;
        }) ? () -> {
            return form;
        } : APIResponse.failBy("fail to login", () -> {
            return form;
        });
    }
}