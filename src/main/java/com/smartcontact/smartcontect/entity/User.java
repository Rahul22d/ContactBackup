package com.smartcontact.smartcontect.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity 
public class User {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank(message = "Name must be filled!")
    @Size(min = 3, message = "Name must be minimum 3 character!")
    private String name;

    @NotBlank(message = "Email must be filled!")
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message="Please enter valid email address")
    @Column(unique = true)
    private String email;

    @NotBlank
    private String password;

    private String imageUrl;

    @Column(length = 500)
    private String about;

    private String role;

    @AssertTrue(message = "Before submit please accept term and condition!")
    private boolean enable;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Contact> Contacts; 

    public User(){}
    

    public User(int id, String name, String email, String password, String imageUrl, String about, String role,
            boolean enable) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.imageUrl = imageUrl;
        this.about = about;
        this.role = role;
        this.enable = enable;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }


    public List<Contact> getContacts() {
        return Contacts;
    }


    public void setContacts(List<Contact> contacts) {
        Contacts = contacts;
    }


    // @Override
    // public String toString() {
    //     return "User [id=" + id + ", name=" + name + ", email=" + email + ", password=" + password + ", imageUrl="
    //             + imageUrl + ", about=" + about + ", role=" + role + ", enable=" + enable + ", Contacts=" + Contacts
    //             + ", getId()=" + getId() + ", getName()=" + getName() + ", getEmail()=" + getEmail()
    //             + ", getPassword()=" + getPassword() + ", getImageUrl()=" + getImageUrl() + ", getAbout()=" + getAbout()
    //             + ", getRole()=" + getRole() + ", isEnable()=" + isEnable() + ", getContacts()=" + getContacts()
    //             + ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()=" + super.toString()
    //             + "]";
    // }


    
    
   
}
