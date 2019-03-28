package com.ashish.myteam;

public class BirthdayList {

    String DOB;
    String name;

    public BirthdayList(String name,String dob) {
        this.DOB = dob;
        this.name = name;
    }

    public String getDOB() {
        return DOB;
    }

    public void setDOB(String dob) {
        this.DOB = dob;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
