package com.example.travelnepalapp.Comment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Vibrator;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.travelnepalapp.API.CommentAPI;
import com.example.travelnepalapp.Adapters.CommentAdapter;
import com.example.travelnepalapp.BusinessLogic.Commentquery;
import com.example.travelnepalapp.Models.CommentModel;
import com.example.travelnepalapp.R;
import com.example.travelnepalapp.Retrofit.RetrofitHelper;
import com.example.travelnepalapp.Activities.StrictMode;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddComment extends AppCompatActivity implements View.OnClickListener {
    private RecyclerView recyclerAdapter;
    private List<CommentModel> commentModelList = new ArrayList<>();
    EditText etcomment;
    FloatingActionButton btncomment;
    String userid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_comment);
        recyclerAdapter = findViewById(R.id.rv_comment_recylerview);

        etcomment=findViewById(R.id.et_comment_text);
        btncomment=findViewById(R.id.btn_comment_add);

        btncomment.setOnClickListener(this);



        init();
    }
    protected void onResume() {
        super.onResume();
        if(recyclerAdapter != null) {
            init();

        }
    }

    private void init() {
        final CommentAPI commentAPI = RetrofitHelper.instance().create(CommentAPI.class);
        final String id = getIntent().getStringExtra("post_id");
        Call<List<CommentModel>> listCall = commentAPI.getallcomment(id);
        listCall.enqueue(new Callback<List<CommentModel>>() {
            @Override
            public void onResponse(Call<List<CommentModel>> call, final Response<List<CommentModel>> responses) {
                commentModelList = responses.body();
                recyclerAdapter.setAdapter(new CommentAdapter(AddComment.this, commentModelList));
                recyclerAdapter.setLayoutManager(new LinearLayoutManager(AddComment.this));
//                JSONObject reader = new JSONObject();
//                JsonObject jsonObject= reader.getJSONObject("comment");

////                jsonObject=responses.body();
//                String[] user =new String[commentModelList.size()];
//                for(int i=0; i<commentModelList.size(); i++){
//                    user[i]=commentModelList.get(i).getUser();
//                    Log.d("userid",user[i]);
//                    Call<UserModel> userModelCalls=commentAPI.getuserdetial(user[i]);
//                    userModelCalls.enqueue(new Callback<UserModel>() {
//                        @Override
//                        public void onResponse(Call<UserModel> call, Response<UserModel> response) {
//                            UserModel userModel =response.body();
//                            recyclerAdapter.setAdapter(new CommentAdapter(AddComment.this, commentModelList,userModel));
//                            recyclerAdapter.setLayoutManager(new LinearLayoutManager(AddComment.this));
//
//                        }
//
//                        @Override
//                        public void onFailure(Call<UserModel> call, Throwable t) {
//                            Toast.makeText(AddComment.this, "Error"+t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
//
//                        }
//                    });
//                }



            }

            @Override
            public void onFailure(Call<List<CommentModel>> call, Throwable t) {

                Toast.makeText(AddComment.this, "Error" + t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_comment_add:
               addcomment();
               onResume();
                break;
        }

    }

    private void addcomment() {
        final String postid = getIntent().getStringExtra("post_id");
        String comment= etcomment.getText().toString();
        SharedPreferences preferences=getSharedPreferences("localstorage",0);
        final String user=preferences.getString("_id",null);
        final String token=preferences.getString("token",null);
        final String id=preferences.getString("_id",null);

        Commentquery commentquery=new Commentquery();
        StrictMode.StrictMode();
        if(commentquery.addcomment(postid,comment,user,token,id)==false){
            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
            Vibrator vibe = (Vibrator) AddComment.this.getSystemService(Context.VIBRATOR_SERVICE);
            vibe.vibrate(100);
        }
        else {
            Toast.makeText(this, "Added Comment", Toast.LENGTH_SHORT).show();
            etcomment.setText("");
            
        }
    }

//    private void addcomment() {
//        CommentAPI commentAPI=RetrofitHelper.instance().create(CommentAPI.class);
//        final String postid = getIntent().getStringExtra("post_id");
//        String comment= etcomment.getText().toString();
//        SharedPreferences preferences=getSharedPreferences("localstorage",0);
//        final String user=preferences.getString("_id",null);
//        final String token=preferences.getString("token",null);
//        final String id=preferences.getString("_id",null);
//
//        Call<String> commentadd=commentAPI.addcomment(postid,comment,user,token,id);
//        commentadd.enqueue(new Callback<String>() {
//            @Override
//            public void onResponse(Call<String> call, Response<String> response) {
////                        Log.d("comment",response.body());
//                Toast.makeText(AddComment.this, "Successfull added", Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onFailure(Call<String> call, Throwable t) {
//                Toast.makeText(AddComment.this, "Eroor"+t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
//
//            }
//        });
//    }
}
