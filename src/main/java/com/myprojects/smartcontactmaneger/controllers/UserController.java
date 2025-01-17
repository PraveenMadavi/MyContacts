package com.myprojects.smartcontactmaneger.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;

import com.myprojects.smartcontactmaneger.Repos.ContactRepo;
import com.myprojects.smartcontactmaneger.Repos.UserRepo;
import com.myprojects.smartcontactmaneger.entities.Contact;
import com.myprojects.smartcontactmaneger.entities.Message;
import com.myprojects.smartcontactmaneger.entities.User;

import java.security.Principal;
import java.util.List;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private ContactRepo contactRepo;

    @Autowired
    private UserRepo userRepo;

    private User user;

    @ModelAttribute
    public void Commonhandler(Model model, Principal principal) {
        String userName = principal.getName();
        String userdetails = principal.toString();
        model.addAttribute("username", userName);
        model.addAttribute("userdetails", userdetails);

        user = userRepo.getUserByEmail(userName);
        model.addAttribute("user", user);
    }

    @RequestMapping("/index")
    public String dashbourd(Model model) {
        model.addAttribute("title", "User Dashboard");
        // System.out.println(userdetails);

        return "normal/dashbourd";
    }

    @RequestMapping("/addContact")
    public String addContact(Model model) {
        model.addAttribute("contact", new Contact());
        model.addAttribute("title", "Add Contact");

        System.out.println("APPLICATION : Add contact form triggered.");
        return "normal/addcontactform";
    }

    @PostMapping("/addNewContact") // Processing method for adding new contact
    public String addNewContact(@ModelAttribute("contact") Contact contact, BindingResult result, Model model) {

        System.out.println("APPLICATION : New Contact");
        contact.setUser(user);
        System.out.println(contact.toString());
        contactRepo.save(contact);
        model.addAttribute("message", new Message("Contact added successfully.", "alert-primary"));
        model.addAttribute("title", "UserDashboard");

        return "normal/addcontactform";
    }

    @GetMapping("/mycontacts") // Processing method for getting my contacts
    public String myContacts(Model model) {
        List<Contact> contacts = contactRepo.getContacts(user.getId());

        model.addAttribute("contacts", contacts);

        if (!contacts.isEmpty()) {
            System.out.println("APPLICATION : My Contacts ~~~~~~~~~~~~~~~~~~~~~~~~~~~");
            for (Contact contact : contacts) {
                System.out.println(contact);
                System.out.println("----------------------------------------");
            }
        }

        if (contacts.isEmpty()) {
            model.addAttribute("message", "No contacts found");
        }

        model.addAttribute("title", "My Contacts");

        return "normal/mycontacts";

    }

}
