package com.example.travelnepalapp.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.travelnepalapp.API.UserAPI;
import com.example.travelnepalapp.Feedback.Feedback;
import com.example.travelnepalapp.API.PostAPI;
import com.example.travelnepalapp.Adapters.DashboardAdapter;
import com.example.travelnepalapp.Models.PostModel;
import com.example.travelnepalapp.Models.UserModel;
import com.example.travelnepalapp.Post.AddPost;
import com.example.travelnepalapp.R;
import com.example.travelnepalapp.Retrofit.RetrofitHelper;
import com.example.travelnepalapp.Retrofit.Url;
import com.example.travelnepalapp.Users.LoginSignup;
import com.example.travelnepalapp.Users.UpdateProfile;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    Toolbar toolbar;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    MenuItem menuItem;
    SharedPreferences preferences;
    TextView ProximitySensor, data;

    SensorManager mySensorManager;
    Sensor myProximitySensor;

    TextView navanme, navemail;
    ImageView navimage;

    private RecyclerView recyclerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.navigation_drawer);


        toolbar = findViewById(R.id.toolbar);
        menuItem = findViewById(R.id.updateprofile);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);
        navanme = navigationView.getHeaderView(0).findViewById(R.id.nav_name);
        navimage = navigationView.getHeaderView(0).findViewById(R.id.navimage);
        navemail = navigationView.getHeaderView(0).findViewById(R.id.nav_email);
        ProximitySensor=findViewById(R.id.proximityvalue);

        mySensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        myProximitySensor = mySensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        if (myProximitySensor == null) {
            ProximitySensor.setText("No Proximity Sensor!");
        } else {
            mySensorManager.registerListener(sensorEventListener, myProximitySensor, SensorManager.SENSOR_DELAY_NORMAL);
        }

//        mySensorManager.registerListener(sensorEventListener, myProximitySensor, SensorManager.SENSOR_DELAY_NORMAL);




        setSupportActionBar(toolbar);
         getSupportActionBar().setTitle("TravelNepal App");
        navigationView.setNavigationItemSelectedListener(this);


        recyclerAdapter = findViewById(R.id.rv_recyclerview);
        preferences = getSharedPreferences("localstorage", 0);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_drawer, R.string.close_drawer);
        drawerLayout.setDrawerListener(toggle);
        toggle.syncState();

        init();
        navheadear();
    }
    SensorEventListener sensorEventListener=new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            WindowManager.LayoutParams params = MainActivity.this.getWindow().getAttributes();
            if(event.sensor.getType()==Sensor.TYPE_PROXIMITY){

                if(event.values[0]==0){
                    params.flags |= WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON;
                    params.screenBrightness = 0;
                    getWindow().setAttributes(params);
                }
                else{
                    params.flags |= WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON;
                    params.screenBrightness = -1f;
                    getWindow().setAttributes(params);
                }
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };
    protected void onResume() {
        super.onResume();
        if(recyclerAdapter != null) {
            init();
            navheadear();
        }
    }

    private void init() {

        PostAPI postAPI = RetrofitHelper.instance().create(PostAPI.class);

        String token = preferences.getString("token", null);
        String username = preferences.getString("username", null);
        String id = preferences.getString("_id", null);


        Call<List<PostModel>> pListCall = postAPI.getpost(token, username, id);
        pListCall.enqueue(new Callback<List<PostModel>>() {
            @Override
            public void onResponse(Call<List<PostModel>> call, Response<List<PostModel>> response) {
//                List<PostModel> list = response.body();
                List<PostModel> list= response.body();
                Toast.makeText(MainActivity.this, "success", Toast.LENGTH_SHORT).show();
                recyclerAdapter.setAdapter(new DashboardAdapter(MainActivity.this, list));
                recyclerAdapter.setLayoutManager(new GridLayoutManager(MainActivity.this,2));


            }

            @Override
            public void onFailure(Call<List<PostModel>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Error" + t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();

            }
        });

    }
    private void StrictMode() {
        android.os.StrictMode.ThreadPolicy policy = new android.os.StrictMode.ThreadPolicy.Builder().permitAll().build();
        android.os.StrictMode.setThreadPolicy(policy);
    }
    private void navheadear(){
        UserAPI userAPI = RetrofitHelper.instance().create(UserAPI.class);
        SharedPreferences preferences = getSharedPreferences("localstorage", 0);
        String id = preferences.getString("_id", null);
        final String token = preferences.getString("token", null);
        String username = preferences.getString("username", null);

        Call<UserModel> userModelCall = userAPI.loadprofile(id, token, username);

        userModelCall.enqueue(new Callback<UserModel>() {
            @Override
            public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                UserModel userModel = response.body();
//
                navanme.setText(response.body().getName());
                navemail.setText(response.body().getEmail());

                StrictMode();
                try {

                    String imgurl = Url.URL_image + userModel.getImage();
                    URL url = new URL(imgurl);
                    navimage.setImageBitmap(BitmapFactory.decodeStream((InputStream) url.getContent()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<UserModel> call, Throwable t) {
                Toast.makeText(MainActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id = menuItem.getItemId();
        Fragment fragment = null;
        Class fragmentClass = null;

        switch (id) {
            case R.id.updateprofile:
                Intent intent = new Intent(MainActivity.this, UpdateProfile.class);
                startActivity(intent);
                break;

            case R.id.addpost:
                Intent intent1 = new Intent(MainActivity.this, AddPost.class);
                startActivity(intent1);
                break;

            case R.id.dashboard:
                Intent intent2 = new Intent(MainActivity.this, MainActivity.class);
                startActivity(intent2);
                break;
            case R.id.feedback:
                Intent intent3 = new Intent(MainActivity.this, Feedback.class);
                startActivity(intent3);
                break;
            case R.id.logout:
                SharedPreferences preferences=getSharedPreferences("localstorage",0);
                SharedPreferences.Editor editor=preferences.edit();
                editor.remove("token");
                editor.remove("username");
                editor.remove("_id");
                editor.remove("email");
                editor.remove("password");
                editor.putBoolean("loginchecker",false);
                editor.commit();

                Intent intent4 = new Intent(MainActivity.this, LoginSignup.class);
                startActivity(intent4);
                finish();
                break;
        }
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.navigation_menu, menu);

        return true;
    }
}
