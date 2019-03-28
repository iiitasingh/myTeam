package com.ashish.myteam;

public class Events_Card {

    //byte[] ownerImg;
    String eventName,teamName,eventDesc,eventDate;

    public Events_Card(String eventName, String teamName, String eventDesc, String eventDate) {
        //this.ownerImg = ownerImg;
        this.eventName = eventName;
        this.teamName = teamName;
        this.eventDesc = eventDesc;
        this.eventDate = eventDate;
    }

//    public byte[] getOwnerImg() {
//        return ownerImg;
//    }
//
//    public void setOwnerImg(byte[] ownerImg) {
//        this.ownerImg = ownerImg;
//    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getEventDesc() {
        return eventDesc;
    }

    public void setEventDesc(String eventDesc) {
        this.eventDesc = eventDesc;
    }

    public String getEventDate() {
        return eventDate;
    }

    public void setEventDate(String eventDate) {
        this.eventDate = eventDate;
    }
}
