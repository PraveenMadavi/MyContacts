package com.myprojects.smartcontactmaneger.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.myprojects.smartcontactmaneger.Repos.UserRepo;
import com.myprojects.smartcontactmaneger.entities.User;

public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String useremail) throws UsernameNotFoundException {
        // TODO: Implement your custom logic to retrieve user details from your database
        
        User userByEmail = userRepo.getUserByEmail(useremail);

        if (userByEmail == null) {
            throw new UsernameNotFoundException("User not found with email: " + useremail);           
        }

       return new CustomUserDetails(userByEmail);
    }
    
}
