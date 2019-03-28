package com.ashish.myteam;

public class teams {

    byte[] image;
    String name;

    public teams(byte[] image, String name) {
        this.image = image;
        this.name = name;
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
}
