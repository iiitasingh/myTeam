package com.ashish.myteam;

public class teams {

    byte[] image;
    String name;
    String email;

    public teams(byte[] image, String name ,String email) {
        this.image = image;
        this.name = name;
        this.email = email;
    }

    public byte[] getImage() {
        return image;
    }

    public String getName() {
        return name;
    }

    public void setImage(byte[] image) {
        this.image = image;
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
}
