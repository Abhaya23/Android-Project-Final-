package com.example.travelnepalapp.BusinessLogic;

import android.util.Log;

import com.example.travelnepalapp.API.UserAPI;
import com.example.travelnepalapp.Models.Authtoken;
import com.example.travelnepalapp.Retrofit.RetrofitHelper;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Response;

public class Loginquery {
    String email;
    String password;
    public static String token;
    public static String username;
    public static String _id;
    public static Authtoken authtoken;

    public boolean checkUser(String email,String password) {
        boolean isloggedin = false;
        UserAPI userAPI = RetrofitHelper.instance().create(UserAPI.class);
        Call<Authtoken> usercall = userAPI.login(email, password);
        try {
            Response<Authtoken> loginresponse = usercall.execute();
            if (loginresponse.body().getSuccess().equals("Success!")) {
                token = loginresponse.body().getToken();
                username=loginresponse.body().getUsername();
                _id=loginresponse.body().get_id();
                isloggedin = true;
            }
            else {
                return isloggedin;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return isloggedin;
    }
}
