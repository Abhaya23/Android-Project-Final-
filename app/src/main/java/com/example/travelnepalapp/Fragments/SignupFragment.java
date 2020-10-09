package com.example.travelnepalapp.Fragments;


import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.travelnepalapp.API.UserAPI;
import com.example.travelnepalapp.BusinessLogic.Registerquery;
import com.example.travelnepalapp.Models.UserModel;
import com.example.travelnepalapp.Activities.Notification;
import com.example.travelnepalapp.R;
import com.example.travelnepalapp.Retrofit.RetrofitHelper;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class SignupFragment extends Fragment implements View.OnClickListener {

    Button fab, register;

    EditText name, email, passwords, imagename, username, passwordds;

    public SignupFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_signup, container, false);
        register = view.findViewById(R.id.btn_resigerbutton);

        name = view.findViewById(R.id.et_fullname_register);
        email = view.findViewById(R.id.et_email_resgister);
        passwords = view.findViewById(R.id.et_password_resgister);
        passwordds = view.findViewById(R.id.et_password2_resgister);
        username = view.findViewById(R.id.et_username_resgister);


        register.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
//            case R.id.btn_imagebutton:
//                SelectImage();

//                break;
            case R.id.btn_resigerbutton:
                registration();
                break;
        }

    }

    private void registration() {
        String success;
        String fullname = name.getText().toString();
        String emails = email.getText().toString();
        String user = username.getText().toString();
        String pass = passwords.getText().toString();
        String passs = passwordds.getText().toString();

        UserModel userModel = new UserModel(fullname, emails, user, pass, passs);
        Registerquery registerquery= new Registerquery();
        StrictMode();
        if(registerquery.register(userModel)==false){
            Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
            Vibrator vibe = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);
            vibe.vibrate(100);
        }
        else {
            Toast.makeText(getActivity(), "Succesfull", Toast.LENGTH_SHORT).show();
            Notification.givenotification(getActivity(),"Register Successfully");
            TabLayout tabs = (TabLayout) getActivity().findViewById(R.id.tablayout);
            tabs.getTabAt(0).select();

        }



    }
    private void StrictMode() {
        android.os.StrictMode.ThreadPolicy policy = new android.os.StrictMode.ThreadPolicy.Builder().permitAll().build();
        android.os.StrictMode.setThreadPolicy(policy);
    }


//    private void registration() {
//        String fullname = name.getText().toString();
//        String emails = email.getText().toString();
//        String user = username.getText().toString();
//        String pass = passwords.getText().toString();
//        String passs = passwordds.getText().toString();
//
//        UserModel userModel = new UserModel(fullname, emails, user, pass, passs);
//        UserAPI userAPI = RetrofitHelper.instance().create(UserAPI.class);
//        Call<String> usercall = userAPI.addUser(userModel);
//        usercall.enqueue(new Callback<String>() {
//            @Override
//            public void onResponse(Call<String> call, Response<String> response) {
//                if (!response.isSuccessful()) {
//                    Toast.makeText(getActivity(), response.code(), Toast.LENGTH_SHORT).show();
//                    TabLayout tabs = (TabLayout) getActivity().findViewById(R.id.tablayout);
//                    tabs.getTabAt(1).select();
//                    return;
//                }
//                String res = response.body();
//
//                Toast.makeText(getActivity(), res, Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onFailure(Call<String> call, Throwable t) {
//                Toast.makeText(getActivity(), "Error" + t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });
//    }

    private void UploadImage(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] bytes = stream.toByteArray();
        try {
            File file = new File(getActivity().getCacheDir(), "image.jpeg");
            file.createNewFile();
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(bytes);
            fos.flush();
            fos.close();

            RequestBody rb = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            MultipartBody.Part body = MultipartBody.Part.createFormData("imageName", file.getName(), rb);

            UserAPI userAPI = RetrofitHelper.instance().create(UserAPI.class);
            Call<String> imagecall = userAPI.uploadImage(body);
            imagecall.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    Toast.makeText(getActivity(), response.body() + "Hello", Toast.LENGTH_SHORT).show();
                    imagename.setText(response.body());

                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    Toast.makeText(getActivity(), "ERROR" + t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
