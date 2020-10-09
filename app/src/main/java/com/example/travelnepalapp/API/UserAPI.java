package com.example.travelnepalapp.API;

import com.example.travelnepalapp.Models.UserModel;
import com.example.travelnepalapp.Models.Authtoken;
import com.example.travelnepalapp.Retrofit.RetrofitHelper;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface UserAPI {

    @POST("users/registration")
    Call<Void> addUser(@Body UserModel userModel);

    @FormUrlEncoded
    @POST("users/login")
    Call<Authtoken> login(@Field("email") String emails, @Field("password") String pass);


    @Multipart
    @POST("users/upload")
    Call<String> uploadImage(@Part MultipartBody.Part body);

    @FormUrlEncoded
    @PUT("users/userupdate")
    Call<String> updateprofle(@Field("_id") String id,
                                 @Field("token") String token,
                                 @Field("username") String username,
                                 @Field("name") String name,
                                 @Field("image") String image,
                                 @Field("email") String email
                                 );
    @FormUrlEncoded
    @PUT("users/userupdate")
    Call<String> updateproflewithoutimage(@Field("_id") String id,
                              @Field("token") String token,
                              @Field("username") String username,
                              @Field("name") String name,
                              @Field("email") String email
    );


    @FormUrlEncoded
    @POST("users/profile")
    Call<UserModel> loadprofile(@Field("_id") String id,
                                @Field("token") String token,
                                @Field("username") String username);


}
