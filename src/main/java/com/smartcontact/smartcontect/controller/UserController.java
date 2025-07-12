package com.smartcontact.smartcontect.controller;

import java.io.IOException;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.data.domain.PageRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.smartcontact.smartcontect.DTO.UserDto;
import com.smartcontact.smartcontect.config.EncryptionUtil;
import com.smartcontact.smartcontect.entity.Contact;
import com.smartcontact.smartcontect.entity.User;
import com.smartcontact.smartcontect.helper.FileUploadHelper;
import com.smartcontact.smartcontect.helper.Message;
import com.smartcontact.smartcontect.repository.ContactRepository;
import com.smartcontact.smartcontect.repository.UserRepository;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.RequestBody;



@Controller 
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ContactRepository contactRepository;

    @Autowired
    private FileUploadHelper fileUploadHelper;

    @GetMapping("")
    public String dashboard(Model model, Principal principal) {
        String email = principal.getName();

        User user = userRepository.findByEmail(email);
        
        model.addAttribute("user", user);

        System.out.println(user.getName());

        return "user/user_dashboard";
    }
    

    @GetMapping("/add_contact_page")
    public String addContactPage(Model model) {
        model.addAttribute("title", "Add Contact");
        model.addAttribute("contact", new Contact());

        return "user/add_contact";
    }

    @PostMapping("/add_contact")
    public String addContact(@Valid @ModelAttribute("contact") Contact contact, BindingResult result, @RequestParam("file") MultipartFile file
        , Principal principal, HttpSession session) throws IOException {

        try {
            
            if(result.hasErrors()) {
                System.out.println(result);
                return "user/add_contact";
            }
            if(file.isEmpty()) {
                System.out.println("image not found");
            }
            
            
            String email = principal.getName();
            
            User user = userRepository.findByEmail(email);

            Contact exist = contactRepository.findByUserAndNumber(user, contact.getNumber());
            if(exist != null){
                result.rejectValue("number", "error.contact", "This number is already saved.");
                return "user/add_contact";
            }
            
            contact.setImageUrl(file.getOriginalFilename());
            
            contact.setUser(user);
            
            fileUploadHelper.uploadFile(file);
            contactRepository.save(contact);
            session.setAttribute("message", new Message("Add number successfully", "alert-success"));

        } catch (Exception e) {
            e.printStackTrace();
            session.setAttribute("message", new Message("Number not add", "alert-denger"));
            return "user/add_contact";

        }
        return "user/add_contact";
        // return "";
    }


    // for view contact
    @GetMapping("/view_contact")
    public String showContact(@RequestParam(value = "page", defaultValue = "0") int page, Model model, Principal principal) {
        String email = principal.getName();
        Pageable pageable = PageRequest.of(page, 12);

        Page<Contact> contacts = contactRepository.findContactsByUserEmail(email, pageable);

        // If user deleted last contact on last page, redirect to previous page
        if (contacts.isEmpty() && page > 0) {
            return "user/view_contact?page=" + (page - 1);
        }

        // Encrypt contact IDs
        Map<Contact, String> contactWithEncryptedIds = new HashMap<>();
        for (Contact c : contacts) {
            try {
                String encryptedId = EncryptionUtil.encrypt(String.valueOf(c.getcId()));
                contactWithEncryptedIds.put(c, encryptedId);
            } catch (Exception e) {
                // handle encryption 
                e.printStackTrace();
            }
        } 

        if(contacts.isEmpty()) {
            model.addAttribute("empty", "You have not save any phone number.");
        }
        model.addAttribute("contacts", contactWithEncryptedIds);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", contacts.getTotalPages());
        
        // contactWithEncryptedIds.clear();

        return "user/view_contact"; // Must have view_contact.html under templates/user/
    }

    
    // for update contact details
    @GetMapping("/update-contact")
    public String updateContactPage(@RequestParam("ref") String encryptedId, Model model) {
        try {
           String decrypted = EncryptionUtil.decrypt(encryptedId);
            int cId = Integer.parseInt(decrypted);
            Contact contact = contactRepository.findBycId(cId);

            if(contact == null) {
               model.addAttribute("error", "Not found!");
                return "user/update_contact_page";
            }
            model.addAttribute("title", "Update_page");
            model.addAttribute("contact", contact);
            model.addAttribute("encryptedId", encryptedId);
           
        } catch (Exception e) {
            // TODO: handle exception
             model.addAttribute("error", "Not found!");
                return "user/update_contact_page";
        }
         return "user/update_contact_page"; 
        
    }

    @PostMapping("/update-contact")
    public String updateContact(@RequestParam("ref") String encryptedId, @Valid @ModelAttribute("contact") Contact contact, BindingResult result, HttpSession session, @RequestParam("file") MultipartFile file,
     Principal principal, Model model) throws Exception {
        if(result.hasErrors()) {
            System.out.println("error on updaete");
            session.setAttribute("message", new Message("Number not updated!", "alert-danger"));
            model.addAttribute("encryptedId", encryptedId);
            return "user/update_contact_page";
        }
       
         // Decrypt cId
        int cId = Integer.parseInt(EncryptionUtil.decrypt(encryptedId));

        Contact exisContact = contactRepository.findBycId(cId);

        // System.out.println( + exisContact);
        exisContact.setcFirstName(contact.getcFirstName());
        exisContact.setcSecondName(contact.getcSecondName());
        exisContact.setEmail(contact.getEmail());
        exisContact.setWork(contact.getWork());
        exisContact.setNumber(contact.getNumber());
        if(!file.isEmpty()) {
        exisContact.setImageUrl(file.getOriginalFilename());
        fileUploadHelper.uploadFile(file);
        } else {
            exisContact.setImageUrl(exisContact.getImageUrl());
        }
        exisContact.setDescription(contact.getDescription());

        // get user 
        User user = userRepository.findByEmail(principal.getName());
        exisContact.setUser(user);
        // for show successful message
        session.setAttribute("message", new Message("Number updated successfully!", "alert-success"));
        contactRepository.save(exisContact);
        return "user/update_contact_page";
    }
    
    // for delete contact using contact id
    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteContact(@RequestParam("ref") String encryptedId)  {
        try {
            String decrypted = EncryptionUtil.decrypt(encryptedId);
            System.out.println(encryptedId + " " + decrypted);
            int cId = Integer.parseInt(decrypted);
            Contact contact = contactRepository.findBycId(cId);
            if(contact != null) {
                contactRepository.delete(contact);

                return ResponseEntity.ok().body("user/view_contact");
            }   

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("user/view_contact");
        } catch (Exception e) {
            // TODO: handle exception
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("user/view_contact");
        }
    }


    // for profile
    @GetMapping("/profile")
    public String profile(Model model, Principal principal) {

        String email = principal.getName();

        User user = userRepository.findByEmail(email);
        model.addAttribute("user", user);
        return "user/profile";
    }

    @GetMapping("edit-profile")
    public String editProfile(Principal principal, Model model) {
        String email = principal.getName();
        User user = userRepository.findByEmail(email);

        model.addAttribute("user", user);
        return "user/edit_profile_page";
    }

   @PostMapping("update-profile")
public String updateProfile(@Valid @ModelAttribute("user") UserDto user,
                            BindingResult result,
                            Principal principal,
                            HttpSession session) {
    
    String email = principal.getName();
    User existingUser = userRepository.findByEmail(email);


    if (result.hasErrors()) {
        System.out.println("Validation Errors: " + result.getAllErrors());
        session.setAttribute("message", new Message("Update failed", "alert-danger"));
        return "user/edit_profile_page";
    }

    existingUser.setName(user.getName());
    existingUser.setEmail(user.getEmail());
    existingUser.setAbout(user.getAbout());

    // Handle image upload if new file is selected
    if (!user.getFile().isEmpty()) {
        fileUploadHelper.uploadFile(user.getFile());
        existingUser.setImageUrl(user.getFile().getOriginalFilename());
    } else {
        existingUser.setImageUrl(existingUser.getImageUrl()); // retain old image
    }

    userRepository.save(existingUser);

    session.setAttribute("message", new Message("Profile updated successfully", "alert-success"));
    return "user/edit_profile_page";
}

    
}
