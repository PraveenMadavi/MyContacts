package com.myprojects.smartcontactmaneger.Repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.myprojects.smartcontactmaneger.entities.Contact;

public interface ContactRepo extends JpaRepository<Contact, Integer> {

    @Query("SELECT c FROM Contact c WHERE c.user.id = :userId")
    List<Contact> getContacts(@Param("userId") int userId);

}
