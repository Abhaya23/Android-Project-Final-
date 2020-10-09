package com.example.travelnepalapp.BusinessLogic;

import android.util.Log;

import com.example.travelnepalapp.API.UserAPI;
import com.example.travelnepalapp.Models.UserModel;
import com.example.travelnepalapp.Retrofit.RetrofitHelper;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Response;

public class Registerquery {
    String success;

    UserModel userModel;
//    public Registerquery(UserModel userModel){
//
//        this.userModel=userModel;
//    }
    public Boolean register(UserModel userModel){
        Boolean a=false;
        UserAPI userAPI = RetrofitHelper.instance().create(UserAPI.class);
        Call<Void> registercall=userAPI.addUser(userModel);
        try{

            Response<Void> registerres=registercall.execute();
            if(registerres.isSuccessful()){
                a=true;
            }


        }catch (IOException e) {
            e.printStackTrace();

        }
        return a;
    }



}
