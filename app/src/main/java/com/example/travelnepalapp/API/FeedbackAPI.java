package com.example.travelnepalapp.API;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface FeedbackAPI {



    @FormUrlEncoded
    @POST("contact")
    Call<Void> addfeedback(@Field("fname") String fname,
                         @Field("lname")String lname,
                         @Field("email") String email,
                         @Field("message") String message
    );
}



