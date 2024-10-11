package com.myprojects.smartcontactmaneger.Repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.myprojects.smartcontactmaneger.entities.Contact;

public interface ContactRepo  extends JpaRepository <Contact , Integer>{
    
}
