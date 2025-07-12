package com.smartcontact.smartcontect.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.smartcontact.smartcontect.entity.User;

public interface UserRepository extends JpaRepository<User, Integer>{

    public User findByEmail(String email);
    
}
