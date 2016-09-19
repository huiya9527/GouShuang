package com.goushuang.lyz.controller;

import com.goushuang.lyz.viewObject.LoginMessage;
import com.goushuang.lyz.viewObject.UserType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by lyz on 2016/9/17.
 */
@Controller
public class WelcomeController {
    @RequestMapping("/")
    public String index(Model model){
        model.addAttribute("loginMessage", new LoginMessage());
        return "login";
    }
}
