package com.ashish.myteam;

public class Contri_Status_details {

    private Long ID;
    String name;
    private boolean status;

    private Contri_Status_details(Long ID, String name, boolean status) {
        this.name = name;
        this.ID = ID;
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private long getID() {
        return ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }

    private boolean isStatus() {
        return status;
    }

    private void setStatus(boolean status) {
        this.status = status;
    }
}