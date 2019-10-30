package com.thorproject.cloudchat;

public class MyMessage {



    String message;
    String nickname;
    String date;
    int personalId;


    public MyMessage(String message, String nickname, int personalId, String date) {
        this.message = message;
        this.nickname = nickname;
        this.personalId = personalId;
        this.date = date;
    }

    public int getPersonalId() { return personalId; }
    public void setPersonalId(int personalId) { this.personalId = personalId; }
    public String getMessage() { return message; }
    public MyMessage(){ }
    public void setMessage(String message) { this.message = message; }
    public String getNickname() { return nickname; }
    public void setNickname(String nick) { this.nickname = nick; }
    public String getDate() { return date;}
    public void setDate(String date) { this.date = date; }
}
