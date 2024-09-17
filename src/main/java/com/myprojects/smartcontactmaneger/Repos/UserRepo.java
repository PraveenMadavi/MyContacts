package com.myprojects.smartcontactmaneger.Repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.myprojects.smartcontactmaneger.entities.User;

public interface UserRepo extends JpaRepository<User, Integer> {

    @Query("select u from User u where u.email = :email")
    public User getUserByEmail(@Param("email") String email);

}
