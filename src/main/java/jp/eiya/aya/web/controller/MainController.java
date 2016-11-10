package jp.eiya.aya.web.controller;

import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.*;

@Controller
public class MainController {
    @RequestMapping("")
    String index(Model model) {
        model.addAttribute("title","Java8LambdaSample");
        model.addAttribute("contentTitle","Java8LambdaSample");
        return "index";
    }
}
