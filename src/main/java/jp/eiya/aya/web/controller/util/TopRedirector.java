package jp.eiya.aya.web.controller.util;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


public class TopRedirector {
    @RequestMapping
    public Object handle(){
        return "redirect:/";
    }
}
