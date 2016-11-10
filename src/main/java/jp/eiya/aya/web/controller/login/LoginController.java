package jp.eiya.aya.web.controller.login;

import jp.eiya.aya.web.model.form.LoginForm;
import jp.eiya.aya.web.service.LoginService;
import jp.eiya.aya.web.util.rest.APIResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.inject.Inject;

@Controller
@RequestMapping("login")
public class LoginController {
    @Inject
    private LoginService loginService;

    @RequestMapping("")
    public String index(Model model){
        model.addAttribute("title","Java8LambdaSample > Login");
        model.addAttribute("contentTitle","Login");
        model.addAttribute("inLogin",true);
        return "login/index";
    }

}
