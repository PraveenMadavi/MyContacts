package com.myprojects.smartcontactmaneger.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.myprojects.smartcontactmaneger.Repos.UserRepo;
import com.myprojects.smartcontactmaneger.entities.User;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Retrieve user from the database using the email (username)
        User userByEmail = userRepo.getUserByEmail(username);
        System.out.println("APPLICATION : USER DETAILS FETCHED : "+ userByEmail);

        // If user is not found, throw UsernameNotFoundException
        if (userByEmail == null) {
            throw new UsernameNotFoundException("User not found with email: " + username);
        }

        // Return a CustomUserDetails object wrapping the User entity
        return new CustomUserDetails(userByEmail);
    }
}
