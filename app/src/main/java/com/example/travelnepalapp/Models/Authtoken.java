package com.example.travelnepalapp.Models;

public class Authtoken
{
    private  String Success;
    private String message;
    private String token;
    private String username;
    private String _id;
    private String admin;
    private boolean loginchecker;



    public String getAdmin() {
        return admin;
    }

    public void setAdmin(String admin) {
        this.admin = admin;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public boolean isLoginchecker() {
        return loginchecker;
    }

    public void setLoginchecker(boolean loginchecker) {
        this.loginchecker = loginchecker;
    }

    public String getSuccess() {
        return Success;
    }

    public String getMessage() {
        return message;
    }

    public String getToken() {
        return token;
    }
    public String getUsername() {
        return username;
    }


    public void setSuccess(String success) {
        Success = success;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setUsername(String username) {
        this.username = username;
    }


}
