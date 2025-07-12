package com.smartcontact.smartcontect.controller;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.smartcontact.smartcontect.entity.Contact;
import com.smartcontact.smartcontect.entity.User;
import com.smartcontact.smartcontect.repository.ContactRepository;
import com.smartcontact.smartcontect.repository.UserRepository;

@RestController
public class api {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ContactRepository contactRepository;

     @GetMapping("/data")
    public List<User> users() {
        return userRepository.findAll();
    }

    @GetMapping("/contact") 
    public List<Contact> contact() {
        return contactRepository.findAll();
    }

    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @DeleteMapping("/delete_contact/{id}") 
    public String deleteContact(@PathVariable("id") int id, Principal principal) {

        // delete logic
        String email = principal.getName();
    Optional<Contact> contactOpt = Optional.ofNullable(contactRepository.findBycId(id));

    System.out.println(contactOpt);
    if(contactOpt.isPresent()) {
        Contact contact = contactOpt.get();
        // Make sure the contact belongs to the logged-in user (optional but recommended)
        if(contact.getUser().getEmail().equals(email)) {
            contactRepository.delete(contact);
        }
    }
    System.out.println("delete contact");
    return "user/view_contact";
    }

    /*
     * GET (DATA REVICE)
     * POST (DATA KO SEND, INSERT)
     * PUT (UPDATE)
     * DELETE (DELETE)
     * http server request or respone
     * 
     * controller request 200 ok 201 create, 404 not found , 
     * 500 internal server error
     * 
     * 
     */
    

}
