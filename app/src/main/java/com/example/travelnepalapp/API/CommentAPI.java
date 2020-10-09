package com.example.travelnepalapp.API;

import com.example.travelnepalapp.Models.CommentModel;
import com.example.travelnepalapp.Models.PostModel;
import com.example.travelnepalapp.Models.UserModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface CommentAPI {

    @GET("ccommentbyid/{id}")
    Call<List<CommentModel>> getallcomment(@Path("id") String id);

    @POST("userbyid/{id}")
    Call<UserModel> getuserdetial(@Path("id") String id);

    @FormUrlEncoded
    @POST("users/comment")
    Call<Void> addcomment(@Field("post_id") String post_id,
                            @Field("comment") String comment,
                            @Field("user") String userid,
                            @Field("token") String token,
                            @Field("_id") String id
    );
}