package com.smartcontact.smartcontect.DTO;

import org.springframework.web.multipart.MultipartFile;

import com.smartcontact.smartcontect.entity.User;


public class ContactForm {
    
    private int cId;

    private String cFirstName;

    private String cSecondName;

    private String email;

    private String work;

    private String number;

    private MultipartFile imageUrl;

    private String description;

    private User user;

    public ContactForm(){}

    public ContactForm(int cId, String cFirstName, String cSecondName, String email, String work, String number,
           MultipartFile imageUrl, String description) {
        this.cId = cId;
        this.cFirstName = cFirstName;
        this.cSecondName = cSecondName;
        this.email = email;
        this.work = work;
        this.number = number;
        this.imageUrl = imageUrl;
        this.description = description;
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

    public MultipartFile getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(MultipartFile imageUrl) {
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

    
    

}

