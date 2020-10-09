package com.example.travelnepalapp.Fragments;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.travelnepalapp.BusinessLogic.Loginquery;
import com.example.travelnepalapp.Activities.MainActivity;
import com.example.travelnepalapp.Models.Authtoken;
import com.example.travelnepalapp.R;

import java.util.regex.Pattern;


/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment implements View.OnClickListener {
    String mail = "";
    String pass = "";
    Button signup, login;
    EditText email, password;
    Boolean isloggedin;
    SharedPreferences.Editor editor;
    SharedPreferences preferences;
    Loginquery lq;
    Authtoken authtoken;
    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^" +
                    "(?=.*[0-9])" + //at least 1 digit
                    "(?=.*[a-z])" + //at least 1 lower case letter
                    "(?=.*[A-Z])" +  //at least 1 upper case Letter
                    "(?=\\S+$)" +   //no white spaces
                    ".{8,}" + //at least 8 characters
                    "$");


    public LoginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        email = view.findViewById(R.id.et_emaillogin);
        password = view.findViewById(R.id.et_password);

        signup = view.findViewById(R.id.btn_signup);
        login = view.findViewById(R.id.btn_login);

        login.setOnClickListener(this);
        signup.setOnClickListener(this);
        preferences = getActivity().getSharedPreferences("localstorage", 0);
        editor = preferences.edit();
        return view;

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:
//                checkUser();
                if (loginvalidation()) {
                    String emails = email.getText().toString();
                    String pass = password.getText().toString();
                    Loginquery login = new Loginquery();
                    StrictMode();
                    if (login.checkUser(emails, pass) == true) {
                        editor.putString("username", Loginquery.username);
                        editor.putString("token", Loginquery.token);
                        editor.putString("_id", Loginquery._id);
                        editor.putString("email", emails);
                        isloggedin = true;
                        editor.putBoolean("loginchecker", isloggedin);
                        editor.commit();

                        Intent intent = new Intent(getActivity(), MainActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
//                        Vibrator vibrator=(Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);
//                        vibrator.vibrate(100);
                    }
//                }
                }

                break;
            case R.id.btn_signup:
                TabLayout tabs = (TabLayout) getActivity().findViewById(R.id.tablayout);
                tabs.getTabAt(1).select();
                break;

        }

    }

    private boolean loginvalidation() {
        if (email.getText().toString().isEmpty()) {
            email.setError("Please enter your username");
            email.requestFocus();
            return false;

        }
        if (password.getText().toString().isEmpty()) {
            password.setError("Please enter your password");
            password.requestFocus();
            return false;

        }
        return true;
    }

    private boolean validateEmail() {
        String emailinput = email.getText().toString().trim();
        if (emailinput.isEmpty()) {
            email.setError("Please enter your email");
            return false;

        } else if (!Patterns.EMAIL_ADDRESS.matcher(emailinput).matches()) {
            email.setError("Please enter your valid email");
            return false;
        } else {
            email.setError(null);
            return true;
        }

    }

    private boolean validatepassword() {
        String passwordinput = email.getText().toString().trim();
        if (passwordinput.isEmpty()) {
            password.setError("Please enter your email");
            return false;

        } else if (!PASSWORD_PATTERN.matcher(passwordinput).matches()) {
            password.setError("Please Enter at least 1 digit,1 lowercase,1 uppercase and should be more than 8 character");
            return false;
        } else {
            password.setError(null);
            return true;
        }

    }

    private void StrictMode() {
        android.os.StrictMode.ThreadPolicy policy = new android.os.StrictMode.ThreadPolicy.Builder().permitAll().build();
        android.os.StrictMode.setThreadPolicy(policy);
    }

//    private void checkUser() {
//        if (loginvalidation()) {
//            final String emails = email.getText().toString().trim();
//            final String pass = password.getText().toString().trim();
//
//            UserAPI userAPI = RetrofitHelper.instance().create(UserAPI.class);
//            Call<Authtoken> logincall = userAPI.login(emails, pass);
//
//            logincall.enqueue(new Callback<Authtoken>() {
//                @Override
//                public void onResponse(Call<Authtoken> call, Response<Authtoken> response) {
//                    Authtoken token = response.body();
//                    if (!response.isSuccessful()) {
//                        Toast.makeText(getActivity(), response.code(), Toast.LENGTH_SHORT).show();
//                        return;
//                    }
//                    editor.putString("username", token.getUsername());
//                    editor.putString("token", token.getToken());
//                    editor.putString("_id", token.getId());
//                    editor.putString("email", emails);
//                    isloggedin=true;
//                    editor.putBoolean("loginchecker",isloggedin);
//
//                    editor.commit();
//
//                    Intent intent = new Intent(getActivity(), MainActivity.class);
//                    startActivity(intent);
//                    getActivity().finish();
//                }
//
//                @Override
//                public void onFailure(Call<Authtoken> call, Throwable t) {
//                    Toast.makeText(getActivity(), "Error" + t.getMessage(), Toast.LENGTH_SHORT).show();
//
//                }
//            });
//
//
//        }
//    }
}
