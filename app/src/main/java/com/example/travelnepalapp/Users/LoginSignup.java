package com.example.travelnepalapp.Users;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.travelnepalapp.Adapters.LoginSignupAdapter;
import com.example.travelnepalapp.Fragments.LoginFragment;
import com.example.travelnepalapp.Fragments.SignupFragment;
import com.example.travelnepalapp.R;

public class LoginSignup extends AppCompatActivity {
    ViewPager viewPager;
    TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_signup);
        viewPager = findViewById(R.id.viewPager);
        tabLayout = findViewById(R.id.tablayout);

        LoginSignupAdapter adapter = new LoginSignupAdapter(getSupportFragmentManager());
        adapter.addFragment(new LoginFragment(), "Login");
        adapter.addFragment(new SignupFragment(), "Register");

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);


    }
}
