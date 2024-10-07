package com.myprojects.smartcontactmaneger.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;


@Controller
@RequestMapping("/user")
public class UserController {

    @RequestMapping("/index")
    public String dashbourd(Model model, Principal principal) {
        String userName = principal.getName();
        String userdetails = principal.toString();
        System.out.println(userdetails);
        model.addAttribute("username", userName);
        model.addAttribute("userdetails",userdetails);


        return "normal/dashbourd";
    }
    
}
