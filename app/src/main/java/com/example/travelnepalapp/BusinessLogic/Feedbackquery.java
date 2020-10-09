package com.example.travelnepalapp.BusinessLogic;

import android.util.Log;

import com.example.travelnepalapp.API.FeedbackAPI;
import com.example.travelnepalapp.API.UserAPI;
import com.example.travelnepalapp.Feedback.Feedback;
import com.example.travelnepalapp.Models.UserModel;
import com.example.travelnepalapp.Retrofit.RetrofitHelper;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Response;

public class Feedbackquery {

    public Boolean addfeedback(String fname,String lname,String email,String message){
        Boolean b=false;

        FeedbackAPI feedbackAPI = RetrofitHelper.instance().create(FeedbackAPI.class);
        Call<Void> feedbackcall= feedbackAPI.addfeedback(fname,lname,email,message);

        try{

            Response<Void> feedbackres=feedbackcall.execute();
            if(feedbackres.isSuccessful()){
                b=true;
            }

        }catch (IOException e) {
            e.printStackTrace();

        }
        return b;
    }


}
