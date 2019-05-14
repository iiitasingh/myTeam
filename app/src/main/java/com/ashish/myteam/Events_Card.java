package com.ashish.myteam;

public class Events_Card {

    String eventName, teamName, eventDesc, eventDate, eventContri, eventMembers, creditMemb;
    Long eventId, contriAmt, spentAmt, remAmt, approxContri;

    public Events_Card(String eventName, String teamName, String eventDesc, String eventDate, String eventContri, Long eventId, String eventMembers, Long contri, Long spent,Long rem, Long approx, String crMems) {
        //this.ownerImg = ownerImg;
        this.eventName = eventName;
        this.teamName = teamName;
        this.eventDesc = eventDesc;
        this.eventDate = eventDate;
        this.eventContri = eventContri;
        this.eventId = eventId;
        this.eventMembers = eventMembers;
        this.contriAmt = contri;
        this.spentAmt = spent;
        this.remAmt = rem;
        this.approxContri = approx;
        this.creditMemb = crMems;
    }

    public String getEventContri() {
        return eventContri;
    }

    public void setEventContri(String eventContri) {
        this.eventContri = eventContri;
    }

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

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

    public String getEventMembers() {
        return eventMembers;
    }

    public void setEventMembers(String eventMembers) {
        this.eventMembers = eventMembers;
    }

    public Long getContriAmt() {
        return contriAmt;
    }

    public void setContriAmt(Long contriAmt) {
        this.contriAmt = contriAmt;
    }

    public Long getSpentAmt() {
        return spentAmt;
    }

    public void setSpentAmt(Long spentAmt) {
        this.spentAmt = spentAmt;
    }

    public Long getRemAmt() {
        return remAmt;
    }

    public void setRemAmt(Long remAmt) {
        this.remAmt = remAmt;
    }

    public Long getApproxContri() {
        return approxContri;
    }

    public void setApproxContri(Long approxContri) {
        this.approxContri = approxContri;
    }

    public String getCreditMemb() {
        return creditMemb;
    }

    public void setCreditMemb(String creditMemb) {
        this.creditMemb = creditMemb;
    }
}
