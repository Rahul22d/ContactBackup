package com.smartcontact.smartcontect.helper;

public class Message {

    private String context;

    private String type;

    public Message(String context, String type) {
        this.context = context;
        this.type = type;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    
    
}
