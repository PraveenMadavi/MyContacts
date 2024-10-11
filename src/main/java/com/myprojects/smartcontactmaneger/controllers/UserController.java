package com.myprojects.smartcontactmaneger.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;

import com.myprojects.smartcontactmaneger.Repos.ContactRepo;
import com.myprojects.smartcontactmaneger.Repos.UserRepo;
import com.myprojects.smartcontactmaneger.entities.Contact;
import com.myprojects.smartcontactmaneger.entities.User;

import java.security.Principal;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RequestBody;



@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private ContactRepo contactRepo;

    @Autowired
    private UserRepo userRepo;

    private User user;

    @ModelAttribute
    public void Commonhandler( Model model,Principal principal){
        String userName = principal.getName();
        String userdetails = principal.toString();
        model.addAttribute("username", userName);
        model.addAttribute("userdetails",userdetails);

        user = userRepo.getUserByEmail(userName);
        // userId = user.getId();
        model.addAttribute("user", user);
    }

    @RequestMapping("/index")
    public String dashbourd() {
        
        // System.out.println(userdetails);
    
        return "normal/dashbourd";
    }

    @RequestMapping("/addContact")
    public String addContact(Model model) {  
        model.addAttribute("contact", new Contact());  
        System.out.println("APPLICATION : Add contact form triggered.");
        return "normal/addcontactform";
    }



    @PostMapping("/addNewContact") // Process method for adding new contact
    public String addNewContact(@ModelAttribute ("contact") Contact contact, BindingResult result, Model  model) { 

        System.out.println("APPLICATION : New Contact");
        contact.setUser(user);
        System.out.println(contact.toString());
        contactRepo.save(contact);

        return "normal/addcontactform";
    }
    
    
}
