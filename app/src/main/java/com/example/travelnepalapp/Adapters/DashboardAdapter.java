package com.example.travelnepalapp.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.travelnepalapp.Models.PostModel;
import com.example.travelnepalapp.Post.PostDashboard;
import com.example.travelnepalapp.R;
import com.example.travelnepalapp.Retrofit.Url;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

public class DashboardAdapter extends RecyclerView.Adapter<DashboardAdapter.ViewHolder> {
    private Context context;
    private List<PostModel> postmodellist;

    public DashboardAdapter(Context context, List<PostModel> postmodellist) {
        this.context = context;
        this.postmodellist = postmodellist;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View rootView = LayoutInflater.from(context).inflate(R.layout.dashboardlayout, viewGroup, false);

        return new ViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        final PostModel postmodel = postmodellist.get(i);
        viewHolder.tv_location.setText(postmodel.getLocation());
        viewHolder.tv_title.setText(postmodel.getTitle());
        StrictMode();
        try {
            String imgurl = Url.URL_image + postmodel.getImage();
            URL url = new URL(imgurl);
            viewHolder.iv_image.setImageBitmap(BitmapFactory.decodeStream((InputStream) url.getContent()));
//                    imagedash.setImageResource(BitmapFactory.decodeStream((InputStream)url.getContent()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        viewHolder.iv_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(context, PostDashboard.class);
                intent.putExtra("post_id",postmodel.get_id());
                intent.putExtra("user_id",postmodel.getUser());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return postmodellist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView iv_image;
        private TextView tv_location;
        private TextView tv_title;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_image = itemView.findViewById(R.id.iv_mainimage);
            tv_location = itemView.findViewById(R.id.tv_location);
            tv_title=itemView.findViewById(R.id.tv_title);


        }
    }
    private void StrictMode() {
        android.os.StrictMode.ThreadPolicy policy = new android.os.StrictMode.ThreadPolicy.Builder().permitAll().build();
        android.os.StrictMode.setThreadPolicy(policy);
    }
}
