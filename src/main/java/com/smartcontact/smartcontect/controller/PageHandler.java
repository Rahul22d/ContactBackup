package com.smartcontact.smartcontect.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.smartcontact.smartcontect.entity.User;
import com.smartcontact.smartcontect.helper.Message;
import com.smartcontact.smartcontect.repository.UserRepository;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;




@Controller
public class PageHandler {

    @Autowired 
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;
    
    @GetMapping("/") 
    public String homePage(Model model) {
        model.addAttribute("title", "Home");
        return "home";
    }

    @GetMapping("/logIn")
    public String logInPage(Model model, HttpServletRequest request) {
        model.addAttribute("title", "logIn");
        String errorMessage = (String) request.getSession().getAttribute("error_message");
        model.addAttribute("error_message", errorMessage);
        return "login";
    }

    @GetMapping("/signUp")
    public String signUpPage(Model model, HttpSession session) {
        model.addAttribute("title", "sigUp");

        // Message message = (Message) session.getAttribute("message");
        // if (message != null) {
        //     model.addAttribute("message", message);
        //     session.removeAttribute("message");
        // }

        model.addAttribute("user", new User());
        return "signup";
    }

    @GetMapping("/about")
    public String aboutPage(Model model) {
        model.addAttribute("title", "About");
        return "about";
    }

    //  For register user
    @PostMapping("/register")
    public String register(@Valid @ModelAttribute("user") User user, BindingResult bindingResult, @RequestParam(value = "enable", defaultValue = "false") boolean enable,
        Model model, HttpSession session) {
        try {

            if(bindingResult.hasErrors()){
                return "signup";
            }
            
            if(!enable) {
                throw new Exception("Accpet the term and condition!");
            }

            System.out.println("agreement " + enable);

            user.setRole("ROLE_USER");
            user.setImageUrl(null);
            user.setPassword(passwordEncoder.encode(user.getPassword()));

            User useResult = userRepository.save(user);
            System.out.println(useResult);

            session.setAttribute("message", new Message("Successfully register", "alert-success"));

            return "signup";

        } catch (DataIntegrityViolationException e) {
            session.setAttribute("message", new Message("Email already registered!", "alert-danger"));
            return "signup";
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            session.setAttribute("message", new Message("Something went wrong ", "alert-danger"));
            return "signup";
        } 
        
            
        // return "signup";
    }

       

    
}
