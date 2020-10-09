package com.example.travelnepalapp.Adapters;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.travelnepalapp.Models.CommentModel;
import com.example.travelnepalapp.Models.UserModel;
import com.example.travelnepalapp.R;
import com.example.travelnepalapp.Retrofit.Url;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder>{

    private Context context;
    private List<CommentModel> commentList;
//    private UserModel userModels;

    public CommentAdapter(Context context, List<CommentModel> commentList) {
        this.context = context;
        this.commentList = commentList;
    }

    @NonNull
    @Override
    public CommentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View rootView = LayoutInflater.from(context).inflate(R.layout.recycler_comment, viewGroup, false);

        return new ViewHolder(rootView);
    }
    private void StrictMode() {
        android.os.StrictMode.ThreadPolicy policy = new android.os.StrictMode.ThreadPolicy.Builder().permitAll().build();
        android.os.StrictMode.setThreadPolicy(policy);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentAdapter.ViewHolder viewHolder, int i) {
        final CommentModel commentModel= commentList.get(i);
        viewHolder.review.setText(commentModel.getComment());
        viewHolder.username.setText(commentModel.getUser().getUsername());
        StrictMode();
        try {
            String imgurl = Url.URL_image + commentModel.getUser().getImage();
            URL url = new URL(imgurl);
            viewHolder.userimage.setImageBitmap(BitmapFactory.decodeStream((InputStream) url.getContent()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return commentList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView userimage;
        private TextView review,username;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            userimage= itemView.findViewById(R.id.rc_comment_image);
            review= itemView.findViewById(R.id.rc_comment_review);
            username= itemView.findViewById(R.id.rc_comment_username);

        }
    }
}
