package com.ashish.myteam;

import java.io.Serializable;

public class User implements Serializable {

    Long Uid;
    String Uemail,Uaadhaar,Uname,Unick_name,Uteam,Udob,Ufood,Umobile,Ubgrp,UpanNo,Uevents,Utransaction,Uabout,Udesignation,Uhobbies;
    byte[] Uimage;

    public User()
    {
    }
    public User(Long id, String uemail, String uaadhaar, String uname, String unick_name, String uteam, String udob, String ufood, String umobile, String ubgrp, String upanNo, String uevents, byte[] uimage, String utransaction, String uabout, String userDesignation, String hobbies) {
        this.Uid = id;
        this.Uemail = uemail;
        this.Uaadhaar = uaadhaar;
        this.Uname = uname;
        this.Unick_name = unick_name;
        this.Uteam = uteam;
        this.Udob = udob;
        this.Ufood = ufood;
        this.Umobile = umobile;
        this.Ubgrp = ubgrp;
        this.UpanNo = upanNo;
        this.Uevents = uevents;
        this.Uimage = uimage;
        this.Utransaction = utransaction;
        this.Uabout = uabout;
        this.Udesignation = userDesignation;
        this.Uhobbies = hobbies;
    }

    public void setId(Long id) {
        this.Uid = id;
    }

    public void setUemail(String uemail) {
        Uemail = uemail;
    }

    public void setUaadhaar(String uaadhaar) {
        Uaadhaar = uaadhaar;
    }

    public void setUname(String uname) {
        Uname = uname;
    }

    public void setUnick_name(String unick_name) {
        Unick_name = unick_name;
    }

    public void setUteam(String uteam) {
        Uteam = uteam;
    }

    public void setUdob(String udob) {
        Udob = udob;
    }

    public void setUfood(String ufood) {
        Ufood = ufood;
    }

    public void setUmobile(String umobile) {
        Umobile = umobile;
    }

    public void setUbgrp(String ubgrp) {
        Ubgrp = ubgrp;
    }

    public void setUpanNo(String upanNo) {
        UpanNo = upanNo;
    }

    public void setUevents(String uevents) {
        Uevents = uevents;
    }

    public void setUimage(byte[] uimage) {
        Uimage = uimage;
    }

    public Long getId() {
        return Uid;
    }

    public String getUemail() {
        return Uemail;
    }

    public String getUaadhaar() {
        return Uaadhaar;
    }

    public String getUname() {
        return Uname;
    }

    public String getUnick_name() {
        return Unick_name;
    }

    public String getUteam() {
        return Uteam;
    }

    public String getUdesignation() {
        return Udesignation;
    }

    public void setUdesignation(String udesignation) {
        Udesignation = udesignation;
    }

    public String getUhobbies() {
        return Uhobbies;
    }

    public void setUhobbies(String uhobbies) {
        Uhobbies = uhobbies;
    }

    public String getUdob() {
        return Udob;
    }

    public String getUfood() {
        return Ufood;
    }

    public String getUmobile() {
        return Umobile;
    }

    public String getUbgrp() {
        return Ubgrp;
    }

    public String getUpanNo() {
        return UpanNo;
    }

    public String getUevents() {
        return Uevents;
    }

    public byte[] getUimage() {
        return Uimage;
    }

    public String getUtransaction() {
        return Utransaction;
    }

    public void setUtransaction(String utransaction) {
        Utransaction = utransaction;
    }

    public String getUabout() {
        return Uabout;
    }

    public void setUabout(String uabout) {
        Uabout = uabout;
    }
}
