package com.myprojects.smartcontactmaneger.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.myprojects.smartcontactmaneger.Repos.UserRepo;
import com.myprojects.smartcontactmaneger.entities.Message;
import com.myprojects.smartcontactmaneger.entities.User;

import jakarta.validation.Valid;

@Controller
public class HomeController {
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private UserRepo userRepo;

    @GetMapping("/home")
    public String home(Model model) {
        // Save a new user
        // User user = new User();
        // user.setName("John Doe");
        // user.setEmail("joj@myprojects.com");
        // user.setPassword("password123");
        // user.setRole("ROLE_USER");
        // user.setEnabled(true);
        // userRepo.save(user);
        // System.out.println("User saved successfully");
        // System.out.println(user + " created successfully");
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
            System.out.println(user);
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

    @PostMapping("/login")
    public String userLogin(@RequestParam("email") String email, @RequestParam("password") String password,
            Model model) {
        User loggedUser = userRepo.getUserByEmail(email);

        if (loggedUser != null && passwordEncoder.matches(password, loggedUser.getPassword())) {
            // User is authenticated, you can save user details in session or perform other
            // actions
            System.out.println("APPLICATION : User logged in successfully.");
            return "redirect:/user/index";
        } else {
            // User authentication failed
            model.addAttribute("message", new Message("Invalid email or password.", "alert-danger"));
            return "signin";
        }
    }



    // @PostMapping("/register")
    // public String registerUser(@RequestParam("name") String name,
    // @RequestParam("email") String email,
    // @RequestParam("password") String password,
    // @RequestParam("terms") boolean terms,
    // @RequestParam("about") String about) {
    // System.out.println("APPLICATION : register triggered.");
    // // Handle registration logic here
    // if (terms) {
    // User user = new User();
    // user.setName(name);
    // user.setEmail(email);
    // user.setPassword(password);
    // user.setAbout(about);
    // user.setRole("ROLE_USER");
    // user.setEnabled(true);
    // userRepo.save(user);
    // System.out.println(user.toString());
    // System.out.println("APPLICATION : User details saves successfully");
    // return "redirect:/signin";
    // }
    // System.out.println("APPLICATION : SignUp triggered due to user not accepted
    // terms and conditions.");
    // return "signup";
    // }
}
