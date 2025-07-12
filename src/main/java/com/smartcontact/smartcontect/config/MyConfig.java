package com.smartcontact.smartcontect.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;



@Configuration
@EnableWebSecurity
public class MyConfig {

    // inject CustomLoginSuccessHandler 
    @Autowired
    private CustomLoginSuccessHandler customLoginSuccessHandler;

   @Bean
   public UserDetailServiceImpl userDetailService() {
    return new UserDetailServiceImpl();
   }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();

        daoAuthenticationProvider.setUserDetailsService(this.userDetailService());
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());

        return daoAuthenticationProvider;

    }

    // for manage authentcation
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder = 
            http.getSharedObject(AuthenticationManagerBuilder.class);
        
        authenticationManagerBuilder.authenticationProvider(daoAuthenticationProvider());
        
        return authenticationManagerBuilder.build();
    }

    /*
     * this is for control user or admin pannel 
     * manage authorization 
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/admin/**").hasRole("ADMIN")
                .requestMatchers("/user/**").hasRole("USER")
                .requestMatchers("/delete_contact/**").hasAnyRole("USER", "ADMIN")
                .requestMatchers("/**").permitAll()
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/logIn")
                .loginProcessingUrl("/logIn")
            .successHandler(customLoginSuccessHandler)  // ✅ Use success handler
                .failureHandler(customLoginFailureHandler()) // use custom handler
                .permitAll()
            )
            // .logout(logout -> logout
            //     .permitAll()
            // );
            .logout(logout -> logout
            .logoutUrl("/logout") // URL to trigger logout
            .logoutRequestMatcher(new AntPathRequestMatcher("/logout", "GET"))
            .logoutSuccessUrl("/") // Redirect after logout on home page {"/logIn"} pass here so after logout goto login page
            .invalidateHttpSession(true)
            .clearAuthentication(true)
            .permitAll()
        );

        return http.build();
    }

    @Bean
    public AuthenticationFailureHandler customLoginFailureHandler() {
        return new CustomLoginFailureHandler();
    }

     
}
