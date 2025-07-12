package com.smartcontact.smartcontect.DTO;

import org.springframework.web.multipart.MultipartFile;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class UserDto {

    @NotBlank(message = "Name must be filled!")
    @Size(min = 3, message = "Name must be minimum 3 character!")
    String name;

    @NotBlank(message = "Email must be filled!")
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message="Please enter valid email address")
    @Column(unique = true)
    String email;

     @Column(length = 500)
     String about;

    MultipartFile file;

    public UserDto() {
    }

    public UserDto(
            @NotBlank(message = "Name must be filled!") @Size(min = 3, message = "Name must be minimum 3 character!") String name,
            @NotBlank(message = "Email must be filled!") @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = "Please enter valid email address") String email,
            String about, MultipartFile file) {
        this.name = name;
        this.email = email;
        this.about = about;
        this.file = file;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }

    
}
