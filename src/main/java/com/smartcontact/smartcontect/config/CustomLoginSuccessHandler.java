package com.smartcontact.smartcontect.config;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class CustomLoginSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                         Authentication authentication) throws IOException {
        // Redirect based on role
        authentication.getAuthorities().forEach(authority -> {
            try {
                if (authority.getAuthority().equals("ROLE_ADMIN")) {
                    response.sendRedirect("/admin/admin_dashboard");
                } else if (authority.getAuthority().equals("ROLE_USER")) {
                    response.sendRedirect("/user"); // auto trigure /user request on url
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

   
}
