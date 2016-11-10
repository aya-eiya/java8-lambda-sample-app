package jp.eiya.aya.web.controller.register;

import com.google.common.collect.ImmutableMap;
import jp.eiya.aya.web.annotation.PermissionFilter;
import jp.eiya.aya.web.model.dao.Account;
import jp.eiya.aya.web.model.dao.AccountInfo;
import jp.eiya.aya.web.model.form.RegisterForm;
import jp.eiya.aya.web.service.AccountInfoService;
import jp.eiya.aya.web.service.AccountService;
import jp.eiya.aya.web.service.LoginService;
import jp.eiya.aya.web.util.rest.APIResponse;
import jp.eiya.aya.web.util.security.PermissionManager;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.inject.Inject;
import java.util.Optional;

@Controller
@RequestMapping("register")
public class RegisterController {
    @Inject
    private AccountService accountService;

    @Inject
    protected AccountInfoService accountInfoService;

    @Inject
    protected LoginService loginService;

    @RequestMapping("")
    @PermissionFilter(permission = PermissionManager.Permissions.NOT_LOGIN_SESSION)
    public String index(Model model) {
        model.addAttribute("inRegister", true);
        return "register/index";
    }
}
