package com.smartcontact.smartcontect.entity;

import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Size;

@Entity
public class Contact {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int cId;

    @NotBlank(message = "First name nust be filled!")
    private String cFirstName;

    private String cSecondName;

    // @Column(unique = true)
    
    private String email;

    private String work;

    @NotBlank(message = "Number must be filled!")
    @Size(min = 10, max=10, message = "Please enter 10 digit !")
    private String number;

    private String imageUrl;

    @Column(length = 50)
    @Size(max=15, message = "description max length is 50 charater")
    private String description;

    @ManyToOne
    @JsonBackReference
    private User user;

    @Transient
    private MultipartFile file;

    public Contact(){}

    public Contact(int cId, String cFirstName, String cSecondName, String email, String work, String number,
            String imageUrl, String description, MultipartFile file) {
        this.cId = cId;
        this.cFirstName = cFirstName;
        this.cSecondName = cSecondName;
        this.email = email;
        this.work = work;
        this.number = number;
        this.imageUrl = imageUrl;
        this.description = description;
        this.file = file;
    }

    public int getcId() {
        return cId;
    }

    public void setcId(int cId) {
        this.cId = cId;
    }

    public String getcFirstName() {
        return cFirstName;
    }

    public void setcFirstName(String cFirstName) {
        this.cFirstName = cFirstName;
    }

    public String getcSecondName() {
        return cSecondName;
    }

    public void setcSecondName(String cSecondName) {
        this.cSecondName = cSecondName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWork() {
        return work;
    }

    public void setWork(String work) {
        this.work = work;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }
    

}
