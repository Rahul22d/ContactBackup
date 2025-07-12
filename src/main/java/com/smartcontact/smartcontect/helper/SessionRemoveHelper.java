package com.smartcontact.smartcontect.helper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jakarta.servlet.http.HttpSession;

@Component
public class SessionRemoveHelper {
    public void removeSession() {
        try{

            HttpSession session = ((ServletRequestAttributes )RequestContextHolder.getRequestAttributes()).getRequest().getSession();
            session.removeAttribute("message");
        } catch(Exception e) {
            e.printStackTrace();
        } 
        
    }
}

