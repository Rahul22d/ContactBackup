package com.smartcontact.smartcontect.config;

import java.io.IOException;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class CustomLoginFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request,
                                        HttpServletResponse response,
                                        AuthenticationException exception) throws IOException {
        String message = "Invalid username or password";

        if (exception.getMessage().toLowerCase().contains("user")) {
            message = "User not found";
        } else if (exception.getMessage().toLowerCase().contains("credentials")) {
            message = "Incorrect password";
        }

        request.getSession().setAttribute("error_message", message);
        response.sendRedirect("/logIn?error");
    }
}

