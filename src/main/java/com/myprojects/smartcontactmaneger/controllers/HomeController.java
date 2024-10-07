package com.myprojects.smartcontactmaneger.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.myprojects.smartcontactmaneger.Repos.UserRepo;
import com.myprojects.smartcontactmaneger.entities.Message;
import com.myprojects.smartcontactmaneger.entities.User;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class HomeController {
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private UserRepo userRepo;

    @GetMapping("/home")
    public String home(Model model) {
        model.addAttribute("title", "Home: Smart Contact Manager");
        model.addAttribute("basetitle", "Model base title");

        System.out.println("APPLICATION : home triggered");

        return "home";
    }

    @GetMapping("/about")
    public String about(Model model) {

        model.addAttribute("title", "Home: Smart Contact Manager");

        System.out.println("APPLICATION : About triggered");

        return "about";
    }

    @GetMapping("/signup")
    public String signup(Model model) {

        model.addAttribute("title", "Home: Smart Contact Manager");
        model.addAttribute("userData", new User());

        System.out.println("APPLICATION : SignUp triggered");

        return "signup";
    }

    @GetMapping("/signin")
    public String signin(Model model) {
        model.addAttribute("title", "Home: Smart Contact Manager");
        System.out.println("APPLICATION : SignIn triggered");
        return "signin";
    }

    @PostMapping("/register") // Best if userside validations
    public String registerUser(@Valid @ModelAttribute("userData") User user, BindingResult result, Model model) {
        try {
            if (result.hasErrors()) {
                System.out.println("Error: " + result.toString());
                model.addAttribute("userData", user);
                model.addAttribute("message", new Message("Please fill all details.", "alert-info"));

                return "signup";
            }

            System.out.println("APPLICATION : register triggered.");
            System.out.println(user); // for assurance purposes

            user.setRole("ROLE_USER");
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setImageUrl("default.jpg");

            userRepo.save(user);
            System.out.println("User Details: " + user);
            System.out.println("APPLICATION : User details saved successfully");

            model.addAttribute("message", new Message("You are successfully registered.", "alert-primary"));
            model.addAttribute("userData", user);

            System.out.println("From edited handler.........");
            return "signup";

        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("userData", user);
            model.addAttribute("message", new Message("Something went wrong in Server side !!", "alert-warning"));

            return "signup";
        }
    }



    // @PostMapping("/login") // NO NEEDED
    // public String userLogin(Model model) {
    //     System.out.println("APPLICATION : User logged in successfully by using login ");
    //     return "redirect:/user/index";
    // }

    // @PostMapping("/logout") //DONT REMOVE  COMMENTED LOGOUT CONTROLLER METHOD 
    // public String logout(HttpServletRequest request, HttpServletResponse response) {
    //     // Invalidate the session
    //     HttpSession session = request.getSession(false);
    //     if (session != null) {
    //         session.invalidate();
    //     }
    //     // Remove the authentication object
    //     SecurityContextHolder.clearContext();
    //     // Delete the cookies (Optional: Delete more cookies if needed)
    //     var cookie = WebUtils.getCookie(request, "JSESSIONID");
    //     if (cookie != null) {
    //         cookie.setMaxAge(0);
    //         cookie.setPath("/");
    //         response.addCookie(cookie);
    //     }
    //     System.out.println("Application : User logged out.");
    //     // Redirect to the login page after logout
    //     return  "redirect:/signin?logout=true"; 
    // }




}