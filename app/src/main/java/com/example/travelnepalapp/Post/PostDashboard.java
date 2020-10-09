package com.example.travelnepalapp.Post;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.travelnepalapp.API.PostAPI;
import com.example.travelnepalapp.Comment.AddComment;
import com.example.travelnepalapp.Models.PostModel;
import com.example.travelnepalapp.Models.UserModel;
import com.example.travelnepalapp.R;
import com.example.travelnepalapp.Retrofit.RetrofitHelper;
import com.example.travelnepalapp.Retrofit.Url;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostDashboard extends AppCompatActivity implements View.OnClickListener {

    TextView posttitle, postlocation, postdesc, postusername;
    ImageView postimageview, postuserimage;
    FloatingActionButton btncomment;
    String post_id;
    String userid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_dashboard);
        posttitle = findViewById(R.id.tv_pc_title);
        postdesc = findViewById(R.id.tv_pc_desc);
        postlocation = findViewById(R.id.tv_pc_location);
        postusername = findViewById(R.id.tv_post_user_name);

        postimageview = findViewById(R.id.iv_pc_post_image);
        postuserimage = findViewById(R.id.iv_pc_user_image);

        btncomment = findViewById(R.id.btn_post_comment);

        btncomment.setOnClickListener(this);

        init();
        postuserdata();
    }

    private void StrictMode() {
        android.os.StrictMode.ThreadPolicy policy = new android.os.StrictMode.ThreadPolicy.Builder().permitAll().build();
        android.os.StrictMode.setThreadPolicy(policy);
    }

    private void init() {
        String id = getIntent().getStringExtra("post_id");
        final PostAPI postAPI = RetrofitHelper.instance().create(PostAPI.class);
        Call<PostModel> postModelCall = postAPI.getpostdetail(id);
        postModelCall.enqueue(new Callback<PostModel>() {
            @Override
            public void onResponse(Call<PostModel> call, Response<PostModel> response) {
                posttitle.setText(response.body().getTitle());
                postdesc.setText(response.body().getDescription());
                postlocation.setText(response.body().getLocation());
                post_id= response.body().get_id();
                StrictMode();
                try {
                    String imgurl = Url.URL_image + response.body().getImage();
                    URL url = new URL(imgurl);
                    postimageview.setImageBitmap(BitmapFactory.decodeStream((InputStream) url.getContent()));

                } catch (IOException e) {
                    Toast.makeText(PostDashboard.this, "Errorimage ma", Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onFailure(Call<PostModel> call, Throwable t) {
                Toast.makeText(PostDashboard.this, "Error" + t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();

            }

        });
    }

    private void postuserdata() {
        final PostAPI postAPI = RetrofitHelper.instance().create(PostAPI.class);
        userid = getIntent().getStringExtra("user_id");
        Call<UserModel> userModelCall = postAPI.getuserdetial(userid);
        userModelCall.enqueue(new Callback<UserModel>() {
            @Override
            public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                postusername.setText(response.body().getUsername());
                StrictMode();
                try {
                    String imgurl = Url.URL_image + response.body().getImage();
                    URL url = new URL(imgurl);
                    postuserimage.setImageBitmap(BitmapFactory.decodeStream((InputStream) url.getContent()));

                } catch (IOException e) {
                    Toast.makeText(PostDashboard.this, "Errorimage ma", Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onFailure(Call<UserModel> call, Throwable t) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_post_comment:

                Intent intent= new Intent(this, AddComment.class);
                intent.putExtra("post_id",post_id);
                intent.putExtra("user_id",userid);
                Log.d("postid",post_id);
                startActivity(intent);

                break;
        }

    }
}
