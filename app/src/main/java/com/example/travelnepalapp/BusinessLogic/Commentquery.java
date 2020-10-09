package com.example.travelnepalapp.BusinessLogic;

import com.example.travelnepalapp.API.CommentAPI;
import com.example.travelnepalapp.API.FeedbackAPI;
import com.example.travelnepalapp.Retrofit.RetrofitHelper;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Response;

public class Commentquery {



    public Boolean addcomment(String postid,String comment,String userid,String token,String id){
        Boolean com=false;

        CommentAPI commentAPI = RetrofitHelper.instance().create(CommentAPI.class);
        Call<Void> commentcall= commentAPI.addcomment(postid,comment,userid,token,id);

        try{

            Response<Void> response=commentcall.execute();
            if(response.isSuccessful()){
                com=true;
            }

        }catch (IOException e) {
            e.printStackTrace();

        }
        return com;
    }


}
