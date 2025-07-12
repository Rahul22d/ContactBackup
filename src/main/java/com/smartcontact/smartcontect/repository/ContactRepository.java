package com.smartcontact.smartcontect.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.smartcontact.smartcontect.entity.Contact;
import com.smartcontact.smartcontect.entity.User;

public interface ContactRepository extends JpaRepository<Contact, Integer> {

   @Query("SELECT c FROM Contact c JOIN c.user u WHERE u.email = :eamil")
    public Page<Contact> findContactsByUserEmail(@Param("eamil") String email, Pageable pageable);

    // public Contact findByNumber(String number);
    public Contact findByUserAndNumber(User user, String number);    

    public Contact findBycId(int contactId);
    
}
